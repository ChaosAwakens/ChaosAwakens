package io.github.chaosawakens.api.ai.pathnav.config;

import com.google.common.base.Predicates;
import io.github.chaosawakens.api.ai.pathnav.path.PathClearanceType;
import net.minecraft.world.entity.Entity;

import java.util.function.Predicate;

public record PathNavigationConfig(Predicate<Entity> stateRefreshPredicate, PathClearanceType... clearanceTypes) {

    public static PathNavigationConfig defaultConfig() {
        return new PathNavigationConfig(Predicates.alwaysTrue(), PathClearanceType.SURFACE);
    }
}
