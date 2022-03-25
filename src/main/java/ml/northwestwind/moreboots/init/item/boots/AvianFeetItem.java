package ml.northwestwind.moreboots.init.item.boots;

import ml.northwestwind.moreboots.events.LivingEvent;
import ml.northwestwind.moreboots.init.ItemInit;
import ml.northwestwind.moreboots.init.item.BootsItem;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.effect.StatusEffects;

public class AvianFeetItem extends BootsItem {
    public AvianFeetItem() {
        super(ItemInit.ModArmorMaterial.AVIAN, "avian_feet");
    }

    @Override
    public void onLivingUpdate(LivingEvent.LivingUpdateEvent event) {
        LivingEntity entity = event.getEntityLiving();
        if (!entity.hasStatusEffect(StatusEffects.SPEED) || entity.getStatusEffect(StatusEffects.SPEED).getAmplifier() < 1)
            entity.addStatusEffect(new StatusEffectInstance(StatusEffects.SPEED, 5, 0, false, false, false));
        if (entity.getVelocity().getX() == 0 && entity.getVelocity().getZ() == 0) return;
        if (entity.getVelocity().getY() < 0.02 && !entity.isSneaking()) {
            entity.setVelocity(entity.getVelocity().multiply(1.05, 0, 1.05).add(0, -0.02, 0));
            entity.velocityDirty = true;
            entity.fallDistance = 0;
        }
    }

    @Override
    public void onLivingJump(LivingEvent.LivingJumpEvent event) {
        LivingEntity entity = event.getEntityLiving();
        entity.setVelocity(entity.getVelocity().add(0, 0.15, 0));
    }
}