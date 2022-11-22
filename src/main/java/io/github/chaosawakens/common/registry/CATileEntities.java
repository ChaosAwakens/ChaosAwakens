package io.github.chaosawakens.common.registry;

import io.github.chaosawakens.ChaosAwakens;
import io.github.chaosawakens.common.blocks.tileentities.CASignTileEntity;
import io.github.chaosawakens.common.blocks.tileentities.CrystalFurnaceTileEntity;
import io.github.chaosawakens.common.blocks.tileentities.DefossilizerCopperTileEntity;
import io.github.chaosawakens.common.blocks.tileentities.DefossilizerIronTileEntity;
import net.minecraft.tileentity.TileEntityType;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;

public class CATileEntities {
	public static final DeferredRegister<TileEntityType<?>> TILE_ENTITIES = DeferredRegister.create(ForgeRegistries.TILE_ENTITIES, ChaosAwakens.MODID);

	public static final RegistryObject<TileEntityType<CrystalFurnaceTileEntity>> CRYSTAL_FURNACE = TILE_ENTITIES.register("crystal_furnace",
			() -> TileEntityType.Builder.of(CrystalFurnaceTileEntity::new,
					CABlocks.CRYSTAL_FURNACE.get()).build(null));
	public static final RegistryObject<TileEntityType<DefossilizerCopperTileEntity>> COPPER_DEFOSSILIZER = TILE_ENTITIES.register("copper_defossilizer",
			() -> TileEntityType.Builder.of(DefossilizerCopperTileEntity::new,
					CABlocks.DEFOSSILIZER_BLOCKS.get(CABlocks.DefossilizerType.byId(CABlocks.DefossilizerType.COPPER.getId())).get()).build(null));
	public static final RegistryObject<TileEntityType<DefossilizerIronTileEntity>> IRON_DEFOSSILIZER = TILE_ENTITIES.register("iron_defossilizer",
			() -> TileEntityType.Builder.of(DefossilizerIronTileEntity::new,
					CABlocks.DEFOSSILIZER_BLOCKS.get(CABlocks.DefossilizerType.byId(CABlocks.DefossilizerType.IRON.getId())).get()).build(null));
	public static final RegistryObject<TileEntityType<CASignTileEntity>> CUSTOM_SIGN = TILE_ENTITIES.register("custom_sign",
					() -> TileEntityType.Builder.of(CASignTileEntity::new, CABlocks.APPLE_WALL_SIGN.get(),
							CABlocks.APPLE_SIGN.get(), CABlocks.CHERRY_WALL_SIGN.get(), CABlocks.CHERRY_SIGN.get(),
							CABlocks.DUPLICATION_WALL_SIGN.get(), CABlocks.DUPLICATION_SIGN.get(), CABlocks.PEACH_WALL_SIGN.get(),
									CABlocks.PEACH_SIGN.get(), CABlocks.SKYWOOD_WALL_SIGN.get(), CABlocks.SKYWOOD_SIGN.get(),
									CABlocks.GINKGO_WALL_SIGN.get(), CABlocks.GINKGO_SIGN.get()).build(null));
}
