package io.github.chaosawakens.api.item;

import com.google.common.collect.Multimap;
import net.minecraft.entity.ai.attributes.Attribute;
import net.minecraft.entity.ai.attributes.AttributeModifier;
import net.minecraftforge.common.ForgeConfigSpec.IntValue;
import net.minecraftforge.common.util.Lazy;

import java.util.UUID;
import java.util.function.Supplier;

//TODO Supplier conversion (for config values, later)
public interface ICATieredItem {
	
	Supplier<IntValue> getActualAttackDamage();
	void setAttackDamage(Supplier<IntValue> attackDamage);
	
	float getAttackSpeed();
	void setAttackSpeed(float attackSpeed);
	
	double getReach();
	void setReach(double reach);
	
	double getAttackKnockback();
	void setAttackKnockback(double attackKnockback);
	
	void setAttributeModifiers(Lazy<? extends Multimap<Attribute, AttributeModifier>> attributeModMapLazy);
	
	static UUID getReachUUIDMod() {
		return UUID.fromString("1C0F03EC-EEB6-414A-8AC6-2A0913844821");
	}

	static UUID getKBUUIDMod() {
		return UUID.fromString("031FCABC-A15C-45C1-B799-5068DB1EAA98");
	}
}