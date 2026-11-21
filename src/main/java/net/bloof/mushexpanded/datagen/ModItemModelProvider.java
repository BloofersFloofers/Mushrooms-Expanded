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
        basicItem(ModItems.EMPTYAGARPLATE.get());
        blockItem(ModBlocks.PORTABELLA_MYCELIUM.getId());

    }

    public ItemModelBuilder blockItem(ResourceLocation item) {
        return ((this.getBuilder(item.toString())).parent(new ModelFile.UncheckedModelFile("block/cube_all"))).texture("all", ResourceLocation.fromNamespaceAndPath(item.getNamespace(), "block/mycelium/" + item.getPath()+0));
    }
}
