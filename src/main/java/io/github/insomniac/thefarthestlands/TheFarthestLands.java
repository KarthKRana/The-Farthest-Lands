package io.github.insomniac.thefarthestlands;

import io.github.insomniac.thefarthestlands.block.ModBlocks;
import io.github.insomniac.thefarthestlands.item.ModItems;
import io.github.insomniac.thefarthestlands.sound.ModSounds;
import io.github.insomniac.thefarthestlands.world.ModDimensions;
import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.itemgroup.v1.ItemGroupEvents;
import net.minecraft.world.item.CreativeModeTabs;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class TheFarthestLands implements ModInitializer {
	public static final String MOD_ID = "thefarthestlands";
	public static final Logger LOGGER = LoggerFactory.getLogger(MOD_ID);

	@Override
	public void onInitialize() {
		ItemGroupEvents.modifyEntriesEvent(CreativeModeTabs.INGREDIENTS).register(content -> {
			content.accept(ModItems.GAZING_EYE);
			content.accept(ModItems.RAW_SHARP_STEEL);
		});
		ItemGroupEvents.modifyEntriesEvent(CreativeModeTabs.NATURAL_BLOCKS).register(content -> {
			content.accept(ModBlocks.FAR_SAND);
		});
		ItemGroupEvents.modifyEntriesEvent(CreativeModeTabs.TOOLS_AND_UTILITIES).register(content -> {
			content.accept(ModItems.ARABESQUE_DISC);
			content.accept(ModItems.SUNKEN_CATHEDRAL_DISC);
			content.accept(ModItems.GAZING_EYE);
		});
		ModItems.registerModItems();
		ModSounds.registerSounds();
		ModBlocks.registerModBlocks();
		ModDimensions.register();
	}
}