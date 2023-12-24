package io.github.chaosawakens.api.animation;

import it.unimi.dsi.fastutil.objects.ObjectOpenHashSet;
import it.unimi.dsi.fastutil.objects.ObjectSet;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.Tuple;

/**
 * TODO Unused
 * Refactored by ya boi MemeMan with modifications, original implementation by DerToaster in MHLib repo (Now known as EEL for 1.20.1+).
 */
public class DiskSaveRunner extends ObjectOpenHashSet<Tuple<ResourceLocation, byte[]>> implements Runnable, ObjectSet<Tuple<ResourceLocation, byte[]>> {
    private static final long serialVersionUID = 42L; // Avoid InvalidClassExceptions, can't ever trust that compiler to declare its own consistent SVUID :skull:

    @Override
    public void run() {

    }
}
