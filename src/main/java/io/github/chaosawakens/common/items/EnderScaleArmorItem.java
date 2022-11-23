package io.github.chaosawakens.common.items;

import java.awt.Color;
import java.util.List;
import java.util.Map;
import java.util.function.Consumer;

import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.vertex.IVertexBuilder;

import io.github.chaosawakens.ChaosAwakens;
import io.github.chaosawakens.api.IUtilityHelper;
import io.github.chaosawakens.client.config.CAClientConfig;
import io.github.chaosawakens.common.config.CACommonConfig;
import io.github.chaosawakens.common.registry.CAItems;
import io.github.chaosawakens.common.util.EnumUtils;
import net.minecraft.client.entity.player.AbstractClientPlayerEntity;
import net.minecraft.client.renderer.IRenderTypeBuffer;
import net.minecraft.client.renderer.ItemRenderer;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.IEntityRenderer;
import net.minecraft.client.renderer.entity.layers.ElytraLayer;
import net.minecraft.client.renderer.entity.model.ElytraModel;
import net.minecraft.client.renderer.entity.model.EntityModel;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnchantmentData;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.inventory.EquipmentSlotType;
import net.minecraft.item.ElytraItem;
import net.minecraft.item.IArmorMaterial;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.nbt.ListNBT;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.StringTextComponent;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.world.World;

//TODO Elytra set bonus W.I.P 
public class EnderScaleArmorItem extends EnchantedArmorItem {
	public static final String ENDERELYTRA = ChaosAwakens.MODID + ":ElytraFullSetBonus";
	
	public EnderScaleArmorItem(IArmorMaterial materialIn, EquipmentSlotType slot, Properties builderIn, EnchantmentData[] enchantments) {
		super(materialIn, slot, builderIn, enchantments);
	}
	
	public static ItemStack getElytraOnArmor(ItemStack stack) {
		CompoundNBT elytraTag = stack.getTagElement(ENDERELYTRA);
		ItemStack elytra = elytraTag != null ? ItemStack.of(elytraTag) : ItemStack.EMPTY;
		return elytra;
	}
	
	@SuppressWarnings("unused")
	public static void handleEnchantments(ItemStack chestPlate, ItemStack elytra, CompoundNBT elytraSaveData) {
		//if (elytra.getItem() != Items.ELYTRA) {
			elytra = new ItemStack(Items.ELYTRA);
		//}
		
		EquipmentSlotType type = chestPlate.getEquipmentSlot();
		ListNBT list = elytra.getEnchantmentTags();
		
	    Map<Enchantment, Integer> chestplateEnch = EnchantmentHelper.getEnchantments(chestPlate);
	    Map<Enchantment, Integer> elytraEnch = EnchantmentHelper.getEnchantments(elytra);
	    
	    for (Enchantment e : chestplateEnch.keySet()) {
	    	if (e == null || !e.canEnchant(elytra)) continue;
	    	
	    	//if (type != null) {		
	    	//	if (!type.equals(EquipmentSlotType.CHEST)) {		
	    	//		return;
	    	//	}
	    	//}
	    	
	    	int cpl = chestplateEnch.get(e);
	    	int el = elytraEnch.getOrDefault(e, 0);
	    	cpl = el == cpl ? cpl + 1 : Math.max(cpl, el);
	    	
	    	for (Enchantment ee : elytraEnch.keySet()) {
	    		if (e != ee && !ee.isCompatibleWith(e)) return;
	    	}
	    	
	    	if (cpl >= e.getMaxLevel()) cpl = e.getMaxLevel();
	    	
	    	//Copy enchantments over to elytra
	    	elytraEnch.put(e, cpl);
	    	
	    	//elytraSaveData.putString("id", String.valueOf(Registry.ENCHANTMENT.getKey(e)));
	    	//elytraSaveData.putInt("lvl", cpl);
	    	//list.add((INBT) (e.getTags()));
	    }
	    EnchantmentHelper.setEnchantments(elytraEnch, elytra);
	    EnchantmentHelper.setEnchantments(chestplateEnch, chestPlate);
	}
	
