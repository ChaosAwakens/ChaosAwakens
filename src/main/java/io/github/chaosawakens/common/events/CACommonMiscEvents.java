package io.github.chaosawakens.common.events;

import java.util.Objects;
import java.util.Random;
import java.util.UUID;

import com.google.common.collect.ListMultimap;
import com.google.common.collect.Lists;
import com.google.common.collect.MultimapBuilder;
import com.mojang.brigadier.exceptions.CommandSyntaxException;

import io.github.chaosawakens.ChaosAwakens;
import io.github.chaosawakens.common.enchantments.HoplologyEnchantment;
import io.github.chaosawakens.common.entity.base.AnimatableAnimalEntity;
import io.github.chaosawakens.common.entity.base.AnimatableFishEntity;
import io.github.chaosawakens.common.entity.base.AnimatableMonsterEntity;
import io.github.chaosawakens.common.entity.base.AnimatableWaterMobEntity;
import io.github.chaosawakens.common.items.base.AnimatableShieldItem;
import io.github.chaosawakens.common.registry.CABlocks;
import io.github.chaosawakens.common.registry.CAEffects;
import io.github.chaosawakens.common.registry.CAEnchantments;
import io.github.chaosawakens.common.registry.CAItems;
import io.github.chaosawakens.common.registry.CATags;
import io.github.chaosawakens.common.util.EntityUtil;
import io.github.chaosawakens.common.util.MathUtil;
import io.github.chaosawakens.manager.CAConfigManager;
import net.minecraft.block.Blocks;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.ai.attributes.Attributes;
import net.minecraft.entity.ai.goal.AvoidEntityGoal;
import net.minecraft.entity.ai.goal.HurtByTargetGoal;
import net.minecraft.entity.ai.goal.LookAtGoal;
import net.minecraft.entity.ai.goal.LookRandomlyGoal;
import net.minecraft.entity.ai.goal.MeleeAttackGoal;
import net.minecraft.entity.ai.goal.NearestAttackableTargetGoal;
import net.minecraft.entity.ai.goal.WaterAvoidingRandomWalkingGoal;
import net.minecraft.entity.boss.dragon.EnderDragonEntity;
import net.minecraft.entity.item.ItemEntity;
import net.minecraft.entity.merchant.villager.AbstractVillagerEntity;
import net.minecraft.entity.merchant.villager.VillagerEntity;
import net.minecraft.entity.merchant.villager.WanderingTraderEntity;
import net.minecraft.entity.monster.GiantEntity;
import net.minecraft.entity.passive.IronGolemEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.Util;
import net.minecraft.util.text.ChatType;
import net.minecraft.util.text.StringTextComponent;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.util.text.event.ClickEvent;
import net.minecraft.world.IWorld;
import net.minecraft.world.World;
import net.minecraft.world.gen.Heightmap;
import net.minecraft.world.gen.feature.EndPodiumFeature;
import net.minecraft.world.server.ServerWorld;
import net.minecraftforge.common.ToolType;
import net.minecraftforge.event.CommandEvent;
import net.minecraftforge.event.enchanting.EnchantmentLevelSetEvent;
import net.minecraftforge.event.entity.EntityJoinWorldEvent;
import net.minecraftforge.event.entity.living.LivingDeathEvent;
import net.minecraftforge.event.entity.living.LivingDropsEvent;
import net.minecraftforge.event.entity.living.LivingEntityUseItemEvent;
import net.minecraftforge.event.entity.living.LivingEvent.LivingJumpEvent;
import net.minecraftforge.event.entity.living.LivingExperienceDropEvent;
import net.minecraftforge.event.entity.living.LivingHurtEvent;
import net.minecraftforge.event.entity.living.LivingKnockBackEvent;
import net.minecraftforge.event.entity.player.AttackEntityEvent;
import net.minecraftforge.event.entity.player.FillBucketEvent;
import net.minecraftforge.event.entity.player.PlayerEvent.ItemPickupEvent;
import net.minecraftforge.event.entity.player.PlayerEvent.PlayerLoggedInEvent;
import net.minecraftforge.event.entity.player.PlayerInteractEvent;
import net.minecraftforge.event.entity.player.PlayerInteractEvent.LeftClickBlock;
import net.minecraftforge.event.entity.player.PlayerInteractEvent.LeftClickEmpty;
import net.minecraftforge.event.entity.player.PlayerInteractEvent.RightClickBlock;
import net.minecraftforge.event.entity.player.PlayerInteractEvent.RightClickEmpty;
import net.minecraftforge.event.world.BlockEvent.BlockToolInteractEvent;
import net.minecraftforge.event.world.BlockEvent.BreakEvent;
import net.minecraftforge.event.world.BlockEvent.EntityPlaceEvent;
import net.minecraftforge.event.world.SleepFinishedTimeEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.ModList;
import net.minecraftforge.fml.VersionChecker;

