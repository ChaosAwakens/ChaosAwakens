package io.github.chaosawakens.mixins.common.level;

import com.llamalad7.mixinextras.sugar.Local;
import io.github.chaosawakens.common.registry.CABlocks;
import net.minecraft.world.level.levelgen.NoiseBasedChunkGenerator;
import net.minecraft.world.level.levelgen.NoiseGeneratorSettings;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyArg;

@Mixin(NoiseBasedChunkGenerator.class)
public abstract class NoiseBasedChunkGeneratorMixin {

    private NoiseBasedChunkGeneratorMixin() {
        throw new IllegalAccessError("Attempted to construct standalone Mixin Class!");
    }

    @ModifyArg(method = "createFluidPicker", at = @At(value = "INVOKE", target = "Lnet/minecraft/world/level/levelgen/Aquifer$FluidStatus;<init>(ILnet/minecraft/world/level/block/state/BlockState;)V", ordinal = 0))
    private static int chaosawakens$createFluidPicker(int fluidLevel, @Local(argsOnly = true) NoiseGeneratorSettings settings) {
        return settings.defaultBlock().is(CABlocks.DREDGESTONE.get()) ? -84 : fluidLevel;
    }
}
