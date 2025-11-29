package io.github.chaosawakens.common.registry;

import com.google.common.collect.ImmutableList;
import io.github.chaosawakens.CAConstants;
import io.github.chaosawakens.api.asm.annotations.RegistrarEntry;
import io.github.chaosawakens.api.entity.ClientDataEntry;
import io.github.chaosawakens.api.entity.EntityTypePropertyWrapper;
import io.github.chaosawakens.api.platform.CAServices;
import io.github.chaosawakens.common.entity.prototype.hostile.Ent;
import io.github.chaosawakens.common.entity.prototype.hostile.robo.RoboPounder;
import io.github.chaosawakens.common.entity.prototype.neutral.RubberDucky;
import io.github.chaosawakens.common.entity.prototype.passive.animal.land.AppleCow;
import io.github.chaosawakens.common.entity.prototype.passive.animal.land.CarrotPig;
import io.github.chaosawakens.common.entity.prototype.passive.animal.land.LettuceChicken;
import io.github.chaosawakens.common.entity.prototype.passive.animal.land.StinkBug;
import io.github.chaosawakens.util.LootUtil;
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
                    .clientTrackingRange(50)
                    .build(CAConstants.prefix("robo_pounder").toString())))
            .builder()
            .withAttributes(RoboPounder::createAttributes)
            .withLootTable(RoboPounder::createLootTable)
            .withClientDataEntry(getSideSafeClientDataEntry("robo_pounder"))
            .build()
            .getParentEntityType();

    // Ent
    public static final Supplier<EntityType<Ent>> ACACIA_ENT = EntityTypePropertyWrapper.create(
                    registerEntityType("acacia_ent", () -> EntityType.Builder.of(Ent::new, MobCategory.MONSTER)
                            .sized(3.35F, 4.225F)
                            .clientTrackingRange(30)
                            .build(CAConstants.prefix("acacia_ent").toString())))
            .builder()
            .withAttributes(Ent::createAttributes)
            .withLootTable(LootUtil::createAcaciaEntLootTable)
            .withClientDataEntry(getSideSafeClientDataEntry("ent"))
            .build()
            .getParentEntityType();
    public static final Supplier<EntityType<Ent>> BIRCH_ENT = EntityTypePropertyWrapper.create(
                    registerEntityType("birch_ent", () -> EntityType.Builder.of(Ent::new, MobCategory.MONSTER)
                            .sized(3.35F, 4.225F)
                            .clientTrackingRange(30)
                            .build(CAConstants.prefix("birch_ent").toString())))
            .builder()
            .withAttributes(Ent::createAttributes)
            .withLootTable(LootUtil::createBirchEntLootTable)
            .withClientDataEntry(getSideSafeClientDataEntry("ent"))
            .build()
            .getParentEntityType();
    public static final Supplier<EntityType<Ent>> CHERRY_ENT = EntityTypePropertyWrapper.create(
                    registerEntityType("cherry_ent", () -> EntityType.Builder.of(Ent::new, MobCategory.MONSTER)
                            .sized(3.35F, 4.225F)
                            .clientTrackingRange(30)
                            .build(CAConstants.prefix("cherry_ent").toString())))
            .builder()
            .withAttributes(Ent::createAttributes)
            .withLootTable(LootUtil::createCherryEntLootTable)
            .withClientDataEntry(getSideSafeClientDataEntry("ent"))
            .build()
            .getParentEntityType();
    public static final Supplier<EntityType<Ent>> CRIMSON_ENT = EntityTypePropertyWrapper.create(
                    registerEntityType("crimson_ent", () -> EntityType.Builder.of(Ent::new, MobCategory.MONSTER)
                            .sized(3.35F, 4.225F)
                            .clientTrackingRange(30)
                            .build(CAConstants.prefix("crimson_ent").toString())))
            .builder()
            .withAttributes(Ent::createAttributes)
            .withLootTable(LootUtil::createCrimsonEntLootTable)
            .withClientDataEntry(getSideSafeClientDataEntry("ent"))
            .build()
            .getParentEntityType();
    public static final Supplier<EntityType<Ent>> DARK_OAK_ENT = EntityTypePropertyWrapper.create(
                    registerEntityType("dark_oak_ent", () -> EntityType.Builder.of(Ent::new, MobCategory.MONSTER)
                            .sized(3.35F, 4.225F)
                            .clientTrackingRange(30)
                            .build(CAConstants.prefix("dark_oak_ent").toString())))
            .builder()
            .withAttributes(Ent::createAttributes)
            .withLootTable(LootUtil::createDarkOakEntLootTable)
            .withClientDataEntry(getSideSafeClientDataEntry("ent"))
            .build()
            .getParentEntityType();
    public static final Supplier<EntityType<Ent>> JUNGLE_ENT = EntityTypePropertyWrapper.create(
                    registerEntityType("jungle_ent", () -> EntityType.Builder.of(Ent::new, MobCategory.MONSTER)
                            .sized(3.35F, 4.225F)
                            .clientTrackingRange(30)
                            .build(CAConstants.prefix("jungle_ent").toString())))
            .builder()
            .withAttributes(Ent::createAttributes)
            .withLootTable(LootUtil::createJungleEntLootTable)
            .withClientDataEntry(getSideSafeClientDataEntry("ent"))
            .build()
            .getParentEntityType();
    public static final Supplier<EntityType<Ent>> OAK_ENT = EntityTypePropertyWrapper.create(
                    registerEntityType("oak_ent", () -> EntityType.Builder.of(Ent::new, MobCategory.MONSTER)
                            .sized(3.35F, 4.225F)
                            .clientTrackingRange(30)
                            .build(CAConstants.prefix("oak_ent").toString())))
            .builder()
            .withAttributes(Ent::createAttributes)
            .withLootTable(LootUtil::createOakEntLootTable)
            .withClientDataEntry(getSideSafeClientDataEntry("ent"))
            .build()
            .getParentEntityType();
    public static final Supplier<EntityType<Ent>> SPRUCE_ENT = EntityTypePropertyWrapper.create(
                    registerEntityType("spruce_ent", () -> EntityType.Builder.of(Ent::new, MobCategory.MONSTER)
                            .sized(3.35F, 4.225F)
                            .clientTrackingRange(30)
                            .build(CAConstants.prefix("spruce_ent").toString())))
            .builder()
            .withAttributes(Ent::createAttributes)
            .withLootTable(LootUtil::createSpruceEntLootTable)
            .withClientDataEntry(getSideSafeClientDataEntry("ent"))
            .build()
            .getParentEntityType();
    public static final Supplier<EntityType<Ent>> WARPED_ENT = EntityTypePropertyWrapper.create(
                    registerEntityType("warped_ent", () -> EntityType.Builder.of(Ent::new, MobCategory.MONSTER)
                            .sized(3.35F, 4.225F)
                            .clientTrackingRange(30)
                            .build(CAConstants.prefix("warped_ent").toString())))
            .builder()
            .withAttributes(Ent::createAttributes)
            .withLootTable(LootUtil::createWarpedEntLootTable)
            .withClientDataEntry(getSideSafeClientDataEntry("ent"))
            .build()
            .getParentEntityType();

    public static final Supplier<EntityType<Ent>> APPLE_ENT = EntityTypePropertyWrapper.create(
                    registerEntityType("apple_ent", () -> EntityType.Builder.of(Ent::new, MobCategory.MONSTER)
                            .sized(3.35F, 4.225F)
                            .clientTrackingRange(30)
                            .build(CAConstants.prefix("apple_ent").toString())))
            .builder()
            .withAttributes(Ent::createAttributes)
            .withLootTable(LootUtil::createAppleEntLootTable)
            .withClientDataEntry(getSideSafeClientDataEntry("ent"))
            .build()
            .getParentEntityType();
    public static final Supplier<EntityType<Ent>> GINKGO_ENT = EntityTypePropertyWrapper.create(
                    registerEntityType("ginkgo_ent", () -> EntityType.Builder.of(Ent::new, MobCategory.MONSTER)
                            .sized(3.35F, 4.225F)
                            .clientTrackingRange(30)
                            .build(CAConstants.prefix("ginkgo_ent").toString())))
            .builder()
            .withAttributes(Ent::createAttributes)
            .withLootTable(LootUtil::createGinkgoEntLootTable)
            .withClientDataEntry(getSideSafeClientDataEntry("ent"))
            .build()
            .getParentEntityType();
    public static final Supplier<EntityType<Ent>> PEACH_ENT = EntityTypePropertyWrapper.create(
                    registerEntityType("peach_ent", () -> EntityType.Builder.of(Ent::new, MobCategory.MONSTER)
                            .sized(3.35F, 4.225F)
                            .clientTrackingRange(30)
                            .build(CAConstants.prefix("peach_ent").toString())))
            .builder()
            .withAttributes(Ent::createAttributes)
            .withLootTable(LootUtil::createPeachEntLootTable)
            .withClientDataEntry(getSideSafeClientDataEntry("ent"))
            .build()
            .getParentEntityType();
    public static final Supplier<EntityType<Ent>> SKYWOOD_ENT = EntityTypePropertyWrapper.create(
                    registerEntityType("skywood_ent", () -> EntityType.Builder.of(Ent::new, MobCategory.MONSTER)
                            .sized(3.35F, 4.225F)
                            .clientTrackingRange(30)
                            .build(CAConstants.prefix("skywood_ent").toString())))
            .builder()
            .withAttributes(Ent::createAttributes)
            .withLootTable(LootUtil::createSkywoodEntLootTable)
            .withClientDataEntry(getSideSafeClientDataEntry("ent"))
            .build()
            .getParentEntityType();

    // Neutral
    public static final Supplier<EntityType<RubberDucky>> RUBBER_DUCKY = EntityTypePropertyWrapper.create(
                    registerEntityType("rubber_ducky", () -> EntityType.Builder.of(RubberDucky::new, MobCategory.CREATURE)
                            .sized(0.65F, 0.65F)
                            .clientTrackingRange(20)
                            .build(CAConstants.prefix("rubber_ducky").toString())))
            .builder()
            .withAttributes(RubberDucky::createAttributes)
            .withLootTable(RubberDucky::createLootTable)
            .withClientDataEntry(getSideSafeClientDataEntry("rubber_ducky"))
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
