package io.github.chaosawakens.core.template;

import com.mememan.nexus.property_wrapper.def.entity.EntityTypePropertyWrapper;
import net.minecraft.world.entity.Entity;

public final class CAETPWTemplates {
    public static final EntityTypePropertyWrapper<Entity> BLOB = new EntityTypePropertyWrapper<>().builder().withClientData(() -> CAClientDataEntryTemplates.BLOB).build();

    public static final EntityTypePropertyWrapper<Entity> NOOP = new EntityTypePropertyWrapper<>().builder().withClientData(() -> CAClientDataEntryTemplates.NOOP).build();

    public static final EntityTypePropertyWrapper<Entity> GECKOLIB_ANIMATED_BOSS = new EntityTypePropertyWrapper<>().builder().withClientData(() -> CAClientDataEntryTemplates.GECKOLIB_ANIMATED_ENTITY).build();

    private CAETPWTemplates() {
        throw new IllegalAccessError("Attempted to construct instance of template class! (CAETPWTemplates)");
    }
}
