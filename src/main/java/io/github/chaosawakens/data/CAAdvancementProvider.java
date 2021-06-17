package io.github.chaosawakens.data;

import com.google.common.collect.Sets;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import io.github.chaosawakens.ChaosAwakens;
import io.github.chaosawakens.common.registry.CABlocks;
import io.github.chaosawakens.common.registry.CADimensions;
import io.github.chaosawakens.common.registry.CAEntityTypes;
import io.github.chaosawakens.common.registry.CAItems;
import net.minecraft.advancements.Advancement;
import net.minecraft.advancements.FrameType;
import net.minecraft.advancements.criterion.*;
import net.minecraft.block.Blocks;
import net.minecraft.data.AdvancementProvider;
import net.minecraft.data.DataGenerator;
import net.minecraft.data.DirectoryCache;
import net.minecraft.data.IDataProvider;
import net.minecraft.util.IItemProvider;
import net.minecraft.util.RegistryKey;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.registry.Registry;
import net.minecraft.util.text.TranslationTextComponent;
import org.apache.commons.lang3.Validate;

import java.io.IOException;
import java.nio.file.Path;
import java.util.Set;
import java.util.function.Consumer;

public class CAAdvancementProvider extends AdvancementProvider {
	private static final ResourceLocation BACKGROUND_TEXTURE = new ResourceLocation(ChaosAwakens.MODID, "textures/gui/advancement_bg.png");
	private static final Gson GSON = (new GsonBuilder()).setPrettyPrinting().create();
	private final DataGenerator generator;
	
	public CAAdvancementProvider(DataGenerator generatorIn) {
		super(generatorIn);
		this.generator = generatorIn;
	}
	
	@Override
	public void act(DirectoryCache cache) throws IOException {
		Path path = this.generator.getOutputFolder();
		Set<ResourceLocation> set = Sets.newHashSet();
		Consumer<Advancement> consumer = (advancement) -> {
			if (!set.add(advancement.getId())) {
				throw new IllegalStateException("Duplicate advancement " + advancement.getId());
			} else {
				Path path1 = getPath(path, advancement);
				try {
					IDataProvider.save(GSON, cache, advancement.copy().serialize(), path1);
				} catch (IOException e) {
					ChaosAwakens.LOGGER.error("Couldn't save advancement {}", path1, e);
				}
			}
		};
		this.register(consumer);
	}
	
	private static Path getPath(Path pathIn, Advancement advancementIn) {
		return pathIn.resolve("data/" + advancementIn.getId().getNamespace() + "/advancements/" + advancementIn.getId().getPath() + ".json");
	}
	
	private static String id(String s) {
		return ChaosAwakens.MODID + ":" + s;
	}
	
