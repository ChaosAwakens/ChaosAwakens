package io.github.chaosawakens.content.registry;

import com.google.common.collect.ImmutableMap;
import com.mememan.nexus.asm.annotations.RegistrarEntry;
import com.mememan.nexus.platform.NexusServices;
import com.mememan.nexus.property_wrapper.impl.generic.misc.BaseDefaultableBareDataGenPropertyWrapper;
import com.mememan.nexus.util.StringUtil;
import io.github.chaosawakens.CAConstants;
import it.unimi.dsi.fastutil.objects.Object2ObjectOpenHashMap;
import it.unimi.dsi.fastutil.objects.ObjectArrayList;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.PaintingVariantTags;
import net.minecraft.world.entity.decoration.PaintingVariant;

import java.util.Map;
import java.util.function.Supplier;

@RegistrarEntry
public final class CAPaintings {
    protected static final Map<ResourceLocation, Supplier<PaintingVariant>> PAINTING_VARIANTS = new Object2ObjectOpenHashMap<>();

    public static final Supplier<PaintingVariant> MAN_MEME = registerPaintingVariant(CAConstants.prefix("man_meme"), 64, 64);
    public static final Supplier<PaintingVariant> APPLE_COW_FIELD = registerPaintingVariant(CAConstants.prefix("apple_cow_field"), 64, 32);
    public static final Supplier<PaintingVariant> OLD_CHAOS_AWAKENS_LOGO = registerPaintingVariant(CAConstants.prefix("old_chaos_awakens_logo"), 32, 32);
    public static final Supplier<PaintingVariant> CHAOS_AWAKENS_LOGO = registerPaintingVariant(CAConstants.prefix("chaos_awakens_logo"), 64, 64);
    public static final Supplier<PaintingVariant> PLAYERS = registerPaintingVariant(CAConstants.prefix("players"), 64, 32);
    public static final Supplier<PaintingVariant> CAMPFIRE = registerPaintingVariant(CAConstants.prefix("campfire"), 64, 32);
    public static final Supplier<PaintingVariant> PURPLE_BULB = registerPaintingVariant(CAConstants.prefix("purple_bulb"), 16, 16);
    public static final Supplier<PaintingVariant> RED = registerPaintingVariant(CAConstants.prefix("red"), 16, 16);
    public static final Supplier<PaintingVariant> CHAOS_AWAKENS_BANNER = registerPaintingVariant(CAConstants.prefix("chaos_awakens_banner"), 64, 32);
    public static final Supplier<PaintingVariant> SUNSET = registerPaintingVariant(CAConstants.prefix("sunset"), 64, 32);
    public static final Supplier<PaintingVariant> UNDERWATER = registerPaintingVariant(CAConstants.prefix("underwater"), 64, 32);
    public static final Supplier<PaintingVariant> VILLAGE = registerPaintingVariant(CAConstants.prefix("village"), 64, 32);
    public static final Supplier<PaintingVariant> VILLAGE_NIGHT = registerPaintingVariant(CAConstants.prefix("village_night"), 64, 32);
    public static final Supplier<PaintingVariant> DIMETRODON_LAKE = registerPaintingVariant(CAConstants.prefix("dimetrodon_lake"), 64, 32);
    public static final Supplier<PaintingVariant> PAEONIA_PLAINS = registerPaintingVariant(CAConstants.prefix("paeonia_plains"), 64, 32);
    public static final Supplier<PaintingVariant> CYAN = registerPaintingVariant(CAConstants.prefix("cyan"), 16, 16);
    public static final Supplier<PaintingVariant> CARROT_PIG_PLAINS = registerPaintingVariant(CAConstants.prefix("carrot_pig_plains"), 64, 32);
    public static final Supplier<PaintingVariant> NIGHTMARE_SAVANNAH = registerPaintingVariant(CAConstants.prefix("nighttime_savannah"), 64, 32);

    private static <P extends PaintingVariant> Supplier<P> registerPaintingVariant(ResourceLocation id, Supplier<P> paintingVariantSup) {
        Supplier<P> registeredPaintingVariant = NexusServices.REGISTRAR.registerObject(id, paintingVariantSup, BuiltInRegistries.PAINTING_VARIANT);

        PAINTING_VARIANTS.put(id, (Supplier<PaintingVariant>) registeredPaintingVariant);

        return registeredPaintingVariant;
    }

    private static <P extends PaintingVariant> Supplier<P> registerPaintingVariant(String id, Supplier<P> paintingVariantSup) {
        return registerPaintingVariant(CAConstants.prefix(id), paintingVariantSup);
    }

    private static <P extends PaintingVariant> Supplier<P> registerPaintingVariant(ResourceLocation id, int width, int height) {
        return (Supplier<P>) registerPaintingVariant(id, () -> new PaintingVariant(width, height));
    }

    private static <P extends PaintingVariant> Supplier<P> registerPaintingVariant(String id, int width, int height) {
        return registerPaintingVariant(CAConstants.prefix(id), width, height);
    }

    public static ImmutableMap<ResourceLocation, Supplier<PaintingVariant>> getPaintingVariants() {
        return ImmutableMap.copyOf(PAINTING_VARIANTS);
    }

    static {
        PAINTING_VARIANTS.forEach((id, paintingVariantSup) -> {
            String literalId = id.getPath();

            new BaseDefaultableBareDataGenPropertyWrapper<>(paintingVariantSup, CAConstants.MOD_ID)
                    .builder()
                    .withTag(() -> PaintingVariantTags.PLACEABLE)
                    .withAdditionalLocalizationKey("painting.chaosawakens." + literalId + ".title", StringUtil.literallyLocalize(literalId, ObjectArrayList.of()))
                    .withAdditionalLocalizationKey("painting.chaosawakens." + literalId + ".author", "Chaos Team - T40ne")
                    .buildAndGet();
        });
    }
}
