package io.github.chaosawakens.common.registry;

import com.google.common.collect.ImmutableList;
import io.github.chaosawakens.CAConstants;
import io.github.chaosawakens.api.asm.annotations.RegistrarEntry;
import io.github.chaosawakens.api.platform.CAServices;
import it.unimi.dsi.fastutil.objects.ObjectArrayList;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.world.item.CreativeModeTab;

import java.util.function.Supplier;

@RegistrarEntry
public final class CACreativeModeTabs {
    private static final ObjectArrayList<Supplier<CreativeModeTab>> CREATIVE_MODE_TABS = new ObjectArrayList<>();

    private static Supplier<CreativeModeTab> registerCreativeModeTab(String id, Supplier<CreativeModeTab> creativeModeTabSup) {
        Supplier<CreativeModeTab> registeredCreativeModeTabSup = CAServices.REGISTRAR.registerObject(CAConstants.prefix(id), creativeModeTabSup, BuiltInRegistries.CREATIVE_MODE_TAB); // Otherwise reference to the creative mode tab sup is null cuz it needs to be registered b4hand
        CREATIVE_MODE_TABS.add(registeredCreativeModeTabSup);
        return registeredCreativeModeTabSup;
    }

    public static ImmutableList<Supplier<CreativeModeTab>> getCreativeModeTabs() {
        return ImmutableList.copyOf(CREATIVE_MODE_TABS);
    }
}
