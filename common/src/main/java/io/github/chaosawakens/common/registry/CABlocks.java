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
            .withCustomModelDefinitions(ModelUtil.trapdoor(CAConstants.prefix("wood/apple/apple_trapdoor"), CAConstants.prefix("block/apple_trapdoor_bottom")))
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

    public static final Supplier<Block> CRYSTALWOOD_LOG = BlockPropertyWrapper.of(CABlockPropertyWrappers.LOG, registerBlock("crystalwood_log", () -> new RotatedPillarBlock(BlockBehaviour.Properties.of().mapColor((appleLogBlock) -> appleLogBlock.getValue(RotatedPillarBlock.AXIS) == Direction.Axis.Y ? MapColor.COLOR_ORANGE : MapColor.COLOR_BROWN).instrument(NoteBlockInstrument.BASS).strength(2.0F).sound(SoundType.WOOD).ignitedByLava())))
            .cachedBuilder()
            .withCustomModelDefinitions(ModelUtil.rotatedPillarBlock(CAConstants.prefix("wood/crystalwood/crystalwood_log"), CAConstants.prefix("wood/crystalwood/crystalwood_log_top")))
            .build()
            .getParentBlock();
    public static final Supplier<Block> CRYSTALWOOD = BlockPropertyWrapper.of(CABlockPropertyWrappers.WOOD, registerBlock("crystalwood", () -> new RotatedPillarBlock(BlockBehaviour.Properties.of().mapColor(MapColor.COLOR_ORANGE).instrument(NoteBlockInstrument.BASS).strength(2.0F).sound(SoundType.WOOD).ignitedByLava())))
            .cachedBuilder()
            .withCustomModelDefinition(ModelUtil.cubeColumn(CAConstants.prefix("wood/crystalwood/crystalwood_log")))
            .build()
            .getParentBlock();
    /*   public static final Supplier<Block> STRIPPED_CRYSTALWOOD_LOG = BlockPropertyWrapper.of(CABlockPropertyWrappers.LOG, registerBlock("stripped_crystalwood_log", () -> new RotatedPillarBlock(BlockBehaviour.Properties.copy(CRYSTALWOOD_LOG.get()).mapColor((appleLogBlock) -> appleLogBlock.getValue(RotatedPillarBlock.AXIS) == Direction.Axis.Y ? MapColor.TERRACOTTA_ORANGE : MapColor.COLOR_ORANGE))))
               .cachedBuilder()
               .withCustomModelDefinitions(ModelUtil.rotatedPillarBlock(CAConstants.prefix("wood/crystalwood/stripped_crystalwood_log"), CAConstants.prefix("wood/crystalwood/stripped_crystalwood_log_top")))
               .build()
               .getParentBlock();
       public static final Supplier<Block> STRIPPED_CRYSTALWOOD = BlockPropertyWrapper.of(CABlockPropertyWrappers.WOOD, registerBlock("stripped_crystalwood", () -> new RotatedPillarBlock(BlockBehaviour.Properties.copy(CRYSTALWOOD.get()))))
               .cachedBuilder()
               .withCustomModelDefinition(ModelUtil.cubeColumn(CAConstants.prefix("wood/crystalwood/stripped_crystalwood_log")))
               .build()
               .getParentBlock(); */
    public static final Supplier<Block> CRYSTALWOOD_PLANKS = BlockPropertyWrapper.of(CABlockPropertyWrappers.WOODEN_PLANKS, registerBlock("crystalwood_planks", () -> new Block(BlockBehaviour.Properties.of().mapColor(MapColor.COLOR_ORANGE).instrument(NoteBlockInstrument.BASS).strength(2.0F, 3.0F).sound(SoundType.WOOD).ignitedByLava())))
            .cachedBuilder()
            .withCustomModelDefinition(ModelUtil.cubeAll(CAConstants.prefix("wood/crystalwood/crystalwood_planks")))
            .build()
            .getParentBlock();
    public static final Supplier<Block> CRYSTALWOOD_STAIRS = BlockPropertyWrapper.of(CABlockPropertyWrappers.WOODEN_STAIRS, registerBlock("crystalwood_stairs", () -> new StairBlock(CRYSTALWOOD_PLANKS.get().defaultBlockState(), BlockBehaviour.Properties.copy(CRYSTALWOOD_PLANKS.get()))))
            .cachedBuilder()
            .withCustomModelDefinitions(ModelUtil.stairs(CAConstants.prefix("wood/crystalwood/crystalwood_planks")))
            .build()
            .getParentBlock();
    public static final Supplier<Block> CRYSTALWOOD_SLAB = BlockPropertyWrapper.of(CABlockPropertyWrappers.WOODEN_SLAB, registerBlock("crystalwood_slab", () -> new SlabBlock(BlockBehaviour.Properties.copy(CRYSTALWOOD_PLANKS.get()))))
            .cachedBuilder()
            .withCustomModelDefinitions(ModelUtil.slab(CAConstants.prefix("wood/crystalwood/crystalwood_planks")))
            .build()
            .getParentBlock();
    public static final Supplier<Block> CRYSTALWOOD_FENCE = BlockPropertyWrapper.of(CABlockPropertyWrappers.WOODEN_FENCE, registerBlock("crystalwood_fence", () -> new FenceBlock(BlockBehaviour.Properties.copy(CRYSTALWOOD_PLANKS.get()))))
            .cachedBuilder()
            .withCustomModelDefinitions(ModelUtil.fence(CAConstants.prefix("wood/crystalwood/crystalwood_planks"), CAConstants.prefix("block/crystalwood_fence_inventory")))
            .build()
            .getParentBlock();
    public static final Supplier<Block> CRYSTALWOOD_FENCE_GATE = BlockPropertyWrapper.of(CABlockPropertyWrappers.WOODEN_FENCE_GATE, registerBlock("crystalwood_fence_gate", () -> new FenceGateBlock(BlockBehaviour.Properties.copy(CRYSTALWOOD_PLANKS.get()).forceSolidOn(), CAWoodTypes.CRYSTALWOOD)))
            .cachedBuilder()
            .withCustomModelDefinitions(ModelUtil.fenceGate(CAConstants.prefix("wood/crystalwood/crystalwood_planks")))
            .build()
            .getParentBlock();
    public static final Supplier<Block> CRYSTALWOOD_DOOR = BlockPropertyWrapper.of(CABlockPropertyWrappers.WOODEN_DOOR, registerBlock("crystalwood_door", () -> new DoorBlock(BlockBehaviour.Properties.of().mapColor(CRYSTALWOOD_PLANKS.get().defaultMapColor()).instrument(NoteBlockInstrument.BASS).strength(3.0F).noOcclusion().ignitedByLava().pushReaction(PushReaction.DESTROY).forceSolidOn(), CABlockSetTypes.CRYSTALWOOD)))
            .cachedBuilder()
            .withCustomModelDefinitions(ModelUtil.door(CAConstants.prefix("wood/crystalwood/crystalwood_door_top"), CAConstants.prefix("wood/crystalwood/crystalwood_door_bottom")))
            .build()
            .getParentBlock();
    public static final Supplier<Block> CRYSTALWOOD_TRAPDOOR = BlockPropertyWrapper.of(CABlockPropertyWrappers.WOODEN_TRAPDOOR, registerBlock("crystalwood_trapdoor", () -> new TrapDoorBlock(BlockBehaviour.Properties.of().mapColor(CRYSTALWOOD_PLANKS.get().defaultMapColor()).instrument(NoteBlockInstrument.BASS).strength(3.0F).noOcclusion().forceSolidOn().isValidSpawn(PredicateUtil::neverSpawnOnBlock).ignitedByLava(), CABlockSetTypes.CRYSTALWOOD)))
            .cachedBuilder()
            .withCustomModelDefinitions(ModelUtil.trapdoor(CAConstants.prefix("wood/crystalwood/crystalwood_trapdoor"), CAConstants.prefix("block/crystalwood_trapdoor_bottom")))
            .build()
            .getParentBlock();
    public static final Supplier<Block> CRYSTALWOOD_PRESSURE_PLATE = BlockPropertyWrapper.of(CABlockPropertyWrappers.WOODEN_PRESSURE_PLATE, registerBlock("crystalwood_pressure_plate", () -> new PressurePlateBlock(PressurePlateBlock.Sensitivity.EVERYTHING, BlockBehaviour.Properties.of().mapColor(CRYSTALWOOD_PLANKS.get().defaultMapColor()).instrument(NoteBlockInstrument.BASS).forceSolidOn().noCollission().pushReaction(PushReaction.DESTROY).strength(0.5F), CABlockSetTypes.CRYSTALWOOD)))
            .cachedBuilder()
            .withCustomModelDefinitions(ModelUtil.pressurePlate(CAConstants.prefix("wood/crystalwood/crystalwood_planks")))
            .build()
            .getParentBlock();
    public static final Supplier<Block> CRYSTALWOOD_BUTTON = BlockPropertyWrapper.of(CABlockPropertyWrappers.WOODEN_BUTTON, registerBlock("crystalwood_button", () -> new ButtonBlock(BlockBehaviour.Properties.of().noCollission().strength(0.5F).pushReaction(PushReaction.DESTROY), CABlockSetTypes.CRYSTALWOOD, 30, true)))
            .cachedBuilder()
            .withCustomModelDefinitions(ModelUtil.button(CAConstants.prefix("wood/crystalwood/crystalwood_planks"), CAConstants.prefix("block/crystalwood_button_inventory")))
            .build()
            .getParentBlock();

    public static final Supplier<Block> DENSEWOOD_LOG = BlockPropertyWrapper.of(CABlockPropertyWrappers.LOG, registerBlock("densewood_log", () -> new RotatedPillarBlock(BlockBehaviour.Properties.of().mapColor((appleLogBlock) -> appleLogBlock.getValue(RotatedPillarBlock.AXIS) == Direction.Axis.Y ? MapColor.COLOR_ORANGE : MapColor.COLOR_BROWN).instrument(NoteBlockInstrument.BASS).strength(2.0F).sound(SoundType.WOOD).ignitedByLava())))
            .cachedBuilder()
            .withCustomModelDefinitions(ModelUtil.rotatedPillarBlock(CAConstants.prefix("wood/densewood/densewood_log"), CAConstants.prefix("wood/densewood/densewood_log_top")))
            .build()
            .getParentBlock();
    public static final Supplier<Block> DENSEWOOD = BlockPropertyWrapper.of(CABlockPropertyWrappers.WOOD, registerBlock("densewood", () -> new RotatedPillarBlock(BlockBehaviour.Properties.of().mapColor(MapColor.COLOR_ORANGE).instrument(NoteBlockInstrument.BASS).strength(2.0F).sound(SoundType.WOOD).ignitedByLava())))
            .cachedBuilder()
            .withCustomModelDefinition(ModelUtil.cubeColumn(CAConstants.prefix("wood/densewood/densewood_log")))
            .build()
            .getParentBlock();
    public static final Supplier<Block> STRIPPED_DENSEWOOD_LOG = BlockPropertyWrapper.of(CABlockPropertyWrappers.LOG, registerBlock("stripped_densewood_log", () -> new RotatedPillarBlock(BlockBehaviour.Properties.copy(DENSEWOOD_LOG.get()).mapColor((appleLogBlock) -> appleLogBlock.getValue(RotatedPillarBlock.AXIS) == Direction.Axis.Y ? MapColor.TERRACOTTA_ORANGE : MapColor.COLOR_ORANGE))))
            .cachedBuilder()
            .withCustomModelDefinitions(ModelUtil.rotatedPillarBlock(CAConstants.prefix("wood/densewood/stripped_densewood_log"), CAConstants.prefix("wood/densewood/stripped_densewood_log_top")))
            .build()
            .getParentBlock();
    public static final Supplier<Block> STRIPPED_DENSEWOOD = BlockPropertyWrapper.of(CABlockPropertyWrappers.WOOD, registerBlock("stripped_densewood", () -> new RotatedPillarBlock(BlockBehaviour.Properties.copy(DENSEWOOD.get()))))
            .cachedBuilder()
            .withCustomModelDefinition(ModelUtil.cubeColumn(CAConstants.prefix("wood/densewood/stripped_densewood_log")))
            .build()
            .getParentBlock();
    public static final Supplier<Block> DENSEWOOD_PLANKS = BlockPropertyWrapper.of(CABlockPropertyWrappers.WOODEN_PLANKS, registerBlock("densewood_planks", () -> new Block(BlockBehaviour.Properties.of().mapColor(MapColor.COLOR_ORANGE).instrument(NoteBlockInstrument.BASS).strength(2.0F, 3.0F).sound(SoundType.WOOD).ignitedByLava())))
            .cachedBuilder()
            .withCustomModelDefinition(ModelUtil.cubeAll(CAConstants.prefix("wood/densewood/densewood_planks")))
            .build()
            .getParentBlock();
    public static final Supplier<Block> DENSEWOOD_STAIRS = BlockPropertyWrapper.of(CABlockPropertyWrappers.WOODEN_STAIRS, registerBlock("densewood_stairs", () -> new StairBlock(DENSEWOOD_PLANKS.get().defaultBlockState(), BlockBehaviour.Properties.copy(DENSEWOOD_PLANKS.get()))))
            .cachedBuilder()
            .withCustomModelDefinitions(ModelUtil.stairs(CAConstants.prefix("wood/densewood/densewood_planks")))
            .build()
            .getParentBlock();
    public static final Supplier<Block> DENSEWOOD_SLAB = BlockPropertyWrapper.of(CABlockPropertyWrappers.WOODEN_SLAB, registerBlock("densewood_slab", () -> new SlabBlock(BlockBehaviour.Properties.copy(DENSEWOOD_PLANKS.get()))))
            .cachedBuilder()
            .withCustomModelDefinitions(ModelUtil.slab(CAConstants.prefix("wood/densewood/densewood_planks")))
            .build()
            .getParentBlock();
    public static final Supplier<Block> DENSEWOOD_FENCE = BlockPropertyWrapper.of(CABlockPropertyWrappers.WOODEN_FENCE, registerBlock("densewood_fence", () -> new FenceBlock(BlockBehaviour.Properties.copy(DENSEWOOD_PLANKS.get()))))
            .cachedBuilder()
            .withCustomModelDefinitions(ModelUtil.fence(CAConstants.prefix("wood/densewood/densewood_planks"), CAConstants.prefix("block/densewood_fence_inventory")))
            .build()
            .getParentBlock();
    public static final Supplier<Block> DENSEWOOD_FENCE_GATE = BlockPropertyWrapper.of(CABlockPropertyWrappers.WOODEN_FENCE_GATE, registerBlock("densewood_fence_gate", () -> new FenceGateBlock(BlockBehaviour.Properties.copy(DENSEWOOD_PLANKS.get()).forceSolidOn(), CAWoodTypes.DENSEWOOD)))
            .cachedBuilder()
            .withCustomModelDefinitions(ModelUtil.fenceGate(CAConstants.prefix("wood/densewood/densewood_planks")))
            .build()
            .getParentBlock();
    public static final Supplier<Block> DENSEWOOD_DOOR = BlockPropertyWrapper.of(CABlockPropertyWrappers.WOODEN_DOOR, registerBlock("densewood_door", () -> new DoorBlock(BlockBehaviour.Properties.of().mapColor(DENSEWOOD_PLANKS.get().defaultMapColor()).instrument(NoteBlockInstrument.BASS).strength(3.0F).noOcclusion().ignitedByLava().pushReaction(PushReaction.DESTROY).forceSolidOn(), CABlockSetTypes.DENSEWOOD)))
            .cachedBuilder()
            .withCustomModelDefinitions(ModelUtil.door(CAConstants.prefix("wood/densewood/densewood_door_top"), CAConstants.prefix("wood/densewood/densewood_door_bottom")))
            .build()
            .getParentBlock();
    public static final Supplier<Block> DENSEWOOD_TRAPDOOR = BlockPropertyWrapper.of(CABlockPropertyWrappers.WOODEN_TRAPDOOR, registerBlock("densewood_trapdoor", () -> new TrapDoorBlock(BlockBehaviour.Properties.of().mapColor(DENSEWOOD_PLANKS.get().defaultMapColor()).instrument(NoteBlockInstrument.BASS).strength(3.0F).noOcclusion().forceSolidOn().isValidSpawn(PredicateUtil::neverSpawnOnBlock).ignitedByLava(), CABlockSetTypes.DENSEWOOD)))
            .cachedBuilder()
            .withCustomModelDefinitions(ModelUtil.trapdoor(CAConstants.prefix("wood/densewood/densewood_trapdoor"), CAConstants.prefix("block/densewood_trapdoor_bottom")))
            .build()
            .getParentBlock();
    public static final Supplier<Block> DENSEWOOD_PRESSURE_PLATE = BlockPropertyWrapper.of(CABlockPropertyWrappers.WOODEN_PRESSURE_PLATE, registerBlock("densewood_pressure_plate", () -> new PressurePlateBlock(PressurePlateBlock.Sensitivity.EVERYTHING, BlockBehaviour.Properties.of().mapColor(DENSEWOOD_PLANKS.get().defaultMapColor()).instrument(NoteBlockInstrument.BASS).forceSolidOn().noCollission().pushReaction(PushReaction.DESTROY).strength(0.5F), CABlockSetTypes.DENSEWOOD)))
            .cachedBuilder()
            .withCustomModelDefinitions(ModelUtil.pressurePlate(CAConstants.prefix("wood/densewood/densewood_planks")))
            .build()
            .getParentBlock();
    public static final Supplier<Block> DENSEWOOD_BUTTON = BlockPropertyWrapper.of(CABlockPropertyWrappers.WOODEN_BUTTON, registerBlock("densewood_button", () -> new ButtonBlock(BlockBehaviour.Properties.of().noCollission().strength(0.5F).pushReaction(PushReaction.DESTROY), CABlockSetTypes.DENSEWOOD, 30, true)))
            .cachedBuilder()
            .withCustomModelDefinitions(ModelUtil.button(CAConstants.prefix("wood/densewood/densewood_planks"), CAConstants.prefix("block/densewood_button_inventory")))
            .build()
            .getParentBlock();

    public static final Supplier<Block> DUPLICATOR_LOG = BlockPropertyWrapper.of(CABlockPropertyWrappers.LOG, registerBlock("duplicator_log", () -> new RotatedPillarBlock(BlockBehaviour.Properties.of().mapColor((duplicatorLogBlock) -> duplicatorLogBlock.getValue(RotatedPillarBlock.AXIS) == Direction.Axis.Y ? MapColor.COLOR_ORANGE : MapColor.COLOR_BROWN).instrument(NoteBlockInstrument.BASS).strength(2.0F).sound(SoundType.WOOD).ignitedByLava())))
            .cachedBuilder()
            .withCustomModelDefinitions(ModelUtil.rotatedPillarBlock(CAConstants.prefix("wood/duplicator/duplicator_log"), CAConstants.prefix("wood/duplicator/duplicator_log_top")))
            .build()
            .getParentBlock();
    public static final Supplier<Block> DUPLICATOR_WOOD = BlockPropertyWrapper.of(CABlockPropertyWrappers.WOOD, registerBlock("duplicator_wood", () -> new RotatedPillarBlock(BlockBehaviour.Properties.of().mapColor(MapColor.COLOR_ORANGE).instrument(NoteBlockInstrument.BASS).strength(2.0F).sound(SoundType.WOOD).ignitedByLava())))
            .cachedBuilder()
            .withCustomModelDefinition(ModelUtil.cubeColumn(CAConstants.prefix("wood/duplicator/duplicator_log")))
            .build()
            .getParentBlock();
    public static final Supplier<Block> STRIPPED_DUPLICATOR_LOG = BlockPropertyWrapper.of(CABlockPropertyWrappers.LOG, registerBlock("stripped_duplicator_log", () -> new RotatedPillarBlock(BlockBehaviour.Properties.copy(DUPLICATOR_LOG.get()).mapColor((duplicatorLogBlock) -> duplicatorLogBlock.getValue(RotatedPillarBlock.AXIS) == Direction.Axis.Y ? MapColor.TERRACOTTA_ORANGE : MapColor.COLOR_ORANGE))))
            .cachedBuilder()
            .withCustomModelDefinitions(ModelUtil.rotatedPillarBlock(CAConstants.prefix("wood/duplicator/stripped_duplicator_log"), CAConstants.prefix("wood/duplicator/stripped_duplicator_log_top")))
            .build()
            .getParentBlock();
    public static final Supplier<Block> STRIPPED_DUPLICATOR_WOOD = BlockPropertyWrapper.of(CABlockPropertyWrappers.WOOD, registerBlock("stripped_duplicator_wood", () -> new RotatedPillarBlock(BlockBehaviour.Properties.copy(DUPLICATOR_WOOD.get()))))
            .cachedBuilder()
            .withCustomModelDefinition(ModelUtil.cubeColumn(CAConstants.prefix("wood/duplicator/stripped_duplicator_log")))
            .build()
            .getParentBlock();
    public static final Supplier<Block> DEAD_DUPLICATOR_LOG = BlockPropertyWrapper.of(CABlockPropertyWrappers.LOG, registerBlock("dead_duplicator_log", () -> new RotatedPillarBlock(BlockBehaviour.Properties.of().mapColor((duplicatorLogBlock) -> duplicatorLogBlock.getValue(RotatedPillarBlock.AXIS) == Direction.Axis.Y ? MapColor.COLOR_ORANGE : MapColor.COLOR_BROWN).instrument(NoteBlockInstrument.BASS).strength(2.0F).sound(SoundType.WOOD).ignitedByLava())))
            .cachedBuilder()
            .withCustomModelDefinitions(ModelUtil.rotatedPillarBlock(CAConstants.prefix("wood/duplicator/dead/dead_duplicator_log"), CAConstants.prefix("wood/duplicator/dead/dead_duplicator_log_top")))
            .build()
            .getParentBlock();
    public static final Supplier<Block> DEAD_DUPLICATOR_WOOD = BlockPropertyWrapper.of(CABlockPropertyWrappers.WOOD, registerBlock("dead_duplicator_wood", () -> new RotatedPillarBlock(BlockBehaviour.Properties.of().mapColor(MapColor.COLOR_ORANGE).instrument(NoteBlockInstrument.BASS).strength(2.0F).sound(SoundType.WOOD).ignitedByLava())))
            .cachedBuilder()
            .withCustomModelDefinition(ModelUtil.cubeColumn(CAConstants.prefix("wood/duplicator/dead/dead_duplicator_log")))
            .build()
            .getParentBlock();
    public static final Supplier<Block> STRIPPED_DEAD_DUPLICATOR_LOG = BlockPropertyWrapper.of(CABlockPropertyWrappers.LOG, registerBlock("stripped_dead_duplicator_log", () -> new RotatedPillarBlock(BlockBehaviour.Properties.copy(DUPLICATOR_LOG.get()).mapColor((duplicatorLogBlock) -> duplicatorLogBlock.getValue(RotatedPillarBlock.AXIS) == Direction.Axis.Y ? MapColor.TERRACOTTA_ORANGE : MapColor.COLOR_ORANGE))))
            .cachedBuilder()
            .withCustomModelDefinitions(ModelUtil.rotatedPillarBlock(CAConstants.prefix("wood/duplicator/dead/stripped_dead_duplicator_log"), CAConstants.prefix("wood/duplicator/dead/stripped_dead_duplicator_log_top")))
            .build()
            .getParentBlock();
    public static final Supplier<Block> STRIPPED_DEAD_DUPLICATOR_WOOD = BlockPropertyWrapper.of(CABlockPropertyWrappers.WOOD, registerBlock("stripped_dead_duplicator_wood", () -> new RotatedPillarBlock(BlockBehaviour.Properties.copy(DUPLICATOR_WOOD.get()))))
            .cachedBuilder()
            .withCustomModelDefinition(ModelUtil.cubeColumn(CAConstants.prefix("wood/duplicator/dead/stripped_dead_duplicator_log")))
            .build()
            .getParentBlock();
    public static final Supplier<Block> DUPLICATOR_PLANKS = BlockPropertyWrapper.of(CABlockPropertyWrappers.WOODEN_PLANKS, registerBlock("duplicator_planks", () -> new Block(BlockBehaviour.Properties.of().mapColor(MapColor.COLOR_ORANGE).instrument(NoteBlockInstrument.BASS).strength(2.0F, 3.0F).sound(SoundType.WOOD).ignitedByLava())))
            .cachedBuilder()
            .withCustomModelDefinition(ModelUtil.cubeAll(CAConstants.prefix("wood/duplicator/duplicator_planks")))
            .build()
            .getParentBlock();
    public static final Supplier<Block> DUPLICATOR_STAIRS = BlockPropertyWrapper.of(CABlockPropertyWrappers.WOODEN_STAIRS, registerBlock("duplicator_stairs", () -> new StairBlock(DUPLICATOR_PLANKS.get().defaultBlockState(), BlockBehaviour.Properties.copy(DUPLICATOR_PLANKS.get()))))
            .cachedBuilder()
            .withCustomModelDefinitions(ModelUtil.stairs(CAConstants.prefix("wood/duplicator/duplicator_planks")))
            .build()
            .getParentBlock();
    public static final Supplier<Block> DUPLICATOR_SLAB = BlockPropertyWrapper.of(CABlockPropertyWrappers.WOODEN_SLAB, registerBlock("duplicator_slab", () -> new SlabBlock(BlockBehaviour.Properties.copy(DUPLICATOR_PLANKS.get()))))
            .cachedBuilder()
            .withCustomModelDefinitions(ModelUtil.slab(CAConstants.prefix("wood/duplicator/duplicator_planks")))
            .build()
            .getParentBlock();
    public static final Supplier<Block> DUPLICATOR_FENCE = BlockPropertyWrapper.of(CABlockPropertyWrappers.WOODEN_FENCE, registerBlock("duplicator_fence", () -> new FenceBlock(BlockBehaviour.Properties.copy(DUPLICATOR_PLANKS.get()))))
            .cachedBuilder()
            .withCustomModelDefinitions(ModelUtil.fence(CAConstants.prefix("wood/duplicator/duplicator_planks"), CAConstants.prefix("block/duplicator_fence_inventory")))
            .build()
            .getParentBlock();
    public static final Supplier<Block> DUPLICATOR_FENCE_GATE = BlockPropertyWrapper.of(CABlockPropertyWrappers.WOODEN_FENCE_GATE, registerBlock("duplicator_fence_gate", () -> new FenceGateBlock(BlockBehaviour.Properties.copy(DUPLICATOR_PLANKS.get()).forceSolidOn(), CAWoodTypes.DUPLICATOR)))
            .cachedBuilder()
            .withCustomModelDefinitions(ModelUtil.fenceGate(CAConstants.prefix("wood/duplicator/duplicator_planks")))
            .build()
            .getParentBlock();
    public static final Supplier<Block> DUPLICATOR_DOOR = BlockPropertyWrapper.of(CABlockPropertyWrappers.WOODEN_DOOR, registerBlock("duplicator_door", () -> new DoorBlock(BlockBehaviour.Properties.of().mapColor(DUPLICATOR_PLANKS.get().defaultMapColor()).instrument(NoteBlockInstrument.BASS).strength(3.0F).noOcclusion().ignitedByLava().pushReaction(PushReaction.DESTROY).forceSolidOn(), CABlockSetTypes.DUPLICATOR)))
            .cachedBuilder()
            .withCustomModelDefinitions(ModelUtil.door(CAConstants.prefix("wood/duplicator/duplicator_door_top"), CAConstants.prefix("wood/duplicator/duplicator_door_bottom")))
            .build()
            .getParentBlock();
    public static final Supplier<Block> DUPLICATOR_TRAPDOOR = BlockPropertyWrapper.of(CABlockPropertyWrappers.WOODEN_TRAPDOOR, registerBlock("duplicator_trapdoor", () -> new TrapDoorBlock(BlockBehaviour.Properties.of().mapColor(DUPLICATOR_PLANKS.get().defaultMapColor()).instrument(NoteBlockInstrument.BASS).strength(3.0F).noOcclusion().forceSolidOn().isValidSpawn(PredicateUtil::neverSpawnOnBlock).ignitedByLava(), CABlockSetTypes.DUPLICATOR)))
            .cachedBuilder()
            .withCustomModelDefinitions(ModelUtil.trapdoor(CAConstants.prefix("wood/duplicator/duplicator_trapdoor"), CAConstants.prefix("block/duplicator_trapdoor_bottom")))
            .build()
            .getParentBlock();
    public static final Supplier<Block> DUPLICATOR_PRESSURE_PLATE = BlockPropertyWrapper.of(CABlockPropertyWrappers.WOODEN_PRESSURE_PLATE, registerBlock("duplicator_pressure_plate", () -> new PressurePlateBlock(PressurePlateBlock.Sensitivity.EVERYTHING, BlockBehaviour.Properties.of().mapColor(DUPLICATOR_PLANKS.get().defaultMapColor()).instrument(NoteBlockInstrument.BASS).forceSolidOn().noCollission().pushReaction(PushReaction.DESTROY).strength(0.5F), CABlockSetTypes.DUPLICATOR)))
            .cachedBuilder()
            .withCustomModelDefinitions(ModelUtil.pressurePlate(CAConstants.prefix("wood/duplicator/duplicator_planks")))
            .build()
            .getParentBlock();
    public static final Supplier<Block> DUPLICATOR_BUTTON = BlockPropertyWrapper.of(CABlockPropertyWrappers.WOODEN_BUTTON, registerBlock("duplicator_button", () -> new ButtonBlock(BlockBehaviour.Properties.of().noCollission().strength(0.5F).pushReaction(PushReaction.DESTROY), CABlockSetTypes.DUPLICATOR, 30, true)))
            .cachedBuilder()
            .withCustomModelDefinitions(ModelUtil.button(CAConstants.prefix("wood/duplicator/duplicator_planks"), CAConstants.prefix("block/duplicator_button_inventory")))
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
