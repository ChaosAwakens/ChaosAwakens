package io.github.chaosawakens.api.animation;

//W.I.P
public class AdvancedAnimationInformation extends AnimationInformation {

	public AdvancedAnimationInformation(String animationName, double animatonLength) {
		super(animationName, animatonLength);
	}
	
	public static class AdvAnimInfoBuilder extends AnimInfoBuilder {
		
		
		public AdvAnimInfoBuilder() {
		}
		
		
		
		public AnimationInformation buildAsBasic() {
			AnimationInformation basicAnimMetadata = new AnimationInformation(animationName, animationLength);
			return basicAnimMetadata;
		}
		
		@Override
		public AdvancedAnimationInformation build() {
			return null;
		}
	}
}
