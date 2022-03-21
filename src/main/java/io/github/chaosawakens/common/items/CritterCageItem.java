package io.github.chaosawakens.common.items;

import java.util.ArrayList;



import java.util.List;

import javax.annotation.Nullable;

import io.github.chaosawakens.api.IUtilityHelper;
import io.github.chaosawakens.common.registry.CAEntityTypes;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.util.ActionResultType;
import net.minecraft.util.Hand;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.registry.Registry;
import net.minecraft.world.World;

public class CritterCageItem extends Item implements IUtilityHelper{
	
	protected static List<EntityType<?>> critterEntityBlacklist = new ArrayList<>();
	//Type t;
	Properties p;

	public CritterCageItem(Properties p) {
		super(p);
		this.p = p;
	//	assert t != null;
	}
	
	public static boolean canUseCritterCage(EntityType<?> entityToCage, ItemStack stack) {
		return !(critterEntityBlacklist.contains(entityToCage)) && !itemHasCritterCageMobAssigned(stack);
	}
	
	public List<EntityType<?>> addEntityToCritterCageBlacklist() {
		critterEntityBlacklist.add(EntityType.ENDER_DRAGON);
		critterEntityBlacklist.add(EntityType.ELDER_GUARDIAN);
		critterEntityBlacklist.add(EntityType.WITHER);
		return critterEntityBlacklist;
	}
	
	//TODO Make this method and others similar to it more efficient/less clunky
	@Override
	public List<EntityType<? extends LivingEntity>> addChaosEntityToCAEntityList(EntityType<? extends LivingEntity> CAMobToAdd) {
		CHAOS_AWAKENS_ENTITIES.add(CAEntityTypes.ACACIA_ENT.get());
		CHAOS_AWAKENS_ENTITIES.add(CAEntityTypes.APPLE_COW.get());
		CHAOS_AWAKENS_ENTITIES.add(CAEntityTypes.BEAVER.get());
		CHAOS_AWAKENS_ENTITIES.add(CAEntityTypes.BIRCH_ENT.get());
		CHAOS_AWAKENS_ENTITIES.add(CAEntityTypes.BIRD.get());
		CHAOS_AWAKENS_ENTITIES.add(CAEntityTypes.BROWN_ANT.get());
		CHAOS_AWAKENS_ENTITIES.add(CAEntityTypes.CARROT_PIG.get());
		CHAOS_AWAKENS_ENTITIES.add(CAEntityTypes.CRIMSON_ENT.get());
		CHAOS_AWAKENS_ENTITIES.add(CAEntityTypes.CRYSTAL_APPLE_COW.get());
		CHAOS_AWAKENS_ENTITIES.add(CAEntityTypes.DARK_OAK_ENT.get());
		CHAOS_AWAKENS_ENTITIES.add(CAEntityTypes.DIMETRODON.get());
		CHAOS_AWAKENS_ENTITIES.add(CAEntityTypes.EMERALD_GATOR.get());
		CHAOS_AWAKENS_ENTITIES.add(CAEntityTypes.ENCHANTED_GOLDEN_APPLE_COW.get());
		CHAOS_AWAKENS_ENTITIES.add(CAEntityTypes.ENCHANTED_GOLDEN_CARROT_PIG.get());
		CHAOS_AWAKENS_ENTITIES.add(CAEntityTypes.FROG.get());
		CHAOS_AWAKENS_ENTITIES.add(CAEntityTypes.GOLDEN_APPLE_COW.get());
		CHAOS_AWAKENS_ENTITIES.add(CAEntityTypes.GOLDEN_CARROT_PIG.get());
		CHAOS_AWAKENS_ENTITIES.add(CAEntityTypes.GREEN_FISH.get());
		CHAOS_AWAKENS_ENTITIES.add(CAEntityTypes.HERCULES_BEETLE.get());
		CHAOS_AWAKENS_ENTITIES.add(CAEntityTypes.JUNGLE_ENT.get());
		CHAOS_AWAKENS_ENTITIES.add(CAEntityTypes.LAVA_EEL.get());
		CHAOS_AWAKENS_ENTITIES.add(CAEntityTypes.OAK_ENT.get());
		CHAOS_AWAKENS_ENTITIES.add(CAEntityTypes.RAINBOW_ANT.get());
		CHAOS_AWAKENS_ENTITIES.add(CAEntityTypes.RED_ANT.get());
		CHAOS_AWAKENS_ENTITIES.add(CAEntityTypes.ROBO_SNIPER.get());
		CHAOS_AWAKENS_ENTITIES.add(CAEntityTypes.ROBO_WARRIOR.get());
		CHAOS_AWAKENS_ENTITIES.add(CAEntityTypes.ROCK_FISH.get());
		CHAOS_AWAKENS_ENTITIES.add(CAEntityTypes.RUBY_BUG.get());
		CHAOS_AWAKENS_ENTITIES.add(CAEntityTypes.SPARK_FISH.get());
		CHAOS_AWAKENS_ENTITIES.add(CAEntityTypes.SPRUCE_ENT.get());
		CHAOS_AWAKENS_ENTITIES.add(CAEntityTypes.STINK_BUG.get());
		CHAOS_AWAKENS_ENTITIES.add(CAEntityTypes.UNSTABLE_ANT.get());
		CHAOS_AWAKENS_ENTITIES.add(CAEntityTypes.WARPED_ENT.get());
		CHAOS_AWAKENS_ENTITIES.add(CAEntityTypes.WASP.get());
		CHAOS_AWAKENS_ENTITIES.add(CAEntityTypes.WHALE.get());
		CHAOS_AWAKENS_ENTITIES.add(CAEntityTypes.WOOD_FISH.get());
		return IUtilityHelper.super.addChaosEntityToCAEntityList(CAMobToAdd);
	}
	
