package io.github.chaosawakens.api.animation;

import com.google.common.collect.ImmutableMap;

import it.unimi.dsi.fastutil.objects.Object2DoubleOpenHashMap;

/**
 * This class is primarily used to sync animation information from the server to the client after the server receives the required asset metadata from the client. 
 * Does not yet support resource packing.
 */
public class AnimationInformation {
	private final String animationName;
	private final double animationLength;
	private static final Object2DoubleOpenHashMap<String> SERVER_ANIMATION_METADATA = new Object2DoubleOpenHashMap<String>();
	
	public AnimationInformation(String animationName, double animatonLength) {
		this.animationName = animationName;
		this.animationLength = animatonLength;
	}
	
	public AnimationInformation(AnimInfoBuilder infoBuilder) {
		this(infoBuilder.animationName, infoBuilder.animationLength);
	}
	
	public static AnimInfoBuilder builder() {
		return new AnimInfoBuilder();
	}
	
	/**
	 * Gets an immutable view of {@link #SERVER_ANIMATION_METADATA}.
	 * @return An immutable map containing all entries in {@link #SERVER_ANIMATION_METADATA}.
	 */
	public static ImmutableMap<String, Double> getAnimationMetadata() {
		return ImmutableMap.copyOf(SERVER_ANIMATION_METADATA);
	}
	
	public String getAnimationName() {
		return animationName;
	}
	
	public double getAnimationLength() {
		return animationLength;
	}
	
	@Override
	public boolean equals(Object other) {
		if (other == null) return false;
		else if (!(other instanceof AnimationInformation)) return false;
		
		AnimationInformation otherAnimInfo = (AnimationInformation) other;
				
		return getAnimationName().equalsIgnoreCase(otherAnimInfo.getAnimationName()) && getAnimationLength() == otherAnimInfo.getAnimationLength();
	}
	
	@Override
	public String toString() {
		return String.format("AnimationInfo{Animation Name = %1$s, Animation Length = %2$d}", getAnimationName(), getAnimationLength());
	}
	
	public static class AnimInfoBuilder {
		protected String animationName = "None";
		protected double animationLength = 0.0D;
		
		public AnimInfoBuilder() {
		}
		
		public AnimInfoBuilder setAnimationName(String animationName) {
			this.animationName = animationName;
			return this;
		}
		
		public AnimInfoBuilder setAnimationLength(double animationLength) {
			this.animationLength = animationLength;
			return this;
		}
		
		public AnimationInformation build() {
			AnimationInformation targetAnimInfo = new AnimationInformation(animationName, animationLength);
			return targetAnimInfo;
		}
	}
}