package io.github.chaosawakens.common.items;

import java.util.List;

import java.util.Map;

import javax.annotation.Nullable;

import com.google.common.collect.Maps;

import io.github.chaosawakens.api.IUtilityHelper;
import io.github.chaosawakens.common.registry.CAItems;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.SpawnReason;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemUseContext;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.server.MinecraftServer;
import net.minecraft.stats.Stats;
import net.minecraft.util.ActionResultType;
import net.minecraft.util.Hand;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.RayTraceContext.FluidMode;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.util.registry.Registry;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.StringTextComponent;
import net.minecraft.world.World;

public class FilledCritterCageItem extends Item implements IUtilityHelper{
	
	//Functions similar to a spawn egg, for now -Meme Man
	final EntityType<?> critterEntity;
	public static final Map<EntityType<?>, FilledCritterCageItem> ENTITY_ID = Maps.newIdentityHashMap();
//	final CompoundNBT mobNBT;
	
	public FilledCritterCageItem(Properties p, EntityType<?> mobContained) {
		super(p);
		this.critterEntity = mobContained;
		if (mobContained != null) {
			ENTITY_ID.put(mobContained, this);
		}
//		this.critterEntityLiving = critterEntityLiving;
//		this.mobNBT = getNBTDataFromLivingEntity(critterEntityLiving);
	}
	
	@Override
	public int getItemStackLimit(ItemStack stack) {
		return 1;
	}
	
	public ItemStack createEmptyCritterCage(ItemStack critterCage, ItemStack cageWithMob, LivingEntity mobToRelease, PlayerEntity player, Hand hand, World world) {
		boolean instaBuild = player.abilities.instabuild;
		
		if (instaBuild && CritterCageItem.canUseCritterCage(mobToRelease.getType(), cageWithMob)) {
			if (!player.inventory.contains(critterCage)) {
				player.inventory.add(critterCage);
			}
			
			return cageWithMob;
			
		} else {
			
			if (!instaBuild) {
				cageWithMob.shrink(1);
			}
			
			if (cageWithMob.isEmpty()) {
				return critterCage;
			} else {
				
				if (!player.inventory.add(critterCage)) {
					player.drop(critterCage, false);
				}
				
				if (critterCage.getItem().getTags() != null) {
					critterCage.setTag(null);
				}
				return cageWithMob;
			}
			
		}
	}
	
	@Override
	public ActionResultType useOn(ItemUseContext context) {
		PlayerEntity player = context.getPlayer();
		
		if (player == null) {
			return ActionResultType.PASS;
		}
		
		final World playerWorld = context.getLevel();
        final Hand hand = context.getHand();
        final BlockPos pos = context.getClickedPos().above();
        final ItemStack entityItem = player.getItemInHand(hand);
   //     final String typeTag = entityItem.getOrCreateTag().getString("entity");
        final MinecraftServer serverMC = playerWorld.getServer();
        final LivingEntity critterEntityLiving = getEntityFromStack(entityItem, playerWorld, true);
        final CompoundNBT entityNBTTagData = getNBTDataFromLivingEntity(critterEntityLiving);
        final EntityType<?> cType = getType(entityItem.getTag());
        
        if (!playerWorld.isClientSide) {
        	if (entityNBTTagData == null) {
        		return ActionResultType.PASS;
        	}
        	
        	RayTraceResult raytraceresult = getPlayerPOVHitResult(playerWorld, player, FluidMode.SOURCE_ONLY);
        	
        	if (raytraceresult.getType() == RayTraceResult.Type.ENTITY) {
        		return ActionResultType.FAIL;
        	}
        	
        	ItemStack emptyCritterCage = new ItemStack(CAItems.CRITTER_CAGE.get());
        	entityItem.getOrCreateTag().put("EntityDataFilled", entityNBTTagData);
        	
        	assert critterEntityLiving != null;
        	critterEntityLiving.setPos(pos.getX(), pos.getY(), pos.getZ());
        	critterEntityLiving.readAdditionalSaveData(entityNBTTagData);
        //	critterEntityLiving.getType().create(playerWorld);
        //	serverMC.overworld().addFreshEntity(critterEntityLiving);
        	cType.spawn(serverMC.overworld(), entityItem, player, pos, SpawnReason.SPAWN_EGG, true, true);
        	createEmptyCritterCage(emptyCritterCage, entityItem, critterEntityLiving, player, hand, playerWorld);
        }
        
        player.awardStat(Stats.ITEM_USED.get(this));
        return ActionResultType.SUCCESS;
	}
	
	
	public EntityType<?> getType(@Nullable CompoundNBT p_208076_1_) {	
		if (p_208076_1_ != null && p_208076_1_.contains("EntityDataFilled", 4)) {
			CompoundNBT compoundnbt = p_208076_1_.getCompound("EntityDataFilled");	 
			if (compoundnbt.contains("id", 2)) {	 
				return EntityType.byString(compoundnbt.getString("id")).orElse(this.critterEntity);	 
			}	 
		}	
		return this.critterEntity;
	}
	
