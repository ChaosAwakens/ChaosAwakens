package io.github.chaosawakens.common.util;

import io.github.chaosawakens.ChaosAwakens;
import io.github.chaosawakens.common.items.UltimateHoeItem;
import io.github.chaosawakens.common.registry.CABlocks;
import io.github.chaosawakens.common.registry.CAItems;
import net.minecraft.block.Blocks;
import net.minecraft.inventory.EquipmentSlotType;
import net.minecraft.item.IArmorMaterial;
import net.minecraft.item.IItemTier;
import net.minecraft.item.Item;
import net.minecraft.item.Items;
import net.minecraft.item.crafting.Ingredient;
import net.minecraft.util.SoundEvent;
import net.minecraft.util.SoundEvents;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

import java.util.EnumSet;
import java.util.Optional;
import java.util.function.Supplier;

import javax.annotation.Nullable;

public class EnumUtils {
	public enum CAItemTier implements IItemTier {
		// Harvest Level, Max Uses, Efficiency, Attack Damage,
		// Enchantability, Repair Material
		TOOL_EMERALD(3, 1300, 8, 6, 24, () -> Ingredient.of(Items.EMERALD.getItem())),
		TOOL_AMETHYST(4, 2000, 9, 11, 18, () -> Ingredient.of(CAItems.AMETHYST.get())),
		TOOL_RUBY(5, 1800, 10, 16, 22, () -> Ingredient.of(CAItems.RUBY.get())),
		TOOL_TIGERS_EYE(4, 1600, 10, 8, 20, () -> Ingredient.of(CAItems.TIGERS_EYE.get())),
		TOOL_CRYSTAL_WOOD(0, 300, 2, 1, 6, () -> Ingredient.of(CABlocks.CRYSTAL_PLANKS.get())),
		TOOL_KYANITE(1, 800, 3, 2, 6, () -> Ingredient.of(CABlocks.KYANITE.get())),
		TOOL_PINK_TOURMALINE(2, 1100, 7, 8, 6, () -> Ingredient.of(CAItems.PINK_TOURMALINE_INGOT.get())),
		TOOL_CATS_EYE(3, 1600, 8, 8, 20, () -> Ingredient.of(CAItems.CATS_EYE_INGOT.get())),
		TOOL_ULTIMATE(6, 3000, 25, 36, 64, () -> Ingredient.of(CAItems.TITANIUM_INGOT.get(), CAItems.URANIUM_INGOT.get())),
		TOOL_NIGHTMARE(3, 1800, 12, 26, 24, () -> Ingredient.of(CAItems.NIGHTMARE_SCALE.get())),
		TOOL_COPPER(2, 150, 4, 2, 6, () -> Ingredient.of(CAItems.COPPER_LUMP.get())),
		TOOL_TIN(2, 180, 5, 3, 8, () -> Ingredient.of(CAItems.TIN_LUMP.get())),
		TOOL_SILVER(3, 450, 7, 4, 10, () -> Ingredient.of(CAItems.SILVER_LUMP.get())),
		TOOL_PLATINUM(5, 1600, 8, 6, 12, () -> Ingredient.of(CAItems.PLATINUM_LUMP.get())),
		WEAPON_BATTLEAXE(5, 1200, 15, 47, 64, () -> Ingredient.of(CAItems.TITANIUM_INGOT.get(), CAItems.URANIUM_INGOT.get())),
		WEAPON_SLAYER_CHAINSAW(4, 250, 30, 40, 0, () -> Ingredient.of(Items.REDSTONE_BLOCK)),
		WEAPON_QUEEN_BATTLEAXE(4, 10000, 15, 663, 96, () -> Ingredient.of(CAItems.QUEEN_SCALE.get())),
		WEAPON_ROYAL_GUARDIAN_SWORD(4, 10000, 15, 748, 96, () -> Ingredient.EMPTY),
		WEAPON_RAY_GUN(0, 50, 0, 0, 0, () -> Ingredient.of(Blocks.REDSTONE_BLOCK)),
		WEAPON_GENERIC(2, 1024, 6, 6, 8, () -> Ingredient.EMPTY),
		WEAPON_BIG_HAMMER(2, 2000, 6, 11, 9, () -> Ingredient.EMPTY),
		WEAPON_BERTHA(4, 6000, 16, 496, 72, () -> Ingredient.of(CAItems.TITANIUM_INGOT.get(), CAItems.URANIUM_INGOT.get()));

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

