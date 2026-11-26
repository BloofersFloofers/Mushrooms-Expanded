package net.bloof.mushexpanded.item.custom;

import net.bloof.mushexpanded.block.custom.MushroomCrop;
import net.bloof.mushexpanded.component.ModDataComponents;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.network.chat.Component;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.inventory.tooltip.TooltipComponent;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.neoforged.neoforge.client.event.RegisterColorHandlersEvent;
import net.neoforged.neoforge.client.model.ItemLayerModel;

import java.awt.image.ColorModel;
import java.util.List;
import java.util.Optional;


public class AgarPlateItem extends Item {

    public AgarPlateItem(Properties properties) {
        super(properties);
    }


    @Override
    public InteractionResult useOn(UseOnContext context) {
        Level level = context.getLevel();
        Block clickedBlock = level.getBlockState(context.getClickedPos()).getBlock();
        BlockState clickedBlockState = level.getBlockState(context.getClickedPos());

        if (clickedBlock instanceof MushroomCrop) {
            if (!level.isClientSide()) {
                if (((MushroomCrop) clickedBlock).getAge(clickedBlockState) == 3)  {
                    context.getItemInHand().set(ModDataComponents.FROM_MUSHROOM, clickedBlock);
                    context.getItemInHand().set(ModDataComponents.MYCELIUM_GROWTH, 0);
                    return InteractionResult.SUCCESS;
                }

            }
        }

        return InteractionResult.FAIL;
    }

    @Override
    public void appendHoverText(ItemStack stack, TooltipContext context, List<Component> tooltipComponents, TooltipFlag tooltipFlag) {
        if (stack.get(ModDataComponents.FROM_MUSHROOM) != null) {
            tooltipComponents.add(Component.literal("Contains spores of ")
                            .append(stack.get(ModDataComponents.FROM_MUSHROOM).getName())
                    );
            tooltipComponents.add(Component.literal(stack.get(ModDataComponents.MYCELIUM_GROWTH).toString()));
        } else {
            tooltipComponents.add(Component.translatable("tooltip.bloofsmushroomsexpanded.emptyagarplate"));
        }



    }
}
