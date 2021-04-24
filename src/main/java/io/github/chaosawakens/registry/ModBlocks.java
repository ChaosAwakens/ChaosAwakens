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

    // GEMSTONE BLOCKS
    public static final RegistryObject<Block> RUBY_BLOCK = registerBlock("ruby_block",() -> new Block(Block.Properties.from(Blocks.IRON_BLOCK)), ItemGroup.BUILDING_BLOCKS);
    public static final RegistryObject<Block> TIGER_EYE_BLOCK = registerBlock("tiger_eye_block",() -> new Block(Block.Properties.from(Blocks.IRON_BLOCK)), ItemGroup.BUILDING_BLOCKS);
    public static final RegistryObject<Block> TITANIUM_BLOCK = registerBlock("titanium_block",() -> new Block(Block.Properties.from(Blocks.IRON_BLOCK)), ItemGroup.BUILDING_BLOCKS);
    public static final RegistryObject<Block> URANIUM_BLOCK = registerBlock("uranium_block",() -> new Block(Block.Properties.from(Blocks.IRON_BLOCK)), ItemGroup.BUILDING_BLOCKS);
    public static final RegistryObject<Block> ALUMINIUM_BLOCK = registerBlock("aluminium_block",() -> new Block(Block.Properties.from(Blocks.IRON_BLOCK)), ItemGroup.BUILDING_BLOCKS);


    public static <B extends Block> RegistryObject<B> registerBlock(String name, Supplier<? extends B> supplier, ItemGroup itemGroup) {
        RegistryObject<B> block = ModBlocks.BLOCKS.register(name, supplier);
        ITEMS.register(name, () -> new BlockItem(block.get(), new Item.Properties().group(itemGroup)));
        return block;
    }
}