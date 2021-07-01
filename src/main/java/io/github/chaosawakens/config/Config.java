package io.github.chaosawakens.config;

import io.github.chaosawakens.ChaosAwakens;
import me.shedaniel.autoconfig.ConfigData;
import me.shedaniel.autoconfig.annotation.ConfigEntry;

@me.shedaniel.autoconfig.annotation.Config(name = ChaosAwakens.modID)
public class Config implements ConfigData {
    @ConfigEntry.Category("itemstats")
    @ConfigEntry.Gui.PrefixText
    @ConfigEntry.Gui.CollapsibleObject
    public Ultimate ultimate = new Ultimate();

    @ConfigEntry.Category("itemstats")
    @ConfigEntry.Gui.CollapsibleObject
    public Emerald emerald = new Emerald();

    @ConfigEntry.Category("itemstats")
    @ConfigEntry.Gui.CollapsibleObject
    public Ruby ruby = new Ruby();

    @ConfigEntry.Category("itemstats")
    @ConfigEntry.Gui.CollapsibleObject
    public Amethyst amethyst = new Amethyst();

    @ConfigEntry.Category("itemstats")
    @ConfigEntry.Gui.CollapsibleObject
    public TigersEye tigersEye = new TigersEye();

    @ConfigEntry.Category("itemstats")
    @ConfigEntry.Gui.CollapsibleObject
    public Misc misc = new Misc();

    public static class Ultimate extends Default {
        public float swordDamage = 40.0f;
        public float axeDamage = 42.0f;
        public float pickaxeDamage = 38.0f;
        public float shovelDamage = 38.5f;
    }
    public static class Emerald extends Default {
        public float swordDamage = 10.0f;
        public float axeDamage = 12.0f;
        public float pickaxeDamage = 8.0f;
        public float shovelDamage = 8.5f;
    }
    public static class Ruby extends Default {
        public float swordDamage = 20.0f;
        public float axeDamage = 22.0f;
        public float pickaxeDamage = 18.0f;
        public float shovelDamage = 18.5f;
    }
    public static class Amethyst extends Default {
        public float swordDamage = 15.0f;
        public float axeDamage = 17.0f;
        public float pickaxeDamage = 13.0f;
        public float shovelDamage = 13.5f;
    }
    public static class TigersEye extends Default {
        public float swordDamage = 12.0f;
        public float axeDamage = 14.0f;
        public float pickaxeDamage = 10.0f;
        public float shovelDamage = 10.5f;
    }
    public static class Misc {
        public float prismaticReaperDamage = 29.0f;
        public float fairySwordDamage = 10.0f;
        public float nightmareSwordDamage = 10.0f;
        public float poisonSwordDamage = 10.0f;
        public float ratSwordDamage = 10.0f;
        public float bigHammerDamage = 10.0f;
        public float experienceSwordDamage = 10.0f;
    }

    public static class Default {
        public float swordDamage = 10.0f;
        public float swordAttackSpeed = -2.4f;
        public float axeDamage = 12.0f;
        public float axeAttackSpeed = -3.0f;
        public float pickaxeDamage = 8.0f;
        public float pickaxeAttackSpeed = -2.8f;
        public float shovelDamage = 8.5f;
        public float shovelAttackSpeed = -3.0f;
        public float hoeDamage = 1.0f;
        public float hoeAttackSpeed = 0;
    }
}
