package io.github.chaosawakens.mixins.common.item;

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
}
