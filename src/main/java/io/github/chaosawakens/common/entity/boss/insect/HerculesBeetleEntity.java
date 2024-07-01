package io.github.chaosawakens.common.entity.boss.insect;

import io.github.chaosawakens.api.animation.IAnimatableEntity;
import io.github.chaosawakens.api.animation.IAnimationBuilder;
import io.github.chaosawakens.api.animation.SingletonAnimationBuilder;
import io.github.chaosawakens.api.animation.WrappedAnimationController;
import io.github.chaosawakens.client.sounds.tickable.boss.insect.HerculesBeetleTickableIdleSound;
import io.github.chaosawakens.client.sounds.tickable.boss.insect.HerculesBeetleTickableMunchSound;
import io.github.chaosawakens.client.sounds.tickable.boss.insect.HerculesBeetleTickableWalkSound;
import io.github.chaosawakens.common.entity.ai.AnimatableMoveToTargetGoal;
import io.github.chaosawakens.common.entity.ai.goals.boss.insect.herculesbeetle.HerculesBeetleDefenseMechanismGoal;
import io.github.chaosawakens.common.entity.ai.goals.boss.insect.herculesbeetle.HerculesBeetleMunchGoal;
import io.github.chaosawakens.common.entity.ai.goals.hostile.AnimatableMeleeGoal;
import io.github.chaosawakens.common.entity.base.AnimatableMonsterEntity;
import io.github.chaosawakens.common.entity.boss.robo.RoboJefferyEntity;
import io.github.chaosawakens.common.entity.hostile.robo.RoboPounderEntity;
import io.github.chaosawakens.common.entity.hostile.robo.RoboSniperEntity;
import io.github.chaosawakens.common.entity.hostile.robo.RoboWarriorEntity;
import io.github.chaosawakens.common.entity.misc.CAScreenShakeEntity;
import io.github.chaosawakens.common.registry.CASoundEvents;
import io.github.chaosawakens.common.util.EntityUtil;
import io.github.chaosawakens.common.util.EnumUtil;
import io.github.chaosawakens.common.util.MathUtil;
import it.unimi.dsi.fastutil.objects.ObjectArrayList;
import net.minecraft.block.BlockState;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.MobEntity;
import net.minecraft.entity.ai.attributes.AttributeModifierMap;
import net.minecraft.entity.ai.attributes.Attributes;
import net.minecraft.entity.ai.controller.LookController;
import net.minecraft.entity.ai.controller.MovementController;
import net.minecraft.entity.ai.goal.HurtByTargetGoal;
import net.minecraft.entity.ai.goal.NearestAttackableTargetGoal;
import net.minecraft.entity.ai.goal.WaterAvoidingRandomWalkingGoal;
import net.minecraft.entity.merchant.villager.AbstractVillagerEntity;
import net.minecraft.entity.monster.MonsterEntity;
import net.minecraft.entity.passive.IronGolemEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.network.datasync.DataParameter;
import net.minecraft.network.datasync.DataSerializers;
import net.minecraft.network.datasync.EntityDataManager;
import net.minecraft.pathfinding.PathNodeType;
import net.minecraft.tags.BlockTags;
import net.minecraft.util.DamageSource;
import net.minecraft.util.SoundEvent;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.vector.Vector3d;
import net.minecraft.util.math.vector.Vector3i;
import net.minecraft.world.World;
import software.bernie.geckolib3.core.PlayState;
import software.bernie.geckolib3.core.builder.ILoopType;
import software.bernie.geckolib3.core.event.predicate.AnimationEvent;
import software.bernie.geckolib3.core.manager.AnimationFactory;

import javax.annotation.Nullable;

