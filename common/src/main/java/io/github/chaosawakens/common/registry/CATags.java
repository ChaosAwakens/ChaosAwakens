package io.github.chaosawakens.common.registry;

import io.github.chaosawakens.CAConstants;
import io.github.chaosawakens.api.asm.annotations.RegistrarEntry;
import net.minecraft.core.registries.Registries;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;

public class CATags {

    @RegistrarEntry
    public static class BlockTags {
        public static final TagKey<Block> BASE_STONE_CRYSTAL = createBlockTag("base_stone_crystal");
        public static final TagKey<Block> BASE_STONE_MINING = createBlockTag("base_stone_mining");
        public static final TagKey<Block> BASE_STONE_VILLAGE = createBlockTag("base_stone_village");

        public static final TagKey<Block> MINERS_DREAM_MINEABLE = createBlockTag("miners_dream_mineable");

        public static final TagKey<Block> FARMABLE = createBlockTag("farmable");

        public static final TagKey<Block> DENSE_DIRT = createBlockTag("dense_dirt");
        public static final TagKey<Block> TERRA_PRETA = createBlockTag("terra_preta");

        public static final TagKey<Block> APPLE_LOGS = createBlockTag("apple_logs");
        public static final TagKey<Block> CHERRY_LOGS = createBlockTag("cherry_logs");
        public static final TagKey<Block> DUPLICATION_LOGS = createBlockTag("duplication_logs");
        public static final TagKey<Block> PEACH_LOGS = createBlockTag("peach_logs");
        public static final TagKey<Block> SKYWOOD_LOGS = createBlockTag("skywood_logs");
        public static final TagKey<Block> GINKGO_LOGS = createBlockTag("ginkgo_logs");
        public static final TagKey<Block> MESOZOIC_LOGS = createBlockTag("mesozoic_logs");
        public static final TagKey<Block> DENSEWOOD_LOGS = createBlockTag("densewood_logs");

        public static final TagKey<Block> CRYSTALWOOD_LOGS = createBlockTag("crystalwood_logs");
        public static final TagKey<Block> CRYSTALWOOD_LEAVES = createBlockTag("crystalwood_leaves");
        public static final TagKey<Block> CRYSTALWOOD_SAPLINGS = createBlockTag("crystalwood_saplings");

        public static final TagKey<Block> RUBY_ORES = createBlockTag("ruby_ores");

        public static final TagKey<Block> STALAGMITE_ORE_COMMON = createBlockTag("stalagmite_ore_common");
        public static final TagKey<Block> STALAGMITE_ORE_RARE = createBlockTag("stalagmite_ore_rare");

        public static final TagKey<Block> ROBO_IMMUNE = createBlockTag("robo_immune");
        public static final TagKey<Block> POUNDER_IMMUNE = createBlockTag("pounder_immune");
        public static final TagKey<Block> JEFFERY_IMMUNE = createBlockTag("jeffery_immune");

        public static final TagKey<Block> FOSSILS = createBlockTag("fossils");
        public static final TagKey<Block> FOSSILS_STONE = createBlockTag("fossils/stone");
        public static final TagKey<Block> FOSSILS_STONE_VANILLA = createBlockTag("fossils/stone/vanilla");
        public static final TagKey<Block> FOSSILS_STONE_CHAOSAWAKENS = createBlockTag("fossils/stone/chaosawakens");

        public static final TagKey<Block> FOSSILS_GRAVEL = createBlockTag("fossils/gravel");
        public static final TagKey<Block> FOSSILS_GRAVEL_VANILLA = createBlockTag("fossils/gravel/vanilla");
        public static final TagKey<Block> FOSSILS_GRAVEL_CHAOSAWAKENS = createBlockTag("fossils/gravel/chaosawakens");

        public static final TagKey<Block> FOSSILS_SAND = createBlockTag("fossils/sand");
        public static final TagKey<Block> FOSSILS_SAND_VANILLA = createBlockTag("fossils/sand/vanilla");
        public static final TagKey<Block> FOSSILS_SAND_CHAOSAWAKENS = createBlockTag("fossils/sand/chaosawakens");

        public static final TagKey<Block> FOSSILS_SANDSTONE = createBlockTag("fossils/sandstone");
        public static final TagKey<Block> FOSSILS_SANDSTONE_VANILLA = createBlockTag("fossils/sandstone/vanilla");
        public static final TagKey<Block> FOSSILS_SANDSTONE_CHAOSAWAKENS = createBlockTag("fossils/sandstone/chaosawakens");

        public static final TagKey<Block> FOSSILS_ICE = createBlockTag("fossils/ice");
        public static final TagKey<Block> FOSSILS_ICE_VANILLA = createBlockTag("fossils/ice/vanilla");
        public static final TagKey<Block> FOSSILS_ICE_CHAOSAWAKENS = createBlockTag("fossils/ice/chaosawakens");

