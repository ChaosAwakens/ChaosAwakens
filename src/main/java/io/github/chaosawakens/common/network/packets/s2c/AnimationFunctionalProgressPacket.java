package io.github.chaosawakens.common.network.packets.s2c;

import java.util.Optional;
import java.util.function.Supplier;

import io.github.chaosawakens.ChaosAwakens;
import io.github.chaosawakens.api.animation.IAnimatableEntity;
import io.github.chaosawakens.api.animation.WrappedAnimationController;
import io.github.chaosawakens.api.network.ICAPacket;
import io.github.chaosawakens.common.util.ObjectUtil;
import net.minecraft.client.world.ClientWorld;
import net.minecraft.entity.Entity;
import net.minecraft.network.PacketBuffer;
import net.minecraft.world.World;
import net.minecraftforge.fml.LogicalSidedProvider;
import net.minecraftforge.fml.network.NetworkEvent.Context;

public class AnimationFunctionalProgressPacket implements ICAPacket {
	private final String wrappedControllerName;
	private final int animatableOwnerID;
	private final double updatedClientProgressTicks;
	
	public AnimationFunctionalProgressPacket(String wrappedControllerName, int targetAnimatableId, double updatedClientProgressTicks) {
		this.wrappedControllerName = wrappedControllerName;
		this.animatableOwnerID = targetAnimatableId;
		this.updatedClientProgressTicks = updatedClientProgressTicks;
	}

	public static AnimationFunctionalProgressPacket decode(PacketBuffer buf) {
		return new AnimationFunctionalProgressPacket(buf.readUtf(), buf.readInt(), buf.readDouble());
	}
	
	@Override
	public void encode(PacketBuffer buf) {
		buf.writeUtf(wrappedControllerName);
		buf.writeInt(animatableOwnerID);
		buf.writeDouble(updatedClientProgressTicks);
	}

	@Override
	public void onRecieve(Supplier<Context> ctx) {
		ctx.get().enqueueWork(() -> {
			Optional<World> clientWorldHolder = LogicalSidedProvider.CLIENTWORLD.get(ctx.get().getDirection().getReceptionSide());
			
			clientWorldHolder.filter(ClientWorld.class::isInstance).ifPresent(curWorld -> {
				Entity target = curWorld.getEntity(animatableOwnerID);
				
				if (ObjectUtil.performNullityChecks(false, curWorld, target) && target instanceof IAnimatableEntity) {
					IAnimatableEntity targetAnimatable = (IAnimatableEntity) target;
					WrappedAnimationController<? extends IAnimatableEntity> targetWrappedController = targetAnimatable.getControllerWrapperByName(wrappedControllerName);
					
					if (targetWrappedController != null) targetWrappedController.updateAnimProgress(updatedClientProgressTicks);
				} else if (target != null) ChaosAwakens.LOGGER.warn("Attempted to send AnimationFunctionalProgressPacket for target entity of type {}, but the target entity class does not implement IAnimatableEntity!", target.getClass().getSimpleName());
			});
		});
		
		ctx.get().setPacketHandled(true);
	}
}
