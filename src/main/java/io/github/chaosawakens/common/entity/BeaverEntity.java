package io.github.chaosawakens.common.entity;

import java.util.Random;

import javax.annotation.Nullable;

import io.github.chaosawakens.common.entity.base.AnimatableAnimalEntity;
import net.minecraft.entity.AgeableEntity;
import net.minecraft.entity.CreatureEntity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.MobEntity;
import net.minecraft.entity.ai.attributes.AttributeModifierMap;
import net.minecraft.entity.ai.attributes.Attributes;
import net.minecraft.entity.ai.goal.LookAtGoal;
import net.minecraft.entity.ai.goal.LookRandomlyGoal;
import net.minecraft.entity.ai.goal.MoveToBlockGoal;
import net.minecraft.entity.ai.goal.RandomWalkingGoal;
import net.minecraft.entity.ai.goal.SwimGoal;
import net.minecraft.entity.passive.AnimalEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.network.datasync.DataParameter;
import net.minecraft.network.datasync.DataSerializers;
import net.minecraft.network.datasync.EntityDataManager;
import net.minecraft.particles.BlockParticleData;
import net.minecraft.particles.ParticleTypes;
import net.minecraft.tags.BlockTags;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockReader;
import net.minecraft.world.IWorld;
import net.minecraft.world.IWorldReader;
import net.minecraft.world.World;
import net.minecraft.world.chunk.ChunkStatus;
import net.minecraft.world.chunk.IChunk;
import net.minecraft.world.server.ServerWorld;
import net.minecraftforge.common.ForgeHooks;
import software.bernie.geckolib3.core.IAnimatable;
import software.bernie.geckolib3.core.PlayState;
import software.bernie.geckolib3.core.builder.AnimationBuilder;
import software.bernie.geckolib3.core.controller.AnimationController;
import software.bernie.geckolib3.core.event.predicate.AnimationEvent;
import software.bernie.geckolib3.core.manager.AnimationData;
import software.bernie.geckolib3.core.manager.AnimationFactory;

public class BeaverEntity extends AnimatableAnimalEntity {
	private final AnimationFactory factory = new AnimationFactory(this);
	private static final DataParameter<Boolean> CHIPPING = EntityDataManager.defineId(BeaverEntity.class, DataSerializers.BOOLEAN);
	private final AnimationController<?> controller = new AnimationController<IAnimatable>(this, "beavercontroller", animationInterval(), this::predicate);

	public BeaverEntity(EntityType<? extends AnimalEntity> type, World worldIn) {
		super(type, worldIn);
		this.noCulling = true;
	}

	public static AttributeModifierMap.MutableAttribute setCustomAttributes() {
		return MobEntity.createLivingAttributes()
				.add(Attributes.MAX_HEALTH, 6)
				.add(Attributes.MOVEMENT_SPEED, 0.15D)
				.add(Attributes.FOLLOW_RANGE, 8);
	}

	public <E extends IAnimatable> PlayState predicate(AnimationEvent<E> event) {
		if (event.isMoving()) {
			event.getController().setAnimation(new AnimationBuilder().addAnimation("animation.beaver.walking_animation", true));
			return PlayState.CONTINUE;
		}
		if (!event.isMoving()) {
			event.getController().setAnimation(new AnimationBuilder().addAnimation("animation.beaver.idle_animation", true));
			return PlayState.CONTINUE;
		}
		if (this.getChipping()) {
			event.getController().setAnimation(new AnimationBuilder().addAnimation("animation.beaver.gnaw_animation", true));
		}
		return PlayState.CONTINUE;
	}
	
	@Override
	public int animationInterval() {
		return 3;
	}
	
	@Override
	protected void defineSynchedData() {
		super.defineSynchedData();
		this.entityData.define(CHIPPING, false);
	}
	
	public boolean getChipping() {
		return this.entityData.get(CHIPPING);
	}
	
	public void setChipping(boolean chipping) {
		this.entityData.set(CHIPPING, chipping);
	}

	@Override
	protected void registerGoals() {
		this.goalSelector.addGoal(0, new BeaverBreakBlockGoal(this, 1.1D, 50));
		this.goalSelector.addGoal(1, new SwimGoal(this));
		this.goalSelector.addGoal(5, new RandomWalkingGoal(this, 1.6));
		this.goalSelector.addGoal(6, new LookAtGoal(this, PlayerEntity.class, 6.0F));
		this.goalSelector.addGoal(7, new LookRandomlyGoal(this));
	}

	@Override
	public void registerControllers(AnimationData data) {
		data.addAnimationController(controller);
	}
	
	@Override
	public AnimationController<?> getController() {
		return controller;
	}

	@Override
	public AnimationFactory getFactory() {
		return this.factory;
	}

	@Nullable
	@Override
	public AgeableEntity getBreedOffspring(ServerWorld world, AgeableEntity mate) {
		return null;
	}
	
	static class BeaverBreakBlockGoal extends MoveToBlockGoal {
		private final BeaverEntity beaver;
		private int ticksSinceReachedGoal;
		
