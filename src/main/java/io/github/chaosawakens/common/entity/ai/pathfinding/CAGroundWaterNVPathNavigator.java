package io.github.chaosawakens.common.entity.ai.pathfinding;

import io.github.chaosawakens.api.IBlockBreakingMob;
import io.github.chaosawakens.api.IUtilityHelper;
import net.minecraft.entity.MobEntity;
import net.minecraft.pathfinding.PathNodeType;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.vector.Vector3d;
import net.minecraft.world.World;

// NV = Not vanilla, where mobs won't just "swim" as vanilla mobs do (which is just hopping around in water). This should be used for the bigger mobs that sink in
// water and move, such as the Robo Pounder --Meme Man
// TODO W.I.P
public class CAGroundWaterNVPathNavigator extends CAStrictGroundPathNavigator implements IBlockBreakingMob {

	public CAGroundWaterNVPathNavigator(MobEntity entity, World world) {
		super(entity, world);
	}
	
	@Override
	protected boolean sweepThrough(Vector3d pathVec, Vector3d center, Vector3d max) {
        float ml = (float) pathVec.length();
        
        if (ml < IUtilityHelper.EPSILON) return true;
        
        final float[] trailEdge = new float[3];
        final int[] leadEdgeI = new int[3];
        final int[] trailEdgeI = new int[3];
        final int[] step = new int[3];
        final float[] trailDelta = new float[3];
        final float[] trailNext = new float[3];
        final float[] normalised = new float[3];
        
        for (int i = 0; i < 3; i++) {
            float axis = switchAxis(pathVec, i);
            boolean validDirection = axis >= 0.0F;
            step[i] = validDirection ? 1 : -1;
            float lead = switchAxis(validDirection ? max : center, i);
            trailEdge[i] = switchAxis(validDirection ? center : max, i);
            leadEdgeI[i] = leti(lead, step[i]);
            trailEdgeI[i] = teti(trailEdge[i], step[i]);
            normalised[i] = axis / ml;
            trailDelta[i] = MathHelper.abs(ml / axis);
            float dist = validDirection ? (leadEdgeI[i] + 1 - lead) : (lead - leadEdgeI[i]);
            trailNext[i] = trailDelta[i] < Float.POSITIVE_INFINITY ? trailDelta[i] * dist : Float.POSITIVE_INFINITY;
        }
        return true;
	}
	
	@Override
	protected boolean hasValidPathType(PathNodeType type) {	
	//	if (type == PathNodeType.WATER) return false;	    
	//	else if (type == PathNodeType.LAVA) return false;	    
	//	else return type != PathNodeType.OPEN;
		return type != PathNodeType.OPEN;
	}

}
