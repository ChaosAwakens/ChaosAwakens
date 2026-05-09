package io.github.chaosawakens.api.misc;

@FunctionalInterface
public interface QuadPredicate<F, S, T, U> {

    boolean test(F first, S second, T third, U fourth);
}


