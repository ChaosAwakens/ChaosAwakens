package io.github.chaosawakens.common.items.weapons.extended;

import java.util.function.Supplier;

import io.github.chaosawakens.client.renderers.item.extended.AttitudeAdjusterItemRenderer;
import io.github.chaosawakens.common.items.base.CASwordItem;
import io.github.chaosawakens.common.util.EnumUtil.CAItemTier;
import io.github.chaosawakens.manager.CAConfigManager;
import net.minecraft.entity.LivingEntity;
import net.minecraft.inventory.EquipmentSlotType;
import net.minecraft.item.ItemStack;
import net.minecraft.world.Explosion;
import net.minecraftforge.common.ForgeConfigSpec.IntValue;
import software.bernie.geckolib3.core.IAnimatable;
import software.bernie.geckolib3.core.manager.AnimationData;
import software.bernie.geckolib3.core.manager.AnimationFactory;

public class AttitudeAdjusterItem extends CASwordItem implements IAnimatable {
	private final AnimationFactory factory = new AnimationFactory(this);
	private static final float EXPLOSION_POWER = CAConfigManager.MAIN_COMMON.attitudeAdjusterExplosionSize.get();

	public AttitudeAdjusterItem(CAItemTier pTier, Supplier<IntValue> configDmg, Properties pProperties) {
		super(pTier, configDmg, -3F, 3.5D, 1, pProperties.setISTER(() -> AttitudeAdjusterItemRenderer::new));
	}

	@Override
	public boolean hurtEnemy(ItemStack stack, LivingEntity target, LivingEntity attacker) {
		if (!target.level.isClientSide) {
			boolean hasFire = CAConfigManager.MAIN_COMMON.attitudeAdjusterExplosionFire.get();
			
			switch (CAConfigManager.MAIN_COMMON.attitudeAdjusterExplosionType.get()) {
			case NONE: target.level.explode(null, target.position().x, target.position().y, target.position().z, EXPLOSION_POWER, hasFire, Explosion.Mode.NONE);
			case BREAK: target.level.explode(null, target.position().x, target.position().y, target.position().z, EXPLOSION_POWER, hasFire, Explosion.Mode.BREAK);
			case DESTROY: target.level.explode(null, target.position().x, target.position().y, target.position().z, EXPLOSION_POWER, hasFire, Explosion.Mode.DESTROY);
			}
		}
		stack.hurtAndBreak(1, attacker, (entity) -> entity.broadcastBreakEvent(EquipmentSlotType.MAINHAND));
		return true;
	}
	
	@Override
	public boolean canDisableShield(ItemStack stack, ItemStack shield, LivingEntity entity, LivingEntity attacker) {
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
