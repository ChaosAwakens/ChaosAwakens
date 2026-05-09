package io.github.chaosawakens.mixins.block;

import com.llamalad7.mixinextras.injector.ModifyReturnValue;
import com.llamalad7.mixinextras.sugar.Local;
import io.github.chaosawakens.content.registry.CATags;
import net.minecraft.world.level.block.BushBlock;
import net.minecraft.world.level.block.state.BlockState;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;

@Mixin(BushBlock.class)
public abstract class BushBlockMixin {

    private BushBlockMixin() {
        throw new IllegalAccessError("Attempted to construct standalone Mixin Class! (BushBlockMixin)");
    }

    @ModifyReturnValue(method = "mayPlaceOn", at = @At("RETURN"))
    private boolean chaosawakens$mayPlaceOn(boolean original, @Local(argsOnly = true) BlockState targetState) {
        return original || targetState.is(CATags.CABlockTags.FARMLAND_BLOCKS.get());
    }
}
