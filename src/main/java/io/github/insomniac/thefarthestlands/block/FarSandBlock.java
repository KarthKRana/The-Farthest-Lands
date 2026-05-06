package io.github.insomniac.thefarthestlands.block;

import com.mojang.serialization.MapCodec;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.block.FallingBlock;
import net.minecraft.world.level.block.state.BlockState;

public class FarSandBlock extends FallingBlock {
    public static final MapCodec<FarSandBlock> CODEC = simpleCodec(FarSandBlock::new);
    public FarSandBlock(Properties properties) { super(properties); }

    @Override
    public MapCodec<? extends FarSandBlock> codec() { return CODEC; }

    @Override
    public int getDustColor(BlockState state, BlockGetter reader, BlockPos pos) { return 0x808080; }
}