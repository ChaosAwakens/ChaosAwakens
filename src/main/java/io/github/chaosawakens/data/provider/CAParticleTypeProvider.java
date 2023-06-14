package io.github.chaosawakens.data.provider;

import io.github.chaosawakens.ChaosAwakens;
import io.github.chaosawakens.common.registry.CAParticleTypes;
import io.github.chaosawakens.data.builder.provider.ParticleTypeProvider;
import net.minecraft.data.DataGenerator;
import net.minecraft.particles.ParticleType;
import net.minecraftforge.fml.RegistryObject;

public class CAParticleTypeProvider extends ParticleTypeProvider {

	public CAParticleTypeProvider(DataGenerator generator) {
		super(generator);
	}

	@Override
	protected void addParticleTypes() {
		for (RegistryObject<ParticleType<?>> particleTypeReg : CAParticleTypes.PARTICLE_TYPES.getEntries()) {
			ParticleType<?> particleType = particleTypeReg.get();
			String regName = particleType.getRegistryName().toString();
			
			createParticleType(regName.substring(regName.lastIndexOf(":") + 1)).addParticleTypeName(regName.substring(regName.lastIndexOf(":") + 1));
		}
	}
	
	@Override
	public String getName() {
		return ChaosAwakens.MODNAME + ": Particle Types";
	}

}
