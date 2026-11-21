package net.bloof.mushexpanded.item;

import net.minecraft.world.food.FoodProperties;

public class ModFoodProperties {
    public static final FoodProperties PORTABELLA_MUSHROOM = new FoodProperties.Builder()
            .nutrition(2)
            .saturationModifier(0.25f)
            .build();
}
