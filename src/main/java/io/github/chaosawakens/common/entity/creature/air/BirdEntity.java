package io.github.chaosawakens.common.entity.creature.air;

import io.github.chaosawakens.api.animation.IAnimatableEntity;
import io.github.chaosawakens.api.animation.IAnimationBuilder;
import io.github.chaosawakens.api.animation.SingletonAnimationBuilder;
import io.github.chaosawakens.api.animation.WrappedAnimationController;
import io.github.chaosawakens.common.entity.ai.controllers.movement.air.JumpFlyingMovementController;
import io.github.chaosawakens.common.entity.base.AnimatableAnimalEntity;
import io.github.chaosawakens.common.registry.CABlocks;
import io.github.chaosawakens.common.registry.CAEntityTypes;
import io.github.chaosawakens.common.registry.CAItems;
import it.unimi.dsi.fastutil.objects.ObjectArrayList;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.block.MagmaBlock;
import net.minecraft.entity.*;
import net.minecraft.entity.ai.RandomPositionGenerator;
import net.minecraft.entity.ai.attributes.AttributeModifierMap;
import net.minecraft.entity.ai.attributes.Attributes;
import net.minecraft.entity.ai.goal.*;
import net.minecraft.entity.monster.MonsterEntity;
import net.minecraft.entity.passive.AnimalEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.item.crafting.Ingredient;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.network.datasync.DataParameter;
import net.minecraft.network.datasync.DataSerializers;
import net.minecraft.network.datasync.EntityDataManager;
import net.minecraft.pathfinding.FlyingPathNavigator;
import net.minecraft.pathfinding.PathNavigator;
import net.minecraft.tags.BlockTags;
import net.minecraft.util.DamageSource;
import net.minecraft.util.Direction;
import net.minecraft.util.SoundEvent;
import net.minecraft.util.SoundEvents;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.vector.Vector3d;
import net.minecraft.world.DifficultyInstance;
import net.minecraft.world.IServerWorld;
import net.minecraft.world.IWorld;
import net.minecraft.world.World;
import net.minecraft.world.server.ServerWorld;
import software.bernie.geckolib3.core.PlayState;
import software.bernie.geckolib3.core.builder.ILoopType;
import software.bernie.geckolib3.core.event.predicate.AnimationEvent;
import software.bernie.geckolib3.core.manager.AnimationFactory;

import javax.annotation.Nullable;
import java.util.Random;

public class BirdEntity extends AnimatableAnimalEntity {
    private final AnimationFactory factory = new AnimationFactory(this);
    private final ObjectArrayList<WrappedAnimationController<BirdEntity>> birdControllers = new ObjectArrayList<>(1);
    private final ObjectArrayList<IAnimationBuilder> birdAnimations = new ObjectArrayList<>(3);
    private final WrappedAnimationController<BirdEntity> mainController = createMainMappedController("birdmaincontroller");
    private final SingletonAnimationBuilder flyAnim = new SingletonAnimationBuilder(this, "Fly", ILoopType.EDefaultLoopTypes.LOOP);
    private final SingletonAnimationBuilder idleAnim = new SingletonAnimationBuilder(this, "Idle", ILoopType.EDefaultLoopTypes.LOOP);
    private final SingletonAnimationBuilder walkAnim = new SingletonAnimationBuilder(this, "Walk", ILoopType.EDefaultLoopTypes.LOOP);
    private static final DataParameter<Integer> TYPE_ID = EntityDataManager.defineId(BirdEntity.class, DataSerializers.INT);
    private static final Ingredient FOOD_ITEMS = Ingredient.of(Items.WHEAT_SEEDS, Items.MELON_SEEDS, Items.PUMPKIN_SEEDS, Items.BEETROOT_SEEDS, CAItems.LETTUCE_SEEDS.get(), CAItems.CORN_SEEDS.get(), CAItems.RADISH_SEEDS.get(), CAItems.STRAWBERRY_SEEDS.get(), CAItems.TOMATO_SEEDS.get());
    public static final String BIRD_MDF_NAME = "bird";

    public BirdEntity(EntityType<? extends AnimalEntity> type, World world) {
        super(type, world);
        this.moveControl = new JumpFlyingMovementController(this, 10, false);
    }

