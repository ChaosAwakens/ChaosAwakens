package io.github.chaosawakens.content.client.container;

import net.minecraft.core.Direction;
import net.minecraft.world.WorldlyContainer;
import net.minecraft.world.level.material.Fluid;

public interface WorldlyFluidContainer extends WorldlyContainer {

    Fluid getFluid(int fluidSlot); // TODO Loader-agnostic "FluidStack"

    Fluid setFluid(int fluidSlot, double initialAmount, Fluid fluid);
    Fluid updateFluid(int fluidSlot, double amountToAdd);

    Fluid removeFluid(int fluidSlot);

    double getFluidAmount(int fluidSlot);

    boolean allowFluidExtraction(int fluidSlot, Direction extractionFace);
    boolean allowFluidInsertion(int fluidSlot, Direction insertionFace);
}
