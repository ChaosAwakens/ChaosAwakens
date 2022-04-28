package io.github.chaosawakens.common.blocks.tileentities;

import io.github.chaosawakens.common.registry.CATileEntities;
import net.minecraft.tileentity.SignTileEntity;
import net.minecraft.tileentity.TileEntityType;

import javax.annotation.Nonnull;

public class CASignTileEntity extends SignTileEntity {
	@Nonnull
	@Override
	public TileEntityType<CASignTileEntity> getType() {
		return CATileEntities.CUSTOM_SIGN.get();
	}
}
