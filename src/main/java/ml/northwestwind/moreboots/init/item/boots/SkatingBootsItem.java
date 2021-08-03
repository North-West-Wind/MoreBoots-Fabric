package ml.northwestwind.moreboots.init.item.boots;

import ml.northwestwind.moreboots.handler.packet.CPlayerSkatePacket;
import ml.northwestwind.moreboots.init.ItemInit;
import ml.northwestwind.moreboots.init.item.BootsItem;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.fabricmc.fabric.api.client.networking.v1.ClientPlayNetworking;
import net.minecraft.block.Material;
import net.minecraft.client.MinecraftClient;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3d;

public class SkatingBootsItem extends BootsItem {
    public SkatingBootsItem() {
        super(ItemInit.ModArmorMaterial.SKATER, "skating_boots");
    }

    @Environment(EnvType.CLIENT)
    @Override
    public void onShift() {
        PlayerEntity player = MinecraftClient.getInstance().player;
        if (player == null) return;
        BlockPos pos = player.getBlockPos();
        Material material = player.world.getBlockState(pos.down()).getMaterial();
        if (material.equals(Material.ICE) || material.equals(Material.DENSE_ICE)) {
            Vec3d motion = player.getVelocity();
            Vec3d direction = player.getRotationVector().multiply(0.75);
            player.setVelocity(motion.multiply(0, 1, 0).add(direction.x, 0, direction.z));
        }
        if (player.world.isClient) ClientPlayNetworking.send(CPlayerSkatePacket.ID, CPlayerSkatePacket.createPacket());
    }
}
