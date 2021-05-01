package io.github.chaosawakens.registry;

import io.github.chaosawakens.ChaosAwakens;
import io.github.chaosawakens.entity.*;
import net.minecraft.entity.EntityClassification;
import net.minecraft.entity.EntityType;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;

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

    // Ruby Bug
    public static final RegistryObject<EntityType<RubyBugEntity>> RUBY_BUG = ENTITY_TYPES.register("ruby_bug",
            () -> EntityType.Builder.create(RubyBugEntity::new, EntityClassification.CREATURE)
                    .size(0.5f, 0.5f) // Hitbox Size ()
                    .build(new ResourceLocation(ChaosAwakens.MODID,"ruby_bug").toString()));

    // Apple Cows
    public static final RegistryObject<EntityType<AppleCowEntity>> APPLE_COW = ENTITY_TYPES.register("apple_cow",
            () -> EntityType.Builder.create(AppleCowEntity::new, EntityClassification.CREATURE)
                    .size(0.9F, 1.4F)
                    .trackingRange(10)// Hitbox Size ()
                    .build(new ResourceLocation(ChaosAwakens.MODID,"apple_cow").toString()));
    public static final RegistryObject<EntityType<GoldenAppleCowEntity>> GOLDEN_APPLE_COW = ENTITY_TYPES.register("golden_apple_cow",
            () -> EntityType.Builder.create(GoldenAppleCowEntity::new, EntityClassification.CREATURE)
                    .size(0.9F, 1.4F)
                    .trackingRange(10)// Hitbox Size ()
                    .build(new ResourceLocation(ChaosAwakens.MODID,"golden_apple_cow").toString()));

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

    // Arrow
    public static final RegistryObject<EntityType<UltimateArrowEntity>> ULTIMATE_ARROW = ENTITY_TYPES.register("ultimate_arrow",
            () -> EntityType.Builder.<UltimateArrowEntity>create(UltimateArrowEntity::new, EntityClassification.MISC)
                    .size(0.5F, 0.5F).trackingRange(4).updateInterval(20)
                    .build(new ResourceLocation(ChaosAwakens.MODID,"ultimate_arrow").toString()));
}