public class CACommonMiscEvents {
	private static final String DOWNLOADS = "https://chaosawakens.github.io/downloads";
	private static final ListMultimap<UUID, TextFormatting> DEVELOPER_COLORS = Util.make(MultimapBuilder.hashKeys().arrayListValues(2).build(), (devMultiMap) -> {
		devMultiMap.putAll(UUID.fromString("89cd9d1b-9d50-4502-8bd4-95b9e63ff589"), Lists.newArrayList(TextFormatting.GREEN, TextFormatting.DARK_GREEN)); // Blackout03
		devMultiMap.putAll(UUID.fromString("29aa413b-d714-46f1-a3f5-68b9c67a4923"), Lists.newArrayList(TextFormatting.BLUE, TextFormatting.DARK_BLUE));// Ninjaguy169
		devMultiMap.putAll(UUID.fromString("2668a475-2166-4539-9935-00f087818c4a"), Lists.newArrayList(TextFormatting.GOLD, TextFormatting.YELLOW));// T40ne
		devMultiMap.putAll(UUID.fromString("8c89a0d3-3271-459d-a8c1-a9d34d53365b"), Lists.newArrayList(TextFormatting.RED, TextFormatting.DARK_RED));// FunkyMonk127
	});

	@SubscribeEvent
	public static void onPlayerLoggedInEvent(PlayerLoggedInEvent event) {
		Entity target = event.getEntity();
		
		if (CAConfigManager.MAIN_COMMON.showUpdateMessage.get() && VersionChecker.getResult(ModList.get().getModContainerById(ChaosAwakens.MODID).get().getModInfo()).status == VersionChecker.Status.OUTDATED) {
			target.sendMessage(new StringTextComponent("A new version of ").withStyle(TextFormatting.WHITE)
					.append(new StringTextComponent(ChaosAwakens.MODNAME).withStyle(TextFormatting.BOLD, TextFormatting.GOLD))
					.append(new StringTextComponent(" is now available from: ").withStyle(TextFormatting.WHITE))
					.append(new StringTextComponent(DOWNLOADS)
							.withStyle((style) -> style
									.withColor(TextFormatting.GOLD)
									.withClickEvent(new ClickEvent(ClickEvent.Action.OPEN_URL, DOWNLOADS)))), Util.NIL_UUID);
		}

		//TODO Reorganize this Yandere-dev ahh code
		if (EntityUtil.isUserOrEntityUUIDEqualTo(target, UUID.fromString("89cd9d1b-9d50-4502-8bd4-95b9e63ff589"))) { // UUID of Blackout03_
			Objects.requireNonNull(target.getServer()).getPlayerList().broadcastMessage(new StringTextComponent("The Developer, ").withStyle(TextFormatting.GREEN)
					.append(new StringTextComponent("Blackout03_").withStyle(TextFormatting.BOLD, TextFormatting.DARK_GREEN))
					.append(new StringTextComponent(" has joined the Server!").withStyle(TextFormatting.GREEN)), ChatType.SYSTEM, Util.NIL_UUID);
		} else if (EntityUtil.isUserOrEntityUUIDEqualTo(target, UUID.fromString("29aa413b-d714-46f1-a3f5-68b9c67a4923"))) { // UUID of Ninjaguy169
			Objects.requireNonNull(target.getServer()).getPlayerList().broadcastMessage(new StringTextComponent("The Developer, ").withStyle(TextFormatting.BLUE)
					.append(new StringTextComponent("Ninjaguy169").withStyle(TextFormatting.BOLD, TextFormatting.DARK_BLUE))
					.append(new StringTextComponent(" has joined the Server!").withStyle(TextFormatting.BLUE)), ChatType.SYSTEM, Util.NIL_UUID);
		} else if (EntityUtil.isUserOrEntityUUIDEqualTo(target, UUID.fromString("2668a475-2166-4539-9935-00f087818c4a"))) { // UUID of T40ne
			Objects.requireNonNull(target.getServer()).getPlayerList().broadcastMessage(new StringTextComponent("The Owner, ").withStyle(TextFormatting.GOLD)
					.append(new StringTextComponent("T40ne").withStyle(TextFormatting.BOLD, TextFormatting.YELLOW))
					.append(new StringTextComponent(" has joined the Server!").withStyle(TextFormatting.GOLD)), ChatType.SYSTEM, Util.NIL_UUID);
		} else if (EntityUtil.isUserOrEntityUUIDEqualTo(target, UUID.fromString("8c89a0d3-3271-459d-a8c1-a9d34d53365b"))) { // UUID of FunkyMonk127
			Objects.requireNonNull(target.getServer()).getPlayerList().broadcastMessage(new StringTextComponent("The Owner, ").withStyle(TextFormatting.RED)
					.append(new StringTextComponent("FunkyMonk127").withStyle(TextFormatting.BOLD, TextFormatting.DARK_RED))
					.append(new StringTextComponent(" has joined the Server!").withStyle(TextFormatting.RED)), ChatType.SYSTEM, Util.NIL_UUID);
		}


	}
	
