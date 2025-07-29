package io.github.chaosawakens.mixins.common.item;

import com.llamalad7.mixinextras.expression.Definition;
import com.llamalad7.mixinextras.expression.Expression;
import com.llamalad7.mixinextras.injector.ModifyExpressionValue;
import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import com.llamalad7.mixinextras.sugar.Local;
import com.mojang.datafixers.util.Pair;
import io.github.chaosawakens.events.common.ChaosAwakensForgeCommonMiscEvents;
import it.unimi.dsi.fastutil.objects.ObjectObjectMutablePair;
import net.minecraft.world.item.HoeItem;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.level.block.Block;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyVariable;

import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.function.Supplier;

@Mixin(HoeItem.class)
public abstract class HoeItemMixin { // Mixin for properly applying custom hoe tilling behaviour set by BPWs

    private HoeItemMixin() {
        throw new IllegalAccessError("Attempted to construct Mixin Class!");
    }

    @Definition(id = "consumer", local = @Local(type = Consumer.class))
    @Definition(id = "accept", method = "Ljava/util/function/Consumer;accept(Ljava/lang/Object;)V")
    @Definition(id = "pContext", local = @Local(type = UseOnContext.class, argsOnly = true))
    @Expression("consumer.accept(pContext)")
    @WrapOperation(method = "useOn", at = @At(value = "MIXINEXTRAS:EXPRESSION"))
    private <T> void nexus$applyMappedTillingBehaviour(Consumer<T> originalBehaviourConsumer, T originalUseOnCtx, Operation<Void> originalOperation, UseOnContext ctx) {
        Block targetBlock = ctx.getLevel().getBlockState(ctx.getClickedPos()).getBlock();
        Function<Supplier<Block>, ObjectObjectMutablePair<Predicate<UseOnContext>, Consumer<UseOnContext>>> tillingBehaviourFunc = ChaosAwakensForgeCommonMiscEvents.CACHED_BLOCK_TILLING_BEHAVIOURS.get(targetBlock);

        if (tillingBehaviourFunc != null) {
            ObjectObjectMutablePair<Predicate<UseOnContext>, Consumer<UseOnContext>> tillingBehaviourPair = tillingBehaviourFunc.apply(() -> targetBlock);

            if (tillingBehaviourPair != null && tillingBehaviourPair.left() != null && tillingBehaviourPair.right() != null && tillingBehaviourPair.left().test(ctx)) tillingBehaviourPair.right().accept(ctx);
            else originalBehaviourConsumer.accept(originalUseOnCtx);
        } else originalBehaviourConsumer.accept(originalUseOnCtx);
    }

    @ModifyVariable(method = "useOn", at = @At(value = "STORE", ordinal = 0))
    private Pair<Predicate<UseOnContext>, Consumer<UseOnContext>> nexus$remapToolModifierState(Pair<Predicate<UseOnContext>, Consumer<UseOnContext>> originalPair, UseOnContext ctx) {
        Block targetBlock = ctx.getLevel().getBlockState(ctx.getClickedPos()).getBlock();
        Function<Supplier<Block>, ObjectObjectMutablePair<Predicate<UseOnContext>, Consumer<UseOnContext>>> tillingBehaviourFunc = ChaosAwakensForgeCommonMiscEvents.CACHED_BLOCK_TILLING_BEHAVIOURS.get(targetBlock);

        if (tillingBehaviourFunc != null) {
            ObjectObjectMutablePair<Predicate<UseOnContext>, Consumer<UseOnContext>> tillingBehaviourPair = tillingBehaviourFunc.apply(() -> targetBlock);

            return tillingBehaviourPair != null && tillingBehaviourPair.left() != null && tillingBehaviourPair.right() != null ? Pair.of(tillingBehaviourPair.left(), tillingBehaviourPair.right()) : originalPair;
        }

        return originalPair;
    }

    @Definition(id = "test", method = "Ljava/util/function/Predicate;test(Ljava/lang/Object;)Z")
    @Definition(id = "pContext", local = @Local(type = UseOnContext.class, argsOnly = true))
    @Definition(id = "predicate", local = @Local(type = Predicate.class))
    @Expression("predicate.test(pContext)")
    @ModifyExpressionValue(method = "useOn", at = @At(value = "MIXINEXTRAS:EXPRESSION"))
    private boolean nexus$checkForMappedTillingBehaviour(boolean original, UseOnContext ctx) {
        Block targetBlock = ctx.getLevel().getBlockState(ctx.getClickedPos()).getBlock();
        Function<Supplier<Block>, ObjectObjectMutablePair<Predicate<UseOnContext>, Consumer<UseOnContext>>> tillingBehaviourFunc = ChaosAwakensForgeCommonMiscEvents.CACHED_BLOCK_TILLING_BEHAVIOURS.get(targetBlock);

        if (tillingBehaviourFunc != null) {
            ObjectObjectMutablePair<Predicate<UseOnContext>, Consumer<UseOnContext>> tillingBehaviourPair = tillingBehaviourFunc.apply(() -> targetBlock);

            return tillingBehaviourPair != null && tillingBehaviourPair.left() != null && tillingBehaviourPair.right() != null ? tillingBehaviourPair.left().test(ctx) : original;
        } else return original;
    }
}
