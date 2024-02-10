package io.github.chaosawakens.common.events;

import com.google.common.collect.ImmutableMultimap;
import com.google.common.collect.Multimap;
import com.mojang.brigadier.CommandDispatcher;
import com.mojang.datafixers.util.Pair;
import com.mojang.serialization.Codec;
import io.github.chaosawakens.ChaosAwakens;
import io.github.chaosawakens.api.item.ICATieredItem;
import io.github.chaosawakens.api.wrapper.CarverWrapper;
import io.github.chaosawakens.api.wrapper.FeatureWrapper;
import io.github.chaosawakens.common.entity.boss.insect.HerculesBeetleEntity;
import io.github.chaosawakens.common.entity.boss.robo.RoboJefferyEntity;
import io.github.chaosawakens.common.entity.creature.air.BirdEntity;
import io.github.chaosawakens.common.entity.creature.land.*;
import io.github.chaosawakens.common.entity.creature.land.applecow.*;
import io.github.chaosawakens.common.entity.creature.land.carrotpig.CarrotPigEntity;
import io.github.chaosawakens.common.entity.creature.land.carrotpig.CrystalCarrotPigEntity;
import io.github.chaosawakens.common.entity.creature.land.carrotpig.EnchantedGoldenCarrotPigEntity;
import io.github.chaosawakens.common.entity.creature.land.carrotpig.GoldenCarrotPigEntity;
import io.github.chaosawakens.common.entity.creature.water.WhaleEntity;
import io.github.chaosawakens.common.entity.creature.water.fish.GreenFishEntity;
import io.github.chaosawakens.common.entity.creature.water.fish.RockFishEntity;
import io.github.chaosawakens.common.entity.creature.water.fish.SparkFishEntity;
import io.github.chaosawakens.common.entity.creature.water.fish.WoodFishEntity;
import io.github.chaosawakens.common.entity.hostile.AggressiveAntEntity;
import io.github.chaosawakens.common.entity.hostile.EntEntity;
import io.github.chaosawakens.common.entity.hostile.insect.WaspEntity;
import io.github.chaosawakens.common.entity.hostile.robo.RoboPounderEntity;
import io.github.chaosawakens.common.entity.hostile.robo.RoboSniperEntity;
import io.github.chaosawakens.common.entity.hostile.robo.RoboWarriorEntity;
import io.github.chaosawakens.common.entity.neutral.land.dino.DimetrodonEntity;
import io.github.chaosawakens.common.entity.neutral.land.gator.CrystalGatorEntity;
import io.github.chaosawakens.common.entity.neutral.land.gator.EmeraldGatorEntity;
import io.github.chaosawakens.common.items.base.CABoatItem;
import io.github.chaosawakens.common.registry.*;
import io.github.chaosawakens.common.util.ObjectUtil;
import io.github.chaosawakens.common.util.TradeUtil;
import io.github.chaosawakens.common.util.TradeUtil.CABasicTrade;
import io.github.chaosawakens.common.util.TradeUtil.CAIngredientTrade;
import io.github.chaosawakens.common.worldgen.BiomeHandlers.GenerationHandler;
import io.github.chaosawakens.common.worldgen.BiomeHandlers.MobSpawnHandler;
import io.github.chaosawakens.data.provider.*;
import io.github.chaosawakens.data.provider.CATagProvider.CABlockTagProvider;
import io.github.chaosawakens.data.provider.CATagProvider.CAEntityTypeTagProvider;
import io.github.chaosawakens.data.provider.CATagProvider.CAItemTagProvider;
import io.github.chaosawakens.manager.CAConfigManager;
import io.github.chaosawakens.manager.CANetworkManager;
import it.unimi.dsi.fastutil.objects.Object2ObjectArrayMap;
import it.unimi.dsi.fastutil.objects.ObjectArrayList;
import net.minecraft.block.Block;
import net.minecraft.command.CommandSource;
import net.minecraft.data.DataGenerator;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.ai.attributes.Attribute;
import net.minecraft.entity.ai.attributes.AttributeModifier;
import net.minecraft.entity.ai.attributes.Attributes;
import net.minecraft.entity.ai.attributes.RangedAttribute;
import net.minecraft.entity.merchant.villager.VillagerProfession;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.item.crafting.Ingredient;
import net.minecraft.util.RegistryKey;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.registry.Registry;
import net.minecraft.util.registry.WorldGenRegistries;
import net.minecraft.world.World;
import net.minecraft.world.gen.ChunkGenerator;
import net.minecraft.world.gen.FlatChunkGenerator;
import net.minecraft.world.gen.feature.structure.Structure;
import net.minecraft.world.gen.settings.DimensionStructuresSettings;
import net.minecraft.world.gen.settings.StructureSeparationSettings;
import net.minecraft.world.raid.Raid;
import net.minecraft.world.server.ServerChunkProvider;
import net.minecraft.world.server.ServerWorld;
import net.minecraftforge.common.BiomeDictionary;
import net.minecraftforge.common.ForgeConfigSpec.IntValue;
import net.minecraftforge.common.ForgeMod;
import net.minecraftforge.common.data.ExistingFileHelper;
import net.minecraftforge.common.util.Lazy;
import net.minecraftforge.event.AddReloadListenerEvent;
import net.minecraftforge.event.RegisterCommandsEvent;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.event.entity.EntityAttributeCreationEvent;
import net.minecraftforge.event.furnace.FurnaceFuelBurnTimeEvent;
import net.minecraftforge.event.village.VillagerTradesEvent;
import net.minecraftforge.event.village.WandererTradesEvent;
import net.minecraftforge.event.world.BiomeLoadingEvent;
import net.minecraftforge.event.world.WorldEvent;
import net.minecraftforge.eventbus.api.EventPriority;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.fml.common.ObfuscationReflectionHelper;
import net.minecraftforge.fml.common.ObfuscationReflectionHelper.UnableToFindMethodException;
import net.minecraftforge.fml.config.ModConfig;
import net.minecraftforge.fml.config.ModConfig.ModConfigEvent;
import net.minecraftforge.fml.config.ModConfig.Type;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.event.lifecycle.FMLLoadCompleteEvent;
import net.minecraftforge.fml.event.lifecycle.GatherDataEvent;
import net.minecraftforge.registries.ForgeRegistries;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Map.Entry;
import java.util.stream.Collectors;

