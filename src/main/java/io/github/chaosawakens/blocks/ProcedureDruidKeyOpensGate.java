package net.mcreator.chaosawakens.procedure;

import net.minecraft.world.World;
import net.minecraft.util.math.BlockPos;
import net.minecraft.item.ItemStack;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.Entity;

import net.mcreator.chaosawakens.item.ItemDruidKey;
import net.mcreator.chaosawakens.block.BlockGateBlock;
import net.mcreator.chaosawakens.ElementsChaosawakensMod;

import java.util.Map;

@ElementsChaosawakensMod.ModElement.Tag
public class ProcedureDruidKeyOpensGate extends ElementsChaosawakensMod.ModElement {
	public ProcedureDruidKeyOpensGate(ElementsChaosawakensMod instance) {
		super(instance, 10);
	}

	public static void executeProcedure(Map<String, Object> dependencies) {
		if (dependencies.get("entity") == null) {
			System.err.println("Failed to load dependency entity for procedure DruidKeyOpensGate!");
			return;
		}
		if (dependencies.get("x") == null) {
			System.err.println("Failed to load dependency x for procedure DruidKeyOpensGate!");
			return;
		}
		if (dependencies.get("y") == null) {
			System.err.println("Failed to load dependency y for procedure DruidKeyOpensGate!");
			return;
		}
		if (dependencies.get("z") == null) {
			System.err.println("Failed to load dependency z for procedure DruidKeyOpensGate!");
			return;
		}
		if (dependencies.get("world") == null) {
			System.err.println("Failed to load dependency world for procedure DruidKeyOpensGate!");
			return;
		}
		Entity entity = (Entity) dependencies.get("entity");
		int x = (int) dependencies.get("x");
		int y = (int) dependencies.get("y");
		int z = (int) dependencies.get("z");
		World world = (World) dependencies.get("world");
		if ((((entity instanceof EntityLivingBase) ? ((EntityLivingBase) entity).getHeldItemMainhand() : ItemStack.EMPTY)
				.getItem() == new ItemStack(ItemDruidKey.block, (int) (1)).getItem())) {
			if (((world.getBlockState(new BlockPos((int) (x + 1), (int) y, (int) z))).getBlock() == BlockGateBlock.block.getDefaultState()
					.getBlock())) {
				if (((world.getBlockState(new BlockPos((int) (x + 2), (int) y, (int) z))).getBlock() == BlockGateBlock.block.getDefaultState()
						.getBlock())) {
					if (((world.getBlockState(new BlockPos((int) (x + 3), (int) y, (int) z))).getBlock() == BlockGateBlock.block.getDefaultState()
							.getBlock())) {
						if (((world.getBlockState(new BlockPos((int) (x + 4), (int) y, (int) z))).getBlock() == BlockGateBlock.block.getDefaultState()
								.getBlock())) {
							if (((world.getBlockState(new BlockPos((int) (x + 1), (int) (y - 1), (int) z))).getBlock() == BlockGateBlock.block
									.getDefaultState().getBlock())) {
								if (((world.getBlockState(new BlockPos((int) (x + 2), (int) (y - 1), (int) z))).getBlock() == BlockGateBlock.block
										.getDefaultState().getBlock())) {
									if (((world.getBlockState(new BlockPos((int) (x + 3), (int) (y - 1), (int) z))).getBlock() == BlockGateBlock.block
											.getDefaultState().getBlock())) {
										if (((world.getBlockState(new BlockPos((int) (x + 4), (int) (y - 1), (int) z)))
												.getBlock() == BlockGateBlock.block.getDefaultState().getBlock())) {
											if (((world.getBlockState(new BlockPos((int) (x + 2), (int) (y + 1), (int) z)))
													.getBlock() == BlockGateBlock.block.getDefaultState().getBlock())) {
												if (((world.getBlockState(new BlockPos((int) (x + 3), (int) (y + 1), (int) z)))
														.getBlock() == BlockGateBlock.block.getDefaultState().getBlock())) {
													world.destroyBlock(new BlockPos((int) (x + 1), (int) y, (int) z), false);
													world.destroyBlock(new BlockPos((int) (x + 2), (int) y, (int) z), false);
													world.destroyBlock(new BlockPos((int) (x + 3), (int) y, (int) z), false);
													world.destroyBlock(new BlockPos((int) (x + 4), (int) y, (int) z), false);
													world.destroyBlock(new BlockPos((int) (x + 1), (int) (y - 1), (int) z), false);
													world.destroyBlock(new BlockPos((int) (x + 2), (int) (y - 1), (int) z), false);
													world.destroyBlock(new BlockPos((int) (x + 3), (int) (y - 1), (int) z), false);
													world.destroyBlock(new BlockPos((int) (x + 4), (int) (y - 1), (int) z), false);
													world.destroyBlock(new BlockPos((int) (x + 2), (int) (y + 1), (int) z), false);
													world.destroyBlock(new BlockPos((int) (x + 3), (int) (y + 1), (int) z), false);
													if (entity instanceof EntityPlayer)
														((EntityPlayer) entity).inventory.clearMatchingItems(
																new ItemStack(ItemDruidKey.block, (int) (1)).getItem(), -1, (int) 1, null);
												}
											}
										}
									}
								}
							}
						}
					}
				}
			}
		} else if ((((entity instanceof EntityLivingBase) ? ((EntityLivingBase) entity).getHeldItemMainhand() : ItemStack.EMPTY)
				.getItem() == new ItemStack(ItemDruidKey.block, (int) (1)).getItem())) {
			if (((world.getBlockState(new BlockPos((int) (x - 1), (int) y, (int) z))).getBlock() == BlockGateBlock.block.getDefaultState()
					.getBlock())) {
				if (((world.getBlockState(new BlockPos((int) (x - 2), (int) y, (int) z))).getBlock() == BlockGateBlock.block.getDefaultState()
						.getBlock())) {
					if (((world.getBlockState(new BlockPos((int) (x - 3), (int) y, (int) z))).getBlock() == BlockGateBlock.block.getDefaultState()
							.getBlock())) {
						if (((world.getBlockState(new BlockPos((int) (x - 4), (int) y, (int) z))).getBlock() == BlockGateBlock.block.getDefaultState()
								.getBlock())) {
							if (((world.getBlockState(new BlockPos((int) (x - 1), (int) (y - 1), (int) z))).getBlock() == BlockGateBlock.block
									.getDefaultState().getBlock())) {
								if (((world.getBlockState(new BlockPos((int) (x - 2), (int) (y - 1), (int) z))).getBlock() == BlockGateBlock.block
										.getDefaultState().getBlock())) {
									if (((world.getBlockState(new BlockPos((int) (x - 3), (int) (y - 1), (int) z))).getBlock() == BlockGateBlock.block
											.getDefaultState().getBlock())) {
										if (((world.getBlockState(new BlockPos((int) (x - 4), (int) (y - 1), (int) z)))
												.getBlock() == BlockGateBlock.block.getDefaultState().getBlock())) {
											if (((world.getBlockState(new BlockPos((int) (x - 2), (int) (y + 1), (int) z)))
													.getBlock() == BlockGateBlock.block.getDefaultState().getBlock())) {
												if (((world.getBlockState(new BlockPos((int) (x - 3), (int) (y + 1), (int) z)))
														.getBlock() == BlockGateBlock.block.getDefaultState().getBlock())) {
													world.destroyBlock(new BlockPos((int) (x - 1), (int) y, (int) z), false);
													world.destroyBlock(new BlockPos((int) (x - 2), (int) y, (int) z), false);
													world.destroyBlock(new BlockPos((int) (x - 3), (int) y, (int) z), false);
													world.destroyBlock(new BlockPos((int) (x - 4), (int) y, (int) z), false);
													world.destroyBlock(new BlockPos((int) (x - 1), (int) (y - 1), (int) z), false);
													world.destroyBlock(new BlockPos((int) (x - 2), (int) (y - 1), (int) z), false);
													world.destroyBlock(new BlockPos((int) (x - 3), (int) (y - 1), (int) z), false);
													world.destroyBlock(new BlockPos((int) (x - 4), (int) (y - 1), (int) z), false);
													world.destroyBlock(new BlockPos((int) (x - 2), (int) (y + 1), (int) z), false);
													world.destroyBlock(new BlockPos((int) (x - 3), (int) (y + 1), (int) z), false);
													if (entity instanceof EntityPlayer)
														((EntityPlayer) entity).inventory.clearMatchingItems(
																new ItemStack(ItemDruidKey.block, (int) (1)).getItem(), -1, (int) 1, null);
												}
											}
										}
									}
								}
							}
						}
					}
				}
			}
		} else if ((((entity instanceof EntityLivingBase) ? ((EntityLivingBase) entity).getHeldItemMainhand() : ItemStack.EMPTY)
				.getItem() == new ItemStack(ItemDruidKey.block, (int) (1)).getItem())) {
			if (((world.getBlockState(new BlockPos((int) x, (int) y, (int) (z + 1)))).getBlock() == BlockGateBlock.block.getDefaultState()
					.getBlock())) {
				if (((world.getBlockState(new BlockPos((int) x, (int) y, (int) (z + 2)))).getBlock() == BlockGateBlock.block.getDefaultState()
						.getBlock())) {
					if (((world.getBlockState(new BlockPos((int) x, (int) y, (int) (z + 3)))).getBlock() == BlockGateBlock.block.getDefaultState()
							.getBlock())) {
						if (((world.getBlockState(new BlockPos((int) x, (int) y, (int) (z + 4)))).getBlock() == BlockGateBlock.block.getDefaultState()
								.getBlock())) {
							if (((world.getBlockState(new BlockPos((int) x, (int) (y - 1), (int) (z + 1)))).getBlock() == BlockGateBlock.block
									.getDefaultState().getBlock())) {
								if (((world.getBlockState(new BlockPos((int) x, (int) (y - 1), (int) (z + 2)))).getBlock() == BlockGateBlock.block
										.getDefaultState().getBlock())) {
									if (((world.getBlockState(new BlockPos((int) x, (int) (y - 1), (int) (z + 3)))).getBlock() == BlockGateBlock.block
											.getDefaultState().getBlock())) {
										if (((world.getBlockState(new BlockPos((int) x, (int) (y - 1), (int) (z + 4))))
												.getBlock() == BlockGateBlock.block.getDefaultState().getBlock())) {
											if (((world.getBlockState(new BlockPos((int) x, (int) (y + 1), (int) (z + 2))))
													.getBlock() == BlockGateBlock.block.getDefaultState().getBlock())) {
												if (((world.getBlockState(new BlockPos((int) x, (int) (y + 1), (int) (z + 3))))
														.getBlock() == BlockGateBlock.block.getDefaultState().getBlock())) {
													world.destroyBlock(new BlockPos((int) x, (int) y, (int) (z + 1)), false);
													world.destroyBlock(new BlockPos((int) x, (int) y, (int) (z + 2)), false);
													world.destroyBlock(new BlockPos((int) x, (int) y, (int) (z + 3)), false);
													world.destroyBlock(new BlockPos((int) x, (int) y, (int) (z + 4)), false);
													world.destroyBlock(new BlockPos((int) x, (int) (y - 1), (int) (z + 1)), false);
													world.destroyBlock(new BlockPos((int) x, (int) (y - 1), (int) (z + 2)), false);
													world.destroyBlock(new BlockPos((int) x, (int) (y - 1), (int) (z + 3)), false);
													world.destroyBlock(new BlockPos((int) x, (int) (y - 1), (int) (z + 4)), false);
													world.destroyBlock(new BlockPos((int) x, (int) (y + 1), (int) (z + 2)), false);
													world.destroyBlock(new BlockPos((int) x, (int) (y + 1), (int) (z + 3)), false);
													if (entity instanceof EntityPlayer)
														((EntityPlayer) entity).inventory.clearMatchingItems(
																new ItemStack(ItemDruidKey.block, (int) (1)).getItem(), -1, (int) 1, null);
												}
											}
										}
									}
								}
							}
						}
					}
				}
			}
		} else if ((((entity instanceof EntityLivingBase) ? ((EntityLivingBase) entity).getHeldItemMainhand() : ItemStack.EMPTY)
				.getItem() == new ItemStack(ItemDruidKey.block, (int) (1)).getItem())) {
			if (((world.getBlockState(new BlockPos((int) x, (int) y, (int) (z - 1)))).getBlock() == BlockGateBlock.block.getDefaultState()
					.getBlock())) {
				if (((world.getBlockState(new BlockPos((int) x, (int) y, (int) (z - 2)))).getBlock() == BlockGateBlock.block.getDefaultState()
						.getBlock())) {
					if (((world.getBlockState(new BlockPos((int) x, (int) y, (int) (z - 3)))).getBlock() == BlockGateBlock.block.getDefaultState()
							.getBlock())) {
						if (((world.getBlockState(new BlockPos((int) x, (int) y, (int) (z - 4)))).getBlock() == BlockGateBlock.block.getDefaultState()
								.getBlock())) {
							if (((world.getBlockState(new BlockPos((int) x, (int) (y - 1), (int) (z - 1)))).getBlock() == BlockGateBlock.block
									.getDefaultState().getBlock())) {
								if (((world.getBlockState(new BlockPos((int) x, (int) (y - 1), (int) (z - 2)))).getBlock() == BlockGateBlock.block
										.getDefaultState().getBlock())) {
									if (((world.getBlockState(new BlockPos((int) x, (int) (y - 1), (int) (z - 3)))).getBlock() == BlockGateBlock.block
											.getDefaultState().getBlock())) {
										if (((world.getBlockState(new BlockPos((int) x, (int) (y - 1), (int) (z - 4))))
												.getBlock() == BlockGateBlock.block.getDefaultState().getBlock())) {
											if (((world.getBlockState(new BlockPos((int) x, (int) (y + 1), (int) (z - 2))))
													.getBlock() == BlockGateBlock.block.getDefaultState().getBlock())) {
												if (((world.getBlockState(new BlockPos((int) x, (int) (y + 1), (int) (z - 3))))
														.getBlock() == BlockGateBlock.block.getDefaultState().getBlock())) {
													world.destroyBlock(new BlockPos((int) x, (int) y, (int) (z - 1)), false);
													world.destroyBlock(new BlockPos((int) x, (int) y, (int) (z - 2)), false);
													world.destroyBlock(new BlockPos((int) x, (int) y, (int) (z - 3)), false);
													world.destroyBlock(new BlockPos((int) x, (int) y, (int) (z - 4)), false);
													world.destroyBlock(new BlockPos((int) x, (int) (y - 1), (int) (z - 1)), false);
													world.destroyBlock(new BlockPos((int) x, (int) (y - 1), (int) (z - 2)), false);
													world.destroyBlock(new BlockPos((int) x, (int) (y - 1), (int) (z - 3)), false);
													world.destroyBlock(new BlockPos((int) x, (int) (y - 1), (int) (z - 4)), false);
													world.destroyBlock(new BlockPos((int) x, (int) (y + 1), (int) (z - 2)), false);
													world.destroyBlock(new BlockPos((int) x, (int) (y + 1), (int) (z - 3)), false);
													if (entity instanceof EntityPlayer)
														((EntityPlayer) entity).inventory.clearMatchingItems(
																new ItemStack(ItemDruidKey.block, (int) (1)).getItem(), -1, (int) 1, null);
												}
											}
										}
									}
								}
							}
						}
					}
				}
			}
		}
	}
}
