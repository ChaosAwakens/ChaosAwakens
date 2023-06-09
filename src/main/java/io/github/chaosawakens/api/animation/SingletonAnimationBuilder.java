package io.github.chaosawakens.api.animation;

import java.util.Map;

import javax.annotation.Nullable;

import io.github.chaosawakens.common.util.ObjectUtil;
import net.minecraft.entity.Entity;
import software.bernie.geckolib3.core.AnimationState;
import software.bernie.geckolib3.core.builder.Animation;
import software.bernie.geckolib3.core.builder.AnimationBuilder;
import software.bernie.geckolib3.core.builder.ILoopType;
import software.bernie.geckolib3.core.builder.ILoopType.EDefaultLoopTypes;
import software.bernie.geckolib3.core.controller.AnimationController;
import software.bernie.geckolib3.resource.GeckoLibCache;
import software.bernie.shadowed.eliotlash.mclib.math.Variable;

/**
 * {@code IAnimationBuilder} instance class. Allows for the instantiation of only 1 (immutable) animation by wrapping around an {@link AnimationBuilder} 
 * instance and pruning its list down to the first animation (the one passed in here).
 * <br> </br>
 * This class holds both client and server side data for its animation. Standard metadata such as Geckolib's animation tick, the animation state, and 
 * animation loop type are <b>ONLY</b> present on the client. Other metadata unique to CA such as a server-side animation progress (tick) field, 
 * animation length (Geckolib), and animation name are present on the server (sometimes on the client, depends on the data).
 * <br> </br>
 * It's advised that animation handling is done solely through {@link IAnimatableEntity} methods, as they handle any necessary siding (note that 
 * this does not mean client data will be present on the server and vice versa). The server progress is effectively "detached" from the client tick 
 * progress due to variation in client animation progress between clients and other factors. The server side progress should still pretty much 
 * sync to the client progress, but there's no guarantee that both will communicate with each other as that in itself can and will cause 
 * hard to debug logical errors. Can still be attributed to MC's goofiness, though :p
 * <br> </br>
 * Another thing to note is that this object <b>CANNOT</b> be statically instantiated, due to the fact that you need to pass an {@link IAnimatableEntity} instance in. 
 * This means that it'll be instantiated every time an animation controller needs to play it, but not every <i>frame</i>. Still much better than handling 
 * metadata and such per frame (potentially multiple times per-frame), though.
 */
public class SingletonAnimationBuilder implements IAnimationBuilder {
	private final IAnimatableEntity owner;
	@Nullable
	private AnimationController<? extends IAnimatableEntity> targetController;
	private final AnimationBuilder animBuilder;
	private final String animName;
	private double progress = 0;
	private ILoopType loopType = EDefaultLoopTypes.PLAY_ONCE;
	private AnimationState animState;

	public SingletonAnimationBuilder(IAnimatableEntity owner, String animName) {
		this.owner = owner;
		this.targetController = owner.getMainController();
		this.animName = animName;
		this.animBuilder = new AnimationBuilder().addAnimation(animName);
		this.animBuilder.getRawAnimationList().removeIf((anim) -> animBuilder.getRawAnimationList().indexOf(anim) > 0);
		this.animState = targetController.getAnimationState();
		if (this.owner.getAnimations() != null && !this.owner.getAnimations().contains(this)) this.owner.getAnimations().add(this);
		owner.getAnimations().add(this);
	}

	public SingletonAnimationBuilder(IAnimatableEntity owner, String animName, ILoopType loopType) {
		this.owner = owner;
		this.targetController = owner.getMainController();
		this.animName = animName;
		this.loopType = loopType;
		this.animBuilder = new AnimationBuilder().addAnimation(animName, loopType);
		this.animBuilder.getRawAnimationList().removeIf((anim) -> animBuilder.getRawAnimationList().indexOf(anim) > 0);
		this.animState = targetController.getAnimationState();
		if (this.owner.getAnimations() != null && this.owner.getAnimations().contains(this)) this.owner.getAnimations().add(this);
		owner.getAnimations().add(this);
	}

