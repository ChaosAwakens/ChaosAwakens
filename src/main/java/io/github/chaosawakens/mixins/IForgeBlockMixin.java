package io.github.chaosawakens.mixins;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;

import io.github.chaosawakens.api.IUtilityHelper;
import io.github.chaosawakens.common.registry.CAItems;
import io.github.chaosawakens.manager.CAConfigManager;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IWorldReader;
import net.minecraft.world.server.ServerWorld;
import net.minecraftforge.common.extensions.IForgeBlock;

@Mixin(IForgeBlock.class)
public interface IForgeBlockMixin {
	
	@Overwrite(remap = false)
	default float getEnchantPowerBonus(BlockState state, IWorldReader world, BlockPos pos) {		
		if (!world.isClientSide()) {
			ServerWorld server = (ServerWorld) world;
			
			//TODO Restrict to radius
			for (PlayerEntity player : server.players()) {
				if (IUtilityHelper.isFullArmorSet(player, CAItems.LAPIS_HELMET.get(), CAItems.LAPIS_CHESTPLATE.get(), CAItems.LAPIS_LEGGINGS.get(), CAItems.LAPIS_BOOTS.get())) {
					return state.is(Blocks.BOOKSHELF) ? 1 * CAConfigManager.MAIN_COMMON.lapisArmorSetBookshelfPowerModifier.get() : 0F;
				}
			}
		}	
		return state.is(Blocks.BOOKSHELF) ? 1 : 0;
	}
}
