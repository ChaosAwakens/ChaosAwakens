package io.github.chaosawakens.common.items.weapons.extended;

import java.util.function.Supplier;

import io.github.chaosawakens.common.items.base.EnchantedAxeItem;
import io.github.chaosawakens.common.util.EnumUtil.CAItemTier;
import net.minecraft.enchantment.EnchantmentData;
import net.minecraft.enchantment.IVanishable;
import net.minecraftforge.common.ForgeConfigSpec.IntValue;
import software.bernie.geckolib3.core.IAnimatable;
import software.bernie.geckolib3.core.manager.AnimationData;
import software.bernie.geckolib3.core.manager.AnimationFactory;

public class QueenScaleBattleAxeItem extends EnchantedAxeItem implements IVanishable, IAnimatable {
	private final AnimationFactory factory = new AnimationFactory(this);
	
	public QueenScaleBattleAxeItem(CAItemTier pTier, Supplier<IntValue> configDmg, Properties pProperties, Supplier<EnchantmentData[]> enchantments) {
		super(pTier, configDmg, -3.25F, 4.0D, pProperties, enchantments);
	}

	@Override
	public void registerControllers(AnimationData data) {
	}

	@Override
	public AnimationFactory getFactory() {
		return factory;
	}
}
