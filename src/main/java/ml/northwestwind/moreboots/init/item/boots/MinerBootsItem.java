package ml.northwestwind.moreboots.init.item.boots;

import ml.northwestwind.moreboots.events.LivingEvent;
import ml.northwestwind.moreboots.init.ItemInit;
import ml.northwestwind.moreboots.init.item.BootsItem;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.effect.StatusEffects;

public class MinerBootsItem extends BootsItem {
    public MinerBootsItem() {
        super(ItemInit.ModArmorMaterial.MINER, "miner_boots");
    }

    @Override
    public void onLivingUpdate(final LivingEvent.LivingUpdateEvent event) {
        LivingEntity entity = event.getEntityLiving();
        entity.addStatusEffect(new StatusEffectInstance(StatusEffects.HASTE, 20, 1));
        entity.addStatusEffect(new StatusEffectInstance(StatusEffects.SLOWNESS, 20, 1));
    }
}
