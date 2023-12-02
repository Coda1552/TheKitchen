package codyhuh.thekitchen.common.blocks;

import codyhuh.thekitchen.registry.ModBlocks;
import com.google.common.collect.ImmutableMap;
import com.google.common.collect.Maps;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.Mirror;
import net.minecraft.world.level.block.Rotation;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.EnumProperty;
import net.minecraft.world.level.block.state.properties.RedstoneSide;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;

import java.util.Map;

public class BreadcrumbTrailBlock extends Block {
    public static final EnumProperty<RedstoneSide> NORTH = BlockStateProperties.NORTH_REDSTONE;
    public static final EnumProperty<RedstoneSide> EAST = BlockStateProperties.EAST_REDSTONE;
    public static final EnumProperty<RedstoneSide> SOUTH = BlockStateProperties.SOUTH_REDSTONE;
    public static final EnumProperty<RedstoneSide> WEST = BlockStateProperties.WEST_REDSTONE;
    public static final Map<Direction, EnumProperty<RedstoneSide>> PROPERTY_BY_DIRECTION = Maps.newEnumMap(ImmutableMap.of(Direction.NORTH, NORTH, Direction.EAST, EAST, Direction.SOUTH, SOUTH, Direction.WEST, WEST));
    private static final VoxelShape SHAPE_DOT = Block.box(3.0D, 0.0D, 3.0D, 13.0D, 1.0D, 13.0D);
    private static final Map<Direction, VoxelShape> SHAPES_FLOOR = Maps.newEnumMap(ImmutableMap.of(Direction.NORTH, Block.box(3.0D, 0.0D, 0.0D, 13.0D, 1.0D, 13.0D), Direction.SOUTH, Block.box(3.0D, 0.0D, 3.0D, 13.0D, 1.0D, 16.0D), Direction.EAST, Block.box(3.0D, 0.0D, 3.0D, 16.0D, 1.0D, 13.0D), Direction.WEST, Block.box(0.0D, 0.0D, 3.0D, 13.0D, 1.0D, 13.0D)));
    private static final Map<Direction, VoxelShape> SHAPES_UP = Maps.newEnumMap(ImmutableMap.of(Direction.NORTH, Shapes.or(SHAPES_FLOOR.get(Direction.NORTH), Block.box(3.0D, 0.0D, 0.0D, 13.0D, 16.0D, 1.0D)), Direction.SOUTH, Shapes.or(SHAPES_FLOOR.get(Direction.SOUTH), Block.box(3.0D, 0.0D, 15.0D, 13.0D, 16.0D, 16.0D)), Direction.EAST, Shapes.or(SHAPES_FLOOR.get(Direction.EAST), Block.box(15.0D, 0.0D, 3.0D, 16.0D, 16.0D, 13.0D)), Direction.WEST, Shapes.or(SHAPES_FLOOR.get(Direction.WEST), Block.box(0.0D, 0.0D, 3.0D, 1.0D, 16.0D, 13.0D))));
    private static final Map<BlockState, VoxelShape> SHAPES_CACHE = Maps.newHashMap();

    public BreadcrumbTrailBlock(BlockBehaviour.Properties pProperties) {
        super(pProperties);
        this.registerDefaultState(this.stateDefinition.any().setValue(NORTH, RedstoneSide.NONE).setValue(EAST, RedstoneSide.NONE).setValue(SOUTH, RedstoneSide.NONE).setValue(WEST, RedstoneSide.NONE));

        for (BlockState blockstate : this.getStateDefinition().getPossibleStates()) {
            SHAPES_CACHE.put(blockstate, this.calculateShape(blockstate));
        }

    }

    private VoxelShape calculateShape(BlockState pState) {
        VoxelShape voxelshape = SHAPE_DOT;

        for (Direction direction : Direction.Plane.HORIZONTAL) {
            RedstoneSide redstoneside = pState.getValue(PROPERTY_BY_DIRECTION.get(direction));
            if (redstoneside == RedstoneSide.SIDE) {
                voxelshape = Shapes.or(voxelshape, SHAPES_FLOOR.get(direction));
            } else if (redstoneside == RedstoneSide.UP) {
                voxelshape = Shapes.or(voxelshape, SHAPES_UP.get(direction));
            }
        }

        return voxelshape;
    }

    public VoxelShape getShape(BlockState pState, BlockGetter pLevel, BlockPos pPos, CollisionContext pContext) {
        return SHAPES_CACHE.get(pState);
    }

    public BlockState getStateForPlacement(BlockPlaceContext pContext) {
        return this.getConnectionState(pContext.getLevel(), defaultBlockState(), pContext.getClickedPos());
    }

