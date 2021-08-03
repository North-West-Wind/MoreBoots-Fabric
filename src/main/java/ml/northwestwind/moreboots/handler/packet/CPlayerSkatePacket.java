package ml.northwestwind.moreboots.handler.packet;

import ml.northwestwind.moreboots.Reference;
import ml.northwestwind.moreboots.handler.Utils;
import ml.northwestwind.moreboots.init.ItemInit;
import net.fabricmc.fabric.api.networking.v1.PacketByteBufs;
import net.minecraft.block.Material;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.item.ItemStack;
import net.minecraft.network.PacketByteBuf;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3d;

public class CPlayerSkatePacket implements IPacket {
    public static final Identifier ID = new Identifier(Reference.MODID, "skate");

    @Override
    public void handle(ServerPlayerEntity player) {
        if(player == null) return;
        ItemStack boots = player.getEquippedStack(EquipmentSlot.FEET);
        if(!boots.getItem().equals(ItemInit.SKATER)) return;
        BlockPos pos = new BlockPos(player.getPos());
        Material material = player.world.getBlockState(pos.down()).getMaterial();
        if (material.equals(Material.ICE) || material.equals(Material.DENSE_ICE)) {
            Vec3d motion = player.getVelocity();
            Vec3d direction = player.getRotationVector().multiply(0.75);
            player.setVelocity(motion.multiply(0, 1, 0).add(direction.getX(), 0, direction.getZ()));
        }
    }

    public static PacketByteBuf createPacket() {
        return PacketByteBufs.create().writeByteArray(Utils.packetToBytes(new CPlayerSkatePacket()));
    }
}
