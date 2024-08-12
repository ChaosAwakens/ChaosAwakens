package io.github.chaosawakens.api.vfx.cutscenes;

import it.unimi.dsi.fastutil.objects.ObjectArrayList;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.phys.AABB;

/**
 * A class representing a minified/modified implementation of {@link AABB}, usually representing any {@link Level} space occupied by a given {@link ICutScene} instance.
 */
public class CutSceneSpace {
    protected final LevelAccessor curLevel;
    protected final double startX;
    protected final double startY;
    protected final double startZ;
    protected final double endX;
    protected final double endY;
    protected final double endZ;
    protected ObjectArrayList<Block> trackedBlocks = new ObjectArrayList<>();
    protected ObjectArrayList<BlockEntity> trackedBlockEntities = new ObjectArrayList<>();
    protected ObjectArrayList<Entity> trackedEntities = new ObjectArrayList<>();

    public CutSceneSpace(LevelAccessor curLevel, double startX, double startY, double startZ, double endX, double endY, double endZ) {
        this.curLevel = curLevel;
        this.startX = startX;
        this.startY = startY;
        this.startZ = startZ;
        this.endX = endX;
        this.endY = endY;
        this.endZ = endZ;
    }
}
