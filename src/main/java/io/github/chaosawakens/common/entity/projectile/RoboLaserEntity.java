package io.github.chaosawakens.common.entity.projectile;

import io.github.chaosawakens.api.animation.IAnimatableEntity;
import io.github.chaosawakens.api.animation.IAnimationBuilder;
import io.github.chaosawakens.api.animation.SingletonAnimationBuilder;
import io.github.chaosawakens.api.animation.WrappedAnimationController;
import io.github.chaosawakens.common.entity.boss.robo.RoboJefferyEntity;
import io.github.chaosawakens.common.entity.hostile.robo.RoboPounderEntity;
import io.github.chaosawakens.common.entity.hostile.robo.RoboSniperEntity;
import io.github.chaosawakens.common.entity.hostile.robo.RoboWarriorEntity;
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
import software.bernie.geckolib3.core.builder.ILoopType.EDefaultLoopTypes;
import software.bernie.geckolib3.core.event.predicate.AnimationEvent;
import software.bernie.geckolib3.core.manager.AnimationFactory;

public class RoboLaserEntity extends DamagingProjectileEntity implements IAnimatableEntity {
	private final AnimationFactory factory = new AnimationFactory(this);
	private final ObjectArrayList<WrappedAnimationController<RoboLaserEntity>> roboLaserControllers = new ObjectArrayList<WrappedAnimationController<RoboLaserEntity>>(1);
	private final ObjectArrayList<IAnimationBuilder> roboLaserAnimations = new ObjectArrayList<IAnimationBuilder>(2);
	private static final DataParameter<Boolean> HAS_HIT = EntityDataManager.defineId(RoboLaserEntity.class, DataSerializers.BOOLEAN);
	private static final DataParameter<Boolean> DYING = EntityDataManager.defineId(RoboLaserEntity.class, DataSerializers.BOOLEAN);
	private static final DataParameter<Boolean> CAUSE_FIRE = EntityDataManager.defineId(RoboLaserEntity.class, DataSerializers.BOOLEAN);
	private static final DataParameter<Float> DAMAGE_POWER = EntityDataManager.defineId(RoboLaserEntity.class, DataSerializers.FLOAT);
	private static final DataParameter<Float> EXPLOSIVE_POWER = EntityDataManager.defineId(RoboLaserEntity.class, DataSerializers.FLOAT);
	private final WrappedAnimationController<RoboLaserEntity> mainController = createMainMappedController("robolasermaincontroller");
	private final SingletonAnimationBuilder idleAnim = new SingletonAnimationBuilder(this, "Idle", EDefaultLoopTypes.LOOP);
	private final SingletonAnimationBuilder deathAnim = new SingletonAnimationBuilder(this, "Death", EDefaultLoopTypes.PLAY_ONCE);
	public static final String ROBO_LASER_MDF_NAME = "robo_sniper_laser";
	
	public RoboLaserEntity(EntityType<? extends RoboLaserEntity> type, World level) {
		super(type, level);
	}

	public RoboLaserEntity(World pLevel, LivingEntity pShooter, double pOffsetX, double pOffsetY, double pOffsetZ) {
		super(CAEntityTypes.ROBO_LASER.get(), pShooter, pOffsetX, pOffsetY, pOffsetZ, pLevel);
		float tempAngle = (float) (MathHelper.wrapDegrees(MathHelper.atan2(zPower, xPower) * 180.0 / Math.PI) - 90.0F);
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
				stopAnimation(getIdleAnim());
				playAnimation(getDeathAnim(), true);

				setDying(true);
			}

			playAnimation(deathAnim, false);
			setDeltaMovement(0, 0, 0);
		} else playAnimation(getIdleAnim(), false);

		if (getMainWrappedController().isAnimationFinished(deathAnim)) remove();
		this.yRot = this.yRotO;

	//	if (tickCount <= 1) playSound(CASoundEvents.ROBO_LASER_TRAVEL.get(), 1.0F, 1.0F);
	}
	
	protected void onHit(RayTraceResult result) {
		super.onHit(result);
		setHasHit(true);

		playSound(CASoundEvents.ROBO_LASER_HIT.get(), 1.0F, 1.0F);

		if (!this.level.isClientSide() && getExplosivePower() > 0) {
			boolean isMobGriefingAllowed = ForgeEventFactory.getMobGriefingEvent(this.level, this.getOwner());
			level.explode(null, getX(), getY(), getZ(), getExplosivePower(), fireOnHit(), isMobGriefingAllowed ? Explosion.Mode.DESTROY : Explosion.Mode.NONE);
		}
	}
	
	@Override
	protected void onHitEntity(EntityRayTraceResult pResult) {
		Entity targetEntity = pResult.getEntity();
		if (!(targetEntity instanceof RoboWarriorEntity) && !(targetEntity instanceof RoboSniperEntity) && !(targetEntity instanceof RoboPounderEntity) && !(targetEntity instanceof RoboJefferyEntity)) targetEntity.hurt(DamageSource.thrown(this, getOwner()), getDamagePower());
	}
	
	public void setPower(float damagePower, float explosivePower, boolean canCauseFire) {
		setDamagePower(damagePower);
		setExplosivePower(explosivePower);
		setFireOnHit(canCauseFire);
	}
	
	public void changeSpeed(double factor) {
		this.xPower *= factor;
		this.yPower *= factor;
		this.zPower *= factor;
	}

	@Override
	public boolean displayFireAnimation() {
		return false;
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

	@Override
	protected void defineSynchedData() {
		super.defineSynchedData();
		this.entityData.define(HAS_HIT, false);
		this.entityData.define(DYING, false);
		this.entityData.define(CAUSE_FIRE, false);
		this.entityData.define(DAMAGE_POWER, 0.0f);
		this.entityData.define(EXPLOSIVE_POWER, 0.0f);
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