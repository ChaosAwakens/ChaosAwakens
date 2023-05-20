package io.github.chaosawakens.api.animation;

import java.util.Map;

import javax.annotation.Nullable;

import io.github.chaosawakens.common.network.packets.c2s.AnimationDataSyncPacket;
import io.github.chaosawakens.common.util.ObjectUtil;
import io.github.chaosawakens.manager.CANetworkManager;
import net.minecraft.entity.Entity;
import net.minecraft.network.datasync.DataParameter;
import software.bernie.geckolib3.core.AnimationState;
import software.bernie.geckolib3.core.builder.Animation;
import software.bernie.geckolib3.core.builder.AnimationBuilder;
import software.bernie.geckolib3.core.builder.ILoopType;
import software.bernie.geckolib3.core.builder.ILoopType.EDefaultLoopTypes;
import software.bernie.geckolib3.core.controller.AnimationController;
import software.bernie.geckolib3.resource.GeckoLibCache;
import software.bernie.shadowed.eliotlash.mclib.math.Variable;

/**
 * Wrapper class for {@link AnimationBuilder} which only allows for the instantiation of 1 animation per instance. Also provides
 * helper methods for more flexibility.
 * <br> </br>
 * <b>NOTE: </b> Since this animation builder has to take in an {@link IAnimatableEntity} instance, it cannot be instantiated statically. 
 * This means that it will only be instantiated once every time the controller needs to play the animation, which doesn't completely 
 * eliminate all the bad practices going on, but it's most certainly better than instantiating a new instance <i>every frame</i>.
 * <br> </br>
 * - <i>"But why not just statically instantiate a normal {@link AnimationBuilder} and use it in animation predicates normally?"</i>
 * <br> </br>
 * Well, you can, but for consistency's sake, it's recommended to use this one (and its siblings) for CA entities. It comes with the benefit of
 * providing extra metadata from animations and such without actively digging into and changing Geckolib's main functionality. 
 * These can be used in a wide variety of checks, instead of being confined to using {@link DataParameter}s and throwing them all over the place
 * for <i>every action tick/frame</i>. It also significantly reduces the risk of desync through the use of separate server-side timers.
 */
public class SingletonAnimationBuilder implements IAnimationBuilder {
	private final IAnimatableEntity owner;
	@Nullable
	private AnimationController<? extends IAnimatableEntity> targetController;
	private final AnimationBuilder animBuilder;
	private final String animName;

	public SingletonAnimationBuilder(IAnimatableEntity owner, String animName) {
		this.owner = owner;
		this.targetController = owner.getMainController();
		this.animName = animName;
		animBuilder = new AnimationBuilder().addAnimation(animName);
		animBuilder.getRawAnimationList().removeIf((anim) -> animBuilder.getRawAnimationList().indexOf(anim) > 0);
	}

	public SingletonAnimationBuilder(IAnimatableEntity owner, String animName, ILoopType loopType) {
		this.owner = owner;
		this.targetController = owner.getMainController();
		this.animName = animName;
		animBuilder = new AnimationBuilder().addAnimation(animName, loopType);
		animBuilder.getRawAnimationList().removeIf((anim) -> animBuilder.getRawAnimationList().indexOf(anim) > 0);
	}

	public SingletonAnimationBuilder(IAnimatableEntity owner, String animName, int loopReps) {
		this.owner = owner;
		this.targetController = owner.getMainController();
		this.animName = animName;
		animBuilder = new AnimationBuilder().addRepeatingAnimation(animName, loopReps);
		animBuilder.getRawAnimationList().removeIf((anim) -> animBuilder.getRawAnimationList().indexOf(anim) > 0);
	}

	public SingletonAnimationBuilder setController(AnimationController<? extends IAnimatableEntity> targetController) {
		this.targetController = targetController;
		return this;
	}
	
	public AnimationController<? extends IAnimatableEntity> getController() {
		return targetController;
	}

	public IAnimatableEntity getOwner() {
		return owner;
	}
	
	public EDefaultLoopTypes getLoopType() {
		return (EDefaultLoopTypes) getAnimation().loop;
	}

	public boolean isPlaying(int id) {
		return /*ObjectUtil.performNullityChecks(false, animBuilder, getAnimation(), targetController) &&*/ targetController.getAnimationState() == AnimationState.Running && targetController.getCurrentAnimation().animationName == getAnimation().animationName;
	}

	public boolean hasAnimationFinished(int id) {
		if (((Entity) owner).getId() != id) return false;
		boolean wasPlaying = false;

		if (isPlaying(id)) wasPlaying = true;

		// Sacrificing some accuracy
		return wasPlaying ? getProgressTicks(id) >= getLengthTicks() - 2 || (targetController.getAnimationState() != AnimationState.Running || targetController.getAnimationState() == AnimationState.Stopped) : false;
	}

	@Nullable
	public Animation getAnimation() {
		return owner.getModel().getAnimation(animName, owner);
	}
	
	public AnimationBuilder getBuilder() {
		return animBuilder;
	}

	public double getProgressTicks(int id) {
		if (((Entity) owner).getId() != id) return 0;
		if (!ObjectUtil.performNullityChecks(false, animBuilder, getAnimation(), targetController)) return 0;	
		
		double progress = 0;
		
		// Get progress using molang query, pretty ick :/
		for (Map.Entry<String, Variable> molangVar : GeckoLibCache.getInstance().parser.variables.entrySet()) {
			if (molangVar.getKey().equals("query.anim_time")) progress = molangVar.getValue().get();
		}
		
		return isPlaying(id) ? Math.ceil(progress * 20) : 0;
	}

	public double getProgressSeconds(int id) {
		return getProgressTicks(id) / 20;
	}

	public double getLengthTicks() {
		if (!ObjectUtil.performNullityChecks(false, animBuilder, getAnimation(), targetController)) return 0;	

		return Math.floor(getAnimation().animationLength);
	}

	public double getLengthSeconds() {
		return getLengthTicks() / 20;
	}
	
	protected void playAnimation(boolean forceAnim) {
		if (!ObjectUtil.performNullityChecks(false, animBuilder, getAnimation(), targetController)) return;
		
		if (targetController.getCurrentAnimation() != null && forceAnim) targetController.markNeedsReload();
		targetController.setAnimation(getBuilder());
	}
	
	/**
	 * Trigger the animation stored in the <code>AnimationBuilder</code> object through the owner controller.
	 * It is recommended that you use {@link IAnimatableEntity}'s implementation instead ({@link IAnimatableEntity#playAnimation(SingletonAnimationBuilder)}),
	 * as it handles all the necessary checks should you need to trigger animations server-side. Otherwise, call this with caution (only client-side)!
	 */
	protected void playAnimation() {
		playAnimation(false);
	}
	
	public void tickAnim() {
		if (isPlaying(((Entity) owner).getId())) {
			// Sync data C2S
			if (((Entity) owner).level.isClientSide) CANetworkManager.sendPacketToServer(new AnimationDataSyncPacket(((Entity) owner).getId(), targetController.getName(), animName, getLoopType(), getProgressTicks(((Entity) owner).getId()), getController().getAnimationState()));
		}
	}
	
	public void stopAnimation() {
		if (!ObjectUtil.performNullityChecks(false, animBuilder, getAnimation(), targetController)) return;
		
		targetController.setAnimation(null);
	}
}