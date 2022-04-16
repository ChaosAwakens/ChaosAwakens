package io.github.chaosawakens.common.entity;

import io.github.chaosawakens.common.entity.ai.AnimatableMeleeGoal;
import io.github.chaosawakens.common.entity.ai.AnimatableMoveToTargetGoal;
import io.github.chaosawakens.common.entity.robo.RoboEntity;
import io.github.chaosawakens.common.registry.CASoundEvents;
import io.github.chaosawakens.common.registry.CAStructures;
import net.minecraft.block.BlockState;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.ILivingEntityData;
import net.minecraft.entity.MobEntity;
import net.minecraft.entity.SpawnReason;
import net.minecraft.entity.ai.attributes.AttributeModifierMap;
import net.minecraft.entity.ai.attributes.Attributes;
import net.minecraft.entity.ai.goal.*;
import net.minecraft.entity.passive.IronGolemEntity;
import net.minecraft.entity.passive.SnowGolemEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.util.DamageSource;
import net.minecraft.util.SoundEvent;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.RayTraceContext;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.util.math.vector.Vector3d;
import net.minecraft.world.DifficultyInstance;
import net.minecraft.world.IServerWorld;
import net.minecraft.world.World;
import net.minecraft.world.gen.feature.structure.StructureManager;
import net.minecraft.world.server.ServerWorld;
import software.bernie.geckolib3.core.IAnimatable;
import software.bernie.geckolib3.core.PlayState;
import software.bernie.geckolib3.core.builder.AnimationBuilder;
import software.bernie.geckolib3.core.controller.AnimationController;
import software.bernie.geckolib3.core.event.predicate.AnimationEvent;
import software.bernie.geckolib3.core.manager.AnimationData;
import software.bernie.geckolib3.core.manager.AnimationFactory;

import javax.annotation.Nullable;

public class EntEntity extends AnimatableMonsterEntity implements IAnimatable {
    private final AnimationFactory factory = new AnimationFactory(this);
    private final Types entType;

    public EntEntity(EntityType<? extends AnimatableMonsterEntity> type, World worldIn, Types entType) {
        super(type, worldIn);
        this.noCulling = true;
        this.entType = entType;
    }

    public static AttributeModifierMap.MutableAttribute setCustomAttributes() {
        return MobEntity.createLivingAttributes()
                .add(Attributes.MAX_HEALTH, 150)
                .add(Attributes.ARMOR, 3)
                .add(Attributes.MOVEMENT_SPEED, 0.15D)
                .add(Attributes.KNOCKBACK_RESISTANCE, 0.5D)
                .add(Attributes.ATTACK_SPEED, 10)
                .add(Attributes.ATTACK_DAMAGE, 25)
                .add(Attributes.ATTACK_KNOCKBACK, 3.5D)
                .add(Attributes.FOLLOW_RANGE, 24);
    }

    private <E extends IAnimatable> PlayState predicate(AnimationEvent<E> event) {
        if (this.dead) {
            event.getController().setAnimation(new AnimationBuilder().addAnimation("animation.ent.death_animation", true));
            return PlayState.CONTINUE;
        }
        if (this.getAttacking()) {
            event.getController().setAnimation(new AnimationBuilder().addAnimation("animation.ent.attacking_animation", true));
            animationSpeed = 0.6F;
            return PlayState.CONTINUE;
        }
        if (event.isMoving()) {
            event.getController().setAnimation(new AnimationBuilder().addAnimation("animation.ent.walking_animation", true));
            return PlayState.CONTINUE;
        }
        event.getController().setAnimation(new AnimationBuilder().addAnimation("animation.ent.idle_animation", true));
        return PlayState.CONTINUE;
    }

