package io.github.chaosawakens.api.animation;

import java.util.stream.Collectors;

import javax.annotation.Nullable;

import io.github.chaosawakens.common.network.packets.s2c.AnimationStopPacket;
import io.github.chaosawakens.common.network.packets.s2c.AnimationTriggerPacket;
import io.github.chaosawakens.common.util.ObjectUtil;
import io.github.chaosawakens.manager.CANetworkManager;
import it.unimi.dsi.fastutil.objects.ObjectArrayList;
import net.minecraft.entity.Entity;
import net.minecraft.network.datasync.DataParameter;
import software.bernie.geckolib3.core.AnimationState;
import software.bernie.geckolib3.core.IAnimatable;
import software.bernie.geckolib3.core.IAnimatableModel;
import software.bernie.geckolib3.core.IAnimationTickable;
import software.bernie.geckolib3.core.PlayState;
import software.bernie.geckolib3.core.builder.Animation;
import software.bernie.geckolib3.core.controller.AnimationController;
import software.bernie.geckolib3.core.controller.AnimationController.IAnimationPredicate;
import software.bernie.geckolib3.core.event.predicate.AnimationEvent;
import software.bernie.geckolib3.core.manager.AnimationData;
import software.bernie.geckolib3.util.AnimationUtils;

public interface IAnimatableEntity extends IAnimatable, IAnimationTickable {

	/**
	 * The main animation controller attached to the entity. Can be used to set animations outside of the <code>predicate(AnimationEvent<E> event)</code>
	 * method.
	 * @return The animation controller attached to the entity, CANNOT BE NULL!
	 */
	AnimationController<? extends IAnimatableEntity> getMainController();

	/**
	 * A forced (tick) interval between each animation.
	 * @return The delay between animations.
	 */
	int animationInterval();

	/**
	 * This is the main predicate, in which all the animations are handled conditionally.
	 * If you need to, you can make more predicates for your use case(s).
	 * @param <E> IAnimatable type parameter.
	 * @param Event the animation event.
	 * @return a PlayState for each set animation.
	 */
	<E extends IAnimatableEntity> PlayState mainPredicate(AnimationEvent<E> event);

	<E extends IAnimatableEntity> ObjectArrayList<AnimationController<? extends E>> getControllers();

	default <E extends IAnimationBuilder> ObjectArrayList<E> getAnimations() { return null; }

	@Override
	default void registerControllers(AnimationData data) {
		for (AnimationController<? extends IAnimatableEntity> controller : getControllers()) {
			if (controller != null) data.addAnimationController(controller);
		}
	}

