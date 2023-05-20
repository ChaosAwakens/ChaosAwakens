package io.github.chaosawakens.common.util;

import java.time.LocalDate;
import java.time.temporal.ChronoField;

import io.github.chaosawakens.manager.CAConfigManager;
import net.minecraft.entity.Entity;

public class DateUtil {
	
	private DateUtil() {
		throw new IllegalAccessError("Utility Class");
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
	
	public static boolean applyHolidayTextures(Entity targetEntity, float chance, int holidayMonth) {
		if (holidayMonth > 12) return false;
		LocalDate curDate = LocalDate.now();
		int curMonth = curDate.get(ChronoField.MONTH_OF_YEAR);
		return targetEntity.level.random.nextFloat() < chance && curMonth == holidayMonth && CAConfigManager.MAIN_COMMON.holidayTextures.get(); 
	}
	
	public static boolean applyHalloweenTextures(Entity targetEntity, float chance) {
		return applyHolidayTextures(targetEntity, chance, 10);
	}
	
	public static boolean applyChristmasTextures(Entity targetEntity, float chance) {
		return applyHolidayTextures(targetEntity, chance, 12);
	}

}
