package io.github.insomniac.thefarthestlands.world;

import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.dimension.DimensionType;

public class ModDimensions {
    public static final ResourceKey<Level> FARTHEST_LANDS_LEVEL_KEY = ResourceKey.create(
            Registries.DIMENSION,
            ResourceLocation.fromNamespaceAndPath("thefarthestlands", "farthest_lands")
    );

    public static final ResourceKey<DimensionType> FARTHEST_LANDS_TYPE_KEY = ResourceKey.create(
            Registries.DIMENSION_TYPE,
            ResourceLocation.fromNamespaceAndPath("thefarthestlands", "farthest_lands")
    );

    public static void register() {
        System.out.println("Registering Dimensions for The Farthest Lands");
    }
}