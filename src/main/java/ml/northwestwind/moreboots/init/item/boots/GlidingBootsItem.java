package ml.northwestwind.moreboots.init.item.boots;

import ml.northwestwind.moreboots.init.ItemInit;
import ml.northwestwind.moreboots.init.item.BootsItem;
import net.minecraft.entity.LivingEntity;
import ml.northwestwind.moreboots.events.LivingEvent;
import net.minecraft.item.ArmorMaterial;

public class GlidingBootsItem extends BootsItem {
    public GlidingBootsItem() {
        this(ItemInit.ModArmorMaterial.GLIDER, "gliding_boots");
    }

    protected GlidingBootsItem(ArmorMaterial material, String registryName) {
        super(material, registryName);
    }

    @Override
    public void onLivingUpdate(LivingEvent.LivingUpdateEvent event) {
        LivingEntity entity = event.getEntityLiving();
        if (entity.getVelocity().x == 0 && entity.getVelocity().z == 0) return;
        if (entity.getVelocity().y < 0.02 && !entity.isSneaking()) {
            entity.setVelocity(entity.getVelocity().multiply(1.05, 0, 1.05).add(0, -0.02, 0));
            entity.velocityDirty = true;
            entity.fallDistance = 0;
        }
    }
}
