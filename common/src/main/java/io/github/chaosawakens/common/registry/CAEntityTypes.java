package io.github.chaosawakens.common.registry;

import com.google.common.collect.ImmutableList;
import io.github.chaosawakens.CAConstants;
import io.github.chaosawakens.api.asm.annotations.RegistrarEntry;
import io.github.chaosawakens.api.entity.ClientDataEntry;
import io.github.chaosawakens.api.entity.EntityTypePropertyWrapper;
import io.github.chaosawakens.api.platform.CAServices;
import io.github.chaosawakens.common.entity.prototype.hostile.robo.RoboPounder;
import io.github.chaosawakens.common.entity.prototype.passive.animal.land.AppleCow;
import io.github.chaosawakens.common.entity.prototype.passive.animal.land.CarrotPig;
import io.github.chaosawakens.common.entity.prototype.passive.animal.land.LettuceChicken;
import io.github.chaosawakens.common.entity.prototype.passive.animal.land.StinkBug;
import it.unimi.dsi.fastutil.objects.ObjectArrayList;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MobCategory;
import org.jetbrains.annotations.Nullable;

import java.util.function.Supplier;

@RegistrarEntry
public class CAEntityTypes {
    private static final ObjectArrayList<Supplier<? extends EntityType<?>>> ENTITY_TYPES = new ObjectArrayList<>();

    // Apple Cow
    public static final Supplier<EntityType<AppleCow>> APPLE_COW = EntityTypePropertyWrapper.create(
                    registerEntityType("apple_cow", () -> EntityType.Builder.of(AppleCow::new, MobCategory.CREATURE)
                            .sized(0.9F, 1.4F)
                            .clientTrackingRange(10)
                            .build(CAConstants.prefix("apple_cow").toString())))
            .builder()
            .withAttributes(AppleCow::createAttributes)
            .withLootTable(AppleCow::createStandardLootTable)
            .withClientDataEntry(getSideSafeClientDataEntry("apple_cow"))
            .build()
            .getParentEntityType();
    public static final Supplier<EntityType<AppleCow>> GOLDEN_APPLE_COW = EntityTypePropertyWrapper.create(
                    registerEntityType("golden_apple_cow", () -> EntityType.Builder.of(AppleCow::new, MobCategory.CREATURE)
                            .sized(0.9F, 1.4F)
                            .clientTrackingRange(10)
                            .build(CAConstants.prefix("golden_apple_cow").toString())))
            .builder()
            .withAttributes(AppleCow::createAttributes)
            .withLootTable(AppleCow::createGoldenLootTable)
            .withClientDataEntry(getSideSafeClientDataEntry("apple_cow"))
            .build()
            .getParentEntityType();
    public static final Supplier<EntityType<AppleCow>> ENCHANTED_GOLDEN_APPLE_COW = EntityTypePropertyWrapper.create(
                    registerEntityType("enchanted_golden_apple_cow", () -> EntityType.Builder.of(AppleCow::new, MobCategory.CREATURE)
                            .sized(0.9F, 1.4F)
                            .clientTrackingRange(10)
                            .build(CAConstants.prefix("enchanted_golden_apple_cow").toString())))
            .builder()
            .withAttributes(AppleCow::createAttributes)
            .withLootTable(AppleCow::createEnchantedLootTable)
            .withClientDataEntry(getSideSafeClientDataEntry("apple_cow"))
            .build()
            .getParentEntityType();