    public static AttributeModifierMap.MutableAttribute setCustomAttributes() {
        return MobEntity.createLivingAttributes()
                .add(Attributes.MAX_HEALTH, 5)
                .add(Attributes.FOLLOW_RANGE, 10)
                .add(Attributes.MOVEMENT_SPEED, 0.3F)
                .add(Attributes.FLYING_SPEED, 0.4);
    }
    @Override
    protected void registerGoals() {
        this.goalSelector.addGoal(0, new SwimGoal(this));
        this.goalSelector.addGoal(1, new PanicGoal(this, 1.25D));
        this.goalSelector.addGoal(1, new AvoidEntityGoal<>(this, MonsterEntity.class, 12.0F, 1.2D, 2.0D));
        this.goalSelector.addGoal(1, new LandingRandomFlyingGoal(this, 1D));
        this.goalSelector.addGoal(2, new BreedGoal(this, 1.2D));
        this.goalSelector.addGoal(2, new TemptGoal(this, 1.2D, false, FOOD_ITEMS));
        this.goalSelector.addGoal(2, new FollowParentGoal(this, 1.2D));
        this.goalSelector.addGoal(3, new LookAtGoal(this, PlayerEntity.class, 6.0F));
        this.goalSelector.addGoal(4, new LookRandomlyGoal(this));
        this.goalSelector.addGoal(4, new WaterAvoidingRandomWalkingGoal(this, 1.1D));
    }

    @Override
    public void aiStep() {
        super.aiStep();
        Vector3d deltaMovement = this.getDeltaMovement();
        if (!this.onGround && deltaMovement.y < 0.0D) {
            this.setDeltaMovement(deltaMovement.multiply(1.0D, 0.6D, 1.0D));
        }
    }

    @Override
    public boolean isFood(ItemStack stack) {
        return FOOD_ITEMS.test(stack);
    }

    @Override
    public boolean causeFallDamage(float fallDistance, float damageMultiplier) {
        return false;
    }

    @Override
    protected void checkFallDamage(double y, boolean onGround, BlockState state, BlockPos pos) {
    }

    @Override
    protected void defineSynchedData() {
        super.defineSynchedData();
        this.entityData.define(TYPE_ID, 0);
    }

    public static boolean checkBirdSpawnRules(EntityType<? extends AnimalEntity> animal, IWorld world, SpawnReason spawnReason, BlockPos pos, Random random) {
        BlockState state = world.getBlockState(pos.below());
        return (state.is(BlockTags.LEAVES) || state.is(BlockTags.LOGS) || state.is(Blocks.GRASS_BLOCK) || state.is(CABlocks.DENSE_GRASS_BLOCK.get())) && world.getRawBrightness(pos, 0) > 8;
    }

    @Override
    public ILivingEntityData finalizeSpawn(IServerWorld world, DifficultyInstance difficulty, SpawnReason reason, @Nullable ILivingEntityData entityData, @Nullable CompoundNBT nbt) {
        int colour = random.nextInt(6);

        if (entityData instanceof BirdData) colour = ((BirdData) entityData).birdColour;
        else entityData = new BirdData(colour);

        setColour(colour);
        return super.finalizeSpawn(world, difficulty, reason, entityData, nbt);
    }
    @Override
    public void addAdditionalSaveData(CompoundNBT nbt) {
        super.addAdditionalSaveData(nbt);
        nbt.putInt("BirdColour", this.getColour());
    }

    @Override
    public void readAdditionalSaveData(CompoundNBT nbt) {
        super.readAdditionalSaveData(nbt);
        setColour(nbt.getInt("BirdColour"));
    }

    @Override
    protected PathNavigator createNavigation(World world) {
        FlyingPathNavigator flyingpathnavigator = new FlyingPathNavigator(this, world);
        flyingpathnavigator.setCanOpenDoors(false);
        flyingpathnavigator.setCanFloat(true);
        flyingpathnavigator.setCanPassDoors(true);
        return flyingpathnavigator;
    }

    @Override
    public ObjectArrayList<IAnimationBuilder> getCachedAnimations() {
        return birdAnimations;
    }

    @Override
    public AnimationFactory getFactory() {
        return factory;
    }

    @Override
    public WrappedAnimationController<? extends AnimatableAnimalEntity> getMainWrappedController() {
        return mainController;
    }

    @Override
    public ObjectArrayList<WrappedAnimationController<BirdEntity>> getWrappedControllers() {
        return birdControllers;
    }

    @Override
    public int animationInterval() {
        return 2;
    }

