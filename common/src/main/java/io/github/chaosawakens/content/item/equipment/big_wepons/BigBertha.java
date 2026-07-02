package io.github.chaosawakens.content.item.equipment.big_wepons;

import com.google.common.collect.ImmutableMultimap;
import com.google.common.collect.Multimap;
import com.mememan.nexus.template.object.attribute.NexusAttributes;
import io.github.chaosawakens.content.item.misc.EnchantedSwordItem;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.ai.attributes.Attribute;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Tier;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraft.world.item.enchantment.EnchantmentHelper;
import net.minecraft.world.item.enchantment.Enchantments;
import net.minecraft.world.level.Level;

import java.util.Map;

public class BigBertha extends EnchantedSwordItem {
    public static final double ATTACK_RANGE = 5;      //# of blocks
    public static final int ATTACK_DAMAGE = 499;   //Additional DMG

    public BigBertha(Tier tier, int attackDamage, float attackSpeedModifier, Item.Properties properties, Map<Enchantment, Integer> enchantment, double attackRangeModifier){
        super(tier, attackDamage, attackSpeedModifier, properties, enchantment);
    }

    @Override
    public void onCraftedBy(ItemStack stack, Level world, Player player) {
        super.onCraftedBy(stack, world, player);
        if (world.isClientSide) return;
        if (!EnchantmentHelper.getEnchantments(stack).isEmpty()) return;
        stack.enchant(Enchantments.BANE_OF_ARTHROPODS, 3);
        stack.enchant(Enchantments.FIRE_ASPECT, 2);
        stack.enchant(Enchantments.KNOCKBACK, 2);
    }

    @Override
    public Multimap<Attribute, AttributeModifier> getDefaultAttributeModifiers(EquipmentSlot slot) {
        if (slot == EquipmentSlot.MAINHAND) {
            ImmutableMultimap.Builder<Attribute, AttributeModifier> builder = ImmutableMultimap.builder();
            builder.putAll(super.getDefaultAttributeModifiers(slot));
            builder.put(NexusAttributes.ENTITY_REACH.get(), new AttributeModifier(ATTACK_RANGE_UUID, "Weapon modifier", ATTACK_RANGE, AttributeModifier.Operation.ADDITION));
            return builder.build();
        }
        return super.getDefaultAttributeModifiers(slot);
    }
}
