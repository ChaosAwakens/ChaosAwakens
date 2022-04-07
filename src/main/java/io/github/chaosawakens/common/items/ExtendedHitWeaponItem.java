package io.github.chaosawakens.common.items;

import com.google.common.collect.ImmutableMultimap;

import com.google.common.collect.Multimap;

import io.github.chaosawakens.api.IUtilityHelper;
import io.github.chaosawakens.common.registry.CAItems;
import net.minecraft.enchantment.IVanishable;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.ai.attributes.Attribute;
import net.minecraft.entity.ai.attributes.AttributeModifier;
import net.minecraft.entity.ai.attributes.Attributes;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.inventory.EquipmentSlotType;
import net.minecraft.item.IItemTier;
import net.minecraft.item.ItemStack;
import net.minecraft.item.SwordItem;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.world.World;
import net.minecraftforge.common.ForgeMod;
import software.bernie.geckolib3.core.IAnimatable;
import software.bernie.geckolib3.core.manager.AnimationData;
import software.bernie.geckolib3.core.manager.AnimationFactory;

import java.util.UUID;

public class ExtendedHitWeaponItem extends SwordItem implements IVanishable, IAnimatable, IUtilityHelper {
    protected static final UUID ATTACK_KNOCKBACK_MODIFIER = UUID.fromString("C59EC38E-DC43-11EB-BA80-0242AC130004");
    public static final UUID REACH_MODIFIER = UUID.fromString("2F05D864-7945-11EC-90D6-0242AC120003");
    private final Multimap<Attribute, AttributeModifier> attributeModifiers;
    public AnimationFactory factory = new AnimationFactory(this);

    public ExtendedHitWeaponItem(IItemTier tier, int attackDamageIn, float attackSpeedIn, double reachDistance, double knockBack, Properties builderIn) {
        super(tier, attackDamageIn, attackSpeedIn, builderIn);
        float attackDamage = (float) attackDamageIn + tier.getAttackDamageBonus();
        ImmutableMultimap.Builder<Attribute, AttributeModifier> builder = ImmutableMultimap.builder();
        builder.put(Attributes.ATTACK_DAMAGE, new AttributeModifier(BASE_ATTACK_DAMAGE_UUID, "Weapon modifier", attackDamage, AttributeModifier.Operation.ADDITION));
        builder.put(Attributes.ATTACK_SPEED, new AttributeModifier(BASE_ATTACK_SPEED_UUID, "Weapon modifier", attackSpeedIn, AttributeModifier.Operation.ADDITION));

        ForgeMod.getInstance();
		if (ForgeMod.REACH_DISTANCE.isPresent()) {
        	builder.put(ForgeMod.REACH_DISTANCE.get(), new AttributeModifier(REACH_MODIFIER, "Weapon modifier", reachDistance, AttributeModifier.Operation.ADDITION));
        }  

        builder.put(Attributes.ATTACK_KNOCKBACK, new AttributeModifier(ATTACK_KNOCKBACK_MODIFIER, "Weapon modifier", knockBack, AttributeModifier.Operation.ADDITION));
        this.attributeModifiers = builder.build();
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
    public Multimap<Attribute, AttributeModifier> getDefaultAttributeModifiers(EquipmentSlotType equipmentSlot) {
        return equipmentSlot == EquipmentSlotType.MAINHAND ? this.attributeModifiers : super.getDefaultAttributeModifiers(equipmentSlot);
    }

    @Override
    public boolean canDisableShield(ItemStack stack, ItemStack shield, LivingEntity entity, LivingEntity attacker) {
        return true;
    }
    
    @Override
    public boolean onEntitySwing(ItemStack stack, LivingEntity entity) {
    	ItemStack a = new ItemStack(CAItems.ATTITUDE_ADJUSTER.get());
    	ItemStack b = new ItemStack(CAItems.BIG_BERTHA.get());
    	if (entity.getItemInHand(Hand.MAIN_HAND).equals(b)) {
    	//	this.getAttributeForgeModModifiers(equipmentSlot);
    		entity.getAttribute(ForgeMod.REACH_DISTANCE.get()).setBaseValue(12.0D);
    	} else if (entity.getItemInHand(Hand.MAIN_HAND).equals(a)) {
    		entity.getAttribute(ForgeMod.REACH_DISTANCE.get()).setBaseValue(7.0D);
    	}
    	return false;
    }
    
    @Override
    public ActionResult<ItemStack> use(World p_77659_1_, PlayerEntity p_77659_2_, Hand p_77659_3_) {
    	this.setReach(p_77659_2_, 10, new ItemStack(CAItems.BIG_BERTHA.get()));
    	return super.use(p_77659_1_, p_77659_2_, p_77659_3_);
    }

	@Override
	public void setReach(PlayerEntity player, int newReachValue, ItemStack stack) {
		stack = new ItemStack(CAItems.BIG_BERTHA.get());
		newReachValue = 10;
		
		if (player.getItemInHand(Hand.MAIN_HAND) == stack) {
			player.getAttribute(ForgeMod.REACH_DISTANCE.get()).setBaseValue(newReachValue);
		}
	}
}