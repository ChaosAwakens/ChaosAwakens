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
import software.bernie.geckolib3.core.builder.AnimationBuilder;
import software.bernie.geckolib3.core.controller.AnimationController;
import software.bernie.geckolib3.core.controller.AnimationController.IAnimationPredicate;
import software.bernie.geckolib3.core.event.predicate.AnimationEvent;
import software.bernie.geckolib3.core.manager.AnimationData;
import software.bernie.geckolib3.util.AnimationUtils;

public interface IAnimatableEntity extends IAnimatable, IAnimationTickable {
	
	<E extends IAnimatableEntity> WrappedAnimationController<? extends E> getMainWrappedController();
	
	/**
	 * A forced (tick) interval between each animation. Is used by default in controller creation methods in this interface, though there are overloaded methods 
	 * that allow you to specify different animation intervals per-controller if needed.
	 * @return The transitioning delay (in ticks) between animations.
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
	
	<E extends IAnimatableEntity> ObjectArrayList<WrappedAnimationController<? extends E>> getWrappedControllers();

	@Override
	default void registerControllers(AnimationData data) {
		for (WrappedAnimationController<? extends IAnimatableEntity> controller : getWrappedControllers()) {
			if (controller != null) data.addAnimationController(controller.getWrappedController());
		}
	}

	/**
	 * Iterates through {@link IAnimatableEntity#getControllers()} in order to return a controller matching the {@code name} string passed in. If no such 
	 * controller with the specified name exists, this returns null.
	 * @param name The name of the controller to find/get.
	 * @return An {@link AnimationController} with the specified name if it exists, else returns null.
	 */
	@Nullable
	default AnimationController<? extends IAnimatableEntity> getControllerByName(String name) {
		if (getWrappedControllers().isEmpty()) return null;

		ObjectArrayList<WrappedAnimationController<? extends IAnimatableEntity>> results = getWrappedControllers().stream()
				.filter((p) -> p.getName().equalsIgnoreCase(name))
				.collect(Collectors.toCollection(ObjectArrayList::new));

		if (results.isEmpty()) return null;

		return results.get(0).getWrappedController();
	}
	
	default WrappedAnimationController<? extends IAnimatableEntity> getControllerWrapperByName(String name) {
		if (getWrappedControllers().isEmpty()) return null;

		ObjectArrayList<WrappedAnimationController<? extends IAnimatableEntity>> results = getWrappedControllers().stream()
				.filter((p) -> p.getName().equalsIgnoreCase(name))
				.collect(Collectors.toCollection(ObjectArrayList::new));

		if (results.isEmpty()) return null;

		return results.get(0);
	}

	@SuppressWarnings("unchecked")
	default <E extends IAnimatableEntity> WrappedAnimationController<E> createMainMappedController(String name) {
		final WrappedAnimationController<E> resultController = new WrappedAnimationController<E>((E) this, new AnimationController<E>((E) this, name, animationInterval(), this::mainPredicate));
		getWrappedControllers().add(resultController);
		return resultController;
	}
	
	@SuppressWarnings("unchecked")
	default <E extends IAnimatableEntity> WrappedAnimationController<E> createMainMappedController(String name, int animTickInterval) {
		final WrappedAnimationController<E> resultController = new WrappedAnimationController<E>((E) this, new AnimationController<E>((E) this, name, animTickInterval, this::mainPredicate));
		getWrappedControllers().add(resultController);
		return resultController;
	}

	@SuppressWarnings("unchecked")
	default <E extends IAnimatableEntity> WrappedAnimationController<E> createMappedController(String name, IAnimationPredicate<E> animationPredicate) {
		final WrappedAnimationController<E> resultController = new WrappedAnimationController<E>((E) this, new AnimationController<E>((E) this, name, animationInterval(), animationPredicate));
		getWrappedControllers().add(resultController);
		return resultController;
	}
	
	@SuppressWarnings("unchecked")
	default <E extends IAnimatableEntity> WrappedAnimationController<E> createMappedController(String name, int animTickInterval, IAnimationPredicate<E> animationPredicate) {
		final WrappedAnimationController<E> resultController = new WrappedAnimationController<E>((E) this, new AnimationController<E>((E) this, name, animTickInterval, animationPredicate));
		getWrappedControllers().add(resultController);
		return resultController;
	}

	/**
	 * Gets the current animation that the entity is playing in its main controller. Can be null.
	 * @return The main controller's currently playing animation if present, else returns null.
	 */
	@Nullable
	default Animation getCurrentAnimation() {
		return getMainWrappedController().getCurrentAnimation();
	}

	/**
	 * Gets the current animation in a specified {@link AnimationController}. Can be null.
	 * @param targetController Controller to check current animation from.
	 * @return The specified controller's currently playing animation if present, else returns null.
	 */
	@Nullable
	default Animation getCurrentAnimation(AnimationController<? extends IAnimatableEntity> targetController) {
		return targetController.getCurrentAnimation();
	}

	default boolean isPlayingAnimation() {
		for (WrappedAnimationController<? extends IAnimatableEntity> controller : getWrappedControllers()) {
			if (controller.getCurrentAnimation() != null) return true;
		}
		return false;
	}

