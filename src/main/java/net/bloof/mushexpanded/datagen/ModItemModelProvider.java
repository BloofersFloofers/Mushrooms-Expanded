package net.bloof.mushexpanded.datagen;

import net.bloof.mushexpanded.MushExpanded;
import net.bloof.mushexpanded.block.ModBlocks;
import net.bloof.mushexpanded.item.ModItems;
import net.minecraft.data.PackOutput;
import net.minecraft.resources.ResourceLocation;
import net.neoforged.neoforge.client.model.generators.ItemModelBuilder;
import net.neoforged.neoforge.client.model.generators.ItemModelProvider;
import net.neoforged.neoforge.client.model.generators.ModelFile;
import net.neoforged.neoforge.common.data.ExistingFileHelper;

public class ModItemModelProvider extends ItemModelProvider {

    public ModItemModelProvider(PackOutput output, ExistingFileHelper existingFileHelper) {
        super(output, MushExpanded.MOD_ID, existingFileHelper);
    }

    @Override
    protected void registerModels() {
        basicItem(ModItems.PORTABELLA_MUSHROOM.get());
        basicItem(ModItems.RED_ALGAE_PIECE.get());
        basicItem(ModItems.BLANCHED_RED_ALGAE.get());
        basicItem(ModItems.FILTER_CLOTH.get());
        basicItem(ModItems.AGAR_CUBE.get());
        //basicItem(ModItems.EMPTYAGARPLATE.get());
        blockItem(ModBlocks.PORTABELLA_MYCELIUM.getId());
        basicItemBlockNonBlock(ModBlocks.RED_ALGAE.getId());

    }

    public ItemModelBuilder basicItemBlockNonBlock(ResourceLocation item) {
        return ((this.getBuilder(item.toString())).parent(new ModelFile.UncheckedModelFile("item/generated"))).texture("layer0", ResourceLocation.fromNamespaceAndPath(item.getNamespace(), "block/" + item.getPath()));
    }

    public ItemModelBuilder blockItem(ResourceLocation item) {
        return ((this.getBuilder(item.toString())).parent(new ModelFile.UncheckedModelFile("block/cube_all"))).texture("all", ResourceLocation.fromNamespaceAndPath(item.getNamespace(), "block/mycelium/" + item.getPath()+0));
    }
}
