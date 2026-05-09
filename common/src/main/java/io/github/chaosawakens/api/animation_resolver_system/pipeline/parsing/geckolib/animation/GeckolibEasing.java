package io.github.chaosawakens.api.animation_resolver_system.pipeline.parsing.geckolib.animation;

import com.google.common.collect.BiMap;
import com.google.common.collect.HashBiMap;
import com.google.common.collect.ImmutableList;
import io.github.chaosawakens.api.animation_resolver_system.pipeline.parsing.base.animation.Easing;
import io.github.chaosawakens.util.MathUtil;
import it.unimi.dsi.fastutil.objects.ObjectArrayList;
import org.apache.commons.lang3.function.TriFunction;

import java.util.Arrays;
import java.util.List;
import java.util.Locale;
import java.util.function.BiFunction;
import java.util.function.Supplier;

public class GeckolibEasing implements Easing {
    public static final GeckolibEasing LINEAR = new GeckolibEasing("linear", (easingArgs, timeDelta) -> timeDelta);
    public static final GeckolibEasing EASE_IN = new GeckolibEasing("easeinsine", (easingArgs, timeDelta) -> MathUtil.sineEasing(timeDelta));
    public static final GeckolibEasing EASE_OUT = new GeckolibEasing("easeoutsine", (easingArgs, timeDelta) -> MathUtil.sineEasing(timeDelta), GeckolibEasingType.EASE_OUT);
    public static final GeckolibEasing EASE_IN_OUT = new GeckolibEasing("easeinoutsine", (easingArgs, timeDelta) -> MathUtil.sineEasing(timeDelta), GeckolibEasingType.EASE_IN_OUT);
    public static final GeckolibEasing QUADRATIC_IN = new GeckolibEasing("easeinquad", (easingArgs, timeDelta) -> MathUtil.quadraticEasing(timeDelta));
    public static final GeckolibEasing QUADRATIC_OUT = new GeckolibEasing("easeoutquad", (easingArgs, timeDelta) -> 1 - MathUtil.quadraticEasing(timeDelta), GeckolibEasingType.EASE_OUT);
    public static final GeckolibEasing QUADRATIC_IN_OUT = new GeckolibEasing("easeinoutquad", (easingArgs, timeDelta) -> MathUtil.quadraticEasing(timeDelta), GeckolibEasingType.EASE_IN_OUT);
    public static final GeckolibEasing CUBIC_IN = new GeckolibEasing("easeincubic", (easingArgs, timeDelta) -> MathUtil.cubicEasing(timeDelta));
    public static final GeckolibEasing CUBIC_OUT = new GeckolibEasing("easeoutcubic", (easingArgs, timeDelta) -> MathUtil.cubicEasing(timeDelta), GeckolibEasingType.EASE_OUT);
    public static final GeckolibEasing CUBIC_IN_OUT = new GeckolibEasing("easeinoutcubic", (easingArgs, timeDelta) -> MathUtil.cubicEasing(timeDelta), GeckolibEasingType.EASE_IN_OUT);
    public static final GeckolibEasing QUARTIC_IN = new GeckolibEasing("easeinquart", (easingArgs, timeDelta) -> MathUtil.quarticEasing(timeDelta));
    public static final GeckolibEasing QUARTIC_OUT = new GeckolibEasing("easeoutquart", (easingArgs, timeDelta) -> MathUtil.quarticEasing(timeDelta), GeckolibEasingType.EASE_OUT);
    public static final GeckolibEasing QUARTIC_IN_OUT = new GeckolibEasing("easeinoutquart", (easingArgs, timeDelta) -> MathUtil.quarticEasing(timeDelta), GeckolibEasingType.EASE_IN_OUT);
    public static final GeckolibEasing QUINTIC_IN = new GeckolibEasing("easeinquint", (easingArgs, timeDelta) -> MathUtil.quinticEasing(timeDelta));
    public static final GeckolibEasing QUINTIC_OUT = new GeckolibEasing("easeoutquint", (easingArgs, timeDelta) -> MathUtil.quinticEasing(timeDelta), GeckolibEasingType.EASE_OUT);
    public static final GeckolibEasing QUINTIC_IN_OUT = new GeckolibEasing("easeinoutquint", (easingArgs, timeDelta) -> MathUtil.quinticEasing(timeDelta), GeckolibEasingType.EASE_IN_OUT);
    public static final GeckolibEasing EXPONENTIAL_IN = new GeckolibEasing("easeinexpo", (easingArgs, timeDelta) -> MathUtil.exponentialEasing(timeDelta));
    public static final GeckolibEasing EXPONENTIAL_OUT = new GeckolibEasing("easeoutexpo", (easingArgs, timeDelta) -> MathUtil.exponentialEasing(timeDelta), GeckolibEasingType.EASE_OUT);
    public static final GeckolibEasing EXPONENTIAL_IN_OUT = new GeckolibEasing("easeinoutexpo", (easingArgs, timeDelta) -> MathUtil.exponentialEasing(timeDelta), GeckolibEasingType.EASE_IN_OUT);
    public static final GeckolibEasing CIRCULAR_IN = new GeckolibEasing("easeincirc", (easingArgs, timeDelta) -> MathUtil.circularEasing(timeDelta));
    public static final GeckolibEasing CIRCULAR_OUT = new GeckolibEasing("easeoutcirc", (easingArgs, timeDelta) -> MathUtil.circularEasing(timeDelta), GeckolibEasingType.EASE_OUT);
    public static final GeckolibEasing CIRCULAR_IN_OUT = new GeckolibEasing("easeinoutcirc", (easingArgs, timeDelta) -> MathUtil.circularEasing(timeDelta), GeckolibEasingType.EASE_IN_OUT);
    public static final GeckolibEasing BACK_IN = new GeckolibEasing("easeinback", (easingArgs, timeDelta) -> MathUtil.backEasing(timeDelta, pickArg(easingArgs, 0)));
    public static final GeckolibEasing BACK_OUT = new GeckolibEasing("easeoutback", (easingArgs, timeDelta) -> MathUtil.backEasing(timeDelta, pickArg(easingArgs, 0)), GeckolibEasingType.EASE_OUT);
    public static final GeckolibEasing BACK_IN_OUT = new GeckolibEasing("easeinoutback", (easingArgs, timeDelta) -> MathUtil.backEasing(timeDelta, pickArg(easingArgs, 0)), GeckolibEasingType.EASE_IN_OUT);
    public static final GeckolibEasing ELASTIC_IN = new GeckolibEasing("easeinelastic", (easingArgs, timeDelta) -> MathUtil.elasticEasing(timeDelta, pickArg(easingArgs, 0)));
    public static final GeckolibEasing ELASTIC_OUT = new GeckolibEasing("easeoutelastic", (easingArgs, timeDelta) -> MathUtil.elasticEasing(timeDelta, pickArg(easingArgs, 0)), GeckolibEasingType.EASE_OUT);
    public static final GeckolibEasing ELASTIC_IN_OUT = new GeckolibEasing("easeinoutelastic", (easingArgs, timeDelta) -> MathUtil.elasticEasing(timeDelta, pickArg(easingArgs, 0)), GeckolibEasingType.EASE_IN_OUT);
    private static final BiMap<String, GeckolibEasing> VALUES = HashBiMap.create();
    protected final GeckolibEasingType easingType;
    protected final BiFunction<List<Double>, Double, Double> easingFunction;
    protected final List<Double> defaultEasingArgs;

