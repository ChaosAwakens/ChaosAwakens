package io.github.chaosawakens.common.worldgen.structure.templatesystem;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import io.github.chaosawakens.common.registry.CABlocks;
import io.github.chaosawakens.common.registry.CAStructures;
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
import org.jetbrains.annotations.Nullable;

import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class MesozoicVinesProcessor extends StructureProcessor {
    public static final Codec<MesozoicVinesProcessor> CODEC = RecordCodecBuilder.create(instance -> instance.group(
                Codec.floatRange(0, 1).fieldOf("chance").forGetter(processor -> processor.chance)
            )
            .apply(instance, MesozoicVinesProcessor::new));

    public final float chance;

    public MesozoicVinesProcessor(float chance) {
        this.chance = chance;
    }

    @Override
    public @Nullable StructureTemplate.StructureBlockInfo processBlock(LevelReader reader, BlockPos origin, BlockPos origin2, StructureTemplate.StructureBlockInfo info, StructureTemplate.StructureBlockInfo info2, StructurePlaceSettings settings) {
        return info2;
    }

    @Override
    public List<StructureTemplate.StructureBlockInfo> finalizeProcessing(ServerLevelAccessor accessor, BlockPos origin, BlockPos originWithOffset, List<StructureTemplate.StructureBlockInfo> infos, List<StructureTemplate.StructureBlockInfo> infosWithOffset, StructurePlaceSettings settings) {
        Map<BlockPos, StructureTemplate.StructureBlockInfo> infoMap = infosWithOffset.stream().collect(Collectors.toMap(StructureTemplate.StructureBlockInfo::pos, item -> item));
        infosWithOffset.forEach(info -> {
            if (info.state().equals(Blocks.EMERALD_BLOCK.defaultBlockState())) {
                if (!accessor.getBlockState(info.pos()).canBeReplaced()) {
                    infoMap.remove(info.pos());
                    return;
                }
                RandomSource random = settings.getRandom(info.pos());
                if (random.nextFloat() < this.chance) {
                    int num = random.nextInt(2) + random.nextInt(3);
                    for (int i = 0; i < num; i++) {
                        if (infoMap.get(info.pos().below(i + 1)) != null || !accessor.getBlockState(info.pos().below(i + 1)).canBeReplaced()) {
                            infoMap.put(info.pos().below(i), new StructureTemplate.StructureBlockInfo(info.pos().below(i), CABlocks.MESOZOIC_VINES_HEAD.get().defaultBlockState(), new CompoundTag()));
                            return;
                        }
                        infoMap.put(info.pos().below(i), new StructureTemplate.StructureBlockInfo(info.pos().below(i), CABlocks.MESOZOIC_VINES_BODY.get().defaultBlockState(), new CompoundTag()));
                    }
                    infoMap.put(info.pos().below(num), new StructureTemplate.StructureBlockInfo(info.pos().below(num), CABlocks.MESOZOIC_VINES_HEAD.get().defaultBlockState(), new CompoundTag()));
                } else {
                    infoMap.remove(info.pos());
                }
            }
        });
        List<StructureTemplate.StructureBlockInfo> infosResult = infoMap.values().stream().sorted(Comparator.comparingInt(info -> -info.pos().getY())).toList();
        return infosResult;
    }

    @Override
    protected StructureProcessorType<?> getType() {
        return CAStructures.StructureProcessors.MESOZOIC_VINES.get();
    }
}
