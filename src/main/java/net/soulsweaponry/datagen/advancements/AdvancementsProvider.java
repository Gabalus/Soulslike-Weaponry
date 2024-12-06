package net.soulsweaponry.datagen.advancements;

import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricAdvancementProvider;
import net.minecraft.advancement.Advancement;
import net.minecraft.advancement.AdvancementFrame;
import net.minecraft.advancement.criterion.*;
import net.minecraft.item.Item;
import net.minecraft.item.Items;
import net.minecraft.predicate.NumberRange;
import net.minecraft.predicate.entity.DistancePredicate;
import net.minecraft.predicate.entity.EntityEffectPredicate;
import net.minecraft.predicate.entity.EntityPredicate;
import net.minecraft.predicate.entity.LocationPredicate;
import net.minecraft.predicate.item.ItemPredicate;
import net.minecraft.registry.tag.TagKey;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;
import net.soulsweaponry.SoulsWeaponry;
import net.soulsweaponry.registry.*;
import net.soulsweaponry.util.ModTags;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;

public class AdvancementsProvider extends FabricAdvancementProvider {

    /**
     * List that should contain all the weapons that are needed to acquire all_weapons advancement
     */
    public static final List<Item> ALL_WEAPONS = new ArrayList<>();
    public static final List<Item> ALL_GUNS = new ArrayList<>();

    public AdvancementsProvider(FabricDataOutput output) {
        super(output);
    }

