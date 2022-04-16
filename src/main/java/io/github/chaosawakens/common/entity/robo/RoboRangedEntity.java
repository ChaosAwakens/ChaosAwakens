package io.github.chaosawakens.common.entity.robo;

import net.minecraft.entity.EntityType;
import net.minecraft.entity.ai.goal.AvoidEntityGoal;
import net.minecraft.entity.monster.MonsterEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.world.World;

abstract public class RoboRangedEntity extends RoboEntity {
	public RoboRangedEntity(EntityType<? extends MonsterEntity> type, World worldIn) {
		super(type, worldIn);
	}
	
	@Override
	protected void registerGoals() {
		super.registerGoals();
		this.goalSelector.addGoal(5, new AvoidEntityGoal<>(this, PlayerEntity.class, 10.0F, 1.6, 1.2));
	}
}