    private BlockState getConnectionState(BlockGetter pLevel, BlockState pState, BlockPos pPos) {
        boolean flag = isDot(pState);
        pState = this.getMissingConnections(pLevel, this.defaultBlockState(), pPos);
        if (!flag || !isDot(pState)) {
            boolean flag1 = pState.getValue(NORTH).isConnected();
            boolean flag2 = pState.getValue(SOUTH).isConnected();
            boolean flag3 = pState.getValue(EAST).isConnected();
            boolean flag4 = pState.getValue(WEST).isConnected();
            boolean flag5 = !flag1 && !flag2;
            boolean flag6 = !flag3 && !flag4;
            if (!flag4 && flag5) {
                pState = pState.setValue(WEST, RedstoneSide.SIDE);
            }

            if (!flag3 && flag5) {
                pState = pState.setValue(EAST, RedstoneSide.SIDE);
            }

            if (!flag1 && flag6) {
                pState = pState.setValue(NORTH, RedstoneSide.SIDE);
            }

            if (!flag2 && flag6) {
                pState = pState.setValue(SOUTH, RedstoneSide.SIDE);
            }

        }
        return pState;
    }

    private BlockState getMissingConnections(BlockGetter pLevel, BlockState pState, BlockPos pPos) {
        boolean flag = !pLevel.getBlockState(pPos.above()).isRedstoneConductor(pLevel, pPos);

        for(Direction direction : Direction.Plane.HORIZONTAL) {
            if (!pState.getValue(PROPERTY_BY_DIRECTION.get(direction)).isConnected()) {
                RedstoneSide redstoneside = this.getConnectingSide(pLevel, pPos, direction, flag);
                pState = pState.setValue(PROPERTY_BY_DIRECTION.get(direction), redstoneside);
            }
        }

        return pState;
    }

    public BlockState updateShape(BlockState pState, Direction pFacing, BlockState pFacingState, LevelAccessor pLevel, BlockPos pCurrentPos, BlockPos pFacingPos) {
        if (pFacing == Direction.DOWN) {
            return pState;
        } else if (pFacing == Direction.UP) {
            return this.getConnectionState(pLevel, pState, pCurrentPos);
        } else {
            RedstoneSide redstoneside = this.getConnectingSide(pLevel, pCurrentPos, pFacing);
            return redstoneside.isConnected() == pState.getValue(PROPERTY_BY_DIRECTION.get(pFacing)).isConnected() && !isCross(pState) ? pState.setValue(PROPERTY_BY_DIRECTION.get(pFacing), redstoneside) : this.getConnectionState(pLevel, defaultBlockState().setValue(PROPERTY_BY_DIRECTION.get(pFacing), redstoneside), pCurrentPos);
        }
    }

    private static boolean isCross(BlockState pState) {
        return pState.getValue(NORTH).isConnected() && pState.getValue(SOUTH).isConnected() && pState.getValue(EAST).isConnected() && pState.getValue(WEST).isConnected();
    }

    private static boolean isDot(BlockState pState) {
        return !pState.getValue(NORTH).isConnected() && !pState.getValue(SOUTH).isConnected() && !pState.getValue(EAST).isConnected() && !pState.getValue(WEST).isConnected();
    }

    public void updateIndirectNeighbourShapes(BlockState pState, LevelAccessor pLevel, BlockPos pPos, int pFlags, int pRecursionLeft) {
        BlockPos.MutableBlockPos mutablePos = new BlockPos.MutableBlockPos();

        for(Direction direction : Direction.Plane.HORIZONTAL) {
            RedstoneSide redstoneside = pState.getValue(PROPERTY_BY_DIRECTION.get(direction));
            if (redstoneside != RedstoneSide.NONE && !pLevel.getBlockState(mutablePos.setWithOffset(pPos, direction)).is(this)) {
                mutablePos.move(Direction.DOWN);
                BlockState blockstate = pLevel.getBlockState(mutablePos);
                if (blockstate.is(this)) {
                    BlockPos blockpos = mutablePos.relative(direction.getOpposite());
                    pLevel.neighborShapeChanged(direction.getOpposite(), pLevel.getBlockState(blockpos), mutablePos, blockpos, pFlags, pRecursionLeft);
                }

                mutablePos.setWithOffset(pPos, direction).move(Direction.UP);
                BlockState blockstate1 = pLevel.getBlockState(mutablePos);
                if (blockstate1.is(this)) {
                    BlockPos blockpos1 = mutablePos.relative(direction.getOpposite());
                    pLevel.neighborShapeChanged(direction.getOpposite(), pLevel.getBlockState(blockpos1), mutablePos, blockpos1, pFlags, pRecursionLeft);
                }
            }
        }

    }

    private RedstoneSide getConnectingSide(BlockGetter pLevel, BlockPos pPos, Direction pFace) {
        return this.getConnectingSide(pLevel, pPos, pFace, !pLevel.getBlockState(pPos.above()).isRedstoneConductor(pLevel, pPos));
    }

