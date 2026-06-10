package io.github.chaosawakens.mixins.util;

import net.minecraft.world.entity.projectile.ProjectileUtil;
import org.spongepowered.asm.mixin.Mixin;

@Mixin(ProjectileUtil.class)
public abstract class ProjectileUtilMixin {

    private ProjectileUtilMixin() {
        throw new IllegalAccessError("Attempted to construct Mixin class! (ProjectileUtilMixin)");
    }
}
