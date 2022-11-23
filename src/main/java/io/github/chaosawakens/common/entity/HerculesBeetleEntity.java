package io.github.chaosawakens.common.entity;

import java.util.List;
import java.util.function.BiFunction;

import io.github.chaosawakens.api.IGrabber;
import io.github.chaosawakens.api.IUtilityHelper;
import io.github.chaosawakens.common.config.CACommonConfig;
import io.github.chaosawakens.common.entity.ai.AnimatableGoal;
import io.github.chaosawakens.common.entity.ai.AnimatableMeleeGoal;
import io.github.chaosawakens.common.entity.ai.AnimatableMoveToTargetGoal;
import io.github.chaosawakens.common.entity.ai.pathfinding.CAGroundMovementController;
import io.github.chaosawakens.common.entity.ai.pathfinding.CAGroundWaterNVPathNavigator;
import io.github.chaosawakens.common.entity.base.AnimatableMonsterEntity;
import io.github.chaosawakens.common.entity.nonliving.CAScreenShakeEntity;
import io.github.chaosawakens.common.registry.CASoundEvents;
import io.github.chaosawakens.common.util.EnumUtils.AITargetingSystemType;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.ILivingEntityData;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.MobEntity;
import net.minecraft.entity.Pose;
import net.minecraft.entity.SpawnReason;
import net.minecraft.entity.ai.attributes.AttributeModifierMap;
import net.minecraft.entity.ai.attributes.Attributes;
import net.minecraft.entity.ai.goal.Goal;
import net.minecraft.entity.ai.goal.HurtByTargetGoal;
import net.minecraft.entity.ai.goal.NearestAttackableTargetGoal;
import net.minecraft.entity.ai.goal.SwimGoal;
import net.minecraft.entity.ai.goal.WaterAvoidingRandomWalkingGoal;
import net.minecraft.entity.passive.IronGolemEntity;
import net.minecraft.entity.passive.SnowGolemEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Items;
import net.minecraft.item.TieredItem;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.network.IPacket;
import net.minecraft.network.PacketBuffer;
import net.minecraft.network.datasync.DataParameter;
import net.minecraft.network.datasync.DataSerializers;
import net.minecraft.network.datasync.EntityDataManager;
import net.minecraft.pathfinding.PathNavigator;
import net.minecraft.util.DamageSource;
import net.minecraft.util.EntityPredicates;
import net.minecraft.util.SoundEvent;
import net.minecraft.util.math.vector.Vector3d;
import net.minecraft.util.math.vector.Vector3i;
import net.minecraft.world.DifficultyInstance;
import net.minecraft.world.IServerWorld;
import net.minecraft.world.World;
import net.minecraftforge.fml.common.registry.IEntityAdditionalSpawnData;
import net.minecraftforge.fml.network.NetworkHooks;
import software.bernie.geckolib3.core.IAnimatable;
import software.bernie.geckolib3.core.PlayState;
import software.bernie.geckolib3.core.builder.AnimationBuilder;
import software.bernie.geckolib3.core.controller.AnimationController;
import software.bernie.geckolib3.core.event.predicate.AnimationEvent;
import software.bernie.geckolib3.core.manager.AnimationData;
import software.bernie.geckolib3.core.manager.AnimationFactory;

//TODO Re-format all this code, this is yet again lazy junior dev shit done by me cause time is tight -- Meme Man
public class HerculesBeetleEntity extends AnimatableMonsterEntity implements IEntityAdditionalSpawnData, IGrabber {
	private final Type type;
	protected final Vector3d grabOffset = new Vector3d(0, 0.5, 2);
	private final AnimationFactory factory = new AnimationFactory(this);
	private final AnimationController<?> controller = new AnimationController<>(this, "herculesbeetlecontroller", animationInterval(), this::predicate);
//	private final AnimationController<?> deathController = new AnimationController<>(this, "herculesbeetledeathcontroller", animationInterval(), this::deathPredicate);
	public static final DataParameter<Byte> ATTACK_ID = EntityDataManager.defineId(HerculesBeetleEntity.class, DataSerializers.BYTE);
	public static final DataParameter<Boolean> MUNCHING = EntityDataManager.defineId(HerculesBeetleEntity.class, DataSerializers.BOOLEAN);
	public static final DataParameter<Integer> DAMAGE_MUNCH = EntityDataManager.defineId(HerculesBeetleEntity.class, DataSerializers.INT);
	public static final DataParameter<Integer> DOCILE_TIME = EntityDataManager.defineId(HerculesBeetleEntity.class, DataSerializers.INT);
	public static final DataParameter<Boolean> DOCILE = EntityDataManager.defineId(HerculesBeetleEntity.class, DataSerializers.BOOLEAN);
	public static final byte RAM_ATTACK = 1;
	public static final byte GRAB_ATTACK = 2;
	public static final byte MUNCH_ATTACK = 3;
	public static final byte FLAP_ATTACK = 4;
	public static final byte LAUNCH = 5;
	
	public HerculesBeetleEntity(EntityType<? extends AnimatableMonsterEntity> type, World worldIn, Type beetleType) {
		super(type, worldIn);
		this.noCulling = true;
		this.maxUpStep = 1.9F;
		this.moveControl = new CAGroundMovementController(this, 90);
		this.type = beetleType;
	}

	public static AttributeModifierMap.MutableAttribute setCustomAttributes() {
		return MobEntity.createLivingAttributes()
				.add(Attributes.MAX_HEALTH, 250)
				.add(Attributes.ARMOR, 20)
				.add(Attributes.MOVEMENT_SPEED, 0.25D)
				.add(Attributes.KNOCKBACK_RESISTANCE, 0.6D)
				.add(Attributes.ATTACK_SPEED, 10)
				.add(Attributes.ATTACK_DAMAGE, 25)
				.add(Attributes.ATTACK_KNOCKBACK, 0.0D)
				.add(Attributes.FOLLOW_RANGE, 25);
	}
	
	public byte getAttackID() {
		return this.entityData.get(ATTACK_ID);
	}
	
	public void setAttackID(byte attackID) {
		this.entityData.set(ATTACK_ID, attackID);
	}
	
	public boolean getDocile() {
		return this.entityData.get(DOCILE);
	}
	