public class CACommonSetupEvents {	
	public static ObjectArrayList<FeatureWrapper> CONFIG_FEATURES = new ObjectArrayList<FeatureWrapper>();
	public static ObjectArrayList<CarverWrapper> CONFIG_CARVERS = new ObjectArrayList<CarverWrapper>();
	private static Method codecMethod;
	
	public static class ForgeSetupEvents {
		
		@SuppressWarnings("unchecked")
		@SubscribeEvent
		public static void onDimensionalSpacingWorldLoadEvent(final WorldEvent.Load event) {
			if (!(event.getWorld() instanceof ServerWorld)) return;

			ServerWorld serverWorld = (ServerWorld) event.getWorld();
			ServerChunkProvider chunkProvider = serverWorld.getChunkSource();

			ChaosAwakens.debug("CURRENT DIMENSION", serverWorld.dimension());

			try {
				if (codecMethod == null) codecMethod = ObfuscationReflectionHelper.findMethod(ChunkGenerator.class, "codec");
				// TODO Fix this
				ResourceLocation chunkGeneratorKey = Registry.CHUNK_GENERATOR.getKey((Codec<? extends ChunkGenerator>) codecMethod.invoke(chunkProvider.generator));
				if (chunkGeneratorKey != null && chunkGeneratorKey.getNamespace().equals("terraforged")) return;
			} catch (IllegalAccessException | IllegalArgumentException | InvocationTargetException exception) {
				ChaosAwakens.LOGGER.warn("[WORLDGEN]: " + exception);
				exception.printStackTrace();
			} catch (UnableToFindMethodException exception) {
				if (CAConfigManager.MAIN_COMMON.terraforgedCheckMsg.get()) ChaosAwakens.LOGGER.info("[WORLDGEN]: Unable to check if " + serverWorld.dimension().location() + " is using Terraforged's ChunkGenerator due to Terraforged not being present or not accessible," + " if you aren't using Terraforged please ignore this message");
			}

			if (serverWorld.getChunkSource().getGenerator() instanceof FlatChunkGenerator && serverWorld.dimension().equals(World.OVERWORLD)) return;

			Object2ObjectArrayMap<Structure<?>, StructureSeparationSettings> newMap = new Object2ObjectArrayMap<>(chunkProvider.generator.getSettings().structureConfig());

			newMap.putIfAbsent(CAStructures.ACACIA_ENT_TREE.get(), DimensionStructuresSettings.DEFAULTS.get(CAStructures.ACACIA_ENT_TREE.get()));
			newMap.putIfAbsent(CAStructures.BIRCH_ENT_TREE.get(), DimensionStructuresSettings.DEFAULTS.get(CAStructures.BIRCH_ENT_TREE.get()));
			newMap.putIfAbsent(CAStructures.CRIMSON_ENT_TREE.get(), DimensionStructuresSettings.DEFAULTS.get(CAStructures.CRIMSON_ENT_TREE.get()));
			newMap.putIfAbsent(CAStructures.DARK_OAK_ENT_TREE.get(), DimensionStructuresSettings.DEFAULTS.get(CAStructures.DARK_OAK_ENT_TREE.get()));
			newMap.putIfAbsent(CAStructures.JUNGLE_ENT_TREE.get(),	DimensionStructuresSettings.DEFAULTS.get(CAStructures.JUNGLE_ENT_TREE.get()));
			newMap.putIfAbsent(CAStructures.OAK_ENT_TREE.get(), DimensionStructuresSettings.DEFAULTS.get(CAStructures.OAK_ENT_TREE.get()));
			newMap.putIfAbsent(CAStructures.SPRUCE_ENT_TREE.get(), DimensionStructuresSettings.DEFAULTS.get(CAStructures.SPRUCE_ENT_TREE.get()));
			newMap.putIfAbsent(CAStructures.WARPED_ENT_TREE.get(), DimensionStructuresSettings.DEFAULTS.get(CAStructures.WARPED_ENT_TREE.get()));
			newMap.putIfAbsent(CAStructures.GINKGO_ENT_TREE.get(), DimensionStructuresSettings.DEFAULTS.get(CAStructures.GINKGO_ENT_TREE.get()));

//			tempMap.putIfAbsent(CAStructures.WASP_DUNGEON.get(), DimensionStructuresSettings.DEFAULTS.get(CAStructures.WASP_DUNGEON.get()));
//			tempMap.putIfAbsent(CAStructures.MINING_WASP_DUNGEON.get(),	DimensionStructuresSettings.DEFAULTS.get(CAStructures.MINING_WASP_DUNGEON.get()));

			chunkProvider.generator.getSettings().structureConfig = newMap;
		}

		@SubscribeEvent(priority = EventPriority.HIGH)
		public static void onBiomeLoadingEvent(final BiomeLoadingEvent event) {
			GenerationHandler.addFeatures(event);
			MobSpawnHandler.addMobSpawns(event);
		}

