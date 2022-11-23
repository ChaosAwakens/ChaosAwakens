package io.github.chaosawakens.data;

import java.io.IOException;
import java.nio.file.Path;
import java.util.Arrays;
import java.util.Set;
import java.util.function.Consumer;

import org.apache.commons.lang3.Validate;

import com.google.common.collect.Sets;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import io.github.chaosawakens.ChaosAwakens;
import io.github.chaosawakens.common.advancements.triggers.AdvancementCompleteTrigger;
import io.github.chaosawakens.common.registry.CABlocks;
import io.github.chaosawakens.common.registry.CADimensions;
import io.github.chaosawakens.common.registry.CAEntityTypes;
import io.github.chaosawakens.common.registry.CAItems;
import io.github.chaosawakens.common.registry.CATags;
import net.minecraft.advancements.Advancement;
import net.minecraft.advancements.AdvancementRewards;
import net.minecraft.advancements.FrameType;
import net.minecraft.advancements.IRequirementsStrategy;
import net.minecraft.advancements.criterion.BredAnimalsTrigger;
import net.minecraft.advancements.criterion.ChangeDimensionTrigger;
import net.minecraft.advancements.criterion.EntityPredicate;
import net.minecraft.advancements.criterion.InventoryChangeTrigger;
import net.minecraft.advancements.criterion.ItemDurabilityTrigger;
import net.minecraft.advancements.criterion.ItemPredicate;
import net.minecraft.advancements.criterion.KilledTrigger;
import net.minecraft.advancements.criterion.LocationPredicate;
import net.minecraft.advancements.criterion.MinMaxBounds.IntBound;
import net.minecraft.advancements.criterion.PlayerEntityInteractionTrigger;
import net.minecraft.advancements.criterion.PositionTrigger;
import net.minecraft.advancements.criterion.RightClickBlockWithItemTrigger;
import net.minecraft.block.Blocks;
import net.minecraft.data.AdvancementProvider;
import net.minecraft.data.DataGenerator;
import net.minecraft.data.DirectoryCache;
import net.minecraft.data.IDataProvider;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.util.IItemProvider;
import net.minecraft.util.RegistryKey;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.registry.Registry;
import net.minecraft.util.text.TranslationTextComponent;

public class CAAdvancementProvider extends AdvancementProvider {
	private static final ResourceLocation BACKGROUND_TEXTURE = new ResourceLocation(ChaosAwakens.MODID, "textures/gui/advancement_bg.png");
	private static final Gson GSON = (new GsonBuilder()).setPrettyPrinting().create();
	private final DataGenerator generator;

	@SuppressWarnings("deprecation")
	public CAAdvancementProvider(DataGenerator generatorIn) {
		super(generatorIn);
		this.generator = generatorIn;
	}

	@Override
	public String getName() {
		return ChaosAwakens.MODNAME + ": Advancements";
	}
	
	private static String advancementId(String s) {
		return ChaosAwakens.MODID + ":root/" + s;
	}

	private static String slayAdvancementId(String name) {
		return ChaosAwakens.MODID + ":slay/" + name;
	}

	private static Path getPath(Path pathIn, Advancement advancementIn) {
		return pathIn.resolve("data/" + advancementIn.getId().getNamespace() + "/advancements/" + advancementIn.getId().getPath() + ".json");
	}

	@Override
	public void run(DirectoryCache cache) {
		Path path = this.generator.getOutputFolder();
		Set<ResourceLocation> set = Sets.newHashSet();
		Consumer<Advancement> consumer = (advancement) -> {
			assert advancement.getDisplay() != null;
			ChaosAwakens.LOGGER.info("=--+--+--+--+--+--+--+--+--+--+--+--=");
			ChaosAwakens.LOGGER.debug("Advancement Id: " + advancement.getId());
			ChaosAwakens.LOGGER.debug("Advancement Item Icon: " + advancement.getDisplay().getIcon().getItem());
			ChaosAwakens.LOGGER.debug("Advancement Frame: " + advancement.getDisplay().getFrame());
			ChaosAwakens.LOGGER.debug("Advancement Criteria: " + advancement.getCriteria().keySet());
			ChaosAwakens.LOGGER.debug("Advancement Requirement Name: " + Arrays.deepToString(advancement.getRequirements()));
			ChaosAwakens.LOGGER.debug("Advancement JSON Id: " + advancement.getDisplay().serializeToJson());
			ChaosAwakens.LOGGER.info("=--+--+--+--+--+--+--+--+--+--+--+--=");

			if (!set.add(advancement.getId())) throw new IllegalStateException("Duplicate advancement " + advancement.getId());
			else {
				Path path1 = getPath(path, advancement);
				try {
					IDataProvider.save(GSON, cache, advancement.deconstruct().serializeToJson(), path1);
				} catch (IOException e) {
					ChaosAwakens.LOGGER.error("Couldn't save advancement {}", path1, e);
				}
			}
		};
		this.register(consumer);
	}

