package ml.northwestwind.moreboots.handler.packet;

import ml.northwestwind.moreboots.Reference;
import ml.northwestwind.moreboots.handler.Utils;
import net.fabricmc.fabric.api.networking.v1.PacketByteBufs;
import net.minecraft.entity.projectile.WitherSkullEntity;
import net.minecraft.network.PacketByteBuf;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvents;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.Vec3d;

public class CShootWitherSkullPacket implements IPacket {
    public static final Identifier ID = new Identifier(Reference.MODID, "wither");

    @Override
    public void handle(ServerPlayerEntity player) {
        if (player == null) return;
        Vec3d position = player.method_30951(1f).add(player.getRotationVector().normalize());
        WitherSkullEntity skull = new WitherSkullEntity(player.world, player, player.getRotationVector().x, player.getRotationVector().y, player.getRotationVector().z);
        skull.setPos(position.x, position.y, position.z);
        player.world.spawnEntity(skull);
        player.world.playSound(null, player.getBlockPos(), SoundEvents.ENTITY_WITHER_SHOOT, SoundCategory.PLAYERS, 1, 1);
    }

    public static PacketByteBuf createPacket() {
        return PacketByteBufs.create().writeByteArray(Utils.packetToBytes(new CShootWitherSkullPacket()));
    }
}
