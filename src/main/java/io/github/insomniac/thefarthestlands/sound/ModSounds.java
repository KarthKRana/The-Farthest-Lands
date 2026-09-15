package io.github.insomniac.thefarthestlands.sound;

import io.github.insomniac.thefarthestlands.TheFarthestLands;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.world.level.block.SoundType; //BlockSoundGroup is deprecated

/** Registers SoundEvents (ogg lookups) and the Far Sand block SoundType. */
public class ModSounds {
    //[[======================================SOUND EVENTS======================================]]\\
    /** Custom Gazing Eye placement sound (registered, but placement currently uses vanilla frame-fill). */
    public static final SoundEvent GAZING_EYE_PLACED_ON_PORTAL_FRAME
            = registerSoundEvent("gazing_eye_placed_on_portal_frame", 8.0f);

    /** Boom played when the 3x3 Farthest Portal actually lights. */
    public static final SoundEvent FARTHEST_PORTAL_LIT
            = registerSoundEvent("farthest_portal_lit", 16.0f);

    // Far Sand
    public static final SoundEvent FAR_SAND_BREAK
            = registerSoundEvent("far_sand_break", 16.0f);
    public static final SoundEvent FAR_SAND_STEP
            = registerSoundEvent("far_sand_step", 16.0f);
    public static final SoundEvent FAR_SAND_PLACE
            = registerSoundEvent("far_sand_place", 16.0f);
    public static final SoundEvent FAR_SAND_HIT
            = registerSoundEvent("far_sand_hit", 16.0f);
    public static final SoundEvent FAR_SAND_FALL
            = registerSoundEvent("far_sand_fall", 16.0f);

    /** Bundles the five Far Sand events into Minecraft's block-sound API. */
    public static final SoundType FAR_SAND_SOUNDS = new SoundType(1.0f, 1.0f,
            FAR_SAND_BREAK, FAR_SAND_STEP, FAR_SAND_PLACE, FAR_SAND_HIT, FAR_SAND_FALL);

    //[[======================================MUSIC DISC LOADING======================================]]\\\
    // -Need to create the audio variables so the game loads the .ogg files, ignore 0 references
    public static final SoundEvent ARABESQUE_AUDIO
            = registerSoundEvent("arabesque", 64.0f);

    public static final SoundEvent SUNKEN_CATHEDRAL_AUDIO
            = registerSoundEvent("sunken_cathedral", 64.0f);

    //[[======================================HELPER METHODS======================================]]\\
    /**
     * Registers a {@link SoundEvent} whose id matches {@code sounds.json}.
     * {@code rangeInBlocks} is how far players can hear it.
     */
    private static SoundEvent registerSoundEvent(String name, float rangeInBlocks) {
        ResourceLocation id = ResourceLocation.fromNamespaceAndPath(TheFarthestLands.MOD_ID, name);
        return Registry.register(BuiltInRegistries.SOUND_EVENT, id, SoundEvent.createFixedRangeEvent(id, rangeInBlocks));
    }

    public static void registerSounds() { TheFarthestLands.LOGGER.info("Registering sfx"); }
}
