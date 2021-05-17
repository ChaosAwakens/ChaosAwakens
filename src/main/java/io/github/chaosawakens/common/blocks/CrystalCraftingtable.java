package io.github.chaosawakens.common.blocks;

import java.util.List;

import net.minecraft.block.Block;
import net.minecraft.util.IReorderingProcessor;
import net.minecraft.util.text.IFormattableTextComponent;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.Style;

/**
 * This class nothing yet, thus {@link @Deprecated}
 * @author invalid2
 *
 */
@Deprecated
public class CrystalCraftingtable extends Block {
	
	private static final ITextComponent CONTAINER_NAME = new ITextComponent() {
		
		@Override
		public String getUnformattedComponentText() {
			return "Crystal Crafting table";
		}
		
		@Override
		public Style getStyle() {
			return Style.EMPTY;
		}
		
		@Override
		public List<ITextComponent> getSiblings() {
			return null;
		}
		
		@Override
		public IReorderingProcessor func_241878_f() {
			return null;
		}
		
		@Override
		public IFormattableTextComponent deepCopy() {
			return null;
		}
		
		@Override
		public IFormattableTextComponent copyRaw() {
			return null;
		}
	};
	
	public CrystalCraftingtable(Properties properties) {
		super(properties);
	}
	
}