    @Override
    protected void registerGoals() {
        this.goalSelector.addGoal(4, new AnimatableMoveToTargetGoal(this, 1.6, 5));
        this.goalSelector.addGoal(4, new AnimatableMeleeGoal(this, 48.3, 0.5, 0.6));
        this.goalSelector.addGoal(5, new LookRandomlyGoal(this));
        this.goalSelector.addGoal(7, new SwimGoal(this));
        this.targetSelector.addGoal(3, new HurtByTargetGoal(this));
        this.targetSelector.addGoal(3, new NearestAttackableTargetGoal<>(this, PlayerEntity.class, true));
        this.targetSelector.addGoal(3, new NearestAttackableTargetGoal<>(this, IronGolemEntity.class, true));
        this.targetSelector.addGoal(3, new NearestAttackableTargetGoal<>(this, SnowGolemEntity.class, true));
        this.targetSelector.addGoal(3, new NearestAttackableTargetGoal<>(this, RoboEntity.class, true));
    }

    @Override
    public ILivingEntityData finalizeSpawn(IServerWorld worldIn, DifficultyInstance difficultyIn, SpawnReason reason, @Nullable ILivingEntityData spawnDataIn, @Nullable CompoundNBT dataTag)
    {
        // Only allow the ENT to wander when spawned outside the tree
        if (worldIn instanceof ServerWorld)
        {
            BlockPos pos = this.getOnPos();
            StructureManager strucManager = worldIn.getLevel().structureFeatureManager();

            boolean inDungeon = strucManager.getStructureAt(pos, false, CAStructures.ACACIA_ENT_TREE.get()).isValid() ||
                    strucManager.getStructureAt(pos, false, CAStructures.BIRCH_ENT_TREE.get()).isValid() ||
                    strucManager.getStructureAt(pos, false, CAStructures.CRIMSON_ENT_TREE.get()).isValid() ||
                    strucManager.getStructureAt(pos, false, CAStructures.DARK_OAK_ENT_TREE.get()).isValid() ||
                    strucManager.getStructureAt(pos, false, CAStructures.JUNGLE_ENT_TREE.get()).isValid() ||
                    strucManager.getStructureAt(pos, false, CAStructures.OAK_ENT_TREE.get()).isValid() ||
                    strucManager.getStructureAt(pos, false, CAStructures.SPRUCE_ENT_TREE.get()).isValid() ||
                    strucManager.getStructureAt(pos, false, CAStructures.WARPED_ENT_TREE.get()).isValid();
            if (!inDungeon || reason != SpawnReason.STRUCTURE)
            {
                this.goalSelector.addGoal(7, new RandomWalkingGoal(this, 1.6));
            }
        }

        return super.finalizeSpawn(worldIn, difficultyIn, reason, spawnDataIn, dataTag);
    }
    
    @Override
    public boolean doHurtTarget(Entity target) {
    	if (!this.isAggressive()) return false;
		if (!this.canSee(target)) return false;
		
		Vector3d attackerVector = new Vector3d(this.getX(), this.getEyeY(), this.getZ()); 
		Vector3d targetVector = new Vector3d(target.getX(), target.getEyeY(), target.getZ()); 
		
		if (target.level != this.level || targetVector.distanceToSqr(attackerVector) > 128.0D * 128.0D) return false; 
		
		return this.level.clip(new RayTraceContext(attackerVector, targetVector, RayTraceContext.BlockMode.COLLIDER, RayTraceContext.FluidMode.NONE, this)).getType() == RayTraceResult.Type.MISS && super.doHurtTarget(target);
    }
    
    @Override
    public void tick() {
    	super.tick();
    	
		if (this.getTarget() != null && this.canSee(this.getTarget()) && this.distanceToSqr(this.getTarget()) >= 12.0F) {
			this.lookControl.setLookAt(this.getTarget(), 30.0F, 30.0F);
			this.getTarget().moveTo(this.getTarget().blockPosition(), 3.0F, 10.0F);
			if (this.navigation.isStuck()) {
				this.navigation.recomputePath(this.getTarget().blockPosition());
			}
		}
		
		if (this.getTarget() != null && this.canSee(this.getTarget()) && this.distanceToSqr(this.getTarget()) <= 12.0F) {
			this.lookControl.setLookAt(this.getTarget(), 30.0F, 30.0F);
			if (this.navigation.isStuck()) {
				this.navigation.recomputePath(this.getTarget().blockPosition());
			}
		}
    }

