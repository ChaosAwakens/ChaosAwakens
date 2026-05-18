package io.github.chaosawakens.mixins.temp;

import com.llamalad7.mixinextras.expression.Definition;
import com.llamalad7.mixinextras.expression.Expression;
import com.llamalad7.mixinextras.injector.ModifyExpressionValue;
import com.llamalad7.mixinextras.sugar.Local;
import it.unimi.dsi.fastutil.objects.ObjectArrayList;
import journeymap.client.model.EntityDTO;
import journeymap.client.model.MapState;
import net.minecraft.client.Minecraft;
import net.minecraft.client.server.IntegratedServer;
import net.minecraft.server.level.ServerChunkCache;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.level.chunk.ChunkGenerator;
import net.minecraft.world.level.levelgen.RandomState;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;

import java.util.stream.Collectors;

@Mixin(value = MapState.class, remap = false)
public abstract class MapStateMixin {
    @Unique
    private static final Minecraft MC = Minecraft.getInstance();

    private MapStateMixin() {
        throw new IllegalAccessError("Attempted to construct a Mixin Class!");
    }

    @Definition(id = "biome", field = "Ljourneymap/client/model/EntityDTO;biome:Ljava/lang/String;")
    @Definition(id = "playerDTO", local = @Local(type = EntityDTO.class))
    @Expression("playerDTO.biome")
    @ModifyExpressionValue(method = "refresh", at = @At(value = "MIXINEXTRAS:EXPRESSION"))
    private String chaosawakens$refresh(String original) {
        ObjectArrayList<String> otherDebugInfo = new ObjectArrayList<>();

        IntegratedServer intSrvr = MC.getSingleplayerServer();
        ServerLevel curServerLevel = intSrvr != null ? intSrvr.getLevel(MC.level.dimension()) : null;
        ServerChunkCache spChunkSource = curServerLevel == null ? null : curServerLevel.getChunkSource();
        ChunkGenerator curChunkGen = spChunkSource == null ? null : spChunkSource.getGenerator();
        RandomState srvrRandState = spChunkSource == null ? null : spChunkSource.randomState();
        Entity activeCamera = MC.getCameraEntity();

        assert curChunkGen != null;
        curChunkGen.addDebugScreenInfo(otherDebugInfo, srvrRandState, activeCamera.blockPosition());

        String debugInfo = otherDebugInfo.stream()
                .collect(Collectors.joining(", ", " [", "]"));

        return original.concat(debugInfo);
    }
}
