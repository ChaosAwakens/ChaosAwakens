package io.github.chaosawakens.api.animation;

import com.google.common.collect.ImmutableList;
import io.github.chaosawakens.common.util.ObjectUtil;
import it.unimi.dsi.fastutil.objects.Object2ObjectArrayMap;
import it.unimi.dsi.fastutil.objects.ObjectArrayList;
import net.minecraft.entity.LivingEntity;
import software.bernie.geckolib3.core.builder.Animation;
import software.bernie.geckolib3.core.builder.AnimationBuilder;
import software.bernie.geckolib3.core.builder.ILoopType;
import software.bernie.geckolib3.core.builder.ILoopType.EDefaultLoopTypes;
import software.bernie.geckolib3.core.builder.RawAnimation;
import software.bernie.geckolib3.core.controller.AnimationController;

import javax.annotation.Nullable;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;
import java.util.function.Predicate;
import java.util.stream.Collectors;

//TODO Forever incomplete.
/**
 * Wrapper class for {@link SingletonAnimationBuilder} which allows for the instantiation of multiple chained animations. It also provides additional 
 * functionality, such as randomization (shuffling) of chained animation selection during a chaining sequence. It's important to note that this does not mean
 * that any rough transitions between chained animations will be handled by this builder. Animation transition behaviour will remain as-is, 
 * and Geckolib will still handle the animation transitioning itself using the transition length of the owner controller.
 * <br> </br>
 * This {@link IAnimationBuilder} instance class handles animation chaining, but it can also be interrupted at any time.
 * It allows for the randomization of the animation chaining sequence, but also allows for effectively freezing animations based on their index in 
 * the chaining sequence using their names or other identifiers. The default {@linkplain EDefaultLoopTypes loop type} used here is {@link EDefaultLoopTypes#PLAY_ONCE}, 
 * but it's also possible to specify the amount of loop repetitions per each animation in the chaining sequence.
 * <br> </br>
 * The chain sequence shuffling also allows for shuffling looping animations, despite how they work (which is by appending multiple animations consecutively). 
 * If a looping animation is added to the {@link #frozenAnimations} list, then all of its consecutive instances will be moved alongside it as well, whilst 
 * maintaining consecutive order.
 * <br> </br>
 * Due to the nature of this {@link IAnimationBuilder} instance, it's not possible to use {@link EDefaultLoopTypes#LOOP} or {@link EDefaultLoopTypes#HOLD_ON_LAST_FRAME}. 
 * However, loop repetitions allow you to specify the number of times a specific animation (or multiple animations) will loop, and {@link EDefaultLoopTypes#HOLD_ON_LAST_FRAME} 
 * can be substituted the same way it normally would be (either using animation length or using a separate animation consisting of the last frame on loop, 
 * using loop reps in this case).
 * <br> </br>
 * See {@link SingletonAnimationBuilder} for details on which data is present on which side.
 */
public class ChainedAnimationBuilder implements IAnimationBuilder {
	private final IAnimatableEntity owner;
	@Nullable
	private WrappedAnimationController<? extends IAnimatableEntity> targetController;
	private final AnimationBuilder animBuilder;
	private double animSpeedMultiplier = 1.0D;
	private final ObjectArrayList<SingletonAnimationBuilder> animations = new ObjectArrayList<SingletonAnimationBuilder>();
	private final ObjectArrayList<SingletonAnimationBuilder> frozenAnimations = new ObjectArrayList<SingletonAnimationBuilder>();
	private final Object2ObjectArrayMap<SingletonAnimationBuilder, Predicate<? extends IAnimatableEntity>> animationsToSkip = new Object2ObjectArrayMap<SingletonAnimationBuilder, Predicate<? extends IAnimatableEntity>>();
	private final ObjectArrayList<ObjectArrayList<SingletonAnimationBuilder>> loopingAnimations = new ObjectArrayList<ObjectArrayList<SingletonAnimationBuilder>>();
	private Queue<SingletonAnimationBuilder> queuedAnimations = new LinkedList<SingletonAnimationBuilder>();
	@Nullable
	private SingletonAnimationBuilder curAnim;
	private final ILoopType loopType = EDefaultLoopTypes.PLAY_ONCE;
	private boolean isRandomized;

