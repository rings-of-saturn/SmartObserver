package rings_of_saturn.github.io.smart_observer.block.custom;

import com.mojang.serialization.MapCodec;
import net.minecraft.block.*;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.BlockItem;
import net.minecraft.item.ItemPlacementContext;
import net.minecraft.item.Items;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.state.StateManager;
import net.minecraft.state.property.BooleanProperty;
import net.minecraft.text.Text;
import net.minecraft.util.ActionResult;
import net.minecraft.util.BlockMirror;
import net.minecraft.util.BlockRotation;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.util.math.random.Random;
import net.minecraft.world.BlockView;
import net.minecraft.world.World;
import net.minecraft.world.WorldAccess;
import org.jetbrains.annotations.Nullable;
import rings_of_saturn.github.io.smart_observer.block.ModBlocks;
import rings_of_saturn.github.io.smart_observer.block.entity.SmartObserverBlockEntity;

import static net.minecraft.state.property.Properties.FACING;
import static net.minecraft.state.property.Properties.POWERED;

public class SmartObserverBlock extends BlockWithEntity implements BlockEntityProvider {

    public static final BooleanProperty TOGGLED = BooleanProperty.of("toggled");

    public SmartObserverBlock(AbstractBlock.Settings settings) {
        super(settings);
        this.setDefaultState(this.stateManager.getDefaultState().with(FACING, Direction.SOUTH).with(POWERED, false).with(TOGGLED, false));
    }
    public static final MapCodec<SmartObserverBlock> CODEC = SmartObserverBlock.createCodec(SmartObserverBlock::new);

    protected void appendProperties(StateManager.Builder<Block, BlockState> builder) {
        builder.add(FACING, POWERED, TOGGLED);
    }

    protected BlockState rotate(BlockState state, BlockRotation rotation) {
        return state.with(FACING, rotation.rotate(state.get(FACING)));
    }

    @Override
    protected BlockRenderType getRenderType(BlockState state) {
        return BlockRenderType.MODEL;
    }

    @Override
    protected MapCodec<SmartObserverBlock> getCodec() {
        return CODEC;
    }

    @Override
    public @Nullable BlockEntity createBlockEntity(BlockPos pos, BlockState state) {
        return new SmartObserverBlockEntity(pos, state);
    }

    protected BlockState mirror(BlockState state, BlockMirror mirror) {
        return state.rotate(mirror.getRotation(state.get(FACING)));
    }

    protected void scheduledTick(BlockState state, ServerWorld world, BlockPos pos, Random random) {
        if (state.get(POWERED)) {
            world.setBlockState(pos, state.with(POWERED, false), 2);
        } else {
            world.setBlockState(pos, state.with(POWERED, true), 2);
            world.scheduleBlockTick(pos, this, 2);
        }
        if(state.get(TOGGLED)) {
            if (world.getBlockEntity(pos) instanceof SmartObserverBlockEntity blockEntity && !world.isClient()) {
                if (blockEntity.getStack(0).getItem() != Items.AIR) {
                    try {
                        BlockItem block = (BlockItem) blockEntity.getStack(0).getItem();
                        if(world.getBlockState(pos.offset(state.get(FACING))).getBlock() == block.getBlock()){
                            world.setBlockState(pos, state.with(POWERED, true), 2);
                            this.scheduleTick(world, pos);
                        } else {
                            world.setBlockState(pos, state.with(POWERED, false), 2);
                        }
                    } catch (ClassCastException ignored) {
                    }
                }
            }
        }
        this.updateNeighbors(world, pos, state);
    }

    protected BlockState getStateForNeighborUpdate(BlockState state, Direction direction, BlockState neighborState, WorldAccess world, BlockPos pos, BlockPos neighborPos) {
        if (state.get(FACING) == direction && !(Boolean)state.get(POWERED)) {
            if (world.getBlockEntity(pos) instanceof SmartObserverBlockEntity blockEntity && !world.isClient()) {
                if (blockEntity.getStack(0).getItem() != Items.AIR) {
                    try {
                        BlockItem block = (BlockItem) blockEntity.getStack(0).getItem();
                        if (block.getBlock() == neighborState.getBlock()) {
                            this.scheduleTick(world, pos);

                        }
                    } catch (ClassCastException ignored) {
                    }
                }
            }
        }

        return super.getStateForNeighborUpdate(state, direction, neighborState, world, pos, neighborPos);
    }

