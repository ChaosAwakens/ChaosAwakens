package io.github.chaosawakens.manager;

import io.github.chaosawakens.common.registry.CAAttributes;
import io.github.chaosawakens.common.registry.CABiomes;
import io.github.chaosawakens.common.registry.CABlocks;
import io.github.chaosawakens.common.registry.CACarvers;
import io.github.chaosawakens.common.registry.CAContainerTypes;
import io.github.chaosawakens.common.registry.CAEffects;
import io.github.chaosawakens.common.registry.CAEnchantments;
import io.github.chaosawakens.common.registry.CAEntityTypes;
import io.github.chaosawakens.common.registry.CAFeatures;
import io.github.chaosawakens.common.registry.CAFoliagePlacerTypes;
import io.github.chaosawakens.common.registry.CAItems;
import io.github.chaosawakens.common.registry.CALootModifiers;
import io.github.chaosawakens.common.registry.CAPaintings;
import io.github.chaosawakens.common.registry.CAParticleTypes;
import io.github.chaosawakens.common.registry.CARecipes;
import io.github.chaosawakens.common.registry.CASoundEvents;
import io.github.chaosawakens.common.registry.CAStats;
import io.github.chaosawakens.common.registry.CAStructures;
import io.github.chaosawakens.common.registry.CASurfaceBuilders;
import io.github.chaosawakens.common.registry.CATileEntities;
import io.github.chaosawakens.common.registry.CATreeDecoratorTypes;
import io.github.chaosawakens.common.registry.CAVillagers;
import net.minecraftforge.eventbus.api.IEventBus;

public class CARegistryManager {

	protected static void registerRegistries(IEventBus modBus) {
		CAAttributes.ATTRIBUTES.register(modBus);
		CABiomes.BIOMES.register(modBus);
		CABlocks.ITEM_BLOCKS.register(modBus);
		CABlocks.BLOCKS.register(modBus);
		CAContainerTypes.CONTAINERS.register(modBus);
		CAEntityTypes.ENTITY_TYPES.register(modBus);
		CAParticleTypes.PARTICLE_TYPES.register(modBus);
		CAPaintings.PAINTINGS.register(modBus);
		CAEnchantments.ENCHANTMENTS.register(modBus); 
		CAItems.ITEMS.register(modBus);
		CAEffects.EFFECTS.register(modBus);
		CAEffects.POTIONS.register(modBus);
		CATileEntities.TILE_ENTITIES.register(modBus);
		CARecipes.RECIPE_SERIALIZERS.register(modBus);
		CAStats.STAT_TYPES.register(modBus);
		CAStructures.STRUCTURES.register(modBus);
		CAFeatures.FEATURES.register(modBus);
		CACarvers.CARVERS.register(modBus);
		CASoundEvents.SOUND_EVENTS.register(modBus);
		CAVillagers.POI_TYPES.register(modBus);
		CAVillagers.PROFESSIONS.register(modBus);
		CALootModifiers.LOOT_MODIFIERS.register(modBus);
		CAFoliagePlacerTypes.FOLIAGE_PLACER_TYPES.register(modBus);
		CATreeDecoratorTypes.TREE_DECORATOR_TYPES.register(modBus);
		CASurfaceBuilders.SURFACE_BUILDERS.register(modBus);
	}
}
