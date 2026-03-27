package io.github.chaosawakens.content.block.vegetation.crystalworld;

import com.mememan.nexus.template.object.block.vegetation.DefaultableTallGrassBlock;
import io.github.chaosawakens.content.registry.CATags;
import it.unimi.dsi.fastutil.objects.ObjectOpenHashSet;
import net.minecraft.world.level.block.DoublePlantBlock;

import java.util.function.Supplier;

public class CrystalTallGrassBlock extends DefaultableTallGrassBlock {

    public CrystalTallGrassBlock(Properties properties, Supplier<DoublePlantBlock> tallPlantBlock) {
        super(properties, ObjectOpenHashSet.of(CATags.CABlockTags.CRYSTAL_SOIL), tallPlantBlock);
    }

    public CrystalTallGrassBlock(Properties properties) {
        super(properties, ObjectOpenHashSet.of(CATags.CABlockTags.CRYSTAL_SOIL));
    }
}
