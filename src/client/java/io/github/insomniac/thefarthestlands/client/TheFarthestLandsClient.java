package io.github.insomniac.thefarthestlands.client;

import com.mojang.blaze3d.vertex.DefaultVertexFormat;
import io.github.insomniac.thefarthestlands.TheFarthestLands;
import io.github.insomniac.thefarthestlands.block.entity.ModBlockEntities;
import io.github.insomniac.thefarthestlands.client.render.FarthestPortalRenderTypes;
import io.github.insomniac.thefarthestlands.client.render.FarthestPortalRenderer;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
import net.fabricmc.fabric.api.client.networking.v1.ClientPlayConnectionEvents;
import net.fabricmc.fabric.api.client.rendering.v1.BlockEntityRendererRegistry;
import net.fabricmc.fabric.api.client.rendering.v1.CoreShaderRegistrationCallback;
import net.fabricmc.fabric.api.client.rendering.v1.HudRenderCallback;
import net.minecraft.resources.ResourceLocation;

/**
 * Client-only entrypoint (see {@code fabric.mod.json}).
 * Wires shaders, the portal renderer, and the cinematic fade overlay.
 */
public class TheFarthestLandsClient implements ClientModInitializer {
	@Override
	public void onInitializeClient() {
		// Load rendertype_farthest_portal.json/.vsh/.fsh and hand the ShaderInstance to our RenderType.
		CoreShaderRegistrationCallback.EVENT.register(context -> context.register(
				ResourceLocation.fromNamespaceAndPath(TheFarthestLands.MOD_ID, "rendertype_farthest_portal"),
				DefaultVertexFormat.POSITION,
				FarthestPortalRenderTypes::setShader
		));
		// Draw Farthest Portal blocks with End Portal geometry + our grayscale shader.
		BlockEntityRendererRegistry.register(ModBlockEntities.FARTHEST_PORTAL, FarthestPortalRenderer::new);

		TheFarthestLands.startEntryFade = FarthestLandsFade::start;
		ClientTickEvents.END_CLIENT_TICK.register(FarthestLandsFade::tick);
		HudRenderCallback.EVENT.register((graphics, tickCounter) -> FarthestLandsFade.render(graphics));
		ClientPlayConnectionEvents.DISCONNECT.register((handler, client) -> FarthestLandsFade.reset());
	}
}
