package io.github.chaosawakens.common.entity.robo;

import java.util.ArrayList;
import java.util.EnumSet;
import java.util.List;
import java.util.function.BiFunction;

import io.github.chaosawakens.ChaosAwakens;
import io.github.chaosawakens.api.IUtilityHelper;
import io.github.chaosawakens.common.config.CACommonConfig;
import io.github.chaosawakens.common.entity.ai.AnimatableGoal;
import io.github.chaosawakens.common.entity.ai.AnimatableMeleeGoal;
import io.github.chaosawakens.common.entity.ai.AnimatableMoveToTargetGoal;
import io.github.chaosawakens.common.entity.ai.CAWalkNodeProcessor;
import io.github.chaosawakens.common.entity.ai.pathfinding.CAGroundMovementController;
import io.github.chaosawakens.common.entity.ai.pathfinding.CAPathFinder;
import io.github.chaosawakens.common.entity.ai.pathfinding.CAStrictGroundPathNavigator;
import io.github.chaosawakens.common.entity.base.AnimatableMonsterEntity;
import io.github.chaosawakens.common.registry.CAEntityTypes;
import io.github.chaosawakens.common.registry.CAParticleTypes;
import io.github.chaosawakens.common.util.EnumUtils.AITargetingSystemType;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.command.arguments.EntityAnchorArgument.Type;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.MobEntity;
import net.minecraft.entity.ai.attributes.AttributeModifierMap;
import net.minecraft.entity.ai.attributes.Attributes;
import net.minecraft.entity.ai.goal.HurtByTargetGoal;
import net.minecraft.entity.ai.goal.NearestAttackableTargetGoal;
import net.minecraft.entity.ai.goal.WaterAvoidingRandomWalkingGoal;
import net.minecraft.entity.merchant.villager.VillagerEntity;
import net.minecraft.entity.monster.MonsterEntity;
import net.minecraft.entity.passive.AnimalEntity;
import net.minecraft.entity.passive.IronGolemEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.entity.projectile.ProjectileEntity;
import net.minecraft.fluid.Fluid;
import net.minecraft.fluid.FluidState;
import net.minecraft.inventory.EquipmentSlotType;
import net.minecraft.item.Items;
import net.minecraft.item.TieredItem;
import net.minecraft.network.IPacket;
import net.minecraft.network.PacketBuffer;
import net.minecraft.network.datasync.DataParameter;
import net.minecraft.network.datasync.DataSerializers;
import net.minecraft.network.datasync.EntityDataManager;
import net.minecraft.particles.ParticleTypes;
import net.minecraft.pathfinding.NodeProcessor;
import net.minecraft.pathfinding.Path;
import net.minecraft.pathfinding.PathFinder;
import net.minecraft.pathfinding.PathNavigator;
import net.minecraft.pathfinding.PathNodeType;
import net.minecraft.pathfinding.PathType;
import net.minecraft.tags.FluidTags;
import net.minecraft.util.DamageSource;
import net.minecraft.util.EntityPredicates;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.vector.Vector3d;
import net.minecraft.util.math.vector.Vector3i;
import net.minecraft.world.Difficulty;
import net.minecraft.world.IBlockReader;
import net.minecraft.world.World;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.ForgeEventFactory;
import net.minecraftforge.event.entity.living.LivingAttackEvent;
import net.minecraftforge.fml.common.registry.IEntityAdditionalSpawnData;
import net.minecraftforge.fml.network.NetworkHooks;
import software.bernie.geckolib3.core.AnimationState;
import software.bernie.geckolib3.core.IAnimatable;
import software.bernie.geckolib3.core.PlayState;
import software.bernie.geckolib3.core.builder.AnimationBuilder;
import software.bernie.geckolib3.core.controller.AnimationController;
import software.bernie.geckolib3.core.event.predicate.AnimationEvent;
import software.bernie.geckolib3.core.manager.AnimationData;
import software.bernie.geckolib3.core.manager.AnimationFactory;

//TODO Re-factor almost all the code here (and for a bunch of other entities/AI), because right now it's functional, but messy AF
//Junior dev shit --Meme Man
@SuppressWarnings("all")
public class RoboPounderEntity extends RoboEntity implements IEntityAdditionalSpawnData {
	private final AnimationFactory factory = new AnimationFactory(this);
	private final AnimationController<?> controller = new AnimationController<>(this, "robopoundercontroller", animationInterval(), this::predicate);
	private final AnimationController<?> secondaryController = new AnimationController<>(this, "robopoundersecondcontroller", animationInterval(), this::secondaryPredicate);
	public static final DataParameter<Byte> ATTACK_ID = EntityDataManager.defineId(RoboPounderEntity.class, DataSerializers.BYTE);
	public static final DataParameter<Byte> DISABLE_RAGE_REASON = EntityDataManager.defineId(RoboPounderEntity.class, DataSerializers.BYTE);
	public static final DataParameter<Integer> RAGE_DAMAGE = EntityDataManager.defineId(RoboPounderEntity.class, DataSerializers.INT);
	public static final byte PUNCH_ATTACK = 1;
	public static final byte PUNCH_ATTACK_MIRROR = 10;
	public static final byte SIDE_SWEEP_ATTACK = 2;
	public static final byte SIDE_SWEEP_ATTACK_MIRROR = 20;
	public static final byte RAGE_RUN_ATTACK = 3;
	public static final byte RAGE_ENABLE = 4;
	public static final byte RAGE_DISABLE = 5;
	public static final byte RAGE_CRASH = 6;
	public static final byte RAGE_REVIVE = 7;
	public static final byte NOT_STOPPED = 12;
	public static final byte DISABLED = 13;
	public static final byte CRASHED = 14;
	protected int punchCooldown = 0;
	protected int sideSweepCooldown = 0;
	int rageRunTime = 0;
	protected boolean shouldCombo;
	List<Block> horizontalBlockBlacklist = new ArrayList<>();
	List<Block> verticalBlockBlacklist = new ArrayList<>();
	
	public RoboPounderEntity(EntityType<? extends MonsterEntity> type, World worldIn) {
		super(type, worldIn);
		this.noCulling = true;
		this.moveControl = new RoboPounderMoveController(this, 90);
		this.maxUpStep = 1.8F;
		this.setPathfindingMalus(PathNodeType.WATER, -1.0F);
		this.setPathfindingMalus(PathNodeType.WATER_BORDER, -1.0F);
		this.setPathfindingMalus(PathNodeType.LAVA, -1.0F);
	}

	public static AttributeModifierMap.MutableAttribute setCustomAttributes() {
		return MobEntity.createLivingAttributes()
				.add(Attributes.MAX_HEALTH, 300)
				.add(Attributes.ARMOR, 30)
				.add(Attributes.ARMOR_TOUGHNESS, 20)
				.add(Attributes.MOVEMENT_SPEED, 0.2D)
				.add(Attributes.KNOCKBACK_RESISTANCE, 360.0D)
				.add(Attributes.ATTACK_DAMAGE, 20)
				.add(Attributes.ATTACK_KNOCKBACK, 200)
				.add(Attributes.FOLLOW_RANGE, 69);
	}
	
	@Override
	protected float getJumpPower() {
		return 0;
	}
	
	@Override
	protected void defineSynchedData() {
		super.defineSynchedData();
		entityData.define(ATTACK_ID, (byte) 0);
		entityData.define(DISABLE_RAGE_REASON, NOT_STOPPED);
		entityData.define(RAGE_DAMAGE, 0);
	}
	
	public byte getAttackID() {
		return entityData.get(ATTACK_ID);
	}
	
	public void setAttackID(byte attackID) {
		entityData.set(ATTACK_ID, attackID);
	}
	
	public byte getRageDisableReason() {
		return entityData.get(DISABLE_RAGE_REASON);
	}
	
	public void setRageDisableReason(byte reason) {
		entityData.set(DISABLE_RAGE_REASON, reason);
	}
	
	public int getRageDamage() {
		return entityData.get(RAGE_DAMAGE);
	}
	
	public void setRageDamage(int damage) {
		entityData.set(RAGE_DAMAGE, damage);
	}
	
	@Override
	public void handleEntityEvent(byte status) {
		super.handleEntityEvent(status);
	}
	
	private <E extends IAnimatable> PlayState secondaryPredicate(AnimationEvent<E> event) {
		if (getAttackID() != RAGE_CRASH && getAttackID() != RAGE_DISABLE && getAttackID() != RAGE_ENABLE && getAttackID() != RAGE_REVIVE && getAttackID() != RAGE_RUN_ATTACK && this.level.random.nextInt(5) == 0) {
			event.getController().setAnimation(new AnimationBuilder().addAnimation("animation.robo_pounder.ambient", false));
			return PlayState.CONTINUE;
		}
		if (event.isMoving() && (this.isInWater() || this.isInLava()) && getAttackID() == 0 && this.isAlive()) {
			event.getController().setAnimationSpeed(0.3D);
			return PlayState.CONTINUE;
		}
		return PlayState.CONTINUE;
	}

