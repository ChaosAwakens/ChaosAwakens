package io.github.chaosawakens.content.registry;

import com.google.common.collect.ImmutableList;
import com.mememan.nexus.asm.annotations.RegistrarEntry;
import com.mememan.nexus.platform.NexusServices;
import io.github.chaosawakens.CAConstants;
import io.github.chaosawakens.content.client.menu.defossilizer.CrystalDefossilizerMenu;
import io.github.chaosawakens.content.client.menu.defossilizer.IronDefossilizerMenu;
import it.unimi.dsi.fastutil.objects.ObjectArrayList;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.flag.FeatureFlags;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.MenuType;

import java.util.function.Supplier;

@RegistrarEntry
public final class CAMenuTypes {
    protected static final ObjectArrayList<Supplier<MenuType<?>>> MENU_TYPES = new ObjectArrayList<>();

    public static final Supplier<MenuType<IronDefossilizerMenu>> IRON_DEFOSSILIZER = registerMenuType("iron_defossilizer", IronDefossilizerMenu::new);
    public static final Supplier<MenuType<CrystalDefossilizerMenu>> CRYSTAL_DEFOSSILIZER = registerMenuType("crystal_defossilizer", CrystalDefossilizerMenu::new);

    private static <ACM extends AbstractContainerMenu, MT extends MenuType<ACM>> Supplier<MT> registerMenuType(ResourceLocation menuTypeId, Supplier<MT> menuTypeSup) {
        Supplier<MT> registeredMenuTypeSup = NexusServices.REGISTRAR.registerObjectAndReflect(menuTypeId, menuTypeSup, BuiltInRegistries.MENU);

        MENU_TYPES.add((Supplier<MenuType<?>>) registeredMenuTypeSup);

        return registeredMenuTypeSup;
    }

    private static <ACM extends AbstractContainerMenu, MT extends MenuType<ACM>> Supplier<MT> registerMenuType(String menuTypeId, Supplier<MT> menuTypeSup) {
        return registerMenuType(CAConstants.prefix(menuTypeId), menuTypeSup);
    }

    private static <ACM extends AbstractContainerMenu, MT extends MenuType<ACM>> Supplier<MT> registerMenuType(ResourceLocation menuTypeId, MenuType.MenuSupplier<ACM> menuTypeSup) {
        return (Supplier<MT>) registerMenuType(menuTypeId, () -> new MenuType<>(menuTypeSup, FeatureFlags.VANILLA_SET));
    }

    private static <ACM extends AbstractContainerMenu, MT extends MenuType<ACM>> Supplier<MT> registerMenuType(String menuTypeId, MenuType.MenuSupplier<ACM> menuTypeSup) {
        return registerMenuType(CAConstants.prefix(menuTypeId), menuTypeSup);
    }

    public static ImmutableList<Supplier<MenuType<?>>> getMenuTypes() {
        return ImmutableList.copyOf(MENU_TYPES);
    }
}
