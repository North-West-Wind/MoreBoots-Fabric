package ml.northwestwind.moreboots.handler.packet;

import ml.northwestwind.moreboots.Reference;
import ml.northwestwind.moreboots.handler.Utils;
import ml.northwestwind.moreboots.init.ItemInit;
import net.fabricmc.fabric.api.networking.v1.PacketByteBufs;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.entity.LivingEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.network.PacketByteBuf;
import net.minecraft.predicate.entity.EntityPredicates;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Box;

import java.util.List;

public class CPlayerKAPacket implements IPacket {
    public static final Identifier ID = new Identifier(Reference.MODID, "ka");

    @Override
    public void handle(ServerPlayerEntity player) {
        if(player == null) return;
        ItemStack boots = player.getEquippedStack(EquipmentSlot.FEET);
        if(!boots.getItem().equals(ItemInit.KA_BOOTS)) return;
        BlockPos pos = new BlockPos(player.getPos());
        Box area = new Box(pos).expand(4);
        List<Entity> collidedEntities = player.world.getOtherEntities(player, area, EntityPredicates.EXCEPT_SPECTATOR);
        LivingEntity closest = null;
        for (Entity entity : collidedEntities) {
            if (!(entity instanceof LivingEntity)) continue;
            if (closest == null || closest.getPos().distanceTo(player.getPos()) > entity.getPos().distanceTo(player.getPos())) closest = (LivingEntity) entity;
        }
        if (closest == null) return;
        player.attack(closest);
    }

    public static PacketByteBuf createPacket() {
        return PacketByteBufs.create().writeByteArray(Utils.packetToBytes(new CPlayerKAPacket()));
    }
}
