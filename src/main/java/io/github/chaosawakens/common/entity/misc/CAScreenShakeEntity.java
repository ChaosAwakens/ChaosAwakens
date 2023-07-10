package io.github.chaosawakens.common.entity.misc;

import io.github.chaosawakens.common.registry.CAEntityTypes;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.network.IPacket;
import net.minecraft.network.datasync.DataParameter;
import net.minecraft.network.datasync.DataSerializers;
import net.minecraft.network.datasync.EntityDataManager;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.vector.Vector3d;
import net.minecraft.world.World;
import net.minecraftforge.fml.network.NetworkHooks;

// MM implementation
public class CAScreenShakeEntity extends Entity {
	protected static final DataParameter<Float> RADIUS = EntityDataManager.defineId(CAScreenShakeEntity.class, DataSerializers.FLOAT);
	protected static final DataParameter<Float> MAGNITUDE = EntityDataManager.defineId(CAScreenShakeEntity.class, DataSerializers.FLOAT);
	protected static final DataParameter<Integer> DURATION = EntityDataManager.defineId(CAScreenShakeEntity.class, DataSerializers.INT);
	protected static final DataParameter<Integer> FADE_DURATION = EntityDataManager.defineId(CAScreenShakeEntity.class, DataSerializers.INT);

	public CAScreenShakeEntity(EntityType<?> type, World world) {
		super(type, world);
	}
	
	public CAScreenShakeEntity(World world, Vector3d pos, float radius, float magnitude, int dur, int fadeDist) {
		super(CAEntityTypes.SCREEN_SHAKE.get(), world);
		setRadius(radius);
		setMagnitude(magnitude);
		setLifetimeDuration(dur);
		setFadeDuration(fadeDist);
		setPos(pos.x, pos.y, pos.z);
	}
	
	@Override
	public void tick() {
		super.tick();	
		if (tickCount >= getLifetimeDuration()) remove();
	}

	@Override
	protected void defineSynchedData() {
		entityData.define(RADIUS, 10.0F);
		entityData.define(MAGNITUDE, 1.0F);
		entityData.define(DURATION, 0);
		entityData.define(FADE_DURATION, 5);
	}
	
	public float getRadius() {
		return entityData.get(RADIUS);
	}
	
	public void setRadius(float radius) {
		entityData.set(RADIUS, radius);
	}
	
	public float getMagnitude() {
		return entityData.get(MAGNITUDE);
	}
	
	public void setMagnitude(float magnitude) {
		entityData.set(MAGNITUDE, magnitude);
	}
	
	public int getLifetimeDuration() {
		return entityData.get(DURATION);
	}
	
	public void setLifetimeDuration(int duration) {
		entityData.set(DURATION, duration);
	}
	
	public int getFadeDuration() {
		return entityData.get(FADE_DURATION);
	}
	
	public void setFadeDuration(int fadeDuration) {
		entityData.set(FADE_DURATION, fadeDuration);
	}
	
	public float getAmp(PlayerEntity owner, float delta) {
		float tickDelta = tickCount + delta;
        float timeFrac = 1.0f - (tickDelta - getLifetimeDuration()) / (getFadeDuration() + 1.0f);
        float baseAmount = tickDelta < getLifetimeDuration() ? getMagnitude() : timeFrac * timeFrac * getMagnitude();
        Vector3d playerPos = owner.getEyePosition(delta);
        float distFrac = (float) (1.0f - MathHelper.clamp(position().distanceTo(playerPos) / getRadius(), 0, 1));
        
        return baseAmount * distFrac * distFrac;
	}

	@Override
	protected void readAdditionalSaveData(CompoundNBT nbt) {
		setRadius(nbt.getFloat("radius"));
		setMagnitude(nbt.getFloat("magnitude"));
		setLifetimeDuration(nbt.getInt("lifetime_duration"));
		setFadeDuration(nbt.getInt("fade_duration"));
		tickCount = nbt.getInt("lifetime_ticks_existed");
	}

	@Override
	protected void addAdditionalSaveData(CompoundNBT nbt) {	
		nbt.putFloat("radius", getRadius());
		nbt.putFloat("magnitude", getMagnitude());
		nbt.putInt("lifetime_duration", getLifetimeDuration());
		nbt.putInt("fade_duration", getFadeDuration());
		nbt.putInt("lifetime_ticks_existed", tickCount);
	}

	@Override
	public IPacket<?> getAddEntityPacket() {
		return NetworkHooks.getEntitySpawningPacket(this);
	}
	
	public static void shakeScreen(World world, Vector3d pos, float radius, float magnitude, int dur, int fadeDist) {
		if (!world.isClientSide) {
			CAScreenShakeEntity screenShake = new CAScreenShakeEntity(world, pos, radius, magnitude, dur, fadeDist);
			world.addFreshEntity(screenShake);
		}
	}
}
