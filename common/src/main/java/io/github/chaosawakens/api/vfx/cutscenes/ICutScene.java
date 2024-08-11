package io.github.chaosawakens.api.vfx.cutscenes;

import io.github.chaosawakens.api.vfx.effects.IScreenEffect;
import net.minecraft.client.Camera;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.entity.BlockEntity;

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
     * @see #getStoredBlockEntities()
     * @see #getStoredEntities()
     * @see #getCutSceneSpace()
     */
    List<Block> getStoredBlocks();

    /**
     * Gets the {@link List} of {@linkplain BlockEntity BlockEntities} that are stored within this {@link ICutScene} instance's {@link CutSceneSpace}.
     *
     * @return The {@link List} of {@linkplain BlockEntity BlockEntities} that are stored within this {@link ICutScene} instance's {@link CutSceneSpace}.
     *
     * @see #getStoredBlocks()
     * @see #getStoredEntities()
     * @see #getCutSceneSpace()
     */
    List<BlockEntity> getStoredBlockEntities();

    /**
     * Gets the {@link List} of {@linkplain Entity Entities} that are stored within this {@link ICutScene} instance's {@link CutSceneSpace}.
     *
     * @return The {@link List} of {@linkplain Entity Entities} that are stored within this {@link ICutScene} instance's {@link CutSceneSpace}.
     *
     * @see #getStoredBlocks()
     * @see #getStoredBlockEntities()
     * @see #getCutSceneSpace()
     */
    List<Entity> getStoredEntities();

    /**
     * Gets the {@link Camera} associated with this {@link ICutScene} instance (I.E. The {@link Camera} all stored {@linkplain Player Players} will be viewing).
     *
     * @return The {@link Camera} associated with this {@link ICutScene} instance.
     *
     * @see #getAdditionalCameras()
     */
    Camera getMainCamera();

    /**
     * Gets a {@link List} of additional {@link Camera Cameras} associated with this {@link ICutScene} instance. Useful for cutscenes with effects that require "split" views or similar effects.
     *
     * @return A {@link List} of additional {@link Camera Cameras} associated with this {@link ICutScene} instance. May be empty.
     *
     * @see #getMainCamera()
     */
    List<Camera> getAdditionalCameras();

    /**
     * Gets the {@link List} of {@link IScreenEffect} instances used to render GUI effects or otherwise manipulate the view of the client in any way.
     *
     * @return The {@link List} of {@link IScreenEffect} instances associated with this {@link ICutScene} instance. May be emtpy.
     */
    List<IScreenEffect> getVFX();

    /**
     * Whether this {@link ICutScene} instance can be interrupted by external events (E.G. All players leaving, any given player dying, etc.).
     *
     * @return Whether this {@link ICutScene} instance can be interrupted by external events.
     */
    boolean isInterruptable();

    /**
     * Whether this {@link ICutScene} instance should trigger.
     *
     * @return Whether this {@link ICutScene} instance should trigger.
     */
    boolean shouldTrigger();

    /**
     * Whether this {@link ICutScene} instance is done (including transitioning out).
     *
     * @return Whether this {@link ICutScene} instance is done.
     */
    boolean isDone();
}
