package io.github.insomniac.thefarthestlands.block;

import com.mojang.serialization.MapCodec;
import net.minecraft.core.Direction;
import net.minecraft.world.level.block.EndPortalFrameBlock;
import net.minecraft.world.level.block.state.BlockState;

public class FarthestPortalFrameBlock extends EndPortalFrameBlock {
    public static final MapCodec<FarthestPortalFrameBlock> CODEC = simpleCodec(FarthestPortalFrameBlock::new);

    public FarthestPortalFrameBlock(Properties properties) {
        super(properties);
    }

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

    public static boolean isFilled(BlockState state) {
        return state.is(ModBlocks.FARTHEST_PORTAL_FRAME) && state.getValue(HAS_EYE);
    }
}
