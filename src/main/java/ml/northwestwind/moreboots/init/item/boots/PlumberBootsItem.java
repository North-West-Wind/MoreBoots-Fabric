package ml.northwestwind.moreboots.init.item.boots;

import ml.northwestwind.moreboots.Reference;
import ml.northwestwind.moreboots.events.LivingEvent;
import ml.northwestwind.moreboots.init.ItemInit;
import ml.northwestwind.moreboots.init.item.BootsItem;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.network.packet.s2c.play.EntityVelocityUpdateS2CPacket;
import net.minecraft.predicate.entity.EntityPredicates;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.math.Vec3d;
import org.apache.logging.log4j.LogManager;

import java.util.List;

public class PlumberBootsItem extends BootsItem {
    public PlumberBootsItem() {
        super(ItemInit.ModArmorMaterial.PLUMBER, "plumber_boots");
    }

    @Override
    public void onLivingUpdate(LivingEvent.LivingUpdateEvent event) {
        LivingEntity entity = event.getEntityLiving();
        List<Entity> collidedEntities = entity.world.getOtherEntities(entity, entity.getBoundingBox(), EntityPredicates.EXCEPT_SPECTATOR);
        Vec3d motion = entity.getVelocity();
        boolean stomped = false;
        for (Entity collidedEntity : collidedEntities) {
            if (!(collidedEntity instanceof LivingEntity)) continue;
            if (collidedEntity.getY() + collidedEntity.getHeight() < entity.getY() || collidedEntity.getEyeY() > entity.getY())
                continue;
            boolean flag = collidedEntity.damage(Reference.STOMP, 4);
            if (!stomped) stomped = flag;
        }
        if (stomped) {
            entity.setVelocity(motion.multiply(1, 0, 1).add(0, 0.5, 0));
            entity.velocityDirty = true;
            if (!entity.world.isClient) ((ServerWorld) entity.world).getServer().getPlayerManager().sendToAll(new EntityVelocityUpdateS2CPacket(entity));
        }
    }
}
