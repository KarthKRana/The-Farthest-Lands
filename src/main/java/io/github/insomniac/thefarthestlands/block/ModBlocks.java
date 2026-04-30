package io.github.insomniac.thefarthestlands.block;

//import io.github.insomniac.thefarthestlands.TheFarthestLands;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockBehaviour;

public class ModBlocks {
    public static final Block FARTHEST_PORTAL_FRAME = registerBlock("farthest_portal_frame",
            new Block(BlockBehaviour.Properties.ofFullCopy(Blocks.END_PORTAL_FRAME)));

    public static final Block FARTHEST_PORTAL = registerBlock("farthest_portal",
            new FarthestPortalBlock(BlockBehaviour.Properties.of().noCollission().noOcclusion().strength(-1.0f)));

    private static Block registerBlock(String name, Block block) {
        registerBlockItem(name, block);
        return Registry.register(BuiltInRegistries.BLOCK,
                ResourceLocation.fromNamespaceAndPath("thefarthestlands", name), block);
    }

    private static void registerBlockItem(String name, Block block) {
        Registry.register(BuiltInRegistries.ITEM,
                ResourceLocation.fromNamespaceAndPath("thefarthestlands", name),
                new BlockItem(block, new Item.Properties()));
    }

    public static void registerModBlocks() {
        System.out.println("Registering Blocks for The Farthest Lands");
    }
}