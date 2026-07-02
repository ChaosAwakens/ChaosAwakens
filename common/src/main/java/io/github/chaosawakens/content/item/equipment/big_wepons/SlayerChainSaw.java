package io.github.chaosawakens.content.item.equipment.big_wepons;

import com.google.common.collect.ImmutableMultimap;
import com.google.common.collect.Multimap;
import com.mememan.nexus.template.object.attribute.NexusAttributes;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.ai.attributes.Attribute;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.item.*;

import static io.github.chaosawakens.content.item.misc.EnchantedSwordItem.ATTACK_RANGE_UUID;

public class SlayerChainSaw extends AxeItem {
    public static final double ATTACK_RANGE = 2;      //# of blocks
    public static final int ATTACK_DAMAGE = 49;

    public SlayerChainSaw(Tier tier, int attackDamage, float attackSpeedModifier, Item.Properties properties) {
        super(tier, attackDamage, attackSpeedModifier, properties);
    }

    @Override
    public boolean isFoil(ItemStack stack) {
        return true;
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

    /*
    public class Behavior {
        public static chainMining() {
            // TODO
            // give item properties of axe mineabels and mining speed
            // adjacent wood-type-family blocks breaks along with the directed block
        }

        // also another method for overiding the mining animation
        public static miningAnimationOverride() {
            // TODO
            // apply animation clip with animation controller
            // apply sound event while mining
        }
    }
     */
}
