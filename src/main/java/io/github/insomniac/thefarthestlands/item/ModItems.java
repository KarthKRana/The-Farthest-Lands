package io.github.insomniac.thefarthestlands.item;

import io.github.insomniac.thefarthestlands.TheFarthestLands;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Rarity;

/** Registers every custom item (portal key, materials, music discs). */
public class ModItems {
    //[[======================================ITEMS======================================]]\\
    /** Used on End Portal Frames to open a Farthest Lands portal instead of The End. */
    public static final Item GAZING_EYE = registerItem("gazing_eye",
            new GazingEyeItem(new Item.Properties().rarity(Rarity.EPIC).stacksTo(16)));

    /** Placeholder crafting material. */
    public static final Item RAW_SHARP_STEEL = registerItem("raw_sharp_steel",
            new Item(new Item.Properties().rarity(Rarity.COMMON).stacksTo(64)));

    /** Placeholder crafting material. */
    public static final Item SHARP_STEEL_INGOT = registerItem("sharp_steel_ingot",
            new Item(new Item.Properties().rarity(Rarity.COMMON).stacksTo(64)));


    /** Music disc that plays {@code arabesque} in a jukebox. */
    public static final Item ARABESQUE_DISC = registerItem("arabesque_disc",
            new Item(new Item.Properties()
                    .jukeboxPlayable(ResourceKey.create(
                            Registries.JUKEBOX_SONG,
                            ResourceLocation.fromNamespaceAndPath(TheFarthestLands.MOD_ID, "arabesque")))
                    .stacksTo(1).rarity(Rarity.RARE)));

    /** Music disc that plays {@code sunken_cathedral} in a jukebox. */
    public static final Item SUNKEN_CATHEDRAL_DISC = registerItem("sunken_cathedral_disc",
            new Item(new Item.Properties()
                    .jukeboxPlayable(ResourceKey.create(
                            Registries.JUKEBOX_SONG,
                            ResourceLocation.fromNamespaceAndPath(TheFarthestLands.MOD_ID, "sunken_cathedral")))
                    .stacksTo(1).rarity(Rarity.RARE)));

    //[[======================================HELPER METHODS======================================]]\\
    /** Puts the item in {@code BuiltInRegistries.ITEM} under this mod's namespace. */
    private static Item registerItem(String name, Item item) {
        return Registry.register(BuiltInRegistries.ITEM,
                ResourceLocation.fromNamespaceAndPath("thefarthestlands", name), item);
    }

    public static void registerModItems() {
        TheFarthestLands.LOGGER.info("Registering items"); //temp logging
    }
}
