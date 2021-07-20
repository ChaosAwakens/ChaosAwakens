package io.github.chaosawakens.common.blocks;

import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.entity.item.ItemEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.pathfinding.PathType;
import net.minecraft.potion.EffectInstance;
import net.minecraft.potion.Effects;
import net.minecraft.state.IntegerProperty;
import net.minecraft.state.StateContainer;
import net.minecraft.state.properties.BlockStateProperties;
import net.minecraft.stats.Stats;
import net.minecraft.util.ActionResultType;
import net.minecraft.util.Direction;
import net.minecraft.util.Hand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.BlockRayTraceResult;
import net.minecraft.util.math.shapes.ISelectionContext;
import net.minecraft.util.math.shapes.VoxelShape;
import net.minecraft.world.IBlockReader;
import net.minecraft.world.IWorld;
import net.minecraft.world.IWorldReader;
import net.minecraft.world.World;

public class EnchantedGoldenCakeBlock extends Block {
    public static final IntegerProperty BITES = BlockStateProperties.BITES;
    protected static final VoxelShape[] SHAPES = new VoxelShape[]{Block.box(1.0D, 0.0D, 1.0D, 15.0D, 8.0D, 15.0D), Block.box(3.0D, 0.0D, 1.0D, 15.0D, 8.0D, 15.0D), Block.box(5.0D, 0.0D, 1.0D, 15.0D, 8.0D, 15.0D), Block.box(7.0D, 0.0D, 1.0D, 15.0D, 8.0D, 15.0D), Block.box(9.0D, 0.0D, 1.0D, 15.0D, 8.0D, 15.0D), Block.box(11.0D, 0.0D, 1.0D, 15.0D, 8.0D, 15.0D), Block.box(13.0D, 0.0D, 1.0D, 15.0D, 8.0D, 15.0D)};

    public EnchantedGoldenCakeBlock(Properties properties) {
        super(properties);
        this.registerDefaultState(this.stateDefinition.any().setValue(BITES, 0));
    }

    public VoxelShape getShape(BlockState state, IBlockReader worldIn, BlockPos pos, ISelectionContext context) {
        return SHAPES[state.getValue(BITES)];
    }

    public ActionResultType use(BlockState state, World worldIn, BlockPos pos, PlayerEntity player, Hand handIn, BlockRayTraceResult hit) {
        if (worldIn.isClientSide) {
            ItemStack itemstack = player.getItemInHand(handIn);
            if (this.eatSlice(worldIn, pos, state, player).consumesAction()) {
                return ActionResultType.SUCCESS;
            }

            if (itemstack.isEmpty()) {
                return ActionResultType.CONSUME;
            }
        }

        return this.eatSlice(worldIn, pos, state, player);
    }

    private ActionResultType eatSlice(World worldIn, BlockPos pos, BlockState state, PlayerEntity player) {
        if (!player.canEat(false)) {
            return ActionResultType.PASS;
        } else {
            player.addEffect(new EffectInstance(Effects.REGENERATION, 1200, 1));
            player.addEffect(new EffectInstance(Effects.DAMAGE_BOOST, 1800, 0));
            player.awardStat(Stats.EAT_CAKE_SLICE);
            ItemEntity entityToSpawn = new ItemEntity(worldIn, pos.getX(), pos.getY(), pos.getZ(), new ItemStack(Items.GOLD_INGOT, 1));
            worldIn.addFreshEntity(entityToSpawn);
            player.getFoodData().eat(7, 0.85F);
            int i = state.getValue(BITES);
            if (i < 6) {
                worldIn.setBlock(pos, state.setValue(BITES, i + 1), 3);
            } else {
                worldIn.removeBlock(pos, false);
            }

            return ActionResultType.SUCCESS;
        }
    }

    /**
     * Update the provided state given the provided neighbor facing and neighbor state, returning a new state.
     * For example, fences make their connections to the passed in state if possible, and wet concrete powder immediately
     * returns its solidified counterpart.
     * Note that this method should ideally consider only the specific face passed in.
     */
    public BlockState updateShape(BlockState stateIn, Direction facing, BlockState facingState, IWorld worldIn, BlockPos currentPos, BlockPos facingPos) {
        return facing == Direction.DOWN && !stateIn.canSurvive(worldIn, currentPos) ? Blocks.AIR.defaultBlockState() : stateIn;
    }

    public boolean canSurvive(BlockState state, IWorldReader worldIn, BlockPos pos) {
        return worldIn.getBlockState(pos.below()).getMaterial().isSolid();
    }

    protected void createBlockStateDefinition(StateContainer.Builder<Block, BlockState> builder) {
        builder.add(BITES);
    }

    public int getAnalogOutputSignal(BlockState blockState, World worldIn, BlockPos pos) {
        return (7 - blockState.getValue(BITES)) * 2;
    }

    public boolean hasAnalogOutputSignal(BlockState state) {
        return true;
    }

    public boolean isPathfindable(BlockState state, IBlockReader worldIn, BlockPos pos, PathType type) {
        return false;
    }
}