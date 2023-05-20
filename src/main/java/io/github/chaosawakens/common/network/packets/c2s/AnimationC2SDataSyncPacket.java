package io.github.chaosawakens.common.network.packets.c2s;

import java.util.Map;
import java.util.function.Supplier;

import io.github.chaosawakens.api.animation.IAnimatableEntity;
import io.github.chaosawakens.api.animation.SingletonAnimationBuilder;
import io.github.chaosawakens.api.network.ICAPacket;
import io.github.chaosawakens.common.util.ObjectUtil;
import net.minecraft.entity.Entity;
import net.minecraft.network.PacketBuffer;
import net.minecraft.world.World;
import net.minecraftforge.fml.network.NetworkEvent.Context;
import software.bernie.geckolib3.core.AnimationState;
import software.bernie.geckolib3.core.builder.ILoopType.EDefaultLoopTypes;
import software.bernie.geckolib3.resource.GeckoLibCache;
import software.bernie.shadowed.eliotlash.mclib.math.Variable;

public class AnimationC2SDataSyncPacket implements ICAPacket {
	private final int animatableOwnerID;
	private final String controllerName;
	private final String animName;
	private final EDefaultLoopTypes loopType;
	private final double animProgress;
	private final AnimationState animState;
	
	public AnimationC2SDataSyncPacket(int animatableOwnerID, String controllerName, String animName, EDefaultLoopTypes loopType, double animProgress, AnimationState animState) {
		this.animatableOwnerID = animatableOwnerID;
		this.controllerName = controllerName;
		this.animName = animName;
		this.loopType = loopType;
		this.animProgress = animProgress;
		this.animState = animState;
	}
	
	public static AnimationC2SDataSyncPacket decode(PacketBuffer buf) {
		return new AnimationC2SDataSyncPacket(buf.readInt(), buf.readUtf(), buf.readUtf(), buf.readEnum(EDefaultLoopTypes.class), buf.readDouble(), buf.readEnum(AnimationState.class));
	}

	@Override
	public void encode(PacketBuffer buf) {
		buf.writeInt(animatableOwnerID);
		buf.writeUtf(controllerName);
		buf.writeUtf(animName);
		buf.writeEnum(loopType);
		buf.writeDouble(animProgress);
		buf.writeEnum(animState);
	}

	@Override
	public void onRecieve(Supplier<Context> ctx) {
		ctx.get().enqueueWork(() -> {
			World curWorld = ctx.get().getSender().getLevel();
			Entity target = curWorld.getEntity(animatableOwnerID);
			
			if (ObjectUtil.performNullityChecks(false, curWorld, target) && target instanceof IAnimatableEntity) {
				IAnimatableEntity targetAnimatable = (IAnimatableEntity) target;
				
				if (targetAnimatable.isPlayingAnimation()) {
					final SingletonAnimationBuilder curAnim = new SingletonAnimationBuilder(targetAnimatable, animName, loopType).setController(targetAnimatable.getControllerByName(controllerName));
					
					// Set/update anim progress
					for (Map.Entry<String, Variable> molangVar : GeckoLibCache.getInstance().parser.variables.entrySet()) {
						if (molangVar.getKey().equals("query.anim_time")) molangVar.getValue().set(animProgress);
					}
					
					// Set/update loop type
					curAnim.getAnimation().loop = loopType;
					
					// Set/update animState
					AnimationState curState = curAnim.getController().getAnimationState();
					curState = animState;
				}
			}
		});
		
		ctx.get().setPacketHandled(true);
	}
}
