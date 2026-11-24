package net.bloof.mushexpanded.util;

import net.bloof.mushexpanded.MushExpanded;
import net.bloof.mushexpanded.component.ModDataComponents;
import net.bloof.mushexpanded.item.ModItems;
import net.minecraft.client.renderer.item.ItemProperties;
import net.minecraft.resources.ResourceLocation;

public class ModItemProperties {
    public static void addCustomItemProperties() {
        ItemProperties.register(ModItems.AGARPLATE.get(), ResourceLocation.fromNamespaceAndPath(MushExpanded.MOD_ID, "used"),
                (itemStack, clientLevel, livingEntity, i) ->
                        itemStack.get(ModDataComponents.FROM_MUSHROOM) != null ? 1f : 0f);


    }
}
