package io.github.chaosawakens.common.integration.top;

import java.util.function.Function;

import io.github.chaosawakens.ChaosAwakens;
import io.github.chaosawakens.common.blocks.ore.CAEntityTrapOreBlock;
import io.github.chaosawakens.common.entity.creature.land.GazelleEntity;
import io.github.chaosawakens.common.entity.creature.land.StinkBugEntity;
import io.github.chaosawakens.common.entity.creature.land.TreeFrogEntity;
import io.github.chaosawakens.common.entity.creature.land.applecow.AppleCowEntity;
import io.github.chaosawakens.common.entity.misc.CABoatEntity;
import io.github.chaosawakens.common.entity.neutral.land.dino.DimetrodonEntity;
import io.github.chaosawakens.common.entity.neutral.land.gator.CrystalGatorEntity;
import io.github.chaosawakens.common.registry.CABlocks;
import mcjty.theoneprobe.Tools;
import mcjty.theoneprobe.api.CompoundText;
import mcjty.theoneprobe.api.IProbeHitEntityData;
import mcjty.theoneprobe.api.IProbeInfo;
import mcjty.theoneprobe.api.IProbeInfoEntityProvider;
import mcjty.theoneprobe.api.ITheOneProbe;
import mcjty.theoneprobe.api.ProbeMode;
import mcjty.theoneprobe.api.TextStyleClass;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.world.World;
import net.minecraftforge.fml.InterModComms;

public class TheOneProbePlugin {
	
	public static void register() {
		InterModComms.sendTo("theoneprobe", "getTheOneProbe", GetTheOneProbe::new);
	}

