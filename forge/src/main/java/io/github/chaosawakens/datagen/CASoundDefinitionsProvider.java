package io.github.chaosawakens.datagen;

import io.github.chaosawakens.CAConstants;
import io.github.chaosawakens.common.registry.CASoundEvents;
import net.minecraft.data.PackOutput;
import net.minecraftforge.common.data.ExistingFileHelper;
import net.minecraftforge.common.data.SoundDefinition;
import net.minecraftforge.common.data.SoundDefinitionsProvider;

public class CASoundDefinitionsProvider extends SoundDefinitionsProvider {

    public CASoundDefinitionsProvider(PackOutput output, ExistingFileHelper helper) {
        super(output, CAConstants.MODID, helper);
    }

    @Override
    public void registerSounds() {
        add(CASoundEvents.CRAGS, SoundDefinition.definition()
                .with(
                        SoundDefinition.Sound.sound(CAConstants.prefix("soundtracks/mining_paradise/crags"), SoundDefinition.SoundType.SOUND).preload()
                )
                .subtitle("soundtracks.mining_paradise.crags")
                .replace(true));
    }
}
