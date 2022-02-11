package io.github.chaosawakens.data;

import io.github.chaosawakens.ChaosAwakens;
import io.github.chaosawakens.common.blocks.GateBlock;
import io.github.chaosawakens.common.blocks.RotatedPillarCrystalBlock;
import io.github.chaosawakens.common.registry.CABlocks;
import net.minecraft.block.*;
import net.minecraft.data.DataGenerator;
import net.minecraft.state.properties.AttachFace;
import net.minecraft.util.Direction;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.client.model.generators.BlockStateProvider;
import net.minecraftforge.client.model.generators.ConfiguredModel;
import net.minecraftforge.client.model.generators.ModelFile;
import net.minecraftforge.common.data.ExistingFileHelper;

public class CABlockStateProvider extends BlockStateProvider {
    public CABlockStateProvider(DataGenerator gen, String modid, ExistingFileHelper exFileHelper) {
        super(gen, modid, exFileHelper);
    }

    @Override
    protected void registerStatesAndModels() {
        this.simpleBlock(CABlocks.FOSSILISED_ACACIA_ENT.get());
        this.simpleBlock(CABlocks.FOSSILISED_BIRCH_ENT.get());
        this.simpleBlock(CABlocks.FOSSILISED_DARK_OAK_ENT.get());
        this.simpleBlock(CABlocks.FOSSILISED_JUNGLE_ENT.get());
        this.simpleBlock(CABlocks.FOSSILISED_OAK_ENT.get());
        this.simpleBlock(CABlocks.FOSSILISED_SPRUCE_ENT.get());
        this.simpleBlock(CABlocks.FOSSILISED_HERCULES_BEETLE.get());
        this.simpleBlock(CABlocks.FOSSILISED_RUBY_BUG.get());
        this.simpleBlock(CABlocks.FOSSILISED_EMERALD_GATOR.get());
        this.simpleBlock(CABlocks.FOSSILISED_GREEN_FISH.get());
        this.simpleBlock(CABlocks.FOSSILISED_ROCK_FISH.get());
        this.simpleBlock(CABlocks.FOSSILISED_SPARK_FISH.get());
        this.simpleBlock(CABlocks.FOSSILISED_WOOD_FISH.get());
        this.simpleBlock(CABlocks.FOSSILISED_WHALE.get());
        this.simpleBlock(CABlocks.FOSSILISED_WTF.get());
        this.simpleBlock(CABlocks.FOSSILISED_SCORPION.get());
        this.simpleBlock(CABlocks.FOSSILISED_WASP.get());
        this.simpleBlock(CABlocks.FOSSILISED_PIRAPORU.get());
        this.simpleBlock(CABlocks.FOSSILISED_APPLE_COW.get());
        this.simpleBlock(CABlocks.FOSSILISED_GOLDEN_APPLE_COW.get());
        this.simpleBlock(CABlocks.FOSSILISED_CARROT_PIG.get());
        this.simpleBlock(CABlocks.FOSSILISED_GOLDEN_CARROT_PIG.get());
        this.simpleBlock(CABlocks.FOSSILISED_BIRD.get());
        this.simpleBlock(CABlocks.FOSSILISED_DIMETRODON.get());
        this.simpleBlock(CABlocks.FOSSILISED_FROG.get());

        this.simpleBlock(CABlocks.FOSSILISED_BAT.get());
        this.simpleBlock(CABlocks.FOSSILISED_BEE.get());
        this.simpleBlock(CABlocks.FOSSILISED_CAVE_SPIDER.get());
        this.simpleBlock(CABlocks.FOSSILISED_CHICKEN.get());
        this.simpleBlock(CABlocks.FOSSILISED_COD.get());
        this.simpleBlock(CABlocks.FOSSILISED_COW.get());
        this.simpleBlock(CABlocks.FOSSILISED_CREEPER.get());
        this.simpleBlock(CABlocks.FOSSILISED_DOLPHIN.get());
        this.simpleBlock(CABlocks.FOSSILISED_DONKEY.get());
        this.simpleBlock(CABlocks.FOSSILISED_DROWNED.get());
        this.simpleBlock(CABlocks.FOSSILISED_ENDERMAN.get());
        this.simpleBlock(CABlocks.FOSSILISED_EVOKER.get());
        this.simpleBlock(CABlocks.FOSSILISED_FOX.get());
        this.simpleBlock(CABlocks.FOSSILISED_GIANT.get());
        this.simpleBlock(CABlocks.FOSSILISED_GUARDIAN.get());
        this.simpleBlock(CABlocks.FOSSILISED_HORSE.get());
        this.simpleBlock(CABlocks.FOSSILISED_HUSK_STONE.get());
        this.simpleBlock(CABlocks.FOSSILISED_ILLUSIONER.get());
        this.simpleBlock(CABlocks.FOSSILISED_IRON_GOLEM.get());
        this.simpleBlock(CABlocks.FOSSILISED_LLAMA.get());
        this.simpleBlock(CABlocks.FOSSILISED_MOOSHROOM.get());
        this.simpleBlock(CABlocks.FOSSILISED_OCELOT.get());
        this.simpleBlock(CABlocks.FOSSILISED_PANDA.get());
        this.simpleBlock(CABlocks.FOSSILISED_PIG.get());
        this.simpleBlock(CABlocks.FOSSILISED_PHANTOM.get());
        this.simpleBlock(CABlocks.FOSSILISED_PILLAGER.get());
        this.simpleBlock(CABlocks.FOSSILISED_POLAR_BEAR.get());
        this.simpleBlock(CABlocks.FOSSILISED_PUFFERFISH.get());
        this.simpleBlock(CABlocks.FOSSILISED_RABBIT.get());
        this.simpleBlock(CABlocks.FOSSILISED_RAVAGER.get());
        this.simpleBlock(CABlocks.FOSSILISED_SALMON.get());
        this.simpleBlock(CABlocks.FOSSILISED_SHEEP.get());
        this.simpleBlock(CABlocks.FOSSILISED_SKELETON.get());
        this.simpleBlock(CABlocks.FOSSILISED_SKELETON_HORSE.get());
        this.simpleBlock(CABlocks.FOSSILISED_SLIME.get());
        this.simpleBlock(CABlocks.FOSSILISED_SNOW_GOLEM.get());
        this.simpleBlock(CABlocks.FOSSILISED_SPIDER.get());
        this.simpleBlock(CABlocks.FOSSILISED_SQUID.get());
        this.simpleBlock(CABlocks.FOSSILISED_STRAY.get());
        this.simpleBlock(CABlocks.FOSSILISED_TROPICAL_FISH.get());
        this.simpleBlock(CABlocks.FOSSILISED_TURTLE.get());
        this.simpleBlock(CABlocks.FOSSILISED_VILLAGER.get());
        this.simpleBlock(CABlocks.FOSSILISED_VINDICATOR.get());
        this.simpleBlock(CABlocks.FOSSILISED_WANDERING_TRADER.get());
        this.simpleBlock(CABlocks.FOSSILISED_WOLF.get());
        this.simpleBlock(CABlocks.FOSSILISED_WITCH.get());
        this.simpleBlock(CABlocks.FOSSILISED_ZOMBIE.get());
        this.simpleBlock(CABlocks.FOSSILISED_ZOMBIE_HORSE.get());

        this.simpleBlock(CABlocks.FOSSILISED_CRIMSON_ENT.get());
        this.simpleBlock(CABlocks.FOSSILISED_WARPED_ENT.get());
        this.simpleBlock(CABlocks.FOSSILISED_LAVA_EEL.get());

        this.simpleBlock(CABlocks.FOSSILISED_BLAZE.get());
        this.simpleBlock(CABlocks.FOSSILISED_GHAST.get());
        this.simpleBlock(CABlocks.FOSSILISED_HOGLIN.get());
        this.simpleBlock(CABlocks.FOSSILISED_ENDERMAN_NETHERRACK.get());
        this.simpleBlock(CABlocks.FOSSILISED_MAGMA_CUBE_NETHERRACK.get());
        this.simpleBlock(CABlocks.FOSSILISED_MAGMA_CUBE_BLACKSTONE.get());
        this.simpleBlock(CABlocks.FOSSILISED_PIGLIN.get());
        this.simpleBlock(CABlocks.FOSSILISED_SKELETON_SOUL_SOIL.get());
        this.simpleBlock(CABlocks.FOSSILISED_STRIDER.get());
        this.simpleBlock(CABlocks.FOSSILISED_WITHER_SKELETON.get());
        this.simpleBlock(CABlocks.FOSSILISED_ZOMBIFIED_PIGLIN.get());

        this.simpleBlock(CABlocks.FOSSILISED_ENDERMAN_END_STONE.get());
        this.simpleBlock(CABlocks.FOSSILISED_ENDERMITE.get());
        this.simpleBlock(CABlocks.FOSSILISED_SHULKER.get());

        this.simpleBlock(CABlocks.CRYSTALISED_CRYSTAL_APPLE_COW.get());

        this.simpleBlock(CABlocks.RUBY_ORE.get());
        this.simpleBlock(CABlocks.NETHERRACK_RUBY_ORE.get());
        this.simpleBlock(CABlocks.BLACKSTONE_RUBY_ORE.get());

        this.logBlock(CABlocks.APPLE_LOG.get());
        this.woodBlock(CABlocks.APPLE_WOOD.get(), chaosRL("apple_log"));
        this.simpleBlock(CABlocks.APPLE_PLANKS.get());
        this.logBlock(CABlocks.STRIPPED_APPLE_LOG.get());
        this.woodBlock(CABlocks.STRIPPED_APPLE_WOOD.get(), chaosRL("stripped_apple_log"));
        this.logBlock(CABlocks.CHERRY_LOG.get());
        this.woodBlock(CABlocks.CHERRY_WOOD.get(), chaosRL("cherry_log"));
        this.simpleBlock(CABlocks.CHERRY_PLANKS.get());
        this.logBlock(CABlocks.STRIPPED_CHERRY_LOG.get());
        this.woodBlock(CABlocks.STRIPPED_CHERRY_WOOD.get(), chaosRL("stripped_cherry_log"));
        this.logBlock(CABlocks.PEACH_LOG.get());
        this.woodBlock(CABlocks.PEACH_WOOD.get(), chaosRL("peach_log"));
        this.simpleBlock(CABlocks.PEACH_PLANKS.get());
        this.logBlock(CABlocks.STRIPPED_PEACH_LOG.get());
        this.woodBlock(CABlocks.STRIPPED_PEACH_WOOD.get(), chaosRL("stripped_peach_log"));
        this.logBlock(CABlocks.DUPLICATION_LOG.get());
        this.woodBlock(CABlocks.DUPLICATION_WOOD.get(), chaosRL("duplication_log"));
        this.logBlock(CABlocks.DEAD_DUPLICATION_LOG.get());
        this.woodBlock(CABlocks.DEAD_DUPLICATION_WOOD.get(), chaosRL("dead_duplication_log"));
        this.simpleBlock(CABlocks.DUPLICATION_PLANKS.get());
        this.simpleBlock(CABlocks.DUPLICATION_LEAVES.get());
        this.logBlock(CABlocks.STRIPPED_DUPLICATION_LOG.get());
        this.woodBlock(CABlocks.STRIPPED_DUPLICATION_WOOD.get(), chaosRL("stripped_duplication_log"));
        this.logBlock(CABlocks.SKYWOOD_LOG.get());
        this.woodBlock(CABlocks.SKYWOOD_WOOD.get(), chaosRL("skywood_log"));
        this.simpleBlock(CABlocks.SKYWOOD_PLANKS.get());
        this.simpleBlock(CABlocks.SKYWOOD_LEAVES.get());
        this.logBlock(CABlocks.STRIPPED_SKYWOOD_LOG.get());
        this.woodBlock(CABlocks.STRIPPED_SKYWOOD_WOOD.get(), chaosRL("stripped_skywood_log"));
        this.crystalLogBlock(CABlocks.CRYSTAL_LOG.get());
        this.crystalWoodBlock(CABlocks.CRYSTAL_WOOD.get(), chaosRL("crystal_log"));
        this.simpleBlock(CABlocks.CRYSTAL_PLANKS.get());
        this.stairsBlock(CABlocks.APPLE_STAIRS.get(), chaosRL("apple_planks"));
        this.stairsBlock(CABlocks.CHERRY_STAIRS.get(), chaosRL("cherry_planks"));
        this.stairsBlock(CABlocks.PEACH_STAIRS.get(), chaosRL("peach_planks"));
        this.stairsBlock(CABlocks.DUPLICATION_STAIRS.get(), chaosRL("duplication_planks"));
        this.stairsBlock(CABlocks.SKYWOOD_STAIRS.get(), chaosRL("skywood_planks"));
        this.stairsBlock(CABlocks.CRYSTAL_STAIRS.get(), chaosRL("crystal_planks"));
        this.slabBlock(CABlocks.APPLE_SLAB.get(), chaosRL("apple_planks"), chaosRL("apple_planks"));
        this.slabBlock(CABlocks.CHERRY_SLAB.get(), chaosRL("cherry_planks"), chaosRL("cherry_planks"));
        this.slabBlock(CABlocks.PEACH_SLAB.get(), chaosRL("peach_planks"), chaosRL("peach_planks"));
        this.slabBlock(CABlocks.DUPLICATION_SLAB.get(), chaosRL("duplication_planks"), chaosRL("duplication_planks"));
        this.slabBlock(CABlocks.SKYWOOD_SLAB.get(), chaosRL("skywood_planks"), chaosRL("skywood_planks"));
        this.slabBlock(CABlocks.CRYSTAL_SLAB.get(), chaosRL("crystal_planks"), chaosRL("crystal_planks"));
        this.fenceBlock(CABlocks.APPLE_FENCE.get(), chaosRL("apple_planks"));
        this.fenceBlock(CABlocks.CHERRY_FENCE.get(), chaosRL("cherry_planks"));
        this.fenceBlock(CABlocks.PEACH_FENCE.get(), chaosRL("peach_planks"));
        this.fenceBlock(CABlocks.DUPLICATION_FENCE.get(), chaosRL("duplication_planks"));
        this.fenceBlock(CABlocks.SKYWOOD_FENCE.get(), chaosRL("skywood_planks"));
        this.fenceBlock(CABlocks.CRYSTAL_FENCE.get(), chaosRL("crystal_planks"));
        this.fenceGateBlock(CABlocks.APPLE_FENCE_GATE.get(), chaosRL("apple_planks"));
        this.fenceGateBlock(CABlocks.CHERRY_FENCE_GATE.get(), chaosRL("cherry_planks"));
        this.fenceGateBlock(CABlocks.PEACH_FENCE_GATE.get(), chaosRL("peach_planks"));
        this.fenceGateBlock(CABlocks.DUPLICATION_FENCE_GATE.get(), chaosRL("duplication_planks"));
        this.fenceGateBlock(CABlocks.SKYWOOD_FENCE_GATE.get(), chaosRL("skywood_planks"));
        this.fenceGateBlock(CABlocks.CRYSTAL_FENCE_GATE.get(), chaosRL("crystal_planks"));
        this.pressurePlateBlock(CABlocks.APPLE_PRESSURE_PLATE.get(), "apple_pressure_plate", chaosRL("apple_planks"));
        this.pressurePlateBlock(CABlocks.CHERRY_PRESSURE_PLATE.get(), "cherry_pressure_plate", chaosRL("cherry_planks"));
        this.pressurePlateBlock(CABlocks.PEACH_PRESSURE_PLATE.get(), "peach_pressure_plate", chaosRL("peach_planks"));
        this.pressurePlateBlock(CABlocks.DUPLICATION_PRESSURE_PLATE.get(), "duplication_pressure_plate", chaosRL("duplication_planks"));
        this.pressurePlateBlock(CABlocks.SKYWOOD_PRESSURE_PLATE.get(), "skywood_pressure_plate", chaosRL("skywood_planks"));
        this.pressurePlateBlock(CABlocks.CRYSTAL_PRESSURE_PLATE.get(), "crystal_pressure_plate", chaosRL("crystal_planks"));
        this.buttonBlock(CABlocks.APPLE_BUTTON.get(), chaosRL("apple_planks"));
        this.buttonBlock(CABlocks.CHERRY_BUTTON.get(), chaosRL("cherry_planks"));
        this.buttonBlock(CABlocks.PEACH_BUTTON.get(), chaosRL("peach_planks"));
        this.buttonBlock(CABlocks.DUPLICATION_BUTTON.get(), chaosRL("duplication_planks"));
        this.buttonBlock(CABlocks.SKYWOOD_BUTTON.get(), chaosRL("skywood_planks"));
        this.buttonBlock(CABlocks.CRYSTAL_BUTTON.get(), chaosRL("crystal_planks"));

        this.cubeBottomTopBlock(CABlocks.ACACIA_GATE_BLOCK.get(), chaosRL("gate_block_acacia"), chaosRL("gate_block_top"));
        this.cubeBottomTopBlock(CABlocks.BIRCH_GATE_BLOCK.get(), chaosRL("gate_block_birch"), chaosRL("gate_block_top"));
        this.cubeBottomTopBlock(CABlocks.CRIMSON_GATE_BLOCK.get(), chaosRL("gate_block_crimson"), chaosRL("gate_block_top"));
        this.cubeBottomTopBlock(CABlocks.DARK_OAK_GATE_BLOCK.get(), chaosRL("gate_block_dark_oak"), chaosRL("gate_block_top"));
        this.cubeBottomTopBlock(CABlocks.JUNGLE_GATE_BLOCK.get(), chaosRL("gate_block_jungle"), chaosRL("gate_block_top"));
        this.cubeBottomTopBlock(CABlocks.OAK_GATE_BLOCK.get(), chaosRL("gate_block_oak"), chaosRL("gate_block_top"));
        this.cubeBottomTopBlock(CABlocks.SPRUCE_GATE_BLOCK.get(), chaosRL("gate_block_spruce"), chaosRL("gate_block_top"));
        this.cubeBottomTopBlock(CABlocks.WARPED_GATE_BLOCK.get(), chaosRL("gate_block_warped"), chaosRL("gate_block_top"));
    }

