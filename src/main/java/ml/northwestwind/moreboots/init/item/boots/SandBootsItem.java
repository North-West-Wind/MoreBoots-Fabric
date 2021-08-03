package ml.northwestwind.moreboots.init.item.boots;

import com.google.common.collect.Sets;
import ml.northwestwind.moreboots.handler.packet.CPlayerMultiJumpPacket;
import ml.northwestwind.moreboots.init.ItemInit;
import ml.northwestwind.moreboots.init.item.BootsItem;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.fabricmc.fabric.api.client.networking.v1.ClientPlayNetworking;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.network.ClientPlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.util.math.BlockPos;

public class SandBootsItem extends BootsItem {
    public SandBootsItem() {
        super(ItemInit.ModArmorMaterial.SAND, "sand_boots");
    }

    @Environment(EnvType.CLIENT)
    @Override
    public void onJump() {
        ClientPlayerEntity player = MinecraftClient.getInstance().player;
        if (player == null || player.isOnGround() || player.getAbilities().flying) return;
        BlockPos pos = player.getBlockPos();
        if (pos.getY() > 255 || pos.getY() < 0) return;
        if (!player.world.isAir(pos) || !player.getInventory().containsAny(Sets.newHashSet(Items.SAND))) return;
        boolean shouldJump = player.isCreative();
        if (!shouldJump) for (ItemStack stack : player.getInventory().main) {
            if (stack.getItem().equals(Items.SAND)) {
                int slot = player.getInventory().getSlotWithStack(stack);
                stack.decrement(1);
                player.getInventory().setStack(slot, stack);
                shouldJump = true;
                break;
            }
        }
        if (!shouldJump) for (ItemStack stack : player.getInventory().offHand) {
            if (stack.getItem().equals(Items.SAND)) {
                int slot = player.getInventory().getSlotWithStack(stack);
                stack.decrement(1);
                player.getInventory().setStack(slot, stack);
                shouldJump = true;
                break;
            }
        }
        if (!shouldJump) for (ItemStack stack : player.getInventory().armor) {
            if (stack.getItem().equals(Items.SAND)) {
                int slot = player.getInventory().getSlotWithStack(stack);
                stack.decrement(1);
                player.getInventory().setStack(slot, stack);
                shouldJump = true;
                break;
            }
        }
        if (shouldJump) {
            player.jump();
            player.setVelocity(player.getVelocity().add(0, 1, 0));
            if (player.world.isClient) ClientPlayNetworking.send(CPlayerMultiJumpPacket.ID, CPlayerMultiJumpPacket.createPacket());
        }
    }
}
