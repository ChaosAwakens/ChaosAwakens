package io.github.chaosawakens.client.entity.model;

import io.github.chaosawakens.ChaosAwakens;
import io.github.chaosawakens.entities.EntEntity;
import net.minecraft.util.ResourceLocation;
import software.bernie.geckolib3.model.AnimatedGeoModel;

public class EntModel extends AnimatedGeoModel<EntEntity>
{
	@Override
	public ResourceLocation getModelLocation(EntEntity object)
	{
		return new ResourceLocation(ChaosAwakens.MODID, "geo/ent.geo.json");
	}

	@Override
	public ResourceLocation getTextureLocation(EntEntity object)
	{
		return new ResourceLocation(ChaosAwakens.MODID, "textures/entity/ent.png");
	}

	@Override
	public ResourceLocation getAnimationFileLocation(EntEntity object)
	{
		return new ResourceLocation(ChaosAwakens.MODID, "animations/ent/idle.json");
	}
}