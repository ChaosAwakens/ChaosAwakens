package io.github.chaosawakens.common.registry;

import net.minecraft.block.SoundType;
import net.minecraftforge.common.util.ForgeSoundType;

public class CASoundType {
	public static final SoundType SKY_MOSS_CARPET = new ForgeSoundType(1.0F, 1.0F, CASoundEvents.SKY_MOSS_CARPET_BREAK, CASoundEvents.SKY_MOSS_CARPET_STEP, CASoundEvents.SKY_MOSS_CARPET_PLACE, CASoundEvents.SKY_MOSS_CARPET_HIT, CASoundEvents.SKY_MOSS_CARPET_FALL);
	public static final SoundType SKY_MOSS = new ForgeSoundType(1.0F, 1.0F, CASoundEvents.SKY_MOSS_BREAK, CASoundEvents.SKY_MOSS_STEP, CASoundEvents.SKY_MOSS_PLACE, CASoundEvents.SKY_MOSS_HIT, CASoundEvents.SKY_MOSS_FALL);
}
