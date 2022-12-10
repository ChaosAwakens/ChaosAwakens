package io.github.chaosawakens.mixins;

import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import io.github.chaosawakens.common.registry.CAAdvancementTriggers;
import net.minecraft.advancements.Advancement;
import net.minecraft.advancements.AdvancementProgress;
import net.minecraft.advancements.PlayerAdvancements;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraftforge.common.util.FakePlayer;

@Mixin(PlayerAdvancements.class)
public abstract class PlayerAdvancementMixin {
	
    @Shadow
    private ServerPlayerEntity player;
    
    @Shadow
    @Final
    private Map<Advancement, AdvancementProgress> advancements;
	
    @Inject(at = @At("RETURN"), method = "Lnet/minecraft/advancements/PlayerAdvancements;award(Lnet/minecraft/advancements/Advancement;Ljava/lang/String;)Z")
    private void award(Advancement adv, String s, CallbackInfoReturnable<Boolean> cir) {
    	if (!(player instanceof FakePlayer)) {
    		List<Advancement> completeAdv = new LinkedList<>();
    		for (Map.Entry<Advancement, AdvancementProgress> advancementProgressEntry : this.advancements.entrySet()) {
                if (advancementProgressEntry.getValue().isDone()) {
                	completeAdv.add(advancementProgressEntry.getKey());
                }
    		}
    		completeAdv.stream().filter(advancmenet -> !advancmenet.getId().toString().contains("recipes")).forEach(this::trigger);
    	}
    }
    
    private void trigger(Advancement advancement) {
        CAAdvancementTriggers.ADVANCEMENT_COMPLETE_TRIGGER.trigger(player, advancement);
    }
    
}
