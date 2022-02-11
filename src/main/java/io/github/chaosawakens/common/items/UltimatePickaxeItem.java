package io.github.chaosawakens.common.items;

import com.google.gson.JsonObject;
import io.github.chaosawakens.api.IAutoEnchantable;
import net.minecraft.enchantment.EnchantmentData;
import net.minecraft.inventory.Inventory;
import net.minecraft.item.IItemTier;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.FurnaceRecipe;
import net.minecraft.item.crafting.IRecipeType;
import net.minecraft.loot.LootContext;
import net.minecraft.loot.conditions.ILootCondition;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.World;
import net.minecraftforge.common.loot.GlobalLootModifierSerializer;
import net.minecraftforge.common.loot.LootModifier;

import javax.annotation.Nonnull;
import java.util.ArrayList;
import java.util.List;

public class UltimatePickaxeItem extends EnchantedPickaxeItem implements IAutoEnchantable{
//	private static final InventoryFake FAKE_INVENTORY = new InventoryFake();
//	private final ILootCondition[] c;

	public UltimatePickaxeItem(IItemTier tier, int attackDamageIn, float attackSpeedIn, Properties builderIn, EnchantmentData[] enchantments) {
		super(tier, attackDamageIn, attackSpeedIn, builderIn, enchantments);
	}
	
	@Override
	public int getEntityLifespan(ItemStack itemStack, World world) {
		return -1;
	}
	
	@Override
	public boolean isFireResistant() {
		return true;
	}
	
	@Override
	public float getXpRepairRatio(ItemStack stack) {
		return 20.0F;
	}

	/*@Override
	public boolean mineBlock(ItemStack stack, World w, BlockState state, BlockPos pos, LivingEntity e) {
		PlayerEntity p = (PlayerEntity) e;

		if (state != null) {
            if (!w.isClientSide && p != null) {
                p.getItemInHand(Hand.MAIN_HAND).hurtAndBreak(1, p, (player) -> player.broadcastBreakEvent(EquipmentSlotType.MAINHAND));
            }
            for (int x = -1; x < 2; x++) {
                for (int y = -1; y < 2; y++) {
                    for (int z = -1; z < 2; z++) {
                        BlockPos targetPos = new BlockPos(p.getX() + x, p.getY() + y, p.getZ() + z);
                        if (w.isEmptyBlock(targetPos.above())) {
                            state = w.getBlockState(targetPos).getToolModifiedState(w, targetPos, p, p.getItemInHand(Hand.MAIN_HAND), ToolType.PICKAXE);
                            if (state != null) {
                                if (!w.isClientSide) {
                                    w.setBlock(targetPos, state, 11);
                                }
                            }
                        }
                    }
                }
            }
		}
		return ActionResultType.SUCCESS != null;
    }*/

	public static class UltimateAutoSmeltingModifier extends LootModifier{
		public UltimateAutoSmeltingModifier(ILootCondition[] conditionsIn) {
			super(conditionsIn);
		}
		
		protected final ItemStack getSmeltedOutput(LootContext context, ItemStack stack) {
			if (context.getLevel() != null) {
		        return context.getLevel().getRecipeManager().getRecipeFor(IRecipeType.SMELTING, new Inventory(stack), context.getLevel())
                    .map(FurnaceRecipe::getResultItem)
                    .filter(itemStack -> !itemStack.isEmpty())
                    .map(itemStack -> copyStackWithSize(itemStack, stack.getCount() * itemStack.getCount() + random.nextInt(8) - random.nextInt(3)))
                    .orElse(stack);
			} else
				return stack;
		}
		
	    @Nonnull
	    public static ItemStack copyStackWithSize(@Nonnull ItemStack itemStack, int size) {
	        if (size == 0)
	            return ItemStack.EMPTY;
	        ItemStack copy = itemStack.copy();
	        copy.setCount(size);
	        return copy;
	    }
		
		@Nonnull
		@Override
		protected List<ItemStack> doApply(List<ItemStack> generatedLoot, LootContext context) {
            ArrayList<ItemStack> ret = new ArrayList<ItemStack>();
            generatedLoot.forEach((stack) -> ret.add(getSmeltedOutput(context, stack)));
            return ret;
		}
		
        public static class Serializer extends GlobalLootModifierSerializer<UltimateAutoSmeltingModifier> {
            @Override
            public UltimateAutoSmeltingModifier read(ResourceLocation name, JsonObject json, ILootCondition[] conditionsIn) {
                return new UltimateAutoSmeltingModifier(conditionsIn);
            }

            @Override
            public JsonObject write(UltimateAutoSmeltingModifier instance) {
                return makeConditions(instance.conditions);
            }
        }
	}
}
