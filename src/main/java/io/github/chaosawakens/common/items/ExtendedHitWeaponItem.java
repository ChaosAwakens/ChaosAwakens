package io.github.chaosawakens.common.items;

import net.minecraft.item.*;
import software.bernie.geckolib3.core.*;
import software.bernie.geckolib3.core.manager.*;

public class ExtendedHitWeaponItem extends SwordItem implements IAnimatable {

	public AnimationFactory factory = new AnimationFactory(this);

	public ExtendedHitWeaponItem(IItemTier tier, int attackDamageIn, float attackSpeedIn, Properties builderIn) {
		super(tier, attackDamageIn, attackSpeedIn, builderIn);
	}

	@Override
	public void registerControllers(AnimationData data) {
		// insert controllers here
	}

	@Override
	public AnimationFactory getFactory()
	{
		return this.factory;
	}
}