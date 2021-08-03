package ml.northwestwind.moreboots.init.item.boots;

import ml.northwestwind.moreboots.events.LivingHurtEvent;
import ml.northwestwind.moreboots.events.LivingEvent;
import ml.northwestwind.moreboots.init.ItemInit;
import ml.northwestwind.moreboots.init.item.BootsItem;
import net.minecraft.block.BlockState;
import net.minecraft.block.Material;
import net.minecraft.block.ShapeContext;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.shape.VoxelShape;
import net.minecraft.util.shape.VoxelShapes;
import net.minecraft.world.BlockView;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

public class StriderBootsItem extends BootsItem {
    public StriderBootsItem() {
        super(ItemInit.ModArmorMaterial.STRIDER, "strider_boots", true);
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
        entity.extinguish();
        if (entity.isInLava()) {
            entity.setVelocity(entity.getVelocity().add(0, 0.1, 0));
            entity.velocityDirty = true;
        }
    }

    @Override
    public void getCollisionShape(BlockView worldIn, BlockPos pos, ShapeContext context, CallbackInfoReturnable<VoxelShape> cir) {
        BlockState state = worldIn.getBlockState(pos);
        if(state.getMaterial().equals(Material.LAVA) && context.isAbove(VoxelShapes.fullCube(), pos, true)) cir.setReturnValue(VoxelShapes.fullCube());
    }
}
