package net.soulsweaponry.items.sword;

import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ToolMaterial;
import net.minecraft.sound.SoundCategory;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;
import net.soulsweaponry.config.ConfigConstructor;
import net.soulsweaponry.entity.projectile.noclip.MoonveilHorizontal;
import net.soulsweaponry.entity.projectile.noclip.MoonveilWave;
import net.soulsweaponry.items.ChargeToUseItem;
import net.soulsweaponry.registry.SoundRegistry;
import net.soulsweaponry.util.WeaponUtil;

public class Moonveil extends ChargeToUseItem {

    public Moonveil(ToolMaterial toolMaterial, Settings settings) {
        super(toolMaterial, ConfigConstructor.moonveil_damage, ConfigConstructor.moonveil_attack_speed, settings);
        this.addTooltipAbility(WeaponUtil.TooltipAbilities.TRANSIENT_MOONLIGHT);
    }

    @Override
    public void onStoppedUsing(ItemStack stack, World world, LivingEntity user, int remainingUseTicks) {
        if (user instanceof PlayerEntity player && !player.getItemCooldownManager().isCoolingDown(this) && !world.isClient) {
            int time = this.getMaxUseTime(stack) - remainingUseTicks;
            if (time >= 10) {
                if (player.isSneaking()) {
                    //TODO add other sounds for moonveil wave and the slam thingy
                    WeaponUtil.doConsumerOnLine(world, user.getYaw() + 90, user.getPos(), user.getY() - 2, 5, 1.75f,
                            (Vec3d position, Integer warmup, Float yaw) -> {
                                MoonveilHorizontal entity = new MoonveilHorizontal(world, user, stack);
                                entity.setDamage(ConfigConstructor.moonveil_horizontal_damage);
                                entity.setKnockUp(ConfigConstructor.moonveil_horizontal_knockup);
                                entity.setWarmup(warmup);
                                entity.setPos(position.getX(), position.getY(), position.getZ());
                                world.spawnEntity(entity);
                            }
                    );
                } else {
                    MoonveilWave entity = new MoonveilWave(world, user, 6);
                    entity.setPos(player.getX(), player.getEyeY() - 0.3f, player.getZ());
                    entity.setVelocity(player, player.getPitch(), player.getYaw(), 0.0F, 1.0f, 1.0F);
                    entity.setDamage(ConfigConstructor.moonveil_wave_damage);
                    world.spawnEntity(entity);
                    world.playSound(null, user.getBlockPos(), SoundRegistry.MOONLIGHT_BIG_EVENT, SoundCategory.PLAYERS, 1f, 1f);
                }
                stack.damage(3, player, (p) -> p.sendToolBreakStatus(user.getActiveHand()));
                this.applyItemCooldown(player, ConfigConstructor.moonveil_ability_cooldown);
            }
        }
    }

    @Override
    public boolean isFireproof() {
        return ConfigConstructor.is_fireproof_moonveil;
    }

    @Override
    public boolean isDisabled(ItemStack stack) {
        return ConfigConstructor.disable_use_moonveil;
    }

    @Override
    public boolean canEnchantReduceCooldown(ItemStack stack) {
        return false;
    }

    @Override
    public String getReduceCooldownEnchantId(ItemStack stack) {
        return "";
    }
}
