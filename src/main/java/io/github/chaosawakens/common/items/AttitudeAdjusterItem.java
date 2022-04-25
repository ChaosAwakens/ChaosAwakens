package io.github.chaosawakens.common.items;

import io.github.chaosawakens.common.config.CAConfig;
import net.minecraft.enchantment.IVanishable;
import net.minecraft.entity.LivingEntity;
import net.minecraft.inventory.EquipmentSlotType;
import net.minecraft.item.IItemTier;
import net.minecraft.item.ItemStack;
import net.minecraft.world.Explosion;
import software.bernie.geckolib3.core.IAnimatable;
import software.bernie.geckolib3.core.manager.AnimationData;
import software.bernie.geckolib3.core.manager.AnimationFactory;

public class AttitudeAdjusterItem extends ExtendedHitWeaponItem implements IVanishable, IAnimatable {
	public AnimationFactory factory = new AnimationFactory(this);

	private static final float EXPLOSION_POWER = CAConfig.COMMON.attitudeAdjusterExplosionSize.get();

	public AttitudeAdjusterItem(IItemTier tier, int attackDamageIn, float attackSpeedIn, double reachDistance,
			double knockBack, Properties builderIn) {
		super(tier, attackDamageIn, attackSpeedIn, reachDistance, knockBack, builderIn);
	}

	@Override
	public boolean hurtEnemy(ItemStack stack, LivingEntity target, LivingEntity attacker) {
		if (!target.level.isClientSide) {
			target.level.explode(null, target.position().x, target.position().y, target.position().z, EXPLOSION_POWER,
					false, Explosion.Mode.DESTROY);
		}

		stack.hurtAndBreak(1, attacker, (entity) -> entity.broadcastBreakEvent(EquipmentSlotType.MAINHAND));
		return true;
	}

	@Override
	public void registerControllers(AnimationData data) {
		// insert controllers here
	}

	@Override
	public AnimationFactory getFactory() {
		return this.factory;
	}

	@Override
	public boolean canDisableShield(ItemStack stack, ItemStack shield, LivingEntity entity, LivingEntity attacker) {
		return true;
	}
}
