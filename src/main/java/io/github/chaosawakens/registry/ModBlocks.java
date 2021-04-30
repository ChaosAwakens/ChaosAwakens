package io.github.chaosawakens.registry;

import io.github.chaosawakens.ChaosAwakens;
import net.minecraft.block.Block;
import net.minecraft.block.Blocks;
import net.minecraft.item.BlockItem;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroup;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;

import java.util.function.Supplier;

@Mod.EventBusSubscriber(modid = ChaosAwakens.MODID, bus = Mod.EventBusSubscriber.Bus.MOD)
public class ModBlocks {
    public static final DeferredRegister<Block> BLOCKS = DeferredRegister.create(ForgeRegistries.BLOCKS, ChaosAwakens.MODID);
    public static final DeferredRegister<Item> ITEMS = DeferredRegister.create(ForgeRegistries.ITEMS, ChaosAwakens.MODID);

    // GEMSTONE ORES
    public static final RegistryObject<Block> SALT_ORE = registerBlock("salt_ore",() -> new Block(Block.Properties.from(Blocks.IRON_ORE).harvestLevel(0)), ModItemGroups.blocksItemGroup);
    public static final RegistryObject<Block> AMETHYST_ORE = registerBlock("amethyst_ore",() -> new Block(Block.Properties.from(Blocks.IRON_ORE).harvestLevel(2)), ModItemGroups.blocksItemGroup);
    public static final RegistryObject<Block> RUBY_ORE = registerBlock("ruby_ore",() -> new Block(Block.Properties.from(Blocks.IRON_ORE).harvestLevel(3)), ModItemGroups.blocksItemGroup);
    public static final RegistryObject<Block> TIGERS_EYE_ORE = registerBlock("tigers_eye_ore",() -> new Block(Block.Properties.from(Blocks.IRON_ORE).harvestLevel(2)), ModItemGroups.blocksItemGroup);
    public static final RegistryObject<Block> TITANIUM_ORE = registerBlock("titanium_ore",() -> new Block(Block.Properties.from(Blocks.IRON_ORE).harvestLevel(3)), ModItemGroups.blocksItemGroup);
    public static final RegistryObject<Block> URANIUM_ORE = registerBlock("uranium_ore",() -> new Block(Block.Properties.from(Blocks.IRON_ORE).harvestLevel(3)), ModItemGroups.blocksItemGroup);
    public static final RegistryObject<Block> ALUMINIUM_ORE = registerBlock("aluminium_ore",() -> new Block(Block.Properties.from(Blocks.IRON_ORE).harvestLevel(2)), ModItemGroups.blocksItemGroup);
    public static final RegistryObject<Block> UNPROCESSED_OIL_ORE = registerBlock("unprocessed_oil_ore",() -> new Block(Block.Properties.from(Blocks.IRON_ORE).harvestLevel(1)), ModItemGroups.blocksItemGroup);

    // GEMSTONE BLOCKS
    public static final RegistryObject<Block> AMETHYST_BLOCK = registerBlock("amethyst_block",() -> new Block(Block.Properties.from(Blocks.IRON_BLOCK).harvestLevel(2)), ModItemGroups.blocksItemGroup);
    public static final RegistryObject<Block> RUBY_BLOCK = registerBlock("ruby_block",() -> new Block(Block.Properties.from(Blocks.IRON_BLOCK).harvestLevel(3)), ModItemGroups.blocksItemGroup);
    public static final RegistryObject<Block> TIGERS_EYE_BLOCK = registerBlock("tigers_eye_block",() -> new Block(Block.Properties.from(Blocks.IRON_BLOCK).harvestLevel(2)), ModItemGroups.blocksItemGroup);
    public static final RegistryObject<Block> TITANIUM_BLOCK = registerBlock("titanium_block",() -> new Block(Block.Properties.from(Blocks.IRON_BLOCK).harvestLevel(3)), ModItemGroups.blocksItemGroup);
    public static final RegistryObject<Block> URANIUM_BLOCK = registerBlock("uranium_block",() -> new Block(Block.Properties.from(Blocks.IRON_BLOCK).harvestLevel(3)), ModItemGroups.blocksItemGroup);
    public static final RegistryObject<Block> ALUMINIUM_BLOCK = registerBlock("aluminium_block",() -> new Block(Block.Properties.from(Blocks.IRON_BLOCK).harvestLevel(2)), ModItemGroups.blocksItemGroup);

    public static final RegistryObject<Block> RED_ANT_NEST = registerBlock("red_ant_nest",() -> new Block(Block.Properties.from(Blocks.GRASS_BLOCK)), ModItemGroups.blocksItemGroup);
    public static final RegistryObject<Block> BROWN_ANT_NEST = registerBlock("brown_ant_nest",() -> new Block(Block.Properties.from(Blocks.GRASS_BLOCK)), ModItemGroups.blocksItemGroup);
    public static final RegistryObject<Block> RAINBOW_ANT_NEST = registerBlock("rainbow_ant_nest",() -> new Block(Block.Properties.from(Blocks.GRASS_BLOCK)), ModItemGroups.blocksItemGroup);
    public static final RegistryObject<Block> UNSTABLE_ANT_NEST = registerBlock("unstable_ant_nest",() -> new Block(Block.Properties.from(Blocks.GRASS_BLOCK)), ModItemGroups.blocksItemGroup);
    public static final RegistryObject<Block> TERMITE_NEST = registerBlock("termite_nest",() -> new Block(Block.Properties.from(Blocks.GRASS_BLOCK)), ModItemGroups.blocksItemGroup);

    public static <B extends Block> RegistryObject<B> registerBlock(String name, Supplier<? extends B> supplier, ItemGroup itemGroup) {
        RegistryObject<B> block = ModBlocks.BLOCKS.register(name, supplier);
        ITEMS.register(name, () -> new BlockItem(block.get(), new Item.Properties().group(itemGroup)));
        return block;
    }
}
