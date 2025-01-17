package net.soulsweaponry.mixin;

import com.llamalad7.mixinextras.injector.ModifyReturnValue;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.sound.SoundCategory;
import net.minecraft.util.ActionResult;
import net.soulsweaponry.config.ConfigConstructor;
import net.soulsweaponry.entitydata.UmbralTrespassData;
import net.soulsweaponry.events.LivingEntityTickCallback;
import net.soulsweaponry.items.DetonateGroundItem;
import net.soulsweaponry.items.IUltraHeavy;
import net.soulsweaponry.particles.ParticleEvents;
import net.soulsweaponry.particles.ParticleHandler;
import net.soulsweaponry.registry.EffectRegistry;
import net.soulsweaponry.registry.SoundRegistry;
import net.soulsweaponry.util.ModifyDamageUtil;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(LivingEntity.class)
public class LivingEntityMixin {

    @Unique
    private DamageSource capturedDamageSource;

    @Inject(method = "modifyAppliedDamage", at = @At("HEAD"))
    private void captureDamageSource(DamageSource source, float amount, CallbackInfoReturnable<Float> info) {
        this.capturedDamageSource = source;
    }

    /*
     * NB! Only called if the damage is bigger than 0 (decimals count)
     */
    @ModifyReturnValue(method = "modifyAppliedDamage", at = @At("TAIL"))
    private float modifyDamageReturnValue(float originalAmount) {
        LivingEntity entity = (LivingEntity) (Object) this;
        if (capturedDamageSource != null) {
            return ModifyDamageUtil.modifyDamageTaken(entity, originalAmount, capturedDamageSource);
        }
        return originalAmount;
    }

    @Inject(method = "damage", at = @At("TAIL"))
    public void interceptDamage(DamageSource source, float amount, CallbackInfoReturnable<Boolean> info) {
        LivingEntity entity = ((LivingEntity)(Object)this);
        // Remove stacks of Blade Dance when taking damage
        if (info.getReturnValue() && entity.hasStatusEffect(EffectRegistry.BLADE_DANCE)) {
            int amp = entity.getStatusEffect(EffectRegistry.BLADE_DANCE).getAmplifier();
            int duration = entity.getStatusEffect(EffectRegistry.BLADE_DANCE).getDuration();
            entity.removeStatusEffect(EffectRegistry.BLADE_DANCE);
            amp--;
            if (amp >= 0) {
                entity.addStatusEffect(new StatusEffectInstance(EffectRegistry.BLADE_DANCE, duration, amp));
            }
        }
    }

    @Inject(method = "heal", at = @At("HEAD"), cancellable = true)
    public void interceptHeal(float amount, CallbackInfo info) {
        LivingEntity entity = ((LivingEntity)(Object)this);
        if (entity.hasStatusEffect(EffectRegistry.DISABLE_HEAL)) {
            info.cancel();
        }
    }

    @Inject(method = "handleFallDamage", at = @At("HEAD"), cancellable = true)
    public void interceptFallDamage(float fallDistance, float damageMultiplier, DamageSource source, CallbackInfoReturnable<Boolean> info) {
        //Another interceptFallDamage is made for players in PlayerEntityMixin since it won't trigger if they are in creative
        //from this, but in survival it would trigger twice. This check is therefore needed to prevent the double call.
        LivingEntity entity = ((LivingEntity)(Object)this);
        if (!(entity instanceof PlayerEntity) && DetonateGroundItem.triggerCalculateFall(entity, fallDistance, source)) {
            info.setReturnValue(false);
            info.cancel();
        }
    }

    @Inject(method = "onDismounted", at = @At("HEAD"))
    public void interceptDismount(Entity entity, CallbackInfo info) {
        LivingEntity thisEntity = ((LivingEntity)(Object)this);
        if (!thisEntity.getWorld().isClient && entity instanceof LivingEntity target && thisEntity instanceof PlayerEntity player) {
            if (UmbralTrespassData.shouldDamageRiding(player)) {
                float damage = UmbralTrespassData.getAbilityDamage(player);
                boolean shouldHeal = UmbralTrespassData.shouldAbilityHeal(player);
                if (shouldHeal) {
                    damage += target.getMaxHealth() * (ConfigConstructor.darkin_scythe_prime_ability_percent_health_damage / 100f);
                    float healing = damage * ConfigConstructor.darkin_scythe_prime_heal_modifier;
                    player.heal(healing);
                }
                player.removeStatusEffect(StatusEffects.INVISIBILITY);
                player.removeStatusEffect(EffectRegistry.GHOSTLY);
                target.damage(player.getWorld().getDamageSources().mobAttack(player), damage);
                UmbralTrespassData.setShouldDamageRiding(player, false);
                if (!player.getWorld().isClient && player.getBlockPos() != null) {
                    player.getWorld().playSound(null, player.getBlockPos(), SoundRegistry.SLICE_TARGET_EVENT, SoundCategory.PLAYERS, 0.8f, 1f);
                    ParticleHandler.particleOutburstMap(player.getWorld(), 150, player.getX(), player.getEyeY(), player.getZ(), ParticleEvents.SOUL_FLAME_SMALL_OUTBURST_MAP, 1f);
                }
            }
        }
    }

    @Inject(method = "tick", at = @At("TAIL"), cancellable = true)
    private void interceptTick(CallbackInfo info) {
        LivingEntity entity = ((LivingEntity)(Object)this);
        ActionResult result = LivingEntityTickCallback.EVENT.invoker().tick(entity);
        if (result == ActionResult.FAIL) {
            info.cancel();
        }
    }

    // Disable shields with ultra heavy if config line is enabled
    @Inject(method = "disablesShield", at = @At("HEAD"), cancellable = true)
    private void interceptDisablesShield(CallbackInfoReturnable<Boolean> info) {
        if (ConfigConstructor.ultra_heavy_disables_shields) {
            LivingEntity entity = ((LivingEntity)(Object)this);
            info.setReturnValue(entity.getMainHandStack().getItem() instanceof IUltraHeavy item && item.isHeavy());
            info.cancel();
        }
    }
}
