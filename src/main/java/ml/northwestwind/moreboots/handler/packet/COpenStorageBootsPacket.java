package ml.northwestwind.moreboots.handler.packet;

import ml.northwestwind.moreboots.Reference;
import ml.northwestwind.moreboots.handler.Utils;
import ml.northwestwind.moreboots.init.item.boots.StorageBootsItem;
import net.fabricmc.fabric.api.networking.v1.PacketByteBufs;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.item.ItemStack;
import net.minecraft.network.PacketByteBuf;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvents;
import net.minecraft.util.Identifier;

public class COpenStorageBootsPacket implements IPacket {
    public static final Identifier ID = new Identifier(Reference.MODID, "storage");

    @Override
    public void handle(ServerPlayerEntity player) {
        if (player != null) {
            ItemStack storageBoots = player.getEquippedStack(EquipmentSlot.FEET);
            if (storageBoots.getItem() instanceof StorageBootsItem) {
                ((StorageBootsItem) storageBoots.getItem()).showInventory(player);
                player.world.playSound(null, player.getBlockPos(), SoundEvents.BLOCK_CHEST_OPEN, SoundCategory.PLAYERS, 0.75f, 1f);
            }
        }
    }

    public static PacketByteBuf createPacket() {
        return PacketByteBufs.create().writeByteArray(Utils.packetToBytes(new COpenStorageBootsPacket()));
    }
}