		@SubscribeEvent
		public static void onRegisterFurnaceFuelEvent(FurnaceFuelBurnTimeEvent event) { //TODO Tags/organize
			ItemStack fuel = event.getItemStack();
			
			if (fuel.getItem() == CAItems.LAVA_PINK_TOURMALINE_BUCKET.get()) {
				event.setBurnTime(20000);
			} else if(fuel.getItem() == CABlocks.TAR.get().asItem() ) {
				event.setBurnTime(10000);
			} else if (fuel.getItem() == CAItems.CRYSTAL_ENERGY.get()) {
				event.setBurnTime(3600);
			} else if (fuel.getItem() instanceof CABoatItem) {
				event.setBurnTime(1200);
			} else if (fuel.getItem() == CABlocks.CRYSTAL_CRAFTING_TABLE.get().asItem() || fuel.getItem() == CABlocks.APPLE_FENCE.get().asItem()
					|| fuel.getItem() == CABlocks.CHERRY_FENCE.get().asItem() || fuel.getItem() == CABlocks.DUPLICATION_FENCE.get().asItem()
					|| fuel.getItem() == CABlocks.MOLDY_FENCE.get().asItem() || fuel.getItem() == CABlocks.PEACH_FENCE.get().asItem()
					|| fuel.getItem() == CABlocks.SKYWOOD_FENCE.get().asItem() || fuel.getItem() == CABlocks.GINKGO_FENCE.get().asItem()
					|| fuel.getItem() == CABlocks.MESOZOIC_FENCE.get().asItem() || fuel.getItem() == CABlocks.DENSEWOOD_FENCE.get().asItem()
					|| fuel.getItem() == CABlocks.CRYSTALWOOD_FENCE.get().asItem() || fuel.getItem() == CABlocks.APPLE_FENCE_GATE.get().asItem()
					|| fuel.getItem() == CABlocks.CHERRY_FENCE_GATE.get().asItem() || fuel.getItem() == CABlocks.DUPLICATION_FENCE_GATE.get().asItem()
					|| fuel.getItem() == CABlocks.PEACH_FENCE_GATE.get().asItem() || fuel.getItem() == CABlocks.SKYWOOD_FENCE_GATE.get().asItem()
					|| fuel.getItem() == CABlocks.GINKGO_FENCE_GATE.get().asItem() || fuel.getItem() == CABlocks.MESOZOIC_FENCE_GATE.get().asItem()
					|| fuel.getItem() == CABlocks.DENSEWOOD_FENCE_GATE.get().asItem()
					|| fuel.getItem() == CABlocks.CRYSTALWOOD_FENCE_GATE.get().asItem()
					|| fuel.getItem() == CABlocks.APPLE_TRAPDOOR.get().asItem() || fuel.getItem() == CABlocks.CHERRY_TRAPDOOR.get().asItem()
					|| fuel.getItem() == CABlocks.DUPLICATION_TRAPDOOR.get().asItem() || fuel.getItem() == CABlocks.GINKGO_TRAPDOOR.get().asItem()
					|| fuel.getItem() == CABlocks.MESOZOIC_TRAPDOOR.get().asItem() || fuel.getItem() == CABlocks.DENSEWOOD_TRAPDOOR.get().asItem()
					|| fuel.getItem() == CABlocks.PEACH_TRAPDOOR.get().asItem() || fuel.getItem() == CABlocks.SKYWOOD_TRAPDOOR.get().asItem()) {
				event.setBurnTime(300);
			} else if (fuel.getItem() == CAItems.CRYSTALWOOD_SHOVEL.get() || fuel.getItem() == CAItems.CRYSTALWOOD_SWORD.get()
					|| fuel.getItem() == CAItems.CRYSTALWOOD_HOE.get() || fuel.getItem() == CAItems.CRYSTALWOOD_AXE.get()
					|| fuel.getItem() == CAItems.CRYSTALWOOD_PICKAXE.get() || fuel.getItem() == CAItems.APPLE_SIGN.get() 
					|| fuel.getItem() == CAItems.CHERRY_SIGN.get() || fuel.getItem() == CAItems.DUPLICATION_SIGN.get() 
					|| fuel.getItem() == CAItems.GINKGO_SIGN.get() || fuel.getItem() == CAItems.PEACH_SIGN.get() || fuel.getItem() == CAItems.SKYWOOD_SIGN.get()
					|| fuel.getItem() == CABlocks.APPLE_DOOR.get().asItem() || fuel.getItem() == CABlocks.CHERRY_DOOR.get().asItem()
					|| fuel.getItem() == CABlocks.DUPLICATION_DOOR.get().asItem() || fuel.getItem() == CABlocks.GINKGO_DOOR.get().asItem()
					|| fuel.getItem() == CABlocks.MESOZOIC_DOOR.get().asItem() || fuel.getItem() == CABlocks.DENSEWOOD_DOOR.get().asItem()
					|| fuel.getItem() == CABlocks.PEACH_DOOR.get().asItem() || fuel.getItem() == CABlocks.SKYWOOD_DOOR.get().asItem()) {
				event.setBurnTime(200);
			} else if (fuel.getItem() == CAItems.CRYSTALWOOD_SHARD.get() || fuel.getItem() == CATags.Items.CRYSTAL_SAPLING) {
				event.setBurnTime(100);
			}
		}

		@SubscribeEvent
		public static void onRegisterCommandsEvent(RegisterCommandsEvent event) {
			CommandDispatcher<CommandSource> commandDispatcher = event.getDispatcher();
			CACommands.register(commandDispatcher);
		}

		@SubscribeEvent
		public static void onWandererTradesEvent(WandererTradesEvent event) {
			TradeUtil.addWandererTrades(event,
					new CABasicTrade(1, CABlocks.CYAN_ROSE.get().asItem(), 12),
					new CABasicTrade(1, CABlocks.RED_ROSE.get().asItem(), 12),
					new CABasicTrade(1, CABlocks.PAEONIA.get().asItem(), 12),
					new CABasicTrade(3, CABlocks.TUBE_WORM.get().asItem(), 12),
					new CABasicTrade(5, CABlocks.APPLE_SAPLING.get().asItem(), 8),
					new CABasicTrade(5, CABlocks.CHERRY_SAPLING.get().asItem(), 8),
					new CABasicTrade(5, CABlocks.PEACH_SAPLING.get().asItem(), 8),
					new CABasicTrade(5, Items.NAME_TAG, 5));

			if (CAConfigManager.MAIN_COMMON.wanderingTraderSellsTriffidGoo.get()) {
				TradeUtil.addWandererTrades(event,
						new CABasicTrade(4, CAItems.TRIFFID_GOO.get().asItem(), 5));
			}

			if (CAConfigManager.MAIN_COMMON.wanderingTraderSellsUraniumAndTitanium.get()) {
				TradeUtil.addRareWandererTrades(event,
						new CABasicTrade(15, CAItems.TITANIUM_NUGGET.get(), 3),
						new CABasicTrade(15, CAItems.URANIUM_NUGGET.get(), 3));
			}
		}

