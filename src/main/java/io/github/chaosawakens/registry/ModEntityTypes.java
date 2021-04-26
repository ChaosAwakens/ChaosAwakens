package io.github.chaosawakens.registry;

import io.github.chaosawakens.ChaosAwakens;
import io.github.chaosawakens.entity.EntEntity;
import net.minecraft.entity.EntityClassification;
import net.minecraft.entity.EntityType;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;

public class ModEntityTypes {
    public static final DeferredRegister<EntityType<?>> ENTITY_TYPES = DeferredRegister.create(ForgeRegistries.ENTITIES, ChaosAwakens.MODID);

    // Entity Types
    public static final RegistryObject<EntityType<EntEntity>> ENT = ENTITY_TYPES.register("ent",
            () -> EntityType.Builder.create(EntEntity::new, EntityClassification.MONSTER)
                    .size(2.5f, 3.125f) // Hitbox Size ()
                    .build(new ResourceLocation(ChaosAwakens.MODID,"ent").toString()));
}