	@Override
	public <E extends IAnimatable> PlayState predicate(AnimationEvent<E> event) {
		if (this.isDeadOrDying() || this.getHealth() <= 0) {
			event.getController().setAnimation(new AnimationBuilder().addAnimation("animation.robo_pounder.death", false));
			event.getController().setAnimationSpeed(0.09D);
			// Always time the animation speed setters correctly, since animation transitioning ticks > 0
			if (event.getController().getAnimationState() == AnimationState.Running) {
				double curAnimTick = event.getAnimationTick();
				if (curAnimTick == 14) {
					for (int i = 0; i < 100; i++) {
						if (i < 99) {
							event.getController().setAnimationSpeed(0.12D);
						}
					}
				}
				
				if (curAnimTick > 14 && curAnimTick < 35) {
					event.getController().setAnimationSpeed(1.5D);
				}
				
				if (curAnimTick == 35) {
					for (int i = 0; i < 100; i++) {
						if (i < 99) {
							event.getController().setAnimationSpeed(0.12D);
						}
					}
				}
				
				if (event.getAnimationTick() > 35) {
					event.getController().setAnimationSpeed(1.25D);
				}
			}
			return PlayState.CONTINUE;
		}
		if (this.getAttackID() == PUNCH_ATTACK) {
			event.getController().setAnimation(new AnimationBuilder().addAnimation("animation.robo_pounder.swing_arm_attack", true));
			event.getController().setAnimationSpeed(0.8D);
			return PlayState.CONTINUE;
		}
		if (this.getAttackID() == PUNCH_ATTACK_MIRROR) {
			event.getController().setAnimation(new AnimationBuilder().addAnimation("animation.robo_pounder.swing_arm_attack_mirrored", true));
			event.getController().setAnimationSpeed(0.8D);
			return PlayState.CONTINUE;
		}
		if (this.getAttackID() == SIDE_SWEEP_ATTACK) {
			event.getController().setAnimation(new AnimationBuilder().addAnimation("animation.robo_pounder.second_swing_arm_attack", true));
			event.getController().setAnimationSpeed(1.25D);
			return PlayState.CONTINUE;
		}
		if (this.getAttackID() == SIDE_SWEEP_ATTACK_MIRROR) {
			event.getController().setAnimation(new AnimationBuilder().addAnimation("animation.robo_pounder.second_swing_arm_attack_mirrrored", true));
			event.getController().setAnimationSpeed(1.25D);
			return PlayState.CONTINUE;
		}
		if (getAttackID() == RAGE_ENABLE) {
			event.getController().setAnimation(new AnimationBuilder().addAnimation("animation.robo_pounder.rage_mode_enable", false));
			return PlayState.CONTINUE;
		}
        if (getAttackID() == RAGE_DISABLE) {
        	event.getController().setAnimation(new AnimationBuilder().addAnimation("animation.robo_pounder.rage_mode_disable", false)); 
        	return PlayState.CONTINUE;
        }
        if (getAttackID() == RAGE_CRASH) {
        	event.getController().setAnimation(new AnimationBuilder().addAnimation("animation.robo_pounder.rage_crash", false)); 
        	return PlayState.CONTINUE;
        }
        if (getAttackID() == RAGE_REVIVE) {
        	event.getController().setAnimation(new AnimationBuilder().addAnimation("animation.robo_pounder.rage_revive", false)); 
        	return PlayState.CONTINUE;
        }
		if (getAttackID() == RAGE_RUN_ATTACK) {
			event.getController().setAnimation(new AnimationBuilder().addAnimation("animation.robo_pounder.rage_run", true));
			return PlayState.CONTINUE;
		}
		if (event.isMoving() && getAttackID() == (byte) 0) {
			event.getController().setAnimation(new AnimationBuilder().addAnimation("animation.robo_pounder.walk", true));
			
			if (!isInWater() && !isInLava() && getAttackID() == (byte) 0) {
				event.getController().setAnimationSpeed(1.0D);
			}
			
			return PlayState.CONTINUE;
		}
		event.getController().setAnimation(new AnimationBuilder().addAnimation("animation.robo_pounder.idle", true));
		return PlayState.CONTINUE;
	}

	@Override
	protected void registerGoals() {
		this.targetSelector.addGoal(0, new RoboPounderMoveToTargetGoal(this, 1, 3));
		this.targetSelector.addGoal(0, new RoboPounderPunchGoal(this, 37.15D, 0.7D, 0.78D));
		this.targetSelector.addGoal(0, new RoboPounderSideSweepGoal(this, 17.1D, 0.60, 0.7));
		this.targetSelector.addGoal(0, new RoboPounderRageActivationGoal(this));
		this.targetSelector.addGoal(0, new RoboPounderRageRunGoal(this));
		this.targetSelector.addGoal(0, new RoboPounderRageDisableGoal(this));
		this.targetSelector.addGoal(0, new RoboPounderRageCrashGoal(this));
		this.targetSelector.addGoal(0, new RoboPounderRageReviveGoal(this));
		this.targetSelector.addGoal(1, new NearestAttackableTargetGoal<>(this, PlayerEntity.class, 0, true, true, null));
		this.targetSelector.addGoal(1, new NearestAttackableTargetGoal<>(this, VillagerEntity.class, 0, true, true, null));
		this.targetSelector.addGoal(1, new NearestAttackableTargetGoal<>(this, IronGolemEntity.class, 0, true, true, null));
		this.targetSelector.addGoal(1, new NearestAttackableTargetGoal<>(this, AnimalEntity.class, 0, true, true, null));
		this.targetSelector.addGoal(1, new HurtByTargetGoal(this).setAlertOthers(RoboPounderEntity.class));
//		this.targetSelector.addGoal(1, new MoveThroughVillageGoal(this, 1.25, false, 5, () -> true));
		this.goalSelector.addGoal(2, new WaterAvoidingRandomWalkingGoal(this, 1.0D, 8.0F));
//		if (getTarget() == null) this.goalSelector.addGoal(3, new LookAtGoal(this, PlayerEntity.class, 8.0F, 0.001F));
//		this.goalSelector.addGoal(6, new LookRandomlyGoal(this));
	}
	
	@OnlyIn(Dist.CLIENT)
	protected void sinkInWater() {
		this.setDeltaMovement(this.getDeltaMovement().add(0.0D, (double)-0.04F * this.getAttribute(net.minecraftforge.common.ForgeMod.SWIM_SPEED.get()).getValue(), 0.0D));
	}
	
	private void registerBlockBreakBlacklist() {
		horizontalBlockBlacklist.forEach((block) -> {
			//if (block)
		});
	}
	
	@Override
	public void travel(Vector3d p_213352_1_) {		
		if (this.isInWater() && this.isAlive()) {
			this.getAttribute(Attributes.MOVEMENT_SPEED).setBaseValue(0.15D);
		} else {
			this.getAttribute(Attributes.MOVEMENT_SPEED).setBaseValue(0.2D);
		}
		super.travel(p_213352_1_);
	}
	
	@Override
	public boolean isAffectedByFluids() {
		return false;
	}
	
	  
/*	private float rotlerp(float p_70663_1_, float p_70663_2_, float p_70663_3_) {		
		float f = MathHelper.wrapDegrees(p_70663_2_ - p_70663_1_);		
		if (f > p_70663_3_) {		  
			f = p_70663_3_;		    
		}		   
		if (f < -p_70663_3_) {		     
			f = -p_70663_3_;		     
		}		  
		return p_70663_1_ + f;		
	}
	
	public void lookAtY(Entity p_70625_1_, float p_70625_2_, float p_70625_3_) {		 
		double d0 = p_70625_1_.getX() - this.getX();		  
		double d2 = p_70625_1_.getZ() - this.getZ();		  
		double d1;		  
		if (p_70625_1_ instanceof LivingEntity) {		   
			LivingEntity livingentity = (LivingEntity)p_70625_1_;		     
			d1 = livingentity.getEyeY() - this.getEyeY();		   
		} else {		    
			d1 = (p_70625_1_.getBoundingBox().minY + p_70625_1_.getBoundingBox().maxY) / 2.0D - this.getEyeY();		    
		}		    
		double d3 = (double)MathHelper.sqrt(d0 * d0 + d2 * d2);		    
		float f = (float)(MathHelper.atan2(d2, d0) * (double)(180F / (float)Math.PI)) - 90.0F;		    
		float f1 = (float)(-(MathHelper.atan2(d1, d3) * (double)(180F / (float)Math.PI)));		     
		this.yRot = this.rotlerp(this.yRot, f, p_70625_2_);	
	}*/

	@Override
	public boolean isSuppressingBounce() {
		return false;
	}
	
	@Override
	public void aiStep() {
//		if (this.level.getGameTime() < 20 || this.tickCount < 20) return;
		super.aiStep();
	//	if (!this.isAlive()) return;
	//	if (this.getTarget() != null/* || this.getAttackID() != RAGE_RUN_ATTACK || this.getAttackID() != RAGE_CRASH || this.getAttackID() != RAGE_ENABLE || this.getAttackID() != RAGE_DISABLE/* || this.getNavigation().getTargetPos() == this.getTarget().blockPosition()*/) {
	//		this.lookAt(getTarget(), 100, 100);
	//		this.getLookControl().setLookAt(getTarget(), 30F, 30F);
	//	}
		
//		ChaosAwakens.LOGGER.debug(this.getAttackID());
//		ChaosAwakens.LOGGER.debug(getRageDamage());
		//&& getAttackID() != RAGE_RUN_ATTACK && getAttackID() != RAGE_ENABLE && getAttackID() != RAGE_DISABLE && getAttackID() != RAGE_CRASH && getAttackID() != RAGE_REVIVE)
		if (getTarget() != null) {
			if ((getAttackID() != (byte) 0 && getAttackID() != RAGE_RUN_ATTACK && getAttackID() != RAGE_ENABLE && getAttackID() != RAGE_DISABLE && getAttackID() != RAGE_CRASH && getAttackID() != RAGE_REVIVE) && !isDeadOrDying()) {
			//	yBodyRot = 0;
		//		this.setSpeed(0);
		//		this.setMovementSpeed(0);
				if (this.isOnGround()) {
		//			this.setDeltaMovement(0, 0, 0);
				}
		//		this.setDeltaMovement(0, this.getDeltaMovement().y, 0);
				this.lookAt(getTarget(), 100, 100);
				this.getLookControl().setLookAt(getTarget(), 30F, 30F);
		//		int maxXRot = this.getMaxHeadXRot();
		//		maxXRot = 1;
			}
			
			if (IUtilityHelper.getHorizontalDistanceBetweenEntities(this, getTarget()) <= 1.0D) {
				if (this.distanceTo(getTarget()) > AnimatableGoal.getAttackReachSq(this, getTarget())) {
					this.navigation.stop();
					this.lookAt(getTarget(), 100, 100);
					this.getLookControl().setLookAt(getTarget(), 30F, 30F);
				}
			}
		}
		
		boolean flag = false;
		AxisAlignedBB axisalignedbb = this.getBoundingBox().inflate(0.2D, -0.5, 0.2D);
		boolean drops = random.nextInt(50) == 0;

		//When the pounder is crushing things in its way
		if (this.horizontalCollision && ForgeEventFactory.getMobGriefingEvent(level, this)) {
			for(BlockPos blockpos : BlockPos.betweenClosed(MathHelper.floor(axisalignedbb.minX), MathHelper.floor(axisalignedbb.minY), MathHelper.floor(axisalignedbb.minZ), MathHelper.floor(axisalignedbb.maxX), MathHelper.floor(axisalignedbb.maxY), MathHelper.floor(axisalignedbb.maxZ))) {
				BlockState blockstate = this.level.getBlockState(blockpos);
				Block block = blockstate.getBlock();
				if ((!isEyeInFluid(FluidTags.WATER) && !isEyeInFluid(FluidTags.LAVA) && block.defaultBlockState().getHarvestLevel() < 3 && !block.is(Blocks.OBSIDIAN) && !block.is(Blocks.CRYING_OBSIDIAN) && !block.is(Blocks.BEDROCK) && !block.is(Blocks.STONE)/* && !block.is(Blocks.DIRT)*/ && !block.is(Blocks.GRASS_BLOCK) && !block.is(Blocks.SAND) && !block.getRegistryName().toString().contains("robo") && block.defaultBlockState() != getFeetBlockState())) {
					flag = this.level.destroyBlock(blockpos, drops, this) || flag;
				}
				
				if (getAttackID() == RAGE_RUN_ATTACK && rageRunTime < 40 && !block.is(Blocks.BEDROCK)) {
					flag = this.level.destroyBlock(blockpos, false, this) || flag;
				}
				
				
			}
		}

		// When the pounder lands on something from above
		if (this.verticalCollision && ForgeEventFactory.getMobGriefingEvent(level, this)) {
			for(BlockPos blockpos : BlockPos.betweenClosed(MathHelper.floor(axisalignedbb.minX), MathHelper.floor(axisalignedbb.minY), MathHelper.floor(axisalignedbb.minZ), MathHelper.floor(axisalignedbb.maxX), MathHelper.floor(axisalignedbb.maxY), MathHelper.floor(axisalignedbb.maxZ))) {
				BlockState blockstate = this.level.getBlockState(blockpos);
				Block block = blockstate.getBlock();
				if (!isInWater() && !isInLava() && !block.is(Blocks.OBSIDIAN) && !block.is(Blocks.CRYING_OBSIDIAN) && !block.is(Blocks.BEDROCK) && !block.is(Blocks.STONE) && !block.is(Blocks.DIRT) && !block.is(Blocks.GRASS_BLOCK) && !block.is(Blocks.SAND) && !block.getRegistryName().toString().contains("robo") && block.defaultBlockState() != getFeetBlockState()) {				
				// Todo: pack all these up into a tag for the robo pounder to, well, you know, pound
		//		if (block instanceof LeavesBlock || block.getBlock() ==  Blocks.FLOWER_POT || block.getBlock() == Blocks.HAY_BLOCK || block.getBlock().is(BlockTags.TRAPDOORS) || block.getBlock().is(BlockTags.FENCES) || block.getBlock().is(BlockTags.FENCE_GATES) || block.getBlock().is(BlockTags.DOORS) || block.getBlock().is(BlockTags.WALLS) || block.getBlock().is(BlockTags.WOODEN_TRAPDOORS) || block.getBlock() instanceof MelonBlock || block.getBlock() instanceof PumpkinBlock || block.getBlock() instanceof BambooBlock || block.getBlock() == Blocks.SNOW || block.getBlock() == Blocks.SNOW_BLOCK ||block.getBlock().is(BlockTags.TALL_FLOWERS) || block.getBlock() == Blocks.GRASS || block  instanceof DoublePlantBlock || block.getBlock() instanceof TorchBlock || block.getBlock() == Blocks.GLASS_PANE || block instanceof AbstractPlantBlock || block instanceof FlowerBlock) {
					flag = this.level.destroyBlock(blockpos, drops, this) || flag;
				}
			}
		}
	}

