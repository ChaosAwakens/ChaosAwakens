package io.github.chaosawakens.common.blocks.tileentities.spawner;

import io.github.chaosawakens.common.entity.HerculesBeetleEntity;
import io.github.chaosawakens.common.registry.CAEntityTypes;
import io.github.chaosawakens.common.registry.CATileEntities;

public class TileEntityHerculesBeetleSpawner extends TileEntityBossSpawner<HerculesBeetleEntity> {

    public TileEntityHerculesBeetleSpawner() {
        super(CATileEntities.HERCULES_BEETLE_SPAWNER.get(), CAEntityTypes.HERCULES_BEETLE.get());
    }

    @Override
    protected int getRange() {
        return LONG_RANGE;
    }
}