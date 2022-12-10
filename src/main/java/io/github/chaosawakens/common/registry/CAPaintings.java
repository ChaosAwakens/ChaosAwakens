package io.github.chaosawakens.common.registry;

import io.github.chaosawakens.ChaosAwakens;
import net.minecraft.entity.item.PaintingType;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;

public class CAPaintings {
	public static final DeferredRegister<PaintingType> PAINTINGS = DeferredRegister.create(ForgeRegistries.PAINTING_TYPES, ChaosAwakens.MODID);
	
	public static final RegistryObject<PaintingType> MAN_MEME = PAINTINGS.register("man_meme", () -> new PaintingType(64, 64));
	public static final RegistryObject<PaintingType> APPLE_COW_FIELD = PAINTINGS.register("apple_cow_field", () -> new PaintingType(64, 32));
	public static final RegistryObject<PaintingType> OLD_CHAOS_AWAKENS_LOGO = PAINTINGS.register("old_chaos_awakens_logo", () -> new PaintingType(32, 32));
	public static final RegistryObject<PaintingType> CHAOS_AWAKENS_LOGO = PAINTINGS.register("chaos_awakens_logo", () -> new PaintingType(64, 64));
	public static final RegistryObject<PaintingType> PLAYERS = PAINTINGS.register("players", () -> new PaintingType(64, 32));
	public static final RegistryObject<PaintingType> CAMPFIRE = PAINTINGS.register("campfire", () -> new PaintingType(64, 32));
	public static final RegistryObject<PaintingType> PURPLE_BULB = PAINTINGS.register("purple_bulb", () -> new PaintingType(16, 16));
	public static final RegistryObject<PaintingType> RED = PAINTINGS.register("red", () -> new PaintingType(16, 16));
	public static final RegistryObject<PaintingType> CHAOS_AWAKENS_BANNER = PAINTINGS.register("chaos_awakens_banner", () -> new PaintingType(64, 32));
	public static final RegistryObject<PaintingType> SUNSET = PAINTINGS.register("sunset", () -> new PaintingType(64, 32));
	public static final RegistryObject<PaintingType> UNDERWATER = PAINTINGS.register("underwater", () -> new PaintingType(64, 32));
	public static final RegistryObject<PaintingType> VILLAGE = PAINTINGS.register("village", () -> new PaintingType(64, 32));
	public static final RegistryObject<PaintingType> VILLAGE_NIGHT = PAINTINGS.register("village_night", () -> new PaintingType(64, 32));
	public static final RegistryObject<PaintingType> DIMETRODON_LAKE = PAINTINGS.register("dimetrodon_lake", () -> new PaintingType(64, 32));
	public static final RegistryObject<PaintingType> PAEONIA_PLAINS = PAINTINGS.register("paeonia_plains", () -> new PaintingType(64, 32));
	public static final RegistryObject<PaintingType> CYAN = PAINTINGS.register("cyan", () -> new PaintingType(16, 16));
	public static final RegistryObject<PaintingType> CARROT_PIG_PLAINS = PAINTINGS.register("carrot_pig_plains", () -> new PaintingType(64, 32));
	public static final RegistryObject<PaintingType> NIGHTMARE_SAVANNAH = PAINTINGS.register("nighttime_savannah", () -> new PaintingType(64, 32));
	
}
