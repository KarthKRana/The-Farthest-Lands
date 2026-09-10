package io.github.insomniac.thefarthestlands.client;

import com.mojang.blaze3d.vertex.DefaultVertexFormat;
import io.github.insomniac.thefarthestlands.TheFarthestLands;
import io.github.insomniac.thefarthestlands.block.entity.ModBlockEntities;
import io.github.insomniac.thefarthestlands.client.render.FarthestPortalRenderTypes;
import io.github.insomniac.thefarthestlands.client.render.FarthestPortalRenderer;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.rendering.v1.BlockEntityRendererRegistry;
import net.fabricmc.fabric.api.client.rendering.v1.CoreShaderRegistrationCallback;
import net.minecraft.resources.ResourceLocation;

public class TheFarthestLandsClient implements ClientModInitializer {
	@Override
	public void onInitializeClient() {
		CoreShaderRegistrationCallback.EVENT.register(context -> context.register(
				ResourceLocation.fromNamespaceAndPath(TheFarthestLands.MOD_ID, "rendertype_farthest_portal"),
				DefaultVertexFormat.POSITION,
				FarthestPortalRenderTypes::setShader
		));
		BlockEntityRendererRegistry.register(ModBlockEntities.FARTHEST_PORTAL, FarthestPortalRenderer::new);
	}
}
