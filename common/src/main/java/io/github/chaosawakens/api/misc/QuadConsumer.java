package io.github.chaosawakens.api.misc;

@FunctionalInterface
public interface QuadConsumer<F, S, T, U> {

    void accept(F first, S second, T third, U fourth);
}