	@Override
	public List<EntityType<? extends LivingEntity>> addVanillaEntityToVanillaEntityList(EntityType<? extends LivingEntity> VanillaMobToAdd) {
		VANILLA_ENTITIES.add(EntityType.BAT);
		VANILLA_ENTITIES.add(EntityType.BEE);
		VANILLA_ENTITIES.add(EntityType.BLAZE);
		VANILLA_ENTITIES.add(EntityType.CAT);
		VANILLA_ENTITIES.add(EntityType.CAVE_SPIDER);
		VANILLA_ENTITIES.add(EntityType.CHICKEN);
		VANILLA_ENTITIES.add(EntityType.COD);
		VANILLA_ENTITIES.add(EntityType.COW);
		VANILLA_ENTITIES.add(EntityType.CREEPER);
		VANILLA_ENTITIES.add(EntityType.DOLPHIN);
		VANILLA_ENTITIES.add(EntityType.DONKEY);
		VANILLA_ENTITIES.add(EntityType.DROWNED);
		VANILLA_ENTITIES.add(EntityType.ENDERMAN);
		VANILLA_ENTITIES.add(EntityType.ENDERMITE);
		VANILLA_ENTITIES.add(EntityType.EVOKER);
		VANILLA_ENTITIES.add(EntityType.FOX);
		VANILLA_ENTITIES.add(EntityType.GHAST);
		VANILLA_ENTITIES.add(EntityType.GIANT);
		VANILLA_ENTITIES.add(EntityType.GUARDIAN);
		VANILLA_ENTITIES.add(EntityType.HOGLIN);
		VANILLA_ENTITIES.add(EntityType.HORSE);
		VANILLA_ENTITIES.add(EntityType.HUSK);
		VANILLA_ENTITIES.add(EntityType.ILLUSIONER);
		VANILLA_ENTITIES.add(EntityType.IRON_GOLEM);
		VANILLA_ENTITIES.add(EntityType.LLAMA);
		VANILLA_ENTITIES.add(EntityType.MAGMA_CUBE);
		VANILLA_ENTITIES.add(EntityType.MOOSHROOM);
		VANILLA_ENTITIES.add(EntityType.MULE);
		VANILLA_ENTITIES.add(EntityType.OCELOT);
		VANILLA_ENTITIES.add(EntityType.PANDA);
		VANILLA_ENTITIES.add(EntityType.PARROT);
		VANILLA_ENTITIES.add(EntityType.PHANTOM);
		VANILLA_ENTITIES.add(EntityType.PIG);
		VANILLA_ENTITIES.add(EntityType.PIGLIN);
		VANILLA_ENTITIES.add(EntityType.PIGLIN_BRUTE);
		VANILLA_ENTITIES.add(EntityType.PILLAGER);
		VANILLA_ENTITIES.add(EntityType.POLAR_BEAR);
		VANILLA_ENTITIES.add(EntityType.PUFFERFISH);
		VANILLA_ENTITIES.add(EntityType.RABBIT);
		VANILLA_ENTITIES.add(EntityType.RAVAGER);
		VANILLA_ENTITIES.add(EntityType.SALMON);
		VANILLA_ENTITIES.add(EntityType.SHEEP);
		VANILLA_ENTITIES.add(EntityType.SHULKER);
		VANILLA_ENTITIES.add(EntityType.SILVERFISH);
		VANILLA_ENTITIES.add(EntityType.SKELETON);
		VANILLA_ENTITIES.add(EntityType.SKELETON_HORSE);
		VANILLA_ENTITIES.add(EntityType.SLIME);
		VANILLA_ENTITIES.add(EntityType.SNOW_GOLEM);
		VANILLA_ENTITIES.add(EntityType.SPIDER);
		VANILLA_ENTITIES.add(EntityType.SQUID);
		VANILLA_ENTITIES.add(EntityType.STRAY);
		VANILLA_ENTITIES.add(EntityType.STRIDER);
		VANILLA_ENTITIES.add(EntityType.TRADER_LLAMA);
		VANILLA_ENTITIES.add(EntityType.VEX);
		VANILLA_ENTITIES.add(EntityType.VILLAGER);
		VANILLA_ENTITIES.add(EntityType.VINDICATOR);
		VANILLA_ENTITIES.add(EntityType.WANDERING_TRADER);
		VANILLA_ENTITIES.add(EntityType.WITCH);
		VANILLA_ENTITIES.add(EntityType.WITHER_SKELETON);
		VANILLA_ENTITIES.add(EntityType.WOLF);
		VANILLA_ENTITIES.add(EntityType.ZOGLIN);
		VANILLA_ENTITIES.add(EntityType.ZOMBIE);
		VANILLA_ENTITIES.add(EntityType.ZOMBIE_HORSE);
		VANILLA_ENTITIES.add(EntityType.ZOMBIE_VILLAGER);
		VANILLA_ENTITIES.add(EntityType.ZOMBIFIED_PIGLIN);
		return IUtilityHelper.super.addVanillaEntityToVanillaEntityList(VanillaMobToAdd);
	}
	
