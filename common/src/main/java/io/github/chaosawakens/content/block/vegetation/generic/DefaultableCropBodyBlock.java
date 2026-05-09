package io.github.chaosawakens.content.block.vegetation.generic;

import com.mememan.nexus.util.RegistryUtil;
import io.github.chaosawakens.content.registry.CATags;
import it.unimi.dsi.fastutil.objects.ObjectOpenHashSet;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.GrowingPlantBodyBlock;
import net.minecraft.world.level.block.GrowingPlantHeadBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.IntegerProperty;
import net.minecraft.world.level.material.Fluids;
import net.minecraft.world.phys.shapes.VoxelShape;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.Set;
import java.util.function.Supplier;

public class DefaultableCropBodyBlock extends GrowingPlantBodyBlock implements CropInstance {
    public static final IntegerProperty DEFAULT_HEIGHT_PROPERTY = IntegerProperty.create("height", 0, 2);
    public static final int DEFAULT_MAX_HEIGHT = 2;
    public static final VoxelShape DEFAULT_SHAPE = Block.box(0.0, 0.0, 0.0, 16.0, 16.0, 16.0);
    @Nullable
    private static IntegerProperty pendingHeightProperty;
    protected final Supplier<Item> produce;
    protected final Supplier<Item> seed;
    protected final Supplier<GrowingPlantHeadBlock> headBlock;
    protected final IntegerProperty heightProperty;
    protected final int maxHeight;
    protected final Set<Supplier<TagKey<Block>>> validPlacementTags;

    public DefaultableCropBodyBlock(Properties properties, Supplier<Item> produce, Supplier<Item> seed, Supplier<GrowingPlantHeadBlock> headBlock, IntegerProperty heightProperty, int maxHeight, Set<Supplier<TagKey<Block>>> validPlacementTags) {
        super(storeHeightInStaticInitializer(properties, heightProperty), Direction.UP, DEFAULT_SHAPE, false);

        this.produce = produce;
        this.seed = seed;
        this.headBlock = headBlock;
        this.heightProperty = heightProperty;
        this.maxHeight = maxHeight;
        this.validPlacementTags = validPlacementTags;

        clearHeightProperty();
    }

    public DefaultableCropBodyBlock(Properties properties, Supplier<Item> produce, Supplier<Item> seed) {
        super(storeHeightInStaticInitializer(properties, DEFAULT_HEIGHT_PROPERTY), Direction.UP, DEFAULT_SHAPE, false);

        this.produce = produce;
        this.seed = seed;
        this.headBlock = findHeadFor(() -> this);
        this.heightProperty = DEFAULT_HEIGHT_PROPERTY;
        this.maxHeight = DEFAULT_MAX_HEIGHT;
        this.validPlacementTags = ObjectOpenHashSet.of(CATags.CABlockTags.FARMLAND_BLOCKS);

        clearHeightProperty();
    }

    public DefaultableCropBodyBlock(Properties properties, Supplier<GrowingPlantHeadBlock> headBlock, IntegerProperty heightProperty, int maxHeight) {
        super(storeHeightInStaticInitializer(properties, heightProperty), Direction.UP, DEFAULT_SHAPE, false);

        this.headBlock = headBlock;
        this.produce = DefaultableCropBlock.findProduceFor(headBlock::get);
        this.seed = DefaultableCropBlock.findSeedsFor(headBlock::get);
        this.heightProperty = heightProperty;
        this.maxHeight = maxHeight;
        this.validPlacementTags = ObjectOpenHashSet.of(CATags.CABlockTags.FARMLAND_BLOCKS);

        clearHeightProperty();
    }

    public DefaultableCropBodyBlock(Properties properties, Supplier<GrowingPlantHeadBlock> headBlock) {
        super(storeHeightInStaticInitializer(properties, DEFAULT_HEIGHT_PROPERTY), Direction.UP, DEFAULT_SHAPE, false);

        this.headBlock = headBlock;
        this.produce = DefaultableCropBlock.findProduceFor(headBlock::get);
        this.seed = DefaultableCropBlock.findSeedsFor(headBlock::get);
        this.heightProperty = DEFAULT_HEIGHT_PROPERTY;
        this.maxHeight = DEFAULT_MAX_HEIGHT;
        this.validPlacementTags = ObjectOpenHashSet.of(CATags.CABlockTags.FARMLAND_BLOCKS);

        clearHeightProperty();
    }

