package io.github.insomniac.thefarthestlands.block.entity;

import net.minecraft.core.BlockPos;
import net.minecraft.world.level.block.entity.TheEndPortalBlockEntity;
import net.minecraft.world.level.block.state.BlockState;

/**
 * Block entity for the Farthest Portal interior.
 * Extends vanilla's End Portal BE so the client renderer can reuse End Portal geometry.
 */
public class FarthestPortalBlockEntity extends TheEndPortalBlockEntity {
    public FarthestPortalBlockEntity(BlockPos pos, BlockState state) {
        super(ModBlockEntities.FARTHEST_PORTAL, pos, state);
    }
}
