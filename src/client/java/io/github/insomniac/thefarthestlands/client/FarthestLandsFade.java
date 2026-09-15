package io.github.insomniac.thefarthestlands.client;

import io.github.insomniac.thefarthestlands.world.ModDimensions;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.screens.ReceivingLevelScreen;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.level.Level;

/**
 * Client-only cinematic black overlay after entering the Farthest Lands.
 *
 * Hold at full black, then ease in: opacity = (1 - t)^{FADE_EXPONENT}.
 * That stays nearly black early and clears quickly near the end.
 *
 * The timer must not run during {@link ReceivingLevelScreen}: a slow dimension
 * load would finish the fade while the screen is still up, so the world would
 * appear with no overlay. Arrival is detected from a dimension change so we
 * still fade if the client never ticked inside the portal block.
 */
public final class FarthestLandsFade {
    /** Full-black duration before the fade starts (~3 seconds at 20 TPS). */
    private static final int HOLD_TICKS = 60;
    /** How long the ease-out from black takes. */
    private static final int FADE_TICKS = 107;
    /** Higher = longer near-black, faster snap to clear at the end. */
    private static final float FADE_EXPONENT = 4.0f;

    private static boolean active;
    private static float opacity;
    private static int elapsedTicks;
    private static ResourceKey<Level> lastDimension;
    /** True from Overworld → Farthest Lands until this cinematic finishes. */
    private static boolean pendingArrivalFade;

    private FarthestLandsFade() {}

    public static boolean isActive() { return active; }

    public static boolean shouldCoverLoadingScreen() {
        if (active || pendingArrivalFade) {
            return true;
        }
        Minecraft minecraft = Minecraft.getInstance();
        return minecraft.level != null
                && minecraft.level.dimension().equals(ModDimensions.FARTHEST_LANDS_LEVEL_KEY);
    }

    /** Portal touch: go black now, but do not restart a fade already in progress. */
    public static void start() {
        pendingArrivalFade = true;
        if (active) {
            opacity = 1.0f;
            return;
        }
        restart();
        pendingArrivalFade = true;
    }

    /** Full cinematic from the beginning. */
    public static void restart() {
        active = true;
        opacity = 1.0f;
        elapsedTicks = 0;
    }

    /** Terrain finished loading into the Farthest Lands — start the hold now. */
    public static void onTerrainReady() {
        Minecraft minecraft = Minecraft.getInstance();
        if (minecraft.level == null
                || !minecraft.level.dimension().equals(ModDimensions.FARTHEST_LANDS_LEVEL_KEY)) {
            return;
        }
        if (pendingArrivalFade) {
            restart();
        }
    }

    /** Cancels the overlay immediately (used on disconnect). */
    public static void reset() {
        active = false;
        opacity = 0.0f;
        elapsedTicks = 0;
        lastDimension = null;
        pendingArrivalFade = false;
    }

    /** Advances the hold/fade timers once per client tick. */
    public static void tick(Minecraft client) {
        if (client.player != null) {
            ResourceKey<Level> current = client.player.level().dimension();
            if (lastDimension != null
                    && !current.equals(lastDimension)
                    && current.equals(ModDimensions.FARTHEST_LANDS_LEVEL_KEY)) {
                pendingArrivalFade = true;
                restart();
            }
            lastDimension = current;
        }

        if (!active) {
            return;
        }

        // Keep full black and freeze the clock while chunks download.
        if (client.screen instanceof ReceivingLevelScreen) {
            opacity = 1.0f;
            elapsedTicks = 0;
            return;
        }

        elapsedTicks++;

        if (elapsedTicks <= HOLD_TICKS) { opacity = 1.0f; return; }

        int fadeElapsed = elapsedTicks - HOLD_TICKS;

        if (fadeElapsed >= FADE_TICKS) {
            opacity = 0.0f;
            active = false;
            pendingArrivalFade = false;
            return;
        }

        float t = Math.clamp(fadeElapsed / (float) FADE_TICKS, 0.0f, 1.0f);
        opacity = (float) Math.pow(1.0f - t, FADE_EXPONENT);
    }

    /** Draws a fullscreen black rectangle using HUD overlay depth. */
    public static void render(GuiGraphics graphics) {
        if (!active || opacity <= 0.0f) { return; }

        int alpha = Math.clamp((int) (opacity * 255.0f), 0, 255);
        if (alpha <= 0) { return; }

        int color = alpha << 24;
        graphics.fill(RenderType.guiOverlay(), 0, 0, graphics.guiWidth(), graphics.guiHeight(), color);
    }
}
