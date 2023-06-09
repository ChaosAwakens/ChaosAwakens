package io.github.chaosawakens.common.registry;

import io.github.chaosawakens.ChaosAwakens;
import net.minecraft.block.WoodType;
import net.minecraft.client.renderer.Atlases;

public class CAWoodTypes {
	public static final WoodType APPLE = WoodType.create(ChaosAwakens.prefix("apple").toString());
	public static final WoodType CHERRY = WoodType.create(ChaosAwakens.prefix("cherry").toString());
	public static final WoodType DENSEWOOD = WoodType.create(ChaosAwakens.prefix("densewood").toString());
	public static final WoodType DUPLICATION = WoodType.create(ChaosAwakens.prefix("duplication").toString());
	public static final WoodType GINKGO = WoodType.create(ChaosAwakens.prefix("ginkgo").toString());
	public static final WoodType MESOZOIC = WoodType.create(ChaosAwakens.prefix("mesozoic").toString());
	public static final WoodType PEACH = WoodType.create(ChaosAwakens.prefix("peach").toString());
	public static final WoodType SKYWOOD = WoodType.create(ChaosAwakens.prefix("skywood").toString());
	public static final WoodType CRYSTALWOOD = WoodType.create(ChaosAwakens.prefix("crystalwood").toString());
	
	public static void registerWoodtypes() {
		WoodType.register(CAWoodTypes.APPLE);
		WoodType.register(CAWoodTypes.CHERRY);
		WoodType.register(CAWoodTypes.DENSEWOOD);
		WoodType.register(CAWoodTypes.DUPLICATION);
		WoodType.register(CAWoodTypes.GINKGO);
		WoodType.register(CAWoodTypes.MESOZOIC);
		WoodType.register(CAWoodTypes.PEACH);
		WoodType.register(CAWoodTypes.SKYWOOD);
		WoodType.register(CAWoodTypes.CRYSTALWOOD);
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
		Atlases.addWoodType(CAWoodTypes.CRYSTALWOOD);
	}
}
