package io.github.chaosawakens.common.registry;

import io.github.chaosawakens.ChaosAwakens;
import io.github.chaosawakens.common.advancements.triggers.AdvancementCompleteTrigger;
import net.minecraft.advancements.CriteriaTriggers;
import net.minecraft.advancements.ICriterionTrigger;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(modid = ChaosAwakens.MODID, bus = Mod.EventBusSubscriber.Bus.MOD)
public class CAAdvancementTriggers {
	
	public static final AdvancementCompleteTrigger ADVANCEMENT_COMPLETE_TRIGGER = register(new AdvancementCompleteTrigger());
	
    private static <I extends ICriterionTrigger<?>> I register(I instance) {
        return CriteriaTriggers.register(instance);
    }
}
