package io.github.insomniac.thefarthestlands.block.entity;

import io.github.insomniac.thefarthestlands.TheFarthestLands;
import io.github.insomniac.thefarthestlands.block.ModBlocks;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.block.entity.BlockEntityType;

public class ModBlockEntities {
    public static final BlockEntityType<FarthestPortalBlockEntity> FARTHEST_PORTAL = Registry.register(
            BuiltInRegistries.BLOCK_ENTITY_TYPE,
            ResourceLocation.fromNamespaceAndPath(TheFarthestLands.MOD_ID, "farthest_portal"),
            BlockEntityType.Builder.of(FarthestPortalBlockEntity::new, ModBlocks.FARTHEST_PORTAL).build(null)
    );

    public static void register() {
        TheFarthestLands.LOGGER.info("Registering block entities");
    }
}
