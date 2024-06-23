package io.github.chaosawakens.mixins;

import net.minecraft.entity.Entity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Invoker;

@Mixin(Entity.class)
public interface IEntityAccessor {

    @Invoker("canRide")
    boolean invokeCanRide(Entity vehicleEntity);
}
