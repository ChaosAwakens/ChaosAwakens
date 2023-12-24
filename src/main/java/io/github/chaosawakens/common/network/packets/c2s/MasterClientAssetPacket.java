package io.github.chaosawakens.common.network.packets.c2s;

import io.github.chaosawakens.api.network.ICAPacket;
import net.minecraft.network.PacketBuffer;
import net.minecraftforge.fml.network.NetworkEvent;

import java.util.function.Supplier;

public class MasterClientAssetPacket implements ICAPacket {


    public MasterClientAssetPacket() {

    }

    public static MasterClientAssetPacket decode(PacketBuffer buf) {
        return new MasterClientAssetPacket();
    }

    @Override
    public void encode(PacketBuffer buf) {

    }

    @Override
    public void onRecieve(Supplier<NetworkEvent.Context> ctx) {

    }
}