	@SubscribeEvent
	public static void onEntityJoinWorldEvent(EntityJoinWorldEvent event) { //TODO Also this
		Entity target = event.getEntity();
		
		if (target instanceof VillagerEntity) {
			VillagerEntity villagerTarget = (VillagerEntity) target;
			
			villagerTarget.goalSelector.addGoal(1, new AvoidEntityGoal<>(villagerTarget, AnimatableMonsterEntity.class, 40.0F, 0.5D, 0.5D));
			villagerTarget.goalSelector.addGoal(1, new AvoidEntityGoal<>(villagerTarget, GiantEntity.class, 32.0F, 0.5D, 0.5D));
		}
		
		if (target instanceof WanderingTraderEntity) {
			WanderingTraderEntity wanderingTraderTarget = (WanderingTraderEntity) target;
			
			wanderingTraderTarget.goalSelector.addGoal(1, new AvoidEntityGoal<>(wanderingTraderTarget, AnimatableMonsterEntity.class, 40.0F, 0.5D, 0.5D));
			wanderingTraderTarget.goalSelector.addGoal(1, new AvoidEntityGoal<>(wanderingTraderTarget, GiantEntity.class, 32.0F, 0.5D, 0.5D));
		}
		
		if (target instanceof GiantEntity) {
			GiantEntity giantTarget = (GiantEntity) target;
			
			giantTarget.goalSelector.addGoal(8, new LookAtGoal(giantTarget, PlayerEntity.class, 24.0F));
			giantTarget.goalSelector.addGoal(8, new LookRandomlyGoal(giantTarget));

			giantTarget.goalSelector.addGoal(2, new MeleeAttackGoal(giantTarget, 1.0F, false));
			giantTarget.goalSelector.addGoal(7, new WaterAvoidingRandomWalkingGoal(giantTarget, 1.0F));
			giantTarget.targetSelector.addGoal(2, new HurtByTargetGoal(giantTarget));
			giantTarget.targetSelector.addGoal(2, new NearestAttackableTargetGoal<>(giantTarget, PlayerEntity.class, true));
			giantTarget.targetSelector.addGoal(3, new NearestAttackableTargetGoal<>(giantTarget, AbstractVillagerEntity.class, false));
			giantTarget.targetSelector.addGoal(3, new NearestAttackableTargetGoal<>(giantTarget, IronGolemEntity.class, true));

			Objects.requireNonNull(giantTarget.getAttribute(Attributes.FOLLOW_RANGE)).setBaseValue(100); // FOLLOW_RANGE
			Objects.requireNonNull(giantTarget.getAttribute(Attributes.MOVEMENT_SPEED)).setBaseValue(0.15F); // MOVEMENT_SPEED
			Objects.requireNonNull(giantTarget.getAttribute(Attributes.ATTACK_DAMAGE)).setBaseValue(20.0D); // ATTACK_DAMAGE
			Objects.requireNonNull(giantTarget.getAttribute(Attributes.ARMOR)).setBaseValue(10.0D); // ARMOR
		}
	}
	
