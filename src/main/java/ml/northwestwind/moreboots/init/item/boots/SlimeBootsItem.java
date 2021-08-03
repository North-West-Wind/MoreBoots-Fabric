package ml.northwestwind.moreboots.init.item.boots;

import ml.northwestwind.moreboots.events.LivingFallEvent;
import ml.northwestwind.moreboots.init.ItemInit;
import ml.northwestwind.moreboots.init.item.BootsItem;
import net.minecraft.entity.LivingEntity;
import net.minecraft.network.packet.s2c.play.EntityVelocityUpdateS2CPacket;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.sound.SoundEvents;
import net.minecraft.util.math.Vec3d;

public class SlimeBootsItem extends BootsItem {
    public SlimeBootsItem() {
        super(ItemInit.ModArmorMaterial.SLIME, "slime_boots");
    }

    @Override
    public void onLivingFall(LivingFallEvent event) {
        LivingEntity entity = event.getEntityLiving();
        if (entity.isSneaking()) return;
        float distance = event.getDistance();
        if (distance < 1.5) return;
        Vec3d motion = entity.getVelocity();
        entity.setVelocity(motion.x * 1.1, Math.sqrt(distance) / 3.0, motion.z * 1.1);
        entity.velocityDirty = true;
        entity.playSound(SoundEvents.BLOCK_SLIME_BLOCK_HIT, 1, 1);
        event.setCancelled(true);
        if (!entity.world.isClient) ((ServerWorld) entity.world).getServer().getPlayerManager().sendToAll(new EntityVelocityUpdateS2CPacket(entity));
    }
}
