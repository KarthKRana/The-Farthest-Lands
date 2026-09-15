package io.github.insomniac.thefarthestlands.client.mixin;

import io.github.insomniac.thefarthestlands.client.FarthestLandsFade;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.screens.ReceivingLevelScreen;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

/** Replaces the dirt / “Downloading terrain” screen with solid black when entering the Farthest Lands. */
@Mixin(ReceivingLevelScreen.class)
public class ReceivingLevelScreenMixin {
    @Inject(method = "render", at = @At("HEAD"), cancellable = true)
    private void thefarthestlands$pitchBlack(GuiGraphics graphics, int mouseX, int mouseY, float partialTick, CallbackInfo ci) {
        if (!FarthestLandsFade.shouldCoverLoadingScreen()) {
            return;
        }
        ReceivingLevelScreen screen = (ReceivingLevelScreen) (Object) this;
        graphics.fill(0, 0, screen.width, screen.height, 0xFF000000);
        ci.cancel();
    }

    @Inject(method = "onClose", at = @At("TAIL"))
    private void thefarthestlands$terrainReady(CallbackInfo ci) {
        FarthestLandsFade.onTerrainReady();
    }
}
