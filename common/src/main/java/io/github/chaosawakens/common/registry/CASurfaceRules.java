package io.github.chaosawakens.common.registry;

import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.levelgen.SurfaceRules;
import net.minecraft.world.level.levelgen.VerticalAnchor;

public class CASurfaceRules {

    public static final SurfaceRules.RuleSource ADD_GLOOMSTONE_LAYER = SurfaceRules.ifTrue(SurfaceRules.verticalGradient("gloomstone_layer",VerticalAnchor.absolute(0),VerticalAnchor.absolute(8)), SurfaceRules.ifTrue(SurfaceRules.not(SurfaceRules.hole()), CAStateRules.GLOOMSTONE));
    public static final SurfaceRules.RuleSource ADD_GRASS_BLOCK_TOP =  SurfaceRules.ifTrue(SurfaceRules.abovePreliminarySurface(), SurfaceRules.ifTrue(SurfaceRules.ON_FLOOR, SurfaceRules.sequence(SurfaceRules.ifTrue(CASurfaceRules.CAConditionSources.AT_ABOVE_WATER_LEVEL, CASurfaceRules.CAStateRules.DENSE_GRASS_BLOCK), CASurfaceRules.CAStateRules.DENSE_DIRT)));
    public static final SurfaceRules.RuleSource ADD_DIRT_LAYER = SurfaceRules.ifTrue(SurfaceRules.UNDER_FLOOR, SurfaceRules.ifTrue(SurfaceRules.not(SurfaceRules.hole()), CASurfaceRules.CAStateRules.DENSE_DIRT));

    public static class CAConditionSources {
        // General
        public static final SurfaceRules.ConditionSource AT_ABOVE_WATER_LEVEL = SurfaceRules.waterBlockCheck(0, 0);
        public static final SurfaceRules.ConditionSource GLOOMSTONE_LEVEL = SurfaceRules.DEEP_UNDER_FLOOR;
    }

    public static class CAStateRules {
        // General
        public static final SurfaceRules.RuleSource BEDROCK = SurfaceRules.state(Blocks.BEDROCK.defaultBlockState());

        // Mining Paradise
        public static final SurfaceRules.RuleSource DENSE_GRASS_BLOCK = SurfaceRules.state(CABlocks.DENSE_GRASS_BLOCK.get().defaultBlockState());
        public static final SurfaceRules.RuleSource DENSE_DIRT = SurfaceRules.state(CABlocks.DENSE_DIRT.get().defaultBlockState());
        public static final SurfaceRules.RuleSource GLOOMSTONE = SurfaceRules.state(CABlocks.GLOOMSTONE.get().defaultBlockState());
    }
}
