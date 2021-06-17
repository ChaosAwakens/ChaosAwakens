package io.github.chaosawakens.api;

import java.util.function.Function;

import javax.annotation.Nullable;

import net.minecraft.block.PortalInfo;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.vector.Vector3d;
import net.minecraft.world.DimensionType;
import net.minecraft.world.border.WorldBorder;
import net.minecraft.world.gen.Heightmap;
import net.minecraft.world.server.ServerWorld;
import net.minecraftforge.common.util.ITeleporter;

/**
 * @author invalid2
 *
 */
public class HeightmapTeleporter implements ITeleporter {
	
	@Nullable
	public PortalInfo getPortalInfo(Entity entity, ServerWorld targetWorld, Function<ServerWorld, PortalInfo> defaultPortalInfo) {
		WorldBorder border = targetWorld.getWorldBorder();
		double coordDiff = DimensionType.getCoordinateDifference(entity.world.getDimensionType(), targetWorld.getDimensionType());
		
		//You know how walking a block on the nether equals to X in the overworld, this is checking for it
		Vector3d newPosVector = new Vector3d(
				MathHelper.clamp(entity.getPosX() * coordDiff * 1.0, Math.max(-2.9999872E7D, border.minX() + 16.0D), Math.min(2.9999872E7D, border.maxX() - 16.0D)),
				entity.getPosY(),
				MathHelper.clamp(entity.getPosZ() * coordDiff * 1.0, Math.max(-2.9999872E7D, border.minZ() + 16.0D), Math.min(2.9999872E7D, border.maxZ() - 16.0D)));
		
		
		BlockPos entityPos = entity.getPosition();
		//Load target chunk
		targetWorld.getChunk(entityPos);
		
		//Get a valid Y pos for the targeted block
		BlockPos targetPos;
		do {
			targetPos = new BlockPos(newPosVector.getX(), targetWorld.getHeight(Heightmap.Type.WORLD_SURFACE, (int) newPosVector.getX(), (int) newPosVector.getZ()), newPosVector.getZ());
		} while(targetPos.getY() == 0);
		
		return new PortalInfo(new Vector3d(newPosVector.getX(), targetPos.getY(), newPosVector.getZ()), new Vector3d(0, 0, 0), entity.rotationYaw, entity.rotationPitch);
	}
	
	@Override
	public boolean isVanilla() { return false; }
	
	@Override
	public boolean playTeleportSound(ServerPlayerEntity player, ServerWorld sourceWorld, ServerWorld destWorld) { return false; }
}
