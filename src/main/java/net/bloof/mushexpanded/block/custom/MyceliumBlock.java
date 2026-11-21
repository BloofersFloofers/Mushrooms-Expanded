package net.bloof.mushexpanded.block.custom;

import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.IntegerProperty;


import java.util.function.Supplier;

public class MyceliumBlock extends Block {
    private final Supplier<MushroomCrop> growthBlock;
    public static final IntegerProperty WETNESS;
    public static final int maxMoisture = 3;
    public MyceliumBlock(Properties properties, Supplier<MushroomCrop> mushroomGrowth) {
        super(properties);
        this.growthBlock = mushroomGrowth;
        this.registerDefaultState((stateDefinition.any()).setValue(WETNESS, 0));
    }

    public int getMoisture(BlockState state) {
        return state.getValue(WETNESS);
    }
    public IntegerProperty getMoistureProperty() {
        return WETNESS;
    }

    @Override
    protected void randomTick(BlockState state, ServerLevel level, BlockPos pos, RandomSource random) {
        int rand;
        if (level.isRainingAt(pos.above())) {
            if (getMoisture(state) < maxMoisture) {
                level.setBlock(pos, defaultBlockState().setValue(WETNESS, getMoisture(state)+1), 2);
            }
        } else {
            rand = random.nextInt(2);
            if (getMoisture(state) > 0 & rand == 1) {
                level.setBlock(pos, defaultBlockState().setValue(WETNESS, getMoisture(state)-1), 2);
            }
        }
        rand = random.nextInt(10 - getMoisture(state)*2);
        if (getMoisture(state) == 0) {
            rand = 0;
        }
        if (level.isEmptyBlock(pos.above()) & rand == 1) {
            level.setBlockAndUpdate(pos.above(), growthBlock.get().defaultBlockState());

        }
    }

    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
        builder.add(WETNESS);
    }

    static {
        WETNESS = IntegerProperty.create("wetness", 0, 3);
    }
}
