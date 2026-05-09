package io.github.chaosawakens.content.block.vegetation.generic;

import com.mememan.nexus.util.RegistryUtil;
import com.mememan.nexus.util.StringUtil;
import io.github.chaosawakens.content.registry.CATags;
import it.unimi.dsi.fastutil.objects.ObjectOpenHashSet;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.tags.TagKey;
import net.minecraft.util.Mth;
import net.minecraft.util.RandomSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.monster.Ravager;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.GameRules;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.block.*;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.IntegerProperty;
import net.minecraft.world.level.gameevent.GameEvent;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.Set;
import java.util.function.Function;
import java.util.function.Supplier;

public class DefaultableCropBlock extends BushBlock implements CropInstance, BonemealableBlock {
    @Nullable
    private static IntegerProperty pendingAgeProperty; // We can skip the whole "store in [either ThreadLocal or AtomicReference or whatever]" shtick here cuz registration is single-threaded, and if a mod does any goofy hacky stuff then it's their fault anyway
    public static final VoxelShape[] DEFAULT_SHAPE_BY_AGE = new VoxelShape[] {
            Block.box(0.0, 0.0, 0.0, 16.0, 2.0, 16.0),
            Block.box(0.0, 0.0, 0.0, 16.0, 4.0, 16.0),
            Block.box(0.0, 0.0, 0.0, 16.0, 6.0, 16.0),
            Block.box(0.0, 0.0, 0.0, 16.0, 8.0, 16.0),
            Block.box(0.0, 0.0, 0.0, 16.0, 10.0, 16.0),
            Block.box(0.0, 0.0, 0.0, 16.0, 12.0, 16.0),
            Block.box(0.0, 0.0, 0.0, 16.0, 14.0, 16.0),
            Block.box(0.0, 0.0, 0.0, 16.0, 16.0, 16.0)
    };
    @Nullable
    protected final VoxelShape[] shapeByAge;
    protected final Supplier<Item> produce;
    protected final Supplier<Item> seed;
    protected final IntegerProperty ageProperty;
    protected final int maxAge;
    protected final Set<Supplier<TagKey<Block>>> validPlacementTags;

    public DefaultableCropBlock(Properties properties, @Nullable VoxelShape[] shapeByAge, Supplier<Item> produce, Supplier<Item> seed, IntegerProperty ageProperty, int maxAge, Set<Supplier<TagKey<Block>>> validPlacementTags) {
        super(storeAgeInStaticInitializer(properties, ageProperty, maxAge));

        this.shapeByAge = shapeByAge;
        this.produce = produce;
        this.seed = seed;
        this.ageProperty = ageProperty;
        this.maxAge = maxAge;
        this.validPlacementTags = validPlacementTags;

        cleanupPendingAge();
        validateShapesByAge(this, shapeByAge, maxAge);
        registerDefaultState(getStateDefinition().any().setValue(getAgeProperty(), 0));
    }

    public DefaultableCropBlock(Properties properties, Supplier<Item> produce, Supplier<Item> seed, IntegerProperty ageProperty, int maxAge, @Nullable VoxelShape[] shapeByAge) {
        this(properties, shapeByAge, produce, seed, ageProperty, maxAge, ObjectOpenHashSet.of(CATags.CABlockTags.FARMLAND_BLOCKS));
    }

    public DefaultableCropBlock(Properties properties, Supplier<Item> produce, Supplier<Item> seed, @Nullable VoxelShape[] shapeByAge) {
        this(properties, produce, seed, BlockStateProperties.AGE_3, BlockStateProperties.MAX_AGE_3, shapeByAge);
    }

    public DefaultableCropBlock(Properties properties, Supplier<Item> produce, Supplier<Item> seed, int maxAge, @Nullable VoxelShape[] shapeByAge) {
        this(properties, produce, seed, IntegerProperty.create("age", 0, maxAge), maxAge, shapeByAge);
    }

