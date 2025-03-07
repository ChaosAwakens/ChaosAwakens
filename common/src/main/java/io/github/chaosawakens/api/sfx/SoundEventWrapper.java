package io.github.chaosawakens.api.sfx;

import com.google.common.collect.ImmutableSortedMap;
import it.unimi.dsi.fastutil.objects.Object2ObjectLinkedOpenHashMap;
import net.minecraft.sounds.SoundEvent;
import org.jetbrains.annotations.Nullable;

import java.util.function.Supplier;

/**
 * A wrapper class used to store information referenced in datagen to simplify creating data entries for different {@link SoundEvent} types.
 */
public class SoundEventWrapper {
    private static final Object2ObjectLinkedOpenHashMap<Supplier<SoundEvent>, SoundEventWrapper> MAPPED_SEWS = new Object2ObjectLinkedOpenHashMap<>();
    private final Supplier<SoundEvent> ownerSoundEvent;
    private final boolean isTemplate;
    @Nullable
    private SEWBuilder builder;

    private SoundEventWrapper(Supplier<SoundEvent> ownerSoundEvent, boolean isTemplate) {
        this.ownerSoundEvent = ownerSoundEvent;
        this.isTemplate = isTemplate;
    }

    private SoundEventWrapper(Supplier<SoundEvent> ownerSoundEvent) {
        this(ownerSoundEvent, false);
    }

    private SoundEventWrapper() {
        this(null, true);
    }

    /**
     * Creates a new {@link SoundEventWrapper} instance. This is usually where you'll begin chaining {@link #builder()} method calls if needed. Use this variant if you want to create an SEW instance with a stored registration call,
     * such that its parent {@link Supplier<SoundEvent>} is not {@code null}.
     *
     * @param ownerSoundEventSup The parent {@link Supplier<SoundEvent>} stored in the newly-initialized SEW instance.
     *
     * @return A new {@link SoundEventWrapper} instance.
     */
    public static SoundEventWrapper create(Supplier<SoundEvent> ownerSoundEventSup) {
        return new SoundEventWrapper(ownerSoundEventSup);
    }

    /**
     * Constructs a builder chain in which certain datagen properties can be assigned and re-built with in this SEW instance. Also sets
     * this SEW instance's {@link #builder} to the newly-constructed {@link SEWBuilder} instance.
     *
     * @return A new {@link SEWBuilder} instance from the {@link #builder} field.
     */
    public SEWBuilder builder() {
        return this.builder = new SEWBuilder(this, ownerSoundEvent);
    }

    /**
     * Gets the cached {@link SEWBuilder} instance from the {@link #builder} if it exists. May be {@code null}. Useful for
     * overriding specific properties after having copied another SEW instance/already set a SEWBuilder.
     *
     * @return The cached {@link SEWBuilder} instance, or {@code null} if the {@link #builder} is {@code null}.
     */
    public SEWBuilder cachedBuilder() {
        return builder;
    }

    /**
     * Gets the parent {@code Supplier<SoundEvent>} of this SEW instance.
     *
     * @return The parent {@code Supplier<SoundEvent>} stored in this SEW instance.
     */
    public Supplier<SoundEvent> getOwnerSoundEvent() {
        return ownerSoundEvent;
    }

    /**
     * Whether this SEW instance is a template. Templates are not stored in {@link #getMappedSews()} and have no parent {@link SoundEvent}.
     *
     * @return Whether this SEW instance is a template.
     *
     * @see #of(SoundEventWrapper, Supplier)
     * @see #createTemplate()
     */
    public boolean isTemplate() {
        return isTemplate;
    }

    /**
     * Gets an immutable view (via {@link ImmutableSortedMap}) of {@link #MAPPED_SEWS}.
     *
     * @return An immutable view (via {@link ImmutableSortedMap}) of {@link #MAPPED_SEWS}.
     */
    public static ImmutableSortedMap<Supplier<SoundEvent>, SoundEventWrapper> getMappedSews() {
        return ImmutableSortedMap.copyOf(MAPPED_SEWS);
    }

    /**
     * A builder class used to construct certain sound event-related data for datagen.
     */
    public static class SEWBuilder {
        private final SoundEventWrapper ownerWrapper;
        private final Supplier<SoundEvent> ownerSoundEvent;

        private SEWBuilder(SoundEventWrapper ownerWrapper, Supplier<SoundEvent> ownerSoundEvent) {
            this.ownerWrapper = ownerWrapper;
            this.ownerSoundEvent = ownerSoundEvent;
        }

        /**
         * Builds a new {@link SoundEventWrapper} using this builder's data. Also maps the owner
         * {@link SoundEventWrapper} to the parent {@linkplain SoundEvent} if isn't already mapped.
         *
         * @return The newly data-populated {@link SoundEventWrapper}.
         */
        public SoundEventWrapper build() {
            if (!ownerWrapper.isTemplate) MAPPED_SEWS.putIfAbsent(ownerSoundEvent, ownerWrapper);
            return ownerWrapper;
        }
    }
}