	public void setDocile(boolean docile) {
		this.entityData.set(DOCILE, docile);
	}
	
	public int getDocileTime() {
		return this.entityData.get(DOCILE_TIME);
	}
	
	public void setDocileTimer(int docileTime) {
		this.entityData.set(DOCILE_TIME, docileTime);
	}
	
	public boolean getMunching() {
		return this.entityData.get(MUNCHING);
	}
	
	public void setMunching(boolean munching) {
		this.entityData.set(MUNCHING, munching);
	}

	@Override
	public <E extends IAnimatable> PlayState predicate(AnimationEvent<E> event) {
		if (getAttackID() == RAM_ATTACK && isAlive()) {
			event.getController().setAnimation(new AnimationBuilder().addAnimation("animation.hercules_beetle.attack_animation_ram", false));
			event.getController().setAnimationSpeed(1);
			return PlayState.CONTINUE;
		}
		
		if (getDocile()) {
			event.getController().setAnimation(new AnimationBuilder().addAnimation("animation.hercules_beetle.sleep", true));
			event.getController().setAnimationSpeed(1);
			return PlayState.CONTINUE;
		}
		
		if (getAttackID() == GRAB_ATTACK && isAlive()) {
			event.getController().setAnimation(new AnimationBuilder().addAnimation("animation.hercules_beetle.attack_animation_grab", false));
			event.getController().setAnimationSpeed(1);
			return PlayState.CONTINUE;
		}
		
		if (getAttackID() == LAUNCH && isAlive()) {
			event.getController().setAnimation(new AnimationBuilder().addAnimation("animation.hercules_beetle.attack_animation_ram", false));
			event.getController().setAnimationSpeed(1.5D);
			return PlayState.CONTINUE;
		}
		
		if (getAttackID() == MUNCH_ATTACK && isAlive()) {
			event.getController().setAnimation(new AnimationBuilder().addAnimation("animation.hercules_beetle.attack_animation_munch", true));
			event.getController().setAnimationSpeed(1.8D);
			return PlayState.CONTINUE;
		}
		
		if (getAttackID() == FLAP_ATTACK && isAlive()) {
			event.getController().setAnimation(new AnimationBuilder().addAnimation("animation.hercules_beetle.awaken", false));
			event.getController().setAnimationSpeed(1);
			return PlayState.CONTINUE;
		}

		if (event.isMoving() && (getAttackID() == (byte) 0)) {
			event.getController().setAnimation(new AnimationBuilder().addAnimation("animation.hercules_beetle.walking_animation", true));
			event.getController().setAnimationSpeed(1.5D);
			return PlayState.CONTINUE;
		}
		
		if (this.isDeadOrDying() || this.getHealth() <= 0) {
			event.getController().setAnimation(new AnimationBuilder().addAnimation("animation.hercules_beetle.death_animation", false));
			event.getController().setAnimationSpeed(0.48D);
			return PlayState.CONTINUE;
		}
		
		event.getController().setAnimation(new AnimationBuilder().addAnimation("animation.hercules_beetle.idle_animation", true));
		return PlayState.CONTINUE;
	}
	
	public <E extends IAnimatable> PlayState deathPredicate(AnimationEvent<E> event) {		
		if (this.isDeadOrDying() || this.getHealth() <= 0) {
			event.getController().setAnimation(new AnimationBuilder().addAnimation("animation.hercules_beetle.death_animation", false));
			event.getController().setAnimationSpeed(0.38D);
			return PlayState.CONTINUE;
		}
		return PlayState.STOP;
	}
	
	@Override
	protected void registerGoals() {
		this.targetSelector.addGoal(0, new HerculesBeetleMoveToTargetGoal(this, 1.75, 8));
		this.targetSelector.addGoal(0, new HerculesBeetleRamGoal(this, 20.4D, 0.7D, 0.8D));
		this.targetSelector.addGoal(0, new HerculesBeetleGrabGoal(this));
		this.targetSelector.addGoal(0, new HerculesBeetleMunchGoal(this));
		this.targetSelector.addGoal(0, new HerculesBeetleFlapRandomlyGoal(this));
		this.targetSelector.addGoal(1, new HurtByTargetGoal(this));
		this.targetSelector.addGoal(1, new NearestAttackableTargetGoal<>(this, PlayerEntity.class, true));
		this.targetSelector.addGoal(1, new NearestAttackableTargetGoal<>(this, IronGolemEntity.class, true));
		this.targetSelector.addGoal(1, new NearestAttackableTargetGoal<>(this, SnowGolemEntity.class, true));
		this.targetSelector.addGoal(1, new NearestAttackableTargetGoal<>(this, UltimateAppleCowEntity.class, true));
		this.goalSelector.addGoal(2, new WaterAvoidingRandomWalkingGoal(this, 1.6));
		this.goalSelector.addGoal(2, new SwimGoal(this));
	}

	@Override
	public void registerControllers(AnimationData data) {
		data.addAnimationController(controller);
//		data.addAnimationController(deathController);
	}
	
	@Override
	public AnimationController<?> getController() {
		return controller;
	}
	
	@Override
	public void manageAttack(LivingEntity target) {
		
	}
	
	@Override
	public int animationInterval() {
		return 3;
	}

	@Override
	public AnimationFactory getFactory() {
		return this.factory;
	}

	@Override
	public boolean causeFallDamage(float p_225503_1_, float p_225503_2_) {
		return false;
	}

	@Override
	public void aiStep() {
		super.aiStep();
		
		if (getTarget() != null) {
			if (distanceTo(getTarget()) <= 6.0D && getAttackID() != MUNCH_ATTACK && getAttackID() != FLAP_ATTACK && !getDocile() && !isDeadOrDying()) {
				lookAt(getTarget(), 100, 100);
				getLookControl().setLookAt(getTarget(), 30F, 30F);
			}
			
			if (hasPassenger(getTarget()) && getAttackID() != GRAB_ATTACK && getAttackID() != MUNCH_ATTACK) ejectPassengers();
		}
		
/*		if (getNavigation().getPath() != null && getTarget() == null) {
			if (!getNavigation().isDone()) {
				if (getNavigation().getPath().getTarget() != null) {
					Vector3d targetPos = new Vector3d(getNavigation().getPath().getTarget().getX(), getNavigation().getPath().getTarget().getY(), getNavigation().getPath().getTarget().getZ());
					lookAt(Type.EYES, targetPos);
					getLookControl().setLookAt(targetPos);
				}
			}
		}*/
		
//		ChaosAwakens.debug("ATTACKID", "[Attack ID]: " + getAttackID());

		if (this.horizontalCollision && this.isOnGround()) {
		//	this.jumpFromGround();
		}
		
	}

