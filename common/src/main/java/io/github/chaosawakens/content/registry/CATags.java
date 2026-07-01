package io.github.chaosawakens.content.registry;

import com.mememan.nexus.asm.annotations.RegistrarEntry;
import com.mememan.nexus.template.property_wrapper.TagPropertyWrapperTemplates;
import io.github.chaosawakens.CAConstants;
import it.unimi.dsi.fastutil.objects.ObjectArrayList;
import net.minecraft.core.registries.Registries;
import net.minecraft.tags.BlockTags;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;

import java.util.Map;
import java.util.function.Supplier;

public final class CATags {

    @RegistrarEntry
    public static final class CABlockTags {
        // Wood
        public static final Supplier<TagKey<Block>> APPLE_LOGS = TagPropertyWrapperTemplates.registerTagKey(Registries.BLOCK, CAConstants.prefix("wood/apple_logs"));
        public static final Supplier<TagKey<Block>> CRYSTALWOOD_LOGS = TagPropertyWrapperTemplates.registerTagKey(Registries.BLOCK, CAConstants.prefix("wood/crystalwood_logs"));
        public static final Supplier<TagKey<Block>> DENSEWOOD_LOGS = TagPropertyWrapperTemplates.registerTagKey(Registries.BLOCK, CAConstants.prefix("wood/densewood_logs"));
        public static final Supplier<TagKey<Block>> DUPLICATOR_LOGS = TagPropertyWrapperTemplates.registerTagKey(Registries.BLOCK, CAConstants.prefix("wood/duplicator_logs"));
        public static final Supplier<TagKey<Block>> GINKGO_LOGS = TagPropertyWrapperTemplates.registerTagKey(Registries.BLOCK, CAConstants.prefix("wood/ginkgo_logs"));
        public static final Supplier<TagKey<Block>> MESOZOIC_LOGS = TagPropertyWrapperTemplates.registerTagKey(Registries.BLOCK, CAConstants.prefix("wood/mesozoic_logs"));
        public static final Supplier<TagKey<Block>> PEACH_LOGS = TagPropertyWrapperTemplates.registerTagKey(Registries.BLOCK, CAConstants.prefix("wood/peach_logs"));
        public static final Supplier<TagKey<Block>> SKYWOOD_LOGS = TagPropertyWrapperTemplates.registerTagKey(Registries.BLOCK, CAConstants.prefix("wood/skywood_logs"));

        // Fossils
        public static final Supplier<TagKey<Block>> DEEPSLATE_FOSSILS = TagPropertyWrapperTemplates.registerTagKey(Registries.BLOCK, CAConstants.prefix("fossil/overworld/deepslate_fossils"));
        public static final Supplier<TagKey<Block>> FROZEN_FOSSILS = TagPropertyWrapperTemplates.registerTagKey(Registries.BLOCK, CAConstants.prefix("fossil/overworld/frozen_fossils"));
        public static final Supplier<TagKey<Block>> GRAVEL_FOSSILS = TagPropertyWrapperTemplates.registerTagKey(Registries.BLOCK, CAConstants.prefix("fossil/overworld/gravel_fossils"));
        public static final Supplier<TagKey<Block>> SAND_FOSSILS = TagPropertyWrapperTemplates.registerTagKey(Registries.BLOCK, CAConstants.prefix("fossil/overworld/sand_fossils"));
        public static final Supplier<TagKey<Block>> SANDSTONE_FOSSILS = TagPropertyWrapperTemplates.registerTagKey(Registries.BLOCK, CAConstants.prefix("fossil/overworld/sandstone_fossils"));
        public static final Supplier<TagKey<Block>> STONE_FOSSILS = TagPropertyWrapperTemplates.registerTagKey(Registries.BLOCK, CAConstants.prefix("fossil/overworld/stone_fossils"));

        public static final Supplier<TagKey<Block>> OVERWORLD_FOSSILS = TagPropertyWrapperTemplates.registerAndChain(Registries.BLOCK, CAConstants.prefix("fossil/overworld_fossils"))
                .withChildTags(DEEPSLATE_FOSSILS, FROZEN_FOSSILS, GRAVEL_FOSSILS, SAND_FOSSILS, SANDSTONE_FOSSILS, STONE_FOSSILS)
                .buildAndGet();