	@Override
	public void appendHoverText(ItemStack stack, World world, List<ITextComponent> tooltip, ITooltipFlag flag) {
		if (world != null) {
		    LivingEntity critterEntityLiving = getEntityFromStack(stack, world, true);
		    EntityType<?> cType = getType(stack.getTag());
			if (critterEntityLiving != null) {
				tooltip.add(new StringTextComponent("Mob: " + cType.getRegistryName().toString()));
				tooltip.add(new StringTextComponent("Health: " + critterEntityLiving.getHealth()));
			}
		}
	}
	
	@Override
	public CompoundNBT readFromNBT(CompoundNBT nbtToRead) {
		nbtToRead.get("entity");
		nbtToRead.get("id");
		nbtToRead.get("health");
		nbtToRead.get("EntityDataFilled");
		return nbtToRead;
	}
	
	@Override
	public CompoundNBT writeToNBT() {
		final CompoundNBT dataNBT = new CompoundNBT();
		return dataNBT;
	}
	
	@Override
	public CompoundNBT getNBTDataFromLivingEntity(LivingEntity entityToGetNBTDataFrom) {
		CompoundNBT entityDataNBT = new CompoundNBT();
		entityDataNBT.putString("entity", entityToGetNBTDataFrom.getType().getRegistryName().toString());
		entityDataNBT.putString("id", EntityType.getKey(entityToGetNBTDataFrom.getType()).toString());
		entityDataNBT.putFloat("health", entityToGetNBTDataFrom.getHealth());
		entityDataNBT.putString("EntityDataFilled", entityToGetNBTDataFrom.getEntityData().toString());
		entityToGetNBTDataFrom.addAdditionalSaveData(entityDataNBT);
		return entityDataNBT;
	}	
	  
	@SuppressWarnings({ "deprecation", "unused" })
	public LivingEntity getEntityFromNBT(CompoundNBT nbt, World world, boolean withInfo) {
		LivingEntity entity = (LivingEntity) Registry.ENTITY_TYPE.get(new ResourceLocation(nbt.getString("entity"))).create(world);
		EntityType<?> eType = entity.getType();
		eType = critterEntity;
		assert getEntityFromNBT(nbt, world, withInfo) != null;
		if (withInfo) {
			entity.load(nbt);
		}
		return entity;
	}
	
	  
	public LivingEntity getEntityFromStack(ItemStack stack, World world, boolean withInfo) {
		return getEntityFromNBT(stack.getOrCreateTag().getCompound("EntityDataFilled"), world, withInfo);	
	}
	
	/*@Nonnull
	@OnlyIn(Dist.CLIENT)
	@Override
	public ITextComponent getName(ItemStack stack) {
		 @SuppressWarnings("resource")
		World world = Minecraft.getInstance().level;
	        if (world == null) {
	            return super.getName(stack);
	        } else {
	      //      final String name = stack.getOrCreateTag().getString("Name");
	            return new TranslationTextComponent("crittertext.mob.name."+ nameOfMob);
	        }
	}*/

}