	@Override
	protected void defineSynchedData() {
		super.defineSynchedData();
		this.entityData.define(ATTACK_ID, (byte) 0);
		this.entityData.define(MUNCHING, false);
		this.entityData.define(DAMAGE_MUNCH, 0);
		this.entityData.define(DOCILE, true);
		this.entityData.define(DOCILE_TIME, 0);
	}

	@Override
	public boolean shouldRiderSit() {
		return false;
	}

	@Override
	public void positionRider(Entity passenger) {
		this.positionRider(this, passenger, Entity::setPos);
	}

	public Vector3d getGrabOffset() {
		return this.grabOffset;
	}

	@Override
	protected SoundEvent getHurtSound(DamageSource damageSourceIn) {
		return CASoundEvents.HERCULES_BEETLE_HURT.get();
	}

	@Override
	protected SoundEvent getDeathSound() {
		return CASoundEvents.HERCULES_BEETLE_DEATH.get();
	}

/*	@Override
	public boolean isPersistenceRequired() {
		return true;
	}*/
	
	@Override
	public void push(double p_70024_1_, double p_70024_3_, double p_70024_5_) {
	}
	
	@Override
	protected void tickDeath() {
		++deathTime;
		if (deathTime == 60) {
			this.remove();
		}
	}
	
	private boolean deemWeakerThreat(LivingEntity curTarget, LivingEntity newTarget) {
		if (curTarget.getAttribute(Attributes.ATTACK_DAMAGE) != null && curTarget.getAttribute(Attributes.ATTACK_SPEED) != null && newTarget.getAttribute(Attributes.ATTACK_DAMAGE) != null && newTarget.getAttribute(Attributes.ATTACK_SPEED) != null) {
			boolean b1 = (curTarget.getHealth() > newTarget.getHealth() && curTarget.getAttributeValue(Attributes.ATTACK_DAMAGE) > newTarget.getAttributeValue(Attributes.ATTACK_DAMAGE)) && (curTarget.getHealth() + (curTarget.getAttributeValue(Attributes.ARMOR) * 0.5F) > newTarget.getHealth() + (newTarget.getAttributeValue(Attributes.ARMOR) * 0.5F)) && this.distanceTo(curTarget) < this.distanceTo(newTarget);
			
			//If the current target's health is more than the new target and the current target is holding some sort of weapon/tool (deals actual damage)
			if (curTarget.getMainHandItem().getItem() instanceof TieredItem && !(newTarget.getMainHandItem().getItem() instanceof TieredItem) && curTarget.getHealth() >= newTarget.getHealth()) {
				return true;
			}
			
			//If the current target's health is greater than the new target's health, and the current target deals more damage than the new target, and the current target's health combined with half armor toughness is greater than the new target's health combined with armor toughness
			if (b1) {	
				return true;
			}
				//If both targets are holding a weapon/tool of some sort
				if (curTarget.getMainHandItem().getItem() instanceof TieredItem && newTarget.getMainHandItem().getItem() instanceof TieredItem) {
					TieredItem curTargetItem = (TieredItem) curTarget.getMainHandItem().getItem();
					TieredItem newTargetItem = (TieredItem) newTarget.getMainHandItem().getItem();
					
					boolean b2 = curTargetItem.getTier().getAttackDamageBonus() > newTargetItem.getTier().getAttackDamageBonus();
					boolean b3 = curTarget.getHealth() > newTarget.getHealth();
					boolean b4 = curTargetItem.getTier().getSpeed() > newTargetItem.getTier().getSpeed() + 1.5F;
					boolean b5 = curTarget.getAttributeValue(Attributes.ATTACK_DAMAGE) > newTarget.getAttributeValue(Attributes.ATTACK_DAMAGE) + newTargetItem.getTier().getAttackDamageBonus();
					boolean b6 = curTarget.getAttributeValue(Attributes.ATTACK_SPEED) > newTarget.getAttributeValue(Attributes.ATTACK_SPEED) + newTargetItem.getTier().getSpeed();			
					
					//If the current target's health and (item) attack damage are greater than the new target's health and (item) attack damage, or the current target's health and (item) attack speed are greater than the new target's health and (item) attack speed, or if the current target's (item) attack damage and attack speed are greater than the new target's (item) attack damage and attack speed
					if ((b2 && b3) || (b3 && b4) || (b2 && b4)) {
						return true;
					}
					
					//If the current target's base attack damage is greater than the new target's base attack damage + (item) attack damage, or if the current target's base attack speed is greater than the new target's base attack speed + (item) attack speed and the current target's health is greater than the new target's health
					if ((b5) || (b6 && b3)) {
						return true;
					}
					
				}
				
				//Getting lazier...
				
				//If the current target is a player, but the new target is not a player
				if (curTarget instanceof PlayerEntity && !(newTarget instanceof PlayerEntity)) {
					PlayerEntity curTargetPlayer = (PlayerEntity) curTarget;
					
					//If both targets are holding a weapon/tool of some sort
					if (curTargetPlayer.getMainHandItem().getItem() instanceof TieredItem && newTarget.getMainHandItem().getItem() instanceof TieredItem) {
						TieredItem curTargetPlayerItem = (TieredItem) curTargetPlayer.getMainHandItem().getItem();
						TieredItem newTargetItem = (TieredItem) newTarget.getMainHandItem().getItem();
						
						boolean b7 = curTargetPlayerItem.getTier().getAttackDamageBonus() > newTarget.getAttributeValue(Attributes.ATTACK_DAMAGE);
						boolean b8 = curTargetPlayerItem.getTier().getAttackDamageBonus() > this.getAttributeValue(Attributes.ARMOR) && newTargetItem.getTier().getAttackDamageBonus() < this.getAttributeValue(Attributes.ARMOR);
						boolean b9 = curTargetPlayerItem.getTier().getAttackDamageBonus() >= this.getHealth() && newTargetItem.getTier().getAttackDamageBonus() < this.getHealth();
						boolean b10 = curTargetPlayerItem.getTier().getAttackDamageBonus() + curTargetPlayer.getAttributeValue(Attributes.ATTACK_DAMAGE) >= this.getHealth() && newTargetItem.getTier().getAttackDamageBonus() + newTarget.getAttributeValue(Attributes.ATTACK_DAMAGE) < this.getHealth();
						
						//If the current target is stronger than the new target/poses a larger threat on the user
						if ((b7 && b8) || (b9) || (b10)) {
							return true;
						}
					}
					
					boolean b11 = curTargetPlayer.getAttributeValue(Attributes.ARMOR) >= newTarget.getAttributeValue(Attributes.ARMOR);
					boolean b12 = curTargetPlayer.getHealth() >= newTarget.getHealth();
					boolean b13 = curTargetPlayer.getAttributeValue(Attributes.ATTACK_DAMAGE) >= newTarget.getAttributeValue(Attributes.ATTACK_DAMAGE);
					boolean b14 = curTargetPlayer.getAttributeValue(Attributes.ATTACK_SPEED) >= newTarget.getAttributeValue(Attributes.ATTACK_SPEED);
					boolean b15 = curTargetPlayer.getAttributeValue(Attributes.MOVEMENT_SPEED) >= newTarget.getAttributeValue(Attributes.MOVEMENT_SPEED);
					
					if ((b11 && b12) || (b13) || (b12 && b14 && b15) || (b13 && b15)) {
						return true;
					}
				}
				
				//If the current target is not a player, but the new target is a player
				if (!(curTarget instanceof PlayerEntity) && newTarget instanceof PlayerEntity) {
					PlayerEntity newTargetPlayer = (PlayerEntity) newTarget;
					
					//If both targets are holding a weapon/tool of some sort
					if (newTargetPlayer.getMainHandItem().getItem() instanceof TieredItem && curTarget.getMainHandItem().getItem() instanceof TieredItem) {
						TieredItem newTargetPlayerItem = (TieredItem) newTargetPlayer.getMainHandItem().getItem();
						TieredItem curTargetItem = (TieredItem) curTarget.getMainHandItem().getItem();
						
						boolean b16 = newTargetPlayerItem.getTier().getAttackDamageBonus() < curTarget.getAttributeValue(Attributes.ATTACK_DAMAGE);
						boolean b17 = newTargetPlayerItem.getTier().getAttackDamageBonus() < this.getAttributeValue(Attributes.ARMOR) && curTargetItem.getTier().getAttackDamageBonus() >= this.getAttributeValue(Attributes.ARMOR);
						boolean b18 = newTargetPlayerItem.getTier().getAttackDamageBonus() < this.getHealth() && curTargetItem.getTier().getAttackDamageBonus() >= this.getHealth();
						boolean b19 = newTargetPlayerItem.getTier().getAttackDamageBonus() + newTargetPlayer.getAttributeValue(Attributes.ATTACK_DAMAGE) < this.getHealth() && curTargetItem.getTier().getAttackDamageBonus() + curTarget.getAttributeValue(Attributes.ATTACK_DAMAGE) >= this.getHealth();
						
						//If the current target is (still) stronger than the new target/poses a larger threat on the user
						if ((b16 && b17) || (b18) || (b19)) {
							return true;
						}
					}
					
					boolean b20 = newTargetPlayer.getAttributeValue(Attributes.ARMOR) < curTarget.getAttributeValue(Attributes.ARMOR);
					boolean b21 = newTargetPlayer.getHealth() < curTarget.getHealth();
					boolean b22 = newTargetPlayer.getAttributeValue(Attributes.ATTACK_DAMAGE) < curTarget.getAttributeValue(Attributes.ATTACK_DAMAGE);
					boolean b23 = newTargetPlayer.getAttributeValue(Attributes.ATTACK_SPEED) < curTarget.getAttributeValue(Attributes.ATTACK_SPEED);
					boolean b24 = newTargetPlayer.getAttributeValue(Attributes.MOVEMENT_SPEED) < curTarget.getAttributeValue(Attributes.MOVEMENT_SPEED);
					
					if ((b20 && b21) || (b22) || (b21 && b23 && b24) || (b22 && b24)) {
						return true;
					}
				}
				
				//If both targets are players
				if (curTarget instanceof PlayerEntity && newTarget instanceof PlayerEntity) {
					PlayerEntity curTargetPlayer = (PlayerEntity) curTarget;
					PlayerEntity newTargetPlayer = (PlayerEntity) newTarget;
					
					//If both targets are holding a weapon/tool of some sort
					if (curTargetPlayer.getMainHandItem().getItem() instanceof TieredItem && newTargetPlayer.getMainHandItem().getItem() instanceof TieredItem) {
						TieredItem curTargetPlayerItem = (TieredItem) curTargetPlayer.getMainHandItem().getItem();
						TieredItem newTargetPlayerItem = (TieredItem) newTargetPlayer.getMainHandItem().getItem();
						
						boolean b25 = curTargetPlayer.getHealth() >= newTargetPlayer.getHealth();
						boolean b26 = curTargetPlayerItem.getTier().getAttackDamageBonus() + curTargetPlayer.getAttributeValue(Attributes.ATTACK_DAMAGE) > newTargetPlayerItem.getTier().getAttackDamageBonus() + newTargetPlayer.getAttributeValue(Attributes.ATTACK_DAMAGE);
						boolean b27 = curTargetPlayer.getAttributeValue(Attributes.ATTACK_SPEED) > newTargetPlayer.getAttributeValue(Attributes.ATTACK_SPEED);
						boolean b28 = curTargetPlayer.getAttributeValue(Attributes.MOVEMENT_SPEED) > newTargetPlayer.getAttributeValue(Attributes.MOVEMENT_SPEED);
						boolean b29 = curTargetPlayerItem.getTier().getSpeed() + curTargetPlayer.getAttributeValue(Attributes.ATTACK_SPEED) > newTargetPlayerItem.getTier().getSpeed() + newTargetPlayer.getAttributeValue(Attributes.ATTACK_SPEED);
						
						if ((b25 && b28) || (b25 && b26) || (b26 && b27 && b29) || (b26 && b27) || (b27 && b28) || (b26 && b28)) {
							return true;
						}
					}
					
					boolean b30 = curTargetPlayer.getAttributeValue(Attributes.ARMOR) > newTargetPlayer.getAttributeValue(Attributes.ARMOR);
					boolean b31 = curTargetPlayer.getAttributeValue(Attributes.ATTACK_DAMAGE) > newTargetPlayer.getAttributeValue(Attributes.ATTACK_DAMAGE);
					boolean b32 = curTargetPlayer.getAttributeValue(Attributes.ATTACK_SPEED) > newTargetPlayer.getAttributeValue(Attributes.ATTACK_SPEED);
					boolean b33 = curTargetPlayer.getHealth() > newTargetPlayer.getHealth();
					boolean b34 = curTargetPlayer.getAttributeValue(Attributes.MOVEMENT_SPEED) > newTargetPlayer.getAttributeValue(Attributes.MOVEMENT_SPEED);
					
					if ((b30 && b31) || (b32 && b33) || (b31 && b32 && b33) || (b31 && b33 && b34) || (b30 && b33 && b34) || (b30 && b33 && b34)) {
						return true;
					}
					
					return false;
					
				}
				
				return false;				
		}
		return false;
	}
	
