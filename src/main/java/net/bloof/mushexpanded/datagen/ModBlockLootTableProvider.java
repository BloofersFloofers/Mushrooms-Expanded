package net.bloof.mushexpanded.datagen;

import net.bloof.mushexpanded.block.ModBlocks;
import net.bloof.mushexpanded.block.custom.MushroomCrop;
import net.bloof.mushexpanded.item.ModItems;
import net.minecraft.advancements.critereon.StatePropertiesPredicate;
import net.minecraft.core.Holder;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.loot.BlockLootSubProvider;
import net.minecraft.world.flag.FeatureFlags;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraft.world.item.enchantment.Enchantments;
import net.minecraft.world.level.ItemLike;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.storage.loot.LootPool;
import net.minecraft.world.level.storage.loot.LootTable;
import net.minecraft.world.level.storage.loot.entries.LootItem;
import net.minecraft.world.level.storage.loot.entries.LootPoolSingletonContainer;
import net.minecraft.world.level.storage.loot.functions.ApplyBonusCount;
import net.minecraft.world.level.storage.loot.predicates.AllOfCondition;
import net.minecraft.world.level.storage.loot.predicates.LootItemBlockStatePropertyCondition;
import net.minecraft.world.level.storage.loot.predicates.LootItemCondition;
import net.minecraft.world.level.storage.loot.providers.number.ConstantValue;
import net.neoforged.fml.common.Mod;

import java.util.Set;

public class ModBlockLootTableProvider extends BlockLootSubProvider {

    protected ModBlockLootTableProvider(HolderLookup.Provider registries) {
        super(Set.of(), FeatureFlags.REGISTRY.allFlags(), registries);
    }

    LootItemCondition.Builder lootItemConditionBuilder = LootItemBlockStatePropertyCondition.hasBlockStateProperties(ModBlocks.PORTABELLA_MUSHROOM.get())
            .setProperties(StatePropertiesPredicate.Builder.properties().hasProperty(MushroomCrop.AGE, 3));

    @Override
    protected void generate() {
        dropSelf(ModBlocks.PORTABELLA_MYCELIUM.get());
        dropSelf(ModBlocks.AGAR_BLOCK.get());
        add(ModBlocks.RED_ALGAE.get(), createMultiItemTable(ModItems.RED_ALGAE_PIECE, 4.0F));
        add(ModBlocks.PORTABELLA_MUSHROOM.get(),
                createCropDropsNoSeeds(ModBlocks.PORTABELLA_MUSHROOM.get(), ModItems.PORTABELLA_MUSHROOM.get(), lootItemConditionBuilder));

    }

    protected LootTable.Builder createCropDropsNoSeeds(Block cropBlock, Item grownCropItem, LootItemCondition.Builder dropGrownCropCondition) {
        HolderLookup.RegistryLookup<Enchantment> registrylookup = registries.lookupOrThrow(Registries.ENCHANTMENT);
        return applyExplosionDecay(cropBlock, LootTable.lootTable().withPool(LootPool.lootPool().add((LootItem.lootTableItem(grownCropItem).when(dropGrownCropCondition)))));
    }

    public LootTable.Builder createMultiItemTable(ItemLike item, Float amount) {
        return LootTable.lootTable().withPool(this.applyExplosionCondition(item, LootPool.lootPool().setRolls(ConstantValue.exactly(amount)).add(LootItem.lootTableItem(item))));
    }

    @Override
    protected Iterable<Block> getKnownBlocks() {
        return ModBlocks.BLOCKS.getEntries().stream().map(Holder::value)::iterator;
    }
}