		public float getAttackDamageBonus() {
			return this.attackDamage;
		}

		public float getSpeed() {
			return this.efficiency;
		}

		public int getEnchantmentValue() {
			return this.enchantability;
		}

		public int getLevel() {
			return this.harvestLevel;
		}

		public int getUses() {
			return this.maxUses;
		}

		@Override
		public Ingredient getRepairIngredient() {
			return this.repairMaterial.get();
		}
	}

	public enum CAArmorMaterial implements IArmorMaterial {
		// Name, Durability multiplier, Damage Reduction multiplier, Damage Reduction,
		// Enchantability, Sound Events, Toughness, Knockback Resistance, Repair
		// Material
		EMERALD("emerald", 35, new int[] { 3, 6, 8, 3 }, 24, SoundEvents.ARMOR_EQUIP_DIAMOND, 2f, 0f, () -> Ingredient.of(Items.EMERALD)),
		EXPERIENCE("experience", 39, new int[] { 4, 7, 9, 5 }, 32, SoundEvents.ARMOR_EQUIP_DIAMOND, 2.5f, 0f, () -> Ingredient.of(Items.EMERALD)),
		AMETHYST("amethyst", 38, new int[] { 3, 7, 8, 4 }, 18, SoundEvents.ARMOR_EQUIP_DIAMOND, 2f, 0f, () -> Ingredient.of(CAItems.AMETHYST.get())),
		RUBY("ruby", 37, new int[] { 4, 8, 9, 4 }, 22, SoundEvents.ARMOR_EQUIP_DIAMOND, 3f, 0f, () -> Ingredient.of(CAItems.RUBY.get())),
		TIGERS_EYE("tigers_eye", 36, new int[] { 4, 7, 8, 4 }, 20, SoundEvents.ARMOR_EQUIP_DIAMOND, 2.5f, 0f, () -> Ingredient.of(CAItems.TIGERS_EYE.get())),
		LAPIS("lapis", 22, new int[] { 2, 5, 7, 2 }, 24, SoundEvents.ARMOR_EQUIP_DIAMOND, 1.75f, 0f, () -> Ingredient.of(Items.LAPIS_BLOCK)),
		ULTIMATE("ultimate", 48, new int[] { 6, 10, 12, 6 }, 64, SoundEvents.ARMOR_EQUIP_NETHERITE, 4.5f, 0.125f, () -> Ingredient.of(CAItems.TITANIUM_INGOT.get(), CAItems.URANIUM_INGOT.get())),
		LAVA_EEL("lava_eel", 33, new int[] { 2, 5, 7, 2 }, 9, SoundEvents.ARMOR_EQUIP_GENERIC, 0f, 0f, () -> Ingredient.of(CAItems.LAVA_EEL.get())),
		MOTH_SCALE("moth_scale", 35, new int[] { 2, 5, 7, 2 }, 16, SoundEvents.ARMOR_EQUIP_GENERIC, 1.75f, 0.05f, () -> Ingredient.of(CAItems.MOTH_SCALE.get())),
		PEACOCK_FEATHER("peacock_feather", 8, new int[] { 2, 4, 5, 2 }, 8, SoundEvents.ARMOR_EQUIP_GENERIC, 0f, 0f, () -> Ingredient.of(CAItems.PEACOCK_FEATHER.get())),
		PINK_TOURMALINE("pink_tourmaline", 24, new int[] { 2, 5, 7, 3 }, 10, SoundEvents.ARMOR_EQUIP_DIAMOND, 1.25f, 0f, () -> Ingredient.of(CAItems.PINK_TOURMALINE_INGOT.get())),
		CATS_EYE("cats_eye", 36, new int[] { 4, 7, 8, 4 }, 20, SoundEvents.ARMOR_EQUIP_DIAMOND, 2.5f, 0f, () -> Ingredient.of(CAItems.CATS_EYE_INGOT.get())),
		COPPER("copper", 9, new int[] { 1, 3, 4, 1 }, 6, SoundEvents.ARMOR_EQUIP_DIAMOND, 0f, 0f, () -> Ingredient.of(CAItems.COPPER_LUMP.get())),
		TIN("tin", 12, new int[] { 1, 4, 5, 2 }, 8, SoundEvents.ARMOR_EQUIP_DIAMOND, 0f, 0f, () -> Ingredient.of(CAItems.TIN_LUMP.get())),
		SILVER("silver", 21, new int[] { 2, 4, 7, 3 }, 10, SoundEvents.ARMOR_EQUIP_DIAMOND, 1f, 0f, () -> Ingredient.of(CAItems.SILVER_LUMP.get())),
		PLATINUM("platinum", 35, new int[] { 3, 6, 8, 3 }, 12, SoundEvents.ARMOR_EQUIP_DIAMOND, 2.5f, 0f, () -> Ingredient.of(CAItems.PLATINUM_LUMP.get())),
		MOBZILLA_SCALE("mobzilla_scale", 40, new int[] { 7, 11, 13, 7 }, 74, SoundEvents.ARMOR_EQUIP_NETHERITE, 6f, 0.2f, () -> Ingredient.EMPTY),
		ROYAL_GUARDIAN("royal_guardian", 72, new int[] { 8, 12, 14, 8 }, 84, SoundEvents.ARMOR_EQUIP_NETHERITE, 7.5f, 0.4f, () -> Ingredient.EMPTY),
		QUEEN_SCALE("queen_scale", 68, new int[] { 9, 14, 16, 9 }, 96, SoundEvents.ARMOR_EQUIP_NETHERITE, 8f, 0.3f, () -> Ingredient.of(CAItems.QUEEN_SCALE.get()));

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
			this.name = ChaosAwakens.MODID + ":" + nameIn;
			this.durability = durabilityIn;
			this.damageReductionAmountArray = damageReductionAmountArrayIn;
			this.enchantability = enchantabilityIn;
			this.soundOnEquip = soundOnEquip;
			this.toughness = toughnessIn;
			this.knockbackResistance = knockbackResistanceIn;
			this.repairMaterial = repairMaterialIn;
		}

