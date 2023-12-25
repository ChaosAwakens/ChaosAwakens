package io.github.chaosawakens.api.animation;

import io.github.chaosawakens.ChaosAwakens;
import io.github.chaosawakens.common.codec.assets.AnimationDataCodec;
import io.github.chaosawakens.common.network.packets.s2c.AnimationStopPacket;
import io.github.chaosawakens.common.network.packets.s2c.AnimationTriggerPacket;
import io.github.chaosawakens.common.registry.CADataLoaders;
import io.github.chaosawakens.common.util.ObjectUtil;
import io.github.chaosawakens.manager.CANetworkManager;
import it.unimi.dsi.fastutil.objects.ObjectArrayList;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.network.datasync.DataParameter;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.fml.loading.FMLEnvironment;
import software.bernie.geckolib3.core.*;
import software.bernie.geckolib3.core.builder.Animation;
import software.bernie.geckolib3.core.builder.AnimationBuilder;
import software.bernie.geckolib3.core.controller.AnimationController;
import software.bernie.geckolib3.core.controller.AnimationController.IAnimationPredicate;
import software.bernie.geckolib3.core.event.predicate.AnimationEvent;
import software.bernie.geckolib3.core.manager.AnimationData;
import software.bernie.geckolib3.util.AnimationUtils;

import javax.annotation.Nullable;
import java.util.List;
import java.util.Optional;

