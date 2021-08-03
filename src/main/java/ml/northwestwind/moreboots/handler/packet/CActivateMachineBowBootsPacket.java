package ml.northwestwind.moreboots.handler.packet;

import ml.northwestwind.moreboots.Reference;
import ml.northwestwind.moreboots.handler.Utils;
import net.fabricmc.fabric.api.networking.v1.PacketByteBufs;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.network.PacketByteBuf;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.util.Identifier;

public record CActivateMachineBowBootsPacket(boolean activate) implements IPacket {
    public static final Identifier ID = new Identifier(Reference.MODID, "activate");

    @Override
    public void handle(ServerPlayerEntity player) {
        if (player == null) return;
        ItemStack boots = player.getEquippedStack(EquipmentSlot.FEET);
        NbtCompound tag = boots.getOrCreateNbt();
        tag.putBoolean("Activated", activate);
        boots.setNbt(tag);
    }

    public static PacketByteBuf createPacket(boolean activate) {
        return PacketByteBufs.create().writeByteArray(Utils.packetToBytes(new CActivateMachineBowBootsPacket(activate)));
    }
}
