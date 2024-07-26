package io.github.chaosawakens.api.services;

import io.github.chaosawakens.api.asm.ClassFinder;
import io.github.chaosawakens.api.platform.services.IRegistrar;
import net.minecraft.core.Registry;
import net.minecraft.resources.ResourceLocation;

import java.util.List;
import java.util.function.Supplier;

public class FabricRegistrar implements IRegistrar {

    @Override
    public void setupRegistrar() {
        List<String> registryClasses = ClassFinder.findClasses("io.github.chaosawakens.common.registry");

        for (String registryClass : registryClasses) {
            try {
                Class.forName(registryClass);
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public <V, T extends V> Supplier<T> registerObject(ResourceLocation objId, Supplier<T> objSup, Registry<V> targetRegistry) {
        T targetObject = Registry.register(targetRegistry, objId, objSup.get());
        return () -> targetObject;
    }
}
