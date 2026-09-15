package io.github.insomniac.thefarthestlands.client.render;

import com.mojang.blaze3d.vertex.DefaultVertexFormat;
import com.mojang.blaze3d.vertex.VertexFormat;
import net.minecraft.client.renderer.FarthestPortalRenderTypeAccess;
import net.minecraft.client.renderer.RenderStateShard;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.ShaderInstance;
import net.minecraft.client.renderer.blockentity.TheEndPortalRenderer;

/**
 * Lazy {@link RenderType} for the Farthest Portal.
 * Uses the same End sky + star textures as vanilla, with our shader instead of {@code rendertype_end_portal}.
 */
public final class FarthestPortalRenderTypes {
    /** Filled by {@code CoreShaderRegistrationCallback} in the client initializer. */
    private static ShaderInstance farthestPortalShader;
    private static RenderType farthestPortal;

    private FarthestPortalRenderTypes() {}

    public static void setShader(ShaderInstance shader) {
        farthestPortalShader = shader;
    }

    public static RenderType farthestPortal() {
        if (farthestPortal == null) {
            farthestPortal = FarthestPortalRenderTypeAccess.create(
                    "thefarthestlands_farthest_portal",
                    DefaultVertexFormat.POSITION,
                    VertexFormat.Mode.QUADS,
                    1536,
                    RenderType.CompositeState.builder()
                            .setShaderState(new RenderStateShard.ShaderStateShard(() -> farthestPortalShader))
                            // Sampler0 = End sky, Sampler1 = End portal stars (same as vanilla).
                            .setTextureState(RenderStateShard.MultiTextureStateShard.builder()
                                    .add(TheEndPortalRenderer.END_SKY_LOCATION, false, false)
                                    .add(TheEndPortalRenderer.END_PORTAL_LOCATION, false, false)
                                    .build())
                            .createCompositeState(false)
            );
        }
        return farthestPortal;
    }
}