    @Override
    public void generateAdvancement(Consumer<Advancement> consumer) {
        Advancement root = Advancement.Builder.create()
                .display(
                        ItemRegistry.LOST_SOUL, // The display icon
                        Text.translatable("advancements.soulsweapons.root.title"), // Title
                        Text.translatable("advancements.soulsweapons.root.description"), // Description
                        new Identifier("textures/gui/advancements/backgrounds/obsidian.png"), // Background image
                        AdvancementFrame.TASK, // Options: TASK, CHALLENGE, GOAL
                        false, // Show toast top right
                        false, // Announce to chat
                        false // Hidden in the advancement tab
                )
                // The first string used in criterion is the name referenced by other advancements when they want to have 'requirements'
                .criterion("crafting_table", InventoryChangedCriterion.Conditions.items(Items.CRAFTING_TABLE))
                .build(consumer, SoulsWeaponry.ModId + ":root"); // "root" is the name

        // Items/base ones
        Advancement lordSoul = this.generateAdvancement(consumer, "lord_soul", root, ItemRegistry.LORD_SOUL_ROSE,
                AdvancementFrame.CHALLENGE, true, true, false, ModTags.Items.LORD_SOUL);
        Advancement moonstone = this.generateAdvancement(consumer, "moonstone", root, ItemRegistry.MOONSTONE,
                AdvancementFrame.TASK, true, true, false, ItemRegistry.MOONSTONE);
        Advancement verglas = this.generateAdvancement(consumer, "verglas", root, ItemRegistry.VERGLAS,
                AdvancementFrame.TASK, true, true, false, ItemRegistry.VERGLAS);
        Advancement uncertainty = this.generateAdvancement(consumer, "uncertainty", root, ItemRegistry.SHARD_OF_UNCERTAINTY,
                AdvancementFrame.TASK, true, true, false, ItemRegistry.SHARD_OF_UNCERTAINTY);
        Advancement demonHeart = this.generateAdvancement(consumer, "demon_heart", root, ItemRegistry.DEMON_HEART,
                AdvancementFrame.TASK, true, true, false, ItemRegistry.DEMON_HEART);
        Advancement bloodthirsty = this.generateAdvancement(consumer, "bloodthirsty", demonHeart, WeaponRegistry.BLOODTHIRSTER,
                AdvancementFrame.TASK, true, true, false,
                InventoryChangedCriterion.Conditions.items(WeaponRegistry.BLOODTHIRSTER),
                EffectsChangedCriterion.Conditions.create(EntityEffectPredicate.create().withEffect(EffectRegistry.BLOODTHIRSTY)));//TODO test

        // Boss kills
        Advancement returningKnight = this.generateAdvancement(consumer, "returning_knight", root, ItemRegistry.ARKENSTONE, AdvancementFrame.CHALLENGE, true, true, false,
                OnKilledCriterion.Conditions.createPlayerKilledEntity(EntityPredicate.Builder.create().type(EntityRegistry.RETURNING_KNIGHT)));
        Advancement draugrBoss = this.generateAdvancement(consumer, "draugr_boss", root, ItemRegistry.ESSENCE_OF_EVENTIDE, AdvancementFrame.GOAL, true, true, false,
                OnKilledCriterion.Conditions.createPlayerKilledEntity(EntityPredicate.Builder.create().type(EntityRegistry.DRAUGR_BOSS)));
        Advancement moonknight = this.generateAdvancement(consumer, "moonknight", draugrBoss, ItemRegistry.ESSENCE_OF_LUMINESCENCE, AdvancementFrame.CHALLENGE, true, true, false,
                OnKilledCriterion.Conditions.createPlayerKilledEntity(EntityPredicate.Builder.create().type(EntityRegistry.MOONKNIGHT)));
        Advancement decayingKing = this.generateAdvancement(consumer, "end_of_reigns", bloodthirsty, ItemRegistry.WITHERED_DEMON_HEART, AdvancementFrame.CHALLENGE, true, true, false,
                OnKilledCriterion.Conditions.createPlayerKilledEntity(EntityPredicate.Builder.create().type(EntityRegistry.ACCURSED_LORD_BOSS)));
        Advancement chaosMonarch = this.generateAdvancement(consumer, "ender_of_world_ender", uncertainty, ItemRegistry.CHAOS_CROWN, AdvancementFrame.CHALLENGE, true, true, false,
                OnKilledCriterion.Conditions.createPlayerKilledEntity(EntityPredicate.Builder.create().type(EntityRegistry.CHAOS_MONARCH)));

        // Armor
        Advancement arkenplate = this.generateAdvancement(consumer, "arkenplate", returningKnight, ItemRegistry.ARKENPLATE,
                AdvancementFrame.TASK, true, true, false, ItemRegistry.ARKENPLATE);
        Advancement hallowheart = this.generateAdvancement(consumer, "hallowheart", decayingKing, ItemRegistry.WITHERED_CHEST,
                AdvancementFrame.TASK, true, true, false, ItemRegistry.WITHERED_CHEST);

        // Guns
        Advancement pistol = this.generateAdvancement(consumer, "hunter_pistol", root, GunRegistry.HUNTER_PISTOL,
                AdvancementFrame.GOAL, true, true, false, GunRegistry.HUNTER_PISTOL);
        Advancement blunderbuss = this.generateAdvancement(consumer, "blunderbuss", pistol, GunRegistry.BLUNDERBUSS,
                AdvancementFrame.GOAL, true, true, false, GunRegistry.BLUNDERBUSS);
        Advancement gatlingGun = this.generateAdvancement(consumer, "gatling_gun", blunderbuss, GunRegistry.GATLING_GUN,
                AdvancementFrame.GOAL, true, true, false, GunRegistry.GATLING_GUN);
        Advancement cannon = this.generateAdvancement(consumer, "cannon", gatlingGun, GunRegistry.HUNTER_CANNON,
                AdvancementFrame.GOAL, true, true, false, GunRegistry.HUNTER_CANNON);

        // Weapons
        Advancement bluemoonSwords = this.generateAdvancementAcceptEither(consumer, "bluemoon_tools", moonstone, WeaponRegistry.BLUEMOON_GREATSWORD,
                AdvancementFrame.TASK, true, true, false,
                WeaponRegistry.BLUEMOON_GREATSWORD, WeaponRegistry.BLUEMOON_SHORTSWORD);
        Advancement moonlightSwords = this.generateAdvancementAcceptEither(consumer, "moonlight_tools", bluemoonSwords, WeaponRegistry.MOONLIGHT_GREATSWORD,
                AdvancementFrame.GOAL, true, true, false,
                WeaponRegistry.MOONLIGHT_GREATSWORD, WeaponRegistry.MOONLIGHT_SHORTSWORD);
        Advancement transformScythe = this.generateAdvancementAcceptEither(consumer, "transform_scythe", lordSoul, WeaponRegistry.DARKIN_SCYTHE_PRE,
                AdvancementFrame.CHALLENGE, true, true, false,//TODO test
                WeaponRegistry.DARKIN_SCYTHE_PRIME, WeaponRegistry.SHADOW_ASSASSIN_SCYTHE);
        Advancement rageblade = this.generateAdvancement(consumer, "rageblade", lordSoul, WeaponRegistry.GUINSOOS_RAGEBLADE,
                AdvancementFrame.GOAL, true, true, false, WeaponRegistry.GUINSOOS_RAGEBLADE);
        Advancement nightfall = this.generateAdvancement(consumer, "nightfall", returningKnight, WeaponRegistry.NIGHTFALL,
                AdvancementFrame.GOAL, true, true, false, WeaponRegistry.NIGHTFALL);
        Advancement dragonslayerSwordspear = this.generateAdvancement(consumer, "dragonslayer_swordspear", lordSoul, WeaponRegistry.DRAGONSLAYER_SWORDSPEAR,
                AdvancementFrame.GOAL, true, true, false, WeaponRegistry.DRAGONSLAYER_SWORDSPEAR);
        Advancement leviathanAxe = this.generateAdvancement(consumer, "leviathan_axe", verglas, WeaponRegistry.LEVIATHAN_AXE,
                AdvancementFrame.GOAL, true, true, false, WeaponRegistry.LEVIATHAN_AXE);
        Advancement whirligigSawblade = this.generateAdvancement(consumer, "whirligig_sawblade", lordSoul, WeaponRegistry.WHIRLIGIG_SAWBLADE,
                AdvancementFrame.GOAL, true, true, false, WeaponRegistry.WHIRLIGIG_SAWBLADE);
        Advancement heapOfIron = this.generateAdvancement(consumer, "heap_of_iron", root, WeaponRegistry.GUTS_SWORD,
                AdvancementFrame.GOAL, true, true, false, WeaponRegistry.GUTS_SWORD);
        Advancement wands = this.generateAdvancement(consumer, "lords_on_stick", chaosMonarch, WeaponRegistry.DRAGON_STAFF,
                AdvancementFrame.GOAL, true, true, false, WeaponRegistry.DRAGON_STAFF, WeaponRegistry.WITHERED_WABBAJACK);
        Advancement trickWeapon = this.generateAdvancement(consumer, "trickweapon", pistol, WeaponRegistry.HOLY_GREATSWORD,
                AdvancementFrame.GOAL, true, true, false, ModTags.Items.TRICK_WEAPONS);//TODO test

        // Special
        Advancement chaosOrb = this.generateAdvancement(consumer, "chaos_orb", decayingKing, ItemRegistry.CHAOS_ORB,
                AdvancementFrame.GOAL, true, true, false, ItemRegistry.CHAOS_ORB);
        Advancement killDayNightBoss = this.generateAdvancement(consumer, "kill_day_night_boss", chaosOrb, ItemRegistry.LORD_SOUL_DAY_STALKER,
                AdvancementFrame.CHALLENGE, true, true, false, ModTags.Items.DUO_BOSS_SOULS);
        Advancement dreamOn = this.generateAdvancement(consumer, "dream_on", lordSoul, Items.GOLDEN_BOOTS,
                AdvancementFrame.CHALLENGE, true, true, false,
                TravelCriterion.Conditions.fallFromHeight(
                        EntityPredicate.Builder.create().effects(EntityEffectPredicate.create().withEffect(EffectRegistry.CALCULATED_FALL)),
                        DistancePredicate.y(NumberRange.FloatRange.atLeast(50.0f)),
                        LocationPredicate.y(NumberRange.FloatRange.ANY)
                ));
        Advancement allWeapons = this.generateAdvancement(consumer, "all_weapons", moonlightSwords, ItemRegistry.LORD_SOUL_PURPLE,
                AdvancementFrame.CHALLENGE, true, true, false, ALL_WEAPONS.toArray(Item[]::new));

        //TODO replace all advancements with datagen ones
        //TODO implement trick weapon changes, then make the new advancement that checks for items in the trickweapon tag instead. Make sure to delete the old advancement!
    }

