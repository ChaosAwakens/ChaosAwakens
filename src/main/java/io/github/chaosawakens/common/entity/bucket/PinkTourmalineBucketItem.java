package io.github.chaosawakens.common.entity.bucket;

import java.util.function.Supplier;

import io.github.chaosawakens.common.items.base.CABucketItem;
import io.github.chaosawakens.common.registry.CAItems;
import net.minecraft.fluid.Fluid;
import net.minecraftforge.fml.RegistryObject;

public class PinkTourmalineBucketItem extends CABucketItem {

	public PinkTourmalineBucketItem(Supplier<? extends Fluid> targetFluidSup, Properties builder) {
		super(targetFluidSup, builder);
	}

	@Override
	public RegistryObject<? extends CABucketItem> getWaterBucketItem() {
		return CAItems.WATER_PINK_TOURMALINE_BUCKET;
	}

	@Override
	public RegistryObject<? extends CABucketItem> getLavaBucketItem() {
		return CAItems.LAVA_PINK_TOURMALINE_BUCKET;
	}
}
