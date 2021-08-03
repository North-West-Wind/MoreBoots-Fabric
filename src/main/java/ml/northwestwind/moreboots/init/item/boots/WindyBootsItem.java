package ml.northwestwind.moreboots.init.item.boots;

import ml.northwestwind.moreboots.init.ItemInit;
import ml.northwestwind.moreboots.init.item.BootsItem;
import net.minecraft.entity.LivingEntity;
import net.minecraft.util.math.Vec3d;
import ml.northwestwind.moreboots.events.LivingEvent;

public class WindyBootsItem extends BootsItem {
    public WindyBootsItem() {
        super(ItemInit.ModArmorMaterial.WINDY, "windy_boots");
    }

    @Override
    public void onLivingUpdate(LivingEvent.LivingUpdateEvent event) {
        LivingEntity entity = event.getEntityLiving();
        if (entity.isOnGround() || entity.isSneaking() || entity.isSubmergedInWater() || entity.isInLava()) return;
        Vec3d motion = entity.getVelocity();
        entity.setVelocity(motion.multiply(1.1, 1, 1.1));
    }
}
