package io.github.chaosawakens.common.entity.hostile.robo;

import io.github.chaosawakens.api.animation.IAnimatableEntity;
import io.github.chaosawakens.api.animation.IAnimationBuilder;
import io.github.chaosawakens.api.animation.SingletonAnimationBuilder;
import io.github.chaosawakens.api.animation.WrappedAnimationController;
import io.github.chaosawakens.common.entity.ai.goals.hostile.AnimatableShootGoal;
import io.github.chaosawakens.common.entity.base.AnimatableMonsterEntity;
import io.github.chaosawakens.common.entity.projectile.RoboLaserEntity;
import io.github.chaosawakens.common.registry.CATeams;
import it.unimi.dsi.fastutil.objects.ObjectArrayList;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.MobEntity;
import net.minecraft.entity.ai.attributes.AttributeModifierMap;
import net.minecraft.entity.ai.attributes.Attributes;
import net.minecraft.entity.ai.goal.AvoidEntityGoal;
import net.minecraft.entity.ai.goal.HurtByTargetGoal;
import net.minecraft.entity.ai.goal.NearestAttackableTargetGoal;
import net.minecraft.entity.merchant.villager.VillagerEntity;
import net.minecraft.entity.monster.MonsterEntity;
import net.minecraft.entity.passive.AnimalEntity;
import net.minecraft.entity.passive.IronGolemEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.scoreboard.Team;
import net.minecraft.util.math.vector.Vector3d;
import net.minecraft.world.World;
import software.bernie.geckolib3.core.PlayState;
import software.bernie.geckolib3.core.builder.ILoopType.EDefaultLoopTypes;
import software.bernie.geckolib3.core.event.predicate.AnimationEvent;
import software.bernie.geckolib3.core.manager.AnimationFactory;

import javax.annotation.Nullable;
import java.util.function.BiFunction;

public class RoboSniperEntity extends AnimatableMonsterEntity {
	private final AnimationFactory factory = new AnimationFactory(this);
	private final ObjectArrayList<WrappedAnimationController<RoboSniperEntity>> roboSniperControllers = new ObjectArrayList<WrappedAnimationController<RoboSniperEntity>>(3);
	private final ObjectArrayList<IAnimationBuilder> roboSniperAnimations = new ObjectArrayList<IAnimationBuilder>(5);
	private final WrappedAnimationController<RoboSniperEntity> mainController = createMainMappedController("robosnipermaincontroller");
	private final WrappedAnimationController<RoboSniperEntity> attackController = createMappedController("robosniperattackcontroller", this::attackPredicate);
	private final WrappedAnimationController<RoboSniperEntity> ambienceController = createMappedController("robosniperambiencecontroller", this::ambiencePredicate);
	private final SingletonAnimationBuilder idleAnim = new SingletonAnimationBuilder(this, "Idle", EDefaultLoopTypes.LOOP);
	private final SingletonAnimationBuilder idleExtrasAnim = new SingletonAnimationBuilder(this, "Idle Extras", EDefaultLoopTypes.LOOP).setWrappedController(ambienceController);
	private final SingletonAnimationBuilder walkAnim = new SingletonAnimationBuilder(this, "Accelerate", EDefaultLoopTypes.LOOP);
	private final SingletonAnimationBuilder deathAnim = new SingletonAnimationBuilder(this, "Death", EDefaultLoopTypes.PLAY_ONCE).setWrappedController(attackController);
	private final SingletonAnimationBuilder shootAnim = new SingletonAnimationBuilder(this, "Shoot Attack", EDefaultLoopTypes.PLAY_ONCE).setWrappedController(attackController);
	private static final byte SHOOT_ATTACK_ID = 1;
	private static final BiFunction<AnimatableMonsterEntity, Vector3d, Entity> LASER_FACTORY_CLOSE = (owner, offset) -> {
		LivingEntity target = owner.getTarget();
		World world = owner.level;

		Vector3d viewVector = owner.getViewVector(1.0F);
		double offsetX = target.getX() - (owner.getX() + viewVector.x * offset.x());
		double offsetY = target.getY(0.5D) - (offset.y() + owner.getY(0.5D));
		double offsetZ = target.getZ() - (owner.getZ() + viewVector.z * offset.z());

		RoboLaserEntity laser = new RoboLaserEntity(world, owner, offsetX, offsetY, offsetZ);
		laser.setPower(4, 0, false);
		laser.setPos(owner.getX() + viewVector.x * offset.x(), owner.getY(0.5D) + offset.y(),
				owner.getZ() + viewVector.z * offset.z());

		laser.xPower *= 10.0F;
		laser.zPower *= 10.0F;

		return laser;
	};
	private static final BiFunction<AnimatableMonsterEntity, Vector3d, Entity> LASER_FACTORY_EXPLOSIVE = (owner, offset) -> {
		LivingEntity target = owner.getTarget();
		World world = owner.level;

		Vector3d viewVector = owner.getViewVector(1.0F);
		double offsetX = target.getX() - (owner.getX() + viewVector.x * offset.x());
		double offsetY = target.getY(0.5D) - (offset.y() + owner.getY(0.5D));
		double offsetZ = target.getZ() - (owner.getZ() + viewVector.z * offset.z());

		RoboLaserEntity laser = new RoboLaserEntity(world, owner, offsetX, offsetY, offsetZ);
		laser.setPower(4, 5, false);
		laser.setPos(owner.getX() + viewVector.x * offset.x(), owner.getY(0.5D) + offset.y(),
				owner.getZ() + viewVector.z * offset.z());

		laser.xPower *= 10.0F;
		laser.zPower *= 10.0F;

		return laser;
	};
	private static final Vector3d LASER_OFFSET = new Vector3d(2.0, 0.4, 2.0);
	public static final String ROBO_SNIPER_MDF_NAME = "robo_sniper";
	