	@Override
	public void registerControllers(AnimationData data) {
		data.addAnimationController(controller);
		data.addAnimationController(secondaryController);
	}
	
	@Override
	public boolean hurt(DamageSource source, float amt) {
        for(int i = 0; i < 6; ++i) {
            double d0 = this.random.nextGaussian() * 0.01D;
            double d1 = this.random.nextGaussian() * 0.02D;
            double d2 = this.random.nextGaussian() * 0.01D;
            this.level.addParticle(CAParticleTypes.ROBO_SPARK.get(), this.getRandomX(0.35D), this.getRandomY(), this.getRandomZ(0.35D), random.nextBoolean() ? 0.01D : -0.01D, 0.02D, random.nextBoolean() ? 0.01D : -0.01D);
         }
        
		return super.hurt(source, amt);
	}
	
	@Override
	public AnimationController<?> getController() {
		return controller;
	}
	
	@Override
	public int animationInterval() {
		return 3;
	}
	
	@Override
	public void manageAttack(LivingEntity target) {
		for (int i = 0; i < 360; i++) {
			if (i % 45 == 0) {
				
			}
		}
	}
	
	@Override
	public boolean isPushable() {
		return false;
	}
	
	@Override
	public void push(double x, double y, double z) {
		return;
	}
	
	@Override
	protected int calculateFallDamage(float f1, float f2) {
		return 0;
	}

	@Override
	public void tick() {
		super.tick();
		if (getAttackID() == 0 && (this.isInWater() || this.isInLava()) && !isDeadOrDying()) {
			getController().setAnimationSpeed(0.4D);
		}
		
		if ((this.isInWater() || this.isInLava())) {
			this.getAttribute(Attributes.ATTACK_DAMAGE).setBaseValue(this.getAttackID() == PUNCH_ATTACK ? 10 : 5);
		} else {
			this.getAttribute(Attributes.ATTACK_DAMAGE).setBaseValue(20);
		}
		
		if (getAttackID() == RAGE_RUN_ATTACK) {
			if (getHealth() < 150.0F) this.getAttribute(Attributes.MOVEMENT_SPEED).setBaseValue(0.44D);
			if (getHealth() < 75.0F) this.getAttribute(Attributes.MOVEMENT_SPEED).setBaseValue(0.46D);
			if (getHealth() < 50.0F) this.getAttribute(Attributes.MOVEMENT_SPEED).setBaseValue(0.48D);
			// Has to be an extra check to work for some reason?? -- Meme Man
			else if (getHealth() > 150.0F) this.getAttribute(Attributes.MOVEMENT_SPEED).setBaseValue(0.42D);
		} else {
			this.getAttribute(Attributes.MOVEMENT_SPEED).setBaseValue(0.2D);
		}
		
		if (getRageDamage() > 30) {
			setRageDamage(0);
		}
		
		if (getAttackID() != 0 && getAttackID() != RAGE_RUN_ATTACK && getAttackID() != RAGE_ENABLE && getAttackID() != RAGE_DISABLE && getAttackID() != RAGE_CRASH && getAttackID() != RAGE_REVIVE) {
		//	setDeltaMovement(Vector3d.ZERO);
		//	setSpeed(0);
		}
		
//		ChaosAwakens.debug("ARMOR", "[Armor Value]: " + getAttributeValue(Attributes.ARMOR));
//		ChaosAwakens.debug("MOVEMENTSPEED", "[Movement Speed Value]: " + getAttributeValue(Attributes.MOVEMENT_SPEED));
		
		if (getAttackID() == RAGE_RUN_ATTACK) {
			rageRunTime++;
			
			if (rageRunTime <= 40) this.getAttribute(Attributes.ARMOR).setBaseValue(90);
			else if (rageRunTime > 40) this.getAttribute(Attributes.ARMOR).setBaseValue(60);
		} else {
			this.getAttribute(Attributes.ARMOR).setBaseValue(30);
		}
				
		if (getAttackID() != RAGE_RUN_ATTACK && rageRunTime > 0) {
			rageRunTime = 0;
		}
		
        if (getHealth() < 25.0F && isAlive()) {
            for(int i = 0; i < level.random.nextInt(1) + 1; ++i) {
               level.addParticle(ParticleTypes.SMOKE, (double)getX(), (double)getEyeY() - 0.17D, (double)getZ() - 0.5D, (double)(level.random.nextFloat() / 2.0F * 0.5F - 0.1F), 5.0E-5D, (double)(level.random.nextFloat() / 2.0F * 0.5F - 0.1F));
               level.addParticle(ParticleTypes.SMOKE, (double)getX(), (double)getEyeY() - 0.17D, (double)getZ() + 0.5D, (double)(level.random.nextFloat() / 2.0F * 0.5F - 0.1F), 5.0E-5D, (double)(level.random.nextFloat() / 2.0F * 0.5F - 0.1F));
               level.addParticle(ParticleTypes.SMOKE, (double)getX() + 0.16D, (double)getEyeY() - 0.24D, (double)getZ() - 0.5D, (double)(level.random.nextFloat() / 2.0F * 0.5F - 0.1F), 5.0E-5D, (double)(level.random.nextFloat() / 2.0F * 0.5F - 0.1F));
               level.addParticle(ParticleTypes.SMOKE, (double)getX() - 0.18D, (double)getEyeY() - 0.24D, (double)getZ() - 0.5D, (double)(level.random.nextFloat() / 2.0F * 0.5F - 0.1F), 5.0E-5D, (double)(level.random.nextFloat() / 2.0F * 0.5F - 0.1F));
               level.addParticle(ParticleTypes.SMOKE, (double)getBoundingBox().minX + 0.5D, (double)getY() + 1.27D, (double)getZ() - 0.5D, (double)(level.random.nextFloat() / 2.0F * 0.5F - 0.1F), 5.0E-5D, (double)(level.random.nextFloat() / 2.0F * 0.5F - 0.1F));
               level.addParticle(ParticleTypes.SMOKE, (double)getBoundingBox().maxX - 0.5D, (double)getY() + 1.27D, (double)getZ() - 0.5D, (double)(level.random.nextFloat() / 2.0F * 0.5F - 0.1F), 5.0E-5D, (double)(level.random.nextFloat() / 2.0F * 0.5F - 0.1F));
            }
         }
		
/*		if (getAttackID() == RAGE_RUN_ATTACK) {
			if (getTarget() != null) {
				if (getBoundingBox().inflate(0.15D).intersects(getTarget().getBoundingBox())) {
					double angle = IUtilityHelper.getAngleBetweenEntities(this, getTarget());
					getTarget().setDeltaMovement((-3.1 * Math.cos(angle)), getTarget().getDeltaMovement().y, -3.1 * Math.sin(angle));
				}
			}
		}*/
		
		repelEntities(2.5F, 5, 2.5F, 2.5F);
	}
	
	@Override
	protected boolean canRide(Entity vehicle) {
		return false;
	}

	@Override
	public boolean canSee(Entity target) {
		if (target != null && this.distanceTo(target) <= getFollowRange() && IUtilityHelper.getVerticalDistanceBetweenEntities(this, target) <= 10.0F) {
			return true;
		}
		return super.canSee(target);
	}
	
	@Override
	public boolean isInvulnerableTo(DamageSource source) {
		if (source.getDirectEntity() != null) {
			if (source.getDirectEntity() instanceof ProjectileEntity && (getAttackID() == RAGE_RUN_ATTACK)) return true;
		}
		return super.isInvulnerableTo(source);
	}
	
	protected boolean canBreakBlock(Block block) {
		return block.defaultBlockState().getHarvestLevel() < 3 && !block.is(Blocks.OBSIDIAN) && !block.is(Blocks.CRYING_OBSIDIAN) && !block.is(Blocks.BEDROCK) && !block.is(Blocks.STONE) && !block.is(Blocks.DIRT) && !block.is(Blocks.GRASS_BLOCK) && !block.is(Blocks.SAND) && !block.getRegistryName().toString().contains("robo") && block.defaultBlockState() != getFeetBlockState();
	}

