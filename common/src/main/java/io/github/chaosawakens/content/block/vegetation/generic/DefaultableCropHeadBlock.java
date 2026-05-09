package io.github.chaosawakens.content.block.vegetation.generic;

import com.mememan.nexus.util.RegistryUtil;
import io.github.chaosawakens.content.registry.CATags;
import it.unimi.dsi.fastutil.objects.ObjectOpenHashSet;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.tags.TagKey;
import net.minecraft.util.Mth;
import net.minecraft.util.RandomSource;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.GrowingPlantBodyBlock;
import net.minecraft.world.level.block.GrowingPlantHeadBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.IntegerProperty;
import net.minecraft.world.level.material.Fluids;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.Set;
import java.util.function.Supplier;

public class DefaultableCropHeadBlock extends GrowingPlantHeadBlock implements CropInstance {
    public static final IntegerProperty DEFAULT_AGE_PROPERTY = BlockStateProperties.AGE_3;
    public static final int DEFAULT_MAX_AGE = BlockStateProperties.MAX_AGE_3;
    public static final IntegerProperty DEFAULT_HEIGHT_PROPERTY = DefaultableCropBodyBlock.DEFAULT_HEIGHT_PROPERTY;
    public static final int DEFAULT_MAX_HEIGHT = DefaultableCropBodyBlock.DEFAULT_MAX_HEIGHT;
    public static final VoxelShape DEFAULT_SHAPE = Block.box(0.0, 0.0, 0.0, 16.0, 12.0, 16.0);
    public static final VoxelShape[] DEFAULT_SHAPE_BY_AGE = new VoxelShape[]{
            Block.box(0.0, 0.0, 0.0, 16.0, 4.0, 16.0),
            Block.box(0.0, 0.0, 0.0, 16.0, 8.0, 16.0),
            Block.box(0.0, 0.0, 0.0, 16.0, 12.0, 16.0),
            Block.box(0.0, 0.0, 0.0, 16.0, 16.0, 16.0)
    };
    @Nullable
    private static IntegerProperty pendingAgeProperty;
    @Nullable
    private static IntegerProperty pendingHeightProperty;
    protected final Supplier<Item> produce;
    protected final Supplier<Item> seed;
    protected final Supplier<GrowingPlantBodyBlock> bodyBlock;
    protected final IntegerProperty ageProperty;
    protected final int maxAge;
    protected final IntegerProperty heightProperty;
    protected final int maxHeight;
    @Nullable
    protected final VoxelShape[] shapeByAge;
    protected final Set<Supplier<TagKey<Block>>> validPlacementTags;

    public DefaultableCropHeadBlock(Properties properties, Supplier<Item> produce, Supplier<Item> seed, Supplier<GrowingPlantBodyBlock> bodyBlock, IntegerProperty ageProperty, int maxAge, IntegerProperty heightProperty, int maxHeight, double growPerTickProbability, @Nullable VoxelShape[] shapeByAge, Set<Supplier<TagKey<Block>>> validPlacementTags) {
        super(storePropertiesInStaticInitializer(properties, ageProperty, heightProperty), Direction.UP, DEFAULT_SHAPE, false, growPerTickProbability);

        this.produce = produce;
        this.seed = seed;
        this.bodyBlock = bodyBlock;
        this.ageProperty = ageProperty;
        this.maxAge = maxAge;
        this.heightProperty = heightProperty;
        this.maxHeight = maxHeight;
        this.shapeByAge = shapeByAge;
        this.validPlacementTags = validPlacementTags;

        clearPendingProperties();
        registerDefaultState(stateDefinition.any().setValue(ageProperty, 0).setValue(heightProperty, 0));
    }

    public DefaultableCropHeadBlock(Properties properties, Supplier<Item> produce, Supplier<Item> seed, Supplier<GrowingPlantBodyBlock> bodyBlock, IntegerProperty ageProperty, int maxAge, IntegerProperty heightProperty, int maxHeight, double growPerTickProbability, Set<Supplier<TagKey<Block>>> validPlacementTags) {
        this(properties, produce, seed, bodyBlock, ageProperty, maxAge, heightProperty, maxHeight, growPerTickProbability, null, validPlacementTags);
    }

