package io.github.chaosawakens.common.items;

import com.google.common.collect.ImmutableMultimap;
import com.google.common.collect.Multimap;
import net.minecraft.enchantment.IVanishable;
import net.minecraft.entity.ai.attributes.Attribute;
import net.minecraft.entity.ai.attributes.AttributeModifier;
import net.minecraft.entity.ai.attributes.Attributes;
import net.minecraft.inventory.EquipmentSlotType;
import net.minecraft.item.*;
import software.bernie.geckolib3.core.*;
import software.bernie.geckolib3.core.manager.*;

public class ExtendedHitWeaponItem extends SwordItem implements IVanishable, IAnimatable {

	public AnimationFactory factory = new AnimationFactory(this);
	private final Multimap<Attribute, AttributeModifier> attributeModifiers;

	public ExtendedHitWeaponItem(IItemTier tier, int attackDamageIn, float attackSpeedIn, float attackRangeIn, Properties builderIn) {
		super(tier, attackDamageIn, attackSpeedIn, builderIn);
		float attackDamage = (float) attackDamageIn + tier.getAttackDamage();
		ImmutableMultimap.Builder<Attribute, AttributeModifier> builder = ImmutableMultimap.builder();
		builder.put(Attributes.ATTACK_DAMAGE, new AttributeModifier(ATTACK_DAMAGE_MODIFIER, "Weapon modifier", attackDamage, AttributeModifier.Operation.ADDITION));
		builder.put(Attributes.ATTACK_SPEED, new AttributeModifier(ATTACK_SPEED_MODIFIER, "Weapon modifier", attackSpeedIn, AttributeModifier.Operation.ADDITION));
		this.attributeModifiers = builder.build();
	}

	@Override
	public void registerControllers(AnimationData data) {
		// insert controllers here
	}

	@Override
	public AnimationFactory getFactory()
	{
		return this.factory;
	}

	public Multimap<Attribute, AttributeModifier> getAttributeModifiers(EquipmentSlotType equipmentSlot) {
		return equipmentSlot == EquipmentSlotType.MAINHAND ? this.attributeModifiers : super.getAttributeModifiers(equipmentSlot);
	}
}