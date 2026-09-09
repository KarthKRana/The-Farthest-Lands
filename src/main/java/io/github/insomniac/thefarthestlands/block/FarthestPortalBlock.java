package io.github.insomniac.thefarthestlands.block;

import io.github.insomniac.thefarthestlands.world.ModDimensions;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import java.util.Set;

public class FarthestPortalBlock extends Block {
    public FarthestPortalBlock(Properties properties) { super(properties); }

    @Override
    public void entityInside(BlockState state, Level level, BlockPos pos, Entity entity) {
        if (!level.isClientSide && entity instanceof ServerPlayer player) {
            // In 1.21.1, check if the player is currently NOT changing dimensions
            if (!player.isChangingDimension()) { performTeleport(player); }
        }
    }

    private void performTeleport(ServerPlayer player) {
        ServerLevel destination = player.server.getLevel(ModDimensions.FARTHEST_LANDS_LEVEL_KEY);
        if (destination != null) {
            BlockPos arrivalPos = new BlockPos(0, 100, 0);
            // Generate the obi platform
            for (int x = -2; x <= 2; x++) {
                for (int z = -2; z <= 2; z++) {
                    destination.setBlock(arrivalPos.offset(x, -1, z), Blocks.OBSIDIAN.defaultBlockState(), 3);
                }
            }
            //TODO: Destroy all enchants on all items as soon as you enter
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
        }
    }
}