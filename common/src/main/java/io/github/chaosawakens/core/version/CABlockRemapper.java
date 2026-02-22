package io.github.chaosawakens.core.version;

import com.mememan.nexus.asm.annotations.RegistrarEntry;

@RegistrarEntry(priority = -1)
public final class CABlockRemapper {

    static {
        remap012Blocks();
        remap011Blocks();
    }

    private static void remap012Blocks() {

    }

    private static void remap011Blocks() {

    }
}
