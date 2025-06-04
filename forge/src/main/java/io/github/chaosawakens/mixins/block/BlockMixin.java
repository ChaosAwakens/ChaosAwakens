package io.github.chaosawakens.mixins.block;

import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import io.github.chaosawakens.common.registry.CATags;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;

@Mixin(Block.class)
public abstract class BlockMixin { // Screw you arbitrary loader hooks pt. 2

    private BlockMixin() {
        throw new IllegalAccessError("Attempted to construct Mixin Class!");
    }

    @WrapOperation(method = "canSustainPlant", at = @At(value = "INVOKE", target = "Lnet/minecraft/world/level/block/state/BlockState;is(Lnet/minecraft/world/level/block/Block;)Z", ordinal = 1))
    private boolean chaosawakens$canSustainPlant(BlockState originalState, Block originalBlock, Operation<Boolean> originalReturnValue) {
        return originalState.is(CATags.CABlockTags.FARMLAND_BLOCKS.get()) || originalReturnValue.call(originalState, originalBlock);
    }
}