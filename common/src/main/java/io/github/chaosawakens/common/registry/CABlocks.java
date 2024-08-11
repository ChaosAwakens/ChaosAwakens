package io.github.chaosawakens.common.registry;

import com.google.common.collect.ImmutableList;
import io.github.chaosawakens.CAConstants;
import io.github.chaosawakens.api.asm.annotations.RegistrarEntry;
import io.github.chaosawakens.api.block.BlockPropertyWrapper;
import io.github.chaosawakens.api.platform.CAServices;
import io.github.chaosawakens.util.ModelUtil;
import io.github.chaosawakens.util.PredicateUtil;
import it.unimi.dsi.fastutil.objects.ObjectArrayList;
import net.minecraft.core.Direction;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.*;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.properties.NoteBlockInstrument;
import net.minecraft.world.level.material.MapColor;
import net.minecraft.world.level.material.PushReaction;

import java.util.function.Supplier;

@RegistrarEntry
public final class CABlocks {
    private static final ObjectArrayList<Supplier<Block>> BLOCKS = new ObjectArrayList<>();
    private static final ObjectArrayList<Supplier<Item>> BLOCK_ITEMS = new ObjectArrayList<>();

    public static final Supplier<Block> APPLE_LOG = BlockPropertyWrapper.of(CABlockPropertyWrappers.LOG, registerBlock("apple_log", () -> new RotatedPillarBlock(BlockBehaviour.Properties.of().mapColor((appleLogBlock) -> appleLogBlock.getValue(RotatedPillarBlock.AXIS) == Direction.Axis.Y ? MapColor.COLOR_ORANGE : MapColor.COLOR_BROWN).instrument(NoteBlockInstrument.BASS).strength(2.0F).sound(SoundType.WOOD).ignitedByLava())))
            .cachedBuilder()
            .withCustomModelDefinitions(ModelUtil.rotatedPillarBlock(CAConstants.prefix("wood/apple/apple_log"), CAConstants.prefix("wood/apple/apple_log_top")))
            .build()
            .getParentBlock();
    public static final Supplier<Block> APPLE_WOOD = BlockPropertyWrapper.of(CABlockPropertyWrappers.WOOD, registerBlock("apple_wood", () -> new RotatedPillarBlock(BlockBehaviour.Properties.of().mapColor(MapColor.COLOR_ORANGE).instrument(NoteBlockInstrument.BASS).strength(2.0F).sound(SoundType.WOOD).ignitedByLava())))
            .cachedBuilder()
            .withCustomModelDefinition(ModelUtil.cubeColumn(CAConstants.prefix("wood/apple/apple_log")))
            .build()
            .getParentBlock();
    public static final Supplier<Block> STRIPPED_APPLE_LOG = BlockPropertyWrapper.of(CABlockPropertyWrappers.LOG, registerBlock("stripped_apple_log", () -> new RotatedPillarBlock(BlockBehaviour.Properties.copy(APPLE_LOG.get()).mapColor((appleLogBlock) -> appleLogBlock.getValue(RotatedPillarBlock.AXIS) == Direction.Axis.Y ? MapColor.TERRACOTTA_ORANGE : MapColor.COLOR_ORANGE))))
            .cachedBuilder()
            .withCustomModelDefinitions(ModelUtil.rotatedPillarBlock(CAConstants.prefix("wood/apple/stripped_apple_log"), CAConstants.prefix("wood/apple/stripped_apple_log_top")))
            .build()
            .getParentBlock();
    public static final Supplier<Block> STRIPPED_APPLE_WOOD = BlockPropertyWrapper.of(CABlockPropertyWrappers.WOOD, registerBlock("stripped_apple_wood", () -> new RotatedPillarBlock(BlockBehaviour.Properties.copy(APPLE_WOOD.get()))))
            .cachedBuilder()
            .withCustomModelDefinition(ModelUtil.cubeColumn(CAConstants.prefix("wood/apple/stripped_apple_log")))
            .build()
            .getParentBlock();
    public static final Supplier<Block> APPLE_PLANKS = BlockPropertyWrapper.of(CABlockPropertyWrappers.WOODEN_PLANKS, registerBlock("apple_planks", () -> new Block(BlockBehaviour.Properties.of().mapColor(MapColor.COLOR_ORANGE).instrument(NoteBlockInstrument.BASS).strength(2.0F, 3.0F).sound(SoundType.WOOD).ignitedByLava())))
            .cachedBuilder()
            .withCustomModelDefinition(ModelUtil.cubeAll(CAConstants.prefix("wood/apple/apple_planks")))
            .build()
            .getParentBlock();
    public static final Supplier<Block> APPLE_STAIRS = BlockPropertyWrapper.of(CABlockPropertyWrappers.WOODEN_STAIRS, registerBlock("apple_stairs", () -> new StairBlock(APPLE_PLANKS.get().defaultBlockState(), BlockBehaviour.Properties.copy(APPLE_PLANKS.get()))))
            .cachedBuilder()
            .withCustomModelDefinitions(ModelUtil.stairs(CAConstants.prefix("wood/apple/apple_planks")))
            .build()
            .getParentBlock();
    public static final Supplier<Block> APPLE_SLAB = BlockPropertyWrapper.of(CABlockPropertyWrappers.WOODEN_SLAB, registerBlock("apple_slab", () -> new SlabBlock(BlockBehaviour.Properties.copy(APPLE_PLANKS.get()))))
            .cachedBuilder()
            .withCustomModelDefinitions(ModelUtil.slab(CAConstants.prefix("wood/apple/apple_planks")))
            .build()
            .getParentBlock();
    public static final Supplier<Block> APPLE_FENCE = BlockPropertyWrapper.of(CABlockPropertyWrappers.WOODEN_FENCE, registerBlock("apple_fence", () -> new FenceBlock(BlockBehaviour.Properties.copy(APPLE_PLANKS.get()))))
            .cachedBuilder()
            .withCustomModelDefinitions(ModelUtil.fence(CAConstants.prefix("wood/apple/apple_planks"), CAConstants.prefix("block/apple_fence_inventory")))
            .build()
            .getParentBlock();
    public static final Supplier<Block> APPLE_FENCE_GATE = BlockPropertyWrapper.of(CABlockPropertyWrappers.WOODEN_FENCE_GATE, registerBlock("apple_fence_gate", () -> new FenceGateBlock(BlockBehaviour.Properties.copy(APPLE_PLANKS.get()).forceSolidOn(), CAWoodTypes.APPLE)))
            .cachedBuilder()
            .withCustomModelDefinitions(ModelUtil.fenceGate(CAConstants.prefix("wood/apple/apple_planks")))
            .build()
            .getParentBlock();
    public static final Supplier<Block> APPLE_DOOR = BlockPropertyWrapper.of(CABlockPropertyWrappers.WOODEN_DOOR, registerBlock("apple_door", () -> new DoorBlock(BlockBehaviour.Properties.of().mapColor(APPLE_PLANKS.get().defaultMapColor()).instrument(NoteBlockInstrument.BASS).strength(3.0F).noOcclusion().ignitedByLava().pushReaction(PushReaction.DESTROY).forceSolidOn(), CABlockSetTypes.APPLE)))
            .cachedBuilder()
            .withCustomModelDefinitions(ModelUtil.door(CAConstants.prefix("wood/apple/apple_door_top"), CAConstants.prefix("wood/apple/apple_door_bottom")))
            .build()
            .getParentBlock();
    public static final Supplier<Block> APPLE_TRAPDOOR = BlockPropertyWrapper.of(CABlockPropertyWrappers.WOODEN_TRAPDOOR, registerBlock("apple_trapdoor", () -> new TrapDoorBlock(BlockBehaviour.Properties.of().mapColor(APPLE_PLANKS.get().defaultMapColor()).instrument(NoteBlockInstrument.BASS).strength(3.0F).noOcclusion().forceSolidOn().isValidSpawn(PredicateUtil::neverSpawnOnBlock).ignitedByLava(), CABlockSetTypes.APPLE)))
            .cachedBuilder()
            .withCustomModelDefinitions(ModelUtil.trapdoor(CAConstants.prefix("wood/apple/apple_planks"), CAConstants.prefix("block/apple_trapdoor_bottom")))
            .build()
            .getParentBlock();
    public static final Supplier<Block> APPLE_PRESSURE_PLATE = BlockPropertyWrapper.of(CABlockPropertyWrappers.WOODEN_PRESSURE_PLATE, registerBlock("apple_pressure_plate", () -> new PressurePlateBlock(PressurePlateBlock.Sensitivity.EVERYTHING, BlockBehaviour.Properties.of().mapColor(APPLE_PLANKS.get().defaultMapColor()).instrument(NoteBlockInstrument.BASS).forceSolidOn().noCollission().pushReaction(PushReaction.DESTROY).strength(0.5F), CABlockSetTypes.APPLE)))
            .cachedBuilder()
            .withCustomModelDefinitions(ModelUtil.pressurePlate(CAConstants.prefix("wood/apple/apple_planks")))
            .build()
            .getParentBlock();
    public static final Supplier<Block> APPLE_BUTTON = BlockPropertyWrapper.of(CABlockPropertyWrappers.WOODEN_BUTTON, registerBlock("apple_button", () -> new ButtonBlock(BlockBehaviour.Properties.of().noCollission().strength(0.5F).pushReaction(PushReaction.DESTROY), CABlockSetTypes.APPLE, 30, true)))
            .cachedBuilder()
            .withCustomModelDefinitions(ModelUtil.button(CAConstants.prefix("wood/apple/apple_planks"), CAConstants.prefix("block/apple_button_inventory")))
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
