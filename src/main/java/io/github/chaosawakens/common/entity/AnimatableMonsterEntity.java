/**
 * 
 */
package io.github.chaosawakens.common.entity;

import net.minecraft.entity.EntityType;
import net.minecraft.entity.monster.MonsterEntity;
import net.minecraft.network.datasync.DataParameter;
import net.minecraft.network.datasync.DataSerializers;
import net.minecraft.network.datasync.EntityDataManager;
import net.minecraft.world.World;
import software.bernie.geckolib3.core.IAnimatable;
import software.bernie.geckolib3.core.builder.Animation;
import software.bernie.geckolib3.core.event.predicate.AnimationEvent;
import software.bernie.geckolib3.core.manager.AnimationData;
import software.bernie.geckolib3.core.manager.AnimationFactory;

/**
 * 
 * @author invalid2
 */
public abstract class AnimatableMonsterEntity extends MonsterEntity implements IAnimatable {
	
	protected static final DataParameter<Boolean> MOVING = EntityDataManager.createKey(AnimatableMonsterEntity.class, DataSerializers.BOOLEAN);
	protected static final DataParameter<Boolean> ATTACKING = EntityDataManager.createKey(AnimatableMonsterEntity.class, DataSerializers.BOOLEAN);
	protected static final DataParameter<Boolean> HITTING = EntityDataManager.createKey(AnimatableMonsterEntity.class, DataSerializers.BOOLEAN);
	
	private Animation animationLast;
	
	private double currentAnimationTick;
	private double animationTickDelta;
	private double animationTickLast;
	
	protected boolean isAnimationFinished = false;
	/**
	 * @param type
	 * @param worldIn
	 */
	public AnimatableMonsterEntity(EntityType<? extends MonsterEntity> type, World worldIn) {
		super(type, worldIn);
	}
	
	/**
	 * Currently unused, might be useful on the future, thus I kept it
	 * @param <E>
	 * @param event
	 */
	protected <E extends IAnimatable> void doTickBase(AnimationEvent<E> event) {
		this.animationTickDelta = event.getAnimationTick() - this.animationTickLast;
		this.currentAnimationTick += this.animationTickDelta;
		
		if(!this.getAnimationFinished())this.setAnimationFinished(true);
		
		Animation currentAnimation = event.getController().getCurrentAnimation();
		if(currentAnimation != null) {
			if(this.currentAnimationTick >= currentAnimation.animationLength || (this.animationLast != null && !this.animationLast.equals(currentAnimation))) {
				this.currentAnimationTick = 0;
				this.setAnimationFinished(true);
			}
		}
		this.animationLast = currentAnimation;
		this.animationTickLast = event.getAnimationTick();
	}
	@Override
	abstract public void registerControllers(AnimationData data);
	
	@Override
	abstract public AnimationFactory getFactory();
	
	@Override
	protected void registerData() {
		super.registerData();
		this.dataManager.register(MOVING, false);
		this.dataManager.register(ATTACKING, false);
		this.dataManager.register(HITTING, false);
	}
	
	public boolean getMoving() { return this.dataManager.get(MOVING); }
	public void setMoving(boolean moving) { this.dataManager.set(MOVING, moving); }
	
	public boolean getAttacking() { return this.dataManager.get(ATTACKING); }
	public void setAttacking(boolean attacking) { this.dataManager.set(ATTACKING, attacking); }
	
	public boolean getHitting() { return this.dataManager.get(HITTING); }
	public void setHitting(boolean hitting) { this.dataManager.set(HITTING, hitting); }
	
	public boolean getAnimationFinished() { return this.isAnimationFinished; }
	public void setAnimationFinished(boolean animationFinished) { this.isAnimationFinished = animationFinished; }
	
	public double getCurrentAnimationTick() {return this.currentAnimationTick; }
}