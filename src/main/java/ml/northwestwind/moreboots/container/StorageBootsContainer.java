package ml.northwestwind.moreboots.container;

import ml.northwestwind.moreboots.init.ContainerInit;
import ml.northwestwind.moreboots.init.ItemInit;
import ml.northwestwind.moreboots.inventory.StorageBootsInventory;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.Inventory;
import net.minecraft.screen.ScreenHandler;
import net.minecraft.screen.slot.Slot;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.item.ItemStack;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvents;

public class StorageBootsContainer extends ScreenHandler {
    private final Inventory storage;
    private final int containerRows;

    public StorageBootsContainer(int id, PlayerInventory playerInventory, int containerRows) {
        this(id, playerInventory, new StorageBootsInventory(containerRows), containerRows);
    }

    public StorageBootsContainer(int id, PlayerInventory playerInventory, Inventory storage, int containerRows) {
        super(ContainerInit.STORAGE_BOOTS, id);
        checkSize(storage, containerRows * 9);
        this.storage = storage;
        this.containerRows = containerRows;
        this.storage.onOpen(playerInventory.player);
        int i = (this.containerRows - 4) * 18;

        for (int j = 0; j < this.containerRows; ++j) for (int k = 0; k < 9; ++k) this.addSlot(new Slot(storage, k + j * 9, 8 + k * 18, 18 + j * 18));
        for (int l = 0; l < 3; ++l) for (int j1 = 0; j1 < 9; ++j1) this.addSlot(new Slot(playerInventory, j1 + l * 9 + 9, 8 + j1 * 18, 103 + l * 18 + i));
        for (int i1 = 0; i1 < 9; ++i1) this.addSlot(new Slot(playerInventory, i1, 8 + i1 * 18, 161 + i));
    }

    @Override
    public boolean canUse(PlayerEntity playerIn) {
        return this.storage.canPlayerUse(playerIn) && playerIn.getEquippedStack(EquipmentSlot.FEET).getItem().equals(ItemInit.STORAGE_BOOTS);
    }

    @Override
    public ItemStack transferSlot(PlayerEntity player, int index) {
        ItemStack itemstack = ItemStack.EMPTY;
        Slot slot = this.slots.get(index);
        if (slot != null && slot.hasStack()) {
            ItemStack itemstack1 = slot.getStack();
            itemstack = itemstack1.copy();
            if (index < this.containerRows * 9) {
                if (!this.insertItem(itemstack1, this.containerRows * 9, this.slots.size(), true)) {
                    return ItemStack.EMPTY;
                }
            } else if (!this.insertItem(itemstack1, 0, this.containerRows * 9, false)) {
                return ItemStack.EMPTY;
            }

            if (itemstack1.isEmpty()) {
                slot.setStack(ItemStack.EMPTY);
            } else {
                slot.markDirty();
            }
        }

        return itemstack;
    }

    @Override
    public void close(PlayerEntity playerIn) {
        super.close(playerIn);
        this.storage.onClose(playerIn);
        playerIn.world.playSound(null, playerIn.getBlockPos(), SoundEvents.BLOCK_CHEST_CLOSE, SoundCategory.PLAYERS, 0.75f, 1f);
    }

    public int getContainerRows() {
        return containerRows;
    }
}