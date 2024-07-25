package net.soulsweaponry.datagen.recipe;

import net.minecraft.data.server.RecipeProvider;
import net.minecraft.data.server.recipe.RecipeJsonProvider;
import net.minecraft.data.server.recipe.ShapedRecipeJsonBuilder;
import net.minecraft.item.Items;
import net.minecraft.predicate.item.ItemPredicate;
import net.minecraft.recipe.Ingredient;
import net.soulsweaponry.registry.ArmorRegistry;
import net.soulsweaponry.registry.ItemRegistry;

import java.util.function.Consumer;

public class ArmorRecipes {

    public static void generateRecipes(Consumer<RecipeJsonProvider> consumer) {
        ShapedRecipeJsonBuilder.create(ArmorRegistry.SOUL_INGOT_HELMET)
                .input('#', ItemRegistry.SOUL_INGOT)
                .pattern("###")
                .pattern("# #")
                .criterion("has_item", RecipeProvider.conditionsFromItemPredicates(ItemPredicate.Builder.create()
                        .items(ItemRegistry.SOUL_INGOT).build()))
                .offerTo(consumer);
        ShapedRecipeJsonBuilder.create(ArmorRegistry.SOUL_INGOT_CHESTPLATE)
                .input('#', ItemRegistry.SOUL_INGOT)
                .pattern("# #")
                .pattern("###")
                .pattern("###")
                .criterion("has_item", RecipeProvider.conditionsFromItemPredicates(ItemPredicate.Builder.create()
                        .items(ItemRegistry.SOUL_INGOT).build()))
                .offerTo(consumer);
        ShapedRecipeJsonBuilder.create(ArmorRegistry.SOUL_INGOT_LEGGINGS)
                .input('#', ItemRegistry.SOUL_INGOT)
                .pattern("###")
                .pattern("# #")
                .pattern("# #")
                .criterion("has_item", RecipeProvider.conditionsFromItemPredicates(ItemPredicate.Builder.create()
                        .items(ItemRegistry.SOUL_INGOT).build()))
                .offerTo(consumer);
        ShapedRecipeJsonBuilder.create(ArmorRegistry.SOUL_INGOT_BOOTS)
                .input('#', ItemRegistry.SOUL_INGOT)
                .pattern("# #")
                .pattern("# #")
                .criterion("has_item", RecipeProvider.conditionsFromItemPredicates(ItemPredicate.Builder.create()
                        .items(ItemRegistry.SOUL_INGOT).build()))
                .offerTo(consumer);
        ShapedRecipeJsonBuilder.create(ArmorRegistry.SOUL_ROBES_HELMET)
                .input('#', ItemRegistry.SOUL_INGOT)
                .input('L', Items.LEATHER)
                .pattern("#L#")
                .pattern("L L")
                .criterion("has_item", RecipeProvider.conditionsFromItemPredicates(ItemPredicate.Builder.create()
                        .items(ItemRegistry.SOUL_INGOT).build()))
                .offerTo(consumer);
        ShapedRecipeJsonBuilder.create(ArmorRegistry.SOUL_ROBES_CHESTPLATE)
                .input('#', ItemRegistry.SOUL_INGOT)
                .input('L', Items.LEATHER)
                .pattern("# #")
                .pattern("L#L")
                .pattern("LLL")
                .criterion("has_item", RecipeProvider.conditionsFromItemPredicates(ItemPredicate.Builder.create()
                        .items(ItemRegistry.SOUL_INGOT).build()))
                .offerTo(consumer);
        ShapedRecipeJsonBuilder.create(ArmorRegistry.SOUL_ROBES_LEGGINGS)
                .input('#', ItemRegistry.SOUL_INGOT)
                .input('L', Items.LEATHER)
                .pattern("#L#")
                .pattern("L L")
                .pattern("L L")
                .criterion("has_item", RecipeProvider.conditionsFromItemPredicates(ItemPredicate.Builder.create()
                        .items(ItemRegistry.SOUL_INGOT).build()))
                .offerTo(consumer);
        ShapedRecipeJsonBuilder.create(ArmorRegistry.SOUL_ROBES_BOOTS)
                .input('#', ItemRegistry.SOUL_INGOT)
                .input('L', Items.LEATHER)
                .pattern("# #")
                .pattern("L L")
                .criterion("has_item", RecipeProvider.conditionsFromItemPredicates(ItemPredicate.Builder.create()
                        .items(ItemRegistry.SOUL_INGOT).build()))
                .offerTo(consumer);
        ShapedRecipeJsonBuilder.create(ArmorRegistry.FORLORN_HELMET)
                .input('#', ItemRegistry.CRIMSON_INGOT)
                .input('L', ItemRegistry.SOUL_INGOT)
                .pattern("#L#")
                .pattern("L L")
                .criterion("has_item", RecipeProvider.conditionsFromItemPredicates(ItemPredicate.Builder.create()
                        .items(ItemRegistry.CRIMSON_INGOT).build()))
                .offerTo(consumer);
        ShapedRecipeJsonBuilder.create(ArmorRegistry.FORLORN_CHESTPLATE)
                .input('#', ItemRegistry.CRIMSON_INGOT)
                .input('L', ItemRegistry.SOUL_INGOT)
                .pattern("# #")
                .pattern("L#L")
                .pattern("LLL")
                .criterion("has_item", RecipeProvider.conditionsFromItemPredicates(ItemPredicate.Builder.create()
                        .items(ItemRegistry.CRIMSON_INGOT).build()))
                .offerTo(consumer);
        ShapedRecipeJsonBuilder.create(ArmorRegistry.FORLORN_LEGGINGS)
                .input('#', ItemRegistry.CRIMSON_INGOT)
                .input('L', ItemRegistry.SOUL_INGOT)
                .pattern("#L#")
                .pattern("L L")
                .pattern("L L")
                .criterion("has_item", RecipeProvider.conditionsFromItemPredicates(ItemPredicate.Builder.create()
                        .items(ItemRegistry.CRIMSON_INGOT).build()))
                .offerTo(consumer);
        ShapedRecipeJsonBuilder.create(ArmorRegistry.FORLORN_BOOTS)
                .input('#', ItemRegistry.CRIMSON_INGOT)
                .input('L', ItemRegistry.SOUL_INGOT)
                .pattern("# #")
                .pattern("L L")
                .criterion("has_item", RecipeProvider.conditionsFromItemPredicates(ItemPredicate.Builder.create()
                        .items(ItemRegistry.CRIMSON_INGOT).build()))
                .offerTo(consumer);
        ShapedRecipeJsonBuilder.create(ItemRegistry.ARKENPLATE)
                .input('#', Items.IRON_INGOT)
                .input('n', Items.NETHERITE_INGOT)
                .input('N', ItemRegistry.ARKENSTONE)
                .input('D', ItemRegistry.SOUL_INGOT)
                .pattern("# #")
                .pattern("nNn")
                .pattern("D#D")
                .criterion("has_item", RecipeProvider.conditionsFromItemPredicates(ItemPredicate.Builder.create()
                        .items(ItemRegistry.ARKENSTONE).build()))
                .offerTo(consumer);
        ShapedRecipeJsonBuilder.create(ItemRegistry.WITHERED_CHEST)
                .input('#', Items.NETHER_BRICK)
                .input('n', Items.NETHERITE_INGOT)
                .input('N', ItemRegistry.WITHERED_DEMON_HEART)
                .input('D', ItemRegistry.CRIMSON_INGOT)
                .pattern("# #")
                .pattern("nNn")
                .pattern("D#D")
                .criterion("has_item", RecipeProvider.conditionsFromItemPredicates(ItemPredicate.Builder.create()
                        .items(ItemRegistry.WITHERED_DEMON_HEART).build()))
                .offerTo(consumer);
        WeaponRecipeProvider.smithingRecipe(Ingredient.ofItems(ItemRegistry.ARKENPLATE), Ingredient.ofItems(ItemRegistry.LORD_SOUL_NIGHT_PROWLER), ItemRegistry.ENHANCED_ARKENPLATE, ItemRegistry.LORD_SOUL_NIGHT_PROWLER, consumer);
        WeaponRecipeProvider.smithingRecipe(Ingredient.ofItems(Items.NETHERITE_HELMET), Ingredient.ofItems(ItemRegistry.CHAOS_CROWN), ItemRegistry.CHAOS_HELMET, ItemRegistry.CHAOS_CROWN, consumer);
        WeaponRecipeProvider.smithingRecipe(Ingredient.ofItems(ItemRegistry.WITHERED_CHEST), Ingredient.ofItems(ItemRegistry.LORD_SOUL_DAY_STALKER), ItemRegistry.ENHANCED_WITHERED_CHEST, ItemRegistry.LORD_SOUL_DAY_STALKER, consumer);
    }
}