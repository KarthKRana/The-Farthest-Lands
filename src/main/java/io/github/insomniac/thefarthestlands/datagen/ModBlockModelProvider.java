package io.github.insomniac.thefarthestlands.datagen;

import io.github.insomniac.thefarthestlands.block.ModBlocks;
import io.github.insomniac.thefarthestlands.item.ModItems;
import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricModelProvider;
import net.minecraft.data.models.BlockModelGenerators;
import net.minecraft.data.models.ItemModelGenerators;
import net.minecraft.data.models.model.ModelTemplates;

public class ModBlockModelProvider extends FabricModelProvider {
    public ModBlockModelProvider(FabricDataOutput output) {
        super(output);
    }

    @Override
    public void generateBlockStateModels(BlockModelGenerators blockModels) {
        blockModels.createTrivialCube(ModBlocks.FAR_SAND);
        blockModels.createTrivialCube(ModBlocks.FARTHEST_PORTAL_FRAME);
        blockModels.createTrivialCube(ModBlocks.FARTHEST_PORTAL);
    }

    @Override
    public void generateItemModels(ItemModelGenerators itemModels) {
        itemModels.generateFlatItem(ModItems.ARABESQUE_DISC, ModelTemplates.FLAT_ITEM);
        itemModels.generateFlatItem(ModItems.SUNKEN_CATHEDRAL_DISC, ModelTemplates.FLAT_ITEM);
    }
}

