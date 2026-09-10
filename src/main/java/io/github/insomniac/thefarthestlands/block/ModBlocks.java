package io.github.insomniac.thefarthestlands.block;

import io.github.insomniac.thefarthestlands.TheFarthestLands;
import io.github.insomniac.thefarthestlands.sound.ModSounds;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.*;
import net.minecraft.world.level.block.state.BlockBehaviour;

public class ModBlocks {
    //[[======================================BLOCKS======================================]]\\
    public static final FarthestPortalFrameBlock FARTHEST_PORTAL_FRAME = registerBlock("farthest_portal_frame",
            new FarthestPortalFrameBlock(BlockBehaviour.Properties.ofFullCopy(Blocks.END_PORTAL_FRAME)));

    public static final Block FARTHEST_PORTAL = registerBlock("farthest_portal",
            new FarthestPortalBlock(BlockBehaviour.Properties.of().noCollission().noOcclusion().strength(-1.0f)));

    public static final Block FAR_SAND = registerBlock("far_sand",
            new FarSandBlock(BlockBehaviour.Properties.ofFullCopy(Blocks.SAND)
                    .sound(ModSounds.FAR_SAND_SOUNDS)
                    .strength(-1.0f)  // Indestructible by mining
            ));

    //[[======================================HELPER METHODS======================================]]\\
    private static <T extends Block> T registerBlock(String name, T block) {
        registerBlockItem(name, block);
        return Registry.register(BuiltInRegistries.BLOCK,
                ResourceLocation.fromNamespaceAndPath("thefarthestlands", name), block);
    }

    private static void registerBlockItem(String name, Block block) {
        Registry.register(BuiltInRegistries.ITEM,
                ResourceLocation.fromNamespaceAndPath("thefarthestlands", name),
                new BlockItem(block, new Item.Properties()));
    }

    public static void registerModBlocks() { TheFarthestLands.LOGGER.info("Registering blocks"); }
}