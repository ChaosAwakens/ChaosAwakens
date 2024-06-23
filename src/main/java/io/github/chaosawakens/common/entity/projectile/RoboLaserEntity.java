package io.github.chaosawakens.common.entity.projectile;

import io.github.chaosawakens.ChaosAwakens;
import io.github.chaosawakens.api.animation.IAnimatableEntity;
import io.github.chaosawakens.api.animation.IAnimationBuilder;
import io.github.chaosawakens.api.animation.SingletonAnimationBuilder;
import io.github.chaosawakens.api.animation.WrappedAnimationController;
import io.github.chaosawakens.common.registry.CAEntityTypes;
import it.unimi.dsi.fastutil.objects.ObjectArrayList;
import net.minecraft.command.arguments.EntityAnchorArgument;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.projectile.DamagingProjectileEntity;
import net.minecraft.network.IPacket;
import net.minecraft.util.DamageSource;
import net.minecraft.util.math.EntityRayTraceResult;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.util.math.vector.Vector3d;
import net.minecraft.world.Explosion;
import net.minecraft.world.World;
import net.minecraftforge.fml.network.NetworkHooks;
import software.bernie.geckolib3.core.PlayState;
import software.bernie.geckolib3.core.builder.ILoopType.EDefaultLoopTypes;
import software.bernie.geckolib3.core.event.predicate.AnimationEvent;
import software.bernie.geckolib3.core.manager.AnimationFactory;

public class RoboLaserEntity extends DamagingProjectileEntity implements IAnimatableEntity {
	AnimationFactory factory = new AnimationFactory(this);
	private final ObjectArrayList<WrappedAnimationController<RoboLaserEntity>> roboLaserControllers = new ObjectArrayList<WrappedAnimationController<RoboLaserEntity>>(1);
	private final ObjectArrayList<IAnimationBuilder> roboLaserAnimations = new ObjectArrayList<IAnimationBuilder>(1);
	private final WrappedAnimationController<RoboLaserEntity> mainController = createMainMappedController("robolasermaincontroller");
	private final SingletonAnimationBuilder idleAnim = new SingletonAnimationBuilder(this, "Idle", EDefaultLoopTypes.LOOP);
	private final SingletonAnimationBuilder deathAnim = new SingletonAnimationBuilder(this, "Death", EDefaultLoopTypes.PLAY_ONCE);
	public static final String ROBO_LASER_MDF_NAME = "robo_sniper_laser";
	private boolean hit = false;
	private boolean isDying = false;
	private float damagePower;
	private float explosivePower;
	private boolean canCauseFire = false;
	private Vector3d targetPos;
	
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
		if (hit) {
			if (!this.isDying) {
				stopAnimation(getIdleAnim());
				playAnimation(getDeathAnim(), true);
				this.isDying = true;
			}
			playAnimation(deathAnim, false);
			this.setDeltaMovement(0, 0, 0);
		}
		else
			playAnimation(getIdleAnim(), true);	
		if(getMainWrappedController().isAnimationFinished(deathAnim)) {
			this.remove();
		}
	}
	
	protected void onHit(RayTraceResult result) {
		super.onHit(result);
		hit = true;
		if(!this.level.isClientSide() && explosivePower > 0) {
			boolean flag = net.minecraftforge.event.ForgeEventFactory.getMobGriefingEvent(this.level, this.getOwner());
			level.explode((Entity)null, this.getX(), this.getY(), this.getZ(), explosivePower, canCauseFire,
					flag ? Explosion.Mode.DESTROY : Explosion.Mode.NONE);
		}
	}
	
	@Override
	protected void onHitEntity(EntityRayTraceResult pResult) {
		Entity entity = pResult.getEntity();
		entity.hurt(DamageSource.thrown(this, this.getOwner()), damagePower);
	}
	
	public void setPower(float damagePower, float explosivePower, boolean canCauseFire) {
		this.damagePower = damagePower;
		this.explosivePower = explosivePower;
		this.canCauseFire = canCauseFire;
	}
	
	@Override
	protected boolean shouldBurn() {
		return true;
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
		return 1;
	}

	@Override
	public <E extends IAnimatableEntity> PlayState mainPredicate(AnimationEvent<E> event) {
		return PlayState.CONTINUE;
	}

	@SuppressWarnings("unchecked")
	@Override
	public ObjectArrayList<WrappedAnimationController<RoboLaserEntity>> getWrappedControllers() {
		return roboLaserControllers;
	}

	@Override
	public ObjectArrayList<IAnimationBuilder> getCachedAnimations() {
		return roboLaserAnimations;
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