	public static boolean itemHasCritterCageMobAssigned(ItemStack itemToCheck) {
		if (itemToCheck.getTag() != null) {
			return itemToCheck.getTag().contains("EntityDataFilled") || itemToCheck.getItem() instanceof CritterCageItem;
		}
		return false;
	}
	
	public ItemStack createFilledCritterCageWithMob(ItemStack critterCage, ItemStack newCageWithMob, CompoundNBT nbtData, EntityType<?> mobToCage, PlayerEntity player, Hand hand, World world) {
		boolean instaBuild = player.abilities.instabuild;
		
		if (instaBuild && canUseCritterCage(getType(nbtData, world), critterCage)) {
			if (!player.inventory.contains(newCageWithMob)) {
				player.inventory.add(newCageWithMob);
			}
			
			return critterCage;
			
		} else {
			
			if (!instaBuild) {
				critterCage.shrink(1);
			}
			
			if (critterCage.isEmpty()) {
				return newCageWithMob;
			} else {
				
				if (!player.inventory.add(newCageWithMob)) {
					player.drop(newCageWithMob, false);
				}
				
				if (newCageWithMob.getItem().getTags() == null) {
					newCageWithMob.getOrCreateTag().put("EntityDataFilled", nbtData);
				}
				
				if (mobToCage == null) {
					return null;
				}
				
				return critterCage;
			}
			
		}
		
	}
	
