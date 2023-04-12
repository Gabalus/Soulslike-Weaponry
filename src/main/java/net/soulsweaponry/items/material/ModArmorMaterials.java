package net.soulsweaponry.items.material;

import net.minecraft.item.ArmorItem;
import net.minecraft.item.ArmorMaterial;
import net.minecraft.item.Items;
import net.minecraft.recipe.Ingredient;
import net.minecraft.sound.SoundEvent;
import net.minecraft.sound.SoundEvents;
import net.soulsweaponry.registry.ItemRegistry;

public enum ModArmorMaterials implements ArmorMaterial {
    //Boots are on the left
    CHAOS_ARMOR("chaos_armor", new int[]{500, 600, 700, 500}, new int[]{3, 6, 8, 3}, 15, SoundEvents.ITEM_ARMOR_EQUIP_NETHERITE, 3.0f, 0.1f, Ingredient.ofItems(ItemRegistry.MOONSTONE, Items.NETHERITE_INGOT)),
    CHAOS_SET("chaos_set", new int[]{400, 500, 600, 400}, new int[]{2, 3, 4, 1}, 10, SoundEvents.ITEM_ARMOR_EQUIP_LEATHER, 0f, 0f, Ingredient.ofItems(ItemRegistry.MOONSTONE)),
    SOUL_INGOT("soul_ingot", new int[]{350, 410, 480, 380}, new int[]{3, 5, 7, 3}, 10, SoundEvents.ITEM_ARMOR_EQUIP_IRON, 0f, 0f, Ingredient.ofItems(ItemRegistry.SOUL_INGOT)),
    SOUL_ROBES("soul_robes", new int[]{150, 210, 280, 180}, new int[]{2, 3, 4, 3}, 30, SoundEvents.ITEM_ARMOR_EQUIP_LEATHER, 0f, 0f, Ingredient.ofItems(ItemRegistry.SOUL_INGOT)),
    FORLORN_ARMOR("forlorn", new int[]{380, 440, 510, 320}, new int[]{3, 6, 8, 3}, 8, SoundEvents.ITEM_ARMOR_EQUIP_IRON, 0f, 0f, Ingredient.ofItems(ItemRegistry.SOUL_INGOT));

    private final String name;
    private final int[] baseDurability;
    private final int[] protectionAmounts;
    private final int enchantability;
    private final SoundEvent equipSound;
    private final float toughness;
    private final float knockbackResistance;
    private final Ingredient repairIngredientSupplier;

    private ModArmorMaterials(String name, int[] baseDurability, int[] protectionAmounts, int enchantability, SoundEvent equipSound, float toughness, float knockbackResistance, Ingredient repairIngredientSupplier) {
        this.name = name;
        this.baseDurability = baseDurability;
        this.protectionAmounts = protectionAmounts;
        this.enchantability = enchantability;
        this.equipSound = equipSound;
        this.toughness = toughness;
        this.knockbackResistance = knockbackResistance;
        this.repairIngredientSupplier = repairIngredientSupplier;
    }

    @Override
    public int getDurability(ArmorItem.Type slot) {
        return this.baseDurability[slot.getEquipmentSlot().getEntitySlotId()];
    }

    @Override
    public int getProtection(ArmorItem.Type slot) {
        return this.protectionAmounts[slot.getEquipmentSlot().getEntitySlotId()];
    }

    @Override
    public int getEnchantability() {
        return this.enchantability;
    }

    @Override
    public SoundEvent getEquipSound() {
        return this.equipSound;
    }

    @Override
    public Ingredient getRepairIngredient() {
        return this.repairIngredientSupplier;
    }

    @Override
    public String getName() {
        return this.name;
    }

    @Override
    public float getToughness() {
        return this.toughness;
    }

    @Override
    public float getKnockbackResistance() {
        return this.knockbackResistance;
    }
}