/**
 * An extended implementation of {@link IAnimatable} and {@link IAnimationTickable} which provides extended functionality to any GeoAnimatable 
 * {@link LivingEntity}. This interface must be implemented to any {@link LivingEntity} that intends to utilize the extended 
 * functionality provided by wrappers such as {@link IAnimationBuilder} instances and {@link WrappedAnimationController}.
 */
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
	<B extends IAnimationBuilder> ObjectArrayList<B> getCachedAnimations();
	
	IAnimationBuilder getIdleAnim();
	IAnimationBuilder getWalkAnim();
	IAnimationBuilder getSwimAnim();
	IAnimationBuilder getDeathAnim();

	@Override
	default void registerControllers(AnimationData data) {
		for (WrappedAnimationController<? extends IAnimatableEntity> controller : getWrappedControllers()) {
			if (controller != null) data.addAnimationController(controller.getWrappedController());
		}
	}
	
	/**
	 * Iterates through {@link #getWrappedControllers()} in order to return a controller matching the {@code name} string passed in. If no such 
	 * controller with the specified name exists, this returns null.
	 * @param name The name of the controller to find/get.
	 * @return A {@link WrappedAnimationController} with the specified name if it exists, else returns null.
	 */
	@Nullable
	default WrappedAnimationController<? extends IAnimatableEntity> getControllerWrapperByName(String name) {
		if (getWrappedControllers().isEmpty()) return null;

		Optional<WrappedAnimationController<? extends IAnimatableEntity>> filteredResult = getWrappedControllers().stream()
				.filter((curController) -> curController.getName().equalsIgnoreCase(name))
				.findFirst();

		return filteredResult.get();
	}
	
	@Nullable
	default AnimationController<? extends IAnimatableEntity> getControllerByName(String name) {
		return getControllerWrapperByName(name).getWrappedController();
	}
	
	@Nullable
	default IAnimationBuilder getCachedAnimationByName(String animName) {
		if (getCachedAnimations().isEmpty()) return null;
		
		Optional<? extends IAnimationBuilder> filteredResult = getCachedAnimations().stream()
				.filter((curAnim) -> curAnim.getAnimationName().equalsIgnoreCase(animName))
				.findFirst();
		
		return filteredResult.get();
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

	/**
	 * Checks if any of the {@link AnimationController}s in an entity are running an animation at all.
	 * @return true if any controllers (stored in {@link #getWrappedControllers()}) are actively playing an animation at all, else returns false.
	 */
	default boolean isPlayingAnimation() {
		for (WrappedAnimationController<? extends IAnimatableEntity> controller : getWrappedControllers()) {
			if (controller.getCurrentAnimation() != null) return true;
		}
		return false;
	}
	
	/**
	 * Checks if a certain animation (by name) is playing in any of the controllers stored in {@link #getWrappedControllers()}.
	 * @param targetAnimName The target animation name to check for.
	 * @return true if the specified animation (by name) is playing in any of the controllers stored in {@link #getWrappedControllers()}, else returns false.
	 */
	default boolean isPlayingAnimation(String targetAnimName) {
		for (WrappedAnimationController<? extends IAnimatableEntity> controller : getWrappedControllers()) {
			if (controller.getCurrentAnimation().animationName == targetAnimName) return true;
		}
		return false;
	}

	/**
	 * Overloaded method for {@link #isPlayingAnimation(String)}.
	 * @param targetAnim The target animation to check for.
	 * @return {@link #isPlayingAnimation(String)}.
	 */
	default boolean isPlayingAnimation(Animation targetAnim) {
		return isPlayingAnimation(targetAnim.animationName);
	}
	
	/**
	 * Uses the target {@link IAnimationBuilder}'s {@link WrappedAnimationController} to check if it's playing the specified 
	 * animation (via {@link WrappedAnimationController#isPlayingAnimation(IAnimationBuilder)}).
	 * @param targetAnim The target animation to check the controller of for matching.
	 * @return true if the specified {@link IAnimationBuilder}'s {@link WrappedAnimationController} is playing the specified animation, 
	 * else returns false.
	 */
	default boolean isPlayingAnimation(IAnimationBuilder targetAnim) {
		return getControllerWrapperByName(targetAnim.getWrappedController().getName()).isPlayingAnimation(targetAnim);
	}
	
	default <E extends IAnimatableEntity> boolean isPlayingAnimation(String targetAnimName, WrappedAnimationController<E> controllerToCheck) {
		return controllerToCheck.isPlayingAnimation(targetAnimName);
	}

	default <E extends IAnimatableEntity> boolean isPlayingAnimation(IAnimationBuilder targetAnim, WrappedAnimationController<E> controllerToCheck) {
		return controllerToCheck.isPlayingAnimation(targetAnim);
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
	 * Plays an animation through the passed in {@link IAnimationBuilder}'s owner {@link WrappedAnimationController}. Like the {@code triggerAnim} 
	 * method in Geckolib 4, this can be called on either the client or the server. This does so by sending a packet to all
	 * tracking entities if triggered on the server. Otherwise, it'll just play an animation normally on the client.
	 * <br> </br>
	 * No need to implement other means of triggering animations for now, so long as this exists.
	 * <br> </br>
	 * <b>IMPORTANT: </b> Just because you can trigger the animation from the server DOES NOT MEAN that animation
	 * predicates are useless! You can still use them to return an {@link AnimationState}, which will be synced and
	 * handled accordingly on the server. You still have to use {@link DataParameter}s for syncing, though. Animation
	 * predicates will <i>always</i> be client side.
	 * @param animation The animation to play.
	 * @param clearCache If the {@link IAnimationBuilder}'s {@link AnimationBuilder} cache should be cleared
	 */
	default void playAnimation(IAnimationBuilder animation, boolean clearCache) {
		if (!ObjectUtil.performNullityChecks(false, animation)) return;
		animation.getWrappedController().playAnimation(animation, clearCache);
		
		if (!((Entity) this).level.isClientSide())
			CANetworkManager.sendEntityTrackingPacket(new AnimationTriggerPacket(((Entity) this).getId(),
					animation.getAnimationName(), animation.getLoopType(),
					animation.getWrappedController().getName(), clearCache), (Entity) this);
	}
	
	default void stopAnimation(IAnimationBuilder animation) {
		if (!ObjectUtil.performNullityChecks(false, animation)) return;
		animation.getWrappedController().stopAnimation(animation);
		
		if (!((Entity) this).level.isClientSide()) CANetworkManager.sendEntityTrackingPacket(new AnimationStopPacket(((Entity) this).getId(), animation.getWrappedController().getName(), animation.getAnimationName()), (Entity) this);
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
		getWrappedControllers().forEach(targetWrappedController -> targetWrappedController.tick());
	}

	@SuppressWarnings("unchecked")
	default <E extends Entity> IAnimatableModel<E> getModel() {
		return !FMLEnvironment.dist.equals(Dist.CLIENT) ? null : (IAnimatableModel<E>) AnimationUtils.getGeoModelForEntity((E) this);
	}

	String getOwnerMDFileName();

	default List<AnimationDataCodec.AnimationMetadataCodec> getSidedAnimationData() {
		return CADataLoaders.ANIMATION_DATA.getData(ChaosAwakens.prefix(getOwnerMDFileName() + ".animation")).getStoredAnims();
	}

	default Optional<AnimationDataCodec.AnimationMetadataCodec> getSidedMetadataFor(String animationName) {
		List<AnimationDataCodec.AnimationMetadataCodec> storedAnims = getSidedAnimationData();

		return Optional.ofNullable(storedAnims.stream().filter(anim -> anim.getAnimationName().equalsIgnoreCase(animationName)).findFirst().orElse(null));
	}
}
