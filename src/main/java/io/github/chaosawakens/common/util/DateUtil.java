package io.github.chaosawakens.common.util;

import java.time.LocalDate;
import java.time.temporal.ChronoField;

import io.github.chaosawakens.manager.CAConfigManager;
import net.minecraft.entity.Entity;

public final class DateUtil {
	
	private DateUtil() {
		throw new IllegalAccessError("Attempted to instantiate a Utility Class!");
	}
	
	public static boolean isHalloween() {
		LocalDate curDate = LocalDate.now();
		int curMonth = curDate.get(ChronoField.MONTH_OF_YEAR);
		return curMonth == 10;
	}
	
	public static boolean isChristmas() {
		LocalDate curDate = LocalDate.now();
		int curMonth = curDate.get(ChronoField.MONTH_OF_YEAR);
		return curMonth == 12;
	}
	
	public static boolean canApplyHolidayTextures(Entity targetEntity, float chance, int holidayMonth) {
		if (holidayMonth > 12) return false;
		LocalDate curDate = LocalDate.now();
		int curMonth = curDate.get(ChronoField.MONTH_OF_YEAR);
		return targetEntity.level.random.nextFloat() < chance && curMonth == holidayMonth && CAConfigManager.MAIN_COMMON.holidayTextures.get(); 
	}
	
	public static boolean canApplyHalloweenTextures(Entity targetEntity, float chance) {
		return canApplyHolidayTextures(targetEntity, chance, 10);
	}
	
	public static boolean canApplyChristmasTextures(Entity targetEntity, float chance) {
		return canApplyHolidayTextures(targetEntity, chance, 12);
	}
}
