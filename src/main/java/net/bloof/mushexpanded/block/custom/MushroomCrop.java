package net.bloof.mushexpanded.block.custom;

import net.bloof.mushexpanded.block.ModBlocks;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.IntegerProperty;
import net.minecraft.world.level.block.state.properties.Property;
import net.minecraft.world.phys.Vec3;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;

public class MushroomCrop extends Block {
    public static final int MAX_AGE = 3;
    public static final IntegerProperty AGE;
    private static final VoxelShape[] SHAPE_BY_AGE;
    public MushroomCrop(Properties properties) {
        super(properties);
        this.registerDefaultState((stateDefinition.any()).setValue(AGE, 0));
    }


    public IntegerProperty getAgeProperty() {
        return AGE;
    }

    @Override
    protected VoxelShape getShape(BlockState state, BlockGetter level, BlockPos pos, CollisionContext context) {
        Vec3 vec3 = state.getOffset(level, pos);
        return SHAPE_BY_AGE[getAge(state)].move(vec3.x, vec3.y, vec3.z);
    }

    public int getAge(BlockState state) {
        return state.getValue(AGE);
    }

    protected void randomTick(BlockState state, ServerLevel level, BlockPos pos, RandomSource random) {
        if (state.getValue(AGE) < MAX_AGE) {
            int rand = random.nextInt(6);
            if (rand == 1) {
                level.setBlock(pos, defaultBlockState().setValue(AGE, getAge(state) + 1), 2);
            }
        }
    }


    @Override
    protected BlockState updateShape(BlockState state, Direction facing, BlockState neighborState, LevelAccessor level, BlockPos pos, BlockPos neighborPos) {
        boolean survival = canSurvive(state, level, pos);
        return !survival ? Blocks.AIR.defaultBlockState() : super.updateShape(state, facing, neighborState, level, pos, neighborPos);
    }


    protected boolean mayPlaceOn(BlockGetter level, BlockPos pos) {
        return level.getBlockState(pos.below()).is(ModBlocks.PORTABELLA_MYCELIUM);
    }

    protected boolean canSurvive(BlockState state, LevelReader level, BlockPos pos) {
        BlockPos blockPos = pos.below();
        boolean airUnder = level.isEmptyBlock(blockPos);
        boolean canPlace = mayPlaceOn(level, pos);
        return !airUnder & canPlace;
    }

    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
        builder.add(AGE);
    }

    static {
        AGE = BlockStateProperties.AGE_3;
        SHAPE_BY_AGE = new VoxelShape[]{
                Block.box(6, 0, 6, 10,  4, 10),  // age 0
                Block.box(5, 0, 5, 11,  6, 11),  // age 1
                Block.box(4, 0, 4, 12,  8, 12),  // age 2
                Block.box(3, 0, 3, 13,  8, 13),  // age 3
        };
    }

}
