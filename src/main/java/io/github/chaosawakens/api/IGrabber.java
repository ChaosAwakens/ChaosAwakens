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
 *
 * @author invalid2
 */
public interface IGrabber {
	DataParameter<Boolean> GRABBING = EntityDataManager.defineId(AnimatableMonsterEntity.class,
			DataSerializers.BOOLEAN);

	default void positionRider(Entity ridden, Entity entity, Entity.IMoveCallback callback) {
		if (ridden.hasPassenger(entity)) {

			Vector3d offset = this.getGrabOffset().yRot((float) Math.toRadians(ridden.yRot));
			double dY = ridden.getY() + offset.y();
			callback.accept(entity, ridden.getX() - offset.x(), dY, ridden.getZ() + offset.z());
		}
	}

	Vector3d getGrabOffset();

	default boolean getGrabbing(LivingEntity entity) {
		return entity.getEntityData().get(GRABBING);
	}

	default void setGrabbing(LivingEntity entity, boolean grabbing) {
		entity.getEntityData().set(GRABBING, grabbing);
	}
}
