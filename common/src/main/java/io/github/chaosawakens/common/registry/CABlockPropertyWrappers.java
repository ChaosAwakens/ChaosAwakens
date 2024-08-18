package io.github.chaosawakens.common.registry;

import io.github.chaosawakens.api.block.standard.BlockPropertyWrapper;
import io.github.chaosawakens.util.LootUtil;
import io.github.chaosawakens.util.ModelUtil;
import io.github.chaosawakens.util.RecipeUtil;
import it.unimi.dsi.fastutil.objects.ObjectArrayList;
import net.minecraft.tags.BlockTags;
import net.minecraft.tags.ItemTags;

public final class CABlockPropertyWrappers { // Commented BMD calls out because they're meant to be examples and are actually useless in templates. Keeping them would constrain the end-developer to using setter methods rather than being free to choose between those and addendum methods

    // Basic
    public static final BlockPropertyWrapper BASIC_BLOCK = BlockPropertyWrapper.createTemplate()
            .builder()
            .withLootTable(LootUtil::dropSelf)
    //      .withCustomModelDefinition(ModelUtil.cubeAll(CAConstants.prefix("base/template")))
            .withBlockStateDefinition(ModelUtil::simpleBlock)
            .build();

    public static final BlockPropertyWrapper BASIC_BLOCK_SWORD = BlockPropertyWrapper.ofTemplate(BASIC_BLOCK)
            .cachedBuilder()
            .withTags(ObjectArrayList.of(BlockTags.SWORD_EFFICIENT))
            .build();
    public static final BlockPropertyWrapper BASIC_BLOCK_SWORD_STONE = BlockPropertyWrapper.ofTemplate(BASIC_BLOCK_SWORD)
            .cachedBuilder()
            .withTags(ObjectArrayList.of(BlockTags.NEEDS_STONE_TOOL))
            .build();
    public static final BlockPropertyWrapper BASIC_BLOCK_SWORD_IRON = BlockPropertyWrapper.ofTemplate(BASIC_BLOCK_SWORD)
            .cachedBuilder()
            .withTags(ObjectArrayList.of(BlockTags.NEEDS_IRON_TOOL))
            .build();
    public static final BlockPropertyWrapper BASIC_BLOCK_SWORD_DIAMOND = BlockPropertyWrapper.ofTemplate(BASIC_BLOCK_SWORD)
            .cachedBuilder()
            .withTags(ObjectArrayList.of(BlockTags.NEEDS_DIAMOND_TOOL))
            .build();

    public static final BlockPropertyWrapper BASIC_BLOCK_PICKAXE = BlockPropertyWrapper.ofTemplate(BASIC_BLOCK)
            .cachedBuilder()
            .withTags(ObjectArrayList.of(BlockTags.MINEABLE_WITH_PICKAXE))
            .build();
    public static final BlockPropertyWrapper BASIC_BLOCK_PICKAXE_STONE = BlockPropertyWrapper.ofTemplate(BASIC_BLOCK_PICKAXE)
            .cachedBuilder()
            .withTags(ObjectArrayList.of(BlockTags.NEEDS_STONE_TOOL))
            .build();
    public static final BlockPropertyWrapper BASIC_BLOCK_PICKAXE_IRON = BlockPropertyWrapper.ofTemplate(BASIC_BLOCK_PICKAXE)
            .cachedBuilder()
            .withTags(ObjectArrayList.of(BlockTags.NEEDS_IRON_TOOL))
            .build();
    public static final BlockPropertyWrapper BASIC_BLOCK_PICKAXE_DIAMOND = BlockPropertyWrapper.ofTemplate(BASIC_BLOCK_PICKAXE)
            .cachedBuilder()
            .withTags(ObjectArrayList.of(BlockTags.NEEDS_DIAMOND_TOOL))
            .build();

    public static final BlockPropertyWrapper BASIC_BLOCK_AXE = BlockPropertyWrapper.ofTemplate(BASIC_BLOCK)
            .cachedBuilder()
            .withTags(ObjectArrayList.of(BlockTags.MINEABLE_WITH_AXE))
            .build();
    public static final BlockPropertyWrapper BASIC_BLOCK_AXE_STONE = BlockPropertyWrapper.ofTemplate(BASIC_BLOCK_AXE)
            .cachedBuilder()
            .withTags(ObjectArrayList.of(BlockTags.NEEDS_STONE_TOOL))
            .build();
    public static final BlockPropertyWrapper BASIC_BLOCK_AXE_IRON = BlockPropertyWrapper.ofTemplate(BASIC_BLOCK_AXE)
            .cachedBuilder()
            .withTags(ObjectArrayList.of(BlockTags.NEEDS_IRON_TOOL))
            .build();
    public static final BlockPropertyWrapper BASIC_BLOCK_AXE_DIAMOND = BlockPropertyWrapper.ofTemplate(BASIC_BLOCK_AXE)
            .cachedBuilder()
            .withTags(ObjectArrayList.of(BlockTags.NEEDS_DIAMOND_TOOL))
            .build();

