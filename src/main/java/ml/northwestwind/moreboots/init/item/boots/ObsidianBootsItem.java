package ml.northwestwind.moreboots.init.item.boots;

import ml.northwestwind.moreboots.events.LivingEquipmentChangeEvent;
import ml.northwestwind.moreboots.init.ItemInit;
import ml.northwestwind.moreboots.init.item.BootsItem;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.item.ItemStack;
import net.minecraft.sound.SoundEvents;

public class ObsidianBootsItem extends BootsItem {
    public ObsidianBootsItem() {
        super(ItemInit.ModArmorMaterial.OBSIDIAN, "obsidian_boots");
    }

    @Override
    public void onLivingEquipmentChange(LivingEquipmentChangeEvent event) {
        EquipmentSlot slot = event.getSlot();
        if (slot.equals(EquipmentSlot.FEET)) return;
        LivingEntity entity = event.getEntityLiving();
        ItemStack equipment = entity.getEquippedStack(slot);
        if (!equipment.isDamageable()) return;
        ItemStack boots = entity.getEquippedStack(EquipmentSlot.FEET);
        ItemStack to = event.getTo();
        ItemStack from = event.getFrom();
        int damage = to.getDamage() - from.getDamage();
        int maxRepair = boots.getMaxDamage() - boots.getDamage();
        if (maxRepair > damage) equipment.setDamage(0);
        else equipment.setDamage(equipment.getDamage() - maxRepair);
        boots.damage(damage, entity, entity1 -> entity1.playSound(SoundEvents.BLOCK_ANVIL_BREAK, 1, 1));
    }
}
