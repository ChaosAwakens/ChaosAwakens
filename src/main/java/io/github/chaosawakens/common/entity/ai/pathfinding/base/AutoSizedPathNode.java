package io.github.chaosawakens.common.entity.ai.pathfinding.base;

import io.github.chaosawakens.api.animation.IAnimatableEntity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.util.math.BlockPos;

import javax.annotation.Nonnull;

public class AutoSizedPathNode extends RefinedPathNode {
    private final IAnimatableEntity ownerEntity;
    private final LivingEntity ownerLivingEntity;

    public AutoSizedPathNode(IAnimatableEntity ownerEntity, BlockPos originPos) {
        super(((LivingEntity) ownerEntity).level, originPos);
        this.ownerEntity = ownerEntity;
        this.ownerLivingEntity = (LivingEntity) ownerEntity;
    }

    public void moveTo(@Nonnull BlockPos newOriginPos, boolean avoidNodeCollisions) {
        if (!avoidNodeCollisions) moveTo(newOriginPos);
        else {
            
        }
    }
}
