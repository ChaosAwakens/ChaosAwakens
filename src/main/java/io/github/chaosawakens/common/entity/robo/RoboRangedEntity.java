package io.github.chaosawakens.common.entity.robo;

import net.minecraft.entity.EntityType;
import net.minecraft.entity.monster.MonsterEntity;
import net.minecraft.world.World;

abstract public class RoboRangedEntity extends RoboEntity {
	public RoboRangedEntity(EntityType<? extends MonsterEntity> type, World worldIn) {
		super(type, worldIn);
	}

	@Override
	protected void registerGoals() {
		super.registerGoals();
	}
	
	@Override
	public void aiStep() {
		if (getTarget() != null) {
			this.lookAt(getTarget(), 100, 100);
			this.lookControl.setLookAt(getTarget(), 30F, 30F);
		}
		super.aiStep();
	}
}
