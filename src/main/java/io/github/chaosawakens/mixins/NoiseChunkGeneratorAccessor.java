package io.github.chaosawakens.mixins;

import java.util.function.Supplier;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;

import net.minecraft.world.gen.DimensionSettings;
import net.minecraft.world.gen.NoiseChunkGenerator;

@Mixin(NoiseChunkGenerator.class)
public interface NoiseChunkGeneratorAccessor {
	@Accessor
	long getSeed();

	@Accessor
	Supplier<DimensionSettings> getSettings();
}
