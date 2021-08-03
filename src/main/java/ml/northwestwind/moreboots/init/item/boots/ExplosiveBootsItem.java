package ml.northwestwind.moreboots.init.item.boots;

import ml.northwestwind.moreboots.events.LivingHurtEvent;
import ml.northwestwind.moreboots.init.ItemInit;
import ml.northwestwind.moreboots.init.item.BootsItem;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.damage.EntityDamageSource;
import net.minecraft.predicate.entity.EntityPredicates;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Box;
import net.minecraft.world.explosion.Explosion;

import java.util.List;

public class ExplosiveBootsItem extends BootsItem {
    public ExplosiveBootsItem() {
        super(ItemInit.ModArmorMaterial.EXPLOSIVE, "explosive_boots");
    }

    @Override
    public void onLivingHurt(LivingHurtEvent event) {
        LivingEntity entity = event.getEntityLiving();
        Entity attacker = event.getSource().getAttacker();
        if (!(event.getSource() instanceof EntityDamageSource) || !(attacker instanceof LivingEntity))
            event.setCancelled(true);
        else {
            entity.world.createExplosion(entity, entity.getX(), entity.getBodyY(0.0625D), entity.getZ(), 4.0F, Explosion.DestructionType.BREAK);
            List<Entity> collidedEntities = entity.world.getOtherEntities(entity, new Box(new BlockPos(entity.getPos())).expand(3, 3, 3), EntityPredicates.EXCEPT_SPECTATOR);
            for (Entity collidedEntity : collidedEntities) {
                if (!(collidedEntity instanceof LivingEntity)) continue;
                collidedEntity.damage(DamageSource.explosion(entity), 40);
            }
        }
    }
}
