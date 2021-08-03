package ml.northwestwind.moreboots.init.item.boots;

import ml.northwestwind.moreboots.events.LivingHurtEvent;
import ml.northwestwind.moreboots.init.ItemInit;
import ml.northwestwind.moreboots.init.item.BootsItem;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.damage.DamageSource;

public class CactusBootsItem extends BootsItem {
    public CactusBootsItem() {
        super(ItemInit.ModArmorMaterial.CACTUS, "cactus_boots");
    }

    @Override
    public void onLivingHurt(LivingHurtEvent event) {
        Entity attacker = event.getSource().getAttacker();
        if (event.getSource().isProjectile()) return;
        float amount = event.getAmount();
        if (attacker instanceof LivingEntity) attacker.damage(DamageSource.CACTUS, amount / 3.0f);
    }
}
