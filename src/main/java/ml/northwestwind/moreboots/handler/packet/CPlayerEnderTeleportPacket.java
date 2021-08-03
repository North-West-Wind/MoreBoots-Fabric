package ml.northwestwind.moreboots.handler.packet;

import ml.northwestwind.moreboots.Reference;
import ml.northwestwind.moreboots.handler.Utils;
import ml.northwestwind.moreboots.init.ItemInit;
import net.fabricmc.fabric.api.networking.v1.PacketByteBufs;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.item.ItemStack;
import net.minecraft.network.PacketByteBuf;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.sound.SoundEvents;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3d;

public class CPlayerEnderTeleportPacket implements IPacket {
    public static final Identifier ID = new Identifier(Reference.MODID, "teleport");

    @Override
    public void handle(ServerPlayerEntity player) {
        if(player == null) return;
        ItemStack boots = player.getEquippedStack(EquipmentSlot.FEET);
        if(!boots.getItem().equals(ItemInit.ENDER_BOOTS)) return;
        Vec3d pos = player.getPos().add(player.getRotationVector().multiply(8, 8, 8));
        BlockPos blockPos = new BlockPos(pos);
        while (!player.world.isAir(blockPos)) blockPos = blockPos.up();
        player.setPos(pos.getX(), blockPos.getY() + pos.getY() % 1, pos.getZ());
        boots.damage(1, player.world.random, player);
    }

    public static PacketByteBuf createPacket() {
        return PacketByteBufs.create().writeByteArray(Utils.packetToBytes(new CPlayerEnderTeleportPacket()));
    }
}
