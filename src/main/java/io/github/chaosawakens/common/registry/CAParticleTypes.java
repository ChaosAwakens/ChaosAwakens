package io.github.chaosawakens.common.registry;

import io.github.chaosawakens.ChaosAwakens;
import net.minecraft.particles.BasicParticleType;
import net.minecraft.particles.ParticleType;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;

public class CAParticleTypes {
	
	public static final DeferredRegister<ParticleType<?>> PARTICLE_TYPES = DeferredRegister.create(ForgeRegistries.PARTICLE_TYPES, ChaosAwakens.MODID);
	
	
	public static final RegistryObject<BasicParticleType> ROBO_SPARK = PARTICLE_TYPES.register("robo_spark", () -> new BasicParticleType(true));
	public static final RegistryObject<BasicParticleType> FART = PARTICLE_TYPES.register("fart", () -> new BasicParticleType(true));

}
