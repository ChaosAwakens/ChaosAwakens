package io.github.chaosawakens.common.registry;

import io.github.chaosawakens.ChaosAwakens;
import io.github.chaosawakens.common.worldgen.feature.GeodeFeature;
import io.github.chaosawakens.common.worldgen.feature.GeodeFeatureConfig;
import io.github.chaosawakens.common.worldgen.feature.StalagmiteFeature;
import io.github.chaosawakens.common.worldgen.feature.StalagmiteFeatureConfig;
import net.minecraft.world.gen.feature.Feature;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;

public class CAFeatures {
	public static final DeferredRegister<Feature<?>> FEATURES = DeferredRegister.create(ForgeRegistries.FEATURES, ChaosAwakens.MODID);

	public static final RegistryObject<GeodeFeature> GEODE = FEATURES.register("geode", () -> new GeodeFeature(GeodeFeatureConfig.CODEC));
	public static final RegistryObject<StalagmiteFeature> STALAGMITE = FEATURES.register("stalagmite", () -> new StalagmiteFeature(StalagmiteFeatureConfig.CODEC));
}
