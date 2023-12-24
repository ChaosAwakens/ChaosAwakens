package io.github.chaosawakens.common.util;

import io.github.chaosawakens.ChaosAwakens;
import io.github.chaosawakens.common.codec.assets.AnimationDataCodec;
import io.github.chaosawakens.common.registry.CADataLoaders;
import net.minecraft.client.Minecraft;
import net.minecraft.resources.IReloadableResourceManager;
import net.minecraft.util.ResourceLocation;
import software.bernie.geckolib3.core.builder.ILoopType;
import software.bernie.geckolib3.file.AnimationFileLoader;
import software.bernie.geckolib3.resource.GeckoLibCache;
import software.bernie.shadowed.eliotlash.molang.MolangParser;

import javax.annotation.Nullable;

public final class AnimationUtil {
	
	private AnimationUtil() {
		throw new IllegalAccessError("Attempted to instantiate a Utility Class!");
	}
	
	public static void assertS2CAnimationData() {
		
	}

	/**
	 * Attempts to retrieve the length of the specified animation, depending on whether the specified {@link ResourceLocation} is server or client-oriented.
	 * TODO THIS IS A VERY ICKY IMPL, BUT WE NEED QUICKNESS IN RELEASING 0.12 AND HEADING OVER TO NEWER VERSIONS!!
	 *
	 * @param animLoc The animation's resource location.
	 * @param targetAnimName The name of the animation to get the length of (relevant only on the client).
	 *
	 * @return The length of the specified animation, from the determined side.
	 */
	public static double getSidedAnimLength(ResourceLocation animLoc, @Nullable String targetAnimName) { //TODO Wacky ahh impl with so many safety hazards it's almost like a construction site :skull, but it's 1.16 so who cares
		String fullRl = animLoc.toString();
		double length = -1;

		if (fullRl.contains("animations")) {
			try {
				AnimationFileLoader animationFileLoader = new AnimationFileLoader();
				IReloadableResourceManager mcRrm = (IReloadableResourceManager) Minecraft.getInstance().getResourceManager();
				MolangParser parser = GeckoLibCache.getInstance().parser;

				length = animationFileLoader.loadAllAnimations(parser, animLoc, mcRrm).getAnimation(targetAnimName).animationLength;
			} catch (Exception e) {
				e.printStackTrace();
			}
		} else if (fullRl.contains("animation_metadata")) {
			AnimationDataCodec animData = CADataLoaders.ANIMATION_DATA.getData(animLoc);

			if (animData != null) length = animData.getAnimationLength();
			else ChaosAwakens.LOGGER.warn("Attempted to get length of animation data that does not exist: {}", animLoc);
		}

		return length;
	}

	/**
	 * Attempts to retrieve the {@link ILoopType} of the specified animation, depending on whether the specified {@link ResourceLocation} is server or client-oriented.
	 * TODO THIS IS A VERY ICKY IMPL, BUT WE NEED QUICKNESS IN RELEASING 0.12 AND HEADING OVER TO NEWER VERSIONS!!
	 *
	 * @param animLoc The animation's resource location.
	 * @param targetAnimName The name of the animation to get the loop type of (relevant only on the client).
	 *
	 * @return The {@link ILoopType} of the specified animation, from the determined side.
	 */
	public static ILoopType getLoopType(ResourceLocation animLoc, @Nullable String targetAnimName) {
		ILoopType loopType = ILoopType.EDefaultLoopTypes.PLAY_ONCE;

		if (animLoc.toString().contains("animations")) {
			try {
				AnimationFileLoader animationFileLoader = new AnimationFileLoader();
				IReloadableResourceManager mcRrm = (IReloadableResourceManager) Minecraft.getInstance().getResourceManager();
				MolangParser parser = GeckoLibCache.getInstance().parser;

				loopType = animationFileLoader.loadAllAnimations(parser, animLoc, mcRrm).getAnimation(targetAnimName).loop;
			} catch (Exception e) {
				e.printStackTrace();
			}
		} else if (animLoc.toString().contains("animation_metadata")) {
			AnimationDataCodec animData = CADataLoaders.ANIMATION_DATA.getData(animLoc);

			if (animData != null) loopType = animData.getLoopType();
			else ChaosAwakens.LOGGER.warn("Attempted to get loop type of animation data that does not exist: {}", animLoc);
		}

		return loopType;
	}
}
