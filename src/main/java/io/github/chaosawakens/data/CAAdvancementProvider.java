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
import net.minecraft.advancements.IRequirementsStrategy;
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

import javax.annotation.Nonnull;
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
    public String getName() {
        return ChaosAwakens.MODNAME + ": Advancements";
    }

    private static Path getPath(Path pathIn, Advancement advancementIn) {
        return pathIn.resolve("data/" + advancementIn.getId().getNamespace() + "/advancements/" + advancementIn.getId().getPath() + ".json");
    }

    private static String id(String s) {
        return ChaosAwakens.MODID + ":" + s;
    }

    @Override
    public void run(DirectoryCache cache) {
        Path path = this.generator.getOutputFolder();
        Set<ResourceLocation> set = Sets.newHashSet();
        Consumer<Advancement> consumer = (advancement) -> {
            if (!set.add(advancement.getId())) {
                throw new IllegalStateException("Duplicate advancement " + advancement.getId());
            } else {
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

    public void register(Consumer<Advancement> t) {
        Advancement root = registerAdvancement("root", FrameType.TASK, CAItems.RUBY.get()).addCriterion("root",
                PositionTrigger.Instance.located(LocationPredicate.inDimension(RegistryKey.create(Registry.DIMENSION_REGISTRY, new ResourceLocation("overworld"))))).save(t, id("root"));

        // ARMOR
        // Cat's Eye Armor
        Advancement catsEyeArmor = registerAdvancement("cats_eye_armor", FrameType.TASK, CAItems.CATS_EYE_CHESTPLATE.get()).parent(root).addCriterion("cats_eye_armor",
                InventoryChangeTrigger.Instance.hasItems(CAItems.CATS_EYE_HELMET.get(), CAItems.CATS_EYE_CHESTPLATE.get(), CAItems.CATS_EYE_LEGGINGS.get(), CAItems.CATS_EYE_BOOTS.get())).save(t, id("cats_eye_armor"));
        // Platinum Armor
        Advancement platinumArmor = registerAdvancement("platinum_armor", FrameType.TASK, CAItems.PLATINUM_CHESTPLATE.get()).parent(catsEyeArmor).addCriterion("platinum_armor",
                InventoryChangeTrigger.Instance.hasItems(CAItems.PLATINUM_HELMET.get(), CAItems.PLATINUM_CHESTPLATE.get(), CAItems.PLATINUM_LEGGINGS.get(), CAItems.PLATINUM_BOOTS.get())).save(t, id("platinum_armor"));
        // Experience Armor
        Advancement experienceArmor = registerAdvancement("experience_armor", FrameType.TASK, CAItems.EXPERIENCE_CHESTPLATE.get()).parent(platinumArmor).addCriterion("experience_armor",
                InventoryChangeTrigger.Instance.hasItems(CAItems.EXPERIENCE_HELMET.get(), CAItems.EXPERIENCE_CHESTPLATE.get(), CAItems.EXPERIENCE_LEGGINGS.get(), CAItems.EXPERIENCE_BOOTS.get())).save(t, id("experience_armor"));
        // Ruby Armor
        Advancement rubyArmor = registerAdvancement("ruby_armor", FrameType.TASK, CAItems.RUBY_CHESTPLATE.get()).parent(experienceArmor).addCriterion("ruby_armor",
                InventoryChangeTrigger.Instance.hasItems(CAItems.RUBY_HELMET.get(), CAItems.RUBY_CHESTPLATE.get(), CAItems.RUBY_LEGGINGS.get(), CAItems.RUBY_BOOTS.get())).save(t, id("ruby_armor"));
        // Ultimate Armor
        Advancement ultimateArmor = registerAdvancement("ultimate_armor", FrameType.GOAL, CAItems.ULTIMATE_CHESTPLATE.get()).parent(root).addCriterion("ultimate_armor",
                InventoryChangeTrigger.Instance.hasItems(CAItems.ULTIMATE_HELMET.get(), CAItems.ULTIMATE_CHESTPLATE.get(), CAItems.ULTIMATE_LEGGINGS.get(), CAItems.ULTIMATE_BOOTS.get())).save(t, id("ultimate_armor"));
        // ALL ARMOR
        Advancement allArmor = registerAdvancement("all_armor", FrameType.CHALLENGE, CAItems.TIGERS_EYE_CHESTPLATE.get()).parent(ultimateArmor).addCriterion("copper_armor",
                InventoryChangeTrigger.Instance.hasItems(CAItems.COPPER_HELMET.get(), CAItems.COPPER_CHESTPLATE.get(), CAItems.COPPER_LEGGINGS.get(), CAItems.COPPER_BOOTS.get())).addCriterion("peacock_feather_armor",
                InventoryChangeTrigger.Instance.hasItems(CAItems.PEACOCK_FEATHER_HELMET.get(), CAItems.PEACOCK_FEATHER_CHESTPLATE.get(), CAItems.PEACOCK_FEATHER_LEGGINGS.get(), CAItems.PEACOCK_FEATHER_BOOTS.get())).addCriterion("tin_armor",
                InventoryChangeTrigger.Instance.hasItems(CAItems.TIN_HELMET.get(), CAItems.TIN_CHESTPLATE.get(), CAItems.TIN_LEGGINGS.get(), CAItems.TIN_BOOTS.get())).addCriterion("lava_eel_armor",
                InventoryChangeTrigger.Instance.hasItems(CAItems.LAVA_EEL_HELMET.get(), CAItems.LAVA_EEL_CHESTPLATE.get(), CAItems.LAVA_EEL_LEGGINGS.get(), CAItems.LAVA_EEL_BOOTS.get()))/*/.addCriterion("moth_scale_armor",
                InventoryChangeTrigger.Instance.hasItems(CAItems.MOTH_SCALE_HELMET.get(), CAItems.MOTH_SCALE_CHESTPLATE.get(), CAItems.MOTH_SCALE_LEGGINGS.get(), CAItems.MOTH_SCALE_BOOTS.get()))/*/.addCriterion("silver_armor",
                InventoryChangeTrigger.Instance.hasItems(CAItems.SILVER_HELMET.get(), CAItems.SILVER_CHESTPLATE.get(), CAItems.SILVER_LEGGINGS.get(), CAItems.SILVER_BOOTS.get())).addCriterion("pink_tourmaline_armor",
                InventoryChangeTrigger.Instance.hasItems(CAItems.PINK_TOURMALINE_HELMET.get(), CAItems.PINK_TOURMALINE_CHESTPLATE.get(), CAItems.PINK_TOURMALINE_LEGGINGS.get(), CAItems.PINK_TOURMALINE_BOOTS.get())).addCriterion("lapis_armor",
                InventoryChangeTrigger.Instance.hasItems(CAItems.LAPIS_HELMET.get(), CAItems.LAPIS_CHESTPLATE.get(), CAItems.LAPIS_LEGGINGS.get(), CAItems.LAPIS_BOOTS.get())).addCriterion("emerald_armor",
                InventoryChangeTrigger.Instance.hasItems(CAItems.EMERALD_HELMET.get(), CAItems.EMERALD_CHESTPLATE.get(), CAItems.EMERALD_LEGGINGS.get(), CAItems.EMERALD_BOOTS.get())).addCriterion("amethyst_armor",
                InventoryChangeTrigger.Instance.hasItems(CAItems.AMETHYST_HELMET.get(), CAItems.AMETHYST_CHESTPLATE.get(), CAItems.AMETHYST_LEGGINGS.get(), CAItems.AMETHYST_BOOTS.get())).addCriterion("cats_eye_armor",
                InventoryChangeTrigger.Instance.hasItems(CAItems.CATS_EYE_HELMET.get(), CAItems.CATS_EYE_CHESTPLATE.get(), CAItems.CATS_EYE_LEGGINGS.get(), CAItems.CATS_EYE_BOOTS.get())).addCriterion("tigers_eye_armor",
                InventoryChangeTrigger.Instance.hasItems(CAItems.TIGERS_EYE_HELMET.get(), CAItems.TIGERS_EYE_CHESTPLATE.get(), CAItems.TIGERS_EYE_LEGGINGS.get(), CAItems.TIGERS_EYE_BOOTS.get())).addCriterion("platinum_armor",
                InventoryChangeTrigger.Instance.hasItems(CAItems.PLATINUM_HELMET.get(), CAItems.PLATINUM_CHESTPLATE.get(), CAItems.PLATINUM_LEGGINGS.get(), CAItems.PLATINUM_BOOTS.get())).addCriterion("experience_armor",
                InventoryChangeTrigger.Instance.hasItems(CAItems.EXPERIENCE_HELMET.get(), CAItems.EXPERIENCE_CHESTPLATE.get(), CAItems.EXPERIENCE_LEGGINGS.get(), CAItems.EXPERIENCE_BOOTS.get())).addCriterion("ruby_armor",
                InventoryChangeTrigger.Instance.hasItems(CAItems.RUBY_HELMET.get(), CAItems.RUBY_CHESTPLATE.get(), CAItems.RUBY_LEGGINGS.get(), CAItems.RUBY_BOOTS.get())).addCriterion("ultimate_armor",
                InventoryChangeTrigger.Instance.hasItems(CAItems.ULTIMATE_HELMET.get(), CAItems.ULTIMATE_CHESTPLATE.get(), CAItems.ULTIMATE_LEGGINGS.get(), CAItems.ULTIMATE_BOOTS.get())).save(t, id("all_armor"));


        Advancement crystalWorld = registerAdvancement("crystal_world", FrameType.TASK, CABlocks.CRYSTAL_GRASS_BLOCK.get()).parent(root).addCriterion("crystal_world",
                ChangeDimensionTrigger.Instance.changedDimensionTo(CADimensions.CRYSTAL_WORLD)).save(t, id("crystal_world"));

        Advancement miningParadise = registerAdvancement("mining_paradise", FrameType.TASK, CABlocks.URANIUM_ORE.get()).parent(root).addCriterion("mining_paradise",
                ChangeDimensionTrigger.Instance.changedDimensionTo(CADimensions.MINING_PARADISE)).save(t, id("mining_paradise"));

        Advancement villageMania = registerAdvancement("village_mania", FrameType.TASK, Blocks.OAK_LOG).parent(root).addCriterion("village_mania",
                ChangeDimensionTrigger.Instance.changedDimensionTo(CADimensions.VILLAGE_MANIA)).save(t, id("village_mania"));

        Advancement roboSlayer = registerAdvancement("robo_slayer", FrameType.GOAL, CAItems.RAY_GUN.get()).parent(root).addCriterion("robo_pounder", 
        		KilledTrigger.Instance.playerKilledEntity(EntityPredicate.Builder.entity().of(CAEntityTypes.ROBO_POUNDER.get()))).addCriterion("robo_sniper",
                KilledTrigger.Instance.playerKilledEntity(EntityPredicate.Builder.entity().of(CAEntityTypes.ROBO_SNIPER.get()))).addCriterion("robo_warrior",
                KilledTrigger.Instance.playerKilledEntity(EntityPredicate.Builder.entity().of(CAEntityTypes.ROBO_WARRIOR.get()))).save(t, id("robo_slayer"));

        Advancement bugSquasher = registerAdvancement("bug_squasher", FrameType.TASK, CAItems.DEAD_STINK_BUG.get()).parent(root).addCriterion("hercules_beetle",
                KilledTrigger.Instance.playerKilledEntity(EntityPredicate.Builder.entity().of(CAEntityTypes.HERCULES_BEETLE.get()))).addCriterion("ruby_bug",
                KilledTrigger.Instance.playerKilledEntity(EntityPredicate.Builder.entity().of(CAEntityTypes.RUBY_BUG.get()))).addCriterion("stink_bug",
                KilledTrigger.Instance.playerKilledEntity(EntityPredicate.Builder.entity().of(CAEntityTypes.STINK_BUG.get()))).save(t, id("bug_squasher"));

        Advancement goFish = registerAdvancement("go_fish", FrameType.TASK, CAItems.ROCK_FISH.get()).parent(root).addCriterion("green_fish",
                KilledTrigger.Instance.playerKilledEntity(EntityPredicate.Builder.entity().of(CAEntityTypes.GREEN_FISH.get()))).addCriterion("rock_fish",
                KilledTrigger.Instance.playerKilledEntity(EntityPredicate.Builder.entity().of(CAEntityTypes.ROCK_FISH.get()))).addCriterion("spark_fish",
                KilledTrigger.Instance.playerKilledEntity(EntityPredicate.Builder.entity().of(CAEntityTypes.SPARK_FISH.get()))).addCriterion("wood_fish",
                KilledTrigger.Instance.playerKilledEntity(EntityPredicate.Builder.entity().of(CAEntityTypes.WOOD_FISH.get()))).save(t, id("go_fish"));

        Advancement entDestroyer = registerAdvancement("ent_destroyer", FrameType.GOAL, Blocks.OAK_LEAVES).parent(root).addCriterion("acacia_ent",
                KilledTrigger.Instance.playerKilledEntity(EntityPredicate.Builder.entity().of(CAEntityTypes.ACACIA_ENT.get()))).addCriterion("birch_ent",
                KilledTrigger.Instance.playerKilledEntity(EntityPredicate.Builder.entity().of(CAEntityTypes.BIRCH_ENT.get()))).addCriterion("crimson_ent",
                KilledTrigger.Instance.playerKilledEntity(EntityPredicate.Builder.entity().of(CAEntityTypes.CRIMSON_ENT.get()))).addCriterion("dark_oak_ent",
                KilledTrigger.Instance.playerKilledEntity(EntityPredicate.Builder.entity().of(CAEntityTypes.DARK_OAK_ENT.get()))).addCriterion("jungle_ent",
                KilledTrigger.Instance.playerKilledEntity(EntityPredicate.Builder.entity().of(CAEntityTypes.JUNGLE_ENT.get()))).addCriterion("oak_ent",
                KilledTrigger.Instance.playerKilledEntity(EntityPredicate.Builder.entity().of(CAEntityTypes.OAK_ENT.get()))).addCriterion("spruce_ent",
                KilledTrigger.Instance.playerKilledEntity(EntityPredicate.Builder.entity().of(CAEntityTypes.SPRUCE_ENT.get()))).addCriterion("warped_ent",
                KilledTrigger.Instance.playerKilledEntity(EntityPredicate.Builder.entity().of(CAEntityTypes.WARPED_ENT.get()))).save(t, id("ent_destroyer"));

        Advancement trophyWorthy = registerAdvancement("trophy_worthy", FrameType.CHALLENGE, CABlocks.PLATINUM_BLOCK.get()).parent(root).addCriterion("platinum_block",
                InventoryChangeTrigger.Instance.hasItems(CABlocks.PLATINUM_BLOCK.get(), CABlocks.URANIUM_BLOCK.get(), CABlocks.TITANIUM_BLOCK.get())).save(t, id("trophy_worthy"));

        Advancement anAppleCowADay = registerAdvancement("an_apple_cow_a_day", FrameType.TASK, CAItems.APPLE_COW_SPAWN_EGG.get()).parent(root).addCriterion("apple_cow",
                BredAnimalsTrigger.Instance.bredAnimals(EntityPredicate.Builder.entity().of(CAEntityTypes.APPLE_COW.get()).build(), EntityPredicate.Builder.entity().of(CAEntityTypes.APPLE_COW.get()).build(), EntityPredicate.ANY)).save(t, id("an_apple_cow_a_day"));

        Advancement shinyCows = registerAdvancement("shiny_cows", FrameType.TASK, CAItems.GOLDEN_APPLE_COW_SPAWN_EGG.get()).parent(anAppleCowADay).addCriterion("golden_apple_cow",
                BredAnimalsTrigger.Instance.bredAnimals(EntityPredicate.Builder.entity().of(CAEntityTypes.GOLDEN_APPLE_COW.get()).build(), EntityPredicate.Builder.entity().of(CAEntityTypes.GOLDEN_APPLE_COW.get()).build(), EntityPredicate.ANY)).addCriterion("enchanted_golden_apple_cow",
                BredAnimalsTrigger.Instance.bredAnimals(EntityPredicate.Builder.entity().of(CAEntityTypes.ENCHANTED_GOLDEN_APPLE_COW.get()).build(), EntityPredicate.Builder.entity().of(CAEntityTypes.ENCHANTED_GOLDEN_APPLE_COW.get()).build(), EntityPredicate.ANY)).requirements(IRequirementsStrategy.OR).save(t, id("shiny_cows"));

        Advancement carrotVision = registerAdvancement("carrot_vision", FrameType.TASK, CAItems.CARROT_PIG_SPAWN_EGG.get()).parent(root).addCriterion("carrot_pig",
                BredAnimalsTrigger.Instance.bredAnimals(EntityPredicate.Builder.entity().of(CAEntityTypes.CARROT_PIG.get()).build(), EntityPredicate.Builder.entity().of(CAEntityTypes.CARROT_PIG.get()).build(), EntityPredicate.ANY)).save(t, id("carrot_vision"));

        Advancement shinyPigs = registerAdvancement("shiny_pigs", FrameType.TASK, CAItems.GOLDEN_CARROT_PIG_SPAWN_EGG.get()).parent(carrotVision).addCriterion("golden_carrot_pig",
                BredAnimalsTrigger.Instance.bredAnimals(EntityPredicate.Builder.entity().of(CAEntityTypes.GOLDEN_CARROT_PIG.get()).build(), EntityPredicate.Builder.entity().of(CAEntityTypes.GOLDEN_CARROT_PIG.get()).build(), EntityPredicate.ANY)).addCriterion("enchanted_golden_carrot_pig",
                BredAnimalsTrigger.Instance.bredAnimals(EntityPredicate.Builder.entity().of(CAEntityTypes.ENCHANTED_GOLDEN_CARROT_PIG.get()).build(), EntityPredicate.Builder.entity().of(CAEntityTypes.ENCHANTED_GOLDEN_CARROT_PIG.get()).build(), EntityPredicate.ANY)).requirements(IRequirementsStrategy.OR).save(t, id("shiny_pigs"));
        
        Advancement chaoticByChoice = registerAdvancement("chaotic_by_choice", FrameType.GOAL, CABlocks.DEFOSSILIZER.get()).parent(root).addCriterion("chaotic_by_choice",
                InventoryChangeTrigger.Instance.hasItems(CABlocks.DEFOSSILIZER.get())).save(t, id("chaotic_by_choice"));
        
        // SECRET
        // Big Bertha
        Advancement bigBertha = registerSecretAdvancement("big_bertha", FrameType.CHALLENGE, CAItems.BIG_BERTHA.get()).parent(root).addCriterion("big_bertha",
                InventoryChangeTrigger.Instance.hasItems(CAItems.BIG_BERTHA.get())).save(t, id("big_bertha"));
        // Mobzilla Scale Armor
        Advancement mobzillaScaleArmor = registerSecretAdvancement("mobzilla_scale_armor", FrameType.CHALLENGE, CAItems.MOBZILLA_SCALE_CHESTPLATE.get()).parent(bigBertha).addCriterion("mobzilla_scale_armor",
                InventoryChangeTrigger.Instance.hasItems(CAItems.MOBZILLA_SCALE_HELMET.get(), CAItems.MOBZILLA_SCALE_CHESTPLATE.get(), CAItems.MOBZILLA_SCALE_LEGGINGS.get(), CAItems.MOBZILLA_SCALE_BOOTS.get())).save(t, id("mobzilla_scale_armor"));
        // Royal Guardian Armor
        Advancement royalGuardianArmor = registerSecretAdvancement("royal_guardian_armor", FrameType.CHALLENGE, CAItems.ROYAL_GUARDIAN_CHESTPLATE.get()).parent(mobzillaScaleArmor).addCriterion("royal_guardian_armor",
                InventoryChangeTrigger.Instance.hasItems(CAItems.ROYAL_GUARDIAN_HELMET.get(), CAItems.ROYAL_GUARDIAN_CHESTPLATE.get(), CAItems.ROYAL_GUARDIAN_LEGGINGS.get(), CAItems.ROYAL_GUARDIAN_BOOTS.get())).save(t, id("royal_guardian_armor"));
        // Queen Scale Armor
        Advancement queenScaleArmor = registerSecretAdvancement("queen_scale_armor", FrameType.CHALLENGE, CAItems.QUEEN_SCALE_CHESTPLATE.get()).parent(royalGuardianArmor).addCriterion("queen_scale_armor",
                InventoryChangeTrigger.Instance.hasItems(CAItems.QUEEN_SCALE_HELMET.get(), CAItems.QUEEN_SCALE_CHESTPLATE.get(), CAItems.QUEEN_SCALE_LEGGINGS.get(), CAItems.QUEEN_SCALE_BOOTS.get())).save(t, id("queen_scale_armor"));
    }

    private Advancement.Builder registerAdvancement(String name, FrameType type, IItemProvider... items) {
        Validate.isTrue(items.length > 0);
        return Advancement.Builder.advancement().display(items[0], new TranslationTextComponent("advancements.chaosawakens." + name + ".title"),
                new TranslationTextComponent("advancements.chaosawakens." + name + ".description"), BACKGROUND_TEXTURE, type, true, true, false);
    }

    private Advancement.Builder registerSecretAdvancement(String name, FrameType type, IItemProvider... items) {
        Validate.isTrue(items.length > 0);
        return Advancement.Builder.advancement().display(items[0], new TranslationTextComponent("advancements.chaosawakens." + name + ".title"),
                new TranslationTextComponent("advancements.chaosawakens." + name + ".description"), BACKGROUND_TEXTURE, type, true, true, true);
    }
}
