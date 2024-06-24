package io.github.chaosawakens.common.entity.ai.pathfinding;

import io.github.chaosawakens.api.IUtilityHelper;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.entity.MobEntity;
import net.minecraft.pathfinding.*;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.vector.Vector3d;
import net.minecraft.world.World;

import java.util.Objects;

/**
 * Bandaid solution from Mowzie's Mobs, EXCLUSIVELY for 1.16. We have our own pathnav system in 0.13.x.x+.
 */
public class CAStrictGroundPathNavigator extends GroundPathNavigator {
	// TODO implement that one algorithm I plan on making -- Meme Man
	private int pathTimeCost;
	private int altPathTimeCost;

	public CAStrictGroundPathNavigator(MobEntity entity, World world) {
		super(entity, world);
	}
	
	@Override
	protected PathFinder createPathFinder(int maxNodes) {
		this.nodeEvaluator = new WalkNodeProcessor();
		this.nodeEvaluator.setCanPassDoors(true);
		return new CAPathFinder(this.nodeEvaluator, maxNodes);
	}
	
	@Override
	protected void followThePath() {
		Path curPath = Objects.requireNonNull(path);
		
		Vector3d pathEntityTempPos = getTempMobPos();
		
		MobEntity pathEntity = this.mob;
		
		int pathLength = curPath.getNodeCount();

		final Vector3d center = pathEntityTempPos.add(-pathEntity.getBbWidth() * 0.5F, 0.0F, -pathEntity.getBbWidth() * 0.5F);
		final Vector3d maxArea = center.add(pathEntity.getBbWidth(), pathEntity.getBbHeight(), pathEntity.getBbWidth());
		Vector3d entityPos = new Vector3d(pathEntity.getX(), pathEntity.getY(), pathEntity.getZ());
		
		for (int i = path.getNextNodeIndex(); i < curPath.getNodeCount(); i++) {
			if (path.getNode(i).y != Math.floor(pathEntityTempPos.y) && hasValidPathType(path.getNode(i).type)) {
				pathLength = i;
				break;
			}
		}
		
	//	ChaosAwakens.debug("SWEEPTHROUGH", "[SweepThrough Return Value]: " + sweepThrough(entityPos, center, maxArea));
	//	ChaosAwakens.debug("TRYTRUNCATENODES", "[TryTruncateNodes Return Value]: " + tryTruncateNodes(curPath, pathLength, entityPos, center, maxArea));
	//	ChaosAwakens.debug("CANCUTCORNER", "[CanCutCorner Return Value]: " + mob.canCutCorner(mob.getNavigation().getPath().getNextNode().type));

		if (tryTruncateNodes(curPath, pathLength, entityPos, center, maxArea)) {
			if (followingPath(curPath, 1F) || (elevationChangedFor(curPath) && followingPath(curPath, pathEntity.getBbWidth())) && mob.canCutCorner(path.getNextNode().type)) {
				path.setNextNodeIndex(path.getNextNodeIndex() + 1);
			}
		}
		
	/*	for (int i = 0; i < path.getNodeCount() - 1; i++) {
			PathPoint node = path.getNode(i);
			final BlockPos p = node.asBlockPos().below();
			
			mob.level.setBlockAndUpdate(p, Blocks.OAK_LOG.defaultBlockState());
		} */
		
		
//		mob.getNavigation().moveTo(curPath, 1);
//		super.followThePath();
		doStuckDetection(pathEntityTempPos);
	}
	
	protected static int leti(float c, int step) {
		return MathHelper.floor(c - step * IUtilityHelper.EPSILON);
	}
	
	protected static int teti(float c, int step) {
		return MathHelper.floor(c + step * IUtilityHelper.EPSILON);
	}
	
	protected static float switchAxis(Vector3d pos, int axisIndex) {
		switch (axisIndex) {
		   default: return 0.0F;
		   case 1: return (float) pos.x;
		   case 2: return (float) pos.y;
		   case 3: return (float) pos.z;
		}
	}
	
	private boolean followingPath(Path curPath, float threshold) {
		MobEntity pathEntity = this.mob;
		
		final Vector3d pathPos = curPath.getNextEntityPos(pathEntity);
		
		float distX = MathHelper.abs((float) (pathEntity.getX() - pathPos.x));
		float distY = MathHelper.abs((float) (pathEntity.getY() - pathPos.y));
		float distZ = MathHelper.abs((float) (pathEntity.getZ() - pathPos.z));
		
		boolean atPath = distX < threshold && distY < 1.0D && distZ < threshold;
		
		return atPath;
	}