        public static final TagKey<Block> FOSSILS_NETHERRACK = createBlockTag("fossils/netherrack");
        public static final TagKey<Block> FOSSILS_NETHERRACK_VANILLA = createBlockTag("fossils/netherrack/vanilla");
        public static final TagKey<Block> FOSSILS_NETHERRACK_CHAOSAWAKENS = createBlockTag("fossils/netherrack/chaosawakens");

        public static final TagKey<Block> FOSSILS_BLACKSTONE = createBlockTag("fossils/blackstone");
        public static final TagKey<Block> FOSSILS_BLACKSTONE_VANILLA = createBlockTag("fossils/blackstone/vanilla");
        public static final TagKey<Block> FOSSILS_BLACKSTONE_CHAOSAWAKENS = createBlockTag("fossils/blackstone/chaosawakens");

        public static final TagKey<Block> FOSSILS_SOUL_SOIL = createBlockTag("fossils/soul_soil");
        public static final TagKey<Block> FOSSILS_SOUL_SOIL_VANILLA = createBlockTag("fossils/soul_soil/vanilla");
        public static final TagKey<Block> FOSSILS_SOUL_SOIL_CHAOSAWAKENS = createBlockTag("fossils/soul_soil/chaosawakens");

        public static final TagKey<Block> FOSSILS_END_STONE = createBlockTag("fossils/end_stone");
        public static final TagKey<Block> FOSSILS_END_STONE_VANILLA = createBlockTag("fossils/end_stone/vanilla");
        public static final TagKey<Block> FOSSILS_END_STONE_CHAOSAWAKENS = createBlockTag("fossils/end_stone/chaosawakens");

        public static final TagKey<Block> FOSSILS_KYANITE = createBlockTag("fossils/kyanite");
        public static final TagKey<Block> FOSSILS_KYANITE_VANILLA = createBlockTag("fossils/kyanite/vanilla");
        public static final TagKey<Block> FOSSILS_KYANITE_CHAOSAWAKENS = createBlockTag("fossils/kyanite/chaosawakens");

        private static TagKey<Block> createBlockTag(String name) {
            return TagKey.create(Registries.BLOCK, CAConstants.prefix(name));
        }
    }

    @RegistrarEntry
    public static class ItemTags {
        public static final TagKey<Item> APPLE_LOGS = createItemTag("apple_logs");
        public static final TagKey<Item> CHERRY_LOGS = createItemTag("cherry_logs");
        public static final TagKey<Item> DUPLICATION_LOGS = createItemTag("duplication_logs");
        public static final TagKey<Item> PEACH_LOGS = createItemTag("peach_logs");
        public static final TagKey<Item> SKYWOOD_LOGS = createItemTag("skywood_logs");
        public static final TagKey<Item> GINKGO_LOGS = createItemTag("ginkgo_logs");
        public static final TagKey<Item> MESOZOIC_LOGS = createItemTag("mesozoic_logs");
        public static final TagKey<Item> DENSEWOOD_LOGS = createItemTag("densewood_logs");

        public static final TagKey<Item> CRYSTALWOOD_LOGS = createItemTag("crystalwood_logs");
        public static final TagKey<Item> CRYSTALWOOD_LEAVES = createItemTag("crystalwood_leaves");
        public static final TagKey<Item> CRYSTALWOOD_SAPLING = createItemTag("crystalwood_saplings");

        public static final TagKey<Item> ULTIMATE_GEAR_HANDLES = createItemTag("ultimate_gear_handles");

        public static final TagKey<Item> FISH = createItemTag("fish");

        public static final TagKey<Item> GEMSTONES = createItemTag("gemstones");
        public static final TagKey<Item> NUGGETS = createItemTag("nuggets");
        public static final TagKey<Item> INGOTS = createItemTag("ingots");
        public static final TagKey<Item> MINERAL_LUMPS = createItemTag("mineral_lumps");

        public static final TagKey<Item> PLANTS = createItemTag("plants");

        public static final TagKey<Item> RUBY_ORES = createItemTag("ruby_ores");

        public static final TagKey<Item> SEEDS = createItemTag("seeds");
        public static final TagKey<Item> LEAF_CARPETS = createItemTag("leaf_carpets");

        public static final TagKey<Item> FOSSILS = createItemTag("fossils");
        public static final TagKey<Item> FOSSILS_STONE = createItemTag("fossils/stone");
        public static final TagKey<Item> FOSSILS_STONE_VANILLA = createItemTag("fossils/stone/vanilla");
        public static final TagKey<Item> FOSSILS_STONE_CHAOSAWAKENS = createItemTag("fossils/stone/chaosawakens");

        public static final TagKey<Item> FOSSILS_GRAVEL = createItemTag("fossils/gravel");
        public static final TagKey<Item> FOSSILS_GRAVEL_VANILLA = createItemTag("fossils/gravel/vanilla");
        public static final TagKey<Item> FOSSILS_GRAVEL_CHAOSAWAKENS = createItemTag("fossils/gravel/chaosawakens");

