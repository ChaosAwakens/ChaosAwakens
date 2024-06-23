package io.github.chaosawakens.common.entity.ai.navigation.ground.base;

import net.minecraft.entity.Entity;
import net.minecraft.entity.MobEntity;
import net.minecraft.pathfinding.GroundPathNavigator;
import net.minecraft.util.math.vector.Vector3d;
import net.minecraft.world.World;

public class BrutePathNavigator extends GroundPathNavigator {

    public BrutePathNavigator(MobEntity owner, World ownerLevel) {
        super(owner, ownerLevel);
    }

    @Override
    protected boolean canMoveDirectly(Vector3d pPosVec31, Vector3d pPosVec32, int pSizeX, int pSizeY, int pSizeZ) {
        return true;
    }
}
