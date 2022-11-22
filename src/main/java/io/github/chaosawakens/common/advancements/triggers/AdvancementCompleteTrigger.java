package io.github.chaosawakens.common.advancements.triggers;

import com.google.gson.JsonObject;

import io.github.chaosawakens.ChaosAwakens;
import io.github.chaosawakens.common.advancements.predicates.AdvancementLocationPredicate;
import net.minecraft.advancements.Advancement;
import net.minecraft.advancements.criterion.AbstractCriterionTrigger;
import net.minecraft.advancements.criterion.CriterionInstance;
import net.minecraft.advancements.criterion.EntityPredicate;
import net.minecraft.advancements.criterion.EntityPredicate.AndPredicate;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.loot.ConditionArrayParser;
import net.minecraft.loot.ConditionArraySerializer;
import net.minecraft.util.ResourceLocation;

public class AdvancementCompleteTrigger extends AbstractCriterionTrigger<AdvancementCompleteTrigger.Instance>{
	public static final ResourceLocation ID = new ResourceLocation(ChaosAwakens.MODID, "advancement_complete");
	
	@Override
	public ResourceLocation getId() {
		return ID;
	}

	@Override
	protected Instance createInstance(JsonObject obj, AndPredicate pred, ConditionArrayParser parser) {
		AdvancementLocationPredicate advPred = AdvancementLocationPredicate.fromJson(obj);
		return new Instance(advPred);
	}
	
	public void trigger(ServerPlayerEntity player, Advancement adv) {
		super.trigger(player, inst -> inst.matches(adv));
	}
	
	public static final class Instance extends CriterionInstance {
		private final AdvancementLocationPredicate predicate;
		
		public Instance(AdvancementLocationPredicate reqAdv) {
			super(ID, EntityPredicate.AndPredicate.ANY);
			this.predicate = reqAdv;
		}

		@Override
		public JsonObject serializeToJson(ConditionArraySerializer serializer) {
            JsonObject obj = super.serializeToJson(serializer);          	
            obj.add(AdvancementLocationPredicate.ADVANCEMENT_LOCATION, predicate.toJson());
            return obj;
		}
		
        private boolean matches(Advancement req) {
            return predicate.matches(req.getId());
        }
		
		public static Instance of(AdvancementLocationPredicate predicate) {
    		return new Instance(predicate);
        }
        
        public static Instance advancementComplete(ResourceLocation advId) {
        	AdvancementLocationPredicate advCompletePredicate = new AdvancementLocationPredicate(advId);
            return of(advCompletePredicate);
        }
		
	}
}
