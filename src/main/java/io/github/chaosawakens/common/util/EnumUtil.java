package io.github.chaosawakens.common.util;

import io.github.chaosawakens.ChaosAwakens;
import io.github.chaosawakens.api.item.IShieldMaterial;
import io.github.chaosawakens.common.registry.CABlocks;
import io.github.chaosawakens.common.registry.CAItems;
import it.unimi.dsi.fastutil.objects.ObjectArrayList;
import net.minecraft.block.Block;
import net.minecraft.block.Blocks;
import net.minecraft.inventory.EquipmentSlotType;
import net.minecraft.item.IArmorMaterial;
import net.minecraft.item.IItemTier;
import net.minecraft.item.Items;
import net.minecraft.item.crafting.Ingredient;
import net.minecraft.util.IStringSerializable;
import net.minecraft.util.SoundEvent;
import net.minecraft.util.SoundEvents;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.BlockPos.Mutable;
import net.minecraft.world.World;

import javax.annotation.Nullable;
import java.util.Comparator;
import java.util.function.Predicate;
import java.util.function.Supplier;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public final class EnumUtil {

	private EnumUtil() {
		throw new IllegalAccessError("Attempted to instantiate a Utility Class!");
	}

	// Tiers
	public enum CAItemTier implements IItemTier {
		// Harvest Level, Max Uses, Efficiency, Attack Damage,
		// Enchantability, Repair Material
		TOOL_EMERALD(3, 1300, 8, 6, 24, () -> Ingredient.of(Items.EMERALD.getItem())),
		TOOL_EXPERIENCE(3, 3000, 8, 6, 30, () -> Ingredient.of(Items.EMERALD.getItem(), Items.EXPERIENCE_BOTTLE)),
		TOOL_AMETHYST(4, 2700, 9, 11, 18, () -> Ingredient.of(CAItems.AMETHYST.get())),
		TOOL_RUBY(5, 3000, 10, 16, 22, () -> Ingredient.of(CAItems.RUBY.get())),
		TOOL_TIGERS_EYE(4, 2400, 10, 8, 20, () -> Ingredient.of(CAItems.TIGERS_EYE.get())),
		TOOL_CRYSTALWOOD(0, 300, 2, 1, 6, () -> Ingredient.of(CABlocks.CRYSTALWOOD_PLANKS.get())),
		TOOL_KYANITE(1, 800, 3, 2, 6, () -> Ingredient.of(CABlocks.KYANITE.get())),
		TOOL_PINK_TOURMALINE(2, 1100, 7, 8, 6, () -> Ingredient.of(CAItems.PINK_TOURMALINE_INGOT.get())),
		TOOL_CATS_EYE(3, 1600, 8, 8, 20, () -> Ingredient.of(CAItems.CATS_EYE_INGOT.get())),
		TOOL_ULTIMATE(6, 6000, 25, 36, 64, () -> Ingredient.of(CAItems.TITANIUM_INGOT.get(), CAItems.URANIUM_INGOT.get())),
		TOOL_NIGHTMARE(3, 3000, 12, 26, 24, () -> Ingredient.of(CAItems.NIGHTMARE_SCALE.get())),
		TOOL_COPPER(2, 150, 4, 2, 6, () -> Ingredient.of(CAItems.COPPER_LUMP.get())),
		TOOL_BASILISK(3, 3000, 12, 26, 24, () -> Ingredient.of(CAItems.BASILISK_SCALE.get())),
		TOOL_TIN(2, 180, 5, 3, 8, () -> Ingredient.of(CAItems.TIN_LUMP.get())),
		TOOL_SILVER(3, 450, 7, 4, 10, () -> Ingredient.of(CAItems.SILVER_LUMP.get())),
		TOOL_PLATINUM(5, 1600, 8, 6, 12, () -> Ingredient.of(CAItems.PLATINUM_LUMP.get())),
		WEAPON_BATTLEAXE(5, 8000, 15, 47, 64, () -> Ingredient.of(CAItems.TITANIUM_INGOT.get(), CAItems.URANIUM_INGOT.get())),
		WEAPON_SLAYER_CHAINSAW(4, 6500, 30, 0, 0, () -> Ingredient.of(Items.REDSTONE_BLOCK)),
		WEAPON_QUEEN_BATTLEAXE(4, 20000, 15, 663, 96, () -> Ingredient.of(CAItems.QUEEN_SCALE.get())),
		WEAPON_ROYAL_GUARDIAN_SWORD(4, 20000, 15, 748, 96, () -> Ingredient.of(CAItems.ROYAL_GUARDIAN_SCALE.get())),
		WEAPON_RAY_GUN(0, 50, 0, 0, 0, () -> Ingredient.of(Blocks.REDSTONE_BLOCK)),
		WEAPON_GENERIC(2, 1024, 6, 6, 8, () -> Ingredient.EMPTY),
		WEAPON_BIG_HAMMER(2, 2000, 6, 11, 9, () -> Ingredient.EMPTY),
		WEAPON_ATTITUDE_ADJUSTER(2, 4500, 6, 11, 9, () -> Ingredient.EMPTY),
		WEAPON_BERTHA(4, 12000, 16, 496, 72, () -> Ingredient.of(CAItems.MOBZILLA_SCALE.get()));

		private final int harvestLevel;
		private final int maxUses;
		private final float efficiency;
		private final float attackDamage;
		private final int enchantability;
		private final Supplier<Ingredient> repairMaterial;

		CAItemTier(int harvestLevel, int maxUses, float efficiency, float damage, int enchantability, Supplier<Ingredient> repairMaterial) {
			this.harvestLevel = harvestLevel;
			this.maxUses = maxUses;
			this.efficiency = efficiency;
			this.attackDamage = damage;
			this.enchantability = enchantability;
			this.repairMaterial = repairMaterial;
		}

		@Override
		public float getAttackDamageBonus() {
			return this.attackDamage;
		}

		@Override
		public float getSpeed() {
			return this.efficiency;
		}

		@Override
		public int getEnchantmentValue() {
			return this.enchantability;
		}

		@Override
		public int getLevel() {
			return this.harvestLevel;
		}

		@Override
		public int getUses() {
			return this.maxUses;
		}

		@Override
		public Ingredient getRepairIngredient() {
			return this.repairMaterial.get();
		}

		public int getAttackDamageMod() {
			return (int) (getAttackDamageBonus() - (getAttackDamageBonus() + 1)) - 2;
		}
	}

	public enum CAArmorMaterial implements IArmorMaterial {
		// Name, Durability multiplier, Damage Reduction multiplier, Damage Reduction,
		// Enchantability, Sound Events, Toughness, Knockback Resistance, Repair
		// Material
		EMERALD("emerald", 35, new int[] { 2, 5, 6, 2 }, 24, SoundEvents.ARMOR_EQUIP_DIAMOND, 2f, 0f, () -> Ingredient.of(Items.EMERALD)),
		EXPERIENCE("experience", 59, new int[] { 4, 7, 9, 5 }, 32, SoundEvents.ARMOR_EQUIP_DIAMOND, 2.5f, 0f, () -> Ingredient.of(Items.EMERALD)),
		AMETHYST("amethyst", 56, new int[] { 3, 7, 8, 4 }, 18, SoundEvents.ARMOR_EQUIP_DIAMOND, 2f, 0f, () -> Ingredient.of(CAItems.AMETHYST.get())),
		RUBY("ruby", 57, new int[] { 4, 8, 9, 4 }, 22, SoundEvents.ARMOR_EQUIP_DIAMOND, 3f, 0f, () -> Ingredient.of(CAItems.RUBY.get())),
		TIGERS_EYE("tigers_eye", 54, new int[] { 4, 7, 8, 4 }, 20, SoundEvents.ARMOR_EQUIP_DIAMOND, 2.5f, 0f, () -> Ingredient.of(CAItems.TIGERS_EYE.get())),
		LAPIS("lapis", 30, new int[] { 3, 5, 7, 2 }, 24, SoundEvents.ARMOR_EQUIP_DIAMOND, 1.75f, 0f, () -> Ingredient.of(Items.LAPIS_BLOCK)),
		ULTIMATE("ultimate", 384, new int[] { 6, 10, 12, 6 }, 64, SoundEvents.ARMOR_EQUIP_NETHERITE, 4.5f, 0.125f, () -> Ingredient.of(CAItems.TITANIUM_INGOT.get(), CAItems.URANIUM_INGOT.get())),
		LAVA_EEL("lava_eel", 33, new int[] { 2, 5, 7, 2 }, 9, SoundEvents.ARMOR_EQUIP_GENERIC, 0f, 0f, () -> Ingredient.of(CAItems.LAVA_EEL.get())),
		MOTH_SCALE("moth_scale", 227, new int[] { 4, 9, 8, 4 }, 16, SoundEvents.ARMOR_EQUIP_GENERIC, 3f, 0.05f, () -> Ingredient.of(CAItems.MOTH_SCALE.get())),
		ENDER_DRAGON_SCALE("ender_dragon_scale", 100, new int[] {4, 7, 9, 4}, 50, SoundEvents.ARMOR_EQUIP_DIAMOND, 3.0f, 0.1f, () -> Ingredient.of(CAItems.ENDER_DRAGON_SCALE.get())),
		PEACOCK_FEATHER("peacock_feather", 8, new int[] { 2, 4, 5, 2 }, 8, SoundEvents.ARMOR_EQUIP_GENERIC, 0f, 0f, () -> Ingredient.of(CAItems.PEACOCK_FEATHER.get())),
		PINK_TOURMALINE("pink_tourmaline", 24, new int[] { 2, 5, 7, 3 }, 10, SoundEvents.ARMOR_EQUIP_DIAMOND, 1.25f, 0f, () -> Ingredient.of(CAItems.PINK_TOURMALINE_INGOT.get())),
		CATS_EYE("cats_eye", 36, new int[] { 4, 7, 8, 4 }, 20, SoundEvents.ARMOR_EQUIP_DIAMOND, 2.5f, 0f, () -> Ingredient.of(CAItems.CATS_EYE_INGOT.get())),
		COPPER("copper", 9, new int[] { 1, 3, 4, 1 }, 6, SoundEvents.ARMOR_EQUIP_DIAMOND, 0f, 0f, () -> Ingredient.of(CAItems.COPPER_LUMP.get())),
		TIN("tin", 12, new int[] { 1, 4, 5, 2 }, 8, SoundEvents.ARMOR_EQUIP_DIAMOND, 0f, 0f, () -> Ingredient.of(CAItems.TIN_LUMP.get())),
		SILVER("silver", 21, new int[] { 2, 4, 7, 3 }, 10, SoundEvents.ARMOR_EQUIP_DIAMOND, 1f, 0f, () -> Ingredient.of(CAItems.SILVER_LUMP.get())),
		PLATINUM("platinum", 35, new int[] { 3, 6, 8, 3 }, 12, SoundEvents.ARMOR_EQUIP_DIAMOND, 2.5f, 0f, () -> Ingredient.of(CAItems.PLATINUM_LUMP.get())),
		MOBZILLA_SCALE("mobzilla_scale", 1200, new int[] { 7, 11, 13, 7 }, 74, SoundEvents.ARMOR_EQUIP_NETHERITE, 6f, 0.2f, () -> Ingredient.of(CAItems.MOBZILLA_SCALE.get())),
		ROYAL_GUARDIAN("royal_guardian", 1440, new int[] { 8, 12, 14, 8 }, 84, SoundEvents.ARMOR_EQUIP_NETHERITE, 7.5f, 0.4f, () -> Ingredient.of(CAItems.ROYAL_GUARDIAN_SCALE.get())),
		QUEEN_SCALE("queen_scale", 1380, new int[] { 9, 14, 16, 9 }, 96, SoundEvents.ARMOR_EQUIP_NETHERITE, 8f, 0.3f, () -> Ingredient.of(CAItems.QUEEN_SCALE.get()));

		private final int[] MAX_DAMAGE_ARRAY = new int[] { 13, 15, 16, 11 };
		private final String name;
		private final int durability;
		private final int[] damageReductionAmountArray;
		private final int enchantability;
		private final SoundEvent soundOnEquip;
		private final float toughness;
		private final float knockbackResistance;
		private final Supplier<Ingredient> repairMaterial;

		CAArmorMaterial(String nameIn, int durabilityIn, int[] damageReductionAmountArrayIn, int enchantabilityIn, SoundEvent soundOnEquip, float toughnessIn, float knockbackResistanceIn, Supplier<Ingredient> repairMaterialIn) {
			this.name = ChaosAwakens.prefix(nameIn).toString();
			this.durability = durabilityIn;
			this.damageReductionAmountArray = damageReductionAmountArrayIn;
			this.enchantability = enchantabilityIn;
			this.soundOnEquip = soundOnEquip;
			this.toughness = toughnessIn;
			this.knockbackResistance = knockbackResistanceIn;
			this.repairMaterial = repairMaterialIn;
		}

		@Override
		public String getName() {
			return this.name;
		}

		@Override
		public int getDurabilityForSlot(EquipmentSlotType slotIn) {
			return MAX_DAMAGE_ARRAY[slotIn.getIndex()] * this.durability;
		}

		@Override
		public int getDefenseForSlot(EquipmentSlotType slotIn) {
			return this.damageReductionAmountArray[slotIn.getIndex()];
		}

		@Override
		public int getEnchantmentValue() {
			return this.enchantability;
		}

		@Override
		public SoundEvent getEquipSound() {
			return this.soundOnEquip;
		}

		@Override
		public float getToughness() {
			return this.toughness;
		}

		@Override
		public float getKnockbackResistance() {
			return this.knockbackResistance;
		}

		@Override
		public Ingredient getRepairIngredient() {
			return this.repairMaterial.get();
		}
	}

	public enum CAShieldMaterial implements IShieldMaterial {
		;

		private final String name;
		private final int durability;
		private final float toughness;
		private final int enchantability;
		private final float knockbackResistance;
		private final double damageResistance;
		private final Supplier<Ingredient> repairMaterial;

		CAShieldMaterial(String materialName, int durability, float toughness, int enchantability, float knockbackResistance, double damageResistance, Supplier<Ingredient> repairMaterial) {
			this.name = ChaosAwakens.prefix(materialName).toString();
			this.durability = durability;
			this.toughness = toughness;
			this.enchantability = enchantability;
			this.knockbackResistance = knockbackResistance;
			this.damageResistance = damageResistance;
			this.repairMaterial = repairMaterial;
		}

		@Override
		public String getMaterialName() {
			return name;
		}

		@Override
		public int getDurability() {
			return durability;
		}

		@Override
		public float getToughness() {
			return toughness;
		}

		@Override
		public int getEnchantability() {
			return enchantability;
		}

		@Override
		public float getKnockbackResistance() {
			return knockbackResistance;
		}

		@Override
		public double getDamageResistance() {
			return damageResistance;
		}

		@Override
		public Ingredient getRepairIngredient() {
			return repairMaterial.get();
		}
	}

	// Entities
	public enum EntType {
		APPLE("apple"),
		ACACIA("acacia"),
		BIRCH("birch"),
		CHERRY("cherry"),
		CRIMSON("crimson"),
		DARK_OAK("dark_oak"),
		GINKGO("ginkgo"),
		JUNGLE("jungle"),
		OAK("oak"),
		SKYWOOD("skywood"),
		SPRUCE("spruce"),
		PEACH("peach"),
		WARPED("warped");

		private final String name;

		EntType(String name) {
			this.name = name;
		}

		public String getName() {
			return this.name;
		}
	}

	public enum HerculesBeetleType {
		MODERN("modern"),
		THROWBACK("throwback");

		private final String name;

		HerculesBeetleType(String name) {
			this.name = name;
		}

		public String getName() {
			return this.name;
		}
	}
	
	// AI
	public enum GroundMovementOperation {
		MOVE,
		REVERSE,
		STRAFE,
		JUMP,
		WAIT
	}

	// Config
	public enum ElytraDamageType {
		ARMOR,
		ELYTRA
	}

	public enum SurvivalSpawnerManipulationType {
		NO_BLOCKING,
		BLOCK_ALL,
		BLOCK_CHAOS_AWAKENS,
		TAG_BLACKLISTED,
		TAG_WHITELISTED
	}

	public enum ExplosionType {
		NONE,
		BREAK,
		DESTROY
	}

	public enum StalagmiteBlockGenType {
		ORE_ALL,
		ORE_RARE,
		ORE_COMMON,
		FOSSIL,
		NONE
	}

	// Blocks
	public enum WireDirection implements IStringSerializable {
		DEFAULT("default"),
		SIDE_VERTICAL("side_vertical"),
		SIDE_HORIZONTAL("side_horizontal"),
		ALL_HORIZONTAL("all_horizontal"),
		ALL_VERTICAL("all_vertical"),
		ALL("all");

		private final String name;

		WireDirection(String name) {
			this.name = name;
		}

		@Override
		public String getSerializedName() {
			return name;
		}

		@Override
		public String toString() {
			return getSerializedName();
		}

		public boolean hasConnection() {
			return this != DEFAULT;
		}
	}

	// Misc
	public enum BobberLavaType {
		INVALID,
		INSIDE_LAVA,
		ABOVE_LAVA;
	}

	public enum BlockPatternShape {
		CIRCLE {
			@Override
			public ObjectArrayList<BlockPos> applyShape(World targetWorld, BlockPos originPos, double radius, double height, boolean shouldFill, boolean includeCenter, Predicate<Block> blockAffectPredicate) {
				ObjectArrayList<BlockPos> validPositions = new ObjectArrayList<BlockPos>(1);
				Mutable mutablePos = new Mutable(originPos.getX(), originPos.getY(), originPos.getZ());

				mutablePos.set(originPos);
				if (includeCenter) validPositions.add(originPos);

				double minRadX = originPos.getX() - radius;
				double maxRadX = originPos.getX() + radius;
				double minRadY = originPos.getY() - height;
				double maxRadY = originPos.getY() + height;
				double minRadZ = originPos.getZ() - radius;
				double maxRadZ = originPos.getZ() + radius;

				if (!shouldFill) {
					for (int angleTheta = 0; angleTheta < 360; angleTheta++) {
						for (double targetY = minRadY; targetY < maxRadY; targetY++) {
							mutablePos.set(maxRadX * Math.cos(angleTheta), targetY, maxRadZ * Math.sin(angleTheta));
							
							BlockPos targetHollowPos = mutablePos.immutable();
							Block targetHollowBlock = targetWorld.getBlockState(targetHollowPos).getBlock();
							
							if (blockAffectPredicate != null && !blockAffectPredicate.test(targetHollowBlock)) continue;
							
							validPositions.add(targetHollowPos);
						}
					}
				} else {
					for (int angleTheta = 0; angleTheta < 360; angleTheta++) {
						for (double targetX = minRadX; targetX < maxRadX; targetX++) {
							for (double targetZ = minRadZ; targetZ < maxRadZ; targetZ++) {
								for (double targetY = minRadY; targetY < maxRadY; targetY++) {
									mutablePos.set(targetX * Math.cos(angleTheta), targetY, targetZ * Math.sin(angleTheta));
									
									BlockPos targetPos = mutablePos.immutable();
									Block targetBlock = targetWorld.getBlockState(targetPos).getBlock();
									
									if (blockAffectPredicate != null && !blockAffectPredicate.test(targetBlock)) continue;
									
									validPositions.add(targetPos);
								}
							}
						}
					}
				}
				return validPositions;
			}
			
			@Override
			public ObjectArrayList<ObjectArrayList<BlockPos>> getRingPositions(BlockPos originPos, ObjectArrayList<BlockPos> originalShapePositions) {
				ObjectArrayList<ObjectArrayList<BlockPos>> ringPositions = new ObjectArrayList<ObjectArrayList<BlockPos>>();
				
				if (!originalShapePositions.isEmpty()) {
					for (BlockPos targetRingPos : originalShapePositions) {
						ObjectArrayList<BlockPos> validRingPositions = new ObjectArrayList<BlockPos>(1);
						
						if (!validRingPositions.contains(targetRingPos) && targetRingPos.distSqr(originPos) > 0) validRingPositions.add(targetRingPos);
						
						final double firstRingPosDistToOriginSqr = validRingPositions.get(0).distSqr(originPos);
						
						ObjectArrayList<BlockPos> queuedRingPositions = originalShapePositions.stream()
								.filter((curTargetPos) -> curTargetPos.distSqr(originPos) == firstRingPosDistToOriginSqr)
								.collect(Collectors.toCollection(ObjectArrayList::new));
						
						validRingPositions.addAll(queuedRingPositions);
						
						if (!ringPositions.contains(validRingPositions)) ringPositions.add(validRingPositions);
					}
				}
				Comparator<ObjectArrayList<BlockPos>> ringPosComparator = (ringListPrev, ringListPost) -> IntStream.range(0, Math.min(ringListPrev.size(), ringListPost.size()))
						.map(ringPosIndex -> Integer.compare((int) ringListPrev.get(ringPosIndex).distSqr(originPos), (int) ringListPost.get(ringPosIndex).distSqr(originPos)))
						.filter((resultingDistValue) -> resultingDistValue != 0)
						.findFirst()
						.orElse(0);
				
				ringPositions.sort(ringPosComparator);
				
				return ringPositions;
			}
		};
		
		BlockPatternShape() {
		}
		
		public abstract ObjectArrayList<BlockPos> applyShape(World targetWorld, BlockPos originPos, double radius, double height, boolean shouldFill, boolean includeCenter, @Nullable Predicate<Block> blockAffectPredicate);
		public abstract ObjectArrayList<ObjectArrayList<BlockPos>> getRingPositions(BlockPos originPos, ObjectArrayList<BlockPos> originalShapePositions);
	}
}
