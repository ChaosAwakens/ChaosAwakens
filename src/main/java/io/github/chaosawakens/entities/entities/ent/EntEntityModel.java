package io.github.chaosawakens.entities.entities.ent;

import io.github.chaosawakens.ChaosAwakens;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.util.Identifier;
import software.bernie.geckolib3.model.AnimatedGeoModel;

@Environment(EnvType.CLIENT)
public class EntEntityModel extends AnimatedGeoModel<EntEntity> {
    @Override
    public Identifier getModelLocation(EntEntity object) {
        return new Identifier(ChaosAwakens.modId, "geo/ent.geo.json");
    }

    @Override
    public Identifier getTextureLocation(EntEntity object) {
        return new Identifier(ChaosAwakens.modId, "textures/entity/ent.png");
    }

    @Override
    public Identifier getAnimationFileLocation(EntEntity animatable) {
        return new Identifier(ChaosAwakens.modId, "animations/ent.animation.json");
    }
}
