package net.soulsweaponry.entity.projectile.noclip;

import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.projectile.PersistentProjectileEntity;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.nbt.NbtList;
import net.minecraft.world.World;
import net.soulsweaponry.config.ConfigConstructor;
import net.soulsweaponry.entitydata.IEntityDataSaver;
import net.soulsweaponry.entitydata.PostureData;
import net.soulsweaponry.registry.EntityRegistry;
import net.soulsweaponry.registry.ParticleRegistry;
import software.bernie.geckolib.animatable.GeoEntity;
import software.bernie.geckolib.core.animatable.instance.AnimatableInstanceCache;
import software.bernie.geckolib.core.animatable.instance.SingletonAnimatableInstanceCache;
import software.bernie.geckolib.core.animation.*;

import java.util.*;

public class GhostGlaiveEntity extends NoClipEntity implements GeoEntity {

    private final int maxAge;
    private final AnimatableInstanceCache factory = new SingletonAnimatableInstanceCache(this);
    private final Set<UUID> entitiesHit = new HashSet<>();

    public GhostGlaiveEntity(EntityType<? extends PersistentProjectileEntity> entityType, World world) {
        super(entityType, world);
        this.maxAge = 100;
    }

    public GhostGlaiveEntity(World world, int maxAge) {
        super(EntityRegistry.GHOST_GLAIVE_TYPE, world);
        this.maxAge = maxAge;
    }

    @Override
    public void tick() {
        // NOTE: This is needed because no-clip makes the entity rotate in weird ways
        this.setPitch(0f);
        this.setYaw(0f);
        super.tick();
        this.setPitch(0f);
        this.setYaw(0f);
        if (this.getWorld().isClient) {
            for (int i = 0; i < 20; ++i) {
                this.getWorld().addParticle(ParticleRegistry.SUN_PARTICLE, this.getParticleX(0.5), this.getRandomBodyY(), this.getParticleZ(0.5), 0.0D, 0.0D, 0.0D);
            }
        } else {
            List<LivingEntity> list = this.getWorld().getNonSpectatingEntities(LivingEntity.class, this.getBoundingBox().expand(0.4D));
            for (LivingEntity living : list) {
                if (this.isOwner(living) || this.entitiesHit.contains(living.getUuid())) {
                    continue;
                }
                if (this.getOwner() instanceof LivingEntity) {
                    living.damage(this.getWorld().getDamageSources().mobProjectile(this, (LivingEntity) this.getOwner()), (float) this.getDamage());
                } else {
                    living.damage(this.getWorld().getDamageSources().mobProjectile(this, null), (float) this.getDamage());
                }
                PostureData.addPosture((IEntityDataSaver) living, ConfigConstructor.glaive_of_hodir_projectile_posture_loss);
                this.entitiesHit.add(living.getUuid());
            }
        }
        if (this.age > this.maxAge) {
            this.discard();
        }
    }

    @Override
    public void registerControllers(AnimatableManager.ControllerRegistrar controllers) {

    }

    @Override
    public AnimatableInstanceCache getAnimatableInstanceCache() {
        return factory;
    }

    @Override
    public void readCustomDataFromNbt(NbtCompound nbt) {
        super.readCustomDataFromNbt(nbt);
        if (nbt.contains("EntitiesHit", 9)) {
            NbtList list = nbt.getList("EntitiesHit", 10);
            this.entitiesHit.clear();
            for (int i = 0; i < list.size(); ++i) {
                NbtCompound tag = list.getCompound(i);
                this.entitiesHit.add(tag.getUuid("UUID"));
            }
        }
    }

    @Override
    public void writeCustomDataToNbt(NbtCompound nbt) {
        super.writeCustomDataToNbt(nbt);
        NbtList list = new NbtList();
        for (UUID uuid : this.entitiesHit) {
            list.add(this.saveUuid(uuid));
        }
        nbt.put("EntitiesHit", list);
    }

    private NbtCompound saveUuid(UUID uuid) {
        NbtCompound tag = new NbtCompound();
        tag.putUuid("UUID", uuid);
        return tag;
    }
}
