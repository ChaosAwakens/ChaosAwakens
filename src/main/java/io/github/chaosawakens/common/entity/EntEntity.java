package io.github.chaosawakens.common.entity;

import java.util.EnumSet;
import java.util.List;
import java.util.function.BiFunction;

import javax.annotation.Nullable;

import io.github.chaosawakens.api.IUtilityHelper;
import io.github.chaosawakens.common.config.CACommonConfig;
import io.github.chaosawakens.common.entity.ai.AnimatableGoal;
import io.github.chaosawakens.common.entity.ai.AnimatableMeleeGoal;
import io.github.chaosawakens.common.entity.ai.AnimatableMoveToTargetGoal;
import io.github.chaosawakens.common.entity.ai.pathfinding.CAGroundMovementController;
import io.github.chaosawakens.common.entity.ai.pathfinding.CAStrictGroundPathNavigator;
import io.github.chaosawakens.common.entity.base.AnimatableMonsterEntity;
import io.github.chaosawakens.common.entity.nonliving.CAFallingBlockEntity;
import io.github.chaosawakens.common.entity.nonliving.CAScreenShakeEntity;
import io.github.chaosawakens.common.entity.robo.RoboEntity;
import io.github.chaosawakens.common.network.PacketHandler;
import io.github.chaosawakens.common.network.PacketThrowFluidParticle;
import io.github.chaosawakens.common.registry.CASoundEvents;
import io.github.chaosawakens.common.registry.CAStructures;
import io.github.chaosawakens.common.util.EnumUtils.AITargetingSystemType;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.block.material.Material;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.ILivingEntityData;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.MobEntity;
import net.minecraft.entity.SpawnReason;
import net.minecraft.entity.ai.attributes.AttributeModifierMap;
import net.minecraft.entity.ai.attributes.Attributes;
import net.minecraft.entity.ai.goal.Goal;
import net.minecraft.entity.ai.goal.HurtByTargetGoal;
import net.minecraft.entity.ai.goal.NearestAttackableTargetGoal;
import net.minecraft.entity.ai.goal.WaterAvoidingRandomWalkingGoal;
import net.minecraft.entity.merchant.villager.VillagerEntity;
import net.minecraft.entity.passive.IFlyingAnimal;
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
import net.minecraft.particles.BlockParticleData;
import net.minecraft.particles.ParticleTypes;
import net.minecraft.pathfinding.PathNavigator;
import net.minecraft.util.DamageSource;
import net.minecraft.util.EntityPredicates;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.SoundEvent;
import net.minecraft.util.SoundEvents;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.vector.Vector3d;
import net.minecraft.world.DifficultyInstance;
import net.minecraft.world.IServerWorld;
import net.minecraft.world.World;
import net.minecraft.world.gen.feature.structure.StructureManager;
import net.minecraft.world.server.ServerWorld;
import net.minecraftforge.fml.common.registry.IEntityAdditionalSpawnData;
import net.minecraftforge.fml.network.NetworkHooks;
import net.minecraftforge.fml.network.PacketDistributor;
import software.bernie.geckolib3.core.AnimationState;
import software.bernie.geckolib3.core.IAnimatable;
import software.bernie.geckolib3.core.PlayState;
import software.bernie.geckolib3.core.builder.AnimationBuilder;
import software.bernie.geckolib3.core.controller.AnimationController;
import software.bernie.geckolib3.core.event.predicate.AnimationEvent;
import software.bernie.geckolib3.core.manager.AnimationData;
import software.bernie.geckolib3.core.manager.AnimationFactory;

public class EntEntity extends AnimatableMonsterEntity implements IEntityAdditionalSpawnData {
	private final AnimationFactory factory = new AnimationFactory(this);
	private final Types entType;
	public static final DataParameter<Byte> ATTACK_ID = EntityDataManager.defineId(EntEntity.class, DataSerializers.BYTE);
	public static final byte PUNCH_ATTACK = 1;
	public static final byte SMASH_ATTACK = 2;
	private final AnimationController<?> controller = new AnimationController<>(this, "entcontroller", animationInterval(), this::predicate);
	private final AnimationController<?> deathController = new AnimationController<>(this, "deathentcontroller", animationInterval(), this::deathPredicate);
	private int smashInterval = 20;
	private boolean hitSmash = false;
	
