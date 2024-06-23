package io.github.chaosawakens.mixins;

import com.google.common.collect.ImmutableMap;
import net.minecraftforge.fml.ModList;
import org.objectweb.asm.tree.ClassNode;
import org.spongepowered.asm.mixin.extensibility.IMixinConfigPlugin;
import org.spongepowered.asm.mixin.extensibility.IMixinInfo;

import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.function.BooleanSupplier;

public final class CAMixinConfigPlugin implements IMixinConfigPlugin {
    private static final BooleanSupplier TRUE = () -> true;
    private static final Map<String, BooleanSupplier> CONDITIONALLY_LOADED_MIXINS = ImmutableMap.of(
            "io.github.chaosawakens.mixins.IElementEntityAccessor", () -> ModList.get().isLoaded("theoneprobe"));

    @Override
    public void onLoad(String mixinPackage) {

    }

    @Override
    public String getRefMapperConfig() {
        return null;
    }

    @Override
    public boolean shouldApplyMixin(String targetClassName, String mixinClassName) {
        return CONDITIONALLY_LOADED_MIXINS.getOrDefault(targetClassName, TRUE).getAsBoolean();
    }

    @Override
    public void acceptTargets(Set<String> myTargets, Set<String> otherTargets) {

    }

    @Override
    public List<String> getMixins() {
        return null;
    }

    @Override
    public void preApply(String targetClassName, ClassNode targetClass, String mixinClassName, IMixinInfo mixinInfo) {

    }

    @Override
    public void postApply(String targetClassName, ClassNode targetClass, String mixinClassName, IMixinInfo mixinInfo) {

    }
}
