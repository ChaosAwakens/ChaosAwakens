package io.github.chaosawakens.equipment;

import io.github.chaosawakens.ModItems;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.init.Items;
import net.minecraft.item.ItemAxe;

/**
 * Generic axe class aka any axe with no special behavior
 * @author invalid2
 */
public class GenericAxe extends ItemAxe
{
	
	/**
	 * 
	 * @param name Both unlocalized and registry names
	 * @param material Tool material
	 * @param damage Axe damage
	 * @param speed Axe swing speed
	 */
	public GenericAxe(String name, ToolMaterial material, float damage, float speed)
	{
		super(material, damage, speed);
		setUnlocalizedName(name);
		setRegistryName(name);
		setCreativeTab(CreativeTabs.TOOLS);
		
		ModItems.ITEMS.add(this);
	}
	
	/**
	 * Damage defaults to material base damage + 5 and axe swing speed to -3.0f
	 * @param name Both unlocalized and registry names
	 * @param material Tool material
	 */
	public GenericAxe(String name, ToolMaterial material)
	{
		this(name, material, 5+material.getAttackDamage(), -3.0f);
	}
}
