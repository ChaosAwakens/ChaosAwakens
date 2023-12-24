package io.github.chaosawakens.common.loot;

import com.google.common.collect.Sets;
import io.github.chaosawakens.ChaosAwakens;
import net.minecraft.block.Blocks;
import net.minecraft.block.ChestBlock;
import net.minecraft.loot.LootConditionType;
import net.minecraft.loot.LootFunctionType;
import net.minecraft.tileentity.ChestTileEntity;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.Direction;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.registry.Registry;
import net.minecraft.world.ISeedReader;
import net.minecraft.world.IWorld;

import java.util.Set;

public class CATreasure {
	public static final CATreasure ENT_TREE_ACACIA_LOOT = new CATreasure("ent_tree/acacia_loot"); // /give @p chest{BlockEntityTag:{LootTable:"chaosawakens:chests/ent_tree/acacia_loot"}} 1
	public static final CATreasure ENT_TREE_BIRCH_LOOT = new CATreasure("ent_tree/birch_loot"); // /give @p chest{BlockEntityTag:{LootTable:"chaosawakens:chests/ent_tree/birch_loot"}} 1
	public static final CATreasure ENT_TREE_CRIMSON_LOOT = new CATreasure("ent_tree/crimson_loot"); // /give @p chest{BlockEntityTag:{LootTable:"chaosawakens:chests/ent_tree/crimson_loot"}} 1
	public static final CATreasure ENT_TREE_DARK_OAK_LOOT = new CATreasure("ent_tree/dark_oak_loot"); // /give @p chest{BlockEntityTag:{LootTable:"chaosawakens:chests/ent_tree/dark_oak_loot"}} 1
	public static final CATreasure ENT_TREE_JUNGLE_LOOT = new CATreasure("ent_tree/jungle_loot"); // /give @p chest{BlockEntityTag:{LootTable:"chaosawakens:chests/ent_tree/jungle_loot"}} 1
	public static final CATreasure ENT_TREE_JUNGLE_TRAP = new CATreasure("ent_tree/jungle_trap"); // /give @p dispenser{BlockEntityTag:{LootTable:"chaosawakens:chests/ent_tree/jungle_trap"}} 1
	public static final CATreasure ENT_TREE_OAK_LOOT = new CATreasure("ent_tree/oak_loot"); // /give @p chest{BlockEntityTag:{LootTable:"chaosawakens:chests/ent_tree/oak_loot"}} 1
	public static final CATreasure ENT_TREE_SPRUCE_LOOT = new CATreasure("ent_tree/spruce_loot"); // /give @p chest{BlockEntityTag:{LootTable:"chaosawakens:chests/ent_tree/spruce_loot"}} 1
	public static final CATreasure ENT_TREE_WARPED_LOOT = new CATreasure("ent_tree/warped_loot"); // /give @p chest{BlockEntityTag:{LootTable:"chaosawakens:chests/ent_tree/warped_loot"}} 1
	public static final CATreasure ENT_TREE_BROWN_MUSHROOM_LOOT = new CATreasure("ent_tree/brown_mushroom_loot"); // /give @p chest{BlockEntityTag:{LootTable:"chaosawakens:chests/ent_tree/brown_mushroom_loot"}} 1
	public static final CATreasure ENT_TREE_RED_MUSHROOM_LOOT = new CATreasure("ent_tree/red_mushroom_loot"); // /give @p chest{BlockEntityTag:{LootTable:"chaosawakens:chests/ent_tree/red_mushroom_loot"}} 1
	public static final CATreasure ENT_TREE_GINKGO_LOOT = new CATreasure("ent_tree/ginkgo_loot"); // /give @p chest{BlockEntityTag:{LootTable:"chaosawakens:chests/ent_tree/ginkgo_loot"}} 1
	public static final CATreasure WASP_DUNGEON_LOOT = new CATreasure("wasp_dungeon/loot"); // /give @p chest{BlockEntityTag:{LootTable:"chaosawakens:chests/wasp_dungeon/loot"}} 1
	public static final CATreasure MINING_WASP_DUNGEON_LOOT = new CATreasure("mining_wasp_dungeon/loot"); // /give @p chest{BlockEntityTag:{LootTable:"chaosawakens:chests/mining_wasp_dungeon/loot"}} 
	
	private static final Set<ResourceLocation> CA_LOOT_TABLES = Sets.newHashSet();
	public static LootFunctionType ENCHANT;
	public final ResourceLocation lootTable;
	public final String stringName;
	public final String langName;

	private CATreasure(String path) {
		this.lootTable = ChaosAwakens.prefix(String.format("chests/%s", path));
		this.stringName = String.valueOf(lootTable);
		this.langName = "dungeon.chaosawakens.jer." + path.replace("/", ".");
	}

	public static void init() {
		ENCHANT = registerFunction("enchant", new LootFunctionType(new LootFunctionEnchant.Serializer()));
	}

	private static LootFunctionType registerFunction(String name, LootFunctionType function) {
		return Registry.register(Registry.LOOT_FUNCTION_TYPE, ChaosAwakens.prefix(name), function); // ILootFunction registry
	}

	@SuppressWarnings("unused")
	private static LootConditionType registerCondition(String name, LootConditionType condition) {
		return Registry.register(Registry.LOOT_CONDITION_TYPE, ChaosAwakens.prefix(name), condition); // ILootCondition registry
	}

	@SuppressWarnings("unused")
	private static ResourceLocation register(String id) {
		return register(ChaosAwakens.prefix(id));
	}

	private static ResourceLocation register(ResourceLocation id) {
		if (CA_LOOT_TABLES.add(id)) return id;
		else throw new IllegalArgumentException(id + " is already a registered built-in loot table");
	}

	public void generateChest(IWorld world, BlockPos pos, Direction dir, boolean trapped) {
		world.setBlock(pos, (trapped ? Blocks.TRAPPED_CHEST : Blocks.CHEST).defaultBlockState().setValue(ChestBlock.FACING, dir), 2);
		TileEntity targetTe = world.getBlockEntity(pos);
		if (targetTe instanceof ChestTileEntity) ((ChestTileEntity) targetTe).setLootTable(lootTable, ((ISeedReader) world).getSeed() * pos.getX() + pos.getY() ^ pos.getZ());
	}

	public void generateChestContents(ISeedReader world, BlockPos pos) {
		TileEntity targetTe = world.getBlockEntity(pos);
		if (targetTe instanceof ChestTileEntity) ((ChestTileEntity) targetTe).setLootTable(lootTable, world.getSeed() * pos.getX() + pos.getY() ^ pos.getZ());
	}
}
