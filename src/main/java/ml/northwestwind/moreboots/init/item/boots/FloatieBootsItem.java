package ml.northwestwind.moreboots.init.item.boots;

import ml.northwestwind.moreboots.events.LivingEvent;
import ml.northwestwind.moreboots.init.ItemInit;
import ml.northwestwind.moreboots.init.item.BootsItem;
import net.minecraft.block.BlockState;
import net.minecraft.block.Material;
import net.minecraft.block.ShapeContext;
import net.minecraft.entity.LivingEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.shape.VoxelShape;
import net.minecraft.util.shape.VoxelShapes;
import net.minecraft.world.BlockView;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

public class FloatieBootsItem extends BootsItem {
    public FloatieBootsItem() {
        super(ItemInit.ModArmorMaterial.FLOATIE, "floatie_boots", true);
    }

    @Override
    public void onLivingUpdate(LivingEvent.LivingUpdateEvent event) {
        LivingEntity entity = event.getEntityLiving();
        if (entity.isSubmergedInWater()) {
            entity.setVelocity(entity.getVelocity().add(0, 0.1, 0));
            entity.velocityDirty = true;
        }
    }

    @Override
    public void getCollisionShape(BlockView worldIn, BlockPos pos, ShapeContext context, CallbackInfoReturnable<VoxelShape> cir) {
        BlockState state = worldIn.getBlockState(pos);
        if(state.getMaterial().equals(Material.WATER) && context.isAbove(VoxelShapes.fullCube(), pos, true)) cir.setReturnValue(VoxelShapes.fullCube());
    }
}