		@SubscribeEvent
		public static void onVillagerTradesEvent(VillagerTradesEvent event) {
			TradeUtil.addVillagerTrades(event, VillagerProfession.FARMER, TradeUtil.NOVICE,
					new CABasicTrade(2, CAItems.CHERRIES.get(), 6, 16, 2),
					new CABasicTrade(2, CAItems.LETTUCE.get(), 6, 12, 2),
					new CABasicTrade(1, CAItems.CORN.get(), 5, 12, 3),
					new CABasicTrade(1, CAItems.TOMATO.get(), 5, 12, 3));
			TradeUtil.addVillagerTrades(event, VillagerProfession.FARMER, TradeUtil.APPRENTICE,
					new CABasicTrade(1, CAItems.PEACH.get(), 6, 16, 5),
					new CABasicTrade(2, CAItems.STRAWBERRY.get(), 6, 12, 5),
					new CABasicTrade(2, CAItems.QUINOA.get(), 4, 10, 9),
					new CABasicTrade(1, CAItems.RADISH.get(), 5, 12, 8));
			
			TradeUtil.addVillagerTrades(event, VillagerProfession.FARMER, TradeUtil.JOURNEYMAN,
					new CABasicTrade(1, CAItems.QUINOA_SALAD.get(), 1, 11, 17),
					new CABasicTrade(1, CAItems.RADISH_STEW.get(), 1, 12, 15));
			TradeUtil.addVillagerTrades(event, VillagerProfession.FARMER, TradeUtil.EXPERT,
					new CABasicTrade(1, CAItems.GOLDEN_MELON_SLICE.get().asItem(), 4, 12, 15),
					new CABasicTrade(1, CAItems.GOLDEN_POTATO.get().asItem(), 4, 12, 15));
			TradeUtil.addVillagerTrades(event, VillagerProfession.FARMER, TradeUtil.MASTER,
					new CABasicTrade(CAItems.GOLDEN_MELON_SLICE.get().asItem(), 6, 1, 12, 30),
					new CABasicTrade(CAItems.GOLDEN_POTATO.get().asItem(), 6, 1, 12, 30));

			TradeUtil.addVillagerTrades(event, VillagerProfession.BUTCHER, TradeUtil.NOVICE,
					new CABasicTrade(CAItems.BACON.get(), 18, 1, 16, 2),
					new CABasicTrade(CAItems.CORNDOG.get(), 18, 1, 16, 2));
			TradeUtil.addVillagerTrades(event, VillagerProfession.BUTCHER, TradeUtil.APPRENTICE,
					new CABasicTrade(1, CAItems.COOKED_BACON.get(), 6, 16, 5),
					new CABasicTrade(1, CAItems.COOKED_CORNDOG.get(), 6, 16, 5));
			TradeUtil.addVillagerTrades(event, VillagerProfession.BUTCHER, TradeUtil.JOURNEYMAN,
					new CABasicTrade(CAItems.CRAB_MEAT.get(), 12, 1, 16, 20),
					new CABasicTrade(1, CAItems.COOKED_CRAB_MEAT.get(), 5, 16, 10));
			TradeUtil.addVillagerTrades(event, VillagerProfession.BUTCHER, TradeUtil.EXPERT,
					new CABasicTrade(CAItems.PEACOCK_LEG.get(), 12, 1, 16, 20),
					new CABasicTrade(1, CAItems.COOKED_PEACOCK_LEG.get(), 5, 16, 20));

			TradeUtil.addVillagerTrades(event, VillagerProfession.FLETCHER, TradeUtil.MASTER,
					new CABasicTrade(3, CAItems.PEACOCK_FEATHER.get(), 4, 12, 30));

			TradeUtil.addVillagerTrades(event, VillagerProfession.FISHERMAN, TradeUtil.JOURNEYMAN,
					new CABasicTrade(3, CAItems.BLUE_FISH.get(), 1, 16, 10),
					new CABasicTrade(3, CAItems.GRAY_FISH.get(), 1, 16, 10),
					new CABasicTrade(3, CAItems.GREEN_FISH.get(), 1, 12, 10),
					new CABasicTrade(3, CAItems.PINK_FISH.get(), 1, 12, 10));
			TradeUtil.addVillagerTrades(event, VillagerProfession.FISHERMAN, TradeUtil.EXPERT,
					new CABasicTrade(4, CAItems.ROCK_FISH.get(), 1, 16, 20),
					new CABasicTrade(4, CAItems.SPARK_FISH.get(), 1, 12, 20),
					new CABasicTrade(4, CAItems.WOOD_FISH.get(), 1, 16, 20));
			TradeUtil.addVillagerTrades(event, VillagerProfession.FISHERMAN, TradeUtil.MASTER,
					new CABasicTrade(5, CAItems.FIRE_FISH.get(), 1, 12, 20),
					new CABasicTrade(5, CAItems.LAVA_EEL.get(), 1, 12, 20),
					new CABasicTrade(5, CAItems.SUN_FISH.get(), 1, 8, 30));

			TradeUtil.addVillagerTrades(event, CAVillagers.ARCHAEOLOGIST.get(), TradeUtil.NOVICE,
					new CABasicTrade(1, Items.WATER_BUCKET, 1, 6, 2),
					new CABasicTrade(2, CAItems.ALUMINUM_POWER_CHIP.get(), 3, 8, 2),
					new CABasicTrade(1, CAItems.COPPER_LUMP.get(), 6, 6, 3),
					new CAIngredientTrade(Pair.of(Ingredient.of(CATags.Items.ARCHAEOLOGIST_FOSSILS_COMMON), 3), 5, 3, 3));
			TradeUtil.addVillagerTrades(event, CAVillagers.ARCHAEOLOGIST.get(), TradeUtil.APPRENTICE,
					new CABasicTrade(1, Items.LAVA_BUCKET, 1, 6, 5),
					new CAIngredientTrade(1, Pair.of(Ingredient.of(CATags.Items.ARCHAEOLOGIST_FOSSILS_COMMON), 3), 4, 5),
					new CAIngredientTrade(3, Pair.of(Ingredient.of(CATags.Items.ARCHAEOLOGIST_FOSSILS), 1), 9, 8),
					new CAIngredientTrade(2, Pair.of(Ingredient.of(CATags.Items.ARCHAEOLOGIST_SPAWN_EGGS_COMMON), 1), 3, 8));
			TradeUtil.addVillagerTrades(event, CAVillagers.ARCHAEOLOGIST.get(), TradeUtil.JOURNEYMAN,
					new CABasicTrade(CAItems.ALUMINUM_POWER_CHIP.get(), 8, 2, 15, 8, 2),
					new CAIngredientTrade(1, Pair.of(Ingredient.of(CATags.Items.ARCHAEOLOGIST_SPAWN_EGGS_UNCOMMON), 2), 3, 10),
					new CAIngredientTrade(4, Pair.of(Ingredient.of(CATags.Items.ARCHAEOLOGIST_FOSSILS), 2), 6, 10),
					new CAIngredientTrade(1, Pair.of(Ingredient.of(CATags.Items.ARCHAEOLOGIST_FOSSILS_UNCOMMON), 1), 6, 20),
					new CAIngredientTrade(Pair.of(Ingredient.of(CATags.Items.ARCHAEOLOGIST_FOSSILS), 6), 11, 3, 20));
			TradeUtil.addVillagerTrades(event, CAVillagers.ARCHAEOLOGIST.get(), TradeUtil.EXPERT,
					new CABasicTrade(1, CAItems.ALUMINUM_POWER_CHIP.get(), 6, 5, 20),
					new CABasicTrade(Items.LAVA_BUCKET, 1, 2, 2, 20),
					new CAIngredientTrade(2, Pair.of(Ingredient.of(CATags.Items.ARCHAEOLOGIST_SPAWN_EGGS), 2), 3, 20),
					new CAIngredientTrade(2, Pair.of(Ingredient.of(CATags.Items.ARCHAEOLOGIST_FOSSILS_RARE), 1), 6, 20),
					new CAIngredientTrade(3, Pair.of(Ingredient.of(CATags.Items.ARCHAEOLOGIST_FOSSILS_RARE), 1), 6, 30));
			TradeUtil.addVillagerTrades(event, CAVillagers.ARCHAEOLOGIST.get(), TradeUtil.MASTER,
					new CAIngredientTrade(2, Pair.of(Ingredient.of(CATags.Items.ARCHAEOLOGIST_FOSSILS), 3), 6, 30),
					new CAIngredientTrade(3, Pair.of(Ingredient.of(CATags.Items.ARCHAEOLOGIST_FOSSILS_EPIC), 1), 6, 30),
					new CAIngredientTrade(3, Pair.of(Ingredient.of(CATags.Items.ARCHAEOLOGIST_SPAWN_EGGS_RARE), 2), 7, 30),
					new CAIngredientTrade(4, Pair.of(Ingredient.of(CATags.Items.ARCHAEOLOGIST_SPAWN_EGGS_EPIC), 1), 3, 40));
		}

