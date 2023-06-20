package io.github.chaosawakens.common.items.base;

import java.util.function.Supplier;

import io.github.chaosawakens.common.util.EnumUtil.CAItemTier;
import net.minecraft.block.BlockState;
import net.minecraft.tags.BlockTags;
import net.minecraftforge.common.ForgeConfigSpec.IntValue;

public class ScytheItem extends CASwordItem {
	
	public ScytheItem(CAItemTier pTier, Supplier<IntValue> configDmg, Properties pProperties) {
		super(pTier, configDmg, -1.9F, pProperties);
	}
	
	public ScytheItem(CAItemTier pTier, Supplier<IntValue> configDmg, double reach, Properties pProperties) {
		super(pTier, configDmg, -1.9F, reach, pProperties);
	}

	@Override
	public boolean isCorrectToolForDrops(BlockState targetState) {
		return targetState.is(BlockTags.CROPS);
	}
}