    private void scheduleTick(WorldAccess world, BlockPos pos) {
        if (!world.isClient() && !world.getBlockTickScheduler().isQueued(pos, this)) {
            world.scheduleBlockTick(pos, this, 2);
        }

    }

    protected void updateNeighbors(World world, BlockPos pos, BlockState state) {
        Direction direction = state.get(FACING);
        BlockPos blockPos = pos.offset(direction.getOpposite());
        world.updateNeighbor(blockPos, this, pos);
        world.updateNeighborsExcept(blockPos, this, direction);
    }

    @Override
    protected boolean emitsRedstonePower(BlockState state) {
        return true;
    }

    @Override
    protected int getStrongRedstonePower(BlockState state, BlockView world, BlockPos pos, Direction direction) {
        return state.getWeakRedstonePower(world, pos, direction);
    }

    @Override
    protected int getWeakRedstonePower(BlockState state, BlockView world, BlockPos pos, Direction direction) {
        return state.get(POWERED) && state.get(FACING) == direction ? 15 : 0;
    }



    @Override
    protected void onBlockAdded(BlockState state, World world, BlockPos pos, BlockState oldState, boolean notify) {
        if (!state.isOf(oldState.getBlock())) {
            if (!world.isClient() && state.get(POWERED) && !world.getBlockTickScheduler().isQueued(pos, this)) {
                BlockState blockState = state.with(POWERED, false);
                world.setBlockState(pos, blockState, 18);
                this.updateNeighbors(world, pos, blockState);
            }
        }
    }

    @Override
    protected ActionResult onUse(BlockState state, World world, BlockPos pos, PlayerEntity player, BlockHitResult hit) {
        if (world.getBlockEntity(pos) instanceof SmartObserverBlockEntity blockEntity && !world.isClient) {
            if (!player.getStackInHand(player.getActiveHand()).isEmpty()) {
                try {
                    BlockItem block = (BlockItem) player.getStackInHand(player.getActiveHand()).getItem();
                    if (block.getBlock() != null) {
                        blockEntity.setStack(0, block.getDefaultStack());
                        player.sendMessage(Text.of("added"));
                        getStateForNeighborUpdate(state, state.get(FACING), world.getBlockState(pos.offset(state.get(FACING))), world, pos, pos.offset(state.get(FACING)));
                    }
                } catch (ClassCastException ignored) {}
            } else {
                if (player.isSneaking()) {
                    world.setBlockState(pos, state.with(TOGGLED, !world.getBlockState(pos).get(TOGGLED)));
                    getStateForNeighborUpdate(state, state.get(FACING), world.getBlockState(pos.offset(state.get(FACING))), world, pos, pos.offset(state.get(FACING)));
                } else if (blockEntity.getStack(0).getItem() != ModBlocks.EMPTY) {
                    blockEntity.setStack(0, ModBlocks.EMPTY.getDefaultStack());
                    player.sendMessage(Text.of("cleared"));
                }
            }
            blockEntity.markDirty();
            world.updateListeners(pos, state, state, 0);
            world.updateNeighbor(pos, ModBlocks.SMART_OBSERVER, pos);
        }
        return ActionResult.SUCCESS_NO_ITEM_USED;
    }

    @Override
    protected void onStateReplaced(BlockState state, World world, BlockPos pos, BlockState newState, boolean moved) {
        if (!state.isOf(newState.getBlock())) {
            if (!world.isClient && state.get(POWERED) && world.getBlockTickScheduler().isQueued(pos, this)) {
                this.updateNeighbors(world, pos, state.with(POWERED, false));
            }
        }
        if(state.getBlock() != world.getBlockState(pos).getBlock()){
            BlockEntity blockEntity = world.getBlockEntity(pos);
            if(blockEntity instanceof SmartObserverBlockEntity){
                world.updateComparators(pos, this);
                blockEntity.markRemoved();
            }
        }
        super.onStateReplaced(state, world, pos, newState, moved);
    }

    public BlockState getPlacementState(ItemPlacementContext ctx) {
        return this.getDefaultState().with(FACING, ctx.getPlayerLookDirection().getOpposite().getOpposite());
    }

}
