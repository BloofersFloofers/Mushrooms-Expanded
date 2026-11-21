package net.bloof.mushexpanded.datagen;

import com.mojang.datafixers.types.Func;
import net.bloof.mushexpanded.MushExpanded;
import net.bloof.mushexpanded.block.ModBlocks;
import net.bloof.mushexpanded.block.custom.MushroomCrop;
import net.bloof.mushexpanded.block.custom.MyceliumBlock;
import net.minecraft.data.PackOutput;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.block.MushroomBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.neoforged.neoforge.client.model.generators.BlockStateProvider;
import net.neoforged.neoforge.client.model.generators.ConfiguredModel;
import net.neoforged.neoforge.common.data.ExistingFileHelper;
import net.neoforged.neoforge.registries.DeferredBlock;

import java.util.function.Function;

public class ModBlockStateProvider extends BlockStateProvider {
    public ModBlockStateProvider(PackOutput output, ExistingFileHelper exFileHelper) {
        super(output, MushExpanded.MOD_ID, exFileHelper);
    }

    @Override
    protected void registerStatesAndModels() {
        makeMyceliumBlock(ModBlocks.PORTABELLA_MYCELIUM.get(), "portabella_mycelium", "portabella_mycelium");
        makeMushroomCrop(ModBlocks.PORTABELLA_MUSHROOM.get(), "portabella_stage", "portabella");
    }

    public void makeMyceliumBlock(MyceliumBlock block, String modelName, String textureName) {
        Function<BlockState, ConfiguredModel[]> function = state -> statesMycelium(state, block, modelName, textureName);

        getVariantBuilder(block).forAllStates(function);
    }

    private ConfiguredModel[] statesMycelium(BlockState state, MyceliumBlock block, String modelName, String textureName) {
        ConfiguredModel[] models = new ConfiguredModel[1];
        models[0] = new ConfiguredModel(models().cubeAll(modelName + state.getValue(((MyceliumBlock) block).getMoistureProperty()),
                ResourceLocation.fromNamespaceAndPath(MushExpanded.MOD_ID, "block/mycelium/" + textureName + state.getValue(((MyceliumBlock) block).getMoistureProperty()))).renderType("cutout"));
        return models;
    }

    public void makeMushroomCrop(MushroomCrop block, String modelName, String textureName) {
        Function<BlockState, ConfiguredModel[]> function = state -> stateMushroom(state, block, modelName, textureName);

        getVariantBuilder(block).forAllStates(function);
    }

    private ConfiguredModel[] stateMushroom(BlockState state, MushroomCrop block, String modelName, String textureName) {
        ConfiguredModel[] models = new ConfiguredModel[1];
        models[0] = new ConfiguredModel(models().cross(modelName + state.getValue(((MushroomCrop) block).getAgeProperty()),
                ResourceLocation.fromNamespaceAndPath(MushExpanded.MOD_ID, "block/crop/" + textureName + state.getValue(((MushroomCrop) block).getAgeProperty()))).renderType("cutout"));
        return models;
    }

    private void blockWithItem(DeferredBlock<?> deferredBlock) {
        simpleBlockWithItem(deferredBlock.get(), cubeAll(deferredBlock.get()));
    }
}
