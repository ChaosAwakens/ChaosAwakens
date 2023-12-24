package io.github.chaosawakens.mixins;

import net.minecraft.entity.LivingEntity;
import org.spongepowered.asm.mixin.Mixin;

@Mixin(LivingEntity.class)
public abstract class LivingEntityMixin {
	
	private LivingEntityMixin() {
		throw new IllegalAccessError("Attempted to instantiate a Mixin Class!");
	}

}