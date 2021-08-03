package ml.northwestwind.moreboots.init.item.boots;

import ml.northwestwind.moreboots.init.ItemInit;
import ml.northwestwind.moreboots.init.item.BootsItem;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.effect.StatusEffectInstance;
import ml.northwestwind.moreboots.events.LivingEvent;

import java.util.Collection;

public class MilkBootsItem extends BootsItem {
    public MilkBootsItem() {
        super(ItemInit.ModArmorMaterial.MILK, "milk_boots");
    }

    @Override
    public void onLivingUpdate(LivingEvent.LivingUpdateEvent event) {
        LivingEntity entity = event.getEntityLiving();
        Collection<StatusEffectInstance> potions = entity.getStatusEffects();
        for (StatusEffectInstance effect : potions)
            if (!effect.getEffectType().isBeneficial()) {
                entity.removeStatusEffect(effect.getEffectType());
                break;
            }
    }
}
