package io.github.chaosawakens.common.registry;

import net.minecraft.util.DamageSource;

public class CADamageSources {
	public static final DamageSource SUNBURN = new DamageSource("sunburn").setIsFire();
	public static final DamageSource BURNS = new DamageSource("burns").setMagic().setIsFire();
	public static final DamageSource THORNY_SUN = new DamageSource("thorny_sun");
	public static final DamageSource SHOCKWAVE = new DamageSource("shockwave").setMagic().setExplosion();
}
