package ml.northwestwind.moreboots.init.item.boots;

import ml.northwestwind.moreboots.events.LivingEvent;
import ml.northwestwind.moreboots.events.LivingFallEvent;
import ml.northwestwind.moreboots.events.RenderLivingEvent;
import ml.northwestwind.moreboots.handler.Utils;
import ml.northwestwind.moreboots.init.BlockInit;
import ml.northwestwind.moreboots.init.EffectInit;
import ml.northwestwind.moreboots.init.ItemInit;
import ml.northwestwind.moreboots.init.item.BootsItem;
import ml.northwestwind.moreboots.mixins.MixinLivingEntityAccessor;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.Material;
import net.minecraft.block.ShapeContext;
import net.minecraft.client.item.TooltipContext;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemPlacementContext;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.sound.SoundEvents;
import net.minecraft.text.Text;
import net.minecraft.text.TranslatableText;
import net.minecraft.util.Hand;
import net.minecraft.util.Identifier;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.util.math.Vec3d;
import net.minecraft.util.math.Vec3f;
import net.minecraft.util.shape.VoxelShape;
import net.minecraft.util.shape.VoxelShapes;
import net.minecraft.world.BlockView;
import net.minecraft.world.World;
import net.minecraft.world.explosion.Explosion;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.util.List;

public class SuperAvianFeetItem extends BootsItem {
    public SuperAvianFeetItem() {
        super(ItemInit.ModArmorMaterial.SUPER_AVIAN, "super_avian_feet");
    }

    @Override
    public void onLivingFall(final LivingFallEvent event) {
        LivingEntity entity = event.getEntityLiving();
        float distance = event.getDistance();
        if (entity.world.isClient) return;
        event.setCancelled(true);
        if (distance > 10) entity.playSound(SoundEvents.ENTITY_PLAYER_BIG_FALL, 1, 1);
        else if (distance > 3) entity.playSound(SoundEvents.ENTITY_PLAYER_SMALL_FALL, 1, 1);
    }

    @Override
    public void onLivingUpdate(LivingEvent.LivingUpdateEvent event) {
        LivingEntity entity = event.getEntityLiving();
        ItemStack boots = entity.getEquippedStack(EquipmentSlot.FEET);
        if (!entity.hasStatusEffect(StatusEffects.SPEED) || entity.getStatusEffect(StatusEffects.SPEED).getAmplifier() < 1)
            entity.addStatusEffect(new StatusEffectInstance(StatusEffects.SPEED, 5, 0, false, false, false));
        if (!entity.isSneaking()) {
            double addToY = 0;
            if (entity instanceof PlayerEntity && ((MixinLivingEntityAccessor) entity).isJumping()) addToY = 0.02;
            else if (entity.getVelocity().getY() < 0) addToY = -0.02;
            if (addToY != 0) {
                entity.setVelocity(entity.getVelocity().multiply(1.05, 0, 1.05).add(0, addToY, 0));
                entity.velocityDirty = true;
                entity.fallDistance = 0;
            }
        } else if (entity.isOnGround()) {
            NbtCompound tag = boots.getOrCreateNbt();
            long tickSneak = tag.getLong("tickSneak");
            tag.putLong("tickSneak", tag.getLong("tickSneak") + 1);
            tickSneak += 1;
            if (entity instanceof PlayerEntity && !entity.world.isClient)
                ((PlayerEntity) entity).sendMessage(new TranslatableText("message.moreboots.building_speed", tickSneak), true);
            if (tickSneak >= 864000 && !entity.isSpectator()) {
                Vec3d pos = entity.getPos();
                tag.putLong("tickSneak", 0);
                boots.setDamage(boots.getMaxDamage());
                entity.world.createExplosion(entity, pos.x, entity.getBodyY(-0.0625D), pos.z, 10.0F, Explosion.DestructionType.BREAK);
                entity.setVelocity(entity.getVelocity().add(0, 0.01 * 864000, 0));
                if (entity instanceof PlayerEntity && !entity.world.isClient) {
                    MinecraftServer server = entity.world.getServer();
                    ServerPlayerEntity serverPlayerEntity = (ServerPlayerEntity) entity;
                    serverPlayerEntity.getAdvancementTracker().grantCriterion(server.getAdvancementLoader().get(new Identifier("moreboots", "moreboots/twelve_hours")), "twelve_hours");
                }
            }
            boots.setNbt(tag);
        }
        boolean climable = !Utils.isSurroundedByInvalidBlocks(entity) && !entity.isInLava() && !entity.isTouchingWater() && !entity.isSpectator() && !entity.isOnGround();
        Vec3d motion = entity.getVelocity();
        motion = motion.multiply(1, 0, 1);
        boolean ascending = entity.horizontalCollision;
        boolean descending = !ascending && entity.isSneaking();
        if (climable) {
            if (!ascending && !descending) entity.setVelocity(motion);
            else if (ascending) entity.setVelocity(motion.add(0, 0.2, 0));
            else entity.setVelocity(motion.subtract(0, 0.2, 0));
            entity.fallDistance = 0f;
        }
        BlockState state = entity.world.getBlockState(entity.getBlockPos().down());
        if (state.getBlock().getSlipperiness() > 0.6f) entity.addStatusEffect(new StatusEffectInstance(EffectInit.SLIPPERINESS, 600, 1, false, false));
        BlockState blockState = entity.world.getBlockState(entity.getBlockPos());
        if (blockState.isOf(BlockInit.VISCOUS_GOO) || !Block.isFaceFullSquare(state.getCollisionShape(entity.world, entity.getBlockPos()), Direction.UP) && (blockState.isAir() || !blockState.canReplace(new ItemPlacementContext((PlayerEntity) entity, Hand.MAIN_HAND, new ItemStack(ItemInit.VISCOUS_GOO), new BlockHitResult(new Vec3d(0.5, 1, 0.5), Direction.UP, entity.getBlockPos().down(), false)))))
            return;
        entity.world.setBlockState(entity.getBlockPos(), BlockInit.VISCOUS_GOO.getDefaultState());
    }

    @Override
    public void onLivingJump(LivingEvent.LivingJumpEvent event) {
        LivingEntity entity = event.getEntityLiving();
        if (entity.isSneaking()) return;
        ItemStack boots = entity.getEquippedStack(EquipmentSlot.FEET);
        Vec3d motion = entity.getVelocity();
        NbtCompound tag = boots.getOrCreateNbt();
        entity.setVelocity(motion.add(0, 0.3 + 0.01 * tag.getLong("tickSneak"), 0));
        tag.putLong("tickSneak", 0);
        boots.setNbt(tag);
    }

    @Override
    public void getCollisionShape(BlockView worldIn, BlockPos pos, ShapeContext context, CallbackInfoReturnable<VoxelShape> cir) {
        BlockState state = worldIn.getBlockState(pos);
        if (state.getMaterial().equals(Material.WATER) && context.isAbove(VoxelShapes.fullCube(), pos, true))
            cir.setReturnValue(VoxelShapes.fullCube());
    }

    @Override
    public void preRenderLiving(RenderLivingEvent.Pre<?, ?> event) {
        LivingEntity entity = event.getEntity();
        MatrixStack matrix = event.getMatrixStack();
        matrix.push();
        if (!(entity instanceof PlayerEntity)) return;
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