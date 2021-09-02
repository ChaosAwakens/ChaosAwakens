package io.github.chaosawakens.common.registry;

import io.github.chaosawakens.ChaosAwakens;
import io.github.chaosawakens.common.blocks.tileentities.CrystalFurnaceTileEntity;
import net.minecraft.tileentity.TileEntityType;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;

public class CATileEntities {

    public static final DeferredRegister<TileEntityType<?>> TILE_ENTITIES = DeferredRegister.create(ForgeRegistries.TILE_ENTITIES, ChaosAwakens.MODID);

    public static final RegistryObject<TileEntityType<CrystalFurnaceTileEntity>> CRYSTAL_FURNACE = TILE_ENTITIES.register("crystal_furnace",
            () -> TileEntityType.Builder.of(CrystalFurnaceTileEntity::new, CABlocks.CRYSTAL_FURNACE.get()).build(null));
}
