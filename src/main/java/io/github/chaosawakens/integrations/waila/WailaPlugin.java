package io.github.chaosawakens.integrations.waila;

import io.github.chaosawakens.blocks.custom.RedAntInfestedOre;
import io.github.chaosawakens.blocks.custom.TermiteInfestedOre;
import mcp.mobius.waila.api.IRegistrar;
import mcp.mobius.waila.api.IWailaPlugin;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import org.spongepowered.asm.mixin.Mixin;

@Mixin(IWailaPlugin.class)
@Environment(EnvType.CLIENT)
public class WailaPlugin implements IWailaPlugin {
    @Override
    public void register(IRegistrar registrar) {
        registrar.addOverride(TermiteOre.INSTANCE, TermiteInfestedOre.class);
        registrar.addOverride(RedAntOre.INSTANCE, RedAntInfestedOre.class);
    }
}
