package io.github.chaosawakens.common.integration;

import com.google.common.cache.Cache;
import com.google.common.cache.CacheBuilder;
import io.github.chaosawakens.ChaosAwakens;
import io.github.chaosawakens.common.blocks.AntInfestedOre;
import io.github.chaosawakens.common.registry.CABlocks;
import mcp.mobius.waila.Waila;
import mcp.mobius.waila.addons.core.HUDHandlerBlocks;
import mcp.mobius.waila.api.*;
import net.minecraft.block.Block;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.StringTextComponent;
import net.minecraft.util.text.TextFormatting;
import net.minecraftforge.registries.ForgeRegistries;
import snownee.jade.JadePlugin;

import java.util.List;

@WailaPlugin
public class CAJadePlugin implements IWailaPlugin {
	private static final Cache<Block, ITextComponent> CACHE = CacheBuilder.newBuilder().build();

	@Override
	public void register(IRegistrar registrar) {
		IComponentProvider provider = new IComponentProvider() {
			@Override
			public ItemStack getStack(IDataAccessor accessor, IPluginConfig config) {
				if (accessor.getBlockState().is(CABlocks.RED_ANT_INFESTED_ORE.get())) {
					return new ItemStack(Items.DIAMOND_ORE);
				} else if (accessor.getBlockState().is(CABlocks.TERMITE_INFESTED_ORE.get())) {
					return new ItemStack(Items.EMERALD_ORE);
				}
				return ItemStack.EMPTY;
			}

			@SuppressWarnings({ "unchecked", "rawtypes" })
			@Override
			public void appendHead(List<ITextComponent> tooltip, IDataAccessor accessor, IPluginConfig config) {
				try {
					ITextComponent name = CACHE.get(accessor.getBlock(), () -> {
						if (accessor.getBlockState().is(CABlocks.RED_ANT_INFESTED_ORE.get())) {
							ResourceLocation infestedOreName = new ResourceLocation("minecraft", Items.DIAMOND_ORE.getRegistryName().getPath());
							ChaosAwakens.LOGGER.debug(infestedOreName);
							Block block = ForgeRegistries.BLOCKS.getValue(infestedOreName);
							if (block != null) {
								return block.getName();
							}
						} else if (accessor.getBlockState().is(CABlocks.TERMITE_INFESTED_ORE.get())) {
							ResourceLocation infestedOreName = new ResourceLocation("minecraft", Items.EMERALD_ORE.getRegistryName().getPath());
							ChaosAwakens.LOGGER.debug(infestedOreName);
							Block block = ForgeRegistries.BLOCKS.getValue(infestedOreName);
							if (block != null) {
								return block.getName();
							}
						}

						return null;
					});
					((ITaggableList) tooltip).setTag(HUDHandlerBlocks.OBJECT_NAME_TAG, new StringTextComponent(String.format((Waila.CONFIG.get()).getFormatting().getBlockName(), name.getString())));
				} catch (Exception ignored) {
				}
			}

			@SuppressWarnings({ "unchecked", "rawtypes" })
			@Override
			public void appendTail(List<ITextComponent> tooltip, IDataAccessor accessor, IPluginConfig config) {
				if (!config.get(JadePlugin.HIDE_MOD_NAME)) {
					if (accessor.getBlock() instanceof AntInfestedOre) {
						((ITaggableList) tooltip).setTag(HUDHandlerBlocks.MOD_NAME_TAG, new StringTextComponent("Minecraft").withStyle(TextFormatting.BLUE).withStyle(TextFormatting.ITALIC));
					}
				}
			}
		};
		registrar.registerStackProvider(provider, AntInfestedOre.class);
		registrar.registerComponentProvider(provider, TooltipPosition.HEAD, AntInfestedOre.class);
		registrar.registerComponentProvider(provider, TooltipPosition.TAIL, AntInfestedOre.class);
	}
}
