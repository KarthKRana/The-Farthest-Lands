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

/** Registers every custom block and its matching inventory item. */
public class ModBlocks {
    //[[======================================BLOCKS======================================]]\\
    /** End-portal-frame lookalike that shows the Gazing Eye texture when filled. */
    public static final FarthestPortalFrameBlock FARTHEST_PORTAL_FRAME = registerBlock("farthest_portal_frame",
            new FarthestPortalFrameBlock(BlockBehaviour.Properties.ofFullCopy(Blocks.END_PORTAL_FRAME)));

    /** Interior portal surface; walking into it teleports to the Farthest Lands. */
    public static final Block FARTHEST_PORTAL = registerBlock("farthest_portal",
            new FarthestPortal(BlockBehaviour.Properties.ofFullCopy(Blocks.END_PORTAL)));

    /** Dimension ground block: sand physics, custom sounds, cannot be mined. */
    public static final Block FAR_SAND = registerBlock("far_sand",
            new FarSandBlock(BlockBehaviour.Properties.ofFullCopy(Blocks.SAND)
                    .sound(ModSounds.FAR_SAND_SOUNDS)
                    .strength(-1.0f)  // Indestructible by mining
            ));

    //[[======================================HELPER METHODS======================================]]\\
    /** Puts the block in {@code BuiltInRegistries.BLOCK} and creates a BlockItem with the same id. */
    private static <T extends Block> T registerBlock(String name, T block) {
        registerBlockItem(name, block);
        return Registry.register(BuiltInRegistries.BLOCK,
                ResourceLocation.fromNamespaceAndPath("thefarthestlands", name), block);
    }

    /** Lets players hold / place the block from their inventory. */
    private static void registerBlockItem(String name, Block block) {
        Registry.register(BuiltInRegistries.ITEM,
                ResourceLocation.fromNamespaceAndPath("thefarthestlands", name),
                new BlockItem(block, new Item.Properties()));
    }

    public static void registerModBlocks() { TheFarthestLands.LOGGER.info("Registering blocks"); }
}
