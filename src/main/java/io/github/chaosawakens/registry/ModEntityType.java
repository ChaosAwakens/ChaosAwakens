package io.github.chaosawakens.registry;


import io.github.chaosawakens.ChaosAwakens;
import io.github.chaosawakens.entities.EntEntity;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityClassification;
import net.minecraft.entity.EntityType;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;

public class ModEntityType {

    public static final DeferredRegister<EntityType<?>> ENTITY_TYPES = DeferredRegister.create(ForgeRegistries.ENTITIES, ChaosAwakens.MODID);

    public static final RegistryObject<EntityType<EntEntity>> ENT_ENTITY = buildEntity(EntEntity::new,
            EntEntity.class, 2.5f, 2.25f);

    public static <T extends Entity> RegistryObject<EntityType<T>> buildEntity(EntityType.IFactory<T> entity,
                                                                               Class<T> entityClass, float width, float height) {
        String name = entityClass.getSimpleName().toLowerCase();
        return ENTITY_TYPES.register(name,
                () -> EntityType.Builder.create(entity, EntityClassification.CREATURE).size(width, height).build(name));
    }
}