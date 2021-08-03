package ml.northwestwind.moreboots.handler.packet;

import ml.northwestwind.moreboots.Reference;
import ml.northwestwind.moreboots.handler.Utils;
import ml.northwestwind.moreboots.init.ItemInit;
import net.fabricmc.fabric.api.networking.v1.PacketByteBufs;
import net.minecraft.block.Blocks;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.network.PacketByteBuf;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.BlockPos;

public class CPlayerMultiJumpPacket implements IPacket {
    public static final Identifier ID = new Identifier(Reference.MODID, "multijump");

    @Override
    public void handle(ServerPlayerEntity player) {
        if(player == null) return;
        ItemStack boots = player.getEquippedStack(EquipmentSlot.FEET);
        if(!boots.getItem().equals(ItemInit.SANDALS)) return;
        BlockPos pos = new BlockPos(player.getPos());
        if (pos.getY() > 255 || pos.getY() < 0) return;
        if (!player.world.isAir(pos)) return;
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
            player.world.setBlockState(pos, Blocks.SAND.getDefaultState());
            player.jump();
            player.setVelocity(player.getVelocity().add(0,1,0));
        }
    }

    public static PacketByteBuf createPacket() {
        return PacketByteBufs.create().writeByteArray(Utils.packetToBytes(new CPlayerMultiJumpPacket()));
    }
}
