package io.github.chaosawakens.common.registry;

import io.github.chaosawakens.ChaosAwakens;
import net.minecraft.block.WoodType;
import net.minecraft.client.renderer.Atlases;
import net.minecraft.util.ResourceLocation;

public class CAWoodTypes {
	public static final WoodType APPLE = WoodType.create(new ResourceLocation(ChaosAwakens.MODID, "apple").toString());
	public static final WoodType CHERRY = WoodType.create(new ResourceLocation(ChaosAwakens.MODID, "cherry").toString());
	public static final WoodType DENSEWOOD = WoodType.create(new ResourceLocation(ChaosAwakens.MODID, "densewood").toString());
	public static final WoodType DUPLICATION = WoodType.create(new ResourceLocation(ChaosAwakens.MODID, "duplication").toString());
	public static final WoodType GINKGO = WoodType.create(new ResourceLocation(ChaosAwakens.MODID, "ginkgo").toString());
	public static final WoodType MESOZOIC = WoodType.create(new ResourceLocation(ChaosAwakens.MODID, "mesozoic").toString());
	public static final WoodType PEACH = WoodType.create(new ResourceLocation(ChaosAwakens.MODID, "peach").toString());
	public static final WoodType SKYWOOD = WoodType.create(new ResourceLocation(ChaosAwakens.MODID, "skywood").toString());
	
	public static void registerWoodtypes() {
		WoodType.register(CAWoodTypes.APPLE);
		WoodType.register(CAWoodTypes.CHERRY);
		WoodType.register(CAWoodTypes.DENSEWOOD);
		WoodType.register(CAWoodTypes.DUPLICATION);
		WoodType.register(CAWoodTypes.GINKGO);
		WoodType.register(CAWoodTypes.MESOZOIC);
		WoodType.register(CAWoodTypes.PEACH);
		WoodType.register(CAWoodTypes.SKYWOOD);
	}
	
	public static void registerWoodTypesAtlas() {
		Atlases.addWoodType(CAWoodTypes.APPLE);
		Atlases.addWoodType(CAWoodTypes.CHERRY);
		Atlases.addWoodType(CAWoodTypes.DENSEWOOD);
		Atlases.addWoodType(CAWoodTypes.DUPLICATION);
		Atlases.addWoodType(CAWoodTypes.GINKGO);
		Atlases.addWoodType(CAWoodTypes.MESOZOIC);
		Atlases.addWoodType(CAWoodTypes.PEACH);
		Atlases.addWoodType(CAWoodTypes.SKYWOOD);
	}
}
