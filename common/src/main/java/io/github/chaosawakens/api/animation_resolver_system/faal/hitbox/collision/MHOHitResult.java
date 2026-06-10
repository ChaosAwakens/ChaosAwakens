package io.github.chaosawakens.api.animation_resolver_system.faal.hitbox.collision;

import io.github.chaosawakens.api.animation_resolver_system.faal.hitbox.MappableHitboxOwner;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.phys.EntityHitResult;
import net.minecraft.world.phys.Vec3;
import org.jetbrains.annotations.NotNull;

public class MHOHitResult<MHO extends Entity & MappableHitboxOwner> extends EntityHitResult {
    protected final MHO target;

    protected MHOHitResult(MHO target) {
        super(target);

        this.target = target;
    }

    @Override
    public double distanceTo(Entity entity) {
        return super.distanceTo(entity);
    }

    @Override
    public @NotNull Vec3 getLocation() {
        return super.getLocation();
    }

    @Override
    public @NotNull Entity getEntity() {
        return target;
    }

    public MHO getTarget() {
        return target;
    }

    @Override
    public @NotNull Type getType() {
        return Type.ENTITY;
    }
}