	public static void toggleElytra(ItemStack chestPlate, ItemStack elytra, boolean toggle) {
		CompoundNBT nbt = new CompoundNBT();
		if (toggle) {	
			elytra = new ItemStack(Items.ELYTRA);
			elytra.setDamageValue(elytra.getMaxDamage());
			chestPlate.addTagElement(ENDERELYTRA, nbt);
			handleEnchantments(chestPlate, elytra, nbt);
		}
		if (!toggle) {
			if (chestPlate.getTag() != null && chestPlate.getTagElement(ENDERELYTRA) != null) {
				chestPlate.removeTagKey(ENDERELYTRA);
			}
		}
	}
	
	public static boolean isElytraToggled(ItemStack stack) {
		return stack.hasTag() && stack.getTag() != null && stack.getTagElement(ENDERELYTRA) != null;
	}
	
/*	public static void damage(ItemStack elytra, ItemStack chestPlate, LivingEntity entity, int damage) {
		EnumUtils.ElytraDamageType damageType = CACommonConfig.COMMON.enderDragonScaleArmorElytraDamageType.get();
		//if (elytra.getItem() != Items.ELYTRA) {
			elytra = new ItemStack(Items.ELYTRA);
			elytra.setDamageValue(elytra.getMaxDamage());
		//}
		
		if (damageType == EnumUtils.ElytraDamageType.ELYTRA) {
			if (entity.fallFlyTicks + 1 % 20 == 0) {
				chestPlate.hurtAndBreak(damage, entity, e -> e.broadcastBreakEvent(EquipmentSlotType.CHEST));
				elytra.hurtAndBreak(damage, entity, e -> e.broadcastBreakEvent(EquipmentSlotType.CHEST));
			}
		} else if (damageType == EnumUtils.ElytraDamageType.ARMOR) {
			if (chestPlate.isDamaged()) {
				elytra.setDamageValue(chestPlate.getDamageValue());
			}
		}		
		toggleElytra(chestPlate, elytra, true);
	}*/
	
	@SuppressWarnings({})
	@Override
	public void appendHoverText(ItemStack stack, World world, List<ITextComponent> tooltip, ITooltipFlag flag) {
		if (!CAClientConfig.CLIENT.enableTooltips.get()) return;
		super.appendHoverText(stack, world, tooltip, flag);
		ItemStack elytra = getElytraOnArmor(stack);
		//if (elytra.getItem() != Items.ELYTRA) {
			elytra = new ItemStack(Items.ELYTRA);
			elytra.setDamageValue(elytra.getMaxDamage());
		//}
		
		if (isElytraToggled(stack) && stack.getItem() == CAItems.ENDER_DRAGON_SCALE_CHESTPLATE.get()) {
			if (!isElytraToggled(elytra) || stack.getTagElement(ENDERELYTRA) == null) toggleElytra(stack, elytra, true);
			tooltip.add(new StringTextComponent("-----------------").withStyle(TextFormatting.DARK_BLUE));
			tooltip.add(new StringTextComponent("Dragon Wings").withStyle(TextFormatting.AQUA));		
			tooltip.add(new StringTextComponent("Durability: " + (stack.getMaxDamage() - stack.getDamageValue()) + "/" + stack.getMaxDamage()));
			//ListNBT enchantments = elytra.getEnchantmentTags();
	//		Map<Enchantment, Integer> elytraEnch = EnchantmentHelper.getEnchantments(elytra);
		//	for (Enchantment e : elytraEnch.keySet()) {		
			//	Registry.ENCHANTMENT.getOptional(ResourceLocation.tryParse(e.getDescriptionId())).ifPresent((enchantment) -> {	
				//	tooltip.add(new StringTextComponent(enchantment.getFullname(EnchantmentHelper.getItemEnchantmentLevel(enchantment, elytra)).toString()));	
			//	});
			//}
			//ItemStack.appendEnchantmentNames(tooltip, enchantments);
			//for (int i = 0; i < enchantments.size(); i++) {
				// Map<Enchantment, Integer> elytraEnch = EnchantmentHelper.getEnchantments(elytra);
				//for (Enchantment e : EnchantmentHelper.getEnchantments(elytra).keySet()) {
					//CompoundNBT singleEnch = enchantments.getCompound(i);
					//if (!isElytraToggled(elytra) || stack.getTagElement(ENDERELYTRA) == null) toggleElytra(stack, elytra, true);
					//ItemStack.appendEnchantmentNames(tooltip, enchantments);
					//Registry.ENCHANTMENT.getOptional(ResourceLocation.tryParse(singleEnch.getString("id"))).ifPresent((enchantment) -> {
						//tooltip.add(new StringTextComponent(enchantment.getFullname(singleEnch.getInt("lvl")).toString()));
					//});
				//}
			//}
			tooltip.add(new StringTextComponent("-----------------").withStyle(TextFormatting.DARK_BLUE));
			//ChaosAwakens.debug("Tooltip: ", tooltip.toString());
			//ChaosAwakens.debug("Ench: ", EnchantmentHelper.getEnchantments(elytra).toString());
		}
	}
	
