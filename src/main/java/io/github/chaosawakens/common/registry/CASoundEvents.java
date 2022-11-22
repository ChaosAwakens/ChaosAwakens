package io.github.chaosawakens.common.registry;

import io.github.chaosawakens.ChaosAwakens;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.SoundEvent;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;

public class CASoundEvents {
	public static final DeferredRegister<SoundEvent> SOUND_EVENTS = DeferredRegister.create(ForgeRegistries.SOUND_EVENTS, ChaosAwakens.MODID);

	public static final RegistryObject<SoundEvent> ROBO_SHOOT = SOUND_EVENTS.register("entity.robo.shoot", () -> new SoundEvent(new ResourceLocation(ChaosAwakens.MODID, "entity.robo.shoot")));
	public static final RegistryObject<SoundEvent> ROBO_HURT = SOUND_EVENTS.register("entity.robo.hurt", () -> new SoundEvent(new ResourceLocation(ChaosAwakens.MODID, "entity.robo.hurt")));
	public static final RegistryObject<SoundEvent> ROBO_DEATH = SOUND_EVENTS.register("entity.robo.death", () -> new SoundEvent(new ResourceLocation(ChaosAwakens.MODID, "entity.robo.death")));

	public static final RegistryObject<SoundEvent> EMERALD_GATOR_WALK = SOUND_EVENTS.register("entity.emerald_gator.walk", () -> new SoundEvent(new ResourceLocation(ChaosAwakens.MODID, "entity.emerald_gator.walk")));
	public static final RegistryObject<SoundEvent> EMERALD_GATOR_ATTACK = SOUND_EVENTS.register("entity.emerald_gator.attack", () -> new SoundEvent(new ResourceLocation(ChaosAwakens.MODID, "entity.emerald_gator.attack")));
	public static final RegistryObject<SoundEvent> EMERALD_GATOR_HURT = SOUND_EVENTS.register("entity.emerald_gator.hurt", () -> new SoundEvent(new ResourceLocation(ChaosAwakens.MODID, "entity.emerald_gator.hurt")));
	public static final RegistryObject<SoundEvent> EMERALD_GATOR_DEATH = SOUND_EVENTS.register("entity.emerald_gator.death", () -> new SoundEvent(new ResourceLocation(ChaosAwakens.MODID, "entity.emerald_gator.death")));

	public static final RegistryObject<SoundEvent> HERCULES_BEETLE_WALK = SOUND_EVENTS.register("entity.hercules_beetle.walk", () -> new SoundEvent(new ResourceLocation(ChaosAwakens.MODID, "entity.hercules_beetle.walk")));
	public static final RegistryObject<SoundEvent> HERCULES_BEETLE_ATTACK = SOUND_EVENTS.register("entity.hercules_beetle.attack", () -> new SoundEvent(new ResourceLocation(ChaosAwakens.MODID, "entity.hercules_beetle.attack")));
	public static final RegistryObject<SoundEvent> HERCULES_BEETLE_HURT = SOUND_EVENTS.register("entity.hercules_beetle.hurt", () -> new SoundEvent(new ResourceLocation(ChaosAwakens.MODID, "entity.hercules_beetle.hurt")));
	public static final RegistryObject<SoundEvent> HERCULES_BEETLE_DEATH = SOUND_EVENTS.register("entity.hercules_beetle.death", () -> new SoundEvent(new ResourceLocation(ChaosAwakens.MODID, "entity.hercules_beetle.death")));
	public static final RegistryObject<SoundEvent> HERCULES_BEETLE_SWING = SOUND_EVENTS.register("entity.hercules_beetle.swing", () -> new SoundEvent(new ResourceLocation(ChaosAwakens.MODID, "entity.hercules_beetle.swing")));
	public static final RegistryObject<SoundEvent> HERCULES_BEETLE_FLAP = SOUND_EVENTS.register("entity.hercules_beetle.flap", () -> new SoundEvent(new ResourceLocation(ChaosAwakens.MODID, "entity.hercules_beetle.flap")));
	