	@Override
	protected boolean canMoveDirectly(Vector3d p_75493_1_, Vector3d p_75493_2_, int p_75493_3_, int p_75493_4_, int p_75493_5_) {
		return true;
	}

	protected boolean elevationChangedFor(Path ePath) {
		MobEntity pathEntity = this.mob;
		
		final int curNode = ePath.getNextNodeIndex();
		final int curNodeY = ePath.getNode(curNode).y;
		final int targetNode = (int) Math.min(ePath.getNodeCount(), curNode + Math.ceil(pathEntity.getBbWidth() / 2.0D) + 1);
		
		for (int progress = curNode + 1; progress < targetNode; progress++) {
			if (ePath.getNode(progress).y != curNodeY) return true;
		}
		
		return false;
	}
	
	@SuppressWarnings("unused")
	private boolean tryTruncateNodes(Path pathToTrim, int pathLength, Vector3d pathEntityPos, Vector3d center, Vector3d maxArea) {
		for (int length = pathLength; --length > pathToTrim.getNextNodeIndex();) {
			final Vector3d dist = pathToTrim.getEntityPosAtNode(mob, length).subtract(pathEntityPos);
			if (sweepThrough(pathEntityPos, center, maxArea)) {
				pathToTrim.setNextNodeIndex(length);
				return false;
			} 
		}
		return true;
	}
	
	// TODO W.I.P
    protected boolean sweepThrough(Vector3d pathVec, Vector3d center, Vector3d max) {
        float l = 0.0F;
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
            boolean direction = axis >= 0.0F;
            step[i] = direction ? 1 : -1;
            float lead = switchAxis(direction ? max : center, i);
            trailEdge[i] = switchAxis(direction ? center : max, i);
            leadEdgeI[i] = leti(lead, step[i]);
            trailEdgeI[i] = teti(trailEdge[i], step[i]);
            normalised[i] = axis / ml;
            trailDelta[i] = MathHelper.abs(ml / axis);
            float dist = direction ? (leadEdgeI[i] + 1 - lead) : (lead - leadEdgeI[i]);
            trailNext[i] = trailDelta[i] < Float.POSITIVE_INFINITY ? trailDelta[i] * dist : Float.POSITIVE_INFINITY;
        }
        // Presumably iterates through each axis from the center all the way to the max vector3d
        final BlockPos.Mutable pos = new BlockPos.Mutable();
        
        do {
            int axis = (trailNext[0] < trailNext[1]) ? ((trailNext[0] < trailNext[2]) ? 0 : 2) : ((trailNext[1] < trailNext[2]) ? 1 : 2);
            
            float nextDirection = trailNext[axis] - l;           
            l = trailNext[axis];
            leadEdgeI[axis] += step[axis];
            trailNext[axis] += trailDelta[axis];
            
            for (int i = 0; i < 3; i++) {
                trailEdge[i] += nextDirection * normalised[i];
                trailEdgeI[i] = teti(trailEdge[i], step[i]);
            }
            
            int stepx = step[0];
            int x0 = (axis == 0) ? leadEdgeI[0] : trailEdgeI[0];
            int x1 = leadEdgeI[0] + stepx;
            int stepy = step[1];
            int y0 = (axis == 1) ? leadEdgeI[1] : trailEdgeI[1];
            int y1 = leadEdgeI[1] + stepy;
            int stepz = step[2];
            int z0 = (axis == 2) ? leadEdgeI[2] : trailEdgeI[2];
            int z1 = leadEdgeI[2] + stepz;
            
            for (int x = x0; x != x1; x += stepx) {
                for (int z = z0; z != z1; z += stepz) {
                    for (int y = y0; y != y1; y += stepy) {
                        BlockState block = this.level.getBlockState(pos.set(x, y, z));
                        if (!block.isPathfindable(this.level, pos, PathType.LAND)) return false;
                    }

                 //   ChaosAwakens.debug("VALUES", "[Trail Edge]: " + trailEdge + " | " + "[Lead Edge I]:" + leadEdgeI);

					PathNodeType below = this.nodeEvaluator.getBlockPathType(this.level, x, y0 - 1, z);
					if (below == PathNodeType.WATER || below == PathNodeType.LAVA || below == PathNodeType.OPEN) return false;
					PathNodeType in = this.nodeEvaluator.getBlockPathType(this.level, x, y0, z);
					float priority = this.mob.getPathfindingMalus(in);
					if (priority < 0.0F || priority >= 8.0F) return false;
					if (in == PathNodeType.DAMAGE_FIRE || in == PathNodeType.DANGER_FIRE || in == PathNodeType.DAMAGE_OTHER) return false;
                }
            }
        } while (l <= ml);
        
