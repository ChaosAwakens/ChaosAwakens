package io.github.chaosawakens.common.entity.prototype.neutral;

import io.github.chaosawakens.api.animation.faal.base.ExtendedAnimationState;
import io.github.chaosawakens.common.entity.prototype.ai.goal.hostile.AnimatableAttackGoal;
import io.github.chaosawakens.common.entity.prototype.ai.goal.hostile.BandaidMoveToTargetGoal;
import io.github.chaosawakens.common.entity.prototype.base.AnimatableAngerableMob;
import io.github.chaosawakens.common.entity.prototype.base.AnimatableMonster;
import it.unimi.dsi.fastutil.objects.ObjectArrayList;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.util.TimeUtil;
import net.minecraft.util.valueproviders.UniformInt;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.AttributeInstance;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.goal.FollowMobGoal;
import net.minecraft.world.entity.ai.goal.RandomStrollGoal;
import net.minecraft.world.entity.ai.goal.RandomSwimmingGoal;
import net.minecraft.world.entity.monster.Monster;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.pathfinder.BlockPathTypes;
import net.minecraft.world.level.storage.loot.LootPool;
import net.minecraft.world.level.storage.loot.LootTable;
import net.minecraft.world.level.storage.loot.entries.LootItem;
import net.minecraft.world.level.storage.loot.functions.SetItemCountFunction;
import net.minecraft.world.level.storage.loot.predicates.LootItemKilledByPlayerCondition;
import net.minecraft.world.level.storage.loot.providers.number.ConstantValue;
import net.minecraft.world.level.storage.loot.providers.number.UniformGenerator;
import org.jetbrains.annotations.Nullable;

import java.util.UUID;
import java.util.function.Supplier;

public class RubberDucky extends AnimatableAngerableMob {
    private static final UUID TRANSFORMED_SPEED_MODIFIER_UUID = UUID.fromString("e4f9ac05-5857-4247-8a47-74b25bbd689d");
    private static final UUID TRANSFORMED_HEALTH_MODIFIER_UUID = UUID.fromString("908cb711-623a-46b9-aa3b-b2a180203983");
    private static final EntityDataAccessor<Byte> STATE_ID = SynchedEntityData.defineId(RubberDucky.class, EntityDataSerializers.BYTE);
    private static final UniformInt PERSISTENT_ANGER_TIME = TimeUtil.rangeOfSeconds(20, 400);
    public static final byte TENDRIL_STAB_ATTACK_ID = 1;
    public static final byte IDLE_STATE_ID = 0;
    public static final byte TRANSFORMING_STATE_ID = 2;
    public static final byte TRANSFORMED_STATE_ID = 3;
    public static final byte DEAD_STATE_ID = 4;
    public static final String IDLE_ANIM = "Idle";
    public static final String WATER_IDLE_ANIM = "Water Idle";
    public static final String BOUNCE_ANIM = "Bounce";
    public static final String TRANSFORM_ANIM = "Transform";
    public static final String TRANSFORMED_IDLE_ANIM = "Transformed Idle";
    public static final String TRANSFORMED_WATER_IDLE_ANIM = "Transformed Water Idle";
    public static final String TENDRIL_STAB_ATTACK_ANIM = "Tendril Stab Attack";
    public static final String HIDE_TENDRIL_ANIM = "Hide Tendril";
    public final ExtendedAnimationState idleAnimState = wrapState(IDLE_ANIM);
    public final ExtendedAnimationState waterIdleAnimState = wrapState(WATER_IDLE_ANIM);
    public final ExtendedAnimationState bounceAnimState = wrapState(BOUNCE_ANIM);
    public final ExtendedAnimationState transformAnimState = wrapState(TRANSFORM_ANIM, 52);
    public final ExtendedAnimationState transformedIdleAnimState = wrapState(TRANSFORMED_IDLE_ANIM);
    public final ExtendedAnimationState transformedWaterIdleAnimState = wrapState(TRANSFORMED_WATER_IDLE_ANIM);
    public final ExtendedAnimationState tendrilStabAttackAnimState = wrapState(TENDRIL_STAB_ATTACK_ANIM);
    public final ExtendedAnimationState hideTendrilAnimState = wrapState(HIDE_TENDRIL_ANIM);

    public RubberDucky(EntityType<? extends AnimatableMonster> pEntityType, Level pLevel) {
        super(pEntityType, pLevel);

        setPathfindingMalus(BlockPathTypes.WATER, 0.0F);
    }

