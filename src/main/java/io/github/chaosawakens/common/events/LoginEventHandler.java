package io.github.chaosawakens.common.events;

import io.github.chaosawakens.ChaosAwakens;
import io.github.chaosawakens.common.UpdateHandler;
import io.github.chaosawakens.common.config.CAConfig;
import net.minecraft.client.resources.I18n;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.text.TranslationTextComponent;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.event.entity.player.PlayerEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber
public class LoginEventHandler {
	
	public static void onPlayerLogin(PlayerEvent.PlayerLoggedInEvent event) {
		PlayerEntity player = event.getPlayer();
		if (UpdateHandler.show && CAConfig.COMMON.showUpdateMessage.get())
			player.sendMessage(new TranslationTextComponent(UpdateHandler.updateStatus), player.getUUID());
		ChaosAwakens.LOGGER.debug(UpdateHandler.updateStatus);
		ChaosAwakens.LOGGER.debug(UpdateHandler.show);
	}
	
	@OnlyIn(Dist.CLIENT)
	public void sendChatMessage(String text, PlayerEntity player) {
		TranslationTextComponent component2 = new TranslationTextComponent(I18n.get(text));
		player.sendMessage(component2, player.getUUID());
	}
}