    private String name(Block block) {
        return block.getRegistryName().getPath();
    }

    private ResourceLocation extend(ResourceLocation rl, String suffix) {
        return new ResourceLocation(rl.getNamespace(), rl.getPath() + suffix);
    }

    public void cubeBottomTopBlock(GateBlock block, ResourceLocation side, ResourceLocation top) {
        cubeBottomTopBlock(block, models().cubeBottomTop(name(block), side, top, top));
    }

    public void cubeBottomTopBlock(GateBlock block, ModelFile model) {
        cubeBottomTopBlock(block, new ConfiguredModel(model));
    }

    public void cubeBottomTopBlock(GateBlock block, ConfiguredModel... model) {
        getVariantBuilder(block).partialState().addModels(model);
    }

    public void crystalLogBlock(RotatedPillarCrystalBlock block) {
        crystalAxisBlock(block, blockTexture(block), extend(blockTexture(block), "_top"));
    }

    public void crystalAxisBlock(RotatedPillarCrystalBlock block, ResourceLocation side, ResourceLocation end) {
        crystalAxisBlock(block, models().cubeColumn(name(block), side, end), models().cubeColumnHorizontal(name(block) + "_horizontal", side, end));
    }

    public void crystalAxisBlock(RotatedPillarCrystalBlock block, ModelFile vertical, ModelFile horizontal) {
        getVariantBuilder(block)
                .partialState().with(RotatedPillarCrystalBlock.AXIS, Direction.Axis.Y)
                .modelForState().modelFile(vertical).addModel()
                .partialState().with(RotatedPillarCrystalBlock.AXIS, Direction.Axis.Z)
                .modelForState().modelFile(horizontal).rotationX(90).addModel()
                .partialState().with(RotatedPillarCrystalBlock.AXIS, Direction.Axis.X)
                .modelForState().modelFile(horizontal).rotationX(90).rotationY(90).addModel();
    }

