package net.soulsweaponry.items.armor;

import net.minecraft.entity.EquipmentSlot;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ArmorMaterial;
import net.minecraft.item.Item;
import net.minecraft.text.Text;
import net.soulsweaponry.registry.ArmorRegistry;
import net.soulsweaponry.registry.EffectRegistry;

public class SoulRobesArmor extends SetBonusArmor {

    public SoulRobesArmor(ArmorMaterial material, EquipmentSlot slot, Settings settings) {
        super(material, slot, settings);
    }

    @Override
    protected void tickAdditionalSetEffects(PlayerEntity player) {}

    @Override
    protected Item getMatchingBoots() {
        return ArmorRegistry.SOUL_ROBES_BOOTS.get();
    }

    @Override
    protected Item getMatchingLegs() {
        return ArmorRegistry.SOUL_ROBES_LEGGINGS.get();
    }

    @Override
    protected Item getMatchingChest() {
        return ArmorRegistry.SOUL_ROBES_CHESTPLATE.get();
    }

    @Override
    protected Item getMatchingHead() {
        return ArmorRegistry.SOUL_ROBES_HELMET.get();
    }

    @Override
    protected StatusEffectInstance[] getFullSetEffects() {
        return new StatusEffectInstance[] {
                new StatusEffectInstance(StatusEffects.NIGHT_VISION, 400, 0),
                new StatusEffectInstance(EffectRegistry.MAGIC_RESISTANCE.get(), 40, 1)
        };
    }

    @Override
    protected Text[] getCustomTooltips() {
        return new Text[0];
    }
}