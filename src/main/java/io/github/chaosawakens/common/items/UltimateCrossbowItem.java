package io.github.chaosawakens.common.items;

import java.util.List;
import java.util.Random;

import com.google.common.collect.Lists;

import io.github.chaosawakens.common.entity.projectile.ExplosiveFireworkEntity;
import io.github.chaosawakens.common.entity.projectile.UltimateArrowEntity;
import net.minecraft.advancements.CriteriaTriggers;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.enchantment.Enchantments;
import net.minecraft.entity.ICrossbowUser;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.entity.projectile.AbstractArrowEntity;
import net.minecraft.entity.projectile.ProjectileEntity;
import net.minecraft.item.ArrowItem;
import net.minecraft.item.CrossbowItem;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.nbt.ListNBT;
import net.minecraft.stats.Stats;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.SoundEvent;
import net.minecraft.util.SoundEvents;
import net.minecraft.util.math.vector.Quaternion;
import net.minecraft.util.math.vector.Vector3d;
import net.minecraft.util.math.vector.Vector3f;
import net.minecraft.world.World;

@SuppressWarnings("all")
public class UltimateCrossbowItem extends CrossbowItem {	  
	private boolean startSoundPlayed = false;	  
	private boolean midLoadSoundPlayed = false;

	public UltimateCrossbowItem(Properties properties) {
		super(properties);
	}
	
	@Override
	public int getUseDuration(ItemStack stack) {
		return super.getUseDuration(stack) / 2;
	}	
	 
	@Override
	public ActionResult<ItemStack> use(World world, PlayerEntity playerOwner, Hand hand) {		    
		ItemStack itemstack = playerOwner.getItemInHand(hand);		     
		if (isCharged(itemstack)) {		      
			performShooting(world, playerOwner, hand, itemstack, getShootingPower(itemstack), 1.0F);		        
			setCharged(itemstack, false);		         
			return ActionResult.consume(itemstack);		      
		} else if (!playerOwner.getProjectile(itemstack).isEmpty()) {	        
			if (!isCharged(itemstack)) {		         
				this.startSoundPlayed = false;		          
				this.midLoadSoundPlayed = false;		           
				playerOwner.startUsingItem(hand);		         		
			}		       
			return ActionResult.consume(itemstack);		      
		} else {		     
			return ActionResult.fail(itemstack);		     
		}		  
	}
	
	  
	public static float getPowerForTime(int i, ItemStack stack) {		  
		float f = (float)i / (float)getChargeDuration(stack);		   
		if (f > 1.0F) {		     
			f = 1.0F;		    
		}		    
		return f + 0.7F;		  
	}
	
	@Override
	public void releaseUsing(ItemStack stack, World world, LivingEntity owner, int dura) {		  
		int i = this.getUseDuration(stack) - dura;		   
		float f = getPowerForTime(i, stack);		     
		if (f >= 1.0F && !isCharged(stack) && tryLoadProjectiles(owner, stack)) {		    
			setCharged(stack, true);		     
			SoundCategory soundcategory = owner instanceof PlayerEntity ? SoundCategory.PLAYERS : SoundCategory.HOSTILE;	      
			world.playSound((PlayerEntity)null, owner.getX(), owner.getY(), owner.getZ(), SoundEvents.CROSSBOW_LOADING_END, soundcategory, 1.0F, 1.0F / (random.nextFloat() * 0.5F + 1.0F) + 0.2F);		     
		}		  
	}
	
	@Override
	public void onUseTick(World world, LivingEntity owner, ItemStack stack, int dura) {		    
		if (!world.isClientSide) {		     
			int i = EnchantmentHelper.getItemEnchantmentLevel(Enchantments.QUICK_CHARGE, stack);		       
			SoundEvent soundevent = this.getStartSound(i);		         
			SoundEvent soundevent1 = i == 0 ? SoundEvents.CROSSBOW_LOADING_MIDDLE : null;		        
			float f = (float)(stack.getUseDuration() - dura) / (float)getChargeDuration(stack);		       
			if (f < 0.1F) {		       
				this.startSoundPlayed = false;		       
				this.midLoadSoundPlayed = false;		        
			}		         
			if (f >= 0.1F && !this.startSoundPlayed) {		          
				this.startSoundPlayed = true;		         
				world.playSound((PlayerEntity)null, owner.getX(), owner.getY(), owner.getZ(), soundevent, SoundCategory.PLAYERS, 0.5F, 1.0F);		        
			}		        
			if (f >= 0.35F && soundevent1 != null && !this.midLoadSoundPlayed) {		          
				this.midLoadSoundPlayed = true;		          
				world.playSound((PlayerEntity)null, owner.getX(), owner.getY(), owner.getZ(), soundevent1, SoundCategory.PLAYERS, 0.5F, 1.0F);		        
			}		     
		}		  
	}
		  
