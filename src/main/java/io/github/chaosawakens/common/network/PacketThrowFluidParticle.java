package io.github.chaosawakens.common.network;

import java.util.Optional;
import java.util.function.Supplier;

import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.client.world.ClientWorld;
import net.minecraft.network.PacketBuffer;
import net.minecraft.particles.BlockParticleData;
import net.minecraft.particles.ParticleTypes;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.World;
import net.minecraftforge.fml.LogicalSidedProvider;
import net.minecraftforge.fml.network.NetworkEvent;

public class PacketThrowFluidParticle {
	private final BlockState state;
	private final BlockPos pos;

	public PacketThrowFluidParticle(BlockState blockState, BlockPos blockPos) {
		this.state = blockState;
		this.pos = blockPos;
	}

	void encode(PacketBuffer buffer) {
		buffer.writeInt(Block.getId(this.state));
		buffer.writeBlockPos(this.pos);
	}

	PacketThrowFluidParticle(PacketBuffer buffer) {
		this.state = Block.stateById(buffer.readInt());
		this.pos = buffer.readBlockPos();
	}

	void handle(Supplier<NetworkEvent.Context> context) {
		context.get().enqueueWork(() -> {
			Optional<World> optionalWorld = LogicalSidedProvider.CLIENTWORLD.get(context.get().getDirection().getReceptionSide());
			optionalWorld.filter(ClientWorld.class::isInstance).ifPresent(world -> {
				this.state.getFluidState().getShape(world, this.pos).forAllBoxes((minX, minY, minZ, maxX, maxY, maxZ) -> {
					double d1 = Math.min(1.0D, maxX - minX);
					double d2 = Math.min(1.0D, maxY - minY);
					double d3 = Math.min(1.0D, maxZ - minZ);
					int i = Math.max(2, MathHelper.ceil(d1 / 0.25D));
					int j = Math.max(2, MathHelper.ceil(d2 / 0.25D));
					int k = Math.max(2, MathHelper.ceil(d3 / 0.25D));

					for (int l = 0; l < i; ++l) {
						for (int i1 = 0; i1 < j; ++i1) {
							for (int j1 = 0; j1 < k; ++j1) {
								double d4 = (l + 0.5D) / i;
								double d5 = (i1 + 0.5D) / j;
								double d6 = (j1 + 0.5D) / k;
								double d7 = d4 * d1 + minX;
								double d8 = d5 * d2 + minY;
								double d9 = d6 * d3 + minZ;
								world.addParticle(new BlockParticleData(ParticleTypes.BLOCK, this.state).setPos(this.pos), true, this.pos.getX() + d7, this.pos.above().getY() + d8, this.pos.getZ() + d9, d4 - 0.5D, d5 - 0.5D, d6 - 0.5D);
							}
						}
					}
				});
			});
		});
		context.get().setPacketHandled(true);
	}

}
