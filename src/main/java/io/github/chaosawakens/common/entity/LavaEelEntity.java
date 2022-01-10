package io.github.chaosawakens.common.entity;

import javax.annotation.Nullable;



import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.MobEntity;
import net.minecraft.entity.SpawnReason;
import net.minecraft.entity.ai.attributes.AttributeModifierMap;
import net.minecraft.entity.ai.attributes.Attributes;
import net.minecraft.entity.ai.goal.AvoidEntityGoal;
import net.minecraft.entity.ai.goal.LookAtGoal;
import net.minecraft.entity.ai.goal.MeleeAttackGoal;
import net.minecraft.entity.ai.goal.NearestAttackableTargetGoal;
import net.minecraft.entity.passive.fish.SalmonEntity;
import net.minecraft.entity.passive.fish.TropicalFishEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.util.DamageSource;
import net.minecraft.util.EntityPredicates;
import net.minecraft.util.SoundEvent;
import net.minecraft.util.SoundEvents;
import net.minecraft.world.IWorld;
import net.minecraft.world.World;
import software.bernie.geckolib3.core.IAnimatable;
import software.bernie.geckolib3.core.PlayState;
import software.bernie.geckolib3.core.builder.AnimationBuilder;
import software.bernie.geckolib3.core.controller.AnimationController;
import software.bernie.geckolib3.core.event.predicate.AnimationEvent;
import software.bernie.geckolib3.core.manager.AnimationData;
import software.bernie.geckolib3.core.manager.AnimationFactory;

public class LavaEelEntity extends AbstractLavaGroupFishEntity implements IAnimatable{
	private final AnimationFactory factory = new AnimationFactory(this);
	
	public LavaEelEntity(EntityType<? extends AbstractLavaGroupFishEntity> p_i49856_1_, World p_i49856_2_) {
		super(p_i49856_1_, p_i49856_2_);
		this.noCulling = true;
	}
	
    public static AttributeModifierMap.MutableAttribute setCustomAttributes() {
        return MobEntity.createLivingAttributes()
                .add(Attributes.MAX_HEALTH, 6.0D)
                .add(Attributes.MOVEMENT_SPEED,1.0D)
                .add(Attributes.ATTACK_SPEED, 0.30D)
                .add(Attributes.ATTACK_DAMAGE, 2.0D)
                .add(Attributes.ATTACK_KNOCKBACK, 1)
                .add(Attributes.KNOCKBACK_RESISTANCE, 0.35D)
                .add(Attributes.FOLLOW_RANGE, 10.0D);
    }

	@Override
	public void registerControllers(AnimationData data) {
		data.addAnimationController(new AnimationController<>(this, "lavaeelcontroller", 0, this::predicate));
	}
	
	private <E extends IAnimatable> PlayState predicate(AnimationEvent<E> event) {
        if (event.isMoving()) {
            event.getController().setAnimation(new AnimationBuilder().addAnimation("animation.lava_eel.swim", true));
            return PlayState.CONTINUE;
        }
        if (!event.isMoving()) {
            event.getController().setAnimation(new AnimationBuilder().addAnimation("animation.lava_eel.swim", true));
            return PlayState.CONTINUE;
        }
        if(this.dead) {
        	event.getController().setAnimation(new AnimationBuilder().addAnimation("animation.lava_eel.flop", true));
        }
        if(this.isSwimming()) {
            event.getController().setAnimation(new AnimationBuilder().addAnimation("animation.lava_eel.swim", true));
        }
        if(this.isDeadOrDying()) {
        	 event.getController().setAnimation(new AnimationBuilder().addAnimation("animation.lava_eel.flop", true));
        	 return PlayState.CONTINUE;
        }
        if(this.isInLava()) {
            event.getController().setAnimation(new AnimationBuilder().addAnimation("animation.lava_eel.swim", true));
            return PlayState.CONTINUE;
        }
        return PlayState.CONTINUE;
    }
	
