package io.github.chaosawakens.common.networking;

import io.github.chaosawakens.api.asm.annotations.NetworkRegistrarEntry;
import io.github.chaosawakens.api.network.BasePacket;
import io.github.chaosawakens.api.platform.CAServices;

@NetworkRegistrarEntry
public final class CAPackets {

    private static <MSGT> BasePacket<MSGT> registerPacket(BasePacket<MSGT> packet) {
        return CAServices.NETWORK_MANAGER.registerPacket(packet);
    }
}
