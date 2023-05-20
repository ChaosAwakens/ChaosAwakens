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
import software.bernie.geckolib3.core.controller.AnimationController;
import software.bernie.geckolib3.core.event.predicate.AnimationEvent;
import software.bernie.geckolib3.core.manager.AnimationFactory;

public class RoboSniperEntity extends AnimatableMonsterEntity {
	private final AnimationFactory factory = new AnimationFactory(this);
	private final AnimationController<RoboSniperEntity> mainController = createMainMappedController("robosnipermaincontroller");

	public RoboSniperEntity(EntityType<? extends MonsterEntity> type, World worldIn) {
		super(type, worldIn);
	}
	
	public static AttributeModifierMap.MutableAttribute setCustomAttributes() {
		return MobEntity.createLivingAttributes()
				.add(Attributes.MAX_HEALTH, 25)
				.add(Attributes.MOVEMENT_SPEED, 0.28D)
				.add(Attributes.KNOCKBACK_RESISTANCE, 0.2D)
				.add(Attributes.ATTACK_SPEED, 10)
				.add(Attributes.ATTACK_DAMAGE, 25)
				.add(Attributes.ATTACK_KNOCKBACK, 3.5D)
				.add(Attributes.FOLLOW_RANGE, 500);
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
	public int animationInterval() {
		return 2;
	}

	@Override
	public void manageAttack(LivingEntity target) {		
	}

	@Override
	public SingletonAnimationBuilder getIdleAnim() {
		return null;
	}

	@Override
	public SingletonAnimationBuilder getWalkAnim() {
		return null;
	}

	@Override
	public SingletonAnimationBuilder getDeathAnim() {
		return null;
	}

	@SuppressWarnings("unchecked")
	@Override
	public ObjectArrayList<AnimationController<RoboSniperEntity>> getControllers() {
		return new ObjectArrayList<AnimationController<RoboSniperEntity>>(1);
	}
}
