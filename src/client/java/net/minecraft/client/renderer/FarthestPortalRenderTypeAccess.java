package net.minecraft.client.renderer;

import com.mojang.blaze3d.vertex.VertexFormat;

/**
 * Same-package helper so we can call RenderType.create (package-private in 1.21.1).
 */
public final class FarthestPortalRenderTypeAccess {
    private FarthestPortalRenderTypeAccess() {}

    /** Forwards to Minecraft's package-private {@code RenderType.create}. */
    public static RenderType create(
            String name,
            VertexFormat format,
            VertexFormat.Mode mode,
            int bufferSize,
            RenderType.CompositeState state) {
        return RenderType.create(name, format, mode, bufferSize, state);
    }
}
