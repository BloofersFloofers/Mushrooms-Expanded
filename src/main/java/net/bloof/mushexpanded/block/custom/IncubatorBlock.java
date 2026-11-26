package net.bloof.mushexpanded.block.custom;


import com.mojang.logging.LogUtils;
import com.mojang.serialization.MapCodec;
import net.bloof.mushexpanded.block.entity.IncubatorBlockEntity;
import net.bloof.mushexpanded.block.entity.ModBlockEntities;
import net.bloof.mushexpanded.component.ModDataComponents;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.util.RandomSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.ItemInteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.block.BaseEntityBlock;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.DirectionalBlock;
import net.minecraft.world.level.block.RenderShape;
import net.minecraft.world.level.block.entity.BeehiveBlockEntity;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityTicker;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.DirectionProperty;
import net.minecraft.world.level.block.state.properties.Property;
import net.minecraft.world.phys.BlockHitResult;
import org.jetbrains.annotations.Nullable;


public class IncubatorBlock extends BaseEntityBlock {
    public static final MapCodec<IncubatorBlock> CODEC = simpleCodec(IncubatorBlock::new);
    public static final DirectionProperty FACING;

    public IncubatorBlock(Properties properties) {
        super(properties);
    }

    public Property getFacingProperty() {
        return FACING;
    }

    @Override
    protected MapCodec<? extends BaseEntityBlock> codec() {
        return CODEC;
    }

    @Override
    protected RenderShape getRenderShape(BlockState state) {
        return RenderShape.MODEL;
    }


    @Override
    public @Nullable <T extends BlockEntity> BlockEntityTicker<T> getTicker(Level level, BlockState state, BlockEntityType<T> blockEntityType) {
        return level.isClientSide ? null : createTickerHelper(blockEntityType, ModBlockEntities.INCUBATOR_BE.get(), IncubatorBlockEntity::serverTick);
    }


    @Override
    protected void onRemove(BlockState state, Level level, BlockPos pos, BlockState newState, boolean movedByPiston) {
        if (state.getBlock() != newState.getBlock()) {
            if (level.getBlockEntity(pos) instanceof IncubatorBlockEntity incubatorBlockEntity) {
                incubatorBlockEntity.drops();
                level.updateNeighbourForOutputSignal(pos, this);
                incubatorBlockEntity.setRemoved();
            }
        }
    }

    @Override
    protected ItemInteractionResult useItemOn(ItemStack stack, BlockState state, Level level, BlockPos pos, Player player, InteractionHand hand, BlockHitResult hitResult) {

        if (level.getBlockEntity(pos) instanceof IncubatorBlockEntity incubatorBlockEntity) {
            if (!stack.isEmpty()) {
                for(int i = 0; i < incubatorBlockEntity.inventory.getSlots(); ++i) {
                    if (incubatorBlockEntity.inventory.getStackInSlot(i).isEmpty()) {
                        incubatorBlockEntity.inventory.insertItem(i, stack.copy(), false);
                        stack.shrink(1);
                        level.playSound(player, pos, SoundEvents.ITEM_PICKUP, SoundSource.BLOCKS, 1f, 2f);
                        return ItemInteractionResult.SUCCESS;
                    }
                }
            } else {
                for(int i = incubatorBlockEntity.inventory.getSlots()-1; i > -1; --i) {
                    if (!incubatorBlockEntity.inventory.getStackInSlot(i).isEmpty()) {
                        ItemStack item = incubatorBlockEntity.inventory.extractItem(i, 1, false);
                        player.setItemInHand(InteractionHand.MAIN_HAND, item);
                        //incubatorBlockEntity.clearSlot(i);
                        level.playSound(player, pos, SoundEvents.ITEM_PICKUP, SoundSource.BLOCKS, 1f, 1f);
                        return ItemInteractionResult.SUCCESS;
                    }
                }
            }
        }
        return ItemInteractionResult.FAIL;
    }

    @Override
    public @Nullable BlockEntity newBlockEntity(BlockPos blockPos, BlockState blockState) {
        return new IncubatorBlockEntity(blockPos, blockState);
    }

    @Override
    public BlockState getStateForPlacement(BlockPlaceContext context) {
        return this.defaultBlockState().setValue(FACING, context.getHorizontalDirection().getOpposite());
    }

    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
        builder.add(FACING);
    }

    static {
        FACING = DirectionalBlock.FACING;
    }
}