	@SubscribeEvent
	public static void onLivingHurtEvent(LivingHurtEvent event) {
		LivingEntity curEntity = event.getEntityLiving();
		
		if (curEntity != null) {
			int hoplologyLevel = EnchantmentHelper.getEnchantmentLevel(CAEnchantments.HOPLOLOGY.get(), curEntity);
			if (hoplologyLevel > 0) HoplologyEnchantment.handleHoplologyProtection(curEntity, CAEnchantments.HOPLOLOGY.get());
		}
	}

	@SubscribeEvent
	public static void onLivingDeathEvent(LivingDeathEvent event) {
		Entity deadTarget = event.getEntity();
		MinecraftServer curServer = deadTarget.getServer();
		Random random = new Random();
		
		if (curServer == null) return;
		
		if (deadTarget instanceof PlayerEntity) {
			// Make myself (Blackout03_) drop Ink Sacs any time I die. Even if I have none on me.
			if (EntityUtil.isUserOrEntityUUIDEqualTo(deadTarget, UUID.fromString("89cd9d1b-9d50-4502-8bd4-95b9e63ff589"))) { // UUID of Blackout03_
				((PlayerEntity) deadTarget).drop(new ItemStack(Items.INK_SAC, random.nextInt(3)), true, false);
			}
		}
		if (CAConfigManager.MAIN_COMMON.enableDragonEggRespawns.get()) {
			if (deadTarget.getCommandSenderWorld().equals(curServer.getLevel(World.END))) {
				if (deadTarget instanceof EnderDragonEntity) {
					EnderDragonEntity deadDragonTarget = (EnderDragonEntity) deadTarget;
					if (deadDragonTarget.getDragonFight() != null && deadDragonTarget.getDragonFight().hasPreviouslyKilledDragon()) {
						deadTarget.getCommandSenderWorld().setBlockAndUpdate(deadTarget.getCommandSenderWorld().getHeightmapPos(Heightmap.Type.MOTION_BLOCKING, EndPodiumFeature.END_PODIUM_LOCATION), Blocks.DRAGON_EGG.defaultBlockState());
					}
				}
			}
		}
	}
	
	@SubscribeEvent
	public static void onMobXPDropEvent(LivingExperienceDropEvent event) {
		PlayerEntity attackingPlayer = event.getAttackingPlayer();
		int xpValue = event.getDroppedExperience();
		
		if (attackingPlayer == null) return;
		
		if (CAConfigManager.MAIN_COMMON.enableExperienceArmorSetBonus.get()) {
			if (EntityUtil.isFullArmorSet(attackingPlayer, CAItems.EXPERIENCE_HELMET.get(), CAItems.EXPERIENCE_CHESTPLATE.get(), CAItems.EXPERIENCE_LEGGINGS.get(), CAItems.EXPERIENCE_BOOTS.get())) {
				event.setDroppedExperience(xpValue * CAConfigManager.MAIN_COMMON.experienceArmorSetXPMultiplier.get());
			}
		}

		if (CAConfigManager.MAIN_COMMON.enableExperienceSwordBonus.get()) {
			if (attackingPlayer.getMainHandItem().getItem().equals(CAItems.EXPERIENCE_SWORD.get())) {
				event.setDroppedExperience(xpValue * CAConfigManager.MAIN_COMMON.experienceSwordXPMultiplier.get());
			}
		}
		
		if (CAConfigManager.MAIN_COMMON.enableExperienceArmorSetBonus.get() && CAConfigManager.MAIN_COMMON.enableExperienceSwordBonus.get()) {
			if (EntityUtil.isFullArmorSet(attackingPlayer, CAItems.EXPERIENCE_HELMET.get(), CAItems.EXPERIENCE_CHESTPLATE.get(), CAItems.EXPERIENCE_LEGGINGS.get(), CAItems.EXPERIENCE_BOOTS.get()) && attackingPlayer.getMainHandItem().getItem().equals(CAItems.EXPERIENCE_SWORD.get())) {
				event.setDroppedExperience(xpValue * (CAConfigManager.MAIN_COMMON.experienceArmorSetXPMultiplier.get() + CAConfigManager.MAIN_COMMON.experienceSwordXPMultiplier.get()));
			}
		}
	}
	
