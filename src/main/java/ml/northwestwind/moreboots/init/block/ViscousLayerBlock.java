package ml.northwestwind.moreboots.init.block;

import ml.northwestwind.moreboots.init.ItemInit;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.block.ShapeContext;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.ai.pathing.NavigationType;
import net.minecraft.item.Item;
import net.minecraft.item.ItemPlacementContext;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.state.StateManager;
import net.minecraft.state.property.IntProperty;
import net.minecraft.state.property.Properties;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.util.math.Vec3d;
import net.minecraft.util.shape.VoxelShape;
import net.minecraft.util.shape.VoxelShapes;
import net.minecraft.world.BlockView;
import net.minecraft.world.World;
import net.minecraft.world.WorldAccess;
import net.minecraft.world.WorldView;
import org.jetbrains.annotations.Nullable;

import java.util.Random;

public class ViscousLayerBlock extends Block {
    public static final IntProperty LAYERS = Properties.LAYERS;
    protected static final VoxelShape[] SHAPE_BY_LAYER = new VoxelShape[]{VoxelShapes.empty(), Block.createCuboidShape(0.0D, 0.0D, 0.0D, 16.0D, 2.0D, 16.0D), Block.createCuboidShape(0.0D, 0.0D, 0.0D, 16.0D, 4.0D, 16.0D), Block.createCuboidShape(0.0D, 0.0D, 0.0D, 16.0D, 6.0D, 16.0D), Block.createCuboidShape(0.0D, 0.0D, 0.0D, 16.0D, 8.0D, 16.0D), Block.createCuboidShape(0.0D, 0.0D, 0.0D, 16.0D, 10.0D, 16.0D), Block.createCuboidShape(0.0D, 0.0D, 0.0D, 16.0D, 12.0D, 16.0D), Block.createCuboidShape(0.0D, 0.0D, 0.0D, 16.0D, 14.0D, 16.0D), Block.createCuboidShape(0.0D, 0.0D, 0.0D, 16.0D, 16.0D, 16.0D)};

    public ViscousLayerBlock(Settings settings) {
        super(settings);
    }

    public boolean canPathfindThrough(BlockState state, BlockView world, BlockPos pos, NavigationType type) {
        if (type.equals(NavigationType.LAND)) return state.get(LAYERS) < 5;
        else return false;
    }

    public VoxelShape getOutlineShape(BlockState state, BlockView world, BlockPos pos, ShapeContext context) {
        return SHAPE_BY_LAYER[state.get(LAYERS)];
    }

    public void onEntityCollision(BlockState state, World world, BlockPos pos, Entity entity) {
        if (!(entity instanceof LivingEntity livingEntity && livingEntity.getEquippedStack(EquipmentSlot.FEET).getItem().equals(ItemInit.SUPER_AVIAN_FEET)))
            entity.slowMovement(state, new Vec3d(0.8F, 0.75D, 0.8F));
    }

    public boolean hasSidedTransparency(BlockState state) {
        return true;
    }

    public boolean canPlaceAt(BlockState state, WorldView world, BlockPos pos) {
        BlockState blockstate = world.getBlockState(pos.down());
        if (!blockstate.isOf(Blocks.ICE) && !blockstate.isOf(Blocks.PACKED_ICE) && !blockstate.isOf(Blocks.BARRIER)) {
            if (!blockstate.isOf(Blocks.HONEY_BLOCK) && !blockstate.isOf(Blocks.SOUL_SAND)) {
                return Block.isFaceFullSquare(blockstate.getCollisionShape(world, pos.down()), Direction.UP) || blockstate.isOf(this) && blockstate.get(LAYERS) == 8;
            } else {
                return true;
            }
        } else {
            return false;
        }
    }

    public BlockState getStateForNeighborUpdate(BlockState state, Direction direction, BlockState neighborState, WorldAccess world, BlockPos pos, BlockPos neighborPos) {
        return !state.canPlaceAt(world, pos) ? Blocks.AIR.getDefaultState() : super.getStateForNeighborUpdate(state, direction, neighborState, world, pos, neighborPos);
    }

    public boolean canReplace(BlockState state, ItemPlacementContext context) {
        int i = state.get(LAYERS);
        if (context.getStack().isOf(this.asItem()) && i < 8) {
            if (context.canReplaceExisting()) {
                return context.getSide() == Direction.UP;
            } else {
                return true;
            }
        } else {
            return i == 1;
        }
    }

    @Nullable
    public BlockState getPlacementState(ItemPlacementContext ctx) {
        BlockState blockstate = ctx.getWorld().getBlockState(ctx.getBlockPos());
        if (blockstate.isOf(this)) {
            int i = blockstate.get(LAYERS);
            return blockstate.with(LAYERS, Math.min(8, i + 1));
        } else {
            return super.getPlacementState(ctx);
        }
    }

    protected void appendProperties(StateManager.Builder<Block, BlockState> builder) {
        builder.add(LAYERS);
    }

    @Override
    public Item asItem() {
        return ItemInit.VISCOUS_GOO;
    }

    @Override
    public boolean hasRandomTicks(BlockState p_49921_) {
        return true;
    }

    @Override
    public void randomTick(BlockState state, ServerWorld level, BlockPos pos, Random random) {
        if (random.nextDouble() < 0.1) {
            int layers = state.get(LAYERS);
            if (layers > 1) level.setBlockState(pos, state.with(LAYERS, layers - 1));
            else level.setBlockState(pos, Blocks.AIR.getDefaultState());
        }
    }
}