    private RedstoneSide getConnectingSide(BlockGetter pLevel, BlockPos pPos, Direction pDirection, boolean pNonNormalCubeAbove) {
        BlockPos blockpos = pPos.relative(pDirection);
        BlockState blockstate = pLevel.getBlockState(blockpos);
        if (pNonNormalCubeAbove) {
            boolean flag = this.canSurviveOn(pLevel, blockpos, blockstate);
            if (flag && pLevel.getBlockState(blockpos.above()).canRedstoneConnectTo(pLevel, blockpos.above(), null)) {
                if (blockstate.isFaceSturdy(pLevel, blockpos, pDirection.getOpposite())) {
                    return RedstoneSide.UP;
                }

                return RedstoneSide.SIDE;
            }
        }

        return shouldConnectTo(blockstate) ? RedstoneSide.SIDE : RedstoneSide.NONE;
    }

    public boolean canSurvive(BlockState pState, LevelReader pLevel, BlockPos pPos) {
        BlockPos blockpos = pPos.below();
        BlockState blockstate = pLevel.getBlockState(blockpos);
        return this.canSurviveOn(pLevel, blockpos, blockstate);
    }

    private boolean canSurviveOn(BlockGetter pLevel, BlockPos pPos, BlockState pState) {
        return pState.isFaceSturdy(pLevel, pPos, Direction.UP) || pState.is(Blocks.HOPPER);
    }

    private void checkCornerChangeAt(Level pLevel, BlockPos pPos) {
        if (pLevel.getBlockState(pPos).is(this)) {
            pLevel.updateNeighborsAt(pPos, this);

            for(Direction direction : Direction.values()) {
                pLevel.updateNeighborsAt(pPos.relative(direction), this);
            }

        }
    }

    public void onPlace(BlockState pState, Level pLevel, BlockPos pPos, BlockState pOldState, boolean pIsMoving) {
        if (!pOldState.is(pState.getBlock()) && !pLevel.isClientSide) {

            for(Direction direction : Direction.Plane.VERTICAL) {
                pLevel.updateNeighborsAt(pPos.relative(direction), this);
            }

            this.updateNeighborsOfNeighboringWires(pLevel, pPos);
        }
    }

    public void onRemove(BlockState pState, Level pLevel, BlockPos pPos, BlockState pNewState, boolean pIsMoving) {
        if (!pIsMoving && !pState.is(pNewState.getBlock())) {
            super.onRemove(pState, pLevel, pPos, pNewState, pIsMoving);
            if (!pLevel.isClientSide) {
                for(Direction direction : Direction.values()) {
                    pLevel.updateNeighborsAt(pPos.relative(direction), this);
                }

                this.updateNeighborsOfNeighboringWires(pLevel, pPos);
            }
        }
    }

    private void updateNeighborsOfNeighboringWires(Level pLevel, BlockPos pPos) {
        for(Direction direction : Direction.Plane.HORIZONTAL) {
            this.checkCornerChangeAt(pLevel, pPos.relative(direction));
        }

        for(Direction direction1 : Direction.Plane.HORIZONTAL) {
            BlockPos blockpos = pPos.relative(direction1);
            if (pLevel.getBlockState(blockpos).isRedstoneConductor(pLevel, blockpos)) {
                this.checkCornerChangeAt(pLevel, blockpos.above());
            } else {
                this.checkCornerChangeAt(pLevel, blockpos.below());
            }
        }

    }

    public void neighborChanged(BlockState pState, Level pLevel, BlockPos pPos, Block pBlock, BlockPos pFromPos, boolean pIsMoving) {
        if (!pLevel.isClientSide) {
            if (!pState.canSurvive(pLevel, pPos)) {
                dropResources(pState, pLevel, pPos);
                pLevel.removeBlock(pPos, false);
            }

        }
    }

    protected static boolean shouldConnectTo(BlockState pState) {
        return pState.is(ModBlocks.BREADCRUMBS.get());
    }

    public BlockState rotate(BlockState pState, Rotation pRotation) {
        switch (pRotation) {
            case CLOCKWISE_180:
                return pState.setValue(NORTH, pState.getValue(SOUTH)).setValue(EAST, pState.getValue(WEST)).setValue(SOUTH, pState.getValue(NORTH)).setValue(WEST, pState.getValue(EAST));
            case COUNTERCLOCKWISE_90:
                return pState.setValue(NORTH, pState.getValue(EAST)).setValue(EAST, pState.getValue(SOUTH)).setValue(SOUTH, pState.getValue(WEST)).setValue(WEST, pState.getValue(NORTH));
            case CLOCKWISE_90:
                return pState.setValue(NORTH, pState.getValue(WEST)).setValue(EAST, pState.getValue(NORTH)).setValue(SOUTH, pState.getValue(EAST)).setValue(WEST, pState.getValue(SOUTH));
            default:
                return pState;
        }
    }

    public BlockState mirror(BlockState pState, Mirror pMirror) {
        return switch (pMirror) {
            case LEFT_RIGHT -> pState.setValue(NORTH, pState.getValue(SOUTH)).setValue(SOUTH, pState.getValue(NORTH));
            case FRONT_BACK -> pState.setValue(EAST, pState.getValue(WEST)).setValue(WEST, pState.getValue(EAST));
            default -> super.mirror(pState, pMirror);
        };
    }

    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> pBuilder) {
        pBuilder.add(NORTH, EAST, SOUTH, WEST);
    }
}