    public GeckolibEasing(String easingName, GeckolibEasingType easingType, BiFunction<List<Double>, Double, Double> easingFunction, List<Double> easingArgs) {
        this.easingType = easingType;
        this.easingFunction = easingFunction;
        this.defaultEasingArgs = easingArgs;

        VALUES.put(easingName, this);
    }

    public GeckolibEasing(String easingName, BiFunction<List<Double>, Double, Double> easingFunction, GeckolibEasingType easingType) {
        this(easingName, easingType, easingFunction, ObjectArrayList.of());
    }

    public GeckolibEasing(String easingName, BiFunction<List<Double>, Double, Double> easingFunction) {
        this(easingName, GeckolibEasingType.EASE_IN, easingFunction, ObjectArrayList.of());
    }

    public static Supplier<GeckolibEasing> getEasing(String easingName) {
        return () -> VALUES.getOrDefault(easingName.toLowerCase(Locale.ROOT).trim().replaceAll("\t\n", ""), LINEAR);
    }

    public static String getEasingName(Supplier<GeckolibEasing> easing) {
        return VALUES.inverse().getOrDefault(easing.get(), "linear");
    }

    public static Double pickArg(List<Double> easingArgs, int idx) {
        return easingArgs.size() > idx ? easingArgs.get(idx) : null;
    }

    @Override
    public double apply(double timeDelta) {
        return easingType.transformAndApply(timeDelta, defaultEasingArgs, easingFunction);
    }

    public double apply(double timeDelta, List<Double> easingArgs) {
        return easingType.transformAndApply(timeDelta, easingArgs, easingFunction);
    }

    public GeckolibEasingType getEasingType() {
        return easingType;
    }

    public ImmutableList<Double> getDefaultEasingArgs() {
        return ImmutableList.copyOf(defaultEasingArgs);
    }

    public enum GeckolibEasingType {
        EASE_IN((timeDelta, easingArgs, originalFunction) -> originalFunction.apply(easingArgs, timeDelta)),
        EASE_OUT((timeDelta, easingArgs, originalFunction) -> 1 - originalFunction.apply(easingArgs, 1 - timeDelta)),
        EASE_IN_OUT((timeDelta, easingArgs, originalFunction) -> {
            if (timeDelta < 0.5) return originalFunction.apply(easingArgs, timeDelta * 2);
            else return 1 - originalFunction.apply(easingArgs, (1 - timeDelta) * 2);
        });

        private final TriFunction<Double, List<Double>, BiFunction<List<Double>, Double, Double>, Double> easingTypeFunction;

        GeckolibEasingType(TriFunction<Double, List<Double>, BiFunction<List<Double>, Double, Double>, Double> easingFunction) {
            this.easingTypeFunction = easingFunction;
        }

        public static GeckolibEasingType byName(String easingTypeName) {
            return Arrays.stream(values())
                    .filter(easingType -> easingType.name().equalsIgnoreCase(easingTypeName))
                    .findFirst()
                    .orElse(EASE_IN);
        }

        public static String getName(GeckolibEasingType easingType) {
            return easingType.name().toLowerCase(Locale.ROOT);
        }

        public double transformAndApply(double timeInSeconds, List<Double> easingArgs, BiFunction<List<Double>, Double, Double> originalEasingFunction) {
            return easingTypeFunction.apply(timeInSeconds, easingArgs, originalEasingFunction);
        }
    }
}
