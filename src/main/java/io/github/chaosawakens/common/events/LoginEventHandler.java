package io.github.chaosawakens.common.events;

import io.github.chaosawakens.ChaosAwakens;
import io.github.chaosawakens.common.UpdateHandler;
import io.github.chaosawakens.common.config.CAConfig;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.text.TranslationTextComponent;
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
}
