package ml.northwestwind.moreboots.init.item.boots;

import ml.northwestwind.moreboots.handler.Utils;
import ml.northwestwind.moreboots.handler.packet.CPlayerKAPacket;
import ml.northwestwind.moreboots.init.ItemInit;
import ml.northwestwind.moreboots.init.item.BootsItem;
import net.fabricmc.fabric.api.client.networking.v1.ClientPlayNetworking;
import net.fabricmc.fabric.api.networking.v1.PacketByteBufs;

public class KABootsItem extends BootsItem {
    public KABootsItem() {
        super(ItemInit.ModArmorMaterial.KA, "ka_boots");
    }

    @Override
    public void onPlayerLeftClick() {
        ClientPlayNetworking.send(CPlayerKAPacket.ID, CPlayerKAPacket.createPacket());
    }
}
