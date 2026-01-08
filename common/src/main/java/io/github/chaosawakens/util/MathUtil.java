package io.github.chaosawakens.util;

public final class MathUtil {
    public static final double PENNER_CONSTANT = 1.70158D;

    private MathUtil() {
        throw new UnsupportedOperationException("Attempted to construct instance of utility class! (MathUtil)");
    }

    public static double sineEasing(double timeDelta) {
        return 1 - Math.cos(timeDelta * Math.PI / 2.0D);
    }

    public static double quadraticEasing(double timeDelta) {
        return timeDelta * timeDelta;
    }

    public static double cubicEasing(double timeDelta) {
        return timeDelta * timeDelta * timeDelta;
    }

    public static double quarticEasing(double timeDelta) {
        return timeDelta * timeDelta * timeDelta * timeDelta;
    }

    public static double quinticEasing(double timeDelta) {
        return timeDelta * timeDelta * timeDelta * timeDelta * timeDelta;
    }
    public static double exponentialEasing(double timeDelta) {
        return Math.pow(2, 10 * (timeDelta - 1));
    }

    public static double circularEasing(double timeDelta) {
        return 1 - Math.sqrt(1 - timeDelta * timeDelta);
    }

    public static double backEasing(double timeDelta, Double overshootFactor) {
        double chosenOvershootFactor = overshootFactor == null ? PENNER_CONSTANT : overshootFactor * PENNER_CONSTANT;

        return timeDelta * timeDelta * ((chosenOvershootFactor + 1) * timeDelta - chosenOvershootFactor);
    }

    public static double elasticEasing(double timeDelta, Double freqAmp) {
        double chosenFrequencyFactor = freqAmp == null ? 1.0D : freqAmp;

        return 1 - Math.pow(Math.cos(timeDelta * (Math.PI / 2.0F)), 3) * Math.cos(timeDelta * chosenFrequencyFactor * Math.PI); // Have it behave deterministically as in Geckolib (different function of t by default, oscillations die out as t -> 1)
    }
}