public class HerculesBeetleEntity extends AnimatableMonsterEntity {
    private final AnimationFactory factory = new AnimationFactory(this);
    private final ObjectArrayList<WrappedAnimationController<HerculesBeetleEntity>> herculesBeetleControllers = new ObjectArrayList<WrappedAnimationController<HerculesBeetleEntity>>(5);
    private final ObjectArrayList<IAnimationBuilder> herculesBeetleAnimations = new ObjectArrayList<IAnimationBuilder>(16);
    private static final DataParameter<Boolean> DOCILE = EntityDataManager.defineId(HerculesBeetleEntity.class, DataSerializers.BOOLEAN);
    private static final DataParameter<Boolean> AWAKENING = EntityDataManager.defineId(HerculesBeetleEntity.class, DataSerializers.BOOLEAN);
    private static final DataParameter<Boolean> ACTIVE = EntityDataManager.defineId(HerculesBeetleEntity.class, DataSerializers.BOOLEAN);
    private static final DataParameter<Boolean> FLINGING = EntityDataManager.defineId(HerculesBeetleEntity.class, DataSerializers.BOOLEAN);
    private static final DataParameter<Integer> DOCILITY_TIME = EntityDataManager.defineId(HerculesBeetleEntity.class, DataSerializers.INT);
    private static final DataParameter<Integer> ACTIVITY_TIME = EntityDataManager.defineId(HerculesBeetleEntity.class, DataSerializers.INT);
    private final WrappedAnimationController<HerculesBeetleEntity> mainController = createMainMappedController("herculesbeetlemaincontroller");
    private final WrappedAnimationController<HerculesBeetleEntity> attackController = createMappedController("herculesbeetleattackcontroller", this::attackPredicate);
    private final WrappedAnimationController<HerculesBeetleEntity> munchController = createMappedController("herculesbeetlemunchcontroller", this::munchPredicate);
    private final WrappedAnimationController<HerculesBeetleEntity> awakeningController = createMappedController("herculesbeetleawakeningcontroller", this::awakeningPredicate);
    private final SingletonAnimationBuilder docileAnim = new SingletonAnimationBuilder(this, "Docile", ILoopType.EDefaultLoopTypes.LOOP).setWrappedController(awakeningController);
    private final SingletonAnimationBuilder awakenedAnim = new SingletonAnimationBuilder(this, "Awakened", ILoopType.EDefaultLoopTypes.PLAY_ONCE).setWrappedController(awakeningController);
    private final SingletonAnimationBuilder idleAnim = new SingletonAnimationBuilder(this, "Idle", ILoopType.EDefaultLoopTypes.LOOP);
    private final SingletonAnimationBuilder walkAnim = new SingletonAnimationBuilder(this, "Walk", ILoopType.EDefaultLoopTypes.LOOP);
    private final SingletonAnimationBuilder leapAnim = new SingletonAnimationBuilder(this, "Leap", ILoopType.EDefaultLoopTypes.PLAY_ONCE);
    private final SingletonAnimationBuilder flyAnim = new SingletonAnimationBuilder(this, "Fly", ILoopType.EDefaultLoopTypes.LOOP);
    private final SingletonAnimationBuilder deathAnim = new SingletonAnimationBuilder(this, "Death", ILoopType.EDefaultLoopTypes.PLAY_ONCE).setWrappedController(attackController);
    private final SingletonAnimationBuilder deathMidairAnim = new SingletonAnimationBuilder(this, "Death (Midair)", ILoopType.EDefaultLoopTypes.PLAY_ONCE).setWrappedController(attackController);
    private final SingletonAnimationBuilder deathMidairFallingAnim = new SingletonAnimationBuilder(this, "Death (Midair, Falling)", ILoopType.EDefaultLoopTypes.LOOP).setWrappedController(attackController);
    private final SingletonAnimationBuilder deathFallenAnim = new SingletonAnimationBuilder(this, "Death (Fallen)", ILoopType.EDefaultLoopTypes.PLAY_ONCE).setWrappedController(attackController);
    private final SingletonAnimationBuilder roarAnim = new SingletonAnimationBuilder(this, "Roar Defense Mechanism", ILoopType.EDefaultLoopTypes.PLAY_ONCE).setWrappedController(attackController);
    private final SingletonAnimationBuilder ramAttackAnim = new SingletonAnimationBuilder(this, "Ram Attack", ILoopType.EDefaultLoopTypes.PLAY_ONCE).setWrappedController(attackController);
    private final SingletonAnimationBuilder grabAnim = new SingletonAnimationBuilder(this, "Grab", ILoopType.EDefaultLoopTypes.PLAY_ONCE).setWrappedController(attackController);
    private final SingletonAnimationBuilder munchAttackAnim = new SingletonAnimationBuilder(this, "Munch Attack", ILoopType.EDefaultLoopTypes.LOOP).setAnimSpeed(2.0D).setWrappedController(munchController);
    private final SingletonAnimationBuilder bodySlamAttackAnim = new SingletonAnimationBuilder(this, "Body Slam Attack", ILoopType.EDefaultLoopTypes.HOLD_ON_LAST_FRAME).setWrappedController(attackController);
    private final SingletonAnimationBuilder bodySlamAttackHitAnim = new SingletonAnimationBuilder(this, "Body Slam Attack (Hit)", ILoopType.EDefaultLoopTypes.HOLD_ON_LAST_FRAME).setWrappedController(attackController);
    public static final byte RAM_ATTACK_ID = 1;
    public static final byte MUNCH_ATTACK_ID = 2;
    public static final byte DEFENSE_MECH_ID = 3;
    public static final String HERCULES_BEETLE_MDF_NAME = "hercules_beetle";
    private final EnumUtil.HerculesBeetleType type;

