package io.github.chaosawakens.content.item.equipment.big_wepons;

import com.google.common.collect.ImmutableMultimap;
import com.google.common.collect.Multimap;
import com.mememan.nexus.template.object.attribute.NexusAttributes;
import io.github.chaosawakens.content.item.misc.EnchantedSwordItem;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.ai.attributes.Attribute;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.SwordItem;
import net.minecraft.world.item.Tier;

import static io.github.chaosawakens.content.item.misc.EnchantedSwordItem.ATTACK_RANGE_UUID;

public class RoyalGuardian extends SwordItem {
    public static final double ATTACK_RANGE = 5;      //# of blocks
    public static final int ATTACK_DAMAGE = 749;

    public RoyalGuardian(Tier tier, int attackDamage, float attackSpeedModifier, Item.Properties properties) {
        super(tier, attackDamage, attackSpeedModifier, properties);
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
