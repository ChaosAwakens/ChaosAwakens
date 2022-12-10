package io.github.chaosawakens.common.registry;

import net.minecraft.util.DamageSource;

public class CADamageSources {
	
	public static final DamageSource SUNBURN = new DamageSource("sunburn").setIsFire();
	public static final DamageSource BURNS = new DamageSource("burns").setMagic().setIsFire();

}
