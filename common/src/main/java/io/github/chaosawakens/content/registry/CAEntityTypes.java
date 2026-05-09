package io.github.chaosawakens.content.registry;

import com.google.common.collect.ImmutableList;
import com.mememan.nexus.asm.annotations.RegistrarEntry;
import it.unimi.dsi.fastutil.objects.ObjectArrayList;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;

import java.util.function.Supplier;

@RegistrarEntry
public final class CAEntityTypes {
    protected static final ObjectArrayList<Supplier<EntityType<Entity>>> ENTITY_TYPES = new ObjectArrayList<>();

    //


    public static ImmutableList<Supplier<EntityType<Entity>>> getEntityTypes() {
        return ImmutableList.copyOf(ENTITY_TYPES);
    }
}
