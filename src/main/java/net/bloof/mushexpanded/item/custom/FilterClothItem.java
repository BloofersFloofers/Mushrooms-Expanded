package net.bloof.mushexpanded.item.custom;


import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;


public class FilterClothItem extends Item {
    public FilterClothItem(Properties properties) {
        super(properties);
    }

    @Override
    public boolean hasCraftingRemainingItem(ItemStack stack) {
        return true;
    }

    @Override
    public ItemStack getCraftingRemainingItem(ItemStack itemStack) {
        ItemStack newItem = itemStack.copyWithCount(1);
        newItem.setDamageValue(newItem.getDamageValue()+1);
        if (newItem.getDamageValue() == newItem.getMaxDamage()) {
            return ItemStack.EMPTY;
        }

        return newItem;
    }
}
