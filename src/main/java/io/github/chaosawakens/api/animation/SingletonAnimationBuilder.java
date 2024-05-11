package io.github.chaosawakens.api.animation;

import io.github.chaosawakens.common.util.ObjectUtil;
import software.bernie.geckolib3.core.builder.Animation;
import software.bernie.geckolib3.core.builder.AnimationBuilder;
import software.bernie.geckolib3.core.builder.ILoopType;
import software.bernie.geckolib3.core.builder.ILoopType.EDefaultLoopTypes;

import javax.annotation.Nullable;

/**
 * {@link IAnimationBuilder} instance class. Allows for the instantiation of only 1 (immutable) animation by wrapping
 * around an {@link AnimationBuilder} instance and pruning its list down to the first animation (the one passed in here).
 * <br> </br>
 * <br> </br>
 * This class holds both client and server side data for its animation. Standard metadata such as Geckolib's animation
 * tick, the animation state, and animation loop type is <b>ONLY</b> present on the client. Other metadata unique to
 * CA such as a server-side animation progress (tick) field, {@link ExpandedAnimationState}, animation length (Geckolib), and animation name is present
 * on the server (sometimes on the client, depends on the data).
 * <br> </br>
 * <br> </br>
 * 
 * <table> <tbody>
 * <caption><i>The following table represents which animation data/metadata is present on which side. All unattached data is effectively 
 * synced about 99% of the time anyway, so the fact that data is unattached (i.e. not actively synced between sides) doesn't necessarily 
 * mean that it's unsafe to use.</i></caption>
 * <thead>
 * <tr>
 *   <th scope="col">Data Type</th>
 *   <th scope="col">Client</th>
 *   <th scope="col">Server</th>
 *   <th scope="col">Attached</th>
 * </tr>
 * </thead>
 * 
 * <tr>
 * 	 <td>Animation Progress (Chaos Awakens)</td>
 * 	 <td>Present</td>
 * 	 <td>Present</td>
 * 	 <td>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;Yes</td>
 * </tr>
 * 
 * <tr>
 * 	 <td>Animation Progress (Geckolib)</td>
 * 	 <td>Present</td>
 * 	 <td>Absent</td>
 * 	 <td>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;No</td>
 * </tr>
 *
 * <tr>
 * 	 <td>Animation Length (Chaos Awakens)</td>
 * 	 <td>Present</td>
 * 	 <td>Present</td>
 * 	 <td>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;No</td>
 * </tr>
 *
 * <tr>
 * 	 <td>Animation Length (Geckolib)</td>
 * 	 <td>Present</td>
 * 	 <td>Absent</td>
 * 	 <td>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;No</td>
 * </tr>
 * 
 * <tr>
 * 	 <td>Animation Name (Chaos Awakens)</td>
 * 	 <td>Present</td>
 * 	 <td>Present</td>
 * 	 <td>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;Yes</td>
 * </tr>
 * 
 * <tr>
 * 	 <td>Animation Name (Geckolib)</td>
 * 	 <td>Present</td>
 * 	 <td>Present</td>
 * 	 <td>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;Yes</td>
 * </tr>
 * 
 * <tr>
 * 	 <td>Animation State (Chaos Awakens)</td>
 * 	 <td>Present</td>
 * 	 <td>Present</td>
 * 	 <td>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;Yes</td>
 * </tr>
 * 
 * <tr>
 * 	 <td>Animation State (Geckolib)</td>
 * 	 <td>Present</td>
 * 	 <td>Present</td>
 * 	 <td>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;No</td>
 * </tr>
 *
 * <tr>
 *   <td>Animation Loop Type (Chaos Awakens)</td>
 *   <td>Present</td>
 *   <td>Present</td>
 *   <td>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;No</td>
 * </tr>
 *
 * <tr>
 *   <td>Animation Loop Type (Geckolib)</td>
 *   <td>Present</td>
 *   <td>Present</td>
 *   <td>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;No</td>
 * </tr>
 * 
 * <tr>
 *   <td>Animation Model (Geckolib)</td>
 *   <td>Present</td>
 *   <td>Absent</td>
 *   <td>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;No</td>
 * </tr>
 * </tbody></table>
 * 
 * <br> </br>
 * <br> </br>
 * It's advised that animation handling is done solely through {@link IAnimatableEntity} methods, as they handle any
 * necessary siding (note that this does not mean client-exclusive data will be present on the server and vice versa). The "functional" 
 * progress is effectively "detached" from Geckolib's tick progress (unless enforced otherwise) due to variation in client animation progress between
 * clients and other factors. The functional tick progress should still pretty much sync to Geckolib's tick progress, but there's
 * no guarantee that both will communicate with each other as that in itself can and will cause hard to debug logical
 * errors. Can still be attributed to MC's goofiness, though :p
 * <br> </br>
 * <br> </br>
 * Another thing to note is that this object <b>CANNOT</b> be statically instantiated, due to the fact that you need to
 * pass an {@link IAnimatableEntity} instance in. This means that it'll be instantiated 
 * once per entity instance, but not every <i>frame</i>. Still much better than handling metadata and such per frame
 * (potentially multiple times per-frame), though.
 */
