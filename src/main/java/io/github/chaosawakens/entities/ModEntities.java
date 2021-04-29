package io.github.chaosawakens.entities;

import io.github.chaosawakens.ChaosAwakens;
import io.github.chaosawakens.entities.entities.ent.EntEntity;
import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.object.builder.v1.entity.FabricDefaultAttributeRegistry;
import net.fabricmc.fabric.api.object.builder.v1.entity.FabricEntityTypeBuilder;
import net.minecraft.entity.EntityDimensions;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.SpawnGroup;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;

public class ModEntities implements ModInitializer {

    public static final EntityType<EntEntity> ENT = Registry.register(
            Registry.ENTITY_TYPE,
            new Identifier(ChaosAwakens.modId, "ent"),
            FabricEntityTypeBuilder.create(SpawnGroup.CREATURE, EntEntity::new).dimensions(EntityDimensions.fixed(0.75f, 0.75f)).build());

    @Override
    public void onInitialize() {
        FabricDefaultAttributeRegistry.register(ENT, EntEntity.setCustomAttributes());
    }
}
