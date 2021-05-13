package io.github.chaosawakens.tileentity.spawner;

import io.github.chaosawakens.entity.EntEntity;
import io.github.chaosawakens.registry.ModEntityTypes;
import io.github.chaosawakens.tileentity.CATileEntities;

public class TileEntityEntSpawner extends TileEntityBossSpawner<EntEntity> {

    public TileEntityEntSpawner() {
        super(CATileEntities.ENT_SPAWNER.get(), ModEntityTypes.ENT.get());
    }

    @Override
    protected int getRange() {
        return LONG_RANGE;
    }
}