	@SubscribeEvent
	public static void onLivingDropsEvent(LivingDropsEvent event) {
		ItemStack targetStack;
		ItemEntity drop;

		// ENDER DRAGON
		if (event.getEntityLiving() instanceof EnderDragonEntity) {
			EnderDragonEntity dragon = (EnderDragonEntity) event.getEntityLiving();

			// Drop #1: Ender Dragon Scales
			int amount = 8 + (int) (Math.random() * 6) + (int) (Math.random() * event.getLootingLevel() * 4);
			if (Objects.requireNonNull(dragon.getDragonFight()).hasPreviouslyKilledDragon()) amount /= 2; // Amount is halved with repeat kills.
			targetStack = new ItemStack(CAItems.ENDER_DRAGON_SCALE.get(), amount);
			drop = new ItemEntity(event.getEntityLiving().level, 0, 90, 0, targetStack);
			event.getDrops().add(drop);

			// Drop #2: Ender Dragon Head
			double chance = 0.1D + event.getLootingLevel() * 0.1D;
			if (Math.random() < chance && CAConfigManager.MAIN_COMMON.enderDragonHeadDrop.get()) {
				targetStack = new ItemStack(Items.DRAGON_HEAD, 1);
				drop = new ItemEntity(event.getEntityLiving().level, 0, 90, 0, targetStack);
				event.getDrops().add(drop);
			}
		}
	}
	
	@SuppressWarnings("resource")
	@SubscribeEvent
	public static void onSleepFinishedTimeEvent(SleepFinishedTimeEvent event) {
		IWorld curWorld = event.getWorld();
		
		if (curWorld instanceof ServerWorld) {
			ServerWorld curServerWorld = (ServerWorld) curWorld;
			
			if (curServerWorld.dimension().location().getNamespace().equalsIgnoreCase(ChaosAwakens.MODID)) curServerWorld.getServer().overworld().setDayTime(event.getNewTime());
		}
	}
	
	@SubscribeEvent
	public static void onEnchantLevelSetEvent(EnchantmentLevelSetEvent event) {
		World world = event.getWorld();

		for (PlayerEntity player : world.players()) {
			if (player == null) return;
			if (EntityUtil.isFullArmorSet(player, CAItems.LAPIS_HELMET.get(), CAItems.LAPIS_CHESTPLATE.get(), CAItems.LAPIS_LEGGINGS.get(), CAItems.LAPIS_BOOTS.get())) {
				ItemStack stack = event.getItem();
				int row = event.getEnchantRow();
				int power = event.getPower();
				event.getOriginalLevel();
				switch (row) {
				default:
					break;
				case 1:
					event.setLevel(event.getLevel() - 5);
					if (event.getLevel() <= 0) {
						event.setLevel(EnchantmentHelper.getEnchantmentCost(world.random, row, power, stack));
					}
					break;
				case 2:
					event.setLevel(event.getLevel() - 5);

					if (event.getLevel() <= 0) {
						event.setLevel(EnchantmentHelper.getEnchantmentCost(world.random, row, power, stack));
					}
					break;
				case 3:
					event.setLevel(event.getLevel() - 5);
					if (event.getLevel() <= 0) {
						event.setLevel(EnchantmentHelper.getEnchantmentCost(world.random, row, power, stack));
					}
					break;
				}
			}
		}
	}
	