		@SubscribeEvent
		public static void onAddReloadListenerEvent(AddReloadListenerEvent event) {
			event.addListener(CADataLoaders.ANIMATION_DATA);
		}
	}
	
	public static class ModSetupEvents {

		@SubscribeEvent
		public static void onFMLCommonSetupEvent(FMLCommonSetupEvent event) {
			CAEntityTypes.registerSpawnPlacementTypes();
			CANetworkManager.registerPackets();
			CAEffects.registerBrewingRecipes(); // Unused currently. Here for FUTURE use.
			Raid.WaveMember.create("illusioner", EntityType.ILLUSIONER, new int[]{0, 0, 0, 0, 1, 1, 0, 2});

			event.enqueueWork(() -> {
				CAVanillaCompat.registerVanillaCompat();
				CAStructures.setupStructures();
				CAConfiguredStructures.registerConfiguredStructures();
				CASurfaceBuilders.ConfiguredSurfaceBuilders.registerConfiguredSurfaceBuilders();
				CAVillagers.registerVillagerTypes();
				CAWoodTypes.registerWoodtypes();

				ObjectUtil.loadClass("io.github.chaosawakens.common.registry.CAConfiguredFeatures");
				CONFIG_FEATURES.forEach((cfWrapper) -> Registry.register(WorldGenRegistries.CONFIGURED_FEATURE, cfWrapper.getIdentifier(), cfWrapper.getFeatureType()));
				CONFIG_CARVERS.forEach((ccWrapper) -> Registry.register(WorldGenRegistries.CONFIGURED_CARVER, ccWrapper.getIdentifier(), ccWrapper.getCarver()));
			});
			
			BiomeDictionary.addTypes(RegistryKey.create(Registry.BIOME_REGISTRY, CABiomes.DENSE_FOREST.getId()), CABiomes.Type.MINING_PARADISE, CABiomes.Type.DENSE_FOREST);
			BiomeDictionary.addTypes(RegistryKey.create(Registry.BIOME_REGISTRY, CABiomes.DENSE_GINKGO_FOREST.getId()), CABiomes.Type.MINING_PARADISE, CABiomes.Type.DENSE_GINKGO_FOREST);
			BiomeDictionary.addTypes(RegistryKey.create(Registry.BIOME_REGISTRY, CABiomes.DENSE_MOUNTAINS.getId()), CABiomes.Type.MINING_PARADISE, CABiomes.Type.DENSE_MOUNTAINS);
			BiomeDictionary.addTypes(RegistryKey.create(Registry.BIOME_REGISTRY, CABiomes.DENSE_PLAINS.getId()), CABiomes.Type.MINING_PARADISE, CABiomes.Type.DENSE_PLAINS);
			BiomeDictionary.addTypes(RegistryKey.create(Registry.BIOME_REGISTRY, CABiomes.MESOZOIC_JUNGLE.getId()), CABiomes.Type.MINING_PARADISE, CABiomes.Type.MESOZOIC_JUNGLE);
			BiomeDictionary.addTypes(RegistryKey.create(Registry.BIOME_REGISTRY, CABiomes.STALAGMITE_VALLEY.getId()), CABiomes.Type.MINING_PARADISE, CABiomes.Type.STALAGMITE_VALLEY);
			BiomeDictionary.addTypes(RegistryKey.create(Registry.BIOME_REGISTRY, CABiomes.VILLAGE_PLAINS.getId()), CABiomes.Type.VILLAGE_MANIA, CABiomes.Type.VILLAGE_PLAINS);
			BiomeDictionary.addTypes(RegistryKey.create(Registry.BIOME_REGISTRY, CABiomes.VILLAGE_SAVANNA.getId()), CABiomes.Type.VILLAGE_MANIA, CABiomes.Type.VILLAGE_SAVANNA);
			BiomeDictionary.addTypes(RegistryKey.create(Registry.BIOME_REGISTRY, CABiomes.VILLAGE_TAIGA.getId()), CABiomes.Type.VILLAGE_MANIA, CABiomes.Type.VILLAGE_TAIGA);
			BiomeDictionary.addTypes(RegistryKey.create(Registry.BIOME_REGISTRY, CABiomes.VILLAGE_SNOWY.getId()), CABiomes.Type.VILLAGE_MANIA, CABiomes.Type.VILLAGE_SNOWY);
			BiomeDictionary.addTypes(RegistryKey.create(Registry.BIOME_REGISTRY, CABiomes.VILLAGE_DESERT.getId()), CABiomes.Type.VILLAGE_MANIA, CABiomes.Type.VILLAGE_DESERT);
			BiomeDictionary.addTypes(RegistryKey.create(Registry.BIOME_REGISTRY, CABiomes.DANGER_ISLANDS.getId()), CABiomes.Type.DANGER_ISLES);
			BiomeDictionary.addTypes(RegistryKey.create(Registry.BIOME_REGISTRY, CABiomes.CRYSTAL_PLAINS.getId()), CABiomes.Type.CRYSTAL_WORLD);
			BiomeDictionary.addTypes(RegistryKey.create(Registry.BIOME_REGISTRY, CABiomes.CRYSTAL_HILLS.getId()), CABiomes.Type.CRYSTAL_WORLD);
		}

