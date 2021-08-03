package ml.northwestwind.moreboots.init.item.boots;

import ml.northwestwind.moreboots.init.BlockInit;
import ml.northwestwind.moreboots.init.ItemInit;
import ml.northwestwind.moreboots.init.block.GlowstoneDustBlock;
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

public class GlowstoneBootsItem extends BootsItem {
    public GlowstoneBootsItem() {
        super(ItemInit.ModArmorMaterial.GLOWSTONE, "glowstone_boots");
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
            entity.world.setBlockState(blockPos, BlockInit.GLOWSTONE_DUST.getDefaultState().with(GlowstoneDustBlock.FACING, GlowstoneDustBlock.getRandomDirection()));
            if (entity instanceof ServerPlayerEntity) boots.damage(1, entity.world.random, (ServerPlayerEntity) entity);
            else boots.damage(1, entity, entity1 -> entity1.playSound(SoundEvents.ENTITY_ITEM_BREAK, 1, 1));
        }
    }
}
