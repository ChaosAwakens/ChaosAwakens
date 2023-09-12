package io.github.chaosawakens.common.network.packets.s2c;

import java.util.Optional;
import java.util.function.Supplier;

import io.github.chaosawakens.ChaosAwakens;
import io.github.chaosawakens.api.animation.AnimationInformation;
import io.github.chaosawakens.api.animation.IAnimatableEntity;
import io.github.chaosawakens.api.animation.IAnimationBuilder;
import io.github.chaosawakens.api.network.ICAPacket;
import io.github.chaosawakens.common.util.ObjectUtil;
import io.github.chaosawakens.manager.CAConfigManager;
import net.minecraft.client.world.ClientWorld;
import net.minecraft.entity.Entity;
import net.minecraft.network.PacketBuffer;
import net.minecraft.world.World;
import net.minecraftforge.fml.LogicalSidedProvider;
import net.minecraftforge.fml.network.NetworkEvent.Context;

public class ServerAssetSyncPacket implements ICAPacket {
	private int targetEntityID;
	private AnimationInformation cachedAnimMetadata;
	
	public ServerAssetSyncPacket(int targetEntityID, AnimationInformation cachedAnimMetadata) {
		this.cachedAnimMetadata = cachedAnimMetadata;
	}

	public static ServerAssetSyncPacket decode(PacketBuffer buf) {
		return new ServerAssetSyncPacket(buf.readInt(), AnimationInformation.builder().setAnimationName(buf.readUtf()).setAnimationLength(buf.readDouble()).build());
	}
	
	@Override
	public void encode(PacketBuffer buf) {
		buf.writeInt(targetEntityID);
		buf.writeUtf(cachedAnimMetadata.getAnimationName());
		buf.writeDouble(cachedAnimMetadata.getAnimationLength());
	}

	@Override
	public void onRecieve(Supplier<Context> ctx) {
		ctx.get().enqueueWork(() -> {
			Optional<World> clientWorldHolder = LogicalSidedProvider.CLIENTWORLD.get(ctx.get().getDirection().getReceptionSide());
			String animationName = cachedAnimMetadata.getAnimationName();
			
			clientWorldHolder.filter(ClientWorld.class::isInstance).ifPresent(curWorld -> {
				Entity target = curWorld.getEntity(targetEntityID);
				
				if (ObjectUtil.performNullityChecks(false, curWorld, target) && target instanceof IAnimatableEntity) {
					IAnimatableEntity targetAnimatable = (IAnimatableEntity) target;
					final IAnimationBuilder targetAnim = targetAnimatable.getCachedAnimationByName(animationName);
					
					if (targetAnim == null) {
						ChaosAwakens.LOGGER.warn("Attempted to send ServerAssetSyncPacket for target entity of type {}, using an IAnimationBuilder instance for animation of name {}, but the target animation is null!", target.getClass().getSimpleName(), animationName);
						return;
					}
					
					if (CAConfigManager.MAIN_SERVER.lenientAssetEnforcement.get()) {
						
					}
				}
			});
		});
		
		ctx.get().setPacketHandled(true);
	}
}