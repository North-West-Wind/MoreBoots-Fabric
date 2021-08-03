package ml.northwestwind.moreboots.init.block;

import net.minecraft.block.*;
import net.minecraft.item.Item;
import net.minecraft.item.ItemPlacementContext;
import net.minecraft.item.Items;
import net.minecraft.state.StateManager;
import net.minecraft.state.property.DirectionProperty;
import net.minecraft.util.BlockMirror;
import net.minecraft.util.BlockRotation;
import net.minecraft.util.function.BooleanBiFunction;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.util.shape.VoxelShape;
import net.minecraft.util.shape.VoxelShapes;
import net.minecraft.world.BlockView;
import net.minecraft.world.World;
import net.minecraft.world.WorldView;

import java.util.Random;
import java.util.stream.Stream;

public class RedstoneDustBlock extends Block implements Waterloggable {
    public static final DirectionProperty FACING = HorizontalFacingBlock.FACING;

    private static final VoxelShape SHAPE_N = Stream.of(
            Block.createCuboidShape(2, 0, 2, 4, 1, 4),
            Block.createCuboidShape(3, 0, 12, 4, 1, 13),
            Block.createCuboidShape(13, 0, 8, 14, 1, 9),
            Block.createCuboidShape(13, 0, 1, 14, 1, 2),
            Block.createCuboidShape(9, 0, 4, 11, 1, 6),
            Block.createCuboidShape(7, 0, 3, 8, 1, 4),
            Block.createCuboidShape(4, 0, 6, 5, 1, 7),
            Block.createCuboidShape(1, 0, 9, 2, 1, 10),
            Block.createCuboidShape(6, 0, 9, 8, 1, 11),
            Block.createCuboidShape(11, 0, 11, 12, 1, 12),
            Block.createCuboidShape(13, 0, 13, 15, 1, 15),
            Block.createCuboidShape(5, 0, 14, 6, 1, 15),
            Block.createCuboidShape(9, 0, 13, 10, 1, 14)
    ).reduce((v1, v2) -> VoxelShapes.combine(v1, v2, BooleanBiFunction.OR)).get();
    private static final VoxelShape SHAPE_W = Stream.of(
            Block.createCuboidShape(2, 0, 12, 4, 1, 14),
            Block.createCuboidShape(12, 0, 12, 13, 1, 13),
            Block.createCuboidShape(8, 0, 2, 9, 1, 3),
            Block.createCuboidShape(1, 0, 2, 2, 1, 3),
            Block.createCuboidShape(4, 0, 5, 6, 1, 7),
            Block.createCuboidShape(3, 0, 8, 4, 1, 9),
            Block.createCuboidShape(6, 0, 11, 7, 1, 12),
            Block.createCuboidShape(9, 0, 14, 10, 1, 15),
            Block.createCuboidShape(9, 0, 8, 11, 1, 10),
            Block.createCuboidShape(11, 0, 4, 12, 1, 5),
            Block.createCuboidShape(13, 0, 1, 15, 1, 3),
            Block.createCuboidShape(14, 0, 10, 15, 1, 11),
            Block.createCuboidShape(13, 0, 6, 14, 1, 7)
    ).reduce((v1, v2) -> VoxelShapes.combine(v1, v2, BooleanBiFunction.OR)).get();
    private static final VoxelShape SHAPE_S = Stream.of(
            Block.createCuboidShape(12, 0, 12, 14, 1, 14),
            Block.createCuboidShape(12, 0, 3, 13, 1, 4),
            Block.createCuboidShape(2, 0, 7, 3, 1, 8),
            Block.createCuboidShape(2, 0, 14, 3, 1, 15),
            Block.createCuboidShape(5, 0, 10, 7, 1, 12),
            Block.createCuboidShape(8, 0, 12, 9, 1, 13),
            Block.createCuboidShape(11, 0, 9, 12, 1, 10),
            Block.createCuboidShape(14, 0, 6, 15, 1, 7),
            Block.createCuboidShape(8, 0, 5, 10, 1, 7),
            Block.createCuboidShape(4, 0, 4, 5, 1, 5),
            Block.createCuboidShape(1, 0, 1, 3, 1, 3),
            Block.createCuboidShape(10, 0, 1, 11, 1, 2),
            Block.createCuboidShape(6, 0, 2, 7, 1, 3)
    ).reduce((v1, v2) -> VoxelShapes.combine(v1, v2, BooleanBiFunction.OR)).get();
    private static final VoxelShape SHAPE_E = Stream.of(
            Block.createCuboidShape(12, 0, 2, 14, 1, 4),
            Block.createCuboidShape(3, 0, 3, 4, 1, 4),
            Block.createCuboidShape(7, 0, 13, 8, 1, 14),
            Block.createCuboidShape(14, 0, 13, 15, 1, 14),
            Block.createCuboidShape(10, 0, 9, 12, 1, 11),
            Block.createCuboidShape(12, 0, 7, 13, 1, 8),
            Block.createCuboidShape(9, 0, 4, 10, 1, 5),
            Block.createCuboidShape(6, 0, 1, 7, 1, 2),
            Block.createCuboidShape(5, 0, 6, 7, 1, 8),
            Block.createCuboidShape(4, 0, 11, 5, 1, 12),
            Block.createCuboidShape(1, 0, 13, 3, 1, 15),
            Block.createCuboidShape(1, 0, 5, 2, 1, 6),
            Block.createCuboidShape(2, 0, 9, 3, 1, 10)
    ).reduce((v1, v2) -> VoxelShapes.combine(v1, v2, BooleanBiFunction.OR)).get();

