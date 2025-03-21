package io.github.chaosawakens.client.renderer.passive.animal.land;

import io.github.chaosawakens.CAConstants;
import io.github.chaosawakens.client.model.passive.animal.land.StinkBugModel;
import io.github.chaosawakens.common.entity.prototype.passive.animal.land.StinkBug;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.resources.ResourceLocation;
import org.jetbrains.annotations.NotNull;

import java.util.Locale;

public class StinkBugRenderer<SB extends StinkBug> extends MobRenderer<SB, StinkBugModel<SB>> {
    public static final ResourceLocation BASE_TEXTURE = CAConstants.prefix("textures/entity/passive/animal/land/stink_bug/stink_bug_");

    public StinkBugRenderer(EntityRendererProvider.Context ctx) {
        super(ctx, new StinkBugModel<>(ctx.bakeLayer(StinkBugModel.BASE_LAYER)), 0.32F);
    }

    @Override
    public @NotNull ResourceLocation getTextureLocation(SB ownerStinkBug) {
        return BASE_TEXTURE.withSuffix(ownerStinkBug.getStinkBugType().name().toLowerCase(Locale.ROOT) + ".png");
    }
}
