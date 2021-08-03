package ml.northwestwind.moreboots.init.item.boots;

import ml.northwestwind.moreboots.events.LivingFallEvent;
import ml.northwestwind.moreboots.init.ItemInit;
import ml.northwestwind.moreboots.init.item.BootsItem;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.item.ItemStack;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.sound.SoundEvents;

public class QuartzBootsItem extends BootsItem {
    public QuartzBootsItem() {
        super(ItemInit.ModArmorMaterial.QUARTZ, "quartz_boots");
    }

    @Override
    public void onLivingFall(final LivingFallEvent event) {
        LivingEntity entity = event.getEntityLiving();
        float distance = event.getDistance();
        ItemStack boots = entity.getEquippedStack(EquipmentSlot.FEET);
        if (entity.world.isClient) return;
        if (distance < 3.0f) return;
        if (entity instanceof ServerPlayerEntity) boots.damage((int) (4 * distance), entity.world.random, (ServerPlayerEntity) entity);
        else boots.damage((int) (4 * distance), entity, entity1 -> entity1.playSound(SoundEvents.ENTITY_ITEM_BREAK, 1, 1));
    }
}
