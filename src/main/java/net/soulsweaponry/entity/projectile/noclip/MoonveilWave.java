package net.soulsweaponry.entity.projectile.noclip;

import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.projectile.PersistentProjectileEntity;
import net.minecraft.particle.ParticleTypes;
import net.minecraft.world.World;
import net.soulsweaponry.registry.EntityRegistry;
import net.soulsweaponry.registry.ParticleRegistry;
import software.bernie.geckolib.animatable.GeoEntity;
import software.bernie.geckolib.core.animatable.instance.AnimatableInstanceCache;
import software.bernie.geckolib.core.animatable.instance.SingletonAnimatableInstanceCache;
import software.bernie.geckolib.core.animation.AnimatableManager;

public class MoonveilWave extends DamagingNoClipEntity implements GeoEntity {

    private final AnimatableInstanceCache factory = new SingletonAnimatableInstanceCache(this);

    public MoonveilWave(EntityType<? extends PersistentProjectileEntity> entityType, World world) {
        super(entityType, world);
    }

    public MoonveilWave(World world, LivingEntity owner, int maxAge) {
        super(EntityRegistry.MOONVEIL_WAVE, world, owner, maxAge);
    }

    @Override
    public void tick() {
        super.tick();
        if (this.getWorld().isClient) {
            for (int i = 0; i < 30; ++i) {
                this.getWorld().addParticle(ParticleRegistry.NIGHTFALL_PARTICLE, this.getParticleX(1.1f), this.getRandomBodyY(), this.getParticleZ(1.1f), 0.0D, 0.0D, 0.0D);
            }
        }
    }

    @Override
    public void onRemoved() {
        super.onRemoved();
        for (int i = 0; i < 40; ++i) {
            double d = 1D;
            float div = 4f;
            double e = this.getWorld().getRandom().nextDouble() / div - d / (div*2);
            double f = this.getWorld().getRandom().nextDouble() / div - d / (div*2);
            double g = this.getWorld().getRandom().nextDouble() / div - d / (div*2);
            this.getWorld().addParticle(ParticleTypes.SOUL_FIRE_FLAME, this.getParticleX(0.75f), this.getRandomBodyY(), this.getParticleZ(0.75f), e, f, g);
        }
    }

    @Override
    public void applyDamageEffects(LivingEntity target) {

    }

    @Override
    public void registerControllers(AnimatableManager.ControllerRegistrar controllerRegistrar) {

    }

    @Override
    public AnimatableInstanceCache getAnimatableInstanceCache() {
        return this.factory;
    }
}
