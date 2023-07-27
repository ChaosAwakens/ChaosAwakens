package io.github.chaosawakens.mixins;

import org.spongepowered.asm.mixin.Mixin;

import net.minecraft.entity.LivingEntity;

@Mixin(LivingEntity.class)
public abstract class LivingEntityMixin {
	
	private LivingEntityMixin() {
		throw new IllegalAccessError("Attempted to instantiate a Mixin Class!");
	}

}