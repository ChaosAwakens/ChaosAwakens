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
    // For easy testing:
    // /give @p chest{BlockEntityTag:{LootTable:"chaosawakens:chests/ent_dungeon"}} 1
    private static final Set<ResourceLocation> CA_LOOT_TABLES = Sets.newHashSet();

    public static final CATreasure ent_dungeon = new CATreasure("ent_dungeon");

    public static LootFunctionType ENCHANT;

    public final ResourceLocation lootTable;

    private CATreasure(String path) {
        lootTable = ChaosAwakens.prefix(String.format("chests/%s", path));
    }

    public static void init() {
        ENCHANT = registerFunction("enchant", new LootFunctionType(new LootFunctionEnchant.Serializer()));
    }

    public void generateChest(IWorld world, BlockPos pos, Direction dir, boolean trapped) {
        world.setBlockState(pos, (trapped ? Blocks.TRAPPED_CHEST : Blocks.CHEST).getDefaultState().with(ChestBlock.FACING, dir), 2);
        TileEntity te = world.getTileEntity(pos);
        if (te instanceof ChestTileEntity) {
            ((ChestTileEntity) te).setLootTable(lootTable, ((ISeedReader)world).getSeed() * pos.getX() + pos.getY() ^ pos.getZ());
        }
    }

    public void generateChestContents(ISeedReader world, BlockPos pos) {
        TileEntity te = world.getTileEntity(pos);
        if (te instanceof ChestTileEntity)
            ((ChestTileEntity) te).setLootTable(lootTable, world.getSeed() * pos.getX() + pos.getY() ^ pos.getZ());
    }

    private static LootFunctionType registerFunction(String name, LootFunctionType function) {
        return Registry.register(Registry.LOOT_FUNCTION_TYPE, ChaosAwakens.prefix(name), function); //ILootFunction registry
    }

    private static LootConditionType registerCondition(String name, LootConditionType condition) {
        return Registry.register(Registry.LOOT_CONDITION_TYPE, ChaosAwakens.prefix(name), condition); //ILootCondition registry
    }

    private static ResourceLocation register(String id) {
        return register(ChaosAwakens.prefix(id));
    }

    private static ResourceLocation register(ResourceLocation id) {
        if (CA_LOOT_TABLES.add(id)) {
            return id;
        } else {
            throw new IllegalArgumentException(id + " is already a registered built-in loot table");
        }
    }
}