	public EntEntity(EntityType<? extends AnimatableMonsterEntity> type, World worldIn, Types entType) {
		super(type, worldIn);
		this.noCulling = true;
		this.entType = entType;
		this.moveControl = new CAGroundMovementController(this, 90);
		this.maxUpStep = 1.5F;
	}

	public static AttributeModifierMap.MutableAttribute setCustomAttributes() {
		return MobEntity.createLivingAttributes()
				.add(Attributes.MAX_HEALTH, 150)
				.add(Attributes.ARMOR, 8)
				.add(Attributes.MOVEMENT_SPEED, 0.15D)
				.add(Attributes.KNOCKBACK_RESISTANCE, 0.5D)
	//			.add(Attributes.ATTACK_SPEED, 10)
				.add(Attributes.ATTACK_DAMAGE, 15)
				.add(Attributes.ATTACK_KNOCKBACK, 3.5D)
				.add(Attributes.FOLLOW_RANGE, 54);
	}
	
	public <E extends IAnimatable> PlayState deathPredicate(AnimationEvent<E> event) {
		if (this.isDeadOrDying() || (this.getAttacking() && this.isDeadOrDying()) || this.getHealth() <= 0) {
			if (getAttackID() != (byte) 0) setAttackID((byte) 0);
			event.getController().setAnimation(new AnimationBuilder().addAnimation("animation.ent.death_animation", true));		
			event.getController().setAnimationSpeed(0.8D);
			return PlayState.CONTINUE;
		}
		return PlayState.CONTINUE;
	}

	@Override
	public <E extends IAnimatable> PlayState predicate(AnimationEvent<E> event) {
		if (this.getAttacking() && this.getAttackID() == PUNCH_ATTACK) {
			event.getController().setAnimation(new AnimationBuilder().addAnimation("animation.ent.attacking_animation", true));
			return PlayState.CONTINUE;
		}
		if (this.getAttacking() && this.getAttackID() == SMASH_ATTACK) {
			event.getController().setAnimation(new AnimationBuilder().addAnimation("animation.ent.smash_attack", false));
			return PlayState.CONTINUE;
		}
		if (event.isMoving()) {
			event.getController().setAnimation(new AnimationBuilder().addAnimation("animation.ent.walking_animation", true));
			return PlayState.CONTINUE;
		}
		event.getController().setAnimation(new AnimationBuilder().addAnimation("animation.ent.idle_animation", true));
		return PlayState.CONTINUE;
	}
	
	@Override
	protected float getJumpPower() {
		return 0;
	}
	
	@Override
	protected void tickDeath() {
		++this.deathTime;
		if (this.deathTime == 60) {
			this.remove();
		}
	}

	@Override
	protected void registerGoals() {
		this.targetSelector.addGoal(0, new AnimatableMoveToTargetGoal(this, 1.6, 3));
		this.targetSelector.addGoal(0, new EntPunchGoal(this, 29.3D, 0.67D, 0.71D));
		this.targetSelector.addGoal(0, new EntSmashAttack(this));
		this.targetSelector.addGoal(0, new HurtByTargetGoal(this));
		this.targetSelector.addGoal(1, new NearestAttackableTargetGoal<>(this, PlayerEntity.class, true));
		this.targetSelector.addGoal(1, new NearestAttackableTargetGoal<>(this, IronGolemEntity.class, true));
		this.targetSelector.addGoal(1, new NearestAttackableTargetGoal<>(this, SnowGolemEntity.class, true));
		this.targetSelector.addGoal(1, new NearestAttackableTargetGoal<>(this, RoboEntity.class, true));
		this.targetSelector.addGoal(1, new NearestAttackableTargetGoal<>(this, VillagerEntity.class, true));
		this.goalSelector.addGoal(2, new WaterAvoidingRandomWalkingGoal(this, 1.0D, 8.0F));
	}
	
	@Override
	protected void defineSynchedData() {
		super.defineSynchedData();
		this.entityData.define(ATTACK_ID, (byte) 0);
	}
	
	public byte getAttackID() {
		return this.entityData.get(ATTACK_ID);
	}
	
	public void setAttackID(byte attackID) {
		this.entityData.set(ATTACK_ID, attackID);
	}

