package io.github.chaosawakens.common.entity.misc;

import io.github.chaosawakens.common.registry.CAEntityTypes;
import net.minecraft.entity.EntitySize;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.Pose;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.World;
import software.bernie.geckolib3.core.IAnimatable;
import software.bernie.geckolib3.core.IAnimationTickable;
import software.bernie.geckolib3.core.PlayState;
import software.bernie.geckolib3.core.builder.AnimationBuilder;
import software.bernie.geckolib3.core.builder.ILoopType.EDefaultLoopTypes;
import software.bernie.geckolib3.core.controller.AnimationController;
import software.bernie.geckolib3.core.event.predicate.AnimationEvent;
import software.bernie.geckolib3.core.manager.AnimationData;
import software.bernie.geckolib3.core.manager.AnimationFactory;

import java.util.function.Consumer;

public class JefferyShockwaveEntity extends AOEHitboxEntity implements IAnimatable, IAnimationTickable {
	private final AnimationFactory factory = new AnimationFactory(this);
	private final AnimationController<JefferyShockwaveEntity> mainController = new AnimationController<JefferyShockwaveEntity>(this, "jefferyshockwavemaincontroller", 0, this::mainPredicate);
	private static final AnimationBuilder EXPANSION_ANIM = new AnimationBuilder().addAnimation("Expand", EDefaultLoopTypes.PLAY_ONCE);

	public JefferyShockwaveEntity(EntityType<?> pType, World pLevel) {
		super(pType, pLevel);
	}
	
	public JefferyShockwaveEntity(World world, BlockPos spawnPos, float maxRad, float expSpeed, int maxAge, int execInterv, Consumer<LivingEntity> actionOnInters) {
		super(CAEntityTypes.JEFFERY_SHOCKWAVE.get(), world, spawnPos, maxRad, expSpeed, maxAge, execInterv, actionOnInters);
	}
	
	public <E extends IAnimatable> PlayState mainPredicate(AnimationEvent<E> event) {
		if (tickCount > 1 && tickCount < getMaxAge()) {
			event.getController().setAnimation(EXPANSION_ANIM);
			event.getController().setAnimationSpeed(1.6D);
			return PlayState.CONTINUE;
		}
		return PlayState.CONTINUE;
	}

	@Override
	public EntitySize getDimensions(Pose pPose) {
		return EntitySize.scalable(getRadius() * 2.0F, getRadius() * 0.44F);
	}

	@Override
	public void tick() {
		super.tick();

		if (tickCount < 10) yRot = MathHelper.lerp(1, yRot, yRot + 2.15F);
	}

	@Override
	public void registerControllers(AnimationData data) {
		data.addAnimationController(mainController);
	}

	@Override
	public AnimationFactory getFactory() {
		return factory;
	}

	@Override
	public int tickTimer() {
		return tickCount;
	}
}