    public static final BlockPropertyWrapper BASIC_BLOCK_SHOVEL = BlockPropertyWrapper.ofTemplate(BASIC_BLOCK)
            .cachedBuilder()
            .withTags(ObjectArrayList.of(BlockTags.MINEABLE_WITH_SHOVEL))
            .build();
    public static final BlockPropertyWrapper BASIC_BLOCK_SHOVEL_STONE = BlockPropertyWrapper.ofTemplate(BASIC_BLOCK_SHOVEL)
            .cachedBuilder()
            .withTags(ObjectArrayList.of(BlockTags.NEEDS_STONE_TOOL))
            .build();
    public static final BlockPropertyWrapper BASIC_BLOCK_SHOVEL_IRON = BlockPropertyWrapper.ofTemplate(BASIC_BLOCK_SHOVEL)
            .cachedBuilder()
            .withTags(ObjectArrayList.of(BlockTags.NEEDS_IRON_TOOL))
            .build();
    public static final BlockPropertyWrapper BASIC_BLOCK_SHOVEL_DIAMOND = BlockPropertyWrapper.ofTemplate(BASIC_BLOCK_SHOVEL)
            .cachedBuilder()
            .withTags(ObjectArrayList.of(BlockTags.NEEDS_DIAMOND_TOOL))
            .build();

    public static final BlockPropertyWrapper BASIC_BLOCK_HOE = BlockPropertyWrapper.ofTemplate(BASIC_BLOCK)
            .cachedBuilder()
            .withTags(ObjectArrayList.of(BlockTags.MINEABLE_WITH_HOE))
            .build();
    public static final BlockPropertyWrapper BASIC_BLOCK_HOE_STONE = BlockPropertyWrapper.ofTemplate(BASIC_BLOCK_SHOVEL)
            .cachedBuilder()
            .withTags(ObjectArrayList.of(BlockTags.NEEDS_STONE_TOOL))
            .build();
    public static final BlockPropertyWrapper BASIC_BLOCK_HOE_IRON = BlockPropertyWrapper.ofTemplate(BASIC_BLOCK_SHOVEL)
            .cachedBuilder()
            .withTags(ObjectArrayList.of(BlockTags.NEEDS_IRON_TOOL))
            .build();
    public static final BlockPropertyWrapper BASIC_BLOCK_HOE_DIAMOND = BlockPropertyWrapper.ofTemplate(BASIC_BLOCK_SHOVEL)
            .cachedBuilder()
            .withTags(ObjectArrayList.of(BlockTags.NEEDS_DIAMOND_TOOL))
            .build();

    public static final BlockPropertyWrapper BASIC_ROTATED_PILLAR_BLOCK = BlockPropertyWrapper.createTemplate()
            .builder()
            .withLootTable(LootUtil::dropSelf)
     //     .withCustomModelDefinitions(ModelUtil.rotatedPillarBlock(CAConstants.prefix("base/template_side"), CAConstants.prefix("base/template_end")))
            .withBlockStateDefinition(ModelUtil::rotatedPillarBlock)
            .build();
    public static final BlockPropertyWrapper BASIC_AXIS_ALIGNED_BLOCK = BlockPropertyWrapper.createTemplate()
            .builder()
            .withLootTable(LootUtil::dropSelf)
     //     .withCustomModelDefinition(ModelUtil.cubeColumn(CAConstants.prefix("base/template")))
            .withBlockStateDefinition(ModelUtil::axisAlignedBlock)
            .build();

    // Wooden Stuff
    public static final BlockPropertyWrapper WOODEN_PLANKS = BlockPropertyWrapper.ofTemplate(BASIC_BLOCK_AXE)
            .cachedBuilder()
            .withRecipe(RecipeUtil::logToPlanks)
            .withTags(ObjectArrayList.of(BlockTags.PLANKS, ItemTags.PLANKS))
            .build();
    public static final BlockPropertyWrapper WOODEN_SLAB = BlockPropertyWrapper.createTemplate()
            .builder()
            .withLootTable(LootUtil::dropSlab)
            .withRecipe(RecipeUtil::slabsFromPlanks)
            .withTags(ObjectArrayList.of(BlockTags.WOODEN_SLABS, BlockTags.MINEABLE_WITH_AXE, ItemTags.WOODEN_SLABS))
  //        .withCustomModelDefinitions(ModelUtil.slab(CAConstants.prefix("base/template")))
            .withBlockStateDefinition(ModelUtil::woodenSlab)
            .build();

