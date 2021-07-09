package io.github.chaosawakens.api;

import net.minecraft.entity.Entity;
import net.minecraft.util.math.vector.Vector3d;

/**
 * 
 * @author invalid2
 */
public interface IGrabber {
	
	default void positionRider(Entity ridden, Entity entity, Entity.IMoveCallback callback) {
		if (ridden.isPassenger(entity)) {
			
			Vector3d offset = this.getGrabOffset().rotateYaw((float) Math.toRadians(ridden.rotationYaw));
			double dY = ridden.getPosY() + offset.getY();
			callback.accept(entity, ridden.getPosX() - offset.getX(), dY , ridden.getPosZ() + offset.getZ());
		}
	}
	
	public Vector3d getGrabOffset();
}