    public HerculesBeetleEntity(EntityType<? extends MonsterEntity> type, World worldIn) {
        super(type, worldIn);
        this.type = EnumUtil.HerculesBeetleType.MODERN;
        this.maxUpStep = 1.5F;
        this.moveControl = new MovementController(this) {
            @Override
            public void tick() {
                if (!isPlayingAnimation(munchAttackAnim) && !isFlinging()) super.tick();
                else setSpeed(0);
            }
        };
        this.lookControl = new LookController(this) {
            @Override
            public void tick() {
                if (!isPlayingAnimation(munchAttackAnim) && !isFlinging()) super.tick();
            }
        };

        setPathfindingMalus(PathNodeType.LEAVES, 1.0F);
    }

    public HerculesBeetleEntity(EntityType<? extends MonsterEntity> type, World worldIn, EnumUtil.HerculesBeetleType beetleType) {
        super(type, worldIn);
        this.type = beetleType;
        this.maxUpStep = 1.5F;
        this.moveControl = new MovementController(this) {
            @Override
            public void tick() {
                if (!isPlayingAnimation(munchAttackAnim) && !isFlinging()) super.tick();
                else setSpeed(0);
            }
        };
        this.lookControl = new LookController(this) {
            @Override
            public void tick() {
                if (!isPlayingAnimation(munchAttackAnim) && !isFlinging()) super.tick();
            }
        };

        setPathfindingMalus(PathNodeType.LEAVES, 1.0F);
    }

    public static AttributeModifierMap.MutableAttribute setCustomAttributes() {
        return MobEntity.createLivingAttributes()
                .add(Attributes.MAX_HEALTH, 250)
                .add(Attributes.ARMOR, 20)
                .add(Attributes.MOVEMENT_SPEED, 0.35D)
                .add(Attributes.FLYING_SPEED, 0.42D)
                .add(Attributes.KNOCKBACK_RESISTANCE, 0.8D)
                .add(Attributes.ATTACK_SPEED, 10)
                .add(Attributes.ATTACK_DAMAGE, 25)
                .add(Attributes.ATTACK_KNOCKBACK, 2D)
                .add(Attributes.FOLLOW_RANGE, 40);
    }

    @Override
    public AnimationFactory getFactory() {
        return factory;
    }

    @Override
    public WrappedAnimationController<? extends IAnimatableEntity> getMainWrappedController() {
        return mainController;
    }

    @Override
    public ObjectArrayList<WrappedAnimationController<HerculesBeetleEntity>> getWrappedControllers() {
        return herculesBeetleControllers;
    }

    @Override
    protected void defineSynchedData() {
        super.defineSynchedData();
        this.entityData.define(DOCILE, true);
        this.entityData.define(AWAKENING, false);
        this.entityData.define(ACTIVE, false);
        this.entityData.define(FLINGING, false);
        this.entityData.define(DOCILITY_TIME, 0);
        this.entityData.define(ACTIVITY_TIME, 0);
    }

