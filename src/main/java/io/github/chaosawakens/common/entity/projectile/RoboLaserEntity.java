package io.github.chaosawakens.common.entity.projectile;

import io.github.chaosawakens.api.animation.IAnimatableEntity;
import io.github.chaosawakens.api.animation.IAnimationBuilder;
import io.github.chaosawakens.api.animation.SingletonAnimationBuilder;
import io.github.chaosawakens.api.animation.WrappedAnimationController;
import io.github.chaosawakens.common.registry.CAEntityTypes;
import it.unimi.dsi.fastutil.objects.ObjectArrayList;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.projectile.DamagingProjectileEntity;
import net.minecraft.network.IPacket;
import net.minecraft.network.play.server.SSpawnObjectPacket;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.util.math.vector.Vector3d;
import net.minecraft.world.World;
import net.minecraftforge.fml.network.NetworkHooks;
import software.bernie.geckolib3.core.PlayState;
import software.bernie.geckolib3.core.builder.ILoopType.EDefaultLoopTypes;
import software.bernie.geckolib3.core.event.predicate.AnimationEvent;
import software.bernie.geckolib3.core.manager.AnimationFactory;

public class RoboLaserEntity extends DamagingProjectileEntity implements IAnimatableEntity {
	AnimationFactory factory = new AnimationFactory(this);
	private final ObjectArrayList<WrappedAnimationController<RoboLaserEntity>> roboSniperControllers = new ObjectArrayList<WrappedAnimationController<RoboLaserEntity>>(1);
	private final ObjectArrayList<IAnimationBuilder> roboSniperAnimations = new ObjectArrayList<IAnimationBuilder>(1);
	private final WrappedAnimationController<RoboLaserEntity> mainController = createMainMappedController("robolasermaincontroller");
	private final SingletonAnimationBuilder idleAnim = new SingletonAnimationBuilder(this, "idle", EDefaultLoopTypes.LOOP);
	private final SingletonAnimationBuilder deathAnim = new SingletonAnimationBuilder(this, "death", EDefaultLoopTypes.PLAY_ONCE);
	public static final String ROBO_LASER_MDF_NAME = "robo_sniper_laser";
	private boolean hit;
	
	public RoboLaserEntity(EntityType<? extends RoboLaserEntity> type, World level) {
	      super(type, level);
	   }
	
	public RoboLaserEntity(World pLevel, LivingEntity pShooter, double pOffsetX, double pOffsetY, double pOffsetZ) {
		super(CAEntityTypes.ROBO_LASER.get(), pShooter, pOffsetX, pOffsetY, pOffsetZ, pLevel);
	}
	
	@Override
	public void tick() {
		tickAnims();
		super.tick();
//		if(hit)
//			playAnimation(deathAnim, true);
//		else
			playAnimation(idleAnim, true);
//		if(deathAnim.hasAnimationFinished())
//			this.remove();
	}
	
	protected void onHit(RayTraceResult result) {
		super.onHit(result);
		hit = true;
	}

	@Override
	public AnimationFactory getFactory() {
		return factory;
	}

	@Override
	public WrappedAnimationController<RoboLaserEntity> getMainWrappedController() {
		return mainController;
	}

	@Override
	public int animationInterval() {
		return 0;
	}

	@Override
	public <E extends IAnimatableEntity> PlayState mainPredicate(AnimationEvent<E> event) {
		return PlayState.CONTINUE;
	}

	@SuppressWarnings("unchecked")
	@Override
	public ObjectArrayList<WrappedAnimationController<RoboLaserEntity>> getWrappedControllers() {
		return roboSniperControllers;
	}

	@Override
	public ObjectArrayList<IAnimationBuilder> getCachedAnimations() {
		return roboSniperAnimations;
	}

	@Override
	public IAnimationBuilder getIdleAnim() {
		return idleAnim;
	}

	@Override
	public IAnimationBuilder getWalkAnim() {
		return idleAnim;
	}
	
	@Override
	public IAnimationBuilder getSwimAnim() {
		return idleAnim;
	}

	@Override
	public IAnimationBuilder getDeathAnim() {
		return deathAnim;
	}

	@Override
	public String getOwnerMDFileName() {
		return ROBO_LASER_MDF_NAME;
	}
	
	@Override
	public IPacket<?> getAddEntityPacket() {
		return NetworkHooks.getEntitySpawningPacket(this);
	}
}