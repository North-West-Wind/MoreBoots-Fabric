package ml.northwestwind.moreboots.mixins;

import ml.northwestwind.moreboots.init.item.BootsItem;
import net.minecraft.block.AbstractBlock;
import net.minecraft.block.EntityShapeContext;
import net.minecraft.block.ShapeContext;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.entity.LivingEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.shape.VoxelShape;
import net.minecraft.world.BlockView;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(AbstractBlock.AbstractBlockState.class)
public class MixinAbstractBlockState {
    @Inject(at = @At("HEAD"), method = "getCollisionShape(Lnet/minecraft/world/BlockView;Lnet/minecraft/util/math/BlockPos;Lnet/minecraft/block/ShapeContext;)Lnet/minecraft/util/shape/VoxelShape;", cancellable = true)
    public void getCollisionShape(BlockView worldIn, BlockPos pos, ShapeContext context, CallbackInfoReturnable<VoxelShape> cir) {
        if (!(context instanceof EntityShapeContext entCtx)) return;
        if (entCtx.getEntity().isEmpty() || !(entCtx.getEntity().get() instanceof LivingEntity entity)) return;
        ItemStack boots = entity.getEquippedStack(EquipmentSlot.FEET);
        if (boots.getItem() instanceof BootsItem) ((BootsItem) boots.getItem()).getCollisionShape(worldIn, pos, context, cir);
    }
}
