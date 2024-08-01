package io.github.chaosawakens.api.platform.services;

import io.github.chaosawakens.ChaosAwakens;
import io.github.chaosawakens.api.network.BasePacket;

/**
 * Loader-agnostic interface used for dynamically registering and sending cross-loader packets without needing multiple methods, classes, or redundant loader-specific setup.
 *
 * @see BasePacket
 */
public interface INetworkManager {

    /**
     * Main method for this service interface, called in {@link ChaosAwakens} in order to load it and its loader-specific implementations accordingly. Should NOT be called anywhere else!
     */
    void setupNetworkHandler();

    /**
     *
     * @param packet
     * @return
     * @param <MSGT>
     */
    <MSGT> INetworkManager registerPacket(BasePacket<MSGT> packet);

    <MSGT> void sendToServer(MSGT c2sPacket);

    <MSGT> void sendToClient(MSGT s2cPacket);

    <MSGT> void sendToClients(MSGT s2cPacket);
}