        public static final Supplier<TagKey<Block>> BLACKSTONE_FOSSILS = TagPropertyWrapperTemplates.registerTagKey(Registries.BLOCK, CAConstants.prefix("fossil/nether/blackstone_fossils"));
        public static final Supplier<TagKey<Block>> NETHERRACK_FOSSILS = TagPropertyWrapperTemplates.registerTagKey(Registries.BLOCK, CAConstants.prefix("fossil/nether/netherrack_fossils"));
        public static final Supplier<TagKey<Block>> SOUL_SOIL_FOSSILS = TagPropertyWrapperTemplates.registerTagKey(Registries.BLOCK, CAConstants.prefix("fossil/nether/soul_soil_fossils"));

        public static final Supplier<TagKey<Block>> NETHER_FOSSILS = TagPropertyWrapperTemplates.registerAndChain(Registries.BLOCK, CAConstants.prefix("fossil/nether_fossils"))
                .withChildTags(BLACKSTONE_FOSSILS, NETHERRACK_FOSSILS, SOUL_SOIL_FOSSILS)
                .buildAndGet();

        public static final Supplier<TagKey<Block>> END_STONE_FOSSILS = TagPropertyWrapperTemplates.registerTagKey(Registries.BLOCK, CAConstants.prefix("fossil/end/end_stone_fossils"));

        public static final Supplier<TagKey<Block>> END_FOSSILS = TagPropertyWrapperTemplates.registerAndChain(Registries.BLOCK, CAConstants.prefix("fossil/end_fossils"))
                .withChildTag(END_STONE_FOSSILS)
                .buildAndGet();

        public static final Supplier<TagKey<Block>> VANILLA_FOSSILS = TagPropertyWrapperTemplates.registerAndChain(Registries.BLOCK, CAConstants.prefix("fossil/vanilla_fossils"))
                .withChildTags(OVERWORLD_FOSSILS, NETHER_FOSSILS, END_FOSSILS)
                .buildAndGet();

        public static final Supplier<TagKey<Block>> DREDGESTONE_FOSSILS = TagPropertyWrapperTemplates.registerTagKey(Registries.BLOCK, CAConstants.prefix("fossil/mining_paradise/dredgestone_fossils"));
        public static final Supplier<TagKey<Block>> GLOOMSTONE_FOSSILS = TagPropertyWrapperTemplates.registerTagKey(Registries.BLOCK, CAConstants.prefix("fossil/mining_paradise/gloomstone_fossils"));

        public static final Supplier<TagKey<Block>> MINING_PARADISE_FOSSILS = TagPropertyWrapperTemplates.registerAndChain(Registries.BLOCK, CAConstants.prefix("fossil/mining_paradise_fossils"))
                .withChildTags(DREDGESTONE_FOSSILS, GLOOMSTONE_FOSSILS)
                .buildAndGet();

        public static final Supplier<TagKey<Block>> KYANITE_FOSSILS = TagPropertyWrapperTemplates.registerTagKey(Registries.BLOCK, CAConstants.prefix("fossil/crystalworld/kyanite_fossils"));

        public static final Supplier<TagKey<Block>> CRYSTALWORLD_FOSSILS = TagPropertyWrapperTemplates.registerAndChain(Registries.BLOCK, CAConstants.prefix("fossil/crystalworld_fossils"))
                .withChildTag(KYANITE_FOSSILS)
                .buildAndGet();

        public static final Supplier<TagKey<Block>> CHAOSAWAKENS_FOSSILS = TagPropertyWrapperTemplates.registerAndChain(Registries.BLOCK, CAConstants.prefix("fossil/chaosawakens_fossils"))
                .withChildTags(MINING_PARADISE_FOSSILS, CRYSTALWORLD_FOSSILS)
                .buildAndGet();

