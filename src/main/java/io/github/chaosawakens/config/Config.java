package io.github.chaosawakens.config;

import io.github.chaosawakens.ChaosAwakens;
import me.shedaniel.autoconfig.ConfigData;
import me.shedaniel.autoconfig.annotation.ConfigEntry;

@me.shedaniel.autoconfig.annotation.Config(name = ChaosAwakens.modID)
public class Config implements ConfigData {
    @ConfigEntry.Category("itemstats")
    @ConfigEntry.Gui.PrefixText
    @ConfigEntry.Gui.Tooltip
    @ConfigEntry.Gui.CollapsibleObject
    public Ultimate ultimate = new Ultimate();

    @ConfigEntry.Gui.Tooltip
    @ConfigEntry.Category("itemstats")
    @ConfigEntry.Gui.CollapsibleObject
    public Emerald emerald = new Emerald();

    @ConfigEntry.Gui.Tooltip
    @ConfigEntry.Category("itemstats")
    @ConfigEntry.Gui.CollapsibleObject
    public Ruby ruby = new Ruby();

    @ConfigEntry.Gui.Tooltip
    @ConfigEntry.Category("itemstats")
    @ConfigEntry.Gui.CollapsibleObject
    public Amethyst amethyst = new Amethyst();

    @ConfigEntry.Gui.Tooltip
    @ConfigEntry.Category("itemstats")
    @ConfigEntry.Gui.CollapsibleObject
    public TigersEye tigersEye = new TigersEye();

    @ConfigEntry.Gui.Tooltip
    @ConfigEntry.Category("itemstats")
    @ConfigEntry.Gui.CollapsibleObject
    public Copper copper = new Copper();

    @ConfigEntry.Gui.Tooltip
    @ConfigEntry.Category("itemstats")
    @ConfigEntry.Gui.CollapsibleObject
    public Tin tin = new Tin();

    @ConfigEntry.Gui.Tooltip
    @ConfigEntry.Category("itemstats")
    @ConfigEntry.Gui.CollapsibleObject
    public Silver silver = new Silver();

    @ConfigEntry.Gui.Tooltip
    @ConfigEntry.Category("itemstats")
    @ConfigEntry.Gui.CollapsibleObject
    public Platinum platinum = new Platinum();

    @ConfigEntry.Gui.Tooltip
    @ConfigEntry.Category("itemstats")
    @ConfigEntry.Gui.CollapsibleObject
    public CrystalWood crystalWood = new CrystalWood();

    @ConfigEntry.Gui.Tooltip
    @ConfigEntry.Category("itemstats")
    @ConfigEntry.Gui.CollapsibleObject
    public Kyanite kyanite = new Kyanite();

    @ConfigEntry.Gui.Tooltip
    @ConfigEntry.Category("itemstats")
    @ConfigEntry.Gui.CollapsibleObject
    public CatsEye catsEye = new CatsEye();

    @ConfigEntry.Gui.Tooltip
    @ConfigEntry.Category("itemstats")
    @ConfigEntry.Gui.CollapsibleObject
    public PinkTourmaline pinkTourmaline = new PinkTourmaline();

    @ConfigEntry.Gui.Tooltip
    @ConfigEntry.Category("itemstats")
    @ConfigEntry.Gui.CollapsibleObject
    public MiscWeapons miscWeapons = new MiscWeapons();

    @ConfigEntry.Gui.PrefixText
    @ConfigEntry.Gui.Tooltip
    @ConfigEntry.Category("functionality")
    @ConfigEntry.Gui.TransitiveObject
    public ThunderStaff thunderStaff = new ThunderStaff();

    @ConfigEntry.Gui.Tooltip
    @ConfigEntry.Category("functionality")
    @ConfigEntry.Gui.TransitiveObject
    public RayGun rayGun = new RayGun();

