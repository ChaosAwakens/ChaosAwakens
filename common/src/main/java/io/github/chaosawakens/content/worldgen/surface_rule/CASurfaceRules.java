package io.github.chaosawakens.content.worldgen.surface_rule;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import io.github.chaosawakens.CAConstants;
import io.github.chaosawakens.content.registry.CABlocks;
import net.minecraft.util.KeyDispatchDataCodec;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.levelgen.Heightmap;
import net.minecraft.world.level.levelgen.SurfaceRules;
import net.minecraft.world.level.levelgen.VerticalAnchor;
import org.jetbrains.annotations.NotNull;

import static net.minecraft.world.level.levelgen.SurfaceRules.abovePreliminarySurface;

public class CASurfaceRules {

    public static final SurfaceRules.RuleSource ADD_CRYSTAL_GRASS_BLOCK_TOP =
            SurfaceRules.sequence(
                    // Top layer: grass above water, dirt underwater
                    SurfaceRules.ifTrue(
                            abovePreliminarySurface(),
                            SurfaceRules.ifTrue(
                                    SurfaceRules.not(SurfaceRules.hole()),
                                    SurfaceRules.ifTrue(
                                            CAConditionSources.AT_ABOVE_WATER_LEVEL,
                                            CAStateRules.KYANITE_GRASS
                                    )
                            )
                    )
            );

    public static final SurfaceRules.RuleSource ADD_GLOOMSTONE_LAYER =
            SurfaceRules.ifTrue(
                    SurfaceRules.verticalGradient(
                            "gloomstone_layer",
                            VerticalAnchor.absolute(
                                    0),
                            VerticalAnchor.absolute(
                                    8)),
                    CAStateRules.GLOOMSTONE);

    public static final SurfaceRules.RuleSource ADD_GRASS_BLOCK_TOP =
            SurfaceRules.sequence(
                    // Top layer: grass above water, dirt underwater
                    SurfaceRules.ifTrue(
                            abovePreliminarySurface(),
                            SurfaceRules.sequence(
                                    SurfaceRules.ifTrue(
                                            SurfaceRules.not(SurfaceRules.hole()),
                                            SurfaceRules.ifTrue(
                                                    CAConditionSources.AT_ABOVE_WATER_LEVEL,
                                                    SurfaceRules.sequence(
                                                            SurfaceRules.ifTrue(
                                                                    new DenseDirtUnderSurface(1),
                                                                    CAStateRules.DENSE_GRASS_BLOCK
                                                            ),
                                                            SurfaceRules.ifTrue(
                                                                    new DenseDirtUnderSurface(5),
                                                                    CAStateRules.DENSE_DIRT
                                                            )
                                                    )
                                            )
                                    ),
                                    SurfaceRules.ifTrue(
                                            SurfaceRules.not(CAConditionSources.AT_ABOVE_WATER_LEVEL),
                                            CAStateRules.DENSE_DIRT
                                    )
                            )
                    )
            );

    public static final SurfaceRules.RuleSource ADD_BEDROCK_LAYER =
            SurfaceRules.ifTrue(
                    SurfaceRules.verticalGradient(
                            "bedrock_floor",
                            VerticalAnchor.bottom(),
                            VerticalAnchor.aboveBottom(5)),
                    CASurfaceRules.CAStateRules.BEDROCK);


    public static class CAConditionSources {
        // General
        public static final SurfaceRules.ConditionSource AT_ABOVE_WATER_LEVEL = SurfaceRules.waterBlockCheck(0, 0);
        public static final SurfaceRules.ConditionSource GLOOMSTONE_LEVEL = SurfaceRules.DEEP_UNDER_FLOOR;
    }

    public static class CAStateRules {
        // General
        public static final SurfaceRules.RuleSource BEDROCK = SurfaceRules.state(Blocks.BEDROCK.defaultBlockState());

        // Crystal World
        public static final SurfaceRules.RuleSource KYANITE_GRASS = SurfaceRules.state(CABlocks.KYANITE_GRASS_BLOCK.get().defaultBlockState());

        // Mining Paradise
        public static final SurfaceRules.RuleSource DENSE_GRASS_BLOCK = SurfaceRules.state(CABlocks.DENSE_GRASS_BLOCK.get().defaultBlockState());
        public static final SurfaceRules.RuleSource DENSE_DIRT = SurfaceRules.state(CABlocks.DENSE_DIRT.get().defaultBlockState());
        public static final SurfaceRules.RuleSource GLOOMSTONE = SurfaceRules.state(CABlocks.GLOOMSTONE.stoneBlockFamily().get(CAConstants.prefix("gloomstone")).get().defaultBlockState());
    }

    public record DenseDirtUnderSurface(int layers) implements SurfaceRules.ConditionSource {
        public static final KeyDispatchDataCodec<DenseDirtUnderSurface> CODEC =
                KeyDispatchDataCodec.of(
                        RecordCodecBuilder.mapCodec(instance ->
                                instance.group(
                                        Codec.intRange(1, 64)
                                                .fieldOf("layers")
                                                .forGetter(DenseDirtUnderSurface::layers)
                                ).apply(instance, DenseDirtUnderSurface::new)
                        ));

        @Override
        public SurfaceRules.Condition apply(SurfaceRules.Context context) {
            return () -> {
                int x = context.blockX;
                int y = context.blockY;
                int z = context.blockZ;

                int surfaceY = context.chunk.getHeight(Heightmap.Types.WORLD_SURFACE, x, z);
                int offset = (surfaceY + 1) - y ;

                // True only for blocks 1 to N under the surface
                return offset >= 1 && offset <= layers;
            };
        }

        @Override
        public @NotNull KeyDispatchDataCodec<? extends SurfaceRules.ConditionSource> codec() {
            return CODEC;
        }
    }
}