package io.github.chaosawakens.common.events;

import io.github.chaosawakens.ChaosAwakens;
import net.minecraft.block.Block;
import net.minecraft.entity.EntityType;
import net.minecraft.item.Item;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber.Bus;
import net.minecraftforge.registries.ForgeRegistries;

//TODO Automate all the remappings, probably by making a class that stores all the older information --Meme Man
@EventBusSubscriber(modid = ChaosAwakens.MODID, bus = Bus.FORGE)
public class RemapHandler {
	/**
	 * Remap Fossilised Frog to Fossilised Tree Frog
	 */
	@SubscribeEvent
	public static void remapBlocks(RegistryEvent.MissingMappings<Block> event) {
		for (RegistryEvent.MissingMappings.Mapping<Block> mapping : event.getAllMappings()) {
			if (mapping.key.getNamespace().equals(ChaosAwakens.MODID) && mapping.key.getPath().contains("fossilised_frog")) {
				String newName = mapping.key.getPath().replace("fossilised_frog", "fossilised_tree_frog");
				ResourceLocation remap = new ResourceLocation(ChaosAwakens.MODID, newName);
				mapping.remap(ForgeRegistries.BLOCKS.getValue(remap));
			}
/*			if (mapping.key.getNamespace().equals(ChaosAwakens.MODID) && mapping.key.getPath().contains("crystalised_crystal_apple_cow")) {
				String newName = mapping.key.getPath().replace("crystalised_crystal_apple_cow", "crystallised_crystal_apple_cow");
				ResourceLocation remap = new ResourceLocation(ChaosAwakens.MODID, newName);
				mapping.remap(ForgeRegistries.BLOCKS.getValue(remap));
			}*/
		}
	}

	/**
	 * Remap Frog Spawn Egg to Tree Frog Spawn Egg
	 */
	@SubscribeEvent
	public static void remapItems(RegistryEvent.MissingMappings<Item> event) {
		for (RegistryEvent.MissingMappings.Mapping<Item> mapping : event.getAllMappings()) {
			if (mapping.key.getNamespace().equals(ChaosAwakens.MODID) && mapping.key.getPath().contains("frog_spawn_egg")) {
				String newName = mapping.key.getPath().replace("frog_spawn_egg", "tree_frog_spawn_egg");
				ResourceLocation remap = new ResourceLocation(ChaosAwakens.MODID, newName);
				mapping.remap(ForgeRegistries.ITEMS.getValue(remap));
			}
		}
	}

	/**
	 * Remap Frog to Tree Frog
	 */
	@SubscribeEvent
	public static void remapEntities(RegistryEvent.MissingMappings<EntityType<?>> event) {
		for (RegistryEvent.MissingMappings.Mapping<EntityType<?>> mapping : event.getAllMappings()) {
			if (mapping.key.getNamespace().equals(ChaosAwakens.MODID) && mapping.key.getPath().contains("frog")) {
				String newName = mapping.key.getPath().replace("frog", "tree_frog");
				ResourceLocation remap = new ResourceLocation(ChaosAwakens.MODID, newName);
				mapping.remap(ForgeRegistries.ENTITIES.getValue(remap));
			}
		}
	}
}
