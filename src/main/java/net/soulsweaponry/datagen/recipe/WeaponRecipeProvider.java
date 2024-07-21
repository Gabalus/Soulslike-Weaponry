package net.soulsweaponry.datagen.recipe;

import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricRecipeProvider;
import net.minecraft.data.server.recipe.CookingRecipeJsonBuilder;
import net.minecraft.data.server.recipe.RecipeJsonProvider;
import net.minecraft.data.server.recipe.SmithingTransformRecipeJsonBuilder;
import net.minecraft.item.Item;
import net.minecraft.item.Items;
import net.minecraft.predicate.item.ItemPredicate;
import net.minecraft.recipe.Ingredient;
import net.minecraft.recipe.book.RecipeCategory;
import net.soulsweaponry.registry.ItemRegistry;
import net.soulsweaponry.util.ModTags;

import java.util.function.Consumer;

public class WeaponRecipeProvider extends FabricRecipeProvider {

    public WeaponRecipeProvider(FabricDataOutput output) {
        super(output);
    }

    @Override
    public void generate(Consumer<RecipeJsonProvider> consumer) {
        GunRecipes.generateRecipes(consumer);
        WeaponRecipes.generateRecipes(consumer);
        ArmorRecipes.generateRecipes(consumer);
        CookingRecipeJsonBuilder.createSmelting(
                        Ingredient.fromTag(ModTags.Items.DEMON_HEARTS), RecipeCategory.MISC, ItemRegistry.MOLTEN_DEMON_HEART, 0.1f, 200)
                .criterion("has_demon_heart", conditionsFromItemPredicates(ItemPredicate.Builder.create()
                        .tag(ModTags.Items.DEMON_HEARTS).build()))
                .offerTo(consumer);
    }

    public static void smithingRecipe(Ingredient smithingTemplate, Ingredient base, Ingredient addition, Item output, RecipeCategory recipeCategory, Item itemCriterion, Consumer<RecipeJsonProvider> consumer) {
        SmithingTransformRecipeJsonBuilder.create(smithingTemplate, base, addition, recipeCategory, output)
                .criterion("has_item", conditionsFromItemPredicates(ItemPredicate.Builder.create()
                        .items(itemCriterion).build()))
                .offerTo(consumer, output.toString());
    }

    public static void smithingRecipeCombat(Ingredient smithingTemplate, Ingredient base, Ingredient addition, Item output, Item itemCriterion, Consumer<RecipeJsonProvider> consumer) {
        SmithingTransformRecipeJsonBuilder.create(smithingTemplate, base, addition, RecipeCategory.COMBAT, output)
                .criterion("has_item", conditionsFromItemPredicates(ItemPredicate.Builder.create()
                        .items(itemCriterion).build()))
                .offerTo(consumer, output.toString());
    }

    public static void smithingRecipeLordSoul(Ingredient base, Item output, RecipeCategory category, Consumer<RecipeJsonProvider> consumer) {
        SmithingTransformRecipeJsonBuilder.create(Ingredient.ofItems(Items.NETHERITE_UPGRADE_SMITHING_TEMPLATE), base, Ingredient.fromTag(ModTags.Items.LORD_SOUL), category, output)
                .criterion("has_lord_soul", conditionsFromItemPredicates(ItemPredicate.Builder.create()
                        .tag(ModTags.Items.LORD_SOUL).build()))
                .offerTo(consumer, output.toString());
    }

    public static void smithingRecipeLordSoulCombat(Ingredient base, Item output, Consumer<RecipeJsonProvider> consumer) {
        SmithingTransformRecipeJsonBuilder.create(Ingredient.ofItems(Items.NETHERITE_UPGRADE_SMITHING_TEMPLATE), base, Ingredient.fromTag(ModTags.Items.LORD_SOUL), RecipeCategory.COMBAT, output)
                .criterion("has_lord_soul", conditionsFromItemPredicates(ItemPredicate.Builder.create()
                        .tag(ModTags.Items.LORD_SOUL).build()))
                .offerTo(consumer, output.toString());
    }
}