	@Override
	protected void divertTarget() {
		if (!this.level.isClientSide) {
			double radius = getFollowRange();
			List<LivingEntity> allAttackableEntitiesAround = IUtilityHelper.getAllEntitiesAround(this, getFollowRange(), getFollowRange(), getFollowRange(), radius);
			AITargetingSystemType type = CACommonConfig.COMMON.herculesBeetleTargetingSystemType.get();
			
			for (LivingEntity target : allAttackableEntitiesAround) {
				
					switch (type) {
					default:
						break;
					case RADIUS_CLOSEST:
						if (getTarget() != null) {
							if (this.distanceTo(target) < this.distanceTo(getTarget()) && EntityPredicates.ATTACK_ALLOWED.test(target) && this.getSensing().canSee(target)) {
								this.setTarget(target);
							}	
						}
						break;
					case RADIUS_FARTHEST:
						if (getTarget() != null) {
							if (this.distanceTo(target) > this.distanceTo(getTarget()) && EntityPredicates.ATTACK_ALLOWED.test(target) && this.getSensing().canSee(target)) {
								this.setTarget(target);
							}
						}
						break;
					case DYNAMIC_WEAKEST:
						if (getTarget() != null) {
							if (deemWeakerThreat(getTarget(), target)) {
								this.setTarget(target);
							}
						}
						break;
					case DYNAMIC_STRONGEST:
						if (getTarget() != null) {
							if (!deemWeakerThreat(getTarget(), target)) {
								this.setTarget(target);
							}
						}
						break;
					case FIXED:
						if (getTarget() != null) {
							continue;
						}
						break;
					case HURT_BY:
						if (getTarget() != null) {
							if (this.getLastHurtByMob() == target) {
								this.setTarget(target);
							}
						}
						break;
					case RANDOMIZED:
						if (getTarget() != null) {
							if (this.getRandom().nextInt(500) == 0) {
								this.setTarget(target);
							}
						}
						break;
					case RADIUS_CLOSEST_DYNAMIC_STRONGEST:
						if (getTarget() != null) {
							if (!deemWeakerThreat(getTarget(), target) && this.distanceTo(target) <= radius / 3) {
								this.setTarget(target);
							}
						}
						break;
					case RADIUS_CLOSEST_DYNAMIC_WEAKEST:
						if (getTarget() != null) {
							if (deemWeakerThreat(getTarget(), target) && this.distanceTo(target) <= radius / 3) {
								this.setTarget(target);
							}
						}
						break;
					case RADIUS_CLOSEST_HURT_BY:
						if (getTarget() != null) {
							if (this.distanceTo(target) < this.distanceTo(getTarget()) && EntityPredicates.ATTACK_ALLOWED.test(target) && this.getSensing().canSee(target) && this.getLastHurtByMob() == target && this.distanceTo(target) <= radius / 3) {
								this.setTarget(target);
							}	
						}
						break;
					case RADIUS_FARTHEST_DYNAMIC_STRONGEST:
						if (getTarget() != null) {
							if (!deemWeakerThreat(getTarget(), target) && this.distanceTo(target) > radius / 3) {
								this.setTarget(target);
							}
						}
						break;
					case RADIUS_FARTHEST_DYNAMIC_WEAKEST:
						if (getTarget() != null) {
							if (deemWeakerThreat(getTarget(), target) && this.distanceTo(target) > radius / 3) {
								this.setTarget(target);
							}
						}
						break;
					case RADIUS_FARTHEST_HURT_BY:
						if (getTarget() != null) {
							if (this.distanceTo(target) > this.distanceTo(getTarget()) && EntityPredicates.ATTACK_ALLOWED.test(target) && this.getSensing().canSee(target) && this.getLastHurtByMob() == target && this.distanceTo(target) >= radius / 3) {
								this.setTarget(target);
							}	
						}
						break;
					}
			}
		}
	}
	
