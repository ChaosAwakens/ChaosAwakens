package io.github.chaosawakens.common.worldgen.structure.structures;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import io.github.chaosawakens.CAConstants;
import io.github.chaosawakens.common.registry.CAStructures;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Holder;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.ChunkPos;
import net.minecraft.world.level.NoiseColumn;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.levelgen.Heightmap;
import net.minecraft.world.level.levelgen.WorldGenerationContext;
import net.minecraft.world.level.levelgen.heightproviders.HeightProvider;
import net.minecraft.world.level.levelgen.structure.Structure;
import net.minecraft.world.level.levelgen.structure.StructureType;
import net.minecraft.world.level.levelgen.structure.pools.JigsawPlacement;
import net.minecraft.world.level.levelgen.structure.pools.StructureTemplatePool;

import java.util.Optional;

public class SurfaceStructure extends Structure {
    public static Codec<SurfaceStructure> CODEC = RecordCodecBuilder.create(instance ->instance.group(
                    settingsCodec(instance),
                    StructureTemplatePool.CODEC.fieldOf("start_pool").forGetter($$0x -> $$0x.startPool),
                    ResourceLocation.CODEC.optionalFieldOf("start_jigsaw_name").forGetter($$0x -> $$0x.startJigsawName),
                    Codec.intRange(0, 7).fieldOf("size").forGetter($$0x -> $$0x.maxDepth),
                    HeightProvider.CODEC.fieldOf("start_height").forGetter($$0x -> $$0x.startHeight),
                    Codec.BOOL.fieldOf("use_expansion_hack").forGetter($$0x -> $$0x.useExpansionHack),
                    Heightmap.Types.CODEC.optionalFieldOf("project_start_to_heightmap").forGetter($$0x -> $$0x.projectStartToHeightmap),
                    Codec.intRange(1, 128).fieldOf("max_distance_from_center").forGetter($$0x -> $$0x.maxDistanceFromCenter))
            .apply(instance, SurfaceStructure::new));

    private final Holder<StructureTemplatePool> startPool;
    private final Optional<ResourceLocation> startJigsawName;
    private final int maxDepth;
    private final HeightProvider startHeight;
    private final boolean useExpansionHack;
    private final Optional<Heightmap.Types> projectStartToHeightmap;
    private final int maxDistanceFromCenter;

    public SurfaceStructure(StructureSettings settings, Holder<StructureTemplatePool> startPool, Optional<ResourceLocation> startJigsawName, int maxDepth, HeightProvider startHeight, boolean useExpansionHack, Optional<Heightmap.Types> projectStartToHeightmap, int maxDistanceFromCenter) {
        super(settings);
        this.startPool = startPool;
        this.startJigsawName = startJigsawName;
        this.maxDepth = maxDepth;
        this.startHeight = startHeight;
        this.useExpansionHack = useExpansionHack;
        this.projectStartToHeightmap = projectStartToHeightmap;
        this.maxDistanceFromCenter = maxDistanceFromCenter;
    }

    public SurfaceStructure(StructureSettings settings, Holder<StructureTemplatePool> startPool, int maxDepth, HeightProvider startHeight, boolean useExpansionHack, Heightmap.Types projectStartToHeightmap) {
        this(settings, startPool, Optional.empty(), maxDepth, startHeight, useExpansionHack, Optional.of(projectStartToHeightmap), 80);
    }

    @Override
    protected Optional<GenerationStub> findGenerationPoint(GenerationContext context) {
        ChunkPos pos = context.chunkPos();
        int y = this.startHeight.sample(context.random(), new WorldGenerationContext(context.chunkGenerator(), context.heightAccessor()));

        // Check if we are in the nether
        if (context.chunkGenerator().getFirstFreeHeight(pos.getMinBlockX(), pos.getMinBlockZ(), Heightmap.Types.WORLD_SURFACE_WG, context.heightAccessor(), context.randomState()) == 128) {
            NoiseColumn column = context.chunkGenerator().getBaseColumn(pos.getMinBlockX(), pos.getMinBlockZ(), context.heightAccessor(), context.randomState());
            BlockState currentState = column.getBlock(127);
            BlockState lastState = currentState;
            for (int i = 0; i < 127; i++) {
                currentState = column.getBlock(i);
                if (currentState.isAir() && !lastState.isAir() && lastState.getFluidState().isEmpty()) {
                    BlockPos targetPos = new BlockPos(pos.getMinBlockX(), i, pos.getMinBlockZ());
                    return JigsawPlacement.addPieces(context, this.startPool, this.startJigsawName, this.maxDepth, targetPos, this.useExpansionHack, Optional.empty(), this.maxDistanceFromCenter);
                }
                lastState = currentState;
            }
            return Optional.empty();
        }

        BlockPos targetPos = new BlockPos(pos.getMinBlockX(), y, pos.getMinBlockZ());
        return JigsawPlacement.addPieces(
                context, this.startPool, this.startJigsawName, this.maxDepth, targetPos, this.useExpansionHack, this.projectStartToHeightmap, this.maxDistanceFromCenter
        );
    }

    @Override
    public StructureType<?> type() {
        return CAStructures.StructureTypes.SURFACE_STRUCTURE.get();
    }
}
