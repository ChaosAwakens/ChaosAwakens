package io.github.chaosawakens.common.registry;

import io.github.chaosawakens.ChaosAwakens;
import io.github.chaosawakens.common.blocks.tileentities.DefossilizerCopperContainer;
import io.github.chaosawakens.common.blocks.tileentities.DefossilizerCopperScreen;
import io.github.chaosawakens.common.blocks.tileentities.DefossilizerIronContainer;
import io.github.chaosawakens.common.blocks.tileentities.DefossilizerIronScreen;
import net.minecraft.client.gui.ScreenManager;
import net.minecraft.inventory.container.Container;
import net.minecraft.inventory.container.ContainerType;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.common.extensions.IForgeContainerType;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import net.minecraftforge.fml.network.IContainerFactory;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;

public class CAContainerTypes {
	public static final DeferredRegister<ContainerType<?>> CONTAINERS = DeferredRegister.create(ForgeRegistries.CONTAINERS, ChaosAwakens.MODID);

	public static final RegistryObject<ContainerType<DefossilizerCopperContainer>> COPPER_DEFOSSILIZER = register("copper_defossilizer", DefossilizerCopperContainer::new);
	public static final RegistryObject<ContainerType<DefossilizerIronContainer>> IRON_DEFOSSILIZER = register("iron_defossilizer", DefossilizerIronContainer::new);

	private static <T extends Container> RegistryObject<ContainerType<T>> register(String name, IContainerFactory<T> factory) {
		return CONTAINERS.register(name, () -> IForgeContainerType.create(factory));
	}

	@OnlyIn(Dist.CLIENT)
	public static void registerScreens(FMLClientSetupEvent event) {
		ScreenManager.register(COPPER_DEFOSSILIZER.get(), DefossilizerCopperScreen::new);
		ScreenManager.register(IRON_DEFOSSILIZER.get(), DefossilizerIronScreen::new);
	}
}
