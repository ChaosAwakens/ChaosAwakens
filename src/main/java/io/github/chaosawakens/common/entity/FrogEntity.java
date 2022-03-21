package io.github.chaosawakens.common.entity;

import net.minecraft.entity.*;
import net.minecraft.entity.ai.attributes.AttributeModifierMap;
import net.minecraft.entity.ai.attributes.Attributes;
import net.minecraft.entity.ai.goal.*;
import net.minecraft.entity.passive.AnimalEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.fluid.Fluid;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.network.datasync.DataParameter;
import net.minecraft.network.datasync.DataSerializers;
import net.minecraft.network.datasync.EntityDataManager;
import net.minecraft.potion.Effects;
import net.minecraft.tags.FluidTags;
import net.minecraft.util.DamageSource;
import net.minecraft.util.SoundEvents;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.vector.Vector3d;
import net.minecraft.world.DifficultyInstance;
import net.minecraft.world.IServerWorld;
import net.minecraft.world.IWorld;
import net.minecraft.world.World;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.server.ServerWorld;
import software.bernie.geckolib3.core.IAnimatable;
import software.bernie.geckolib3.core.PlayState;
import software.bernie.geckolib3.core.builder.AnimationBuilder;
import software.bernie.geckolib3.core.controller.AnimationController;
import software.bernie.geckolib3.core.event.predicate.AnimationEvent;
import software.bernie.geckolib3.core.manager.AnimationData;
import software.bernie.geckolib3.core.manager.AnimationFactory;

import javax.annotation.Nullable;

public class FrogEntity extends AnimalEntity implements IAnimatable {
    private final AnimationFactory factory = new AnimationFactory(this);
    public static final DataParameter<Integer> DATA_TYPE_ID = EntityDataManager.defineId(FrogEntity.class, DataSerializers.INT);

    public FrogEntity(EntityType<? extends AnimalEntity> type, World worldIn) {
        super(type, worldIn);
        this.noCulling = true;
    }

    public void addAdditionalSaveData(CompoundNBT nbt) {
        super.addAdditionalSaveData(nbt);
        nbt.putInt("FrogType", this.getFrogType());
    }

    public void readAdditionalSaveData(CompoundNBT nbt) {
        super.readAdditionalSaveData(nbt);
        this.setFrogType(nbt.getInt("FrogType"));
    }

    @Override
    protected void registerGoals() {
        this.goalSelector.addGoal(0, new SwimGoal(this));
        this.goalSelector.addGoal(3, new PanicGoal(this, 1.2D));
        this.goalSelector.addGoal(6, new WaterAvoidingRandomWalkingGoal(this, 0.7D));
        this.goalSelector.addGoal(7, new LookAtGoal(this, PlayerEntity.class, 6.0F));
        this.goalSelector.addGoal(8, new LookRandomlyGoal(this));
    }

    public static AttributeModifierMap.MutableAttribute setCustomAttributes() {
        return MobEntity.createLivingAttributes()
                .add(Attributes.MAX_HEALTH, 6)
                .add(Attributes.MOVEMENT_SPEED, 0.15D)
                .add(Attributes.JUMP_STRENGTH, 2.0D)
                .add(Attributes.FOLLOW_RANGE, 8.0D);
    }

    protected void defineSynchedData() {
        super.defineSynchedData();
        this.entityData.define(DATA_TYPE_ID, 0);
    }

    public int getFrogType() {
        return MathHelper.clamp(this.entityData.get(DATA_TYPE_ID), 0, 99);
    }

    public void setFrogType(int type) {
        if (type == 99) {
            this.getAttribute(Attributes.ARMOR).setBaseValue(8.0D);
            this.goalSelector.addGoal(4, new FrogEntity.EvilAttackGoal(this));
            this.targetSelector.addGoal(1, (new HurtByTargetGoal(this)).setAlertOthers());
            this.targetSelector.addGoal(2, new NearestAttackableTargetGoal<>(this, PlayerEntity.class, true));
        }
        this.entityData.set(DATA_TYPE_ID, type);
    }

