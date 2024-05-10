package io.github.chaosawakens.common.registry;

import it.unimi.dsi.fastutil.objects.ObjectOpenHashSet;
import net.minecraft.scoreboard.Team;
import net.minecraft.util.text.IFormattableTextComponent;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.StringTextComponent;
import net.minecraft.util.text.TextFormatting;

import java.util.Collection;

public class CATeams {

    public static final Team ROBO_TEAM = new Team() { //TODO In newer versions, defo not this anon class mess :skull:
        private final ObjectOpenHashSet<String> players = new ObjectOpenHashSet<>();

        @Override
        public String getName() {
            return "Robo Team";
        }

        @Override
        public IFormattableTextComponent getFormattedName(ITextComponent pFormattedName) {
            return new StringTextComponent("Robo Team").withStyle(TextFormatting.DARK_PURPLE);
        }

        @Override
        public boolean canSeeFriendlyInvisibles() {
            return true;
        }

        @Override
        public boolean isAllowFriendlyFire() {
            return false;
        }

        @Override
        public Visible getNameTagVisibility() {
            return Visible.NEVER;
        }

        @Override
        public TextFormatting getColor() {
            return null;
        }

        @Override
        public Collection<String> getPlayers() {
            return players;
        }

        @Override
        public Visible getDeathMessageVisibility() {
            return Visible.NEVER;
        }

        @Override
        public CollisionRule getCollisionRule() {
            return CollisionRule.PUSH_OTHER_TEAMS;
        }
    };
}
