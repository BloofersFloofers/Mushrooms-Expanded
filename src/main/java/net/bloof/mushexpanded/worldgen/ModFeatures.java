package net.bloof.mushexpanded.worldgen;

import net.bloof.mushexpanded.MushExpanded;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.world.level.levelgen.feature.Feature;
import net.minecraft.world.level.levelgen.feature.configurations.NoneFeatureConfiguration;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;

public class ModFeatures {
    public static final DeferredRegister<Feature<?>> FEATURES =
            DeferredRegister.create(BuiltInRegistries.FEATURE, MushExpanded.MOD_ID);

    public static final DeferredHolder<Feature<?>, MyceliumPatchFeature> MYCELIUM_PATCH =
            FEATURES.register("mycelium_patch",
                    () -> new MyceliumPatchFeature(MyceliumClassConfiguration.CODEC));

    public static final DeferredHolder<Feature<?>, RedAlgaeFeature> RED_ALGAE_FEATURE =
            FEATURES.register("red_algae_feature",
                    () -> new RedAlgaeFeature(NoneFeatureConfiguration.CODEC));


    public static void register(IEventBus eventBus) {
        FEATURES.register(eventBus);
    }
}