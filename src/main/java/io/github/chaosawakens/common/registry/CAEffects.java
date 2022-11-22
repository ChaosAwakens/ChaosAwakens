package io.github.chaosawakens.common.registry;

import io.github.chaosawakens.ChaosAwakens;
import io.github.chaosawakens.common.effects.BurnsEffect;
import io.github.chaosawakens.common.effects.ParalysisEffect;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.item.crafting.Ingredient;
import net.minecraft.potion.Effect;
import net.minecraft.potion.EffectInstance;
import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionUtils;
import net.minecraftforge.common.brewing.BrewingRecipeRegistry;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;

public class CAEffects {
	public static final DeferredRegister<Effect> EFFECTS = DeferredRegister.create(ForgeRegistries.POTIONS, ChaosAwakens.MODID);
	public static final DeferredRegister<Potion> POTIONS = DeferredRegister.create(ForgeRegistries.POTION_TYPES, ChaosAwakens.MODID);

	public static final RegistryObject<Effect> PARALYSIS_EFFECT = EFFECTS.register("paralysis", ParalysisEffect::new);
	public static final RegistryObject<Effect> BURNS_EFFECT = EFFECTS.register("burns", BurnsEffect::new);

	// These are for Creative Use Only.
	public static final RegistryObject<Potion> PARALYSIS = POTIONS.register("paralysis", () -> new Potion(new EffectInstance(PARALYSIS_EFFECT.get(), 600, 0)));
	public static final RegistryObject<Potion> LONG_PARALYSIS = POTIONS.register("long_paralysis", () -> new Potion(new EffectInstance(PARALYSIS_EFFECT.get(), 1400, 0)));
	public static final RegistryObject<Potion> BURNS = POTIONS.register("burns", () -> new Potion(new EffectInstance(BURNS_EFFECT.get(), 600, 0)));
	public static final RegistryObject<Potion> LONG_BURNS = POTIONS.register("long_burns", () -> new Potion(new EffectInstance(BURNS_EFFECT.get(), 1400, 0)));
	
	public static void registerBrewingRecipes() { // Recipes that are commented out here are examples for future recipes.
//		addBrewingRecipe(Potions.AWKWARD, CAItems.BASILISK_SCALE.get(), PARALYSIS.get());
//		addBrewingRecipe(PARALYSIS.get(), Items.REDSTONE, LONG_PARALYSIS.get());
	}

	public static void addBrewingRecipe(Potion potionBase, Item itemIngredient, Potion potionOutput){
		BrewingRecipeRegistry.addRecipe(Ingredient.of(PotionUtils.setPotion(new ItemStack(Items.POTION), potionBase)), Ingredient.of(itemIngredient), PotionUtils.setPotion(new ItemStack(Items.POTION), potionOutput));
	}
}
