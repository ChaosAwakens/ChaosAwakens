package io.github.chaosawakens.common.entity.hostile.robo;

import io.github.chaosawakens.api.animation.IAnimatableEntity;
import io.github.chaosawakens.api.animation.IAnimationBuilder;
import io.github.chaosawakens.api.animation.SingletonAnimationBuilder;
import io.github.chaosawakens.api.animation.WrappedAnimationController;
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
import software.bernie.geckolib3.core.event.predicate.AnimationEvent;
import software.bernie.geckolib3.core.manager.AnimationFactory;

public class RoboWarriorEntity extends AnimatableMonsterEntity {
	private final AnimationFactory factory = new AnimationFactory(this);
	private final ObjectArrayList<WrappedAnimationController<RoboWarriorEntity>> roboWarriorControllers = new ObjectArrayList<WrappedAnimationController<RoboWarriorEntity>>(1);
	private final ObjectArrayList<IAnimationBuilder> roboWarriorAnimations = new ObjectArrayList<IAnimationBuilder>(1);
	private final WrappedAnimationController<RoboWarriorEntity> mainController = createMainMappedController("robowarriormaincontroller");
	private final SingletonAnimationBuilder idleAnim = new SingletonAnimationBuilder(this, "Idle", EDefaultLoopTypes.LOOP);
	private final SingletonAnimationBuilder walkAnim = new SingletonAnimationBuilder(this, "Walk", EDefaultLoopTypes.LOOP);
	private final SingletonAnimationBuilder deathAnim = new SingletonAnimationBuilder(this, "Death", EDefaultLoopTypes.LOOP);

	public RoboWarriorEntity(EntityType<? extends MonsterEntity> type, World worldIn) {
		super(type, worldIn);
	}
	
	public static AttributeModifierMap.MutableAttribute setCustomAttributes() {
		return MobEntity.createLivingAttributes()
				.add(Attributes.MAX_HEALTH, 180) // 45 shield health, 10 shield armor, 3 shield armor toughness
				.add(Attributes.ARMOR, 10)
				.add(Attributes.ARMOR_TOUGHNESS, 3)
				.add(Attributes.MOVEMENT_SPEED, 0.24D)
				.add(Attributes.KNOCKBACK_RESISTANCE, 1.4D)
				.add(Attributes.ATTACK_DAMAGE, 10)
				.add(Attributes.ATTACK_KNOCKBACK, 2.5D)
				.add(Attributes.FOLLOW_RANGE, 60);
	}

	@Override
	public AnimationFactory getFactory() {
		return factory;
	}
	
	@Override
	public WrappedAnimationController<RoboWarriorEntity> getMainWrappedController() {
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
	public boolean ignoreExplosion() {
		return true;
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
		return deathAnim;
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public ObjectArrayList<WrappedAnimationController<RoboWarriorEntity>> getWrappedControllers() {
		return roboWarriorControllers;
	}
	
	@Override
	public ObjectArrayList<IAnimationBuilder> getCachedAnimations() {
		return roboWarriorAnimations;
	}
}