    public RedstoneDustBlock(Settings settings) {
        super(settings);
        this.setDefaultState(this.getStateManager().getDefaultState().with(FACING, Direction.NORTH));
    }

    public static Direction getRandomDirection() {
        int random = new Random().nextInt(4);
        switch (random) {
            case 1:
                return Direction.EAST;
            case 2:
                return Direction.SOUTH;
            case 3:
                return Direction.WEST;
            default:
                return Direction.NORTH;
        }
    }

    public VoxelShape getOutlineShape(BlockState state, BlockView worldIn, BlockPos pos, ShapeContext context) {
        switch (state.get(FACING)) {
            case EAST:
                return SHAPE_E;
            case SOUTH:
                return SHAPE_S;
            case WEST:
                return SHAPE_W;
            case NORTH:
                return SHAPE_N;
            default:
                int random = new Random().nextInt(4);
                switch (random) {
                    case 1:
                        return SHAPE_E;
                    case 2:
                        return SHAPE_S;
                    case 3:
                        return SHAPE_W;
                    default:
                        return SHAPE_N;
                }
        }
    }

    @Override
    public boolean canPlaceAt(BlockState state, WorldView world, BlockPos pos) {
        return state.isSideSolidFullSquare(world, pos.down(), Direction.UP);
    }

    @Override
    public BlockState getPlacementState(ItemPlacementContext context) {
        return this.getDefaultState().with(FACING, context.getPlayerLookDirection().getOpposite());
    }

    @Override
    public BlockState rotate(BlockState state, BlockRotation rot) {
        return state.with(FACING, rot.rotate(state.get(FACING)));
    }

    @Override
    public BlockState mirror(BlockState state, BlockMirror mirrorIn) {
        return state.rotate(mirrorIn.getRotation(state.get(FACING)));
    }

    @Override
    protected void appendProperties(StateManager.Builder<Block, BlockState> builder) {
        builder.add(FACING);
    }

    @Override
    public Item asItem() {
        return Items.REDSTONE;
    }

    @Override
    public int getWeakRedstonePower(BlockState blockState, BlockView blockAccess, BlockPos pos, Direction side) {
        return 15;
    }

    private void notifyWireNeighborsOfStateChange(World worldIn, BlockPos pos) {
        if (worldIn.getBlockState(pos).isOf(this)) {
            worldIn.updateNeighbors(pos, this);

            for (Direction direction : Direction.values()) {
                worldIn.updateNeighbors(pos.offset(direction), this);
            }

        }
    }

    @Override
    public void onBlockAdded(BlockState state, World worldIn, BlockPos pos, BlockState oldState, boolean isMoving) {
        if (!oldState.isOf(state.getBlock()) && !worldIn.isClient) {
            for (Direction direction : Direction.values()) {
                worldIn.updateNeighbors(pos.offset(direction), this);
            }
        }
    }

    @Override
    public void onStateReplaced(BlockState state, World worldIn, BlockPos pos, BlockState newState, boolean isMoving) {
        if (!isMoving && !state.isOf(newState.getBlock())) {
            super.onStateReplaced(state, worldIn, pos, newState, isMoving);
            if (!worldIn.isClient) {
                for (Direction direction : Direction.values()) {
                    worldIn.updateNeighbors(pos.offset(direction), this);
                }
            }
        }
    }

    @Override
    public boolean emitsRedstonePower(BlockState state) {
        return true;
    }
}
