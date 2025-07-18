package io.github.chaosawakens.mixins.common.level;

import com.llamalad7.mixinextras.expression.Definition;
import com.llamalad7.mixinextras.expression.Expression;
import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import com.llamalad7.mixinextras.sugar.Local;
import io.github.chaosawakens.CAConstants;
import io.github.chaosawakens.common.worldgen.level.OptimizedBiomeManager;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.biome.BiomeManager;
import net.minecraft.world.level.dimension.DimensionType;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;

@Mixin(Level.class)
public abstract class LevelMixin {
    @Unique
    private OptimizedBiomeManager chaosawakens$optimizedBiomeManager;

    private LevelMixin() {
        throw new IllegalAccessError("Attempted to construct standalone Mixin Class!");
    }

    @Definition(id = "BiomeManager", type = BiomeManager.class)
    @Definition(id = "___7", local = @Local(type = long.class, argsOnly = true))
    @Expression("new BiomeManager(this, ___7)")
    @WrapOperation(method = "<init>", at = @At("MIXINEXTRAS:EXPRESSION"))
    private BiomeManager chaosawakens$initializeOptimizedBiomeManager(BiomeManager.NoiseBiomeSource noiseBiomeSource, long biomeZoomSeed, Operation<BiomeManager> original, @Local(type = ResourceKey.class, argsOnly = true) ResourceKey<DimensionType> curDim) {
        BiomeManager originalBiomeManager = original.call(noiseBiomeSource, biomeZoomSeed);

        if (chaosawakens$optimizedBiomeManager == null) this.chaosawakens$optimizedBiomeManager = new OptimizedBiomeManager(noiseBiomeSource, biomeZoomSeed);

        return curDim.location().getNamespace().equals(CAConstants.MODID) ? chaosawakens$optimizedBiomeManager : originalBiomeManager;
    }
}
