package net.bloof.mushexpanded.item;

import net.bloof.mushexpanded.MushExpanded;
import net.minecraft.world.item.Item;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredItem;
import net.neoforged.neoforge.registries.DeferredRegister;

public class ModItems {
    public static final DeferredRegister.Items ITEMS = DeferredRegister.createItems(MushExpanded.MOD_ID);

    public static final DeferredItem<Item> EMPTYAGARPLATE = ITEMS.register("emptyagarplate",
            () -> new Item(new Item.Properties())
    );

    public static void register(IEventBus eventBus) {
        ITEMS.register(eventBus);
    }
}
