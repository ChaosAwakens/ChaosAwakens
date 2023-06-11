package io.github.chaosawakens.client.models.entity.creature.land;

import io.github.chaosawakens.ChaosAwakens;
import io.github.chaosawakens.client.models.entity.base.ExtendedAnimatedTickingGeoModel;
import io.github.chaosawakens.common.entity.creature.land.TreeFrogEntity;
import net.minecraft.util.ResourceLocation;

public class TreeFrogEntityModel extends ExtendedAnimatedTickingGeoModel<TreeFrogEntity> {
	
	@Override
	public ResourceLocation getAnimationFileLocation(TreeFrogEntity animatable) {
		return ChaosAwakens.prefix("animations/entity/creature/land/tree_frog.animation.json");
	}
	
	@Override
	public ResourceLocation getModelLocation(TreeFrogEntity animatable) {
		return ChaosAwakens.prefix("geo/entity/creature/land/tree_frog.geo.json");
	}

	@Override
	public ResourceLocation getTextureLocation(TreeFrogEntity animatable) {
		switch (animatable.getTreeFrogType()) {
		default: return ChaosAwakens.prefix("textures/entity/creature/land/tree_frog/green_tree_frog.png");
		case 1: return ChaosAwakens.prefix("textures/entity/creature/land/tree_frog/gray_tree_frog.png");
		case 2: return ChaosAwakens.prefix("textures/entity/creature/land/tree_frog/squirrel_tree_frog.png");
		case 3: return ChaosAwakens.prefix("textures/entity/creature/land/tree_frog/pine_woods_tree_frog.png");
		case 4: return ChaosAwakens.prefix("textures/entity/creature/land/tree_frog/froakie.png");
		}
	}

	@Override
	protected boolean shouldApplyHeadRot() {
		return false;
	}

	@Override
	protected boolean shouldApplyChildScaling() {
		return false;
	}
}
