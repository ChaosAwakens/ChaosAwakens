package io.github.chaosawakens.registry;

import com.ibm.icu.text.DateIntervalFormat;
import io.github.chaosawakens.ChaosAwakens;
import io.github.chaosawakens.entity.*;
import net.minecraft.entity.EntityClassification;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.projectile.ArrowEntity;
import net.minecraft.item.Item;
import net.minecraft.item.SpawnEggItem;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import software.bernie.example.registry.EntityRegistry;

public class ModEntityTypes {


    public static final DeferredRegister<EntityType<?>> ENTITY_TYPES = DeferredRegister.create(ForgeRegistries.ENTITIES, ChaosAwakens.MODID);

    // ENTITY TYPES
    // Ent
    public static final RegistryObject<EntityType<EntEntity>> ENT = ENTITY_TYPES.register("ent",
            () -> EntityType.Builder.create(EntEntity::new, EntityClassification.MONSTER)
                    .size(2.5f, 3.125f) // Hitbox Size ()
                    .build(new ResourceLocation(ChaosAwakens.MODID,"ent").toString()));

    // Hercules Beetle
    public static final RegistryObject<EntityType<HerculesBeetleEntity>> HERCULES_BEETLE = ENTITY_TYPES.register("hercules_beetle",
            () -> EntityType.Builder.create(HerculesBeetleEntity::new, EntityClassification.MONSTER)
                    .size(4.5f, 3.125f) // Hitbox Size ()
                    .build(new ResourceLocation(ChaosAwakens.MODID,"hercules_beetle").toString()));
    // Ants
    public static final RegistryObject<EntityType<BrownAntEntity>> BROWN_ANT = ENTITY_TYPES.register("brown_ant",
            () -> EntityType.Builder.create(BrownAntEntity::new, EntityClassification.CREATURE)
                    .size(0.25f, 0.25f) // Hitbox Size ()
                    .build(new ResourceLocation(ChaosAwakens.MODID,"brown_ant").toString()));
    public static final RegistryObject<EntityType<RainbowAntEntity>> RAINBOW_ANT = ENTITY_TYPES.register("rainbow_ant",
            () -> EntityType.Builder.create(RainbowAntEntity::new, EntityClassification.CREATURE)
                    .size(0.25f, 0.25f) // Hitbox Size ()
                    .build(new ResourceLocation(ChaosAwakens.MODID,"rainbow_ant").toString()));
    public static final RegistryObject<EntityType<RedAntEntity>> RED_ANT = ENTITY_TYPES.register("red_ant",
            () -> EntityType.Builder.create(RedAntEntity::new, EntityClassification.MONSTER)
                    .size(0.25f, 0.25f) // Hitbox Size ()
                    .build(new ResourceLocation(ChaosAwakens.MODID,"red_ant").toString()));
    public static final RegistryObject<EntityType<UnstableAntEntity>> UNSTABLE_ANT = ENTITY_TYPES.register("unstable_ant",
            () -> EntityType.Builder.create(UnstableAntEntity::new, EntityClassification.CREATURE)
                    .size(0.25f, 0.25f) // Hitbox Size ()
                    .build(new ResourceLocation(ChaosAwakens.MODID,"unstable_ant").toString()));

    // Termite
    public static final RegistryObject<EntityType<TermiteEntity>> TERMITE = ENTITY_TYPES.register("termite",
            () -> EntityType.Builder.create(TermiteEntity::new, EntityClassification.CREATURE)
                    .size(0.5f, 0.25f) // Hitbox Size ()
                    .build(new ResourceLocation(ChaosAwakens.MODID,"termite").toString()));

    public static final RegistryObject<EntityType<UltimateArrowEntity>> ULTIMATE_ARROW = ENTITY_TYPES.register("ultimate_arrow",
            () -> EntityType.Builder.<UltimateArrowEntity>create(UltimateArrowEntity::new, EntityClassification.MISC)
                    .size(0.5F, 0.5F).trackingRange(4).updateInterval(20)
                    .build(new ResourceLocation(ChaosAwakens.MODID,"ultimate_arrow").toString()));
}
