package io.github.chaosawakens.api.animation;

import javax.annotation.Nullable;

import io.github.chaosawakens.common.util.ObjectUtil;
import software.bernie.geckolib3.core.builder.Animation;
import software.bernie.geckolib3.core.builder.AnimationBuilder;
import software.bernie.geckolib3.core.builder.ILoopType;
import software.bernie.geckolib3.core.builder.ILoopType.EDefaultLoopTypes;

/**
 * {@code IAnimationBuilder} instance class. Allows for the instantiation of only 1 (immutable) animation by wrapping
 * around an {@link AnimationBuilder} instance and pruning its list down to the first animation (the one passed in here).
 * <br> </br>
 * This class holds both client and server side data for its animation. Standard metadata such as Geckolib's animation
 * tick, the animation state, and animation loop type are <b>ONLY</b> present on the client. Other metadata unique to
 * CA such as a server-side animation progress (tick) field, animation length (Geckolib), and animation name are present
 * on the server (sometimes on the client, depends on the data).
 * <br> </br>
 * It's advised that animation handling is done solely through {@link IAnimatableEntity} methods, as they handle any
 * necessary siding (note that this does not mean client data will be present on the server and vice versa). The server
 * progress is effectively "detached" from the client tick progress due to variation in client animation progress between
 * clients and other factors. The server side progress should still pretty much sync to the client progress, but there's
 * no guarantee that both will communicate with each other as that in itself can and will cause hard to debug logical
 * errors. Can still be attributed to MC's goofiness, though :p
 * <br> </br>
 * Another thing to note is that this object <b>CANNOT</b> be statically instantiated, due to the fact that you need to
 * pass an {@link IAnimatableEntity} instance in. This means that it'll be instantiated every time an animation
 * controller needs to play it, but not every <i>frame</i>. Still much better than handling metadata and such per frame
 * (potentially multiple times per-frame), though.
 */
public class SingletonAnimationBuilder implements IAnimationBuilder {
	private final IAnimatableEntity owner;
	@Nullable
	private WrappedAnimationController<? extends IAnimatableEntity> targetController;
	private final AnimationBuilder animBuilder;
	private final String animName;
	private ILoopType loopType = EDefaultLoopTypes.PLAY_ONCE;
	private ExpandedAnimationState animState;

	public SingletonAnimationBuilder(IAnimatableEntity owner, String animName) {
		this.owner = owner;
		this.targetController = owner.getMainWrappedController();
		this.animName = animName;
		this.animBuilder = new AnimationBuilder().addAnimation(animName);
		this.animBuilder.getRawAnimationList().removeIf((anim) -> animBuilder.getRawAnimationList().indexOf(anim) > 0);
		this.animState = targetController.getAnimationState();
		this.owner.getAnimations().add(this);
	}

	public SingletonAnimationBuilder(IAnimatableEntity owner, String animName, ILoopType loopType) {
		this.owner = owner;
		this.targetController = owner.getMainWrappedController();
		this.animName = animName;
		this.loopType = loopType;
		this.animBuilder = new AnimationBuilder().addAnimation(animName, loopType);
		this.animBuilder.getRawAnimationList().removeIf((anim) -> animBuilder.getRawAnimationList().indexOf(anim) > 0);
		this.animState = targetController.getAnimationState();
		this.owner.getAnimations().add(this);
	}

	public SingletonAnimationBuilder(IAnimatableEntity owner, String animName, int loopReps) {
		this.owner = owner;
		this.targetController = owner.getMainWrappedController();
		this.animName = animName;
		this.animBuilder = new AnimationBuilder().addRepeatingAnimation(animName, loopReps);
		this.animBuilder.getRawAnimationList().removeIf((anim) -> animBuilder.getRawAnimationList().indexOf(anim) > 0);
		this.animState = targetController.getAnimationState();
		this.owner.getAnimations().add(this);
	}

	public SingletonAnimationBuilder setWrapped(WrappedAnimationController<? extends IAnimatableEntity> wrapped) {
		this.targetController = wrapped;
		return this;
	}

	@Override
	public WrappedAnimationController<? extends IAnimatableEntity> getWrappedController() {
		return targetController;
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
		return ObjectUtil.performNullityChecks(false, animBuilder, getAnimation(), targetController)
				&& this.getWrappedController().getAnimationProgress() < getWrappedAnimLength()
				&& owner.isPlayingAnimation(this, targetController);
	}

	@Override
	public boolean hasAnimationFinished() {
		if (!ObjectUtil.performNullityChecks(false, animBuilder, getAnimation(), targetController)) return false;
		boolean wasPlaying = false;
		
		if (isPlaying()) wasPlaying = true;
		
		return wasPlaying ?
//				progress >= getLengthTicks()
				targetController.getAnimationState() == ExpandedAnimationState.FINISHED
				|| ( this.getWrappedController().getAnimationProgress() >= getWrappedAnimLength()
				&& targetController.getAnimationState() == ExpandedAnimationState.RUNNING) : false;
	}

	@Override
	public Animation getAnimation() {
		return owner.getModel().getAnimation(animName, owner);
	}
	
	public ExpandedAnimationState getAnimState() {
		this.animState = targetController.getAnimationState();
		return animState;
	}

	@Override
	public AnimationBuilder getBuilder() {
		return animBuilder;
	}

	@Override
	public void playAnimation(boolean forceAnim) {
		if (!ObjectUtil.performNullityChecks(false, animBuilder, getAnimation(), targetController)) return;

		if (forceAnim) targetController.getWrappedController().clearAnimationCache();
		targetController.getWrappedController().setAnimation(getBuilder());
	}

	/**
	 * Trigger the animation stored in the <code>AnimationBuilder</code> object through the owner controller.
	 * It is recommended that you use {@link IAnimatableEntity}'s implementation instead 
	 * ({@link IAnimatableEntity#playAnimation(SingletonAnimationBuilder, boolean)}), as it handles all the necessary checks
	 * should you need to trigger animations server-side. Otherwise, call this with caution (only client-side)!
	 */
	public void playAnimation() {
		playAnimation(false);
	}

	@Override
	public void stopAnimation() {
		if (!ObjectUtil.performNullityChecks(false, animBuilder, getAnimation(), targetController)) return;
		targetController.getWrappedController().setAnimation(null);
	}

	@Override
	public double getWrappedAnimProgress() {
		return this.getWrappedController().getAnimationProgress();
	}

	@Override
	public double getWrappedAnimLength() {
		return this.getWrappedController().getAnimationLength();
	}
}