    public static AttributeSupplier.Builder createAttributes() {
        return Monster.createMonsterAttributes()
                .add(Attributes.MAX_HEALTH, 10.0D)
                .add(Attributes.MOVEMENT_SPEED, 0.25D)
                .add(Attributes.ATTACK_DAMAGE, 12.0D)
                .add(Attributes.FOLLOW_RANGE, 128.0D);
    }

    public static LootTable.Builder createLootTable(Supplier<EntityType<RubberDucky>> ownerType) {
        return LootTable.lootTable()
                .withPool(LootPool.lootPool()
                        .setRolls(ConstantValue.exactly(1))
                        .add(LootItem.lootTableItem(Items.COOKIE)
                                .apply(SetItemCountFunction.setCount(UniformGenerator.between(1.0F, 2.0F)))
                                .when(LootItemKilledByPlayerCondition.killedByPlayer())));
    }

    @Override
    protected void registerGoals() {
        super.registerGoals();

        // Movement
        goalSelector.addGoal(0, new BandaidMoveToTargetGoal(this) {
            @Override
            public boolean canUse() {
                return super.canUse() && getStateId() == TRANSFORMED_STATE_ID;
            }

            @Override
            public boolean canContinueToUse() {
                return super.canContinueToUse() && getStateId() == TRANSFORMED_STATE_ID;
            }
        }.satisfactoryDist(2.5D));

        // Attack
        goalSelector.addGoal(0, new AnimatableAttackGoal<>(this, ObjectArrayList.of(() -> tendrilStabAttackAnimState), 22, true, TENDRIL_STAB_ATTACK_ID)
                .attackArc(125.0D)
                .additionalStartConditions(owner -> owner.getStateId() == TRANSFORMED_STATE_ID)
                .potentialTargetRadius(7.0D)
                .attackTickCooldown(20.0D)
                .attackFrame(3.4D, 11.0D)
                .initiationRange(4.0D)
                .actionOnStart((animatable, target, potentialTargets, curTick) -> animatable.stopAnimation(HIDE_TENDRIL_ANIM))
                .actionOnEnd((animatable, target, potentialTargets, curTick) -> animatable.playAnimation(HIDE_TENDRIL_ANIM, true)));

        // Misc.
        goalSelector.addGoal(2, new FollowMobGoal(this, 1.2D, 2.0F, 10.0F) {

            @Override
            public boolean canUse() {
                return super.canUse() && !isAngry() && getStateId() != TRANSFORMING_STATE_ID;
            }

            @Override
            public boolean canContinueToUse() {
                return super.canContinueToUse() && !isAngry() && getStateId() != TRANSFORMING_STATE_ID;
            }
        });
        goalSelector.addGoal(3, new RandomStrollGoal(this, 1.1D, 50) {

            @Override
            public boolean canUse() {
                return super.canUse() && !isAngry() && getStateId() != TRANSFORMING_STATE_ID;
            }

            @Override
            public boolean canContinueToUse() {
                return super.canContinueToUse() && !isAngry() && getStateId() != TRANSFORMING_STATE_ID;
            }
        });
        goalSelector.addGoal(3, new RandomSwimmingGoal(this, 1.1D, 40) {

            @Override
            public boolean canUse() {
                return super.canUse() && !isAngry() && getStateId() != TRANSFORMING_STATE_ID;
            }

            @Override
            public boolean canContinueToUse() {
                return super.canContinueToUse() && !isAngry() && getStateId() != TRANSFORMING_STATE_ID;
            }
        });
    }

    @Override
    protected void defineSynchedData() {
        super.defineSynchedData();

        this.entityData.define(STATE_ID, IDLE_STATE_ID);
    }

    public byte getStateId() {
        return this.entityData.get(STATE_ID);
    }

    public void setStateId(byte stateId) {
        this.entityData.set(STATE_ID, stateId);
    }

    @Override
    public float maxUpStep() {
        return getStateId() == TRANSFORMED_STATE_ID ? 1.0F : super.maxUpStep();
    }

    @Override
    public boolean isFunctionallyAnimatingAttack() {
        return tendrilStabAttackAnimState.isStarted() && tendrilStabAttackAnimState.getAccumulatedTime() <= 1100;
    }

    @Override
    public double getMovementThreshold() {
        return super.getMovementThreshold() * 0.01E-27D;
    }

