package net.bloof.mushexpanded.block.entity;

import com.mojang.logging.LogUtils;
import net.bloof.mushexpanded.component.ModDataComponents;
import net.minecraft.core.BlockPos;
import net.minecraft.core.HolderLookup;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.protocol.Packet;
import net.minecraft.network.protocol.game.ClientGamePacketListener;
import net.minecraft.network.protocol.game.ClientboundBlockEntityDataPacket;
import net.minecraft.network.protocol.game.ClientboundBlockUpdatePacket;
import net.minecraft.world.Containers;
import net.minecraft.world.SimpleContainer;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.DirectionalBlock;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.neoforged.neoforge.items.ItemStackHandler;
import org.jetbrains.annotations.Nullable;
import org.jline.utils.Log;

public class IncubatorBlockEntity extends BlockEntity {
    public final ItemStackHandler inventory = new ItemStackHandler(4) {
        @Override
        protected int getStackLimit(int slot, ItemStack stack) {
            return 1;
        }

        @Override
        protected void onContentsChanged(int slot) {
            setChanged();
            if (!level.isClientSide()) {
                level.sendBlockUpdated(getBlockPos(), getBlockState(), getBlockState(), 3);
            }
        }
    };
    public IncubatorBlockEntity(BlockPos pos, BlockState blockState) {
        super(ModBlockEntities.INCUBATOR_BE.get(), pos, blockState);
    }

    public void clearContents() {
        for(int i = 0; i < inventory.getSlots(); ++i) {
            inventory.setStackInSlot(i, ItemStack.EMPTY);
        }
    }

    public void clearSlot(Integer i) {
        inventory.setStackInSlot(i, ItemStack.EMPTY);
    }

    public void drops() {
        SimpleContainer inv = new SimpleContainer(inventory.getSlots());
        for(int i = 0; i < inventory.getSlots(); ++i) {
            inv.setItem(i, inventory.getStackInSlot(i));
        }

        Containers.dropContents(this.level, this.worldPosition, inv);
        clearContents();
    }

    @Override
    protected void saveAdditional(CompoundTag tag, HolderLookup.Provider registries) {
        super.saveAdditional(tag, registries);
        tag.put("inventory", inventory.serializeNBT(registries));
    }

    @Override
    protected void loadAdditional(CompoundTag tag, HolderLookup.Provider registries) {
        super.loadAdditional(tag, registries);
        inventory.deserializeNBT(registries, tag.getCompound("inventory"));
    }

    @Override
    public CompoundTag getUpdateTag(HolderLookup.Provider registries) {
        return saveWithoutMetadata(registries);
    }

    @Override
    public @Nullable Packet<ClientGamePacketListener> getUpdatePacket() {
        return ClientboundBlockEntityDataPacket.create(this);
    }


    public static void serverTick(Level level, BlockPos blockPos, BlockState blockState, IncubatorBlockEntity incubatorBlockEntity) {
        if (level.getRandom().nextDouble() < 0.005) {
                LogUtils.getLogger().debug("Ticked!");
                for (int i = 0; i < incubatorBlockEntity.inventory.getSlots(); ++i) {
                    if (!incubatorBlockEntity.inventory.getStackInSlot(i).isEmpty()) {
                        ItemStack item = incubatorBlockEntity.inventory.getStackInSlot(i);
                        if (item.get(ModDataComponents.MYCELIUM_GROWTH) != null) {
                            if (item.get(ModDataComponents.MYCELIUM_GROWTH) < 98) {
                                item.set(ModDataComponents.MYCELIUM_GROWTH, item.get(ModDataComponents.MYCELIUM_GROWTH)+2);
                                LogUtils.getLogger().debug(incubatorBlockEntity.inventory.getStackInSlot(i).get(ModDataComponents.MYCELIUM_GROWTH).toString());
                            } else {
                                item.set(ModDataComponents.MYCELIUM_GROWTH, 100);
                                LogUtils.getLogger().debug("Max reached");
                                LogUtils.getLogger().debug(incubatorBlockEntity.inventory.getStackInSlot(i).get(ModDataComponents.MYCELIUM_GROWTH).toString());
                            }

                        }
                    }
                }
        }
    }
}