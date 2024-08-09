package net.soulsweaponry.items;

import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.item.TooltipContext;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.SwordItem;
import net.minecraft.item.ToolMaterial;
import net.minecraft.text.Text;
import net.minecraft.text.TranslatableText;
import net.minecraft.world.World;
import net.soulsweaponry.config.ConfigConstructor;
import net.soulsweaponry.util.WeaponUtil;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public abstract class ModdedSword extends SwordItem implements IConfigDisable {

    protected final float attackSpeed;
    protected final List<WeaponUtil.TooltipAbilities> tooltipAbilities = new ArrayList<>();

    public ModdedSword(ToolMaterial toolMaterial, int attackDamage, float ingameAttackSpeed, Settings settings) {
        super(toolMaterial, attackDamage, - (4f - ingameAttackSpeed), settings);
        this.attackSpeed = - (4f - ingameAttackSpeed);
    }

    public float getAttackSpeed() {
        return attackSpeed;
    }

    public List<WeaponUtil.TooltipAbilities> getTooltipAbilities() {
        return this.tooltipAbilities;
    }

    public void addTooltipAbility(WeaponUtil.TooltipAbilities... abilities) {
        Collections.addAll(this.tooltipAbilities, abilities);
    }


    @Override
    public void appendTooltip(ItemStack stack, @Nullable World world, List<Text> tooltip, TooltipContext context) {
        if (this.isDisabled(stack)) {
            tooltip.add(new TranslatableText("tooltip.soulsweapons.disabled"));
        }
        boolean epicFight = WeaponUtil.isModLoaded("epicfight");
        boolean showInfo = !epicFight ? Screen.hasShiftDown() : Screen.hasAltDown();
        if (showInfo) {
            for (WeaponUtil.TooltipAbilities ability : this.getTooltipAbilities()) {
                WeaponUtil.addAbilityTooltip(ability, stack, tooltip);
            }
            tooltip.addAll(Arrays.asList(this.getAdditionalTooltips()));
        } else {
            if (epicFight) {
                tooltip.add(new TranslatableText("tooltip.soulsweapons.alt"));
            } else {
                tooltip.add(new TranslatableText("tooltip.soulsweapons.shift"));
            }
        }
        super.appendTooltip(stack, world, tooltip, context);
    }

    public Text[] getAdditionalTooltips() {
        return new Text[0];
    }

    public void notifyCooldown(LivingEntity user) {
        if (!ConfigConstructor.inform_player_about_cooldown_effect) {
            return;
        }
        if (user instanceof PlayerEntity player) {
            player.sendMessage(new TranslatableText("soulsweapons.weapon.on_cooldown"), true);
        }
    }

    @Override
    public abstract boolean isFireproof();
}