	@Override
	public ILivingEntityData finalizeSpawn(IServerWorld worldIn, DifficultyInstance difficultyIn, SpawnReason reason, @Nullable ILivingEntityData spawnDataIn, @Nullable CompoundNBT dataTag) {
		if (worldIn instanceof ServerWorld) {
			BlockPos pos = this.getOnPos();
			StructureManager strucManager = worldIn.getLevel().structureFeatureManager();

			boolean inDungeon = strucManager.getStructureAt(pos, false, CAStructures.ACACIA_ENT_TREE.get()).isValid()
					|| strucManager.getStructureAt(pos, false, CAStructures.BIRCH_ENT_TREE.get()).isValid()
					|| strucManager.getStructureAt(pos, false, CAStructures.CRIMSON_ENT_TREE.get()).isValid()
					|| strucManager.getStructureAt(pos, false, CAStructures.DARK_OAK_ENT_TREE.get()).isValid()
					|| strucManager.getStructureAt(pos, false, CAStructures.JUNGLE_ENT_TREE.get()).isValid()
					|| strucManager.getStructureAt(pos, false, CAStructures.OAK_ENT_TREE.get()).isValid()
					|| strucManager.getStructureAt(pos, false, CAStructures.SPRUCE_ENT_TREE.get()).isValid()
					|| strucManager.getStructureAt(pos, false, CAStructures.WARPED_ENT_TREE.get()).isValid();
//			if (!inDungeon || reason != SpawnReason.STRUCTURE) this.goalSelector.addGoal(7, new RandomWalkingGoal(this, 1.6));
		}
		return super.finalizeSpawn(worldIn, difficultyIn, reason, spawnDataIn, dataTag);
	}

	@Override
	public boolean doHurtTarget(Entity target) {
		if (getAttackID() == SMASH_ATTACK) {
			if (target instanceof PlayerEntity) {
				PlayerEntity player = (PlayerEntity) target;
				
				if (player.getOffhandItem().getItem().equals(Items.SHIELD) || player.getMainHandItem().getItem().equals(Items.SHIELD)) {
					player.disableShield(true);
				}
	//			player.setDeltaMovement(new Vector3d(player.getDeltaMovement().x, 0.7D, player.getDeltaMovement().z));
			}
			
//			target.setDeltaMovement(new Vector3d(target.getDeltaMovement().x, 0.7D, target.getDeltaMovement().z));
		}	
		return super.doHurtTarget(target);

    }
	
	
	@Override
	protected PathNavigator createNavigation(World world) {	
		return new CAStrictGroundPathNavigator(this, world);
	}
    
    @Override
    public void aiStep() {
    	
		if (getTarget() != null) {
			if ((getAttackID() != 0) && isAlive()) {
			//	this.setSpeed(0);
/*				if (this.isOnGround()) {
					this.setDeltaMovement(new Vector3d(0, 0, 0));
				}*/
			//	this.setDeltaMovement(0, 0, 0);
				this.lookAt(getTarget(), 100, 100);
				this.getLookControl().setLookAt(getTarget(), 30F, 30F);
			//	int maxXRot = this.getMaxHeadXRot();
			//	maxXRot = 1;
			}
		}
    	
    	super.aiStep();
   // 	ChaosAwakens.debug("ATTACK", this.getAttackID());
   // 	if (this.getNavigation().getPath() != null) {
    //		ChaosAwakens.debug("DEBUG ENT PATHFINDING", ((CAPath) this.getNavigation().getPath()).toString());
    		
/*    		if (this.getNavigation().isDone() && getTarget() == null) {
    			this.getNavigation().stop();
    		}*/
  //  	}
    	
    	
    }
    
    @Override
    public void tick() {
    	super.tick();
   // 	smashInterval--;
   // 	ChaosAwakens.debug("SMASHATK", "Smash Interval: " + smashInterval);
    	
   // 	if (smashInterval <= -5 && hitSmash) smashInterval = IUtilityHelper.randomBetween(100, 400);
    	
	}

	@Override
	public void registerControllers(AnimationData data) {
		data.addAnimationController(controller);
		data.addAnimationController(deathController);
	}
	
