package io.github.chaosawakens.common.entity.projectile;

import io.github.chaosawakens.api.animation.IAnimatableEntity;
import io.github.chaosawakens.api.animation.IAnimationBuilder;
import io.github.chaosawakens.api.animation.SingletonAnimationBuilder;
import io.github.chaosawakens.api.animation.WrappedAnimationController;
import io.github.chaosawakens.common.registry.CAEntityTypes;
import it.unimi.dsi.fastutil.objects.ObjectArrayList;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.projectile.DamagingProjectileEntity;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.network.IPacket;
import net.minecraft.network.datasync.DataParameter;
import net.minecraft.network.datasync.DataSerializers;
import net.minecraft.network.datasync.EntityDataManager;
import net.minecraft.util.DamageSource;
import net.minecraft.util.math.RayTraceResult;
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
	private final ObjectArrayList<IAnimationBuilder> roboLaserAnimations = new ObjectArrayList<IAnimationBuilder>(2);
	private static final DataParameter<Boolean> HAS_HIT = EntityDataManager.defineId(RoboLaserEntity.class, DataSerializers.BOOLEAN);
	private static final DataParameter<Boolean> IS_EXPLOSIVE = EntityDataManager.defineId(RoboLaserEntity.class, DataSerializers.BOOLEAN);
	private static final DataParameter<Boolean> FIRE_ON_HIT = EntityDataManager.defineId(RoboLaserEntity.class, DataSerializers.BOOLEAN);
	private static final DataParameter<Integer> EXPLOSION_POWER = EntityDataManager.defineId(RoboLaserEntity.class, DataSerializers.INT);
	private final WrappedAnimationController<RoboLaserEntity> mainController = createMainMappedController("robolasermaincontroller");
	private final SingletonAnimationBuilder idleAnim = new SingletonAnimationBuilder(this, "Idle", EDefaultLoopTypes.LOOP);
	private final SingletonAnimationBuilder deathAnim = new SingletonAnimationBuilder(this, "Death", EDefaultLoopTypes.PLAY_ONCE);
	public static final String ROBO_LASER_MDF_NAME = "robo_sniper_laser";
	private Explosion.Mode explosionMode = Explosion.Mode.DESTROY;

	public RoboLaserEntity(EntityType<? extends RoboLaserEntity> type, World level) {
		super(type, level);
	}

	public RoboLaserEntity(World pLevel, LivingEntity pShooter, double pOffsetX, double pOffsetY, double pOffsetZ) {
		super(CAEntityTypes.ROBO_LASER.get(), pShooter, pOffsetX, pOffsetY, pOffsetZ, pLevel);
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
		this.entityData.define(IS_EXPLOSIVE, false);
		this.entityData.define(FIRE_ON_HIT, false);
		this.entityData.define(EXPLOSION_POWER, 5);
	}

	public boolean hasHit() {
		return this.entityData.get(HAS_HIT);
	}

	public void setHasHit(boolean hasHit) {
		this.entityData.set(HAS_HIT, hasHit);
	}

	public boolean isExplosive() {
		return this.entityData.get(IS_EXPLOSIVE);
	}

	public void setExplosive(boolean isExplosive) {
		this.entityData.set(IS_EXPLOSIVE, isExplosive);
	}

	public boolean fireOnHit() {
		return this.entityData.get(FIRE_ON_HIT);
	}

	public void setFireOnHit(boolean fireOnHit) {
		this.entityData.set(FIRE_ON_HIT, fireOnHit);
	}

	public int getExplosionPower() {
		return this.entityData.get(EXPLOSION_POWER);
	}

	public void setExplosionPower(int power) {
		this.entityData.set(EXPLOSION_POWER, power);
	}

	public Explosion.Mode getExplosionMode() {
		return this.explosionMode;
	}

	public void setExplosionMode(Explosion.Mode mode) {
		this.explosionMode = mode;
	}

	@Override
	public void tick() {
		tickAnims();

		super.tick();
	}

	@Override
	protected void onHit(RayTraceResult result) {
		super.onHit(result);
		setHasHit(true);

		if (deathAnim.hasAnimationFinished()) {
			if (isExplosive()) level.explode(null, DamageSource.indirectMobAttack(this, (LivingEntity) getOwner()), null, getX(), getY(), getZ(), getExplosionPower(), fireOnHit(), this.explosionMode);

			remove();
		}
	}

	@Override
	protected boolean shouldBurn() {
		return false;
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
	public void addAdditionalSaveData(CompoundNBT pCompound) {
		super.addAdditionalSaveData(pCompound);

		pCompound.putString("ExplosionMode", explosionMode.name());
	}

	@Override
	public void readAdditionalSaveData(CompoundNBT pCompound) {
		super.readAdditionalSaveData(pCompound);

		this.explosionMode = Explosion.Mode.valueOf(pCompound.getString("ExplosionMode"));
	}

	@Override
	public String getOwnerMDFileName() {
		return ROBO_LASER_MDF_NAME;
	}

	@Override
	public IPacket<?> getAddEntityPacket() {
		return NetworkHooks.getEntitySpawningPacket(this);
	}

	protected void handleBaseAnimations() {
		if (hasHit()) playAnimation(deathAnim, true);
		else playAnimation(idleAnim, true);
	}
}