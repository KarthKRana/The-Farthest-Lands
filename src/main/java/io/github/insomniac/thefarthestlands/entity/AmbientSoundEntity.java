package io.github.insomniac.thefarthestlands.entity;

import io.github.insomniac.thefarthestlands.sound.SoundPath;
import net.minecraft.world.phys.Vec3;

public class AmbientSoundEntity {
    private final Vec3           startPos;
    private final Vec3          direction;
    private final SoundPath.Type pathType;
    private final float             speed;
    private final int            maxTicks;
    private       int     currentTick = 0;

    public AmbientSoundEntity(Vec3 playerPos, SoundPath.Type pathType, float speed, int maxTicks) {
        this.pathType = pathType;
        this.speed = speed;
        this.maxTicks = maxTicks;
        //Make random angles for spawning random sounds
        double angle = Math.random() * Math.PI * 2;
        double distance = 20.0 + Math.random() * 30.0;

        double startX = playerPos.x + Math.cos(angle) * distance;
        double startY = playerPos.y + (Math.random() * 10.0 - 5.0);
        double startZ = playerPos.z + Math.sin(angle) * distance;
        this.startPos = new Vec3(startX, startY, startZ);
        // Target near the player for pass-by/through trajectories
        this.direction = playerPos.subtract(this.startPos).normalize();

    }

    public void tick(Vec3 playerPos) { this.currentTick++; }

    // Getters for X, Y, Z coordinates...
    public Vec3 getPosition(Vec3 playerPos) {
        return SoundPath.calculatePosition(this.pathType,
                this.startPos, this.direction,
                playerPos, this.currentTick,
                this.speed);
    }

    public boolean isExpired() {
        return this.currentTick >= this.maxTicks;
    }




}
