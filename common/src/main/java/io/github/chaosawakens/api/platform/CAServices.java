package io.github.chaosawakens.api.platform;

import io.github.chaosawakens.CAConstants;
import io.github.chaosawakens.api.platform.services.INetworkManager;
import io.github.chaosawakens.api.platform.services.IPlatformHelper;
import io.github.chaosawakens.api.platform.services.IRegistrar;

import java.util.ServiceLoader;

public final class CAServices {
    public static final IPlatformHelper PLATFORM = load(IPlatformHelper.class);
    public static final IRegistrar REGISTRAR = load(IRegistrar.class);
    public static final INetworkManager NETWORK_MANAGER = load(INetworkManager.class);

    public static <T> T load(Class<T> clazz) {
        final T loadedService = ServiceLoader.load(clazz)
                .findFirst()
                .orElseThrow(() -> new NullPointerException("Failed to load service for " + clazz.getName()));
        CAConstants.LOGGER.debug("Loaded {} for service {}", loadedService, clazz);
        return loadedService;
    }
}