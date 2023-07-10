package io.github.chaosawakens.common.entity.misc;

import net.minecraft.entity.EntityType;
import net.minecraft.world.World;
import software.bernie.geckolib3.core.IAnimatable;
import software.bernie.geckolib3.core.manager.AnimationData;
import software.bernie.geckolib3.core.manager.AnimationFactory;

public class JefferyShockwaveEntity extends AOEHitboxEntity implements IAnimatable {
	private final AnimationFactory factory = new AnimationFactory(this);

	public JefferyShockwaveEntity(EntityType<?> pType, World pLevel) {
		super(pType, pLevel);
	}
	
	@Override
	public void registerControllers(AnimationData data) {		
	}

	@Override
	public AnimationFactory getFactory() {
		return factory;
	}
}