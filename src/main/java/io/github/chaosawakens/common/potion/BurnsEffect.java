package io.github.chaosawakens.common.potion;

import net.minecraft.entity.LivingEntity;
import net.minecraft.potion.Effect;
import net.minecraft.potion.EffectType;
import net.minecraft.util.DamageSource;

/**
 * @author invalid2
 */
public class BurnsEffect extends Effect {

	/**
	 * @param p_i50391_1_
	 * @param p_i50391_2_
	 */
	public BurnsEffect(EffectType p_i50391_1_, int p_i50391_2_) {
		super(p_i50391_1_, p_i50391_2_);
	}
	
	public void applyEffectTick(LivingEntity e, int p_76394_2_) {
		e.hurt(DamageSource.WITHER, 1.0f);
	}
	
	public boolean isDurationEffectTick(int p_76397_1_, int p_76397_2_) {
		return p_76397_1_ % (30 >> p_76397_2_) == 0;
	}
}
