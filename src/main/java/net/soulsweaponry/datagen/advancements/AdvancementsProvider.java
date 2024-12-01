package net.soulsweaponry.datagen.advancements;

import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricAdvancementProvider;
import net.minecraft.advancement.Advancement;
import net.minecraft.advancement.AdvancementFrame;
import net.minecraft.advancement.criterion.InventoryChangedCriterion;
import net.minecraft.item.Item;
import net.minecraft.item.Items;
import net.minecraft.predicate.item.ItemPredicate;
import net.minecraft.registry.tag.TagKey;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;
import net.soulsweaponry.SoulsWeaponry;
import net.soulsweaponry.registry.ItemRegistry;
import net.soulsweaponry.registry.WeaponRegistry;
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

        Advancement lordSoul = this.generateAdvancement(consumer, "lord_soul", root, ItemRegistry.LORD_SOUL_ROSE,
                AdvancementFrame.CHALLENGE, true, true, false, ModTags.Items.LORD_SOUL);
        Advancement moonstone = this.generateAdvancement(consumer, "moonstone", root, ItemRegistry.MOONSTONE,
                AdvancementFrame.TASK, true, true, false, ItemRegistry.MOONSTONE);
        Advancement bluemoon_swords = this.generateAdvancementAcceptEither(consumer, "bluemoon_tools", moonstone, WeaponRegistry.BLUEMOON_GREATSWORD,
                AdvancementFrame.TASK, true, true, false,
                WeaponRegistry.BLUEMOON_GREATSWORD, WeaponRegistry.BLUEMOON_SHORTSWORD);
        Advancement moonlight_swords = this.generateAdvancementAcceptEither(consumer, "moonlight_tools", bluemoon_swords, WeaponRegistry.MOONLIGHT_GREATSWORD,
                AdvancementFrame.GOAL, true, true, false,
                WeaponRegistry.MOONLIGHT_GREATSWORD, WeaponRegistry.MOONLIGHT_SHORTSWORD);
        Advancement all_weapons = this.generateAdvancement(consumer, "all_weapons", moonlight_swords, ItemRegistry.LORD_SOUL_PURPLE,
                AdvancementFrame.CHALLENGE, true, true, false, ALL_WEAPONS.toArray(Item[]::new));

        //TODO replace all advancements with datagen ones
        //TODO delete the non generated ones
    }

    public Advancement generateAdvancement(Consumer<Advancement> consumer, String advancementId, Advancement root, Item display,
                                           AdvancementFrame frame, boolean showToast, boolean announceToChat, boolean hidden, TagKey<Item> tagCondition) {
        return this.generateAdvancement(consumer, advancementId, root, display, frame, showToast, announceToChat, hidden,
                InventoryChangedCriterion.Conditions.items(ItemPredicate.Builder.create().tag(tagCondition).build()));
    }

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

    public Advancement generateAdvancement(Consumer<Advancement> consumer, String advancementId, Advancement root, Item display,
                                           AdvancementFrame frame, boolean showToast, boolean announceToChat, boolean hidden, InventoryChangedCriterion.Conditions... conditions) {
        Advancement.Builder builder = this.generateAdvancementBase(root, display, advancementId, frame, showToast, announceToChat, hidden);
        for (int i = 0; i < conditions.length; i++) {
            builder.criterion("condition_" + i, conditions[i]);
        }
        return builder.build(consumer, SoulsWeaponry.ModId + ":" + advancementId);
    }

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
