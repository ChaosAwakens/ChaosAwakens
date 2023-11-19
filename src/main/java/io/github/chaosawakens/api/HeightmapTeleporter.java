package io.github.chaosawakens.api;

import java.util.function.Function;

import javax.annotation.Nullable;

import net.minecraft.block.PortalInfo;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.vector.Vector3d;
import net.minecraft.world.gen.Heightmap;
import net.minecraft.world.server.ServerWorld;
import net.minecraftforge.common.util.ITeleporter;

public class HeightmapTeleporter implements ITeleporter {
	
	@Nullable
	@Override
	public PortalInfo getPortalInfo(Entity entity, ServerWorld targetWorld, Function<ServerWorld, PortalInfo> defaultPortalInfo) {
		return new PortalInfo(entity.position(), Vector3d.ZERO, entity.yRot, entity.xRot);
	}

	@Override
	public boolean isVanilla() {
		return false;
	}

	@Override
	public boolean playTeleportSound(ServerPlayerEntity player, ServerWorld sourceWorld, ServerWorld destWorld) {
		return false;
	}

	@Override
	public Entity placeEntity(Entity entity, ServerWorld currentWorld, ServerWorld destWorld, float yaw, Function<Boolean, Entity> repositionEntity) {
		repositionEntity.apply(false);
		
		BlockPos curPos = entity.blockPosition();
		entity.moveTo(curPos.getX() + 0.5, destWorld.getHeight(Heightmap.Type.WORLD_SURFACE, curPos.getX(), curPos.getZ()), curPos.getZ() + 0.5);
		
		return entity;
	}
}