    public DefaultableCropHeadBlock(Properties properties, Supplier<Item> produce, Supplier<Item> seed, Supplier<GrowingPlantBodyBlock> bodyBlock, double growPerTickProbability) {
        this(properties, produce, seed, bodyBlock, DEFAULT_AGE_PROPERTY, DEFAULT_MAX_AGE, DEFAULT_HEIGHT_PROPERTY, DEFAULT_MAX_HEIGHT, growPerTickProbability, null, ObjectOpenHashSet.of(CATags.CABlockTags.FARMLAND_BLOCKS));
    }

    public DefaultableCropHeadBlock(Properties properties, Supplier<Item> produce, Supplier<Item> seed, Supplier<GrowingPlantBodyBlock> bodyBlock) {
        this(properties, produce, seed, bodyBlock, 0.1);
    }

    public DefaultableCropHeadBlock(Properties properties, Supplier<GrowingPlantBodyBlock> bodyBlock, IntegerProperty ageProperty, int maxAge, IntegerProperty heightProperty, int maxHeight, double growPerTickProbability, @Nullable VoxelShape[] shapeByAge, Set<Supplier<TagKey<Block>>> validPlacementTags) {
        super(storePropertiesInStaticInitializer(properties, ageProperty, heightProperty), Direction.UP, DEFAULT_SHAPE, false, growPerTickProbability);

        this.produce = DefaultableCropBlock.findProduceFor(() -> this);
        this.seed = DefaultableCropBlock.findSeedsFor(() -> this);
        this.bodyBlock = bodyBlock;
        this.ageProperty = ageProperty;
        this.maxAge = maxAge;
        this.heightProperty = heightProperty;
        this.maxHeight = maxHeight;
        this.shapeByAge = shapeByAge;
        this.validPlacementTags = validPlacementTags;

        clearPendingProperties();
        registerDefaultState(stateDefinition.any().setValue(ageProperty, 0).setValue(heightProperty, 0));
    }

    public DefaultableCropHeadBlock(Properties properties, Supplier<GrowingPlantBodyBlock> bodyBlock, IntegerProperty ageProperty, int maxAge, IntegerProperty heightProperty, int maxHeight, double growPerTickProbability, Set<Supplier<TagKey<Block>>> validPlacementTags) {
        this(properties, bodyBlock, ageProperty, maxAge, heightProperty, maxHeight, growPerTickProbability, null, validPlacementTags);
    }

    public DefaultableCropHeadBlock(Properties properties, Supplier<GrowingPlantBodyBlock> bodyBlock, double growPerTickProbability) {
        this(properties, bodyBlock, DEFAULT_AGE_PROPERTY, DEFAULT_MAX_AGE, DEFAULT_HEIGHT_PROPERTY, DEFAULT_MAX_HEIGHT, growPerTickProbability, null, ObjectOpenHashSet.of(CATags.CABlockTags.FARMLAND_BLOCKS));
    }

    public DefaultableCropHeadBlock(Properties properties, Supplier<GrowingPlantBodyBlock> bodyBlock) {
        this(properties, bodyBlock, 0.1);
    }

    public DefaultableCropHeadBlock(Properties properties, IntegerProperty ageProperty, int maxAge, IntegerProperty heightProperty, int maxHeight, double growPerTickProbability, @Nullable VoxelShape[] shapeByAge, Set<Supplier<TagKey<Block>>> validPlacementTags) {
        super(storePropertiesInStaticInitializer(properties, ageProperty, heightProperty), Direction.UP, DEFAULT_SHAPE, false, growPerTickProbability);

        this.produce = DefaultableCropBlock.findProduceFor(() -> this);
        this.seed = DefaultableCropBlock.findSeedsFor(() -> this);
        this.bodyBlock = findBodyFor(() -> this);
        this.ageProperty = ageProperty;
        this.maxAge = maxAge;
        this.heightProperty = heightProperty;
        this.maxHeight = maxHeight;
        this.shapeByAge = shapeByAge;
        this.validPlacementTags = validPlacementTags;

        clearPendingProperties();
        registerDefaultState(stateDefinition.any().setValue(ageProperty, 0).setValue(heightProperty, 0));
    }

