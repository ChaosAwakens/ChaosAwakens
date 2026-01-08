package io.github.chaosawakens.api.ai.advanced_task_system.planning;

import io.github.chaosawakens.api.ai.advanced_task_system.planning.data.TaskData;
import io.github.chaosawakens.api.ai.advanced_task_system.task.TaskDataRepository;

import java.util.Comparator;
import java.util.List;

public class BaseDataProber implements DataProber {

    public BaseDataProber() {

    }

    @Override
    public List<DataProbe> getActiveProbes() {
        return List.of();
    }

    @Override
    public <T> Comparator<T> getDataSorter() {
        return null;
    }

    @Override
    public <T> List<T> getDroppedData() {
        return List.of();
    }

    @Override
    public <T> List<T> acceptAndValidateData(TaskData<T> processedTaskData, TaskDataRepository targetRepo) {
        return List.of();
    }
}
