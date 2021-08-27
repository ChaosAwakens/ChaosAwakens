package io.github.chaosawakens.common.blocks;

import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.SoundType;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.particles.BlockParticleData;
import net.minecraft.particles.ParticleTypes;
import net.minecraft.state.BooleanProperty;
import net.minecraft.state.StateContainer;
import net.minecraft.util.ActionResultType;
import net.minecraft.util.Direction;
import net.minecraft.util.Hand;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.BlockRayTraceResult;
import net.minecraft.util.math.shapes.ISelectionContext;
import net.minecraft.util.math.shapes.VoxelShape;
import net.minecraft.util.math.shapes.VoxelShapes;
import net.minecraft.world.Explosion;
import net.minecraft.world.IBlockReader;
import net.minecraft.world.World;
import net.minecraft.world.server.ServerWorld;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

import java.util.Random;

public class GateBlock extends Block {
    public static final BooleanProperty ACTIVE = BooleanProperty.create("active");
    public static final BooleanProperty VANISHED = BooleanProperty.create("vanished");
    private static final VoxelShape VANISHED_SHAPE = box(6, 6, 6, 10, 10, 10);

    public GateBlock(Properties props) {
        super(props);
        this.registerDefaultState(stateDefinition.any().setValue(ACTIVE, false));
    }

    @Override
    @Deprecated
    public void tick(BlockState state, ServerWorld world, BlockPos pos, Random random) {
        if (world.isClientSide) {
            return;
        }

        if (isVanished(state)) {
            if (state.getValue(ACTIVE)) {
                world.setBlockAndUpdate(pos, state.setValue(VANISHED, false).setValue(ACTIVE, false));
            } else {
                world.setBlockAndUpdate(pos, state.setValue(ACTIVE, true));
                world.getBlockTicks().scheduleTick(pos, this, 15);
            }
            world.playSound(null, pos, SoundType.ANVIL.getPlaceSound(), SoundCategory.BLOCKS, 0.3F, 0.6F);
        } else {
            if (state.getValue(ACTIVE)) {
                if (state.hasProperty(VANISHED)) {
                    world.setBlockAndUpdate(pos, state.setValue(ACTIVE, false).setValue(VANISHED, true));
                    world.getBlockTicks().scheduleTick(pos, this, 80);
                } else {
                    world.removeBlock(pos, false);
                }

                world.playSound(null, pos, SoundType.ANVIL.getPlaceSound(), SoundCategory.BLOCKS, 0.3F, 0.5F);

                for (Direction e : Direction.values()) {
                    activate(world, pos.relative(e));
                }
            }
        }
    }

    private boolean isVanished(BlockState state) {
        return state.hasProperty(VANISHED) && state.getValue(VANISHED);
    }

    @Override
    public VoxelShape getShape(BlockState state, IBlockReader world, BlockPos pos, ISelectionContext ctx) {
        return isVanished(state) ? VANISHED_SHAPE : VoxelShapes.block();
    }

    @Override
    protected void createBlockStateDefinition(StateContainer.Builder<Block, BlockState> builder) {
        super.createBlockStateDefinition(builder);
        builder.add(ACTIVE);
    }

    @Override
    public VoxelShape getCollisionShape(BlockState state, IBlockReader world, BlockPos pos, ISelectionContext ctx) {
        return isVanished(state) ? VoxelShapes.empty() : this.hasCollision ? state.getShape(world, pos) : VoxelShapes.empty();
    }

    @Override
    @Deprecated
    public void neighborChanged(BlockState state, World world, BlockPos pos, Block blockIn, BlockPos fromPos, boolean isMoving) {
        if (world.isClientSide) {
            return;
        }

        if (!isVanished(state) && !state.getValue(ACTIVE) && world.hasNeighborSignal(pos)) {
            activate(world, pos);
        }
    }

    @Override
    public boolean canEntityDestroy(BlockState state, IBlockReader world, BlockPos pos, Entity entity) {
        super.canEntityDestroy(state, world, pos, entity);
        return !state.getValue(ACTIVE);
    }

    @Override
    @OnlyIn(Dist.CLIENT)
    public void animateTick(BlockState state, World world, BlockPos pos, Random random) {
        if (state.getValue(ACTIVE)) {
            this.sparkle(world, pos);
        }
    }

    @Override
    @Deprecated
    public ActionResultType use(BlockState state, World world, BlockPos pos, PlayerEntity player, Hand handIn, BlockRayTraceResult hit) {
        if (!isVanished(state) && !state.getValue(ACTIVE)) {
            activate(world, pos);
            return ActionResultType.SUCCESS;
        }
        return ActionResultType.PASS;
    }

    public void sparkle(World worldIn, BlockPos pos) {
        Random random = worldIn.random;
        double d0 = 0.0625D;

        for (int i = 0; i < 6; ++i) {
            double d1 = ((float) pos.getX() + random.nextFloat());
            double d2 = ((float) pos.getY() + random.nextFloat());
            double d3 = ((float) pos.getZ() + random.nextFloat());

            if (i == 0 && !worldIn.getBlockState(pos.above()).isSolidRender(worldIn, pos)) {
                d2 = (double) pos.getY() + d0 + 1.25D;
            }

            if (i == 1 && !worldIn.getBlockState(pos.below()).isSolidRender(worldIn, pos)) {
                d2 = (double) pos.getY() - d0;
            }

            if (i == 2 && !worldIn.getBlockState(pos.south()).isSolidRender(worldIn, pos)) {
                d3 = (double) pos.getZ() + d0 + 1.25D;
            }

            if (i == 3 && !worldIn.getBlockState(pos.north()).isSolidRender(worldIn, pos)) {
                d3 = (double) pos.getZ() - d0;
            }

            if (i == 4 && !worldIn.getBlockState(pos.east()).isSolidRender(worldIn, pos)) {
                d1 = (double) pos.getX() + d0 + 1.25D;
            }

            if (i == 5 && !worldIn.getBlockState(pos.west()).isSolidRender(worldIn, pos)) {
                d1 = (double) pos.getX() - d0;
            }

            if (d1 < (double) pos.getX() || d1 > (double) (pos.getX() + 1.25F) || d2 < 0.25D || d2 > (pos.getY() + 1.25D) || d3 < (double) pos.getZ() || d3 > (pos.getZ() + 1.25D)) {
                worldIn.addParticle(new BlockParticleData(ParticleTypes.FALLING_DUST, defaultBlockState()), d1, d2, d3, 0.0D, 0.0D, 0.0D);
            }
        }
    }

    @Override
    public float getExplosionResistance(BlockState state, IBlockReader world, BlockPos pos, Explosion explosion) {
        return !state.getValue(ACTIVE) ? 6000F : super.getExplosionResistance(state, world, pos, explosion);
    }

    private void activate(World world, BlockPos pos) {
        BlockState state = world.getBlockState(pos);
        if (state.getBlock() instanceof GateBlock && !isVanished(state) && !state.getValue(ACTIVE)) {
            world.setBlockAndUpdate(pos, state.setValue(ACTIVE, true));
            world.getBlockTicks().scheduleTick(pos, state.getBlock(), 2 + world.random.nextInt(5));
        }
    }
}