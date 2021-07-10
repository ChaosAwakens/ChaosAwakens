package io.github.chaosawakens.api;

import io.github.chaosawakens.common.entity.AnimatableMonsterEntity;
import net.minecraft.entity.Entity;
import net.minecraft.network.datasync.DataParameter;
import net.minecraft.network.datasync.DataSerializers;
import net.minecraft.network.datasync.EntityDataManager;
import net.minecraft.util.math.vector.Vector3d;

/**
 * 
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
	
	boolean getGrabbing();
	void setGrabbing(boolean grabbing);
}
