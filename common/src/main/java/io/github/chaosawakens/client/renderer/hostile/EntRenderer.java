package io.github.chaosawakens.client.renderer.hostile;

import io.github.chaosawakens.CAConstants;
import io.github.chaosawakens.client.model.hostile.EntModel;
import io.github.chaosawakens.common.entity.prototype.hostile.Ent;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.resources.ResourceLocation;
import org.jetbrains.annotations.NotNull;

import java.util.function.Function;

public class EntRenderer<E extends Ent> extends MobRenderer<E, EntModel<E>> {
    public static final Function<Ent, ResourceLocation> TEXTURE_BY_TYPE = (ent) -> {
        String typeDescId = ent.getType().getDescriptionId();

        return CAConstants.prefix("textures/entity/hostile/ent/ent_" + typeDescId.substring(typeDescId.lastIndexOf(".") + 1, typeDescId.lastIndexOf("_")) + ".png");
    };

    public EntRenderer(EntityRendererProvider.Context ctx) {
        super(ctx, new EntModel<>(ctx.bakeLayer(EntModel.BASE_LAYER)), 1.4F);
    }

    @Override
    public @NotNull ResourceLocation getTextureLocation(E owner) {
        return TEXTURE_BY_TYPE.apply(owner);
    }
}
