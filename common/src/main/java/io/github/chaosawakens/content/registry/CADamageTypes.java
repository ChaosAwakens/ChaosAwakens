package io.github.chaosawakens.content.registry;

import com.mememan.nexus.asm.annotations.RegistrarEntry;
import com.mememan.nexus.template.property_wrapper.DamageTypePropertyWrapperTemplates;
import io.github.chaosawakens.CAConstants;
import it.unimi.dsi.fastutil.objects.ObjectArrayList;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.damagesource.DamageType;
import net.minecraft.world.level.Level;

import java.util.function.Supplier;

@RegistrarEntry
public class CADamageTypes {
    protected static final ObjectArrayList<Supplier<ResourceKey<DamageType>>> DAMAGE_TYPES = new ObjectArrayList<>();

    public static final Supplier<ResourceKey<DamageType>> THORNY_SUN = DamageTypePropertyWrapperTemplates.registerAndChain(CAConstants.prefix("thorny_sun"), () -> new DamageType("thorny_sun", 0.1F), DAMAGE_TYPES)
            .withAdditionalLocalizationKey("death.attack.thorny_sun", "%1$s was pricked to death by a Thorny Sun")
            .buildAndGet();
    public static final Supplier<ResourceKey<DamageType>> BIG_CARNIVOROUS_PLANT = DamageTypePropertyWrapperTemplates.registerAndChain(CAConstants.prefix("big_carnivorous_plant"), () -> new DamageType("big_carnivorous_plant", 0.1F), DAMAGE_TYPES)
            .withAdditionalLocalizationKey("death.attack.big_carnivorous_plant", "%1$s was bitten to death by a Big Carnivorous Plant")
            .buildAndGet();

    public static DamageSource indirectSource(Level targetLevel, Supplier<ResourceKey<DamageType>> damageTypeKey) {
        return new DamageSource(targetLevel.registryAccess().lookupOrThrow(Registries.DAMAGE_TYPE).getOrThrow(damageTypeKey.get()), null, null);
    }
}
