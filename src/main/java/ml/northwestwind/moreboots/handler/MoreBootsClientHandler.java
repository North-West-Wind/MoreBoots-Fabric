package ml.northwestwind.moreboots.handler;

import ml.northwestwind.moreboots.events.PlaySoundEvent;
import ml.northwestwind.moreboots.events.RenderLivingEvent;
import ml.northwestwind.moreboots.events.RenderNameplateEvent;
import ml.northwestwind.moreboots.init.KeybindInit;
import ml.northwestwind.moreboots.init.item.BootsItem;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.network.ClientPlayerEntity;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.entity.LivingEntity;
import net.minecraft.item.ItemStack;

@Environment(EnvType.CLIENT)
public class MoreBootsClientHandler {
    public static void onPlayerLeftClick() {
        ClientPlayerEntity player = MinecraftClient.getInstance().player;
        if (player == null) return;
        ItemStack boots = player.getEquippedStack(EquipmentSlot.FEET);
        if (boots.getItem() instanceof BootsItem) ((BootsItem) boots.getItem()).onPlayerLeftClick();
    }

    public static void onPlaySound(PlaySoundEvent event) {
        ClientPlayerEntity player = MinecraftClient.getInstance().player;
        if (player == null) return;
        ItemStack boots = player.getEquippedStack(EquipmentSlot.FEET);
        if (boots.getItem() instanceof BootsItem) ((BootsItem) boots.getItem()).onPlaySound(event);
    }

    public static void onKeyInput(MinecraftClient client) {
        ClientPlayerEntity player = client.player;
        if (player == null) return;
        ItemStack boots = player.getEquippedStack(EquipmentSlot.FEET);
        if (!(boots.getItem() instanceof BootsItem item)) return;
        if (client.options.keySneak.wasPressed()) item.onShift();
        if (client.options.keyJump.wasPressed()) item.onJump();
        if (KeybindInit.activate.wasPressed()) item.activateBoots();
    }

    public static void preRenderLiving(final RenderLivingEvent.Pre<?, ?> event) {
        ItemStack boots = event.getEntity().getEquippedStack(EquipmentSlot.FEET);
        if (boots.getItem() instanceof BootsItem) ((BootsItem) boots.getItem()).preRenderLiving(event);
    }

    public static void postRenderLiving(final RenderLivingEvent.Post<?, ?> event) {
        ItemStack boots = event.getEntity().getEquippedStack(EquipmentSlot.FEET);
        if (boots.getItem() instanceof BootsItem) ((BootsItem) boots.getItem()).postRenderLiving(event);
    }

    public static void renderNameplate(final RenderNameplateEvent event) {
        if (!(event.getEntity() instanceof LivingEntity)) return;
        ItemStack boots = ((LivingEntity) event.getEntity()).getEquippedStack(EquipmentSlot.FEET);
        if (boots.getItem() instanceof BootsItem) ((BootsItem) boots.getItem()).renderNameplate(event);
    }
}
