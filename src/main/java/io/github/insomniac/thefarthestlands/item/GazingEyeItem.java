package io.github.insomniac.thefarthestlands.item;

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

        if (state.is(Blocks.END_PORTAL_FRAME)) {
            if (!state.getValue(EndPortalFrameBlock.HAS_EYE)) {
                if (!level.isClientSide) {
                    level.setBlock(pos, state.setValue(EndPortalFrameBlock.HAS_EYE, true), 3);
                    context.getItemInHand().shrink(1);
                    level.playSound(null, pos, ModSounds.GAZING_EYE_PLACED_ON_PORTAL_FRAME, SoundSource.BLOCKS, 1.0F, 1.0F);
                    if (checkPortalStructure(level, pos)) {
                        farPortalActivated = true;
                    }
                }
                return InteractionResult.SUCCESS;
            }
        }
        return InteractionResult.PASS;
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
            BlockState state = level.getBlockState(pos);
            if (state.is(Blocks.END_PORTAL_FRAME) && state.getValue(EndPortalFrameBlock.HAS_EYE)) {
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
                        return true;
                    }
                }
            }
        }
        return false;
    }

    // Helper to find the actual 3x3 hole
    private boolean isCorrectPortalCenter(Level level, BlockPos pos) {
        // Check if a 3x3 area is all air/replaceable
        for (int x = -1; x <= 1; x++) {
            for (int z = -1; z <= 1; z++) {
                if (!level.getBlockState(pos.offset(x, 0, z)).isAir()) { return false; }
            }
        }
        // Check if there's a frame at least 2 blocks away to verify it's the portal
        return level.getBlockState(pos.west(2)).is(Blocks.END_PORTAL_FRAME) ||
                level.getBlockState(pos.east(2)).is(Blocks.END_PORTAL_FRAME);
    }

//    private boolean isFrameBorder(Level level, BlockPos center) {
//        // Check if the blocks are ± 2 blocks away are portal frames
//        return level.getBlockState(center.west(2)).is(Blocks.END_PORTAL_FRAME) ||
//                level.getBlockState(center.east(2)).is(Blocks.END_PORTAL_FRAME);
//    }

    private void activateFarthestPortal(Level level, BlockPos center) {
        // Flag '3' means: Update the block + Send to clients + Re-render
        // 1. Swap Frames
        for (BlockPos pos : BlockPos.betweenClosed(center.offset(-3, 0, -3), center.offset(3, 0, 3))) {
            if (level.getBlockState(pos).is(Blocks.END_PORTAL_FRAME)) {
                level.setBlock(pos, ModBlocks.FARTHEST_PORTAL_FRAME.defaultBlockState(), 3);
            }
        }
        // 2. Spawn Portal Blocks
        for (int x = -1; x <= 1; x++) {
            for (int z = -1; z <= 1; z++) {
                BlockPos p = center.offset(x, 0, z);
                level.setBlock(p, ModBlocks.FARTHEST_PORTAL.defaultBlockState(), 3);
            }
        }
    }
}