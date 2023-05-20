package io.github.chaosawakens.common.network.packets.s2c;

import java.util.function.Supplier;

import io.github.chaosawakens.ChaosAwakens;
import io.github.chaosawakens.api.animation.IAnimatableEntity;
import io.github.chaosawakens.api.animation.SingletonAnimationBuilder;
import io.github.chaosawakens.api.network.ICAPacket;
import io.github.chaosawakens.common.util.ObjectUtil;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.Entity;
import net.minecraft.network.PacketBuffer;
import net.minecraft.world.World;
import net.minecraftforge.fml.network.NetworkEvent.Context;

public class AnimationStopPacket implements ICAPacket {
	private final int animatableOwnerID;
	private final String animationName;
	
	public AnimationStopPacket(int animatableOwnerID, String animationName) {
		this.animatableOwnerID = animatableOwnerID;
		this.animationName = animationName;
	}
	
	public static AnimationStopPacket decode(PacketBuffer buf) {
		return new AnimationStopPacket(buf.readInt(), buf.readUtf());
	}

	@Override
	public void encode(PacketBuffer buf) {
		buf.writeInt(animatableOwnerID);
		buf.writeUtf(animationName);
	}

	@SuppressWarnings("resource")
	@Override
	public void onRecieve(Supplier<Context> ctx) {
		ctx.get().enqueueWork(() -> {
			World curWorld = Minecraft.getInstance().level;
			Entity target = curWorld.getEntity(animatableOwnerID);
			
			if (ObjectUtil.performNullityChecks(false, curWorld, target) && target instanceof IAnimatableEntity) {
				final IAnimatableEntity targetAnimatable = (IAnimatableEntity) target;
				final SingletonAnimationBuilder targetAnim = new SingletonAnimationBuilder(targetAnimatable, animationName);
				
				if (targetAnimatable.isPlayingAnimation(targetAnim)) targetAnimatable.stopAnimation(targetAnim); 
			} else ChaosAwakens.LOGGER.warn("Attempted to send AnimationStopPacket for target entity of type " + target.getClass().getSimpleName() + ", but the target entity class does not implement IAnimatableEntity!");
		});
		
		ctx.get().setPacketHandled(true);
	}

}
