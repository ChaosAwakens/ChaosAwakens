package io.github.chaosawakens.mixins.temp;

import com.llamalad7.mixinextras.injector.ModifyReturnValue;
import it.unimi.dsi.fastutil.objects.ObjectArrayList;
import journeymap.client.ui.fullscreen.layer.BlockInfoLayer;
import net.minecraft.client.Minecraft;
import net.minecraft.client.server.IntegratedServer;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerChunkCache;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.level.chunk.ChunkGenerator;
import net.minecraft.world.level.levelgen.RandomState;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;

import java.util.stream.Collectors;

@Mixin(value = BlockInfoLayer.class, remap = false)
public abstract class BlockInfoLayerMixin {
    @Unique
    private static final Minecraft MC = Minecraft.getInstance();

    private BlockInfoLayerMixin() {
        throw new IllegalAccessError("Attempted to construct a Mixin Class!");
    }

    @ModifyReturnValue(method = "getBlockInfo(Lnet/minecraft/core/BlockPos;Ljava/lang/String;Ljourneymap/client/model/RegionCoord;)Ljava/lang/String;", at = @At(value = "RETURN"))
    private String chaosawakens$getBlockInfo(String original, BlockPos blockPos) {
        ObjectArrayList<String> otherDebugInfo = new ObjectArrayList<>();

        IntegratedServer intSrvr = MC.getSingleplayerServer();
        ServerLevel curServerLevel = intSrvr != null ? intSrvr.getLevel(MC.level.dimension()) : null;
        ServerChunkCache spChunkSource = curServerLevel == null ? null : curServerLevel.getChunkSource();
        ChunkGenerator curChunkGen = spChunkSource == null ? null : spChunkSource.getGenerator();
        RandomState srvrRandState = spChunkSource == null ? null : spChunkSource.randomState();

        assert curChunkGen != null;
        curChunkGen.addDebugScreenInfo(otherDebugInfo, srvrRandState, blockPos);

        String debugInfo = otherDebugInfo.stream()
                .collect(Collectors.joining(", ", " [", "]"));

        return original.concat(debugInfo);
    }
}
