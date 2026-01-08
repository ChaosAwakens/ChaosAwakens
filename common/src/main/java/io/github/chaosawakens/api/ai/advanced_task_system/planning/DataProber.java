package io.github.chaosawakens.api.ai.advanced_task_system.planning;

import io.github.chaosawakens.api.ai.advanced_task_system.planning.data.TaskData;
import io.github.chaosawakens.api.ai.advanced_task_system.task.TaskDataRepository;

import java.util.Comparator;
import java.util.List;

public interface DataProber {

    List<DataProbe> getActiveProbes();

    <T> Comparator<T> getDataSorter();

    <T> List<T> getDroppedData();

    <T> List<T> acceptAndValidateData(TaskData<T> processedTaskData, TaskDataRepository targetRepo);
}
