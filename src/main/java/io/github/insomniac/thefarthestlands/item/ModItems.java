package io.github.insomniac.thefarthestlands.item;

import io.github.insomniac.thefarthestlands.TheFarthestLands;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Rarity;

public class ModItems {
    //[[======================================ITEMS======================================]]\\
    public static final Item GAZING_EYE = registerItem("gazing_eye",
            new GazingEyeItem(new Item.Properties().rarity(Rarity.EPIC).stacksTo(16)));

    public static final Item RAW_SHARP_STEEL = registerItem("raw_sharp_steel",
            new Item(new Item.Properties().rarity(Rarity.UNCOMMON).stacksTo(64)));


    public static final Item ARABESQUE_DISC = registerItem("arabesque_disc",
            new Item(new Item.Properties()
                    .jukeboxPlayable(ResourceKey.create(
                            Registries.JUKEBOX_SONG,
                            ResourceLocation.fromNamespaceAndPath(TheFarthestLands.MOD_ID, "arabesque")))
                    .stacksTo(1).rarity(Rarity.RARE)));

    public static final Item SUNKEN_CATHEDRAL_DISC = registerItem("sunken_cathedral_disc",
            new Item(new Item.Properties()
                    .jukeboxPlayable(ResourceKey.create(
                            Registries.JUKEBOX_SONG,
                            ResourceLocation.fromNamespaceAndPath(TheFarthestLands.MOD_ID, "sunken_cathedral")))
                    .stacksTo(1).rarity(Rarity.EPIC)));

    //[[======================================HELPER METHODS======================================]]\\
    private static Item registerItem(String name, Item item) {
        return Registry.register(BuiltInRegistries.ITEM,
                ResourceLocation.fromNamespaceAndPath("thefarthestlands", name), item);
    }

    public static void registerModItems() {
        TheFarthestLands.LOGGER.info("Registering items"); //temp logging
    }
}
