package io.github.chaosawakens.mixins.block;

import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import io.github.chaosawakens.content.block.vegetation.generic.DefaultableCropHeadBlock;
import net.minecraft.world.level.block.GrowingPlantHeadBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.Property;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;

@Mixin(GrowingPlantHeadBlock.class)
public abstract class GrowingPlantHeadBlockMixin {

    private GrowingPlantHeadBlockMixin() {
        throw new IllegalAccessError("Attempted to construct Mixin class! (GrowingPlantHeadBlockMixin)");
    }

    @WrapOperation(method = "<init>", at = @At(value = "INVOKE", target = "Lnet/minecraft/world/level/block/state/BlockState;setValue(Lnet/minecraft/world/level/block/state/properties/Property;Ljava/lang/Comparable;)Ljava/lang/Object;"))
    private <T extends Comparable<T>> Object chaosawakens$skipVanillaAgeSetValue(BlockState state, Property<T> property, T value, Operation<BlockState> original) {
        if ((Object) this instanceof DefaultableCropHeadBlock) return state; // We need to do it like this to inject into the bytecode before it runs and inevitably throws, since the property isn't added in #createBaseDefinition

        return original.call(state, property, value);
    }
}