package io.github.insomniac.thefarthestlands.item;

import net.minecraft.core.Registry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Rarity;

public class ModItems {
    public static final Item GAZING_EYE = registerItem("gazing_eye",
            new GazingEyeItem(new Item.Properties().rarity(Rarity.EPIC).stacksTo(16)));

    // Helper method to make registration cleaner
    private static Item registerItem(String name, Item item) {
        return Registry.register(BuiltInRegistries.ITEM,
                ResourceLocation.fromNamespaceAndPath("thefarthestlands", name), item);
    }

    public static void registerModItems() {
        System.out.println("Registering Mod Items for The Farthest Lands"); //temp logging
    }
}