	@SuppressWarnings({ "unused" })
	public void register(Consumer<Advancement> t) {
		Advancement root = registerAdvancement("root", FrameType.TASK, CAItems.RUBY.get())
				.addCriterion("root", PositionTrigger.Instance.located(LocationPredicate.ANY))
				.save(t, advancementId("root"));

		// ARMOR
		// Cat's Eye Armor
		Advancement catsEyeArmor = registerAdvancement("cats_eye_armor", FrameType.TASK, CAItems.CATS_EYE_CHESTPLATE.get()).parent(root)
				.addCriterion("cats_eye_armor", InventoryChangeTrigger.Instance.hasItems(CAItems.CATS_EYE_HELMET.get(), CAItems.CATS_EYE_CHESTPLATE.get(), CAItems.CATS_EYE_LEGGINGS.get(), CAItems.CATS_EYE_BOOTS.get()))
				.save(t, advancementId("cats_eye_armor"));
		// Tin Armor
		Advancement tinArmor = registerAdvancement("tin_armor", FrameType.TASK, CAItems.TIN_CHESTPLATE.get()).parent(catsEyeArmor)
				.addCriterion("tin_armor", InventoryChangeTrigger.Instance.hasItems(CAItems.TIN_HELMET.get(), CAItems.TIN_CHESTPLATE.get(), CAItems.TIN_LEGGINGS.get(), CAItems.TIN_BOOTS.get()))
				.save(t, advancementId("tin_armor"));
		// Silver Armor
		Advancement silverArmor = registerAdvancement("silver_armor", FrameType.TASK, CAItems.SILVER_CHESTPLATE.get()).parent(tinArmor)
				.addCriterion("silver_armor", InventoryChangeTrigger.Instance.hasItems(CAItems.SILVER_HELMET.get(), CAItems.SILVER_CHESTPLATE.get(), CAItems.SILVER_LEGGINGS.get(), CAItems.SILVER_BOOTS.get()))
				.save(t, advancementId("silver_armor"));
		// Platinum Armor
		Advancement platinumArmor = registerAdvancement("platinum_armor", FrameType.TASK, CAItems.PLATINUM_CHESTPLATE.get()).parent(silverArmor)
				.addCriterion("platinum_armor", InventoryChangeTrigger.Instance.hasItems(CAItems.PLATINUM_HELMET.get(), CAItems.PLATINUM_CHESTPLATE.get(), CAItems.PLATINUM_LEGGINGS.get(), CAItems.PLATINUM_BOOTS.get()))
				.save(t, advancementId("platinum_armor"));
		// Emerald Armor
		Advancement emeraldArmor = registerAdvancement("emerald_armor", FrameType.TASK, CAItems.EMERALD_CHESTPLATE.get()).parent(catsEyeArmor)
				.addCriterion("emerald_armor", InventoryChangeTrigger.Instance.hasItems(CAItems.EMERALD_HELMET.get(), CAItems.EMERALD_CHESTPLATE.get(), CAItems.EMERALD_LEGGINGS.get(), CAItems.EMERALD_BOOTS.get()))
				.save(t, advancementId("emerald_armor"));
		// Experience Armor
		Advancement experienceArmor = registerAdvancement("experience_armor", FrameType.TASK, CAItems.EXPERIENCE_CHESTPLATE.get()).parent(emeraldArmor)
				.addCriterion("experience_armor", InventoryChangeTrigger.Instance.hasItems(CAItems.EXPERIENCE_HELMET.get(), CAItems.EXPERIENCE_CHESTPLATE.get(), CAItems.EXPERIENCE_LEGGINGS.get(), CAItems.EXPERIENCE_BOOTS.get()))
				.save(t, advancementId("experience_armor"));
		// Lapis Armor
		Advancement lapisArmor = registerAdvancement("lapis_armor", FrameType.TASK, CAItems.LAPIS_CHESTPLATE.get()).parent(experienceArmor)
				.addCriterion("lapis_armor", InventoryChangeTrigger.Instance.hasItems(CAItems.LAPIS_HELMET.get(), CAItems.LAPIS_CHESTPLATE.get(), CAItems.LAPIS_LEGGINGS.get(), CAItems.LAPIS_BOOTS.get()))
				.save(t, advancementId("lapis_armor"));
		// Pink Tourmaline Armor
		Advancement pinkTourmalineArmor = registerAdvancement("pink_tourmaline_armor", FrameType.TASK, CAItems.PINK_TOURMALINE_CHESTPLATE.get()).parent(catsEyeArmor)
				.addCriterion("pink_tourmaline_armor", InventoryChangeTrigger.Instance.hasItems(CAItems.PINK_TOURMALINE_HELMET.get(), CAItems.PINK_TOURMALINE_CHESTPLATE.get(), CAItems.PINK_TOURMALINE_LEGGINGS.get(), CAItems.PINK_TOURMALINE_BOOTS.get()))
				.save(t, advancementId("pink_tourmaline_armor"));
		// Peacock Armor
		Advancement peacockArmor = registerAdvancement("peacock_armor", FrameType.TASK, CAItems.PEACOCK_FEATHER_CHESTPLATE.get()).parent(pinkTourmalineArmor)
				.addCriterion("peacock_armor", InventoryChangeTrigger.Instance.hasItems(CAItems.PEACOCK_FEATHER_HELMET.get(), CAItems.PEACOCK_FEATHER_CHESTPLATE.get(), CAItems.PEACOCK_FEATHER_LEGGINGS.get(), CAItems.PEACOCK_FEATHER_BOOTS.get()))
				.save(t, advancementId("peacock_armor"));
		// Ender Dragon Scale Armor
		Advancement enderDragonScaleArmor = registerAdvancement("ender_dragon_scale_armor", FrameType.TASK, CAItems.ENDER_DRAGON_SCALE_CHESTPLATE.get()).parent(peacockArmor)
				.addCriterion("ender_dragon_scale_armor", InventoryChangeTrigger.Instance.hasItems(CAItems.ENDER_DRAGON_SCALE_HELMET.get(), CAItems.ENDER_DRAGON_SCALE_CHESTPLATE.get(), CAItems.ENDER_DRAGON_SCALE_LEGGINGS.get(), CAItems.ENDER_DRAGON_SCALE_BOOTS.get()))
				.save(t, advancementId("ender_dragon_scale_armor"));
		// Tiger's Eye Armor
		Advancement tigersEyeArmor = registerAdvancement("tigers_eye_armor", FrameType.TASK, CAItems.TIGERS_EYE_CHESTPLATE.get()).parent(pinkTourmalineArmor)
				.addCriterion("tigers_eye_armor", InventoryChangeTrigger.Instance.hasItems(CAItems.TIGERS_EYE_HELMET.get(), CAItems.TIGERS_EYE_CHESTPLATE.get(), CAItems.TIGERS_EYE_LEGGINGS.get(), CAItems.TIGERS_EYE_BOOTS.get()))
				.save(t, advancementId("tigers_eye_armor"));
		// Amethyst Armor
		Advancement amethystArmor = registerAdvancement("amethyst_armor", FrameType.TASK, CAItems.AMETHYST_CHESTPLATE.get()).parent(tigersEyeArmor)
				.addCriterion("amethyst_armor", InventoryChangeTrigger.Instance.hasItems(CAItems.AMETHYST_HELMET.get(), CAItems.AMETHYST_CHESTPLATE.get(), CAItems.AMETHYST_LEGGINGS.get(), CAItems.AMETHYST_BOOTS.get()))
				.save(t, advancementId("amethyst_armor"));
		// Ruby Armor
		Advancement rubyArmor = registerAdvancement("ruby_armor", FrameType.TASK, CAItems.RUBY_CHESTPLATE.get()).parent(amethystArmor)
				.addCriterion("ruby_armor", InventoryChangeTrigger.Instance.hasItems(CAItems.RUBY_HELMET.get(), CAItems.RUBY_CHESTPLATE.get(), CAItems.RUBY_LEGGINGS.get(), CAItems.RUBY_BOOTS.get()))
				.save(t, advancementId("ruby_armor"));
		// Lava Eel Armor
		Advancement lavaEelArmor = registerAdvancement("lava_eel_armor", FrameType.TASK, CAItems.LAVA_EEL_CHESTPLATE.get()).parent(amethystArmor)
				.addCriterion("lava_eel_armor", InventoryChangeTrigger.Instance.hasItems(CAItems.LAVA_EEL_HELMET.get(), CAItems.LAVA_EEL_CHESTPLATE.get(), CAItems.LAVA_EEL_LEGGINGS.get(), CAItems.LAVA_EEL_BOOTS.get()))
				.save(t, advancementId("lava_eel_armor"));
		
		// ALL ARMOR
		Advancement allArmor = registerAdvancement("all_armor", FrameType.CHALLENGE, CAItems.ENDER_DRAGON_SCALE_CHESTPLATE.get()).parent(platinumArmor)
				.addCriterion("copper_armor", InventoryChangeTrigger.Instance.hasItems(CAItems.COPPER_HELMET.get(), CAItems.COPPER_CHESTPLATE.get(), CAItems.COPPER_LEGGINGS.get(), CAItems.COPPER_BOOTS.get()))
				.addCriterion("peacock_feather_armor", InventoryChangeTrigger.Instance.hasItems(CAItems.PEACOCK_FEATHER_HELMET.get(), CAItems.PEACOCK_FEATHER_CHESTPLATE.get(), CAItems.PEACOCK_FEATHER_LEGGINGS.get(), CAItems.PEACOCK_FEATHER_BOOTS.get()))
				.addCriterion("tin_armor", InventoryChangeTrigger.Instance.hasItems(CAItems.TIN_HELMET.get(), CAItems.TIN_CHESTPLATE.get(), CAItems.TIN_LEGGINGS.get(), CAItems.TIN_BOOTS.get()))
				.addCriterion("lava_eel_armor", InventoryChangeTrigger.Instance.hasItems(CAItems.LAVA_EEL_HELMET.get(), CAItems.LAVA_EEL_CHESTPLATE.get(), CAItems.LAVA_EEL_LEGGINGS.get(), CAItems.LAVA_EEL_BOOTS.get()))
//				.addCriterion("moth_scale_armor", InventoryChangeTrigger.Instance.hasItems(CAItems.MOTH_SCALE_HELMET.get(), CAItems.MOTH_SCALE_CHESTPLATE.get(), CAItems.MOTH_SCALE_LEGGINGS.get(), CAItems.MOTH_SCALE_BOOTS.get()))
				.addCriterion("silver_armor", InventoryChangeTrigger.Instance.hasItems(CAItems.SILVER_HELMET.get(), CAItems.SILVER_CHESTPLATE.get(), CAItems.SILVER_LEGGINGS.get(), CAItems.SILVER_BOOTS.get()))
				.addCriterion("pink_tourmaline_armor", InventoryChangeTrigger.Instance.hasItems(CAItems.PINK_TOURMALINE_HELMET.get(), CAItems.PINK_TOURMALINE_CHESTPLATE.get(), CAItems.PINK_TOURMALINE_LEGGINGS.get(), CAItems.PINK_TOURMALINE_BOOTS.get()))
				.addCriterion("lapis_armor", InventoryChangeTrigger.Instance.hasItems(CAItems.LAPIS_HELMET.get(), CAItems.LAPIS_CHESTPLATE.get(), CAItems.LAPIS_LEGGINGS.get(), CAItems.LAPIS_BOOTS.get()))
				.addCriterion("emerald_armor", InventoryChangeTrigger.Instance.hasItems(CAItems.EMERALD_HELMET.get(), CAItems.EMERALD_CHESTPLATE.get(), CAItems.EMERALD_LEGGINGS.get(), CAItems.EMERALD_BOOTS.get()))
				.addCriterion("amethyst_armor", InventoryChangeTrigger.Instance.hasItems(CAItems.AMETHYST_HELMET.get(), CAItems.AMETHYST_CHESTPLATE.get(), CAItems.AMETHYST_LEGGINGS.get(), CAItems.AMETHYST_BOOTS.get()))
				.addCriterion("cats_eye_armor", InventoryChangeTrigger.Instance.hasItems(CAItems.CATS_EYE_HELMET.get(), CAItems.CATS_EYE_CHESTPLATE.get(), CAItems.CATS_EYE_LEGGINGS.get(), CAItems.CATS_EYE_BOOTS.get()))
				.addCriterion("tigers_eye_armor", InventoryChangeTrigger.Instance.hasItems(CAItems.TIGERS_EYE_HELMET.get(), CAItems.TIGERS_EYE_CHESTPLATE.get(), CAItems.TIGERS_EYE_LEGGINGS.get(), CAItems.TIGERS_EYE_BOOTS.get()))
				.addCriterion("platinum_armor", InventoryChangeTrigger.Instance.hasItems(CAItems.PLATINUM_HELMET.get(), CAItems.PLATINUM_CHESTPLATE.get(), CAItems.PLATINUM_LEGGINGS.get(), CAItems.PLATINUM_BOOTS.get()))
				.addCriterion("experience_armor", InventoryChangeTrigger.Instance.hasItems(CAItems.EXPERIENCE_HELMET.get(), CAItems.EXPERIENCE_CHESTPLATE.get(), CAItems.EXPERIENCE_LEGGINGS.get(), CAItems.EXPERIENCE_BOOTS.get()))
				.addCriterion("ruby_armor", InventoryChangeTrigger.Instance.hasItems(CAItems.RUBY_HELMET.get(), CAItems.RUBY_CHESTPLATE.get(), CAItems.RUBY_LEGGINGS.get(), CAItems.RUBY_BOOTS.get()))
				.addCriterion("ultimate_armor", InventoryChangeTrigger.Instance.hasItems(CAItems.ULTIMATE_HELMET.get(), CAItems.ULTIMATE_CHESTPLATE.get(), CAItems.ULTIMATE_LEGGINGS.get(), CAItems.ULTIMATE_BOOTS.get()))
				.addCriterion("ender_dragon_scale_armor", InventoryChangeTrigger.Instance.hasItems(CAItems.ENDER_DRAGON_SCALE_HELMET.get(), CAItems.ENDER_DRAGON_SCALE_CHESTPLATE.get(), CAItems.ENDER_DRAGON_SCALE_LEGGINGS.get(), CAItems.ENDER_DRAGON_SCALE_BOOTS.get()))
				.save(t, advancementId("all_armor"));
		
		// CRYSTAL WORLD SERIES
		Advancement crystalWorld = registerAdvancement("crystal_world", FrameType.TASK, CABlocks.CRYSTAL_GRASS_BLOCK.get()).parent(root)
				.addCriterion("crystal_world", ChangeDimensionTrigger.Instance.changedDimensionTo(CADimensions.CRYSTAL_WORLD))
				.save(t, advancementId("crystal_world"));
		Advancement clearlyWood = registerAdvancement("clearly_wood", FrameType.TASK, CABlocks.CRYSTAL_LOG.get()).parent(crystalWorld)
				.addCriterion("crystal_wood", InventoryChangeTrigger.Instance.hasItems(ItemPredicate.Builder.item().of(CATags.Items.CRYSTAL_LOGS).build()))
				.save(t, advancementId("clearly_wood"));

		// MINING PARADISE SERIES
		Advancement miningParadise = registerAdvancement("mining_paradise", FrameType.TASK, CABlocks.URANIUM_ORE.get()).parent(root)
				.addCriterion("mining_paradise", ChangeDimensionTrigger.Instance.changedDimensionTo(CADimensions.MINING_PARADISE))
				.save(t, advancementId("mining_paradise"));	
		Advancement didntTheyGoExtinct = registerAdvancement("didnt_they_go_extinct", FrameType.TASK, CAItems.DIMETRODON_SPAWN_EGG.get()).parent(miningParadise)
				.addCriterion("dimetrodon", KilledTrigger.Instance.playerKilledEntity(EntityPredicate.Builder.entity().of(CAEntityTypes.DIMETRODON.get())))
				.save(t, advancementId("didnt_they_go_extinct"));
		Advancement dreamToReality = registerAdvancement("dream_to_reality", FrameType.GOAL, CAItems.MINERS_DREAM.get()).parent(miningParadise)
				.addCriterion("used_miners_dream_stalagmite_valley", RightClickBlockWithItemTrigger.Instance.itemUsedOnBlock(LocationPredicate.Builder.location().setBiome(RegistryKey.create(Registry.BIOME_REGISTRY, new ResourceLocation(ChaosAwakens.MODID, "dense_mountain"))), ItemPredicate.Builder.item().of(CAItems.MINERS_DREAM.get())))
				.addCriterion("used_miners_dream_dense_mountain", RightClickBlockWithItemTrigger.Instance.itemUsedOnBlock(LocationPredicate.Builder.location().setBiome(RegistryKey.create(Registry.BIOME_REGISTRY, new ResourceLocation(ChaosAwakens.MODID, "stalagmite_valley"))), ItemPredicate.Builder.item().of(CAItems.MINERS_DREAM.get())))
				.requirements(IRequirementsStrategy.OR).save(t, advancementId("dream_to_reality"));

		// VILLAGE MANIA SERIES
		Advancement villageMania = registerAdvancement("village_mania", FrameType.TASK, Items.EMERALD).parent(root)
				.addCriterion("village_mania", ChangeDimensionTrigger.Instance.changedDimensionTo(CADimensions.VILLAGE_MANIA))
				.save(t, advancementId("village_mania"));
		Advancement roboSlayer = registerAdvancement("robo_slayer", FrameType.TASK, CAItems.RAY_GUN.get()).parent(villageMania)
				.addCriterion("robo_pounder", KilledTrigger.Instance.playerKilledEntity(EntityPredicate.Builder.entity().of(CAEntityTypes.ROBO_POUNDER.get())))
				.addCriterion("robo_sniper", KilledTrigger.Instance.playerKilledEntity(EntityPredicate.Builder.entity().of(CAEntityTypes.ROBO_SNIPER.get())))
				.addCriterion("robo_warrior", KilledTrigger.Instance.playerKilledEntity(EntityPredicate.Builder.entity().of(CAEntityTypes.ROBO_WARRIOR.get())))
				.save(t, advancementId("robo_slayer"));

		Advancement bugSquasher = registerAdvancement("bug_squasher", FrameType.TASK, CAItems.DEAD_STINK_BUG.get()).parent(root)
				.addCriterion("hercules_beetle", KilledTrigger.Instance.playerKilledEntity(EntityPredicate.Builder.entity().of(CAEntityTypes.HERCULES_BEETLE.get())))
				.addCriterion("ruby_bug", KilledTrigger.Instance.playerKilledEntity(EntityPredicate.Builder.entity().of(CAEntityTypes.RUBY_BUG.get())))
				.addCriterion("stink_bug", KilledTrigger.Instance.playerKilledEntity(EntityPredicate.Builder.entity().of(CAEntityTypes.STINK_BUG.get())))
				.save(t, advancementId("bug_squasher"));
		
		Advancement minusculeImprisonment = registerAdvancement("minuscule_imprisonment", FrameType.TASK, CAItems.CRITTER_CAGE.get()).parent(root)
				.addCriterion("used_critter_cage", PlayerEntityInteractionTrigger.Instance.itemUsedOnEntity(EntityPredicate.AndPredicate.ANY, ItemPredicate.Builder.item().of(CAItems.CRITTER_CAGE.get()), EntityPredicate.AndPredicate.ANY))
				.save(t, advancementId("miniscule_imprisonment"));

		Advancement goFish = registerAdvancement("go_fish", FrameType.TASK, CAItems.ROCK_FISH.get()).parent(root)
				.addCriterion("green_fish", KilledTrigger.Instance.playerKilledEntity(EntityPredicate.Builder.entity().of(CAEntityTypes.GREEN_FISH.get())))
				.addCriterion("rock_fish", KilledTrigger.Instance.playerKilledEntity(EntityPredicate.Builder.entity().of(CAEntityTypes.ROCK_FISH.get())))
				.addCriterion("spark_fish", KilledTrigger.Instance.playerKilledEntity(EntityPredicate.Builder.entity().of(CAEntityTypes.SPARK_FISH.get())))
				.addCriterion("wood_fish", KilledTrigger.Instance.playerKilledEntity(EntityPredicate.Builder.entity().of(CAEntityTypes.WOOD_FISH.get())))
//				.addCriterion("blue_fish", KilledTrigger.Instance.playerKilledEntity(EntityPredicate.Builder.entity().of(CAEntityTypes.BLUE_FISH.get())))
//				.addCriterion("gray_fish", KilledTrigger.Instance.playerKilledEntity(EntityPredicate.Builder.entity().of(CAEntityTypes.GRAY_FISH.get())))
//				.addCriterion("pink_fish", KilledTrigger.Instance.playerKilledEntity(EntityPredicate.Builder.entity().of(CAEntityTypes.PINK_FISH.get())))
				.save(t, advancementId("go_fish"));

		Advancement entDestroyer = registerAdvancement("ent_destroyer", FrameType.TASK, Blocks.OAK_LEAVES).parent(root)
				.addCriterion("acacia_ent", KilledTrigger.Instance.playerKilledEntity(EntityPredicate.Builder.entity().of(CAEntityTypes.ACACIA_ENT.get())))
				.addCriterion("birch_ent", KilledTrigger.Instance.playerKilledEntity(EntityPredicate.Builder.entity().of(CAEntityTypes.BIRCH_ENT.get())))
				.addCriterion("crimson_ent", KilledTrigger.Instance.playerKilledEntity(EntityPredicate.Builder.entity().of(CAEntityTypes.CRIMSON_ENT.get())))
				.addCriterion("dark_oak_ent", KilledTrigger.Instance.playerKilledEntity(EntityPredicate.Builder.entity().of(CAEntityTypes.DARK_OAK_ENT.get())))
				.addCriterion("jungle_ent", KilledTrigger.Instance.playerKilledEntity(EntityPredicate.Builder.entity().of(CAEntityTypes.JUNGLE_ENT.get())))
				.addCriterion("oak_ent", KilledTrigger.Instance.playerKilledEntity(EntityPredicate.Builder.entity().of(CAEntityTypes.OAK_ENT.get())))
				.addCriterion("spruce_ent", KilledTrigger.Instance.playerKilledEntity(EntityPredicate.Builder.entity().of(CAEntityTypes.SPRUCE_ENT.get())))
				.addCriterion("warped_ent", KilledTrigger.Instance.playerKilledEntity(EntityPredicate.Builder.entity().of(CAEntityTypes.WARPED_ENT.get())))
				.save(t, advancementId("ent_destroyer"));

		Advancement stopBeingSalty = registerAdvancement("stop_being_salty", FrameType.TASK, CABlocks.SALT_BLOCK.get()).parent(root)
				.addCriterion("salt_block", InventoryChangeTrigger.Instance.hasItems(CABlocks.SALT_BLOCK.get()))
				.save(t, advancementId("stop_being_salty"));

		// Apple Cow Series
		Advancement anAppleCowADay = registerAdvancement("an_apple_cow_a_day", FrameType.TASK, CAItems.APPLE_COW_SPAWN_EGG.get()).parent(root)
				.addCriterion("apple_cow", BredAnimalsTrigger.Instance.bredAnimals(EntityPredicate.Builder.entity().of(CAEntityTypes.APPLE_COW.get()).build(), EntityPredicate.Builder.entity().of(CAEntityTypes.APPLE_COW.get()).build(), EntityPredicate.ANY))
				.save(t, advancementId("an_apple_cow_a_day"));
		Advancement shinyCows = registerAdvancement("shiny_cows", FrameType.TASK, CAItems.GOLDEN_APPLE_COW_SPAWN_EGG.get()).parent(anAppleCowADay)
				.addCriterion("golden_apple_cow", BredAnimalsTrigger.Instance.bredAnimals(EntityPredicate.Builder.entity().of(CAEntityTypes.GOLDEN_APPLE_COW.get()).build(), EntityPredicate.Builder.entity().of(CAEntityTypes.GOLDEN_APPLE_COW.get()).build(), EntityPredicate.ANY))
				.addCriterion("enchanted_golden_apple_cow", BredAnimalsTrigger.Instance.bredAnimals(EntityPredicate.Builder.entity().of(CAEntityTypes.ENCHANTED_GOLDEN_APPLE_COW.get()).build(), EntityPredicate.Builder.entity().of(CAEntityTypes.ENCHANTED_GOLDEN_APPLE_COW.get()).build(), EntityPredicate.ANY))
				.requirements(IRequirementsStrategy.OR).save(t, advancementId("shiny_cows"));		
		Advancement theGoldenChild = registerAdvancement("the_golden_child", FrameType.CHALLENGE, CAItems.ENCHANTED_GOLDEN_APPLE_COW_SPAWN_EGG.get()).parent(shinyCows)
				.addCriterion("ultimate_apple_cow", BredAnimalsTrigger.Instance.bredAnimals(EntityPredicate.Builder.entity().of(CAEntityTypes.ENCHANTED_GOLDEN_APPLE_COW.get()).build(), EntityPredicate.Builder.entity().of(CAEntityTypes.ENCHANTED_GOLDEN_APPLE_COW.get()).build(), EntityPredicate.Builder.entity().of(CAEntityTypes.ULTIMATE_APPLE_COW.get()).build()))
				.save(t, advancementId("the_golden_child"));
		Advancement appleHoarder = registerAdvancement("apple_hoarder", FrameType.GOAL, Items.APPLE).parent(anAppleCowADay)
				.addCriterion("apple", InventoryChangeTrigger.Instance.hasItems(Items.APPLE))
				.addCriterion("golden_apple", InventoryChangeTrigger.Instance.hasItems(Items.GOLDEN_APPLE))
				.addCriterion("enchanted_golden_apple", InventoryChangeTrigger.Instance.hasItems(Items.ENCHANTED_GOLDEN_APPLE))
				.addCriterion("crystal_apple", InventoryChangeTrigger.Instance.hasItems(CAItems.CRYSTAL_APPLE.get()))
				.addCriterion("ultimate_apple", InventoryChangeTrigger.Instance.hasItems(CAItems.ULTIMATE_APPLE.get()))
				.requirements(IRequirementsStrategy.AND).save(t, advancementId("apple_hoarder"));

		// Carrot Pig Series
		Advancement carrotVision = registerAdvancement("carrot_vision", FrameType.TASK, CAItems.CARROT_PIG_SPAWN_EGG.get()).parent(root)
				.addCriterion("carrot_pig", BredAnimalsTrigger.Instance.bredAnimals(EntityPredicate.Builder.entity().of(CAEntityTypes.CARROT_PIG.get()).build(), EntityPredicate.Builder.entity().of(CAEntityTypes.CARROT_PIG.get()).build(), EntityPredicate.ANY))
				.save(t, advancementId("carrot_vision"));
		Advancement shinyPigs = registerAdvancement("shiny_pigs", FrameType.TASK, CAItems.GOLDEN_CARROT_PIG_SPAWN_EGG.get()).parent(carrotVision)
				.addCriterion("golden_carrot_pig", BredAnimalsTrigger.Instance.bredAnimals(EntityPredicate.Builder.entity().of(CAEntityTypes.GOLDEN_CARROT_PIG.get()).build(), EntityPredicate.Builder.entity().of(CAEntityTypes.GOLDEN_CARROT_PIG.get()).build(), EntityPredicate.ANY))
				.addCriterion("enchanted_golden_carrot_pig", BredAnimalsTrigger.Instance.bredAnimals(EntityPredicate.Builder.entity().of(CAEntityTypes.ENCHANTED_GOLDEN_CARROT_PIG.get()).build(), EntityPredicate.Builder.entity().of(CAEntityTypes.ENCHANTED_GOLDEN_CARROT_PIG.get()).build(), EntityPredicate.ANY))
				.requirements(IRequirementsStrategy.OR).save(t, advancementId("shiny_pigs"));
		Advancement superCarrotVision = registerAdvancement("super_carrot_vision", FrameType.GOAL, Items.CARROT).parent(carrotVision)
				.addCriterion("carrot", InventoryChangeTrigger.Instance.hasItems(Items.CARROT))
				.addCriterion("golden_carrot", InventoryChangeTrigger.Instance.hasItems(Items.GOLDEN_CARROT))
				.addCriterion("enchanted_golden_carrot", InventoryChangeTrigger.Instance.hasItems(CAItems.ENCHANTED_GOLDEN_CARROT.get()))
				.addCriterion("crystal_carrot", InventoryChangeTrigger.Instance.hasItems(CAItems.CRYSTAL_CARROT.get()))
				.requirements(IRequirementsStrategy.AND).save(t, advancementId("super_carrot_vision"));
		
		// Defossilizer Series
		Advancement chaoticByChoice = registerAdvancement("chaotic_by_choice", FrameType.TASK, CABlocks.DEFOSSILIZER_BLOCKS.get(CABlocks.DefossilizerType.byId(CABlocks.DefossilizerType.IRON.getId())).get()).parent(root)
				.addCriterion("copper_defossilizer", InventoryChangeTrigger.Instance.hasItems(CABlocks.DEFOSSILIZER_BLOCKS.get(CABlocks.DefossilizerType.byId(CABlocks.DefossilizerType.COPPER.getId())).get()))
				.addCriterion("iron_defossilizer", InventoryChangeTrigger.Instance.hasItems(CABlocks.DEFOSSILIZER_BLOCKS.get(CABlocks.DefossilizerType.byId(CABlocks.DefossilizerType.IRON.getId())).get()))
				.requirements(IRequirementsStrategy.OR).save(t, advancementId("chaotic_by_choice"));
		Advancement empoweringDefossilization = registerAdvancement("empowering_defossilization", FrameType.TASK, CAItems.ALUMINUM_POWER_CHIP.get()).parent(chaoticByChoice)
				.addCriterion("copper_defossilizer", InventoryChangeTrigger.Instance.hasItems(CAItems.ALUMINUM_POWER_CHIP.get()))
				.save(t, advancementId("empowering_defossilization"));
		Advancement aPieceOfHistory = registerAdvancement("a_piece_of_history", FrameType.TASK, CABlocks.FOSSILISED_CREEPER.get()).parent(chaoticByChoice)
				.addCriterion("fossilised_mob", InventoryChangeTrigger.Instance.hasItems(ItemPredicate.Builder.item().of(CATags.Items.FOSSILS).build()))
				.save(t, advancementId("a_piece_of_history"));
		
		// Ultimate Series
		Advancement roadToUltimate = registerAdvancement("road_to_ultimate", FrameType.TASK, CAItems.ULTIMATE_HELMET.get()).parent(root)
				.addCriterion("platinum_lump", InventoryChangeTrigger.Instance.hasItems(CAItems.PLATINUM_LUMP.get()))
				.addCriterion("titanium_nugget", InventoryChangeTrigger.Instance.hasItems(CAItems.TITANIUM_NUGGET.get()))
				.addCriterion("uranium_nugget", InventoryChangeTrigger.Instance.hasItems(CAItems.URANIUM_NUGGET.get()))
				.requirements(IRequirementsStrategy.AND).save(t, advancementId("road_to_ultimate"));
		Advancement trophyWorthy = registerAdvancement("trophy_worthy", FrameType.CHALLENGE, CABlocks.PLATINUM_BLOCK.get()).parent(roadToUltimate)
				.addCriterion("platinum_block", InventoryChangeTrigger.Instance.hasItems(CABlocks.PLATINUM_BLOCK.get()))
				.addCriterion("uranium_block", InventoryChangeTrigger.Instance.hasItems(CABlocks.URANIUM_BLOCK.get()))
				.addCriterion("titanium_block", InventoryChangeTrigger.Instance.hasItems(CABlocks.TITANIUM_BLOCK.get()))
				.rewards(AdvancementRewards.Builder.experience(90))
				.save(t, advancementId("trophy_worthy"));
		Advancement ultimateSlayer = registerAdvancement("ultimate_slayer", FrameType.TASK, CAItems.ULTIMATE_SWORD.get()).parent(roadToUltimate)
				.addCriterion("ultimate_sword", InventoryChangeTrigger.Instance.hasItems(CAItems.ULTIMATE_SWORD.get()))
				.requirements(IRequirementsStrategy.AND).save(t, advancementId("ultimate_slayer"));
		Advancement hateToBreakIt = registerAdvancement("hate_to_break_it", FrameType.GOAL, CAItems.ULTIMATE_SWORD.get()).parent(ultimateSlayer)
				.addCriterion("broken_ultimate_sword", ItemDurabilityTrigger.Instance.changedDurability(EntityPredicate.AndPredicate.ANY, ItemPredicate.Builder.item().of(CAItems.ULTIMATE_SWORD.get()).build(), IntBound.exactly(0)))
				.rewards(AdvancementRewards.Builder.experience(60))
				.requirements(IRequirementsStrategy.AND).save(t, advancementId("hate_to_break_it"));
		Advancement ultimateMiner = registerAdvancement("ultimate_miner", FrameType.TASK, CAItems.ULTIMATE_PICKAXE.get()).parent(ultimateSlayer)
				.addCriterion("ultimate_pickaxe", InventoryChangeTrigger.Instance.hasItems(CAItems.ULTIMATE_PICKAXE.get()))
				.requirements(IRequirementsStrategy.AND).save(t, advancementId("ultimate_miner"));
		Advancement mineTooMuch = registerAdvancement("mine_too_much", FrameType.GOAL, CAItems.ULTIMATE_PICKAXE.get()).parent(ultimateMiner)
				.addCriterion("broken_ultimate_pickaxe", ItemDurabilityTrigger.Instance.changedDurability(EntityPredicate.AndPredicate.ANY, ItemPredicate.Builder.item().of(CAItems.ULTIMATE_PICKAXE.get()).build(), IntBound.exactly(0)))
				.rewards(AdvancementRewards.Builder.experience(60))
				.requirements(IRequirementsStrategy.AND).save(t, advancementId("mine_too_much"));
		Advancement ultimateLumberjack = registerAdvancement("ultimate_lumberjack", FrameType.TASK, CAItems.ULTIMATE_AXE.get()).parent(ultimateMiner)
				.addCriterion("ultimate_axe", InventoryChangeTrigger.Instance.hasItems(CAItems.ULTIMATE_AXE.get()))
				.requirements(IRequirementsStrategy.AND).save(t, advancementId("ultimate_lumberjack"));
		Advancement antiLorax = registerAdvancement("anti_lorax", FrameType.GOAL, CAItems.ULTIMATE_AXE.get()).parent(ultimateLumberjack)
				.addCriterion("broken_ultimate_axe", ItemDurabilityTrigger.Instance.changedDurability(EntityPredicate.AndPredicate.ANY, ItemPredicate.Builder.item().of(CAItems.ULTIMATE_AXE.get()).build(), IntBound.exactly(0)))
				.rewards(AdvancementRewards.Builder.experience(60))
				.requirements(IRequirementsStrategy.AND).save(t, advancementId("anti_lorax"));
		Advancement ultimateDigger = registerAdvancement("ultimate_excavator", FrameType.TASK, CAItems.ULTIMATE_SHOVEL.get()).parent(ultimateLumberjack)
				.addCriterion("ultimate_shovel", InventoryChangeTrigger.Instance.hasItems(CAItems.ULTIMATE_SHOVEL.get()))
				.requirements(IRequirementsStrategy.AND).save(t, advancementId("ultimate_excavator"));
		Advancement groundBreaking = registerAdvancement("ground_breaking", FrameType.GOAL, CAItems.ULTIMATE_SHOVEL.get()).parent(ultimateDigger)
				.addCriterion("broken_ultimate_shovel", ItemDurabilityTrigger.Instance.changedDurability(EntityPredicate.AndPredicate.ANY, ItemPredicate.Builder.item().of(CAItems.ULTIMATE_SHOVEL.get()).build(), IntBound.exactly(0)))
				.rewards(AdvancementRewards.Builder.experience(60))
				.requirements(IRequirementsStrategy.AND).save(t, advancementId("ground_breaking"));
		Advancement ultimateFarmer = registerAdvancement("ultimate_farmer", FrameType.TASK, CAItems.ULTIMATE_HOE.get()).parent(ultimateDigger)
				.addCriterion("ultimate_hoe", InventoryChangeTrigger.Instance.hasItems(CAItems.ULTIMATE_HOE.get()))
				.requirements(IRequirementsStrategy.AND).save(t, advancementId("ultimate_farmer"));
		Advancement veryVegan = registerAdvancement("very_vegan", FrameType.GOAL, CAItems.ULTIMATE_HOE.get()).parent(ultimateFarmer)
				.addCriterion("broken_ultimate_hoe", ItemDurabilityTrigger.Instance.changedDurability(EntityPredicate.AndPredicate.ANY, ItemPredicate.Builder.item().of(CAItems.ULTIMATE_HOE.get()).build(), IntBound.exactly(0)))
				.rewards(AdvancementRewards.Builder.experience(60))
				.requirements(IRequirementsStrategy.AND).save(t, advancementId("very_vegan"));
		Advancement ultimateArcher = registerAdvancement("ultimate_archer", FrameType.TASK, CAItems.ULTIMATE_BOW.get()).parent(ultimateFarmer)
				.addCriterion("ultimate_bow", InventoryChangeTrigger.Instance.hasItems(CAItems.ULTIMATE_BOW.get()))
				.rewards(AdvancementRewards.Builder.experience(60))
				.requirements(IRequirementsStrategy.AND).save(t, advancementId("ultimate_archer"));
		Advancement apollo = registerAdvancement("apollo", FrameType.GOAL, CAItems.ULTIMATE_BOW.get()).parent(ultimateArcher)
				.addCriterion("broken_ultimate_bow", ItemDurabilityTrigger.Instance.changedDurability(EntityPredicate.AndPredicate.ANY, ItemPredicate.Builder.item().of(CAItems.ULTIMATE_BOW.get()).build(), IntBound.exactly(0)))
				.rewards(AdvancementRewards.Builder.experience(60))
				.requirements(IRequirementsStrategy.AND).save(t, advancementId("apollo"));
		Advancement ultimateFisherman = registerAdvancement("ultimate_fisherman", FrameType.TASK, CAItems.ULTIMATE_FISHING_ROD.get()).parent(ultimateArcher)
				.addCriterion("ultimate_fishing_rod", InventoryChangeTrigger.Instance.hasItems(CAItems.ULTIMATE_FISHING_ROD.get()))
				.requirements(IRequirementsStrategy.AND).save(t, advancementId("ultimate_fisherman"));
		Advancement cleanTheSea = registerAdvancement("clean_the_sea", FrameType.GOAL, CAItems.ULTIMATE_FISHING_ROD.get()).parent(ultimateFisherman)
				.addCriterion("broken_ultimate_fishing_rod", ItemDurabilityTrigger.Instance.changedDurability(EntityPredicate.AndPredicate.ANY, ItemPredicate.Builder.item().of(CAItems.ULTIMATE_FISHING_ROD.get()).build(), IntBound.exactly(0)))
				.rewards(AdvancementRewards.Builder.experience(60))
				.requirements(IRequirementsStrategy.AND).save(t, advancementId("clean_the_sea"));
		Advancement ultimateArmor = registerAdvancement("ultimate_armor", FrameType.TASK, CAItems.ULTIMATE_CHESTPLATE.get()).parent(trophyWorthy)
				.addCriterion("ultimate_armor", InventoryChangeTrigger.Instance.hasItems(CAItems.ULTIMATE_HELMET.get(), CAItems.ULTIMATE_CHESTPLATE.get(), CAItems.ULTIMATE_LEGGINGS.get(), CAItems.ULTIMATE_BOOTS.get()))
				.requirements(IRequirementsStrategy.AND).save(t, advancementId("ultimate_armor"));
		Advancement brokenBeyond = registerAdvancement("broken_beyond", FrameType.GOAL, CAItems.ULTIMATE_CHESTPLATE.get()).parent(ultimateArmor)
				.addCriterion("broken_ultimate_helmet", ItemDurabilityTrigger.Instance.changedDurability(EntityPredicate.AndPredicate.ANY, ItemPredicate.Builder.item().of(CAItems.ULTIMATE_HELMET.get()).build(), IntBound.exactly(0)))
				.addCriterion("broken_ultimate_chestplate", ItemDurabilityTrigger.Instance.changedDurability(EntityPredicate.AndPredicate.ANY, ItemPredicate.Builder.item().of(CAItems.ULTIMATE_CHESTPLATE.get()).build(), IntBound.exactly(0)))
				.addCriterion("broken_ultimate_leggings", ItemDurabilityTrigger.Instance.changedDurability(EntityPredicate.AndPredicate.ANY, ItemPredicate.Builder.item().of(CAItems.ULTIMATE_LEGGINGS.get()).build(), IntBound.exactly(0)))
				.addCriterion("broken_ultimate_boots", ItemDurabilityTrigger.Instance.changedDurability(EntityPredicate.AndPredicate.ANY, ItemPredicate.Builder.item().of(CAItems.ULTIMATE_BOOTS.get()).build(), IntBound.exactly(0)))
				.rewards(AdvancementRewards.Builder.experience(60))
				.requirements(IRequirementsStrategy.AND).save(t, advancementId("broken_beyond"));
		Advancement ultimateMeal = registerAdvancement("ultimate_meal", FrameType.GOAL, CAItems.ULTIMATE_APPLE.get()).parent(ultimateArmor)
				.addCriterion("ultimate_apple", InventoryChangeTrigger.Instance.hasItems(CAItems.ULTIMATE_APPLE.get()))
				.requirements(IRequirementsStrategy.AND).save(t, advancementId("ultimate_meal"));
		Advancement ultimatePlayer = registerAdvancement("ultimate_player", FrameType.CHALLENGE, CABlocks.TITANIUM_BLOCK.get()).parent(ultimateMeal)
				.addCriterion("road_to_ultimate", AdvancementCompleteTrigger.Instance.advancementComplete(roadToUltimate.getId()))
				.addCriterion("trophy_worthy", AdvancementCompleteTrigger.Instance.advancementComplete(trophyWorthy.getId()))
				.addCriterion("ultimate_slayer", AdvancementCompleteTrigger.Instance.advancementComplete(ultimateSlayer.getId()))
				.addCriterion("hate_to_break_it", AdvancementCompleteTrigger.Instance.advancementComplete(hateToBreakIt.getId()))
				.addCriterion("ultimate_miner", AdvancementCompleteTrigger.Instance.advancementComplete(ultimateMiner.getId()))
				.addCriterion("mine_too_much", AdvancementCompleteTrigger.Instance.advancementComplete(mineTooMuch.getId()))
				.addCriterion("ultimate_lumberjack", AdvancementCompleteTrigger.Instance.advancementComplete(ultimateLumberjack.getId()))
				.addCriterion("anti_lorax", AdvancementCompleteTrigger.Instance.advancementComplete(antiLorax.getId()))
				.addCriterion("ultimate_digger", AdvancementCompleteTrigger.Instance.advancementComplete(ultimateDigger.getId()))
				.addCriterion("ground_breaking", AdvancementCompleteTrigger.Instance.advancementComplete(groundBreaking.getId()))
				.addCriterion("ultimate_farmer", AdvancementCompleteTrigger.Instance.advancementComplete(ultimateFarmer.getId()))
				.addCriterion("very_vegan", AdvancementCompleteTrigger.Instance.advancementComplete(veryVegan.getId()))
				.addCriterion("ultimate_archer", AdvancementCompleteTrigger.Instance.advancementComplete(ultimateArcher.getId()))
				.addCriterion("apollo", AdvancementCompleteTrigger.Instance.advancementComplete(apollo.getId()))
				.addCriterion("ultimate_fisherman", AdvancementCompleteTrigger.Instance.advancementComplete(ultimateFisherman.getId()))
				.addCriterion("clean_the_sea", AdvancementCompleteTrigger.Instance.advancementComplete(cleanTheSea.getId()))
				.addCriterion("ultimate_armor", AdvancementCompleteTrigger.Instance.advancementComplete(ultimateArmor.getId()))
				.addCriterion("broken_beyond", AdvancementCompleteTrigger.Instance.advancementComplete(brokenBeyond.getId()))
				.addCriterion("ultimate_meal", AdvancementCompleteTrigger.Instance.advancementComplete(ultimateMeal.getId()))
				.rewards(AdvancementRewards.Builder.experience(180))
				.requirements(IRequirementsStrategy.AND).save(t, advancementId("ultimate_player"));
		
//		registerSlayAdvancements(t);
	}

