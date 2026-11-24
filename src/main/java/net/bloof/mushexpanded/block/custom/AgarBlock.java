package net.bloof.mushexpanded.block.custom;

import net.minecraft.core.BlockPos;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;

public class AgarBlock extends Block {

    public AgarBlock(Properties properties) {
        super(properties);
    }

    protected static final VoxelShape SHAPE = Block.box(3.0F, 0.0F, 3.0F, 13.0F, 13.0F, 13.0F);

    @Override
    protected VoxelShape getCollisionShape(BlockState state, BlockGetter level, BlockPos pos, CollisionContext context) {
        return SHAPE;
    }
}
