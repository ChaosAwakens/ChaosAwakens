package io.github.chaosawakens.api.animation_resolver_system.pipeline;

import io.github.chaosawakens.api.animation_resolver_system.faal.animation.Animatable;
import io.github.chaosawakens.api.animation_resolver_system.pipeline.parsing.base.model.ModelInfo;
import io.github.chaosawakens.api.animation_resolver_system.pipeline.processing.base.model.template.SkeletonTemplate;
import io.github.chaosawakens.api.animation_resolver_system.pipeline.processing.geckolib.model.GeckolibSkeleton;
import io.github.chaosawakens.api.animation_resolver_system.pipeline.processing.geckolib.model.template.GeckolibSkeletonTemplate;
import it.unimi.dsi.fastutil.objects.Object2ObjectOpenHashMap;
import org.jetbrains.annotations.NotNull;

import java.util.Map;
import java.util.Objects;

public final class ARSPipeline {
    private static final Map<ModelInfo, SkeletonTemplate> SKELETON_TEMPLATES_BY_MODEL = new Object2ObjectOpenHashMap<>();

    private ARSPipeline() {
    }

    public static @NotNull GeckolibSkeletonTemplate bakeGeckolibModelInfo(ModelInfo info) {
        Objects.requireNonNull(info, "Cannot bake GeckoLibSkeleton template from null ModelInfo!");

        return (GeckolibSkeletonTemplate) SKELETON_TEMPLATES_BY_MODEL.computeIfAbsent(info, GeckolibSkeletonTemplate::new);
    }

    public static @NotNull GeckolibSkeleton createGeckolibSkeleton(ModelInfo info) {
        return new GeckolibSkeleton(bakeGeckolibModelInfo(info));
    }

    public static @NotNull GeckolibSkeleton createGeckolibSkeleton(Animatable animatable) {
        Objects.requireNonNull(animatable, "Cannot create GeckoLibSkeleton for null Animatable!");

        return createGeckolibSkeleton(animatable.getModelInfo());
    }

    public static void invalidateGeckolibModelInfo(ModelInfo info) {
        if (info != null) SKELETON_TEMPLATES_BY_MODEL.remove(info);
    }

    public static void invalidateCache() {
        SKELETON_TEMPLATES_BY_MODEL.clear();
    }
}