	public ChainedAnimationBuilder(IAnimatableEntity owner, String... animNames) {
		this.owner = owner;
		this.targetController = owner.getMainWrappedController();
		this.animBuilder = new AnimationBuilder();

		for (String animName : animNames) {
			if (animName == null || animName.isEmpty()) continue;

			SingletonAnimationBuilder singletonAnim = new SingletonAnimationBuilder(owner, animName, loopType);

			if (singletonAnim != null) animations.add(singletonAnim);
			animBuilder.addAnimation(animName, loopType);
		}
	}
	
	public ChainedAnimationBuilder(IAnimatableEntity owner, SingletonAnimationBuilder... animationsToChain) {
		this.owner = owner;
		this.targetController = owner.getMainWrappedController();
		this.animBuilder = new AnimationBuilder();
		
		for (SingletonAnimationBuilder targetAnim : animationsToChain) {
			if (targetAnim == null) continue;
			
			if (targetAnim.getLoopType() != loopType) targetAnim.setLoopType(loopType);
			
			animations.add(targetAnim);
			animBuilder.addAnimation(targetAnim.getAnimationName(), loopType);
		}
	}

	@Override
	public ChainedAnimationBuilder setWrappedController(WrappedAnimationController<? extends IAnimatableEntity> targetWrappedController) {
		this.targetController = targetWrappedController;
		return this;
	}
	
	@Override
	public ChainedAnimationBuilder setAnimSpeed(double animSpeedMultiplier) {
		this.animSpeedMultiplier = animSpeedMultiplier;
		return this;
	}

	/**
	 * Sets whether or not the chain in this {@code ChainedAnimationBuilder} instance should be shuffled.
	 * @param randomized whether or not the chain in this {@code ChainedAnimationBuilder} instance should be shuffled.
	 * @return {@code this} (builder method).
	 */
	public ChainedAnimationBuilder setRandomized(boolean randomized) {
		this.isRandomized = randomized;
		return this;
	}

	/**
	 * Sets the amount of times a specified animation (by name) should loop before continuing the chaining sequence.
	 * @param animName The name of the animation to set the loop reps for.
	 * @param loopReps The amount of times the specified animation (by name) should loop before continuing the chaining sequence.
	 * @return {@code this} (builder method).
	 */
	public ChainedAnimationBuilder setLoopRepsFor(String animName, int loopReps) {
		List<RawAnimation> rawAnims = animBuilder.getRawAnimationList();
		ObjectArrayList<SingletonAnimationBuilder> targetAnimLoops = new ObjectArrayList<SingletonAnimationBuilder>();
		
		assert loopReps > 0;
		rawAnims.forEach((rawAnim) -> {
			if (rawAnim.animationName.equalsIgnoreCase(animName)) {
				for (int loopRep = 0; loopRep < loopReps; loopRep++) rawAnims.add(rawAnims.indexOf(rawAnim) + 1, new RawAnimation(animName, loopType));
			}
		});
		
		animations.forEach((singletonAnim) -> {
			if (singletonAnim.getAnimationName().equalsIgnoreCase(animName)) {
				for (int loopRep = 0; loopRep < loopReps; loopRep++) {
					animations.add(animations.indexOf(singletonAnim) + 1, new SingletonAnimationBuilder(owner, singletonAnim.getAnimationName()).setWrappedController(singletonAnim.getWrappedController()));
					targetAnimLoops.add(new SingletonAnimationBuilder(owner, singletonAnim.getAnimationName()).setWrappedController(singletonAnim.getWrappedController()));
				}
			}
		});
		
		loopingAnimations.add(targetAnimLoops);

		return this;
	}

	/**
	 * Overloaded method for {@link #setLoopRepsFor(String, int)}.
	 * @param targetAnim The animation to set the loop reps for.
	 * @param loopReps The amount of times the specified animation should loop before continuing the chaining sequence.
	 * @return {@code this} (builder method).
	 */
	public ChainedAnimationBuilder setLoopRepsFor(SingletonAnimationBuilder targetAnim, int loopReps) {
		return setLoopRepsFor(targetAnim.getAnimationName(), loopReps);
	}