    public DefaultableCropHeadBlock(Properties properties, IntegerProperty ageProperty, int maxAge, IntegerProperty heightProperty, int maxHeight, double growPerTickProbability, Set<Supplier<TagKey<Block>>> validPlacementTags) {
        this(properties, ageProperty, maxAge, heightProperty, maxHeight, growPerTickProbability, null, validPlacementTags);
    }

    public DefaultableCropHeadBlock(Properties properties, double growPerTickProbability) {
        this(properties, DEFAULT_AGE_PROPERTY, DEFAULT_MAX_AGE, DEFAULT_HEIGHT_PROPERTY, DEFAULT_MAX_HEIGHT, growPerTickProbability, null, ObjectOpenHashSet.of(CATags.CABlockTags.FARMLAND_BLOCKS));
    }

    public DefaultableCropHeadBlock(Properties properties) {
        this(properties, 0.1D);
    }

    public static Supplier<GrowingPlantBodyBlock> findBodyFor(Supplier<Block> targetBlockSup) {
        return () -> RegistryUtil.getObjectFrom(targetBlockSup, targetBlockId -> targetBlockId.withPath(path -> path.replace("_head_", "_body_")))
                .filter(GrowingPlantBodyBlock.class::isInstance)
                .map(GrowingPlantBodyBlock.class::cast)
                .orElse(null);
    }

    private static Properties storePropertiesInStaticInitializer(Properties properties, IntegerProperty ageProperty, IntegerProperty heightProperty) {
        pendingAgeProperty = ageProperty;
        pendingHeightProperty = heightProperty;
        return properties;
    }

    private static void clearPendingProperties() {
        pendingAgeProperty = null;
        pendingHeightProperty = null;
    }

    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
        IntegerProperty chosenAge = ageProperty != null ? ageProperty : pendingAgeProperty;
        IntegerProperty chosenHeight = heightProperty != null ? heightProperty : pendingHeightProperty;