		@OnlyIn(Dist.CLIENT)
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
	
	//TODO: efficiency
	public enum CAGearEnumList {
		AMETHYST_SWORD(CAItems.AMETHYST_SWORD.get().getRegistryName().toString(), CAItems.AMETHYST_SWORD.get()),
		AMETHYST_PICKAXE(CAItems.AMETHYST_PICKAXE.get().getRegistryName().toString(), CAItems.AMETHYST_PICKAXE.get()),
		AMETHYST_AXE(CAItems.AMETHYST_AXE.get().getRegistryName().toString(), CAItems.AMETHYST_AXE.get()),
		AMETHYST_SHOVEL(CAItems.AMETHYST_SHOVEL.get().getRegistryName().toString(), CAItems.AMETHYST_SHOVEL.get()),
		AMETHYST_HOE(CAItems.AMETHYST_HOE.get().getRegistryName().toString(), CAItems.AMETHYST_HOE.get()),
		ATTITUDE_ADJUSTER(CAItems.ATTITUDE_ADJUSTER.get().getRegistryName().toString(), CAItems.ATTITUDE_ADJUSTER.get()),
		BATTLE_AXE(CAItems.BATTLE_AXE.get().getRegistryName().toString(), CAItems.BATTLE_AXE.get()),
		BIG_BERTHA(CAItems.BIG_BERTHA.get().getRegistryName().toString(), CAItems.BIG_BERTHA.get()),
		BIG_HAMMER(CAItems.BIG_HAMMER.get().getRegistryName().toString(), CAItems.BIG_HAMMER.get()),
		CATS_EYE_SWORD(CAItems.CATS_EYE_SWORD.get().getRegistryName().toString(), CAItems.CATS_EYE_SWORD.get()),
		CATS_EYE_PICKAXE(CAItems.CATS_EYE_PICKAXE.get().getRegistryName().toString(), CAItems.CATS_EYE_PICKAXE.get()),
		CATS_EYE_AXE(CAItems.CATS_EYE_AXE.get().getRegistryName().toString(), CAItems.CATS_EYE_AXE.get()),
		CATS_EYE_SHOVEL(CAItems.CATS_EYE_SHOVEL.get().getRegistryName().toString(), CAItems.CATS_EYE_SHOVEL.get()),
		CATS_EYE_HOE(CAItems.CATS_EYE_HOE.get().getRegistryName().toString(), CAItems.CATS_EYE_HOE.get()),
		COPPER_SWORD(CAItems.COPPER_SWORD.get().getRegistryName().toString(), CAItems.COPPER_SWORD.get()),
		COPPER_PICKAXE(CAItems.COPPER_PICKAXE.get().getRegistryName().toString(), CAItems.COPPER_PICKAXE.get()),
		COPPER_AXE(CAItems.COPPER_AXE.get().getRegistryName().toString(), CAItems.COPPER_AXE.get()),
		COPPER_SHOVEL(CAItems.COPPER_SHOVEL.get().getRegistryName().toString(), CAItems.COPPER_SHOVEL.get()),
		COPPER_HOE(CAItems.COPPER_HOE.get().getRegistryName().toString(), CAItems.COPPER_HOE.get()),
		CRYSTAL_WOOD_SWORD(CAItems.CRYSTAL_WOOD_SWORD.get().getRegistryName().toString(), CAItems.CRYSTAL_WOOD_SWORD.get()),
		CRYSTAL_WOOD_PICKAXE(CAItems.CRYSTAL_WOOD_PICKAXE.get().getRegistryName().toString(), CAItems.CRYSTAL_WOOD_PICKAXE.get()),
		CRYSTAL_WOOD_AXE(CAItems.CRYSTAL_WOOD_AXE.get().getRegistryName().toString(), CAItems.CRYSTAL_WOOD_AXE.get()),
		CRYSTAL_WOOD_SHOVEL(CAItems.CRYSTAL_WOOD_SHOVEL.get().getRegistryName().toString(), CAItems.CRYSTAL_WOOD_SHOVEL.get()),
		CRYSTAL_WOOD_HOE(CAItems.CRYSTAL_WOOD_HOE.get().getRegistryName().toString(), CAItems.CRYSTAL_WOOD_HOE.get()),
		EMERALD_SWORD(CAItems.EMERALD_SWORD.get().getRegistryName().toString(), CAItems.EMERALD_SWORD.get()),
		EMERALD_PICKAXE(CAItems.EMERALD_PICKAXE.get().getRegistryName().toString(), CAItems.EMERALD_PICKAXE.get()),
		EMERALD_AXE(CAItems.EMERALD_AXE.get().getRegistryName().toString(), CAItems.EMERALD_AXE.get()),
		EMERALD_SHOVEL(CAItems.EMERALD_SHOVEL.get().getRegistryName().toString(), CAItems.EMERALD_SHOVEL.get()),
		EMERALD_HOE(CAItems.EMERALD_HOE.get().getRegistryName().toString(), CAItems.EMERALD_HOE.get()),
		EXPERIENCE_SWORD(CAItems.EXPERIENCE_SWORD.get().getRegistryName().toString(), CAItems.EXPERIENCE_SWORD.get()),
		FAIRY_SWORD(CAItems.FAIRY_SWORD.get().getRegistryName().toString(), CAItems.FAIRY_SWORD.get()),
		KYANITE_SWORD(CAItems.KYANITE_SWORD.get().getRegistryName().toString(), CAItems.KYANITE_SWORD.get()),
		KYANITE_PICKAXE(CAItems.KYANITE_PICKAXE.get().getRegistryName().toString(), CAItems.KYANITE_PICKAXE.get()),
		KYANITE_AXE(CAItems.KYANITE_AXE.get().getRegistryName().toString(), CAItems.KYANITE_AXE.get()),
		KYANITE_SHOVEL(CAItems.KYANITE_SHOVEL.get().getRegistryName().toString(), CAItems.KYANITE_SHOVEL.get()),
		KYANITE_HOE(CAItems.KYANITE_HOE.get().getRegistryName().toString(), CAItems.KYANITE_HOE.get()),
		MANTIS_CLAW(CAItems.MANTIS_CLAW.get().getRegistryName().toString(), CAItems.MANTIS_CLAW.get()),
		NIGHTMARE_SWORD(CAItems.NIGHTMARE_SWORD.get().getRegistryName().toString(), CAItems.NIGHTMARE_SWORD.get()),
		PINK_TOURMALINE_SWORD(CAItems.PINK_TOURMALINE_SWORD.get().getRegistryName().toString(), CAItems.PINK_TOURMALINE_SWORD.get()),
		PINK_TOURMALINE_PICKAXE(CAItems.PINK_TOURMALINE_PICKAXE.get().getRegistryName().toString(), CAItems.PINK_TOURMALINE_PICKAXE.get()),
		PINK_TOURMALINE_AXE(CAItems.PINK_TOURMALINE_AXE.get().getRegistryName().toString(), CAItems.PINK_TOURMALINE_AXE.get()),
		PINK_TOURMALINE_SHOVEL(CAItems.PINK_TOURMALINE_SHOVEL.get().getRegistryName().toString(), CAItems.PINK_TOURMALINE_SHOVEL.get()),
		PINK_TOURMALINE_HOE(CAItems.PINK_TOURMALINE_HOE.get().getRegistryName().toString(), CAItems.PINK_TOURMALINE_HOE.get()),
		PLATINUM_SWORD(CAItems.PLATINUM_SWORD.get().getRegistryName().toString(), CAItems.PLATINUM_SWORD.get()),
		PLATINUM_PICKAXE(CAItems.PLATINUM_PICKAXE.get().getRegistryName().toString(), CAItems.PLATINUM_PICKAXE.get()),
		PLATINUM_AXE(CAItems.PLATINUM_AXE.get().getRegistryName().toString(), CAItems.PLATINUM_AXE.get()),
		PLATINUM_SHOVEL(CAItems.PLATINUM_SHOVEL.get().getRegistryName().toString(), CAItems.PLATINUM_SHOVEL.get()),
		PLATINUM_HOE(CAItems.PLATINUM_HOE.get().getRegistryName().toString(), CAItems.PLATINUM_HOE.get()),
		POISON_SWORD(CAItems.POISON_SWORD.get().getRegistryName().toString(), CAItems.POISON_SWORD.get()),
		PRISMATIC_REAPER(CAItems.PRISMATIC_REAPER.get().getRegistryName().toString(), CAItems.PRISMATIC_REAPER.get()),
		QUEEN_SCALE_BATTLE_AXE(CAItems.QUEEN_SCALE_BATTLE_AXE.get().getRegistryName().toString(), CAItems.QUEEN_SCALE_BATTLE_AXE.get()),
		RAT_SWORD(CAItems.RAT_SWORD.get().getRegistryName().toString(), CAItems.RAT_SWORD.get()),
		ROYAL_GUARDIAN_SWORD(CAItems.ROYAL_GUARDIAN_SWORD.get().getRegistryName().toString(), CAItems.ROYAL_GUARDIAN_SWORD.get()),
		RUBY_SWORD(CAItems.RUBY_SWORD.get().getRegistryName().toString(), CAItems.RUBY_SWORD.get()),
		RUBY_PICKAXE(CAItems.RUBY_PICKAXE.get().getRegistryName().toString(), CAItems.RUBY_PICKAXE.get()),
		RUBY_AXE(CAItems.RUBY_AXE.get().getRegistryName().toString(), CAItems.RUBY_AXE.get()),
		RUBY_SHOVEL(CAItems.RUBY_SHOVEL.get().getRegistryName().toString(), CAItems.RUBY_SHOVEL.get()),
		RUBY_HOE(CAItems.RUBY_HOE.get().getRegistryName().toString(), CAItems.RUBY_HOE.get()),
		SILVER_SWORD(CAItems.SILVER_SWORD.get().getRegistryName().toString(), CAItems.SILVER_SWORD.get()),
		SILVER_PICKAXE(CAItems.SILVER_PICKAXE.get().getRegistryName().toString(), CAItems.SILVER_PICKAXE.get()),
		SILVER_AXE(CAItems.SILVER_AXE.get().getRegistryName().toString(), CAItems.SILVER_AXE.get()),
		SILVER_SHOVEL(CAItems.SILVER_SHOVEL.get().getRegistryName().toString(), CAItems.SILVER_SHOVEL.get()),
		SILVER_HOE(CAItems.SILVER_HOE.get().getRegistryName().toString(), CAItems.SILVER_HOE.get()),
		SLAYER_CHAINSAW(CAItems.SLAYER_CHAINSAW.get().getRegistryName().toString(), CAItems.SLAYER_CHAINSAW.get()),
		TIGERS_EYE_SWORD(CAItems.TIGERS_EYE_SWORD.get().getRegistryName().toString(), CAItems.TIGERS_EYE_SWORD.get()),
		TIGERS_EYE_PICKAXE(CAItems.TIGERS_EYE_PICKAXE.get().getRegistryName().toString(), CAItems.TIGERS_EYE_PICKAXE.get()),
		TIGERS_EYE_AXE(CAItems.TIGERS_EYE_AXE.get().getRegistryName().toString(), CAItems.TIGERS_EYE_AXE.get()),
		TIGERS_EYE_SHOVEL(CAItems.TIGERS_EYE_SHOVEL.get().getRegistryName().toString(), CAItems.TIGERS_EYE_SHOVEL.get()),
		TIGERS_EYE_HOE(CAItems.TIGERS_EYE_HOE.get().getRegistryName().toString(), CAItems.TIGERS_EYE_HOE.get()),
		TIN_SWORD(CAItems.TIN_SWORD.get().getRegistryName().toString(), CAItems.TIN_SWORD.get()),
		TIN_PICKAXE(CAItems.TIN_PICKAXE.get().getRegistryName().toString(), CAItems.TIN_PICKAXE.get()),
		TIN_AXE(CAItems.TIN_AXE.get().getRegistryName().toString(), CAItems.TIN_AXE.get()),
		TIN_SHOVEL(CAItems.TIN_SHOVEL.get().getRegistryName().toString(), CAItems.TIN_SHOVEL.get()),
		TIN_HOE(CAItems.TIN_HOE.get().getRegistryName().toString(), CAItems.TIN_HOE.get()),
		ULTIMATE_SWORD(CAItems.ULTIMATE_SWORD.get().getRegistryName().toString(), CAItems.ULTIMATE_SWORD.get()),
		ULTIMATE_PICKAXE(CAItems.ULTIMATE_PICKAXE.get().getRegistryName().toString(), CAItems.ULTIMATE_PICKAXE.get()),
		ULTIMATE_AXE(CAItems.ULTIMATE_AXE.get().getRegistryName().toString(), CAItems.ULTIMATE_AXE.get()),
		ULTIMATE_SHOVEL(CAItems.ULTIMATE_SHOVEL.get().getRegistryName().toString(), CAItems.ULTIMATE_SHOVEL.get()),
		ULTIMATE_HOE(CAItems.ULTIMATE_HOE.get().getRegistryName().toString(), CAItems.ULTIMATE_HOE.get());
		
