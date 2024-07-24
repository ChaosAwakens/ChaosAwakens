package io.github.chaosawakens.common.entity.projectile;

import io.github.chaosawakens.api.animation.IAnimatableEntity;
import io.github.chaosawakens.api.animation.IAnimationBuilder;
import io.github.chaosawakens.api.animation.SingletonAnimationBuilder;
import io.github.chaosawakens.api.animation.WrappedAnimationController;
import io.github.chaosawakens.common.registry.CAEntityTypes;
import io.github.chaosawakens.common.registry.CASoundEvents;
import it.unimi.dsi.fastutil.objects.ObjectArrayList;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.projectile.DamagingProjectileEntity;
import net.minecraft.network.IPacket;
import net.minecraft.network.datasync.DataParameter;
import net.minecraft.network.datasync.DataSerializers;
import net.minecraft.network.datasync.EntityDataManager;
import net.minecraft.util.DamageSource;
import net.minecraft.util.math.EntityRayTraceResult;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.world.Explosion;
import net.minecraft.world.World;
import net.minecraftforge.event.ForgeEventFactory;
import net.minecraftforge.fml.network.NetworkHooks;
import software.bernie.geckolib3.core.PlayState;
import software.bernie.geckolib3.core.builder.ILoopType;
import software.bernie.geckolib3.core.event.predicate.AnimationEvent;
import software.bernie.geckolib3.core.manager.AnimationFactory;

public class RoboRayEntity extends DamagingProjectileEntity implements IAnimatableEntity {
	private final AnimationFactory factory = new AnimationFactory(this);
	private final ObjectArrayList<WrappedAnimationController<RoboRayEntity>> roboRayControllers = new ObjectArrayList<WrappedAnimationController<RoboRayEntity>>(1);
	private final ObjectArrayList<IAnimationBuilder> roboRayAnimations = new ObjectArrayList<IAnimationBuilder>(3);
	private static final DataParameter<Boolean> SHOT = EntityDataManager.defineId(RoboRayEntity.class, DataSerializers.BOOLEAN);
	private static final DataParameter<Boolean> HAS_HIT = EntityDataManager.defineId(RoboRayEntity.class, DataSerializers.BOOLEAN);
	private static final DataParameter<Boolean> DYING = EntityDataManager.defineId(RoboRayEntity.class, DataSerializers.BOOLEAN);
	private static final DataParameter<Boolean> CAUSE_FIRE = EntityDataManager.defineId(RoboRayEntity.class, DataSerializers.BOOLEAN);
	private static final DataParameter<Float> DAMAGE_POWER = EntityDataManager.defineId(RoboRayEntity.class, DataSerializers.FLOAT);
	private static final DataParameter<Float> EXPLOSIVE_POWER = EntityDataManager.defineId(RoboRayEntity.class, DataSerializers.FLOAT);
	private final WrappedAnimationController<RoboRayEntity> mainController = createMainMappedController("roborayrmaincontroller");
	private final SingletonAnimationBuilder idleAnim = new SingletonAnimationBuilder(this, "Inept", ILoopType.EDefaultLoopTypes.LOOP);
	private final SingletonAnimationBuilder activeAnim = new SingletonAnimationBuilder(this, "Active", ILoopType.EDefaultLoopTypes.LOOP);
	private final SingletonAnimationBuilder deathAnim = new SingletonAnimationBuilder(this, "Death", ILoopType.EDefaultLoopTypes.PLAY_ONCE);
	public static final String ROBO_RAY_MDF_NAME = "robo_ray";

	public RoboRayEntity(EntityType<? extends RoboRayEntity> type, World world) {
		super(type, world);
	}

	public RoboRayEntity(World pLevel, LivingEntity pShooter, double pOffsetX, double pOffsetY, double pOffsetZ) {
		super(CAEntityTypes.ROBO_RAY.get(), pShooter, pOffsetX, pOffsetY, pOffsetZ, pLevel);
		float tempAngle = (float) (MathHelper.wrapDegrees(MathHelper.atan2(zPower, xPower) * 180.0 / Math.PI) - 90.0f);
		if (tempAngle <= -90.0f) {
			this.yRotO = -90.0f - tempAngle;
		} else if(tempAngle > 90.0f) {
			this.yRotO = 270.0f - tempAngle;
		} else {
			this.yRotO = -90.0f - tempAngle;
		}
		this.yRot = this.yRotO;
	}
	
	@Override
	public void tick() {
		tickAnims();
		super.tick();
		
		if (hasHit() || tickCount >= 800) {
			if (!isDying()) {
				stopAnimation(getWalkAnim());
				playAnimation(getDeathAnim(), true);
				
				setDying(true);
			}
			
			playAnimation(deathAnim, false);
			setDeltaMovement(0, 0, 0);
		} else playAnimation(getWalkAnim(), false);
		
		if (getMainWrappedController().isAnimationFinished(deathAnim)) remove();
		if (tickCount <= 1) playSound(CASoundEvents.ROBO_RAY_TRAVEL.get(), 1.0F, 1.0F);
	}
	