	private SoundEvent getStartSound(int progress) {		  
		switch(progress) {		    
		case 1:		      
			return SoundEvents.CROSSBOW_QUICK_CHARGE_1;		    
		case 2:		     
			return SoundEvents.CROSSBOW_QUICK_CHARGE_2;		    
		case 3:	    
			return SoundEvents.CROSSBOW_QUICK_CHARGE_3;		     
		default:		      
			return SoundEvents.CROSSBOW_LOADING_START;		    
		}		
	}
	
	@Override
	public int getDefaultProjectileRange() {
		return super.getDefaultProjectileRange() * 3;
	}
	 
	public static boolean tryLoadProjectiles(LivingEntity entity, ItemStack stack) {		 
		int i = EnchantmentHelper.getItemEnchantmentLevel(Enchantments.MULTISHOT, stack);		    
		int j = i == 0 ? 3 : 6;		     
		boolean flag = entity instanceof PlayerEntity && ((PlayerEntity)entity).abilities.instabuild;		     
		ItemStack itemstack = entity.getProjectile(stack);		     
		ItemStack itemstack1 = itemstack.copy();		     
		
		for(int k = 0; k < j; ++k) {		      
			if (k > 0) {		        
				itemstack = itemstack1.copy();		         
			}		       
			if (itemstack.isEmpty() && flag) {		        
				itemstack = new ItemStack(Items.ARROW);
				itemstack1 = itemstack.copy();		         
			}
			if (!loadProjectile(entity, stack, itemstack, k > 0, flag)) {		         
				return false;		        
			}		    
		}		    
		return true;		  
	}
	   
	private static boolean loadProjectile(LivingEntity entity, ItemStack crossbowStack, ItemStack arrowStack, boolean b1, boolean b2) {		   		
		if (arrowStack.isEmpty()) {		     
			return false;		     
		} else {		      
			boolean flag = b2 && arrowStack.getItem() instanceof ArrowItem;		      
			ItemStack itemstack;		       
			if (!flag && !b2 && !b1) {		        
				itemstack = arrowStack.split(1);	         
				if (arrowStack.isEmpty() && entity instanceof PlayerEntity) {	          
					((PlayerEntity)entity).inventory.removeItem(arrowStack);		          
				}		         
			} else {		        
				itemstack = arrowStack.copy();		        
			}		        
			addChargedProjectile(crossbowStack, itemstack);	        
			return true;		     
		}		 
	}
	  
	private static void addChargedProjectile(ItemStack stack, ItemStack stack2) {		
		CompoundNBT compoundnbt = stack.getOrCreateTag();		  
		ListNBT listnbt;		   
		if (compoundnbt.contains("ChargedProjectiles", 9)) {		   
			listnbt = compoundnbt.getList("ChargedProjectiles", 10);		     
		} else {		      
			listnbt = new ListNBT();		     
		}		     
		CompoundNBT compoundnbt1 = new CompoundNBT();		     
		stack2.save(compoundnbt1);		     
		listnbt.add(compoundnbt1);		    		
		compoundnbt.put("ChargedProjectiles", listnbt);
	}
		  
	private static void shootProjectile(World world, LivingEntity owner, Hand hand, ItemStack crossbowStack, ItemStack arrowStack, float f1, boolean b1, float f2, float f3, float f4) {		   
		if (!world.isClientSide) {		     
			boolean flag = arrowStack.getItem() == Items.FIREWORK_ROCKET;		       
			ProjectileEntity projectileentity;		        
			if (flag) {		         
				projectileentity = new ExplosiveFireworkEntity(world, arrowStack, owner, owner.getX(), owner.getEyeY() - (double)0.15F, owner.getZ(), true);		        
			} else {		        
				projectileentity = getArrow(world, owner, crossbowStack, arrowStack);		        
				if (b1 || f4 != 0.0F) {		          
					((UltimateArrowEntity)projectileentity).pickup = AbstractArrowEntity.PickupStatus.CREATIVE_ONLY;		         
				}		       
			}		       
			if (owner instanceof ICrossbowUser) {		        
				ICrossbowUser icrossbowuser = (ICrossbowUser)owner;		        
				icrossbowuser.shootCrossbowProjectile(icrossbowuser.getTarget(), crossbowStack, projectileentity, f4);	        
			} else {		         
				Vector3d vector3d1 = owner.getUpVector(1.0F);		         
				Quaternion quaternion = new Quaternion(new Vector3f(vector3d1), f4, true);	        
				Vector3d vector3d = owner.getViewVector(1.0F);
				Vector3f vector3f = new Vector3f(vector3d);		        
				vector3f.transform(quaternion);		        
				projectileentity.shoot((double)vector3f.x(), (double)vector3f.y(), (double)vector3f.z(), f2, f3);		        
			}		        
			crossbowStack.hurtAndBreak(flag ? 3 : 1, owner, (itemstack) -> {		        
				itemstack.broadcastBreakEvent(hand);		        
			});		        
			world.addFreshEntity(projectileentity);		       
			world.playSound((PlayerEntity)null, owner.getX(), owner.getY(), owner.getZ(), SoundEvents.CROSSBOW_SHOOT, SoundCategory.PLAYERS, 1.0F, f1);		      
		}	 
	}
	   