    @ConfigEntry.Gui.PrefixText
    @ConfigEntry.Gui.Tooltip
    @ConfigEntry.Category("dimensionworldgen")
    @ConfigEntry.Gui.CollapsibleObject
    public Ant ant = new Ant();

    @ConfigEntry.Gui.Tooltip
    @ConfigEntry.Category("dimensionworldgen")
    @ConfigEntry.Gui.CollapsibleObject
    public WorldGen worldGen = new WorldGen();

    @ConfigEntry.Gui.Tooltip
    @ConfigEntry.Category("dimensionworldgen")
    @ConfigEntry.Gui.CollapsibleObject
    public WorldGenOres worldGenOres = new WorldGenOres();

    @ConfigEntry.Gui.PrefixText
    @ConfigEntry.Gui.Tooltip
    @ConfigEntry.Category("misc")
    @ConfigEntry.Gui.TransitiveObject
    public Misc misc = new Misc();

    @ConfigEntry.Category("misc")
    @ConfigEntry.Gui.Excluded
    public Default aDefault = new Default();

    public static class Ultimate extends Default {
        @ConfigEntry.Gui.Tooltip
        public float swordDamage = 40.0f;
        @ConfigEntry.Gui.Tooltip
        public float axeDamage = 42.0f;
        @ConfigEntry.Gui.Tooltip
        public float pickaxeDamage = 38.0f;
        @ConfigEntry.Gui.Tooltip
        public float shovelDamage = 39.0f;
    }
    public static class Emerald extends Default {
        @ConfigEntry.Gui.Tooltip
        public float swordDamage = 10.0f;
        @ConfigEntry.Gui.Tooltip
        public float axeDamage = 12.0f;
        @ConfigEntry.Gui.Tooltip
        public float pickaxeDamage = 8.0f;
        @ConfigEntry.Gui.Tooltip
        public float shovelDamage = 9.0f;
    }
    public static class Ruby extends Default {
        @ConfigEntry.Gui.Tooltip
        public float swordDamage = 20.0f;
        @ConfigEntry.Gui.Tooltip
        public float axeDamage = 22.0f;
        @ConfigEntry.Gui.Tooltip
        public float pickaxeDamage = 18.0f;
        @ConfigEntry.Gui.Tooltip
        public float shovelDamage = 19.0f;
    }
    public static class Amethyst extends Default {
        @ConfigEntry.Gui.Tooltip
        public float swordDamage = 15.0f;
        @ConfigEntry.Gui.Tooltip
        public float axeDamage = 17.0f;
        @ConfigEntry.Gui.Tooltip
        public float pickaxeDamage = 13.0f;
        @ConfigEntry.Gui.Tooltip
        public float shovelDamage = 14.0f;
    }
    public static class TigersEye extends Default {
        @ConfigEntry.Gui.Tooltip
        public float swordDamage = 12.0f;
        @ConfigEntry.Gui.Tooltip
        public float axeDamage = 14.0f;
        @ConfigEntry.Gui.Tooltip
        public float pickaxeDamage = 10.0f;
        @ConfigEntry.Gui.Tooltip
        public float shovelDamage = 11.0f;
    }
    public static class Copper extends Default {
        @ConfigEntry.Gui.Tooltip
        public float swordDamage = 5.0f;
        @ConfigEntry.Gui.Tooltip
        public float axeDamage = 9.0f;
        @ConfigEntry.Gui.Tooltip
        public float pickaxeDamage = 3.0f;
        @ConfigEntry.Gui.Tooltip
        public float shovelDamage = 4.0f;
    }
    public static class Tin extends Default {
        @ConfigEntry.Gui.Tooltip
        public float swordDamage = 6.0f;
        @ConfigEntry.Gui.Tooltip
        public float axeDamage = 9.0f;
        @ConfigEntry.Gui.Tooltip
        public float pickaxeDamage = 4.0f;
        @ConfigEntry.Gui.Tooltip
        public float shovelDamage = 4.0f;
    }
    public static class Silver extends Default {
        @ConfigEntry.Gui.Tooltip
        public float swordDamage = 6.0f;
        @ConfigEntry.Gui.Tooltip
        public float axeDamage = 9.0f;
        @ConfigEntry.Gui.Tooltip
        public float pickaxeDamage = 4.0f;
        @ConfigEntry.Gui.Tooltip
        public float shovelDamage = 5.0f;
    }
    public static class Platinum extends Default {
        @ConfigEntry.Gui.Tooltip
        public float swordDamage = 10.0f;
        @ConfigEntry.Gui.Tooltip
        public float axeDamage = 12.0f;
        @ConfigEntry.Gui.Tooltip
        public float pickaxeDamage = 8.0f;
        @ConfigEntry.Gui.Tooltip
        public float shovelDamage = 9.0f;
    }
    public static class CrystalWood extends Default {
        @ConfigEntry.Gui.Tooltip
        public float swordDamage = 5.0f;
        @ConfigEntry.Gui.Tooltip
        public float axeDamage = 9.0f;
        @ConfigEntry.Gui.Tooltip
        public float pickaxeDamage = 3.0f;
        @ConfigEntry.Gui.Tooltip
        public float shovelDamage = 3.0f;
    }
    public static class Kyanite extends Default {
        @ConfigEntry.Gui.Tooltip
        public float swordDamage = 6.0f;
        @ConfigEntry.Gui.Tooltip
        public float axeDamage = 9.0f;
        @ConfigEntry.Gui.Tooltip
        public float pickaxeDamage = 4.0f;
        @ConfigEntry.Gui.Tooltip
        public float shovelDamage = 4.0f;
    }
    public static class CatsEye extends Default {
        @ConfigEntry.Gui.Tooltip
        public float swordDamage = 12.0f;
        @ConfigEntry.Gui.Tooltip
        public float axeDamage = 14.0f;
        @ConfigEntry.Gui.Tooltip
        public float pickaxeDamage = 10.0f;
        @ConfigEntry.Gui.Tooltip
        public float shovelDamage = 11.0f;
    }
    public static class PinkTourmaline extends Default {
        @ConfigEntry.Gui.Tooltip
        public float swordDamage = 11.0f;
        @ConfigEntry.Gui.Tooltip
        public float axeDamage = 13.0f;
        @ConfigEntry.Gui.Tooltip
        public float pickaxeDamage = 9.0f;
        @ConfigEntry.Gui.Tooltip
        public float shovelDamage = 10.0f;
    }
    public static class MiscWeapons extends Default {
        @ConfigEntry.Gui.Tooltip
        public float nightmareSwordDamage = 30.0f;
        @ConfigEntry.Gui.Tooltip
        public float experienceSwordDamage = 10.0f;
        @ConfigEntry.Gui.Tooltip
        public float poisonSwordDamage = 10.0f;
        @ConfigEntry.Gui.Tooltip
        public float ratSwordDamage = 10.0f;
        @ConfigEntry.Gui.Tooltip
        public float fairySwordDamage = 10.0f;
        @ConfigEntry.Gui.Tooltip
        public float mantisClawDamage = 10.0f;
        @ConfigEntry.Gui.Tooltip
        public float bigHammerDamage = 15.0f;
        @ConfigEntry.Gui.Tooltip
        public float prismaticReaperDamage = 29.0f;
        @ConfigEntry.Gui.Tooltip
        public float battleAxeDamage = 50.0f;
        @ConfigEntry.Gui.Tooltip
        public float queenAxeDamage = 666.0f;
    }
    public static class ThunderStaff {
        @ConfigEntry.Gui.Tooltip
        public int thunderStaffExplosionSize = 4;
        @ConfigEntry.Gui.Tooltip
        @ConfigEntry.Gui.PrefixText
        public int thunderStaffExplosionType = 2;
        @ConfigEntry.Gui.Tooltip
        public boolean thunderStaffExplosionFire = true;
    }
    public static class RayGun {
        @ConfigEntry.Gui.Tooltip
        public int rayGunExplosionSize = 6;
        @ConfigEntry.Gui.Tooltip
        @ConfigEntry.Gui.PrefixText
        public int rayGunExplosionType = 2;
        @ConfigEntry.Gui.Tooltip
        public boolean rayGunExplosionFire = false;
    }
    public static class Ant {
        @ConfigEntry.Gui.Tooltip
        public boolean enableBrownAntTeleport = true;
        @ConfigEntry.Gui.Tooltip
        public boolean enableRainbowAntTeleport = true;
        @ConfigEntry.Gui.Tooltip
        public boolean enableRedAntTeleport = true;
        @ConfigEntry.Gui.Tooltip
        public boolean enableUnstableAntTeleport = true;
        @ConfigEntry.Gui.Tooltip
        public boolean enableTermiteTeleport = true;
    }
    public static class WorldGen {
        @ConfigEntry.Gui.Tooltip
        public boolean enableOreGen = true;
        @ConfigEntry.Gui.Tooltip
        public boolean enableFossilGen = true;
        @ConfigEntry.Gui.Tooltip
        public boolean enableTrollOreGen = true;
        @ConfigEntry.Gui.Tooltip
        public boolean enableDzMineralOreGen = true;
        @ConfigEntry.Gui.Tooltip
        public boolean spawnDzOresInOverworld = true;
        @ConfigEntry.Gui.Tooltip
        public boolean enableNestGen = true;
    }
    public static class WorldGenOres {
        @ConfigEntry.Gui.Tooltip
        public boolean enableOreRubyGen = true;
        @ConfigEntry.Gui.Tooltip
        public boolean enableOreTigersEyeGen = true;
        @ConfigEntry.Gui.Tooltip
        public boolean enableOreAmethystGen = true;
        @ConfigEntry.Gui.Tooltip
        public boolean enableOreTitaniumGen = true;
        @ConfigEntry.Gui.Tooltip
        public boolean enableOreUraniumGen = true;
        @ConfigEntry.Gui.Tooltip
        public boolean enableOreSaltGen = true;
        @ConfigEntry.Gui.Tooltip
        public boolean enableOreAluminumGen = true;
        @ConfigEntry.Gui.Tooltip
        public boolean enableOreCopperGen = true;
        @ConfigEntry.Gui.Tooltip
        public boolean enableOreTinGen = true;
        @ConfigEntry.Gui.Tooltip
        public boolean enableOreSilverGen = true;
        @ConfigEntry.Gui.Tooltip
        public boolean enableOrePlatinumGen = true;
        @ConfigEntry.Gui.Tooltip
        public boolean enableOreSunstoneGen = true;
        @ConfigEntry.Gui.Tooltip
        public boolean enableOreBloodstoneGen = true;
    }
    public static class Misc {
        @ConfigEntry.Gui.Tooltip
        public boolean enableEnchantedGoldenAppleCowBreeding = false;
        @ConfigEntry.Gui.Tooltip
        public boolean enableDragonEggRespawns = true;
        @ConfigEntry.Gui.Tooltip
        public boolean mobHeadDrops = true;
        @ConfigEntry.Gui.Tooltip
        public boolean terraforgedCheckMsg = true;
        @ConfigEntry.Gui.Tooltip
        public boolean enableAutoEnchanting = true;
    }

    public static class Default {
        public float swordDamage = 10.0f;
        public float swordAttackSpeed = -2.4f;
        public float axeDamage = 12.0f;
        public float axeAttackSpeed = -3.0f;
        public float pickaxeDamage = 8.0f;
        public float pickaxeAttackSpeed = -2.8f;
        public float shovelDamage = 9.0f;
        public float shovelAttackSpeed = -3.0f;
        public float hoeDamage = 1.0f;
        public float hoeAttackSpeed = 0;
    }
}
