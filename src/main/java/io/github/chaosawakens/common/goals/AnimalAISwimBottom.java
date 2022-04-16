package io.github.chaosawakens.common.goals;

import net.minecraft.entity.CreatureEntity;
import net.minecraft.entity.ai.RandomPositionGenerator;
import net.minecraft.entity.ai.goal.RandomSwimmingGoal;
import net.minecraft.pathfinding.PathType;
import net.minecraft.tags.FluidTags;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.vector.Vector3d;

import javax.annotation.Nullable;
/**
 * WARNING: DO NOT USE YET AS IT CAUSES NULLPOINTEREXCEPTIONS
 * for now...
 * @author Meme Man
 */
public class AnimalAISwimBottom extends RandomSwimmingGoal {
	
	private CreatureEntity creature;
	
    public AnimalAISwimBottom(CreatureEntity creature, double d, int i) {
        super(creature, d, i);
    }

    @Nullable
    @Override
    protected Vector3d getPosition() {
        Vector3d vec = RandomPositionGenerator.getPos(this.creature, 10, 7);

        for(int var2 = 0; vec != null && !this.creature.level.getBlockState(new BlockPos(vec)).isPathfindable(this.creature.level, new BlockPos(vec), PathType.WATER) && var2++ < 10; vec = RandomPositionGenerator.getPos(this.creature, 10, 7)) {
        }
        int yDrop = 1 + this.creature.getRandom().nextInt(3);
        if(vec != null){
            BlockPos pos = new BlockPos(vec);
            while(this.creature.level.getFluidState(pos).is(FluidTags.WATER) && this.creature.level.getBlockState(pos).isPathfindable(this.creature.level, new BlockPos(vec), PathType.WATER) && pos.getY() > 1){
                pos = pos.below();
            }
            pos = pos.above();
            int yUp = 0;
            while(this.creature.level.getFluidState(pos).is(FluidTags.WATER) && this.creature.level.getBlockState(pos).isPathfindable(this.creature.level, new BlockPos(vec), PathType.WATER) && yUp < yDrop){
                pos = pos.above();
                yUp++;
            }
            return Vector3d.atCenterOf(pos);
        }

        return vec;
    }
}
