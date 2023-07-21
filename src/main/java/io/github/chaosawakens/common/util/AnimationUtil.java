package io.github.chaosawakens.common.util;

import java.util.Random;
import java.util.function.Supplier;

import javax.annotation.Nonnull;

import io.github.chaosawakens.api.animation.SingletonAnimationBuilder;

public final class AnimationUtil {
	
	private AnimationUtil() {
		throw new IllegalAccessError("Attempted to instantiate a Utility Class!");
	}
	
	@Nonnull
	public static Supplier<SingletonAnimationBuilder> pickAnimation(Supplier<SingletonAnimationBuilder> animA, Supplier<SingletonAnimationBuilder> animB, Random probabilityA) {
		return probabilityA.nextInt(2) == 0 ? animA : animB;
	}
}
