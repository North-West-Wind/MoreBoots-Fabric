package ml.northwestwind.moreboots.handler.packet;

import ml.northwestwind.moreboots.Reference;
import ml.northwestwind.moreboots.handler.Utils;
import net.fabricmc.fabric.api.networking.v1.PacketByteBufs;
import net.minecraft.entity.projectile.DragonFireballEntity;
import net.minecraft.network.PacketByteBuf;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvents;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.Vec3d;

public class CShootDragonBallPacket implements IPacket {
    public static final Identifier ID = new Identifier(Reference.MODID, "dragon");

    @Override
    public void handle(ServerPlayerEntity player) {
        if (player == null) return;
        Vec3d position = player.getEyePos().add(player.getRotationVector().normalize());
        DragonFireballEntity dragonBall = new DragonFireballEntity(player.world, player, player.getRotationVector().x, player.getRotationVector().y, player.getRotationVector().z);
        dragonBall.setPos(position.x, position.y, position.z);
        player.world.spawnEntity(dragonBall);
        player.world.playSound(null, player.getBlockPos(), SoundEvents.ENTITY_ENDER_DRAGON_SHOOT, SoundCategory.PLAYERS, 1, 1);
    }

    public static PacketByteBuf createPacket() {
        return PacketByteBufs.create().writeByteArray(Utils.packetToBytes(new CShootDragonBallPacket()));
    }
}