    @Nullable
    public ILivingEntityData finalizeSpawn(IServerWorld world, DifficultyInstance difficultyInstance, SpawnReason spawnReason, @Nullable ILivingEntityData entityData, @Nullable CompoundNBT nbt) {
        int i = this.getRandomFrogType(world);
        if (entityData instanceof FrogEntity.FrogData) {
            i = ((FrogData)entityData).frogType;
        } else {
            entityData = new FrogEntity.FrogData(i);
        }
        this.setFrogType(i);
        return super.finalizeSpawn(world, difficultyInstance, spawnReason, entityData, nbt);
    }

    private int getRandomFrogType(IWorld world) {
        Biome biome = world.getBiome(this.blockPosition());
        int i = this.random.nextInt(8);
        if (biome.getBiomeCategory() == Biome.Category.NETHER) {
            i = 99;
        }
        return i;
    }

    public boolean doHurtTarget(Entity entity) {
        if (this.getFrogType() == 99) {
            this.playSound(SoundEvents.RABBIT_ATTACK, 1.0F, (this.random.nextFloat() - this.random.nextFloat()) * 0.2F + 1.0F);
            return entity.hurt(DamageSource.mobAttack(this), 8.0F);
        } else {
            return entity.hurt(DamageSource.mobAttack(this), 3.0F);
        }
    }

    public boolean canStandOnFluid(Fluid fluid) {
        if (this.getFrogType() == 99) {
            return fluid.is(FluidTags.LAVA);
        }
        return false;
    }

    public boolean fireImmune() {
        return this.getFrogType() == 99;
    }

    @Nullable
    @Override
    public FrogEntity getBreedOffspring(ServerWorld world, AgeableEntity ageable) {
        return null;
    }

    private <E extends IAnimatable> PlayState predicate(AnimationEvent<E> event) {
        if (event.isMoving()) {
            event.getController().setAnimation(new AnimationBuilder().addAnimation("animation.frog.jump_animation", true));
            return PlayState.CONTINUE;
        } else if (!event.isMoving()) {
            return PlayState.CONTINUE;
        }
        return PlayState.CONTINUE;
    }

    public boolean causeFallDamage(float p_225503_1_, float p_225503_2_) {
        return false;
    }

    @Override
    public void registerControllers(AnimationData data) {
        data.addAnimationController(new AnimationController<>(this, "frogcontroller", 0, this::predicate));
    }

    @Override
    protected void jumpFromGround() {
        float f = this.getJumpPower() + 1.0F;
        if (this.hasEffect(Effects.JUMP)) {
           f += 0.1F * (float)(this.getEffect(Effects.JUMP).getAmplifier() + 1);
        }

        Vector3d vector3d = this.getDeltaMovement();
        this.setDeltaMovement(vector3d.x, (double)f, vector3d.z);
        if (this.isSprinting()) {
           float f1 = this.yRot * ((float)Math.PI / 180F);
           this.setDeltaMovement(this.getDeltaMovement().add((double)(-MathHelper.sin(f1) * 0.2F), 0.0D, (double)(MathHelper.cos(f1) * 0.2F)));
        }

        this.hasImpulse = true;
        net.minecraftforge.common.ForgeHooks.onLivingJump(this);
    }
    
    @Override
    public AnimationFactory getFactory() {
        return this.factory;
    }

    static class FrogData extends AgeableEntity.AgeableData {
        public final int frogType;
        private FrogData(int frogType) {
            super(true);
            this.frogType = frogType;
        }
    }

    static class EvilAttackGoal extends MeleeAttackGoal {
        public EvilAttackGoal(FrogEntity entity) {
            super(entity, 1.4D, true);
        }
        protected double getAttackReachSqr(LivingEntity livingEntity) {
            return (4.0F + livingEntity.getBbWidth());
        }
    }
}
