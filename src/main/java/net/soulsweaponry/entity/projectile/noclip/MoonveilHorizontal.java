package net.soulsweaponry.entity.projectile.noclip;

import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.projectile.PersistentProjectileEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.particle.ParticleTypes;
import net.minecraft.world.World;
import net.soulsweaponry.registry.EntityRegistry;
import net.soulsweaponry.registry.ParticleRegistry;

import java.util.Random;

public class MoonveilHorizontal extends HolyMoonlightPillar {

    public MoonveilHorizontal(EntityType<? extends PersistentProjectileEntity> entityType, World world) {
        super(entityType, world);
    }

    public MoonveilHorizontal(World world, LivingEntity user, ItemStack stack) {
        super(EntityRegistry.MOONVEIL_HORIZONTAL, world);
        this.setOwner(user);
        this.setStack(stack);
    }

    @Override
    public float getFinalDamage(LivingEntity target) {
        return (float) this.getDamage() + EnchantmentHelper.getAttackDamage(this.getStack(), target.getGroup());
    }

    @Override
    public void onRemoved() {
        Random random = new Random();
        double d = random.nextGaussian() * 0.05D;
        double e = random.nextGaussian() * 0.05D;
        double f = random.nextGaussian() * 0.05D;
        for(int j = 0; j < 300; ++j) {
            double newX = random.nextDouble() - 0.5D + random.nextGaussian() * 0.15D + d;
            double newZ = random.nextDouble() - 0.5D + random.nextGaussian() * 0.15D + e;
            double newY = random.nextDouble() - 0.5D + random.nextGaussian() * 0.5D + f;
            this.getWorld().addParticle(ParticleTypes.SOUL_FIRE_FLAME, this.getX(), this.getY(), this.getZ(), newX * 0.1f, newY * 0.5f, newZ * 0.1f);
            this.getWorld().addParticle(ParticleRegistry.NIGHTFALL_PARTICLE, this.getX(), this.getY(), this.getZ(), newX * 0.1f, newY * 0.5f, newZ * 0.1f);
        }
    }
}
