package io.github.chaosawakens.common.entity.ai;

import java.util.Optional;

import javax.annotation.Nullable;

import io.github.chaosawakens.api.animation.SingletonAnimationBuilder;
import io.github.chaosawakens.common.entity.base.AnimatableAnimalEntity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.ILivingEntityData;
import net.minecraft.entity.SpawnReason;
import net.minecraft.entity.ai.RandomPositionGenerator;
import net.minecraft.entity.ai.controller.FlyingMovementController;
import net.minecraft.entity.ai.controller.MovementController;
import net.minecraft.entity.passive.AnimalEntity;
import net.minecraft.entity.passive.IFlyingAnimal;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.network.IPacket;
import net.minecraft.network.datasync.DataParameter;
import net.minecraft.network.datasync.DataSerializers;
import net.minecraft.network.datasync.EntityDataManager;
import net.minecraft.pathfinding.FlyingPathNavigator;
import net.minecraft.pathfinding.GroundPathNavigator;
import net.minecraft.pathfinding.Path;
import net.minecraft.pathfinding.PathNavigator;
import net.minecraft.pathfinding.PathNodeType;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.BlockPos.Mutable;
import net.minecraft.world.DifficultyInstance;
import net.minecraft.world.IServerWorld;
import net.minecraft.world.World;
import net.minecraftforge.fml.network.NetworkHooks;

public abstract class AnimatableFlyingAnimalEntity extends AnimatableAnimalEntity implements IFlyingAnimal {
	private static final DataParameter<Boolean> IS_PERCHING = EntityDataManager.defineId(AnimatableFlyingAnimalEntity.class, DataSerializers.BOOLEAN);
	private static final DataParameter<Boolean> IS_FLYING = EntityDataManager.defineId(AnimatableFlyingAnimalEntity.class, DataSerializers.BOOLEAN);
	private static final DataParameter<Boolean> IS_WALKING = EntityDataManager.defineId(AnimatableFlyingAnimalEntity.class, DataSerializers.BOOLEAN);
	private static final DataParameter<Optional<BlockPos>> PERCHING_POS = EntityDataManager.defineId(AnimatableFlyingAnimalEntity.class, DataSerializers.OPTIONAL_BLOCK_POS);

	public AnimatableFlyingAnimalEntity(EntityType<? extends AnimalEntity> type, World world) {
		super(type, world);
		this.moveControl = new FlyingMovementController(this, 1, false); //TODO Custom move control
		this.setPathfindingMalus(PathNodeType.DAMAGE_FIRE, -1.0F);
		this.setPathfindingMalus(PathNodeType.LAVA, -1.0F);
		this.setPathfindingMalus(PathNodeType.DAMAGE_CACTUS, -1.0F);
		this.setPathfindingMalus(PathNodeType.DANGER_OTHER, -1.0F);
		this.setPathfindingMalus(PathNodeType.WATER_BORDER, 0.0F);
		this.setPathfindingMalus(PathNodeType.OPEN, 8.0F);
	}
	
	@Override
	protected void registerGoals() {
		
	}
	
	@Override
	protected void defineSynchedData() {
		super.defineSynchedData();
		this.entityData.define(IS_PERCHING, true);
		this.entityData.define(IS_FLYING, false);
		this.entityData.define(IS_WALKING, false);
		this.entityData.define(PERCHING_POS, Optional.empty());
	}
	
	public boolean isPerching() {
		return this.entityData.get(IS_PERCHING);
	}
	
	public void setPerching(boolean perching) {
		this.entityData.set(IS_PERCHING, perching);
	}
	
	@Nullable
	public BlockPos getPerchingPos() {
		return this.entityData.get(PERCHING_POS).orElse(null);
	}
	
	public void setPerchingPos(BlockPos perchPos) {
		this.entityData.set(PERCHING_POS, Optional.ofNullable(perchPos));
	}
	
	public boolean isFlying() {
		return this.entityData.get(IS_FLYING);
	}
	
	public void setFlying(boolean flying) {
		this.entityData.set(IS_FLYING, flying);
	}
	
	public boolean isWalking() {
		return this.entityData.get(IS_WALKING);
	}
	
	public void setWalking(boolean walking) {
		this.entityData.set(IS_WALKING, walking);
	}
	
	@Override
	public void addAdditionalSaveData(CompoundNBT pCompound) {
		super.addAdditionalSaveData(pCompound);
	}
	
	@Override
	public void readAdditionalSaveData(CompoundNBT pCompound) {
		super.readAdditionalSaveData(pCompound);
	}
	
