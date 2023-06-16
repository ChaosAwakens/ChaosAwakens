package io.github.chaosawakens.manager;

import org.apache.commons.lang3.tuple.Pair;

import io.github.chaosawakens.ChaosAwakens;
import io.github.chaosawakens.config.client.CAClientConfig;
import io.github.chaosawakens.config.common.CACommonConfig;
import io.github.chaosawakens.config.server.CAServerConfig;
import net.minecraftforge.common.ForgeConfigSpec;
import net.minecraftforge.fml.ModLoadingContext;
import net.minecraftforge.fml.config.ModConfig;
import net.minecraftforge.fml.loading.FMLPaths;
import net.minecraftforge.fml.loading.FileUtils;

public class CAConfigManager {
	public static final ForgeConfigSpec MAIN_CLIENT_SPEC;
	public static final CAClientConfig MAIN_CLIENT;
	
	public static final ForgeConfigSpec MAIN_COMMON_SPEC;
	public static final CACommonConfig MAIN_COMMON;
	
	public static final ForgeConfigSpec MAIN_SERVER_SPEC;
	public static final CAServerConfig MAIN_SERVER;
	
	static {
		final Pair<CAClientConfig, ForgeConfigSpec> clientSpecPair = new ForgeConfigSpec.Builder().configure(CAClientConfig::new);
		final Pair<CACommonConfig, ForgeConfigSpec> commonSpecPair = new ForgeConfigSpec.Builder().configure(CACommonConfig::new);
		final Pair<CAServerConfig, ForgeConfigSpec> serverSpecPair = new ForgeConfigSpec.Builder().configure(CAServerConfig::new);
		
		MAIN_CLIENT_SPEC = clientSpecPair.getRight();
		MAIN_CLIENT = clientSpecPair.getLeft();
		
		MAIN_COMMON_SPEC = commonSpecPair.getRight();
		MAIN_COMMON = commonSpecPair.getLeft();
		
		MAIN_SERVER_SPEC = serverSpecPair.getRight();
		MAIN_SERVER = serverSpecPair.getLeft();
	}
	
	protected static void registerConfigs() {
		registerConfigFolder();
		registerClientConfig();
		registerCommonConfig();
		registerServerConfig();
	}
	
	private static void registerClientConfig() {
		ModLoadingContext.get().registerConfig(ModConfig.Type.CLIENT, MAIN_CLIENT_SPEC, ChaosAwakens.MODID + "/chaosawakens-client.toml");
	}
	
	private static void registerCommonConfig() {
		ModLoadingContext.get().registerConfig(ModConfig.Type.COMMON, MAIN_COMMON_SPEC, ChaosAwakens.MODID + "/chaosawakens-common.toml");
	}
	
	private static void registerServerConfig() {
		ModLoadingContext.get().registerConfig(ModConfig.Type.SERVER, MAIN_SERVER_SPEC);
	}
	
	private static void registerConfigFolder() {
		FileUtils.getOrCreateDirectory(FMLPaths.CONFIGDIR.get().resolve(ChaosAwakens.MODID), ChaosAwakens.MODID);
	}
}
