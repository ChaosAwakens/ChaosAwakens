package io.github.chaosawakens.content.block.vegetation.generic;

import com.mememan.nexus.template.object.block.vegetation.ConfigurablePlant;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.state.properties.IntegerProperty;
import net.minecraft.world.phys.shapes.VoxelShape;
import org.jetbrains.annotations.Nullable;

import java.util.function.Supplier;

public interface CropInstance extends ConfigurablePlant {

    Supplier<Item> getProduce();

    Supplier<Item> getSeed();

    @Nullable
    IntegerProperty getAgeProperty();

    VoxelShape[] getShapeByAge();

    int getMaxAge();
}
