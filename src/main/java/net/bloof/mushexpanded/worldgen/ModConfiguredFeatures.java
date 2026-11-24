package net.bloof.mushexpanded.worldgen;

import net.bloof.mushexpanded.MushExpanded;
import net.bloof.mushexpanded.block.ModBlocks;
import net.minecraft.core.Direction;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.worldgen.BootstrapContext;
import net.minecraft.data.worldgen.placement.PlacementUtils;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.valueproviders.UniformInt;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.levelgen.blockpredicates.BlockPredicate;
import net.minecraft.world.level.levelgen.feature.ConfiguredFeature;
import net.minecraft.world.level.levelgen.feature.Feature;
import net.minecraft.world.level.levelgen.feature.configurations.*;
import net.minecraft.world.level.levelgen.feature.stateproviders.BlockStateProvider;
import net.minecraft.world.level.levelgen.feature.stateproviders.RuleBasedBlockStateProvider;



import java.util.List;

public class ModConfiguredFeatures {
    public static final ResourceKey<ConfiguredFeature<?,?>> PORTABELLA_MYCELIUM_KEY = registerKey("portabella_mycelium");
    public static final ResourceKey<ConfiguredFeature<?,?>> RED_ALGAE_KEY = registerKey("red_algae");


    public static void bootstrap(BootstrapContext<ConfiguredFeature<?, ?>> context) {


        register(context, PORTABELLA_MYCELIUM_KEY, ModFeatures.MYCELIUM_PATCH.get(), new MyceliumClassConfiguration(6, 6, ModBlocks.PORTABELLA_MYCELIUM.get().defaultBlockState())
        );
        register(context, RED_ALGAE_KEY, ModFeatures.RED_ALGAE_FEATURE.get(), new NoneFeatureConfiguration());
    }

    public static ResourceKey<ConfiguredFeature<?, ?>> registerKey(String name) {
        return ResourceKey.create(Registries.CONFIGURED_FEATURE, ResourceLocation.fromNamespaceAndPath(MushExpanded.MOD_ID, name));
    }

    private static <FC extends FeatureConfiguration, F extends Feature<FC>> void register(BootstrapContext<ConfiguredFeature<?, ?>> context,
                                                                                          ResourceKey<ConfiguredFeature<?, ?>> key, F feature, FC configuration) {
        context.register(key, new ConfiguredFeature<>(feature, configuration));
    }
}