        public static final Supplier<TagKey<Block>> FOSSILS = TagPropertyWrapperTemplates.registerAndChain(Registries.BLOCK, CAConstants.prefix("fossil/fossils"))
                .withChildTags(VANILLA_FOSSILS, CHAOSAWAKENS_FOSSILS)
                .buildAndGet();

        // Vegetation (Generic)
        public static final Supplier<TagKey<Block>> LEAF_CARPETS = TagPropertyWrapperTemplates.registerTagKey(Registries.BLOCK, CAConstants.prefix("vegetation/leaf_carpet"));

        public static final Supplier<TagKey<Block>> FLOWER_BLOCKS = TagPropertyWrapperTemplates.registerTagKey(Registries.BLOCK, CAConstants.prefix("vegetation/flower_blocks"));

        public static final Supplier<TagKey<Block>> FARMLAND_BLOCKS = TagPropertyWrapperTemplates.registerAndChain(Registries.BLOCK, CAConstants.prefix("vegetation/farmland_blocks"))
                .withTaggedObject(() -> Blocks.FARMLAND)
                .buildAndGet();

        // Vegetation (Mining Paradise)
        public static final Supplier<TagKey<Block>> DENSE_SOIL = TagPropertyWrapperTemplates.registerTagKey(Registries.BLOCK, CAConstants.prefix("vegetation/mining_paradise/dense_soil"));

        public static final Supplier<TagKey<Block>> DENSE_FLOWERS = TagPropertyWrapperTemplates.registerTagKey(Registries.BLOCK, CAConstants.prefix("vegetation/mining_paradise/dense_flowers"));
        public static final Supplier<TagKey<Block>> DENSE_VEGETATION = TagPropertyWrapperTemplates.registerAndChain(Registries.BLOCK, CAConstants.prefix("vegetation/mining_paradise/dense_vegetation"))
                .withChildTag(DENSE_FLOWERS)
                .buildAndGet();

        // Vegetation (CrystalWorld)
        public static final Supplier<TagKey<Block>> CRYSTAL_SOIL = TagPropertyWrapperTemplates.registerTagKey(Registries.BLOCK, CAConstants.prefix("vegetation/crystalworld/crystal_soil"));

        public static final Supplier<TagKey<Block>> CRYSTAL_FLOWERS = TagPropertyWrapperTemplates.registerTagKey(Registries.BLOCK, CAConstants.prefix("vegetation/crystalworld/crystal_flowers"));
        public static final Supplier<TagKey<Block>> CRYSTAL_VEGETATION = TagPropertyWrapperTemplates.registerAndChain(Registries.BLOCK, CAConstants.prefix("vegetation/crystalworld/crystal_vegetation"))
                .withChildTag(CRYSTAL_FLOWERS)
                .buildAndGet();

        // Ores (Mining Paradise)
        public static final Supplier<TagKey<Block>> MINING_CARVER_REPLACEABLES = TagPropertyWrapperTemplates.registerTagKey(Registries.BLOCK, CAConstants.prefix("ore/mining_paradise/mining_carver_replaceables"));

        // Misc.
        public static final Supplier<TagKey<Block>> ROBO_BLOCKS = TagPropertyWrapperTemplates.registerTagKey(Registries.BLOCK, CAConstants.prefix("misc/robo_blocks"));
    }

    @RegistrarEntry(priority = -1)
    public static final class CABlockDependantTags {

        // Base Stone
        public static final Supplier<TagKey<Block>> BASE_STONE_CRYSTALWORLD = TagPropertyWrapperTemplates.registerAndChain(Registries.BLOCK, CAConstants.prefix("stone/base_stone_crystalworld"))
                .withTaggedObject(CABlocks.KYANITE)
                .buildAndGet();
        public static final Supplier<TagKey<Block>> BASE_STONE_MINING_PARADISE = TagPropertyWrapperTemplates.registerAndChain(Registries.BLOCK, CAConstants.prefix("stone/base_stone_mining_paradise"))
                .withTaggedObject((Supplier<Block>) CABlocks.DREDGESTONE.stoneBlockFamily().entrySet().stream().filter(curBlockEntry -> curBlockEntry.getKey().getPath().equals("dredgestone")).map(Map.Entry::getValue).findFirst().orElseThrow())
                .withTaggedObject((Supplier<Block>) CABlocks.GLOOMSTONE.stoneBlockFamily().entrySet().stream().filter(curBlockEntry -> curBlockEntry.getKey().getPath().equals("gloomstone")).map(Map.Entry::getValue).findFirst().orElseThrow())
                .buildAndGet();
        public static final Supplier<TagKey<Block>> BASE_STONE_VILLAGE_MANIA = TagPropertyWrapperTemplates.registerAndChain(Registries.BLOCK, CAConstants.prefix("stone/base_stone_village_mania"))
                .buildAndGet();