	default boolean isPlayingAnimation(Animation targetAnim) {
		for (WrappedAnimationController<? extends IAnimatableEntity> controller : getWrappedControllers()) {
			if (controller.getCurrentAnimation().animationName == targetAnim.animationName) return true;
		}
		return false;
	}

	default boolean isPlayingAnimation(String targetAnimName) {
		for (WrappedAnimationController<? extends IAnimatableEntity> controller : getWrappedControllers()) {
			if (controller.getCurrentAnimation().animationName == targetAnimName) return true;
		}
		return false;
	}
	
	default boolean isPlayingAnimation(SingletonAnimationBuilder targetAnim) {
		return getControllerWrapperByName(targetAnim.getWrappedController().getName()).isPlayingAnimation(targetAnim);
	}

	default <E extends IAnimatableEntity> boolean isPlayingAnimation(SingletonAnimationBuilder targetAnim, WrappedAnimationController<E> controllerToCheck) {
		if (controllerToCheck.getCurrentAnimation() == null) return false;
		return controllerToCheck.getCurrentAnimation().animationName == targetAnim.getAnimation().animationName;
	}

	default boolean isPlayingAnimationInController(AnimationController<? extends IAnimatableEntity> targetController) {
		return targetController.getCurrentAnimation() != null;
	}
	
	default boolean isPlayingAnimationInController(WrappedAnimationController<? extends IAnimatableEntity> targetController) {
		return targetController.getCurrentAnimation() != null;
	}

	default boolean isPlayingAnimationInController(String targetControllerName) {
		return isPlayingAnimationInController(getControllerWrapperByName(targetControllerName));
	}

	default boolean isPlayingAnimationInController(String animName, AnimationController<? extends IAnimatableEntity> targetController) {
		return targetController.getCurrentAnimation() != null && targetController.getCurrentAnimation().animationName == animName;
	}

	default boolean isPlayingAnimationInController(String animName, String targetControllerName) {
		return isPlayingAnimationInController(animName, getControllerByName(targetControllerName));
	}
	
	default boolean isPlayingAnimationInWrappedController(String animName, WrappedAnimationController<? extends IAnimatableEntity> targetController) {
		return targetController.getCurrentAnimation() != null && targetController.getCurrentAnimation().animationName == animName;
	}

	default boolean isPlayingAnimationInWrappedController(String animName, String targetControllerName) {
		return isPlayingAnimationInWrappedController(animName, getControllerWrapperByName(targetControllerName));
	}

	/**
	 * <b>GECKOLIB 4 IMPL</b>
	 * <br> </br>
	 * Plays an animation through the passed in animation's owner controller. Like the {@code triggerAnim} method in
	 * Geckolib 4, this can be called on either the client or the server. This does so by sending a packet to all
	 * tracking entities if triggered on the server. Otherwise it'll just play an animation normally on the client.
	 * <br> </br>
	 * No need to implement other means of triggering animations for now, so long as this exists.
	 * <br> </br>
	 * <b>IMPORTANT: </b> Just because you can trigger the animation from the server DOES NOT MEAN that animation
	 * predicates are useless! You can still use them to return an {@link AnimationState}, which will be synced and
	 * handled accordingly on the server. You still have to use {@link DataParameter}s for that, though. Animation
	 * predicates will <i>always</i> be client side.
	 * @param animation The animation to play.
	 * @param clearCache If the {@link AnimationBuilder}'s cache should be cleared
	 */
	default void playAnimation(SingletonAnimationBuilder animation, boolean clearCache) {
		if (!ObjectUtil.performNullityChecks(false, animation)) return;
		animation.getWrappedController().playAnimation(animation, false);
		
		if (!((Entity) this).level.isClientSide()) CANetworkManager.sendEntityTrackingPacket(new AnimationTriggerPacket(((Entity) this).getId(), animation.getAnimation().animationName, animation.getLoopType(), animation.getWrappedController().getName(), clearCache), (Entity) this);
	}
	
	//TODO Finish implementing this
	default void stopAnimation(SingletonAnimationBuilder animation) {
		if (!ObjectUtil.performNullityChecks(false, animation)) return;
		animation.stopAnimation();
		
		if (!((Entity) this).level.isClientSide()) CANetworkManager.sendEntityTrackingPacket(new AnimationStopPacket(((Entity) this).getId(), animation.getWrappedController().getName(), animation.getAnimation().animationName), (Entity) this);
	}

	/**
	 * Clears the animation cache, which also clears every stored animation within the entity.
	 */
	default void resetAnimationStates() {
		for (WrappedAnimationController<? extends IAnimatableEntity> controller : getWrappedControllers()) {
			controller.getWrappedController().clearAnimationCache();
		}
	}

	@Override
	default int tickTimer() {
		return ((Entity) this).tickCount;
	}

	default void tickAnims() {
		for (WrappedAnimationController<? extends IAnimatableEntity> wrapper : getWrappedControllers()) wrapper.tick();
	}

	@SuppressWarnings("unchecked")
	default <E extends Entity> IAnimatableModel<E> getModel() { //TODO Dedicated server crash fix
		return (IAnimatableModel<E>) AnimationUtils.getGeoModelForEntity((E) this);
	}
}