    public boolean isDocile() {
        return this.entityData.get(DOCILE);
    }

    public void setDocile(boolean isDocile) {
        if (!level.isClientSide) this.entityData.set(DOCILE, isDocile);
    }

    public boolean isAwakening() {
        return this.entityData.get(AWAKENING);
    }

    public void setAwakening(boolean isAwakening) {
        if (!level.isClientSide) this.entityData.set(AWAKENING, isAwakening);
    }

    public boolean isActive() {
        return this.entityData.get(ACTIVE);
    }

    public void setActive(boolean isActive) {
        if (!level.isClientSide) this.entityData.set(ACTIVE, isActive);
    }

    public boolean isFlinging() {
        return this.entityData.get(FLINGING);
    }

    public void setFlinging(boolean isFlinging) {
        if (!level.isClientSide) this.entityData.set(FLINGING, isFlinging);
    }

    public int getDocilityTime() {
        return this.entityData.get(DOCILITY_TIME);
    }

    public void setDocilityTime(int docilityTime) {
        if (!level.isClientSide) this.entityData.set(DOCILITY_TIME, docilityTime);
    }

    public void updateDocilityTime(int incrementalValue) {
        setDocilityTime(getDocilityTime() + incrementalValue);
    }

    public int getActivityTime() {
        return this.entityData.get(ACTIVITY_TIME);
    }

    public void setActivityTime(int activityTime) {
        if (!level.isClientSide) this.entityData.set(ACTIVITY_TIME, activityTime);
    }

    public void updateActivityTime(int incrementalValue) {
        setActivityTime(getActivityTime() + incrementalValue);
    }

    @Override
    public <E extends IAnimatableEntity> PlayState mainPredicate(AnimationEvent<E> event) {
        if (isAttacking() || isOnAttackCooldown()) playAnimation(idleAnim, true);
        return isDeadOrDying() ? PlayState.STOP : PlayState.CONTINUE;
    }

    public <E extends IAnimatableEntity> PlayState attackPredicate(AnimationEvent<E> event) {
        return PlayState.CONTINUE;
    }

    public <E extends IAnimatableEntity> PlayState munchPredicate(AnimationEvent<E> event) {
        return getAttackID() != MUNCH_ATTACK_ID || isDeadOrDying() ? PlayState.STOP : PlayState.CONTINUE;
    }

    public <E extends IAnimatableEntity> PlayState awakeningPredicate(AnimationEvent<E> event) {
        return (!isPlayingAnimation(awakenedAnim) && !isAwakening() && !isPlayingAnimation(docileAnim) && !isDocile()) || isDeadOrDying() ? PlayState.STOP : PlayState.CONTINUE;
    }

    @Override
    protected void registerGoals() {
        this.goalSelector.addGoal(0, new WaterAvoidingRandomWalkingGoal(this, 1.2D) {
            @Override
            public boolean canUse() {
                return super.canUse() && isActive() && !isAttacking() && !isOnAttackCooldown() && !isFlinging() && getTarget() == null;
            }

            @Override
            public boolean canContinueToUse() {
                return super.canContinueToUse() && isActive() && !isAttacking() && !isOnAttackCooldown() && !isFlinging() && getTarget() == null;
            }
        });
        this.goalSelector.addGoal(0, new AnimatableMoveToTargetGoal(this, 1.2D, 3) {
            @Override
            public boolean canUse() {
                return super.canUse() && isActive() && !isFlinging();
            }
        });
        this.targetSelector.addGoal(0, new AnimatableMeleeGoal(this, () -> ramAttackAnim, RAM_ATTACK_ID, 125.0D, 9.4D, 12.8D, 20) {
            @Override
            public boolean canUse() {
                return super.canUse() && isActive() && !isFlinging();
            }

            @Override
            public void start() {
                super.start();
                ramAttackAnim.setAnimSpeed(1.0D);
            }
        }.soundOnStart(CASoundEvents.HERCULES_BEETLE_BEETLE_RAM, 1.0F));
        this.targetSelector.addGoal(0, new HerculesBeetleDefenseMechanismGoal(this, () -> roarAnim, DEFENSE_MECH_ID)); // Misuse of the target selector but whatever, not like this version is LTS or anything
        this.targetSelector.addGoal(0, new HerculesBeetleMunchGoal(this, () -> grabAnim, () -> munchAttackAnim, MUNCH_ATTACK_ID));
        this.targetSelector.addGoal(1, new NearestAttackableTargetGoal<>(this, AbstractVillagerEntity.class, false));
        this.targetSelector.addGoal(1, new NearestAttackableTargetGoal<>(this, PlayerEntity.class, false));
        this.targetSelector.addGoal(1, new NearestAttackableTargetGoal<>(this, IronGolemEntity.class, false));
        this.targetSelector.addGoal(1, new NearestAttackableTargetGoal<>(this, RoboPounderEntity.class, false));
        this.targetSelector.addGoal(1, new NearestAttackableTargetGoal<>(this, RoboPounderEntity.class, false));
        this.targetSelector.addGoal(1, new NearestAttackableTargetGoal<>(this, RoboSniperEntity.class, false));
        this.targetSelector.addGoal(1, new NearestAttackableTargetGoal<>(this, RoboJefferyEntity.class, false));
        this.targetSelector.addGoal(1, new NearestAttackableTargetGoal<>(this, RoboWarriorEntity.class, false));
        this.targetSelector.addGoal(1, new HurtByTargetGoal(this));
    }

