package io.github.chaosawakens.common.registry;

import io.github.chaosawakens.ChaosAwakens;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.SoundEvent;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;

public class CASoundEvents {
	
	public static final DeferredRegister<SoundEvent> SOUND_EVENTS = DeferredRegister.create(ForgeRegistries.SOUND_EVENTS, ChaosAwakens.MODID);
	
	public static final RegistryObject<SoundEvent> ROBO_SHOOT = SOUND_EVENTS.register("entity.robo.shoot", () -> new SoundEvent( new ResourceLocation(ChaosAwakens.MODID, "entity.robo.shoot")));
	public static final RegistryObject<SoundEvent> ROBO_HURT = SOUND_EVENTS.register("entity.robo.hurt", () -> new SoundEvent( new ResourceLocation(ChaosAwakens.MODID, "entity.robo.hurt")));
	public static final RegistryObject<SoundEvent> ROBO_DEATH = SOUND_EVENTS.register("entity.robo.death", () -> new SoundEvent( new ResourceLocation(ChaosAwakens.MODID, "entity.robo.death")));
	
	public static final RegistryObject<SoundEvent> EMERALD_GATOR_WALK = SOUND_EVENTS.register("entity.emerald_gator.walk", () -> new SoundEvent( new ResourceLocation(ChaosAwakens.MODID, "entity.emerald_gator.walk")));
	public static final RegistryObject<SoundEvent> EMERALD_GATOR_ATTACK = SOUND_EVENTS.register("entity.emerald_gator.attack", () -> new SoundEvent( new ResourceLocation(ChaosAwakens.MODID, "entity.emerald_gator.attack")));
	public static final RegistryObject<SoundEvent> EMERALD_GATOR_HURT = SOUND_EVENTS.register("entity.emerald_gator.hurt", () -> new SoundEvent( new ResourceLocation(ChaosAwakens.MODID, "entity.emerald_gator.hurt")));
	public static final RegistryObject<SoundEvent> EMERALD_GATOR_DEATH = SOUND_EVENTS.register("entity.emerald_gator.death", () -> new SoundEvent( new ResourceLocation(ChaosAwakens.MODID, "entity.emerald_gator.death")));
	
}