        public static final TagKey<Item> FOSSILS_SAND = createItemTag("fossils/sand");
        public static final TagKey<Item> FOSSILS_SAND_VANILLA = createItemTag("fossils/sand/vanilla");
        public static final TagKey<Item> FOSSILS_SAND_CHAOSAWAKENS = createItemTag("fossils/sand/chaosawakens");

        public static final TagKey<Item> FOSSILS_SANDSTONE = createItemTag("fossils/sandstone");
        public static final TagKey<Item> FOSSILS_SANDSTONE_VANILLA = createItemTag("fossils/sandstone/vanilla");
        public static final TagKey<Item> FOSSILS_SANDSTONE_CHAOSAWAKENS = createItemTag("fossils/sandstone/chaosawakens");

        public static final TagKey<Item> FOSSILS_ICE = createItemTag("fossils/ice");
        public static final TagKey<Item> FOSSILS_ICE_VANILLA = createItemTag("fossils/ice/vanilla");
        public static final TagKey<Item> FOSSILS_ICE_CHAOSAWAKENS = createItemTag("fossils/ice/chaosawakens");

        public static final TagKey<Item> FOSSILS_NETHERRACK = createItemTag("fossils/netherrack");
        public static final TagKey<Item> FOSSILS_NETHERRACK_VANILLA = createItemTag("fossils/netherrack/vanilla");
        public static final TagKey<Item> FOSSILS_NETHERRACK_CHAOSAWAKENS = createItemTag("fossils/netherrack/chaosawakens");

        public static final TagKey<Item> FOSSILS_BLACKSTONE = createItemTag("fossils/blackstone");
        public static final TagKey<Item> FOSSILS_BLACKSTONE_VANILLA = createItemTag("fossils/blackstone/vanilla");
        public static final TagKey<Item> FOSSILS_BLACKSTONE_CHAOSAWAKENS = createItemTag("fossils/blackstone/chaosawakens");

        public static final TagKey<Item> FOSSILS_SOUL_SOIL = createItemTag("fossils/soul_soil");
        public static final TagKey<Item> FOSSILS_SOUL_SOIL_VANILLA = createItemTag("fossils/soul_soil/vanilla");
        public static final TagKey<Item> FOSSILS_SOUL_SOIL_CHAOSAWAKENS = createItemTag("fossils/soul_soil/chaosawakens");

        public static final TagKey<Item> FOSSILS_END_STONE = createItemTag("fossils/end_stone");
        public static final TagKey<Item> FOSSILS_END_STONE_VANILLA = createItemTag("fossils/end_stone/vanilla");
        public static final TagKey<Item> FOSSILS_END_STONE_CHAOSAWAKENS = createItemTag("fossils/end_stone/chaosawakens");

        public static final TagKey<Item> FOSSILS_KYANITE = createItemTag("fossils/kyanite");
        public static final TagKey<Item> FOSSILS_KYANITE_VANIILLA = createItemTag("fossils/kyanite/vanilla");
        public static final TagKey<Item> FOSSILS_KYANITE_CHAOSAWAKENS = createItemTag("fossils/kyanite/chaosawakens");

        public static final TagKey<Item> ARCHAEOLOGIST_FOSSILS = createItemTag("villager_trades/archaeologist/fossils");
        public static final TagKey<Item> ARCHAEOLOGIST_FOSSILS_COMMON = createItemTag("villager_trades/archaeologist/fossils/common");
        public static final TagKey<Item> ARCHAEOLOGIST_FOSSILS_UNCOMMON = createItemTag("villager_trades/archaeologist/fossils/uncommon");
        public static final TagKey<Item> ARCHAEOLOGIST_FOSSILS_RARE = createItemTag("villager_trades/archaeologist/fossils/rare");
        public static final TagKey<Item> ARCHAEOLOGIST_FOSSILS_EPIC = createItemTag("villager_trades/archaeologist/fossils/epic");

        public static final TagKey<Item> ARCHAEOLOGIST_SPAWN_EGGS = createItemTag("villager_trades/archaeologist/spawn_eggs");
        public static final TagKey<Item> ARCHAEOLOGIST_SPAWN_EGGS_COMMON = createItemTag("villager_trades/archaeologist/spawn_eggs/common");
        public static final TagKey<Item> ARCHAEOLOGIST_SPAWN_EGGS_UNCOMMON = createItemTag("villager_trades/archaeologist/spawn_eggs/uncommon");
        public static final TagKey<Item> ARCHAEOLOGIST_SPAWN_EGGS_RARE = createItemTag("villager_trades/archaeologist/spawn_eggs/rare");
        public static final TagKey<Item> ARCHAEOLOGIST_SPAWN_EGGS_EPIC = createItemTag("villager_trades/archaeologist/spawn_eggs/epic");

        public static final TagKey<Item> SPAWNER_SPAWN_EGGS = createItemTag("spawner_spawn_eggs");
        
        public static final TagKey<Item> CRYSTAL_FURNACE_FUEL = createItemTag("crystal_furnace_fuel");

        private static TagKey<Item> createItemTag(String name) {
            return TagKey.create(Registries.ITEM, CAConstants.prefix(name));
        }
    }
}