        if (chosenAge != null) builder.add(chosenAge);
        if (chosenHeight != null) builder.add(chosenHeight);
    }

    @Override
    public @NotNull VoxelShape getShape(BlockState state, BlockGetter level, BlockPos pos, CollisionContext context) {
        return getShapeByAge()[getAge(state)];
    }

    @Override
    public boolean canSurvive(BlockState state, LevelReader level, BlockPos pos) {
        BlockPos below = pos.relative(this.growthDirection.getOpposite());
        BlockState belowState = level.getBlockState(below);

        return canAttachTo(belowState) && (belowState.is(getBodyBlock()) || belowState.is(this) || allowPlacementOn(belowState, level, below));
    }

    @Override
    public @NotNull BlockState getStateForPlacement(LevelAccessor level) {
        return defaultBlockState().setValue(ageProperty, 0).setValue(heightProperty, 0);
    }

    @Override
    public void randomTick(BlockState state, ServerLevel level, BlockPos pos, RandomSource random) {
        if (level.getRawBrightness(pos, 0) >= 9) {
            if (getAge(state) < maxAge - 1) {
                float growthSpeed = DefaultableCropBlock.getGrowthSpeed(this, level, pos);
                if (random.nextInt((int) (25.0F / growthSpeed) + 1) == 0) {
                    level.setBlock(pos, state.setValue(ageProperty, getAge(state) + 1).setValue(heightProperty, getHeight(state)), Block.UPDATE_CLIENTS);
                }
            } else {
                if (getHeight(state) < maxHeight && level.getBlockState(pos.above()).isAir()) {
                    level.setBlock(pos.above(), defaultBlockState().setValue(ageProperty, 0).setValue(heightProperty, getHeight(state) + 1), Block.UPDATE_CLIENTS);
                }
                level.setBlock(pos, getBodyBlock().defaultBlockState().setValue(heightProperty, getHeight(state)), Block.UPDATE_CLIENTS);
            }
        }
    }

    @Override
    public @NotNull BlockState updateShape(BlockState state, Direction facing, BlockState facingState, LevelAccessor level, BlockPos currentPos, BlockPos facingPos) {
        if (facing == growthDirection.getOpposite() && !state.canSurvive(level, currentPos)) {
            level.scheduleTick(currentPos, this, 1);
        }

        if (facing != growthDirection || !facingState.is(this) && !facingState.is(getBodyBlock())) {
            if (scheduleFluidTicks) level.scheduleTick(currentPos, Fluids.WATER, Fluids.WATER.getTickDelay(level));
            return super.updateShape(state, facing, facingState, level, currentPos, facingPos);
        } else return getBodyBlock().defaultBlockState().setValue(heightProperty, getHeight(state));
    }

    @Override
    public @NotNull ItemStack getCloneItemStack(BlockGetter level, BlockPos pos, BlockState state) {
        return seed.get().getDefaultInstance();
    }

    @Override
    public boolean isValidBonemealTarget(LevelReader levelReader, BlockPos blockPos, BlockState state, boolean isClient) {
        return !isMaxAge(state);
    }

    @Override
    public boolean isBonemealSuccess(Level level, RandomSource randomSource, BlockPos blockPos, BlockState blockState) {
        return true;
    }

    @Override
    public void performBonemeal(ServerLevel level, RandomSource randomSource, BlockPos pos, BlockState state) {
        growCrops(level, pos, state);
    }

    public void growCrops(Level level, BlockPos pos, BlockState state) {
        int newAge = getAge(state) + Mth.nextInt(level.random, 1, maxAge);

        if (newAge >= maxAge) {
            if (getHeight(state) < maxHeight) {
                level.setBlock(pos.above(), defaultBlockState().setValue(ageProperty, 0).setValue(heightProperty, getHeight(state) + 1), Block.UPDATE_CLIENTS);
                level.scheduleTick(pos, this, 1);
            }
            level.setBlock(pos, getBodyBlock().defaultBlockState().setValue(heightProperty, getHeight(state)), Block.UPDATE_CLIENTS);
        } else {
            level.setBlock(pos, state.setValue(ageProperty, newAge).setValue(heightProperty, getHeight(state)), 2);
        }
    }

    @Override
    public boolean isRandomlyTicking(BlockState state) {
        return true;
    }

    @Override
    protected int getBlocksToGrowWhenBonemealed(RandomSource randomSource) {
        return 0;
    }

    @Override
    protected boolean canGrowInto(BlockState blockState) {
        return blockState.isAir();
    }

    @Override
    protected @NotNull Block getBodyBlock() {
        return bodyBlock.get();
    }

    @Override
    public boolean allowPlacementOn(BlockState targetState, BlockGetter curLevel, BlockPos targetPos) {
        return CropInstance.super.allowPlacementOn(targetState, curLevel, targetPos) || targetState.is(getBodyBlock()) || targetState.is(this);
    }

    @Override
    public Supplier<Item> getProduce() {
        return produce;
    }

    @Override
    public Supplier<Item> getSeed() {
        return seed;
    }

    @Override
    public IntegerProperty getAgeProperty() {
        return ageProperty;
    }

    @Override
    public VoxelShape[] getShapeByAge() {
        return shapeByAge != null ? shapeByAge : DEFAULT_SHAPE_BY_AGE;
    }

    @Override
    public int getMaxAge() {
        return maxAge;
    }

    @Override
    public Set<Supplier<TagKey<Block>>> getValidPlacementTags() {
        return validPlacementTags;
    }

    public IntegerProperty getHeightProperty() {
        return heightProperty;
    }

    public int getAge(BlockState state) {
        return state.getValue(ageProperty);
    }

    public boolean isMaxAge(BlockState state) {
        return getAge(state) >= maxAge;
    }

    public int getHeight(BlockState state) {
        return state.getValue(heightProperty);
    }

    public boolean isMaxHeight(BlockState state) {
        return getHeight(state) >= maxHeight;
    }
}
