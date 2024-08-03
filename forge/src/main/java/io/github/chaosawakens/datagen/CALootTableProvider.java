package io.github.chaosawakens.datagen;

import it.unimi.dsi.fastutil.objects.ObjectArrayList;
import net.minecraft.data.PackOutput;
import net.minecraft.data.loot.LootTableProvider;
import net.minecraft.world.level.storage.loot.parameters.LootContextParamSets;

import java.util.Set;

public class CALootTableProvider extends LootTableProvider {

    public CALootTableProvider(PackOutput pOutput) {
        super(pOutput, Set.of(), ObjectArrayList.of(
                new SubProviderEntry(CABlockLootTableProvider::new, LootContextParamSets.BLOCK)
        ));
    }
}
