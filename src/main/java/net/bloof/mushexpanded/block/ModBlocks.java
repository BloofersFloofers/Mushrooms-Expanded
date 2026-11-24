package net.bloof.mushexpanded.block;

import net.bloof.mushexpanded.MushExpanded;
import net.bloof.mushexpanded.block.custom.MushroomCrop;
import net.bloof.mushexpanded.block.custom.MyceliumBlock;
import net.bloof.mushexpanded.block.custom.RedAlgaeCrop;
import net.bloof.mushexpanded.item.ModItems;
import net.bloof.mushexpanded.block.custom.AgarBlock;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.material.PushReaction;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredBlock;
import net.neoforged.neoforge.registries.DeferredRegister;

import java.util.function.Supplier;

public class ModBlocks {
    public static final DeferredRegister.Blocks BLOCKS = DeferredRegister.createBlocks(MushExpanded.MOD_ID);

    public static final DeferredBlock<MushroomCrop> PORTABELLA_MUSHROOM = registerBlock("portabella_mushroom",
            () -> new MushroomCrop(BlockBehaviour.Properties.of()
                    .instabreak()
                    .noCollission()
                    .pushReaction(PushReaction.DESTROY)
                    .randomTicks()
                    .offsetType(BlockBehaviour.OffsetType.XZ)
                    .dynamicShape()), false);

    public static final DeferredBlock<MyceliumBlock> PORTABELLA_MYCELIUM = registerBlock("portabella_mycelium",
            () -> new MyceliumBlock(BlockBehaviour.Properties.of()
                    .strength(4f)
                    .sound(SoundType.ROOTED_DIRT)
                    .randomTicks(),
                    PORTABELLA_MUSHROOM), true);

    public static final DeferredBlock<RedAlgaeCrop> RED_ALGAE = registerBlock("red_algae",
            () -> new RedAlgaeCrop(BlockBehaviour.Properties.of().instabreak()
                    .noCollission()
                    .pushReaction(PushReaction.DESTROY)
                    .sound(SoundType.WET_GRASS)
            ), true);

    public static final DeferredBlock<AgarBlock> AGAR_BLOCK = registerBlock("agar_block",
            () -> new AgarBlock(BlockBehaviour.Properties.of()
                    .strength(1f)
                    .sound(SoundType.HONEY_BLOCK)
                    .noOcclusion()
                    .speedFactor(0.5F)
                    .jumpFactor(0.5F)), true);

    private static <T extends Block> DeferredBlock<T> registerBlock(String name, Supplier<T> block, boolean registerBlockItem) {
        DeferredBlock<T> toReturn = BLOCKS.register(name, block);
        if (registerBlockItem) {
            registerBlockItem(name, toReturn);
        }

        return toReturn;
    }

    private static <T extends Block> void registerBlockItem(String name, DeferredBlock<T> block) {
        ModItems.ITEMS.register(name, () -> new BlockItem(block.get(), new Item.Properties()));
    }


    public static void register(IEventBus eventBus) {
        BLOCKS.register(eventBus);
    }

}
