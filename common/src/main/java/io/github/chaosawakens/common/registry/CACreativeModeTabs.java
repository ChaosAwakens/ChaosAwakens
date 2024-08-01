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
        CREATIVE_MODE_TABS.add(creativeModeTabSup);
        return CAServices.REGISTRAR.registerObject(CAConstants.prefix(id), creativeModeTabSup, BuiltInRegistries.CREATIVE_MODE_TAB);
    }

    public static ImmutableList<Supplier<CreativeModeTab>> getCreativeModeTabs() {
        return ImmutableList.copyOf(CREATIVE_MODE_TABS);
    }
}
