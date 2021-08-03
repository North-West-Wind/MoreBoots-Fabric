package ml.northwestwind.moreboots.init.item.boots;

import ml.northwestwind.moreboots.init.ItemInit;
import ml.northwestwind.moreboots.init.item.BootsItem;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.item.ItemStack;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.sound.SoundEvents;
import net.minecraft.util.math.Vec3d;
import ml.northwestwind.moreboots.events.LivingEvent;

public class PrismarineBootsItem extends BootsItem {
    public PrismarineBootsItem() {
        super(ItemInit.ModArmorMaterial.PRISMARINE, "prismarine_boots");
    }

    @Override
    public void onLivingUpdate(LivingEvent.LivingUpdateEvent event) {
        LivingEntity entity = event.getEntityLiving();
        if (!entity.isInSwimmingPose() || !entity.isSubmergedInWater()) return;
        ItemStack boots = entity.getEquippedStack(EquipmentSlot.FEET);
        Vec3d motion = entity.getVelocity();
        Vec3d direction = entity.getRotationVector().multiply(0.15);
        entity.setVelocity(motion.multiply(1.01, 1, 1.01).add(direction));
        if (entity.getRandom().nextInt(100) == 0)
            if (entity instanceof ServerPlayerEntity) boots.damage(1, entity.world.random, (ServerPlayerEntity) entity);
            else boots.damage(1, entity, entity1 -> entity1.playSound(SoundEvents.ENTITY_ITEM_BREAK, 1, 1));
    }
}
