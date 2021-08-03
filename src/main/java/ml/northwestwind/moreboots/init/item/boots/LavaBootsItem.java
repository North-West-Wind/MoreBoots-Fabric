package ml.northwestwind.moreboots.init.item.boots;

import ml.northwestwind.moreboots.init.ItemInit;
import ml.northwestwind.moreboots.init.item.BootsItem;
import net.minecraft.block.Block;
import net.minecraft.block.Blocks;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.fluid.FluidState;
import net.minecraft.fluid.WaterFluid;
import net.minecraft.item.ItemStack;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.sound.SoundEvents;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3d;
import ml.northwestwind.moreboots.events.LivingEvent;

public class LavaBootsItem extends BootsItem {
    public LavaBootsItem() {
        super(ItemInit.ModArmorMaterial.LAVA, "lava_boots");
    }

    @Override
    public void onLivingUpdate(LivingEvent.LivingUpdateEvent event) {
        LivingEntity entity = event.getEntityLiving();
        ItemStack boots = entity.getEquippedStack(EquipmentSlot.FEET);
        Vec3d pos = entity.getPos();
        BlockPos blockPos = new BlockPos(pos);
        BlockPos under = blockPos.down();
        Block block = entity.world.getBlockState(blockPos).getBlock();
        FluidState underneath = entity.world.getFluidState(under);
        if (underneath.getFluid() instanceof WaterFluid) {
            WaterFluid water = (WaterFluid) underneath.getFluid();
            if (water.isStill(underneath))
                entity.world.setBlockState(under, Blocks.STONE.getDefaultState());
            else entity.world.setBlockState(under, Blocks.COBBLESTONE.getDefaultState());
            entity.playSound(SoundEvents.BLOCK_LAVA_EXTINGUISH, 1, 1);
            if (entity instanceof ServerPlayerEntity) boots.damage(1, entity.world.random, (ServerPlayerEntity) entity);
            else boots.damage(1, entity, entity1 -> entity1.playSound(SoundEvents.ENTITY_ITEM_BREAK, 1, 1));
        } else if (block.equals(Blocks.ICE) || block.equals(Blocks.FROSTED_ICE))
            entity.world.setBlockState(blockPos, Blocks.WATER.getDefaultState());
        if (entity.isInLava() && boots.getMaxDamage() - boots.getDamage() > 0 && entity.getRandom().nextInt(10) == 0) {
            boots.setDamage(Math.max(boots.getDamage() - 2, 0));
        }
    }
}
