package io.github.insomniac.thefarthestlands.item;

import io.github.insomniac.thefarthestlands.block.FarthestPortalFrameBlock;
import io.github.insomniac.thefarthestlands.block.ModBlocks;
import io.github.insomniac.thefarthestlands.sound.ModSounds;
import net.minecraft.core.BlockPos;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.EndPortalFrameBlock;
import net.minecraft.world.level.block.state.BlockState;

public class GazingEyeItem extends Item {
    public static boolean farPortalActivated = false;

    public GazingEyeItem(Properties properties) { super(properties); }

    @Override
    public InteractionResult useOn(UseOnContext context) {
        Level level = context.getLevel();
        BlockPos pos = context.getClickedPos();
        BlockState state = level.getBlockState(pos);
        // Only allow portal to open in the Overworld
        if (level.dimension() != Level.OVERWORLD) { return InteractionResult.PASS; }
        if (canInsertGazingEye(state)) {
            if (!level.isClientSide) {
                level.setBlock(pos, FarthestPortalFrameBlock.withGazingEye(state), 3);
                context.getItemInHand().shrink(1);
                level.playSound(null, pos, ModSounds.GAZING_EYE_PLACED_ON_PORTAL_FRAME, SoundSource.BLOCKS, 0.8F, 1.0F);
                if (checkPortalStructure(level, pos)) { farPortalActivated = true; }
            }
            return InteractionResult.SUCCESS;
        }
        return InteractionResult.PASS;
    }

    private boolean canInsertGazingEye(BlockState state) {
        if (!state.is(Blocks.END_PORTAL_FRAME) && !state.is(ModBlocks.FARTHEST_PORTAL_FRAME)) {
            return false;
        }
        return !state.getValue(EndPortalFrameBlock.HAS_EYE);
    }

    /**
     * Scans a 10-block radius around the placed eye.
     * If 12 filled frames are found, the portal is considered complete.
     */
    private boolean checkPortalStructure(Level level, BlockPos clickedPos) {
        int eyeCount = 0;
        int radius = 5;
        // 1. Count the eyes (standard check)
        for (BlockPos pos : BlockPos.betweenClosed(clickedPos.offset(-radius, -1, -radius), clickedPos.offset(radius, 1, radius))) {
            if (FarthestPortalFrameBlock.isFilled(level.getBlockState(pos))) {
                eyeCount++;
            }
        }

        if (eyeCount >= 12) {
            // 2. Find the actual 3x3 center. Then scan a small area around the clicked block on the SAME Y level
            for (int x = -4; x <= 4; x++) {
                for (int z = -4; z <= 4; z++) {
                    BlockPos potentialCenter = clickedPos.offset(x, 0, z);
                    if (isCorrectPortalCenter(level, potentialCenter)) {
                        activateFarthestPortal(level, potentialCenter);
                        level.playSound(null, potentialCenter, ModSounds.FARTHEST_PORTAL_LIT,
                                SoundSource.BLOCKS, 1.0F, 1.0F);
                        return true;
                    }
                }
            }
        }
        return false;
    }

    // Helper to find the actual 3x3 hole
    private boolean isCorrectPortalCenter(Level level, BlockPos pos) {
        // Check that all 12 frame blocks surrounding this center are filled farthest portal frames
        for (int x = -1; x <= 1; x++) {
            if (!FarthestPortalFrameBlock.isFilled(level.getBlockState(pos.offset(x, 0, -2)))) return false;
            if (!FarthestPortalFrameBlock.isFilled(level.getBlockState(pos.offset(x, 0, 2)))) return false;
        }
        for (int z = -1; z <= 1; z++) {
            if (!FarthestPortalFrameBlock.isFilled(level.getBlockState(pos.offset(-2, 0, z)))) return false;
            if (!FarthestPortalFrameBlock.isFilled(level.getBlockState(pos.offset(2, 0, z)))) return false;
        }
        return true;
    }

    private void activateFarthestPortal(Level level, BlockPos center) {
        // Flag '3' means: Update the block + Send to clients + Re-render
        // Convert leftover vanilla frames in the ring, preserving facing and the gazing-eye model.
        for (BlockPos pos : BlockPos.betweenClosed(center.offset(-3, 0, -3), center.offset(3, 0, 3))) {
            BlockState state = level.getBlockState(pos);
            if (state.is(Blocks.END_PORTAL_FRAME)) {
                level.setBlock(pos, FarthestPortalFrameBlock.withGazingEye(state), 3);
            }
        }
        // Spawn Portal Blocks
        for (int x = -1; x <= 1; x++) {
            for (int z = -1; z <= 1; z++) {
                BlockPos p = center.offset(x, 0, z);
                level.setBlock(p, ModBlocks.FARTHEST_PORTAL.defaultBlockState(), 3);
            }
        }
    }
}
