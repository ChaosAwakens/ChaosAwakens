package io.github.chaosawakens.client.models.entity.base;

import io.github.chaosawakens.api.animation.IAnimatableEntity;
import io.github.chaosawakens.api.animation.ICAGeoModel;
import net.minecraft.client.Minecraft;
import net.minecraft.server.MinecraftServer;
import net.minecraftforge.fml.server.ServerLifecycleHooks;
import software.bernie.geckolib3.core.event.predicate.AnimationEvent;
import software.bernie.geckolib3.core.manager.AnimationData;
import software.bernie.geckolib3.core.processor.IBone;
import software.bernie.geckolib3.model.AnimatedTickingGeoModel;
import software.bernie.geckolib3.resource.GeckoLibCache;

import javax.annotation.Nullable;
import java.util.Collections;

public abstract class ExtendedAnimatedTickingGeoModel<E extends IAnimatableEntity> extends AnimatedTickingGeoModel<E> implements ICAGeoModel {
	protected abstract boolean shouldApplyHeadRot();
	protected abstract boolean shouldApplyChildScaling();
	
	protected boolean shouldScaleHeadWithChild() {
		return false;
	}
	
	@Override
	public IBone getBodyBone() {
		return getAnimationProcessor().getBone("root");
	}
	
	@Override
	public IBone getHeadBone() {
		return getAnimationProcessor().getBone("head");
	}
	
	protected void handleFunctionalAnimTick(E owner, Integer uniqueID, @Nullable AnimationEvent<E> customPredicate) {
		AnimationData curAnimManager = owner.getFactory().getOrCreateAnimationData(uniqueID);
		MinecraftServer curServer = ServerLifecycleHooks.getCurrentServer();
		curAnimManager.shouldPlayWhilePaused = curServer != null && curServer.isDedicatedServer();
		boolean shouldTick = (!Minecraft.getInstance().isPaused() || curAnimManager.shouldPlayWhilePaused);
		double curTrackedProgress = owner.tickTimer() + Minecraft.getInstance().getFrameTime();
		
		if (curAnimManager.startTick == null) curAnimManager.startTick = curTrackedProgress;
		if (shouldTick) {
			curAnimManager.tick = curTrackedProgress;
			
			double curTick = curAnimManager.tick;
			double deltaTicks = curTick - lastGameTickTime;
			
			seekTime += deltaTicks;
			lastGameTickTime = curTick;
		}
		
		AnimationEvent<E> safeCustomPredicate = customPredicate == null ? new AnimationEvent<E>(owner, 0, 0, 0, false, Collections.emptyList()) : customPredicate;
		
		safeCustomPredicate.animationTick = seekTime;
		
		getAnimationProcessor().preAnimationSetup(safeCustomPredicate.getAnimatable(), seekTime);
		
		if (!getAnimationProcessor().getModelRendererList().isEmpty()) getAnimationProcessor().tickAnimation(owner, uniqueID, seekTime, safeCustomPredicate, GeckoLibCache.getInstance().parser, shouldCrashOnMissing);
		if (shouldTick) codeAnimations(owner, uniqueID, customPredicate);
	}
	
	@SuppressWarnings({ "rawtypes", "unchecked" })
	@Override
	public void setLivingAnimations(E entity, Integer uniqueID, @Nullable AnimationEvent customPredicate) {		
		handleFunctionalAnimTick(entity, uniqueID, customPredicate);
		
		if (shouldApplyHeadRot()) applyHeadRotations(getAnimationProcessor(), customPredicate);
		if (shouldApplyChildScaling()) setBabyScaling(getAnimationProcessor(), customPredicate, shouldScaleHeadWithChild());
	}
}
