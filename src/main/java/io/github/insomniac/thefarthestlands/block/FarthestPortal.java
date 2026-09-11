package io.github.insomniac.thefarthestlands.block;

import com.mojang.serialization.MapCodec;
import io.github.insomniac.thefarthestlands.block.entity.FarthestPortalBlockEntity;
import io.github.insomniac.thefarthestlands.world.ModDimensions;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.BaseEntityBlock;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;

import java.awt.*;
import java.util.Set;

/**
 * Portal block that teleports into the Farthest Lands.
 *
 * The in-world look is not the cube JSON model. It is drawn like a vanilla End Portal
 * by the client {@code FarthestPortalRenderer}, which uses the grayscale core shader:
 * {@code assets/thefarthestlands/shaders/core/rendertype_farthest_portal.*}
 */
public class FarthestPortal extends BaseEntityBlock {
    public static final MapCodec<FarthestPortal> CODEC = simpleCodec(FarthestPortal::new);
    private static final VoxelShape SHAPE = Block.box(0.0, 6.0, 0.0, 16.0, 12.0, 16.0);

    public FarthestPortal(Properties properties) { super(properties); }

    @Override
    protected MapCodec<? extends BaseEntityBlock> codec() { return CODEC; }

    @Override
    public BlockEntity newBlockEntity(BlockPos pos, BlockState state) {
        return new FarthestPortalBlockEntity(pos, state); }

    @Override
    protected VoxelShape getShape(BlockState state, BlockGetter level,
                                  BlockPos pos, CollisionContext context) {
        return SHAPE; }

    @Override
    protected void entityInside(BlockState state, Level level, BlockPos pos, Entity entity) {
        if (!level.isClientSide && entity instanceof ServerPlayer player) {
            // In 1.21.1, check if the player is currently NOT changing dimensions
            if (!player.isChangingDimension()) { performTeleport(player); }
        }}

    private void performTeleport(ServerPlayer player) {
        ServerLevel destination = player.server.getLevel(ModDimensions.FARTHEST_LANDS_LEVEL_KEY);
        if (destination != null) {
            BlockPos arrivalPos = new BlockPos(0, 100, 0);
            // Generate the obi platform
            /**
            for (int x = -2; x <= 2; x++) {
                for (int z = -2; z <= 2; z++) {
                    destination.setBlock(arrivalPos.offset(x, -1, z), Blocks.OBSIDIAN.defaultBlockState(), 3);
                }
            }
             */
            //TODO: Destroy all items as soon as you enter, save a few exceptions
            //player.getItemBySlot().enchantments().clear();
//            for (int i = 1; i <= EquipmentSlot.; i++) {
//                player.getItemBySlot(i).enchantments().clear();
//            }
            // Using the 1.21.1 teleportTo method
            // The empty set represents 'RelativeArguments' (none in this case)
            //TODO: change the spawn
            player.teleportTo(
                    destination,
                    0.5, 70.0, 0.5,
                    Set.of(),
                    0.0f, 0.0f
            );
            //player.setRespawnPosition(ModDimensions.THY, arrivalPos, 0.0f, true);
        }
    }
}