	/**
	 * Prevents the specified animations (by name) from being affected by chain shuffling if {@link #isRandomized} is true.
	 * @param animNames The names of the animations to add to {@link #frozenAnimations} to prevent them from being affected by chain shuffling.
	 * @return {@code this} (builder method).
	 */
	public ChainedAnimationBuilder setIndexFrozenAnimations(String... animNames) {		
		for (String animName : animNames) {
			if (animations.isEmpty() || !isRandomized) break;
			if (animName == null || animName.isEmpty()) continue;

			for (SingletonAnimationBuilder singletonAnim : animations) {
				if (frozenAnimations.contains(singletonAnim)) continue;
				if (singletonAnim.getAnimationName().equals(animName)) frozenAnimations.add(singletonAnim);
			}
		}

		return this;
	}
	
	/**
	 * Prevents the specified animations from being affected by chain shuffling if {@link #isRandomized} is true.
	 * @param animationsToFreeze The animations to add to {@link #frozenAnimations} to prevent them from being affected by chain shuffling.
	 * @return {@code this} (builder method).
	 */
	public ChainedAnimationBuilder setIndexFrozenAnimations(SingletonAnimationBuilder... animationsToFreeze) {
		for (SingletonAnimationBuilder curAnim : animationsToFreeze) {
			if (animations.isEmpty() || !isRandomized) break;
			if (curAnim == null) continue;
			
			for (SingletonAnimationBuilder singletonAnim : animations) {
				if (frozenAnimations.contains(singletonAnim)) continue;
				if (singletonAnim.getAnimationName().equals(curAnim.getAnimationName())) frozenAnimations.add(singletonAnim);
			}
		}
		
		return this;
	}
	
	/**
	 * Sets a condition which, if met, makes the chaining sequence skip the specified animation (by name) <b>IF</b> and only if said animation is playing.
	 * @param animNameToSkip The name of the animation to skip in the chaining sequence (if it's playing).
	 * @param skipPredicate The predicate to test against for skipping the specified animation (by name).
	 * @return {@code this} (builder method).
	 */
	public ChainedAnimationBuilder skipAnimationIf(String animNameToSkip, Predicate<? extends IAnimatableEntity> skipPredicate) {
		animations.stream()
				.filter((singletonAnim) -> singletonAnim.getAnimationName().equalsIgnoreCase(animNameToSkip))
				.findFirst()
				.ifPresent((singletonAnim) -> animationsToSkip.put(singletonAnim, skipPredicate));
		
		return this;
	}
	
	/**
	 * Overloaded method for {@link #skipAnimationIf(String, Predicate)}.
	 * @param animToSkip The animation to skip in the chaining sequence (if it's playing).
	 * @param skipPredicate The predicate to test against for skipping the specified animation.
	 * @return {@code this} (builder method).
	 */
	public ChainedAnimationBuilder skipAnimationIf(SingletonAnimationBuilder animToSkip, Predicate<? extends IAnimatableEntity> skipPredicate) {
		return skipAnimationIf(animToSkip.getAnimationName(), skipPredicate);
	}

	@Override
	public WrappedAnimationController<? extends IAnimatableEntity> getWrappedController() {
		return targetController;
	}

	/**
	 * Gets whether this {@code ChainedAnimationBuilder} instance should shuffle its animation chain.
	 * @return {@code true} if {@link #isRandomized} is set to true (through {@link #setRandomized(boolean)}), else returns {@code false}.
	 */
	public boolean isRandomized() {
		return isRandomized;
	}
	
	/**
	 * Gets the cached animations (in {@link #animations}).
	 * @return An immutable view of {@link #animations}.
	 */
	public ImmutableList<SingletonAnimationBuilder> getCachedAnimations() {
		return ImmutableList.copyOf(animations);
	}
	