	@Override
	public <T extends LivingEntity> int damageItem(ItemStack chestPlate, int amount, T entity, Consumer<T> onBroken) {
		EnumUtils.ElytraDamageType damageType = CACommonConfig.COMMON.enderDragonScaleArmorElytraDamageType.get();
		ItemStack elytra = getElytraOnArmor(chestPlate);
		
		switch (damageType) {
		default:
			amount = chestPlate.getDamageValue();
			elytra.setDamageValue(chestPlate.getDamageValue());
			return amount;
		case ELYTRA:
			amount = elytra.getDamageValue();
			if (entity.isFallFlying()) entity.fallFlyTicks += 1;
			if (entity.isFallFlying() && entity.getFallFlyingTicks() + 1 % 20 == 0) {
				chestPlate.hurtAndBreak(amount, entity, e -> e.broadcastBreakEvent(EquipmentSlotType.CHEST));
				elytra.hurtAndBreak(amount, entity, e -> e.broadcastBreakEvent(EquipmentSlotType.CHEST));
			}
			return amount;
		case ARMOR:
			amount = chestPlate.getDamageValue();
			if (chestPlate.isDamaged()) {
				elytra.setDamageValue(chestPlate.getDamageValue());
			}
			return amount;
		}
	//	toggleElytra(chestPlate, elytra, true);
	}
	
	public static boolean canUse(ItemStack elytra, ItemStack chestPlate) {
		EnumUtils.ElytraDamageType damageType = CACommonConfig.COMMON.enderDragonScaleArmorElytraDamageType.get();
		elytra = getElytraOnArmor(chestPlate);
		
		if (elytra.isEmpty()) return false;
		if (damageType == EnumUtils.ElytraDamageType.ELYTRA) {
			return elytra.getItem() instanceof ElytraItem && ElytraItem.isFlyEnabled(elytra);
		} else if (damageType == EnumUtils.ElytraDamageType.ARMOR) {
			return chestPlate.getDamageValue() < chestPlate.getMaxDamage() - 1;
		}
		return true;
	}
	
	//Useless
/*	public static boolean isValidForFlying(PlayerEntity player) {
		int scancode = GLFW.glfwGetKeyScancode(32);
		KeyInputEvent event = new KeyInputEvent(32, scancode, 1, 0);
		MinecraftForge.EVENT_BUS.post(event);
		return !player.isOnGround() && event.getKey() == 32 && !player.isFallFlying() && !player.isInWater() && !player.isInLava() && !player.hasEffect(Effects.LEVITATION) && !player.level.isClientSide;
	}*/
	
	public static boolean isFlying(PlayerEntity player) {
		return player.isFallFlying() && IUtilityHelper.isFullArmorSet(player, CAItems.ENDER_DRAGON_SCALE_HELMET.get(), CAItems.ENDER_DRAGON_SCALE_CHESTPLATE.get(), CAItems.ENDER_DRAGON_SCALE_LEGGINGS.get(), CAItems.ENDER_DRAGON_SCALE_BOOTS.get());
	}
	
