package io.github.chaosawakens.api.services;

import com.google.common.collect.ImmutableMap;
import io.github.chaosawakens.CAConstants;
import io.github.chaosawakens.api.asm.ClassFinder;
import io.github.chaosawakens.api.platform.services.IRegistrar;
import it.unimi.dsi.fastutil.objects.Object2ObjectLinkedOpenHashMap;
import net.minecraft.core.Registry;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.RegistryObject;

import java.util.List;
import java.util.function.Supplier;

public class ForgeRegistrar implements IRegistrar {
    private static final Object2ObjectLinkedOpenHashMap<ResourceKey<?>, DeferredRegister<?>> CACHED_REGISTRIES = new Object2ObjectLinkedOpenHashMap<>();

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
    public <V, T extends V> RegistryObject<T> registerObject(ResourceLocation objId, Supplier<T> objSup, Registry<V> targetRegistry) {
        IEventBus modBus = FMLJavaModLoadingContext.get().getModEventBus(); // Should not be null at the time this method is called

        ResourceKey<? extends Registry<V>> targetRegistryKey = targetRegistry.key();

        DeferredRegister<V> existingDefReg = (DeferredRegister<V>) CACHED_REGISTRIES.computeIfAbsent(targetRegistryKey, defReg -> {
            DeferredRegister<V> cachedDefReg = DeferredRegister.create(targetRegistryKey, CAConstants.MODID);
            cachedDefReg.register(modBus);
            return cachedDefReg;
        });
        return existingDefReg.register(objId.getPath(), objSup);
    }

    public static ImmutableMap<ResourceKey, DeferredRegister> getCachedRegistries() {
        return ImmutableMap.copyOf(CACHED_REGISTRIES);
    }
}
