package ml.northwestwind.moreboots.init.item.boots;

import ml.northwestwind.moreboots.events.LivingHurtEvent;
import ml.northwestwind.moreboots.init.ItemInit;
import ml.northwestwind.moreboots.init.item.BootsItem;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LightningEntity;
import net.minecraft.entity.LivingEntity;

public class LightningBootsItem extends BootsItem {
    public LightningBootsItem() {
        super(ItemInit.ModArmorMaterial.LIGHTNING, "lightning_boots");
    }

    @Override
    public void onLivingHurt(LivingHurtEvent event) {
        LivingEntity entity = event.getEntityLiving();
        LightningEntity lightning = new LightningEntity(EntityType.LIGHTNING_BOLT, entity.world);
        lightning.setPos(entity.getX(), entity.getY(), entity.getZ());
        entity.world.spawnEntity(lightning);
    }

    @Override
    public void onLivingAttack(LivingHurtEvent event) {
        onLivingHurt(event);
    }
}