    public static final BlockPropertyWrapper WOODEN_DOOR = BlockPropertyWrapper.createTemplate()
            .builder()
            .withLootTable(LootUtil::dropDoor)
            .withRecipe(RecipeUtil::doorsFromPlanks)
            .withTags(ObjectArrayList.of(BlockTags.WOODEN_DOORS, BlockTags.MINEABLE_WITH_AXE, ItemTags.WOODEN_DOORS))
  //        .withCustomModelDefinitions(ModelUtil.door(CAConstants.prefix("base/template_door_top"), CAConstants.prefix("base/template_door_bottom")))
            .withBlockStateDefinition(ModelUtil::door)
            .build();
    public static final BlockPropertyWrapper WOODEN_TRAPDOOR = BlockPropertyWrapper.createTemplate()
            .builder()
            .withLootTable(LootUtil::dropSelf)
            .withRecipe(RecipeUtil::trapdoorsFromPlanks)
            .withTags(ObjectArrayList.of(BlockTags.WOODEN_TRAPDOORS, BlockTags.MINEABLE_WITH_AXE, ItemTags.WOODEN_TRAPDOORS))
  //        .withCustomModelDefinitions(ModelUtil.trapdoor(CAConstants.prefix("base/template_trapdoor"), CAConstants.prefix("block/template_trapdoor_bottom")))
            .withBlockStateDefinition(ModelUtil::trapdoor)
            .build();

    public static final BlockPropertyWrapper WOODEN_FENCE = BlockPropertyWrapper.createTemplate()
            .builder()
            .withLootTable(LootUtil::dropSelf)
            .withRecipe(RecipeUtil::fencesFromPlanks)
            .withTags(ObjectArrayList.of(BlockTags.WOODEN_FENCES, BlockTags.MINEABLE_WITH_AXE, ItemTags.WOODEN_FENCES))
   //       .withCustomModelDefinitions(ModelUtil.fence(CAConstants.prefix("base/template"), CAConstants.prefix("template_fence_inventory")))
            .withBlockStateDefinition(ModelUtil::fence)
            .build();
    public static final BlockPropertyWrapper WOODEN_FENCE_GATE = BlockPropertyWrapper.createTemplate()
            .builder()
            .withLootTable(LootUtil::dropSelf)
            .withRecipe(RecipeUtil::fenceGateFromPlanks)
            .withTags(ObjectArrayList.of(BlockTags.FENCE_GATES, BlockTags.MINEABLE_WITH_AXE, ItemTags.FENCE_GATES))
    //      .withCustomModelDefinitions(ModelUtil.fenceGate(CAConstants.prefix("base/template")))
            .withBlockStateDefinition(ModelUtil::fenceGate)
            .build();

    public static final BlockPropertyWrapper WOODEN_STAIRS = BlockPropertyWrapper.createTemplate()
            .builder()
            .withLootTable(LootUtil::dropSelf)
            .withRecipe(RecipeUtil::stairsFromPlanks)
            .withTags(ObjectArrayList.of(BlockTags.WOODEN_STAIRS, BlockTags.MINEABLE_WITH_AXE, ItemTags.WOODEN_STAIRS))
     //     .withCustomModelDefinitions(ModelUtil.stairs(CAConstants.prefix("base/template")))
            .withBlockStateDefinition(ModelUtil::stairs)
            .build();

    public static final BlockPropertyWrapper WOODEN_BUTTON = BlockPropertyWrapper.createTemplate()
            .builder()
            .withLootTable(LootUtil::dropSelf)
            .withRecipe(RecipeUtil::buttonFromPlank)
            .withTags(ObjectArrayList.of(BlockTags.WOODEN_BUTTONS, BlockTags.MINEABLE_WITH_AXE))
     //     .withCustomModelDefinitions(ModelUtil.button(CAConstants.prefix("base/template"), CAConstants.prefix("template_button_inventory")))
            .withBlockStateDefinition(ModelUtil::button)
            .build();
    public static final BlockPropertyWrapper WOODEN_PRESSURE_PLATE = BlockPropertyWrapper.createTemplate()
            .builder()
            .withLootTable(LootUtil::dropSelf)
            .withRecipe(RecipeUtil::pressurePlateFromPlanks)
            .withTags(ObjectArrayList.of(BlockTags.WOODEN_PRESSURE_PLATES, BlockTags.MINEABLE_WITH_AXE))
     //     .withCustomModelDefinitions(ModelUtil.pressurePlate(CAConstants.prefix("base/template")))
            .withBlockStateDefinition(ModelUtil::pressurePlate)
            .build();

    public static final BlockPropertyWrapper LOG = BlockPropertyWrapper.ofTemplate(BASIC_ROTATED_PILLAR_BLOCK)
            .cachedBuilder()
            .withTags(ObjectArrayList.of(BlockTags.MINEABLE_WITH_AXE, BlockTags.LOGS_THAT_BURN, ItemTags.LOGS_THAT_BURN))
            .build();
    public static final BlockPropertyWrapper WOOD = BlockPropertyWrapper.ofTemplate(BASIC_AXIS_ALIGNED_BLOCK)
            .cachedBuilder()
            .withRecipe(RecipeUtil::logToWood)
            .withTags(ObjectArrayList.of(BlockTags.MINEABLE_WITH_AXE, BlockTags.LOGS_THAT_BURN, ItemTags.LOGS_THAT_BURN))
            .build();
}