    @Override
    public void tickClientAnimations() {
        if (getStateId() != TRANSFORMING_STATE_ID) {
            if (isInWater()) {
                playAnimation(getStateId() == TRANSFORMED_STATE_ID ? TRANSFORMED_WATER_IDLE_ANIM : WATER_IDLE_ANIM);
                stopAnimation(getStateId() == TRANSFORMED_STATE_ID ? IDLE_ANIM : TRANSFORMED_IDLE_ANIM);
            } else {
                if (walkAnimation.isMoving()) {
                    if (getStateId() == IDLE_STATE_ID) playAnimation(BOUNCE_ANIM);
                    else stopAnimation(BOUNCE_ANIM);
                } else stopAnimation(BOUNCE_ANIM);

                playAnimation(getStateId() == TRANSFORMED_STATE_ID ? TRANSFORMED_IDLE_ANIM : IDLE_ANIM);
                stopAnimation(getStateId() == TRANSFORMED_STATE_ID ? TRANSFORMED_WATER_IDLE_ANIM : WATER_IDLE_ANIM);
            }
        } else {
            stopAnimation(getStateId() == TRANSFORMED_STATE_ID ? IDLE_ANIM : TRANSFORMED_IDLE_ANIM);
            stopAnimation(getStateId() == TRANSFORMED_STATE_ID ? TRANSFORMED_IDLE_ANIM : IDLE_ANIM);
            stopAnimation(BOUNCE_ANIM);
        }
    }

    @Override
    public void tickServerAnimations() {
        if (!isFunctionallyAnimatingAttack()) playAnimation(HIDE_TENDRIL_ANIM);
        else stopAnimation(HIDE_TENDRIL_ANIM);

        updateState();
    }

    @Override
    public void tickGeneralClient() {

    }

    @Override
    public boolean useCustomDeathTime() {
        return false;
    }

    @Override
    public void setTarget(@Nullable LivingEntity target) {
        super.setTarget(target);

        AttributeInstance movementSpeed = getAttribute(Attributes.MOVEMENT_SPEED);
        AttributeInstance maxHealth = getAttribute(Attributes.MAX_HEALTH);

        if (getTarget() != null) {
            if (movementSpeed != null && movementSpeed.getModifier(TRANSFORMED_SPEED_MODIFIER_UUID) == null) movementSpeed.addTransientModifier(new AttributeModifier(TRANSFORMED_SPEED_MODIFIER_UUID, "Transformed Speed Modifier", 0.35D, AttributeModifier.Operation.ADDITION));
            if (maxHealth != null && maxHealth.getModifier(TRANSFORMED_HEALTH_MODIFIER_UUID) == null) {
                maxHealth.addTransientModifier(new AttributeModifier(TRANSFORMED_HEALTH_MODIFIER_UUID, "Transformed Health Modifier", 55.0D, AttributeModifier.Operation.ADDITION));

            }
        } else {
            if (movementSpeed != null) movementSpeed.removeModifier(TRANSFORMED_SPEED_MODIFIER_UUID);
            if (maxHealth != null) maxHealth.removeModifier(TRANSFORMED_HEALTH_MODIFIER_UUID);
        }
    }

    @Override
    public boolean canBeKnockedBack() {
        return getStateId() != TRANSFORMING_STATE_ID;
    }

    protected void updateState() { // Frick goals cuh!11!! (Gonna be using the Advanced Task System instead of ts soon anyway sooooo... top thinko bandaid solutions :trol:)
        if (isDeadOrDying()) {
            if (getStateId() != DEAD_STATE_ID) setStateId(DEAD_STATE_ID);
            return;
        }

        if (getStateId() == IDLE_STATE_ID) {
            if (isAngry() && !isDeadOrDying()) {
                setStateId(TRANSFORMING_STATE_ID);

                stopAnimation(IDLE_ANIM);
                stopAnimation(WATER_IDLE_ANIM);

                playAnimation(TRANSFORM_ANIM);
            }
        }

        if (getStateId() == TRANSFORMING_STATE_ID) {
            if (!transformAnimState.isStarted()) playAnimation(TRANSFORM_ANIM);
            if (transformAnimState.getAccumulatedTicks() >= 51.0D) {
                setStateId(TRANSFORMED_STATE_ID);

                playAnimation(TRANSFORMED_IDLE_ANIM);
                stopAnimation(TRANSFORM_ANIM);
            }

            setDeltaMovement(0.0D, getDeltaMovement().y(), 0.0D);
        }

        if (getStateId() == TRANSFORMED_STATE_ID) {
            if (isDeadOrDying()) setStateId(DEAD_STATE_ID);

            if (tickCount - getLastHurtByMobTimestamp() >= 100 && tickCount % 5 == 0) heal(1.0F);
        }
    }

    @Override
    public boolean requiresCustomPersistence() {
        return super.requiresCustomPersistence() || getStateId() != IDLE_STATE_ID;
    }

    @Override
    public void startPersistentAngerTimer() {
        setRemainingPersistentAngerTime(PERSISTENT_ANGER_TIME.sample(getRandom()));
    }
}
