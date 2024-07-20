package codyhuh.thekitchen.common.blocks;

import codyhuh.thekitchen.common.blockentities.LittlePlatterBlockEntity;
import codyhuh.thekitchen.registry.ModBlockEntities;
import net.minecraft.core.BlockPos;
import net.minecraft.core.NonNullList;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.Container;
import net.minecraft.world.Containers;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.BaseEntityBlock;
import net.minecraft.world.level.block.RenderShape;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;
import org.jetbrains.annotations.Nullable;

public class LittlePlatterBlock extends BaseEntityBlock {

    public LittlePlatterBlock(Properties p_49224_) {
        super(p_49224_);
    }

    @Nullable
    @Override
    public BlockEntity newBlockEntity(BlockPos pos, BlockState state) {
        return ModBlockEntities.LITTLE_PLATTER.get().create(pos, state);
    }

    @Override
    public RenderShape getRenderShape(BlockState pState) {
        return RenderShape.MODEL;
    }

    @Override
    public InteractionResult use(BlockState pState, Level pLevel, BlockPos pPos, Player pPlayer, InteractionHand pHand, BlockHitResult pHit) {
        ItemStack stack = pPlayer.getItemInHand(pHand);

        if (stack.getItem().isEdible() && pLevel.getBlockEntity(pPos) instanceof LittlePlatterBlockEntity blockEntity) {
            int itemCount = blockEntity.countItems(blockEntity.getItems());

            if (itemCount < blockEntity.getContainerSize()) {
                pLevel.playSound(pPlayer, pPos, SoundEvents.ITEM_FRAME_ADD_ITEM, SoundSource.BLOCKS, 0.35F, 1.0F);

                blockEntity.setItem(itemCount, stack.split(1));

                return InteractionResult.SUCCESS;
            }
            return InteractionResult.SUCCESS;
        }
        else if (stack.isEmpty() && pLevel.getBlockEntity(pPos) instanceof LittlePlatterBlockEntity blockEntity) {
            int itemCount = blockEntity.countItems(blockEntity.getItems());
            ItemStack toRemove = blockEntity.getItem(Math.max(1, blockEntity.countItems(blockEntity.getItems())) - 1);

            if (itemCount > 0) {
                pLevel.playSound(pPlayer, pPos, SoundEvents.ITEM_FRAME_REMOVE_ITEM, SoundSource.BLOCKS, 0.35F, 1.0F);

                if (pPlayer.isShiftKeyDown()) {
                    NonNullList<ItemStack> items = blockEntity.getItems();
                    for (ItemStack itemStack : items) {
                        pPlayer.getInventory().add(itemStack);
                        blockEntity.removeItem(items.size(), 1);
                    }
                }
                else {
                    pPlayer.getInventory().add(toRemove);
                    blockEntity.removeItem(itemCount, 1);
                }
                return InteractionResult.SUCCESS;

            }
            return InteractionResult.SUCCESS;
        }
        else {
            return super.use(pState, pLevel, pPos, pPlayer, pHand, pHit);
        }
    }

    @Override
    public VoxelShape getShape(BlockState p_60555_, BlockGetter p_60556_, BlockPos p_60557_, CollisionContext p_60558_) {
        return Shapes.box(0.275, 0, 0.275, 0.725, 0.0625, 0.725);
    }

    @Override
    public void onRemove(BlockState state, Level worldIn, BlockPos pos, BlockState newState, boolean isMoving) {
        if (!state.is(newState.getBlock())) {
            BlockEntity be = worldIn.getBlockEntity(pos);
            if (be instanceof Container) {
                Containers.dropContents(worldIn, pos, (Container)be);
                worldIn.updateNeighbourForOutputSignal(pos, this);
            }

            super.onRemove(state, worldIn, pos, newState, isMoving);
        }
    }
}
