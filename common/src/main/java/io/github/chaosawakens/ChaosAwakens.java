package io.github.chaosawakens;

import com.mememan.nexus.event.result.EventResult;
import com.mememan.nexus.template.event.blueprint.common.TickEventBlueprint;

public class ChaosAwakens {

    public static void initialize() {
        TickEventBlueprint.LEVEL_TICK.onEvent((tickEvent) -> {
            return EventResult.success(tickEvent);
        });
    }
}