	/**
	 * Gets the animations unaffected by chain shuffling (in {@link #frozenAnimations}).
	 * @return An immutable view of {@link #frozenAnimations}.
	 */
	public ImmutableList<SingletonAnimationBuilder> getIndexFrozenAnimations() {
		return ImmutableList.copyOf(frozenAnimations);
	}
	
	/**
	 * Gets the queued animations ready to be played (in {@link #queuedAnimations}).
	 * @return An immutable view of {@link #queuedAnimations}.
	 */
	public ImmutableList<SingletonAnimationBuilder> getQueuedAnimations() {
		return ImmutableList.copyOf(queuedAnimations);
	}

	@Override
	public IAnimatableEntity getOwner() {
		return owner;
	}

	@Override
	public EDefaultLoopTypes getLoopType() {
		return (EDefaultLoopTypes) loopType;
	}

	@Override
	public boolean isPlaying() {
		if (!ObjectUtil.performNullityChecks(false, animBuilder, getCurrentAnimation(), targetController)) return false;
		if (animations.isEmpty() || queuedAnimations.isEmpty()) return false;

		return getWrappedController().getAnimationProgressTicks() < getWrappedAnimLength() && owner.isPlayingAnimation(getCurrentAnimation(), targetController);
	}

	@Override
	public boolean hasAnimationFinished() {
		if (!ObjectUtil.performNullityChecks(false, animBuilder, targetController)) return false;
		return curAnim == null || queuedAnimations.isEmpty();
	}

	@Override
	public String getAnimationName() {
		return curAnim != null ? curAnim.getAnimationName() : "None";
	}

	@Override
	public String getDatapackFileName() {
		return null;
	}

	@Override
	public Animation getAnimation() {
		return owner.getModel().getAnimation(curAnim.getAnimationName(), owner);
	}
	
	@Nullable
	public SingletonAnimationBuilder getCurrentAnimation() {
		return curAnim;
	}

	@Override
	public ExpandedAnimationState getAnimationState() {
		return targetController.getAnimationState();
	}

	@Override
	public AnimationBuilder getBuilder() {
		return animBuilder;
	}

	@Override
	public double getWrappedAnimProgress() {
		return curAnim != null ? curAnim.getWrappedAnimProgress() : 0;
	}

	@Override
	public double getWrappedAnimLength() {
		return curAnim != null ? curAnim.getWrappedAnimLength() : 0;
	}

	/**
	 * Begins a chaining sequence in which the animations passed in are played in a sequence specified by {@link #queuedAnimations}. The chaining 
	 * sequence is completely handled by and stored in {@link #queuedAnimations}, including animation order shuffling (if {@link #isRandomized} is 
	 * true). Note that <i>while</i> the chaining sequence is in progress, it doesn't force itself on the wrapped {@link AnimationController}. This 
	 * means that the chaining sequence can be interrupted at any time.
	 */
	@Override
	public void playAnimation(boolean forceAnim) {
		if (!ObjectUtil.performNullityChecks(false, animBuilder, targetController, animations, queuedAnimations)) return;

		final boolean isCurrentlyRandomized = isRandomized; // Finalize the boolean in case its field was modified somewhere/time it shouldn't have been.

		if (forceAnim) {
			getWrappedController().getWrappedController().clearAnimationCache();
			queuedAnimations.clear();
			this.curAnim = null;
		}
		if (isCurrentlyRandomized) {			
			queuedAnimations.addAll(animations);
			handleAnimationShuffling();
			assertAnimationOrder();

			while (!queuedAnimations.isEmpty()) {
				if (getWrappedAnimProgress() == 0) {
					SingletonAnimationBuilder singletonAnim = queuedAnimations.poll();
					this.curAnim = singletonAnim;
				}
			}

			getWrappedController().getWrappedController().setAnimation(animBuilder);
		} else {			
			queuedAnimations.addAll(animations);
			assertAnimationOrder();

			while (!queuedAnimations.isEmpty()) {
				if (getWrappedAnimProgress() == 0) {
					SingletonAnimationBuilder singletonAnim = queuedAnimations.poll();
					this.curAnim = singletonAnim;
				}
			}
			
			getWrappedController().getWrappedController().setAnimation(animBuilder);
		}
	}

