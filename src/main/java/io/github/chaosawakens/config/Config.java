package io.github.chaosawakens.config;

import io.github.chaosawakens.ChaosAwakens;
import me.shedaniel.autoconfig.ConfigData;
import me.shedaniel.autoconfig.annotation.ConfigEntry;

@me.shedaniel.autoconfig.annotation.Config(name = ChaosAwakens.modId)
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

    public static class Ultimate {
        public float ultimateSwordDamage = 40.0f;
        public float ultimateAxeDamage = 42.0f;
        public float ultimatePickaxeDamage = 38.0f;
        public float ultimateShovelDamage = 38.5f;
        public float ultimateHoeDamage = 1.0f;
    }
    public static class Emerald {
        public float emeraldSwordDamage = 10.0f;
        public float emeraldAxeDamage = 12.0f;
        public float emeraldPickaxeDamage = 8.0f;
        public float emeraldShovelDamage = 8.5f;
        public float emeraldHoeDamage = 1.0f;
    }
    public static class Ruby {
        public float rubySwordDamage = 20.0f;
        public float rubyAxeDamage = 22.0f;
        public float rubyPickaxeDamage = 18.0f;
        public float rubyShovelDamage = 18.5f;
        public float rubyHoeDamage = 1.0f;
    }
    public static class Amethyst {
        public float amethystSwordDamage = 15.0f;
        public float amethystAxeDamage = 17.0f;
        public float amethystPickaxeDamage = 13.0f;
        public float amethystShovelDamage = 13.5f;
        public float amethystHoeDamage = 1.0f;
    }
    public static class TigersEye {
        public float tigersEyeSwordDamage = 12.0f;
        public float tigersEyeAxeDamage = 14.0f;
        public float tigersEyePickaxeDamage = 10.0f;
        public float tigersEyeShovelDamage = 10.5f;
        public float tigersEyeHoeDamage = 1.0f;
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


}