	private static AbstractArrowEntity getArrow(World world, LivingEntity owner, ItemStack crossbowStack, ItemStack arrowStack) {		  
		ArrowItem arrowitem = (ArrowItem)(arrowStack.getItem() instanceof ArrowItem ? arrowStack.getItem() : Items.ARROW);		    
		UltimateArrowEntity abstractarrowentity = (UltimateArrowEntity) arrowitem.createArrow(world, arrowStack, owner);		     
		abstractarrowentity.setCritArrow(true);
		abstractarrowentity.setSoundEvent(SoundEvents.CROSSBOW_HIT);		     
		abstractarrowentity.setShotFromCrossbow(true);		
		abstractarrowentity.setPierceLevel((byte) 4);
		int i = EnchantmentHelper.getItemEnchantmentLevel(Enchantments.PIERCING, crossbowStack);		     
		if (i > 0) {	     
			abstractarrowentity.setPierceLevel((byte) ((byte)i + 4));		     
		}
		return abstractarrowentity;		  
	}
		  
	private static float[] getShotPitches(Random random) {		
		boolean flag = random.nextBoolean();		 
		return new float[]{1.0F, getRandomShotPitch(flag), getRandomShotPitch(!flag)};		 
	}
	  
	private static float getRandomShotPitch(boolean b1) {		
		float f = b1 ? 0.63F : 0.43F;		  
		return 1.0F / (random.nextFloat() * 0.5F + 1.8F) + f - 0.22F;		  
	}
	   
	public static void performShooting(World world, LivingEntity owner, Hand hand, ItemStack stack, float f1, float f2) {		  
		List<ItemStack> list = getChargedProjectiles(stack);		     
		float[] afloat = getShotPitches(owner.getRandom());
		
		for(int i = 0; i < list.size(); ++i) {		      
			ItemStack itemstack = list.get(i);		        
			boolean flag = owner instanceof PlayerEntity && ((PlayerEntity)owner).abilities.instabuild;		       
			if (!itemstack.isEmpty()) {			
				for (float c = -30.0F; c < 30.0F; c++) {
					if (c % 10 == 0) {
						shootProjectile(world, owner, hand, stack, itemstack, afloat[i], flag, f1, f2, c);
					}
				}
			}		    
		}		    
		onCrossbowShot(world, owner, stack);		  
	}
		 
	private static List<ItemStack> getChargedProjectiles(ItemStack stack) {		 
		List<ItemStack> list = Lists.newArrayList();		    
		CompoundNBT compoundnbt = stack.getTag();		     
		if (compoundnbt != null && compoundnbt.contains("ChargedProjectiles", 9)) {		    
			ListNBT listnbt = compoundnbt.getList("ChargedProjectiles", 10);		      
			if (listnbt != null) {		         
				for(int i = 0; i < listnbt.size(); ++i) {		           
					CompoundNBT compoundnbt1 = listnbt.getCompound(i);		            
					list.add(ItemStack.of(compoundnbt1));		           
				}	        
			}		    
		}		     
		return list;		  
	}
	  
	private static void onCrossbowShot(World world, LivingEntity owner, ItemStack stack) {		   
		if (owner instanceof ServerPlayerEntity) {		      
			ServerPlayerEntity serverplayerentity = (ServerPlayerEntity)owner;		        
			if (!world.isClientSide) {		         
				CriteriaTriggers.SHOT_CROSSBOW.trigger(serverplayerentity, stack);
			}		        
			serverplayerentity.awardStat(Stats.ITEM_USED.get(stack.getItem()));		      
		}		      
		clearChargedProjectiles(stack);		  
	}
	
	private static void clearChargedProjectiles(ItemStack stack) {		  
		CompoundNBT compoundnbt = stack.getTag();		    
		if (compoundnbt != null) {		     
			ListNBT listnbt = compoundnbt.getList("ChargedProjectiles", 9);		       
			listnbt.clear();		        
			compoundnbt.put("ChargedProjectiles", listnbt);		    
		}		  
	}

}
