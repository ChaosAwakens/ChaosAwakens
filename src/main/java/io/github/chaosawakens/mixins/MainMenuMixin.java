package io.github.chaosawakens.mixins;

import io.github.chaosawakens.ChaosAwakens;
import net.minecraft.client.gui.screen.MainMenuScreen;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.util.text.ITextComponent;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(MainMenuScreen.class)
public class MainMenuMixin extends Screen {
    protected MainMenuMixin(ITextComponent iTextComponent) {
        super(iTextComponent);
    }

    @Inject(at = @At("HEAD"), method = "init()V")
    public void init(CallbackInfo info) {
        ChaosAwakens.LOGGER.info("Hello from ChaosAwakens MainMenuMixin!");
    }
}
