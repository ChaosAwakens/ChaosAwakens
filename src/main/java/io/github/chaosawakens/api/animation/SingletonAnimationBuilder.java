package io.github.chaosawakens.api.animation;

import java.util.Map;

import javax.annotation.Nullable;

import io.github.chaosawakens.common.network.packets.c2s.AnimationDataSyncPacket;
import io.github.chaosawakens.common.util.ObjectUtil;
import io.github.chaosawakens.manager.CANetworkManager;
import net.minecraft.entity.Entity;
import software.bernie.geckolib3.core.AnimationState;
import software.bernie.geckolib3.core.builder.Animation;
import software.bernie.geckolib3.core.builder.AnimationBuilder;
import software.bernie.geckolib3.core.builder.ILoopType;
import software.bernie.geckolib3.core.builder.ILoopType.EDefaultLoopTypes;
import software.bernie.geckolib3.core.controller.AnimationController;
import software.bernie.geckolib3.resource.GeckoLibCache;
import software.bernie.shadowed.eliotlash.mclib.math.Variable;

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
		if (!this.owner.getAnimations().contains(this)) this.owner.getAnimations().add(this);
	}

	public SingletonAnimationBuilder(IAnimatableEntity owner, String animName, ILoopType loopType) {
		this.owner = owner;
		this.targetController = owner.getMainController();
		this.animName = animName;
		this.loopType = loopType;
		this.animBuilder = new AnimationBuilder().addAnimation(animName, loopType);
		this.animBuilder.getRawAnimationList().removeIf((anim) -> animBuilder.getRawAnimationList().indexOf(anim) > 0);
		this.animState = targetController.getAnimationState();
		if (!this.owner.getAnimations().contains(this)) this.owner.getAnimations().add(this);
	}

	public SingletonAnimationBuilder(IAnimatableEntity owner, String animName, int loopReps) {
		this.owner = owner;
		this.targetController = owner.getMainController();
		this.animName = animName;
		this.animBuilder = new AnimationBuilder().addRepeatingAnimation(animName, loopReps);
		this.animBuilder.getRawAnimationList().removeIf((anim) -> animBuilder.getRawAnimationList().indexOf(anim) > 0);
		this.animState = targetController.getAnimationState();
		if (!this.owner.getAnimations().contains(this)) this.owner.getAnimations().add(this);
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
		return ObjectUtil.performNullityChecks(false, animBuilder, getAnimation(), targetController) && targetController.getAnimationState() == AnimationState.Running && owner.isPlayingAnimation(this, targetController);
	}

	@Override
	public boolean hasAnimationFinished() {
		if (!ObjectUtil.performNullityChecks(false, animBuilder, getAnimation(), targetController)) return false;
		boolean wasPlaying = false;
		
		if (isPlaying()) wasPlaying = true;
		
		return wasPlaying ? getProgressTicks() >= getLengthTicks() || targetController.getAnimationState() != AnimationState.Running : false;
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

		for (Map.Entry<String, Variable> molangVar : GeckoLibCache.getInstance().parser.variables.entrySet()) {
			if (molangVar.getKey().equals("query.anim_time")) progress = molangVar.getValue().get();	
		}

		return isPlaying() ? Math.ceil(progress * 20) : 0;
	}

	@Override
	public double getLengthTicks() {
		if (!ObjectUtil.performNullityChecks(false, animBuilder, getAnimation(), targetController)) return 0;
		return Math.floor(getAnimation().animationLength);
	}

	@Override
	public void playAnimation(boolean forceAnim) {
		if (!ObjectUtil.performNullityChecks(false, animBuilder, getAnimation(), targetController)) return;

		if (targetController.getCurrentAnimation() != null && forceAnim) targetController.markNeedsReload();
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
		// C2S (Sync data to server)
		if (((Entity) owner).level.isClientSide) {
			if (isPlaying()) {
				CANetworkManager.sendPacketToServer(new AnimationDataSyncPacket(((Entity) owner).getId(), targetController.getName(), animName, getLoopType(), getProgressTicks(), targetController.getAnimationState()));
			}
		}

		// S2C (Impose data on client, accounting for some edge cases such as server lag)
		if (!((Entity) owner).level.isClientSide) {
			if (isPlaying()) {

			}
		}
	}

	@Override
	public boolean equals(Object obj) {
/*		if (!ObjectUtil.performNullityChecks(false, this, obj)) return false;

		SingletonAnimationBuilder other = (SingletonAnimationBuilder) obj;
		
		if (!ObjectUtil.performNullityChecks(false, this, other)) return false;

		if (getAnimation() == other.getAnimation()) return true;
		else if (getAnimation().animationName == other.getAnimation().animationName) return true;
		else */return super.equals(obj);
	}
}
