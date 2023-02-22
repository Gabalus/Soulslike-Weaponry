package net.soulsweaponry.items;

import net.minecraft.client.render.item.BuiltinModelItemRenderer;
import net.minecraft.item.ToolMaterial;
import net.soulsweaponry.client.renderer.item.ForlornScytheRenderer;
import net.soulsweaponry.config.ConfigConstructor;
import software.bernie.geckolib.animatable.GeoItem;
import software.bernie.geckolib.animatable.client.RenderProvider;
import software.bernie.geckolib.core.animatable.instance.AnimatableInstanceCache;
import software.bernie.geckolib.core.animation.AnimatableManager;
import software.bernie.geckolib.util.GeckoLibUtil;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.item.TooltipContext;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.projectile.WitherSkullEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.text.Text;
import net.minecraft.util.Formatting;
import net.minecraft.util.Hand;
import net.minecraft.util.TypedActionResult;
import net.minecraft.world.World;

import java.util.List;
import java.util.UUID;
import java.util.function.Consumer;
import java.util.function.Supplier;


public class ForlornScythe extends SoulHarvestingItem implements GeoItem {

    private static final String CRITICAL = "3rd_shot";
    private static final String PREV_UUID = "prev_projectile_uuid";
    private final AnimatableInstanceCache factory = GeckoLibUtil.createInstanceCache(this);
    private final Supplier<Object> renderProvider = GeoItem.makeRenderer(this);

    public ForlornScythe(ToolMaterial toolMaterial, float attackSpeed, Settings settings) {
        super(toolMaterial, ConfigConstructor.forlorn_scythe_damage, attackSpeed, settings);
    }

    @Override
    public TypedActionResult<ItemStack> use(World world, PlayerEntity user, Hand hand) {
        ItemStack stack = user.getStackInHand(hand);
        if (!world.isClient) {
            this.detonatePrevEntity((ServerWorld) world, stack);
        }
        if (stack.hasNbt()) {
            if (stack.getNbt().contains(KILLS)) {
                int power = this.getSouls(stack);
                if (power > 0) {
                    WitherSkullEntity entity = new WitherSkullEntity(EntityType.WITHER_SKULL, world);
                    entity.setPos(user.getX(), user.getEyeY(), user.getZ());
                    entity.setOwner(user);
                    if (this.isCritical(stack)) {
                        entity.setCharged(true);
                        stack.getNbt().putInt(CRITICAL, 1);
                    } else {
                        stack.getNbt().putInt(CRITICAL, stack.getNbt().getInt(CRITICAL) + 1);
                    }
                    entity.setVelocity(user, user.getPitch(), user.getYaw(), 0.0F, 3f, 1.0F);
                    world.spawnEntity(entity);
                    this.setPrevUuid(stack, entity);
                    this.addAmount(stack, -1);
                    user.getItemCooldownManager().set(this, 10);
                    stack.damage(1, user, (p_220045_0_) -> {
                        p_220045_0_.sendToolBreakStatus(hand);
                    });
                    return TypedActionResult.success(stack, world.isClient());
                }
            }
        }
        return TypedActionResult.fail(stack);
    }

    /**
     * Due to the skulls' drag at 0.95 (and 0.73 for the charged ones), the projectiles WILL get stuck in the air
     * at some point. Therefore, to avoid this, the previous projectile's UUID will be stored into NBT, then
     * checked in the world whether it exists or not, then removed accordingly.
     */
    private void detonatePrevEntity(ServerWorld world, ItemStack stack) {
        if (stack.hasNbt() && stack.getNbt().contains(PREV_UUID)) {
            UUID uuid = stack.getNbt().getUuid(PREV_UUID);
            Entity entity = world.getEntity(uuid);
            if (entity != null && entity instanceof  WitherSkullEntity) {
                WitherSkullEntity skull = (WitherSkullEntity) entity;
                world.createExplosion(skull, skull.getX(), skull.getY(), skull.getZ(), skull.isCharged() ? 2f : 1f, false, World.ExplosionSourceType.MOB);
                skull.discard();
            }
        }
    }

    private void setPrevUuid(ItemStack stack, Entity entityToSet) {
        if (stack.hasNbt()) {
            stack.getNbt().putUuid(PREV_UUID, entityToSet.getUuid());
        }
    }

    private boolean isCritical(ItemStack stack) {
        if (stack.hasNbt() && stack.getNbt().contains(CRITICAL)) {
            if (stack.getNbt().getInt(CRITICAL) >= 3) {
                return true;
            }
        } else {
            stack.getNbt().putInt(CRITICAL, 1);
        }
        return false;
    }

    @Override
    public void appendTooltip(ItemStack stack, World world, List<Text> tooltip, TooltipContext context) {
        if (Screen.hasShiftDown()) {
            String kills = "0";
            if (stack.hasNbt() && stack.getNbt().contains(KILLS)) {
                kills = String.valueOf(stack.getNbt().getInt(KILLS));
            }
            tooltip.add(Text.translatable("tooltip.soulsweapons.soul_trap").formatted(Formatting.DARK_PURPLE));
            tooltip.add(Text.translatable("tooltip.soulsweapons.soul_trap_description").formatted(Formatting.GRAY));
            tooltip.add(Text.translatable("tooltip.soulsweapons.soul_release_wither").formatted(Formatting.DARK_RED));
            tooltip.add(Text.translatable("tooltip.soulsweapons.soul_release_wither_description_1").formatted(Formatting.GRAY));
            tooltip.add(Text.translatable("tooltip.soulsweapons.soul_release_wither_description_2").formatted(Formatting.GRAY));
            tooltip.add(Text.translatable("tooltip.soulsweapons.soul_release_wither_description_3").formatted(Formatting.GRAY));
            tooltip.add(Text.translatable("tooltip.soulsweapons.soul_trap_kills").formatted(Formatting.DARK_AQUA));
            tooltip.add(Text.literal(kills).formatted(Formatting.WHITE));
        } else {
            tooltip.add(Text.translatable("tooltip.soulsweapons.shift"));
        }
        super.appendTooltip(stack, world, tooltip, context);
    }

    @Override
    public void registerControllers(AnimatableManager.ControllerRegistrar controllers) {}

    @Override
    public AnimatableInstanceCache getAnimatableInstanceCache() {
        return this.factory;
    }

    @Override
    public void createRenderer(Consumer<Object> consumer) {
        consumer.accept(new RenderProvider() {
            private final ForlornScytheRenderer renderer = new ForlornScytheRenderer();

            @Override
            public BuiltinModelItemRenderer getCustomRenderer() {
                return this.renderer;
            }
        });
    }

    @Override
    public Supplier<Object> getRenderProvider() {
        return this.renderProvider;
    }
    
}
