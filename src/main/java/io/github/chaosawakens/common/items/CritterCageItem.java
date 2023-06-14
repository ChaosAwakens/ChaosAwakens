package io.github.chaosawakens.common.items;

import java.util.List;

import javax.annotation.Nonnull;

import io.github.chaosawakens.common.registry.CATags;
import io.github.chaosawakens.common.util.ObjectUtil;
import io.github.chaosawakens.manager.CAConfigManager;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.ai.attributes.Attributes;
import net.minecraft.entity.merchant.villager.VillagerEntity;
import net.minecraft.entity.merchant.villager.VillagerProfession;
import net.minecraft.entity.passive.TameableEntity;
import net.minecraft.entity.passive.WolfEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemUseContext;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.util.ActionResultType;
import net.minecraft.util.Direction;
import net.minecraft.util.Hand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.StringTextComponent;
import net.minecraft.util.text.TranslationTextComponent;
import net.minecraft.world.World;

public class CritterCageItem extends Item {
	
	public CritterCageItem(Properties properties) {
		super(properties);
	}
	
	@Override
	public ActionResultType useOn(ItemUseContext context) {
		PlayerEntity owner = context.getPlayer();
		BlockPos targetPos = context.getClickedPos();
		Direction facingDirection = context.getClickedFace();
		World curWorld = context.getLevel();
		ItemStack curStack = context.getItemInHand();
		
		if (!canReleaseEntity(owner, targetPos, facingDirection, curWorld, curStack)) return ActionResultType.FAIL;
		return ActionResultType.SUCCESS;
	}
	
	@SuppressWarnings("unused")
	@Override
	public ActionResultType interactLivingEntity(ItemStack stack, PlayerEntity playerIn, LivingEntity target, Hand hand) {
		stack = playerIn.getMainHandItem();
		ItemStack stackFill = getDefaultInstance();
		//Note: Nesting apparently works better in this case than guard clause --Meme Man
		if (shouldCaptureEntity(stackFill, playerIn, target)) {
			if (stack.getCount() == 1) {
				if (!containsEntity(stack) || stack.isEmpty()) {
				  	stack = stackFill;   
					playerIn.setItemInHand(hand, stack);   
					target.remove(true);
					return ActionResultType.SUCCESS;
				}
				
				playerIn.setItemInHand(hand, stack);
				return ActionResultType.FAIL;
			}
			
			if (stackFill == stack) return ActionResultType.SUCCESS;
	   
			if (stack.getCount() > 1 || stackFill.getCount() > 1) {
				if (!containsEntity(stack) || !containsEntity(stackFill)) {
				  	stack.shrink(1);
				  	playerIn.inventory.add(stackFill);
				  	if (playerIn.inventory.getFreeSlot() <= 0) {
//				  	PlayerEntity.drop(itemstack, includename) // doesn't work for some reason
				  		playerIn.drop(stackFill, true, true); // This still doesn't work btw
				  	}
					target.remove(true);
					return ActionResultType.SUCCESS;
				}
				boolean add = playerIn.inventory.add(stackFill);
				add = false;
				return ActionResultType.FAIL;
			} 
			return ActionResultType.SUCCESS;
		}
		return ActionResultType.FAIL;
	}
	
