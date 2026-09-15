package io.github.insomniac.thefarthestlands.client;

import io.github.insomniac.thefarthestlands.entity.AmbientSoundEntity;
import io.github.insomniac.thefarthestlands.sound.SoundPath;
import net.minecraft.client.Minecraft;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.entity.player.Player;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class AmbientSoundManager {
    private static final Random RANDOM = new Random();
    private static final List<ActiveSound> ACTIVE_SOUNDS = new ArrayList<>();

    // Uses your existing MovingSoundEntity class!
    private record ActiveSound(AmbientSoundEntity entity, MovingSoundEntity soundInstance) {}

    public static void tick(Minecraft client) {
        Player player = client.player;
        if (player == null || client.level == null) return;

        // 1. Tick entities and clean up finished sounds
        ACTIVE_SOUNDS.removeIf(active -> {
            active.entity().tick(player.position());
            if (active.entity().isExpired()) {
                client.getSoundManager().stop(active.soundInstance());
                return true;
            }
            return false;
        });

        // 2. Example trigger: ~1% chance per tick to spawn an ambient sound
        if (RANDOM.nextFloat() < 0.01f && ACTIVE_SOUNDS.size() < 5) {
            SoundEvent breezeEvent = null; // Plug in your ModSounds event here

            if (breezeEvent != null) {
                spawnPassBySound(client, player, breezeEvent);
            }
        }
    }

    public static void spawnPassBySound(Minecraft client, Player player, SoundEvent soundEvent) {
        AmbientSoundEntity entity = new AmbientSoundEntity(
                player.position(),
                SoundPath.Type.STRAIGHT_PASS_BY,
                0.15f, // Speed
                200    // Lifespan in ticks (10 seconds)
        );

        // Instantiate your MovingSoundEntity
        MovingSoundEntity soundInstance = new MovingSoundEntity(soundEvent, SoundSource.AMBIENT, entity);

        ACTIVE_SOUNDS.add(new ActiveSound(entity, soundInstance));
        client.getSoundManager().play(soundInstance);
    }
}