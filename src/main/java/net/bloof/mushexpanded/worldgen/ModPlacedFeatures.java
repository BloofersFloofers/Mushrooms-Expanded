package net.bloof.mushexpanded.worldgen;

import net.bloof.mushexpanded.MushExpanded;
import net.minecraft.core.Holder;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.worldgen.BootstrapContext;
import net.minecraft.data.worldgen.placement.PlacementUtils;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.valueproviders.ConstantInt;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.levelgen.blockpredicates.BlockPredicate;
import net.minecraft.world.level.levelgen.feature.ConfiguredFeature;
import net.minecraft.world.level.levelgen.feature.SeagrassFeature;
import net.minecraft.world.level.levelgen.placement.*;

import java.util.List;

public class ModPlacedFeatures {

    public static final ResourceKey<PlacedFeature> PORTABELLA_MYCELIUM_PLACED_KEY = registerKey("portabella_mycelium_placed");
    public static final ResourceKey<PlacedFeature> RED_ALGAE_PLACED_KEY = registerKey("red_algae_placed");


    public static void bootstrap(BootstrapContext<PlacedFeature> context) {
        var configuredFeatures = context.lookup(Registries.CONFIGURED_FEATURE);
        register(context, PORTABELLA_MYCELIUM_PLACED_KEY, configuredFeatures.getOrThrow(ModConfiguredFeatures.PORTABELLA_MYCELIUM_KEY),
                List.of(RarityFilter.onAverageOnceEvery(48),
                        InSquarePlacement.spread(),
                        PlacementUtils.HEIGHTMAP_WORLD_SURFACE,
                        CountPlacement.of(10),
                        RandomOffsetPlacement.horizontal(ConstantInt.of(-1)),
                        BiomeFilter.biome()));
        register(context, RED_ALGAE_PLACED_KEY, configuredFeatures.getOrThrow(ModConfiguredFeatures.RED_ALGAE_KEY),
                List.of(RarityFilter.onAverageOnceEvery(1),
                        InSquarePlacement.spread(),
                        PlacementUtils.HEIGHTMAP_TOP_SOLID,
                        CountPlacement.of(5),
                        RandomOffsetPlacement.horizontal(ConstantInt.of(-1)),
                        BiomeFilter.biome()));
    }

    private static ResourceKey<PlacedFeature> registerKey(String name) {
        return ResourceKey.create(Registries.PLACED_FEATURE, ResourceLocation.fromNamespaceAndPath(MushExpanded.MOD_ID, name));
    }

    private static void register(BootstrapContext<PlacedFeature> context, ResourceKey<PlacedFeature> key, Holder<ConfiguredFeature<?, ?>> configuration,
                                 List<PlacementModifier> modifiers) {
        context.register(key, new PlacedFeature(configuration, List.copyOf(modifiers)));
    }
}