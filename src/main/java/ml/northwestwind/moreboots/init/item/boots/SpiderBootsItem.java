package ml.northwestwind.moreboots.init.item.boots;

import ml.northwestwind.moreboots.handler.Utils;
import ml.northwestwind.moreboots.init.ItemInit;
import ml.northwestwind.moreboots.init.item.BootsItem;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.item.ItemStack;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.sound.SoundEvents;
import net.minecraft.util.math.Vec3d;
import ml.northwestwind.moreboots.events.LivingEvent;

public class SpiderBootsItem extends BootsItem {
    public SpiderBootsItem() {
        super(ItemInit.ModArmorMaterial.SPIDER, "spider_boots");
    }

    @Override
    public void onLivingUpdate(final LivingEvent.LivingUpdateEvent event) {
        LivingEntity entity = event.getEntityLiving();
        ItemStack boots = entity.getEquippedStack(EquipmentSlot.FEET);
        boolean climable = !Utils.isSurroundedByInvalidBlocks(entity) && !entity.isInLava() && !entity.isSubmergedInWater() && !entity.isSpectator() && !entity.isOnGround();
        Vec3d motion = entity.getVelocity();
        motion = motion.multiply(1, 0, 1);
        boolean ascending = entity.horizontalCollision;
        boolean descending = !ascending && entity.isSneaking();
        if (climable) {
            if (!ascending && !descending) entity.setVelocity(motion);
            else if (ascending) entity.setVelocity(motion.add(0, 0.2, 0));
            else entity.setVelocity(motion.subtract(0, 0.2, 0));
            if (entity.getRandom().nextInt(100) == 0)
                if (entity instanceof ServerPlayerEntity) boots.damage(1, entity.world.random, (ServerPlayerEntity) entity);
                else boots.damage(1, entity, entity1 -> entity1.playSound(SoundEvents.ENTITY_ITEM_BREAK, 1, 1));
            entity.fallDistance = 0f;
        }
    }
}
