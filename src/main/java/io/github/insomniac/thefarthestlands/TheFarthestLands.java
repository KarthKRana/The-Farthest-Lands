package io.github.insomniac.thefarthestlands;

import io.github.insomniac.thefarthestlands.advancement.ModCriteria;
import io.github.insomniac.thefarthestlands.block.ModBlocks;
import io.github.insomniac.thefarthestlands.block.entity.ModBlockEntities;
import io.github.insomniac.thefarthestlands.item.ModItems;
import io.github.insomniac.thefarthestlands.sound.ModSounds;
import io.github.insomniac.thefarthestlands.world.ModDimensions;
import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.itemgroup.v1.ItemGroupEvents;
import net.minecraft.world.item.CreativeModeTabs;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Entrypoint for this single-player mod. Fabric still runs an integrated server
 * in the same process, so world changes happen on that server thread.
 */
public class TheFarthestLands implements ModInitializer {
	/** Namespace used in registries, assets, and data packs: {@code thefarthestlands:...} */
	public static final String MOD_ID = "thefarthestlands";
	/** Shared logger; search logs for this name when debugging. */
	public static final Logger LOGGER = LoggerFactory.getLogger(MOD_ID);

	/**
	 * Client entrypoint points this at the black overlay.
	 * The portal calls it on the client thread so we do not need a custom packet.
	 */
	public static Runnable startEntryFade = () -> {};

	@Override
	public void onInitialize() {
		// Put custom items into vanilla creative tabs so they show up in the inventory GUI.
		ItemGroupEvents.modifyEntriesEvent(CreativeModeTabs.INGREDIENTS).register(content -> {
			content.accept(ModItems.GAZING_EYE);
			content.accept(ModItems.RAW_SHARP_STEEL);
			content.accept(ModItems.SHARP_STEEL_INGOT);
		});
		ItemGroupEvents.modifyEntriesEvent(CreativeModeTabs.NATURAL_BLOCKS).register(content -> {
			content.accept(ModBlocks.FAR_SAND);
		});
		ItemGroupEvents.modifyEntriesEvent(CreativeModeTabs.TOOLS_AND_UTILITIES).register(content -> {
			content.accept(ModItems.ARABESQUE_DISC);
			content.accept(ModItems.SUNKEN_CATHEDRAL_DISC);
			content.accept(ModItems.GAZING_EYE);
		});
		// Static fields on these classes actually register content; the methods force class-load + log.
		  ModItems.registerModItems();
		   ModSounds.registerSounds();
		ModBlocks.registerModBlocks();
		  ModBlockEntities.register();
		       ModCriteria.register();
		     ModDimensions.register();
	}
}
