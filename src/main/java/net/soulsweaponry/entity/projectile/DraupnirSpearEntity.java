package net.soulsweaponry.entity.projectile;

import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.enchantment.Enchantments;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.data.DataTracker;
import net.minecraft.entity.data.TrackedData;
import net.minecraft.entity.data.TrackedDataHandlerRegistry;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.projectile.PersistentProjectileEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.sound.SoundEvent;
import net.minecraft.sound.SoundEvents;
import net.minecraft.util.hit.EntityHitResult;
import net.minecraft.util.math.Box;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;
import net.soulsweaponry.config.ConfigConstructor;
import net.soulsweaponry.registry.EntityRegistry;
import net.soulsweaponry.registry.WeaponRegistry;
import org.jetbrains.annotations.Nullable;
import software.bernie.geckolib.animatable.GeoEntity;
import software.bernie.geckolib.core.animatable.instance.AnimatableInstanceCache;
import software.bernie.geckolib.core.animatable.instance.SingletonAnimatableInstanceCache;
import software.bernie.geckolib.core.animation.*;
import software.bernie.geckolib.core.object.PlayState;

public class DraupnirSpearEntity extends PersistentProjectileEntity implements GeoEntity {
    private static final TrackedData<Boolean> ENCHANTED;
    private final ItemStack stack;
    private final AnimatableInstanceCache factory = new SingletonAnimatableInstanceCache(this);
    private boolean dealtDamage;
    private LivingEntity stuckEntity;

    public DraupnirSpearEntity(EntityType<? extends PersistentProjectileEntity> entityType, World world) {
        super(entityType, world);
        this.stack = new ItemStack(WeaponRegistry.DRAUPNIR_SPEAR.get());
    }

    public DraupnirSpearEntity(World world, LivingEntity owner, ItemStack stack) {
        super(EntityRegistry.DRAUPNIR_SPEAR_TYPE.get(), owner, world);
        this.stack = stack.copy();
        this.dataTracker.set(ENCHANTED, stack.hasGlint());
    }

    public void detonate() {
        if (this.getOwner() != null && this.getBlockPos() != null && !this.getWorld().isClient) {
            float power = ConfigConstructor.draupnir_spear_detonate_power
                    + ((float) EnchantmentHelper.getLevel(Enchantments.SHARPNESS, asItemStack()) / 2.5f);

            this.getWorld().createExplosion(
                    this.getOwner(),
                    this.getX(), this.getY(), this.getZ(),
                    power,
                    false,
                    World.ExplosionSourceType.NONE
            );

            if (power > 2f) {
                for (Entity entity : this.getWorld().getOtherEntities(this.getOwner(), this.getBoundingBox().expand(power))) {
                    if (entity instanceof LivingEntity living) {
                        living.addStatusEffect(new StatusEffectInstance(StatusEffects.WEAKNESS, 100, MathHelper.floor(power - 2)));
                    }
                }
            }

            this.remove(RemovalReason.DISCARDED);
        }
    }




    @Override
    protected void onEntityHit(EntityHitResult entityHitResult) {
        Entity entity = entityHitResult.getEntity();
        float f = ConfigConstructor.draupnir_spear_projectile_damage;
        if (entity instanceof LivingEntity livingEntity) {
            f += EnchantmentHelper.getAttackDamage(stack, livingEntity.getGroup());
        }

        Entity owner = this.getOwner();
        DamageSource damageSource = this.getWorld().getDamageSources().thrown(this, owner);
        this.dealtDamage = true;
        SoundEvent soundEvent = SoundEvents.ITEM_TRIDENT_HIT;

        if (entity.damage(damageSource, f)) {
            if (entity.getType() == EntityType.ENDERMAN) {
                return;
            }
            if (entity instanceof LivingEntity livingHitEntity) {
                if (owner instanceof LivingEntity livingOwner) {
                    EnchantmentHelper.onUserDamaged(livingHitEntity, owner);
                    EnchantmentHelper.onTargetDamaged(livingOwner, livingHitEntity);
                }
                this.onHit(livingHitEntity);

                // Increase stuck arrow count for visual indication
                if (!this.getWorld().isClient && this.getPierceLevel() <= 0) {
                    livingHitEntity.setStuckArrowCount(livingHitEntity.getStuckArrowCount() + 1);
                }

                // Instead of discarding, we "stick" the spear inside the entity
                // Make the spear no-clip and set it to the entity's location
                this.setNoClip(true);
                this.setVelocity(Vec3d.ZERO);
                // Position the spear roughly at the center of the target
                this.updatePosition(livingHitEntity.getX(), livingHitEntity.getBodyY(0.5), livingHitEntity.getZ());

                // Optional: Make the spear silent/invisible if desired
                // this.setInvisible(true);

                // The spear now remains inside the entity until detonated.
                // The entity ID is already stored via the item code when spawned, so no discard here.
                stuckEntity = livingHitEntity;
            }

            this.playSound(soundEvent, 1.0F, 1.0F);
        } else {
            // If no damage dealt, just bounce off slightly
            this.setVelocity(this.getVelocity().multiply(-0.01D, -0.1D, -0.01D));
            this.playSound(soundEvent, 1.0F, 1.0F);
        }
    }

    @Override
    protected void initDataTracker() {
        super.initDataTracker();
        this.dataTracker.startTracking(ENCHANTED, false);
    }

    @Override
    public void tick() {
        super.tick();
        if (this.isNoClip() && this.stuckEntity != null) {
            this.setPosition(stuckEntity.getX(), stuckEntity.getBodyY(0.5), stuckEntity.getZ());
            this.velocityDirty = true;

            float width = this.getWidth();
            float height = this.getHeight();
            double x = this.getX();
            double y = this.getY();
            double z = this.getZ();
this.setInvisible(true);
            this.setBoundingBox(new Box(
                    x - (width / 2), y,
                    z - (width / 2),
                    x + (width / 2), y + height,
                    z + (width / 2)
            ));
        }
    }


    @Override
    protected ItemStack asItemStack() {
        return this.stack;
    }

    @Nullable
    @Override
    protected EntityHitResult getEntityCollision(Vec3d currentPosition, Vec3d nextPosition) {
        return this.dealtDamage ? null : super.getEntityCollision(currentPosition, nextPosition);
    }

    @Override
    protected boolean tryPickup(PlayerEntity player) {
        return false;
    }

    protected SoundEvent getHitSound() {
        return SoundEvents.ITEM_TRIDENT_HIT_GROUND;
    }

    private PlayState predicate(AnimationState<?> state) {
        state.getController().setAnimation(RawAnimation.begin().then("idle", Animation.LoopType.LOOP));
        return PlayState.CONTINUE;
    }

    @Override
    public void registerControllers(AnimatableManager.ControllerRegistrar controllers) {
        controllers.add(new AnimationController<>(this, "controller", 0, this::predicate));
    }

    @Override
    public AnimatableInstanceCache getAnimatableInstanceCache() {
        return factory;
    }

    protected float getDragInWater() {
        return 0.99F;
    }

    static {
        ENCHANTED = DataTracker.registerData(DraupnirSpearEntity.class, TrackedDataHandlerRegistry.BOOLEAN);
    }

    @Override
    public boolean isFireImmune() {
        return true;
    }
}
