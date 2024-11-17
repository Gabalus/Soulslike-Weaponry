package net.soulsweaponry.datagen.recipe;

import net.minecraft.data.server.recipe.RecipeJsonProvider;
import net.minecraft.data.server.recipe.ShapedRecipeJsonBuilder;
import net.minecraft.item.Items;
import net.minecraft.predicate.item.ItemPredicate;
import net.minecraft.recipe.Ingredient;
import net.minecraft.recipe.book.RecipeCategory;
import net.minecraft.util.Identifier;
import net.soulsweaponry.SoulsWeaponry;
import net.soulsweaponry.registry.ItemRegistry;
import net.soulsweaponry.registry.WeaponRegistry;
import net.soulsweaponry.util.ModTags;

import java.util.function.Consumer;

import static net.minecraft.data.server.recipe.RecipeProvider.conditionsFromItemPredicates;

public class WeaponRecipes {

    public static void generateRecipes(Consumer<RecipeJsonProvider> consumer) {
        // Regular weapons
        ShapedRecipeJsonBuilder.create(RecipeCategory.COMBAT, WeaponRegistry.TRANSLUCENT_SWORD)
                .input('#', ItemRegistry.LOST_SOUL)
                .input('X', ItemRegistry.SOUL_INGOT)
                .pattern(" # ")
                .pattern(" # ")
                .pattern(" X ")
                .criterion("has_item", conditionsFromItemPredicates(ItemPredicate.Builder.create()
                        .items(ItemRegistry.SOUL_INGOT).build()))
                .offerTo(consumer);
        ShapedRecipeJsonBuilder.create(RecipeCategory.COMBAT, WeaponRegistry.TRANSLUCENT_GLAIVE)
                .input('#', ItemRegistry.LOST_SOUL)
                .input('X', ItemRegistry.SOUL_INGOT)
                .pattern(" #")
                .pattern("##")
                .pattern("#X")
                .criterion("has_item", conditionsFromItemPredicates(ItemPredicate.Builder.create()
                        .items(ItemRegistry.SOUL_INGOT).build()))
                .offerTo(consumer);
        ShapedRecipeJsonBuilder.create(RecipeCategory.COMBAT, WeaponRegistry.TRANSLUCENT_DOUBLE_GREATSWORD)
                .input('#', ItemRegistry.LOST_SOUL)
                .input('X', ItemRegistry.SOUL_INGOT)
                .pattern(" ##")
                .pattern(" X ")
                .pattern("## ")
                .criterion("has_item", conditionsFromItemPredicates(ItemPredicate.Builder.create()
                        .items(ItemRegistry.SOUL_INGOT).build()))
                .offerTo(consumer);

        // Legendary weapons
        ShapedRecipeJsonBuilder.create(RecipeCategory.COMBAT, WeaponRegistry.BLUEMOON_GREATSWORD)
                .input('#', ItemRegistry.MOONSTONE)
                .input('X', ModTags.Items.STICKS)
                .pattern(" # ")
                .pattern("###")
                .pattern("#X#")
                .criterion("has_item", conditionsFromItemPredicates(ItemPredicate.Builder.create()
                        .items(ItemRegistry.MOONSTONE).build()))
                .offerTo(consumer);
        ShapedRecipeJsonBuilder.create(RecipeCategory.COMBAT, WeaponRegistry.BLUEMOON_SHORTSWORD)
                .input('#', ItemRegistry.MOONSTONE)
                .input('X', ModTags.Items.STICKS)
                .pattern(" # ")
                .pattern(" # ")
                .pattern(" X ")
                .criterion("has_item", conditionsFromItemPredicates(ItemPredicate.Builder.create()
                        .items(ItemRegistry.MOONSTONE).build()))
                .offerTo(consumer);
        ShapedRecipeJsonBuilder.create(RecipeCategory.COMBAT, WeaponRegistry.BLOODTHIRSTER)
                .input('i', Items.IRON_INGOT)
                .input('S', ItemRegistry.CRIMSON_INGOT)
                .input('/', ModTags.Items.STICKS)
                .pattern("iSi")
                .pattern("iSi")
                .pattern(" / ")
                .criterion("has_item", conditionsFromItemPredicates(ItemPredicate.Builder.create()
                        .items(ItemRegistry.CRIMSON_INGOT).build()))
                .offerTo(consumer);
        ShapedRecipeJsonBuilder.create(RecipeCategory.COMBAT, WeaponRegistry.COMET_SPEAR)
                .input('#', Items.GOLD_INGOT)
                .input('G', ModTags.Items.LORD_SOUL)
                .input('S', ItemRegistry.MOONSTONE)
                .pattern(" ##")
                .pattern("SG#")
                .pattern("#S ")
                .criterion("has_item", conditionsFromItemPredicates(ItemPredicate.Builder.create()
                        .tag(ModTags.Items.LORD_SOUL).build()))
                .offerTo(consumer);
        ShapedRecipeJsonBuilder.create(RecipeCategory.COMBAT, WeaponRegistry.DRAGON_STAFF)
                .input('#', Items.BLAZE_ROD)
                .input('X', Items.DRAGON_HEAD)
                .pattern("X")
                .pattern("#")
                .pattern("#")
                .criterion("has_item", conditionsFromItemPredicates(ItemPredicate.Builder.create()
                        .items(Items.BLAZE_ROD).build()))
                .offerTo(consumer);
        ShapedRecipeJsonBuilder.create(RecipeCategory.COMBAT, WeaponRegistry.GUTS_SWORD)
                .input('#', Items.IRON_BLOCK)
                .input('X', ModTags.Items.STICKS)
                .pattern(" # ")
                .pattern("###")
                .pattern("#X#")
                .criterion("has_item", conditionsFromItemPredicates(ItemPredicate.Builder.create()
                        .items(Items.IRON_BLOCK).build()))
                .offerTo(consumer);
        ShapedRecipeJsonBuilder.create(RecipeCategory.COMBAT, WeaponRegistry.DRAGONSLAYER_SWORDSPEAR)
                .input('S', ModTags.Items.LORD_SOUL)
                .input('G', Items.GOLD_INGOT)
                .input('/', ModTags.Items.STICKS)
                .pattern(" G ")
                .pattern("GSG")
                .pattern("G/G")
                .criterion("has_item", conditionsFromItemPredicates(ItemPredicate.Builder.create()
                        .tag(ModTags.Items.LORD_SOUL).build()))
                .offerTo(consumer);
        ShapedRecipeJsonBuilder.create(RecipeCategory.COMBAT, WeaponRegistry.GALEFORCE)
                .input('#', ItemRegistry.MOONSTONE)
                .input('S', Items.STRING)
                .input('G', ModTags.Items.LORD_SOUL)
                .pattern(" #S")
                .pattern("#GS")
                .pattern(" #S")
                .criterion("has_item", conditionsFromItemPredicates(ItemPredicate.Builder.create()
                        .tag(ModTags.Items.LORD_SOUL).build()))
                .offerTo(consumer);
        ShapedRecipeJsonBuilder.create(RecipeCategory.COMBAT, WeaponRegistry.LICH_BANE)
                .input('S', ModTags.Items.LORD_SOUL)
                .input('g', Items.DIAMOND)
                .input('/', Items.BLAZE_ROD)
                .input('F', Items.COPPER_INGOT)
                .pattern("  /")
                .pattern("gS ")
                .pattern("Fg ")
                .criterion("has_item", conditionsFromItemPredicates(ItemPredicate.Builder.create()
                        .tag(ModTags.Items.LORD_SOUL).build()))
                .offerTo(consumer);
        ShapedRecipeJsonBuilder.create(RecipeCategory.COMBAT, WeaponRegistry.NIGHTFALL)
                .input('S', ModTags.Items.LORD_SOUL)
                .input('X', ItemRegistry.LOST_SOUL)
                .input('Y', Items.IRON_BLOCK)
                .input('#', ItemRegistry.SOUL_INGOT)
                .pattern("YYY")
                .pattern("XSX")
                .pattern(" # ")
                .criterion("has_item", conditionsFromItemPredicates(ItemPredicate.Builder.create()
                        .tag(ModTags.Items.LORD_SOUL).build()))
                .offerTo(consumer);
        ShapedRecipeJsonBuilder.create(RecipeCategory.COMBAT, WeaponRegistry.GUINSOOS_RAGEBLADE)
                .input('/', Items.BLAZE_ROD)
                .input('g', Items.GOLD_INGOT)
                .input('S', ModTags.Items.LORD_SOUL)
                .pattern(" /g")
                .pattern("/S ")
                .pattern("/  ")
                .criterion("has_item", conditionsFromItemPredicates(ItemPredicate.Builder.create()
                        .tag(ModTags.Items.LORD_SOUL).build()))
                .offerTo(consumer);
        ShapedRecipeJsonBuilder.create(RecipeCategory.COMBAT, WeaponRegistry.SOUL_REAPER)
                .input('i', ItemRegistry.SOUL_INGOT)
                .input('/', ModTags.Items.STICKS)
                .input('S', ModTags.Items.LORD_SOUL)
                .pattern(" ii")
                .pattern("S/ ")
                .pattern(" / ")
                .criterion("has_item", conditionsFromItemPredicates(ItemPredicate.Builder.create()
                        .tag(ModTags.Items.LORD_SOUL).build()))
                .offerTo(consumer);
        ShapedRecipeJsonBuilder.create(RecipeCategory.COMBAT, WeaponRegistry.FORLORN_SCYTHE)
                .input('i', Items.NETHERITE_SCRAP)
                .input('/', ModTags.Items.STICKS)
                .input('S', ModTags.Items.LORD_SOUL)
                .pattern(" ii")
                .pattern("S/ ")
                .pattern(" / ")
                .criterion("has_item", conditionsFromItemPredicates(ItemPredicate.Builder.create()
                        .tag(ModTags.Items.LORD_SOUL).build()))
                .offerTo(consumer);
        ShapedRecipeJsonBuilder.create(RecipeCategory.COMBAT, WeaponRegistry.WHIRLIGIG_SAWBLADE)
                .input('i', Items.IRON_INGOT)
                .input('/', ModTags.Items.STICKS)
                .input('S', ModTags.Items.LORD_SOUL)
                .pattern(" i ")
                .pattern("iSi")
                .pattern("/i ")
                .criterion("has_item", conditionsFromItemPredicates(ItemPredicate.Builder.create()
                        .tag(ModTags.Items.LORD_SOUL).build()))
                .offerTo(consumer);
        ShapedRecipeJsonBuilder.create(RecipeCategory.COMBAT, WeaponRegistry.WITHERED_WABBAJACK)
                .input('#', ItemRegistry.CRIMSON_INGOT)
                .input('X', Items.WITHER_SKELETON_SKULL)
                .pattern("X")
                .pattern("#")
                .pattern("#")
                .criterion("has_item", conditionsFromItemPredicates(ItemPredicate.Builder.create()
                        .items(Items.WITHER_SKELETON_SKULL).build()))
                .offerTo(consumer);
        ShapedRecipeJsonBuilder.create(RecipeCategory.COMBAT, WeaponRegistry.LEVIATHAN_AXE)
                .input('#', ItemRegistry.VERGLAS)
                .input('/', ModTags.Items.STICKS)
                .input('S', ModTags.Items.LORD_SOUL)
                .pattern("#S")
                .pattern("#/")
                .pattern(" /")
                .criterion("has_item", conditionsFromItemPredicates(ItemPredicate.Builder.create()
                        .tag(ModTags.Items.LORD_SOUL).build()))
                .offerTo(consumer, new Identifier(SoulsWeaponry.ModId, "leviathan_axe_left"));

        ShapedRecipeJsonBuilder.create(RecipeCategory.COMBAT, WeaponRegistry.LEVIATHAN_AXE)
                .input('#', ItemRegistry.VERGLAS)
                .input('/', ModTags.Items.STICKS)
                .input('S', ModTags.Items.LORD_SOUL)
                .pattern("S#")
                .pattern("/#")
                .pattern("/ ")
                .criterion("has_item", conditionsFromItemPredicates(ItemPredicate.Builder.create()
                        .tag(ModTags.Items.LORD_SOUL).build()))
                .offerTo(consumer, new Identifier(SoulsWeaponry.ModId, "leviathan_axe_right"));
        ShapedRecipeJsonBuilder.create(RecipeCategory.COMBAT, WeaponRegistry.MJOLNIR)
                .input('#', ModTags.Items.STICKS)
                .input('X', Items.IRON_BLOCK)
                .input('O', ItemRegistry.VERGLAS)
                .input('Y', ModTags.Items.LORD_SOUL)
                .pattern("XOX")
                .pattern("XYX")
                .pattern(" # ")
                .criterion("has_item", conditionsFromItemPredicates(ItemPredicate.Builder.create()
                        .tag(ModTags.Items.LORD_SOUL).build()))
                .offerTo(consumer);
        ShapedRecipeJsonBuilder.create(RecipeCategory.COMBAT, WeaponRegistry.FREYR_SWORD)
                .input('#', Items.IRON_INGOT)
                .input('Y', ItemRegistry.MOONSTONE)
                .input('X', ItemRegistry.VERGLAS)
                .input('O', ModTags.Items.LORD_SOUL)
                .pattern(" X ")
                .pattern(" Y ")
                .pattern("#O#")
                .criterion("has_item", conditionsFromItemPredicates(ItemPredicate.Builder.create()
                        .tag(ModTags.Items.LORD_SOUL).build()))
                .offerTo(consumer);
        ShapedRecipeJsonBuilder.create(RecipeCategory.COMBAT, WeaponRegistry.STING)
                .input('X', ModTags.Items.STICKS)
                .input('#', ItemRegistry.VERGLAS)
                .pattern("#")
                .pattern("#")
                .pattern("X")
                .criterion("has_item", conditionsFromItemPredicates(ItemPredicate.Builder.create()
                        .items(ItemRegistry.VERGLAS).build()))
                .offerTo(consumer);
        ShapedRecipeJsonBuilder.create(RecipeCategory.COMBAT, WeaponRegistry.FEATHERLIGHT)
                .input('X', Items.DIAMOND_PICKAXE)
                .input('#', Items.OBSIDIAN)
                .input('O', Items.AMETHYST_SHARD)
                .pattern("#O#")
                .pattern("#O#")
                .pattern(" X ")
                .criterion("has_item", conditionsFromItemPredicates(ItemPredicate.Builder.create()
                        .items(Items.AMETHYST_SHARD).build()))
                .offerTo(consumer);
        ShapedRecipeJsonBuilder.create(RecipeCategory.COMBAT, WeaponRegistry.DARKIN_SCYTHE_PRE)
                .input('X', ModTags.Items.STICKS)
                .input('#', ItemRegistry.CRIMSON_INGOT)
                .input('O', ModTags.Items.LORD_SOUL)
                .pattern(" ##")
                .pattern("OX ")
                .pattern(" X ")
                .criterion("has_item", conditionsFromItemPredicates(ItemPredicate.Builder.create()
                        .tag(ModTags.Items.LORD_SOUL).build()))
                .offerTo(consumer);
        ShapedRecipeJsonBuilder.create(RecipeCategory.COMBAT, WeaponRegistry.KIRKHAMMER)
                .input('X', Items.IRON_SWORD)
                .input('#', Items.STONE)
                .pattern("###")
                .pattern("###")
                .pattern(" X ")
                .criterion("has_item", conditionsFromItemPredicates(ItemPredicate.Builder.create()
                        .items(Items.IRON_SWORD).build()))
                .offerTo(consumer);
        ShapedRecipeJsonBuilder.create(RecipeCategory.COMBAT, WeaponRegistry.HOLY_GREATSWORD)
                .input('X', Items.IRON_SWORD)
                .input('#', Items.IRON_INGOT)
                .pattern(" # ")
                .pattern("###")
                .pattern("#X#")
                .criterion("has_item", conditionsFromItemPredicates(ItemPredicate.Builder.create()
                        .items(Items.IRON_INGOT).build()))
                .offerTo(consumer);
        ShapedRecipeJsonBuilder.create(RecipeCategory.COMBAT, WeaponRegistry.DRAUPNIR_SPEAR)
                .input('X', Items.BLAZE_ROD)
                .input('#', Items.NETHERITE_INGOT)
                .input('Y', ModTags.Items.LORD_SOUL)
                .pattern("#")
                .pattern("Y")
                .pattern("X")
                .criterion("has_item", conditionsFromItemPredicates(ItemPredicate.Builder.create()
                        .tag(ModTags.Items.LORD_SOUL).build()))
                .offerTo(consumer);
        ShapedRecipeJsonBuilder.create(RecipeCategory.COMBAT, WeaponRegistry.FROSTMOURNE)
                .input('#', ItemRegistry.SOUL_INGOT)
                .input('X', ItemRegistry.VERGLAS)
                .input('D', ItemRegistry.LORD_SOUL_DAY_STALKER)
                .input('N', ItemRegistry.LORD_SOUL_NIGHT_PROWLER)
                .pattern(" #X")
                .pattern("DX#")
                .pattern("#N ")
                .criterion("has_item", conditionsFromItemPredicates(ItemPredicate.Builder.create()
                        .tag(ModTags.Items.LORD_SOUL).build()))
                .offerTo(consumer);
        ShapedRecipeJsonBuilder.create(RecipeCategory.COMBAT, WeaponRegistry.NIGHTS_EDGE_ITEM)
                .input('/', ModTags.Items.STICKS)
                .input('X', ItemRegistry.MOONSTONE)
                .input('Y', ItemRegistry.LORD_SOUL_NIGHT_PROWLER)
                .input('#', ItemRegistry.SOUL_INGOT)
                .pattern(" XX")
                .pattern("#YX")
                .pattern("/# ")
                .criterion("has_item", conditionsFromItemPredicates(ItemPredicate.Builder.create()
                        .items(ItemRegistry.LORD_SOUL_NIGHT_PROWLER).build()))
                .offerTo(consumer);
        ShapedRecipeJsonBuilder.create(RecipeCategory.COMBAT, WeaponRegistry.KRAKEN_SLAYER)
                .input('#', Items.IRON_INGOT)
                .input('X', Items.GOLD_BLOCK)
                .input('Y', ModTags.Items.LORD_SOUL)
                .input('A', Items.BOW)
                .pattern("A# ")
                .pattern("#Y#")
                .pattern(" #X")
                .criterion("has_item", conditionsFromItemPredicates(ItemPredicate.Builder.create()
                        .tag(ModTags.Items.LORD_SOUL).build()))
                .offerTo(consumer);
        ShapedRecipeJsonBuilder.create(RecipeCategory.COMBAT, WeaponRegistry.KRAKEN_SLAYER_CROSSBOW)
                .input('#', Items.IRON_INGOT)
                .input('X', Items.GOLD_BLOCK)
                .input('Y', ModTags.Items.LORD_SOUL)
                .input('A', Items.CROSSBOW)
                .pattern("A# ")
                .pattern("#Y#")
                .pattern(" #X")
                .criterion("has_item", conditionsFromItemPredicates(ItemPredicate.Builder.create()
                        .tag(ModTags.Items.LORD_SOUL).build()))
                .offerTo(consumer);
        ShapedRecipeJsonBuilder.create(RecipeCategory.COMBAT, WeaponRegistry.DARKMOON_LONGBOW)
                .input('#', Items.GOLD_INGOT)
                .input('Y', ItemRegistry.ESSENCE_OF_EVENTIDE)
                .input('A', Items.STRING)
                .input('X', ModTags.Items.STICKS)
                .pattern(" #A")
                .pattern("XYA")
                .pattern(" #A")
                .criterion("has_item", conditionsFromItemPredicates(ItemPredicate.Builder.create()
                        .items(ItemRegistry.ESSENCE_OF_EVENTIDE).build()))
                .offerTo(consumer);
        ShapedRecipeJsonBuilder.create(RecipeCategory.COMBAT, WeaponRegistry.CHUNGUS_STAFF)
                .input('#', Items.BLAZE_ROD)
                .input('E', ItemRegistry.CHUNGUS_EMERALD)
                .input('0', Items.EGG)
                .pattern(" E0")
                .pattern(" #E")
                .pattern("#  ")
                .criterion("has_item", conditionsFromItemPredicates(ItemPredicate.Builder.create()
                        .items(ItemRegistry.ESSENCE_OF_EVENTIDE).build()))
                .offerTo(consumer);
        ShapedRecipeJsonBuilder.create(RecipeCategory.COMBAT, WeaponRegistry.DARK_MOON_GREATSWORD)
                .input('/', ModTags.Items.STICKS)
                .input('#', ItemRegistry.VERGLAS)
                .input('0', ItemRegistry.MOONSTONE)
                .input('L', ModTags.Items.LORD_SOUL)
                .pattern(" #0")
                .pattern("#L#")
                .pattern("/# ")
                .criterion("has_item", conditionsFromItemPredicates(ItemPredicate.Builder.create()
                        .tag(ModTags.Items.LORD_SOUL).build()))
                .offerTo(consumer);
        ShapedRecipeJsonBuilder.create(RecipeCategory.COMBAT, WeaponRegistry.GLAIVE_OF_HODIR)
                .input('#', Items.GOLD_INGOT)
                .input('S', ItemRegistry.SOUL_INGOT)
                .input('X', ModTags.Items.LORD_SOUL)
                .pattern(" #")
                .pattern("S#")
                .pattern("SX")
                .criterion("has_item", conditionsFromItemPredicates(ItemPredicate.Builder.create()
                        .tag(ModTags.Items.LORD_SOUL).build()))
                .offerTo(consumer);

        WeaponRecipeProvider.smithingRecipeLordSoulCombat(Ingredient.ofItems(WeaponRegistry.BLOODTHIRSTER), WeaponRegistry.DARKIN_BLADE, consumer);
        WeaponRecipeProvider.smithingRecipeLordSoulCombat(Ingredient.ofItems(Items.GOLDEN_SWORD), WeaponRegistry.DAWNBREAKER, consumer);
        WeaponRecipeProvider.smithingRecipeLordSoulCombat(Ingredient.ofItems(WeaponRegistry.BLUEMOON_GREATSWORD), WeaponRegistry.MOONLIGHT_GREATSWORD, consumer);
        WeaponRecipeProvider.smithingRecipeLordSoulCombat(Ingredient.ofItems(WeaponRegistry.BLUEMOON_SHORTSWORD), WeaponRegistry.MOONLIGHT_SHORTSWORD, consumer);
        WeaponRecipeProvider.smithingRecipeLordSoulCombat(Ingredient.ofItems(Items.IRON_SWORD), WeaponRegistry.SKOFNUNG, consumer);
        WeaponRecipeProvider.smithingRecipeCombat(Ingredient.ofItems(Items.NETHERITE_UPGRADE_SMITHING_TEMPLATE), Ingredient.fromTag(ModTags.Items.MOONLIGHT_SWORD), Ingredient.ofItems(ItemRegistry.ESSENCE_OF_LUMINESCENCE), WeaponRegistry.PURE_MOONLIGHT_GREATSWORD, ItemRegistry.ESSENCE_OF_LUMINESCENCE, consumer);
        WeaponRecipeProvider.smithingRecipeCombat(Ingredient.ofItems(Items.NETHERITE_UPGRADE_SMITHING_TEMPLATE), Ingredient.ofItems(Items.IRON_SWORD), Ingredient.ofItems(ItemRegistry.ESSENCE_OF_EVENTIDE), WeaponRegistry.DRAUGR, ItemRegistry.ESSENCE_OF_EVENTIDE, consumer);
        WeaponRecipeProvider.smithingRecipeLordSoulCombat(Ingredient.ofItems(Items.NETHERITE_SWORD), WeaponRegistry.CRUCIBLE_SWORD, consumer);
        WeaponRecipeProvider.smithingRecipeLordSoulCombat(Ingredient.ofItems(WeaponRegistry.STING), WeaponRegistry.HOLY_MOONLIGHT_SWORD, consumer);
        WeaponRecipeProvider.smithingRecipeLordSoulCombat(Ingredient.ofItems(Items.DIAMOND_SWORD), WeaponRegistry.MASTER_SWORD, consumer);
        WeaponRecipeProvider.smithingRecipeCombat(Ingredient.ofItems(Items.NETHERITE_UPGRADE_SMITHING_TEMPLATE), Ingredient.ofItems(WeaponRegistry.DAWNBREAKER), Ingredient.ofItems(ItemRegistry.LORD_SOUL_DAY_STALKER), WeaponRegistry.EMPOWERED_DAWNBREAKER, ItemRegistry.LORD_SOUL_DAY_STALKER, consumer);
    }
}