		public BeaverBreakBlockGoal(BeaverEntity owner, double speedMultiplier, int verticalSearchRange) {
			super(owner, speedMultiplier, verticalSearchRange);
			this.beaver = owner;
		}
		
		public boolean canUse() {			
			if (!ForgeHooks.canEntityDestroy(this.beaver.level, this.blockPos, this.beaver)) {			
				return false;			 
			} else if (this.nextStartTick > 0) {			  
				--this.nextStartTick;			  
				return false;			   
			} else if (this.tryFindBlock()) {			    
				this.nextStartTick = 20;			    
				return true;			 			
			} else {			   
				this.nextStartTick = this.nextStartTick(this.mob);			   
				return false;			   
			}		   
		}

		@Override
		protected int nextStartTick(CreatureEntity entity) {
			return 40;
		}
			
		private boolean tryFindBlock() {			
			return this.blockPos != null && this.isValidTarget(this.mob.level, this.blockPos) ? true : this.findNearestBlock();			
		}
		
		public void stop() {			
			super.stop();			
		}
		
		public void start() {			
			super.start();			 
			this.ticksSinceReachedGoal = 0;			
		}

			 
		public void playDestroyProgressSound(IWorld p_203114_1_, BlockPos p_203114_2_) {
			 
		}

			
		public void playBreakSound(World p_203116_1_, BlockPos p_203116_2_) {
			 
		}
		
		@Override
		public double acceptedDistance() {
			return 2.0D;
		}
		
		@Override
		protected BlockPos getMoveToTarget() {
			return this.blockPos;
		}
		
		@SuppressWarnings("unused")
		@Override	 
		public void tick() {			  			
			super.tick();
			World world = this.beaver.level;			   
			BlockPos blockpos = this.beaver.blockPosition();			    
			BlockPos blockpos1 = this.getPosWithBlock(blockpos, world);			     
			Random random = this.beaver.getRandom();			     
			if (this.isReachedTarget() && blockpos1 != null) {			    
				if (this.ticksSinceReachedGoal > 0) {			     
					this.beaver.setChipping(true);	      
		//			this.beaver.lookAt(Type.EYES, beaver.getLookAngle());
					if (!world.isClientSide) {			       
						double d0 = 0.08D;			        
						((ServerWorld)world).sendParticles(new BlockParticleData(ParticleTypes.BLOCK, world.getBlockState(blockpos1)), (double)blockpos1.getX() + 0.5D, (double)blockpos1.getY() + 0.7D, (double)blockpos1.getZ() + 0.5D, 3, ((double)random.nextFloat() - 0.5D) * 0.08D, ((double)random.nextFloat() - 0.5D) * 0.08D, ((double)random.nextFloat() - 0.5D) * 0.08D, (double)0.15F);			       
					}			       
				}
				
				if (this.ticksSinceReachedGoal % 2 == 0) {			        		        
					if (this.ticksSinceReachedGoal % 6 == 0) {			        
						this.playDestroyProgressSound(world, this.blockPos);			            
					}			        
				}
				
				if (this.ticksSinceReachedGoal > 60) {			  					
					world.removeBlock(blockpos1, false);		
					this.beaver.setChipping(false);
					if (!world.isClientSide) {			        		            
						this.playBreakSound(world, blockpos1);			           
					}		       
				}		      
				++this.ticksSinceReachedGoal;			      
			}		 
			
			if (ticksSinceReachedGoal > 0) {
		//		ChaosAwakens.LOGGER.debug(ticksSinceReachedGoal);
			}
		}

			 
		@Nullable		 
		private BlockPos getPosWithBlock(BlockPos p_203115_1_, IBlockReader p_203115_2_) {			   
			if (p_203115_2_.getBlockState(p_203115_1_).is(BlockTags.LOGS)) {			    
				return p_203115_1_;			    
			} else {			      
				BlockPos[] ablockpos = new BlockPos[]{p_203115_1_.west(), p_203115_1_.below(), p_203115_1_.east(), p_203115_1_.north(), p_203115_1_.south(), p_203115_1_.above()};			        
				for(BlockPos blockpos : ablockpos) {			         
					if (p_203115_2_.getBlockState(blockpos).is(BlockTags.LOGS)) {			          
						return blockpos;			         
					}			      
				}			       
				return null;
			}			 
		}

			  
		@SuppressWarnings("deprecation")
		protected boolean isValidTarget(IWorldReader p_179488_1_, BlockPos p_179488_2_) {			  
			IChunk ichunk = p_179488_1_.getChunk(p_179488_2_.getX() >> 4, p_179488_2_.getZ() >> 4, ChunkStatus.FULL, false);		    
			if (ichunk == null) {		     
				return false;     
			} else {			        
				return ichunk.getBlockState(p_179488_2_).is(BlockTags.LOGS) && (ichunk.getBlockState(p_179488_2_.north()).isAir() || ichunk.getBlockState(p_179488_2_.south()).isAir() ||  ichunk.getBlockState(p_179488_2_.east()).isAir() ||  ichunk.getBlockState(p_179488_2_.west()).isAir());			    
			}		  
		}
		
	}

	@Override
	public int tickTimer() {
		return tickCount;
	}
}
