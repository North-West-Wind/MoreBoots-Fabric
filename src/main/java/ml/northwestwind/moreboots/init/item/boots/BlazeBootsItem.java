package ml.northwestwind.moreboots.init.item.boots;

import ml.northwestwind.moreboots.events.LivingHurtEvent;
import ml.northwestwind.moreboots.init.ItemInit;
import ml.northwestwind.moreboots.init.item.BootsItem;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.entity.LivingEntity;
import net.minecraft.util.math.BlockPos;
import ml.northwestwind.moreboots.events.LivingEvent;

public class BlazeBootsItem extends BootsItem {
    public BlazeBootsItem() {
        super(ItemInit.ModArmorMaterial.BLAZE, "blaze_boots");
    }

    @Override
    public void onLivingHurt(LivingHurtEvent event) {
        DamageSource source = event.getSource();
        if (source.equals(DamageSource.IN_FIRE) || source.equals(DamageSource.LAVA) || source.equals(DamageSource.ON_FIRE))
            event.setCancelled(true);
    }

    @Override
    public void onLivingUpdate(LivingEvent.LivingUpdateEvent event) {
        LivingEntity entity = event.getEntityLiving();
        BlockPos blockPos = entity.getBlockPos();
        BlockPos under = blockPos.down();
        BlockState underneath = entity.world.getBlockState(under);
        if (underneath.isOpaque() && entity.world.isAir(blockPos))
            entity.world.setBlockState(blockPos, Blocks.FIRE.getDefaultState());
    }
}
