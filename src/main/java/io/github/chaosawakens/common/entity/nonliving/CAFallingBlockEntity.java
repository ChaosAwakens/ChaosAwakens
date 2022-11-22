package io.github.chaosawakens.common.entity.nonliving;

import java.util.Optional;

import io.github.chaosawakens.ChaosAwakens;
import io.github.chaosawakens.common.registry.CAEntityTypes;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.MoverType;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.nbt.INBT;
import net.minecraft.nbt.NBTUtil;
import net.minecraft.network.IPacket;
import net.minecraft.network.datasync.DataParameter;
import net.minecraft.network.datasync.DataSerializers;
import net.minecraft.network.datasync.EntityDataManager;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.vector.Vector3d;
import net.minecraft.world.World;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.fml.network.NetworkHooks;

public class CAFallingBlockEntity extends Entity {
	public int lifetime;
	protected static final DataParameter<BlockPos> START_POS = EntityDataManager.defineId(CAFallingBlockEntity.class, DataSerializers.BLOCK_POS);
	protected static final DataParameter<Optional<BlockState>> BLOCK = EntityDataManager.defineId(CAFallingBlockEntity.class, DataSerializers.BLOCK_STATE);
	
	public CAFallingBlockEntity(EntityType<?> type, World world) {
		super(CAEntityTypes.FALLING_BLOCK.get(), world);
//		setBlock(Blocks.DIRT.defaultBlockState());
		this.lifetime = 20;
	}
	
	public CAFallingBlockEntity(World world, double x, double y, double z, BlockState block) {
		super(CAEntityTypes.FALLING_BLOCK.get(), world);
		setBlock(block);
		double height = getBbHeight();
		setPos(x, ((1.0F - height) * 0.5F), z);
		setDeltaMovement(Vector3d.ZERO);
		this.lifetime = 20;
		setDeltaMovement(Vector3d.ZERO);
		this.xo = x;
		this.yo = y;
		this.zo = z;
		setStartPos(blockPosition());
	}
	
	public CAFallingBlockEntity(World world, int lifetime, double x, double y, double z, BlockState block) {
		super(CAEntityTypes.FALLING_BLOCK.get(), world);
		setBlock(block);
		double height = getBbHeight();
		setPos(x, ((1.0F - height) * 0.5F), z);
		setDeltaMovement(Vector3d.ZERO);
		this.lifetime = lifetime;
		setDeltaMovement(Vector3d.ZERO);
		this.xo = x;
		this.yo = y;
		this.zo = z;
		setStartPos(blockPosition());
	}
	
	@Override
	protected void addAdditionalSaveData(CompoundNBT nbt) {
		nbt.putInt("lifetime", lifetime);
		if (getBlock() != null) nbt.put("blockstate", NBTUtil.writeBlockState(getBlock()));
	}
	
	@Override
	protected void readAdditionalSaveData(CompoundNBT nbt) {
		INBT blockData = nbt.get("blockstate");
		if (blockData != null) {		
			setBlock(NBTUtil.readBlockState((CompoundNBT) blockData));
		}
		this.lifetime = nbt.getInt("lifetime");
	}

	@Override
	protected void defineSynchedData() {
		entityData.define(START_POS, BlockPos.ZERO);
		entityData.define(BLOCK, Optional.of(Blocks.DIRT.defaultBlockState()));
	}
	
	public BlockState getBlock() {
		Optional<BlockState> optionalBlock = (Optional<BlockState>)entityData.get(BLOCK);
		return optionalBlock.orElse(null);
	}
	
	public void setBlock(BlockState newBlock) {
		this.entityData.set(BLOCK, Optional.of(newBlock));
	}
	
	public BlockPos getStartPos() {
		return (BlockPos) this.entityData.get(START_POS);
	}
	
	public void setStartPos(BlockPos newStartPos) {
		this.entityData.set(START_POS, newStartPos);
	}
	
	@Override
	public void tick() {
		if (!isNoGravity()) setDeltaMovement(getDeltaMovement().add(0, -0.04D, 0));
		move(MoverType.SELF, getDeltaMovement());	
		if (tickCount > lifetime && lifetime > 0 || this.isOnGround()) remove();
//		ChaosAwakens.debug("SUMMONBLOCK", lifetime);
	}

	@Override
	public IPacket<?> getAddEntityPacket() {
		return NetworkHooks.getEntitySpawningPacket(this);
	}
	
	@Override
	public boolean displayFireAnimation() {
		return false;
	}
		 
	@OnlyIn(Dist.CLIENT)	   
	public World getLevel() {	   
		return this.level;  
	}
}
