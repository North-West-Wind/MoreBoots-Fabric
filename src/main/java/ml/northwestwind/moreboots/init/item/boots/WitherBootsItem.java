package ml.northwestwind.moreboots.init.item.boots;

import ml.northwestwind.moreboots.handler.packet.CShootWitherSkullPacket;
import ml.northwestwind.moreboots.init.ItemInit;
import ml.northwestwind.moreboots.init.item.BootsItem;
import net.fabricmc.fabric.api.client.networking.v1.ClientPlayNetworking;

public class WitherBootsItem extends BootsItem {
    public WitherBootsItem() {
        super(ItemInit.ModArmorMaterial.WITHER, "wither_boots");
    }

    @Override
    public void activateBoots() {
        ClientPlayNetworking.send(CShootWitherSkullPacket.ID, CShootWitherSkullPacket.createPacket());
    }
}
