package io.github.chaosawakens.tileentity;

import io.github.chaosawakens.ChaosAwakens;
import io.github.chaosawakens.registry.CABlocks;
import io.github.chaosawakens.tileentity.spawner.TileEntityEntSpawner;
import io.github.chaosawakens.tileentity.spawner.TileEntityHerculesBeetleSpawner;
import net.minecraft.tileentity.FurnaceTileEntity;
import net.minecraft.tileentity.TileEntityType;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;

public class CATileEntities {

    public static final DeferredRegister<TileEntityType<?>> TILE_ENTITIES = DeferredRegister.create(ForgeRegistries.TILE_ENTITIES, ChaosAwakens.MODID);

    public static final RegistryObject<TileEntityType<TileEntityEntSpawner>> ENT_SPAWNER = TILE_ENTITIES.register("ent_spawner", () ->
            TileEntityType.Builder.create(TileEntityEntSpawner::new, CABlocks.SPAWNER_BLOCK.get()).build(null));

    public static final RegistryObject<TileEntityType<TileEntityHerculesBeetleSpawner>> HERCULES_BEETLE_SPAWNER = TILE_ENTITIES.register("hercules_beetle_spawner", () ->
            TileEntityType.Builder.create(TileEntityHerculesBeetleSpawner::new, CABlocks.SPAWNER_BLOCK.get()).build(null));

    public static final RegistryObject<TileEntityType<CrystalFurnaceTileEntity>> CRYSTAL_FURNACE = TILE_ENTITIES.register("crystal_furnace", () ->
            TileEntityType.Builder.create(CrystalFurnaceTileEntity::new, CABlocks.CRYSTAL_FURNACE.get()).build(null));
}
