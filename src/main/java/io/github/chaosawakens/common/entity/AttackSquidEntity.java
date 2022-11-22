package io.github.chaosawakens.common.entity;

import io.github.chaosawakens.common.entity.base.AnimatableMonsterEntity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.IRangedAttackMob;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.ai.goal.Goal;
import net.minecraft.entity.passive.SquidEntity;
import net.minecraft.network.datasync.DataParameter;
import net.minecraft.network.datasync.DataSerializers;
import net.minecraft.network.datasync.EntityDataManager;
import net.minecraft.util.DamageSource;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.World;
import software.bernie.geckolib3.core.IAnimatable;
import software.bernie.geckolib3.core.PlayState;
import software.bernie.geckolib3.core.builder.AnimationBuilder;
import software.bernie.geckolib3.core.controller.AnimationController;
import software.bernie.geckolib3.core.event.predicate.AnimationEvent;
import software.bernie.geckolib3.core.manager.AnimationData;
import software.bernie.geckolib3.core.manager.AnimationFactory;

public class AttackSquidEntity extends SquidEntity implements IAnimatable, IRangedAttackMob {
	private final AnimationFactory factory = new AnimationFactory(this);
	//Mini-implementation of AnimatableMonsterEntity.java
	private static final DataParameter<Boolean> ATTACKING = EntityDataManager.defineId(AnimatableMonsterEntity.class, DataSerializers.BOOLEAN);

	public AttackSquidEntity(EntityType<? extends SquidEntity> type, World world) {
		super(type, world);
	}
	
	public boolean getAttacking() {
		return this.entityData.get(ATTACKING);
	}

	public void setAttacking(boolean attacking) {
		this.entityData.set(ATTACKING, attacking);
	}
	
	private <E extends IAnimatable> PlayState predicate(AnimationEvent<E> event) {
		if (this.isSwimming()) {
			event.getController().setAnimation(new AnimationBuilder().addAnimation("animation.attack_squid.swimming", true));
			return PlayState.CONTINUE;
		}
		if (this.isDeadOrDying()) {
			event.getController().setAnimation(new AnimationBuilder().addAnimation("animation.attack_squid.death", false));
			return PlayState.CONTINUE;
		}
		if (!this.isInWater()) {
			event.getController().setAnimation(new AnimationBuilder().addAnimation("animation.attack_squid.swimming", true));
			return PlayState.CONTINUE;
		}
		event.getController().setAnimation(new AnimationBuilder().addAnimation("animation.attack_squid.swimming", true));
		return PlayState.CONTINUE;
	}
	
	@Override
	protected void registerGoals() {
		this.goalSelector.addGoal(0, new MoveRandomGoal(this));
	}

	@Override
	public void registerControllers(AnimationData data) {
		data.addAnimationController(new AnimationController<>(this, "attacksquidcontroller", 0, this::predicate));
	}

	@Override
	public AnimationFactory getFactory() {
		return factory;
	}
	
	@Override
	public boolean hurt(DamageSource p_70097_1_, float p_70097_2_) {
		return super.hurt(p_70097_1_, p_70097_2_);
	}
	
	@Override
	public void aiStep() {
		super.aiStep();
		LivingEntity target = this.getTarget();
		
		if (this.distanceToSqr(target) <= 5.0D) {
			
		}
	}
	class MoveRandomGoal extends Goal {
		private final AttackSquidEntity squid;
	
		public MoveRandomGoal(AttackSquidEntity entity) {	
			this.squid = entity;	
		}
		
		public boolean canUse() {		 
			return true;		  
		}
		
		public void tick() {	  
			int i = this.squid.getNoActionTime();	   
			if (i > 120) {		   
				this.squid.setMovementVector(0.0F, 0.0F, 0.0F);		   
			} else if (this.squid.getRandom().nextInt(50) == 0 || !this.squid.wasTouchingWater || !this.squid.hasMovementVector()) {		   
				float f = this.squid.getRandom().nextFloat() * ((float)Math.PI * 2F);		    
				float f1 = MathHelper.cos(f) * 0.2F;		    
				float f2 = -0.1F + this.squid.getRandom().nextFloat() * 0.2F;		    
				float f3 = MathHelper.sin(f) * 0.2F;		    
				this.squid.setMovementVector(f1, f2, f3);		    
			}		  
		}		
	}

	@Override
	public void performRangedAttack(LivingEntity entity, float p_82196_2_) {
		
	}
	
	

}
