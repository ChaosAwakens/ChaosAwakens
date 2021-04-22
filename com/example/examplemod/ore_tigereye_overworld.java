package com.example.examplemod;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.block.properties.IProperty;
import net.minecraft.block.properties.PropertyEnum;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.NonNullList;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.world.World;

public class ore_tigereye_overworld extends Block implements IHadModels, IMetaName{

	
	
	public static final PropertyEnum<EnumHandler0.EnumType> VARIANT = PropertyEnum.<EnumHandler0.EnumType>create("varient", EnumHandler0.EnumType.class);
	private String name, dimension;
	public ore_tigereye_overworld(String name, String dimension) {
		super(Material.IRON);
		// TODO Auto-generated constructor stub
	setUnlocalizedName(name);
	setRegistryName(name);
	setCreativeTab(CreativeTabs.BUILDING_BLOCKS);
	setDefaultState(this.blockState.getBaseState().withProperty(VARIANT, EnumHandler0.EnumType.TIGEREYE));
	
	this.name = name;
	this.dimension = dimension;
	
	ExampleMod.BLOCKS.add(this);
	ExampleMod.ITEMS.add(new ItemBlockVariants(this).setRegistryName(this.getRegistryName()));
	}

	@Override
	public int damageDropped(IBlockState state)
	{
		return((EnumHandler0.EnumType)state.getValue(VARIANT)).getMeta();
	}
	@Override
	public int getMetaFromState(IBlockState state)
	{
		return((EnumHandler0.EnumType)state.getValue(VARIANT)).getMeta();
	}
	@Override
	public IBlockState getStateFromMeta(int meta)
	{
		return this.getDefaultState().withProperty(VARIANT, EnumHandler0.EnumType.byMetadata(meta));
	}
	
	@Override 
	public ItemStack getPickBlock(IBlockState state, RayTraceResult target, World world, BlockPos pos, EntityPlayer player)
	{
		
		return new ItemStack(Item.getItemFromBlock(this), 1, getMetaFromState(world.getBlockState(pos)));
	}
	
	@Override
	public void getSubBlocks(CreativeTabs itemIn, NonNullList<ItemStack> items)
	{
		for(EnumHandler0.EnumType variant : EnumHandler0.EnumType.values())
		{
			items.add(new ItemStack(this, 1, variant.getMeta()));
		}
		
	}	
	
	@Override
	protected BlockStateContainer createBlockState()
	{
		return new BlockStateContainer(this, new IProperty[] {VARIANT});
	}
	
	@Override
	public String getSpecialName(ItemStack stack)
	{
		return EnumHandler0.EnumType.values()[stack.getItemDamage()].getName();
	}
	
	@Override
	public void registerModels() {
		// TODO Auto-generated method stub
	for(int i = 0; i < EnumHandler0.EnumType.values().length; i++)
	{
		
		ExampleMod.proxy.registerVariantRenderer(Item.getItemFromBlock(this), i, "ore_" + this.dimension + "_" + EnumHandler0.EnumType.values()[i].getName(), "inventory");
	}
	
	
	
	
	}
	
	
	
}