		@SubscribeEvent
		public static void onConfigLoadEvent(ModConfigEvent event) {
			ModConfig targetConfig = event.getConfig();
			handlePostRegConfigValues(targetConfig);
		}

		@SubscribeEvent
		public static void onFMLLoadCompleteEvent(FMLLoadCompleteEvent event) {
			modifyAttributeValues();
		}
		
		@SubscribeEvent
		public static void onEntityAttributeCreationEvent(final EntityAttributeCreationEvent event) {
			event.put(CAEntityTypes.OAK_ENT.get(), EntEntity.setCustomAttributes().build());
			event.put(CAEntityTypes.APPLE_ENT.get(), EntEntity.setCustomAttributes().build());
			event.put(CAEntityTypes.ACACIA_ENT.get(), EntEntity.setCustomAttributes().build());
			event.put(CAEntityTypes.BIRCH_ENT.get(), EntEntity.setCustomAttributes().build());
			event.put(CAEntityTypes.CHERRY_ENT.get(), EntEntity.setCustomAttributes().build());
			event.put(CAEntityTypes.DARK_OAK_ENT.get(), EntEntity.setCustomAttributes().build());
			event.put(CAEntityTypes.JUNGLE_ENT.get(), EntEntity.setCustomAttributes().build());
			event.put(CAEntityTypes.PEACH_ENT.get(), EntEntity.setCustomAttributes().build());
			event.put(CAEntityTypes.SKYWOOD_ENT.get(), EntEntity.setCustomAttributes().build());
			event.put(CAEntityTypes.GINKGO_ENT.get(), EntEntity.setCustomAttributes().build());
			event.put(CAEntityTypes.SPRUCE_ENT.get(), EntEntity.setCustomAttributes().build());
			event.put(CAEntityTypes.CRIMSON_ENT.get(), EntEntity.setCustomAttributes().build());
			event.put(CAEntityTypes.WARPED_ENT.get(), EntEntity.setCustomAttributes().build());
			event.put(CAEntityTypes.RED_ANT.get(), AggressiveAntEntity.setCustomAttributes().build());
			event.put(CAEntityTypes.BROWN_ANT.get(), AntEntity.setCustomAttributes().build());
			event.put(CAEntityTypes.RAINBOW_ANT.get(), AntEntity.setCustomAttributes().build());
			event.put(CAEntityTypes.UNSTABLE_ANT.get(), AntEntity.setCustomAttributes().build());
			event.put(CAEntityTypes.TERMITE.get(), AggressiveAntEntity.setCustomAttributes().build());
			event.put(CAEntityTypes.TREE_FROG.get(), TreeFrogEntity.setCustomAttributes().build());
			event.put(CAEntityTypes.HERCULES_BEETLE.get(), HerculesBeetleEntity.setCustomAttributes().build());
//			event.put(CAEntityTypes.THROWBACK_HERCULES_BEETLE.get(), HerculesBeetleEntity.setCustomAttributes().build());
			event.put(CAEntityTypes.BIRD.get(), BirdEntity.setCustomAttributes().build());
			event.put(CAEntityTypes.APPLE_COW.get(), AppleCowEntity.setCustomAttributes().build());
			event.put(CAEntityTypes.GOLDEN_APPLE_COW.get(), GoldenAppleCowEntity.setCustomAttributes().build());
			event.put(CAEntityTypes.ULTIMATE_APPLE_COW.get(), UltimateAppleCowEntity.setCustomAttributes().build());
			event.put(CAEntityTypes.ENCHANTED_GOLDEN_APPLE_COW.get(), EnchantedGoldenAppleCowEntity.setCustomAttributes().build());
			event.put(CAEntityTypes.CRYSTAL_APPLE_COW.get(), CrystalAppleCowEntity.setCustomAttributes().build());
			event.put(CAEntityTypes.CRYSTAL_GATOR.get(), CrystalGatorEntity.setCustomAttributes().build());
			event.put(CAEntityTypes.CRYSTAL_CARROT_PIG.get(), CrystalCarrotPigEntity.setCustomAttributes().build());
			event.put(CAEntityTypes.CARROT_PIG.get(), CarrotPigEntity.setCustomAttributes().build());
			event.put(CAEntityTypes.GOLDEN_CARROT_PIG.get(), GoldenCarrotPigEntity.setCustomAttributes().build());
			event.put(CAEntityTypes.ENCHANTED_GOLDEN_CARROT_PIG.get(), EnchantedGoldenCarrotPigEntity.setCustomAttributes().build());
			event.put(CAEntityTypes.BEAVER.get(), BeaverEntity.setCustomAttributes().build());
			event.put(CAEntityTypes.DIMETRODON.get(), DimetrodonEntity.setCustomAttributes().build());
			event.put(CAEntityTypes.GAZELLE.get(), GazelleEntity.setCustomAttributes().build());
			event.put(CAEntityTypes.LETTUCE_CHICKEN.get(), LettuceChickenEntity.setCustomAttributes().build());
			event.put(CAEntityTypes.EMERALD_GATOR.get(), EmeraldGatorEntity.setCustomAttributes().build());
			event.put(CAEntityTypes.RUBY_BUG.get(), RubyBugEntity.setCustomAttributes().build());
			event.put(CAEntityTypes.STINK_BUG.get(), StinkBugEntity.setCustomAttributes().build());
			event.put(CAEntityTypes.ROBO_JEFFERY.get(), RoboJefferyEntity.setCustomAttributes().build());
			event.put(CAEntityTypes.ROBO_POUNDER.get(), RoboPounderEntity.setCustomAttributes().build());
			event.put(CAEntityTypes.ROBO_SNIPER.get(), RoboSniperEntity.setCustomAttributes().build());
			event.put(CAEntityTypes.ROBO_WARRIOR.get(), RoboWarriorEntity.setCustomAttributes().build());
			event.put(CAEntityTypes.WASP.get(), WaspEntity.setCustomAttributes().build());
			event.put(CAEntityTypes.WHALE.get(), WhaleEntity.setCustomAttributes().build());
			event.put(CAEntityTypes.GREEN_FISH.get(), GreenFishEntity.setCustomAttributes().build());
			event.put(CAEntityTypes.ROCK_FISH.get(), RockFishEntity.setCustomAttributes().build());
			event.put(CAEntityTypes.SPARK_FISH.get(), SparkFishEntity.setCustomAttributes().build());
			event.put(CAEntityTypes.WOOD_FISH.get(), WoodFishEntity.setCustomAttributes().build());
//			event.put(CAEntityTypes.LAVA_EEL.get(), LavaEelEntity.setCustomAttributes().build());
		}

