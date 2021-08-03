package ml.northwestwind.moreboots.init.item.boots;

import ml.northwestwind.moreboots.events.LivingFallEvent;
import ml.northwestwind.moreboots.init.ItemInit;
import ml.northwestwind.moreboots.init.item.BootsItem;
import net.minecraft.entity.LivingEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.shape.VoxelShapes;
import ml.northwestwind.moreboots.events.LivingEvent;

public class DownwarpBootsItem extends BootsItem {
    public DownwarpBootsItem() {
        super(ItemInit.ModArmorMaterial.DOWNWARP, "downwarp_boots");
    }

    @Override
    public void onLivingUpdate(LivingEvent.LivingUpdateEvent event) {
        LivingEntity entity = event.getEntityLiving();
        BlockPos pos = entity.getBlockPos();
        if (!entity.isOnGround()) {
            boolean isThisAirBlock = entity.world.isAir(pos) || entity.world.getBlockState(pos).getCollisionShape(entity.world, pos).equals(VoxelShapes.empty());
            while (isThisAirBlock && pos.getY() > 0) {
                pos = pos.down();
                isThisAirBlock = entity.world.isAir(pos) || entity.world.getBlockState(pos).getCollisionShape(entity.world, pos).equals(VoxelShapes.empty());
            }
            if (pos.getY() <= 0 || entity.getY() - pos.up().getY() < 0.2) return;
            entity.fallDistance = 0;
            entity.setPos(entity.getX(), pos.getY() + 1, entity.getZ());
        }
    }

    @Override
    public void onLivingFall(LivingFallEvent event) {
        event.setCancelled(true);
    }
}
