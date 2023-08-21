package io.github.chaosawakens.client.models.entity.base;

import javax.annotation.Nullable;

import io.github.chaosawakens.api.animation.IAnimatableEntity;
import io.github.chaosawakens.api.animation.ICAGeoModel;
import net.minecraft.client.Minecraft;
import software.bernie.geckolib3.core.event.predicate.AnimationEvent;
import software.bernie.geckolib3.core.manager.AnimationData;
import software.bernie.geckolib3.core.processor.IBone;
import software.bernie.geckolib3.model.AnimatedTickingGeoModel;
import software.bernie.geckolib3.resource.GeckoLibCache;
import software.bernie.shadowed.eliotlash.molang.MolangParser;

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
	
	protected void handleAnimTick(E owner, AnimationData manager) {
		if (manager.startTick == null) manager.startTick = (double) (owner.tickTimer() + Minecraft.getInstance().getFrameTime());
		
		manager.tick = (owner.tickTimer() + Minecraft.getInstance().getFrameTime());
		
		double gameTick = manager.tick;
		double deltaTicks = gameTick - lastGameTickTime;
		
		seekTime += deltaTicks;
		lastGameTickTime = gameTick;
	}
	
	@SuppressWarnings("rawtypes")
	@Override
	public void setLivingAnimations(E entity, Integer uniqueID, @Nullable AnimationEvent customPredicate) {
		super.setLivingAnimations(entity, uniqueID, customPredicate);
		
		MolangParser p = GeckoLibCache.getInstance().parser;
		/*try {
			ChaosAwakens.debug("GECKOLIB ANIM PROGRESS", p.parse("query.anim_time").get());
		} catch (Exception e) {
			e.printStackTrace();
		}*/
		
		if (shouldApplyHeadRot()) applyHeadRotations(getAnimationProcessor(), customPredicate);
		if (shouldApplyChildScaling()) setBabyScaling(getAnimationProcessor(), customPredicate, shouldScaleHeadWithChild());
	}
}
