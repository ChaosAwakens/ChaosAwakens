package io.github.chaosawakens.data.builder;

import java.util.ArrayList;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;

import io.github.chaosawakens.ChaosAwakens;

public class ParticleTypeBuilder {
	private ArrayList<String> particleTypeNameArray;
	@SuppressWarnings("unused")
	private String fileName;
	
	public ParticleTypeBuilder(String particleTypeName) {
		this.particleTypeNameArray = new ArrayList<String>();
		this.fileName = particleTypeName;
	}	
	
	public ParticleTypeBuilder addParticleTypeName(String particleTypeName) {
		particleTypeNameArray.add(ChaosAwakens.MODID + ":" + particleTypeName);
		return this;
	}
	
	public JsonObject serialize() {
		JsonObject obj = new JsonObject();		
		JsonArray textureSpriteArray = new JsonArray();
		
		for (int length = 0; length < particleTypeNameArray.size(); length++) {
			String particleTypeNameInArray = particleTypeNameArray.get(length);
			textureSpriteArray.add(particleTypeNameInArray);
		}
		
		obj.add("textures", textureSpriteArray);
		
		return obj;
	}

}
