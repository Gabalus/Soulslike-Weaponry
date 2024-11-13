package net.soulsweaponry.items.spear;

import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ToolMaterial;
import net.soulsweaponry.config.ConfigConstructor;
import net.soulsweaponry.items.BladeDanceItem;

public class GlaiveOfHodir extends BladeDanceItem {

    public GlaiveOfHodir(ToolMaterial toolMaterial, Settings settings) {
        super(toolMaterial, ConfigConstructor.glaive_of_hodir_damage, ConfigConstructor.glaive_of_hodir_attack_speed, settings);
    }//TODO make better combat file with cool attacks
    //TODO make model and textures
    //TODO make an 'use' ability where you throw out a spinning ghost glaive a short distance that goes through walls
    //TODO make blade dance effect texture

    @Override
    public boolean isFireproof() {
        return ConfigConstructor.is_fireproof_glaive_of_hodir;
    }

    @Override
    public boolean isDisabled(ItemStack stack) {
        return ConfigConstructor.disable_use_glaive_of_hodir;
    }

    @Override
    public boolean canEnchantReduceCooldown(ItemStack stack) {
        return ConfigConstructor.glaive_of_hodir_enchant_reduces_cooldown;
    }

    @Override
    public String getReduceCooldownEnchantId(ItemStack stack) {
        return ConfigConstructor.glaive_of_hodir_enchant_reduces_cooldown_id;
    }

    @Override
    public float getBonusDamagePerStack() {
        return ConfigConstructor.glaive_of_hodir_bonus_damage_per_stack;
    }

    @Override
    public float getBonusAttackSpeedPerStack() {
        return ConfigConstructor.glaive_of_hodir_bonus_attack_speed_per_stack;
    }

    @Override
    public int getMaxStacks() {
        return ConfigConstructor.glaive_of_hodir_max_stacks;
    }

    @Override
    public void applyMaxStacksEffects(LivingEntity entity, ItemStack stack) {
        entity.addStatusEffect(new StatusEffectInstance(StatusEffects.RESISTANCE, ConfigConstructor.glaive_of_hodir_effects_duration, ConfigConstructor.glaive_of_hodir_resistance_amplifier - 1));
        entity.addStatusEffect(new StatusEffectInstance(StatusEffects.ABSORPTION, ConfigConstructor.glaive_of_hodir_effects_duration, ConfigConstructor.glaive_of_hodir_absorption_amplifier - 1));
    }

    @Override
    public int getMaxStacksCooldown() {
        return ConfigConstructor.glaive_of_hodir_add_effects_cooldown;
    }
}
