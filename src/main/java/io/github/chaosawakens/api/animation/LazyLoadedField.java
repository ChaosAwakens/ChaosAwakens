package io.github.chaosawakens.api.animation;

import com.google.common.base.Supplier;

/**
 * TODO Unused
 * Credits to DerToaster for LLF implementation. Constantly updated lazy object holder.
 */
public class LazyLoadedField<T> implements Supplier<T> {
    private T value = null;
    private int lifetime = -1;
    private long lastSet = System.currentTimeMillis();
    private final Supplier<T> function;

    public LazyLoadedField(final Supplier<T> function) {
        this.function = function;
    }

    public LazyLoadedField(final Supplier<T> function, final int lifetime) {
        this(function);
        this.lifetime = lifetime;
    }

    public void reset() {
        this.value = null;
    }

    @Override
    public T get() {
        if (this.lifetime > 0) {
            if (System.currentTimeMillis() - this.lastSet >= this.lifetime) {
                this.lastSet = System.currentTimeMillis();
                this.value = this.function.get();
            }
        }

        if (this.value == null) this.value = this.function.get();

        return this.value;
    }
}
