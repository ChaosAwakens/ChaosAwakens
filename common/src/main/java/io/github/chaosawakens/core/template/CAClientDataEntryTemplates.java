package io.github.chaosawakens.core.template;

import com.mememan.nexus.client.entity.EntityClientData;
import io.github.chaosawakens.api.animation_resolver_system.faal.rendering.geckolib.renderer.GeckolibEntitySkeletonRenderer;
import io.github.chaosawakens.api.animation_resolver_system.prototyping.renderer.BlobRenderer;
import io.github.chaosawakens.content.entity.boss.HerculesBeetle;
import io.github.chaosawakens.content.entity.projectiles.RayGunProjectileEntity;
import io.github.chaosawakens.content.entity.projectiles.ThunderStaffProjectileEntity;
import net.minecraft.client.renderer.entity.NoopRenderer;
import net.minecraft.client.renderer.entity.ThrownItemRenderer;
import net.minecraft.world.entity.Entity;

public final class CAClientDataEntryTemplates {
    public static final EntityClientData<Entity> BLOB = new EntityClientData<>(BlobRenderer::new);
    public static final EntityClientData<Entity> NOOP = new EntityClientData<>(NoopRenderer::new);

//    public static final EntityClientData<Entity> BEDROCK_ANIMATED_ENTITY = new EntityClientData<>(ctx -> new BedrockEntitySkeletonRenderer(ctx));
    public static final EntityClientData<Entity> GECKOLIB_ANIMATED_ENTITY = new EntityClientData<>(ctx -> new GeckolibEntitySkeletonRenderer(ctx));
    public static final EntityClientData<HerculesBeetle> HERCULES_BEETLE = new EntityClientData<>(GeckolibEntitySkeletonRenderer::new);

    private CAClientDataEntryTemplates() {
        throw new IllegalAccessError("Attempted to construct instance of template class! (CAClientDataEntryTemplates)");
    }
}
