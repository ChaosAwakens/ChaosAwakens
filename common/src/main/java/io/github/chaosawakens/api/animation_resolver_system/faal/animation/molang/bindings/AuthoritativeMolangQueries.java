package io.github.chaosawakens.api.animation_resolver_system.faal.animation.molang.bindings;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import team.unnamed.mocha.runtime.value.ObjectProperty;
import team.unnamed.mocha.runtime.value.ObjectValue;
import team.unnamed.mocha.util.CaseInsensitiveStringHashMap;

import java.util.Map;

public class AuthoritativeMolangQueries implements ObjectValue {
    public static final String ANIM_TIME = "anim_time";
    public static final String LIFETIME = "life_time";
    protected final Map<String, ObjectProperty> queries = new CaseInsensitiveStringHashMap<>();

    private AuthoritativeMolangQueries() {
        setFunction(ANIM_TIME, ctx -> ctx);
        setFunction(LIFETIME, ctx -> ctx);
    }

    @Override
    public @Nullable ObjectProperty getProperty(@NotNull String name) {
        return queries.get(name);
    }

}
