package io.github.chaosawakens.mixins;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import io.github.chaosawakens.common.registry.CABiomes;
import net.minecraft.entity.passive.RabbitEntity;
import net.minecraft.util.RegistryKey;
import net.minecraft.world.IWorld;
import net.minecraft.world.biome.Biome;
import net.minecraftforge.common.BiomeDictionary;

@Mixin(RabbitEntity.class)
public abstract class RabbitEntityMixin {
	
	private RabbitEntityMixin() {
		throw new IllegalAccessError("Attempted to instantiate a Mixin Class!");
	}

	@Inject(method = "getRandomRabbitType", at = @At("HEAD"), cancellable = true)
	private void chaosawakens$getRandomRabbitType(IWorld world, CallbackInfoReturnable<Integer> cir) {
		RabbitEntity targetRabbit = (RabbitEntity) (Object) this;
		
		RegistryKey<Biome> biomeRegistryKey = world.getBiomeName(targetRabbit.blockPosition()).get();
		int chance = targetRabbit.getRandom().nextInt(100);
		
		if (BiomeDictionary.hasType(biomeRegistryKey, CABiomes.Type.VILLAGE_SNOWY)) cir.setReturnValue(chance < 80 ? 1 : 3);
		else if (BiomeDictionary.hasType(biomeRegistryKey, CABiomes.Type.VILLAGE_DESERT)) cir.setReturnValue(4);
	}
}