    public DefaultableCropBlock(Properties properties, @Nullable VoxelShape[] shapeByAge, IntegerProperty ageProperty, int maxAge) {
        super(storeAgeInStaticInitializer(properties, ageProperty, maxAge));

        this.shapeByAge = shapeByAge;
        this.produce = findProduceFor(() -> this);
        this.seed = findSeedsFor(() -> this);
        this.ageProperty = ageProperty;
        this.maxAge = maxAge;
        this.validPlacementTags = ObjectOpenHashSet.of(CATags.CABlockTags.FARMLAND_BLOCKS);

        cleanupPendingAge();
        validateShapesByAge(this, shapeByAge, maxAge);
        registerDefaultState(stateDefinition.any().setValue(getAgeProperty(), 0));
    }

    public DefaultableCropBlock(Properties properties, @Nullable VoxelShape[] shapeByAge, int maxAge) {
        this(properties, shapeByAge, IntegerProperty.create("age", 0, maxAge), maxAge);
    }

    public DefaultableCropBlock(Properties properties, @Nullable VoxelShape[] shapeByAge) {
        this(properties, shapeByAge, BlockStateProperties.AGE_3, BlockStateProperties.MAX_AGE_3);
    }

    public DefaultableCropBlock(Properties properties, Supplier<Item> produce, Supplier<Item> seed, IntegerProperty ageProperty, int maxAge) {
        this(properties, produce, seed, ageProperty, maxAge, null);
    }

    public DefaultableCropBlock(Properties properties, Supplier<Item> produce, Supplier<Item> seed, int maxAge) {
        this(properties, produce, seed, IntegerProperty.create("age", 0, maxAge), maxAge, null);
    }

    public DefaultableCropBlock(Properties properties, Supplier<Item> produce, Supplier<Item> seed) {
        this(properties, produce, seed, BlockStateProperties.AGE_3, BlockStateProperties.MAX_AGE_3, null);
    }

    public DefaultableCropBlock(Properties properties, IntegerProperty ageProperty, int maxAge) {
        this(properties, null, ageProperty, maxAge);
    }

    public DefaultableCropBlock(Properties properties, int maxAge) {
        this(properties, (VoxelShape[]) null, maxAge);
    }

    public DefaultableCropBlock(Properties properties) {
        this(properties, null);
    }

    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
        IntegerProperty chosenProp = ageProperty != null ? ageProperty : pendingAgeProperty;

