package io.github.chaosawakens.common.items;

import java.util.Objects;

import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ActionResult;
import net.minecraft.util.ActionResultType;
import net.minecraft.util.Hand;
import net.minecraft.util.registry.Registry;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.world.World;
import vazkii.patchouli.api.PatchouliAPI;
import vazkii.patchouli.common.base.PatchouliSounds;

public class MobestiaryItem extends Item {
	public MobestiaryItem(Properties props) {
		super(props);
	}

//	public static boolean isOpen() {
	//	return Registry.ITEM.getKey(CAItems.MOBESTIARY.get()).equals(PatchouliAPI.get().getOpenBookGui());
//	}

//	@Override
//	@OnlyIn(Dist.CLIENT)
//	public void appendHoverText(ItemStack itemStack, @Nullable World world, List<ITextComponent> tooltip, ITooltipFlag flag) {
//		tooltip.add(getEdition().copy().withStyle(TextFormatting.GRAY));
//	}

	@Override
	public ActionResult<ItemStack> use(World world, PlayerEntity player, Hand hand) {
		ItemStack stack = player.getItemInHand(hand);

		if (player instanceof ServerPlayerEntity) {
			PatchouliAPI.get().openBookGUI((ServerPlayerEntity) player, Registry.ITEM.getKey(this));
			player.playSound(PatchouliSounds.book_open, 1F, (float) (0.7 + Math.random() * 0.4));
		}

		return new ActionResult<>(ActionResultType.SUCCESS, stack);
	}

//	public static ITextComponent getEdition() {
//		return PatchouliAPI.get().getSubtitle(Registry.ITEM.getKey(CAItems.MOBESTIARY.get()));
//	}

	public static ITextComponent getTitle(ItemStack stack) {
		ITextComponent title = stack.getDisplayName();

		String akashicTomeNBT = "akashictome:displayName";
		if (stack.hasTag() && Objects.requireNonNull(stack.getTag()).contains(akashicTomeNBT)) {
			title = ITextComponent.Serializer.fromJson(stack.getTag().getString(akashicTomeNBT));
		}

		return title;
	}
}
