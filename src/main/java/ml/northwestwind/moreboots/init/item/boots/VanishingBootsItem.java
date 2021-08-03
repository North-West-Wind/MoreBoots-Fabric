package ml.northwestwind.moreboots.init.item.boots;

import ml.northwestwind.moreboots.init.ItemInit;
import ml.northwestwind.moreboots.init.item.BootsItem;
import net.minecraft.block.BlockState;
import net.minecraft.block.Material;
import net.minecraft.block.ShapeContext;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.shape.VoxelShape;
import net.minecraft.util.shape.VoxelShapes;
import net.minecraft.world.BlockView;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

public class VanishingBootsItem extends BootsItem {
    public VanishingBootsItem() {
        super(ItemInit.ModArmorMaterial.VANISHING, "vanishing_boots");
    }

    @Override
    public void getCollisionShape(BlockView worldIn, BlockPos pos, ShapeContext context, CallbackInfoReturnable<VoxelShape> cir) {
        BlockState state = worldIn.getBlockState(pos);
        if (!state.isOpaque() || state.getMaterial().equals(Material.GLASS)) cir.setReturnValue(VoxelShapes.empty());
    }
}