	@Override
	public boolean canBeControlledByRider() {
		return false;
	}
	
	@Override
	public boolean canRiderInteract() {
		return true;
	}
	
	@Override
	public boolean showVehicleHealth() {
		return false;
	}
	
	@Override
	public void tick() {
		super.tick();
		int docileTimer = 0;
				
		if (!level.isClientSide) {
			if (getDocile()) {
				if (!isOnGround()) setDeltaMovement(new Vector3d(0.0D, getDeltaMovement().y, 0.0D));
				setDeltaMovement(Vector3d.ZERO);
				getNavigation().stop();
		//		yHeadRot = yBodyRotO = yBodyRot;
				float health = getHealth();
				setDocileTimer(0);
				setArmor(10);
				
				if (health < getMaxHealth()) {
					if (tickCount % 20 == 0) setHealth(getHealth() + 1);
				}
				if (getTarget() != null) {
					setDocile(false);
					setAttackID(FLAP_ATTACK);
				}
				if (hurtTime > 0) {
					setDocile(false);
					setAttackID(FLAP_ATTACK);
				}
			}
			
			boolean canSleep = getTarget() == null && hurtTime == 0 && getAttackID() == (byte) 0 && getDocileTime() > 1000 && isOnGround() && getNavigation().isDone() && level.random.nextBoolean();
			
			if (!getDocile()) {
				setArmor(20);
				docileTimer++;
				if (canSleep) setDocile(true);
				if (getTarget() != null) setDocileTimer(0);
			}
			
//			ChaosAwakens.debug("DOCILETIME", "[Docile Timer]: " + canSleep);
			
			setDocileTimer(getDocileTime() + docileTimer);
		}
//		ChaosAwakens.debug("DOCILETIME", "[Docile Timer]: " + getDocileTime());
	}
	
