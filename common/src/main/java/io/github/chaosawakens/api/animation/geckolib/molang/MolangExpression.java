package io.github.chaosawakens.api.animation.geckolib.molang;

import java.util.function.DoubleSupplier;

public class MolangExpression {

    public MolangExpression(String expression) {
    }

    public DoubleSupplier computeResult() {
        return () -> 0.0D;
    }

    public static void parse(String expression) {

    }
}