    /**
     * Advancement for when acquiring an item in a specific tag
     */
    public Advancement generateAdvancement(Consumer<Advancement> consumer, String advancementId, Advancement root, Item display,
                                           AdvancementFrame frame, boolean showToast, boolean announceToChat, boolean hidden, TagKey<Item> tagCondition) {
        return this.generateAdvancement(consumer, advancementId, root, display, frame, showToast, announceToChat, hidden,
                InventoryChangedCriterion.Conditions.items(ItemPredicate.Builder.create().tag(tagCondition).build()));
    }

    /**
     * Advancement for when acquiring an item or multiple items (all items must be acquired if multiple are stated)
     */
    public Advancement generateAdvancement(Consumer<Advancement> consumer, String advancementId, Advancement root, Item display,
                                           AdvancementFrame frame, boolean showToast, boolean announceToChat, boolean hidden, Item... itemCondition) {
        Advancement.Builder builder = this.generateAdvancementBase(root, display, advancementId, frame, showToast, announceToChat, hidden);
        for (Item item : itemCondition) {
            builder.criterion(
                    item.toString(),
                    InventoryChangedCriterion.Conditions.items(item)
            );
        }
        return builder.build(consumer, SoulsWeaponry.ModId + ":" + advancementId);
    }

    /**
     * Helper method for building advancement based on conditions, such as {@code InventoryChangedCriterion.Conditions} or {@code OnKilledCriterion.Conditions}
     */
    public Advancement generateAdvancement(Consumer<Advancement> consumer, String advancementId, Advancement root, Item display,
                                           AdvancementFrame frame, boolean showToast, boolean announceToChat, boolean hidden, AbstractCriterionConditions... conditions) {
        Advancement.Builder builder = this.generateAdvancementBase(root, display, advancementId, frame, showToast, announceToChat, hidden);
        for (int i = 0; i < conditions.length; i++) {
            builder.criterion("condition_" + i, conditions[i]);
        }
        return builder.build(consumer, SoulsWeaponry.ModId + ":" + advancementId);
    }

