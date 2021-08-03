package ml.northwestwind.moreboots.handler;

import com.google.common.collect.ImmutableList;
import ml.northwestwind.moreboots.handler.packet.*;
import net.fabricmc.fabric.api.networking.v1.ServerPlayNetworking;
import net.minecraft.util.Identifier;

import java.util.List;

public class ServerEvents {
    public static final List<Identifier> PACKETS = ImmutableList.of(
            CActivateMachineBowBootsPacket.ID,
            COpenStorageBootsPacket.ID,
            CPlayerEnderTeleportPacket.ID,
            CPlayerKAPacket.ID,
            CPlayerMultiJumpPacket.ID,
            CPlayerSkatePacket.ID,
            CShootDragonBallPacket.ID,
            CShootWitherSkullPacket.ID
    );

    public static void serverStarting() {
        for (Identifier id : PACKETS) ServerPlayNetworking.registerGlobalReceiver(id, (s, player, h, buf, r) -> Utils.bytesToObj(buf.readByteArray()).handle(player));
    }
}
