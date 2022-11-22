package io.github.chaosawakens.mixins;

import io.github.chaosawakens.common.registry.CABiomes;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.passive.AnimalEntity;
import net.minecraft.entity.passive.RabbitEntity;
import net.minecraft.util.RegistryKey;
import net.minecraft.world.IWorld;
import net.minecraft.world.World;
import net.minecraft.world.biome.Biome;
import net.minecraftforge.common.BiomeDictionary;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(RabbitEntity.class)
public abstract class RabbitEntityMixin extends AnimalEntity {
	public RabbitEntityMixin(EntityType<? extends RabbitEntity> entityType, World world) {
		super(entityType, world);
	}

	@Inject(method = "getRandomRabbitType", at = @At("HEAD"), cancellable = true)
	public void chaosawakens$getRandomRabbitType(IWorld world, CallbackInfoReturnable<Integer> cir) {
		RegistryKey<Biome> biomeRegistryKey = world.getBiomeName(this.blockPosition()).get();
		int i = this.random.nextInt(100);
		if (BiomeDictionary.hasType(biomeRegistryKey, CABiomes.Type.VILLAGE_SNOWY)) {
			cir.setReturnValue(i < 80 ? 1 : 3);
		} else if (BiomeDictionary.hasType(biomeRegistryKey, CABiomes.Type.VILLAGE_DESERT)) {
			cir.setReturnValue(4);
		}
	}
}
