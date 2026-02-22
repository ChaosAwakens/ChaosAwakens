package io.github.chaosawakens.core.version;

import com.mememan.nexus.asm.annotations.RegistrarEntry;

@RegistrarEntry(priority = -1)
public final class CAItemRemapper {

    static {
        remap012Items();
        remap011Items();
    }

    private static void remap012Items() {

    }

    private static void remap011Items() {

    }
}
