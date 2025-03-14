package io.github.chaosawakens.datagen;

import io.github.chaosawakens.CAConstants;
import io.github.chaosawakens.api.entity.EntityTypePropertyWrapper;
import io.github.chaosawakens.common.registry.CAEntityTypes;
import net.minecraft.data.loot.EntityLootSubProvider;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.flag.FeatureFlags;

import java.util.Optional;
import java.util.function.Supplier;
import java.util.stream.Stream;

public class CAEntityLootTableProvider extends EntityLootSubProvider {

    public CAEntityLootTableProvider() {
        super(FeatureFlags.REGISTRY.allFlags());
    }

    @Override
    public void generate() {
        EntityTypePropertyWrapper.getMappedEtpws().forEach((curType, curEtpw) ->
                Optional.ofNullable(curEtpw.getEntityLootTableMappingFunction())
                        .ifPresent(curLootTableProvider -> {
                            CAConstants.LOGGER.debug("[Adding EntityType Loot Table]: " + curType.get().getDescriptionId());
                            add(curType.get(), curLootTableProvider.apply(eCastType(curType)));
                        }));
    }

    protected <E extends Entity> Supplier<EntityType<E>> eCastType(Supplier<? extends EntityType<?>> pEntitySupplier) {
        return (Supplier<EntityType<E>>) pEntitySupplier;
    }

    @Override
    protected Stream<EntityType<?>> getKnownEntityTypes() {
        return CAEntityTypes.getEntityTypes().stream().map(Supplier::get);
    }
}