	@Override
	public void rideTick() {
		super.rideTick();
		if (getTarget() != null && hasPassenger(getTarget())) {
			getTarget().setPose(Pose.SPIN_ATTACK);
		}
	}
	
	@Override
	public boolean canBeCollidedWith() {
		return false;
	}
	
	@Override
	public boolean canSee(Entity target) {
		if (this.getTarget() != null && this.distanceTo(target) <= getFollowRange() && IUtilityHelper.getVerticalDistanceBetweenEntities(this, getTarget()) <= getFollowRange() / 2) return true;
		return super.canSee(target);
	}
	
	public class HerculesBeetleRamGoal extends AnimatableMeleeGoal {
		
		public HerculesBeetleRamGoal(AnimatableMonsterEntity entity, double animationLength, double attackBegin, double attackEnd) {
			super(entity, animationLength, attackBegin, attackEnd);
		}
		
		@Override
		public void start() {
			super.start();
			getNavigation().stop();
			setAttackID(RAM_ATTACK);
		}
		
		@Override
		public void stop() {
			super.stop();
			setAttackID((byte) 0);
	//		controller.markNeedsReload();
		}
		
		@Override
		public boolean canUse() {
			return super.canUse() && getAttackID() == (byte) 0 && level.random.nextInt(2) == 0 && !this.hasHit && !getDocile();
		}
		
		@Override
		public boolean canContinueToUse() {
			return super.canContinueToUse() && getAttackID() == RAM_ATTACK;
		}
		
		public void animateAttack(AnimatableMonsterEntity attacker) {
			if (attacker.getTarget() == null) return;
			
			double reach = AnimatableGoal.getAttackReachSq(attacker, attacker.getTarget());
			
			List<LivingEntity> targets = IUtilityHelper.getAllEntitiesAround(attacker, reach, reach, reach, 6.0D);
			
			for (LivingEntity target : targets) {
				float targetAngle = (float) ((Math.atan2(target.getZ() - attacker.getZ(), target.getX() - attacker.getX()) * (180 / Math.PI) - 90) % 360);
				float attackAngle = attacker.yBodyRot % 360;
				
				if (targetAngle < 0) targetAngle += 360;
				if (attackAngle < 0) attackAngle += 360;
				
				float relativeHitAngle = targetAngle - attackAngle;
				float hitDistanceSqr = (float) (Math.sqrt((target.getZ() - attacker.getZ()) * (target.getZ() - attacker.getZ()) + (target.getX() - attacker.getX()) * (target.getX() - attacker.getX())) - attacker.getBbWidth() / 2F);
				
//				attacker.lookAt(target, 100, 100);
//				attacker.getLookControl().setLookAt(target, 30F, 30F);
				
				if (hitDistanceSqr <= reach && (relativeHitAngle <= 65F / 2 && relativeHitAngle >= -65F / 2) || (relativeHitAngle >= 360 - 65F / 2 || relativeHitAngle <= -360 + 65F / 2)) {
					attacker.doHurtTarget(target);
					target.push(0, 0.7D, 0);
					this.hasHit = true;
				}
			}
		}
		
		@Override
		public void tick() {
			this.baseTick();
			
			if (attackEndPredicate.apply(animationProgress, animationLength)) {
				if (!isOnGround()) {
					setDeltaMovement(0, getDeltaMovement().y, 0);
					setSpeed(0);
				} else {
					setDeltaMovement(Vector3d.ZERO);
				}
			}
			
			LivingEntity target = this.entity.getTarget();
			
			if (target != null) {
				
//				ChaosAwakens.debug("GOAL", (this.animationProgress) + "/" + (this.animationLength) + "/" + (this.tickDelta) + "/" + (this.animationProgress/this.animationLength));
				 
				if (this.attackPredicate.apply(this.animationProgress, this.animationLength) && !this.hasHit) {
					playSound(CASoundEvents.HERCULES_BEETLE_SWING.get(), 1.0F, 1.1F);
					animateAttack(this.entity);
				}

				if (this.animationProgress >= this.animationLength - 1) {
					this.animationProgress = 0;
					this.hasHit = false;
					setAttackID((byte) 0);
				}
			}
		}
		
	}
	
	public class HerculesBeetleMoveToTargetGoal extends AnimatableMoveToTargetGoal {

		public HerculesBeetleMoveToTargetGoal(AnimatableMonsterEntity entity, double speedMultiplier, int checkRate) {
			super(entity, speedMultiplier, checkRate);
		}
		
		@Override
		public boolean canUse() {
			return super.canUse() && getAttackID() == (byte) 0 && !getDocile();
		}
		
		@Override
		public boolean canContinueToUse() {
			return super.canContinueToUse() && getAttackID() == (byte) 0 && !getDocile();
		}
		
	}
	
	public class HerculesBeetleGrabGoal extends AnimatableGoal {
		private boolean successful = false;
		protected final BiFunction<Double, Double, Boolean> attackEndPredicate;
		protected final BiFunction<Double, Double, Boolean> attackGrabPredicate;
		private final double grabAnimLength;
		
		public HerculesBeetleGrabGoal(HerculesBeetleEntity beetle) {
			this.entity = beetle;
			grabAnimLength = 14;
			attackEndPredicate = (progress, length) -> ++progress >= grabAnimLength;
			attackGrabPredicate = (progress, length) -> ++progress >= grabAnimLength - 5 && progress <= grabAnimLength - 3;
		}
		
		@Override
		public void start() {
			setAttackID(GRAB_ATTACK);
			successful = false;
			getNavigation().stop();
			this.animationProgress = 0;
		}
		
