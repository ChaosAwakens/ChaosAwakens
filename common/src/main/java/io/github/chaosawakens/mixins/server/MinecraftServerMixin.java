package io.github.chaosawakens.mixins.server;

import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import io.github.chaosawakens.CAConstants;
import net.minecraft.resources.ResourceKey;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.progress.ChunkProgressListener;
import net.minecraft.world.RandomSequences;
import net.minecraft.world.level.CustomSpawner;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.dimension.DimensionType;
import net.minecraft.world.level.dimension.LevelStem;
import net.minecraft.world.level.storage.LevelStorageSource;
import net.minecraft.world.level.storage.ServerLevelData;
import net.minecraft.world.level.storage.WorldData;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;

import java.util.List;
import java.util.Optional;
import java.util.concurrent.Executor;

@Mixin(MinecraftServer.class)
public abstract class MinecraftServerMixin {
    @Shadow
    @Final
    protected WorldData worldData;

    private MinecraftServerMixin() {
        throw new IllegalAccessError("Attempted to construct standalone Mixin Class!");
    }

    @WrapOperation(method = "createLevels", at = @At(value = "NEW", target = "(Lnet/minecraft/server/MinecraftServer;Ljava/util/concurrent/Executor;Lnet/minecraft/world/level/storage/LevelStorageSource$LevelStorageAccess;Lnet/minecraft/world/level/storage/ServerLevelData;Lnet/minecraft/resources/ResourceKey;Lnet/minecraft/world/level/dimension/LevelStem;Lnet/minecraft/server/level/progress/ChunkProgressListener;ZJLjava/util/List;ZLnet/minecraft/world/RandomSequences;)Lnet/minecraft/server/level/ServerLevel;", ordinal = 1))
    private ServerLevel chaosawakens$createLevels(MinecraftServer curServer, Executor mainThreadExecutor, LevelStorageSource.LevelStorageAccess serverStorageAccess, ServerLevelData dummyData, ResourceKey<Level> curLevelKey, LevelStem curLevelStem, ChunkProgressListener chunkProgressListener, boolean isDebugWorld, long obfuscatedWorldSeed, List<CustomSpawner> customSpawners, boolean shouldTickTime, RandomSequences randomSequences, Operation<ServerLevel> original) {
        Optional<DimensionType> potentialCADim = curLevelStem.type().unwrap().right();
        boolean isNaturalDimension = potentialCADim.isEmpty() || potentialCADim.get().natural();
        ServerLevelData targetData = curLevelKey.location().getNamespace().equals(CAConstants.MODID) && isNaturalDimension
                ? worldData.overworldData() // Use overworld data globally for natural CA dimensions since the game was designed that way anyway
                : dummyData;

        return original.call(curServer, mainThreadExecutor, serverStorageAccess, targetData, curLevelKey, curLevelStem, chunkProgressListener, isDebugWorld, obfuscatedWorldSeed, customSpawners, shouldTickTime, randomSequences);
    }
}