public class SingletonAnimationBuilder implements IAnimationBuilder {
	private final IAnimatableEntity owner;
	@Nullable
	private WrappedAnimationController<? extends IAnimatableEntity> targetController;
	private final AnimationBuilder animBuilder;
	private final String animName;
	private double animSpeedMultiplier = 1.0D;
	private ILoopType loopType = EDefaultLoopTypes.PLAY_ONCE;
	private final Animation heldAnim;

	public SingletonAnimationBuilder(IAnimatableEntity owner, String animName) {
		this.owner = owner;
		this.targetController = owner.getMainWrappedController();
		this.animName = animName;
		this.animBuilder = new AnimationBuilder().addAnimation(animName);
		this.animBuilder.getRawAnimationList().removeIf((anim) -> animBuilder.getRawAnimationList().indexOf(anim) > 0);
		this.heldAnim = new Animation();

		this.heldAnim.animationLength = owner.getSidedMetadataFor(animName).get().getAnimationLength();
		this.heldAnim.animationName = animName;
		this.heldAnim.loop = owner.getSidedMetadataFor(animName).get().getLoopType();

		owner.getCachedAnimations().add(this);
	}

	public SingletonAnimationBuilder(IAnimatableEntity owner, String animName, ILoopType loopType) {
		this.owner = owner;
		this.targetController = owner.getMainWrappedController();
		this.animName = animName;
		this.loopType = loopType;
		this.animBuilder = new AnimationBuilder().addAnimation(animName, loopType);
		this.animBuilder.getRawAnimationList().removeIf((anim) -> animBuilder.getRawAnimationList().indexOf(anim) > 0);
		this.heldAnim = new Animation();

		this.heldAnim.animationLength = owner.getSidedMetadataFor(animName).get().getAnimationLength();
		this.heldAnim.animationName = animName;
		this.heldAnim.loop = owner.getSidedMetadataFor(animName).get().getLoopType();

		owner.getCachedAnimations().add(this);
	}

	public SingletonAnimationBuilder(IAnimatableEntity owner, String animName, int loopReps) {
		this.owner = owner;
		this.targetController = owner.getMainWrappedController();
		this.animName = animName;
		this.animBuilder = new AnimationBuilder().addRepeatingAnimation(animName, loopReps);
		this.animBuilder.getRawAnimationList().removeIf((anim) -> animBuilder.getRawAnimationList().indexOf(anim) > 0);
		this.heldAnim = new Animation();

		this.heldAnim.animationLength = owner.getSidedMetadataFor(animName).get().getAnimationLength();
		this.heldAnim.animationName = animName;
		this.heldAnim.loop = owner.getSidedMetadataFor(animName).get().getLoopType();

		owner.getCachedAnimations().add(this);
	}

	@Override
	public SingletonAnimationBuilder setWrappedController(WrappedAnimationController<? extends IAnimatableEntity> targetWrappedController) {
		this.targetController = targetWrappedController;
		return this;
	}

	@Override
	public SingletonAnimationBuilder setAnimSpeed(double animSpeedMultiplier) {
		this.animSpeedMultiplier = animSpeedMultiplier;
		return this;
	}

	public SingletonAnimationBuilder setLoopType(ILoopType loopType) {
		this.loopType = loopType;
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
		return ObjectUtil.performNullityChecks(false, animBuilder, targetController)
				&& getWrappedController().getAnimationProgressTicks() <= getWrappedAnimLength()
				&& owner.isPlayingAnimation(this, targetController);
	}

	@Override
	public boolean hasAnimationFinished() {
		if (!ObjectUtil.performNullityChecks(false, animBuilder, targetController))
			return false;

		return targetController.isAnimationFinished(this)
				|| (isPlaying() && targetController.getAnimationProgressTicks() >= getWrappedAnimLength()
					&& (targetController.getAnimationState() == ExpandedAnimationState.RUNNING
							|| targetController.getAnimationState() == ExpandedAnimationState.TRANSITIONING || targetController.getAnimationState() == ExpandedAnimationState.STOPPED));
	}

	@Override
	public Animation getAnimation() {
		return heldAnim;
	}

	@Override
	public String getAnimationName() {
		return animName;
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
	public void playAnimation(boolean forceAnim) {
		if (!ObjectUtil.performNullityChecks(false, animBuilder, targetController)) return;
		this.animBuilder.getRawAnimationList().removeIf((anim) -> animBuilder.getRawAnimationList().indexOf(anim) > 0);

		if (forceAnim && !isPlaying()) {
			targetController.getWrappedController().clearAnimationCache();
			targetController.getWrappedController().markNeedsReload();
		}
		if (!isPlaying()) targetController.getWrappedController().setAnimation(animBuilder);
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
		if (!ObjectUtil.performNullityChecks(false, animBuilder, targetController)) return;
		this.animBuilder.getRawAnimationList().removeIf((anim) -> animBuilder.getRawAnimationList().indexOf(anim) > 1);

		targetController.getWrappedController().setAnimation(null);
	}

	@Override
	public double getWrappedAnimProgress() {
		return isPlaying() ? getWrappedController().getAnimationProgressTicks() : 0;
	}

	@Override
	public double getWrappedAnimLength() {
		return getWrappedController().getAnimationLength();
	}

	@Override
	public double getWrappedAnimSpeed() {
		return animSpeedMultiplier;
	}

	@Override
	public String getDatapackFileName() {
		return owner.getOwnerMDFileName();
	}
}
