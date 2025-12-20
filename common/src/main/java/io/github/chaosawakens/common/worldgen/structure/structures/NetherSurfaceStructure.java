package io.github.chaosawakens.common.worldgen.structure.structures;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import io.github.chaosawakens.common.registry.CAStructures;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Holder;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.ChunkPos;
import net.minecraft.world.level.NoiseColumn;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.levelgen.WorldGenerationContext;
import net.minecraft.world.level.levelgen.heightproviders.HeightProvider;
import net.minecraft.world.level.levelgen.structure.Structure;
import net.minecraft.world.level.levelgen.structure.StructureType;
import net.minecraft.world.level.levelgen.structure.pools.JigsawPlacement;
import net.minecraft.world.level.levelgen.structure.pools.StructureTemplatePool;

import java.util.Optional;

public class NetherSurfaceStructure extends Structure {
    public static Codec<NetherSurfaceStructure> CODEC = RecordCodecBuilder.create(instance ->instance.group(
                    settingsCodec(instance),
                    StructureTemplatePool.CODEC.fieldOf("start_pool").forGetter($$0x -> $$0x.startPool),
                    ResourceLocation.CODEC.optionalFieldOf("start_jigsaw_name").forGetter($$0x -> $$0x.startJigsawName),
                    Codec.intRange(0, 7).fieldOf("max_depth").forGetter($$0x -> $$0x.maxDepth),
                    HeightProvider.CODEC.fieldOf("min_height").forGetter($$0x -> $$0x.minHeight),
                    Codec.intRange(1, 128).fieldOf("max_distance_from_center").forGetter($$0x -> $$0x.maxDistanceFromCenter))
            .apply(instance, NetherSurfaceStructure::new));

    private final Holder<StructureTemplatePool> startPool;
    private final Optional<ResourceLocation> startJigsawName;
    private final int maxDepth;
    private final HeightProvider minHeight;
    private final int maxDistanceFromCenter;

    public NetherSurfaceStructure(StructureSettings settings, Holder<StructureTemplatePool> startPool, Optional<ResourceLocation> startJigsawName, int maxDepth, HeightProvider minHeight, int maxDistanceFromCenter) {
        super(settings);
        this.startPool = startPool;
        this.startJigsawName = startJigsawName;
        this.maxDepth = maxDepth;
        this.minHeight = minHeight;
        this.maxDistanceFromCenter = maxDistanceFromCenter;
    }

    public NetherSurfaceStructure(StructureSettings settings, Holder<StructureTemplatePool> startPool, int maxDepth, HeightProvider minHeight) {
        this(settings, startPool, Optional.empty(), maxDepth, minHeight, 80);
    }

    @Override
    protected Optional<GenerationStub> findGenerationPoint(GenerationContext context) {
        ChunkPos pos = context.chunkPos();
        int minSearchHeight = this.minHeight.sample(context.random(), new WorldGenerationContext(context.chunkGenerator(), context.heightAccessor()));

        Optional<Integer> targetY = this.getSurfaceHeight(context, pos.getMinBlockX(), pos.getMinBlockZ(), minSearchHeight);

        if (targetY.isEmpty()) {
            return Optional.empty();
        }

        BlockPos targetPos = new BlockPos(pos.getMinBlockX(), targetY.get(), pos.getMinBlockZ());
        return JigsawPlacement.addPieces(context, this.startPool, this.startJigsawName, this.maxDepth, targetPos, false, Optional.empty(), this.maxDistanceFromCenter);

    }

    private Optional<Integer> getSurfaceHeight(GenerationContext context, int x, int z, int minSearchHeight) {
        NoiseColumn column = context.chunkGenerator().getBaseColumn(x, z, context.heightAccessor(), context.randomState());
        BlockState currentState = column.getBlock(127);
        BlockState lastState = currentState;
        for (int i = 127; i >= minSearchHeight; i--) {
            currentState = column.getBlock(i);
            if (!currentState.isAir() && lastState.isAir() && lastState.getFluidState().isEmpty()) {
                return Optional.of(i);
            }
            lastState = currentState;
        }
        return Optional.empty();
    }

    @Override
    public StructureType<?> type() {
        return CAStructures.StructureTypes.NETHER_SURFACE_STRUCTURE.get();
    }
}
