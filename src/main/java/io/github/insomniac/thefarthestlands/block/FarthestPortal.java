package io.github.insomniac.thefarthestlands.block;

import com.mojang.serialization.MapCodec;
import io.github.insomniac.thefarthestlands.TheFarthestLands;
import io.github.insomniac.thefarthestlands.block.entity.FarthestPortalBlockEntity;
import io.github.insomniac.thefarthestlands.world.ModDimensions;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.BaseEntityBlock;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;

import java.util.Set;

/**
 * Portal block that teleports into the Farthest Lands.
 *
 * The in-world look is not the cube JSON model. It is drawn like a vanilla End Portal
 * by the client {@code FarthestPortalRenderer}, which uses the grayscale core shader:
 * {@code assets/thefarthestlands/shaders/core/rendertype_farthest_portal.*}
 */
public class FarthestPortal extends BaseEntityBlock {
    private static boolean clearInventory = true; //Clear the inventory
    public static final MapCodec<FarthestPortal> CODEC = simpleCodec(FarthestPortal::new);
    /** Same slab height as vanilla end portal (y 6–12 in pixel space) so you fall into it. */
    private static final VoxelShape SHAPE = Block.box(0.0, 6.0, 0.0, 16.0, 12.0, 16.0);

    public FarthestPortal(Properties properties) { super(properties); }

    @Override
    protected MapCodec<? extends BaseEntityBlock> codec() { return CODEC; }

    /** Spawns the block entity the client renderer uses for the swirling portal effect. */
    @Override
    public BlockEntity newBlockEntity(BlockPos pos, BlockState state) {
        return new FarthestPortalBlockEntity(pos, state); }

    @Override
    protected VoxelShape getShape(BlockState state, BlockGetter level,
                                  BlockPos pos, CollisionContext context) {
        return SHAPE; }

    /** Break / mine dust should look like obsidian, not the portal cube texture. */
    @Override
    protected void spawnDestroyParticles(Level level, Player player, BlockPos pos, BlockState state) {
        level.levelEvent(player, 2001, pos, Block.getId(Blocks.OBSIDIAN.defaultBlockState()));
    }

    /** Called every tick an entity's hitbox overlaps this block. */
    @Override
    protected void entityInside(BlockState state, Level level, BlockPos pos, Entity entity) {
        if (level.isClientSide) {
            // Cover the dimension load in black before the integrated server teleports.
            if (entity instanceof Player) {
                TheFarthestLands.startEntryFade.run();
            }
            return;
        }
        if (entity instanceof ServerPlayer player && !player.isChangingDimension()) {
            performTeleport(player);
        }
    }

    /** Moves the player into {@code farthest_lands} at a high Y. */
    private void performTeleport(ServerPlayer player) {
        ServerLevel destination = player.server.getLevel(ModDimensions.FARTHEST_LANDS_LEVEL_KEY);
        if (destination != null) {
            //Make the player temporarily invincible so they don't die to fall damage
            player.addEffect(new MobEffectInstance(MobEffects.DAMAGE_RESISTANCE,
                    200,
                    99,
                    false,
                    false));

            //Clear all items as soon as you enter, save a few exceptions
            if (clearInventory) { player.getInventory().clearContent(); }

            // Using the 1.21.1 teleportTo method
            // The empty set represents 'RelativeArguments' (none in this case)
            player.teleportTo(
                    destination,
                    0.0, 170.0, 0.0,
                    Set.of(),
                    0.0f, 0.0f
            );
            //player.setRespawnPosition(ModDimensions.THY, arrivalPos, 0.0f, true);
        }
    }
}
