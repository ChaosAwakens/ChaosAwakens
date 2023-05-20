package io.github.chaosawakens.config.server;

import net.minecraftforge.common.ForgeConfigSpec;
import net.minecraftforge.common.ForgeConfigSpec.BooleanValue;

public class CAServerConfig {
	
	public final BooleanValue modServerPermission;
	public final BooleanValue debugLogging;
	
	public CAServerConfig(ForgeConfigSpec.Builder builder) {
		builder.push("Logging");
		builder.comment("Allow Chaos Awakens to manipulate server settings such as enabling flight.");
		modServerPermission = builder.define("Chaos Awakens has server permissions", true);
		builder.comment("Debug events on the server while it's running.");
		debugLogging = builder.define("Debug logging", true);
		builder.pop();
	}

}
