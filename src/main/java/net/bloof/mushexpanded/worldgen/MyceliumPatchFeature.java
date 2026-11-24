package net.bloof.mushexpanded.worldgen;

import com.mojang.serialization.Codec;
import net.bloof.mushexpanded.block.ModBlocks;
import net.minecraft.core.BlockPos;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.LevelWriter;
import net.minecraft.world.level.WorldGenLevel;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.levelgen.blockpredicates.BlockPredicate;
import net.minecraft.world.level.levelgen.feature.Feature;
import net.minecraft.world.level.levelgen.feature.FeaturePlaceContext;
import net.minecraft.world.level.levelgen.feature.configurations.NoneFeatureConfiguration;

public class MyceliumPatchFeature extends Feature<MyceliumClassConfiguration> {
    public MyceliumPatchFeature(Codec<MyceliumClassConfiguration> codec) {
        super(codec);
    }

    @Override
    public boolean place(FeaturePlaceContext<MyceliumClassConfiguration> context) {
        MyceliumClassConfiguration myceliumClassConfiguration = context.config();
        RandomSource randomSource = context.random();
        BlockPos blockPos = context.origin();
        WorldGenLevel level = context.level();

        int i = 0;
        BlockPos.MutableBlockPos mutableBlockPos = context.origin().mutable();
        int j = myceliumClassConfiguration.xzSpread();
        BlockState state = myceliumClassConfiguration.state();


        for(int l = 0; l < myceliumClassConfiguration.tries(); ++l) {
            mutableBlockPos.setWithOffset(blockPos, randomSource.nextInt(j)-randomSource.nextInt(j), 0, randomSource.nextInt(j)-randomSource.nextInt(j));
            BlockPos.MutableBlockPos ground = mutableBlockPos.below().mutable();

            if (isDirt(level.getBlockState(mutableBlockPos))) {
                level.setBlock(mutableBlockPos, state, 2);
                i++;
            }

        }

        return i>0;
    }
}
