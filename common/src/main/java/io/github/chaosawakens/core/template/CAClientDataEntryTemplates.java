package io.github.chaosawakens.core.template;

import com.mememan.nexus.client.entity.EntityClientData;
import io.github.chaosawakens.api.animation_resolver_system.pipeline.rendering.geckolib.renderer.GeckolibEntitySkeletonRenderer;
import io.github.chaosawakens.api.animation_resolver_system.prototyping.renderer.BlobRenderer;
import net.minecraft.world.entity.Entity;

public final class CAClientDataEntryTemplates {
    public static final EntityClientData<Entity> BLOB = new EntityClientData<>(BlobRenderer::new);

//    public static final EntityClientData<Entity> BEDROCK_ANIMATED_ENTITY = new EntityClientData<>(ctx -> new BedrockEntitySkeletonRenderer(ctx));
    public static final EntityClientData<Entity> GECKOLIB_ANIMATED_ENTITY = new EntityClientData<>(ctx -> new GeckolibEntitySkeletonRenderer(ctx));

    private CAClientDataEntryTemplates() {
        throw new IllegalAccessError("Attempted to construct instance of template class! (CAClientDataEntryTemplates)");
    }
}
