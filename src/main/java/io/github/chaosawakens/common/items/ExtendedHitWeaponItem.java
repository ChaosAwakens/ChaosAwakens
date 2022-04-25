package io.github.chaosawakens.common.items;

import com.google.common.collect.ArrayListMultimap;

import com.google.common.collect.ImmutableMultimap;

import com.google.common.collect.Multimap;

import io.github.chaosawakens.api.IUtilityHelper;
import net.minecraft.enchantment.IVanishable;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.ai.attributes.Attribute;
import net.minecraft.entity.ai.attributes.AttributeModifier;
import net.minecraft.entity.ai.attributes.Attributes;
import net.minecraft.inventory.EquipmentSlotType;
import net.minecraft.item.IItemTier;
import net.minecraft.item.ItemStack;
import net.minecraft.item.SwordItem;
import net.minecraftforge.common.ForgeMod;
import net.minecraftforge.common.util.Lazy;
import software.bernie.geckolib3.core.IAnimatable;
import software.bernie.geckolib3.core.manager.AnimationData;
import software.bernie.geckolib3.core.manager.AnimationFactory;
import java.util.UUID;

public class ExtendedHitWeaponItem extends SwordItem implements IVanishable, IAnimatable, IUtilityHelper {
	public static final UUID ATTACK_KNOCKBACK_MODIFIER = UUID.fromString("C59EC38E-DC43-11EB-BA80-0242AC130004");
	public static final UUID ATTACK_REACH_MODIFIER = UUID.fromString("532618EB-72B6-4C63-AFE5-F407227FDFB1");
	public AnimationFactory factory = new AnimationFactory(this);
	// public static float damage;
//    public static float speed;
	public static double reach;
//    public static double kb;
	private static Multimap<Attribute, AttributeModifier> attributeModifiersDefault;
	public static Lazy<Multimap<Attribute, AttributeModifier>> LAZY = Lazy.of(() -> {
		ImmutableMultimap.Builder<Attribute, AttributeModifier> builder = ImmutableMultimap.builder();
		// No, adding other attributes here will NOT fix configs. Make a new Lazy or
		// something, I myself am too "lazy" to do it --Meme Man
		if (ForgeMod.REACH_DISTANCE.isPresent()) {
			builder.put(ForgeMod.REACH_DISTANCE.get(), new AttributeModifier(ATTACK_REACH_MODIFIER, "Weapon modifier",
					reach, AttributeModifier.Operation.ADDITION));
		}
		Multimap<Attribute, AttributeModifier> attributeModifiers = ArrayListMultimap.create();
		attributeModifiers = builder.build();
		return attributeModifiers;
	});

	public ExtendedHitWeaponItem(IItemTier tier, int attackDamageIn, float attackSpeedIn, double reachDistance,
			double knockBack, Properties builderIn) {
		super(tier, attackDamageIn, attackSpeedIn, builderIn);
		float attackDamage = (float) attackDamageIn + tier.getAttackDamageBonus();
		ImmutableMultimap.Builder<Attribute, AttributeModifier> builder = ImmutableMultimap.builder();
		builder.put(Attributes.ATTACK_DAMAGE, new AttributeModifier(BASE_ATTACK_DAMAGE_UUID, "Weapon modifier",
				attackDamage, AttributeModifier.Operation.ADDITION));
		builder.put(Attributes.ATTACK_SPEED, new AttributeModifier(BASE_ATTACK_SPEED_UUID, "Weapon modifier",
				attackSpeedIn, AttributeModifier.Operation.ADDITION));
		builder.put(Attributes.ATTACK_KNOCKBACK, new AttributeModifier(ATTACK_KNOCKBACK_MODIFIER, "Weapon modifier",
				knockBack, AttributeModifier.Operation.ADDITION));
		if (ForgeMod.REACH_DISTANCE.isPresent()) {
			builder.put(ForgeMod.REACH_DISTANCE.get(), new AttributeModifier(ATTACK_REACH_MODIFIER, "Weapon modifier",
					reach, AttributeModifier.Operation.ADDITION));
		}
		reach = reachDistance;
		attributeModifiersDefault = builder.build();
	}

	@Override
	public void registerControllers(AnimationData data) {
		// insert controllers here
	}

	@Override
	public AnimationFactory getFactory() {
		return this.factory;
	}

	@Override
	public boolean canDisableShield(ItemStack stack, ItemStack shield, LivingEntity entity, LivingEntity attacker) {
		return true;
	}

	// DO NOT UN-COMMENT!! (for now)
	/*
	 * @Override public Multimap<Attribute, AttributeModifier>
	 * getAttributeModifiers(EquipmentSlotType slot, ItemStack stack) { return slot
	 * == EquipmentSlotType.MAINHAND ? LAZY.get() :
	 * super.getAttributeModifiers(slot, stack); }
	 */

	@Override
	public Multimap<Attribute, AttributeModifier> getDefaultAttributeModifiers(EquipmentSlotType slot) {
		return slot == EquipmentSlotType.MAINHAND ? attributeModifiersDefault
				: super.getDefaultAttributeModifiers(slot);
	}
}