    @Override
    public <E extends IAnimatableEntity> PlayState mainPredicate(AnimationEvent<E> event) {
        return PlayState.CONTINUE;
    }

    @Nullable
    @Override
    public IAnimationBuilder getIdleAnim() {
        return idleAnim;
    }

    @Nullable
    @Override
    public IAnimationBuilder getWalkAnim() {
        return walkAnim;
    }

    @Override
    protected SoundEvent getAmbientSound() {
        return SoundEvents.PARROT_AMBIENT;
    }

    @Override
    protected SoundEvent getHurtSound(DamageSource damageSourceIn) {
        return SoundEvents.PARROT_HURT;
    }

    @Override
    protected SoundEvent getDeathSound() {
        return SoundEvents.PARROT_DEATH;
    }

    @Nullable
    @Override
    public IAnimationBuilder getDeathAnim() {
        return null;
    }

    @Override
    public String getOwnerMDFileName() {
        return BIRD_MDF_NAME;
    }

    @Nullable
    @Override
    public AgeableEntity getBreedOffspring(ServerWorld world, AgeableEntity mate) {
        BirdEntity offspring = CAEntityTypes.BIRD.get().create(world);

        assert offspring != null;
        offspring.setColour(((BirdEntity) mate).getColour());

        return offspring;
    }

    public int getColour() {
        return entityData.get(TYPE_ID);
    }

    public void setColour(int colour) {
        entityData.set(TYPE_ID, colour);
    }

    @Override
    protected void handleBaseAnimations() {
        if (getIdleAnim() != null && !isMoving() && isOnGround()) playAnimation(getIdleAnim(), false);
        if (getWalkAnim() != null && isMoving() && isOnGround()) playAnimation(getWalkAnim(), false);
        if (!isOnGround()) playAnimation(flyAnim, true);
    }

    private static class BirdData extends AgeableData {
        public final int birdColour;

        private BirdData(int birdColour) {
            super(true);
            this.birdColour = birdColour;
        }
    }
    private class LandingRandomFlyingGoal extends WaterAvoidingRandomFlyingGoal {
        public LandingRandomFlyingGoal(CreatureEntity creature, double speedModifier) {
            super(creature, speedModifier);
        }

        @Nullable
        protected Vector3d getPosition() {
            Vector3d targetPos = null;
            if (this.mob.isInWater())
                targetPos = RandomPositionGenerator.getLandPos(this.mob, 15, 15);

            if (this.mob.getRandom().nextFloat() >= this.probability * 10)
                targetPos = this.getElevatedPos(targetPos);

            if (this.mob.getRandom().nextFloat() >= this.probability)
                targetPos = RandomPositionGenerator.getAirPos(mob, 10, 7, 8, BirdEntity.this.getViewVector(0), Math.PI / 2D);

            return targetPos == null ? super.getPosition() : targetPos;
        }

        @Nullable
        private Vector3d getElevatedPos(Vector3d fallback) {
            BlockPos blockpos = this.mob.blockPosition();
            BlockPos.Mutable mutable = new BlockPos.Mutable();
            BlockPos.Mutable mutable1 = new BlockPos.Mutable();

            for(BlockPos blockpos1 : BlockPos.betweenClosed(MathHelper.floor(this.mob.getX() - 30.0D), MathHelper.floor(this.mob.getY() + 3.0D), MathHelper.floor(this.mob.getZ() - 30.0D), MathHelper.floor(this.mob.getX() + 30.0D), MathHelper.floor(this.mob.getY() + 10.0D), MathHelper.floor(this.mob.getZ() + 30.0D))) {
                if (!blockpos.equals(blockpos1) && this.mob.getRandom().nextFloat() >= Math.pow(probability, blockpos1.getY() - this.mob.getY() + 1)) {
                    BlockState state = this.mob.level.getBlockState(mutable1.setWithOffset(blockpos1, Direction.DOWN));
                    boolean flag = state.isCollisionShapeFullBlock(mob.level, mutable1) && !(state.getBlock() instanceof MagmaBlock);
                    if (flag && this.mob.level.isEmptyBlock(blockpos1) && this.mob.level.isEmptyBlock(mutable.setWithOffset(blockpos1, Direction.UP)))
                        return Vector3d.atBottomCenterOf(blockpos1);
                }
            }

            return fallback;
        }
    }
}