	@SuppressWarnings("resource")
	public boolean shouldCaptureEntity(ItemStack stack, PlayerEntity player, LivingEntity target) {
		if (target.getCommandSenderWorld().isClientSide) return false;
		if (target instanceof PlayerEntity || !target.isAlive()) return false;
		if (containsEntity(stack)) return false;
		if (isBlacklisted(target.getType())) return false;
		if (target.getBbWidth() > 2.0F || target.getBbHeight() > 3.125F) return false; //Ent size or higher
		
		CompoundNBT critterCageData = new CompoundNBT();
		
		critterCageData.putString("entity", EntityType.getKey(target.getType()).toString());
		critterCageData.putString("entityName", target.getName().getString());
		critterCageData.putDouble("entityMaxHealth", target.getAttribute(Attributes.MAX_HEALTH).getValue());
		critterCageData.putBoolean("isBaby", target.isBaby());
		critterCageData.putBoolean("isVillager", target instanceof VillagerEntity);
		critterCageData.putBoolean("isTameable", target instanceof TameableEntity);
		
		if (critterCageData.getBoolean("isVillager")) {
			VillagerEntity targetVillager = (VillagerEntity) target;
			
			critterCageData.putString("entityName", targetVillager.getName().getString() + " Villager");
			critterCageData.putString("villagerProfession", targetVillager.getVillagerData().getProfession() != VillagerProfession.NONE ? ObjectUtil.capitalizeFirstLetter(targetVillager.getVillagerData().getProfession().toString()) : "None");
			critterCageData.putInt("villagerTradingLevel", targetVillager.getVillagerData().getLevel());		
		}
		
		if (critterCageData.getBoolean("isTameable")) {
			TameableEntity targetTameable = (TameableEntity) target;
			PlayerEntity playerOwner = (PlayerEntity) targetTameable.getOwner();
			
			critterCageData.putString("owner", targetTameable.getOwner() instanceof PlayerEntity ? playerOwner.getScoreboardName() : "None");
			if (targetTameable instanceof WolfEntity) {
				WolfEntity targetWolf = (WolfEntity) targetTameable;
				critterCageData.putString("collarColor", targetWolf.isTame() ? ObjectUtil.capitalizeFirstLetter(targetWolf.getCollarColor().toString()) : "None");
			}
		}
		
		critterCageData.putBoolean("enchanted", target.getType().toString().contains("enchanted"));
		critterCageData.putString("entityRegName", target.getType().getRegistryName().toString());
		
		target.saveWithoutId(critterCageData);
		stack.setTag(critterCageData);
		
		stack.setCount(1);
		return true;
	}
	
	@SuppressWarnings("resource")
	public boolean canReleaseEntity(PlayerEntity player, BlockPos pos, Direction facing, World worldIn, ItemStack stack) {
		if (player.getCommandSenderWorld().isClientSide) return false;
		if (!containsEntity(stack)) return false;
		
		Entity curHeldEntity = getEntityFromStack(stack, worldIn, true);
		BlockPos relPos = pos.relative(facing);
		
		curHeldEntity.absMoveTo(relPos.getX() + 0.5, relPos.getY(), relPos.getZ() + 0.5, 0, 0);
		stack.setTag(null);
		worldIn.addFreshEntity(curHeldEntity);
		return true;
	}
	
	public boolean isBlacklisted(EntityType<?> targetEntity) {
		return targetEntity.is(CATags.EntityTypes.CRITTER_CAGE_BLACKLISTED);
	}
	
	public boolean containsEntity(ItemStack stack) {
		if (stack == null) return false;
		return !stack.isEmpty() && stack.hasTag() && stack.getTag().contains("entity");
	}
	