		@SubscribeEvent
		public static void onRemapBlocksEvent(RegistryEvent.MissingMappings<Block> event) {
			for (RegistryEvent.MissingMappings.Mapping<Block> mapping : event.getAllMappings()) {
				if (mapping.key.getNamespace().equals(ChaosAwakens.MODID)) {
					if (mapping.key.getPath().contains("fossilised_frog")) {
						String newName = mapping.key.getPath().replace("fossilised_frog", "fossilised_tree_frog");
						ResourceLocation remappedFrog = ChaosAwakens.prefix(newName);

						mapping.remap(ForgeRegistries.BLOCKS.getValue(remappedFrog));
					}

					if (mapping.key.getPath().contains("crystal_energy")) {
						String newName = mapping.key.getPath().replace("crystal_energy", "energized_kyanite");
						ResourceLocation remappedCrystalEnergy = ChaosAwakens.prefix(newName);

						mapping.remap(ForgeRegistries.BLOCKS.getValue(remappedCrystalEnergy));
					}

					if (mapping.key.getPath().contains("skywood_wood")) {
						String newName = mapping.key.getPath().replace("skywood_wood", "skywood");
						ResourceLocation remappedCrystalEnergy = ChaosAwakens.prefix(newName);

						mapping.remap(ForgeRegistries.BLOCKS.getValue(remappedCrystalEnergy));
					}

					if (mapping.key.getPath().contains("stripped_skywood_wood")) {
						String newName = mapping.key.getPath().replace("stripped_skywood_wood", "stripped_skywood");
						ResourceLocation remappedCrystalEnergy = ChaosAwakens.prefix(newName);

						mapping.remap(ForgeRegistries.BLOCKS.getValue(remappedCrystalEnergy));
					}
				}
			}
		}

