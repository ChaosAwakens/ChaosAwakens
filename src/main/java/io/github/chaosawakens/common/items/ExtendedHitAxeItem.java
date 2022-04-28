package io.github.chaosawakens.common.items;

import com.google.common.collect.ImmutableMultimap;
import com.google.common.collect.Multimap;
import io.github.chaosawakens.api.IUtilityHelper;
import net.minecraft.enchantment.IVanishable;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.ai.attributes.Attribute;
import net.minecraft.entity.ai.attributes.AttributeModifier;
import net.minecraft.entity.ai.attributes.Attributes;
import net.minecraft.inventory.EquipmentSlotType;
import net.minecraft.item.AxeItem;
import net.minecraft.item.IItemTier;
import net.minecraft.item.ItemStack;
import software.bernie.geckolib3.core.IAnimatable;
import software.bernie.geckolib3.core.manager.AnimationData;
import software.bernie.geckolib3.core.manager.AnimationFactory;

import java.util.UUID;

public class ExtendedHitAxeItem extends AxeItem implements IVanishable, IAnimatable, IUtilityHelper {
	public static final UUID ATTACK_KNOCKBACK_MODIFIER = UUID.fromString("C59EC38E-DC43-11EB-BA80-0242AC130004");
	public AnimationFactory factory = new AnimationFactory(this);
	private final Multimap<Attribute, AttributeModifier> attributeModifiersDefault;

	public ExtendedHitAxeItem(IItemTier tier, int attackDamageIn, float attackSpeedIn, double reachDistance, double knockBack, Properties builderIn) {
		super(tier, attackDamageIn, attackSpeedIn, builderIn);
		float attackDamage = (float) attackDamageIn + tier.getAttackDamageBonus();
		ImmutableMultimap.Builder<Attribute, AttributeModifier> builder = ImmutableMultimap.builder();
		builder.put(Attributes.ATTACK_DAMAGE, new AttributeModifier(BASE_ATTACK_DAMAGE_UUID, "Weapon modifier", attackDamage, AttributeModifier.Operation.ADDITION));
		builder.put(Attributes.ATTACK_SPEED, new AttributeModifier(BASE_ATTACK_SPEED_UUID, "Weapon modifier", attackSpeedIn, AttributeModifier.Operation.ADDITION));
		builder.put(Attributes.ATTACK_KNOCKBACK, new AttributeModifier(ATTACK_KNOCKBACK_MODIFIER, "Weapon modifier", knockBack, AttributeModifier.Operation.ADDITION));
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

	@Override
	public Multimap<Attribute, AttributeModifier> getDefaultAttributeModifiers(EquipmentSlotType slot) {
		return slot == EquipmentSlotType.MAINHAND ? attributeModifiersDefault : super.getDefaultAttributeModifiers(slot);
	}
}
