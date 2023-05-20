package io.github.chaosawakens.common.util;

import java.util.Random;
import java.util.function.Supplier;

import javax.annotation.Nonnull;

import io.github.chaosawakens.api.animation.IAnimatableEntity;
import io.github.chaosawakens.api.animation.SingletonAnimationBuilder;
import software.bernie.geckolib3.core.controller.AnimationController;

public final class AnimationUtil {
	
	private AnimationUtil() {
		throw new IllegalAccessError("Utility Class");
	}

	//TODO Prune
	public static void consecutivelyPlayAnimations(IAnimatableEntity animatableOwner, AnimationController<? extends IAnimatableEntity> targetController, boolean shouldForce, SingletonAnimationBuilder... animations) {
		if (animatableOwner != null) {
			for (int i = 0; i < animations.length; i++) {
				SingletonAnimationBuilder curAnim = animations[i];
				SingletonAnimationBuilder nextAnim = animations[i + 1];
				
				curAnim.setController(targetController);
				nextAnim.setController(targetController);
				
				if (shouldForce) {
					targetController.markNeedsReload();
					targetController.setAnimation(curAnim.getBuilder());
				//	if (curAnim.hasAnimationFinished()) targetController.setAnimation(nextAnim.getBuilder());
				} else {
					targetController.setAnimation(curAnim.getBuilder());
				//	if (curAnim.hasAnimationFinished()) targetController.setAnimation(nextAnim.getBuilder());
				}
			}
		}
	}

	public static void consecutivelyPlayAnimations(IAnimatableEntity animatableOwner, boolean shouldForce, SingletonAnimationBuilder... animations) {
		consecutivelyPlayAnimations(animatableOwner, animatableOwner.getMainController(), shouldForce, animations);
	}

	public static void consecutivelyPlayAnimations(IAnimatableEntity animatableOwner, SingletonAnimationBuilder... animations) {
		consecutivelyPlayAnimations(animatableOwner, animatableOwner.getMainController(), true, animations);
	}
	
	@Nonnull
	public static Supplier<SingletonAnimationBuilder> pickAnimation(Supplier<SingletonAnimationBuilder> animA, Supplier<SingletonAnimationBuilder> animB, Random probabilityA) {
		return probabilityA.nextInt(2) == 0 ? animA : animB;
	}
}
