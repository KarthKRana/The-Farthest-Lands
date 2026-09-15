package io.github.insomniac.thefarthestlands;

import net.fabricmc.api.ClientModInitializer;

/**
 * Unused leftover client entrypoint in the common source set.
 * The real client init is {@code io.github.insomniac.thefarthestlands.client.TheFarthestLandsClient},
 * which is what {@code fabric.mod.json} points at.
 */
public class TheFarthestLandsClient implements ClientModInitializer {
    @Override
    public void onInitializeClient() {}
}
