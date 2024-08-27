package io.github.chaosawakens.api.services;

import io.github.chaosawakens.api.loader.ModLoader;
import io.github.chaosawakens.api.platform.services.IPlatformHelper;
import net.fabricmc.loader.api.FabricLoader;

import java.lang.annotation.Annotation;
import java.util.List;

public class FabricPlatformHelper implements IPlatformHelper {

    @Override
    public ModLoader getPlatform() {
        return ModLoader.FABRIC;
    }

    @Override
    public boolean isModLoaded(String modId) {
        return FabricLoader.getInstance().isModLoaded(modId);
    }

    @Override
    public boolean isDevelopmentEnvironment() {
        return FabricLoader.getInstance().isDevelopmentEnvironment();
    }

    @Override
    public List<Class<?>> discoverAnnotatedClasses(Class<? extends Annotation> annotationTypeClazz) {
    /*    return FabricLoader.getInstance().getAllMods()
                .stream()
                .flatMap(a -> a.getRootPaths().stream())
                .map(); */
        return List.of();
    }
}
