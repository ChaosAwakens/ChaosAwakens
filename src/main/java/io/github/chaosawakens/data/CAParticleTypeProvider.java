package io.github.chaosawakens.data;

import io.github.chaosawakens.ChaosAwakens;
import net.minecraft.data.DataGenerator;

public class CAParticleTypeProvider extends ParticleTypeProvider {

	public CAParticleTypeProvider(DataGenerator generator) {
		super(generator);
	}

	@Override
	protected void addParticleTypes() {
		createParticleType("robo_spark")
		      .addParticleTypeName("robo_spark");		
		createParticleType("fart")
		      .addParticleTypeName("fart");
	}
	
	@Override
	public String getName() {
		return ChaosAwakens.MODNAME + ": Particle Types";
	}

}
