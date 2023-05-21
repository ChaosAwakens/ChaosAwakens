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
import software.bernie.geckolib3.core.builder.ILoopType.EDefaultLoopTypes;

public class AnimationTriggerPacket implements ICAPacket {
	private final int animatableOwnerID;
	private final String animationName;
	private final EDefaultLoopTypes loopType;
	private final String controllerName;
	
	public AnimationTriggerPacket(int animatableOwnerID, String animationName, EDefaultLoopTypes loopType, String controllerName) {
		this.animatableOwnerID = animatableOwnerID;
		this.animationName = animationName;
		this.loopType = loopType;
		this.controllerName = controllerName;
	}
	
	public static AnimationTriggerPacket decode(PacketBuffer buf) {
		return new AnimationTriggerPacket(buf.readInt(), buf.readUtf(), utfToLoopEnum(buf.readUtf()), buf.readUtf());
	}

	@Override
	public void encode(PacketBuffer buf) {
		buf.writeInt(animatableOwnerID);
		buf.writeUtf(animationName);
		buf.writeUtf(loopType.toString());
		buf.writeUtf(controllerName);
	}
	
	private static EDefaultLoopTypes utfToLoopEnum(String name) {
		if(name.equals("LOOP"))
			return EDefaultLoopTypes.LOOP;
		else if(name.equals("HOLD_ON_LAST_FRAME"))
			return EDefaultLoopTypes.HOLD_ON_LAST_FRAME;
		else
			return EDefaultLoopTypes.PLAY_ONCE;
	}

	@SuppressWarnings("resource")
	@Override
	public void onRecieve(Supplier<Context> ctx) {
		ctx.get().enqueueWork(() -> {
			World curWorld = Minecraft.getInstance().level;
			Entity target = curWorld.getEntity(animatableOwnerID);
			
			if (ObjectUtil.performNullityChecks(false, curWorld, target) && target instanceof IAnimatableEntity) {
				IAnimatableEntity targetAnimatable = (IAnimatableEntity) target;
				final SingletonAnimationBuilder targetAnim = new SingletonAnimationBuilder(targetAnimatable, animationName, loopType).setController(targetAnimatable.getControllerByName(controllerName));
				
				targetAnimatable.playAnimation(targetAnim);
			} else if (target != null) ChaosAwakens.LOGGER.warn("Attempted to send AnimationTriggerPacket for target entity of type " + target.getClass().getSimpleName() + ", but the target entity class does not implement IAnimatableEntity!");
		});
		
		ctx.get().setPacketHandled(true);
	}

}
