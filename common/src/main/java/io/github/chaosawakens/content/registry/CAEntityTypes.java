package io.github.chaosawakens.content.registry;

import com.google.common.collect.ImmutableList;
import com.mememan.nexus.asm.annotations.RegistrarEntry;
import com.mememan.nexus.template.property_wrapper.EntityTypePropertyWrapperTemplates;
import io.github.chaosawakens.CAConstants;
import io.github.chaosawakens.content.entity.boss.HerculesBeetle;
import io.github.chaosawakens.content.entity.projectiles.RayGunProjectileEntity;
import io.github.chaosawakens.content.entity.projectiles.ThunderStaffProjectileEntity;
import io.github.chaosawakens.core.template.CAClientDataEntryTemplates;
import io.github.chaosawakens.core.template.CAETPWTemplates;
import it.unimi.dsi.fastutil.objects.ObjectArrayList;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MobCategory;

import java.util.function.Supplier;

@RegistrarEntry
public final class CAEntityTypes {
    protected static final ObjectArrayList<Supplier<EntityType<Entity>>> ENTITY_TYPES = new ObjectArrayList<>();

    // Bosses
    public static final Supplier<EntityType<HerculesBeetle>> HERCULES_BEETLE = EntityTypePropertyWrapperTemplates.registerAndChain(CAConstants.prefix("hercules_beetle"), () ->
            EntityType.Builder.of(HerculesBeetle::new, MobCategory.MONSTER)
                    .sized(2.0F, 2.0F)
                    .clientTrackingRange(20)
                    .build(CAConstants.prefix("hercules_beetle").toString()))
            .withAttributes(HerculesBeetle::createAttributes)
            .withClientData(() -> CAClientDataEntryTemplates.HERCULES_BEETLE)
            .buildAndGet();

    // Projectiles
    public static final Supplier<EntityType<ThunderStaffProjectileEntity>> THUNDER_BALL = EntityTypePropertyWrapperTemplates.registerEntityTypeFromTemplate(CAConstants.prefix("thunder_ball"), () -> EntityType.Builder
            .<ThunderStaffProjectileEntity>of(ThunderStaffProjectileEntity::new, MobCategory.MISC)
            .sized(0.25F, 0.25F)
            .clientTrackingRange(4)
            .updateInterval(20)
            .build(CAConstants.prefix("thunder_ball").toString()), CAETPWTemplates.NOOP, ENTITY_TYPES);

    public static final Supplier<EntityType<RayGunProjectileEntity>> RAY_GUN_LASER = EntityTypePropertyWrapperTemplates.registerEntityTypeFromTemplate(CAConstants.prefix("ray_gun_laser"), () -> EntityType.Builder
            .<RayGunProjectileEntity>of(RayGunProjectileEntity::new, MobCategory.MISC)
            .sized(1F, 1F)
            .clientTrackingRange(4)
            .updateInterval(20)
            .build(CAConstants.prefix("ray_gun_laser").toString()), CAETPWTemplates.NOOP, ENTITY_TYPES);


    public static ImmutableList<Supplier<EntityType<Entity>>> getEntityTypes() {
        return ImmutableList.copyOf(ENTITY_TYPES);
    }
}
