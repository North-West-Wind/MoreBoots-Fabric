package ml.northwestwind.moreboots.init.item.boots;

import ml.northwestwind.moreboots.init.ItemInit;
import ml.northwestwind.moreboots.init.item.BootsItem;
import net.minecraft.entity.Entity;
import net.minecraft.entity.ItemEntity;
import net.minecraft.predicate.entity.EntityPredicates;
import net.minecraft.util.math.Box;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import ml.northwestwind.moreboots.events.LivingEvent;

import java.util.List;

public class HopperBootsItem extends BootsItem {
    public HopperBootsItem() {
        super(ItemInit.ModArmorMaterial.HOPPER, "hopper_boots");
    }

    @Override
    public void onLivingUpdate(LivingEvent.LivingUpdateEvent event) {
        LivingEntity entity = event.getEntityLiving();
        if (!(entity instanceof PlayerEntity)) return;
        PlayerEntity player = (PlayerEntity) entity;
        List<Entity> entities = entity.world.getOtherEntities(entity, new Box(entity.getBlockPos()).expand(5), EntityPredicates.EXCEPT_SPECTATOR);
        if (entities.size() < 1) return;
        int available = 0;
        for (ItemStack stack : player.getInventory().main) {
            if (stack.equals(ItemStack.EMPTY)) available++;
        }
        for (Entity ent : entities) {
            if (!(ent instanceof ItemEntity)) continue;
            if (available < 1) break;
            if (player.giveItemStack(((ItemEntity) ent).getStack())) available--;
        }
    }
}
