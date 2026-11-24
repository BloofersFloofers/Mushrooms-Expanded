package net.bloof.mushexpanded.datagen;

import net.bloof.mushexpanded.MushExpanded;
import net.bloof.mushexpanded.block.ModBlocks;
import net.bloof.mushexpanded.item.ModItems;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.PackOutput;
import net.minecraft.data.recipes.*;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.crafting.AbstractCookingRecipe;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraft.world.item.crafting.SmeltingRecipe;
import net.minecraft.world.level.ItemLike;
import net.neoforged.neoforge.common.Tags;
import net.neoforged.neoforge.common.conditions.IConditionBuilder;

import java.util.concurrent.CompletableFuture;

public class ModRecipeProvider extends RecipeProvider implements IConditionBuilder {
    public ModRecipeProvider(PackOutput output, CompletableFuture<HolderLookup.Provider> registries) {
        super(output, registries);
    }

    @Override
    protected void buildRecipes(RecipeOutput recipeOutput) {
        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, ModItems.AGARPLATE.get(), 4)
                .pattern("AGG")
                .define('G', Tags.Items.GLASS_BLOCKS)
                .define('A', ModItems.AGAR_CUBE)
                .unlockedBy("has_glass", has(Tags.Items.GLASS_BLOCKS))
                .unlockedBy("has_agar", has(ModItems.AGAR_CUBE))
                .save(recipeOutput);

        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, ModItems.FILTER_CLOTH.get())
                        .define('S', Items.STRING)
                        .pattern("SSS")
                        .pattern("SSS")
                        .pattern("SSS")
                        .unlockedBy("has_string", has(Items.STRING)).save(recipeOutput);

        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, ModBlocks.AGAR_BLOCK.get())
                        .define('A', ModItems.AGAR_CUBE)
                        .pattern("AA")
                        .pattern("AA")
                        .unlockedBy("has_agar", has(ModItems.AGAR_CUBE)).save(recipeOutput);

        simpleCookingRecipe(recipeOutput, "blanch_algae", RecipeSerializer.SMELTING_RECIPE, SmeltingRecipe :: new, 200, ModItems.RED_ALGAE_PIECE, ModItems.BLANCHED_RED_ALGAE, 0.25f);


        ShapelessRecipeBuilder.shapeless(RecipeCategory.MISC, ModItems.AGAR_CUBE.get())
                .requires(ModItems.BLANCHED_RED_ALGAE)
                .requires(ModItems.FILTER_CLOTH)
                .unlockedBy("has_red_algae", has(ModItems.BLANCHED_RED_ALGAE))
                .unlockedBy("has_filter_cloth", has(ModItems.FILTER_CLOTH)).save(recipeOutput);


    }

    protected static <T extends AbstractCookingRecipe> void simpleCookingRecipe(RecipeOutput recipeOutput, String cookingMethod, RecipeSerializer<T> cookingSerializer, AbstractCookingRecipe.Factory<T> recipeFactory, int cookingTime, ItemLike material, ItemLike result, float experience) {
        SimpleCookingRecipeBuilder recipe = SimpleCookingRecipeBuilder.generic(Ingredient.of(new ItemLike[]{material}), RecipeCategory.FOOD, result, experience, cookingTime, cookingSerializer, recipeFactory).unlockedBy(getHasName(material), has(material));
        String var10002 = getItemName(result);
        recipe.save(recipeOutput, MushExpanded.MOD_ID + ":" + getItemName(result) + cookingMethod + "_" + getItemName(material));
    }
}
