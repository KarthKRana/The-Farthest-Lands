package io.github.insomniac.thefarthestlands.datagen;

import io.github.insomniac.thefarthestlands.block.ModBlocks;
import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricBlockLootTableProvider;
import java.util.concurrent.CompletableFuture;

public class ModLootTableProvider extends FabricBlockLootTableProvider {
    public ModLootTableProvider(FabricDataOutput dataOutput) {
        super(dataOutput, CompletableFuture.completedFuture(null));
    }

    @Override
    public void generate() {
        // Blocks that drop themselves
        this.dropSelf(ModBlocks.FAR_SAND);
    }
}

