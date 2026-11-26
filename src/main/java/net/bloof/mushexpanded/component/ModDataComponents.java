package net.bloof.mushexpanded.component;

import com.mojang.serialization.Codec;
import net.bloof.mushexpanded.MushExpanded;
import net.bloof.mushexpanded.block.ModBlocks;
import net.minecraft.core.component.DataComponentType;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.core.registries.Registries;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.world.level.block.Block;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;


import java.util.function.UnaryOperator;

public class ModDataComponents {
    public static final DeferredRegister<DataComponentType<?>> DATA_COMPONENT_TYPES =
            DeferredRegister.createDataComponents(Registries.DATA_COMPONENT_TYPE, MushExpanded.MOD_ID);

    public static final DeferredHolder<DataComponentType<?>, DataComponentType<Block>> FROM_MUSHROOM = register("from_mushroom",
            builder -> builder.persistent(BuiltInRegistries.BLOCK.byNameCodec()).networkSynchronized(ByteBufCodecs.registry(Registries.BLOCK)));

    public static final DeferredHolder<DataComponentType<?>, DataComponentType<Integer>> MYCELIUM_GROWTH = register("mycelium_growth",
            integerBuilder -> integerBuilder.networkSynchronized(ByteBufCodecs.VAR_INT).persistent(Codec.INT));



    private static <T>DeferredHolder<DataComponentType<?>, DataComponentType<T>> register(String name,
                                                                                          UnaryOperator<DataComponentType.Builder<T>> builderOperator) {
        return DATA_COMPONENT_TYPES.register(name, () -> builderOperator.apply(DataComponentType.builder()).build());
    }

    public static void register(IEventBus eventBus) {
        DATA_COMPONENT_TYPES.register(eventBus);
    }
}
