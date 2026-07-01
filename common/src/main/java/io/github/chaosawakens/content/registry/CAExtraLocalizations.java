package io.github.chaosawakens.content.registry;


public class CAExtraLocalizations {
    public static final Localization SET_BONUS_LABEL = new Localization("set_bonus.label", "Set Bonus:");
    public static final Localization EXPERIENCE_BONUS_DESCRIPTION = new Localization("set_bonus.experience.description", "1.5x experience out of experience orbs");
    public static final Localization LAVA_EEL_BONUS_DESCRIPTION = new Localization("set_bonus.lava_eel.description", "Swim in lava");
    public static final Localization EMERALD_BONUS_DESCRIPTION = new Localization("set_bonus.emerald.description", "20% discount on villager trades");
    public static final Localization LAPIS_BONUS_DESCRIPTION = new Localization("set_bonus.lapis.description", "Enchantability increased by 5, discount on levels of 10%");

    public record Localization(String key, String value){}
}
