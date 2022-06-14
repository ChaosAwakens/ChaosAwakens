package io.github.chaosawakens.common.items;

import java.util.List;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

import io.github.chaosawakens.api.IUtilityHelper;
import io.github.chaosawakens.common.registry.CATags;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.ai.attributes.Attributes;
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
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

public class CritterCageItem extends Item implements IUtilityHelper{
	
    public CritterCageItem(Properties properties) {
        super(properties);
    }

    @Override
    public ActionResultType useOn(ItemUseContext context) {
        PlayerEntity player = context.getPlayer();
        BlockPos pos = context.getClickedPos();
        Direction facing = context.getClickedFace();
        World worldIn = context.getLevel();
        ItemStack stack = context.getItemInHand();
        if (!release(player, pos, facing, worldIn, stack)) return ActionResultType.FAIL;
        return ActionResultType.SUCCESS;
    }
    
    @SuppressWarnings("unused")
	@Override
    public ActionResultType interactLivingEntity(ItemStack stack, PlayerEntity playerIn, LivingEntity target, Hand hand) {
    	stack = playerIn.getMainHandItem();
        ItemStack stackFill = new ItemStack(this);
        //Note: Nesting apparently works better in this case than guard clause -Meme Man
        if (capture(stackFill, playerIn, target)) {
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
            
            if (stackFill == stack) {
            	return ActionResultType.SUCCESS;
            }
       
            if (stack.getCount() > 1 || stackFill.getCount() > 1) {
            	if (!containsEntity(stack) || !containsEntity(stackFill)) {
                  	stack.shrink(1);    
                  	playerIn.inventory.add(stackFill);
                  	if (playerIn.inventory.getFreeSlot() <= 0) {
                  		//PlayerEntity.drop(itemstack, includename) doesn't work for some reason
                  		//This still doesn't work btw
                  		playerIn.drop(stackFill, true, true);
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
	public boolean capture(ItemStack stack, PlayerEntity player, LivingEntity target) {
        if (target.getCommandSenderWorld().isClientSide) return false;
        if (target instanceof PlayerEntity || !target.isAlive()) return false;
        if (containsEntity(stack)) return false;
        if (isBlacklisted(target.getType())) return false;
        
        CompoundNBT nbt = new CompoundNBT();
        nbt.putString("entity", EntityType.getKey(target.getType()).toString());
        nbt.putString("entityName", target.getName().getString());
        nbt.putDouble("entityMaxHealth", target.getAttribute(Attributes.MAX_HEALTH).getValue());
        nbt.putBoolean("isBaby", target.isBaby());
        nbt.putString("entityRegName", target.getType().getRegistryName().toString());
                
        target.saveWithoutId(nbt);
        stack.setTag(nbt);
        
        stack.setCount(1);
        return true;
    }
    
	@SuppressWarnings("resource")
	public boolean release(PlayerEntity player, BlockPos pos, Direction facing, World worldIn, ItemStack stack) {
        if (player.getCommandSenderWorld().isClientSide) return false;
        if (!containsEntity(stack)) return false;
        Entity entity = getEntityFromStack(stack, worldIn, true);
        BlockPos blockPos = pos.relative(facing);
        entity.absMoveTo(blockPos.getX() + 0.5, blockPos.getY(), blockPos.getZ() + 0.5, 0, 0);
        stack.setTag(null);
        worldIn.addFreshEntity(entity);
        return true;
    }
    
    public boolean isBlacklisted(EntityType<?> entity) {
        return entity.is(CATags.EntityTypes.CRITTER_CAGE_BLACKLISTED);
    }

    public boolean containsEntity(ItemStack stack) {
    	if (stack == null) return false;
        return !stack.isEmpty() && stack.hasTag() && stack.getTag().contains("entity");
    }

    @Override
    public void appendHoverText(ItemStack stack, World world, List<ITextComponent> tooltip, ITooltipFlag flag) {
        if (containsEntity(stack)) {
            tooltip.add(new StringTextComponent("Mob: " + getMobID(stack)));
            tooltip.add(new StringTextComponent("Mob Name: " + getMobName(stack)));
            tooltip.add(new StringTextComponent("Health: " + stack.getTag().getFloat("Health") + "/" + stack.getTag().getDouble("entityMaxHealth")));
            tooltip.add(new StringTextComponent("Is Baby: " + stack.getTag().getBoolean("isBaby")));
        }
    }

	@SuppressWarnings("rawtypes")
	@Nullable
    public LivingEntity getEntityFromStack(ItemStack stack, World world, boolean withInfo) {
        if (stack.hasTag()) {
            EntityType type = EntityType.byString(stack.getTag().getString("entity")).orElse(null);
            if (type != null) {
            	LivingEntity entity = (LivingEntity) type.create(world);
                if (withInfo) {
                    entity.load(stack.getTag());
                } else if (!type.canSummon()) {
                    return null;
                }
                return entity;
            }
        }
        return null;
    }

    public String getMobID(ItemStack stack) {
        return stack.getTag().getString("entity");
    }

    public String getMobName(ItemStack stack) {
        return stack.getTag().getBoolean("isBaby") ? "Baby " +  stack.getTag().getString("entityName"): stack.getTag().getString("entityName");
    }

    @Nonnull
    @OnlyIn(Dist.CLIENT)
    @Override
    public ITextComponent getName(ItemStack stack) {
        if (!containsEntity(stack)) return new TranslationTextComponent("item.chaosawakens.critter_cage");
        return new TranslationTextComponent("item.chaosawakens.critter_cage").append(" (" + getMobName(stack) + ")");
    }
}
