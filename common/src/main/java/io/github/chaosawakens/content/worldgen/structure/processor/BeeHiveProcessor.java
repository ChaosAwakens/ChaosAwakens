package io.github.chaosawakens.content.worldgen.structure.processor;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import io.github.chaosawakens.content.registry.CAFeatures;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.ListTag;
import net.minecraft.util.RandomSource;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.block.BeehiveBlock;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.levelgen.structure.templatesystem.StructurePlaceSettings;
import net.minecraft.world.level.levelgen.structure.templatesystem.StructureProcessor;
import net.minecraft.world.level.levelgen.structure.templatesystem.StructureProcessorType;
import net.minecraft.world.level.levelgen.structure.templatesystem.StructureTemplate;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class BeeHiveProcessor extends StructureProcessor {
    public static final Codec<BeeHiveProcessor> CODEC = RecordCodecBuilder.create(instance -> instance.group(
                    Codec.floatRange(0, 1).fieldOf("chance").forGetter(processor -> processor.chance))
            .apply(instance, BeeHiveProcessor::new));

    public final float chance;

    public BeeHiveProcessor(float chance) {
        this.chance = chance;
    }

    @Override
    public @Nullable StructureTemplate.StructureBlockInfo processBlock(LevelReader reader, BlockPos offset, BlockPos pos, StructureTemplate.StructureBlockInfo info, StructureTemplate.StructureBlockInfo info2, StructurePlaceSettings settings) {
        if (info2.state().equals(Blocks.GOLD_BLOCK.defaultBlockState())) {
            RandomSource random = settings.getRandom(info2.pos());
            if (random.nextFloat() < chance) {
                CompoundTag tag = new CompoundTag();
                tag.putString("id", "minecraft:beehive");
                ListTag bees = new ListTag();

                int num = 2 + random.nextInt(2);
                for (int i = 0; i < num; i++) {
                    CompoundTag bee = new CompoundTag();

                    CompoundTag entityData = new CompoundTag();
                    entityData.putString("id", BuiltInRegistries.ENTITY_TYPE.getKey(EntityType.BEE).toString());

                    bee.put("EntityData", entityData);
                    bee.putInt("TicksInHive", random.nextInt(599));
                    bee.putInt("MinOccupationTicks", 600);
                    bees.add(bee);
                }
                tag.put("Bees", bees);

                return new StructureTemplate.StructureBlockInfo(info2.pos(), Blocks.BEE_NEST.defaultBlockState().setValue(BeehiveBlock.FACING, Direction.SOUTH), tag);
            } else
                return new StructureTemplate.StructureBlockInfo(info2.pos(), Blocks.AIR.defaultBlockState(), info2.nbt());
        }

        return info2;
    }

    @Override
    protected @NotNull StructureProcessorType<?> getType() {
        return CAFeatures.StructureProcessors.BEE_HIVE.get();
    }
}