package io.github.chaosawakens.api.ai.advanced_task_system.planning.data;

import io.github.chaosawakens.api.ai.advanced_task_system.planning.DataProber;

import java.util.List;
import java.util.function.Function;

public interface TaskData<T> {

    <I> void passComputableData(Function<I, T> dataMapper);

    List<T> popData(DataProber dataProber);
}
