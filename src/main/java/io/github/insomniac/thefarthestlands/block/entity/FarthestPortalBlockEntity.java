package io.github.insomniac.thefarthestlands.block.entity;

import net.minecraft.core.BlockPos;
import net.minecraft.world.level.block.entity.TheEndPortalBlockEntity;
import net.minecraft.world.level.block.state.BlockState;

/** Block entity so the client can draw this portal with the End Portal renderer/shader. */
public class FarthestPortalBlockEntity extends TheEndPortalBlockEntity {
    public FarthestPortalBlockEntity(BlockPos pos, BlockState state) {
        super(ModBlockEntities.FARTHEST_PORTAL, pos, state);
    }
}
