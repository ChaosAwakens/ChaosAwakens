package io.github.chaosawakens.common.registry;

import io.github.chaosawakens.ChaosAwakens;
import io.github.chaosawakens.common.blocks.tileentities.containers.DefossilizerCopperContainer;
import io.github.chaosawakens.common.blocks.tileentities.containers.DefossilizerCrystalContainer;
import io.github.chaosawakens.common.blocks.tileentities.containers.DefossilizerIronContainer;
import net.minecraft.inventory.container.Container;
import net.minecraft.inventory.container.ContainerType;
import net.minecraftforge.common.extensions.IForgeContainerType;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.fml.network.IContainerFactory;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;

public class CAContainerTypes {
	public static final DeferredRegister<ContainerType<?>> CONTAINERS = DeferredRegister.create(ForgeRegistries.CONTAINERS, ChaosAwakens.MODID);

	public static final RegistryObject<ContainerType<DefossilizerCopperContainer>> COPPER_DEFOSSILIZER = registerContainerType("copper_defossilizer", DefossilizerCopperContainer::new);
	public static final RegistryObject<ContainerType<DefossilizerIronContainer>> IRON_DEFOSSILIZER = registerContainerType("iron_defossilizer", DefossilizerIronContainer::new);
	public static final RegistryObject<ContainerType<DefossilizerCrystalContainer>> CRYSTAL_DEFOSSILIZER = registerContainerType("crystal_defossilizer", DefossilizerCrystalContainer::new);

	private static <T extends Container> RegistryObject<ContainerType<T>> registerContainerType(String name, IContainerFactory<T> factory) {
		return CONTAINERS.register(name, () -> IForgeContainerType.create(factory));
	}
}