	public static class GetTheOneProbe implements Function<ITheOneProbe, Void> {
		@Override
		public Void apply(ITheOneProbe iTheOneProbe) {
			iTheOneProbe.registerBlockDisplayOverride((probeMode, iProbeInfo, playerEntity, world, blockState, iProbeHitData) -> {
				if (blockState.getBlock() instanceof CAEntityTrapOreBlock) {
					if (blockState.is(CABlocks.RED_ANT_INFESTED_ORE.get())) {
						ItemStack diamondStack = new ItemStack(Items.DIAMOND_ORE);
						iProbeInfo
								.horizontal()
								.item(diamondStack)
								.vertical()
								.itemLabel(diamondStack)
								.text(CompoundText.create()
										.style(TextStyleClass.MODNAME)
										.text(Tools.getModName(diamondStack.getItem())));
					} else if (blockState.is(CABlocks.TERMITE_INFESTED_ORE.get())) {
						ItemStack emeraldStack = new ItemStack(Items.EMERALD_ORE);
						iProbeInfo
								.horizontal()
								.item(emeraldStack)
								.vertical()
								.itemLabel(emeraldStack)
								.text(CompoundText.create()
										.style(TextStyleClass.MODNAME)
										.text(Tools.getModName(emeraldStack.getItem())));
					}
					return true;
				}
				return false;
			});

			iTheOneProbe.registerEntityProvider(new IProbeInfoEntityProvider() {
				@Override
				public String getID() {
					return ChaosAwakens.prefix("default").toString();
				}

				@Override
				public void addProbeEntityInfo(ProbeMode probeMode, IProbeInfo iProbeInfo, PlayerEntity playerEntity, World world, Entity entity, IProbeHitEntityData iProbeHitEntityData) {
					String unformattedName = TextFormatting.stripFormatting(entity.getName().getString());
					
					if ("Froakie".equalsIgnoreCase(unformattedName)) iProbeInfo.text(CompoundText.createLabelInfo("Special Frog Species: ", "Blue"));
					
					if (entity instanceof AppleCowEntity) {
						int type = ((AppleCowEntity) entity).getAppleCowType();
						switch (type) {
						case 0:
						default:
							iProbeInfo.text(CompoundText.createLabelInfo("Apple Cow Species: ", "Apple Cow (" + type + ")"));
							return;
						case 1:
							iProbeInfo.text(CompoundText.createLabelInfo("Apple Cow Species: ", "Halloween (" + type + ")"));
						}
					}

		/*			if (entity instanceof BirdEntity) {
						int type = ((BirdEntity) entity).getBirdType();
						switch (type) {
						case 0:
						default:
							iProbeInfo.text(CompoundText.createLabelInfo("Bird Species: ", "Black (" + type + ")"));
							return;
						case 1:
							iProbeInfo.text(CompoundText.createLabelInfo("Bird Species: ", "Brown (" + type + ")"));
							return;
						case 2:
							iProbeInfo.text(CompoundText.createLabelInfo("Bird Species: ", "Blue (" + type + ")"));
							return;
						case 3:
							iProbeInfo.text(CompoundText.createLabelInfo("Bird Species: ", "Green (" + type + ")"));
							return;
						case 4:
							iProbeInfo.text(CompoundText.createLabelInfo("Bird Species: ", "Red (" + type + ")"));
						}
					}*/
					
					if (entity instanceof CABoatEntity) {
						String type = ((CABoatEntity) entity).getBoatWoodType();
						iProbeInfo.text(CompoundText.createLabelInfo(type.substring(type.indexOf(":") + 1).replaceFirst(type.substring(0), type.substring(0).toUpperCase()).concat(" Boat"), ""));
					}

					if (entity instanceof TreeFrogEntity) {
						int type = ((TreeFrogEntity) entity).getTreeFrogType();
						switch (type) {
						case 0:
						default:
							iProbeInfo.text(CompoundText.createLabelInfo("Tree Frog Species: ", "Green (" + type + ")"));
							return;
						case 1:
							iProbeInfo.text(CompoundText.createLabelInfo("Tree Frog Species: ", "Gray (" + type + ")"));
							return;
						case 2:
							iProbeInfo.text(CompoundText.createLabelInfo("Tree Frog Species: ", "Squirrel (" + type + ")"));
							return;
						case 3:
							iProbeInfo.text(CompoundText.createLabelInfo("Tree Frog Species: ", "Pine Woods (" + type + ")"));
						}
					}

					if (entity instanceof GazelleEntity) {
						int type = ((GazelleEntity) entity).getGazelleType();
						switch (type) {
						case 0:
						default:
							iProbeInfo.text(CompoundText.createLabelInfo("Gazelle Species: ", "Brown (" + type + ")"));
							return;
						case 1:
							iProbeInfo.text(CompoundText.createLabelInfo("Gazelle Species: ", "Tan (" + type + ")"));
							return;
						case 2:
							iProbeInfo.text(CompoundText.createLabelInfo("Gazelle Species: ", "Red (" + type + ")"));
							return;
						case 3:
							iProbeInfo.text(CompoundText.createLabelInfo("Gazelle Species: ", "Black (" + type + ")"));
							return;
						case 4:
							iProbeInfo.text(CompoundText.createLabelInfo("Gazelle Species: ", "Beige (" + type + ")"));
						}
					}

					if (entity instanceof CrystalGatorEntity) {
						int type = ((CrystalGatorEntity) entity).getCrystalGatorType();
						switch (type) {
						case 0:
						default:
							iProbeInfo.text(CompoundText.createLabelInfo("Crystal Gator Species: ", "Blue (" + type + ")"));
							return;
						case 1:
							iProbeInfo.text(CompoundText.createLabelInfo("Crystal Gator Species: ", "Red (" + type + ")"));
							return;
						case 2:
							iProbeInfo.text(CompoundText.createLabelInfo("Crystal Gator Species: ", "Yellow (" + type + ")"));
							return;
						case 3:
							iProbeInfo.text(CompoundText.createLabelInfo("Crystal Gator Species: ", "Orange (" + type + ")"));
							return;
						case 4:
							iProbeInfo.text(CompoundText.createLabelInfo("Crystal Gator Species: ", "Pink (" + type + ")"));
							return;
						case 5:
							iProbeInfo.text(CompoundText.createLabelInfo("Crystal Gator Species: ", "Green (" + type + ")"));
						}
					}

					if (entity instanceof DimetrodonEntity) {
						int type = ((DimetrodonEntity) entity).getDimetrodonType();
						switch (type) {
							case 0:
							default:
								iProbeInfo.text(CompoundText.createLabelInfo("Dimetrodon Species: ", "Green (" + type + ")"));
								return;
							case 1:
								iProbeInfo.text(CompoundText.createLabelInfo("Dimetrodon Species: ", "Orange (" + type + ")"));
								return;
							case 2:
								iProbeInfo.text(CompoundText.createLabelInfo("Dimetrodon Species: ", "Purple (" + type + ")"));
								return;
							case 3:
								iProbeInfo.text(CompoundText.createLabelInfo("Dimetrodon Species: ", "Throwback (" + type + ")"));
						}
					}

					if (entity instanceof StinkBugEntity) {
						int type = ((StinkBugEntity) entity).getStinkBugType();
						switch (type) {
							case 0:
							default:
								iProbeInfo.text(CompoundText.createLabelInfo("Stink Bug Species: ", "Green (" + type + ")"));
								return;
							case 1:
								iProbeInfo.text(CompoundText.createLabelInfo("Stink Bug Species: ", "Blue (" + type + ")"));
								return;
							case 2:
								iProbeInfo.text(CompoundText.createLabelInfo("Stink Bug Species: ", "Brown (" + type + ")"));
								return;
							case 3:
								iProbeInfo.text(CompoundText.createLabelInfo("Stink Bug Species: ", "Cyan (" + type + ")"));
								return;
							case 4:
								iProbeInfo.text(CompoundText.createLabelInfo("Stink Bug Species: ", "Gray & Blue (" + type + ")"));
								return;
							case 5:
								iProbeInfo.text(CompoundText.createLabelInfo("Stink Bug Species: ", "Gray & Yellow (" + type + ")"));
								return;
							case 6:
								iProbeInfo.text(CompoundText.createLabelInfo("Stink Bug Species: ", "Throwback (" + type + ")"));
						}
					}
				}
			});
			return null;
		}
	}
}
