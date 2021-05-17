package io.github.chaosawakens.common.blocks.tileentities.spawner;

import io.github.chaosawakens.common.entity.EntEntity;
import io.github.chaosawakens.common.registry.CAEntityTypes;
import io.github.chaosawakens.common.registry.CATileEntities;

public class TileEntityEntSpawner extends TileEntityBossSpawner<EntEntity> {

    public TileEntityEntSpawner() {
        super(CATileEntities.ENT_SPAWNER.get(), CAEntityTypes.ENT.get());
    }

    @Override
    protected int getRange() {
        return LONG_RANGE;
    }
}