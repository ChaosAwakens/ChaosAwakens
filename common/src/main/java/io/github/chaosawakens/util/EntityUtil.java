package io.github.chaosawakens.util;

import io.github.chaosawakens.api.animation_resolver_system.faal.animation.Animatable;
import io.github.chaosawakens.api.animation_resolver_system.pipeline.processing.geckolib.model.GeckolibSkeleton;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;

import java.util.List;
import java.util.function.Predicate;

public final class EntityUtil {

    private EntityUtil() {
        throw new UnsupportedOperationException("Attempted to construct instance of utility class! (EntityUtil)");
    }

    public static <E extends Entity> List<E> getEntitiesAround(Entity user, Class<E> entityClass, double dX, double dY, double dZ, double radius) {
        Predicate<E> distPredicate = target -> target != user
                && (user.getTeam() != null && target.getTeam() != null ? !target.getTeam().equals(user.getTeam()) : target.isAlive())
                && user.distanceTo(target) <= radius + target.getBbWidth() / 2F;

        return user.level().getEntitiesOfClass(entityClass, user.getBoundingBox().inflate(dX, dY, dZ), distPredicate);
    }

    public static <E extends Entity> List<E> getEntitiesAroundNoPredicate(LivingEntity user, Class<E> entityClass, double dX, double dY, double dZ) {
        return user.level().getEntitiesOfClass(entityClass, user.getBoundingBox().inflate(dX, dY, dZ));
    }

    public static <E extends Entity> List<E> getEntitiesAround(Entity user, Class<E> entityClass, double dX, double dY, double dZ, Predicate<E> detectionConditions) {
        return user.level().getEntitiesOfClass(entityClass, user.getBoundingBox().inflate(dX, dY, dZ), detectionConditions);
    }

    public static List<LivingEntity> getAllEntitiesAround(Entity user, double dX, double dY, double dZ, double radius) {
        return getEntitiesAround(user, LivingEntity.class, dX, dY, dZ, radius);
    }

    public static GeckolibSkeleton createGeckolibSkeleton(Animatable targetEntity) {
        return new GeckolibSkeleton(targetEntity.getModelInfo());
    }
}
