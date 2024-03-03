package io.github.chaosawakens.common.entity.ai.pathfinding.base;

import net.minecraft.entity.LivingEntity;

public interface ISubNodeType {

    int getMalus(RefinedNodeProcessor targetProcessor);

    boolean isPassable(LivingEntity targetPathfindingEntity);
    boolean isStepable(LivingEntity targetPathfindingEntity);
    boolean isFluid();
    boolean isAir();
    boolean isSolid();
}
