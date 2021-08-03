package ml.northwestwind.moreboots.init.item.boots;

import ml.northwestwind.moreboots.handler.Utils;
import ml.northwestwind.moreboots.init.ItemInit;
import ml.northwestwind.moreboots.init.item.BootsItem;
import net.minecraft.entity.LivingEntity;
import net.minecraft.util.math.BlockPos;
import ml.northwestwind.moreboots.events.LivingEvent;

public class MushroomBootsItem extends BootsItem {
    public MushroomBootsItem() {
        super(ItemInit.ModArmorMaterial.MUSHROOM, "mushroom_boots");
    }

    @Override
    public void onLivingUpdate(LivingEvent.LivingUpdateEvent event) {
        LivingEntity entity = event.getEntityLiving();
        Utils.changeGroundBlocks(entity, entity.world, new BlockPos(entity.getPos()).up(), 2, Utils.grass, Utils.air);
        Utils.changeGroundBlocks(entity, entity.world, new BlockPos(entity.getPos()), 2, Utils.target, Utils.to);
    }
}
