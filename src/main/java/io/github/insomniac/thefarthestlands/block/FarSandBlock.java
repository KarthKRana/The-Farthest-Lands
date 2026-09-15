package io.github.insomniac.thefarthestlands.block;

import com.mojang.serialization.MapCodec;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.block.FallingBlock;
import net.minecraft.world.level.block.state.BlockState;

/** Gravity sand used as the Farthest Lands surface. Falls like vanilla sand. */
public class FarSandBlock extends FallingBlock {
    /** Required in 1.21 so the block can be serialized in data-driven contexts. */
    public static final MapCodec<FarSandBlock> CODEC = simpleCodec(FarSandBlock::new);
    public FarSandBlock(Properties properties) { super(properties); }

    @Override
    public MapCodec<? extends FarSandBlock> codec() { return CODEC; }

    /** RGB color of the falling-dust particles (medium gray). */
    @Override
    public int getDustColor(BlockState state, BlockGetter reader, BlockPos pos) { return 0x808080; }
}
