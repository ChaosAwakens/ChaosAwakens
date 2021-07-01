package io.github.chaosawakens.entities;

import io.github.chaosawakens.ChaosAwakens;
import io.github.chaosawakens.entities.entities.ent.EntEntity;
import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.object.builder.v1.entity.FabricDefaultAttributeRegistry;
import net.fabricmc.fabric.api.object.builder.v1.entity.FabricEntityTypeBuilder;
import net.minecraft.entity.*;
import net.minecraft.entity.attribute.DefaultAttributeContainer;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;

public class Entities implements ModInitializer {
    public static final EntityType<EntEntity> ENT;

    private static final <T extends LivingEntity> EntityType<?> register(String identifier, SpawnGroup spawnGroup, EntityType.EntityFactory<T> factory /* Entity::new */, float width, float height, DefaultAttributeContainer.Builder builder) {
        EntityType<T> entityType = Registry.register(Registry.ENTITY_TYPE, new Identifier(ChaosAwakens.modID, identifier), FabricEntityTypeBuilder.create(spawnGroup, factory).dimensions(EntityDimensions.fixed(width, height)).build());
        FabricDefaultAttributeRegistry.register(entityType, builder);
        return entityType;
    }

    static {
        ENT = (EntityType<EntEntity>) register("ent", SpawnGroup.CREATURE, EntEntity::new, 0.75f, 0.75f, EntEntity.createLivingAttributes());
    }

    @Override
    public void onInitialize() { }
}
