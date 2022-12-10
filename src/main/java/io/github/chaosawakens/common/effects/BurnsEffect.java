package io.github.chaosawakens.common.effects;

import io.github.chaosawakens.common.registry.CADamageSources;
import net.minecraft.entity.LivingEntity;
import net.minecraft.potion.Effect;
import net.minecraft.potion.EffectType;

public class BurnsEffect extends Effect {

	public BurnsEffect() {
		super(EffectType.HARMFUL, 14241294);
	}
	
	public void applyEffectTick(LivingEntity targetEntity, int exhaustion) {
		targetEntity.hurt(CADamageSources.BURNS, 1.0f);
	}
	
	public boolean isDurationEffectTick(int tickCount, int tick) {
		return tickCount % (30 >> tick) == 0;
	}

}