        // Misc.
        public static final Supplier<TagKey<Block>> MINERS_DREAM_MINABLE = TagPropertyWrapperTemplates.registerAndChain(Registries.BLOCK, CAConstants.prefix("miners_dream_minable"))
                .withChildTags(ObjectArrayList.of(BASE_STONE_CRYSTALWORLD, BASE_STONE_MINING_PARADISE, BASE_STONE_VILLAGE_MANIA, () -> BlockTags.BASE_STONE_OVERWORLD, () -> BlockTags.BASE_STONE_NETHER, () -> BlockTags.DIRT, () -> BlockTags.SAND, () -> BlockTags.ICE, () -> BlockTags.CAVE_VINES, () -> BlockTags.LEAVES, () -> BlockTags.CROPS, () -> BlockTags.FLOWERS))
                .withTaggedObjects(ObjectArrayList.of(() -> Blocks.GRAVEL, () -> Blocks.SUSPICIOUS_GRAVEL, () -> Blocks.CLAY, () -> Blocks.SANDSTONE, () -> Blocks.SMALL_DRIPLEAF, () -> Blocks.SOUL_SAND, () -> Blocks.MAGMA_BLOCK, () -> Blocks.SOUL_SOIL, () -> Blocks.BIG_DRIPLEAF, () -> Blocks.DRIPSTONE_BLOCK, () -> Blocks.POINTED_DRIPSTONE))
                .buildAndGet();
    }

    @RegistrarEntry
    public static final class CAItemTags {
        // Wood
        public static final Supplier<TagKey<Item>> APPLE_LOGS = TagPropertyWrapperTemplates.registerTagKey(Registries.ITEM, CAConstants.prefix("wood/apple_logs"));
        public static final Supplier<TagKey<Item>> CRYSTALWOOD_LOGS = TagPropertyWrapperTemplates.registerTagKey(Registries.ITEM, CAConstants.prefix("wood/crystalwood_logs"));
        public static final Supplier<TagKey<Item>> DENSEWOOD_LOGS = TagPropertyWrapperTemplates.registerTagKey(Registries.ITEM, CAConstants.prefix("wood/densewood_logs"));
        public static final Supplier<TagKey<Item>> DUPLICATOR_LOGS = TagPropertyWrapperTemplates.registerTagKey(Registries.ITEM, CAConstants.prefix("wood/duplicator_logs"));
        public static final Supplier<TagKey<Item>> GINKGO_LOGS = TagPropertyWrapperTemplates.registerTagKey(Registries.ITEM, CAConstants.prefix("wood/ginkgo_logs"));
        public static final Supplier<TagKey<Item>> MESOZOIC_LOGS = TagPropertyWrapperTemplates.registerTagKey(Registries.ITEM, CAConstants.prefix("wood/mesozoic_logs"));
        public static final Supplier<TagKey<Item>> PEACH_LOGS = TagPropertyWrapperTemplates.registerTagKey(Registries.ITEM, CAConstants.prefix("wood/peach_logs"));
        public static final Supplier<TagKey<Item>> SKYWOOD_LOGS = TagPropertyWrapperTemplates.registerTagKey(Registries.ITEM, CAConstants.prefix("wood/skywood_logs"));

        // Vegetation
        public static final Supplier<TagKey<Item>> LEAF_CARPETS = TagPropertyWrapperTemplates.registerTagKey(Registries.ITEM, CAConstants.prefix("vegetation/leaf_carpet"));

