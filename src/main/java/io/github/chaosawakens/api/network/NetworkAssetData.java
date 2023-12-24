package io.github.chaosawakens.api.network;

import com.mojang.serialization.Codec;
import io.github.chaosawakens.common.codec.assets.AnimationDataCodec;
import it.unimi.dsi.fastutil.objects.Object2ObjectLinkedOpenHashMap;
import net.minecraft.util.ResourceLocation;

import java.util.Map;

public class NetworkAssetData {
    private static final Codec<Map<ResourceLocation, AnimationDataCodec>> MAPPER = Codec.unboundedMap(ResourceLocation.CODEC, AnimationDataCodec.CODEC);
    public static Map<ResourceLocation, AnimationDataCodec> SYNCED_DATA = new Object2ObjectLinkedOpenHashMap<>();

}
