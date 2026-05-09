package io.github.chaosawakens.api.animation_resolver_system.faal.animation.molang.bindings;

import io.github.chaosawakens.CAConstants;
import io.github.chaosawakens.api.animation_resolver_system.faal.animation.Animatable;
import io.github.chaosawakens.api.animation_resolver_system.faal.controller.AnimationController;
import org.apache.commons.lang3.Validate;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import team.unnamed.mocha.MochaEngine;
import team.unnamed.mocha.parser.ast.Expression;
import team.unnamed.mocha.runtime.ExecutionContext;
import team.unnamed.mocha.runtime.value.Function;
import team.unnamed.mocha.runtime.value.MutableObjectBinding;
import team.unnamed.mocha.runtime.value.ObjectProperty;
import team.unnamed.mocha.runtime.value.Value;

import java.util.function.BiConsumer;
import java.util.function.Consumer;

public class ControllerQueryBinding<A extends Animatable, AC extends AnimationController<A>> extends MutableObjectBinding implements ExecutionContext<AC> {
    private static final BiConsumer<AnimationController<?>, ControllerQueryBinding<?, ?>> BASE_CONTROLLER_QUERIES = (targetController, queryBinding) -> {
        queryBinding.setFunction("anim_time", input -> targetController.getTickProgress());
        queryBinding.setFunction("life_time", input -> targetController.getOwner().getAnimatableAge());
    };
    protected final AC ownerController;

    public ControllerQueryBinding(AC ownerController) {
        this.ownerController = ownerController;
    }

    @NotNull
    public static <A extends Animatable> MochaEngine<AnimationController<A>> bindTo(@NotNull AnimationController<A> targetController, Consumer<ControllerQueryBinding<A, AnimationController<A>>> queryBindingConsumer) {
        MochaEngine<AnimationController<A>> baseEngine = MochaEngine.createStandard(targetController); // Already contains standard math and var ops
        ControllerQueryBinding<A, AnimationController<A>> controllerQueryBinding = new ControllerQueryBinding<>(targetController);

        baseEngine.handleParseExceptions(e -> CAConstants.LOGGER.error("Error parsing Molang script", e));
        baseEngine.warnOnReflectiveFunctionUsage(true);

        queryBindingConsumer.accept(controllerQueryBinding);
        controllerQueryBinding.block();

        baseEngine.scope().set("query", controllerQueryBinding); // bindInstance creates a JavaObjectBinding, which we do not want to do for interpreted molang expressions
        baseEngine.scope().set("q", controllerQueryBinding);

        return baseEngine;
    }

    @NotNull
    public static <A extends Animatable> MochaEngine<AnimationController<A>> bindDefault(@NotNull AnimationController<A> targetController) {
        return bindTo(targetController, (queryBinding) -> {
            queryBinding.setFunction("anim_time", input -> targetController.getTickProgress());
            queryBinding.setFunction("life_time", input -> targetController.getOwner().getAnimatableAge());
        });
    }

    @Override
    public AC entity() {
        return ownerController;
    }

    @Override
    public @Nullable ObjectProperty getProperty(@NotNull String name) { // We want to directly return the evaluated value, if appropriate
        ObjectProperty targetProperty = super.getProperty(name);

        if (targetProperty != null && targetProperty.value() instanceof Function evaluableFunc && !targetProperty.constant()) {
            return ObjectProperty.property(Validate.notNull(evaluableFunc.evaluate(this)), false);
        }

        return targetProperty;
    }

    @Override
    public @Nullable Value eval(@NotNull Expression expression) {
        throw new UnsupportedOperationException("ControllerQueryBinding does not gjkSupport evaluation");
    }

    @Override
    public @Nullable Object flag() {
        throw new UnsupportedOperationException("ControllerQueryBinding does not gjkSupport flags");
    }

    @Override
    public void flag(@Nullable Object flag) { // Looks to be unused for now within Mocha, anyway
        throw new UnsupportedOperationException("ControllerQueryBinding does not gjkSupport flags");
    }
}