    @Override
    public float getMeleeAttackReach() {
        return super.getMeleeAttackReach() * 0.94F;
    }

    @Override
    public boolean canBeKnockedBack() {
        return false;
    }

    @Override
    public boolean canCollideWith(Entity pEntity) {
        return false;
    }

    @Override
    public boolean causeFallDamage(float pFallDistance, float pDamageMultiplier) {
        return false;
    }

    @Override
    public boolean isAffectedByFluids() {
        return false;
    }

    @Override
    protected void checkFallDamage(double pY, boolean pOnGround, BlockState pState, BlockPos pPos) {
    }

    @Override
    public int animationInterval() {
        return awakenedAnim != null && isPlayingAnimation(awakenedAnim) ? 1 : 2;
    }

    @Override
    protected void onSpawn(boolean hasAlreadyDied) {
        if (!hasAlreadyDied && level.isClientSide) {
            Minecraft.getInstance().getSoundManager().queueTickingSound(new HerculesBeetleTickableIdleSound(CASoundEvents.HERCULES_BEETLE_IDLE.get(), this).setDocilitySound(new HerculesBeetleTickableIdleSound(CASoundEvents.HERCULES_BEETLE_DOCILE.get(), this)));
            Minecraft.getInstance().getSoundManager().queueTickingSound(new HerculesBeetleTickableWalkSound(CASoundEvents.HERCULES_BEETLE_WALK.get(), this).setFlyingSound(new HerculesBeetleTickableWalkSound(CASoundEvents.HERCULES_BEETLE_FLY.get(), this)));
            Minecraft.getInstance().getSoundManager().queueTickingSound(new HerculesBeetleTickableMunchSound(CASoundEvents.HERCULES_BEETLE_MAD_MUNCH.get(), this));
        }
    }

    @Override
    public void positionRider(Entity rider) {
        extPositionRider(this, rider, Entity::setPos);
    }

    protected void extPositionRider(Entity vehicle, Entity rider, IMoveCallback callback) { // TODO Better extensibility
        Vector3d grabOffset = new Vector3d(0, 0.5, 2.0);

        Vector3d offset = grabOffset.yRot((float) Math.toRadians(vehicle.yRot));
        double dY = vehicle.getY() + offset.y();
        callback.accept(rider, vehicle.getX() - offset.x(), dY, vehicle.getZ() + offset.z());
    }

    @Override
    public void manageAttack(LivingEntity target) {
        switch (getAttackID()) {
            case RAM_ATTACK_ID:
                if (!target.isInvulnerable() && !target.noPhysics) {
                    Vector3i facing = getDirection().getNormal();
                    target.push(facing.getX() * 1.0D, 0.675D, facing.getZ() * 1.0D);

                    if (target instanceof PlayerEntity && !((PlayerEntity) target).isCreative()) target.hurtMarked = true;
                }
                setAttackDamage(25.0D);
                break;
            case MUNCH_ATTACK_ID:
                setAttackDamage(10.0D);
                break;
        }
    }

