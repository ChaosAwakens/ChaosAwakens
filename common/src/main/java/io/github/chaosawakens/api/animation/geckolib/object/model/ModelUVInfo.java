package io.github.chaosawakens.api.animation.geckolib.object.model;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;

public record ModelUVInfo(ModelDirectionBasedUVInfo northUVInfo, ModelDirectionBasedUVInfo eastUVInfo, ModelDirectionBasedUVInfo southUVInfo, ModelDirectionBasedUVInfo westUVInfo, ModelDirectionBasedUVInfo upUVInfo, ModelDirectionBasedUVInfo downUVInfo) { // We're hitting JIC territory, but we might as well have texture info for when (if) we ever need it + hardcoded parameter count cuz no need for weird array shenanigans
    public static final Codec<ModelUVInfo> CODEC = RecordCodecBuilder.create(instance -> instance.group(
            ModelDirectionBasedUVInfo.CODEC.fieldOf("north").forGetter(ModelUVInfo::northUVInfo),
            ModelDirectionBasedUVInfo.CODEC.fieldOf("east").forGetter(ModelUVInfo::eastUVInfo),
            ModelDirectionBasedUVInfo.CODEC.fieldOf("south").forGetter(ModelUVInfo::southUVInfo),
            ModelDirectionBasedUVInfo.CODEC.fieldOf("west").forGetter(ModelUVInfo::westUVInfo),
            ModelDirectionBasedUVInfo.CODEC.fieldOf("up").forGetter(ModelUVInfo::upUVInfo),
            ModelDirectionBasedUVInfo.CODEC.fieldOf("down").forGetter(ModelUVInfo::downUVInfo)
    ).apply(instance, ModelUVInfo::new));
}
