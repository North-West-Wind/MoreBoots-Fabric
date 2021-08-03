package ml.northwestwind.moreboots.init.item.boots;

import ml.northwestwind.moreboots.handler.packet.CPlayerEnderTeleportPacket;
import ml.northwestwind.moreboots.init.ItemInit;
import ml.northwestwind.moreboots.init.item.BootsItem;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.fabricmc.fabric.api.client.networking.v1.ClientPlayNetworking;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.network.ClientPlayerEntity;
import net.minecraft.text.TranslatableText;
import net.minecraft.util.Formatting;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3d;

public class EnderBootsItem extends BootsItem {
    public EnderBootsItem() {
        super(ItemInit.ModArmorMaterial.ENDER, "ender_boots");
    }

    @Environment(EnvType.CLIENT)
    @Override
    public void activateBoots() {
        ClientPlayerEntity player = MinecraftClient.getInstance().player;
        if (player == null) return;
        Vec3d pos = player.getPos().add(player.getRotationVector().multiply(8, 8, 8));
        BlockPos blockPos = new BlockPos(pos);
        if (!player.world.isAir(blockPos) && player.world.getBlockState(blockPos).isOpaque()) {
            player.sendMessage(new TranslatableText("warning.moreboots.ender_boots").formatted(Formatting.RED), true);
            return;
        }
        player.setPos(pos.getX(), blockPos.getY() + pos.getY() % 1, pos.getZ());
        ClientPlayNetworking.send(CPlayerEnderTeleportPacket.ID, CPlayerEnderTeleportPacket.createPacket());
    }
}