	@Override
	public void stopAnimation() {
		if (!ObjectUtil.performNullityChecks(false, animBuilder, targetController, animations, queuedAnimations)) return;

		queuedAnimations.clear();
		getWrappedController().getWrappedController().setAnimation(null);
	}

	/**
	 * Uses a temporary {@link ObjectArrayList} of type {@link SingletonAnimationBuilder} in order to shuffle the {@link #queuedAnimations}. This 
	 * allows for the application of animation shuffling whilst avoiding making any modifications to {@link #animations}.
	 */
	private void handleAnimationShuffling() {
		final boolean isCurrentlyRandomized = isRandomized;

		if (animations.isEmpty() || queuedAnimations.isEmpty() || !isCurrentlyRandomized) return;
		ObjectArrayList<SingletonAnimationBuilder> shuffledAnimationsList = new ObjectArrayList<SingletonAnimationBuilder>(queuedAnimations);

		for (int i = 0; i < shuffledAnimationsList.size(); i++) {
			SingletonAnimationBuilder curSingletonAnim = shuffledAnimationsList.get(i);

			if (!shouldRandomizeAnimation(curSingletonAnim)) continue; //TODO Add shuffling for looping anims.
			
			boolean isLoopingAnim = loopingAnimations.stream()
					.map((loopingAnim) -> loopingAnim.stream().filter((singletonAnim) -> singletonAnim.getAnimationName().equalsIgnoreCase(curSingletonAnim.getAnimationName())).findFirst())
					.filter((singletonAnim) -> singletonAnim.isPresent())
					.findFirst()
					.isPresent();
			
			if (isLoopingAnim) {
				
			} else Collections.swap(shuffledAnimationsList, shuffledAnimationsList.indexOf(curSingletonAnim), ((LivingEntity) owner).getRandom().nextInt(i));
		}

		queuedAnimations.clear();
		queuedAnimations.addAll(shuffledAnimationsList);
	}

	/**
	 * Checks whether or not a specific animation should be shuffled if {@link #isRandomized} is true. Since animations with loop 
	 * repetitions count as separate animations played consecutively (similar to how Geckolib handles it), this method asserts that both animations 
	 * in {@link #frozenAnimations} and repeated/looping animations aren't shuffled when {@link #handleAnimationShuffling()} is called.
	 * @param targetAnim The target animation to check for randomization selection.
	 * @return {@code true} if the specified animation (by name) can be shuffled, else returns {@code false}.
	 */
	private boolean shouldRandomizeAnimation(SingletonAnimationBuilder targetAnim) {
		final boolean isCurrentlyRandomized = isRandomized;
		
		if (animBuilder.getRawAnimationList().isEmpty() || animations.isEmpty() || queuedAnimations.isEmpty()) return false;

		return !frozenAnimations.contains(targetAnim) && isCurrentlyRandomized;
	}

	/**
	 * Asserts the order of {@link #queuedAnimations} on {@link AnimationBuilder#getRawAnimationList()} (in {@link #animBuilder}).
	 */
	private void assertAnimationOrder() {
		ObjectArrayList<SingletonAnimationBuilder> tempQueuedAnimationsList = new ObjectArrayList<SingletonAnimationBuilder>(queuedAnimations);

		if (!tempQueuedAnimationsList.equals(animBuilder.getRawAnimationList())) {
			animBuilder.getRawAnimationList().clear();

			ObjectArrayList<RawAnimation> reorderedRawAnims = tempQueuedAnimationsList.stream()
					.map((singletonAnim) -> new RawAnimation(singletonAnim.getAnimationName(), loopType))
					.collect(Collectors.toCollection(ObjectArrayList::new));

			animBuilder.getRawAnimationList().addAll(reorderedRawAnims);
		}
	}

	@Override
	public double getWrappedAnimSpeed() {
		return curAnim == null ? 1.0D : curAnim.getWrappedAnimSpeed();
	}
}
