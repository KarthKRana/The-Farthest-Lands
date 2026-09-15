package io.github.insomniac.thefarthestlands.block;

import com.mojang.serialization.MapCodec;
import net.minecraft.core.Direction;
import net.minecraft.world.level.block.EndPortalFrameBlock;
import net.minecraft.world.level.block.state.BlockState;

/**
 * Custom portal frame that keeps vanilla collision / facing / {@code eye} state,
 * but uses our filled model (Gazing Eye texture) instead of the vanilla eye.
 */
public class FarthestPortalFrameBlock extends EndPortalFrameBlock {
    public static final MapCodec<FarthestPortalFrameBlock> CODEC = simpleCodec(FarthestPortalFrameBlock::new);

    public FarthestPortalFrameBlock(Properties properties) {
        super(properties);
    }

    /** Parent codec is typed as EndPortalFrameBlock; xmap adapts our subclass in and out. */
    @Override
    public MapCodec<EndPortalFrameBlock> codec() {
        return CODEC.xmap(block -> block, block -> (FarthestPortalFrameBlock) block);
    }

    /** Copies facing from a vanilla or custom frame and marks this block as filled. */
    public static BlockState withGazingEye(BlockState source) {
        Direction facing = source.hasProperty(FACING) ? source.getValue(FACING) : Direction.NORTH;
        return ModBlocks.FARTHEST_PORTAL_FRAME.defaultBlockState()
                .setValue(FACING, facing)
                .setValue(HAS_EYE, true);
    }

    /** True when this is our frame and a Gazing Eye is already in it. */
    public static boolean isFilled(BlockState state) {
        return state.is(ModBlocks.FARTHEST_PORTAL_FRAME) && state.getValue(HAS_EYE);
    }
}