	public void registerSlayAdvancements(Consumer<Advancement> consumer) {
		Advancement.Builder slayRootBuilder = registerAdvancement("root", FrameType.CHALLENGE, CAItems.EMERALD_GATOR_SPAWN_EGG.get()).requirements(IRequirementsStrategy.AND);
		CAEntityTypes.ENTITY_TYPES.getEntries().stream()
				.filter(type -> !(type.getId().getPath().equals("thunder_ball") || type.getId().getPath().equals("ultimate_arrow") || type.getId().getPath().equals("ultimate_fishing_bobber") || type.getId().getPath().equals("robo_laser") || type.getId().getPath().equals("irukandji_arrow") || type.getId().getPath().equals("explosive_ball")))
				.forEach(type -> slayRootBuilder.addCriterion("slay_" + type.get().getRegistryName(), KilledTrigger.Instance.playerKilledEntity(new EntityPredicate.Builder().of(type.get()))));
		Advancement slayRoot = slayRootBuilder
				.save(consumer, advancementId("slay_root"));

		CAEntityTypes.ENTITY_TYPES.getEntries().stream()
				.filter(type -> !(type.getId().getPath().equals("thunder_ball") || type.getId().getPath().equals("ultimate_arrow") || type.getId().getPath().equals("ultimate_fishing_bobber") || type.getId().getPath().equals("robo_laser") || type.getId().getPath().equals("irukandji_arrow") || type.getId().getPath().equals("explosive_ball")))
				.forEach(type -> {
							Advancement.Builder.advancement()
									.parent(slayRoot)
									.display(new ItemStack(Items.EGG),
											new TranslationTextComponent("advancements.chaosawakens.slay_" + type.getId().getPath() + ".title"),
											new TranslationTextComponent("advancements.chaosawakens.slay_" + type.getId().getPath() + ".description"),
											BACKGROUND_TEXTURE, FrameType.TASK, false, false, false)
									.addCriterion("slay_" + type.getId().getPath(), KilledTrigger.Instance.playerKilledEntity(EntityPredicate.Builder.entity().of(type.get())))
									.save(consumer, slayAdvancementId(type.getId().getPath()));
						}
				);
	}
	
	private Advancement.Builder registerAdvancement(String name, FrameType type, IItemProvider... items) {
		Validate.isTrue(items.length > 0);
		return Advancement.Builder.advancement().display(items[0],
				new TranslationTextComponent("advancements.chaosawakens." + name + ".title"),
				new TranslationTextComponent("advancements.chaosawakens." + name + ".description"),
				BACKGROUND_TEXTURE, type, true, true, false);
	}
}
