package io.github.chaosawakens.common.enums;

import net.minecraft.block.SkullBlock;
import net.minecraft.util.IStringSerializable;

import javax.annotation.Nullable;

import io.github.chaosawakens.common.blocks.tileentities.spawner.TileEntityBossSpawner;
import io.github.chaosawakens.common.blocks.tileentities.spawner.TileEntityEntSpawner;
import io.github.chaosawakens.common.blocks.tileentities.spawner.TileEntityHerculesBeetleSpawner;

import java.util.Locale;
import java.util.function.Supplier;

public enum BossVariant implements IStringSerializable, SkullBlock.ISkullType {

    HERCULES_BEETLE(TileEntityHerculesBeetleSpawner::new),
    ENT(TileEntityEntSpawner::new);

    private final Supplier<? extends TileEntityBossSpawner<?>> factory;

    BossVariant(@Nullable Supplier<? extends TileEntityBossSpawner<?>> factory) {
        this.factory = factory;
    }

    @Override
    public String getString() {
        return name().toLowerCase(Locale.ROOT);
    }

    public boolean hasSpawner() {
        return factory != null;
    }

    @Nullable
    public TileEntityBossSpawner<?> getSpawner() {
        return factory != null ? factory.get() : null;
    }
}