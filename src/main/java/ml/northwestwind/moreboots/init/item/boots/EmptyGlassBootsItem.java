package ml.northwestwind.moreboots.init.item.boots;

import ml.northwestwind.moreboots.init.ItemInit;
import ml.northwestwind.moreboots.init.item.BootsItem;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.PotionUtil;
import net.minecraft.potion.Potions;
import net.minecraft.tag.FluidTags;
import net.minecraft.util.Hand;
import net.minecraft.util.TypedActionResult;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.RaycastContext;
import net.minecraft.world.World;

public class EmptyGlassBootsItem extends BootsItem {
    public EmptyGlassBootsItem() {
        super(ItemInit.ModArmorMaterial.GLASS_EMPTY, "glass_boots_empty");
    }

    @Override
    public TypedActionResult<ItemStack> use(World worldIn, PlayerEntity playerIn, Hand handIn) {
        ItemStack stack = playerIn.getStackInHand(handIn);
        BlockHitResult blockRayTraceResult = raycast(worldIn, playerIn, RaycastContext.FluidHandling.ANY);
        if (!blockRayTraceResult.getType().equals(BlockHitResult.Type.BLOCK)) return TypedActionResult.pass(stack);
        BlockPos pos = blockRayTraceResult.getBlockPos();
        if (!worldIn.canPlayerModifyAt(playerIn, pos)) return TypedActionResult.pass(stack);
        if (worldIn.getFluidState(pos).isIn(FluidTags.WATER)) {
            ItemStack newStack = new ItemStack(ItemInit.GLASS_BOOTS, 1);
            PotionUtil.setPotion(newStack, Potions.WATER);
            playerIn.setStackInHand(handIn, newStack);
            return TypedActionResult.consume(newStack);
        }
        return TypedActionResult.pass(stack);
    }
}
