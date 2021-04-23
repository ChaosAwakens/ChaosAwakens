package io.github.chaosawakens;

/**
 * Enum dedicated to storing tool material data
 * @author invalid2
 *
 */
public enum ToolMaterials
{
	EMERALD("emerald", 2, 2000, 10.0F, 9.0F, 10),
	AMETHYST("amethyst", 8, 5000, 20.0F, 18.0F, 20),
	RUBY("ruby", 12, 8000, 20.0F, 30.0F, 30),
	TIGERS_EYE("tigers_eye", 5, 3500, 15.0F, 10.0F, 15),
	OPTIMISED("optimised", 15, 13000, 75.0F, 8.0F, 45);
	
	private String name;
	private int harvestLevel;
	private int maxUses;
	private float efficiency;
	private float damage;
	private int enchantability;
	
	private ToolMaterials(String name, int harvestLevel, int maxUses, float efficiency, float damage,
			int enchantability) {
		this.name = name;
		this.harvestLevel = harvestLevel;
		this.maxUses = maxUses;
		this.efficiency = efficiency;
		this.damage = damage;
		this.enchantability = enchantability;
	}

	public String getName()
	{
		return name;
	}

	public int getHarvestLevel()
	{
		return harvestLevel;
	}

	public int getMaxUses()
	{
		return maxUses;
	}

	public float getEfficiency()
	{
		return efficiency;
	}

	public float getDamage()
	{
		return damage;
	}

	public int getEnchantability()
	{
		return enchantability;
	}
	
}
