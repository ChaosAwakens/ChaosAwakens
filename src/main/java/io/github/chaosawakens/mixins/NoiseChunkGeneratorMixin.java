package io.github.chaosawakens.mixins;

import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Mutable;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.ModifyVariable;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;

import net.minecraft.world.biome.provider.BiomeProvider;
import net.minecraft.world.gen.ChunkGenerator;
import net.minecraft.world.gen.DimensionSettings;
import net.minecraft.world.gen.NoiseChunkGenerator;

@Mixin(NoiseChunkGenerator.class)
public abstract class NoiseChunkGeneratorMixin {
	@Shadow
	@Final
	@Mutable
	public static Codec<NoiseChunkGenerator> CODEC;

	@Unique
	private static long generationSeed = 0;

	private NoiseChunkGeneratorMixin() {
		throw new IllegalAccessError("Attempted to instantiate a Mixin Class!");
	}

	@Unique
	private static long getGenerationSeed() { return generationSeed; }

	@Inject(method = "<clinit>", at = @At("RETURN"))
	private static void chaosawakens$clinit(CallbackInfo ci) {
		CODEC = RecordCodecBuilder.create((instance) -> instance.group(
				BiomeProvider.CODEC.fieldOf("biome_source").forGetter(ChunkGenerator::getBiomeSource),
				Codec.LONG.fieldOf("seed").stable().forGetter((noiseChunkGenerator) -> ((NoiseChunkGeneratorAccessor) (Object) noiseChunkGenerator).getSeed()),
				DimensionSettings.CODEC.fieldOf("settings").forGetter((noiseChunkGenerator) -> ((NoiseChunkGeneratorAccessor) (Object) noiseChunkGenerator).getSettings())
				).apply(instance, instance.stable(NoiseChunkGenerator::new)));
	}

	@ModifyVariable(method = "<init>(Lnet/minecraft/world/biome/provider/BiomeProvider;Lnet/minecraft/world/biome/provider/BiomeProvider;JLjava/util/function/Supplier;)V", at = @At("HEAD"), argsOnly = true)
	private static long chaosawakens$init(long seed) {
		if (seed == 0) return getGenerationSeed();
		else return generationSeed = seed;
	}
}