	@Nullable
	default AnimationController<? extends IAnimatableEntity> getControllerByName(String name) {
		if (getControllers().isEmpty()) return null;

		ObjectArrayList<AnimationController<? extends IAnimatableEntity>> results = getControllers().stream()
				.filter((p) -> p.getName().equalsIgnoreCase(name))
				.collect(Collectors.toCollection(ObjectArrayList::new));

		if (results.isEmpty()) return null;

		return (AnimationController<? extends IAnimatableEntity>) results.get(0);
	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	default <E extends IAnimatableEntity> AnimationController<E> createMainMappedController(String name) {
		final AnimationController<E> resultController = new AnimationController(this, name, animationInterval(), this::mainPredicate);
		getControllers().add(resultController);
		return resultController;
	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	default <E extends IAnimatableEntity> AnimationController<E> createMappedController(String name, IAnimationPredicate<? extends IAnimatableEntity> animationPredicate) {
		final AnimationController<E> resultController = new AnimationController(this, name, animationInterval(), animationPredicate);
		getControllers().add(resultController);
		return resultController;
	}

	/**
	 * Gets the current animation that the entity is playing in its main controller. Can be null.
	 */
	@Nullable
	default Animation getCurrentAnimation() {
		return getMainController().getCurrentAnimation();
	}

	@Nullable
	default Animation getCurrentAnimation(AnimationController<? extends IAnimatableEntity> targetController) {
		return targetController.getCurrentAnimation();
	}
	
	@Nullable
	default IAnimationBuilder getCurrentAnimationAsCB(AnimationController<? extends IAnimatableEntity> targetController) {
		Animation curAnim = targetController.getCurrentAnimation();
		
		for (IAnimationBuilder curAnimB : getAnimations()) {
			if (curAnim.animationName == curAnimB.getAnimation().animationName) return curAnimB;
		}
		
		return null;
	}

	@Nullable
	default IAnimationBuilder getCurrentAnimationAsCB(String targetControllerName) {
		return getCurrentAnimationAsCB(getControllerByName(targetControllerName));
	}

	@Nullable
	default SingletonAnimationBuilder getCurrentAnimationAsSAB(AnimationController<? extends IAnimatableEntity> targetController) {
		for (IAnimationBuilder curAnimB : getAnimations()) {
			if (curAnimB instanceof SingletonAnimationBuilder) return (SingletonAnimationBuilder) getCurrentAnimationAsCB(targetController);
		}
		
		return null;
	}

	@Nullable
	default SingletonAnimationBuilder getCurrentAnimationAsSAB(String targetControllerName) {
		return getCurrentAnimationAsSAB(getControllerByName(targetControllerName));
	}

	default boolean isPlayingAnimation() {
		for (AnimationController<? extends IAnimatableEntity> controller : getControllers()) {
			if (controller.getCurrentAnimation() != null) return true;
		}

		return false;
	}

	default boolean isPlayingAnimation(Animation targetAnim) {
		for (AnimationController<? extends IAnimatableEntity> controller : getControllers()) {
			if (controller.getCurrentAnimation().animationName == targetAnim.animationName) return true;
		}

		return false;
	}

	default boolean isPlayingAnimation(SingletonAnimationBuilder targetAnim) {
		return targetAnim.isPlaying();
	}

	default <E extends IAnimatableEntity> boolean isPlayingAnimation(SingletonAnimationBuilder targetAnim, AnimationController<E> controllerToCheck) {
		return controllerToCheck.getCurrentAnimation().animationName == targetAnim.getAnimation().animationName;
	}

	default boolean isPlayingAnimation(String targetAnimName) {
		for (AnimationController<? extends IAnimatableEntity> controller : getControllers()) {
			if (controller.getCurrentAnimation().animationName == targetAnimName) return true;
		}

		return false;
	}

	default boolean isPlayingAnimationInController(AnimationController<? extends IAnimatableEntity> targetController) {
		return targetController.getCurrentAnimation() != null;
	}

	default boolean isPlayingAnimationInController(String targetControllerName) {
		return isPlayingAnimationInController(getControllerByName(targetControllerName));
	}

	default boolean isPlayingAnimationInController(String animName, AnimationController<? extends IAnimatableEntity> targetController) {
		return targetController.getCurrentAnimation() != null && targetController.getCurrentAnimation().animationName == animName;
	}

	default boolean isPlayingAnimationInController(String animName, String targetControllerName) {
		return isPlayingAnimationInController(animName, getControllerByName(targetControllerName));
	}

	/**
	 * <b>GECKOLIB 4 IMPL</b>
	 * <br> </br>
	 * Plays an animation through the passed in animation's owner controller. Like the {@code triggerAnim} method in Geckolib 4, this can be
	 * called on either the client or the server. This does so by sending a packet to all tracking entities if triggered on the server. 
	 * Otherwise it'll just play an animation normally on the client.
	 * <br> </br>
	 * No need to implement other means of triggering animations for now, so long as this exists.
	 * <br> </br>
	 * <b>IMPORTANT: </b> Just because you can trigger the animation from the server DOES NOT MEAN that animation predicates are useless! You can still 
	 * use them to return an {@link AnimationState}, which will be synced and handled accordingly on the server. You still have to use {@link DataParameter}s
	 * for that, though. Animation predicates will <i>always</i> be client side.
	 * @param animation The animation to play.
	 */
	default void playAnimation(SingletonAnimationBuilder animation) {
		if (!ObjectUtil.performNullityChecks(false, animation)) return;

		if (((Entity) this).level.isClientSide) {
			animation.playAnimation();
		} else {
			CANetworkManager.sendEntityTrackingPacket(new AnimationTriggerPacket(((Entity) this).getId(), animation.getAnimation().animationName, animation.getLoopType(), animation.getController().getName()), (Entity) this);
		}
	}

	default void stopAnimation(SingletonAnimationBuilder animation) {
		if (!ObjectUtil.performNullityChecks(false, animation)) return;

		if (((Entity) this).level.isClientSide) {
			animation.stopAnimation();
		} else {
			CANetworkManager.sendEntityTrackingPacket(new AnimationStopPacket(((Entity) this).getId(), animation.getAnimation().animationName), (Entity) this);
		}
	}

	/**
	 * Clears the animation cache, which also clears every stored animation within the entity.
	 */
	default void resetAnimationStates() {
		for (AnimationController<? extends IAnimatableEntity> controller : getControllers()) {
			controller.clearAnimationCache();
		}
	}

	@Override
	default int tickTimer() {
		return ((Entity) this).tickCount;
	}

	default void tickAnims() {
		for (IAnimationBuilder anim : getAnimations()) {
			if (anim.isPlaying()) anim.tickAnim();
		}
	}

	@SuppressWarnings("unchecked")
	default <E extends Entity> IAnimatableModel<E> getModel() {
		return (IAnimatableModel<E>) AnimationUtils.getGeoModelForEntity((E) this);
	}
}
