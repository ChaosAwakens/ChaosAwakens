package io.github.chaosawakens.common.items.weapons.extended;

import java.util.function.Supplier;

import io.github.chaosawakens.client.renderers.item.extended.BattleAxeItemRenderer;
import io.github.chaosawakens.common.items.base.EnchantedAxeItem;
import io.github.chaosawakens.common.util.EnumUtil.CAItemTier;
import net.minecraft.enchantment.EnchantmentData;
import net.minecraftforge.common.ForgeConfigSpec.IntValue;
import software.bernie.geckolib3.core.IAnimatable;
import software.bernie.geckolib3.core.manager.AnimationData;
import software.bernie.geckolib3.core.manager.AnimationFactory;

public class BattleAxeItem extends EnchantedAxeItem implements IAnimatable {
	private final AnimationFactory factory = new AnimationFactory(this);
	
	public BattleAxeItem(CAItemTier pTier, Supplier<IntValue> configDmg, Properties pProperties, Supplier<EnchantmentData[]> enchantments) {
		super(pTier, configDmg, -3.35F, 3.0D, pProperties.setISTER(() -> BattleAxeItemRenderer::new), enchantments);
	}

	@Override
	public void registerControllers(AnimationData data) {
	}

	@Override
	public AnimationFactory getFactory() {
		return factory;
	}
}