	public ItemStack defaultToFilledCritterCageIfMobIsUnsupported(ItemStack mobToCageInStack, CompoundNBT nbtData, Hand hand, World world, LivingEntity entity) {
		getMatching(entity, mobToCageInStack, mobToCageInStack, mobToCageInStack, entity.getType().getRegistryName());
		return mobToCageInStack;
	}
	
/*	@Override
	public ActionResult<ItemStack> use(World w, PlayerEntity p, Hand h) {
		
		RayTraceResult result = getPlayerPOVHitResult(w, p, RayTraceContext.FluidMode.SOURCE_ONLY);
		if (result.getType() == RayTraceResult.Type.ENTITY) {
			
		}
		
		return super.use(w, p, h);
		
	}*/
	
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
		assert getEntityFromNBT(nbt, world, withInfo) != null;
		if (withInfo) {
			entity.load(nbt);
		}
		return entity;
	}
	
	  
	public LivingEntity getEntityFromStack(ItemStack stack, World world, boolean withInfo) {
		return getEntityFromNBT(stack.getOrCreateTag().getCompound("EntityDataFilled"), world, withInfo);	
	}
	
	public EntityType<?> getType(@Nullable CompoundNBT p_208076_1_, World world) {	
		CompoundNBT compoundnbt = p_208076_1_.getCompound("EntityDataFilled");	 
		LivingEntity entity = getEntityFromNBT(compoundnbt, world, true);
		if (p_208076_1_ != null) {
			if (p_208076_1_ != null && p_208076_1_.contains("EntityDataFilled", 4)) {
				if (compoundnbt.contains("id", 2)) {	 
					return EntityType.byString(compoundnbt.getString("id")).orElse(entity.getType());	 
				}	 
			}	
		}
		return entity.getType();
	}
	
	/*@Override
	public ActionResult<ItemStack> use(World w, PlayerEntity p, Hand h) {
		final LivingEntity e = getEntityFromStack(p.getItemInHand(h), w, true);
		final ItemStack stackWithFilledMob = new ItemStack(new FilledCritterCageItem(this.p, e.getType()));
		final ItemStack s = new ItemStack(this);
		final Vector3d entityPos = new Vector3d(e.getX(), e.getY(), e.getZ());
		
		final CompoundNBT entityFillingData = getNBTDataFromLivingEntity(e);
	//	final EntityType<?> type = getType(stackWithFilledMob.getTag());
		
		RayTraceResult raytraceresult = getPlayerPOVHitResult(w, p, FluidMode.ANY);
		
		if (raytraceresult.getType() == RayTraceResult.Type.BLOCK) {
			p.displayClientMessage(new TranslationTextComponent("crittercage.clientmessage.mobisnullorerror.message"), true);
			return ActionResult.fail(p.getItemInHand(h));
		}else if ( raytraceresult.getType() == RayTraceResult.Type.MISS) {
			p.displayClientMessage(new TranslationTextComponent("crittercage.clientmessage.mobisnullorerror.message"), true);
			return ActionResult.fail(p.getItemInHand(h));
		} else {
			if (e.isBaby()) {
				if (w.isClientSide) {	
					addParticles(w, ParticleTypes.SMOKE, e.getX(), e.getY(), e.getZ(), e.getX() + 0.3D, e.getY() + 0.1D, e.getZ() + 0.2D, 10);		
					w.playSound(p, entityPos.x ,entityPos.y, entityPos.z, SoundEvents.ARMOR_EQUIP_CHAIN, e.getSoundSource(), 1.0F, 1.0F);	
				}	
				createFilledCritterCageWithMob(s, stackWithFilledMob, entityFillingData, e, p, h, w);		
				stackWithFilledMob.getOrCreateTag().put("EntityDataFilled", entityFillingData);	
				if (stackWithFilledMob.getTag() == null) {
					stackWithFilledMob.setTag(entityFillingData);		
				}
				e.remove(true);
				return ActionResult.success(p.getItemInHand(h));	
			}
			if (w.isClientSide) {
				addParticles(w, ParticleTypes.LARGE_SMOKE, e.getX(), e.getY(), e.getZ(), e.getX() + 0.3D, e.getY() + 0.1D, e.getZ() + 0.2D, 10);
				w.playSound(p, entityPos.x ,entityPos.y, entityPos.z, SoundEvents.ARMOR_EQUIP_NETHERITE, e.getSoundSource(), 1.0F, 1.0F);	
			}	
			createFilledCritterCageWithMob(s, stackWithFilledMob, entityFillingData, e, p, h, w);	
			stackWithFilledMob.getOrCreateTag().put("EntityDataFilled", entityFillingData);		
			if (stackWithFilledMob.getTag() == null) {	
				stackWithFilledMob.setTag(entityFillingData);			
			}
			e.remove(true);
			return ActionResult.success(p.getItemInHand(h));
		}
	}*/
	