        public static final Supplier<TagKey<Item>> FLOWER_BLOCKS = TagPropertyWrapperTemplates.registerTagKey(Registries.ITEM, CAConstants.prefix("vegetation/flower_blocks"));

        // Fossils
        public static final Supplier<TagKey<Item>> DEEPSLATE_FOSSILS = TagPropertyWrapperTemplates.registerTagKey(Registries.ITEM, CAConstants.prefix("fossil/overworld/deepslate_fossils"));
        public static final Supplier<TagKey<Item>> FROZEN_FOSSILS = TagPropertyWrapperTemplates.registerTagKey(Registries.ITEM, CAConstants.prefix("fossil/overworld/frozen_fossils"));
        public static final Supplier<TagKey<Item>> GRAVEL_FOSSILS = TagPropertyWrapperTemplates.registerTagKey(Registries.ITEM, CAConstants.prefix("fossil/overworld/gravel_fossils"));
        public static final Supplier<TagKey<Item>> SAND_FOSSILS = TagPropertyWrapperTemplates.registerTagKey(Registries.ITEM, CAConstants.prefix("fossil/overworld/sand_fossils"));
        public static final Supplier<TagKey<Item>> SANDSTONE_FOSSILS = TagPropertyWrapperTemplates.registerTagKey(Registries.ITEM, CAConstants.prefix("fossil/overworld/sandstone_fossils"));
        public static final Supplier<TagKey<Item>> STONE_FOSSILS = TagPropertyWrapperTemplates.registerTagKey(Registries.ITEM, CAConstants.prefix("fossil/overworld/stone_fossils"));

        public static final Supplier<TagKey<Item>> OVERWORLD_FOSSILS = TagPropertyWrapperTemplates.registerAndChain(Registries.ITEM, CAConstants.prefix("fossil/overworld_fossils"))
                .withChildTags(DEEPSLATE_FOSSILS, FROZEN_FOSSILS, GRAVEL_FOSSILS, SAND_FOSSILS, SANDSTONE_FOSSILS, STONE_FOSSILS)
                .buildAndGet();

        public static final Supplier<TagKey<Item>> BLACKSTONE_FOSSILS = TagPropertyWrapperTemplates.registerTagKey(Registries.ITEM, CAConstants.prefix("fossil/nether/blackstone_fossils"));
        public static final Supplier<TagKey<Item>> NETHERRACK_FOSSILS = TagPropertyWrapperTemplates.registerTagKey(Registries.ITEM, CAConstants.prefix("fossil/nether/netherrack_fossils"));
        public static final Supplier<TagKey<Item>> SOUL_SOIL_FOSSILS = TagPropertyWrapperTemplates.registerTagKey(Registries.ITEM, CAConstants.prefix("fossil/nether/soul_soil_fossils"));

        public static final Supplier<TagKey<Item>> NETHER_FOSSILS = TagPropertyWrapperTemplates.registerAndChain(Registries.ITEM, CAConstants.prefix("fossil/nether_fossils"))
                .withChildTags(BLACKSTONE_FOSSILS, NETHERRACK_FOSSILS, SOUL_SOIL_FOSSILS)
                .buildAndGet();

        public static final Supplier<TagKey<Item>> END_STONE_FOSSILS = TagPropertyWrapperTemplates.registerTagKey(Registries.ITEM, CAConstants.prefix("fossil/end/end_stone_fossils"));

        public static final Supplier<TagKey<Item>> END_FOSSILS = TagPropertyWrapperTemplates.registerAndChain(Registries.ITEM, CAConstants.prefix("fossil/end_fossils"))
                .withChildTag(END_STONE_FOSSILS)
                .buildAndGet();

        public static final Supplier<TagKey<Item>> VANILLA_FOSSILS = TagPropertyWrapperTemplates.registerAndChain(Registries.ITEM, CAConstants.prefix("fossil/vanilla_fossils"))
                .withChildTags(OVERWORLD_FOSSILS, NETHER_FOSSILS, END_FOSSILS)
                .buildAndGet();

