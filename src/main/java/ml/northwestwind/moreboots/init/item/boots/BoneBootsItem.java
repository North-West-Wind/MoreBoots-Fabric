package ml.northwestwind.moreboots.init.item.boots;

import ml.northwestwind.moreboots.init.ItemInit;
import ml.northwestwind.moreboots.init.item.BootsItem;
import net.minecraft.block.DirtPathBlock;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.block.BlockState;
import net.minecraft.entity.LivingEntity;
import net.minecraft.util.math.BlockPos;
import ml.northwestwind.moreboots.events.LivingEvent;

import java.util.Iterator;

public class BoneBootsItem extends BootsItem {
    public BoneBootsItem() {
        super(ItemInit.ModArmorMaterial.BONE, "bone_boots");
    }

    @Override
    public void onLivingUpdate(LivingEvent.LivingUpdateEvent event) {
        LivingEntity entity = event.getEntityLiving();
        if (entity.world.isClient) return;
        BlockPos pos = new BlockPos(entity.getPos());
        Iterator<BlockPos> iterator = BlockPos.iterate(pos.add(5, 5, 5), pos.add(-5, -5, -5)).iterator();
        while (iterator.hasNext()) {
            BlockPos blockPos = iterator.next();
            BlockState state = entity.world.getBlockState(blockPos);
            if (!(state.getBlock() instanceof DirtPathBlock))
                state.randomTick((ServerWorld) entity.world, blockPos, entity.getRandom());
        }
    }
}
