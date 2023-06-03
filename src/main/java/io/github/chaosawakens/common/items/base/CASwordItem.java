package io.github.chaosawakens.common.items.base;

import java.util.function.Supplier;

import com.google.common.collect.ImmutableMultimap;
import com.google.common.collect.Multimap;

import io.github.chaosawakens.api.item.ICATieredItem;
import io.github.chaosawakens.common.util.EntityUtil;
import io.github.chaosawakens.common.util.EnumUtil.CAItemTier;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.ai.attributes.Attribute;
import net.minecraft.entity.ai.attributes.AttributeModifier;
import net.minecraft.entity.ai.attributes.Attributes;
import net.minecraft.inventory.EquipmentSlotType;
import net.minecraft.item.ItemStack;
import net.minecraft.item.SwordItem;
import net.minecraftforge.common.ForgeConfigSpec.IntValue;
import net.minecraftforge.common.ForgeMod;
import net.minecraftforge.common.util.Lazy;

public class CASwordItem extends SwordItem {
	private Supplier<IntValue> configDmg;
	private float attackSpeed;
	private double reach;
	private double attackKnockback;
	private Lazy<? extends Multimap<Attribute, AttributeModifier>> attributeModMapLazy = Lazy.of(() -> {
		ImmutableMultimap.Builder<Attribute, AttributeModifier> builder = ImmutableMultimap.builder();
		
		builder.put(Attributes.ATTACK_DAMAGE, new AttributeModifier(BASE_ATTACK_DAMAGE_UUID, "Weapon modifier", getDamage(), AttributeModifier.Operation.ADDITION));
		builder.put(Attributes.ATTACK_SPEED, new AttributeModifier(BASE_ATTACK_SPEED_UUID, "Weapon modifier", getAttackSpeed(), AttributeModifier.Operation.ADDITION));
		if (ForgeMod.REACH_DISTANCE.isPresent()) builder.put(ForgeMod.REACH_DISTANCE.get(), new AttributeModifier(ICATieredItem.getReachUUIDMod(), "Weapon modifier", getReach(), AttributeModifier.Operation.ADDITION));
		builder.put(Attributes.ATTACK_KNOCKBACK, new AttributeModifier(ICATieredItem.getKBUUIDMod(), "Weapon modifier", getAttackKnockback(), AttributeModifier.Operation.ADDITION));
		
		return builder.build();
	});

	public CASwordItem(CAItemTier pTier, Supplier<IntValue> configDmg, float pAttackSpeedModifier, double reach, Properties pProperties) {
		super(pTier, pTier.getAttackDamageMod(), pAttackSpeedModifier, pProperties);
		this.configDmg = configDmg;
		this.attackSpeed = pAttackSpeedModifier;
		this.reach = reach;
		this.attackKnockback = 0;
	}
	
	public CASwordItem(CAItemTier pTier, Supplier<IntValue> configDmg, float pAttackSpeedModifier, Double attackKnockback, Properties pProperties) {
		super(pTier, pTier.getAttackDamageMod(), pAttackSpeedModifier, pProperties);
		this.configDmg = configDmg;
		this.attackSpeed = pAttackSpeedModifier;
		this.reach = 0;
		this.attackKnockback = attackKnockback;
	}
	
	public CASwordItem(CAItemTier pTier, Supplier<IntValue> configDmg, double reach, double attackKnockback, Properties pProperties) {
		super(pTier, pTier.getAttackDamageMod(), -2.4F, pProperties);
		this.configDmg = configDmg;
		this.attackSpeed = -2.4F;
		this.reach = reach;
		this.attackKnockback = attackKnockback;
	}
	
	public CASwordItem(CAItemTier pTier, Supplier<IntValue> configDmg, float pAttackSpeedModifier, double reach, double attackKnockback, Properties pProperties) {
		super(pTier, pTier.getAttackDamageMod(), pAttackSpeedModifier, pProperties);
		this.configDmg = configDmg;
		this.attackSpeed = pAttackSpeedModifier;
		this.reach = reach;
		this.attackKnockback = attackKnockback;
	}
	
	public CASwordItem(CAItemTier pTier, Supplier<IntValue> configDmg, Properties pProperties) {
		super(pTier, pTier.getAttackDamageMod(), -2.4F, pProperties);
		this.configDmg = configDmg;
		this.attackSpeed = -2.4F;
		this.reach = 0;
		this.attackKnockback = 0;
	}
	
	@Override
	public Multimap<Attribute, AttributeModifier> getAttributeModifiers(EquipmentSlotType slot, ItemStack stack) {
		return slot.equals(EquipmentSlotType.MAINHAND) ? attributeModMapLazy.get() : super.getAttributeModifiers(slot, stack);
	}
	
	@Override
	public float getDamage() {
		return configDmg.get().get();
	}
	
	public float getAttackSpeed() {
		return attackSpeed;
	}
	
	public double getReach() {
		return reach;
	}
	
	public double getAttackKnockback() {
		return attackKnockback;
	}
	
	@Override
	public boolean onEntitySwing(ItemStack stack, LivingEntity entity) {
		EntityUtil.applyReachModifierToEntity(entity, stack, getDamage());
		return super.onEntitySwing(stack, entity);
	}
}