		@Override
		public void stop() {
			if (successful) setAttackID(MUNCH_ATTACK);
			else setAttackID((byte) 0);
			successful = false;
			this.animationProgress = 0;
		}
		
		@Override
		public boolean canUse() {
			return getAttackID() == (byte) 0 && level.random.nextInt(2) == 1 && !isInWaterOrBubble() && !getDocile() && !isInLava() && getTarget() != null && distanceToSqr(getTarget()) <= AnimatableGoal.getAttackReachSq(entity, getTarget()) - 0.3D;
		}
		
		@Override
		public boolean canContinueToUse() {
			return !attackEndPredicate.apply(animationProgress, grabAnimLength);
		}
		
		@SuppressWarnings("unused")
		@Override
		public void tick() {
			this.baseTick();
			
			LivingEntity target = getTarget();
			if (!attackEndPredicate.apply(animationProgress, grabAnimLength)) {
				getNavigation().stop();
				if (!isOnGround()) {
					setDeltaMovement(0, getDeltaMovement().y, 0);
					setSpeed(0);
				} else {
					setDeltaMovement(Vector3d.ZERO);
					setSpeed(0);
				}
			}
			
			if (target != null) {
				if (attackGrabPredicate.apply(animationProgress, grabAnimLength)) {
					if (getTarget() == null) return;
					
					double reach = AnimatableGoal.getAttackReachSq(entity, getTarget());
					double reachSqr = reach * reach;
					
					List<LivingEntity> targets = IUtilityHelper.getAllEntitiesAround(entity, reach, reach, reach, 6.0D);
					
					for (LivingEntity targetHit : targets) {
						float targetAngle = (float) ((Math.atan2(target.getZ() - getZ(), target.getX() - getX()) * (180 / Math.PI) - 90) % 360);
						float attackAngle = yBodyRot % 360;
						
						if (targetAngle < 0) targetAngle += 360;
						if (attackAngle < 0) attackAngle += 360;
						
						float relativeHitAngle = targetAngle - attackAngle;
						float hitDistanceSqr = (float) (Math.sqrt((target.getZ() - getZ()) * (target.getZ() - getZ()) + (target.getX() - getX()) * (target.getX() - getX())) - getBbWidth() / 2F);
						
//						attacker.lookAt(target, 100, 100);
//						attacker.getLookControl().setLookAt(target, 30F, 30F);
						
						if (hitDistanceSqr <= reach && (relativeHitAngle <= 65F / 2 && relativeHitAngle >= -65F / 2) || (relativeHitAngle >= 360 - 65F / 2 || relativeHitAngle <= -360 + 65F / 2)) {
							if (targetHit == target) {
								doHurtTarget(target);
								if (target.isUsingItem() && target.getUseItem().getItem().equals(Items.SHIELD)) {
									successful = false;
									return;
								}
								for (Goal.Flag f : Goal.Flag.values()) {
									if (target instanceof MobEntity) {
										((MobEntity) target).goalSelector.disableControlFlag(Flag.MOVE);
										((MobEntity) target).goalSelector.disableControlFlag(Flag.LOOK);
									}
								}
							//	if (doHurtTarget(target)) {
									target.startRiding(entity);
									this.successful = true;
						//		}
							} else {
								doHurtTarget(targetHit);
						//		targetHit.push(0, 0.7D, 0);
							}
						} else {
							this.successful = false;
						}
					}
				}
			}
		}
	}
	
	public class HerculesBeetleMunchGoal extends AnimatableGoal {
		private boolean shouldLetGo = false;
		protected final BiFunction<Double, Double, Boolean> attackEndPredicate;
		private final double munchAnimLength;
		
		public HerculesBeetleMunchGoal(HerculesBeetleEntity beetle) {
			this.entity = beetle;
			munchAnimLength = 100;
			attackEndPredicate = (progress, length) -> ++progress >= munchAnimLength;
		}	
		
		@Override
		public boolean canUse() {
			return getAttackID() == MUNCH_ATTACK && !isInLava() && !getDocile();
		}
		
		@Override
		public boolean canContinueToUse() {
			return getTarget() != null && !this.attackEndPredicate.apply(animationProgress, munchAnimLength) && isAlive() && !isDeadOrDying() && EntityPredicates.ATTACK_ALLOWED.test(getTarget()) && hasPassenger(getTarget()) && !getDocile();
		}
		
		@Override
		public void start() {
			setAttackID(MUNCH_ATTACK);
			getNavigation().stop();
			setSpeed(0);
			this.animationProgress = 0;
		}
		
		@Override
		public void stop() {
			setAttackID((byte) 0);
			if (getTarget() != null && hasPassenger(getTarget())) {
				Vector3i facing = getDirection().getNormal();
				getTarget().stopRiding();
				getTarget().push(facing.getX() * 1.0D, 1.2D, facing.getZ() * 1.0D);
				if (getTarget() instanceof PlayerEntity) ((PlayerEntity) getTarget()).hurtMarked = true;
			}
			this.animationProgress = 0;
		}
		
		@Override
		public void tick() {
			this.baseTick();
			
			LivingEntity curTarget = getTarget();
			if (!this.attackEndPredicate.apply(animationProgress, munchAnimLength)) {
				getNavigation().stop();
				if (!isOnGround()) {
					setDeltaMovement(0, getDeltaMovement().y, 0);
					setSpeed(0);
				} else {
					setDeltaMovement(Vector3d.ZERO);
					setSpeed(0);
				}
			}
		//	if (attackEndPredicate.apply(animationProgress, munchAnimLength)) getTarget().push(0, 1.2D, 0);
			
			if (curTarget != null) {
/*				double reach = AnimatableGoal.getAttackReachSq(entity, getTarget());
				double reachSqr = reach * reach;
				
				List<LivingEntity> targets = IUtilityHelper.getAllEntitiesAround(entity, reachSqr, reachSqr, reachSqr, 12.0D);
				
				for (LivingEntity target : targets) {
					if (hasPassenger(target)) continue;
					if (target instanceof CreatureEntity) {
						Path nPath = getNavigation().getPath();
						Vector3d newAway = RandomPositionGenerator.getLandPosAvoid((CreatureEntity) target, 12, 4, target.position());
						if (newAway != null) {
							BlockPos targetPos = new BlockPos(newAway.x(), newAway.y(), newAway.z());
							if (!getNavigation().isDone()) getNavigation().stop();
							nPath = getNavigation().createPath(targetPos, 0);
							if (nPath != null) getNavigation().moveTo(nPath, 1.3D);
						}
					}					
					
				}*/
				for (Entity e : getPassengers()) {
					if (tickCount % 2 == 0) {
						doHurtTarget(e);
					}
				}
			}
		}
	}
	
