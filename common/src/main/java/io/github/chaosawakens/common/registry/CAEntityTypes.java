package io.github.chaosawakens.common.registry;

import com.google.common.collect.ImmutableList;
import io.github.chaosawakens.CAConstants;
import io.github.chaosawakens.api.asm.annotations.RegistrarEntry;
import io.github.chaosawakens.api.entity.EntityTypePropertyWrapper;
import io.github.chaosawakens.api.platform.CAServices;
import io.github.chaosawakens.common.entity.prototype.hostile.robo.RoboPounder;
import io.github.chaosawakens.common.entity.prototype.passive.animal.land.AppleCow;
import io.github.chaosawakens.common.entity.prototype.passive.animal.land.CarrotPig;
import it.unimi.dsi.fastutil.objects.ObjectArrayList;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MobCategory;

import java.util.function.Supplier;

@RegistrarEntry
public class CAEntityTypes {
    private static final ObjectArrayList<Supplier<? extends EntityType<?>>> ENTITY_TYPES = new ObjectArrayList<>();

    // Apple Cow
    public static final Supplier<EntityType<AppleCow>> APPLE_COW = EntityTypePropertyWrapper.create(
                    registerEntityType("apple_cow", () -> EntityType.Builder.of(AppleCow::new, MobCategory.CREATURE)
                            .sized(0.9F, 1.4F)
                            .build(CAConstants.prefix("apple_cow").toString())))
            .builder()
            .withAttributes(AppleCow::createAttributes)
            .withLootTable(AppleCow::createStandardLootTable)
            .withClientDataEntry(CAClientDataEntries.APPLE_COW)
            .build()
            .getParentEntityType();
    public static final Supplier<EntityType<AppleCow>> GOLDEN_APPLE_COW = EntityTypePropertyWrapper.create(
                    registerEntityType("golden_apple_cow", () -> EntityType.Builder.of(AppleCow::new, MobCategory.CREATURE)
                            .sized(0.9F, 1.4F)
                            .build(CAConstants.prefix("golden_apple_cow").toString())))
            .builder()
            .withAttributes(AppleCow::createAttributes)
            .withLootTable(AppleCow::createGoldenLootTable)
            .withClientDataEntry(CAClientDataEntries.APPLE_COW)
            .build()
            .getParentEntityType();
    public static final Supplier<EntityType<AppleCow>> ENCHANTED_GOLDEN_APPLE_COW = EntityTypePropertyWrapper.create(
                    registerEntityType("enchanted_golden_apple_cow", () -> EntityType.Builder.of(AppleCow::new, MobCategory.CREATURE)
                            .sized(0.9F, 1.4F)
                            .build(CAConstants.prefix("enchanted_golden_apple_cow").toString())))
            .builder()
            .withAttributes(AppleCow::createAttributes)
            .withLootTable(AppleCow::createEnchantedLootTable)
            .withClientDataEntry(CAClientDataEntries.APPLE_COW)
            .build()
            .getParentEntityType();

    // Carrot Pig
    public static final Supplier<EntityType<CarrotPig>> CARROT_PIG = EntityTypePropertyWrapper.create(
                    registerEntityType("carrot_pig", () -> EntityType.Builder.of(CarrotPig::new, MobCategory.CREATURE)
                            .sized(0.9F, 0.9F)
                            .build(CAConstants.prefix("carrot_pig").toString())))
            .builder()
            .withAttributes(CarrotPig::createAttributes)
            .withLootTable(CarrotPig::createStandardLootTable)
            .withClientDataEntry(CAClientDataEntries.CARROT_PIG)
            .build()
            .getParentEntityType();
    public static final Supplier<EntityType<CarrotPig>> GOLDEN_CARROT_PIG = EntityTypePropertyWrapper.create(
                    registerEntityType("golden_carrot_pig", () -> EntityType.Builder.of(CarrotPig::new, MobCategory.CREATURE)
                            .sized(0.9F, 0.9F)
                            .build(CAConstants.prefix("golden_carrot_pig").toString())))
            .builder()
            .withAttributes(CarrotPig::createAttributes)
            .withLootTable(CarrotPig::createGoldenLootTable)
            .withClientDataEntry(CAClientDataEntries.CARROT_PIG)
            .build()
            .getParentEntityType();
    public static final Supplier<EntityType<CarrotPig>> ENCHANTED_GOLDEN_CARROT_PIG = EntityTypePropertyWrapper.create(
                    registerEntityType("enchanted_golden_carrot_pig", () -> EntityType.Builder.of(CarrotPig::new, MobCategory.CREATURE)
                            .sized(0.9F, 0.9F)
                            .build(CAConstants.prefix("enchanted_golden_carrot_pig").toString())))
            .builder()
            .withAttributes(CarrotPig::createAttributes)
            .withLootTable(CarrotPig::createEnchantedLootTable)
            .withClientDataEntry(CAClientDataEntries.CARROT_PIG)
            .build()
            .getParentEntityType();

    // Robo
    public static final Supplier<EntityType<RoboPounder>> ROBO_POUNDER = EntityTypePropertyWrapper.create(
            registerEntityType("robo_pounder", () -> EntityType.Builder.of(RoboPounder::new, MobCategory.MONSTER)
                    .sized(4.1F, 6.0F)
                    .build(CAConstants.prefix("robo_pounder").toString())))
            .builder()
            .withAttributes(RoboPounder::createAttributes)
            .withLootTable(RoboPounder::createLootTable)
            .withClientDataEntry(CAClientDataEntries.ROBO_POUNDER)
            .build()
            .getParentEntityType();

    private static <E extends Entity> Supplier<EntityType<E>> registerEntityType(ResourceLocation id, Supplier<EntityType<E>> entityTypeSup) {
        Supplier<EntityType<E>> registeredEntityTypeSup = CAServices.REGISTRAR.registerObject(id, entityTypeSup, BuiltInRegistries.ENTITY_TYPE); // Otherwise reference to the entity type sup is null cuz it needs to be registered b4hand
        ENTITY_TYPES.add(registeredEntityTypeSup);
        return registeredEntityTypeSup;
    }

    private static <E extends Entity> Supplier<EntityType<E>> registerEntityType(String id, Supplier<EntityType<E>> entityTypeSup) {
        return registerEntityType(CAConstants.prefix(id), entityTypeSup);
    }

    public static ImmutableList<Supplier<? extends EntityType<?>>> getEntityTypes() {
        return ImmutableList.copyOf(ENTITY_TYPES);
    }
}