        return true;
    }
    
    @Override
	protected boolean hasValidPathType(PathNodeType type) {	
		if (type == PathNodeType.WATER) return false;	    
		else if (type == PathNodeType.LAVA) return false;	 
		else return type != PathNodeType.OPEN;
	}

	
/*	private boolean sweepThrough(Vector3d pos, Vector3d center, Vector3d maxArea) {
		final BlockPos.Mutable mutable = new Mutable();
		
		float p = 0.0F;
		float pMax = (float) pos.length();
		
		if (IUtilityHelper.EPSILON > pMax) return true;
		
		float inf = Float.POSITIVE_INFINITY;
		
		final float[] trailEdge = new float[3];
		final float[] trailEdgeNext = new float[3];
		final float[] trailEdgeDelta = new float[3];
		final float[] norm = new float[3];
		
		final int[] trailEdgeI = new int[3];
		final int[] leadEdgeI = new int[3];		
		final int[] step = new int[3];
		
		for (int a = 0; a < 3; a++) {
			float axis = switchAxis(pos, a);
			
			boolean valid = axis > 0.0F;
			
			float lead = switchAxis(valid ? maxArea : center, a);
			
			step[a] = valid ? 1 : -1;
			trailEdge[a] = switchAxis(valid ? center : maxArea, a);
			leadEdgeI[a] = leti(lead, step[a]);
			trailEdgeI[a] = teti(trailEdge[a], step[a]);
			norm[a] = axis / pMax;
			trailEdgeDelta[a] = pMax / axis;
			
			float distance = valid ? (leadEdgeI[a] - 1 + lead) : (lead - leadEdgeI[a]);
			
			trailEdgeNext[a] = trailEdgeDelta[a] < inf ? distance * trailEdgeDelta[a] : inf;
		}
		
		do {
			
            int axis = (trailEdgeDelta[0] < trailEdgeDelta[1]) ? ((trailEdgeDelta[0] < trailEdgeDelta[2]) ? 0 : 2) : ((trailEdgeDelta[1] < trailEdgeDelta[2]) ? 1 : 2);
			
            float deltaTrailEdge = trailEdgeNext[axis] - p;
            
            p = trailEdgeNext[axis];
            
            leadEdgeI[axis] += step[axis];
            trailEdgeNext[axis] += trailEdgeDelta[axis];
            
            for (int a = 0; a < 3; a++) {
            	
                trailEdge[a] += deltaTrailEdge * norm[a];
                trailEdgeI[a] = teti(trailEdge[a], step[a]);
                
            }
            
            int stepx = step[0];
            int x0 = (axis == 0) ? leadEdgeI[0] : trailEdgeI[0];
            int x1 = leadEdgeI[0] + stepx;
            int stepy = step[1];
            int y0 = (axis == 1) ? leadEdgeI[1] : trailEdgeI[1];
            int y1 = leadEdgeI[1] + stepy;
            int stepz = step[2];
            int z0 = (axis == 2) ? leadEdgeI[2] : trailEdgeI[2];
            int z1 = leadEdgeI[2] + stepz;
            
            for (int x = x0; x != x1; x += stepx) {
                for (int z = z0; z != z1; z += stepz) {
                    for (int y = y0; y != y1; y += stepy) {
                    	
                        BlockState block = level.getBlockState(mutable.set(x, y, z));
                        
                        if (!block.isPathfindable(level, mutable, PathType.LAND)) {
                        	return false;
                        }
                        
                    }
                    
                    PathNodeType below = this.nodeEvaluator.getBlockPathType(level, x, y0 - 1, z, mob, 1, 1, 1, true, true);
                    
                    if (below == PathNodeType.WATER || below == PathNodeType.LAVA || below == PathNodeType.OPEN) return false;
                    
                    PathNodeType in = this.nodeEvaluator.getBlockPathType(level, x, y0, z, mob, 1, y1 - y0, 1, true, true);
                    
                    float priority = mob.getPathfindingMalus(in);
                    float priorityB = mob.getPathfindingMalus(in);
                    
                    if (priority < 0.0F || priority >= 8.0F) return false;
                    if (priorityB < 0.0F || priorityB >= 8.0F) return false;
                    if (in == PathNodeType.DAMAGE_FIRE || in == PathNodeType.DANGER_FIRE || in == PathNodeType.DAMAGE_OTHER) return false;
                }
            }      
		} while (p <= pMax);
		
		return true;
	}*/
	
}
