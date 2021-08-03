package ml.northwestwind.moreboots.inventory;

import ml.northwestwind.moreboots.init.ItemInit;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.inventory.Inventories;
import net.minecraft.inventory.Inventory;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.nbt.NbtList;
import net.minecraft.util.collection.DefaultedList;
import net.minecraft.entity.player.PlayerEntity;

import java.util.List;

public class StorageBootsInventory implements Inventory {
    private final int size;
    private final DefaultedList<ItemStack> items;
    public StorageBootsInventory(int rows)
    {
        this.size = 9 * rows;
        this.items = DefaultedList.ofSize(this.size, ItemStack.EMPTY);
    }

    @Override
    public int size() {
        return size;
    }

    @Override
    public boolean isEmpty() {
        for (ItemStack stack : items) if (!stack.isEmpty()) return false;
        return true;
    }

    @Override
    public ItemStack getStack(int index) {
        List<ItemStack> list = getListSafe(index);
        return list.get(index);
    }

    @Override
    public ItemStack removeStack(int index, int amount) {
        List<ItemStack> list = getListSafe(index);
        return list != null && !list.get(index).isEmpty() ? Inventories.splitStack(list, index, amount) : ItemStack.EMPTY;
    }

    @Override
    public ItemStack removeStack(int index) {
        List<ItemStack> list = getListSafe(index);
        if (list != null && !list.get(index).isEmpty()) {
            ItemStack itemstack = list.get(index);
            list.set(index, ItemStack.EMPTY);
            return itemstack;
        } else {
            return ItemStack.EMPTY;
        }
    }

    @Override
    public void setStack(int index, ItemStack stack) {
        List<ItemStack> list = getListSafe(index);
        list.set(index, stack);
    }

    @Override
    public void markDirty() { }

    @Override
    public boolean canPlayerUse(PlayerEntity player)
    {
        return player.getEquippedStack(EquipmentSlot.FEET).getItem().equals(ItemInit.STORAGE_BOOTS);
    }

    @Override
    public void onOpen(PlayerEntity player) {
        this.clear();
        ItemStack boots = player.getEquippedStack(EquipmentSlot.FEET);
        if(boots.getItem().equals(ItemInit.STORAGE_BOOTS)) {
            NbtCompound compound = boots.getOrCreateNbt();
            if(compound.contains("Items")) loadAllItems((NbtList) compound.get("Items"), this);
        }
    }

    @Override
    public void onClose(PlayerEntity player) {
        ItemStack boots = player.getEquippedStack(EquipmentSlot.FEET);
        if(boots.getItem().equals(ItemInit.STORAGE_BOOTS)) {
            NbtCompound compound = boots.getOrCreateNbt();
            NbtList list = new NbtList();
            saveAllItems(list, this);
            compound.put("Items", list);
            boots.setNbt(compound);
        }
    }

    public static void loadAllItems(NbtList list, Inventory inventory)
    {
        for(int i = 0; i < list.size(); i++) {
            NbtCompound compound = list.getCompound(i);
            int slot = compound.getInt("Slot");
            if(slot < inventory.size()) inventory.setStack(slot, ItemStack.fromNbt(compound.getCompound("Item")));
        }
    }

    public static NbtList saveAllItems(NbtList list, Inventory inventory)
    {
        for(int i = 0; i < inventory.size(); ++i) {
            ItemStack itemstack = inventory.getStack(i);
            if(!itemstack.isEmpty()) {
                NbtCompound compound = new NbtCompound(), item = new NbtCompound();
                compound.putInt("Slot", i);
                itemstack.writeNbt(item);
                compound.put("Item", item);
                list.add(compound);
            }
        }
        return list;
    }

    @Override
    public void clear() {
        this.items.clear();
    }

    private List<ItemStack> getListSafe(int index) {
        List<ItemStack> list = null;
        if (index < this.items.size()) list = this.items;
        else index -= this.items.size();
        return list;
    }
}
