package io.github.chaosawakens.common.registry;

import com.mojang.brigadier.CommandDispatcher;
import io.github.chaosawakens.ChaosAwakens;
import net.minecraft.command.CommandSource;
import net.minecraft.command.Commands;
import net.minecraft.util.Util;
import net.minecraft.util.text.ChatType;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.StringTextComponent;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.util.text.event.ClickEvent;

public class CACommand {
    public static int register(CommandDispatcher<CommandSource> dispatcher) {
        dispatcher.register(Commands.literal("chaosawakens").requires((commandSource) -> commandSource.hasPermission(1))
                .then(Commands.literal("website")
                        .executes((commandSource) -> {
                            ITextComponent itextcomponent = new StringTextComponent("https://chaosawakens.github.io").withStyle((style) ->
                                    style.withColor(TextFormatting.LIGHT_PURPLE).withClickEvent(new ClickEvent(ClickEvent.Action.OPEN_URL, "https://chaosawakens.github.io")));
                            commandSource.getSource().getServer().getPlayerList().broadcastMessage(itextcomponent, ChatType.SYSTEM, Util.NIL_UUID);
                            return 0;
                        }))
                .then(Commands.literal("discord")
                        .executes((commandSource) -> {
                            ITextComponent itextcomponent = new StringTextComponent("https://discord.com/invite/TmVqnT5Zmj").withStyle((style) ->
                                    style.withColor(TextFormatting.BLUE).withClickEvent(new ClickEvent(ClickEvent.Action.OPEN_URL, "https://discord.com/invite/TmVqnT5Zmj")));
                            commandSource.getSource().getServer().getPlayerList().broadcastMessage(itextcomponent, ChatType.SYSTEM, Util.NIL_UUID);
                            return 0;
                        }))
                .then(Commands.literal("github")
                        .executes((commandSource) -> {
                            ITextComponent itextcomponent = new StringTextComponent("https://github.com/ChaosAwakens/ChaosAwakens").withStyle((style) ->
                                    style.withColor(TextFormatting.DARK_PURPLE).withClickEvent(new ClickEvent(ClickEvent.Action.OPEN_URL, "https://github.com/ChaosAwakens/ChaosAwakens")));
                            commandSource.getSource().getServer().getPlayerList().broadcastMessage(itextcomponent, ChatType.SYSTEM, Util.NIL_UUID);
                            return 0;
                        }))
                .then(Commands.literal("wiki")
                        .executes((commandSource) -> {
                            ITextComponent itextcomponent = new StringTextComponent("https://chaos-awakens.fandom.com/wiki/Chaos_Awakens_Wiki").withStyle((style) ->
                                    style.withColor(TextFormatting.DARK_GRAY).withClickEvent(new ClickEvent(ClickEvent.Action.OPEN_URL, "https://chaos-awakens.fandom.com/wiki/Chaos_Awakens_Wiki")));
                            commandSource.getSource().getServer().getPlayerList().broadcastMessage(itextcomponent, ChatType.SYSTEM, Util.NIL_UUID);
                            return 0;
                        }))
                .then(Commands.literal("reddit")
                        .executes((commandSource) -> {
                            ITextComponent itextcomponent = new StringTextComponent("https://www.reddit.com/r/ChaosAwakens").withStyle((style) ->
                                    style.withColor(TextFormatting.RED).withClickEvent(new ClickEvent(ClickEvent.Action.OPEN_URL, "https://www.reddit.com/r/ChaosAwakens")));
                            commandSource.getSource().getServer().getPlayerList().broadcastMessage(itextcomponent, ChatType.SYSTEM, Util.NIL_UUID);
                            return 0;
                        }))
                .then(Commands.literal("version")
                        .executes((commandSource) -> {
                            ITextComponent itextcomponent = new StringTextComponent(ChaosAwakens.MODNAME + " Version is: " + ChaosAwakens.VERSION).withStyle((style) ->
                                    style.withColor(TextFormatting.DARK_RED));
                            commandSource.getSource().getServer().getPlayerList().broadcastMessage(itextcomponent, ChatType.SYSTEM, Util.NIL_UUID);
                            return 0;
                        })));
        return 0;
    }
}