package net.soulsweaponry.entity.projectile.noclip;

import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.projectile.PersistentProjectileEntity;
import net.minecraft.world.World;

import java.util.List;

public abstract class DamagingWarmupEntity extends NoClipWarmupEntity{

    public DamagingWarmupEntity(EntityType<? extends PersistentProjectileEntity> entityType, World world) {
        super(entityType, world);
    }

    @Override
    public void tick() {
        super.tick();
        if (!this.getWorld().isClient) {
            this.setWarmup(this.getWarmup() - 1);
            if (this.getWarmup() < 0) {
                if (this.getWarmup() == -7) {
                    List<LivingEntity> list = this.getWorld().getNonSpectatingEntities(LivingEntity.class, this.getBoundingBox().expand(0.2D));
                    for (LivingEntity livingEntity : list) {
                        if (livingEntity.isTeammate(this.getOwner()) || this.isOwner(livingEntity) || !livingEntity.isAlive() || livingEntity.isInvulnerable()) {
                            continue;
                        }
                        boolean wasHit;
                        if (this.getOwner() instanceof LivingEntity) {
                            wasHit = livingEntity.damage(this.getWorld().getDamageSources().mobProjectile(this, (LivingEntity) this.getOwner()), (float) this.getDamage() + this.getBonusDamage(livingEntity));
                        } else {
                            wasHit = livingEntity.damage(this.getWorld().getDamageSources().mobProjectile(this, null), (float) this.getDamage() + this.getBonusDamage(livingEntity));
                        }
                        this.applyDamageEffects(wasHit, livingEntity);
                    }
                    this.onTrigger();
                    this.discard();
                }
            }
        }
    }

    public abstract void applyDamageEffects(boolean wasHit, LivingEntity target);
    public abstract void onTrigger();

    public float getBonusDamage(LivingEntity target) {
        return 0f;
    }
}