		@SubscribeEvent
		public static void onRemapItemsEvent(RegistryEvent.MissingMappings<Item> event) {
			for (RegistryEvent.MissingMappings.Mapping<Item> mapping : event.getAllMappings()) {
				if (mapping.key.getNamespace().equals(ChaosAwakens.MODID)) {
					if (mapping.key.getPath().contains("frog_spawn_egg")) {
						String newName = mapping.key.getPath().replace("frog_spawn_egg", "tree_frog_spawn_egg");
						ResourceLocation remappedFrog = ChaosAwakens.prefix(newName);
						mapping.remap(ForgeRegistries.ITEMS.getValue(remappedFrog));
					}
				}
			}
		}

		@SubscribeEvent
		public static void onRemapEntitiesEvent(RegistryEvent.MissingMappings<EntityType<?>> event) {
			for (RegistryEvent.MissingMappings.Mapping<EntityType<?>> mapping : event.getAllMappings()) {
				if (mapping.key.getNamespace().equals(ChaosAwakens.MODID)) {
					if (mapping.key.getPath().contains("frog")) {
						String newName = mapping.key.getPath().replace("frog", "tree_frog");
						ResourceLocation remappedFrog = ChaosAwakens.prefix(newName);
						mapping.remap(ForgeRegistries.ENTITIES.getValue(remappedFrog));
					}
				}
			}
		}

		@SubscribeEvent
		public static void onGatherDataEvent(final GatherDataEvent event) {
			DataGenerator dataGenerator = event.getGenerator();
			final ExistingFileHelper existingFileHelper = event.getExistingFileHelper();

			if (event.includeClient()) {
				dataGenerator.addProvider(new CABlockModelProvider(dataGenerator, existingFileHelper));
				dataGenerator.addProvider(new CAItemModelProvider(dataGenerator, existingFileHelper));
				dataGenerator.addProvider(new CABlockStateProvider(dataGenerator, existingFileHelper));
				dataGenerator.addProvider(new CAParticleTypeProvider(dataGenerator));
			}
			//		dataGenerator.addProvider(new CALanguageProvider(dataGenerator));
			if (event.includeServer()) {
				dataGenerator.addProvider(new CAAnimationMetadataProvider(dataGenerator));
				dataGenerator.addProvider(new CAAdvancementProvider(dataGenerator, existingFileHelper));
				dataGenerator.addProvider(new CACustomConversionProvider(dataGenerator));
				dataGenerator.addProvider(new CADimensionTypeProvider(dataGenerator));
				dataGenerator.addProvider(new CAGlobalLootModifierProvider(dataGenerator));
				dataGenerator.addProvider(new CALootTableProvider(dataGenerator));
				dataGenerator.addProvider(new CARecipeProvider(dataGenerator));
				dataGenerator.addProvider(new CABlockTagProvider(dataGenerator, existingFileHelper));
				dataGenerator.addProvider(new CAItemTagProvider(dataGenerator, existingFileHelper));
				dataGenerator.addProvider(new CAEntityTypeTagProvider(dataGenerator, existingFileHelper));
			}
		}
	}

	// Took the main functionality of AttributeFix and clamped it into a singular method --Meme Man
	private static void modifyAttributeValues() {
		ObjectArrayList<Attribute> registeredAttributes = ForgeRegistries.ATTRIBUTES.getEntries().stream()
				.map(Entry::getValue)
				.filter((targetAttribute) -> targetAttribute != null && targetAttribute instanceof RangedAttribute)
				.collect(Collectors.toCollection(ObjectArrayList::new));

		for (Attribute targetAttribute : registeredAttributes) {
			RangedAttribute targetRangedAttribute = (RangedAttribute) targetAttribute;
			targetRangedAttribute.maxValue = Math.max(targetRangedAttribute.maxValue, Double.MAX_VALUE);
		}
	}

	private static void handlePostRegConfigValues(ModConfig targetConfig) {
		if (targetConfig.getModId().equals(ChaosAwakens.MODID)) {
			ObjectArrayList<Item> registeredTieredItems = CAItems.ITEMS.getEntries().stream()
					.map(RegistryObject::get)
					.filter((itemEntry) -> itemEntry instanceof ICATieredItem)
					.collect(Collectors.toCollection(ObjectArrayList::new));

			if (targetConfig.getType().equals(Type.SERVER)) {
				registeredTieredItems.forEach((targetItem) -> {
					ICATieredItem tieredTargetItem = (ICATieredItem) targetItem;
					IntValue targetDamageValue = tieredTargetItem.getActualAttackDamage().get();
					Lazy<? extends Multimap<Attribute, AttributeModifier>> updatedAttributeModMapLazy = Lazy.of(() -> {
						ImmutableMultimap.Builder<Attribute, AttributeModifier> attrModMapBuilder = ImmutableMultimap.builder();
						
						attrModMapBuilder.put(Attributes.ATTACK_DAMAGE, new AttributeModifier(Item.BASE_ATTACK_DAMAGE_UUID, "Weapon modifier", targetDamageValue.get() - 1, AttributeModifier.Operation.ADDITION));
						attrModMapBuilder.put(Attributes.ATTACK_SPEED, new AttributeModifier(Item.BASE_ATTACK_SPEED_UUID, "Weapon modifier", tieredTargetItem.getAttackSpeed(), AttributeModifier.Operation.ADDITION));
						if (ForgeMod.REACH_DISTANCE.isPresent()) attrModMapBuilder.put(ForgeMod.REACH_DISTANCE.get(), new AttributeModifier(ICATieredItem.getReachUUIDMod(), "Weapon modifier", tieredTargetItem.getReach(), AttributeModifier.Operation.ADDITION));
						attrModMapBuilder.put(Attributes.ATTACK_KNOCKBACK, new AttributeModifier(ICATieredItem.getKBUUIDMod(), "Weapon modifier", tieredTargetItem.getAttackKnockback(), AttributeModifier.Operation.ADDITION));
						
						return attrModMapBuilder.build();
					});
					
					tieredTargetItem.setAttributeModifiers(updatedAttributeModMapLazy);
				});
			}
		}
	}
}