    @Override
    protected void tickDeath() {
        setDocile(false);
        setActive(false);
        setAwakening(false);
        setFlinging(false);
        setDocilityTime(0);
        setActivityTime(0);
        ejectPassengers();

        super.tickDeath();

        if (MathUtil.isBetween(deathAnim.getWrappedAnimProgress(), 12.8D, 14.8D)) CAScreenShakeEntity.shakeScreen(level, position(), 12.0F, 0.054F, 5, 30);
    }

    @Override
    public void aiStep() {
        handleStates();
        super.aiStep();
    }

    @Override
    public void tick() {
        super.tick();
    }

    @Override
    public boolean isColliding(BlockPos targetPos, BlockState targetState) {
        return !targetState.is(BlockTags.LEAVES) && super.isColliding(targetPos, targetState);
    }

    @Override
    public boolean isPersistenceRequired() {
        return !isDocile();
    }

    private void handleStates() { //TODO Move to brains/state handlers in 1.20.1+
        handleDocility();
        handleActivity();
    }

    private void handleDocility() {
        boolean hasValidTarget = getTarget() != null && getTarget().isAlive() && (getTarget() instanceof PlayerEntity && getTarget().isCrouching() ? getTarget().distanceTo(getTarget()) <= 8.0F : distanceTo(getTarget()) <= 20.0F);

        if (isDocile()) { //TODO Turn into goal
            boolean shouldAwaken = (hurtTime > 0 || hasValidTarget || !EntityUtil.checkIncomingProjectiles(this, 45.0D, 0.2D).isEmpty());

            if (shouldAwaken) {
                setDocile(false);
                setDocilityTime(0);

                if (!isDeadOrDying() && !isAwakening()) setAwakening(true);
            } else {
                updateDocilityTime(1);
                if (tickCount % 20 == 0) heal(5.0F);
            }
        } else if (!isDocile()) {
            if (isAwakening()) {
                if (!isPlayingAnimation(awakenedAnim)) {
                    playAnimation(awakenedAnim, false);
                    playSound(CASoundEvents.HERCULES_BEETLE_AWAKEN.get(), 1.0F, 1.0F);
                } else if (awakenedAnim.hasAnimationFinished()) {
                    setDocile(false); // JIC
                    setAwakening(false);
                    setActive(true);
                }
            }
        }
    }

    private void handleActivity() {
        if (isActive()) {
            updateActivityTime(1);

            boolean hasValidTarget = getTarget() != null && getTarget().isAlive() && distanceTo(getTarget()) <= getFollowRange();

            if (isOnGround() && !isInWater() && !isInLava() && !isMoving() && !hasValidTarget && !isOnAttackCooldown() && !isFlinging() && !isOnPortalCooldown() && !isAttacking() && !isOnFire() && hurtTime == 0 && ((getActivityTime() > 500 && getActivityTime() % MathHelper.nextInt(random, 10, getActivityTime() * 2) == 0) || (getActivityTime() > 1000 && getHealth() < 100.0F)) && !isDeadOrDying() && tickCount % 18 == 0) {
                setActive(false);
                setAwakening(false); // JIC
                setAttackID((byte) 0);
                setAttackCooldown(0);
                setAggressive(false);
                setDocile(true);
                setActivityTime(0);
            }
        }
    }

    @Override
    public ObjectArrayList<IAnimationBuilder> getCachedAnimations() {
        return herculesBeetleAnimations;
    }

    @Override
    protected SoundEvent getHurtSound(DamageSource source) {
        SoundEvent criticalDamageSound = CASoundEvents.HERCULES_BEETLE_DAMAGE_CRITICAL.get();
        SoundEvent evasiveDamageSound = CASoundEvents.HERCULES_BEETLE_DAMAGE_EVASIVE.get(); //TODO Ye, 1.20.1+ mechanics n stuff
        SoundEvent defenseDamageSound = CASoundEvents.HERCULES_BEETLE_DAMAGE_DEFENSE.get();
        SoundEvent offenseDamageSound = CASoundEvents.HERCULES_BEETLE_DAMAGE_OFFENSE.get();

        return getHealth() <= 80.0F ? criticalDamageSound : offenseDamageSound;
    }

