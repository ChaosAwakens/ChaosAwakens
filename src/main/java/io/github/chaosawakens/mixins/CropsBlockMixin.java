package io.github.chaosawakens.mixins;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import io.github.chaosawakens.common.registry.CATags;
import net.minecraft.block.BlockState;
import net.minecraft.block.CropsBlock;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockReader;

@Mixin(CropsBlock.class)
public abstract class CropsBlockMixin {
	
	@Inject(method = "Lnet/minecraft/block/CropsBlock;mayPlaceOn(Lnet/minecraft/block/BlockState;Lnet/minecraft/world/IBlockReader;Lnet/minecraft/util/math/BlockPos;)Z", at = @At("HEAD"), cancellable = true)
	private void chaosawakens$mayPlaceOn(BlockState state, IBlockReader level, BlockPos pos, CallbackInfoReturnable<Boolean> cir) {
		if(state.is(CATags.Blocks.FARMABLE)) cir.setReturnValue(true);
	}
}
