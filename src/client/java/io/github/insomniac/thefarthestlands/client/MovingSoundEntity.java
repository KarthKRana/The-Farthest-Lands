package io.github.insomniac.thefarthestlands.client;
import io.github.insomniac.thefarthestlands.entity.AmbientSoundEntity;
import net.minecraft.client.resources.sounds.AbstractTickableSoundInstance;
import net.minecraft.client.resources.sounds.SoundInstance;
import net.minecraft.sounds.SoundSource;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.util.RandomSource;
import net.minecraft.world.entity.Entity;

public class MovingSoundEntity extends AbstractTickableSoundInstance{

    private final AmbientSoundEntity ambientSoundEntity;

    public MovingSoundEntity(SoundEvent sound, SoundSource source, AmbientSoundEntity ambientSoundEntity) {
        super(sound, source, SoundInstance.createUnseededRandom());
        this.ambientSoundEntity = ambientSoundEntity;
        this.looping = true;
        this.delay = 0;
    }

    @Override
    public void tick() {
        if (this.ambientSoundEntity.isExpired()) {
            this.stop(); return;
        }
        //Lock sound to the entity
        this.x = this.ambientSoundEntity.getPosition(null).x;
        this.y = this.ambientSoundEntity.getPosition(null).y;
        this.z = this.ambientSoundEntity.getPosition(null).z;

    }
}