	@SubscribeEvent
	public static void onBlockBreakXPEvent(BreakEvent event) {
		PlayerEntity curPlayer = event.getPlayer();
		int xpValue = event.getExpToDrop();
		
		if (curPlayer == null) return;

		if (CAConfigManager.MAIN_COMMON.enableExperienceArmorSetBonus.get()) {
			if (EntityUtil.isFullArmorSet(curPlayer, CAItems.EXPERIENCE_HELMET.get(), CAItems.EXPERIENCE_CHESTPLATE.get(), CAItems.EXPERIENCE_LEGGINGS.get(), CAItems.EXPERIENCE_BOOTS.get())) {
				event.setExpToDrop(xpValue * CAConfigManager.MAIN_COMMON.experienceArmorSetXPMultiplier.get());
			}
		}

		if (curPlayer instanceof LivingEntity) {
			if (event.isCancelable() && curPlayer.hasEffect(CAEffects.PARALYSIS_EFFECT.get())) event.setCanceled(true);
		}
	}
	
	@SubscribeEvent
	public static void onBlockToolInteractEvent(BlockToolInteractEvent event) {
		//TODO Replace with base spreadabledirtblock class or something
		if (event.getToolType().equals(ToolType.HOE)) {
			if (event.getState().is(CATags.Blocks.DENSE_DIRT)) event.setFinalState(CABlocks.DENSE_FARMLAND.get().defaultBlockState());
			else if (event.getState().is(CATags.Blocks.TERRA_PRETA)) event.setFinalState(CABlocks.TERRA_PRETA_FARMLAND.get().defaultBlockState());
		}
	}
	
	@SubscribeEvent
	public static void onLivingKnockbackEvent(LivingKnockBackEvent event) {
		LivingEntity target = event.getEntityLiving();
		
		if (target != null) { //TODO Make this a common interface impl :bruh:
			if (target instanceof AnimatableMonsterEntity) {
				AnimatableMonsterEntity monsterTarget = (AnimatableMonsterEntity) target;
				if (!monsterTarget.canBeKnockedBack()) event.setCanceled(true);

				float curKnockbackMagnitude = event.getStrength();
				float knockbackReductionPercentage = monsterTarget.getDeltaKnockbackResistance();
				float newKnockbackStrength = MathUtil.reduceByPercentage(curKnockbackMagnitude, knockbackReductionPercentage);

				event.setStrength(newKnockbackStrength);
			}
			
			if (target instanceof AnimatableAnimalEntity) {
				AnimatableAnimalEntity animalTarget = (AnimatableAnimalEntity) target;
				if (!animalTarget.canBeKnockedBack()) event.setCanceled(true);
			}
			
			if (target instanceof AnimatableFishEntity) {
				AnimatableFishEntity fishTarget = (AnimatableFishEntity) target;
				if (!fishTarget.canBeKnockedBack()) event.setCanceled(true);
			}
			
			if (target instanceof AnimatableWaterMobEntity) {
				AnimatableWaterMobEntity waterMobTarget = (AnimatableWaterMobEntity) target;
				if (!waterMobTarget.canBeKnockedBack()) event.setCanceled(true);
			}
			
			if (target.isUsingItem() && target.getUseItem().getItem() instanceof AnimatableShieldItem) {
				AnimatableShieldItem animatableShield = (AnimatableShieldItem) target.getUseItem().getItem();
				float curKnockbackMagnitude = event.getStrength();
				float knockbackReductionPercentage = animatableShield.getShieldMaterial().getKnockbackResistance();
				float newKnockbackStrength = MathUtil.reduceByPercentage(curKnockbackMagnitude, knockbackReductionPercentage);
				
				event.setStrength(newKnockbackStrength);
			}
		}
	}
	
	// Account for paralysis actually taking full effect
	@SubscribeEvent
	public static void onLivingJumpEvent(LivingJumpEvent event) {
		if (event.isCancelable() && event.getEntityLiving() instanceof PlayerEntity && event.getEntityLiving().hasEffect(CAEffects.PARALYSIS_EFFECT.get())) event.setCanceled(true);
	}
	
