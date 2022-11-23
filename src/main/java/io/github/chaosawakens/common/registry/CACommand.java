package io.github.chaosawakens.common.registry;

import com.mojang.brigadier.CommandDispatcher;

import com.mojang.brigadier.builder.LiteralArgumentBuilder;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import com.mojang.brigadier.exceptions.SimpleCommandExceptionType;
import io.github.chaosawakens.ChaosAwakens;
import net.minecraft.command.CommandSource;
import net.minecraft.command.Commands;
import net.minecraft.util.Util;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.text.*;
import net.minecraft.util.text.event.ClickEvent;
import net.minecraft.util.text.event.HoverEvent;
import net.minecraft.world.gen.feature.structure.Structure;
import net.minecraftforge.registries.ForgeRegistries;

import java.util.Objects;

public class CACommand {
	private static final SimpleCommandExceptionType LOCATE_ERROR_FAILED = new SimpleCommandExceptionType(new TranslationTextComponent("commands.locate.failed"));

	public static int register(CommandDispatcher<CommandSource> dispatcher) {
		dispatcher.register(Commands.literal("chaosawakens")
				.then(Commands.literal("website").executes((commandSource) -> {
					ITextComponent itextcomponent = new StringTextComponent("https://chaosawakens.github.io")
							.withStyle((style) -> style
									.withColor(TextFormatting.LIGHT_PURPLE)
									.withClickEvent(new ClickEvent(ClickEvent.Action.OPEN_URL, "https://chaosawakens.github.io")));
					commandSource.getSource().getServer().getPlayerList()
							.broadcastMessage(itextcomponent, ChatType.SYSTEM, Util.NIL_UUID);
					return 0;
				})).then(Commands.literal("curseforge").executes((commandSource) -> {
					ITextComponent itextcomponent = new StringTextComponent("https://www.curseforge.com/minecraft/mc-mods/chaos-awakens")
							.withStyle((style) -> style
									.withColor(TextFormatting.GOLD)
									.withClickEvent(new ClickEvent(ClickEvent.Action.OPEN_URL, "https://www.curseforge.com/minecraft/mc-mods/chaos-awakenso")));
					commandSource.getSource().getServer().getPlayerList()
							.broadcastMessage(itextcomponent, ChatType.SYSTEM, Util.NIL_UUID);
					return 0;
				})).then(Commands.literal("modrinth").executes((commandSource) -> {
					ITextComponent itextcomponent = new StringTextComponent("https://modrinth.com/mod/chaos-awakens")
							.withStyle((style) -> style
									.withColor(TextFormatting.GREEN)
									.withClickEvent(new ClickEvent(ClickEvent.Action.OPEN_URL, "https://modrinth.com/mod/chaos-awakens")));
					commandSource.getSource().getServer().getPlayerList()
							.broadcastMessage(itextcomponent, ChatType.SYSTEM, Util.NIL_UUID);
					return 0;
				})).then(Commands.literal("discord").executes((commandSource) -> {
					ITextComponent itextcomponent = new StringTextComponent("https://discord.com/invite/hnCKD4M87R")
							.withStyle((style) -> style
									.withColor(TextFormatting.BLUE)
									.withClickEvent(new ClickEvent(ClickEvent.Action.OPEN_URL, "https://discord.com/invite/hnCKD4M87R")));
					commandSource.getSource().getServer().getPlayerList()
							.broadcastMessage(itextcomponent, ChatType.SYSTEM, Util.NIL_UUID);
					return 0;
				})).then(Commands.literal("github").executes((commandSource) -> {
					ITextComponent itextcomponent = new StringTextComponent("https://github.com/ChaosAwakens/ChaosAwakens")
							.withStyle((style) -> style
									.withColor(TextFormatting.DARK_PURPLE)
									.withClickEvent(new ClickEvent(ClickEvent.Action.OPEN_URL, "https://github.com/ChaosAwakens/ChaosAwakens")));
					commandSource.getSource().getServer().getPlayerList()
							.broadcastMessage(itextcomponent, ChatType.SYSTEM, Util.NIL_UUID);
					return 0;
				})).then(Commands.literal("wiki").executes((commandSource) -> {
					ITextComponent itextcomponent = new StringTextComponent("https://chaos-awakens.fandom.com/wiki/Chaos_Awakens_Wiki")
							.withStyle((style) -> style
									.withColor(TextFormatting.DARK_GRAY)
									.withClickEvent(new ClickEvent(ClickEvent.Action.OPEN_URL, "https://chaos-awakens.fandom.com/wiki/Chaos_Awakens_Wiki")));
					commandSource.getSource().getServer().getPlayerList()
							.broadcastMessage(itextcomponent, ChatType.SYSTEM, Util.NIL_UUID);
					return 0;
				})).then(Commands.literal("reddit").executes((commandSource) -> {
					ITextComponent itextcomponent = new StringTextComponent("https://www.reddit.com/r/ChaosAwakens")
							.withStyle((style) -> style
									.withColor(TextFormatting.RED)
									.withClickEvent(new ClickEvent(ClickEvent.Action.OPEN_URL, "https://www.reddit.com/r/ChaosAwakens")));
					commandSource.getSource().getServer().getPlayerList()
							.broadcastMessage(itextcomponent, ChatType.SYSTEM, Util.NIL_UUID);
					return 0;
				})).then(Commands.literal("version").executes((commandSource) -> {
					ITextComponent itextcomponent = new StringTextComponent(ChaosAwakens.MODNAME + " Version is: " + ChaosAwakens.VERSION)
							.withStyle((style) -> style
									.withColor(TextFormatting.DARK_RED));
					commandSource.getSource().getServer().getPlayerList()
							.broadcastMessage(itextcomponent, ChatType.SYSTEM, Util.NIL_UUID);
					return 0;
				})));


		LiteralArgumentBuilder<CommandSource> chaosawakenslocate = Commands.literal("chaosawakens");
		for (Structure<?> structureFeature : ForgeRegistries.STRUCTURE_FEATURES) {
			if (Objects.requireNonNull(structureFeature.getRegistryName()).toString().contains("chaosawakens:")) {
				String name = structureFeature.getRegistryName().toString().replace("chaosawakens:", "");
				chaosawakenslocate = chaosawakenslocate.then(Commands.literal("locate").requires((p_198533_0_) -> p_198533_0_.hasPermission(2)).then(Commands.literal(name).executes(ctx -> locate(ctx.getSource(), structureFeature))));
			}
		}
		dispatcher.register(chaosawakenslocate);

		dispatcher.register(Commands.literal("ca")
				.then(Commands.literal("website").executes((commandSource) -> {
					ITextComponent itextcomponent = new StringTextComponent("https://chaosawakens.github.io")
							.withStyle((style) -> style
									.withColor(TextFormatting.LIGHT_PURPLE)
									.withClickEvent(new ClickEvent(ClickEvent.Action.OPEN_URL, "https://chaosawakens.github.io")));
					commandSource.getSource().getServer().getPlayerList()
							.broadcastMessage(itextcomponent, ChatType.SYSTEM, Util.NIL_UUID);
					return 0;
				})).then(Commands.literal("curseforge").executes((commandSource) -> {
					ITextComponent itextcomponent = new StringTextComponent("https://www.curseforge.com/minecraft/mc-mods/chaos-awakens")
							.withStyle((style) -> style
									.withColor(TextFormatting.GOLD)
									.withClickEvent(new ClickEvent(ClickEvent.Action.OPEN_URL, "https://www.curseforge.com/minecraft/mc-mods/chaos-awakenso")));
					commandSource.getSource().getServer().getPlayerList()
							.broadcastMessage(itextcomponent, ChatType.SYSTEM, Util.NIL_UUID);
					return 0;
				})).then(Commands.literal("modrinth").executes((commandSource) -> {
					ITextComponent itextcomponent = new StringTextComponent("https://modrinth.com/mod/chaos-awakens")
							.withStyle((style) -> style
									.withColor(TextFormatting.GREEN)
									.withClickEvent(new ClickEvent(ClickEvent.Action.OPEN_URL, "https://modrinth.com/mod/chaos-awakens")));
					commandSource.getSource().getServer().getPlayerList()
							.broadcastMessage(itextcomponent, ChatType.SYSTEM, Util.NIL_UUID);
					return 0;
				})).then(Commands.literal("discord").executes((commandSource) -> {
					ITextComponent itextcomponent = new StringTextComponent("https://discord.com/invite/TmVqnT5Zmj")
							.withStyle((style) -> style
									.withColor(TextFormatting.BLUE)
									.withClickEvent(new ClickEvent(ClickEvent.Action.OPEN_URL, "https://discord.com/invite/TmVqnT5Zmj")));
					commandSource.getSource().getServer().getPlayerList()
							.broadcastMessage(itextcomponent, ChatType.SYSTEM, Util.NIL_UUID);
					return 0;
				})).then(Commands.literal("github").executes((commandSource) -> {
					ITextComponent itextcomponent = new StringTextComponent("https://github.com/ChaosAwakens/ChaosAwakens")
							.withStyle((style) -> style
									.withColor(TextFormatting.DARK_PURPLE)
									.withClickEvent(new ClickEvent(ClickEvent.Action.OPEN_URL, "https://github.com/ChaosAwakens/ChaosAwakens")));
					commandSource.getSource().getServer().getPlayerList()
							.broadcastMessage(itextcomponent, ChatType.SYSTEM, Util.NIL_UUID);
					return 0;
				})).then(Commands.literal("wiki").executes((commandSource) -> {
					ITextComponent itextcomponent = new StringTextComponent("https://chaos-awakens.fandom.com/wiki/Chaos_Awakens_Wiki")
							.withStyle((style) -> style
									.withColor(TextFormatting.DARK_GRAY)
									.withClickEvent(new ClickEvent(ClickEvent.Action.OPEN_URL, "https://chaos-awakens.fandom.com/wiki/Chaos_Awakens_Wiki")));
					commandSource.getSource().getServer().getPlayerList()
							.broadcastMessage(itextcomponent, ChatType.SYSTEM, Util.NIL_UUID);
					return 0;
				})).then(Commands.literal("reddit").executes((commandSource) -> {
					ITextComponent itextcomponent = new StringTextComponent("https://www.reddit.com/r/ChaosAwakens")
							.withStyle((style) -> style
									.withColor(TextFormatting.RED)
									.withClickEvent(new ClickEvent(ClickEvent.Action.OPEN_URL, "https://www.reddit.com/r/ChaosAwakens")));
					commandSource.getSource().getServer().getPlayerList()
							.broadcastMessage(itextcomponent, ChatType.SYSTEM, Util.NIL_UUID);
					return 0;
				})).then(Commands.literal("version").executes((commandSource) -> {
					ITextComponent itextcomponent = new StringTextComponent(ChaosAwakens.MODNAME + " Version is: " + ChaosAwakens.VERSION)
							.withStyle((style) -> style
									.withColor(TextFormatting.DARK_RED));
					commandSource.getSource().getServer().getPlayerList()
							.broadcastMessage(itextcomponent, ChatType.SYSTEM, Util.NIL_UUID);
					return 0;
				})));


		LiteralArgumentBuilder<CommandSource> calocate = Commands.literal("ca");
		for (Structure<?> structureFeature : ForgeRegistries.STRUCTURE_FEATURES) {
			if (Objects.requireNonNull(structureFeature.getRegistryName()).toString().contains("chaosawakens:")) {
				String name = structureFeature.getRegistryName().toString().replace("chaosawakens:", "");
				calocate = calocate.then(Commands.literal("locate").requires((p_198533_0_) -> p_198533_0_.hasPermission(2)).then(Commands.literal(name).executes(ctx -> locate(ctx.getSource(), structureFeature))));
			}
		}
		dispatcher.register(calocate);
		return 0;
	}

