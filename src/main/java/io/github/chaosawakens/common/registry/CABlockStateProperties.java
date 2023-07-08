package io.github.chaosawakens.common.registry;

import net.minecraft.state.IntegerProperty;

public class CABlockStateProperties {
	
	// Glow
	public static final IntegerProperty URANIUM_GLOW_STRENGTH = IntegerProperty.create("glow_strength", 0, 5);
	
	// Age
	public static final IntegerProperty AGE_4 = IntegerProperty.create("age", 0, 4);
}
