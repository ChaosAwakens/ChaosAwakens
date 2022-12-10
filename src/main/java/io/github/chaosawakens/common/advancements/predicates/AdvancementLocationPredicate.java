package io.github.chaosawakens.common.advancements.predicates;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;

import net.minecraft.util.JSONUtils;
import net.minecraft.util.ResourceLocation;

public class AdvancementLocationPredicate {
	private final ResourceLocation loc;
	public static final String ADV_ID = "advancement_id";
	public static final String ADVANCEMENT_LOCATION = "advancement_location";
	
	public AdvancementLocationPredicate(ResourceLocation advLoc) {
		this.loc = advLoc;
	}
	
	public static AdvancementLocationPredicate fromJson(JsonElement e) {
		if (!e.isJsonNull()) {
			JsonObject advLocObj = JSONUtils.convertToJsonObject(e, ADVANCEMENT_LOCATION);
			JsonObject advLocObj1 = (JsonObject) advLocObj.get(ADVANCEMENT_LOCATION);
			
			ResourceLocation newAdvLoc;
			
			if (advLocObj1.has(ADV_ID)) {
				newAdvLoc = new ResourceLocation(JSONUtils.getAsString(advLocObj1, ADV_ID));
			} else {
				throw new NullPointerException("Couldn't find advancement location: " + ADVANCEMENT_LOCATION);
			}
			
			return new AdvancementLocationPredicate(newAdvLoc);
		}
		
		throw new NullPointerException("Null advancement: " + ADV_ID);
	}
	
	public JsonElement toJson() {
		JsonObject obj = new JsonObject();
		obj.addProperty(ADV_ID, loc.toString());
		return obj;
	}
	
	public static AdvancementLocationPredicate of(ResourceLocation advLoc) {
		return new AdvancementLocationPredicate(advLoc);
	}
	
	public boolean matches(ResourceLocation loc) {
		final boolean match = loc.toString().equals(this.loc.toString());
		return match;
	}
}