        if (chosenProp != null) builder.add(chosenProp);
    }

    @Override
    protected boolean mayPlaceOn(BlockState state, BlockGetter level, BlockPos pos) {
        return allowPlacementOn(state, level, pos);
    }

    @Override
    public boolean canSurvive(BlockState state, LevelReader level, BlockPos pos) {
        return (level.getRawBrightness(pos, 0) >= 8 || level.canSeeSky(pos)) && mayPlaceOn(level.getBlockState(pos.below()), level, pos.below()); // TODO Probably look into re-integrating this with the super-call, cuz Forge's #canSustainPlant method overwrites the basic placement condition with its own logic (too lazy to explore why atm)
    }

    @Override
    public @NotNull VoxelShape getShape(BlockState state, BlockGetter level, BlockPos pos, CollisionContext context) {
        return getShapeByAge()[getAge(state)];
    }

    @Override
    public @NotNull InteractionResult use(BlockState state, Level level, BlockPos pos, Player player, InteractionHand hand, BlockHitResult hit) {
        if (!isMaxAge(state) && player.getItemInHand(hand).is(Items.BONE_MEAL)) return InteractionResult.PASS;
        else if (getAge(state) > 1) {
            int produceCount = 1 + level.random.nextInt(2);

            popResource(level, pos, new ItemStack(getProduce().get(), produceCount + (isMaxAge(state) ? 1 : 0)));

            level.playSound(player, pos, SoundEvents.SWEET_BERRY_BUSH_PICK_BERRIES, SoundSource.BLOCKS, 1.0F, 0.8F + level.random.nextFloat() * 0.4F);

            BlockState updatedCropState = state.setValue(getAgeProperty(), 1);

            level.setBlock(pos, updatedCropState, Block.UPDATE_CLIENTS);
            level.gameEvent(GameEvent.BLOCK_CHANGE, pos, GameEvent.Context.of(player, updatedCropState));

            return InteractionResult.sidedSuccess(level.isClientSide);
        } else return super.use(state, level, pos, player, hand, hit);
    }

    @Override
    public void randomTick(BlockState state, ServerLevel level, BlockPos pos, RandomSource random) {
        if (level.getRawBrightness(pos, 0) >= 9) {
            if (getAge(state) < getMaxAge()) {
                float growthSpeed = getGrowthSpeed(this, level, pos);
                int randomTickChance = (int) (25.0F / growthSpeed);

                if (random.nextInt(randomTickChance + 1) == 0) {
                    level.setBlock(pos, state.setValue(getAgeProperty(), getAge(state) + 1), Block.UPDATE_CLIENTS);
                }
            }
        }
    }

    @Override
    public boolean isRandomlyTicking(BlockState state) {
        return !isMaxAge(state);
    }

    @Override
    public void entityInside(BlockState state, Level level, BlockPos pos, Entity entity) {
        if (entity instanceof Ravager && level.getGameRules().getBoolean(GameRules.RULE_MOBGRIEFING)) {
            level.destroyBlock(pos, true, entity);
        }

        super.entityInside(state, level, pos, entity);
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
        return shapeByAge == null ? DEFAULT_SHAPE_BY_AGE : shapeByAge;
    }

    @Override
    public int getMaxAge() {
        return maxAge;
    }

    @Override
    public Set<Supplier<TagKey<Block>>> getValidPlacementTags() {
        return validPlacementTags;
    }

    @Override
    public boolean isValidBonemealTarget(LevelReader levelReader, BlockPos blockPos, BlockState blockState, boolean b) {
        return !isMaxAge(blockState);
    }

    @Override
    public boolean isBonemealSuccess(Level level, RandomSource randomSource, BlockPos blockPos, BlockState blockState) {
        return true;
    }

    @Override
    public void performBonemeal(ServerLevel serverLevel, RandomSource randomSource, BlockPos blockPos, BlockState blockState) {
        growCropWithBonemeal(serverLevel, blockPos, blockState);
    }

    public void growCropWithBonemeal(Level curLevel, BlockPos targetPos, BlockState targetState) {
        int bonemealAge = Mth.clamp(getAge(targetState) + getBoneMealAgeIncrease(curLevel), 0, getMaxAge());

        curLevel.setBlock(targetPos, targetState.setValue(getAgeProperty(), bonemealAge), 2);
    }

    public int getAge(BlockState targetState) {
        return targetState.getValue(ageProperty);
    }

    public boolean isMaxAge(BlockState targetState) {
        return getAge(targetState) >= getMaxAge();
    }

    protected int getBoneMealAgeIncrease(Level curLevel) {
        return Mth.nextInt(curLevel.random, 1, getMaxAge());
    }

    public static Supplier<Item> findProduceFor(Supplier<Block> targetBlockSup) {
        Supplier<Item> targetBlockItem = () -> targetBlockSup.get().asItem();

        return () -> RegistryUtil.getObjectFrom(targetBlockItem, targetBlockId -> targetBlockId.withPath(path -> path.replace("_seeds", "")))
                .or(() -> RegistryUtil.getObjectFrom(targetBlockItem, targetBlockId -> targetBlockId.withPath(path -> path.replace("_body_block", ""))))
                .or(() -> RegistryUtil.getObjectFrom(targetBlockItem, targetBlockId -> targetBlockId.withPath(path -> path.replace("_head_block", ""))))
                .or(() -> RegistryUtil.getObjectFrom(targetBlockItem, targetBlockId -> targetBlockId.withPath(path -> path.replace("_plant", ""))))
                .or(() -> RegistryUtil.getObjectFrom(targetBlockItem, targetBlockId -> targetBlockId.withPath(path -> path.replace("_" + StringUtil.lastToken(path), ""))))
                .orElse(null);
    }

    public static Supplier<Item> findSeedsFor(Supplier<Block> targetBlockSup) {
        Supplier<Item> targetBlockItem = () -> targetBlockSup.get().asItem();

        return () -> RegistryUtil.getObjectFrom(targetBlockItem, Function.identity())
                .or(() -> RegistryUtil.getObjectFrom(targetBlockItem, targetBlockId -> targetBlockId.withPath(path -> path.replace("_plant", "_seeds"))))
                .or(() -> RegistryUtil.getObjectFrom(targetBlockItem, targetBlockId -> targetBlockId.withPath(path -> path.replace("_body_block", "_seeds"))))
                .or(() -> RegistryUtil.getObjectFrom(targetBlockItem, targetBlockId -> targetBlockId.withPath(path -> path.replace("_head_block", "_seeds"))))
                .or(() -> RegistryUtil.getObjectFrom(targetBlockItem, targetBlockId -> targetBlockId.withPath(path -> path.replace("_" + StringUtil.lastToken(path), "_seeds"))))
                .orElse(null);
    }

    protected static float getGrowthSpeed(Block targetBlock, BlockGetter curLevel, BlockPos targetPos) {
        float baseSpeed = 1.0F;
        BlockPos belowPos = targetPos.below();

        for (int offsetX = -1; offsetX <= 1; ++offsetX) {
            for (int offsetZ = -1; offsetZ <= 1; ++offsetZ) {
                float bonus = 0.0F;
                BlockState curState = curLevel.getBlockState(belowPos.offset(offsetX, 0, offsetZ));

                if (curState.is(Blocks.FARMLAND)) bonus = curState.getValue(FarmBlock.MOISTURE) > 0 ? 3.0F : 1.0F;
                if (offsetX != 0 || offsetZ != 0) bonus /= 4.0F;

                baseSpeed += bonus;
            }
        }

        BlockPos westPos = targetPos.west();
        BlockPos eastPos = targetPos.east();

        if ((curLevel.getBlockState(westPos).is(targetBlock) || curLevel.getBlockState(eastPos).is(targetBlock))
                && (curLevel.getBlockState(targetPos.north()).is(targetBlock) || curLevel.getBlockState(targetPos.south()).is(targetBlock))) {
            baseSpeed /= 2.0F;
        } else if (curLevel.getBlockState(westPos.north()).is(targetBlock)
                || curLevel.getBlockState(eastPos.north()).is(targetBlock)
                || curLevel.getBlockState(eastPos.south()).is(targetBlock)
                || curLevel.getBlockState(westPos.south()).is(targetBlock)) {
            baseSpeed /= 2.0F;
        }

        return baseSpeed;
    }

    private static void validateShapesByAge(Block targetBlock, VoxelShape[] shapeByAge, int maxAge) {
        VoxelShape[] chosenShapeByAge = shapeByAge != null ? shapeByAge : DEFAULT_SHAPE_BY_AGE;

        if (chosenShapeByAge.length < maxAge) {
            throw new IllegalArgumentException(String.format("shapeByAge length must be at least maxAge for %s '%s' with maxAge: %d, got: %d", targetBlock.getClass().getSimpleName(), targetBlock.builtInRegistryHolder().key().location(), maxAge, chosenShapeByAge.length));
        }
    }

    private static Properties storeAgeInStaticInitializer(Properties properties, IntegerProperty ageProperty, int maxAge) {
        pendingAgeProperty = ageProperty;

        return properties;
    }

    private static void cleanupPendingAge() {
        pendingAgeProperty = null;
    }
}