/*	@Override
	public ActionResultType interactLivingEntity(ItemStack s, PlayerEntity p, LivingEntity e, Hand h) {
		
		final World playerLevel = p.level;
		final Vector3d entityPos = new Vector3d(e.getX(), e.getY(), e.getZ());
		RayTraceResult raytraceresult = getPlayerPOVHitResult(p.level, p, FluidMode.SOURCE_ONLY);
		
		final ItemStack stackWithFilledMob = new ItemStack(new FilledCritterCageItem(this.p, e.getType()));
		final CompoundNBT entityFillingData = getNBTDataFromLivingEntity(e);
		final EntityType<?> type = getType(stackWithFilledMob.getTag());
		
		if (raytraceresult.getType() == RayTraceResult.Type.BLOCK || raytraceresult.getType() == RayTraceResult.Type.MISS) {
			p.displayClientMessage(new TranslationTextComponent("crittercage.clientmessage.mobisnullorerror.message"), true);
			return ActionResultType.FAIL;
		}
		
		if (isInCritterCageCAEntityList(e.getType()) && canUseCritterCage(e.getType(), stackWithFilledMob)) {
			if (e.isBaby()) {
				if (playerLevel.isClientSide) {	
					addParticles(playerLevel, ParticleTypes.SMOKE, entityPos.x, entityPos.y, entityPos.z, entityPos.x + 0.3D, entityPos.y + 0.2D, entityPos.z + 0.3D, 10);
					playerLevel.playSound(p, entityPos.x, entityPos.y, entityPos.z, SoundEvents.ARMOR_EQUIP_IRON, e.getSoundSource(), 1.0F, 1.0F);
				}
				createFilledCritterCageWithMob(s, stackWithFilledMob, entityFillingData, e, p, h, playerLevel);		
				if (entityFillingData != null) {
					e.addAdditionalSaveData(entityFillingData);	
				}
	//			entityFillingData.putString("EntityType", "ChaosAwakensEntity");	
				stackWithFilledMob.getOrCreateTag().put("EntityDataFilled", entityFillingData);
				stackWithFilledMob.setTag(entityFillingData);	
				e.remove(true);	
				return ActionResultType.SUCCESS;
			} else {	
				if (playerLevel.isClientSide) {	
					addParticles(playerLevel, ParticleTypes.LARGE_SMOKE, entityPos.x, entityPos.y, entityPos.z, entityPos.x + 0.3D, entityPos.y + 0.2D, entityPos.z + 0.3D, 10);	
				} else {	
					createFilledCritterCageWithMob(s, stackWithFilledMob, entityFillingData, e, p, h, playerLevel);		
					if (entityFillingData != null) {
						e.addAdditionalSaveData(entityFillingData);	
					}
		//			entityFillingData.putString("EntityType", "ChaosAwakensEntity");	
					stackWithFilledMob.getOrCreateTag().put("EntityDataFilled", entityFillingData);
					stackWithFilledMob.setTag(entityFillingData);		
					e.remove(true);	
					playerLevel.playSound(p, entityPos.x, entityPos.y, entityPos.z, SoundEvents.ARMOR_EQUIP_NETHERITE, e.getSoundSource(), 1.0F, 1.0F);	
				}	
			}
			return ActionResultType.SUCCESS;	
		}	
		
		else if (isInCritterCageVanillaEntityList(e.getType()) && canUseCritterCage(e.getType(), stackWithFilledMob)) {
			if (e.isBaby()) {
				if (playerLevel.isClientSide) {	
					createFilledCritterCageWithMob(s, stackWithFilledMob, entityFillingData, e, p, h, playerLevel);		
					if (entityFillingData != null) {
						e.addAdditionalSaveData(entityFillingData);	
					}
		//			entityFillingData.putString("EntityType", "ChaosAwakensEntity");	
					stackWithFilledMob.getOrCreateTag().put("EntityDataFilled", entityFillingData);
					stackWithFilledMob.setTag(entityFillingData);	
					e.remove(true);	
					playerLevel.playSound(p, entityPos.x, entityPos.y, entityPos.z, SoundEvents.ARMOR_EQUIP_IRON, e.getSoundSource(), 1.0F, 1.0F);
				}
				createFilledCritterCageWithMob(s, stackWithFilledMob, entityFillingData, e, p, h, playerLevel);	
				return ActionResultType.SUCCESS;
			} else {	
				createFilledCritterCageWithMob(s, stackWithFilledMob, entityFillingData, e, p, h, playerLevel);		
				if (entityFillingData != null) {
					e.addAdditionalSaveData(entityFillingData);	
				}
	//			entityFillingData.putString("EntityType", "ChaosAwakensEntity");	
				stackWithFilledMob.getOrCreateTag().put("EntityDataFilled", entityFillingData);
				stackWithFilledMob.setTag(entityFillingData);	
				e.remove(true);	
				playerLevel.playSound(p, entityPos.x, entityPos.y, entityPos.z, SoundEvents.ARMOR_EQUIP_NETHERITE, e.getSoundSource(), 1.0F, 1.0F);		
			}	
			return ActionResultType.SUCCESS;	
		}

	//	p.displayClientMessage(new TranslationTextComponent("crittercage.clientmessage.mobisnullorerror.message"), true);
		return ActionResultType.FAIL;
	}*/
	
	@Override
	public ActionResultType interactLivingEntity(ItemStack s, PlayerEntity p, LivingEntity e, Hand h) {
		CompoundNBT nbt = getNBTDataFromLivingEntity(e);
		EntityType<?> type = getType(nbt, e.level);
		ItemStack newStack = new ItemStack(new FilledCritterCageItem(this.p, type));
	    if (e instanceof PlayerEntity || !e.isAlive() || !canUseCritterCage(type, s) || newStack == null || newStack.getItem() == Items.AIR) {
	    	return ActionResultType.FAIL;
	    }
	    
	    if (nbt == null) {
	    	return ActionResultType.FAIL;
	    }
	    
	    newStack.getOrCreateTag().put("EntityDataFilled", nbt);
	      createFilledCritterCageWithMob(s, newStack, nbt, type, p, h, e.level);
	      e.remove();
	      return ActionResultType.SUCCESS;
	}
	
}
