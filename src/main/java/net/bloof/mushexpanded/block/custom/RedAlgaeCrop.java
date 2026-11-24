package net.bloof.mushexpanded.block.custom;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.tags.FluidTags;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.LiquidBlockContainer;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.material.Fluid;
import net.minecraft.world.level.material.FluidState;
import net.minecraft.world.level.material.Fluids;
import org.jetbrains.annotations.Nullable;

public class RedAlgaeCrop extends Block implements LiquidBlockContainer {

    public RedAlgaeCrop(Properties properties) {
        super(properties);
    }

    protected boolean mayPlaceOn(BlockGetter level, BlockPos pos) {
        BlockPos blockPos = pos.below();
        return level.getBlockState(blockPos).is(Blocks.SAND) || level.getBlockState(blockPos).is(Blocks.DIRT) || level.getBlockState(blockPos).is(Blocks.GRAVEL) &&
                !level.getBlockState(blockPos).is(this);
    }

    protected boolean canSurvive(BlockState state, LevelReader level, BlockPos pos) {
        boolean canSurvive = true;
        if (!mayPlaceOn(level, pos)) {
            canSurvive = false;
        }
        return canSurvive;
    }

    @Override
    protected BlockState updateShape(BlockState state, Direction direction, BlockState blockState, LevelAccessor level, BlockPos blockPos, BlockPos neighborPos) {
        BlockState blockstate = super.updateShape(state, direction, blockState, level, blockPos, neighborPos);
        if (!blockstate.isAir()) {
            level.scheduleTick(blockPos, Fluids.WATER, Fluids.WATER.getTickDelay(level));
        }

        return !canSurvive(state, level, blockPos) ? Blocks.AIR.defaultBlockState() : super.updateShape(state, direction, blockState, level, blockPos, neighborPos);
    }

    @Override
    public BlockState getStateForPlacement(BlockPlaceContext context) {
        FluidState fluidstate = context.getLevel().getFluidState(context.getClickedPos());
        return fluidstate.is(FluidTags.WATER) && fluidstate.getAmount() == 8 ? super.getStateForPlacement(context) : null;
    }

    @Override
    protected FluidState getFluidState(BlockState p_154537_) {
        return Fluids.WATER.getSource(false);
    }


    @Override
    public boolean canPlaceLiquid(@Nullable Player player, BlockGetter blockGetter, BlockPos blockPos, BlockState blockState, Fluid fluid) {
        return false;
    }

    @Override
    public boolean placeLiquid(LevelAccessor levelAccessor, BlockPos blockPos, BlockState blockState, FluidState fluidState) {
        return false;
    }
}