	@SubscribeEvent
	public static void onPlayerInteractEvent(PlayerInteractEvent event) {
		if (event.isCancelable() && event.getEntityLiving() instanceof PlayerEntity && event.getEntityLiving().hasEffect(CAEffects.PARALYSIS_EFFECT.get())) event.setCanceled(true);
	}
	
	@SubscribeEvent
	public static void onLivingAttackEntityEvent(AttackEntityEvent event) {
		if (event.isCancelable() && event.getEntityLiving() instanceof PlayerEntity && event.getEntityLiving().hasEffect(CAEffects.PARALYSIS_EFFECT.get())) event.setCanceled(true);
	}
	
	@SubscribeEvent
	public static void onLivingUseItemEvent(LivingEntityUseItemEvent event) {
		if (event.isCancelable() && event.getEntityLiving() instanceof PlayerEntity && event.getEntityLiving().hasEffect(CAEffects.PARALYSIS_EFFECT.get())) event.setCanceled(true);
	}
	
	@SubscribeEvent
	public static void onLivingBlockPlaceEvent(EntityPlaceEvent event) {
		if (event.getEntity() instanceof LivingEntity) {
			if (event.isCancelable() && ((LivingEntity) event.getEntity()) instanceof PlayerEntity && ((LivingEntity) event.getEntity()).hasEffect(CAEffects.PARALYSIS_EFFECT.get())) event.setCanceled(true);
		}
	}
	
	@SubscribeEvent
	public static void onBucketFillEvent(FillBucketEvent event) {
		if (event.isCancelable() && event.getEntityLiving() instanceof PlayerEntity && event.getEntityLiving().hasEffect(CAEffects.PARALYSIS_EFFECT.get())) event.setCanceled(true);
	}
	
	@SubscribeEvent
	public static void onPlayerItemPickupEvent(ItemPickupEvent event) {
		if (event.isCancelable() && event.getEntityLiving() instanceof PlayerEntity && event.getEntityLiving().hasEffect(CAEffects.PARALYSIS_EFFECT.get())) event.setCanceled(true);
	}
	
	// Client side events to prevent the player/entity from swinging their hand or interacting while paralyzed
	@SubscribeEvent
	public static void onPlayerLeftClickInteractEmptyEvent(LeftClickEmpty event) {
		if (event.isCancelable() && event.getEntityLiving() instanceof PlayerEntity && event.getEntityLiving().hasEffect(CAEffects.PARALYSIS_EFFECT.get())) event.setCanceled(true);
	}

	@SubscribeEvent
	public static void onPlayerRightClickInteractEmptyEvent(RightClickEmpty event) {
		if (event.isCancelable() && event.getEntityLiving() instanceof PlayerEntity && event.getEntityLiving().hasEffect(CAEffects.PARALYSIS_EFFECT.get())) event.setCanceled(true);
	}

	@SubscribeEvent
	public static void onPlayerLeftClickInteractBlockEvent(LeftClickBlock event) {
		if (event.isCancelable() && event.getEntityLiving() instanceof PlayerEntity && event.getEntityLiving().hasEffect(CAEffects.PARALYSIS_EFFECT.get())) event.setCanceled(true);
	}

	@SubscribeEvent
	public static void onPlayerRightClickInteractBlockEvent(RightClickBlock event) {
		if (event.isCancelable() && event.getEntityLiving() instanceof PlayerEntity && event.getEntityLiving().hasEffect(CAEffects.PARALYSIS_EFFECT.get())) event.setCanceled(true);
	}
	
	@SubscribeEvent
	public static void onPlayerCommand(CommandEvent event) throws CommandSyntaxException {
		String command = event.getParseResults().getReader().getString();
		Entity e = event.getParseResults().getContext().getSource().getEntity();
		if (e != null && "chaosawakens".equals(e.level.dimension().location().getNamespace())
				&& command.contains("/weather")) {
			event.setParseResults(event.getParseResults().getContext().getDispatcher()
					.parse("execute in minecraft:overworld run ".concat(
							command.substring(1)), event.getParseResults().getContext().getSource()));
		}
	}
}
