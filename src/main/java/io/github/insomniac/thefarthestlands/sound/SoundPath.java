package io.github.insomniac.thefarthestlands.sound;



import net.minecraft.world.phys.Vec3;

import java.util.Random;

public class SoundPath {

    private Random random = new Random();

    public enum Type {
        STRAIGHT_PASS_THROUGH, STRAIGHT_PASS_BY, CURVE_2D
    }

    public static Vec3 calculatePosition(Type type, Vec3 startPos, Vec3 direction,
                                         Vec3 playerPos, int currentTick, float speed) {
        double time = 0.05 * currentTick;

        switch (type) {
            case STRAIGHT_PASS_THROUGH:
            case STRAIGHT_PASS_BY:
            default:
                return startPos.add(currentTick * speed, 0, 0);
        }
    }
}