    /**
     * Advancement for when acquiring either of multiple items
     */
    public Advancement generateAdvancementAcceptEither(Consumer<Advancement> consumer, String advancementId, Advancement root, Item display,
                                           AdvancementFrame frame, boolean showToast, boolean announceToChat, boolean hidden, Item... itemCondition) {
        Advancement.Builder builder = this.generateAdvancementBase(root, display, advancementId, frame, showToast, announceToChat, hidden);
        List<String> criteriaNames = new ArrayList<>();
        for (Item item : itemCondition) {
            String itemName = item.toString();
            builder.criterion(itemName, InventoryChangedCriterion.Conditions.items(item));
            criteriaNames.add(itemName);
        }
        // Single requirement to accept either of the items
        builder.requirements(new String[][]{criteriaNames.toArray(new String[0])});
        return builder.build(consumer, SoulsWeaponry.ModId + ":" + advancementId);
    }

    /**
     * Helper method to generate an advancement base for the mod. NB! You still need to add conditions yourself!
     */
    public Advancement.Builder generateAdvancementBase(Advancement root, Item display, String advancementId,
                                           AdvancementFrame frame, boolean showToast, boolean announceToChat, boolean hidden) {
        return Advancement.Builder.create()
                .parent(root)
                .display(
                        display,
                        Text.translatable("advancements.soulsweapons." + advancementId + ".title"),
                        Text.translatable("advancements.soulsweapons." + advancementId + ".description"),
                        null, // children to parent advancements don't need a background set
                        frame,
                        showToast,
                        announceToChat,
                        hidden
                );
    }
}
