package io.github.chaosawakens.api.animation_resolver_system.faal.animation.molang;

import it.unimi.dsi.fastutil.objects.Object2ObjectOpenHashMap;
import team.unnamed.mocha.parser.ast.Expression;

import java.util.List;
import java.util.Map;

public final class MolangExpressionCache {
    private static final Map<String, List<Expression>> EXPRESSIONS_BY_SOURCE = new Object2ObjectOpenHashMap<>();

    private MolangExpressionCache() {

    }
}