	public void register(Consumer<Advancement> t) {
		
		
		Advancement root = itemAdvancement("root", FrameType.TASK, CAItems.RUBY.get()).withCriterion("root",
				PositionTrigger.Instance.forLocation(LocationPredicate.forRegistryKey(RegistryKey.getOrCreateKey(Registry.WORLD_KEY, new ResourceLocation("overworld"))))).register(t, id("root"));
		
		// ARMOR
		// Cat's Eye Armor
		Advancement catsEyeArmor = itemAdvancement("cats_eye_armor", FrameType.TASK, CAItems.CATS_EYE_CHESTPLATE.get()).withParent(root).withCriterion("cats_eye_helmet",
				InventoryChangeTrigger.Instance.forItems(CAItems.CATS_EYE_HELMET.get(), CAItems.CATS_EYE_CHESTPLATE.get(), CAItems.CATS_EYE_LEGGINGS.get(), CAItems.CATS_EYE_BOOTS.get())).register(t, id("cats_eye_armor"));
		// Platinum Armor
		Advancement platinumArmor = itemAdvancement("platinum_armor", FrameType.TASK, CAItems.PLATINUM_CHESTPLATE.get()).withParent(catsEyeArmor).withCriterion("platinum_helmet",
				InventoryChangeTrigger.Instance.forItems(CAItems.PLATINUM_HELMET.get(), CAItems.PLATINUM_CHESTPLATE.get(), CAItems.PLATINUM_LEGGINGS.get(), CAItems.PLATINUM_BOOTS.get())).register(t, id("platinum_armor"));
		// Experience Armor
		Advancement experienceArmor = itemAdvancement("experience_armor", FrameType.TASK, CAItems.EXPERIENCE_CHESTPLATE.get()).withParent(platinumArmor).withCriterion("experience_helmet",
				InventoryChangeTrigger.Instance.forItems(CAItems.EXPERIENCE_HELMET.get(), CAItems.EXPERIENCE_CHESTPLATE.get(), CAItems.EXPERIENCE_LEGGINGS.get(), CAItems.EXPERIENCE_BOOTS.get())).register(t, id("experience_armor"));
		// Ruby Armor
		Advancement rubyArmor = itemAdvancement("ruby_armor", FrameType.TASK, CAItems.RUBY_CHESTPLATE.get()).withParent(experienceArmor).withCriterion("ruby_helmet",
				InventoryChangeTrigger.Instance.forItems(CAItems.RUBY_HELMET.get(), CAItems.RUBY_CHESTPLATE.get(), CAItems.RUBY_LEGGINGS.get(), CAItems.RUBY_BOOTS.get())).register(t, id("ruby_armor"));
		// Ultimate Armor
		Advancement ultimateArmor = itemAdvancement("ultimate_armor", FrameType.GOAL, CAItems.ULTIMATE_CHESTPLATE.get()).withParent(root).withCriterion("ultimate_helmet",
				InventoryChangeTrigger.Instance.forItems(CAItems.ULTIMATE_HELMET.get(), CAItems.ULTIMATE_CHESTPLATE.get(), CAItems.ULTIMATE_LEGGINGS.get(), CAItems.ULTIMATE_BOOTS.get())).register(t, id("ultimate_armor"));
		// ALL ARMOR
		Advancement allArmor = itemAdvancement("all_armor", FrameType.CHALLENGE, CAItems.TIGERS_EYE_CHESTPLATE.get()).withParent(ultimateArmor).withCriterion("copper_helmet",
				InventoryChangeTrigger.Instance.forItems(CAItems.COPPER_HELMET.get(), CAItems.COPPER_CHESTPLATE.get(), CAItems.COPPER_LEGGINGS.get(), CAItems.COPPER_BOOTS.get())).withCriterion("peacock_feather_helmet",
				InventoryChangeTrigger.Instance.forItems(CAItems.PEACOCK_FEATHER_HELMET.get(), CAItems.PEACOCK_FEATHER_CHESTPLATE.get(), CAItems.PEACOCK_FEATHER_LEGGINGS.get(), CAItems.PEACOCK_FEATHER_BOOTS.get())).withCriterion("tin_helmet",
				InventoryChangeTrigger.Instance.forItems(CAItems.TIN_HELMET.get(), CAItems.TIN_CHESTPLATE.get(), CAItems.TIN_LEGGINGS.get(), CAItems.TIN_BOOTS.get())).withCriterion("lava_eel_helmet",
				InventoryChangeTrigger.Instance.forItems(CAItems.LAVA_EEL_HELMET.get(), CAItems.LAVA_EEL_CHESTPLATE.get(), CAItems.LAVA_EEL_LEGGINGS.get(), CAItems.LAVA_EEL_BOOTS.get())).withCriterion("moth_scale_helmet",
				InventoryChangeTrigger.Instance.forItems(CAItems.MOTH_SCALE_HELMET.get(), CAItems.MOTH_SCALE_CHESTPLATE.get(), CAItems.MOTH_SCALE_LEGGINGS.get(), CAItems.MOTH_SCALE_BOOTS.get())).withCriterion("silver_helmet",
				InventoryChangeTrigger.Instance.forItems(CAItems.SILVER_HELMET.get(), CAItems.SILVER_CHESTPLATE.get(), CAItems.SILVER_LEGGINGS.get(), CAItems.SILVER_BOOTS.get())).withCriterion("pink_tourmaline_helmet",
				InventoryChangeTrigger.Instance.forItems(CAItems.PINK_TOURMALINE_HELMET.get(), CAItems.PINK_TOURMALINE_CHESTPLATE.get(), CAItems.PINK_TOURMALINE_LEGGINGS.get(), CAItems.PINK_TOURMALINE_BOOTS.get())).withCriterion("lapis_helmet",
				InventoryChangeTrigger.Instance.forItems(CAItems.LAPIS_HELMET.get(), CAItems.LAPIS_CHESTPLATE.get(), CAItems.LAPIS_LEGGINGS.get(), CAItems.LAPIS_BOOTS.get())).withCriterion("emerald_helmet",
				InventoryChangeTrigger.Instance.forItems(CAItems.EMERALD_HELMET.get(), CAItems.EMERALD_CHESTPLATE.get(), CAItems.EMERALD_LEGGINGS.get(), CAItems.EMERALD_BOOTS.get())).withCriterion("amethyst_helmet",
				InventoryChangeTrigger.Instance.forItems(CAItems.AMETHYST_HELMET.get(), CAItems.AMETHYST_CHESTPLATE.get(), CAItems.AMETHYST_LEGGINGS.get(), CAItems.AMETHYST_BOOTS.get())).withCriterion("cats_eye_helmet",
				InventoryChangeTrigger.Instance.forItems(CAItems.CATS_EYE_HELMET.get(), CAItems.CATS_EYE_CHESTPLATE.get(), CAItems.CATS_EYE_LEGGINGS.get(), CAItems.CATS_EYE_BOOTS.get())).withCriterion("tigers_eye_helmet",
				InventoryChangeTrigger.Instance.forItems(CAItems.TIGERS_EYE_HELMET.get(), CAItems.TIGERS_EYE_CHESTPLATE.get(), CAItems.TIGERS_EYE_LEGGINGS.get(), CAItems.TIGERS_EYE_BOOTS.get())).withCriterion("platinum_helmet",
				InventoryChangeTrigger.Instance.forItems(CAItems.PLATINUM_HELMET.get(), CAItems.PLATINUM_CHESTPLATE.get(), CAItems.PLATINUM_LEGGINGS.get(), CAItems.PLATINUM_BOOTS.get())).withCriterion("experience_helmet",
				InventoryChangeTrigger.Instance.forItems(CAItems.EXPERIENCE_HELMET.get(), CAItems.EXPERIENCE_CHESTPLATE.get(), CAItems.EXPERIENCE_LEGGINGS.get(), CAItems.EXPERIENCE_BOOTS.get())).withCriterion("ruby_helmet",
				InventoryChangeTrigger.Instance.forItems(CAItems.RUBY_HELMET.get(), CAItems.RUBY_CHESTPLATE.get(), CAItems.RUBY_LEGGINGS.get(), CAItems.RUBY_BOOTS.get())).withCriterion("ultimate_helmet",
				InventoryChangeTrigger.Instance.forItems(CAItems.ULTIMATE_HELMET.get(), CAItems.ULTIMATE_CHESTPLATE.get(), CAItems.ULTIMATE_LEGGINGS.get(), CAItems.ULTIMATE_BOOTS.get())).register(t, id("all_armor"));
		
		Advancement crystalDimension = itemAdvancement("crystal_dimension", FrameType.TASK, CABlocks.CRYSTAL_GRASS_BLOCK.get()).withParent(root).withCriterion("crystal_dimension", ChangeDimensionTrigger.Instance.toWorld(CADimensions.CRYSTAL_DIMENSION_LEGACY)).register(t, id("crystal_dimension"));
		
		Advancement miningDimension = itemAdvancement("mining_dimension", FrameType.TASK, CABlocks.URANIUM_ORE.get()).withParent(root).withCriterion("mining_dimension", ChangeDimensionTrigger.Instance.toWorld(CADimensions.MINING_DIMENSION)).register(t, id("mining_dimension"));
		
		Advancement villageDimension = itemAdvancement("village_dimension", FrameType.TASK, Blocks.OAK_LOG).withParent(root).withCriterion("village_dimension", ChangeDimensionTrigger.Instance.toWorld(CADimensions.VILLAGE_MANIA)).register(t, id("village_dimension"));
		
		Advancement roboSlayer = itemAdvancement("robo_slayer", FrameType.TASK, CAItems.RAY_GUN.get()).withParent(root).withCriterion("robo_sniper",
				KilledTrigger.Instance.playerKilledEntity(EntityPredicate.Builder.create().type(CAEntityTypes.ROBO_SNIPER.get()))).withCriterion("robo_warrior", KilledTrigger.Instance.playerKilledEntity(EntityPredicate.Builder.create().type(CAEntityTypes.ROBO_WARRIOR.get()))).register(t, id("robo_slayer"));
		
		Advancement bugSquasher = itemAdvancement("bug_squasher", FrameType.TASK, CAItems.DEAD_STINK_BUG.get()).withParent(root).withCriterion("hercules_beetle",
				KilledTrigger.Instance.playerKilledEntity(EntityPredicate.Builder.create().type(CAEntityTypes.HERCULES_BEETLE.get()))).withCriterion("ruby_bug",
				KilledTrigger.Instance.playerKilledEntity(EntityPredicate.Builder.create().type(CAEntityTypes.RUBY_BUG.get()))).withCriterion("stink_bug",
				KilledTrigger.Instance.playerKilledEntity(EntityPredicate.Builder.create().type(CAEntityTypes.STINK_BUG.get()))).register(t, id("bug_squasher"));
		
		Advancement trophyWorthy = itemAdvancement("trophy_worthy", FrameType.CHALLENGE, CABlocks.PLATINUM_BLOCK.get()).withParent(root).withCriterion("platinum_block",
				InventoryChangeTrigger.Instance.forItems(CABlocks.PLATINUM_BLOCK.get(), CABlocks.URANIUM_BLOCK.get(), CABlocks.TITANIUM_BLOCK.get())).register(t, id("trophy_worthy"));
		
		Advancement anAppleCowADay = itemAdvancement("an_apple_cow_a_day", FrameType.TASK, CAItems.APPLE_COW_SPAWN_EGG.get()).withParent(root).withCriterion("apple_cow",
				BredAnimalsTrigger.Instance.forAll(EntityPredicate.Builder.create().type(CAEntityTypes.APPLE_COW.get()).build(), EntityPredicate.Builder.create().type(CAEntityTypes.APPLE_COW.get()).build(), EntityPredicate.ANY)).register(t, id("an_apple_cow_a_day"));
		
		Advancement shinyCows = itemAdvancement("shiny_cows", FrameType.TASK, CAItems.GOLDEN_APPLE_COW_SPAWN_EGG.get()).withParent(anAppleCowADay).withCriterion("golden_apple_cow",
				BredAnimalsTrigger.Instance.forAll(EntityPredicate.Builder.create().type(CAEntityTypes.GOLDEN_APPLE_COW.get()).build(), EntityPredicate.Builder.create().type(CAEntityTypes.GOLDEN_APPLE_COW.get()).build(), EntityPredicate.ANY)).register(t, id("shiny_cows"));
		
		Advancement entDestroyer = itemAdvancement("ent_destroyer", FrameType.TASK, Blocks.OAK_LEAVES).withParent(root).withCriterion("ent",
				KilledTrigger.Instance.playerKilledEntity(EntityPredicate.Builder.create().type(CAEntityTypes.ENT.get()))).register(t, id("ent_destroyer"));
	}
	
	private Advancement.Builder itemAdvancement(String name, FrameType type, IItemProvider... items) {
		Validate.isTrue(items.length > 0);
		return Advancement.Builder.builder().withDisplay(items[0], new TranslationTextComponent("advancements.chaosawakens." + name + ".title"),
				new TranslationTextComponent("advancements.chaosawakens." + name + ".description"), BACKGROUND_TEXTURE, type, true, true, false);
	}
}