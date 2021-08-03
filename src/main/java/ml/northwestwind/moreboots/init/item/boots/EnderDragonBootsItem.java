package ml.northwestwind.moreboots.init.item.boots;

import ml.northwestwind.moreboots.handler.packet.CShootDragonBallPacket;
import ml.northwestwind.moreboots.init.ItemInit;
import ml.northwestwind.moreboots.init.item.BootsItem;
import net.fabricmc.fabric.api.client.networking.v1.ClientPlayNetworking;

public class EnderDragonBootsItem extends BootsItem {
    public EnderDragonBootsItem() {
        super(ItemInit.ModArmorMaterial.DRAGON, "ender_dragon_boots");
    }

    @Override
    public void activateBoots() {
        ClientPlayNetworking.send(CShootDragonBallPacket.ID, CShootDragonBallPacket.createPacket());
    }
}
