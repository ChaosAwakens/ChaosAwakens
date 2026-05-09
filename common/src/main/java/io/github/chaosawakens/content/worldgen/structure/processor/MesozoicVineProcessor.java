package io.github.chaosawakens.content.worldgen.structure.processor;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import io.github.chaosawakens.content.registry.CABlocks;
import io.github.chaosawakens.content.registry.CAFeatures;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.ServerLevelAccessor;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.levelgen.structure.templatesystem.StructurePlaceSettings;
import net.minecraft.world.level.levelgen.structure.templatesystem.StructureProcessor;
import net.minecraft.world.level.levelgen.structure.templatesystem.StructureProcessorType;
import net.minecraft.world.level.levelgen.structure.templatesystem.StructureTemplate;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class MesozoicVineProcessor extends StructureProcessor {
    public static final Codec<MesozoicVineProcessor> CODEC = RecordCodecBuilder.create(instance -> instance.group(
                    Codec.floatRange(0, 1).fieldOf("chance").forGetter(processor -> processor.chance)
            ).apply(instance, MesozoicVineProcessor::new));

    public final float chance;

    public MesozoicVineProcessor(float chance) {
        this.chance = chance;
    }

    @Override
    public @Nullable StructureTemplate.StructureBlockInfo processBlock(LevelReader reader, BlockPos pos, BlockPos pos2, StructureTemplate.StructureBlockInfo info, StructureTemplate.StructureBlockInfo info2, StructurePlaceSettings settings) {
        return info2;
    }

    public List<StructureTemplate.StructureBlockInfo> finalizeProcessing(ServerLevelAccessor accessor, BlockPos pos, BlockPos pos2, List<StructureTemplate.StructureBlockInfo> infos, List<StructureTemplate.StructureBlockInfo> infos2, StructurePlaceSettings settings) {
        Map<BlockPos, StructureTemplate.StructureBlockInfo> infoMap = infos2.stream().collect(Collectors.toMap(StructureTemplate.StructureBlockInfo::pos, item -> item));
        infos2.forEach(info -> {
            if (info.state().equals(Blocks.EMERALD_BLOCK.defaultBlockState())) {
                if (!accessor.getBlockState(info.pos()).canBeReplaced()) {
                    infoMap.put(info.pos(), new StructureTemplate.StructureBlockInfo(info.pos(), Blocks.STRUCTURE_VOID.defaultBlockState(), new CompoundTag()));
                    return;
                }
                RandomSource random = settings.getRandom(info.pos());
                if (random.nextFloat() < this.chance) {
                    int num = random.nextInt(2) + random.nextInt(3);
                    for (int i = 0; i < num; i++) {
                        if (infoMap.get(info.pos().below(i + 1)) != null || !accessor.getBlockState(info.pos().below(i + 1)).canBeReplaced()) {
                            infoMap.put(info.pos().below(i), new StructureTemplate.StructureBlockInfo(info.pos().below(i), CABlocks.MESOZOIC_VINES_BODY.get().defaultBlockState(), new CompoundTag()));
                            return;
                        }
                        infoMap.put(info.pos().below(i), new StructureTemplate.StructureBlockInfo(info.pos().below(i), CABlocks.MESOZOIC_VINES_HEAD.get().defaultBlockState(), new CompoundTag()));
                    }
                    infoMap.put(info.pos().below(num), new StructureTemplate.StructureBlockInfo(info.pos().below(num), CABlocks.MESOZOIC_VINES_BODY.get().defaultBlockState(), new CompoundTag()));
                } else {
                    infoMap.put(info.pos(), new StructureTemplate.StructureBlockInfo(info.pos(), Blocks.AIR.defaultBlockState(), new CompoundTag()));
                }
            }
        });
        return infoMap.values().stream().toList();
    }

    @Override
    protected @NotNull StructureProcessorType<?> getType() {
        return CAFeatures.StructureProcessors.MESOZOIC_VINE.get();
    }
}