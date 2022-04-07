package io.github.chaosawakens.common.registry;

import java.util.UUID;

import io.github.chaosawakens.ChaosAwakens;
import net.minecraft.entity.ai.attributes.Attribute;
import net.minecraft.entity.ai.attributes.RangedAttribute;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;

public class CAAttributes {
	public static final DeferredRegister<Attribute> ATTRIBUTES = DeferredRegister.create(ForgeRegistries.ATTRIBUTES, ChaosAwakens.MODID);
	
	//ATTRIBUTES
	public static final RegistryObject<Attribute> ATTACK_REACH = ATTRIBUTES.register("attack_reach", () -> new RangedAttribute("attribute.chaosawakens.attack_reach", 5.0D, 0, 100.0D));
	
	//MODIFIERS
	public static final UUID ATTACH_REACH_MODIFIER = UUID.fromString("7cc9a781-fdde-4e1b-85fe-acb912fc0430");
}