    public void crystalWoodBlock(RotatedPillarCrystalBlock block, ResourceLocation texture) {
        crystalAxisBlock(block, texture, texture);
    }

    public void woodBlock(RotatedPillarBlock block, ResourceLocation texture) {
        axisBlock(block, texture, texture);
    }

    public void pressurePlateBlock(PressurePlateBlock block, String name, ResourceLocation all) {
        pressurePlateBlockInternal(block, name, all);
    }

    private void pressurePlateBlockInternal(PressurePlateBlock block, String baseName, ResourceLocation all) {
        ModelFile pressurePlateUp = models().withExistingParent(baseName, mcRL("pressure_plate_up")).texture("texture", all);
        ModelFile pressurePlateDown = models().withExistingParent(baseName + "_down", mcRL("pressure_plate_down")).texture("texture", all);
        pressurePlateBlock(block, pressurePlateUp, pressurePlateDown);
    }

    public void pressurePlateBlock(PressurePlateBlock block, ModelFile pressurePlateUp, ModelFile pressurePlateDown) {
        getVariantBuilder(block)
                .forAllStates(state -> {
                    Boolean powered = state.getValue(PressurePlateBlock.POWERED);
                    return ConfiguredModel.builder()
                            .modelFile(powered ? pressurePlateDown : pressurePlateUp)
                            .build();
                });
    }

