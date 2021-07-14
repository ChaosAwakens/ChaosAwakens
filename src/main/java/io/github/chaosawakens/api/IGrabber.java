package io.github.chaosawakens.api;

import io.github.chaosawakens.common.entity.AnimatableMonsterEntity;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.network.datasync.DataParameter;
import net.minecraft.network.datasync.DataSerializers;
import net.minecraft.network.datasync.EntityDataManager;
import net.minecraft.util.math.vector.Vector3d;

/**
 * Interface for entities that can use the grab goal to grab others
 * @author invalid2
 */
public interface IGrabber {
	DataParameter<Boolean> GRABBING = EntityDataManager.createKey(AnimatableMonsterEntity.class, DataSerializers.BOOLEAN);
	
	default void positionRider(Entity ridden, Entity entity, Entity.IMoveCallback callback) {
		if (ridden.isPassenger(entity)) {
			
			Vector3d offset = this.getGrabOffset().rotateYaw((float) Math.toRadians(ridden.rotationYaw));
			double dY = ridden.getPosY() + offset.getY();
			callback.accept(entity, ridden.getPosX() - offset.getX(), dY , ridden.getPosZ() + offset.getZ());
		}
	}
	
	Vector3d getGrabOffset();
	
	default Vector3d getGrabExtraMotion() { return null; }
	
	default boolean getGrabbing(LivingEntity entity) { return entity.getDataManager().get(GRABBING); }
	default void setGrabbing(LivingEntity entity, boolean grabbing) { entity.getDataManager().set(GRABBING, grabbing); }
}
