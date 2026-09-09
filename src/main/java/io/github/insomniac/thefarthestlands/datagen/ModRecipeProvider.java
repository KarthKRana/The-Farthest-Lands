package io.github.insomniac.thefarthestlands.datagen;

import io.github.insomniac.thefarthestlands.item.ModItems;
import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricRecipeProvider;
import net.minecraft.data.recipes.RecipeCategory;
import net.minecraft.data.recipes.RecipeOutput;
import net.minecraft.data.recipes.ShapedRecipeBuilder;
import net.minecraft.world.item.Items;
import java.util.concurrent.CompletableFuture;

public class ModRecipeProvider extends FabricRecipeProvider {
    public ModRecipeProvider(FabricDataOutput output) {
        super(output, CompletableFuture.completedFuture(null));
    }

    @Override
    public void buildRecipes(RecipeOutput exporter) {
        // Gazing Eye is crafted from 4x soul soil, 1x wither rose, 3x ghast tear, and 1x eyes of ender
        // Unlocks when player has either a Wither Rose or an Eye of Ender
        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, ModItems.GAZING_EYE, 1)
                .pattern("SWS")
                .pattern("GEG")
                .pattern("SGS")
                .define('S', Items.SOUL_SOIL)
                .define('W', Items.WITHER_ROSE)
                .define('G', Items.GHAST_TEAR)
                .define('E', Items.ENDER_EYE)
                .unlockedBy("has_ender_eye", has(Items.ENDER_EYE))
                .unlockedBy("has_wither_rose", has(Items.WITHER_ROSE))
                .save(exporter);
    }
}

