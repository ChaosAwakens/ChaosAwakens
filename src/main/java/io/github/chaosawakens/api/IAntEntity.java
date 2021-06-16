package io.github.chaosawakens.api;

import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.RegistryKey;
import net.minecraft.world.World;
import net.minecraft.world.server.ServerWorld;

/**
 * Helper interface, so we dont have to code the same thing 5 times
 * @author invalid2
 */
public interface IAntEntity {
	
	static void doTeleport(ServerPlayerEntity serverPlayer, ServerWorld currentWorld, RegistryKey<World> targetKey) {
		MinecraftServer minecraftServer = currentWorld.getServer();
		ServerWorld targetWorld = minecraftServer.getWorld(targetKey);

		if (targetWorld != null)
			serverPlayer.changeDimension(targetWorld, new HeightmapTeleporter());
		
		//Old code, just in case we need a reversal
		/*serverPlayer.connection.sendPacket(new SChangeGameStatePacket(SChangeGameStatePacket.PERFORM_RESPAWN, 0));
		
		targetWorld.getChunk(playerIn.getPosition());
		serverPlayer.teleport(targetWorld, playerIn.getPosX(), targetWorld.getHeight(Heightmap.Type.WORLD_SURFACE, (int) playerIn.getPosX(), (int) playerIn.getPosZ()), playerIn.getPosZ(), serverPlayer.rotationYaw, serverPlayer.rotationPitch);
		
		serverPlayer.connection.sendPacket(new SPlayerAbilitiesPacket(serverPlayer.abilities));
		
		for (EffectInstance effectinstance : (serverPlayer.getActivePotionEffects())) {
			serverPlayer.connection.sendPacket(new SPlayEntityEffectPacket(serverPlayer.getEntityId(), effectinstance));
		}*/
	}
}
