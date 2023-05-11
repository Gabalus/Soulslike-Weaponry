package net.soulsweaponry.networking;

import com.google.common.collect.Iterables;
import net.fabricmc.fabric.api.networking.v1.ServerPlayNetworking;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvents;
import net.minecraft.text.Text;
import net.minecraft.util.Hand;
import net.minecraft.util.math.Box;
import net.minecraft.util.math.MathHelper;
import net.soulsweaponry.config.ConfigConstructor;
import net.soulsweaponry.entity.mobs.FreyrSwordEntity;
import net.soulsweaponry.entity.mobs.Remnant;
import net.soulsweaponry.entity.projectile.DraupnirSpearEntity;
import net.soulsweaponry.items.*;
import net.soulsweaponry.registry.SoundRegistry;
import net.soulsweaponry.registry.WeaponRegistry;
import net.soulsweaponry.util.ParticleNetworking;
import net.soulsweaponry.util.WeaponUtil;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;

import static net.soulsweaponry.util.WeaponUtil.TRICK_WEAPONS;

public class PacketsServer {

    public static void initServer() {
        ServerPlayNetworking.registerGlobalReceiver(PacketRegistry.MOONLIGHT, (server, player, handler, buf, responseSender) -> {
            server.execute(() -> {
                ServerWorld serverWorld = Iterables.tryFind(server.getWorlds(), (element) -> element == player.world).orNull();
                if (serverWorld != null) {
                    MoonlightShortsword.summonSmallProjectile(serverWorld, player);
                }
            });
        });

        ServerPlayNetworking.registerGlobalReceiver(PacketRegistry.RETURN_FREYR_SWORD, (server, player, handler, buf, responseSender) -> {
            server.execute(() -> {
                ServerWorld serverWorld = Iterables.tryFind(server.getWorlds(), (element) -> element == player.world).orNull();
                if (serverWorld != null) {
                    //NOTE: reading bufs does not work for some reason
                    try {
                        Optional<UUID> op = player.getDataTracker().get(FreyrSword.SUMMON_UUID);
                        if (op.isPresent() && player.getBlockPos() != null) {
                            Entity sword = serverWorld.getEntity(op.get());
                            if (sword instanceof FreyrSwordEntity) {
                                if (!((FreyrSwordEntity)sword).insertStack(player)) {
                                    sword.setPos(player.getX(), player.getEyeY(), player.getZ());
                                    ((FreyrSwordEntity)sword).dropStack();
                                }
                                sword.discard();
                            } else {
                                player.sendMessage(Text.literal("There is no Freyr Sword bound to you!"), true);
                            }
                        }
                    } catch (Exception e) {
                        player.sendMessage(Text.literal("There is no Freyr Sword bound to you!"), true);
                    }
                }
            });
        });

        ServerPlayNetworking.registerGlobalReceiver(PacketRegistry.STATIONARY_FREYR_SWORD, (server, player, handler, buf, responseSender) -> {
            server.execute(() -> {
                ServerWorld serverWorld = Iterables.tryFind(server.getWorlds(), (element) -> element == player.world).orNull();
                if (serverWorld != null) {
                    try {
                        Optional<UUID> op = player.getDataTracker().get(FreyrSword.SUMMON_UUID);
                        if (op.isPresent() && player.getBlockPos() != null) {
                            Entity entity = serverWorld.getEntity(player.getDataTracker().get(FreyrSword.SUMMON_UUID).get());
                            if (entity instanceof FreyrSwordEntity sword) {
                                sword.setStationaryPos(player.getBlockPos());
                            } else {
                                player.sendMessage(Text.literal("There is no Freyr Sword bound to you!"), true);
                            }
                        }
                    } catch (Exception e) {
                        player.sendMessage(Text.literal("There is no Freyr Sword bound to you!"), true);
                    }
                }
            });
        });

        ServerPlayNetworking.registerGlobalReceiver(PacketRegistry.COLLECT_SUMMONS, (server, player, handler, buf, responseSender) -> {
            server.execute(() -> {
                ServerWorld serverWorld = Iterables.tryFind(server.getWorlds(), (element) -> element == player.world).orNull();
                if (serverWorld != null) {
                    for (Hand hand : Hand.values()) {
                        Item handItem = player.getStackInHand(hand).getItem();
                        if (handItem instanceof SoulHarvestingItem && !(handItem instanceof UmbralTrespassItem || handItem instanceof DarkinScythePre)) {
                            int collectedSouls = 0;
                            for (Entity entity : serverWorld.getOtherEntities(player, player.getBoundingBox().expand(8))) {
                                if (entity instanceof Remnant remnant && ((Remnant)entity).getOwner() == player) {
                                    collectedSouls = remnant.getSoulAmount();
                                    ParticleNetworking.sendServerParticlePacket(serverWorld, PacketRegistry.DARK_EXPLOSION_ID, entity.getBlockPos(), 10);
                                    serverWorld.playSound(null, entity.getBlockPos(), SoundEvents.ENTITY_ZOMBIE_VILLAGER_CURE, SoundCategory.PLAYERS, 0.5f, 0.7f);
                                    entity.discard();
                                }
                            }
                            SoulHarvestingItem item = (SoulHarvestingItem)player.getStackInHand(hand).getItem();
                            String msg = collectedSouls == 0 ? "There were no bound allies to collect!" : "Collected " + collectedSouls + " souls back to the " + item.getName().getString();
                            item.addAmount(player.getStackInHand(hand), collectedSouls);
                            player.sendMessage(Text.literal(msg), true);
                        }
                    }
                }
            });
        });

        ServerPlayNetworking.registerGlobalReceiver(PacketRegistry.SWITCH_TRICK_WEAPON, (server, player, handler, buf, responseSender) -> {
            server.execute(() -> {
                ServerWorld serverWorld = Iterables.tryFind(server.getWorlds(), (element) -> element == player.world).orNull();
                if (serverWorld != null) {
                    Item handItem = player.getStackInHand(Hand.MAIN_HAND).getItem();
                    if (handItem instanceof TrickWeapon && !player.getItemCooldownManager().isCoolingDown(handItem)) {
                        ItemStack stack = player.getStackInHand(Hand.MAIN_HAND);
                        TrickWeapon switchWeapon = TRICK_WEAPONS[((TrickWeapon) handItem).getSwitchWeaponIndex()];
                        if (stack.hasNbt() && stack.getNbt().contains(WeaponUtil.PREV_TRICK_WEAPON)) {
                            switchWeapon = TRICK_WEAPONS[stack.getNbt().getInt(WeaponUtil.PREV_TRICK_WEAPON)];
                        }
                        ItemStack newWeapon = new ItemStack(switchWeapon);
                        Map<Enchantment, Integer> enchants = EnchantmentHelper.get(stack);
                        for (Enchantment enchant : enchants.keySet()) {
                            newWeapon.addEnchantment(enchant, enchants.get(enchant));
                        }
                        if (stack.hasCustomName()) {
                            newWeapon.setCustomName(stack.getName());
                        }
                        if (newWeapon.hasNbt()) {
                            newWeapon.getNbt().putInt(WeaponUtil.PREV_TRICK_WEAPON, ((TrickWeapon) handItem).getOwnWeaponIndex());
                            if (stack.getNbt().contains(WeaponUtil.CHARGE)) {
                                newWeapon.getNbt().putInt(WeaponUtil.CHARGE, stack.getNbt().getInt(WeaponUtil.CHARGE));
                            }
                        }
                        serverWorld.playSound(null, player.getBlockPos(), SoundRegistry.TRICK_WEAPON_EVENT, SoundCategory.PLAYERS, 0.8f, MathHelper.nextFloat(player.getRandom(), 0.75f, 1.5f));
                        ParticleNetworking.sendServerParticlePacket(serverWorld, PacketRegistry.DARK_EXPLOSION_ID, player.getBlockPos(), 20);
                        newWeapon.setDamage(stack.getDamage());
                        int slot = player.getInventory().selectedSlot;
                        player.getInventory().removeStack(slot);
                        player.getInventory().insertStack(slot, newWeapon);
                        player.getItemCooldownManager().set(switchWeapon, 20);
                    }
                }
            });
        });

        ServerPlayNetworking.registerGlobalReceiver(PacketRegistry.DETONATE_DRAUPNIR_SPEAR, (server, player, handler, buf, responseSender) -> {
            server.execute(() -> {
                ServerWorld serverWorld = Iterables.tryFind(server.getWorlds(), (element) -> element == player.world).orNull();
                if (serverWorld != null) {
                    ItemStack stack = player.getStackInHand(Hand.MAIN_HAND);
                    if (stack.isOf(WeaponRegistry.DRAUPNIR_SPEAR) && !player.getItemCooldownManager().isCoolingDown(stack.getItem())) {
                        Box box = player.getBoundingBox().expand(3);
                        List<Entity> entities = serverWorld.getOtherEntities(player, box);
                        float power = ConfigConstructor.draupnir_spear_projectile_damage;
                        for (Entity entity : entities) {
                            if (entity instanceof LivingEntity) {
                                entity.damage(serverWorld.getDamageSources().mobAttack(player), power + EnchantmentHelper.getAttackDamage(stack, ((LivingEntity) entity).getGroup()));
                                entity.addVelocity(0, .1f, 0);
                            }
                        }
                        ParticleNetworking.specificServerParticlePacket(serverWorld, PacketRegistry.GRAND_SKYFALL_SMASH_ID, player.getBlockPos(), 0.5D);
                        serverWorld.playSound(null, player.getBlockPos(), SoundEvents.ENTITY_GENERIC_EXPLODE, SoundCategory.PLAYERS, 1f, 1f);

                        if (stack.hasNbt() && stack.getNbt().contains(DraupnirSpear.SPEARS_ID)) {
                            int[] ids = stack.getNbt().getIntArray(DraupnirSpear.SPEARS_ID);
                            for (int id : ids) {
                                Entity entity = serverWorld.getEntityById(id);
                                if (entity instanceof DraupnirSpearEntity spear) {
                                    spear.detonate();
                                }
                            }
                            stack.getNbt().putIntArray(DraupnirSpear.SPEARS_ID, new int[0]);
                            player.getItemCooldownManager().set(stack.getItem(), ConfigConstructor.draupnir_spear_detonate_cooldown);
                        }
                    }
                }
            });
        });
    }
}
