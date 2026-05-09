package io.github.chaosawakens.mixins.block;

import com.llamalad7.mixinextras.injector.ModifyReturnValue;
import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import com.llamalad7.mixinextras.sugar.Local;
import io.github.chaosawakens.content.registry.CATags;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.CropBlock;
import net.minecraft.world.level.block.state.BlockState;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;

@Mixin(CropBlock.class)
public final class CropBlockMixin {

    private CropBlockMixin() {
        throw new IllegalAccessError("Attempted to construct standalone Mixin Class! (CropBlockMixin)");
    }

    @WrapOperation(method = "getGrowthSpeed", at = @At(value = "INVOKE", target = "Lnet/minecraft/world/level/block/state/BlockState;is(Lnet/minecraft/world/level/block/Block;)Z", ordinal = 0))
    private static boolean chaosawakens$alternativelyAmplifyGrowthSpeed(BlockState originalState, Block originalBlock, Operation<Boolean> original) {
        return original.call(originalState, originalBlock) || originalState.is(CATags.CABlockTags.FARMLAND_BLOCKS.get());
    }

    @ModifyReturnValue(method = "mayPlaceOn", at = @At("RETURN"))
    private boolean chaosawakens$mayPlaceOn(boolean original, @Local(argsOnly = true) BlockState targetState) {
        return original || targetState.is(CATags.CABlockTags.FARMLAND_BLOCKS.get());
    }
}
