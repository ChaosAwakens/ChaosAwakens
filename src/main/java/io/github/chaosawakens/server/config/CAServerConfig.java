package io.github.chaosawakens.server.config;

import org.apache.commons.lang3.tuple.Pair;

import net.minecraftforge.common.ForgeConfigSpec;
import net.minecraftforge.common.ForgeConfigSpec.ConfigValue;

public class CAServerConfig {
	public static final ForgeConfigSpec SERVER_SPEC;
	public static final Server SERVER;
	
	static {
		final Pair<Server, ForgeConfigSpec> serverSpecPair = new ForgeConfigSpec.Builder().configure(Server::new);
		SERVER_SPEC = serverSpecPair.getRight();
		SERVER = serverSpecPair.getLeft();
	}
	
	public static class Server {
		public final ConfigValue<Boolean> modServerPermission;
		public final ConfigValue<Boolean> debugLogging;
//		public final ConfigValue<RoboPounderTargetingSystemType> roboPounderTargetingSystemType;
		
		Server(ForgeConfigSpec.Builder builder) {
			builder.push("Logging");
			builder.comment("Allow Chaos Awakens to manipulate server settings such as enabling flight.");
			modServerPermission = builder.define("Chaos Awakens has server permissions", true);
			builder.comment("Debug events on the server while it's running.");
			debugLogging = builder.define("Debug logging", true);
			builder.pop();
			
/*			builder.push("AI");
			builder.push("Robo Pounder");
			builder.push("Targeting System");
			roboPounderTargetingSystemType = builder.comment("Define how the Robo Pounder will act according to its targets, where:"
					+ "\n" + "RADIUS_CLOSEST: The Robo Pounder will target the closest entity within its attack radius."
					+ "\n" + "RADIUS_FARTHEST: The Robo Pounder will target the farthest entity within its attack radius."
					+ "\n" + "DYNAMIC_WEAKEST: The Robo Pounder will target the weakest entity (measured by base stats and gear) within its attack radius."
					+ "\n" + "DYNAMIC_STRONGEST: The Robo Pounder will target the strongest entity (measured by base stats and gear) within its attack radius."
					+ "\n" + "FIXED: The Robo Pounder will follow its initial target until the entity is eliminated, no matter the circumstances."
					+ "\n" + "HURT_BY: The Robo Pounder will automatically target another entity if said entity has hurt it in some way."
					+ "\n" + "RANDOMIZED: The Robo Pounder will randomly switch targets whenever it feels like it."
					+ "\n" + "RADIUS_CLOSEST_DYNAMIC_STRONGEST: The Robo Pounder will target the strongest entity that also happens to be the nearest to it."
					+ "\n" + "RADIUS_CLOSEST_DYNAMIC_WEAKEREST: The Robo Pounder will target the weakest entity that also happens to be the nearest to it."
					+ "\n" + "RADIUS_FARTHEST_DYNAMIC_STRONGEST: The Robo Pounder will target the strongest entity that also happens to be the farthest from it."
					+ "\n" + "RADIUS_FARTHEST_DYNAMIC_WEAKEST: The Robo Pounder will target the weakest entity that also happens to be the farthest from it."
					+ "\n" + "RADIUS_CLOSEST_HURT_BY: The Robo Pounder will target the entity closest to it that hurt it (if there are multiple)."
					+ "\n" + "RADIUS_FARTHEST_HURT_BY: The Robo Pounder will target the entity farthest from it that hurt it (if there are multiple).")
					.define("Robo Pounder targeting system", RoboPounderTargetingSystemType.HURT_BY);
			builder.pop();
			builder.pop();
			builder.pop();*/
		}
	}

}