    public DefaultableCropBodyBlock(Properties properties, IntegerProperty heightProperty, int maxHeight, Set<Supplier<TagKey<Block>>> validPlacementTags) {
        super(storeHeightInStaticInitializer(properties, heightProperty), Direction.UP, DEFAULT_SHAPE, false);

        this.headBlock = findHeadFor(() -> this);
        this.produce = DefaultableCropBlock.findProduceFor(headBlock::get);
        this.seed = DefaultableCropBlock.findSeedsFor(headBlock::get);
        this.heightProperty = heightProperty;
        this.maxHeight = maxHeight;
        this.validPlacementTags = validPlacementTags;

        clearHeightProperty();
    }

    public DefaultableCropBodyBlock(Properties properties, Set<Supplier<TagKey<Block>>> validPlacementTags) {
        super(storeHeightInStaticInitializer(properties, DEFAULT_HEIGHT_PROPERTY), Direction.UP, DEFAULT_SHAPE, false);

        this.headBlock = findHeadFor(() -> this);
        this.produce = DefaultableCropBlock.findProduceFor(headBlock::get);
        this.seed = DefaultableCropBlock.findSeedsFor(headBlock::get);
        this.heightProperty = DEFAULT_HEIGHT_PROPERTY;
        this.maxHeight = DEFAULT_MAX_HEIGHT;
        this.validPlacementTags = validPlacementTags;

        clearHeightProperty();
    }

    public DefaultableCropBodyBlock(Properties properties, IntegerProperty heightProperty, int maxHeight) {
        super(storeHeightInStaticInitializer(properties, heightProperty), Direction.UP, DEFAULT_SHAPE, false);

        this.headBlock = findHeadFor(() -> this);
        this.produce = DefaultableCropBlock.findProduceFor(headBlock::get);
        this.seed = DefaultableCropBlock.findSeedsFor(headBlock::get);
        this.heightProperty = heightProperty;
        this.maxHeight = maxHeight;
        this.validPlacementTags = ObjectOpenHashSet.of(CATags.CABlockTags.FARMLAND_BLOCKS);

        clearHeightProperty();
    }

    public DefaultableCropBodyBlock(Properties properties) {
        super(storeHeightInStaticInitializer(properties, DEFAULT_HEIGHT_PROPERTY), Direction.UP, DEFAULT_SHAPE, false);

        this.headBlock = findHeadFor(() -> this);
        this.produce = DefaultableCropBlock.findProduceFor(headBlock::get);
        this.seed = DefaultableCropBlock.findSeedsFor(headBlock::get);
        this.heightProperty = DEFAULT_HEIGHT_PROPERTY;
        this.maxHeight = DEFAULT_MAX_HEIGHT;
        this.validPlacementTags = ObjectOpenHashSet.of(CATags.CABlockTags.FARMLAND_BLOCKS);

        clearHeightProperty();
    }

    public DefaultableCropBodyBlock(Properties properties, Supplier<Item> produce, Supplier<Item> seed, Supplier<GrowingPlantHeadBlock> headBlock, Set<Supplier<TagKey<Block>>> validPlacementTags) {
        this(properties, produce, seed, headBlock, DEFAULT_HEIGHT_PROPERTY, DEFAULT_MAX_HEIGHT, validPlacementTags);
    }

    public DefaultableCropBodyBlock(Properties properties, Supplier<Item> produce, Supplier<Item> seed, Supplier<GrowingPlantHeadBlock> headBlock, IntegerProperty heightProperty, int maxHeight) {
        this(properties, produce, seed, headBlock, heightProperty, maxHeight, ObjectOpenHashSet.of(CATags.CABlockTags.FARMLAND_BLOCKS));
    }

    public DefaultableCropBodyBlock(Properties properties, Supplier<Item> produce, Supplier<Item> seed, Supplier<GrowingPlantHeadBlock> headBlock) {
        this(properties, produce, seed, headBlock, DEFAULT_HEIGHT_PROPERTY, DEFAULT_MAX_HEIGHT, ObjectOpenHashSet.of(CATags.CABlockTags.FARMLAND_BLOCKS));
    }

