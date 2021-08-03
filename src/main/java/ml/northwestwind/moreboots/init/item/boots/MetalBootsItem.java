package ml.northwestwind.moreboots.init.item.boots;

import ml.northwestwind.moreboots.events.LivingHurtEvent;
import ml.northwestwind.moreboots.init.ItemInit;
import ml.northwestwind.moreboots.init.item.BootsItem;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.item.ItemStack;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.sound.SoundEvents;
import org.apache.logging.log4j.LogManager;

public class MetalBootsItem extends BootsItem {
    public MetalBootsItem() {
        super(ItemInit.ModArmorMaterial.METAL, "metal_boots");
    }

    @Override
    public void onLivingHurt(LivingHurtEvent event) {
        LogManager.getLogger().info("Received LivingDamageEvent in MetalBoots");
        LivingEntity entity = event.getEntityLiving();
        ItemStack boots = entity.getEquippedStack(EquipmentSlot.FEET);
        if (entity.world.isClient) return;
        LogManager.getLogger().info("Cancelled LivingDamageEvent");
        event.setCancelled(true);
        if (entity instanceof ServerPlayerEntity) boots.damage(1, entity.world.random, (ServerPlayerEntity) entity);
        else boots.damage(1, entity, entity1 -> entity1.playSound(SoundEvents.BLOCK_GLASS_BREAK, 1.0f, 1.0f));
    }
}