	@Override
	public EntityType<?> getType() {
		return CAEntityTypes.ROBO_POUNDER.get();
	}

	@Override
	public AnimationFactory getFactory() {
		return factory;
	}
	
	@Override
	public boolean canStandOnFluid(Fluid fluid) {
		return false;
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
			AITargetingSystemType type = CACommonConfig.COMMON.roboPounderTargetingSystemType.get();
			
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
	public boolean isPushedByFluid() {
		return false;
	}
	
	@Override
	protected float getVoicePitch() {
		return super.getVoicePitch() - 0.6F;
	}
	
	@Override
	public double getFluidJumpThreshold() {
		return 0;
	}
	
	@Override
	public boolean ignoreExplosion() {
		return true;
	}
	
	@Override
	protected boolean isStuck() {
		return super.isStuck() && (getAttackID() == RAGE_RUN_ATTACK || getAttackID() == RAGE_ENABLE) ? rageRunTime > 20 && super.isStuck() : super.isStuck();
	}
	
	@Override
	protected void tickDeath() {	    
		++this.deathTime;	     
		if (this.deathTime == 85) {	      
			this.remove();
			
	         for(int i = 0; i < 25; ++i) {
	             double d0 = this.random.nextGaussian() * 0.02D;
	             double d1 = this.random.nextGaussian() * 0.02D;
	             double d2 = this.random.nextGaussian() * 0.02D;
	             this.level.addParticle(ParticleTypes.POOF, this.getRandomX(1.2D), this.getRandomY(), this.getRandomZ(1.2D), d0, d1, d2);
	          }
		}	
	}
	
	@Override
	protected void updateNoActionTime() {
		super.updateNoActionTime();
	}

	@Override
	public void writeSpawnData(PacketBuffer buffer) {
	}

	@Override
	public void readSpawnData(PacketBuffer additionalData) {
	}
	
	@Override
	public boolean displayFireAnimation() {
		return false;
	}
	
	private double getRageRunDuration() {
		// Between 10s and 19s
		if (getHealth() <= 150.0F) return IUtilityHelper.randomBetween(200, 380);
		// Between 7.5s and 15s
		else if (getHealth() <= 75.0F) return IUtilityHelper.randomBetween(150, 300);
		// Between 5s and 10s
		else if (getHealth() <= 50.0F) return IUtilityHelper.randomBetween(100, 200);
		// Between 12.5s and 22.5s
		else if (getHealth() > 150.0F) return IUtilityHelper.randomBetween(250, 450);
		
		return 0;
	}
	
	@Override
	public void animateHurt() {
		super.animateHurt();
	}
	
	@Override
	public void spawnAnim() {
		super.spawnAnim();
	}
	
	@Override
	public boolean canBeCollidedWith() {
		if (getAttackID() == RAGE_RUN_ATTACK) return false;
		return isAlive();
	}
	
/*	@Override
	public boolean canCutCorner(PathNodeType type) {
		return type != PathNodeType.WATER && type != PathNodeType.LAVA;
	}*/
	
	@Override
	protected void blockedByShield(LivingEntity entity) {
	//	super.blockedByShield(entity);		
	}
	
	@Override
	public IPacket<?> getAddEntityPacket() {
		return NetworkHooks.getEntitySpawningPacket(this);
	}
	
	public class RoboPounderMoveToTargetGoal extends AnimatableMoveToTargetGoal {

		public RoboPounderMoveToTargetGoal(RoboPounderEntity entity, double speedMultiplier, int checkRate) {
			super(entity, speedMultiplier, checkRate);
			this.setFlags(EnumSet.of(Flag.MOVE));
		}
		
		@Override
		public boolean canUse() {
			return super.canUse() && (getAttackID() == (byte) 0/* || getAttackID() == RAGE_RUN_ATTACK*/);
		}
		
		@Override
		public boolean canContinueToUse() {
			return super.canContinueToUse() && (getAttackID() == (byte) 0/* || getAttackID() == RAGE_RUN_ATTACK*/);
		}
		
		@Override
		public void tick() {
			if (getAttackID() != (byte) 0 /*&& getAttackID() != RAGE_RUN_ATTACK*/) return;
/*			if (getTarget() == null) return;
			
			if (getAttackID() == RAGE_RUN_ATTACK) {
				Path curPath = getNavigation().getPath();
				
				if (curPath == null) {
					curPath = getNavigation().createPath(getTarget().blockPosition(), 1);
					getNavigation().moveTo(curPath, 1);
				}
				
				if (curPath != null) {
					getNavigation().moveTo(curPath, 1);
					
				}
			}*/
			
			super.tick();
		}
		
	}
	
	//T4!!
	//WHAT IS HAPENING!!!!!!
/*	@Override
	protected BodyController createBodyControl() {
		return new CABodyController(this);
	}*/
	
	@Override
	public boolean doHurtTarget(Entity target) {
		double angle = IUtilityHelper.getAngleBetweenEntities(this, target);
		float f1 = (float) this.getAttributeValue(Attributes.ATTACK_KNOCKBACK);
		
		if (target instanceof LivingEntity) {
			if (target instanceof PlayerEntity) {
				PlayerEntity player = (PlayerEntity) target;
				
				if ((getAttackID() == PUNCH_ATTACK || getAttackID() == PUNCH_ATTACK_MIRROR || getAttackID() == RAGE_RUN_ATTACK) && (player.getOffhandItem().getItem().equals(Items.SHIELD) || player.getMainHandItem().getItem().equals(Items.SHIELD)) && player.isUsingItem()) {
					player.disableShield(true);
			//		player.getCooldowns().addCooldown(player.getUseItem().getItem(), 100);			         
			//		player.stopUsingItem();
			//		player.level.broadcastEntityEvent(player, (byte) 30);
			//		player.disableShield(true);
			//		player.getUseItem().hurtAndBreak(10, player, e -> e.broadcastBreakEvent(player.getUseItem().getEquipmentSlot() != null ? player.getUseItem().getEquipmentSlot() : EquipmentSlotType.OFFHAND));
				}
			}
			
			if (getAttackID() == RAGE_RUN_ATTACK) {
				((LivingEntity) target).setDeltaMovement((-0.5 * Math.cos(angle)), target.getDeltaMovement().y, -0.5 * Math.sin(angle));
			}
			
			if (getAttackID() == PUNCH_ATTACK || getAttackID() == PUNCH_ATTACK_MIRROR) {
	//			 ((LivingEntity)target).knockback(f1, (double)MathHelper.sin(this.yRot * ((float)Math.PI / 180F)), (double)(-MathHelper.cos(this.yRot * ((float)Math.PI / 180F))));
			}
			
			if (getAttackID() == SIDE_SWEEP_ATTACK || getAttackID() == SIDE_SWEEP_ATTACK_MIRROR) {
	//			((LivingEntity)target).knockback(f1 * 0.5F, (double)MathHelper.sin(this.yRot * ((float)Math.PI / 180F)), (double)(-MathHelper.cos(this.yRot * ((float)Math.PI / 180F))));
				setAttackDamage(getAttackDamage() / 2);
				return super.doHurtTarget(target);
			}
		}
		return super.doHurtTarget(target);
	}
		
	@Override
	public void knockback(float p_233627_1_, double p_233627_2_, double p_233627_4_) {
		return;
	}
	
	@Override
	protected void lavaHurt() {
		this.hurt(DamageSource.LAVA, 4.0F);
	}
	
	@Override
	protected boolean shouldDespawnInPeaceful() {
		return super.shouldDespawnInPeaceful();
	}
	
	@Override
	public void checkDespawn() {      
		if (this.level.getDifficulty() == Difficulty.PEACEFUL && this.shouldDespawnInPeaceful()) {	       
			this.remove();     
		}
	}
	
	public class RoboPounderPunchGoal extends AnimatableMeleeGoal {
		
		public RoboPounderPunchGoal(AnimatableMonsterEntity entity, double animationLength, double attackBegin, double attackEnd) {
			super(entity, animationLength, attackBegin, attackEnd);
		}
		
		@Override
		public void start() {
			super.start();
			getNavigation().stop();
			if (random.nextBoolean()) setAttackID(PUNCH_ATTACK_MIRROR);
			else setAttackID(PUNCH_ATTACK);
		}
		
		@Override
		public void stop() {
			super.stop();
			setAttackID((byte) 0);
	//		controller.markNeedsReload();
		}
		
		@Override
		public boolean canUse() {
			return super.canUse() && getAttackID() == (byte) 0 && level.random.nextInt(2) == 0 && !this.hasHit;
		}
		
		@Override
		public boolean canContinueToUse() {
			return super.canContinueToUse() && (getAttackID() == PUNCH_ATTACK || getAttackID() == PUNCH_ATTACK_MIRROR);
		}
		
		public void animateAttack(AnimatableMonsterEntity attacker) {
			if (attacker.getTarget() == null) return;
			
			double reach = AnimatableGoal.getAttackReachSq(attacker, attacker.getTarget());
			double reachSqr = reach * reach;
			
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
				
				if (hitDistanceSqr <= reach && (relativeHitAngle <= 45F / 2 && relativeHitAngle >= -45F / 2) || (relativeHitAngle >= 360 - 45F / 2 || relativeHitAngle <= -360 + 45F / 2)) {
					attacker.doHurtTarget(target);
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
				
	//			ChaosAwakens.debug("GOAL", (this.animationProgress) + "/" + (this.animationLength) + "/" + (this.tickDelta) + "/" + (this.animationProgress/this.animationLength));
				 
				if (this.attackPredicate.apply(this.animationProgress, this.animationLength) && !this.hasHit) {
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
	
	public class RoboPounderSideSweepGoal extends AnimatableMeleeGoal {
		
		public RoboPounderSideSweepGoal(RoboPounderEntity entity, double animationLength, double attackBegin, double attackEnd) {
			super(entity, animationLength, attackBegin, attackEnd);
		}
		
		@Override
		public void start() {
			super.start();
			getNavigation().stop();
			if (level.random.nextInt(2) == 0) setAttackID(SIDE_SWEEP_ATTACK_MIRROR);
			else setAttackID(SIDE_SWEEP_ATTACK);
		}
		
		@Override
		public void stop() {
			super.stop();
			setAttackID((byte) 0);
	//		controller.markNeedsReload();
		}
		
		@Override
		public boolean canUse() {
			if (getTarget() == null) return false;
			double reach = AnimatableGoal.getAttackReachSq(this.entity, this.entity.getTarget()) - 0.4D;
			double distance = this.entity.distanceToSqr(getTarget().getX(), getTarget().getY(), getTarget().getZ());
			List<LivingEntity> targets = IUtilityHelper.getAllEntitiesAround(this.entity, reach, reach, reach, reach);
			return (AnimatableMeleeGoal.checkIfValid(this, entity, getTarget()) && distance <= reach && getAttackID() == (byte) 0 && level.random.nextInt(2) == 1) || (super.canUse() && getAttackID() == (byte) 0 && targets.size() >= 3);
		}
		
		@Override
		public boolean canContinueToUse() {
			return super.canContinueToUse() && (getAttackID() == SIDE_SWEEP_ATTACK || getAttackID() == SIDE_SWEEP_ATTACK_MIRROR);
		}
		
		private void affectSideSweepAOEArea(AnimatableMonsterEntity attacker) {
			if (attacker.getTarget() == null) return;
			
			double reach = AnimatableGoal.getAttackReachSq(attacker, attacker.getTarget());
			double reachSqr = reach * reach;
			
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
				
				if (hitDistanceSqr <= reach && (relativeHitAngle <= 110F / 2 && relativeHitAngle >= -110F / 2) || (relativeHitAngle >= 360 - 110F / 2 || relativeHitAngle <= -360 + 110F / 2)) {
					attacker.doHurtTarget(target);
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
				
	//			ChaosAwakens.debug("GOAL", (this.animationProgress) + "/" + (this.animationLength) + "/" + (this.tickDelta) + "/" + (this.animationProgress/this.animationLength));
				
				if (this.attackPredicate.apply(this.animationProgress, this.animationLength) && !this.hasHit) {
					affectSideSweepAOEArea(this.entity);
				}
				
				if (this.animationProgress >= this.animationLength) {
					this.animationProgress = 0;
					this.hasHit = false;
					setAttackID((byte) 0);
				}
			}
		}
		
	}
	
	public class RoboPounderRageActivationGoal extends AnimatableGoal {
		private final BiFunction<Double, Double, Boolean> animationEndPredicate;
		private final double animationLength;
//		float probability = 0.2F;
		
		public RoboPounderRageActivationGoal(RoboPounderEntity pounder) {
			this.entity = pounder;
			this.animationLength = 40;
			animationEndPredicate = (progress, length) -> ++progress >= animationLength;
		}
		
		@Override
		public void start() {
			setAttackID(RAGE_ENABLE);
			setSpeed(0);
			this.animationProgress = 0;
		}
		
		@Override
		public void stop() {
			setAttackID(RAGE_RUN_ATTACK);
			setSpeed((float) getMovementSpeed());
			this.animationProgress = 0;
		}

		@Override
		public boolean canUse() {
			if (getHealth() < 150.0F) return level.random.nextInt(140) == 4 && getAttackID() == (byte) 0 && getTarget() != null && !isInWaterOrBubble() && !isInLava() && !isInsidePortal || getAttackID() == RAGE_ENABLE;
			if (getHealth() < 75.0F) return level.random.nextInt(125) == 4 && getAttackID() == (byte) 0 && getTarget() != null && !isInWaterOrBubble() && !isInLava() && !isInsidePortal || getAttackID() == RAGE_ENABLE;
			if (getHealth() < 50.0F) return level.random.nextInt(50) == 4 && getAttackID() == (byte) 0 && getTarget() != null && !isInWaterOrBubble() && !isInLava() && !isInsidePortal || getAttackID() == RAGE_ENABLE;
     		else return (level.random.nextInt(350) == 4 && getAttackID() == (byte) 0 && getTarget() != null && !isInWaterOrBubble() && !isInLava() && !isInsidePortal) || getAttackID() == RAGE_ENABLE;
		}

		@Override
		public boolean canContinueToUse() {
			return !animationEndPredicate.apply(this.animationProgress, this.animationLength) && entity.isAlive();
		}
		
		@Override
		public void tick() {
			this.baseTick();
			
			if (!animationEndPredicate.apply(this.animationProgress, this.animationLength)) {
				if (!isOnGround()) {
					setDeltaMovement(0, getDeltaMovement().y, 0);
					setSpeed(0);
				} else {
					setDeltaMovement(Vector3d.ZERO);
				}
			}
			
			
		}
	}
	
	//TODO Rename some variables for conventions n stuff -- Meme Man
	public class RoboPounderRageRunGoal extends AnimatableGoal {
		private final BiFunction<Double, Double, Boolean> rageRunAnimationPredicate;
		private double rageRunLength;
		private BlockPos targetPos = BlockPos.ZERO;
		private int entityDistanceCounter;
		
		/*
		 * 		// Between 10s and 19s
		if (getHealth() <= 150.0F) return IUtilityHelper.randomBetween(200, 380);
		// Between 7.5s and 15s
		else if (getHealth() <= 75.0F) return IUtilityHelper.randomBetween(150, 300);
		// Between 5s and 10s
		else if (getHealth() <= 50.0F) return IUtilityHelper.randomBetween(100, 200);
		// Between 12.5s and 22.5s
		else if (getHealth() > 150.0F) return IUtilityHelper.randomBetween(250, 450);
		
		return 0;
		 */
		
		public RoboPounderRageRunGoal(RoboPounderEntity pounder) {
			this.entity = pounder;
			//TODO This is some next level junior dev shit --Meme Man
			rageRunLength = getHealth() < 150.0F ? IUtilityHelper.randomBetween(200, 380) : getHealth() < 75.0F ? IUtilityHelper.randomBetween(150, 300) : getHealth() < 50.0F ? IUtilityHelper.randomBetween(100, 200) : IUtilityHelper.randomBetween(250, 450);
			rageRunAnimationPredicate = (progress, length) -> ++progress >= rageRunLength;
		}
		
		@Override
		public void start() {
			setAttackID(RAGE_RUN_ATTACK);
			entityDistanceCounter = 0;
	//		setMovementSpeed(getMovementSpeed() + 0.6D);
	//		setSpeed((float) getMovementSpeed() + 0.6F);
	//		targetPos = entity.getTarget().blockPosition();
	//		if (level.getFluidState(targetPos) != null) {
	//			targetPos = conductMiniHorizontalSweep(5, 5);
	//		}
	//		final BlockPos finalTarget = targetPos;
	//		Path path = getNavigation().createPath(finalTarget, 0);
	//		getNavigation().moveTo(path, 1.4D);
			this.animationProgress = 0;
		}
		
		@Override
		public void stop() {
			if (getRageDamage() > 30) setAttackID(RAGE_CRASH);
			else setAttackID(RAGE_DISABLE);
			entityDistanceCounter = 0;
		//	if (getRageDisableReason() == CRASHED) setAttackID(RAGE_CRASH);
		//	if (getRageDisableReason() != NOT_STOPPED) setRageDisableReason(NOT_STOPPED);
		//	setMovementSpeed(getMovementSpeed() - 0.6D);
		//	setSpeed((float) getMovementSpeed() - 0.6F);
			this.animationProgress = 0;		
		}
		
		@Override
		public boolean canUse() {
			return getAttackID() == RAGE_RUN_ATTACK;
		}
		
		private double getHorizontalDeltaMovement() {
			return getDeltaMovement().multiply(1, 0, 1).length();
		}

		//This code for some reason works better in this method than in tick()???
		//It just works™ -- Meme Man
		@Override
		public boolean canContinueToUse() {
	//		boolean validationCheck1 = getRageDisableReason() == NOT_STOPPED;
			if (getTarget() == null) return false;
			AxisAlignedBB axisalignedbb = getBoundingBox().inflate(0.15D);
//			ChaosAwakens.debug("RAGERUNLENGTH", "[Rage Run Time/Length]: " + rageRunTime + "/" + rageRunLength);
			
			for(BlockPos blockpos : BlockPos.betweenClosed(MathHelper.floor(axisalignedbb.minX), MathHelper.floor(axisalignedbb.minY), MathHelper.floor(axisalignedbb.minZ), MathHelper.floor(axisalignedbb.maxX), MathHelper.floor(axisalignedbb.maxY), MathHelper.floor(axisalignedbb.maxZ))) {
				BlockState state = level.getBlockState(blockpos);
				FluidState fState = level.getFluidState(blockpos);
				Block block = state.getBlock();
				Vector3i facing = getDirection().getNormal();			
				
				Vector3i facingA = getDirection().getNormal();
				final BlockPos targetPos = getTarget().blockPosition();//.offset(facingA.getX() - 100 * facingA.getZ(), 0, facingA.getZ() - 100 * facingA.getX());
				
	//			ChaosAwakens.debug("PathNodeType", level.getFluidState(targetPos) != null ? level.getFluidState(targetPos).toString() : "NO AI PATHNODE TYPE");
	//			ChaosAwakens.debug("", "NODE COUNT: " + getNavigation().getPath().getNodeCount());
				
				if (getNavigation().getPath() == null) {
				//	getNavigation().stop();
					final Path newPathTest = getNavigation().createPath(targetPos, 0);
					// Prevent anti-knockback mobs from causing the navigation to go insane
					if (newPathTest == null) return false;
					 /*Start the path off at node index 0 (no longer in effect), all checks (such as, for example, big holes in between the start pos and end pos)
					  can be done using BlockPos.betweenClosed(blockPosition(), finalPos) to disable it right before those holes.
					  This code directly works with the CAStrictGroundPathNavigator class, which has been re-structured for both
					  general use cases, and unique use cases such as this one.
					  IMPORTANT NOTE: This code DOES NOT work with AnimatableMoveToTargetGoal.class at all. The same applies
					  for RoboPounderMoveToTargetGoal.class. This (RoboPounderMoveToTargetGoal.class) will probably be removed after I re-factor this messy ass code.
					  -- Meme Man*/
			//		if (newPathTest != null) newPathTest.setNextNodeIndex(0);
					Vector3i facingB = getDirection().getNormal();
					BlockPos nextNodePos = newPathTest.getNextNode().asBlockPos();
					final BlockPos finalPos = newPathTest.getEndNode().asBlockPos();
					if (newPathTest != null) newPathTest.replaceNode(newPathTest.getNextNodeIndex(), newPathTest.getEndNode().cloneAndMove(newPathTest.getEndNode().x + facing.getX() * 4, newPathTest.getEndNode().y, newPathTest.getEndNode().z + facing.getZ() * 4));
					PathNodeType type = newPathTest.getNextNode().type;
					Vector3d relevantVec = new Vector3d(finalPos.getX(), finalPos.getY(), finalPos.getZ());
	//				Vector3d afterMinPass = RandomPositionGenerator.getLandPosTowards((CreatureEntity) getTarget(), 10, 10, relevantVec);
					
					lookAt(Type.EYES, relevantVec);
					getLookControl().setLookAt(relevantVec);
					
					if (getNavigation().getNodeEvaluator().getGoal(nextNodePos.getX(), nextNodePos.getY(), nextNodePos.getZ()) != null) {
			//			ChaosAwakens.debug("TEST TARGET POS", "Goal Pos: " + getNavigation().getNodeEvaluator().getGoal(nextNodePos.getX(), nextNodePos.getY(), nextNodePos.getZ()).toString());
					}
	//				newPathTest.getNextNode().cloneAndMove(facingB.getX() + 10, facingB.getY(), facingB.getZ() + 10);
	//				newPathTest.truncateNodes(getNavigation().getPath().getNodeCount() - 1);
					getNavigation().moveTo(newPathTest, 1);
	//				newPathTest.truncateNodes(getNavigation().getPath().getNodeCount() - 1);
		//			ChaosAwakens.debug("Target Pos", "[TARGET POS]:" + finalPos.toString() + " VS " + " [NEXT NODE]:" + getNavigation().getPath().getNextNodePos().toString());
		//			ChaosAwakens.debug("MOVEDNODE", newPathTest.getEndNode().cloneAndMove(newPathTest.getEndNode().x + facing.getX() * 4, newPathTest.getEndNode().y, newPathTest.getEndNode().z + facing.getZ() * 4).asBlockPos().toString());
				}
				
				if (getNavigation().getPath() != null) {
					int nodeCount = getNavigation().getPath().getNodeCount();
					
			//		ChaosAwakens.debug("NODECOUNT", "[Node Count]: " + nodeCount);
			//		ChaosAwakens.debug("NEXTNODE", "[Next Node]: " + getNavigation().getPath().getNextNodeIndex());
					final BlockPos finalPos = getNavigation().getPath().getEndNode().asBlockPos();
					
					getNavigation().moveTo(getNavigation().getPath(), 1);
					
					if (getNavigation().getPath().getNextNodeIndex() < getNavigation().getPath().getNodeCount()) {
						BlockPos nextNodePos = getNavigation().getPath().getNextNode().asBlockPos();
						Vector3i facingC = getDirection().getNormal();
						if (facingC != null) {
			//				ChaosAwakens.debug("FACING", "[Currently Facing]: " + facingC.toString());
						}
						// Prevent the Robo Pounder from occasionally turning right due to the node index changing to 0 for a single tick
						getNavigation().getPath().setNextNodeIndex(getNavigation().getPath().getNodeCount() - 1);
						if (nextNodePos != null && finalPos != null) {
				//			ChaosAwakens.debug("GOAL", "[FLAGGED GOAL]:" + getNavigation().getNodeEvaluator().getGoal(nextNodePos.getX(), nextNodePos.getY(), nextNodePos.getZ()).toString());
							if (nextNodePos != finalPos) {
								nextNodePos = finalPos;
							}
			//				ChaosAwakens.debug("NEXTNODEPOS", "[Next Node Pos]: " + nextNodePos.toString());
			//				ChaosAwakens.debug("FINALNODEPOS", "[Final Node Pos]: " + finalPos.toString());
			//				ChaosAwakens.debug("CURPATH", "[Currently Following]: " + getNavigation().moveTo(getNavigation().getPath(), 1));
			//				ChaosAwakens.debug("GOALTYPE", "[Target Pos PathNodeType]: " + getNavigation().getNodeEvaluator().getGoal(nextNodePos.getX(), nextNodePos.getY(), nextNodePos.getZ()).type);
						}
					}
					
					if (IUtilityHelper.getHorizontalDistanceBetween(blockPosition(), finalPos) <= 1.5D && !rageRunAnimationPredicate.apply(animationProgress, rageRunLength) && getRageDamage() < 30) {
						getNavigation().stop();
						final BlockPos updatedTargetPos = getTarget().blockPosition();
						Path newPath2 = getNavigation().createPath(updatedTargetPos, 0);
						// Prevent anti-knockback mobs from causing the navigation to go insane
						if (newPath2 == null) return false;
			//			if (newPath2 != null) newPath2.setNextNodeIndex(0);
						final BlockPos finalPos2 = newPath2.getEndNode().asBlockPos();
						if (newPath2 != null) newPath2.replaceNode(newPath2.getNextNodeIndex(), newPath2.getEndNode().cloneAndMove(newPath2.getEndNode().x + facing.getX() * 4, newPath2.getEndNode().y, newPath2.getEndNode().z + facing.getZ() * 4));
						Vector3d relevantVec2 = new Vector3d(finalPos2.getX(), finalPos2.getY(), finalPos2.getZ());
						lookAt(Type.EYES, relevantVec2);
						getLookControl().setLookAt(relevantVec2);
						getNavigation().moveTo(newPath2, 1);
					} else if (IUtilityHelper.getHorizontalDistanceBetween(blockPosition(), finalPos) <= 0.5D && rageRunAnimationPredicate.apply(animationProgress, rageRunLength)) {
						return false;
					}
					
					
					
		//			ChaosAwakens.debug("BOOLEAN", "[RAND BOOL]: " + level.random.nextBoolean());
		//			ChaosAwakens.debug("Movement", "[DeltaMovement Horizontal]: " + getHorizontalDeltaMovement());
					
		//			if ((getDeltaMovement().length() < 0.1D && animationProgress > 10)) return false;
				}
				
	/*			if (getNavigation().getPath() != null) {
					BlockPos nextNodePos = getNavigation().getPath().getNextNode().asBlockPos();
					if (getNavigation().getPath().getNextNodeIndex() - 1 < getNavigation().getPath().getNodeCount()) {
						if (getNavigation().getNodeEvaluator().getGoal(nextNodePos.getX(), nextNodePos.getY(), nextNodePos.getZ()) != null) {
						//	if (getNavigation().getNodeEvaluator().getGoal(nextNodePos.getX(), nextNodePos.getY(), nextNodePos.getZ()).type == PathNodeType.WATER_BORDER) {
						//		if (IUtilityHelper.getHorizontalDistanceBetween(blockPosition(), getNavigation().getNodeEvaluator().getGoal(nextNodePos.getX(), nextNodePos.getY(), nextNodePos.getZ()).asBlockPos()) <= 0.5D) {
						//			
						//		}
						//	}
		//					ChaosAwakens.debug("TEST TARGET POS", "Goal Pos: " + getNavigation().getNodeEvaluator().getGoal(nextNodePos.getX(), nextNodePos.getY(), nextNodePos.getZ()).toString());
						}
					}
					
		//			ChaosAwakens.debug("Target Pos", "[TARGET POS]:" + getNavigation().getPath().getEndNode().asBlockPos().toString() + " VS " + " [NEXT NODE]:" + getNavigation().getPath().getNextNodePos().toString());
					
				}*/
				
				if (level.getBlockState(targetPos).getAiPathNodeType(level, targetPos) != null) {
					if (level.getFluidState(targetPos).is(FluidTags.WATER) || level.getBlockState(targetPos).getAiPathNodeType(level, targetPos).equals(PathNodeType.WATER_BORDER)) {
						if (IUtilityHelper.getHorizontalDistanceBetween(blockPosition(), targetPos) < 4.0D) {
							return false;
						}
					}
				}
				
				if (getNavigation().isStuck() || (horizontalCollision && (block.is(Blocks.OBSIDIAN) || (block.is(Blocks.STONE)) || block.is(Blocks.CRYING_OBSIDIAN) || block.is(Blocks.BEDROCK) || block.getRegistryName().toString().contains("robo")) || block.defaultBlockState().getHarvestLevel() > 3) && rageRunTime > 40) {
					setRageDamage(100);
		//			return false;
				}
				if (isEyeInFluid(FluidTags.WATER) || isEyeInFluid(FluidTags.LAVA)) {
					setRageDamage(100);
				}
			}
			
	/*		for (LivingEntity entity : IUtilityHelper.getAllEntitiesAround(entity, 2.8F, getBoundingBox().maxY, 2.8F, 2.8F)) {
				if (getBoundingBox().intersects(entity.getBoundingBox()) && entity.canBeCollidedWith()) {
					setRageDamage(100);
				}
				
				if (entity instanceof RoboPounderEntity) return false;
			}*/
			
			if (horizontalCollision && !ForgeEventFactory.getMobGriefingEvent(level, entity)) return false;
	//		if (getHorizontalDeltaMovement() <= 0.22235792875289917D && rageRunTime > 60) return false;
			
			if (getTarget() != null) {
				if (IUtilityHelper.getHorizontalDistanceBetweenEntities(entity, getTarget()) <= 1.0D) {
					if ((getY() + 5 > getTarget().getY() || getY() - 5 < getTarget().getY())) {
						return false;
					}
				}
			}
						
			if (getTarget() != null) {
				if (distanceTo(getTarget()) <= 4.0D) {
					entityDistanceCounter++;
				}
			}
			
			if (isStuck()) return false;
			
			if (getTarget() == null) {
				entityDistanceCounter = 0;
				return false;
			}
			
			if (entityDistanceCounter > 30) return false;
	//		ChaosAwakens.debug("DISTANCECOUNTER", "[Entity Distance Counter]:" + entityDistanceCounter);
			
			LivingEntity target = getTarget();
			LivingEntity attacker = this.entity;
			float hitDistanceSqr = (float) (Math.sqrt((target.getZ() - attacker.getZ()) * (target.getZ() - attacker.getZ()) + (target.getX() - attacker.getX()) * (target.getX() - attacker.getX())) - attacker.getBbWidth() / 2F);
			double reach = getBoundingBox().getSize();
			boolean validationCheck2 = !rageRunAnimationPredicate.apply(this.animationProgress, rageRunLength);
			return validationCheck2 && getTarget() != null && getRageDamage() < 30;
		}
		
		private void animateAttack(AnimatableMonsterEntity attacker) {
			if (attacker.getTarget() == null) return;
			
			double reach = getBoundingBox().getSize();
			double reachSqr = reach * reach;
			
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
				
				if (hitDistanceSqr <= reach && (relativeHitAngle <= 140F / 2 && relativeHitAngle >= -140F / 2) || (relativeHitAngle >= 360 - 140F / 2 || relativeHitAngle <= -360 + 140F / 2)) {
					attacker.doHurtTarget(target);
				}
			}
		}
		
		@Override
		public void tick() {
			this.baseTick();
			
			if (rageRunAnimationPredicate.apply(this.animationProgress, rageRunLength)) {
			//	setRageDisableReason(DISABLED);
			}
			
			if (!rageRunAnimationPredicate.apply(this.animationProgress, rageRunLength)) {
				animateAttack(entity);
			}
			
			if (!rageRunAnimationPredicate.apply(this.animationProgress, rageRunLength)) {
				if (getLastHurtByMob() != null && getLastDamageSource() != null && getLastHurtByMobTimestamp() < 10) {
					LivingAttackEvent event = new LivingAttackEvent(entity, getLastDamageSource(), (float) getLastHurtByMob().getAttributeValue(Attributes.ATTACK_DAMAGE));
					setRageDamage((int) (getRageDamage() + event.getAmount()));
					MinecraftForge.EVENT_BUS.post(event);
				}
			}
		}
				/*	if (block.getHarvestLevel(state) > 3) {
						setRageDisableReason(CRASHED);
					}
				}
			}
		}
		
	/*	private BlockPos conductMiniHorizontalSweep(int sizeX, int sizeZ) {
			Mutable mutable = new Mutable();
			LivingEntity target = getTarget();
			double posY = target.getY() - 1;
			Direction dir = entity.getDirection();
			Vector3i facing = dir.getNormal();
			
			for (int x = 0; x < sizeX; x++) {
				for (int z = 0; z < sizeZ; z++) {
					mutable = mutable.set(facing.getX() + x, posY, facing.getZ() + z);
					
					BlockState state = level.getBlockState(mutable);
					FluidState fState = level.getFluidState(mutable);
					
					if (fState != null) break;
				}
			}
			return mutable;
		}*/
		
	}
	
	//TODO Note, I know that the second double (representing the length) in the BiFunction type fields does nothing,
	//All code (entity/AI related) will yet again be refactored by me in the foreseeable future --Meme Man
	public class RoboPounderRageDisableGoal extends AnimatableGoal {
		private final BiFunction<Double, Double, Boolean> animDisablePredicate;
		private final double rageDisableAnimLength;
		
		public RoboPounderRageDisableGoal(RoboPounderEntity pounder) {
			this.entity = pounder;
			rageDisableAnimLength = 43;
			animDisablePredicate = (progress, length) -> ++progress >= rageDisableAnimLength;
		}
		
		public void start() {
			setAttackID(RAGE_DISABLE);
			setSpeed(0);
			getNavigation().stop();
			this.animationProgress = 0;
		}
		
		@Override
		public void stop() {
			setAttackID((byte) 0);
			setSpeed((float) getMovementSpeed());
			this.animationProgress = 0;
		}

		@Override
		public boolean canUse() {
			return getAttackID() == RAGE_DISABLE;
		}

		@Override
		public boolean canContinueToUse() {
			return !animDisablePredicate.apply(animationProgress, rageDisableAnimLength);
		}
		
		@Override
		public void tick() {
			this.baseTick();
			
			if (!animDisablePredicate.apply(this.animationProgress, this.rageDisableAnimLength)) {
				if (!isOnGround()) {
					setDeltaMovement(0, getDeltaMovement().y, 0);
					setSpeed(0);
				} else {
					setDeltaMovement(Vector3d.ZERO);
					setSpeed(0);
				}
			}
			
			
		}
		
	}
	
	public class RoboPounderRageCrashGoal extends AnimatableGoal {
		private final BiFunction<Double, Double, Boolean> animationEndPredicate;
		private final double animationLength;
		
		public RoboPounderRageCrashGoal(RoboPounderEntity pounder) {
			this.entity = pounder;
			this.animationLength = IUtilityHelper.randomBetween(60, 160);
			animationEndPredicate = (progress, length) -> ++progress >= animationLength;
		}
		
		@Override
		public void start() {
			setAttackID(RAGE_CRASH);
			setSpeed(0);
			getNavigation().stop();
			this.animationProgress = 0;
		}
		
		@Override
		public void stop() {
			setAttackID(RAGE_REVIVE);
			setSpeed((float) getMovementSpeed());
			this.animationProgress = 0;
		}

		@Override
		public boolean canUse() {
			return getAttackID() == RAGE_CRASH;
		}

		@Override
		public boolean canContinueToUse() {
			return !animationEndPredicate.apply(this.animationProgress, this.animationLength) && entity.isAlive();
		}
		
		@Override
		public void tick() {
			this.baseTick();
			
			if (!animationEndPredicate.apply(this.animationProgress, this.animationLength)) {
				if (!isOnGround()) {
					setDeltaMovement(0, getDeltaMovement().y, 0);
					setSpeed(0);
				} else {
					setDeltaMovement(Vector3d.ZERO);
				}
			}
			
			
		}
	}
	
	public class RoboPounderRageReviveGoal extends AnimatableGoal {
		private final BiFunction<Double, Double, Boolean> animationEndPredicate;
		private final double animationLength;
		private boolean shouldRebootWithRage;
		
		public RoboPounderRageReviveGoal(RoboPounderEntity pounder) {
			this.entity = pounder;
			this.animationLength = 33;
			animationEndPredicate = (progress, length) -> ++progress >= animationLength;
			shouldRebootWithRage = level.random.nextInt(100) == 5 && getHealth() > 50.0F && !isInWaterOrBubble() && !isInLava();
		}
		
		@Override
		public void start() {
			setAttackID(RAGE_REVIVE);
			getNavigation().stop();
			setSpeed(0);
			this.animationProgress = 0;
		}
		
		@Override
		public void stop() {
			if (shouldRebootWithRage) setAttackID(RAGE_ENABLE);
			else setAttackID((byte) 0);
			setSpeed((float) getMovementSpeed());
			this.animationProgress = 0;
		}

		@Override
		public boolean canUse() {
			return getAttackID() == RAGE_REVIVE;
		}

		@Override
		public boolean canContinueToUse() {
			return !animationEndPredicate.apply(this.animationProgress, this.animationLength) && entity.isAlive();
		}
		
		@Override
		public void tick() {
			this.baseTick();
			
			if (!animationEndPredicate.apply(this.animationProgress, this.animationLength)) {
				if (!isOnGround()) {
					setDeltaMovement(0.0D, getDeltaMovement().y, 0.0D);
					setSpeed(0);
				} else {
					setDeltaMovement(Vector3d.ZERO);
					setSpeed(0);
				}
			}
			
			
		}
	}
	
	@Override
	protected void addPassenger(Entity p_184200_1_) {
		super.addPassenger(p_184200_1_);
	}
	
	static class Navigator extends CAStrictGroundPathNavigator {

		public Navigator(MobEntity entity, World world) {
			super(entity, world);
		}
		
		@Override
		protected PathFinder createPathFinder(int maxNodes) {
			this.nodeEvaluator = new RoboPounderEntity.RoboPounderNodeEvaluator();
			this.nodeEvaluator.setCanPassDoors(true);
			return new CAPathFinder(nodeEvaluator, maxNodes);
		}
		
		@Override
		protected boolean canMoveDirectly(Vector3d startPos, Vector3d endPos, int x, int y, int z) {
			return true;
		}
		
		@Override
		protected boolean sweepThrough(Vector3d pathVec, Vector3d center, Vector3d max) {
	        float l = 0.0F;
	        float ml = (float) pathVec.length();
	        
	        if (ml < IUtilityHelper.EPSILON) return true;
	        
	        final float[] trailEdge = new float[3];
	        final int[] leadEdgeI = new int[3];
	        final int[] trailEdgeI = new int[3];
	        final int[] step = new int[3];
	        final float[] trailDelta = new float[3];
	        final float[] trailNext = new float[3];
	        final float[] normalised = new float[3];
	        
	        for (int i = 0; i < 3; i++) {
	            float axis = switchAxis(pathVec, i);
	            boolean direction = axis >= 0.0F;
	            step[i] = direction ? 1 : -1;
	            float lead = switchAxis(direction ? max : center, i);
	            trailEdge[i] = switchAxis(direction ? center : max, i);
	            leadEdgeI[i] = leti(lead, step[i]);
	            trailEdgeI[i] = teti(trailEdge[i], step[i]);
	            normalised[i] = axis / ml;
	            trailDelta[i] = MathHelper.abs(ml / axis);
	            float dist = direction ? (leadEdgeI[i] + 1 - lead) : (lead - leadEdgeI[i]);
	            trailNext[i] = trailDelta[i] < Float.POSITIVE_INFINITY ? trailDelta[i] * dist : Float.POSITIVE_INFINITY;
	        }
	        // Presumably iterates through each axis from the center all the way to the max vector3d
	        final BlockPos.Mutable pos = new BlockPos.Mutable();
	        
	        do {
	            int axis = (trailNext[0] < trailNext[1]) ? ((trailNext[0] < trailNext[2]) ? 0 : 2) : ((trailNext[1] < trailNext[2]) ? 1 : 2);
	            
	            float nextDirection = trailNext[axis] - l;           
	            l = trailNext[axis];
	            leadEdgeI[axis] += step[axis];
	            trailNext[axis] += trailDelta[axis];
	            
	            for (int i = 0; i < 3; i++) {
	                trailEdge[i] += nextDirection * normalised[i];
	                trailEdgeI[i] = teti(trailEdge[i], step[i]);
	            }
	            
	            int stepx = step[0];
	            int x0 = (axis == 0) ? leadEdgeI[0] : trailEdgeI[0];
	            int x1 = leadEdgeI[0] + stepx;
	            int stepy = step[1];
	            int y0 = (axis == 1) ? leadEdgeI[1] : trailEdgeI[1];
	            int y1 = leadEdgeI[1] + stepy;
	            int stepz = step[2];
	            int z0 = (axis == 2) ? leadEdgeI[2] : trailEdgeI[2];
	            int z1 = leadEdgeI[2] + stepz;
	            
	            for (int x = x0; x != x1; x += stepx) {
	                for (int z = z0; z != z1; z += stepz) {
	                    for (int y = y0; y != y1; y += stepy) {
	                        BlockState block = this.level.getBlockState(pos.set(x, y, z));
	                        if (!block.isPathfindable(this.level, pos, PathType.LAND)) return false;
	                    }
	                    
	                    Vector3i facing = mob.getDirection().getNormal();
	                    
	                 //   ChaosAwakens.debug("VALUES", "[Trail Edge]: " + trailEdge + " | " + "[Lead Edge I]:" + leadEdgeI);
	                    
	                    PathNodeType below = this.nodeEvaluator.getBlockPathType(this.level, x, y0 - 1, z, this.mob, 1, 1, 1, true, true);
	                    if (below == PathNodeType.WATER || below == PathNodeType.LAVA || below == PathNodeType.OPEN) return false;
	                    
	                    PathNodeType inFrontOf = nodeEvaluator.getBlockPathType(level, x + facing.getX() * 4, y0, z + facing.getZ() * 4, mob, MathHelper.floor(mob.getBbWidth() + 1.0F), MathHelper.floor(mob.getBbHeight() + 1.0F), MathHelper.floor(mob.getBbWidth() + 1.0F), true, true);
	                    if (inFrontOf == PathNodeType.WATER || inFrontOf == PathNodeType.LAVA) return false;
	                    
	                    BlockPos nextNodePos = getPath().getNextNodePos();
	                    PathNodeType inFrontOfNextNodePos = nodeEvaluator.getBlockPathType(level, nextNodePos.getX(), y0, nextNodePos.getZ(), mob, MathHelper.floor(mob.getBbWidth() + 1.0F), MathHelper.floor(mob.getBbHeight() + 1.0F), MathHelper.floor(mob.getBbWidth() + 1.0F), true, true);
	                    if (inFrontOfNextNodePos == PathNodeType.WATER || inFrontOfNextNodePos == PathNodeType.LAVA) return false;
	                    
	                    PathNodeType around = this.nodeEvaluator.getBlockPathType(this.level, x, y0, z, this.mob, x1 - x0, 1, z1 - z0, true, true);
	                    if (around == PathNodeType.DAMAGE_FIRE || around == PathNodeType.DANGER_FIRE || around == PathNodeType.DAMAGE_OTHER || around == PathNodeType.WATER || around == PathNodeType.LAVA) return false;

	                    PathNodeType in = this.nodeEvaluator.getBlockPathType(this.level, x, y0, z, this.mob, 1, y1 - y0, 1, true, true);
	                    float priority = this.mob.getPathfindingMalus(in);
	                    float prioritya = this.mob.getPathfindingMalus(around);
	                    
	                    if (priority < 0.0F || priority >= 8.0F) return false;
	                    if (prioritya < 0.0F || prioritya >= 8.0F) return false;
	                    if (in == PathNodeType.DAMAGE_FIRE || in == PathNodeType.DANGER_FIRE || in == PathNodeType.DAMAGE_OTHER ||in == PathNodeType.WATER || in == PathNodeType.LAVA) return false;
	                }
	            }
	        } while (l <= ml);
	        
	        return true;
		}
		
		@Override
		protected boolean hasValidPathType(PathNodeType type) {
			if (type == PathNodeType.WATER) return false;	    
			else if (type == PathNodeType.LAVA) return false;	 
			else return type != PathNodeType.OPEN;
		}
		
/*		@Override
		protected boolean canMoveDirectly(Vector3d startPos, Vector3d endPos, int x, int y, int z) {
			MobEntity pathEntity = this.mob;
			Vector3d entityPos = new Vector3d(pathEntity.getX(), pathEntity.getY(), pathEntity.getZ());
			Vector3d pathEntityTempPos = this.getTempMobPos();
			final Vector3d center = pathEntityTempPos.add(-pathEntity.getBbWidth() * 0.5F, 0.0F, -pathEntity.getBbWidth() * 0.5F);
			final Vector3d maxArea = center.add(-pathEntity.getBbWidth(), -pathEntity.getBbHeight(), -pathEntity.getBbWidth());
			
			return sweepThrough(entityPos, center, maxArea);
		}*/
		
	}
	
	static class RoboPounderNodeEvaluator extends CAWalkNodeProcessor {
		
		//Useless constructor moment
		private RoboPounderNodeEvaluator() {
			
		}
		
/*		@Override
		public PathNodeType getBlockPathType(IBlockReader p_186319_1_, int p_186319_2_, int p_186319_3_,
				int p_186319_4_, MobEntity p_186319_5_, int p_186319_6_, int p_186319_7_, int p_186319_8_,
				boolean p_186319_9_, boolean p_186319_10_) {
			
			
			
			return PathNodeType.WALKABLE;
		}
		
		@Override
		public PathNodeType getBlockPathType(IBlockReader p_186330_1_, int p_186330_2_, int p_186330_3_, int p_186330_4_) {
			return PathNodeType.WALKABLE;
		}
		
/*		@Override
		protected PathNodeType evaluateBlockPathType(IBlockReader world, boolean canOpenDoors, boolean canEnterDoors, BlockPos pos, PathNodeType type) {		
			return world.getFluidState(pos) == null ? PathNodeType.WALKABLE : super.evaluateBlockPathType(world, canOpenDoors, canEnterDoors, pos, type);
		}
		
/*		@Override
		public PathNodeType getBlockPathTypes(IBlockReader p_193577_1_, int p_193577_2_, int p_193577_3_,
				int p_193577_4_, int p_193577_5_, int p_193577_6_, int p_193577_7_, boolean p_193577_8_,
				boolean p_193577_9_, EnumSet<PathNodeType> p_193577_10_, PathNodeType p_193577_11_,
				BlockPos p_193577_12_) {
			
			return PathNodeType.WALKABLE;
		}
		
		@Override
		public PathNodeType getBlockPathTypeRaw(IBlockReader blockaccessIn, int x, int y, int z) {
			return PathNodeType.WALKABLE;
		}
		
		@Override
		public PathNodeType checkNeighborBlocks(IBlockReader blockaccessIn, int x, int y, int z,
				PathNodeType nodeType) {
			return PathNodeType.WALKABLE;
		}*/
		
		
	}
	
	public class RoboPounderMoveController extends CAGroundMovementController {

		public RoboPounderMoveController(MobEntity entity, float maxRotation) {
			super(entity, maxRotation);
		}
		
		@Override
		public void tick() {
			if (this.mob.getTarget() != null && this.mob.getNavigation().getTargetPos() == this.mob.getTarget().blockPosition()) {
				this.mob.lookAt(this.mob.getTarget(), 100, 100);
				this.mob.getLookControl().setLookAt(mob.getTarget(), 30F, 30F);
			}
	        if (this.operation == CAGroundMovementController.Action.STRAFE) {
	            float speed = (float)this.mob.getAttribute(Attributes.MOVEMENT_SPEED).getValue();
	            float speedModifier = (float)this.speedModifier * speed;
	            float forwardMovement = this.strafeForwards;
	            float rightMovement = this.strafeRight;
	            float totalMovement = MathHelper.sqrt(forwardMovement * forwardMovement + rightMovement * rightMovement);

	            if (totalMovement < 1.0F) {
	                totalMovement = 1.0F;
	            }
	            
	            totalMovement = speedModifier / totalMovement;
	            forwardMovement = forwardMovement * totalMovement;
	            rightMovement = rightMovement * totalMovement;
	            float yawSine = MathHelper.sin(this.mob.yRot * 0.017453292F);
	            float yawCosine = MathHelper.cos(this.mob.yRot * 0.017453292F);
	            float totalDistMoveRight = forwardMovement * yawCosine - rightMovement * yawSine;
	            float totalDistMoveForward = rightMovement * yawCosine + forwardMovement * yawSine;
	            PathNavigator navigator = this.mob.getNavigation();

	            if (navigator != null) {
	                NodeProcessor processor = navigator.getNodeEvaluator();

	                if (processor != null && processor.getBlockPathType(this.mob.level, MathHelper.floor(this.mob.getX() + (double)totalDistMoveRight), MathHelper.floor(this.mob.getY()),MathHelper.floor(this.mob.getZ() + (double)totalDistMoveForward)) != PathNodeType.WALKABLE) {
	                    this.strafeRight = 0.0F;
	                    this.strafeForwards = 0.0F;
	                    speedModifier = speed;
	                }
	                
	                if (processor != null && processor.getBlockPathType(this.mob.level, MathHelper.floor(this.mob.getX() + (double)totalDistMoveRight), MathHelper.floor(this.mob.getY()),MathHelper.floor(this.mob.getZ() + (double)totalDistMoveForward)) != PathNodeType.WALKABLE) {
	                    this.strafeRight = 0.0F;
	                    this.strafeForwards = 0.0F;
	                    speedModifier = speed;
	                }
	                
	                if (processor != null && processor.getBlockPathType(this.mob.level, MathHelper.floor(this.mob.getX() + (double)totalDistMoveRight), MathHelper.floor(this.mob.getY()),MathHelper.floor(this.mob.getZ() + (double)totalDistMoveForward)) == PathNodeType.WATER) {
	                    this.strafeRight = 0.0F;
	                    this.strafeForwards = 0.0F;
	                    speedModifier = speed;
	                }
	            }

	            this.mob.setSpeed(speedModifier);
	            this.mob.setZza(this.strafeForwards);
	            this.mob.setXxa(this.strafeRight);
	            this.operation = CAGroundMovementController.Action.WAIT;
	        } else if (this.operation == CAGroundMovementController.Action.MOVE_TO) {
	            this.operation = CAGroundMovementController.Action.WAIT;
	            double distX = this.wantedX - this.mob.getX();
	            double distZ = this.wantedZ - this.mob.getZ();
	            double distY = this.wantedY - this.mob.getY();
	            double totalDist = distX * distX + distY * distY + distZ * distZ;

	            //Vanilla value moment
	            if (totalDist < 2.500000277905201E-7D) {
	                this.mob.setZza(0.0F);
	                return;
	            }

	            float distRotH = (float)(MathHelper.atan2(distZ, distX) * (180D / Math.PI)) - 90.0F;
	            this.mob.yRot = this.rotlerp(this.mob.yRot, distRotH, maxRot);
	            this.mob.setSpeed((float)(this.speedModifier * this.mob.getAttribute(Attributes.MOVEMENT_SPEED).getValue()));

	            if (distY > (double)this.mob.maxUpStep && distX * distX + distZ * distZ < (double)Math.max(1.0F, this.mob.getBbWidth())) {
	            	if (distY + 1.0D > (double)this.mob.maxUpStep && distX * distX + distZ * distZ < (double)Math.max(1.0F, this.mob.getBbWidth())) return;
	            	else {
	            		mob.maxUpStep += 0.40000002377464892E-8F;
	            	}
	             //   navigation.getPath().setNextNodeIndex(0);
	             //   this.operation = CAGroundMovementController.Action.JUMPING;
	    
	                //Pointless for now
	/*                int time = 0;
	                
	                if (this.operation == CAGroundMovementController.Action.JUMPING) {
	                	time++;
	                	
	                	if (time >= 1000) {
	                        this.mob.setZza(this.strafeForwards);
	                        this.mob.setXxa(this.strafeRight);
	                	}
	                } else {
	                	if (time > 0) {
	                		time --;
	                	}
	                }*/
	            }
	        } else if (this.operation == CAGroundMovementController.Action.JUMPING) {
	            this.mob.setSpeed((float)(this.speedModifier * this.mob.getAttribute(Attributes.MOVEMENT_SPEED).getValue()));

	            if (this.mob.isOnGround()) {
	                this.operation = CAGroundMovementController.Action.WAIT;
	            }
	        } else {
	            this.mob.setZza(0.0F);            
	        }	
		}
		
	}
	
	@Override
	protected PathNavigator createNavigation(World world) {
		return new Navigator(this, world);
	}

	@Override
	public int tickTimer() {
		return tickCount;
	}
}
