package io.github.chaosawakens.common.blocks;

import net.minecraft.advancements.CriteriaTriggers;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.SoundType;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.item.*;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.server.MinecraftServer;
import net.minecraft.state.Property;
import net.minecraft.state.StateContainer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ActionResultType;
import net.minecraft.util.NonNullList;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.SoundEvent;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.shapes.ISelectionContext;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.world.World;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

import javax.annotation.Nullable;
import java.util.List;

public class EnchantedBlockItem extends Item {
	private final Block block;
	
	public EnchantedBlockItem(Block blockIn, Item.Properties builder) {
		super(builder);
		this.block = blockIn;
	}
	
	/**
	 * Called when this item is used when targetting a Block
	 */
	public ActionResultType useOn(ItemUseContext context) {
		ActionResultType actionresulttype = this.tryPlace(new BlockItemUseContext(context));
		return !actionresulttype.consumesAction() && this.isEdible() ? this.use(context.getLevel(), context.getPlayer(), context.getHand()).getResult() : actionresulttype;
	}
	
	public ActionResultType tryPlace(BlockItemUseContext context) {
		if (!context.canPlace()) {
			return ActionResultType.FAIL;
		} else {
			BlockItemUseContext blockitemusecontext = this.getBlockItemUseContext(context);
			if (blockitemusecontext == null) {
				return ActionResultType.FAIL;
			} else {
				BlockState blockstate = this.getStateForPlacement(blockitemusecontext);
				if (blockstate == null) {
					return ActionResultType.FAIL;
				} else if (!this.placeBlock(blockitemusecontext, blockstate)) {
					return ActionResultType.FAIL;
				} else {
					BlockPos blockpos = blockitemusecontext.getClickedPos();
					World world = blockitemusecontext.getLevel();
					PlayerEntity playerentity = blockitemusecontext.getPlayer();
					ItemStack itemstack = blockitemusecontext.getItemInHand();
					BlockState blockstate1 = world.getBlockState(blockpos);
					Block block = blockstate1.getBlock();
					if (block == blockstate.getBlock()) {
						blockstate1 = this.updateBlockStateFromTag(blockpos, world, itemstack, blockstate1);
						this.onBlockPlaced(blockpos, world, playerentity, itemstack);
						block.setPlacedBy(world, blockpos, blockstate1, playerentity, itemstack);
						if (playerentity instanceof ServerPlayerEntity) {
							CriteriaTriggers.PLACED_BLOCK.trigger((ServerPlayerEntity) playerentity, blockpos, itemstack);
						}
					}
					
					SoundType soundtype = blockstate1.getSoundType(world, blockpos, context.getPlayer());
					world.playSound(playerentity, blockpos, this.getPlaceSound(blockstate1, world, blockpos, context.getPlayer()), SoundCategory.BLOCKS, (soundtype.getVolume() + 1.0F) / 2.0F, soundtype.getPitch() * 0.8F);
					if (playerentity == null || !playerentity.abilities.instabuild) {
						itemstack.shrink(1);
					}
					
					return ActionResultType.sidedSuccess(world.isClientSide);
				}
			}
		}
	}
	
	@Deprecated // Forge: Use more sensitive version {@link BlockItem#getPlaceSound(BlockState,
				// IBlockReader, BlockPos, Entity) }
	protected SoundEvent getPlaceSound(BlockState state) {
		return state.getSoundType().getPlaceSound();
	}
	
	// Forge: Sensitive version of BlockItem#getPlaceSound
	protected SoundEvent getPlaceSound(BlockState state, World world, BlockPos pos, PlayerEntity entity) {
		return state.getSoundType(world, pos, entity).getPlaceSound();
	}
	
	@Nullable
	public BlockItemUseContext getBlockItemUseContext(BlockItemUseContext context) {
		return context;
	}
	
	protected void onBlockPlaced(BlockPos pos, World worldIn, @Nullable PlayerEntity player, ItemStack stack) {
		setTileEntityNBT(worldIn, player, pos, stack);
	}
	
	@Nullable
	protected BlockState getStateForPlacement(BlockItemUseContext context) {
		BlockState blockstate = this.getBlock().getStateForPlacement(context);
		return blockstate != null && this.canPlace(context, blockstate) ? blockstate : null;
	}
	