	public static final RegistryObject<SoundEvent> ACACIA_ENT_WALK = SOUND_EVENTS.register("entity.acacia_ent.walk", () -> new SoundEvent(new ResourceLocation(ChaosAwakens.MODID, "entity.ent.walk")));
	public static final RegistryObject<SoundEvent> BIRCH_ENT_WALK = SOUND_EVENTS.register("entity.birch_ent.walk", () -> new SoundEvent(new ResourceLocation(ChaosAwakens.MODID, "entity.ent.walk")));
	public static final RegistryObject<SoundEvent> CRIMSON_ENT_WALK = SOUND_EVENTS.register("entity.crimson_ent.walk", () -> new SoundEvent(new ResourceLocation(ChaosAwakens.MODID, "entity.ent.walk")));
	public static final RegistryObject<SoundEvent> DARK_OAK_ENT_WALK = SOUND_EVENTS.register("entity.dark_oak_ent.walk", () -> new SoundEvent(new ResourceLocation(ChaosAwakens.MODID, "entity.ent.walk")));
	public static final RegistryObject<SoundEvent> JUNGLE_ENT_WALK = SOUND_EVENTS.register("entity.jungle_ent.walk", () -> new SoundEvent(new ResourceLocation(ChaosAwakens.MODID, "entity.ent.walk")));
	public static final RegistryObject<SoundEvent> OAK_ENT_WALK = SOUND_EVENTS.register("entity.oak_ent.walk", () -> new SoundEvent(new ResourceLocation(ChaosAwakens.MODID, "entity.ent.walk")));
	public static final RegistryObject<SoundEvent> SPRUCE_ENT_WALK = SOUND_EVENTS.register("entity.spruce_ent.walk", () -> new SoundEvent(new ResourceLocation(ChaosAwakens.MODID, "entity.ent.walk")));
	public static final RegistryObject<SoundEvent> WARPED_ENT_WALK = SOUND_EVENTS.register("entity.warped_ent.walk", () -> new SoundEvent(new ResourceLocation(ChaosAwakens.MODID, "entity.ent.walk")));
	public static final RegistryObject<SoundEvent> ACACIA_ENT_ATTACK = SOUND_EVENTS.register("entity.acacia_ent.attack", () -> new SoundEvent(new ResourceLocation(ChaosAwakens.MODID, "entity.ent.attack")));
	public static final RegistryObject<SoundEvent> BIRCH_ENT_ATTACK = SOUND_EVENTS.register("entity.birch_ent.attack", () -> new SoundEvent(new ResourceLocation(ChaosAwakens.MODID, "entity.ent.attack")));
	public static final RegistryObject<SoundEvent> CRIMSON_ENT_ATTACK = SOUND_EVENTS.register("entity.crimson_ent.attack", () -> new SoundEvent(new ResourceLocation(ChaosAwakens.MODID, "entity.ent.attack")));
	public static final RegistryObject<SoundEvent> DARK_OAK_ENT_ATTACK = SOUND_EVENTS.register("entity.dark_oak_ent.attack", () -> new SoundEvent(new ResourceLocation(ChaosAwakens.MODID, "entity.ent.attack")));
	public static final RegistryObject<SoundEvent> JUNGLE_ENT_ATTACK = SOUND_EVENTS.register("entity.jungle_ent.attack", () -> new SoundEvent(new ResourceLocation(ChaosAwakens.MODID, "entity.ent.attack")));
	public static final RegistryObject<SoundEvent> OAK_ENT_ATTACK = SOUND_EVENTS.register("entity.oak_ent.attack", () -> new SoundEvent(new ResourceLocation(ChaosAwakens.MODID, "entity.ent.attack")));
	public static final RegistryObject<SoundEvent> SPRUCE_ENT_ATTACK = SOUND_EVENTS.register("entity.spruce_ent.attack", () -> new SoundEvent(new ResourceLocation(ChaosAwakens.MODID, "entity.ent.attack")));
	public static final RegistryObject<SoundEvent> WARPED_ENT_ATTACK = SOUND_EVENTS.register("entity.warped_ent.attack", () -> new SoundEvent(new ResourceLocation(ChaosAwakens.MODID, "entity.ent.attack")));
	public static final RegistryObject<SoundEvent> ACACIA_ENT_HURT = SOUND_EVENTS.register("entity.acacia_ent.hurt", () -> new SoundEvent(new ResourceLocation(ChaosAwakens.MODID, "entity.ent.hurt")));
	public static final RegistryObject<SoundEvent> BIRCH_ENT_HURT = SOUND_EVENTS.register("entity.birch_ent.hurt", () -> new SoundEvent(new ResourceLocation(ChaosAwakens.MODID, "entity.ent.hurt")));
	public static final RegistryObject<SoundEvent> CRIMSON_ENT_HURT = SOUND_EVENTS.register("entity.crimson_ent.hurt", () -> new SoundEvent(new ResourceLocation(ChaosAwakens.MODID, "entity.ent.hurt")));
	public static final RegistryObject<SoundEvent> DARK_OAK_ENT_HURT = SOUND_EVENTS.register("entity.dark_oak_ent.hurt", () -> new SoundEvent(new ResourceLocation(ChaosAwakens.MODID, "entity.ent.hurt")));
	public static final RegistryObject<SoundEvent> JUNGLE_ENT_HURT = SOUND_EVENTS.register("entity.jungle_ent.hurt", () -> new SoundEvent(new ResourceLocation(ChaosAwakens.MODID, "entity.ent.hurt")));
	public static final RegistryObject<SoundEvent> OAK_ENT_HURT = SOUND_EVENTS.register("entity.oak_ent.hurt", () -> new SoundEvent(new ResourceLocation(ChaosAwakens.MODID, "entity.ent.hurt")));
	public static final RegistryObject<SoundEvent> SPRUCE_ENT_HURT = SOUND_EVENTS.register("entity.spruce_ent.hurt", () -> new SoundEvent(new ResourceLocation(ChaosAwakens.MODID, "entity.ent.hurt")));
	public static final RegistryObject<SoundEvent> WARPED_ENT_HURT = SOUND_EVENTS.register("entity.warped_ent.hurt", () -> new SoundEvent(new ResourceLocation(ChaosAwakens.MODID, "entity.ent.hurt")));
	public static final RegistryObject<SoundEvent> ACACIA_ENT_DEATH = SOUND_EVENTS.register("entity.acacia_ent.death", () -> new SoundEvent(new ResourceLocation(ChaosAwakens.MODID, "entity.ent.death")));
	public static final RegistryObject<SoundEvent> BIRCH_ENT_DEATH = SOUND_EVENTS.register("entity.birch_ent.death", () -> new SoundEvent(new ResourceLocation(ChaosAwakens.MODID, "entity.ent.death")));
	public static final RegistryObject<SoundEvent> CRIMSON_ENT_DEATH = SOUND_EVENTS.register("entity.crimson_ent.death", () -> new SoundEvent(new ResourceLocation(ChaosAwakens.MODID, "entity.ent.death")));
	public static final RegistryObject<SoundEvent> DARK_OAK_ENT_DEATH = SOUND_EVENTS.register("entity.dark_oak_ent.death", () -> new SoundEvent(new ResourceLocation(ChaosAwakens.MODID, "entity.ent.death")));
	public static final RegistryObject<SoundEvent> JUNGLE_ENT_DEATH = SOUND_EVENTS.register("entity.jungle_ent.death", () -> new SoundEvent(new ResourceLocation(ChaosAwakens.MODID, "entity.ent.death")));
	public static final RegistryObject<SoundEvent> OAK_ENT_DEATH = SOUND_EVENTS.register("entity.oak_ent.death", () -> new SoundEvent(new ResourceLocation(ChaosAwakens.MODID, "entity.ent.death")));
	public static final RegistryObject<SoundEvent> SPRUCE_ENT_DEATH = SOUND_EVENTS.register("entity.spruce_ent.death", () -> new SoundEvent(new ResourceLocation(ChaosAwakens.MODID, "entity.ent.death")));
	public static final RegistryObject<SoundEvent> WARPED_ENT_DEATH = SOUND_EVENTS.register("entity.warped_ent.death", () -> new SoundEvent(new ResourceLocation(ChaosAwakens.MODID, "entity.ent.death")));
	public static final RegistryObject<SoundEvent> ENT_DEATH = SOUND_EVENTS.register("entity.ent.death", () -> new SoundEvent(new ResourceLocation(ChaosAwakens.MODID, "entity.ent.death")));
	public static final RegistryObject<SoundEvent> ENT_WALK = SOUND_EVENTS.register("entity.ent.walk", () -> new SoundEvent(new ResourceLocation(ChaosAwakens.MODID, "entity.ent.walk")));
	public static final RegistryObject<SoundEvent> ENT_ATTACK = SOUND_EVENTS.register("entity.ent.attack", () -> new SoundEvent(new ResourceLocation(ChaosAwakens.MODID, "entity.ent.attack")));
	public static final RegistryObject<SoundEvent> ENT_HURT = SOUND_EVENTS.register("entity.ent.hurt", () -> new SoundEvent(new ResourceLocation(ChaosAwakens.MODID, "entity.ent.hurt")));
	
	public static final RegistryObject<SoundEvent> WHALE_AMBIENT = SOUND_EVENTS.register("entity.whale.ambient", () -> new SoundEvent(new ResourceLocation(ChaosAwakens.MODID, "entity.whale.ambient")));
	public static final RegistryObject<SoundEvent> WHALE_HURT = SOUND_EVENTS.register("entity.whale.hurt", () -> new SoundEvent(new ResourceLocation(ChaosAwakens.MODID, "entity.whale.hurt")));
	public static final RegistryObject<SoundEvent> WHALE_DEATH = SOUND_EVENTS.register("entity.whale.death", () -> new SoundEvent(new ResourceLocation(ChaosAwakens.MODID, "entity.whale.death")));

	public static final RegistryObject<SoundEvent> WASP_AMBIENT = SOUND_EVENTS.register("entity.wasp.ambient", () -> new SoundEvent(new ResourceLocation(ChaosAwakens.MODID, "entity.wasp.ambient")));
}
