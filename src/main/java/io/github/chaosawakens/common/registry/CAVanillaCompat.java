package io.github.chaosawakens.common.registry;

import com.google.common.collect.Maps;

import io.github.chaosawakens.common.entity.projectile.IrukandjiArrowEntity;
import net.minecraft.block.Block;
import net.minecraft.block.Blocks;
import net.minecraft.block.ComposterBlock;
import net.minecraft.block.DispenserBlock;
import net.minecraft.block.FireBlock;
import net.minecraft.dispenser.IPosition;
import net.minecraft.dispenser.ProjectileDispenseBehavior;
import net.minecraft.entity.projectile.ProjectileEntity;
import net.minecraft.item.AxeItem;
import net.minecraft.item.ItemStack;
import net.minecraft.util.IItemProvider;
import net.minecraft.world.World;
import net.minecraftforge.event.furnace.FurnaceFuelBurnTimeEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;

public class CAVanillaCompat {
	public static void setup() {
		// Flammability
		registerFlammable(CABlocks.APPLE_LEAVES.get(), 30, 60);
		registerFlammable(CABlocks.APPLE_LOG.get(), 5, 5);
		registerFlammable(CABlocks.APPLE_WOOD.get(), 5, 5);
		registerFlammable(CABlocks.STRIPPED_APPLE_LOG.get(), 5, 5);
		registerFlammable(CABlocks.STRIPPED_APPLE_WOOD.get(), 5, 5);
		registerFlammable(CABlocks.APPLE_PLANKS.get(), 5, 20);
		registerFlammable(CABlocks.APPLE_SLAB.get(), 5, 20);
		registerFlammable(CABlocks.APPLE_STAIRS.get(), 5, 20);
		registerFlammable(CABlocks.APPLE_FENCE.get(), 5, 20);
		registerFlammable(CABlocks.APPLE_FENCE_GATE.get(), 5, 20);
		registerFlammable(CABlocks.CHERRY_LEAVES.get(), 30, 60);
		registerFlammable(CABlocks.CHERRY_LOG.get(), 5, 5);
		registerFlammable(CABlocks.CHERRY_WOOD.get(), 5, 5);
		registerFlammable(CABlocks.STRIPPED_CHERRY_LOG.get(), 5, 5);
		registerFlammable(CABlocks.STRIPPED_CHERRY_WOOD.get(), 5, 5);
		registerFlammable(CABlocks.CHERRY_PLANKS.get(), 5, 20);
		registerFlammable(CABlocks.CHERRY_SLAB.get(), 5, 20);
		registerFlammable(CABlocks.CHERRY_STAIRS.get(), 5, 20);
		registerFlammable(CABlocks.CHERRY_FENCE.get(), 5, 20);
		registerFlammable(CABlocks.CHERRY_FENCE_GATE.get(), 5, 20);
		registerFlammable(CABlocks.DUPLICATION_LEAVES.get(), 30, 60);
		registerFlammable(CABlocks.DUPLICATION_LOG.get(), 5, 5);
		registerFlammable(CABlocks.DUPLICATION_WOOD.get(), 5, 5);
		registerFlammable(CABlocks.STRIPPED_DUPLICATION_LOG.get(), 5, 5);
		registerFlammable(CABlocks.STRIPPED_DUPLICATION_WOOD.get(), 5, 5);
		registerFlammable(CABlocks.DUPLICATION_PLANKS.get(), 5, 20);
		registerFlammable(CABlocks.DUPLICATION_SLAB.get(), 5, 20);
		registerFlammable(CABlocks.DUPLICATION_STAIRS.get(), 5, 20);
		registerFlammable(CABlocks.DUPLICATION_FENCE.get(), 5, 20);
		registerFlammable(CABlocks.DUPLICATION_FENCE_GATE.get(), 5, 20);
		registerFlammable(CABlocks.PEACH_LEAVES.get(), 30, 60);
		registerFlammable(CABlocks.PEACH_LOG.get(), 5, 5);
		registerFlammable(CABlocks.PEACH_WOOD.get(), 5, 5);
		registerFlammable(CABlocks.STRIPPED_PEACH_LOG.get(), 5, 5);
		registerFlammable(CABlocks.STRIPPED_PEACH_WOOD.get(), 5, 5);
		registerFlammable(CABlocks.PEACH_PLANKS.get(), 5, 20);
		registerFlammable(CABlocks.PEACH_SLAB.get(), 5, 20);
		registerFlammable(CABlocks.PEACH_STAIRS.get(), 5, 20);
		registerFlammable(CABlocks.PEACH_FENCE.get(), 5, 20);
		registerFlammable(CABlocks.PEACH_FENCE_GATE.get(), 5, 20);
		registerFlammable(CABlocks.SKYWOOD_LEAVES.get(), 30, 60);
		registerFlammable(CABlocks.SKYWOOD_LOG.get(), 5, 5);
		registerFlammable(CABlocks.SKYWOOD_WOOD.get(), 5, 5);
		registerFlammable(CABlocks.STRIPPED_SKYWOOD_LOG.get(), 5, 5);
		registerFlammable(CABlocks.STRIPPED_SKYWOOD_WOOD.get(), 5, 5);
		registerFlammable(CABlocks.SKYWOOD_PLANKS.get(), 5, 20);
		registerFlammable(CABlocks.SKYWOOD_SLAB.get(), 5, 20);
		registerFlammable(CABlocks.SKYWOOD_STAIRS.get(), 5, 20);
		registerFlammable(CABlocks.SKYWOOD_FENCE.get(), 5, 20);
		registerFlammable(CABlocks.SKYWOOD_FENCE_GATE.get(), 5, 20);
		registerFlammable(CABlocks.GINKGO_LEAVES.get(), 30, 60);
		registerFlammable(CABlocks.GINKGO_LOG.get(), 5, 5);
		registerFlammable(CABlocks.GINKGO_WOOD.get(), 5, 5);
		registerFlammable(CABlocks.STRIPPED_GINKGO_LOG.get(), 5, 5);
		registerFlammable(CABlocks.STRIPPED_GINKGO_WOOD.get(), 5, 5);
		registerFlammable(CABlocks.GINKGO_PLANKS.get(), 5, 20);
		registerFlammable(CABlocks.GINKGO_SLAB.get(), 5, 20);
		registerFlammable(CABlocks.GINKGO_STAIRS.get(), 5, 20);
		registerFlammable(CABlocks.GINKGO_FENCE.get(), 5, 20);
		registerFlammable(CABlocks.GINKGO_FENCE_GATE.get(), 5, 20);
		registerFlammable(CABlocks.RED_CRYSTAL_LEAVES.get(), 30, 60);
		registerFlammable(CABlocks.GREEN_CRYSTAL_LEAVES.get(), 30, 60);
		registerFlammable(CABlocks.YELLOW_CRYSTAL_LEAVES.get(), 30, 60);
		registerFlammable(CABlocks.CRYSTAL_LOG.get(), 5, 5);
		registerFlammable(CABlocks.CRYSTAL_WOOD.get(), 5, 5);
		registerFlammable(CABlocks.CRYSTAL_PLANKS.get(), 5, 20);
		registerFlammable(CABlocks.CRYSTAL_SLAB.get(), 5, 20);
		registerFlammable(CABlocks.CRYSTAL_STAIRS.get(), 5, 20);
		registerFlammable(CABlocks.CRYSTAL_FENCE.get(), 5, 20);
		registerFlammable(CABlocks.CRYSTAL_FENCE_GATE.get(), 5, 20);
		registerFlammable(CABlocks.CYAN_ROSE.get(), 60, 100);
		registerFlammable(CABlocks.RED_ROSE.get(), 60, 100);
		registerFlammable(CABlocks.PAEONIA.get(), 60, 100);
		registerFlammable(CABlocks.STRAWBERRY_BUSH.get(), 60, 100);
		registerFlammable(CABlocks.CORN_TOP_BLOCK.get(), 60, 100);
		registerFlammable(CABlocks.CORN_BODY_BLOCK.get(), 60, 100);
		registerFlammable(CABlocks.TOMATO_TOP_BLOCK.get(), 60, 100);
		registerFlammable(CABlocks.TOMATO_BODY_BLOCK.get(), 60, 100);
		registerFlammable(CABlocks.DENSE_GRASS.get(), 50, 80);
		registerFlammable(CABlocks.TALL_DENSE_GRASS.get(), 50, 80);
		registerFlammable(CABlocks.THORNY_SUN.get(), 50, 80);
		registerFlammable(CABlocks.BLUE_BULB.get(), 60, 100);
		registerFlammable(CABlocks.PINK_BULB.get(), 60, 100);
		registerFlammable(CABlocks.PURPLE_BULB.get(), 60, 100);
		registerFlammable(CABlocks.CRYSTAL_GRASS.get(), 50, 80);
		registerFlammable(CABlocks.TALL_CRYSTAL_GRASS.get(), 50, 80);
		registerFlammable(CABlocks.RED_CRYSTAL_FLOWER.get(), 60, 100);
		registerFlammable(CABlocks.BLUE_CRYSTAL_FLOWER.get(), 60, 100);
		registerFlammable(CABlocks.GREEN_CRYSTAL_FLOWER.get(), 60, 100);
		registerFlammable(CABlocks.YELLOW_CRYSTAL_FLOWER.get(), 60, 100);		
		registerFlammable(CABlocks.PINK_CRYSTAL_FLOWER.get(), 60, 100);
		registerFlammable(CABlocks.ORANGE_CRYSTAL_FLOWER.get(), 60, 100);
		registerFlammable(CABlocks.RED_CRYSTAL_GROWTH.get(), 60, 100);
		registerFlammable(CABlocks.BLUE_CRYSTAL_GROWTH.get(), 60, 100);
		registerFlammable(CABlocks.GREEN_CRYSTAL_GROWTH.get(), 60, 100);
		registerFlammable(CABlocks.YELLOW_CRYSTAL_GROWTH.get(), 60, 100);
		registerFlammable(CABlocks.ORANGE_CRYSTAL_GROWTH.get(), 60, 100);
		registerFlammable(CABlocks.PINK_CRYSTAL_GROWTH.get(), 60, 100);
		registerFlammable(CABlocks.CRYSTAL_ROSE.get(), 60, 100);

		// Log Stripping
		registerStrippable(CABlocks.APPLE_LOG.get(), CABlocks.STRIPPED_APPLE_LOG.get());
		registerStrippable(CABlocks.APPLE_WOOD.get(), CABlocks.STRIPPED_APPLE_WOOD.get());
		registerStrippable(CABlocks.CHERRY_LOG.get(), CABlocks.STRIPPED_CHERRY_LOG.get());
		registerStrippable(CABlocks.CHERRY_WOOD.get(), CABlocks.STRIPPED_CHERRY_WOOD.get());
		registerStrippable(CABlocks.PEACH_LOG.get(), CABlocks.STRIPPED_PEACH_LOG.get());
		registerStrippable(CABlocks.PEACH_WOOD.get(), CABlocks.STRIPPED_PEACH_WOOD.get());
		registerStrippable(CABlocks.DUPLICATION_LOG.get(), CABlocks.STRIPPED_DUPLICATION_LOG.get());
		registerStrippable(CABlocks.DUPLICATION_WOOD.get(), CABlocks.STRIPPED_DUPLICATION_WOOD.get());
		registerStrippable(CABlocks.SKYWOOD_LOG.get(), CABlocks.STRIPPED_SKYWOOD_LOG.get());
		registerStrippable(CABlocks.SKYWOOD_WOOD.get(), CABlocks.STRIPPED_SKYWOOD_WOOD.get());
		registerStrippable(CABlocks.GINKGO_LOG.get(), CABlocks.STRIPPED_GINKGO_LOG.get());
		registerStrippable(CABlocks.GINKGO_WOOD.get(), CABlocks.STRIPPED_GINKGO_WOOD.get());

		// Compostable Blocks
		registerCompostable(0.3F, CABlocks.APPLE_SAPLING.get());
		registerCompostable(0.3F, CABlocks.APPLE_LEAVES.get());
		registerCompostable(0.2F, CABlocks.APPLE_LEAF_CARPET.get());
		registerCompostable(0.3F, CABlocks.CHERRY_SAPLING.get());
		registerCompostable(0.3F, CABlocks.CHERRY_LEAVES.get());
		registerCompostable(0.2F, CABlocks.CHERRY_LEAF_CARPET.get());
		registerCompostable(0.3F, CABlocks.DUPLICATION_LEAVES.get());
		registerCompostable(0.2F, CABlocks.DUPLICATION_LEAF_CARPET.get());
		registerCompostable(0.3F, CABlocks.PEACH_SAPLING.get());
		registerCompostable(0.3F, CABlocks.PEACH_LEAVES.get());
		registerCompostable(0.2F, CABlocks.PEACH_LEAF_CARPET.get());
//        registerCompostable(0.3F, CABlocks.SKYWOOD_SAPLING.get());
		registerCompostable(0.3F, CABlocks.SKYWOOD_LEAVES.get());
		registerCompostable(0.2F, CABlocks.SKYWOOD_LEAF_CARPET.get());
//        registerCompostable(0.3F, CABlocks.GINKGO_SAPLING.get());
		registerCompostable(0.3F, CABlocks.GINKGO_LEAVES.get());
		registerCompostable(0.2F, CABlocks.GINKGO_LEAF_CARPET.get());
		registerCompostable(0.3F, CABlocks.RED_CRYSTAL_SAPLING.get());
		registerCompostable(0.3F, CABlocks.RED_CRYSTAL_LEAVES.get());
		registerCompostable(0.3F, CABlocks.GREEN_CRYSTAL_SAPLING.get());
		registerCompostable(0.3F, CABlocks.GREEN_CRYSTAL_LEAVES.get());
		registerCompostable(0.3F, CABlocks.YELLOW_CRYSTAL_SAPLING.get());
		registerCompostable(0.3F, CABlocks.YELLOW_CRYSTAL_LEAVES.get());
		registerCompostable(0.5F, CABlocks.TUBE_WORM.get());
		registerCompostable(0.65F, CABlocks.CYAN_ROSE.get());
		registerCompostable(0.65F, CABlocks.RED_ROSE.get());
		registerCompostable(0.65F, CABlocks.PAEONIA.get());
		registerCompostable(0.2F, CABlocks.OAK_LEAF_CARPET.get());
		registerCompostable(0.2F, CABlocks.SPRUCE_LEAF_CARPET.get());
		registerCompostable(0.2F, CABlocks.BIRCH_LEAF_CARPET.get());
		registerCompostable(0.2F, CABlocks.JUNGLE_LEAF_CARPET.get());
		registerCompostable(0.2F, CABlocks.ACACIA_LEAF_CARPET.get());
		registerCompostable(0.2F, CABlocks.DARK_OAK_LEAF_CARPET.get());
		registerCompostable(0.15F, CAItems.CORN_SEEDS.get());
		registerCompostable(0.15F, CAItems.TOMATO_SEEDS.get());
		registerCompostable(0.15F, CAItems.RADISH_SEEDS.get());
		registerCompostable(0.15F, CAItems.LETTUCE_SEEDS.get());
		registerCompostable(0.15F, CAItems.STRAWBERRY_SEEDS.get());
		registerCompostable(0.45F, CABlocks.DENSE_GRASS.get());
		registerCompostable(0.75F, CABlocks.TALL_DENSE_GRASS.get());
		registerCompostable(0.75F, CABlocks.THORNY_SUN.get());
		registerCompostable(0.65F, CABlocks.BLUE_BULB.get());
		registerCompostable(0.65F, CABlocks.PINK_BULB.get());
		registerCompostable(0.65F, CABlocks.PURPLE_BULB.get());
		registerCompostable(0.3F, CABlocks.CRYSTAL_GRASS.get());
		registerCompostable(0.6F, CAItems.CORN.get());
		registerCompostable(0.5F, CAItems.TOMATO.get());
		registerCompostable(0.35F, CAItems.STRAWBERRY.get());
		registerCompostable(0.4F, CAItems.RADISH.get());
		registerCompostable(0.5F, CAItems.LETTUCE.get());
		registerCompostable(0.5F, CAItems.CHEESE.get());
		registerCompostable(0.25F, CAItems.CHERRIES.get());
		registerCompostable(0.3F, CAItems.PEACH.get());
		registerCompostable(0.7F, CAItems.CRYSTAL_APPLE.get());
		registerCompostable(0.65F, CAItems.CRYSTAL_BEETROOT.get());
		registerCompostable(0.75F, CAItems.CRYSTAL_CARROT.get());
		registerCompostable(0.8F, CAItems.CRYSTAL_POTATO.get());
		registerCompostable(0.5F, CABlocks.TALL_CRYSTAL_GRASS.get());
		registerCompostable(0.65F, CABlocks.RED_CRYSTAL_FLOWER.get());
		registerCompostable(0.65F, CABlocks.BLUE_CRYSTAL_FLOWER.get());
		registerCompostable(0.65F, CABlocks.GREEN_CRYSTAL_FLOWER.get());
		registerCompostable(0.65F, CABlocks.YELLOW_CRYSTAL_FLOWER.get());
		registerCompostable(0.65F, CABlocks.PINK_CRYSTAL_FLOWER.get());
		registerCompostable(0.65F, CABlocks.ORANGE_CRYSTAL_FLOWER.get());
		registerCompostable(0.65F, CABlocks.RED_CRYSTAL_GROWTH.get());
		registerCompostable(0.65F, CABlocks.BLUE_CRYSTAL_GROWTH.get());
		registerCompostable(0.65F, CABlocks.GREEN_CRYSTAL_GROWTH.get());
		registerCompostable(0.65F, CABlocks.YELLOW_CRYSTAL_GROWTH.get());
		registerCompostable(0.65F, CABlocks.ORANGE_CRYSTAL_GROWTH.get());
		registerCompostable(0.65F, CABlocks.PINK_CRYSTAL_GROWTH.get());
		registerCompostable(0.65F, CABlocks.CRYSTAL_ROSE.get());
		
		setDispenserBehaviour();
	}