	private BlockState updateBlockStateFromTag(BlockPos p_219985_1_, World p_219985_2_, ItemStack p_219985_3_, BlockState p_219985_4_) {
		BlockState blockstate = p_219985_4_;
		CompoundNBT compoundnbt = p_219985_3_.getTag();
		if (compoundnbt != null) {
			CompoundNBT compoundnbt1 = compoundnbt.getCompound("BlockStateTag");
			StateContainer<Block, BlockState> statecontainer = p_219985_4_.getBlock().getStateDefinition();
			
			for (String s : compoundnbt1.getAllKeys()) {
				Property<?> property = statecontainer.getProperty(s);
				if (property != null) {
					String s1 = compoundnbt1.get(s).getAsString();
					blockstate = updateState(blockstate, property, s1);
				}
			}
		}
		
		if (blockstate != p_219985_4_) {
			p_219985_2_.setBlock(p_219985_1_, blockstate, 2);
		}
		
		return blockstate;
	}
	
	private static <T extends Comparable<T>> BlockState updateState(BlockState p_219988_0_, Property<T> p_219988_1_, String p_219988_2_) {
		return p_219988_1_.getValue(p_219988_2_).map((p_219986_2_) -> p_219988_0_.setValue(p_219988_1_, p_219986_2_)).orElse(p_219988_0_);
	}
	
	protected boolean canPlace(BlockItemUseContext p_195944_1_, BlockState p_195944_2_) {
		PlayerEntity playerentity = p_195944_1_.getPlayer();
		ISelectionContext iselectioncontext = playerentity == null ? ISelectionContext.empty() : ISelectionContext.of(playerentity);
		return (!this.checkPosition() || p_195944_2_.canSurvive(p_195944_1_.getLevel(), p_195944_1_.getClickedPos())) && p_195944_1_.getLevel().isUnobstructed(p_195944_2_, p_195944_1_.getClickedPos(), iselectioncontext);
	}
	
	protected boolean checkPosition() {
		return true;
	}
	
	protected boolean placeBlock(BlockItemUseContext context, BlockState state) {
		return context.getLevel().setBlock(context.getClickedPos(), state, 11);
	}
	
	public static void setTileEntityNBT(World worldIn, @Nullable PlayerEntity player, BlockPos pos, ItemStack stackIn) {
		MinecraftServer minecraftserver = worldIn.getServer();
		if (minecraftserver == null) {
		} else {
			CompoundNBT compoundnbt = stackIn.getTagElement("BlockEntityTag");
			if (compoundnbt != null) {
				TileEntity tileentity = worldIn.getBlockEntity(pos);
				if (tileentity != null) {
					if (!worldIn.isClientSide && tileentity.onlyOpCanSetNbt() && (player == null || !player.canUseGameMasterBlocks())) {
						return;
					}
					
					CompoundNBT compoundnbt1 = tileentity.save(new CompoundNBT());
					CompoundNBT compoundnbt2 = compoundnbt1.copy();
					compoundnbt1.merge(compoundnbt);
					compoundnbt1.putInt("x", pos.getX());
					compoundnbt1.putInt("y", pos.getY());
					compoundnbt1.putInt("z", pos.getZ());
					if (!compoundnbt1.equals(compoundnbt2)) {
						tileentity.load(worldIn.getBlockState(pos), compoundnbt1);
						tileentity.setChanged();
					}
				}
			}

		}
	}
	
	/**
	 * Returns the unlocalized name of this item.
	 */
	public String getDescriptionId() {
		return this.getBlock().getDescriptionId();
	}
	
	/**
	 * returns a list of items with the same ID, but different meta (eg: dye returns
	 * 16 items)
	 */
	public void fillItemCategory(ItemGroup group, NonNullList<ItemStack> items) {
		if (this.allowdedIn(group)) {
			this.getBlock().fillItemCategory(group, items);
		}
		
	}
	
	/**
	 * allows items to add custom lines of information to the mouseover description
	 */
	@OnlyIn(Dist.CLIENT)
	public void appendHoverText(ItemStack stack, @Nullable World worldIn, List<ITextComponent> tooltip, ITooltipFlag flagIn) {
		super.appendHoverText(stack, worldIn, tooltip, flagIn);
		this.getBlock().appendHoverText(stack, worldIn, tooltip, flagIn);
	}
	
	public Block getBlock() {
		return this.getBlockRaw() == null ? null : this.getBlockRaw().delegate.get();
	}
	
	private Block getBlockRaw() {
		return this.block;
	}

	public boolean isFoil(ItemStack stack) {
		return true;
	}
}