	public class HerculesBeetleFlapRandomlyGoal extends AnimatableGoal {
		protected final BiFunction<Double, Double, Boolean> attackEndPredicate;
		protected final BiFunction<Double, Double, Boolean> flapPredicate;
		private final double flapAnimLength;
		boolean shouldPlaySound = true;
		
		public HerculesBeetleFlapRandomlyGoal(HerculesBeetleEntity beetle) {
			this.entity = beetle;
			flapAnimLength = 146;
			attackEndPredicate = (progress, length) -> ++progress >= flapAnimLength;
			flapPredicate = (progress, length) -> ++progress >= flapAnimLength - 86 && progress <= flapAnimLength - 6;
		}

		@Override
		public boolean canUse() {
			return (getAttackID() == (byte) 0 && getHealth() > 0 && level.random.nextInt((int)getHealth()) == 0 && getHealth() <= 125.0F) || getAttackID() == FLAP_ATTACK && !getDocile();
		}

		@Override
		public boolean canContinueToUse() {
			return !attackEndPredicate.apply(animationProgress, flapAnimLength) && !isDeadOrDying() && isAlive() && !getDocile();
		}
		
		@Override
		public void start() {
			setAttackID(FLAP_ATTACK);
			getNavigation().stop();
			shouldPlaySound = true;
			animationProgress = 0;
		}
		
		@Override
		public void stop() {
			setAttackID((byte) 0);
			setNoGravity(false);
			shouldPlaySound = true;
			animationProgress = 0;
		}
		
		private void applyEffects() {
			if (level.isClientSide) return;
			List<Entity> targets = IUtilityHelper.getEntitiesAroundNoPredicate(entity, Entity.class, 8.0F, 8.0F, 8.0F, 8.0F);
			
			for (Entity target : targets) {
				if (target == entity || target == HerculesBeetleEntity.this) continue;
				
                double angle = (IUtilityHelper.getAngleBetweenEntities(entity, target) + 90) * Math.PI / 180;
                double distance = distanceTo(target) - 6;
                target.setDeltaMovement(target.getDeltaMovement().add(Math.min(1 / (distance * distance), 1) * -1 * Math.cos(angle), 0, Math.min(1 / (distance * distance), 1) * -1 * Math.sin(angle)));
                if (target instanceof PlayerEntity) target.hurtMarked = true;
                // Unneeded since hurtMarked is set to true
       //         if (target instanceof ServerPlayerEntity) ((ServerPlayerEntity) target).connection.send(new SEntityVelocityPacket(entity));
			}
			
	/*		for (int i = 0; i < 360; i++) {
				if (i % 45 == 0) {
					//TODO Add particles
				}
			}*/
		}
		
		@Override
		public void tick() {
			this.baseTick();
			
			if (!attackEndPredicate.apply(animationProgress, flapAnimLength)) {
				getNavigation().stop();
				if (flapPredicate.apply(animationProgress, flapAnimLength) && canContinueToUse()) {
					if (shouldPlaySound) {
						playSound(CASoundEvents.HERCULES_BEETLE_FLAP.get(), 0.6F, 0.9F);
						shouldPlaySound = false;
					}
			//		setNoGravity(true);
					if (!isOnGround()) setYya(getDeltaMovement().y > 0 ? -0.2F : 0.4F);
					setDeltaMovement(Vector3d.ZERO);
					CAScreenShakeEntity.shakeScreen(level, position(), 30.0F, 0.0025F, 17, 6);	
					applyEffects();
				}
	//			yHeadRot = yBodyRotO = yBodyRot;
				setYya(0);
				setDeltaMovement(0, getDeltaMovement().y, 0);
			}
			
			if (attackEndPredicate.apply(animationProgress, flapAnimLength)) {
				setAttackID((byte) 0);
			}
		}
		
	}
	
	@Override
	public boolean doHurtTarget(Entity target) {
		if (getAttackID() == MUNCH_ATTACK) {
			setAttackDamage(7.5);
			return super.doHurtTarget(target);
		} else if (getAttackID() == GRAB_ATTACK) {
			setAttackDamage(12.5);
			return super.doHurtTarget(target);
		} else {
			setAttackDamage(25);
		}
		return super.doHurtTarget(target);
	}
	
	@Override
	public float getEyeHeight(Pose pose) {
		return super.getEyeHeight(pose) - 0.3F;
	}

	@Override
	public int tickTimer() {
		return tickCount;
	}
	
	@Override
	protected PathNavigator createNavigation(World world) {	
		return new CAGroundWaterNVPathNavigator(this, world);
	}
	
	@Override
	public void addAdditionalSaveData(CompoundNBT nbt) {
		super.addAdditionalSaveData(nbt);
	}
	
	@Override
	public void readAdditionalSaveData(CompoundNBT nbt) {
		super.readAdditionalSaveData(nbt);
	}

	@Override
	public void writeSpawnData(PacketBuffer buffer) {
		
	}

	@Override
	public void readSpawnData(PacketBuffer additionalData) {
		
	}
	
	@Override
	public IPacket<?> getAddEntityPacket() {
		return NetworkHooks.getEntitySpawningPacket(this);
	}
	
	public enum Type {
		THROWBACK("throwback"), MODERN("modern");

		private final String name;

		Type(String name) {
			this.name = name;
		}

		public String getNameString() {
			return this.name;
		}
	}
	
	public Type getBeetleType() {
		return type;
	}
	
	@Override
	public ILivingEntityData finalizeSpawn(IServerWorld world, DifficultyInstance difficulty, SpawnReason reason, ILivingEntityData data, CompoundNBT nbt) {
		
		return super.finalizeSpawn(world, difficulty, reason, data, nbt);
	}
	
}
