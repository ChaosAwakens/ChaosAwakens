package io.github.chaosawakens.api.ai.task;

public interface Task {

    void start();

    void tick();

    void stop();
}
