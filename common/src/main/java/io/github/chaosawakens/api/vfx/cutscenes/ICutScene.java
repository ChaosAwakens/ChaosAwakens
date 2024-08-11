package io.github.chaosawakens.api.vfx.cutscenes;

import net.minecraft.world.entity.Entity;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.block.Block;

import java.util.List;

/**
 * The base interface representing any scripted sequence of events in any given {@link Level}. <b>Currently programmatic!</b> Datapackability coming with the release of VFXLib:tm: (The Chaos Team's first official API)
 */
public interface ICutScene {

    /**
     * @return The {@link LevelAccessor} this {@link ICutScene} instance takes place in.
     */
    LevelAccessor getLevel();

    /**
     * Gets the current progress of this {@link ICutScene} instance, in ticks, since it started.
     *
     * @return The current progress of this {@link ICutScene} instance, in ticks, since it started.
     */
    double getProgress();

    /**
     * Gets the duration of this {@link ICutScene} instance, in ticks.
     *
     * @return The duration of this {@link ICutScene} instance, in ticks.
     */
    double getDuration();

    /**
     * Whether this {@link ICutScene} instance should pause its sequence of events.
     *
     * @return Whether this {@link ICutScene} instance should pause its sequence of events.
     */
    boolean shouldPause();

    /**
     * Gets the object representing the bounding box, or level space, encompassed by this {@link ICutScene} instance.
     *
     * @return The bounding box, or level space, encompassed by this {@link ICutScene} instance.
     */
    CutSceneSpace getCutSceneSpace();

    /**
     * Gets the {@link List} of {@linkplain Block Blocks} that are stored within this {@link ICutScene} instance's {@link CutSceneSpace}.
     *
     * @return The {@link List} of {@linkplain Block Blocks} that are stored within this {@link ICutScene} instance's {@link CutSceneSpace}.
     *
     * @see #getStoredEntities()
     * @see #getCutSceneSpace()
     */
    List<Block> getStoredBlocks();

    /**
     * Gets the {@link List} of {@linkplain Entity Entities} that are stored within this {@link ICutScene} instance's {@link CutSceneSpace}.
     *
     * @return The {@link List} of {@linkplain Entity Entities} that are stored within this {@link ICutScene} instance's {@link CutSceneSpace}.
     *
     * @see #getStoredBlocks()
     * @see #getCutSceneSpace()
     */
    List<Entity> getStoredEntities();
}