	public RoboSniperEntity(EntityType<? extends MonsterEntity> type, World worldIn) {
		super(type, worldIn);
	}
	
	public static AttributeModifierMap.MutableAttribute setCustomAttributes() {
		return MobEntity.createLivingAttributes()
				.add(Attributes.MAX_HEALTH, 25)
				.add(Attributes.MOVEMENT_SPEED, 0.28D)
				.add(Attributes.KNOCKBACK_RESISTANCE, 0.2D)
				.add(Attributes.ATTACK_SPEED, 10)
				.add(Attributes.ATTACK_DAMAGE, 25)
				.add(Attributes.ATTACK_KNOCKBACK, 3.5D)
				.add(Attributes.FOLLOW_RANGE, 500);
	}

	@Override
	public AnimationFactory getFactory() {
		return factory;
	}

	@Override
	public WrappedAnimationController<RoboSniperEntity> getMainWrappedController() {
		return mainController;
	}

	@Override
	public <E extends IAnimatableEntity> PlayState mainPredicate(AnimationEvent<E> event) {
		return isDeadOrDying() || isAttacking() ? PlayState.STOP : PlayState.CONTINUE;
	}

	public <E extends IAnimatableEntity> PlayState attackPredicate(AnimationEvent<E> event) {
		return !isAttacking() && !isDeadOrDying() ? PlayState.STOP : PlayState.CONTINUE;
	}

	public <E extends IAnimatableEntity> PlayState ambiencePredicate(AnimationEvent<E> event) {
		return isDeadOrDying() || isAttacking() ? PlayState.STOP : PlayState.CONTINUE;
	}

	@Override
	public int animationInterval() {
		return 1;
	}
	
	@Override
	protected void registerGoals() {
		this.targetSelector.addGoal(0, new AnimatableShootGoal(this, SHOOT_ATTACK_ID, () -> shootAnim, LASER_FACTORY_CLOSE, LASER_OFFSET, 1.0, 3.0, 60, 3, 6));
		this.targetSelector.addGoal(0, new AnimatableShootGoal(this, SHOOT_ATTACK_ID, () -> shootAnim, LASER_FACTORY_EXPLOSIVE, LASER_OFFSET, 1.0, 3.0, 60, 3, 14));
		this.targetSelector.addGoal(1, new NearestAttackableTargetGoal<PlayerEntity>(this, PlayerEntity.class, false));
		this.targetSelector.addGoal(1, new NearestAttackableTargetGoal<VillagerEntity>(this, VillagerEntity.class, false));
		this.targetSelector.addGoal(1, new NearestAttackableTargetGoal<IronGolemEntity>(this, IronGolemEntity.class, false));
		this.targetSelector.addGoal(1, new NearestAttackableTargetGoal<AnimalEntity>(this, AnimalEntity.class, false));
		this.targetSelector.addGoal(2, new AvoidEntityGoal<>(this, PlayerEntity.class, 16f, 1.0f, 1.8f));
		this.targetSelector.addGoal(2, new AvoidEntityGoal<>(this, IronGolemEntity.class, 16f, 1.0f, 1.8f));
		this.targetSelector.addGoal(2, new AvoidEntityGoal<>(this, AnimalEntity.class, 16f, 1.0f, 1.8f));
		this.targetSelector.addGoal(1, new HurtByTargetGoal(this));
	}
	
	@Override
	protected void defineSynchedData() {
		super.defineSynchedData();
	}

	@Override
	public void manageAttack(LivingEntity target) {
	}

	@Nullable
	@Override
	public Team getTeam() {
		return CATeams.ROBO_TEAM;
	}

	@Override
	public boolean ignoreExplosion() {
		return true;
	}

	@Override
	public SingletonAnimationBuilder getIdleAnim() {
		return idleAnim;
	}

	@Override
	public SingletonAnimationBuilder getWalkAnim() {
		return walkAnim;
	}

	@Override
	public SingletonAnimationBuilder getDeathAnim() {
		return deathAnim;
	}

	@Override
	public String getOwnerMDFileName() {
		return ROBO_SNIPER_MDF_NAME;
	}

	@SuppressWarnings("unchecked")
	@Override
	public ObjectArrayList<WrappedAnimationController<RoboSniperEntity>> getWrappedControllers() {
		return roboSniperControllers;
	}
	
	@Override
	public ObjectArrayList<IAnimationBuilder> getCachedAnimations() {
		return roboSniperAnimations;
	}

	@Override
	protected void handleBaseAnimations() {
		super.handleBaseAnimations();

		if (random.nextInt(150) == 0 && !isPlayingAnimation(idleExtrasAnim) && getTarget() == null) playAnimation(idleExtrasAnim, false);
		if ((isAttacking() || !isMoving()) && isPlayingAnimation(walkAnim)) stopAnimation(walkAnim);
	}
}
