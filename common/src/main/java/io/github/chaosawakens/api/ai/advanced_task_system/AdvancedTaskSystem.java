package io.github.chaosawakens.api.ai.advanced_task_system;

import com.mememan.nexus.template.event.blueprint.server.ServerLifeCycleEventBlueprint;
import io.github.chaosawakens.api.ai.advanced_task_system.task.TaskDeviser;
import it.unimi.dsi.fastutil.Hash;
import it.unimi.dsi.fastutil.ints.IntArraySet;
import it.unimi.dsi.fastutil.ints.IntSet;
import it.unimi.dsi.fastutil.objects.Object2ObjectMap;
import it.unimi.dsi.fastutil.objects.Object2ObjectMaps;
import it.unimi.dsi.fastutil.objects.Object2ObjectOpenCustomHashMap;
import net.minecraft.resources.ResourceLocation;

import java.util.Map;
import java.util.Objects;

public final class AdvancedTaskSystem {
    private static final Object2ObjectMap<TaskDeviser, IntSet> TASK_DEVISERS = Object2ObjectMaps.synchronize(new Object2ObjectOpenCustomHashMap<>(new Hash.Strategy<TaskDeviser>() {
        @Override
        public int hashCode(TaskDeviser o) {
            return o.hashCode();
        }

        @Override
        public boolean equals(TaskDeviser a, TaskDeviser b) {
            return Objects.equals(a.getId(), b.getId());
        }
    }));

    private AdvancedTaskSystem() {

    }

    public static void assignToDeviser(int targetEntityId, TaskDeviser targetDeviser) {
        TASK_DEVISERS
                .computeIfAbsent(targetDeviser, oK -> new IntArraySet())
                .add(targetEntityId);
    }

    public static void assignToDeviser(int targetEntityId, ResourceLocation existingDeviserId) {
        TASK_DEVISERS.entrySet()
                .stream()
                .filter(curEntry -> curEntry.getKey().getId().equals(existingDeviserId))
                .map(Map.Entry::getValue)
                .findFirst()
                .ifPresent(mappedDeviserIds -> mappedDeviserIds.add(targetEntityId));
    }

    public static void initializeATS() {
        ServerLifeCycleEventBlueprint.SERVER_STARTED.onEvent(event -> {
            initializeTickHooks();
        });
    }

    public static void shutdownATS() {
        ServerLifeCycleEventBlueprint.SERVER_STOPPING.onEvent(event -> {

        });
    }

    private static void initializeTickHooks() {

    }
}