	@Override
	protected void registerGoals() {
		super.registerGoals();
		this.goalSelector.addGoal(0, new LookAtGoal(this, PlayerEntity.class, 3.0F, 3.0F));	   
		this.goalSelector.addGoal(2, new AvoidEntityGoal<>(this, RockFishEntity.class, 2.0F, 0.5D, 0.3D, EntityPredicates.NO_SPECTATORS::test));
		this.targetSelector.addGoal(3, new LavaEelEntity.AttackGoal(this, 2.0F, false));
        this.targetSelector.addGoal(3, new NearestAttackableTargetGoal<>(this, SalmonEntity.class, true));
        this.targetSelector.addGoal(3, new NearestAttackableTargetGoal<>(this, TropicalFishEntity.class, true));
        this.targetSelector.addGoal(3, new NearestAttackableTargetGoal<>(this, PlayerEntity.class, true));
        this.targetSelector.addGoal(3, new NearestAttackableTargetGoal<>(this, RockFishEntity.class, true));
        this.targetSelector.addGoal(3, new NearestAttackableTargetGoal<>(this, WhaleEntity.class, true));
		//this.goalSelector.addGoal(4, new WaterAvoidingRandomWalkingGoal(this, 8.0D));
	}
	
	@Override
	public boolean checkSpawnRules(IWorld p_213380_1_, SpawnReason p_213380_2_) {
		return super.checkSpawnRules(p_213380_1_, p_213380_2_) && super.checkLavaMobSpawnRules(p_213380_1_, p_213380_2_, blockPosition(), random);
	}
	
	  @Override
	  public int maxSchoolSize() {
		  return 4;
     }
	  
	    public boolean okTarget(@Nullable LivingEntity livingEntity) {
	        if (livingEntity != null) {
	            return !this.level.isDay() || livingEntity.isInLava();
	        } else {
	            return false;
	        }
	    }
	    
	    @Override
	    public boolean hurt(DamageSource dmgsrc, float power) {
	    	if (this.isInWaterOrRain()) {
	    		this.hurt(dmgsrc, power);
	    	}
	    	return super.hurt(dmgsrc, power);
	    }
	  
	    static class AttackGoal extends MeleeAttackGoal {
	        private final LavaEelEntity lavaeel;

	        public AttackGoal(LavaEelEntity mob, double speedModifier, boolean followingTargetEvenIfNotSeen) {
	            super(mob, speedModifier, followingTargetEvenIfNotSeen);
	            this.lavaeel = mob;
	        }

	        public boolean canUse() {
	            return super.canUse() && this.lavaeel.okTarget(this.lavaeel.getTarget());
	        }

	        public boolean canContinueToUse() {
	            return super.canContinueToUse() && this.lavaeel.okTarget(this.lavaeel.getTarget());
	        }
	       
	    }
	    
	    @Override
	    public boolean doHurtTarget(Entity attacker) {
	    	LivingEntity target = this.getTarget();
	    	LavaEelEntity attacc = (LavaEelEntity)attacker;
	    	if(target.getLastHurtByMob() == attacc) {
	    		if(target instanceof LivingEntity) {
	    			float h = attacc.getHealth();
	    			target.setSecondsOnFire((int) (h * 4));
	    		}
	    	}
	    	return super.doHurtTarget(attacker);
	    }
	    
		  
		  @Override
		   public int getMaxSpawnClusterSize() {
		      return 8;
		   }


	@Override
	public AnimationFactory getFactory() {
		return this.factory;
	}

	@Override
	protected ItemStack getBucketItemStack() {
		return null;
	}

	@Override
	protected SoundEvent getFlopSound() {
		return SoundEvents.COD_FLOP;
	}
	
	@Override
	protected SoundEvent getSwimSound() {
		return SoundEvents.FISH_SWIM;
	}
	
	@Override
	protected SoundEvent getHurtSound(DamageSource source) {
		return SoundEvents.TROPICAL_FISH_HURT;
	}
	
	@Override
	protected SoundEvent getDeathSound() {
		return SoundEvents.TROPICAL_FISH_DEATH;
	}
	
	@Override
	public int getMaxAirSupply() {
		return 2000;
	}
	
	@Override
	protected SoundEvent getAmbientSound() {
		return SoundEvents.TROPICAL_FISH_AMBIENT;
	}

}
