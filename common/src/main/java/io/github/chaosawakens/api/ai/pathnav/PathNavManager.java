package io.github.chaosawakens.api.ai.pathnav;

import io.github.chaosawakens.api.ai.pathnav.config.PathNavigationConfig;
import io.github.chaosawakens.api.ai.pathnav.path.PathState;
import it.unimi.dsi.fastutil.ints.Int2ObjectOpenHashMap;
import net.minecraft.core.Position;

/**
 * The primary pathnav class responsible for managing pathfinding-related tasks in a multithreaded environment.
 */
public final class PathNavManager {
    private static final Int2ObjectOpenHashMap<PathState> ACTIVE_PATH_STATES = new Int2ObjectOpenHashMap<>();

    private PathNavManager() { // Stateless, so uhh, NO-OP constructor makes sense here

    }

    public static void submitPathState(int targetEntityId, Position targetPos, PathNavigationConfig config) {

    }

    public static void submitPathState(int targetEntityId, Position targetPos) {
        submitPathState(targetEntityId, targetPos, PathNavigationConfig.defaultConfig());
    }
}