    public DefaultableCropBodyBlock(Properties properties, Supplier<Item> produce, Supplier<Item> seed, IntegerProperty heightProperty, int maxHeight) {
        super(storeHeightInStaticInitializer(properties, heightProperty), Direction.UP, DEFAULT_SHAPE, false);

        this.produce = produce;
        this.seed = seed;
        this.headBlock = findHeadFor(() -> this);
        this.heightProperty = heightProperty;
        this.maxHeight = maxHeight;
        this.validPlacementTags = ObjectOpenHashSet.of(CATags.CABlockTags.FARMLAND_BLOCKS);

        clearHeightProperty();
    }

    public static Supplier<GrowingPlantHeadBlock> findHeadFor(Supplier<Block> targetBlockSup) {
        return () -> RegistryUtil.getObjectFrom(targetBlockSup, targetBlockId -> targetBlockId.withPath(path -> path.replace("_body_", "_head_")))
                .filter(GrowingPlantHeadBlock.class::isInstance)
                .map(GrowingPlantHeadBlock.class::cast)
                .orElse(null);
    }

    private static Properties storeHeightInStaticInitializer(Properties properties, IntegerProperty heightProperty) {
        pendingHeightProperty = heightProperty;
        return properties;
    }

    private static void clearHeightProperty() {
        pendingHeightProperty = null;
    }

    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
        super.createBlockStateDefinition(builder);

        IntegerProperty prop = heightProperty != null ? heightProperty : pendingHeightProperty;

        if (prop != null) builder.add(prop);
    }

    @Override
    public boolean canSurvive(BlockState state, LevelReader level, BlockPos pos) {
        BlockPos relativePlacementPos = pos.relative(this.growthDirection.getOpposite());
        BlockState baseState = level.getBlockState(relativePlacementPos);

        return canAttachTo(baseState) && allowPlacementOn(baseState, level, relativePlacementPos);
    }

    @Override
    public @NotNull BlockState updateShape(BlockState state, Direction facing, BlockState facingState, LevelAccessor level, BlockPos currentPos, BlockPos facingPos) {
        if (facing == growthDirection.getOpposite() && !state.canSurvive(level, currentPos))
            level.scheduleTick(currentPos, this, 1);

        GrowingPlantHeadBlock headBlock = getHeadBlock();
        IntegerProperty ageProperty = headBlock instanceof DefaultableCropHeadBlock defHeadBlock ? defHeadBlock.getAgeProperty() : GrowingPlantHeadBlock.AGE;
        IntegerProperty heightProperty = headBlock instanceof DefaultableCropHeadBlock defHeadBlock ? defHeadBlock.getHeightProperty() : DEFAULT_HEIGHT_PROPERTY;

        if (ageProperty != null && facing == growthDirection && !facingState.is(this) && !facingState.is(headBlock))
            return headBlock.defaultBlockState().setValue(ageProperty, getMaxHeight()).setValue(heightProperty, getHeight(state));
        if (scheduleFluidTicks) level.scheduleTick(currentPos, Fluids.WATER, Fluids.WATER.getTickDelay(level));

        return super.updateShape(state, facing, facingState, level, currentPos, facingPos);
    }

    @Override
    public @NotNull ItemStack getCloneItemStack(BlockGetter level, BlockPos pos, BlockState state) {
        return getSeed().get().getDefaultInstance();
    }

    @Override
    public boolean allowPlacementOn(BlockState targetState, BlockGetter curLevel, BlockPos targetPos) {
        return CropInstance.super.allowPlacementOn(targetState, curLevel, targetPos) || targetState.is(getHeadBlock()) || targetState.is(getBodyBlock());
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
        return heightProperty;
    }

    @Override
    public VoxelShape[] getShapeByAge() {
        return new VoxelShape[]{DEFAULT_SHAPE};
    }

    @Override
    public int getMaxAge() {
        return getHeadBlock() instanceof CropInstance cropInstance ? cropInstance.getMaxAge() : maxHeight;
    }

    @Override
    public Set<Supplier<TagKey<Block>>> getValidPlacementTags() {
        return validPlacementTags;
    }

    @Override
    protected @NotNull GrowingPlantHeadBlock getHeadBlock() {
        return headBlock.get();
    }

    public boolean isMaxHeight(BlockState targetState) {
        return getHeight(targetState) >= getMaxHeight();
    }

    public int getHeight(BlockState targetState) {
        return targetState.getValue(heightProperty);
    }

    public int getMaxHeight() {
        return maxHeight;
    }
}