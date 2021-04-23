package io.github.chaosawakens;

import net.minecraft.init.SoundEvents;
import net.minecraft.util.SoundEvent;

/**
 * Enum dedicated to storing armor material data
 * @author invalid2
 *
 */
public enum ArmorMaterials
{
	EMERALD("emerald", "emerald", 25, new int[] {5, 10, 10, 5}, 20, SoundEvents.ITEM_ARMOR_EQUIP_DIAMOND , 20.0F),
	AMETHYST("amethyst", "amethyst", 25, new int[] {10, 20, 20, 10}, 50, SoundEvents.ITEM_ARMOR_EQUIP_DIAMOND , 20.0F),
	RUBY("ruby", "ruby", 50, new int[] {15, 25, 25, 15}, 60, SoundEvents.ITEM_ARMOR_EQUIP_DIAMOND , 40.0F),
	TIGERS_EYE("ruby", "ruby", 35, new int[] {10, 15, 15, 10}, 30, SoundEvents.ITEM_ARMOR_EQUIP_DIAMOND , 25.0F);
	
	private String name;
	private String textureName;
	private int durability;
	private int[] reductionAmounts;
	private int enchantability;
	private SoundEvent soundOnEquip;
	private float thoughness;
	
	/**
	 * 
	 * @param name Name
	 * @param textureName Texture name
	 * @param durability Durability
	 * @param reductionAmounts Defense value
	 * @param enchantability Enchantability
	 * @param soundOnEquip Sound made when armor is equipped
	 * @param thoughness Thoughness
	 * 
	 * @apiNote Arg javadoc names are a stub
	 */
	ArmorMaterials(String name, String textureName, int durability, int[] reductionAmounts, int enchantability, SoundEvent soundOnEquip, float thoughness)
	{
		this.name = name;
		this.textureName = String.format("%s:%s", Reference.MODID, textureName);
		this.durability = durability;
		this.reductionAmounts = reductionAmounts;
		this.enchantability = enchantability;
		this.soundOnEquip = soundOnEquip;
		this.thoughness = thoughness;
	}

	public String getName()
	{
		return name;
	}

	public String getTextureName()
	{
		return textureName;
	}

	public int getDurability()
	{
		return durability;
	}

	public int[] getReductionAmounts()
	{
		return reductionAmounts;
	}

	public int getEnchantability()
	{
		return enchantability;
	}

	public SoundEvent getSoundOnEquip()
	{
		return soundOnEquip;
	}

	public float getThoughness()
	{
		return thoughness;
	}
	
}
