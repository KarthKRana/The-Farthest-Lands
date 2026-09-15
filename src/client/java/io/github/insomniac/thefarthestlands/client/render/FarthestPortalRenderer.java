package io.github.insomniac.thefarthestlands.client.render;

import io.github.insomniac.thefarthestlands.block.entity.FarthestPortalBlockEntity;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.blockentity.BlockEntityRendererProvider;
import net.minecraft.client.renderer.blockentity.TheEndPortalRenderer;

/** Same geometry as the End Portal, but bound to the grayscale farthest-portal shader. */
public class FarthestPortalRenderer extends TheEndPortalRenderer<FarthestPortalBlockEntity> {
    public FarthestPortalRenderer(BlockEntityRendererProvider.Context context) {
        super(context);
    }

    /** Swaps vanilla's end-portal RenderType for ours. */
    @Override
    protected RenderType renderType() {
        return FarthestPortalRenderTypes.farthestPortal();
    }
}