	private static int locate(CommandSource commandSource, Structure<?> structure) throws CommandSyntaxException {
		BlockPos blockpos = new BlockPos(commandSource.getPosition());
		BlockPos blockpos1 = commandSource.getLevel().findNearestMapFeature(structure, blockpos, 100, false);
		if (blockpos1 == null) throw LOCATE_ERROR_FAILED.create();
		else return showLocateResult(commandSource, structure.getFeatureName(), blockpos, blockpos1, "commands.locate.success");
	}

	public static int showLocateResult(CommandSource commandSource, String p_241054_1_, BlockPos pos1, BlockPos pos2, String p_241054_4_) {
		int i = MathHelper.floor(dist(pos1.getX(), pos1.getZ(), pos2.getX(), pos2.getZ()));
		ITextComponent itextcomponent = TextComponentUtils.wrapInSquareBrackets(new TranslationTextComponent("chat.coordinates", pos2.getX(), "~", pos2.getZ())).withStyle((style) -> style
				.withColor(TextFormatting.GREEN)
				.withClickEvent(new ClickEvent(ClickEvent.Action.SUGGEST_COMMAND, "/tp @s " + pos2.getX() + " ~ " + pos2.getZ()))
				.withHoverEvent(new HoverEvent(HoverEvent.Action.SHOW_TEXT, new TranslationTextComponent("chat.coordinates.tooltip"))));
		commandSource.sendSuccess(new TranslationTextComponent(p_241054_4_, p_241054_1_, itextcomponent, i), false);
		return i;
	}

	private static float dist(int p_211907_0_, int p_211907_1_, int p_211907_2_, int p_211907_3_) {
		int i = p_211907_2_ - p_211907_0_;
		int j = p_211907_3_ - p_211907_1_;
		return MathHelper.sqrt((float)(i * i + j * j));
	}
}