    @Override
    protected SoundEvent getDeathSound() {
        return CASoundEvents.HERCULES_BEETLE_DEATH.get();
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

    @Nullable
    @Override
    public IAnimationBuilder getDeathAnim() {
        return deathAnim;
    }

    @Override
    public String getOwnerMDFileName() {
        return HERCULES_BEETLE_MDF_NAME;
    }

    @Override
    protected void handleBaseAnimations() {
        if (isDocile() && !isAwakening() && !isMoving() && !isDeadOrDying()) playAnimation(docileAnim, false);

        if (((isActive() || (awakenedAnim.hasAnimationFinished() && !isDocile())) && !isMoving() && !isAttacking()) && !isDeadOrDying()) playAnimation(idleAnim, true);
        else if (isActive() && isMoving() && !isAttacking() && !isDeadOrDying()) playAnimation(walkAnim, false);

        if (isPlayingAnimation(awakenedAnim) && !isDeadOrDying()) {
            if (MathUtil.isBetween(awakenedAnim.getWrappedAnimProgress(), 60, 90)) CAScreenShakeEntity.shakeScreen(level, position(), 440F, (float) (awakenedAnim.getWrappedAnimProgress() / 100F) / 6, 2, 210);
            if (MathUtil.isBetween(awakenedAnim.getWrappedAnimProgress(), 90, 140)) CAScreenShakeEntity.shakeScreen(level, position(), 340F, (float) (awakenedAnim.getWrappedAnimProgress() / 100F) / 18, 2, 210);
            if (MathUtil.isBetween(awakenedAnim.getWrappedAnimProgress(), 60, 140)) EntityUtil.repelEntities(this, 10, 8, MathHelper.clamp((awakenedAnim.getWrappedAnimProgress() / 100F) / 6, 0.2F, 0.55F));
        }

        if (roarAnim.getWrappedAnimProgress() >= 18.4D) {
            CAScreenShakeEntity.shakeScreen(level, position(), 440F,  Math.min(Math.max(((float) (roarAnim.getWrappedAnimLength() / 100F) - (float) (roarAnim.getWrappedAnimProgress() / 100F)) / 6.0F, 0.01F), 0.5F), 2, 210);
            EntityUtil.repelEntities(this, 12, 9, MathHelper.clamp((roarAnim.getWrappedAnimProgress() / 100F), 0.3F, 1.0F));
        }

        if (isPlayingAnimation(munchAttackAnim)) { // External goal handling because tick lag + no brains :p
            EntityUtil.freezeEntityRotation(this);

            getNavigation().stop();
            setDeltaMovement(0, getDeltaMovement().y(), 0);
            stopAnimation(walkAnim);
        }

        if (isFlinging() && !isPlayingAnimation(ramAttackAnim) && !getPassengers().isEmpty()) {
            ramAttackAnim.setAnimSpeed(4.0D);

            playAnimation(ramAttackAnim, false);
            playSound(CASoundEvents.HERCULES_BEETLE_BEETLE_RAM.get(), 1.0F, 1.3F);
        } else if (MathUtil.isBetween(ramAttackAnim.getWrappedAnimProgress(), 12.2D, 15.8D) && isFlinging() && !getPassengers().isEmpty()) {
            Vector3i facing = getDirection().getNormal();
            Entity passenger = getPassengers().get(0);

            if (!passenger.isAlive() || getPassengers().isEmpty()) {
                setFlinging(false);
                return;
            }

            passenger.push(facing.getX() * 1.0D, 1.2D, facing.getZ() * 1.0D);

            if (passenger instanceof PlayerEntity) passenger.hurtMarked = true;
            passenger.stopRiding();
        } else if (ramAttackAnim.hasAnimationFinished() && getPassengers().isEmpty()) {
            setFlinging(false);

            ramAttackAnim.setAnimSpeed(1.0D);
        }
    }
}
