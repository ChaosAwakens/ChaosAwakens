package io.github.chaosawakens.common.items.weapons.extended;

import java.util.function.Supplier;

import io.github.chaosawakens.client.renderers.item.extended.RoyalGuardianSwordItemRenderer;
import io.github.chaosawakens.common.items.base.EnchantedSwordItem;
import io.github.chaosawakens.common.util.EnumUtil.CAItemTier;
import net.minecraft.enchantment.EnchantmentData;
import net.minecraft.entity.LivingEntity;
import net.minecraft.item.ItemStack;
import net.minecraftforge.common.ForgeConfigSpec.IntValue;
import software.bernie.geckolib3.core.IAnimatable;
import software.bernie.geckolib3.core.manager.AnimationData;
import software.bernie.geckolib3.core.manager.AnimationFactory;

public class RoyalGuardianSwordItem extends EnchantedSwordItem implements IAnimatable {
	private final AnimationFactory factory = new AnimationFactory(this);
	
	public RoyalGuardianSwordItem(CAItemTier pTier, Supplier<IntValue> configDmg, Properties pProperties, Supplier<EnchantmentData[]> enchantments) {
		super(pTier, configDmg, -3.25F, 5.5D, pProperties.setISTER(() -> RoyalGuardianSwordItemRenderer::new), enchantments);
	}

	@Override
	public void registerControllers(AnimationData data) {
	}

	@Override
	public AnimationFactory getFactory() {
		return factory;
	}

	@Override
	public boolean canDisableShield(ItemStack stack, ItemStack shield, LivingEntity entity, LivingEntity attacker) {
		return true;
	}
}