	@Override
	public void appendHoverText(ItemStack stack, World world, List<ITextComponent> tooltip, ITooltipFlag flag) {
		if (containsEntity(stack) && CAConfigManager.MAIN_CLIENT.enableCritterCageTooltips.get()) {
			if (CAConfigManager.MAIN_CLIENT.enableCritterCageMobIDToolTip.get()) tooltip.add(new StringTextComponent("Mob: " + getMobID(stack)));
			if (CAConfigManager.MAIN_CLIENT.enableCritterCageMobNameToolTip.get()) tooltip.add(new StringTextComponent("Mob Name: " + getMobName(stack)));
			if (CAConfigManager.MAIN_CLIENT.enableCritterCageMobHealthToolTip.get()) tooltip.add(new StringTextComponent("Health: " + stack.getTag().getFloat("Health") + "/" + stack.getTag().getDouble("entityMaxHealth")));
		//	tooltip.add(new StringTextComponent("Is Baby: " + stack.getTag().getBoolean("isBaby"))); -Unneeded for now --Meme Man
			if (stack.getTag() != null && stack.getTag().getBoolean("isVillager")) {
				LivingEntity target = getEntityFromStack(stack, world, true);
				//Extra check
				if (target instanceof VillagerEntity) {
					if (CAConfigManager.MAIN_CLIENT.enableCritterCageVillagerProfessionToolTip.get()) tooltip.add(new StringTextComponent("Profession: " + stack.getTag().getString("villagerProfession")));
					if (CAConfigManager.MAIN_CLIENT.enableCritterCageVillagerTradingLevelToolTip.get()) {
						switch (stack.getTag().getInt("villagerTradingLevel")) {
						default: if (stack.getTag().getString("villagerProfession").equalsIgnoreCase("none")) tooltip.add(new StringTextComponent("Trading Level: None"));
						case 1: if (stack.getTag().getInt("villagerTradingLevel") == 1 && !stack.getTag().getString("villagerProfession").equalsIgnoreCase("none")) tooltip.add(new StringTextComponent("Trading Level: Novice"));
						case 2: if (stack.getTag().getInt("villagerTradingLevel") == 2) tooltip.add(new StringTextComponent("Trading Level: Apprentice"));
						case 3: if (stack.getTag().getInt("villagerTradingLevel") == 3) tooltip.add(new StringTextComponent("Trading Level: Journeyman"));
						case 4: if (stack.getTag().getInt("villagerTradingLevel") == 4) tooltip.add(new StringTextComponent("Trading Level: Expert"));
						case 5: if (stack.getTag().getInt("villagerTradingLevel") == 5) tooltip.add(new StringTextComponent("Trading Level: Master"));
						}
					}
				}
			}
			
			if (stack.getTag() != null && stack.getTag().getBoolean("isTameable")) {
				LivingEntity target = getEntityFromStack(stack, world, true);
				
				if (target instanceof TameableEntity) {
					if (CAConfigManager.MAIN_CLIENT.enableCritterCageMobOwnerToolTip.get()) tooltip.add(new StringTextComponent("Owner: " + stack.getTag().getString("owner")));
					if (target instanceof WolfEntity) {
						if (CAConfigManager.MAIN_CLIENT.enableCritterCageMobCollarColorToolTip.get()) tooltip.add(new StringTextComponent("Collar Color: " + stack.getTag().getString("collarColor")));
					}
				}
			}
			
 		}
	}
	
	public LivingEntity getEntityFromStack(ItemStack targetStack, World world, boolean withInfo) {
		if (targetStack.hasTag()) {
			EntityType<?> type = EntityType.byString(targetStack.getTag().getString("entity")).orElse(null);
			
			if (type != null) {
				LivingEntity targetEntity = (LivingEntity) type.create(world);
				
				if (withInfo) targetEntity.load(targetStack.getTag());
				else if (!type.canSummon()) return null;
				return targetEntity;
			}
		}
		return null;
	}
	
	@Override
	public boolean isFoil(ItemStack stack) {
		if (stack.hasTag() && stack.getTag().contains("enchanted")) return stack.getTag().getBoolean("enchanted");
		return false;
	}
	
	public String getMobID(ItemStack stack) {
		return stack.getTag().getString("entity");
	}
	
	public String getMobName(ItemStack stack) {
		return stack.getTag().getBoolean("isBaby") ? "Baby " +  stack.getTag().getString("entityName"): stack.getTag().getString("entityName");
	}
	
	@Nonnull
	@Override
	public ITextComponent getName(ItemStack stack) {
		if (!containsEntity(stack)) return new TranslationTextComponent("item.chaosawakens.critter_cage");
		return new TranslationTextComponent("item.chaosawakens.critter_cage").append(" (" + getMobName(stack) + ")");
	}
}
