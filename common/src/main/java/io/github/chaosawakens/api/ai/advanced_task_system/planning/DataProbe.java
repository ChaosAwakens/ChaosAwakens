package io.github.chaosawakens.api.ai.advanced_task_system.planning;

import io.github.chaosawakens.api.ai.advanced_task_system.planning.data.TaskData;
import net.minecraft.world.entity.Entity;

import java.util.List;

@FunctionalInterface
public interface DataProbe {

    <T> List<TaskData<T>> probeData(Entity owner);
}
