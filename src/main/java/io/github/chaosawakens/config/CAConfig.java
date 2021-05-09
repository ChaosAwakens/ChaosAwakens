package io.github.chaosawakens.config;

import net.minecraftforge.common.ForgeConfigSpec;
import net.minecraftforge.common.ForgeConfigSpec.*;
import org.apache.commons.lang3.tuple.Pair;

public class CAConfig {

    public static class Common {
        public final ConfigValue<Integer> ultimateSwordDamage;
        public final ConfigValue<Integer> ultimateAxeDamage;
        public final ConfigValue<Integer> ultimatePickaxeDamage;
        public final ConfigValue<Integer> ultimateShovelDamage;
        public final ConfigValue<Integer> ultimateHoeDamage;
        public final ConfigValue<Integer> emeraldSwordDamage;
        public final ConfigValue<Integer> emeraldAxeDamage;
        public final ConfigValue<Integer> emeraldPickaxeDamage;
        public final ConfigValue<Integer> emeraldShovelDamage;
        public final ConfigValue<Integer> emeraldHoeDamage;
        public final ConfigValue<Integer> rubySwordDamage;
        public final ConfigValue<Integer> rubyAxeDamage;
        public final ConfigValue<Integer> rubyPickaxeDamage;
        public final ConfigValue<Integer> rubyShovelDamage;
        public final ConfigValue<Integer> rubyHoeDamage;
        public final ConfigValue<Integer> amethystSwordDamage;
        public final ConfigValue<Integer> amethystAxeDamage;
        public final ConfigValue<Integer> amethystPickaxeDamage;
        public final ConfigValue<Integer> amethystShovelDamage;
        public final ConfigValue<Integer> amethystHoeDamage;
        public final ConfigValue<Integer> tigersEyeSwordDamage;
        public final ConfigValue<Integer> tigersEyeAxeDamage;
        public final ConfigValue<Integer> tigersEyePickaxeDamage;
        public final ConfigValue<Integer> tigersEyeShovelDamage;
        public final ConfigValue<Integer> tigersEyeHoeDamage;
        public final ConfigValue<Integer> nightmareSwordDamage;
        public final ConfigValue<Integer> experienceSwordDamage;
        public final ConfigValue<Integer> poisonSwordDamage;
        public final ConfigValue<Integer> ratSwordDamage;
        public final ConfigValue<Integer> fairySwordDamage;
        public final ConfigValue<Integer> bigHammerDamage;
        public final ConfigValue<Integer> prismaticReaperDamage;
        public final ConfigValue<Integer> explosionSize;
        public final ConfigValue<Integer> explosionType;
        public final ConfigValue<Boolean> explosionFire;

        Common(ForgeConfigSpec.Builder builder) {
            builder.push("Attack Damage");
            builder.push("Ultimate Weapons/Tools");
            ultimateSwordDamage = builder.define("Damage of the Ultimate Sword:", 40);
            ultimateAxeDamage = builder.define("Damage of the Ultimate Axe:", 42);
            ultimatePickaxeDamage = builder.define("Damage of the Ultimate Pickaxe:", 38);
            ultimateShovelDamage = builder.define("Damage of the Ultimate Shovel:", 39);
            ultimateHoeDamage = builder.define("Damage of the Ultimate Hoe:", 1);
            builder.pop();
            builder.push("Emerald Weapons/Tools");
            emeraldSwordDamage = builder.define("Damage of the Emerald Sword:", 10);
            emeraldAxeDamage = builder.define("Damage of the Emerald Axe:", 12);
            emeraldPickaxeDamage = builder.define("Damage of the Emerald Pickaxe:", 8);
            emeraldShovelDamage = builder.define("Damage of the Emerald Shovel:", 9);
            emeraldHoeDamage = builder.define("Damage of the Emerald Hoe:", 1);
            builder.pop();
            builder.push("Ruby Weapons/Tools");
            rubySwordDamage = builder.define("Damage of the Ruby Sword:", 20);
            rubyAxeDamage = builder.define("Damage of the Ruby Axe:", 22);
            rubyPickaxeDamage = builder.define("Damage of the Ruby Pickaxe:", 18);
            rubyShovelDamage = builder.define("Damage of the Ruby Shovel:", 19);
            rubyHoeDamage = builder.define("Damage of the Ruby Hoe:", 1);
            builder.pop();
            builder.push("Amethyst Weapons/Tools");
            amethystSwordDamage = builder.define("Damage of the Amethyst Sword:", 15);
            amethystAxeDamage = builder.define("Damage of the Amethyst Axe:", 17);
            amethystPickaxeDamage = builder.define("Damage of the Amethyst Pickaxe:", 13);
            amethystShovelDamage = builder.define("Damage of the Amethyst Shovel:", 14);
            amethystHoeDamage = builder.define("Damage of the Amethyst Hoe:", 1);
            builder.pop();
            builder.push("Tiger's Eye Weapons/Tools");
            tigersEyeSwordDamage = builder.define("Damage of the Tiger's Eye Sword:", 12);
            tigersEyeAxeDamage = builder.define("Damage of the Tiger's Eye Axe:", 14);
            tigersEyePickaxeDamage = builder.define("Damage of the Tiger's Eye Pickaxe:", 10);
            tigersEyeShovelDamage = builder.define("Damage of the Tiger's Eye Shovel:", 11);
            tigersEyeHoeDamage = builder.define("Damage of the Tiger's Eye Hoe:", 1);
            builder.pop();
            builder.push("Misc Weapons/Tools");
            nightmareSwordDamage = builder.define("Damage of the Nightmare Sword", 30);
            experienceSwordDamage = builder.define("Damage of the Experience Sword", 10);
            poisonSwordDamage = builder.define("Damage of the Poison Sword", 10);
            ratSwordDamage = builder.define("Damage of the Rat Sword", 10);
            fairySwordDamage = builder.define("Damage of the Fairy Sword", 10);
            bigHammerDamage = builder.define("Damage of the Big Hammer", 15);
            prismaticReaperDamage = builder.define("Damage of the Prismatic Reaper:", 29);
            builder.pop();
            builder.pop();
            builder.push("Thunder Staff");
            explosionSize = builder.define("Thunder Staff Explosion Size", 5);
            explosionType = builder
                    .comment("0 = NONE - The Thunder Staff will no break blocks." + "\n" +
                            "1 = BREAK - The Thunder Staff will drop some blocks that it breaks. (May not work due to lightning)" + "\n" +
                            "2 = DESTROY - The Thunder Staff will destroy blocks and never drop them.")
                    .defineInRange("Thunder Staff Explosion Type:", 2, 0, 2);
            explosionFire = builder.define("Fire From Explosion:", true);
            builder.pop();
        }
    }

    public static final ForgeConfigSpec COMMON_SPEC;
    public static final Common COMMON;

    static {
        final Pair<Common, ForgeConfigSpec> commonSpecPair = new ForgeConfigSpec.Builder().configure(Common::new);
        COMMON_SPEC = commonSpecPair.getRight();
        COMMON = commonSpecPair.getLeft();
    }
}