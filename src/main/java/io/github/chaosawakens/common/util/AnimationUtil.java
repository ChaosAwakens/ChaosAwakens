package io.github.chaosawakens.common.util;

import java.util.Random;
import java.util.function.Supplier;

import javax.annotation.Nonnull;

import io.github.chaosawakens.api.animation.IAnimatableEntity;
import io.github.chaosawakens.api.animation.SingletonAnimationBuilder;
import io.github.chaosawakens.api.animation.WrappedAnimationController;

public final class AnimationUtil {
	
	private AnimationUtil() {
		throw new IllegalAccessError("Utility Class");
	}

	//TODO Prune
	public static void consecutivelyPlayAnimations(IAnimatableEntity animatableOwner, WrappedAnimationController<? extends IAnimatableEntity> targetWrapped, boolean shouldForce, SingletonAnimationBuilder... animations) {
		if (animatableOwner != null) {
			for (int i = 0; i < animations.length; i++) {
				SingletonAnimationBuilder curAnim = animations[i];
				SingletonAnimationBuilder nextAnim = animations[i + 1];
				
				curAnim.setWrappedController(targetWrapped);
				nextAnim.setWrappedController(targetWrapped);
				
				if (shouldForce) {
					targetWrapped.getWrappedController().markNeedsReload();
					targetWrapped.getWrappedController().setAnimation(curAnim.getBuilder());
				//	if (curAnim.hasAnimationFinished()) targetController.setAnimation(nextAnim.getBuilder());
				} else {
					targetWrapped.getWrappedController().setAnimation(curAnim.getBuilder());
				//	if (curAnim.hasAnimationFinished()) targetController.setAnimation(nextAnim.getBuilder());
				}
			}
		}
	}

	public static void consecutivelyPlayAnimations(IAnimatableEntity animatableOwner, boolean shouldForce, SingletonAnimationBuilder... animations) {
		consecutivelyPlayAnimations(animatableOwner, animatableOwner.getMainWrappedController(), shouldForce, animations);
	}

	public static void consecutivelyPlayAnimations(IAnimatableEntity animatableOwner, SingletonAnimationBuilder... animations) {
		consecutivelyPlayAnimations(animatableOwner, animatableOwner.getMainWrappedController(), true, animations);
	}
	
	@Nonnull
	public static Supplier<SingletonAnimationBuilder> pickAnimation(Supplier<SingletonAnimationBuilder> animA, Supplier<SingletonAnimationBuilder> animB, Random probabilityA) {
		return probabilityA.nextInt(2) == 0 ? animA : animB;
	}
}
