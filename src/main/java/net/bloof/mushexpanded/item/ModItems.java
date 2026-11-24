package net.bloof.mushexpanded.item;

import net.bloof.mushexpanded.MushExpanded;
import net.bloof.mushexpanded.item.custom.AgarPlateItem;
import net.bloof.mushexpanded.item.custom.FilterClothItem;
import net.minecraft.world.item.Item;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredItem;
import net.neoforged.neoforge.registries.DeferredRegister;

public class ModItems {
    public static final DeferredRegister.Items ITEMS = DeferredRegister.createItems(MushExpanded.MOD_ID);

    public static final DeferredItem<AgarPlateItem> AGARPLATE = ITEMS.register("agarplate",
            () -> new AgarPlateItem(new Item.Properties())
    );

    public static final DeferredItem<Item> RED_ALGAE_PIECE = ITEMS.register("redalgaepiece",
            () -> new Item(new Item.Properties())
    );

    public static final DeferredItem<Item> BLANCHED_RED_ALGAE = ITEMS.register("blanchedredalgae",
            () -> new Item(new Item.Properties())
    );
    public static final DeferredItem<Item> AGAR_CUBE = ITEMS.register("agarcube",
            () -> new Item(new Item.Properties())
    );

    public static final DeferredItem<FilterClothItem> FILTER_CLOTH = ITEMS.register("filtercloth",
            () -> new FilterClothItem(new Item.Properties().durability(64))
    );

    public static final DeferredItem<Item> PORTABELLA_MUSHROOM = ITEMS.register("portabellamushroom",
            () -> new Item(new Item.Properties().food(ModFoodProperties.PORTABELLA_MUSHROOM))
    );



    public static void register(IEventBus eventBus) {
        ITEMS.register(eventBus);
    }
}
