package net.soulsweaponry.items;

import com.google.common.collect.ImmutableMultimap;
import com.google.common.collect.Multimap;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.attribute.EntityAttribute;
import net.minecraft.entity.attribute.EntityAttributeModifier;
import net.minecraft.entity.attribute.EntityAttributes;
import net.minecraft.entity.player.ItemCooldownManager;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ToolMaterial;
import net.minecraft.text.Text;
import net.minecraft.world.World;
import net.soulsweaponry.config.CommonConfig;
import net.soulsweaponry.util.WeaponUtil;

public class CrucibleSword extends ModdedSword {

    private static final String EMP = "empowered";

    public CrucibleSword(ToolMaterial toolMaterial, Settings settings) {
        super(toolMaterial, CommonConfig.CRUCIBLE_SWORD_DAMAGE.get(), CommonConfig.CRUCIBLE_SWORD_ATTACK_SPEED.get(), settings);
        this.addTooltipAbility(WeaponUtil.TooltipAbilities.DOOM);
    }

    @Override
    public boolean postHit(ItemStack stack, LivingEntity target, LivingEntity attacker) {
        if (attacker instanceof PlayerEntity && !this.isDisabled(stack)) {
            ItemCooldownManager cooldownManager = ((PlayerEntity) attacker).getItemCooldownManager();
            if (!cooldownManager.isCoolingDown(this)) {
                cooldownManager.set(this, CommonConfig.CRUCIBLE_SWORD_EMP_COOLDOWN.get() - WeaponUtil.getEnchantDamageBonus(stack) * 20);
            }
        }
        return super.postHit(stack, target, attacker);
    }

    @Override
    public void inventoryTick(ItemStack stack, World world, Entity entity, int slot, boolean selected) {
        super.inventoryTick(stack, world, entity, slot, selected);
        if (entity instanceof PlayerEntity player) {
            this.updateEmpowered(stack, !player.getItemCooldownManager().isCoolingDown(this));
        }
    }

    private void updateEmpowered(ItemStack stack, boolean bl) {
        if (stack.hasNbt()) {
            stack.getNbt().putBoolean(EMP, bl);
        }
    }

    private boolean isEmpowered(ItemStack stack) {
        if (stack.hasNbt() && stack.getNbt().contains(EMP)) {
            return stack.getNbt().getBoolean(EMP);
        } else {
            return false;
        }
    }

    @Override
    public Multimap<EntityAttribute, EntityAttributeModifier> getAttributeModifiers(EquipmentSlot slot, ItemStack stack) {
        Multimap<EntityAttribute, EntityAttributeModifier> attributeModifiers;
        if (slot == EquipmentSlot.MAINHAND) {
            ImmutableMultimap.Builder<EntityAttribute, EntityAttributeModifier> builder = ImmutableMultimap.builder();
            builder.put(EntityAttributes.GENERIC_ATTACK_DAMAGE, new EntityAttributeModifier(ATTACK_DAMAGE_MODIFIER_ID, "Weapon modifier", (this.isEmpowered(stack) && !this.isDisabled(stack) ? CommonConfig.CRUCIBLE_SWORD_EMP_DAMAGE.get() : CommonConfig.CRUCIBLE_SWORD_DAMAGE.get()) - 1, EntityAttributeModifier.Operation.ADDITION));
            builder.put(EntityAttributes.GENERIC_ATTACK_SPEED, new EntityAttributeModifier(ATTACK_SPEED_MODIFIER_ID, "Weapon modifier", this.getAttackSpeed(), EntityAttributeModifier.Operation.ADDITION));
            attributeModifiers = builder.build();
            return attributeModifiers;
        } else {
            return super.getAttributeModifiers(slot, stack);
        }
    }

    @Override
    public Text[] getAdditionalTooltips() {
        return new Text[0];
    }

    @Override
    public boolean isDisabled(ItemStack stack) {
        return CommonConfig.DISABLE_USE_CRUCIBLE_SWORD.get();
    }
}
