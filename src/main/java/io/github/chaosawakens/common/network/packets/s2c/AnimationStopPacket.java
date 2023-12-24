package io.github.chaosawakens.common.network.packets.s2c;

import io.github.chaosawakens.ChaosAwakens;
import io.github.chaosawakens.api.animation.IAnimatableEntity;
import io.github.chaosawakens.api.animation.IAnimationBuilder;
import io.github.chaosawakens.api.network.ICAPacket;
import io.github.chaosawakens.common.util.ObjectUtil;
import net.minecraft.client.world.ClientWorld;
import net.minecraft.entity.Entity;
import net.minecraft.network.PacketBuffer;
import net.minecraft.world.World;
import net.minecraftforge.fml.LogicalSidedProvider;
import net.minecraftforge.fml.network.NetworkEvent.Context;

import java.util.Optional;
import java.util.function.Supplier;

public class AnimationStopPacket implements ICAPacket {
	private final int animatableOwnerID;
	private final String controllerName;
	private final String animationName;
	
	public AnimationStopPacket(int animatableOwnerID, String controllerName, String animationName) {
		this.animatableOwnerID = animatableOwnerID;
		this.controllerName = controllerName;
		this.animationName = animationName;
	}
	
	public static AnimationStopPacket decode(PacketBuffer buf) {
		return new AnimationStopPacket(buf.readInt(), buf.readUtf(), buf.readUtf());
	}

	@Override
	public void encode(PacketBuffer buf) {
		buf.writeInt(animatableOwnerID);
		buf.writeUtf(controllerName);
		buf.writeUtf(animationName);
	}

	@Override
	public void onRecieve(Supplier<Context> ctx) {
		ctx.get().enqueueWork(() -> {
			Optional<World> clientWorldHolder = LogicalSidedProvider.CLIENTWORLD.get(ctx.get().getDirection().getReceptionSide());
			clientWorldHolder.filter(ClientWorld.class::isInstance).ifPresent(curWorld -> {
				Entity target = curWorld.getEntity(animatableOwnerID);
				
				if (ObjectUtil.performNullityChecks(false, curWorld, target) && target instanceof IAnimatableEntity) {
					IAnimatableEntity targetAnimatable = (IAnimatableEntity) target;
					final IAnimationBuilder targetAnim = targetAnimatable.getCachedAnimationByName(animationName);
					
					if (targetAnim == null) {
						ChaosAwakens.LOGGER.warn("Attempted to send AnimationStopPacket for target entity of type {}, using an IAnimationBuilder instance for animation of name {}, but the target animation is null!", target.getClass().getSimpleName(), animationName);
						return;
					}
					
					targetAnimatable.getControllerWrapperByName(controllerName).stopAnimation(targetAnim);
					targetAnimatable.stopAnimation(targetAnim); 
				} else if (target != null) ChaosAwakens.LOGGER.warn("Attempted to send AnimationStopPacket for target entity of type {}, but the target entity class does not implement IAnimatableEntity!", target.getClass().getSimpleName());
			});
		});
		
		ctx.get().setPacketHandled(true);
	}

}