	public SingletonAnimationBuilder(IAnimatableEntity owner, String animName, int loopReps) {
		this.owner = owner;
		this.targetController = owner.getMainController();
		this.animName = animName;
		this.animBuilder = new AnimationBuilder().addRepeatingAnimation(animName, loopReps);
		this.animBuilder.getRawAnimationList().removeIf((anim) -> animBuilder.getRawAnimationList().indexOf(anim) > 0);
		this.animState = targetController.getAnimationState();
		if (this.owner.getAnimations() != null && !this.owner.getAnimations().contains(this)) this.owner.getAnimations().add(this);
		owner.getAnimations().add(this);
	}

	public SingletonAnimationBuilder setController(AnimationController<? extends IAnimatableEntity> targetController) {
		this.targetController = targetController;
		return this;
	}

	@Override
	public AnimationController<? extends IAnimatableEntity> getController() {
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
		return ObjectUtil.performNullityChecks(false, animBuilder, getAnimation(), targetController) && progress < getLengthTicks() && owner.isPlayingAnimation(this, targetController);
	}

	@Override
	public boolean hasAnimationFinished() {
		if (!ObjectUtil.performNullityChecks(false, animBuilder, getAnimation(), targetController)) return false;
		boolean wasPlaying = false;
		
		if (isPlaying()) wasPlaying = true;
		
		return wasPlaying ? progress >= getLengthTicks() || targetController.getAnimationState() == AnimationState.Stopped || (progress >= getLengthTicks() && targetController.getAnimationState() == AnimationState.Running)  : false;
	}

	@Override
	public Animation getAnimation() {
		return owner.getModel().getAnimation(animName, owner);
	}
	
	public AnimationState getAnimState() {
		this.animState = targetController.getAnimationState();
		return animState;
	}

	@Override
	public AnimationBuilder getBuilder() {
		return animBuilder;
	}

	@Override
	public double getProgressTicks() {
		if (!ObjectUtil.performNullityChecks(false, animBuilder, getAnimation(), targetController)) return 0;

		return isPlaying() ? progress : 0;
	}
	
	public double getProgressTicksClient() {
		if (!ObjectUtil.performNullityChecks(false, animBuilder, getAnimation(), targetController)) return 0;
		
		double clientProgress = 0;

		for (Map.Entry<String, Variable> molangVar : GeckoLibCache.getInstance().parser.variables.entrySet()) {
			if (molangVar.getKey().equals("query.anim_time")) clientProgress = molangVar.getValue().get();	
		}

		return isPlaying() ? Math.ceil(clientProgress * 20) : 0;
	}

	@Override
	public double getLengthTicks() {
		if (!ObjectUtil.performNullityChecks(false, animBuilder, getAnimation(), targetController)) return 0;
		return Math.floor(getAnimation().animationLength);
	}

	@Override
	public void playAnimation(boolean forceAnim) {
		if (!ObjectUtil.performNullityChecks(false, animBuilder, getAnimation(), targetController)) return;

		if (targetController.getCurrentAnimation() != null && forceAnim) targetController.clearAnimationCache();
		targetController.setAnimation(getBuilder());
	}

	/**
	 * Trigger the animation stored in the <code>AnimationBuilder</code> object through the owner controller.
	 * It is recommended that you use {@link IAnimatableEntity}'s implementation instead ({@link IAnimatableEntity#playAnimation(SingletonAnimationBuilder)}),
	 * as it handles all the necessary checks should you need to trigger animations server-side. Otherwise, call this with caution (only client-side)!
	 */
	public void playAnimation() {
		playAnimation(false);
	}

	@Override
	public void stopAnimation() {
		if (!ObjectUtil.performNullityChecks(false, animBuilder, getAnimation(), targetController)) return;
		targetController.setAnimation(null);
	}

	@Override
	public void tickAnim() {
		//TODO Either action points (object, possibly) or manual action point stuff
		//TODO Server side progress
		//TODO Override animations where necessary (e.g. death, etc.)
		this.animBuilder.getRawAnimationList().removeIf((anim) -> animBuilder.getRawAnimationList().indexOf(anim) > 0);
		
		if (((Entity) owner).level.isClientSide) {
			if (isPlaying()) {
				
			}
		}

		if (!((Entity) owner).level.isClientSide) {
			if (isPlaying()) {
				progress++; // Value only handled during running animation, does not affect/get affected by transitioning
				if (hasAnimationFinished() || !isPlaying()) progress = 0;
			}
		}
	}
}