	@Override
	public int animationInterval() {
		return 2;
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
			AITargetingSystemType type = CACommonConfig.COMMON.entTargetingSystemType.get();
			
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
	public boolean canSee(Entity target) {
		if (this.getTarget() != null && this.distanceTo(target) <= getFollowRange() && IUtilityHelper.getVerticalDistanceBetweenEntities(this, getTarget()) <= getFollowRange() / 2) return true;
		return super.canSee(target);
	}
	
	@Override
	public AnimationController<?> getController() {
		return controller;
	}
	
	@Override
	public void manageAttack(LivingEntity target) {
		
	}

	@Override
	public boolean isPersistenceRequired() {
		return true;
	}

	@Override
	public AnimationFactory getFactory() {
		return this.factory;
	}

	public enum Types {
		OAK("oak"), APPLE("apple"), ACACIA("acacia"), DARK_OAK("dark_oak"), JUNGLE("jungle"), SPRUCE("spruce"), BIRCH("birch"), CRIMSON("crimson"), WARPED("warped"), PEACH("peach"), CHERRY("cherry"), SKYWOOD("skywood");

		private final String name;

		Types(String name) {
			this.name = name;
		}

		public String getNameString() {
			return this.name;
		}
	}

	public EntEntity.Types getEntType() {
		return entType;
	}

	@Override
	protected SoundEvent getHurtSound(DamageSource p_184601_1_) {
		if (this.getEntType() == Types.ACACIA) return CASoundEvents.ACACIA_ENT_HURT.get();
		else if (this.getEntType() != Types.BIRCH) return CASoundEvents.BIRCH_ENT_HURT.get();
		else if (this.getEntType() == Types.CRIMSON) return CASoundEvents.CRIMSON_ENT_HURT.get();
		else if (this.getEntType() == Types.DARK_OAK) return CASoundEvents.DARK_OAK_ENT_HURT.get();
		else if (this.getEntType() == Types.JUNGLE) return CASoundEvents.JUNGLE_ENT_HURT.get();
		else if (this.getEntType() == Types.OAK) return CASoundEvents.OAK_ENT_HURT.get();
		else if (this.getEntType() == Types.SPRUCE) return CASoundEvents.SPRUCE_ENT_HURT.get();
		else if (this.getEntType() == Types.WARPED) return CASoundEvents.WARPED_ENT_HURT.get();
		return CASoundEvents.ENT_HURT.get();
	}

	@Override
	protected SoundEvent getDeathSound() {
		if (this.getEntType() == Types.ACACIA) return CASoundEvents.ACACIA_ENT_DEATH.get();
		else if (this.getEntType() != Types.BIRCH) return CASoundEvents.BIRCH_ENT_DEATH.get();
		else if (this.getEntType() == Types.CRIMSON) return CASoundEvents.CRIMSON_ENT_DEATH.get();
		else if (this.getEntType() == Types.DARK_OAK) return CASoundEvents.DARK_OAK_ENT_DEATH.get();
		else if (this.getEntType() == Types.JUNGLE) return CASoundEvents.JUNGLE_ENT_DEATH.get();
		else if (this.getEntType() == Types.OAK) return CASoundEvents.OAK_ENT_DEATH.get();
		else if (this.getEntType() == Types.SPRUCE) return CASoundEvents.SPRUCE_ENT_DEATH.get();
		else if (this.getEntType() == Types.WARPED) return CASoundEvents.WARPED_ENT_DEATH.get();
		return CASoundEvents.ENT_HURT.get();
	}

	@Override
	protected void playStepSound(BlockPos p_180429_1_, BlockState p_180429_2_) {
		if (!p_180429_2_.getMaterial().isLiquid()) this.playSound(CASoundEvents.ENT_WALK.get(), this.getVoicePitch() * 0.30F, this.getSoundVolume() * 1);
	}
	
	

	@Override
	protected float getVoicePitch() {
		return 0.4F;
	}

	@Override
	protected float getSoundVolume() {
		return 1.2F;
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
	
	public class EntPunchGoal extends AnimatableMeleeGoal {

		public EntPunchGoal(EntEntity entity, double animationLength, double attackBegin, double attackEnd) {
			super(entity, animationLength, attackBegin, attackEnd);
		}
		
		@Override
		public void start() {
			super.start();
			getNavigation().stop();
			setAttackID(PUNCH_ATTACK);
		}
		
		@Override
		public void stop() {
			super.stop();
			setAttackID((byte) 0);
		}
		
		@Override
		public boolean canUse() {
			List<LivingEntity> aoeArea = IUtilityHelper.getAllEntitiesAround(entity, 16.0F, 12.0D, 16.0F, 16.0F);
			return super.canUse() && getAttackID() == (byte) 0 && level.random.nextInt(2) == 0 && !this.hasHit && aoeArea.size() < 4;
		}
		
		@Override
		public boolean canContinueToUse() {
			return super.canContinueToUse() && getAttackID() == PUNCH_ATTACK;
		}
		
		@SuppressWarnings("unused")
		public void animateAttack(AnimatableMonsterEntity attacker) {
			if (attacker.getTarget() == null) return;
			
			double reach = AnimatableGoal.getAttackReachSq(attacker, attacker.getTarget());
			double reachSqr = reach * reach;
			
			List<LivingEntity> targets = IUtilityHelper.getAllEntitiesAround(attacker, reach, reach, reach, 5.6D);
			
			for (LivingEntity target : targets) {
				float targetAngle = (float) ((Math.atan2(target.getZ() - attacker.getZ(), target.getX() - attacker.getX()) * (180 / Math.PI) - 90) % 360);
				float attackAngle = attacker.yBodyRot % 360;
				
				if (targetAngle < 0) targetAngle += 360;
				if (attackAngle < 0) attackAngle += 360;
				
				float relativeHitAngle = targetAngle - attackAngle;
				float hitDistanceSqr = (float) (Math.sqrt((target.getZ() - attacker.getZ()) * (target.getZ() - attacker.getZ()) + (target.getX() - attacker.getX()) * (target.getX() - attacker.getX())) - attacker.getBbWidth() / 2F);
				
//				attacker.lookAt(target, 100, 100);
//				attacker.getLookControl().setLookAt(target, 30F, 30F);
				
				if (hitDistanceSqr <= reach && (relativeHitAngle <= 48F / 2 && relativeHitAngle >= -48F / 2) || (relativeHitAngle >= 360 - 48F / 2 || relativeHitAngle <= -360 + 48F / 2)) {
					attacker.doHurtTarget(target);
					this.hasHit = true;
				}
			}
		}
		
		@Override
		public void tick() {			
			this.baseTick();
			setAttackID(PUNCH_ATTACK);
			
			if (attackEndPredicate.apply(animationProgress, animationLength)) {
				if (!isOnGround()) {
					setDeltaMovement(0, getDeltaMovement().y, 0);
					setSpeed(0);
				} else {
					setDeltaMovement(Vector3d.ZERO);
					setSpeed(0);
				}
			}
			
			LivingEntity target = this.entity.getTarget();
			
			if (target != null) {					
				if (!this.attackPredicate.apply(this.animationProgress, this.animationLength) && !this.hasHit) {
					lookAt(target, 200, 200);
					getLookControl().setLookAt(target, 30F, 30F);
				}
				
				if (this.attackPredicate.apply(this.animationProgress, this.animationLength) && !this.hasHit) {
					setDeltaMovement(0, 0, 0);
					animateAttack(this.entity);
				}

				if (this.animationProgress >= this.animationLength) {
					this.animationProgress = 0;
					this.hasHit = false;
					setAttackID((byte) 0);
				}
			}
		}
		
	}
	
	public class EntSmashAttack extends AnimatableGoal {
		protected final BiFunction<Double, Double, Boolean> attackEndPredicate;
		protected final BiFunction<Double, Double, Boolean> attackSmashPredicate;
		private final double animationLengthEntSmash;
		private boolean firstLoopSafetyCheck = true;
		
		public EntSmashAttack(EntEntity ent) {
			this.entity = ent;
			animationLengthEntSmash = 24.34D;
			attackEndPredicate = (progress, length) -> ++progress <= animationLengthEntSmash && progress != -1;
			attackSmashPredicate = (progress, length) -> ++progress >= animationLengthEntSmash - 0.44D && progress <= animationLengthEntSmash + 0.44D;
			this.setFlags(EnumSet.of(Goal.Flag.LOOK));
		}

		@Override
		public boolean canUse() {
			if (getTarget() == null) return false;
			List<LivingEntity> aoeArea = IUtilityHelper.getAllEntitiesAround(entity, 16.0F, 12.0D, 16.0F, 16.0F);
			return getTarget() != null && getTarget() instanceof IFlyingAnimal ? getTarget() != null : getTarget().isOnGround() && distanceTo(getTarget()) <= 12.0F && getAttackID() == (byte) 0 && ((level.random.nextInt(5) == 1) || aoeArea.size() >= 4) && !isInWater() && !isInLava() && firstLoopSafetyCheck && distanceTo(getTarget()) <= getFollowRange() && getController().getAnimationState() != AnimationState.Transitioning;
		}

		@Override
		public boolean canContinueToUse() {
			return this.attackEndPredicate.apply(this.animationProgress, animationLengthEntSmash) && getAttackID() == SMASH_ATTACK;
		}
		
		@Override
		public void start() {
			setAttackID(SMASH_ATTACK);
			setAttacking(true);
			setAggressive(false);
			firstLoopSafetyCheck = false;
			hitSmash = false;
			setSpeed(0);
			this.animationProgress = 0;
		}
		
		@Override
		public void stop() {
			setAttackID((byte) 0);
			setAttacking(false);
			setAggressive(false);
			if (!EntityPredicates.NO_CREATIVE_OR_SPECTATOR.test(getTarget())) setTarget(null);
			setSpeed((float) entity.getAttributeValue(Attributes.MOVEMENT_SPEED));
			
		//	int interval = 40;
			
		//	if (--interval == 0) firstLoopSafetyCheck = true;
			firstLoopSafetyCheck = true;
			hitSmash = true;
			
			this.animationProgress = 0;
		}
		
		private void doSmash(AnimatableMonsterEntity entity) {
			if (getTarget() == null) return;
			LivingEntity target = getTarget();
			
			List<LivingEntity> aoeArea = IUtilityHelper.getAllEntitiesAround(entity, 8.0F, 12.0D, 8.0F, 8.0F);
			
			for (LivingEntity t : aoeArea) {
		//		if (attackSmashPredicate.apply(this.animationProgress, animationLengthEntSmash)) {
					((EntEntity) entity).doHurtTarget(t);
					t.push(0, 0.5D, 0);
			//		target.push(0, 0.5D, 0);
					
					AxisAlignedBB axisalignedbb = getBoundingBox().inflate(3.0D);
					
					double x = this.entity.getTarget().getX() - entity.getX();
					double z = this.entity.getTarget().getX() - entity.getZ();
					double d = Math.sqrt(x * x + z * z);
					double dirX = x / d;
					double dirZ = z / d;
					
					int xp = MathHelper.floor(entity.getX() + dirX * 2.0D);
					int zp = MathHelper.floor(entity.getZ() + dirZ * 2.0D);
					int x1 = MathHelper.floor(entity.getX() + dirX * 8.0D);
					int z1 = MathHelper.floor(entity.getZ() + dirZ * 8.0D);
					
					boolean b = level.random.nextInt() % 8 == 0;
					
					for(BlockPos blockpos1 : BlockPos.betweenClosed(MathHelper.floor(axisalignedbb.minX), MathHelper.floor(axisalignedbb.minY + 1), MathHelper.floor(axisalignedbb.minZ), MathHelper.floor(axisalignedbb.maxX), MathHelper.floor(axisalignedbb.minY + 2), MathHelper.floor(axisalignedbb.maxZ))) {							
						if (!level.isClientSide) {
							
							float targetAngle = (float) ((Math.atan2(target.getZ() - entity.getZ(), target.getX() - entity.getX()) * (180 / Math.PI) - 90) % 360);
							float attackAngle = entity.yBodyRot % 360;
							
							if (targetAngle < 0) targetAngle += 360;
							if (attackAngle < 0) attackAngle += 360;
							
							float relativeHitAngle = targetAngle - attackAngle;
							float hitDistanceSqr = (float) (Math.sqrt((target.getZ() - entity.getZ()) * (target.getZ() - entity.getZ()) + (target.getX() - entity.getX()) * (target.getX() - entity.getX())) - entity.getBbWidth() / 2F);
							
							if (hitDistanceSqr <= 16.0F && (relativeHitAngle <= 60F / 2 && relativeHitAngle >= -60F / 2) || (relativeHitAngle >= 360 - 60F / 2 || relativeHitAngle <= -360 + 60F / 2)) {						
								((ServerWorld)level).sendParticles(new BlockParticleData(ParticleTypes.BLOCK, level.getBlockState(blockpos1)), (double)blockpos1.getX() + 0.5D, (double)blockpos1.getY() + 0.7D, (double)blockpos1.getZ() + 0.5D, 3, ((double)random.nextFloat() - 0.5D) * 0.08D, ((double)random.nextFloat() - 0.5D) * 0.08D, ((double)random.nextFloat() - 0.5D) * 0.08D, (double)0.15F);							
							}
							
							BlockPos pos = new BlockPos(blockpos1);
				//			ChaosAwakens.debug("POS", pos.toString());
							BlockState targetState = level.getBlockState(pos.below());
			/*				if (targetState.getMaterial() != Material.AIR && targetState.getBlock() != Blocks.AIR && !targetState.getBlock().hasTileEntity(targetState) && !level.getBlockState(pos.above()).getMaterial().blocksMotion()) {
								CAFallingBlockEntity e = new CAFallingBlockEntity(level, pos.getX() + 0.5D, pos.getY() + 2.0D, pos.getZ() + 0.5D, targetState);	
								e.push(0, 0.2D + random.nextGaussian() * 0.15D, 0);
								level.addFreshEntity((Entity) e);
							}*/
							
						}
						if (b) PacketHandler.CHANNEL.send(PacketDistributor.NEAR.with(() -> new PacketDistributor.TargetPoint(getX(), getY(), getZ(), 1024.0D, entity.level.dimension())), new PacketThrowFluidParticle(level.getBlockState(blockpos1), blockpos1));
					}
					
			}
		}
		
		@Override
		public void tick() {
			this.baseTick();
			setAttackID(SMASH_ATTACK);
			
			if (attackEndPredicate.apply(animationProgress, animationLengthEntSmash)) {
				if (!isOnGround()) {
					setDeltaMovement(0, getDeltaMovement().y, 0);
					setSpeed(0);
				} else {
					setDeltaMovement(Vector3d.ZERO);
					setSpeed(0);
				}
			}
			
			LivingEntity target = getTarget();
	//		ChaosAwakens.debug("SMASHATK", (this.animationProgress) + " progress || " + (this.animationLengthEntSmash) + " length");
			
			if (target != null) {
				if (!this.attackSmashPredicate.apply(this.animationProgress, this.animationLengthEntSmash)) {
					lookAt(target, 100, 100);
					getLookControl().setLookAt(target, 30F, 30F);
				}
				
				if (this.attackSmashPredicate.apply(this.animationProgress, this.animationLengthEntSmash)) {
					level.playSound((PlayerEntity) null, blockPosition(), SoundEvents.GENERIC_EXPLODE, SoundCategory.HOSTILE, 1.0F, 0.8F);
					CAScreenShakeEntity.shakeScreen(level, position(), 15.0F, 0.15F, 4, 20);
					doSmash(entity);
					BlockPos blockpos1 = getBlockPosBelowThatAffectsMyMovement();
					BlockState targetState = level.getBlockState(blockpos1);
;					if (targetState.getMaterial() != Material.AIR && targetState.getBlock() != Blocks.AIR && !targetState.getBlock().hasTileEntity(targetState) && !level.getBlockState(blockpos1.above()).getMaterial().blocksMotion()) {
						CAFallingBlockEntity e = new CAFallingBlockEntity(level, 100, blockpos1.getX() + 0.5D, blockpos1.getY() + 3.0D, blockpos1.getZ() + 0.5D, targetState);	
						e.push(0, 0.2D + random.nextGaussian() * 0.15D, 0);
						level.addFreshEntity((Entity) e);
					}
				}
								
				if (!this.attackEndPredicate.apply(animationProgress, animationLengthEntSmash)) {
					this.animationProgress = -1;
					this.animationProgress = 0;
					hitSmash = true;
					setAttackID((byte) 0);
		//			firstLoopSafetyCheck = false;
				}
				
			}
		}
		
	}

	@Override
	public int tickTimer() {
		return tickCount;
	}
}