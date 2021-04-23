package io.github.chaosawakens.dunnowheretoputthis;

import net.minecraft.util.IStringSerializable;

public class EnumHandler0 {

	public static enum EnumType implements IStringSerializable
	{
	
		RUBY(0, "ruby"),
		TIGEREYE(1, "tigereye"),
				AMATHYEST(2, "amathyest"),
				UNPROCESSEDOIL(3, "unprocessedoil"),
				TITANIUM(4, "titanium"),
URANIUM(5, "uranium"),
SALT(6, "salt"),
ALUMINIUM(7, "aluminium"
				);
		
		public static final EnumType[] META_LOOKUP = new EnumType[values().length];
		private final int meta;
		private final String name, unlocalizedName;
		
		private EnumType(int meta, String name)
		{
			this(meta, name, name);
			
		}
		private EnumType (int meta, String name, String unlocalizedName)
		{
			this.meta = meta;
			this.name = name;
			this.unlocalizedName = unlocalizedName;
		}
		
		@Override
		public String getName()
		{
			return this.name;
		}
		public int getMeta()
		{
			return this.meta;
			
		}
		
		public String getUnlocalizedName()
		{
			
			return this.unlocalizedName;
			
		}
	
	@Override
	public String toString()
	{
		return this.name;
		
	}
	public static EnumType byMetadata(int meta)
	{
		return META_LOOKUP[meta];
		
	}
	static
	{
		for(EnumType enumtype : values())
		{
			
			META_LOOKUP[enumtype.getMeta()] = enumtype;
			
		}
		
		
	}
	
	}
	
	
	
	
	
	
	
	}
	
	
	

