package io.github.chaosawakens.common.entity;

import net.minecraft.entity.*;
import net.minecraft.entity.ai.attributes.AttributeModifierMap;
import net.minecraft.entity.ai.attributes.Attributes;
import net.minecraft.entity.ai.goal.*;
import net.minecraft.entity.passive.AnimalEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.projectile.AbstractArrowEntity;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.network.datasync.DataParameter;
import net.minecraft.network.datasync.DataSerializers;
import net.minecraft.network.datasync.EntityDataManager;
import net.minecraft.util.DamageSource;
import net.minecraft.util.RangedInteger;
import net.minecraft.util.SoundEvent;
import net.minecraft.util.TickRangeConverter;
import net.minecraft.util.math.vector.Vector3d;
import net.minecraft.world.World;
import net.minecraft.world.server.ServerWorld;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import software.bernie.geckolib3.core.IAnimatable;
import software.bernie.geckolib3.core.PlayState;
import software.bernie.geckolib3.core.builder.AnimationBuilder;
import software.bernie.geckolib3.core.controller.AnimationController;
import software.bernie.geckolib3.core.event.predicate.AnimationEvent;
import software.bernie.geckolib3.core.manager.AnimationData;
import software.bernie.geckolib3.core.manager.AnimationFactory;

import javax.annotation.Nullable;

import io.github.chaosawakens.common.registry.CASoundEvents;

import java.util.UUID;

public class EmeraldGatorEntity extends AnimalEntity implements IAngerable, IAnimatable {
	
    private static final DataParameter<Integer> ANGER_TIME = EntityDataManager.defineId(EmeraldGatorEntity.class, DataSerializers.INT);
    private static final RangedInteger ANGER_TIME_RANGE = TickRangeConverter.rangeOfSeconds(20, 39);
    private UUID persistentAngerTarget;

    private final AnimationFactory factory = new AnimationFactory(this);

    public EmeraldGatorEntity(EntityType<? extends AnimalEntity> type, World worldIn) {
        super(type, worldIn);
        this.noCulling = true;
    }

    private <E extends IAnimatable> PlayState predicate(AnimationEvent<E> event) {
        if(event.isMoving()){
            event.getController().setAnimation(new AnimationBuilder().addAnimation("animation.emerald_gator.walking_animation", true));
            return PlayState.CONTINUE;
        }
        if (!event.isMoving()) {
            event.getController().setAnimation(new AnimationBuilder().addAnimation("animation.emerald_gator.idle_animation", true));
            return PlayState.CONTINUE;
        }
        return PlayState.CONTINUE;
    }

    @Override
    protected void registerGoals() {
        this.goalSelector.addGoal(1, new SwimGoal(this));
        this.goalSelector.addGoal(2, new RandomWalkingGoal(this, 1.0));
        this.goalSelector.addGoal(4, new LeapAtTargetGoal(this, 0.4F));
        this.goalSelector.addGoal(5, new MeleeAttackGoal(this, 1.0, true));
        this.goalSelector.addGoal(8, new WaterAvoidingRandomWalkingGoal(this, 1.0));
        this.goalSelector.addGoal(10, new LookAtGoal(this, PlayerEntity.class, 8.0F));
        this.goalSelector.addGoal(10, new LookRandomlyGoal(this));
        this.targetSelector.addGoal(3, new HurtByTargetGoal(this));
        this.targetSelector.addGoal(4, new NearestAttackableTargetGoal<>(this, PlayerEntity.class, 10, true, false, this::isAngryAt));
        this.targetSelector.addGoal(8, new ResetAngerGoal<>(this, true));
    }

    public static AttributeModifierMap.MutableAttribute setCustomAttributes() {
        return MobEntity.createLivingAttributes()
                .add(Attributes.MAX_HEALTH, 18)
                .add(Attributes.ATTACK_DAMAGE, 3)
                .add(Attributes.ATTACK_KNOCKBACK, 1)
                .add(Attributes.ATTACK_SPEED, 1)
                .add(Attributes.MOVEMENT_SPEED, 0.35D)
                .add(Attributes.FOLLOW_RANGE, 8);
    }

    protected void defineSynchedData() {
        super.defineSynchedData();
        this.entityData.define(ANGER_TIME, 0);
    }

    public void addAdditionalSaveData(CompoundNBT compound) {
        super.addAdditionalSaveData(compound);
        this.addPersistentAngerSaveData(compound);
    }

    public void readAdditionalSaveData(CompoundNBT compound) {
        super.readAdditionalSaveData(compound);

        if(!level.isClientSide)
            this.readPersistentAngerSaveData((ServerWorld)this.level, compound);
    }

    public boolean hurt(DamageSource source, float amount) {
        if (this.isInvulnerableTo(source)) {
            return false;
        } else {
            Entity entity = source.getEntity();
            if (entity != null && !(entity instanceof PlayerEntity) && !(entity instanceof AbstractArrowEntity)) {
                amount = (amount + 1.0F) / 2.0F;
            }

            return super.hurt(source, amount);
        }
    }

    public boolean doHurtTarget(Entity entityIn) {
        boolean flag = entityIn.hurt(DamageSource.mobAttack(this), (float)((int)this.getAttributeValue(Attributes.ATTACK_DAMAGE)));
        if (flag) {
            this.doEnchantDamageEffects(this, entityIn);
        }

        return flag;
    }

    public int getRemainingPersistentAngerTime() {
        return this.entityData.get(ANGER_TIME);
    }

    public void setRemainingPersistentAngerTime(int time) {
        this.entityData.set(ANGER_TIME, time);
    }

    public void startPersistentAngerTimer() {
        this.setRemainingPersistentAngerTime(ANGER_TIME_RANGE.randomValue(this.random));
    }

    @Nullable
    public UUID getPersistentAngerTarget() {
        return this.persistentAngerTarget;
    }

    public void setPersistentAngerTarget(@Nullable UUID target) {
        this.persistentAngerTarget = target;
    }

    public boolean canBeLeashed(PlayerEntity player) {
        return !this.isAngry() && super.canBeLeashed(player);
    }

    @OnlyIn(Dist.CLIENT)
    public Vector3d getLeashOffset() {
        return new Vector3d(0.0D, 0.6F * this.getEyeHeight(), this.getBbWidth() * 0.4F);
    }

    public void tick() {
        EmeraldGatorEntity.this.setTarget(null);
        super.tick();
    }

    @Override
    public void registerControllers(AnimationData data) {
        data.addAnimationController(new AnimationController<>(this, "emeraldgatorcontroller", 0, this::predicate));
    }

    @Override
    public AnimationFactory getFactory() {
        return this.factory;
    }

    @Nullable
    @Override
    public AgeableEntity getBreedOffspring(ServerWorld world, AgeableEntity mate) {
        return null;
    }
    
    @Override
	protected SoundEvent getHurtSound(DamageSource damageSourceIn) {
		return CASoundEvents.EMERALD_GATOR_HURT.get();
	}
	
	@Override
	protected SoundEvent getDeathSound() {
		return CASoundEvents.EMERALD_GATOR_DEATH.get();
	}
}
