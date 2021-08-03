package ml.northwestwind.moreboots.handler.packet;

import net.fabricmc.fabric.api.networking.v1.PacketByteBufs;
import net.minecraft.server.network.ServerPlayerEntity;

import java.io.Serializable;

public interface IPacket extends Serializable {
    void handle(ServerPlayerEntity player);
}
