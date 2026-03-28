package io.github.chaosawakens.util;

import com.mememan.nexus.client.block.WrappedBlockColor;
import net.minecraft.client.renderer.BiomeColors;
import net.minecraft.world.level.GrassColor;
import net.minecraft.world.level.block.Block;

import java.util.function.Supplier;

public class VanillaUtil {

    public static WrappedBlockColor standardLeavesColor(Supplier<Block> targetBlock) {
        return (targetState, tintGetter, targetPos, tint) -> tintGetter != null && targetPos != null
                ? BiomeColors.getAverageFoliageColor(tintGetter, targetPos)
                : GrassColor.get(0.5D, 1.0D);
    }
}