    // Carrot Pig
    public static final Supplier<EntityType<CarrotPig>> CARROT_PIG = EntityTypePropertyWrapper.create(
                    registerEntityType("carrot_pig", () -> EntityType.Builder.of(CarrotPig::new, MobCategory.CREATURE)
                            .sized(0.9F, 0.9F)
                            .clientTrackingRange(10)
                            .build(CAConstants.prefix("carrot_pig").toString())))
            .builder()
            .withAttributes(CarrotPig::createAttributes)
            .withLootTable(CarrotPig::createStandardLootTable)
            .withClientDataEntry(getSideSafeClientDataEntry("carrot_pig"))
            .build()
            .getParentEntityType();
    public static final Supplier<EntityType<CarrotPig>> GOLDEN_CARROT_PIG = EntityTypePropertyWrapper.create(
                    registerEntityType("golden_carrot_pig", () -> EntityType.Builder.of(CarrotPig::new, MobCategory.CREATURE)
                            .sized(0.9F, 0.9F)
                            .clientTrackingRange(10)
                            .build(CAConstants.prefix("golden_carrot_pig").toString())))
            .builder()
            .withAttributes(CarrotPig::createAttributes)
            .withLootTable(CarrotPig::createGoldenLootTable)
            .withClientDataEntry(getSideSafeClientDataEntry("carrot_pig"))
            .build()
            .getParentEntityType();
    public static final Supplier<EntityType<CarrotPig>> ENCHANTED_GOLDEN_CARROT_PIG = EntityTypePropertyWrapper.create(
                    registerEntityType("enchanted_golden_carrot_pig", () -> EntityType.Builder.of(CarrotPig::new, MobCategory.CREATURE)
                            .sized(0.9F, 0.9F)
                            .clientTrackingRange(10)
                            .build(CAConstants.prefix("enchanted_golden_carrot_pig").toString())))
            .builder()
            .withAttributes(CarrotPig::createAttributes)
            .withLootTable(CarrotPig::createEnchantedLootTable)
            .withClientDataEntry(getSideSafeClientDataEntry("carrot_pig"))
            .build()
            .getParentEntityType();

    // Other Food Animals
    public static final Supplier<EntityType<LettuceChicken>> LETTUCE_CHICKEN = EntityTypePropertyWrapper.create(
                    registerEntityType("lettuce_chicken", () -> EntityType.Builder.of(LettuceChicken::new, MobCategory.CREATURE)
                            .sized(0.4F, 0.7F)
                            .clientTrackingRange(10)
                            .build(CAConstants.prefix("lettuce_chicken").toString())))
            .builder()
            .withAttributes(LettuceChicken::createAttributes)
            .withLootTable(LettuceChicken::createLootTable)
            .withClientDataEntry(getSideSafeClientDataEntry("lettuce_chicken"))
            .build()
            .getParentEntityType();

    // Bugs
    public static final Supplier<EntityType<StinkBug>> STINK_BUG = EntityTypePropertyWrapper.create(
                    registerEntityType("stink_bug", () -> EntityType.Builder.of(StinkBug::new, MobCategory.CREATURE)
                            .sized(1.21F, 0.47F)
                            .clientTrackingRange(12)
                            .build(CAConstants.prefix("stink_bug").toString())))
            .builder()
            .withAttributes(StinkBug::createAttributes)
            .withLootTable(StinkBug::createLootTable)
            .withClientDataEntry(getSideSafeClientDataEntry("stink_bug"))
            .build()
            .getParentEntityType();

    // Robo
    public static final Supplier<EntityType<RoboPounder>> ROBO_POUNDER = EntityTypePropertyWrapper.create(
            registerEntityType("robo_pounder", () -> EntityType.Builder.of(RoboPounder::new, MobCategory.MONSTER)
                    .sized(4.1F, 6.0F)
                    .clientTrackingRange(15)
                    .build(CAConstants.prefix("robo_pounder").toString())))
            .builder()
            .withAttributes(RoboPounder::createAttributes)
            .withLootTable(RoboPounder::createLootTable)
            .withClientDataEntry(getSideSafeClientDataEntry("robo_pounder"))
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

    @Nullable
    public static Supplier<ClientDataEntry> getSideSafeClientDataEntry(ResourceLocation mappedName) {
        return CAServices.PLATFORM.getEnvironmentSide().isClient() ? CAClientDataEntries.getClientDataEntries().stream()
                .filter(curEntry -> curEntry.get().entityTypeId().equals(mappedName))
                .findFirst()
                .get(): null;
    }

    @Nullable
    public static Supplier<ClientDataEntry> getSideSafeClientDataEntry(String mappedName) {
        return getSideSafeClientDataEntry(CAConstants.prefix(mappedName));
    }

    public static ImmutableList<Supplier<? extends EntityType<?>>> getEntityTypes() {
        return ImmutableList.copyOf(ENTITY_TYPES);
    }
}
