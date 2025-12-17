package io.github.chaosawakens.common.worldgen.feature.configurations;

import com.mojang.datafixers.util.Either;
import com.mojang.serialization.Codec;
import com.mojang.serialization.DataResult;
import com.mojang.serialization.DynamicOps;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.levelgen.feature.configurations.FeatureConfiguration;
import net.minecraft.world.level.levelgen.structure.templatesystem.StructureProcessorList;
import net.minecraft.world.level.levelgen.structure.templatesystem.StructureProcessorType;
import net.minecraft.world.level.levelgen.structure.templatesystem.StructureTemplate;

import java.util.Optional;

public class NBTTreeConfiguration implements FeatureConfiguration {
    public static final Codec<Either<ResourceLocation, StructureTemplate>> TEMPLATE_CODEC = Codec.of(NBTTreeConfiguration::encodeTemplate, ResourceLocation.CODEC.map(Either::left));
    public static final Codec<NBTTreeConfiguration> CODEC = RecordCodecBuilder.create(instance -> instance.group(
            TEMPLATE_CODEC.fieldOf("location").forGetter(conf -> conf.template),
            Codec.intRange(0, 32).fieldOf("ground_level").forGetter(conf -> conf.groundLevel),
            StructureProcessorType.LIST_OBJECT_CODEC.optionalFieldOf("processors").forGetter(conf -> conf.processors)
    ).apply(instance, NBTTreeConfiguration::new));

    public final Either<ResourceLocation, StructureTemplate> template;
    public final int groundLevel;
    public final Optional<StructureProcessorList> processors;
    public boolean placedFromSapling;

    public NBTTreeConfiguration(Either<ResourceLocation, StructureTemplate> template, int groundLevel, Optional<StructureProcessorList> processors) {
        this.template = template;
        this.groundLevel = groundLevel;
        this.processors = processors;
    }

    private static <T> DataResult<T> encodeTemplate(Either<ResourceLocation, StructureTemplate> either, DynamicOps<T> ops, T templateObj) {
        Optional<ResourceLocation> templateLoc = either.left();

        return templateLoc.isEmpty() ? DataResult.error(() -> "Cannot serialize a runtime pool element") : ResourceLocation.CODEC.encode(templateLoc.get(), ops, templateObj);
    }
}
