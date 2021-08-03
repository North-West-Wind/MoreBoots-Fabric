package ml.northwestwind.moreboots.init.item.boots;

import ml.northwestwind.moreboots.init.BlockInit;
import ml.northwestwind.moreboots.init.ItemInit;
import ml.northwestwind.moreboots.init.block.RedstoneDustBlock;
import ml.northwestwind.moreboots.init.item.BootsItem;
import net.minecraft.block.BlockState;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.item.ItemStack;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.sound.SoundEvents;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3d;
import ml.northwestwind.moreboots.events.LivingEvent;

public class RedstoneBootsItem extends BootsItem {
    public RedstoneBootsItem() {
        super(ItemInit.ModArmorMaterial.REDSTONE, "redstone_boots");
    }

    @Override
    public void onLivingUpdate(LivingEvent.LivingUpdateEvent event) {
        LivingEntity entity = event.getEntityLiving();
        ItemStack boots = entity.getEquippedStack(EquipmentSlot.FEET);
        Vec3d pos = entity.getPos();
        BlockPos blockPos = new BlockPos(pos);
        BlockPos under = blockPos.down();
        BlockState underneath = entity.world.getBlockState(under);
        if (underneath.isOpaque() && entity.world.isAir(blockPos) && entity.getRandom().nextInt(100) == 0) {
            entity.world.setBlockState(blockPos, BlockInit.REDSTONE_DUST.getDefaultState().with(RedstoneDustBlock.FACING, RedstoneDustBlock.getRandomDirection()));
            if (entity instanceof ServerPlayerEntity) boots.damage(1, entity.world.random, (ServerPlayerEntity) entity);
            else boots.damage(1, entity, entity1 -> entity1.playSound(SoundEvents.ENTITY_ITEM_BREAK, 1, 1));
        }
    }
}