	public static void registerStrippable(Block log, Block stripped_log) {
		AxeItem.STRIPABLES = Maps.newHashMap(AxeItem.STRIPABLES);
		AxeItem.STRIPABLES.put(log, stripped_log);
	}

	public static void registerCompostable(float chance, IItemProvider itemIn) {
		ComposterBlock.COMPOSTABLES.put(itemIn.asItem(), chance);
	}

	public static void registerFlammable(Block blockIn, int encouragement, int flammability) {
		FireBlock fireblock = (FireBlock) Blocks.FIRE;
		fireblock.setFlammable(blockIn, encouragement, flammability);
	}
	
	public static void setDispenserBehaviour() {
		DispenserBlock.registerBehavior(CAItems.IRUKANDJI_ARROW.get(), new ProjectileDispenseBehavior() {			
			@Override
			protected ProjectileEntity getProjectile(World world, IPosition pos, ItemStack stack) {
				return new IrukandjiArrowEntity(world, pos.x(), pos.y(), pos.z());
			}
		});
	}

	//TODO Reformat -- Meme Man
	@SubscribeEvent
	public static void registerFurnaceFuel(FurnaceFuelBurnTimeEvent event) {
		ItemStack fuel = event.getItemStack();
		if (fuel.getItem() == CABlocks.CRYSTAL_ENERGY.get().asItem()) {
			event.setBurnTime(3600);
		} else if (fuel.getItem() == CABlocks.CRYSTAL_CRAFTING_TABLE.get().asItem() || fuel.getItem() == CABlocks.APPLE_FENCE.get().asItem()
				|| fuel.getItem() == CABlocks.CHERRY_FENCE.get().asItem() || fuel.getItem() == CABlocks.DUPLICATION_FENCE.get().asItem()
				|| fuel.getItem() == CABlocks.MOLDY_FENCE.get().asItem() || fuel.getItem() == CABlocks.PEACH_FENCE.get().asItem()
				|| fuel.getItem() == CABlocks.SKYWOOD_FENCE.get().asItem() || fuel.getItem() == CABlocks.GINKGO_FENCE.get().asItem()
				|| fuel.getItem() == CABlocks.CRYSTAL_FENCE.get().asItem() || fuel.getItem() == CABlocks.APPLE_FENCE_GATE.get().asItem()
				|| fuel.getItem() == CABlocks.CHERRY_FENCE_GATE.get().asItem() || fuel.getItem() == CABlocks.DUPLICATION_FENCE_GATE.get().asItem()
				|| fuel.getItem() == CABlocks.PEACH_FENCE_GATE.get().asItem() || fuel.getItem() == CABlocks.SKYWOOD_FENCE_GATE.get().asItem()
				|| fuel.getItem() == CABlocks.GINKGO_FENCE_GATE.get().asItem() || fuel.getItem() == CABlocks.CRYSTAL_FENCE_GATE.get().asItem()
				|| fuel.getItem() == CABlocks.APPLE_TRAPDOOR.get().asItem() || fuel.getItem() == CABlocks.CHERRY_TRAPDOOR.get().asItem()
				|| fuel.getItem() == CABlocks.DUPLICATION_TRAPDOOR.get().asItem() || fuel.getItem() == CABlocks.GINKGO_TRAPDOOR.get().asItem()
				|| fuel.getItem() == CABlocks.PEACH_TRAPDOOR.get().asItem() || fuel.getItem() == CABlocks.SKYWOOD_TRAPDOOR.get().asItem()) {
			event.setBurnTime(300);
		} else if (fuel.getItem() == CAItems.APPLE_BOAT.get() || fuel.getItem() == CAItems.CHERRY_BOAT.get() || fuel.getItem() == CAItems.DUPLICATOR_BOAT.get()
				|| fuel.getItem() == CAItems.GINKGO_BOAT.get() || fuel.getItem() == CAItems.GINKGO_BOAT.get() || fuel.getItem() == CAItems.PEACH_BOAT.get()
				|| fuel.getItem() == CAItems.SKYWOOD_BOAT.get()) {
			event.setBurnTime(1200);
		} else if (fuel.getItem() == CAItems.CRYSTAL_WOOD_SHOVEL.get() || fuel.getItem() == CAItems.CRYSTAL_WOOD_SWORD.get()
				|| fuel.getItem() == CAItems.CRYSTAL_WOOD_HOE.get() || fuel.getItem() == CAItems.CRYSTAL_WOOD_AXE.get()
				|| fuel.getItem() == CAItems.CRYSTAL_WOOD_PICKAXE.get() || fuel.getItem() == CAItems.APPLE_SIGN.get() 
				|| fuel.getItem() == CAItems.CHERRY_SIGN.get() || fuel.getItem() == CAItems.DUPLICATION_SIGN.get() 
				|| fuel.getItem() == CAItems.GINKGO_SIGN.get() || fuel.getItem() == CAItems.PEACH_SIGN.get() || fuel.getItem() == CAItems.SKYWOOD_SIGN.get()
				|| fuel.getItem() == CABlocks.APPLE_DOOR.get().asItem() || fuel.getItem() == CABlocks.CHERRY_DOOR.get().asItem()
				|| fuel.getItem() == CABlocks.DUPLICATION_DOOR.get().asItem() || fuel.getItem() == CABlocks.GINKGO_DOOR.get().asItem()
				|| fuel.getItem() == CABlocks.PEACH_DOOR.get().asItem() || fuel.getItem() == CABlocks.SKYWOOD_DOOR.get().asItem()) {
			event.setBurnTime(200);
		} else if (fuel.getItem() == CAItems.CRYSTAL_SHARD.get() || fuel.getItem() == CATags.Items.CRYSTAL_SAPLING) {
			event.setBurnTime(100);
		}
	}
}
