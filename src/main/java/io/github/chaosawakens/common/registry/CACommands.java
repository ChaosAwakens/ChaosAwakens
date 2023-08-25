package io.github.chaosawakens.common.registry;

import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.builder.LiteralArgumentBuilder;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import com.mojang.brigadier.exceptions.SimpleCommandExceptionType;

import com.mojang.brigadier.tree.LiteralCommandNode;
import io.github.chaosawakens.ChaosAwakens;
import net.minecraft.command.CommandSource;
import net.minecraft.command.Commands;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.text.*;
import net.minecraft.util.text.event.ClickEvent;
import net.minecraft.util.text.event.HoverEvent;
import net.minecraft.world.gen.feature.structure.Structure;
import net.minecraftforge.registries.ForgeRegistries;

import java.util.Objects;

public class CACommands {
	private static final SimpleCommandExceptionType LOCATE_ERROR_FAILED = new SimpleCommandExceptionType(new TranslationTextComponent("commands.locate.failed"));
	static String website = "https://chaosawakens.github.io";
	static String curseforge = "https://chaosawakens.github.io/curseforge";
	static String modrinth = "https://chaosawakens.github.io/modrinth";
	static String discord = "https://chaosawakens.github.io/discord";
	static String github = "https://chaosawakens.github.io/github";
	static String wiki = "https://chaosawakens.github.io/wiki";
	static String reddit = "https://chaosawakens.github.io/reddit";

	public static int register(CommandDispatcher<CommandSource> dispatcher) {
		LiteralArgumentBuilder<CommandSource> chaosawakens = Commands.literal("chaosawakens")
				.then(Commands.literal("website").executes((commandSource) -> {
					ITextComponent itextcomponent = new StringTextComponent(website)
							.withStyle((style) -> style
									.withColor(TextFormatting.LIGHT_PURPLE)
									.withClickEvent(new ClickEvent(ClickEvent.Action.OPEN_URL, website)));
					sendMessage(commandSource.getSource(), itextcomponent);
					return 0;
				})).then(Commands.literal("curseforge").executes((commandSource) -> {
					ITextComponent itextcomponent = new StringTextComponent(curseforge)
							.withStyle((style) -> style
									.withColor(TextFormatting.GOLD)
									.withClickEvent(new ClickEvent(ClickEvent.Action.OPEN_URL, curseforge)));
					sendMessage(commandSource.getSource(), itextcomponent);
					return 0;
				})).then(Commands.literal("modrinth").executes((commandSource) -> {
					ITextComponent itextcomponent = new StringTextComponent(modrinth)
							.withStyle((style) -> style
									.withColor(TextFormatting.GREEN)
									.withClickEvent(new ClickEvent(ClickEvent.Action.OPEN_URL, modrinth)));
					sendMessage(commandSource.getSource(), itextcomponent);
					return 0;
				})).then(Commands.literal("discord").executes((commandSource) -> {
					ITextComponent itextcomponent = new StringTextComponent(discord)
							.withStyle((style) -> style
									.withColor(TextFormatting.BLUE)
									.withClickEvent(new ClickEvent(ClickEvent.Action.OPEN_URL, discord)));
					sendMessage(commandSource.getSource(), itextcomponent);
					return 0;
				})).then(Commands.literal("github").executes((commandSource) -> {
					ITextComponent itextcomponent = new StringTextComponent(github)
							.withStyle((style) -> style
									.withColor(TextFormatting.DARK_PURPLE)
									.withClickEvent(new ClickEvent(ClickEvent.Action.OPEN_URL, github)));
					sendMessage(commandSource.getSource(), itextcomponent);
					return 0;
				})).then(Commands.literal("wiki").executes((commandSource) -> {
					ITextComponent itextcomponent = new StringTextComponent(wiki)
							.withStyle((style) -> style
									.withColor(TextFormatting.DARK_GRAY)
									.withClickEvent(new ClickEvent(ClickEvent.Action.OPEN_URL, wiki)));
					sendMessage(commandSource.getSource(), itextcomponent);
					return 0;
				})).then(Commands.literal("reddit").executes((commandSource) -> {
					ITextComponent itextcomponent = new StringTextComponent(reddit)
							.withStyle((style) -> style
									.withColor(TextFormatting.RED)
									.withClickEvent(new ClickEvent(ClickEvent.Action.OPEN_URL, reddit)));
					sendMessage(commandSource.getSource(), itextcomponent);
					return 0;
				})).then(Commands.literal("version").executes((commandSource) -> {
					ITextComponent itextcomponent = new StringTextComponent(ChaosAwakens.MODNAME + " Version is: " + ChaosAwakens.VERSION)
							.withStyle((style) -> style
									.withColor(TextFormatting.DARK_RED));
					sendMessage(commandSource.getSource(), itextcomponent);
					return 0;
				}));
		for (Structure<?> structureFeature : ForgeRegistries.STRUCTURE_FEATURES) {
			if (Objects.requireNonNull(structureFeature.getRegistryName()).toString().contains("chaosawakens:")) {
				String name = structureFeature.getRegistryName().toString().replace("chaosawakens:", "");
				chaosawakens = chaosawakens.then(Commands.literal("locate").requires((permission) -> permission.hasPermission(2)).then(Commands.literal(name).executes(ctx -> locate(ctx.getSource(), structureFeature))));
			}
		}

		LiteralCommandNode<CommandSource> chaosawakensRedirect = dispatcher.register(chaosawakens);
		dispatcher.register(Commands.literal("ca")
				.then(chaosawakensRedirect.getChild("website"))
				.then(chaosawakensRedirect.getChild("curseforge"))
				.then(chaosawakensRedirect.getChild("modrinth"))
				.then(chaosawakensRedirect.getChild("discord"))
				.then(chaosawakensRedirect.getChild("github"))
				.then(chaosawakensRedirect.getChild("wiki"))
				.then(chaosawakensRedirect.getChild("reddit"))
				.then(chaosawakensRedirect.getChild("version"))
				.then(chaosawakensRedirect.getChild("locate")));
		return 0;
	}

	private static void sendMessage(CommandSource pSource, ITextComponent pMessage) {
		Entity entity = pSource.getEntity();
		if (entity instanceof ServerPlayerEntity) {
			ServerPlayerEntity serverplayerentity = (ServerPlayerEntity)entity;
			serverplayerentity.sendMessage(pMessage, serverplayerentity.getUUID());
		} else {
			pSource.sendSuccess(pMessage, false);
		}
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