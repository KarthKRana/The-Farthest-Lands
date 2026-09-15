package io.github.insomniac.thefarthestlands.item;

import io.github.insomniac.thefarthestlands.advancement.ModCriteria;
import io.github.insomniac.thefarthestlands.block.FarthestPortalFrameBlock;
import io.github.insomniac.thefarthestlands.block.ModBlocks;
import io.github.insomniac.thefarthestlands.sound.ModSounds;
import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.DustParticleOptions;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import org.joml.Vector3f;

/**
 * Right-click item that fills End Portal Frames with a Gazing Eye.
 * When 12 filled custom frames surround a 3x3 hole, it lights a Farthest Portal.
 */
public class GazingEyeItem extends Item {
    /** Set when a Farthest Portal successfully lights (currently unused by other classes). */
    public static boolean farPortalActivated = false;
    /** Dark-gray dust burst used on place and when the portal activates. */
    private static final DustParticleOptions DARK_GRAY_DUST =
            new DustParticleOptions(new Vector3f(0.22f, 0.22f, 0.22f), 1.2f);

    public GazingEyeItem(Properties properties) { super(properties); }

    /** Handles placing a Gazing Eye into a portal frame (Overworld only). */
    @Override
    public InteractionResult useOn(UseOnContext context) {
        Level level = context.getLevel();
        BlockPos pos = context.getClickedPos();
        BlockState state = level.getBlockState(pos);
        // Only allow portal to open in the Overworld
        if (level.dimension() != Level.OVERWORLD) { return InteractionResult.PASS; }
        if (canInsertGazingEye(state)) {
            if (!level.isClientSide) {
                ServerLevel serverLevel = (ServerLevel) level;
                // Swap vanilla/custom frame for a filled farthest-portal-frame (keeps facing).
                serverLevel.setBlock(pos, FarthestPortalFrameBlock.withGazingEye(state), 3);
                context.getItemInHand().shrink(1);
                // Same fill sound as placing a vanilla Eye of Ender in a frame.
                serverLevel.playSound(null, pos, SoundEvents.END_PORTAL_FRAME_FILL, SoundSource.BLOCKS, 1.0F, 1.0F);
                spawnGazingEyeParticles(serverLevel, pos);
                if (checkPortalStructure(serverLevel, pos)) {
                    farPortalActivated = true;
                    // Grants the hidden Story advancement "The End of it All".
                    ModCriteria.THE_END_OF_IT_ALL.trigger((ServerPlayer) context.getPlayer());
                }
            }
            return InteractionResult.SUCCESS;
        }
        return InteractionResult.PASS;
    }

    //particles that appear when I place the Gazing Eye in the frame
    private void spawnGazingEyeParticles(ServerLevel level, BlockPos pos) {
        double x = pos.getX() + 0.5;
        double y = pos.getY() + 0.95;
        double z = pos.getZ() + 0.5;
        level.sendParticles(DARK_GRAY_DUST, x, y, z, 24, 0.28, 0.18, 0.28, 0.02);
    }

    /** True if the clicked block is a vanilla or custom portal frame. */
    private boolean canInsertGazingEye(BlockState state) {
        if (!state.is(Blocks.END_PORTAL_FRAME) && !state.is(ModBlocks.FARTHEST_PORTAL_FRAME)) {
            return false;
        }
        return true;
        // --Gazing Eyes can replace Eyes of Ender because the chance of an end portal having no eyes is low
    }

    /**
     * Scans a 10-block radius around the placed eye.
     * If 12 filled frames are found, the portal is considered complete.
     */
    private boolean checkPortalStructure(ServerLevel level, BlockPos clickedPos) {
        int eyeCount = 0;
        int radius = 5;
        // 1. Count the eyes (standard check)
        for (BlockPos pos : BlockPos.betweenClosed(
                clickedPos.offset(-radius, -1, -radius), clickedPos.offset(radius, 1, radius))) {
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
                        level.playSound(null, potentialCenter,
                                ModSounds.FARTHEST_PORTAL_LIT,
                                SoundSource.BLOCKS, 1.0F, 1.0F); //The BOOM sfx
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

    /** Replaces leftover vanilla frames, fills the 3x3 with portal blocks, and bursts particles. */
    private void activateFarthestPortal(ServerLevel level, BlockPos center) {
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
        // Burst dark-gray dust from every filled frame in the ring.
        for (BlockPos pos : BlockPos.betweenClosed(center.offset(-3, 0, -3), center.offset(3, 0, 3))) {
            if (FarthestPortalFrameBlock.isFilled(level.getBlockState(pos))) {
                spawnGazingEyeParticles(level, pos.immutable());
            }
        }
    }
}
