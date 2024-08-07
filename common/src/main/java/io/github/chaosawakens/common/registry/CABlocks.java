package io.github.chaosawakens.common.registry;

import com.google.common.collect.ImmutableList;
import io.github.chaosawakens.CAConstants;
import io.github.chaosawakens.api.asm.annotations.RegistrarEntry;
import io.github.chaosawakens.api.block.BlockPropertyWrapper;
import io.github.chaosawakens.api.platform.CAServices;
import it.unimi.dsi.fastutil.objects.ObjectArrayList;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.tags.BlockTags;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockBehaviour;

import java.util.function.Supplier;

@RegistrarEntry
public final class CABlocks {
    private static final ObjectArrayList<Supplier<Block>> BLOCKS = new ObjectArrayList<>();
    private static final ObjectArrayList<Supplier<Item>> BLOCK_ITEMS = new ObjectArrayList<>();

    public static final Supplier<Block> CAOS_WAKENS = BlockPropertyWrapper.of(CABlockPropertyWrappers.BASIC_BLOCK_PICKAXE, registerBlock("caos_wakens", () -> new Block(BlockBehaviour.Properties.of().destroyTime(20).requiresCorrectToolForDrops())))
            .getParentBlock();
    public static final Supplier<Block> COPIED_WAKER = BlockPropertyWrapper.of("updated_waker", CAOS_WAKENS)
            .cachedBuilder()
            .withCustomName("C.O.P.I.E.D. WAKER")
            .withTags(ObjectArrayList.of(BlockTags.NEEDS_DIAMOND_TOOL))
            .build()
            .getParentBlock();

    private static Supplier<Block> registerBlock(String id, Supplier<Block> blockSup) {
        return registerBlock(id, blockSup, new Item.Properties());
    }

    private static Supplier<Block> registerBlock(String id, Supplier<Block> blockSup, Item.Properties blockItemProperties) {
        Supplier<Block> registeredBlock = registerItemlessBlock(id, blockSup);
        registerBlockItem(id, () -> new BlockItem(registeredBlock.get(), blockItemProperties));
        return registeredBlock;
    }

    private static Supplier<Block> registerBlock(String id, Supplier<Block> blockSup, Supplier<Item> itemSup) {
        Supplier<Block> registeredBlock = registerItemlessBlock(id, blockSup);
        registerBlockItem(id, itemSup);
        return registeredBlock;
    }

    private static Supplier<Block> registerItemlessBlock(String id, Supplier<Block> blockSup) {
        Supplier<Block> registeredBlockSup = CAServices.REGISTRAR.registerObject(CAConstants.prefix(id), blockSup, BuiltInRegistries.BLOCK); // Otherwise reference to the block sup is null cuz it needs to be registered b4hand
        BLOCKS.add(registeredBlockSup);
        return registeredBlockSup;
    }

    private static Supplier<Item> registerBlockItem(String id, Supplier<Item> itemSup) {
        Supplier<Item> registeredItemSup = CAServices.REGISTRAR.registerObject(CAConstants.prefix(id), itemSup, BuiltInRegistries.ITEM); // Otherwise reference to the item sup is null cuz it needs to be registered b4hand
        BLOCK_ITEMS.add(registeredItemSup);
        return registeredItemSup;
    }

    public static Supplier<Block> registerExternalBlock(String id, Supplier<Block> blockSup) {
        return registerBlock(id, blockSup);
    }

    public static ImmutableList<Supplier<Block>> getBlocks() {
        return ImmutableList.copyOf(BLOCKS);
    }

    public static ImmutableList<Supplier<Item>> getBlockItems() {
        return ImmutableList.copyOf(BLOCK_ITEMS);
    }
}
