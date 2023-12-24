package io.github.chaosawakens.common.entity.ai.goals.passive.land;

import io.github.chaosawakens.api.animation.IAnimationBuilder;
import io.github.chaosawakens.common.entity.base.AnimatableAnimalEntity;
import net.minecraft.entity.EntityPredicate;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.ai.RandomPositionGenerator;
import net.minecraft.entity.ai.goal.Goal;
import net.minecraft.pathfinding.Path;
import net.minecraft.util.EntityPredicates;
import net.minecraft.util.math.vector.Vector3d;

import javax.annotation.Nullable;
import java.util.EnumSet;
import java.util.function.Consumer;
import java.util.function.Predicate;
import java.util.function.Supplier;

public class AnimatableAvoidEntityGoal<E extends LivingEntity> extends Goal {
	protected final AnimatableAnimalEntity owner;
	protected E entityToAvoid;
	protected final Supplier<IAnimationBuilder> panicAnim;
	protected final Class<E> entityToAvoidClass;
	protected final double maxAvoidDist;
	protected final double panicDistThreshold;
	protected final double walkSpeedModifier;
	protected final double sprintSpeedModifier;
	@Nullable
	protected Consumer<AnimatableAnimalEntity> actionOnActivation;
	@Nullable
	protected Consumer<AnimatableAnimalEntity> actionOnDeactivation;
	protected final Predicate<LivingEntity> avoidPredicate;
	protected final Predicate<LivingEntity> predicateOnAvoidEntity;
	private final EntityPredicate entityToAvoidEvaluationPredicate;
	private Path avoidancePath;
	
	public AnimatableAvoidEntityGoal(AnimatableAnimalEntity owner, Supplier<IAnimationBuilder> panicAnim, Class<E> entityToAvoidClass, double maxAvoidDist, double panicDistThreshold, double walkSpeedModifier, double sprintSpeedModifier, Predicate<LivingEntity> avoidPredicate, Predicate<LivingEntity> predicateOnAvoidEntity) {
		this.owner = owner;
		this.panicAnim = panicAnim;
		this.entityToAvoidClass = entityToAvoidClass;
		this.maxAvoidDist = maxAvoidDist;
		this.panicDistThreshold = panicDistThreshold;
		this.walkSpeedModifier = walkSpeedModifier;
		this.sprintSpeedModifier = sprintSpeedModifier;
		this.avoidPredicate = avoidPredicate;
		this.predicateOnAvoidEntity = predicateOnAvoidEntity;
		this.entityToAvoidEvaluationPredicate = (new EntityPredicate()).range(maxAvoidDist).selector(avoidPredicate.and(predicateOnAvoidEntity));;
		
		setFlags(EnumSet.of(Flag.MOVE));
	}
	
	public AnimatableAvoidEntityGoal(AnimatableAnimalEntity owner, Supplier<IAnimationBuilder> panicAnim, Class<E> entityToAvoidClass, double maxAvoidDist, double panicDistThreshold, double walkSpeedModifier, double sprintSpeedModifier) {
		this(owner, panicAnim, entityToAvoidClass, maxAvoidDist, panicDistThreshold, walkSpeedModifier, sprintSpeedModifier, (avoidingEntity) -> true, EntityPredicates.NO_CREATIVE_OR_SPECTATOR::test);
	}
	
	public AnimatableAvoidEntityGoal(AnimatableAnimalEntity owner, Class<E> entityToAvoidClass, double maxAvoidDist, double panicDistThreshold, double walkSpeedModifier, double sprintSpeedModifier) {
		this(owner, null, entityToAvoidClass, maxAvoidDist, panicDistThreshold, walkSpeedModifier, sprintSpeedModifier, (avoidingEntity) -> true, EntityPredicates.NO_CREATIVE_OR_SPECTATOR::test);
	}
	
	public AnimatableAvoidEntityGoal<E> setActionOnActivation(Consumer<AnimatableAnimalEntity> actionOnActivation) {
		this.actionOnActivation = actionOnActivation;
		return this;
	}
	
	public AnimatableAvoidEntityGoal<E> setActionOnDeactivation(Consumer<AnimatableAnimalEntity> actionOnDeactivation) {
		this.actionOnDeactivation = actionOnDeactivation;
		return this;
	}

	@Override
	public boolean canUse() {
		this.entityToAvoid = owner.level.getNearestLoadedEntity(entityToAvoidClass, entityToAvoidEvaluationPredicate, owner, owner.getX(), owner.getY(), owner.getZ(), owner.getBoundingBox().inflate(maxAvoidDist, 3.0D, maxAvoidDist));
		
		if (entityToAvoid == null || (entityToAvoid != null && !entityToAvoid.isAlive())) return false;
		else {
			Vector3d avoidancePosition = RandomPositionGenerator.getPosAvoid(owner, 16, 7, entityToAvoid.position());
			
			if (avoidancePosition == null || (avoidancePosition != null && entityToAvoid.distanceToSqr(avoidancePosition.x, avoidancePosition.y, avoidancePosition.z) < entityToAvoid.distanceToSqr(owner))) return false;
			else this.avoidancePath = owner.getNavigation().createPath(avoidancePosition.x, avoidancePosition.y, avoidancePosition.z, 0);
		}
		
		return avoidancePath != null;
	}
	
	@Override
	public boolean canContinueToUse() {
		return !owner.isAlive() && owner.getNavigation().isDone();
	}
	
	@Override
	public void start() {
		if (actionOnActivation != null) actionOnActivation.accept(owner);
		if (avoidancePath != null) owner.getNavigation().moveTo(avoidancePath, walkSpeedModifier);
	}
	
	@Override
	public void stop() {
		if (actionOnDeactivation != null) actionOnDeactivation.accept(owner);
		
		this.entityToAvoid = null;
	}
	
	@Override
	public void tick() {
		if (entityToAvoid != null && entityToAvoid.isAlive() && owner.distanceTo(entityToAvoid) <= panicDistThreshold) {
			owner.getNavigation().setSpeedModifier(sprintSpeedModifier);
			
			if (panicAnim != null) owner.playAnimation(panicAnim.get(), false);
		} else {
			owner.getNavigation().setSpeedModifier(walkSpeedModifier);
			
			if (panicAnim != null && panicAnim.get().isPlaying()) owner.stopAnimation(panicAnim.get());
		}
	}
}