	@Override
	protected PathNavigator createNavigation(World world) { //TODO Custom pathnav
		FlyingPathNavigator flyingPathNav = new FlyingPathNavigator(this, world);
		
		flyingPathNav.setCanOpenDoors(false);
		flyingPathNav.setCanFloat(false);
		flyingPathNav.setCanPassDoors(true);
		
		return flyingPathNav;
	}
	
	@Override
	public void tick() {
		super.tick();
		
		if (!level.isClientSide) handleStates();
	}
	
	private void handleStates() {
		if (isPerching()) {
			if (!isOnGround() || hurtTime > 0) {
				setPerching(false);
				setPerchingPos(null);
				setFlying(true);
			}
			
			if (getRandom().nextInt(120) == 0) {
				if (getNavigation().getPath() != null) {
					
				} else if (getNavigation().getPath() == null) {
					Path targetPath = getNavigation().createPath(getRandom().nextInt(20) == 0 ? new BlockPos(RandomPositionGenerator.getLandPos(this, 10, 5)) : new BlockPos(RandomPositionGenerator.getAirPos(this, 12, 5, 4, null, 1.5D)), 1);
					
					if (targetPath.getEndNode().type == PathNodeType.OPEN) {
						FlyingPathNavigator flyPathNav = new FlyingPathNavigator(this, level); //TODO Custom pathnav
						
						flyPathNav.setCanOpenDoors(false);
						flyPathNav.setCanFloat(false);
						flyPathNav.setCanPassDoors(true);
						
						this.navigation = flyPathNav;
						this.moveControl = new FlyingMovementController(this, 1, false); //TODO Custom move control
						this.moveControl.setWantedPosition(targetPath.getNextNodePos().getX(), targetPath.getNextNodePos().getY(), targetPath.getNextNodePos().getZ(), 1.0D);
						
						this.navigation.moveTo(targetPath, 1.1D);
					} else if (targetPath.getEndNode().type != PathNodeType.OPEN || targetPath.getEndNode().type == PathNodeType.WALKABLE) {
						GroundPathNavigator walkPathNav = new GroundPathNavigator(this, level); //TODO Custom pathnav
						
						this.navigation = walkPathNav;
						this.moveControl = new MovementController(this); //TODO Custom move control
						this.moveControl.setWantedPosition(targetPath.getNextNodePos().getX(), targetPath.getNextNodePos().getY(), targetPath.getNextNodePos().getZ(), 1.0D);
						
						this.navigation.moveTo(targetPath, 1.1D);
					}
				}
			}
		}
		
		if (isWalking()) {
			if (getRandom().nextInt(120) == 0) {
				Path targetPath = getNavigation().createPath(new BlockPos(RandomPositionGenerator.getLandPos(this, 12, 5)), 0);
				
				if (targetPath != null) {
					getNavigation().moveTo(targetPath, 1.0D);
					
					if (getNavigation().isDone()) {
						setWalking(false);
						setPerching(true);
						setPerchingPos(blockPosition());
					}
				}
			}
		}
	}
	
	@Nullable
	protected BlockPos getRandomGroundPos(BlockPos origin) {
		return null;
	}
	
	@Nullable
	protected BlockPos getRandomAirPos(BlockPos origin, int yLevelRaise) {
		Mutable mutBlockPos = new Mutable();
		
		for (int y = 0; y < yLevelRaise; y++) {
			mutBlockPos = (Mutable) mutBlockPos.above();
			if (level.getBlockState(mutBlockPos).getBlock().isAir(level.getBlockState(mutBlockPos), level, mutBlockPos)) break;
		}
		
		return mutBlockPos.immutable();
	}
	
	@Override
	public ILivingEntityData finalizeSpawn(IServerWorld pLevel, DifficultyInstance pDifficulty, SpawnReason pReason, ILivingEntityData pSpawnData, CompoundNBT pDataTag) {
		setPerchingPos(blockPosition());
		return super.finalizeSpawn(pLevel, pDifficulty, pReason, pSpawnData, pDataTag);
	}
	
	public abstract SingletonAnimationBuilder getFlyAnim();
	
	@Override
	public IPacket<?> getAddEntityPacket() {
		return NetworkHooks.getEntitySpawningPacket(this);
	}
	
	@Override
	protected void handleBaseAnimations() {
		if (getIdleAnim() != null && isOnGround()) playAnimation(getIdleAnim());
		if (getWalkAnim() != null && isOnGround()) playAnimation(getWalkAnim());
		if (getFlyAnim() != null && !isOnGround()) playAnimation(getFlyAnim());
	}
}
