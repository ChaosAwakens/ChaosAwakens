/**
 * 
 */
package io.github.chaosawakens.common.network;

import java.io.IOException;
import java.util.List;
import java.util.function.Supplier;

import io.github.chaosawakens.ChaosAwakens;
import io.github.chaosawakens.common.entity.AnimatedMonsterEntity;
import net.minecraft.network.PacketBuffer;
import net.minecraft.network.datasync.EntityDataManager;
import net.minecraft.network.datasync.EntityDataManager.DataEntry;
import net.minecraftforge.fml.network.NetworkEvent;

/**
 * @author invalid2
 *
 */
public class PacketUpdateDataManagerValues {
	
	private List<DataEntry<?>> parameters;
	private  int entityID;
	/**
	 * 
	 */
	public PacketUpdateDataManagerValues(int entityID, List<DataEntry<?>> parameters) {
		this.entityID = entityID;
		this.parameters = parameters;
	}
	
	public PacketUpdateDataManagerValues(PacketBuffer buffer) {
		this.entityID = buffer.getInt(entityID);
		try {
			this.parameters = EntityDataManager.readEntries(buffer);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public void encode(PacketBuffer buffer) {
		buffer.writeInt(this.entityID);
		try {
			EntityDataManager.writeEntries(this.parameters, buffer);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public static class Handler {
		
		public static boolean onMessage(PacketUpdateDataManagerValues message, Supplier<NetworkEvent.Context> ctx) {
			ctx.get().enqueueWork(() -> {
				ChaosAwakens.LOGGER.debug(ctx.get());
				//GeoMonsterEntity entity = (GeoMonsterEntity) ctx.get().getSender().world.getEntityByID(message.entityID);
				//entity.getDataManager().setEntryValues(message.parameters);
			});
			return true;
		}
	}
	
}