        public static final Supplier<TagKey<Item>> DREDGESTONE_FOSSILS = TagPropertyWrapperTemplates.registerTagKey(Registries.ITEM, CAConstants.prefix("fossil/mining_paradise/dredgestone_fossils"));
        public static final Supplier<TagKey<Item>> GLOOMSTONE_FOSSILS = TagPropertyWrapperTemplates.registerTagKey(Registries.ITEM, CAConstants.prefix("fossil/mining_paradise/gloomstone_fossils"));

        public static final Supplier<TagKey<Item>> MINING_PARADISE_FOSSILS = TagPropertyWrapperTemplates.registerAndChain(Registries.ITEM, CAConstants.prefix("fossil/mining_paradise_fossils"))
                .withChildTags(DREDGESTONE_FOSSILS, GLOOMSTONE_FOSSILS)
                .buildAndGet();

        public static final Supplier<TagKey<Item>> KYANITE_FOSSILS = TagPropertyWrapperTemplates.registerTagKey(Registries.ITEM, CAConstants.prefix("fossil/crystalworld/kyanite_fossils"));

        public static final Supplier<TagKey<Item>> CRYSTALWORLD_FOSSILS = TagPropertyWrapperTemplates.registerAndChain(Registries.ITEM, CAConstants.prefix("fossil/crystalworld_fossils"))
                .withChildTag(KYANITE_FOSSILS)
                .buildAndGet();

        public static final Supplier<TagKey<Item>> CHAOSAWAKENS_FOSSILS = TagPropertyWrapperTemplates.registerAndChain(Registries.ITEM, CAConstants.prefix("fossil/chaosawakens_fossils"))
                .withChildTags(MINING_PARADISE_FOSSILS, CRYSTALWORLD_FOSSILS)
                .buildAndGet();

        public static final Supplier<TagKey<Item>> FOSSILS = TagPropertyWrapperTemplates.registerAndChain(Registries.ITEM, CAConstants.prefix("fossil/fossils"))
                .withChildTags(VANILLA_FOSSILS, CHAOSAWAKENS_FOSSILS)
                .buildAndGet();

        // Defossilizer
        public static final Supplier<TagKey<Item>> IRON_DEFOSSILIZER_BUCKETS = TagPropertyWrapperTemplates.registerTagKey(Registries.ITEM, CAConstants.prefix("defossilizer/iron_defossilizer_buckets"));

        public static final Supplier<TagKey<Item>> CRYSTAL_DEFOSSILIZER_BUCKETS = TagPropertyWrapperTemplates.registerTagKey(Registries.ITEM, CAConstants.prefix("defossilizer/crystal_defossilizer_buckets"));

        // Misc.
        public static final Supplier<TagKey<Item>> ROBO_BLOCKS = TagPropertyWrapperTemplates.registerTagKey(Registries.ITEM, CAConstants.prefix("misc/robo_blocks"));
        public static final Supplier<TagKey<Item>> ENCHANTED_GOLDEN_APPLES = TagPropertyWrapperTemplates.registerAndChain(Registries.ITEM, CAConstants.prefix("enchantment/enchanted_golden_apple")).withTaggedObject(() -> Items.ENCHANTED_GOLDEN_APPLE).buildAndGet();
        public static final Supplier<TagKey<Item>> SPOON = TagPropertyWrapperTemplates.registerAndChain(Registries.ITEM, CAConstants.prefix("misc/spoon")).withTaggedObjects(() -> Items.NETHERITE_SHOVEL, () -> Items.DIAMOND_SHOVEL, () -> Items.IRON_SHOVEL, () -> Items.GOLDEN_SHOVEL, () -> Items.STONE_SHOVEL, () -> Items.WOODEN_SHOVEL).withTaggedObjects(CAItems.CATS_EYE_SHOVEL::get, CAItems.CRYSTALWOOD_SHOVEL::get, CAItems.EMERALD_SHOVEL::get, CAItems.KUNZITE_SHOVEL::get, CAItems.RUBY_SHOVEL::get, CAItems.KYANITE_SHOVEL::get, CAItems.PINK_TOURMALINE_SHOVEL::get, CAItems.ULTIMATE_SHOVEL::get).buildAndGet();
    }
}