	@Override
	protected void onHit(RayTraceResult pResult) {
		super.onHit(pResult);
		setHasHit(true);

		playSound(CASoundEvents.ROBO_RAY_HIT.get(), 1.0F, 1.0F);
		
		if (getExplosivePower() > 0.0F) level.explode(this, getX(), getY(), getZ(), getExplosivePower(), fireOnHit(), ForgeEventFactory.getMobGriefingEvent(level, getOwner()) ? Explosion.Mode.DESTROY : Explosion.Mode.NONE);
	}
	
	@Override
	protected void onHitEntity(EntityRayTraceResult pResult) {
		super.onHitEntity(pResult);
		
		Entity targetEntity = pResult.getEntity();
		
		if (targetEntity != null && targetEntity.isAlive()) targetEntity.hurt(DamageSource.thrown(this, getOwner()), getDamagePower());
	}

	@Override
	public WrappedAnimationController<RoboRayEntity> getMainWrappedController() {
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

	@Override
	protected void defineSynchedData() {
		super.defineSynchedData();
		this.entityData.define(SHOT, false);
		this.entityData.define(HAS_HIT, false);
		this.entityData.define(DYING, false);
		this.entityData.define(CAUSE_FIRE, false);
		this.entityData.define(DAMAGE_POWER, 0.0F);
		this.entityData.define(EXPLOSIVE_POWER, 0.0F);
	}

	public boolean hasBeenShot() {
		return this.entityData.get(SHOT);
	}

	public void setShot(boolean shot) {
		this.entityData.set(SHOT, shot);
	}

	public boolean hasHit() {
		return this.entityData.get(HAS_HIT);
	}

	public void setHasHit(boolean hasHit) {
		this.entityData.set(HAS_HIT, hasHit);
	}

	public boolean isDying() {
		return this.entityData.get(DYING);
	}

	public void setDying(boolean dying) {
		this.entityData.set(DYING, dying);
	}
	
    public boolean fireOnHit() {
        return this.entityData.get(CAUSE_FIRE);
    }

	public void setFireOnHit(boolean fireOnHit) {
		this.entityData.set(CAUSE_FIRE, fireOnHit);
	}

	public float getDamagePower() {
		return this.entityData.get(DAMAGE_POWER);
	}

	public void setDamagePower(float damage) {
		this.entityData.set(DAMAGE_POWER, damage);
	}

	public float getExplosivePower() {
		return this.entityData.get(EXPLOSIVE_POWER);
	}

	public void setExplosivePower(float power) {
		this.entityData.set(EXPLOSIVE_POWER, power);
	}

	public void setPower(float damagePower, float explosivePower, boolean canCauseFire) {
		setDamagePower(damagePower);
		setExplosivePower(explosivePower);
		setFireOnHit(canCauseFire);
	}
	
	public void changeSpeed(double factor) {
		changeSpeed(factor, factor, factor);
	}

	public void changeSpeed(double xFactor, double yFactor, double zFactor) {
		this.xPower *= xFactor;
		this.yPower *= yFactor;
		this.zPower *= zFactor;
	}

	@Override
	protected float getInertia() {
		return !hasBeenShot() || !isAlive() ? 0.0F : super.getInertia() * 2.0F;
	}

	@Override
	public boolean displayFireAnimation() {
		return false;
	}

	@SuppressWarnings("unchecked")
	@Override
	public ObjectArrayList<WrappedAnimationController<RoboRayEntity>> getWrappedControllers() {
		return roboRayControllers;
	}

	@Override
	public ObjectArrayList<IAnimationBuilder> getCachedAnimations() {
		return roboRayAnimations;
	}

	@Override
	public IAnimationBuilder getIdleAnim() {
		return idleAnim;
	}

	@Override
	public IAnimationBuilder getWalkAnim() {
		return activeAnim;
	}

	@Override
	public IAnimationBuilder getSwimAnim() {
		return activeAnim;
	}

	@Override
	public IAnimationBuilder getDeathAnim() {
		return deathAnim;
	}

	@Override
	public String getOwnerMDFileName() {
		return ROBO_RAY_MDF_NAME;
	}

	@Override
	public AnimationFactory getFactory() {
		return factory;
	}

	protected void handleBaseAnimations() {
		if (deathAnim.hasAnimationFinished()) remove();

		if (!level.isClientSide) {
			if ((hasHit() || tickCount >= 400) && !isPlayingAnimation(deathAnim)) {
				stopAnimation(getWalkAnim());
				stopAnimation(getIdleAnim());
				playAnimation(getDeathAnim(), true);
			} else if (hasBeenShot() && !hasHit()) {
				if (isPlayingAnimation(getIdleAnim())) stopAnimation(getIdleAnim());
				playAnimation(getWalkAnim(), true);
			} else if (!hasHit()) playAnimation(getIdleAnim(), true);
		}
	}
    
	@Override
	public IPacket<?> getAddEntityPacket() {
		return NetworkHooks.getEntitySpawningPacket(this);
	}
}
