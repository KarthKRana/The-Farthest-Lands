package io.github.insomniac.thefarthestlands.world;

import io.github.insomniac.thefarthestlands.TheFarthestLands;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.dimension.DimensionType;

/**
 * Resource keys for the Farthest Lands dimension.
 * The actual dimension JSON lives under {@code data/thefarthestlands/dimension/};
 * this class just gives Java a typed id to teleport into.
 */
public class ModDimensions {
    /** World/level key used by {@code server.getLevel(...)} and portal teleport. */
    public static final ResourceKey<Level> FARTHEST_LANDS_LEVEL_KEY = ResourceKey.create(
            Registries.DIMENSION,
            ResourceLocation.fromNamespaceAndPath("thefarthestlands", "farthest_lands")
    );

    /** Dimension-type key (sky, bed rules, etc.) matching the datapack dimension type. */
    public static final ResourceKey<DimensionType> FARTHEST_LANDS_TYPE_KEY = ResourceKey.create(
            Registries.DIMENSION_TYPE,
            ResourceLocation.fromNamespaceAndPath("thefarthestlands", "farthest_lands")
    );

    public static void register() {
        TheFarthestLands.LOGGER.info("Far from registering");
    }
}
