package io.github.insomniac.thefarthestlands.client.render;

import com.mojang.blaze3d.vertex.DefaultVertexFormat;
import com.mojang.blaze3d.vertex.VertexFormat;
import net.minecraft.client.renderer.FarthestPortalRenderTypeAccess;
import net.minecraft.client.renderer.RenderStateShard;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.ShaderInstance;
import net.minecraft.client.renderer.blockentity.TheEndPortalRenderer;

public final class FarthestPortalRenderTypes {
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