    @Override
    public void registerControllers(AnimationData data) {
        data.addAnimationController(new AnimationController<>(this, "entcontroller", 0, this::predicate));
    }

    @Override
    public boolean isPersistenceRequired() {
        return true;
    }

    @Override
    public AnimationFactory getFactory() {
        return this.factory;
    }

    public enum Types {
        OAK("oak"), ACACIA("acacia"), DARK_OAK("dark_oak"), JUNGLE("jungle"), SPRUCE("spruce"), BIRCH("birch"), CRIMSON("crimson"), WARPED("warped");

        private final String name;

        Types(String name) {
            this.name = name;
        }

        public String getNameString() {
            return this.name;
        }
    }
    
    public EntEntity.Types getEntType() {
    	return entType;
    }
    
    @Override
    protected SoundEvent getHurtSound(DamageSource p_184601_1_) {
    	if(this.getEntType() == Types.ACACIA) {
    		return CASoundEvents.ACACIA_ENT_HURT.get();
    	} else if (this.getEntType() != Types.BIRCH) {
    		return CASoundEvents.BIRCH_ENT_HURT.get();
    	} else if (this.getEntType() == Types.CRIMSON) {
    		return CASoundEvents.CRIMSON_ENT_HURT.get();
    	} else if (this.getEntType() == Types.DARK_OAK) {
    		return CASoundEvents.DARK_OAK_ENT_HURT.get();
    	} else if (this.getEntType() == Types.JUNGLE) {
    		return CASoundEvents.JUNGLE_ENT_HURT.get();
    	} else if (this.getEntType() == Types.OAK) {
    		return CASoundEvents.OAK_ENT_HURT.get();
    	} else if (this.getEntType() == Types.SPRUCE) {
    		return CASoundEvents.SPRUCE_ENT_HURT.get();
    	} else if (this.getEntType() == Types.WARPED) {
    		return CASoundEvents.WARPED_ENT_HURT.get();
    	}
    	return CASoundEvents.ENT_HURT.get();
    }
    
    @Override
    protected SoundEvent getDeathSound() {
    	if(this.getEntType() == Types.ACACIA) {
    		return CASoundEvents.ACACIA_ENT_DEATH.get();
    	} else if (this.getEntType() != Types.BIRCH) {
    		return CASoundEvents.BIRCH_ENT_DEATH.get();
    	} else if (this.getEntType() == Types.CRIMSON) {
    		return CASoundEvents.CRIMSON_ENT_DEATH.get();
    	} else if (this.getEntType() == Types.DARK_OAK) {
    		return CASoundEvents.DARK_OAK_ENT_DEATH.get();
    	} else if (this.getEntType() == Types.JUNGLE) {
    		return CASoundEvents.JUNGLE_ENT_DEATH.get();
    	} else if (this.getEntType() == Types.OAK) {
    		return CASoundEvents.OAK_ENT_DEATH.get();
    	} else if (this.getEntType() == Types.SPRUCE) {
    		return CASoundEvents.SPRUCE_ENT_DEATH.get();
    	} else if (this.getEntType() == Types.WARPED) {
    		return CASoundEvents.WARPED_ENT_DEATH.get();
    	}
    	return CASoundEvents.ENT_HURT.get();
    }
    
    @Override
    protected void playStepSound(BlockPos p_180429_1_, BlockState p_180429_2_) {
    	if (!p_180429_2_.getMaterial().isLiquid()) { 
    		this.playSound(CASoundEvents.ENT_WALK.get(), this.getVoicePitch() * 0.30F, this.getSoundVolume() * 1);
    	}
    }
    
    @Override
    protected float getVoicePitch() {
    	return 0.4F;
    }
    
    @Override
    protected float getSoundVolume() {
    	return 4.0F;
    }
}
