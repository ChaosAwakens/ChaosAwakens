package io.github.chaosawakens.mixins;

import mcjty.theoneprobe.api.IEntityStyle;
import mcjty.theoneprobe.apiimpl.elements.ElementEntity;
import net.minecraft.nbt.CompoundNBT;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;

@Mixin(value = ElementEntity.class, remap = false)
public interface IElementEntityAccessor {

    @Accessor("entityName")
    String getEntityName();

    @Accessor("entityNBT")
    CompoundNBT getEntityNBT();

    @Accessor("style")
    IEntityStyle getStyle();
}