	@Override
	public void onArmorTick(ItemStack stack, World world, PlayerEntity player) {
		if (!world.isClientSide) {
			if (player.getArmorSlots() != null) {
				if (IUtilityHelper.isFullArmorSet(player, CAItems.ENDER_DRAGON_SCALE_HELMET.get(), CAItems.ENDER_DRAGON_SCALE_CHESTPLATE.get(), CAItems.ENDER_DRAGON_SCALE_LEGGINGS.get(), CAItems.ENDER_DRAGON_SCALE_BOOTS.get())) {
					ItemStack elytra = getElytraOnArmor(stack);
					toggleElytra(stack, elytra, true);
				//	if (isValidForFlying(player)) {
			//		if (player.jumping) {
						if (!player.isFallFlying()) player.startFallFlying();
				//	}
					if ((player.isOnGround() || player.isInLava() || player.isInWall() || player.isInWater()) && player.isFallFlying()) player.stopFallFlying();
				//	}
				}
			}
		}
	}
	
	public static class DragonElytraLayer<P extends PlayerEntity, M extends EntityModel<P>> extends ElytraLayer<P, M> {
		private final ElytraModel<P> elytraModel = new ElytraModel<>();
		
		public DragonElytraLayer(IEntityRenderer<P, M> renderer) {
			super(renderer);
		}
		
		@Override
		public void render(MatrixStack stack, IRenderTypeBuffer buffer, int packedLight, P player, float limbSwing, float limbSwingAmount, float partialTicks, float tickAge, float headYaw, float headPitch) {
			ItemStack chest = player.getItemBySlot(EquipmentSlotType.CHEST);
			if (chest.getItem() != Items.ELYTRA) {
				if (chest.getTag() != null && chest.getTag().contains(ENDERELYTRA) && shouldRender(chest, player)) {
					ResourceLocation elytraLoc;
					if (player instanceof AbstractClientPlayerEntity) {
						AbstractClientPlayerEntity clientPlayer = (AbstractClientPlayerEntity) player;
						boolean elytra = clientPlayer.isLocalPlayer() && clientPlayer.isElytraLoaded();
						boolean cape = clientPlayer.isCapeLoaded();					
						if (cape) cape = false;
						
						if (elytra) {
							elytraLoc = new ResourceLocation(ChaosAwakens.MODID, "textures/entity/elytra/gray_dragon_wings.png");
						} else {
							elytraLoc = new ResourceLocation(ChaosAwakens.MODID, "textures/entity/elytra/gray_dragon_wings.png");
						}
					} else {
						elytraLoc = new ResourceLocation(ChaosAwakens.MODID, "textures/entity/elytra/gray_dragon_wings.png");
					}
					stack.pushPose();
					if (isElytraToggled(chest)) stack.translate(0, 0, 0.1);
					
					this.getParentModel().copyPropertiesTo(elytraModel);
					elytraModel.setupAnim(player, limbSwing, limbSwingAmount, tickAge, headYaw, headPitch);
					
					elytraLoc = new ResourceLocation(ChaosAwakens.MODID, "textures/entity/elytra/gray_dragon_wings.png");
			        IVertexBuilder vertex = ItemRenderer.getArmorFoilBuffer(buffer, RenderType.armorCutoutNoCull(elytraLoc), false, getElytraOnArmor(chest).isEnchanted() || chest.getItem() == CAItems.ENDER_DRAGON_SCALE_CHESTPLATE.get() && chest.isEnchanted());
			        Color elytraColor = new Color(1.0F, 1.0F, 1.0F, 1.0F);
			        
			        float r = elytraColor.getRed() / 255;
			        float g = elytraColor.getGreen() / 255;
			        float b = elytraColor.getBlue() / 255;
			        float a = elytraColor.getAlpha() / 255;
			        
			        elytraModel.renderToBuffer(stack, vertex, packedLight, OverlayTexture.NO_OVERLAY, r, g, b, a);
			        stack.popPose();
				}
			}
		}
		
		@Override
		public boolean shouldRender(ItemStack stack, P entity) {			
			return entity instanceof LivingEntity ? IUtilityHelper.isFullArmorSet(entity, CAItems.ENDER_DRAGON_SCALE_HELMET.get(), CAItems.ENDER_DRAGON_SCALE_CHESTPLATE.get(), CAItems.ENDER_DRAGON_SCALE_LEGGINGS.get(), CAItems.ENDER_DRAGON_SCALE_BOOTS.get()) && EnderScaleArmorItem.isElytraToggled(stack) : false;			
		}
	}
}
