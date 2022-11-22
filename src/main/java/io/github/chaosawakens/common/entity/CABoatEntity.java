package io.github.chaosawakens.common.entity;

import io.github.chaosawakens.ChaosAwakens;
import io.github.chaosawakens.common.items.CABoatWoodTypes;
import io.github.chaosawakens.common.registry.CAEntityTypes;
import io.github.chaosawakens.common.registry.CAItems;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.item.BoatEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.network.IPacket;
import net.minecraft.network.datasync.DataParameter;
import net.minecraft.network.datasync.DataSerializers;
import net.minecraft.network.datasync.EntityDataManager;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.util.math.vector.Vector3d;
import net.minecraft.world.World;
import net.minecraftforge.fml.network.NetworkHooks;
import net.minecraftforge.registries.ForgeRegistries;

public class CABoatEntity extends BoatEntity {
	public static final DataParameter<String> TYPE = EntityDataManager.defineId(CABoatEntity.class, DataSerializers.STRING);

	public CABoatEntity(EntityType<? extends CABoatEntity> type, World world) {
		super(type, world);
	}
	
	public CABoatEntity(World world, double x, double y, double z) {
		this(CAEntityTypes.CA_BOAT.get(), world);
		this.setPos(x, y, z);
		this.setDeltaMovement(Vector3d.ZERO);
		this.xo = x;
		this.yo = y;
		this.zo = z;
	}
	
	@Override
	protected void defineSynchedData() {
		super.defineSynchedData();
		this.entityData.define(TYPE, CABoatWoodTypes.APPLE.name());
	}
	
	public String getBoatWoodType() {
		return this.entityData.get(TYPE);
	}
	
	public void setBoatWoodType(String name) {
		this.entityData.set(TYPE, name);
	}
	
	@Override
	protected void addAdditionalSaveData(CompoundNBT nbt) {
		super.addAdditionalSaveData(nbt);
		nbt.putString("Type", this.getBoatWoodType());
	}
	
	@Override
	protected void readAdditionalSaveData(CompoundNBT nbt) {
		super.readAdditionalSaveData(nbt);
		nbt.putString("Type", this.getBoatWoodType());
	}
	
	@Override
	public IPacket<?> getAddEntityPacket() {
		return NetworkHooks.getEntitySpawningPacket(this);
	}
	
	@Override
	public Item getDropItem() {
		switch(this.getBoatWoodType()) {
		default:
			return CAItems.APPLE_BOAT.get();
		case "apple":
			return CAItems.APPLE_BOAT.get();
		case "cherry":
			return CAItems.CHERRY_BOAT.get();
		case "duplication":
			return CAItems.DUPLICATOR_BOAT.get();
		case "ginkgo":
			return CAItems.GINKGO_BOAT.get();
		case "peach":
			return CAItems.PEACH_BOAT.get();
		case "skywood":
			return CAItems.SKYWOOD_BOAT.get();
		}
	}
	
	@Override
	public ItemStack getPickedResult(RayTraceResult target) {
        return new ItemStack(ForgeRegistries.ITEMS.getValue(new ResourceLocation(ChaosAwakens.MODID, this.getBoatWoodType() + "_boat")));
	}

}
