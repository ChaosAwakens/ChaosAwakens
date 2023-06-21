package io.github.chaosawakens.common.items.base;

import io.github.chaosawakens.api.item.IShieldMaterial;
import net.minecraft.entity.LivingEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ShieldItem;
import software.bernie.geckolib3.core.IAnimatable;
import software.bernie.geckolib3.core.manager.AnimationData;
import software.bernie.geckolib3.core.manager.AnimationFactory;

public class AnimatableShieldItem extends ShieldItem implements IAnimatable {
	private final AnimationFactory factory = new AnimationFactory(this);
	private final IShieldMaterial shieldMaterial;

	public AnimatableShieldItem(IShieldMaterial shieldMaterial, Properties properties) {
		super(properties.durability(shieldMaterial.getDurability()));
		this.shieldMaterial = shieldMaterial;
	}
	
	public IShieldMaterial getShieldMaterial() {
		return shieldMaterial;
	}
	
	@Override
	public int getEnchantmentValue() {
		return shieldMaterial.getEnchantability();
	}
	
	@Override
	public boolean isRepairable(ItemStack targetStack) {
		return shieldMaterial.getRepairIngredient().test(targetStack);
	}
	
	@Override
	public boolean isShield(ItemStack stack, LivingEntity entity) {
		return true;
	}

	@Override
	public void registerControllers(AnimationData data) {		
	}

	@Override
	public AnimationFactory getFactory() {
		return factory;
	}
}
