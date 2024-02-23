package net.soulsweaponry.mixin;

import net.minecraft.entity.EntityStatuses;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.entity.projectile.ProjectileEntity;
import net.minecraft.sound.SoundCategory;
import net.minecraft.util.math.Vec3d;
import net.soulsweaponry.config.ConfigConstructor;
import net.soulsweaponry.items.DetonateGroundItem;
import net.soulsweaponry.registry.EffectRegistry;
import net.soulsweaponry.registry.ItemRegistry;
import net.soulsweaponry.registry.SoundRegistry;
import net.soulsweaponry.util.ParryData;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import net.minecraft.entity.player.PlayerEntity;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import static net.soulsweaponry.items.UmbralTrespassItem.SHOULD_DAMAGE_RIDING;
import static net.soulsweaponry.items.UmbralTrespassItem.TICKS_BEFORE_DISMOUNT;


@Mixin(PlayerEntity.class)
public class PlayerEntityMixin {

    @Inject(method = "handleFallDamage", at = @At("HEAD"), cancellable = true)
    public void interceptFallDamage(float fallDistance, float damageMultiplier, DamageSource source, CallbackInfoReturnable<Boolean> info) {
        if (DetonateGroundItem.triggerCalculateFall(((PlayerEntity)(Object)this), fallDistance, source)) {
            info.setReturnValue(false);
            info.cancel();
        }
    }

    @Inject(method = "tickRiding", at = @At("HEAD"))
    public void interceptTickRiding(CallbackInfo info) {
        PlayerEntity player = ((PlayerEntity) (Object)this);
        try {
            if (player.getDataTracker().get(SHOULD_DAMAGE_RIDING)) {
                int time = player.getDataTracker().get(TICKS_BEFORE_DISMOUNT);
                player.addStatusEffect(new StatusEffectInstance(StatusEffects.INVISIBILITY, 10, 0));
                if (time < 0) {
                    player.stopRiding();
                } else {
                    player.getDataTracker().set(TICKS_BEFORE_DISMOUNT, time - 1);
                }
            }
        } catch (Exception ignored) {}
    }

    @Inject(method = "damage", at = @At("HEAD"), cancellable = true)
    public void interceptDamage(DamageSource source, float amount, CallbackInfoReturnable<Boolean> info) {
        PlayerEntity player = ((PlayerEntity) (Object)this);
        try {
            if (player.getDataTracker().get(SHOULD_DAMAGE_RIDING)) {
                info.setReturnValue(false);
            }
        } catch (Exception ignored) {}
        int frames = ParryData.getParryFrames(player);
        if (frames >= 1 && frames <= ConfigConstructor.shield_parry_frames && !source.isUnblockable()) {
            player.world.sendEntityStatus(player, EntityStatuses.BLOCK_WITH_SHIELD);
            if (source.isProjectile() && source.getSource() instanceof ProjectileEntity) {
                info.setReturnValue(false);
                return;
            }
            if (source.getAttacker() instanceof LivingEntity attacker) {
                if (!attacker.hasStatusEffect(EffectRegistry.POSTURE_BREAK)) {
                    attacker.world.playSound(null, attacker.getBlockPos(), SoundRegistry.POSTURE_BREAK_EVENT, SoundCategory.PLAYERS, .5f, 1f);
                }
                attacker.addStatusEffect(new StatusEffectInstance(EffectRegistry.POSTURE_BREAK, 60, 0));
                attacker.takeKnockback(0.4f,  player.getX() - attacker.getX(), player.getZ() - attacker.getZ());
                info.setReturnValue(false);
            }
        }
        // Enhanced arkenplate && health < 1/3 && projectile
        if (player.getInventory().getArmorStack(2).isOf(ItemRegistry.ENHANCED_ARKENPLATE) && player.getHealth() < player.getMaxHealth() * ConfigConstructor.arkenplate_mirror_trigger_percent
                && source.isProjectile() && source.getSource() instanceof ProjectileEntity projectile) {
            Vec3d playerPos = player.getPos();
            Vec3d projectilePos = projectile.getPos();
            Vec3d projectileMotion = projectile.getVelocity();
            Vec3d reflectionVector = this.calculateReflectionVector(playerPos, projectilePos, projectileMotion);
            // Reflect the projectile back
            projectile.setVelocity(reflectionVector);
            info.setReturnValue(false);
        }
    }

    @Unique
    private Vec3d calculateReflectionVector(Vec3d playerPos, Vec3d projectilePos, Vec3d projectileMotion) {
        Vec3d vectorToPlayer = playerPos.subtract(projectilePos).normalize();
        return projectileMotion.subtract(vectorToPlayer.multiply(projectileMotion.dotProduct(vectorToPlayer) * 2.0D));
    }
}
