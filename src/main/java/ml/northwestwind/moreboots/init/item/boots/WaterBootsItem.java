package ml.northwestwind.moreboots.init.item.boots;

import ml.northwestwind.moreboots.events.LivingEvent;
import ml.northwestwind.moreboots.init.ItemInit;
import ml.northwestwind.moreboots.init.item.BootsItem;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.block.Waterloggable;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.entity.LivingEntity;
import net.minecraft.fluid.FluidState;
import net.minecraft.fluid.LavaFluid;
import net.minecraft.item.ItemStack;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.sound.SoundEvents;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3d;

public class WaterBootsItem extends BootsItem {
    public WaterBootsItem() {
        super(ItemInit.ModArmorMaterial.WATER, "water_boots");
    }

    @Override
    public void onLivingUpdate(final LivingEvent.LivingUpdateEvent event) {
        LivingEntity entity = event.getEntityLiving();
        ItemStack boots = entity.getEquippedStack(EquipmentSlot.FEET);
        Vec3d pos = entity.getPos();
        BlockPos blockPos = new BlockPos(pos);
        BlockPos under = blockPos.down();
        FluidState underneath = entity.world.getFluidState(under);
        BlockState underneathBlock = entity.world.getBlockState(under);
        if (underneath.getFluid() instanceof LavaFluid && !(underneathBlock.getBlock() instanceof Waterloggable)) {
            LavaFluid lava = (LavaFluid) underneath.getFluid();
            if (lava.isStill(underneath)) entity.world.setBlockState(under, Blocks.OBSIDIAN.getDefaultState());
            else entity.world.setBlockState(under, Blocks.COBBLESTONE.getDefaultState());
            entity.playSound(SoundEvents.BLOCK_LAVA_EXTINGUISH, 1, 1);
            if (entity instanceof ServerPlayerEntity) boots.damage(1, entity.world.random, (ServerPlayerEntity) entity);
            else boots.damage(1, entity, entity1 -> entity1.playSound(SoundEvents.ENTITY_ITEM_BREAK, 1, 1));
        } else if (entity.world.getBlockState(blockPos).getBlock().equals(Blocks.FIRE))
            entity.world.setBlockState(blockPos, Blocks.AIR.getDefaultState());
        if (entity.isSubmergedInWater() && boots.getMaxDamage() - boots.getDamage() > 0 && entity.getRandom().nextInt(10) == 0)
            boots.setDamage(Math.max(boots.getDamage() - 2, 0));
    }
}
