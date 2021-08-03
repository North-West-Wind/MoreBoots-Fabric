package ml.northwestwind.moreboots.init.item.boots;

import ml.northwestwind.moreboots.events.LivingEvent;
import ml.northwestwind.moreboots.handler.packet.CActivateMachineBowBootsPacket;
import ml.northwestwind.moreboots.init.ItemInit;
import ml.northwestwind.moreboots.init.item.BootsItem;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.fabricmc.fabric.api.client.networking.v1.ClientPlayNetworking;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.network.ClientPlayerEntity;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.projectile.ArrowEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvents;

public class MachineBowBoots extends BootsItem {
    public MachineBowBoots() {
        super(ItemInit.ModArmorMaterial.MACHINE_BOW, "machine_bow_boots");
    }

    @Environment(EnvType.CLIENT)
    @Override
    public void activateBoots() {
        ClientPlayerEntity player = MinecraftClient.getInstance().player;
        if (player == null) return;
        boolean newState = !player.getEquippedStack(EquipmentSlot.FEET).getOrCreateNbt().getBoolean("Activated");
        ClientPlayNetworking.send(CActivateMachineBowBootsPacket.ID, CActivateMachineBowBootsPacket.createPacket(newState));
    }

    @Override
    public void onLivingUpdate(LivingEvent.LivingUpdateEvent event) {
        LivingEntity entity = event.getEntityLiving();
        ItemStack boots = entity.getEquippedStack(EquipmentSlot.FEET);
        NbtCompound tag = boots.getOrCreateNbt();
        if (tag.getBoolean("Activated") && hasArrows(entity)) {
            ArrowEntity arrow = new ArrowEntity(entity.world, entity);
            arrow.setProperties(entity, entity.getPitch(), entity.getYaw(), 0, 1, 1);
            entity.world.spawnEntity(arrow);
            entity.world.playSound(null, entity.getBlockPos(), SoundEvents.ENTITY_ARROW_SHOOT, SoundCategory.PLAYERS, 1, 1);
            if (entity.getRandom().nextInt(100) == 0)
                if (entity instanceof ServerPlayerEntity) boots.damage(1, entity.world.random, (ServerPlayerEntity) entity);
                else boots.damage(1, entity, e -> e.playSound(SoundEvents.ENTITY_ITEM_BREAK, 1, 1));
        }
    }

    private boolean hasArrows(LivingEntity entity) {
        if (!(entity instanceof PlayerEntity)) return true;
        PlayerEntity player = (PlayerEntity) entity;
        boolean shouldJump = player.isCreative();
        if (!shouldJump) for (ItemStack stack : player.getInventory().main) {
            if (stack.getItem().equals(Items.ARROW)) {
                int slot = player.getInventory().getSlotWithStack(stack);
                stack.decrement(1);
                player.getInventory().setStack(slot, stack);
                shouldJump = true;
                break;
            }
        }
        if (!shouldJump) for (ItemStack stack : player.getInventory().offHand) {
            if (stack.getItem().equals(Items.ARROW)) {
                int slot = player.getInventory().getSlotWithStack(stack);
                stack.decrement(1);
                player.getInventory().setStack(slot, stack);
                shouldJump = true;
                break;
            }
        }
        if (!shouldJump) for (ItemStack stack : player.getInventory().armor) {
            if (stack.getItem().equals(Items.ARROW)) {
                int slot = player.getInventory().getSlotWithStack(stack);
                stack.decrement(1);
                player.getInventory().setStack(slot, stack);
                shouldJump = true;
                break;
            }
        }
        return shouldJump;
    }
}
