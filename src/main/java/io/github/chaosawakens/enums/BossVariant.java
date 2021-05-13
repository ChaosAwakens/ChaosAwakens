package io.github.chaosawakens.enums;

import io.github.chaosawakens.tileentity.spawner.TileEntityBossSpawner;
import io.github.chaosawakens.tileentity.spawner.TileEntityEntSpawner;
import io.github.chaosawakens.tileentity.spawner.TileEntityHerculesBeetleSpawner;
import net.minecraft.block.SkullBlock;
import net.minecraft.util.IStringSerializable;

import javax.annotation.Nullable;
import java.util.Locale;
import java.util.function.Supplier;

public enum BossVariant implements IStringSerializable, SkullBlock.ISkullType {

    HERCULES_BEETLE(TileEntityHerculesBeetleSpawner::new),
    ENT(TileEntityEntSpawner::new);

    private final Supplier<? extends TileEntityBossSpawner<?>> factory;

    public static final BossVariant[] VARIANTS = values();

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

    public static BossVariant getVariant(int id) {
        return id >= 0 && id < VARIANTS.length ? VARIANTS[id] : ENT;
    }
}