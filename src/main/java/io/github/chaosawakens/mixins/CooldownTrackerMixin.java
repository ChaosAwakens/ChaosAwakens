package io.github.chaosawakens.mixins;

import io.github.chaosawakens.events.CAEventFactory;
import io.github.chaosawakens.events.common.ItemCooldownEvent.ItemCooldownStartEvent;
import io.github.chaosawakens.events.common.ItemCooldownEvent.ItemCooldownTickEvent;
import net.minecraft.item.Item;
import net.minecraft.util.CooldownTracker;
import net.minecraft.util.CooldownTracker.Cooldown;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Mutable;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.Map;

@Mixin(CooldownTracker.class)
public abstract class CooldownTrackerMixin {
	@Shadow
	@Final
	@Mutable
	private Map<Item, Cooldown> cooldowns;
	@Shadow
	private int tickCount;
	
	private CooldownTrackerMixin() {
		throw new IllegalAccessError("Attempted to instantiate a Mixin Class!");
	}
	
	@Inject(method = "Lnet/minecraft/util/CooldownTracker;onCooldownStarted(Lnet/minecraft/item/Item;I)V", at = @At("HEAD"), cancellable = true)
	private void chaosawakens$onCooldownStarted(Item targetCooldownItem, int cooldownTicks, CallbackInfo cir) {
		CooldownTracker curTracker = (CooldownTracker) (Object) this;
		ItemCooldownStartEvent icdsEvent = CAEventFactory.onItemCooldownStart(targetCooldownItem, cooldownTicks);
		int eventCooldownTicks = icdsEvent.getCooldownTicks();
		
		if (icdsEvent.isCanceled()) {
			if (cooldowns.containsKey(targetCooldownItem)) cooldowns.remove(targetCooldownItem);
			cir.cancel();
		}
		
		if (cooldownTicks != eventCooldownTicks) {
			cooldowns.computeIfPresent(targetCooldownItem, (cooldownItem, itemCooldown) -> {
				int cooldownDelta = eventCooldownTicks > itemCooldown.endTime ? eventCooldownTicks - itemCooldown.endTime : itemCooldown.endTime - eventCooldownTicks;
				Cooldown updatedItemCooldown = curTracker.new Cooldown(tickCount, itemCooldown.endTime + cooldownDelta);
				return updatedItemCooldown;
			});
		}
	}
	
	@Inject(method = "Lnet/minecraft/util/CooldownTracker;tick()V", at = @At("INVOKE"), cancellable = true)
	private void chaosawakens$tick(CallbackInfo cir) {
		CooldownTracker curTracker = (CooldownTracker) (Object) this;
		
		cooldowns.forEach((targetCooldownItem, targetItemCooldown) -> {
			ItemCooldownTickEvent icdtEvent = CAEventFactory.onItemCooldownTick(targetCooldownItem, targetItemCooldown.endTime);
			int eventCooldownTicks = icdtEvent.getCooldownTicks();
			
			if (icdtEvent.isCanceled()) {
				if (cooldowns.containsKey(targetCooldownItem)) cooldowns.remove(targetCooldownItem);
				cir.cancel();
			}
			
			if (targetItemCooldown.endTime != icdtEvent.getCooldownTicks()) {
				cooldowns.computeIfPresent(targetCooldownItem, (cooldownItem, itemCooldown) -> {
					int cooldownDelta = eventCooldownTicks > itemCooldown.endTime ? eventCooldownTicks - itemCooldown.endTime : itemCooldown.endTime - eventCooldownTicks;
					Cooldown updatedItemCooldown = curTracker.new Cooldown(tickCount, itemCooldown.endTime + cooldownDelta);
					return updatedItemCooldown;
				});
			}
		});
	}
	
	@Inject(method = "Lnet/minecraft/util/CooldownTracker;onCooldownEnded(Lnet/minecraft/item/Item;)V", at = @At("HEAD"), cancellable = true)
	private void chaosawakens$onCooldownEnded(Item targetCooldownItem, CallbackInfo cir) {
		CAEventFactory.onItemCooldownEnd(targetCooldownItem);
	}
}