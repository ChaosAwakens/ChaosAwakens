package io.github.chaosawakens.common.entity.hostile.robo;

import io.github.chaosawakens.api.animation.IAnimatableEntity;
import io.github.chaosawakens.api.animation.SingletonAnimationBuilder;
import io.github.chaosawakens.common.entity.base.AnimatableMonsterEntity;
import it.unimi.dsi.fastutil.objects.ObjectArrayList;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.MobEntity;
import net.minecraft.entity.ai.attributes.AttributeModifierMap;
import net.minecraft.entity.ai.attributes.Attributes;
import net.minecraft.entity.monster.MonsterEntity;
import net.minecraft.world.World;
import software.bernie.geckolib3.core.PlayState;
import software.bernie.geckolib3.core.builder.ILoopType.EDefaultLoopTypes;
import software.bernie.geckolib3.core.controller.AnimationController;
import software.bernie.geckolib3.core.event.predicate.AnimationEvent;
import software.bernie.geckolib3.core.manager.AnimationFactory;

public class RoboWarriorEntity extends AnimatableMonsterEntity {
	private final AnimationFactory factory = new AnimationFactory(this);
	private final AnimationController<RoboWarriorEntity> mainController = createMainMappedController("robowarriormaincontroller");
	private final SingletonAnimationBuilder idleAnim = new SingletonAnimationBuilder(this, "Idle", EDefaultLoopTypes.LOOP);
	private final SingletonAnimationBuilder walkAnim = new SingletonAnimationBuilder(this, "Walk", EDefaultLoopTypes.LOOP);

	public RoboWarriorEntity(EntityType<? extends MonsterEntity> type, World worldIn) {
		super(type, worldIn);
	}
	
	public static AttributeModifierMap.MutableAttribute setCustomAttributes() {
		return MobEntity.createLivingAttributes()
				.add(Attributes.MAX_HEALTH, 180)
				.add(Attributes.ARMOR, 10)
				.add(Attributes.MOVEMENT_SPEED, 0.24D)
				.add(Attributes.KNOCKBACK_RESISTANCE, 1.4D)
				.add(Attributes.ATTACK_DAMAGE, 10)
				.add(Attributes.ATTACK_KNOCKBACK, 4.5D)
				.add(Attributes.FOLLOW_RANGE, 70);
	}

	@Override
	public AnimationFactory getFactory() {
		return factory;
	}

	@Override
	public AnimationController<? extends AnimatableMonsterEntity> getMainController() {
		return mainController;
	}

	@Override
	public <E extends IAnimatableEntity> PlayState mainPredicate(AnimationEvent<E> event) {
		return PlayState.CONTINUE;
	}
	
	@Override
	protected void registerGoals() {
	}

	@Override
	public int animationInterval() {
		return 2;
	}

	@Override
	public void manageAttack(LivingEntity target) {		
	}

	@Override
	public SingletonAnimationBuilder getIdleAnim() {
		return idleAnim;
	}

	@Override
	public SingletonAnimationBuilder getWalkAnim() {
		return walkAnim;
	}

	@Override
	public SingletonAnimationBuilder getDeathAnim() {
		return null;
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public ObjectArrayList<AnimationController<RoboWarriorEntity>> getControllers() {
		return new ObjectArrayList<AnimationController<RoboWarriorEntity>>(1);
	}
}
