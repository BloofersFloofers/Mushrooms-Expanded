package net.bloof.mushexpanded.worldgen;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.util.ExtraCodecs;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.levelgen.blockpredicates.BlockPredicate;
import net.minecraft.world.level.levelgen.feature.configurations.FeatureConfiguration;
import net.minecraft.world.level.levelgen.feature.stateproviders.RuleBasedBlockStateProvider;


public record MyceliumClassConfiguration(int tries, int xzSpread, BlockState state) implements FeatureConfiguration {
    public static final Codec<MyceliumClassConfiguration> CODEC = RecordCodecBuilder
            .create((instance) -> instance.group(
                    ExtraCodecs.POSITIVE_INT.fieldOf("tries").orElse(128).forGetter(MyceliumClassConfiguration::tries),
                    ExtraCodecs.NON_NEGATIVE_INT.fieldOf("xz_spread").orElse(7).forGetter(MyceliumClassConfiguration::xzSpread),
                            BlockState.CODEC.fieldOf("state").forGetter(MyceliumClassConfiguration::state))
                            .apply(instance, MyceliumClassConfiguration::new));

    public MyceliumClassConfiguration(int tries, int xzSpread, BlockState state) {
        this.state = state;
        this.tries = tries;
        this.xzSpread = xzSpread;
    }

    public BlockState state() {return this.state;}

    public int tries() {
        return this.tries;
    }

    public int xzSpread() {
        return this.xzSpread;
    }


}
