package io.github.chaosawakens.manager;

import io.github.chaosawakens.common.network.packets.s2c.EnforceAssetsPacket;
import io.github.chaosawakens.common.registry.*;
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
		CARecipeTypes.RECIPE_SERIALIZERS.register(modBus);
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
