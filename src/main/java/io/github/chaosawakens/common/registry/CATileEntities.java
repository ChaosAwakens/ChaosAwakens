package io.github.chaosawakens.common.registry;

import io.github.chaosawakens.ChaosAwakens;
import io.github.chaosawakens.common.blocks.tileentities.CASignTileEntity;
import io.github.chaosawakens.common.blocks.tileentities.CrystalFurnaceTileEntity;
import io.github.chaosawakens.common.blocks.tileentities.DefossilizerBlock.DefossilizerType;
import io.github.chaosawakens.common.blocks.tileentities.CopperDefossilizerTileEntity;
import io.github.chaosawakens.common.blocks.tileentities.CrystalDefossilizerTileEntity;
import io.github.chaosawakens.common.blocks.tileentities.IronDefossilizerTileEntity;
import io.github.chaosawakens.common.blocks.tileentities.RoboCrateTileEntity;
import net.minecraft.tileentity.TileEntityType;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;

public class CATileEntities {
	public static final DeferredRegister<TileEntityType<?>> TILE_ENTITIES = DeferredRegister.create(ForgeRegistries.TILE_ENTITIES, ChaosAwakens.MODID);

	// Furnaces
	public static final RegistryObject<TileEntityType<CrystalFurnaceTileEntity>> CRYSTAL_FURNACE = TILE_ENTITIES.register("crystal_furnace",
			() -> TileEntityType.Builder.of(CrystalFurnaceTileEntity::new,
					CABlocks.CRYSTAL_FURNACE.get()).build(null));
	
	// Defossilizers
	public static final RegistryObject<TileEntityType<CopperDefossilizerTileEntity>> COPPER_DEFOSSILIZER = TILE_ENTITIES.register("copper_defossilizer",
			() -> TileEntityType.Builder.of(CopperDefossilizerTileEntity::new,
					CABlocks.DEFOSSILIZER_BLOCKS.get(DefossilizerType.byId(DefossilizerType.COPPER.getId())).get()).build(null));
	public static final RegistryObject<TileEntityType<IronDefossilizerTileEntity>> IRON_DEFOSSILIZER = TILE_ENTITIES.register("iron_defossilizer",
			() -> TileEntityType.Builder.of(IronDefossilizerTileEntity::new,
					CABlocks.DEFOSSILIZER_BLOCKS.get(DefossilizerType.byId(DefossilizerType.IRON.getId())).get()).build(null));
	public static final RegistryObject<TileEntityType<CrystalDefossilizerTileEntity>> CRYSTAL_DEFOSSILIZER = TILE_ENTITIES.register("crystal_defossilizer",
			() -> TileEntityType.Builder.of(CrystalDefossilizerTileEntity::new,
					CABlocks.DEFOSSILIZER_BLOCKS.get(DefossilizerType.byId(DefossilizerType.CRYSTAL.getId())).get()).build(null));
	
	// Robo Crate
	public static final RegistryObject<TileEntityType<RoboCrateTileEntity>> ROBO_CRATE = TILE_ENTITIES.register("robo_crate",
			() -> TileEntityType.Builder.of(RoboCrateTileEntity::new,
					CABlocks.ROBO_CRATE.get()).build(null));

	// Signs
	public static final RegistryObject<TileEntityType<CASignTileEntity>> CUSTOM_SIGN = TILE_ENTITIES.register("custom_sign",
					() -> TileEntityType.Builder.of(CASignTileEntity::new, CABlocks.APPLE_WALL_SIGN.get(),
							CABlocks.APPLE_SIGN.get(), CABlocks.CHERRY_WALL_SIGN.get(), CABlocks.CHERRY_SIGN.get(), CABlocks.DENSEWOOD_WALL_SIGN.get(), CABlocks.DENSEWOOD_SIGN.get(),
							CABlocks.DUPLICATION_WALL_SIGN.get(), CABlocks.DUPLICATION_SIGN.get(), CABlocks.MESOZOIC_WALL_SIGN.get(), CABlocks.MESOZOIC_SIGN.get(),
							CABlocks.PEACH_WALL_SIGN.get(), CABlocks.PEACH_SIGN.get(), CABlocks.SKYWOOD_WALL_SIGN.get(), CABlocks.SKYWOOD_SIGN.get(),
							CABlocks.GINKGO_WALL_SIGN.get(), CABlocks.GINKGO_SIGN.get()).build(null));
}