    public void buttonBlock(AbstractButtonBlock block, ResourceLocation textureName) {
        ModelFile unpressed = models().withExistingParent(block.getRegistryName().getPath(), "button").texture("texture", textureName);
        ModelFile pressed = models().withExistingParent(block.getRegistryName().getPath() + "_pressed", "button_pressed").texture("texture", textureName);
        ModelFile inventory = models().withExistingParent(block.getRegistryName().getPath() + "_inventory", "button_inventory").texture("texture", textureName);

        getVariantBuilder(block).forAllStates(state -> {
            int rotX = 0;
            switch (state.getValue(HorizontalFaceBlock.FACE)) {
                case CEILING:
                    rotX = 180;
                    break;
                case FLOOR:
                    rotX = 0;
                    break;
                case WALL:
                    rotX = 90;
                    break;
            }
            int rotY = 0;
            if (state.getValue(HorizontalFaceBlock.FACE) == AttachFace.CEILING)  {
                switch (state.getValue(HorizontalBlock.FACING)) {
                    case NORTH: rotY = 180; break;
                    case SOUTH: rotY = 0; break;
                    case WEST: rotY = 90; break;
                    case EAST: rotY = 270; break;
                }
            } else {
                switch (state.getValue(HorizontalBlock.FACING)) {
                    case NORTH: rotY = 0; break;
                    case SOUTH: rotY = 180; break;
                    case WEST: rotY = 270; break;
                    case EAST: rotY = 90; break;
                }
            }
            ModelFile model0 = state.getValue(AbstractButtonBlock.POWERED) ? pressed : unpressed;
            boolean uvlock = state.getValue(HorizontalFaceBlock.FACE) == AttachFace.WALL;

            return ConfiguredModel.builder()
                    .uvLock(uvlock).rotationX(rotX).rotationY(rotY).modelFile(model0)
                    .build();
        });
    }

    private ResourceLocation chaosRL(String texture) {
        return new ResourceLocation(ChaosAwakens.MODID, "block/" + texture);
    }

    private ResourceLocation mcRL(String texture) {
        return new ResourceLocation("minecraft", "block/" + texture);
    }
}