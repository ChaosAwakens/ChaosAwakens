package io.github.chaosawakens.mixins.block;

import com.llamalad7.mixinextras.injector.ModifyReturnValue;
import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import com.llamalad7.mixinextras.sugar.Local;
import io.github.chaosawakens.content.registry.CATags;
import net.minecraft.tags.TagKey;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.StemBlock;
import net.minecraft.world.level.block.state.BlockState;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;

@Mixin(StemBlock.class)
public abstract class StemBlockMixin {

    private StemBlockMixin() {
        throw new IllegalAccessError("Attempted to construct standalone Mixin Class! (StemBlockMixin)");
    }

    @ModifyReturnValue(method = "mayPlaceOn", at = @At("RETURN"))
    private boolean chaosawakens$mayPlaceOn(boolean original, @Local(argsOnly = true) BlockState targetState) {
        return original || targetState.is(CATags.CABlockTags.FARMLAND_BLOCKS.get());
    }

    @WrapOperation(method = "randomTick", at = @At(value = "INVOKE", target = "Lnet/minecraft/world/level/block/state/BlockState;is(Lnet/minecraft/tags/TagKey;)Z"))
    private boolean chaosawakens$alternativelyRandomlyTick(BlockState originalState, TagKey<Block> originalTagKey, Operation<Boolean> original) {
        return original.call(originalState, originalTagKey) || originalState.is(CATags.CABlockTags.FARMLAND_BLOCKS.get());
    }
}
