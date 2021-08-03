package ml.northwestwind.moreboots.init.item.boots;

import ml.northwestwind.moreboots.init.ItemInit;
import ml.northwestwind.moreboots.init.item.BootsItem;
import net.minecraft.entity.LivingEntity;
import ml.northwestwind.moreboots.events.LivingEvent;

public class GlidingBootsItem extends BootsItem {
    public GlidingBootsItem() {
        super(ItemInit.ModArmorMaterial.GLIDER, "gliding_boots");
    }

    @Override
    public void onLivingUpdate(LivingEvent.LivingUpdateEvent event) {
        LivingEntity entity = event.getEntityLiving();
        if (entity.getVelocity().x == 0 && entity.getVelocity().z == 0) return;
        if (entity.getVelocity().y < 0.02 && !entity.isSneaking()) {
            entity.setVelocity(entity.getVelocity().multiply(1.1, 0, 1.1).add(0, -0.02, 0));
            entity.velocityDirty = true;
            entity.fallDistance = 0;
        }
    }
}
