package io.github.chaosawakens.common.network.packets.s2c;

import io.github.chaosawakens.api.network.ICAPacket;
import net.minecraft.network.PacketBuffer;
import net.minecraftforge.fml.network.NetworkEvent;

import java.util.function.Supplier;

public class EnforceAssetsPacket implements ICAPacket {

    public EnforceAssetsPacket() {

    }

    public static EnforceAssetsPacket decode(PacketBuffer buf) {
        return new EnforceAssetsPacket();
    }

    @Override
    public void encode(PacketBuffer buf) {

    }

    @Override
    public void onRecieve(Supplier<NetworkEvent.Context> ctx) {

    }
}
