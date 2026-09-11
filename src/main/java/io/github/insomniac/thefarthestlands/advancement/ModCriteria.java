package io.github.insomniac.thefarthestlands.advancement;

import io.github.insomniac.thefarthestlands.TheFarthestLands;
import net.minecraft.advancements.CriterionTrigger;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.ResourceLocation;

public class ModCriteria {
    public static final TheEndOfItAllTrigger THE_END_OF_IT_ALL = register("the_end_of_it_all", new TheEndOfItAllTrigger());

    private static <T extends CriterionTrigger<?>> T register(String name, T trigger) {
        return Registry.register(BuiltInRegistries.TRIGGER_TYPES,
                ResourceLocation.fromNamespaceAndPath(TheFarthestLands.MOD_ID, name), trigger);
    }

    public static void register() {
        TheFarthestLands.LOGGER.info("Registering advancement criteria");
    }
}
