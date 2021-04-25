package io.github.chaosawakens;

import java.util.ArrayList;
import java.util.List;

import io.github.chaosawakens.blocks.GenericBlock;
import io.github.chaosawakens.dunnowheretoputthis.RubyOre;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.item.Item;

/**
 * Class for block creation, such that the main class is not unreadable
 * @author invalid2
 */
public class Blocks {
	
	/**
	 * List holding ALL mod blocks
	 */
	public static final List<Block> BLOCKS = new ArrayList<Block>();

	
	
	public static final Block RubyBlock = new GenericBlock("RubyBlock", Material.IRON);
	public static final Block ore_overworld = new RubyOre("ore_overworld", "overworld");
	public static final Block TigerEyeBlock = new GenericBlock("TigerEyeBlock", Material.IRON);
	public static final Block TitaniumBlock = new GenericBlock("TitaniumBlock", Material.IRON);
	public static final Block UraniumBlock = new GenericBlock("UraniumBlock", Material.IRON);
	public static final Block AluminiumBlock = new GenericBlock("AluminiumBlock", Material.IRON);

	
	
	
	
	
	public Blocks() {
		// TODO Auto-generated constructor stub
	}

}
