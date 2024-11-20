package net.soulsweaponry.items.sword;

import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ToolMaterial;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvents;
import net.minecraft.util.Hand;
import net.minecraft.util.TypedActionResult;
import net.minecraft.util.UseAction;
import net.minecraft.util.math.*;
import net.minecraft.world.World;
import net.soulsweaponry.config.ConfigConstructor;
import net.soulsweaponry.entity.projectile.noclip.HolyMoonlightPillar;
import net.soulsweaponry.items.IChargeNeeded;
import net.soulsweaponry.items.TrickWeapon;
import net.soulsweaponry.particles.ParticleEvents;
import net.soulsweaponry.particles.ParticleHandler;
import net.soulsweaponry.registry.EffectRegistry;
import net.soulsweaponry.registry.EntityRegistry;
import net.soulsweaponry.registry.SoundRegistry;
import net.soulsweaponry.util.CustomDamageSource;
import net.soulsweaponry.util.WeaponUtil;

public class HolyMoonlightGreatsword extends TrickWeapon implements IChargeNeeded {

    public HolyMoonlightGreatsword(ToolMaterial toolMaterial, Settings settings, int switchWeaponIndex) {
        super(toolMaterial, 3, settings, switchWeaponIndex, 3, false, true);
        this.addTooltipAbility(WeaponUtil.TooltipAbilities.NEED_CHARGE, WeaponUtil.TooltipAbilities.LUNAR_HERALD_NO_CHARGE, WeaponUtil.TooltipAbilities.CHARGE, WeaponUtil.TooltipAbilities.MOONFALL);
    }

    @Override
    public void onStoppedUsing(ItemStack stack, World world, LivingEntity user, int remainingUseTicks) {
        if (user instanceof PlayerEntity player) {
            int chargeTime = this.getMaxUseTime(stack) - remainingUseTicks;
            if (chargeTime >= 10) {
                int emp = player.hasStatusEffect(EffectRegistry.MOON_HERALD) ? 20 * player.getStatusEffect(EffectRegistry.MOON_HERALD).getAmplifier() : 0;
                this.applyItemCooldown(player, Math.max(ConfigConstructor.holy_moonlight_ability_min_cooldown, ConfigConstructor.holy_moonlight_ability_cooldown - this.getReduceCooldownEnchantLevel(stack) * 30 - emp));
                stack.damage(5, player, (p_220045_0_) -> p_220045_0_.sendToolBreakStatus(player.getActiveHand()));
                int ruptures = ConfigConstructor.holy_moonlight_ruptures_amount + WeaponUtil.getEnchantDamageBonus(stack);
                Vec3d vecBlocksAway = player.getRotationVector().multiply(3).add(player.getPos());
                BlockPos targetArea = new BlockPos((int)vecBlocksAway.x, (int)user.getY(), (int)vecBlocksAway.z);
                float power = ConfigConstructor.holy_moonlight_ability_damage;
                for (Entity entity : world.getOtherEntities(player, new Box(targetArea).expand(3))) {
                    if (entity instanceof LivingEntity) {
                        entity.damage(CustomDamageSource.create(world, CustomDamageSource.OBLITERATED, player), power + 2 * EnchantmentHelper.getAttackDamage(stack, ((LivingEntity) entity).getGroup()));
                        entity.addVelocity(0, this.getKnockup(stack), 0);
                    }
                }
                if (!world.isClient) {
                    WeaponUtil.doConsumerOnLine(world, user.getYaw() + 90, user.getPos(), user.getY() - 2, ruptures, 1.75f,
                            (Vec3d position, Integer warmup, Float yaw) -> {
                                HolyMoonlightPillar pillar = new HolyMoonlightPillar(EntityRegistry.HOLY_MOONLIGHT_PILLAR, world);
                                pillar.setOwner(user);
                                pillar.setParticleMod(1f);
                                pillar.setRadius(1.85f);
                                pillar.setDamage(this.getAbilityDamage());
                                pillar.setKnockUp(this.getKnockup(stack));
                                pillar.setWarmup(warmup);
                                pillar.setPos(position.getX(), position.getY(), position.getZ());
                                world.spawnEntity(pillar);
                            }
                    );
                }
                if (stack.hasNbt() && !player.isCreative()) {
                    stack.getNbt().putInt(IChargeNeeded.CHARGE, 0);
                }
                world.playSound(player, targetArea, SoundRegistry.MOONLIGHT_BIG_EVENT, SoundCategory.PLAYERS, 1f, 1f);
                world.playSound(player, targetArea, SoundEvents.ENTITY_GENERIC_EXPLODE, SoundCategory.PLAYERS, 1f, 1f);
                if (!world.isClient) {
                    ParticleHandler.particleOutburstMap(player.getWorld(), 150, vecBlocksAway.getX(), user.getY(), vecBlocksAway.getZ(), ParticleEvents.MOONFALL_MAP, 1f);
                }
            }
        }
    }

    @Override
    public boolean postHit(ItemStack stack, LivingEntity target, LivingEntity attacker) {
        if (!this.isDisabled(stack)) this.addCharge(stack, this.getAddedCharge(stack));
        return super.postHit(stack, target, attacker);
    }

    private float getAbilityDamage() {
        return ConfigConstructor.holy_moonlight_ability_damage;
    }

    private float getKnockup(ItemStack stack) {
        return ConfigConstructor.holy_moonlight_ability_knockup + (float)WeaponUtil.getEnchantDamageBonus(stack)/10;
    }

    @Override
    public TypedActionResult<ItemStack> use(World world, PlayerEntity user, Hand hand) {
        ItemStack itemStack = user.getStackInHand(hand);
        if (this.isDisabled(itemStack)) {
            this.notifyDisabled(user);
            return TypedActionResult.fail(itemStack);
        }
        if (itemStack.getDamage() < itemStack.getMaxDamage() - 1 && (this.isCharged(itemStack) || user.isCreative() || user.hasStatusEffect(EffectRegistry.MOON_HERALD))) {
            user.setCurrentHand(hand);
            return TypedActionResult.success(itemStack);
        }
        else {
            return TypedActionResult.fail(itemStack);
        }
    }

    public UseAction getUseAction(ItemStack stack) {
        return UseAction.SPEAR;
    }

    public int getMaxUseTime(ItemStack stack) {
        return 72000;
    }

    @Override
    public int getMaxCharge() {
        return ConfigConstructor.holy_moonlight_ability_charge_needed;
    }

    @Override
    public int getAddedCharge(ItemStack stack) {
        int base = ConfigConstructor.holy_moonlight_greatsword_charge_added_post_hit;
        return base + WeaponUtil.getEnchantDamageBonus(stack) * 2;
    }

    @Override
    public boolean canEnchantReduceCooldown(ItemStack stack) {
        return ConfigConstructor.holy_moonlight_ability_enchant_reduces_cooldown;
    }

    @Override
    public String getReduceCooldownEnchantId(ItemStack stack) {
        return ConfigConstructor.holy_moonlight_ability_enchant_reduces_cooldown_id;
    }
}