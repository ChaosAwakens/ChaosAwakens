package io.github.chaosawakens.api.ai.advanced_task_system.task;

import com.google.common.collect.ImmutableList;
import io.github.chaosawakens.api.ai.advanced_task_system.planning.DataProber;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerLevel;

import java.util.List;

public class TaskDeviser {
    protected final ResourceLocation deviserId;
    protected final TaskDataRepository ownerRepo;
    protected final DataProber dataProber;
    protected final List<Task> cachedTasks;

    public TaskDeviser(ResourceLocation deviserId, TaskDataRepository ownerRepo, DataProber dataProber, List<Task> cachedTasks) {
        this.deviserId = deviserId;
        this.ownerRepo = ownerRepo;
        this.dataProber = dataProber;
        this.cachedTasks = cachedTasks;
    }

    public ResourceLocation getId() {
        return deviserId;
    }

    public TaskDataRepository getDataRepository() {
        return ownerRepo;
    }

    public DataProber getDataProber() {
        return dataProber;
    }

    public List<Task> getCachedTasks() {
        return ImmutableList.copyOf(cachedTasks);
    }

    public void updateTasks(ServerLevel curServerLevel) {
        // Prober update
        // Data repo update
        // Base filtration/validation
        // Priority-based sorting of tasks
        // Actual task ticking
    }
}
