package io.github.chaosawakens.content.registry;

import com.mememan.nexus.asm.annotations.RegistrarEntry;
import com.mememan.nexus.platform.NexusServices;
import com.mememan.nexus.resource.config.ResourceReloadListenerConfig;
import com.mememan.nexus.resource.reload_listeners.DefaultedCodecResourceReloadListener;
import com.mojang.serialization.Codec;
import io.github.chaosawakens.CAConstants;
import io.github.chaosawakens.api.animation_resolver_system.pipeline.parsing.geckolib.animation.GeckolibAnimationInfo;
import io.github.chaosawakens.api.animation_resolver_system.pipeline.parsing.geckolib.model.GeckolibModelInfo;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.packs.PackType;
import net.minecraft.server.packs.resources.PreparableReloadListener;

import java.util.Map;
import java.util.function.BiConsumer;
import java.util.function.Function;

@RegistrarEntry
public final class CAResourceReloadListeners {

    // Geckolib
    public static final DefaultedCodecResourceReloadListener<GeckolibAnimationInfo> GECKOLIB_ANIM_INFO = registerDefaultReloadListener(CAConstants.prefix("geckolib_anim_info"), new DefaultedCodecResourceReloadListener<>(CAConstants.prefix("animations/geckolib"), GeckolibAnimationInfo.CODEC));
    public static final DefaultedCodecResourceReloadListener<GeckolibModelInfo> GECKOLIB_MODEL_INFO = registerDefaultReloadListener(CAConstants.prefix("geckolib_model_info"), new DefaultedCodecResourceReloadListener<>(CAConstants.prefix("models/geckolib"), GeckolibModelInfo.CODEC));

    // Bedrock [TODO]

    private static <PRL extends PreparableReloadListener> PRL registerReloadListener(ResourceLocation listenerId, PRL listener, ResourceReloadListenerConfig<PRL> config) {
        return NexusServices.REGISTRAR.registerReloadListener(listenerId, listener, config);
    }

    private static <PRL extends PreparableReloadListener> PRL registerSidedReloadListener(ResourceLocation listenerId, PRL listener, Function<PRL, Map<ResourceLocation, ?>> dataMapGetter, Function<PRL, Codec<?>> dataCodecMapper, BiConsumer<PRL, Map<ResourceLocation, ?>> resourceSyncOperation) {
        return NexusServices.REGISTRAR.registerSyncableReloadListener(listenerId, listener, dataMapGetter, dataCodecMapper, resourceSyncOperation);
    }

    private static <T> DefaultedCodecResourceReloadListener<T> registerDefaultReloadListener(ResourceLocation listenerId, DefaultedCodecResourceReloadListener<T> listener) {
        return registerReloadListener(listenerId, listener, ResourceReloadListenerConfig.createForDefaultable(PackType.SERVER_DATA, true));
    }

    private static <PRL extends PreparableReloadListener> PRL registerServerReloadListener(ResourceLocation listenerId, PRL listener) {
        return NexusServices.REGISTRAR.registerServerReloadListener(listenerId, listener);
    }

    private static <PRL extends PreparableReloadListener> PRL registerClientReloadListener(ResourceLocation listenerId, PRL listener) {
        return NexusServices.REGISTRAR.registerClientReloadListener(listenerId, listener);
    }
}
