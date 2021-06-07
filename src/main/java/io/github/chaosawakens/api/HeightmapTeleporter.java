package io.github.chaosawakens.api;

import java.util.function.Function;

import javax.annotation.Nullable;

import io.github.chaosawakens.ChaosAwakens;
import net.minecraft.block.PortalInfo;
import net.minecraft.block.PortalSize;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.util.Direction;
import net.minecraft.util.TeleportationRepositioner;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.vector.Vector3d;
import net.minecraft.world.DimensionType;
import net.minecraft.world.border.WorldBorder;
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
		double minX = Math.max(-2.9999872E7D, border.minX() + 16.0D);
		double minZ = Math.max(-2.9999872E7D, border.minZ() + 16.0D);
		double maxX = Math.min(2.9999872E7D, border.maxX() - 16.0D);
		double maxZ = Math.min(2.9999872E7D, border.maxZ() - 16.0D);
		double coordDiff = DimensionType.getCoordinateDifference(entity.world.getDimensionType(), targetWorld.getDimensionType());
		
		//You know how walking a block on the nether equals to X in the overworld, this is checking for it
		BlockPos targetPos = new BlockPos(MathHelper.clamp(entity.getPosX() * coordDiff * 1.0, minX, maxX), entity.getPosY(), MathHelper.clamp(entity.getPosZ() * coordDiff * 1.0, minZ, maxZ));
		
		//Load target chunk
		targetWorld.getChunk(targetPos);
		Direction.Axis targetAxis;
		if(MathHelper.abs(entity.rotationPitch) > MathHelper.abs(entity.rotationYaw)) {
			targetAxis = Direction.Axis.X;
		} else {
			targetAxis = Direction.Axis.Z;
		}
		
		//Move the player to the correct Y level
		TeleportationRepositioner.Result result = TeleportationRepositioner.findLargestRectangle(targetPos, targetAxis, 2, Direction.Axis.Y, 120, (pos) -> {
			return !(targetWorld.getBlockState(pos).isAir(targetWorld, pos) && !targetWorld.getBlockState(pos.down()).isAir(targetWorld, pos.down()));
		});
		
		Vector3d motion = PortalSize.func_242973_a(result, targetAxis, entity.getPositionVec(), entity.getSize(entity.getPose()));
		//The offset inside of the block an entity had before teleporting
		Vector3d blockOffset = new Vector3d(entity.getPosX() - Math.floor(entity.getPosX()), 0, entity.getPosZ() - Math.floor(entity.getPosZ()));
		ChaosAwakens.LOGGER.debug(blockOffset.getX()+" "+entity.getPosY()+" "+result.startPos+" "+result.height);
		
		return new PortalInfo(new Vector3d(targetPos.getX() + blockOffset.getX(), result.startPos.getY() > 0 ? result.startPos.getY() - 2 : result.startPos.getY() + result.height, targetPos.getZ() + blockOffset.getZ()), motion, entity.rotationYaw, entity.rotationPitch);
	}
	
	@Override
	public boolean isVanilla() {
		return false;
	}
	
	@Override
	public boolean playTeleportSound(ServerPlayerEntity player, ServerWorld sourceWorld, ServerWorld destWorld) {
		return false;
	}
}
