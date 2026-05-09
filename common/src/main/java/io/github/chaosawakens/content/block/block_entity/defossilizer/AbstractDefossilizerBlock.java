package io.github.chaosawakens.content.block.block_entity.defossilizer;

import io.github.chaosawakens.content.block_entity.defossilizer.AbstractDefossilizerBlockEntity;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.util.RandomSource;
import net.minecraft.world.Containers;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.*;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityTicker;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import net.minecraft.world.level.block.state.properties.DirectionProperty;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.Vec3;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public abstract class AbstractDefossilizerBlock extends BaseEntityBlock {
    public static final DirectionProperty FACING = HorizontalDirectionalBlock.FACING;
    public static final BooleanProperty LIT = BlockStateProperties.LIT;

    protected AbstractDefossilizerBlock(Properties properties) {
        super(properties);

        registerDefaultState(defaultBlockState().setValue(FACING, Direction.NORTH).setValue(LIT, false));
    }

    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
        super.createBlockStateDefinition(builder);

        builder.add(FACING, LIT);
    }

    @Override
    public @NotNull InteractionResult use(BlockState state, Level level, BlockPos pos, Player player, InteractionHand hand, BlockHitResult hit) {
        if (level.isClientSide) return InteractionResult.SUCCESS;
        else {
            openContainer(level, pos, player); // Can't abstract this here since referencing static BE types will likely result in circular classloading deadlocks
            return InteractionResult.CONSUME;
        }
    }

    @Override
    public @Nullable BlockState getStateForPlacement(BlockPlaceContext context) {
        return defaultBlockState().setValue(FACING, context.getHorizontalDirection().getOpposite());
    }

    @Override
    public @NotNull BlockState rotate(BlockState state, Rotation rotation) {
        return state.setValue(FACING, rotation.rotate(state.getValue(FACING)));
    }

    @Override
    public @NotNull BlockState mirror(BlockState state, Mirror mirror) {
        return state.setValue(FACING, mirror.mirror(state.getValue(FACING)));
    }

    @Override
    public void setPlacedBy(Level level, BlockPos pos, BlockState state, @Nullable LivingEntity placer, ItemStack stack) {
        if (stack.hasCustomHoverName()) {
            BlockEntity targetBlockEntity = level.getBlockEntity(pos);

            if (targetBlockEntity instanceof AbstractDefossilizerBlockEntity targetDefossilizerBlockEntity) {
                targetDefossilizerBlockEntity.setCustomName(stack.getHoverName());
            }
        }
    }

    @Override
    public void onRemove(BlockState state, Level level, BlockPos pos, BlockState newState, boolean movedByPiston) {
        if (!state.is(newState.getBlock())) {
            BlockEntity targetBlockEntity = level.getBlockEntity(pos);

            if (targetBlockEntity instanceof AbstractDefossilizerBlockEntity targetDefossilizerBlockEntity) {
                Containers.dropContents(level, pos, targetDefossilizerBlockEntity);

                targetDefossilizerBlockEntity.getRecipesToAwardAndPopExperience((ServerLevel) level, Vec3.atCenterOf(pos));
            }

            super.onRemove(state, level, pos, newState, movedByPiston);
        }
    }

    @Override
    public boolean hasAnalogOutputSignal(BlockState state) {
        return true;
    }

    @Override
    public int getAnalogOutputSignal(BlockState blockState, Level level, BlockPos pos) {
        return AbstractContainerMenu.getRedstoneSignalFromBlockEntity(level.getBlockEntity(pos));
    }

    @Override
    public @NotNull RenderShape getRenderShape(BlockState state) {
        return RenderShape.MODEL;
    }

    protected abstract void openContainer(Level curLevel, BlockPos targetPos, Player interactingPlayer);

    protected static <BE extends BlockEntity> BlockEntityTicker<BE> createDefossilizerTicker(Level level, BlockEntityType<BE> serverType, BlockEntityType<? extends AbstractDefossilizerBlockEntity> clientType) {
        return level.isClientSide ? null : createTickerHelper(serverType, clientType, AbstractDefossilizerBlockEntity::serverTick);
    }

    @Override
    public void animateTick(BlockState targetState, Level curLevel, BlockPos targetPos, RandomSource randSrc) {
        if (!targetState.getValue(LIT)) return;

        double baseX = targetPos.getX() + 0.5D;
        double baseY = targetPos.getY();
        double baseZ = targetPos.getZ() + 0.5D;

        if (randSrc.nextDouble() < 0.1D) {
            curLevel.playLocalSound(baseX, baseY, baseZ, SoundEvents.FURNACE_FIRE_CRACKLE, SoundSource.BLOCKS, 1.0F, 1.0F, false);
        }

        Direction facing = targetState.getValue(FACING);
        Direction.Axis axis = facing.getAxis();
        double offset = 0.52D;
        double lateral = randSrc.nextDouble() * 0.6D - 0.3D;
        double xOffset = axis == Direction.Axis.X ? facing.getStepX() * offset : lateral;
        double yOffset = randSrc.nextDouble() * 6.0D / 16.0D;
        double zOffset = axis == Direction.Axis.Z ? facing.getStepZ() * offset : lateral;

        double spawnX = baseX + xOffset;
        double spawnY = baseY + yOffset;
        double spawnZ = baseZ + zOffset;

        curLevel.addParticle(ParticleTypes.SMOKE, spawnX, spawnY, spawnZ, 0.0D, 0.0D, 0.0D);
        curLevel.addParticle(ParticleTypes.FLAME, spawnX, spawnY, spawnZ, 0.0D, 0.0D, 0.0D);
    }
}
