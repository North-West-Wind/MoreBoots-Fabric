package ml.northwestwind.moreboots.init.item.boots;

import ml.northwestwind.moreboots.events.LivingEvent;
import ml.northwestwind.moreboots.events.RenderLivingEvent;
import ml.northwestwind.moreboots.handler.Utils;
import ml.northwestwind.moreboots.init.BlockInit;
import ml.northwestwind.moreboots.init.ItemInit;
import ml.northwestwind.moreboots.init.item.BootsItem;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.client.item.TooltipContext;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemPlacementContext;
import net.minecraft.item.ItemStack;
import net.minecraft.sound.SoundEvents;
import net.minecraft.text.Text;
import net.minecraft.text.TranslatableText;
import net.minecraft.util.Hand;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.util.math.Vec3d;
import net.minecraft.util.math.Vec3f;
import net.minecraft.world.World;

import java.util.List;

public class ViscousBootsItem extends BootsItem {
    public ViscousBootsItem() {
        super(ItemInit.ModArmorMaterial.VISCOUS, "viscous_boots");
    }

    @Override
    public void onLivingUpdate(final LivingEvent.LivingUpdateEvent event) {
        LivingEntity entity = event.getEntityLiving();
        ItemStack boots = entity.getEquippedStack(EquipmentSlot.FEET);
        boolean climable = !Utils.isSurroundedByInvalidBlocks(entity) && !entity.isInLava() && !entity.isTouchingWater() && !entity.isSpectator() && !entity.isOnGround();
        Vec3d motion = entity.getVelocity();
        motion = motion.multiply(1, 0, 1);
        boolean ascending = entity.horizontalCollision;
        boolean descending = !ascending && entity.isSneaking();
        if (climable) {
            if (!ascending && !descending) entity.setVelocity(motion);
            else if (ascending) entity.setVelocity(motion.add(0, 0.2, 0));
            else entity.setVelocity(motion.subtract(0, 0.2, 0));
            if (entity.getRandom().nextInt(Math.max(1, boots.getMaxDamage() - boots.getDamage())) == 0)
                boots.damage(1, entity, entity1 -> entity1.playSound(SoundEvents.ENTITY_ITEM_BREAK, 1, 1));
            entity.fallDistance = 0f;
        }
        BlockState state = entity.world.getBlockState(entity.getBlockPos().down());
        BlockState blockState = entity.world.getBlockState(entity.getBlockPos());
        if (blockState.isOf(BlockInit.VISCOUS_GOO) || !Block.isFaceFullSquare(state.getCollisionShape(entity.world, entity.getBlockPos()), Direction.UP) && (blockState.isAir() || !blockState.canReplace(new ItemPlacementContext((PlayerEntity) entity, Hand.MAIN_HAND, new ItemStack(ItemInit.VISCOUS_GOO), new BlockHitResult(new Vec3d(0.5, 1, 0.5), Direction.UP, entity.getBlockPos().down(), false)))))
            return;
        entity.world.setBlockState(entity.getBlockPos(), BlockInit.VISCOUS_GOO.getDefaultState());
    }

    @Override
    public void preRenderLiving(RenderLivingEvent.Pre<?, ?> event) {
        LivingEntity entity = event.getEntity();
        if (!(entity instanceof PlayerEntity) || !entity.getEquippedStack(EquipmentSlot.FEET).getItem().equals(ItemInit.VISCOUS_BOOTS))
            return;
        BlockPos blockPos = entity.getBlockPos();
        BlockPos closest = blockPos;
        double distanceSqr = 4;
        for (int x = -1; x <= 1; x++) {
            for (int z = -1; z <= 1; z++) {
                if (Math.abs(x) == Math.abs(z)) continue;
                BlockPos adjacent = blockPos.add(x, 0, z);
                double newDistSqr = entity.squaredDistanceTo(adjacent.getX(), adjacent.getY(), adjacent.getZ());
                if (newDistSqr < distanceSqr && entity.world.getBlockState(adjacent).isOpaque()) {
                    closest = adjacent;
                    distanceSqr = newDistSqr;
                }
            }
        }
        MatrixStack matrix = event.getMatrixStack();
        matrix.push();
        if (blockPos.equals(closest)) return;
        BlockPos subtracted = blockPos.subtract(closest);
        if (subtracted.getX() == 1) matrix.multiply(Vec3f.NEGATIVE_Z.getDegreesQuaternion(90));
        else if (subtracted.getX() == -1) matrix.multiply(Vec3f.POSITIVE_Z.getDegreesQuaternion(90));
        else if (subtracted.getZ() == 1) matrix.multiply(Vec3f.POSITIVE_X.getDegreesQuaternion(90));
        else if (subtracted.getZ() == -1) matrix.multiply(Vec3f.NEGATIVE_X.getDegreesQuaternion(90));
        matrix.translate(0, -0.25, 0);
    }

    @Override
    public void postRenderLiving(RenderLivingEvent.Post<?, ?> event) {
        event.getMatrixStack().pop();
    }

    @Override
    @Environment(EnvType.CLIENT)
    public void appendTooltip(ItemStack stack, World worldIn, List<Text> tooltip, TooltipContext flagIn) {
        super.appendTooltip(stack, worldIn, tooltip, flagIn);
        tooltip.add(new TranslatableText("credit.moreboots." + registryName));
    }
}