		CAGearEnumList(String itemName, Item rep) {
			this.repName = itemName;
			this.representation = rep;
		}

		private final String repName;
		private final Item representation;
		private static CAGearEnumList gearList;
		
		public static CAGearEnumList getEnum() {
			return gearList;
		}
		
		public void forEach() {
			getAllEntries().forEach((entry) -> {
				entry.getItemRepresentation();
			});
		}
		
		public EnumSet<CAGearEnumList> getAllEntries() {
			EnumSet<CAGearEnumList> set = EnumSet.of(AMETHYST_SWORD,
					 AMETHYST_PICKAXE,
					 AMETHYST_AXE,
					 AMETHYST_SHOVEL,
					 AMETHYST_HOE,
					 ATTITUDE_ADJUSTER,
					 BATTLE_AXE,
					 BIG_BERTHA,
					 BIG_HAMMER,
					 CATS_EYE_SWORD,
					 CATS_EYE_PICKAXE,
					 CATS_EYE_AXE,
					 CATS_EYE_SHOVEL,
					 CATS_EYE_HOE,
					 COPPER_SWORD,
					 COPPER_PICKAXE,
					 COPPER_AXE,
					 COPPER_SHOVEL,
					 COPPER_HOE,
					 CRYSTAL_WOOD_SWORD,
					 CRYSTAL_WOOD_PICKAXE,
					 CRYSTAL_WOOD_AXE,
				     CRYSTAL_WOOD_SHOVEL,
					 CRYSTAL_WOOD_HOE,
					 EMERALD_SWORD,
					 EMERALD_PICKAXE,
				     EMERALD_AXE,
					 EMERALD_SHOVEL,
					 EMERALD_HOE,
					 EXPERIENCE_SWORD,
				     FAIRY_SWORD,
					 KYANITE_SWORD,
				     KYANITE_PICKAXE,
					 KYANITE_AXE,
					 KYANITE_SHOVEL,
					 KYANITE_HOE,
					 MANTIS_CLAW,
					 NIGHTMARE_SWORD,
					 PINK_TOURMALINE_SWORD,
					 PINK_TOURMALINE_PICKAXE,
					 PINK_TOURMALINE_AXE,
				     PINK_TOURMALINE_SHOVEL,
					 PINK_TOURMALINE_HOE,
				     PLATINUM_SWORD,
					 PLATINUM_PICKAXE,
					 PLATINUM_AXE,
					 PLATINUM_SHOVEL,
					 PLATINUM_HOE,
					 POISON_SWORD,
					 PRISMATIC_REAPER,
					 QUEEN_SCALE_BATTLE_AXE,
				     RAT_SWORD,
					 ROYAL_GUARDIAN_SWORD,
					 RUBY_SWORD,
					 RUBY_PICKAXE,
					 RUBY_AXE,
					 RUBY_SHOVEL,
					 RUBY_HOE,
					 SILVER_SWORD,
					 SILVER_PICKAXE,
			         SILVER_AXE,
				     SILVER_SHOVEL,
					 SILVER_HOE,
					 SLAYER_CHAINSAW,
					 TIGERS_EYE_SWORD,
					 TIGERS_EYE_PICKAXE,
					 TIGERS_EYE_AXE,
				     TIGERS_EYE_SHOVEL,
					 TIGERS_EYE_HOE,
					 TIN_SWORD,
					 TIN_PICKAXE,
					 TIN_AXE,
					 TIN_SHOVEL,
					 TIN_HOE,
					 ULTIMATE_SWORD,
					 ULTIMATE_PICKAXE,
					 ULTIMATE_AXE,
					 ULTIMATE_SHOVEL,
					 ULTIMATE_HOE);
			return Optional.of(set).orElse(null);
		}
		
		public final String getItemRegistryName() {
			return repName;
		}
		
		public final Item getItemRepresentation() {
			return representation;
		}
	}
}
