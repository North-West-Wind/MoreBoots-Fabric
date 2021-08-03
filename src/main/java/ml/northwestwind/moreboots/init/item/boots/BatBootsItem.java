package ml.northwestwind.moreboots.init.item.boots;

import ml.northwestwind.moreboots.events.LivingEvent;
import ml.northwestwind.moreboots.init.ItemInit;
import ml.northwestwind.moreboots.init.item.BootsItem;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.item.ItemStack;
import net.minecraft.sound.SoundEvents;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3d;

public class BatBootsItem extends BootsItem {
    public BatBootsItem() {
        super(ItemInit.ModArmorMaterial.BAT, "bat_boots");
    }

    @Override
    public void onLivingUpdate(LivingEvent.LivingUpdateEvent event) {
        LivingEntity entity = event.getEntityLiving();
        ItemStack boots = entity.getEquippedStack(EquipmentSlot.FEET);
        Vec3d upper = entity.getPos().add(0, entity.getHeight() + 1, 0);
        boolean climable = !entity.isOnGround() && !entity.world.isAir(new BlockPos(upper)) && !entity.isSneaking();
        Vec3d motion = entity.getVelocity();
        if (climable) entity.setVelocity(motion.multiply(1, 0, 1));
        if (climable && entity.getRandom().nextInt(100) == 0)
            boots.damage(1, entity, entity1 -> entity1.playSound(SoundEvents.ENTITY_ITEM_BREAK, 1, 1));
    }
}
