package io.github.chaosawakens.data;

import io.github.chaosawakens.registry.ModBlocks;
import io.github.chaosawakens.registry.ModItems;
import net.minecraft.advancements.criterion.InventoryChangeTrigger;
import net.minecraft.data.*;
import net.minecraft.item.Items;
import net.minecraftforge.common.crafting.conditions.IConditionBuilder;

import java.util.function.Consumer;

public class ModRecipes extends RecipeProvider implements IConditionBuilder {

    public ModRecipes(DataGenerator generatorIn) {
        super(generatorIn);
    }

    @Override
    protected void registerRecipes(Consumer<IFinishedRecipe> consumer) {
        // EMERALD
        ShapedRecipeBuilder.shapedRecipe(ModItems.EMERALD_PICKAXE.get())
                .patternLine("EEE")
                .patternLine(" s ")
                .patternLine(" s ")
                .key('E', Items.EMERALD)
                .key('s', Items.STICK)
                .addCriterion("emerald", InventoryChangeTrigger.Instance.forItems(Items.EMERALD))
                .build(consumer);
        ShapedRecipeBuilder.shapedRecipe(ModItems.EMERALD_SWORD.get())
                .patternLine("E")
                .patternLine("E")
                .patternLine("s")
                .key('E', Items.EMERALD)
                .key('s', Items.STICK)
                .addCriterion("emerald", InventoryChangeTrigger.Instance.forItems(Items.EMERALD))
                .build(consumer);
        ShapedRecipeBuilder.shapedRecipe(ModItems.EMERALD_AXE.get())
                .patternLine("EE")
                .patternLine("sE")
                .patternLine("s ")
                .key('E', Items.EMERALD)
                .key('s', Items.STICK)
                .addCriterion("emerald", InventoryChangeTrigger.Instance.forItems(Items.EMERALD))
                .build(consumer);
        ShapedRecipeBuilder.shapedRecipe(ModItems.EMERALD_SHOVEL.get())
                .patternLine("E")
                .patternLine("s")
                .patternLine("s")
                .key('E', Items.EMERALD)
                .key('s', Items.STICK)
                .addCriterion("emerald", InventoryChangeTrigger.Instance.forItems(Items.EMERALD))
                .build(consumer);
        ShapedRecipeBuilder.shapedRecipe(ModItems.EMERALD_HOE.get())
                .patternLine("EE")
                .patternLine("s ")
                .patternLine("s ")
                .key('E', Items.EMERALD)
                .key('s', Items.STICK)
                .addCriterion("emerald", InventoryChangeTrigger.Instance.forItems(Items.EMERALD))
                .build(consumer);
        ShapedRecipeBuilder.shapedRecipe(ModItems.EMERALD_HELMET.get())
                .patternLine("EEE")
                .patternLine("E E")
                .key('E', Items.EMERALD)
                .addCriterion("emerald", InventoryChangeTrigger.Instance.forItems(Items.EMERALD))
                .build(consumer);
        ShapedRecipeBuilder.shapedRecipe(ModItems.EMERALD_CHESTPLATE.get())
                .patternLine("E E")
                .patternLine("EEE")
                .patternLine("EEE")
                .key('E', Items.EMERALD)
                .addCriterion("emerald", InventoryChangeTrigger.Instance.forItems(Items.EMERALD))
                .build(consumer);
        ShapedRecipeBuilder.shapedRecipe(ModItems.EMERALD_LEGGINGS.get())
                .patternLine("EEE")
                .patternLine("E E")
                .patternLine("E E")
                .key('E', Items.EMERALD)
                .addCriterion("emerald", InventoryChangeTrigger.Instance.forItems(Items.EMERALD))
                .build(consumer);
        ShapedRecipeBuilder.shapedRecipe(ModItems.EMERALD_BOOTS.get())
                .patternLine("E E")
                .patternLine("E E")
                .key('E', Items.EMERALD)
                .addCriterion("emerald", InventoryChangeTrigger.Instance.forItems(Items.EMERALD))
                .build(consumer);

        // RUBY
        ShapedRecipeBuilder.shapedRecipe(ModItems.RUBY_PICKAXE.get())
                .patternLine("RRR")
                .patternLine(" s ")
                .patternLine(" s ")
                .key('R', ModItems.RUBY.get())
                .key('s', Items.STICK)
                .addCriterion("ruby", InventoryChangeTrigger.Instance.forItems(ModItems.RUBY.get()))
                .build(consumer);
        ShapedRecipeBuilder.shapedRecipe(ModItems.RUBY_SWORD.get())
                .patternLine("R")
                .patternLine("R")
                .patternLine("s")
                .key('R', ModItems.RUBY.get())
                .key('s', Items.STICK)
                .addCriterion("ruby", InventoryChangeTrigger.Instance.forItems(ModItems.RUBY.get()))
                .build(consumer);
        ShapedRecipeBuilder.shapedRecipe(ModItems.RUBY_AXE.get())
                .patternLine("RR")
                .patternLine("sR")
                .patternLine("s ")
                .key('R', ModItems.RUBY.get())
                .key('s', Items.STICK)
                .addCriterion("ruby", InventoryChangeTrigger.Instance.forItems(ModItems.RUBY.get()))
                .build(consumer);
        ShapedRecipeBuilder.shapedRecipe(ModItems.RUBY_SHOVEL.get())
                .patternLine("R")
                .patternLine("s")
                .patternLine("s")
                .key('R', ModItems.RUBY.get())
                .key('s', Items.STICK)
                .addCriterion("ruby", InventoryChangeTrigger.Instance.forItems(ModItems.RUBY.get()))
                .build(consumer);
        ShapedRecipeBuilder.shapedRecipe(ModItems.RUBY_HOE.get())
                .patternLine("RR")
                .patternLine("s ")
                .patternLine("s ")
                .key('R', ModItems.RUBY.get())
                .key('s', Items.STICK)
                .addCriterion("ruby", InventoryChangeTrigger.Instance.forItems(ModItems.RUBY.get()))
                .build(consumer);
        ShapedRecipeBuilder.shapedRecipe(ModItems.RUBY_HELMET.get())
                .patternLine("RRR")
                .patternLine("R R")
                .key('R', ModItems.RUBY.get())
                .addCriterion("ruby", InventoryChangeTrigger.Instance.forItems(ModItems.RUBY.get()))
                .build(consumer);
        ShapedRecipeBuilder.shapedRecipe(ModItems.RUBY_CHESTPLATE.get())
                .patternLine("R R")
                .patternLine("RRR")
                .patternLine("RRR")
                .key('R', ModItems.RUBY.get())
                .addCriterion("ruby", InventoryChangeTrigger.Instance.forItems(ModItems.RUBY.get()))
                .build(consumer);
        ShapedRecipeBuilder.shapedRecipe(ModItems.RUBY_LEGGINGS.get())
                .patternLine("RRR")
                .patternLine("R R")
                .patternLine("R R")
                .key('R', ModItems.RUBY.get())
                .addCriterion("ruby", InventoryChangeTrigger.Instance.forItems(ModItems.RUBY.get()))
                .build(consumer);
        ShapedRecipeBuilder.shapedRecipe(ModItems.RUBY_BOOTS.get())
                .patternLine("R R")
                .patternLine("R R")
                .key('R', ModItems.RUBY.get())
                .addCriterion("ruby", InventoryChangeTrigger.Instance.forItems(ModItems.RUBY.get()))
                .build(consumer);
        ShapedRecipeBuilder.shapedRecipe(ModBlocks.RUBY_BLOCK.get())
                .patternLine("RRR")
                .patternLine("RRR")
                .patternLine("RRR")
                .key('R', ModItems.RUBY.get())
                .addCriterion("ruby", InventoryChangeTrigger.Instance.forItems(ModItems.RUBY.get()))
                .build(consumer);
        ShapelessRecipeBuilder.shapelessRecipe(ModItems.RUBY.get(), 9)
                .addIngredient(ModBlocks.RUBY_BLOCK.get())
                .addCriterion("ruby", InventoryChangeTrigger.Instance.forItems(ModBlocks.RUBY_BLOCK.get()))
                .build(consumer);

        // AMETHYST
        ShapedRecipeBuilder.shapedRecipe(ModItems.AMETHYST_PICKAXE.get())
                .patternLine("AAA")
                .patternLine(" s ")
                .patternLine(" s ")
                .key('A', ModItems.AMETHYST.get())
                .key('s', Items.STICK)
                .addCriterion("amethyst", InventoryChangeTrigger.Instance.forItems(ModItems.AMETHYST.get()))
                .build(consumer);
        ShapedRecipeBuilder.shapedRecipe(ModItems.AMETHYST_SWORD.get())
                .patternLine("A")
                .patternLine("A")
                .patternLine("s")
                .key('A', ModItems.AMETHYST.get())
                .key('s', Items.STICK)
                .addCriterion("amethyst", InventoryChangeTrigger.Instance.forItems(ModItems.AMETHYST.get()))
                .build(consumer);
        ShapedRecipeBuilder.shapedRecipe(ModItems.AMETHYST_AXE.get())
                .patternLine("AA")
                .patternLine("sA")
                .patternLine("s ")
                .key('A', ModItems.AMETHYST.get())
                .key('s', Items.STICK)
                .addCriterion("amethyst", InventoryChangeTrigger.Instance.forItems(ModItems.AMETHYST.get()))
                .build(consumer);
        ShapedRecipeBuilder.shapedRecipe(ModItems.AMETHYST_SHOVEL.get())
                .patternLine("A")
                .patternLine("s")
                .patternLine("s")
                .key('A', ModItems.AMETHYST.get())
                .key('s', Items.STICK)
                .addCriterion("amethyst", InventoryChangeTrigger.Instance.forItems(ModItems.AMETHYST.get()))
                .build(consumer);
        ShapedRecipeBuilder.shapedRecipe(ModItems.AMETHYST_HOE.get())
                .patternLine("AA")
                .patternLine("s ")
                .patternLine("s ")
                .key('A', ModItems.AMETHYST.get())
                .key('s', Items.STICK)
                .addCriterion("amethyst", InventoryChangeTrigger.Instance.forItems(ModItems.AMETHYST.get()))
                .build(consumer);
        ShapedRecipeBuilder.shapedRecipe(ModItems.AMETHYST_HELMET.get())
                .patternLine("AAA")
                .patternLine("A A")
                .key('A', ModItems.AMETHYST.get())
                .addCriterion("amethyst", InventoryChangeTrigger.Instance.forItems(ModItems.AMETHYST.get()))
                .build(consumer);
        ShapedRecipeBuilder.shapedRecipe(ModItems.AMETHYST_CHESTPLATE.get())
                .patternLine("A A")
                .patternLine("AAA")
                .patternLine("AAA")
                .key('A', ModItems.AMETHYST.get())
                .addCriterion("amethyst", InventoryChangeTrigger.Instance.forItems(ModItems.AMETHYST.get()))
                .build(consumer);
        ShapedRecipeBuilder.shapedRecipe(ModItems.AMETHYST_LEGGINGS.get())
                .patternLine("AAA")
                .patternLine("A A")
                .patternLine("A A")
                .key('A', ModItems.AMETHYST.get())
                .addCriterion("amethyst", InventoryChangeTrigger.Instance.forItems(ModItems.AMETHYST.get()))
                .build(consumer);
        ShapedRecipeBuilder.shapedRecipe(ModItems.AMETHYST_BOOTS.get())
                .patternLine("A A")
                .patternLine("A A")
                .key('A', ModItems.AMETHYST.get())
                .addCriterion("amethyst", InventoryChangeTrigger.Instance.forItems(ModItems.AMETHYST.get()))
                .build(consumer);
        ShapedRecipeBuilder.shapedRecipe(ModBlocks.AMETHYST_BLOCK.get())
                .patternLine("AAA")
                .patternLine("AAA")
                .patternLine("AAA")
                .key('A', ModItems.AMETHYST.get())
                .addCriterion("amethyst", InventoryChangeTrigger.Instance.forItems(ModItems.AMETHYST.get()))
                .build(consumer);
        ShapelessRecipeBuilder.shapelessRecipe(ModItems.AMETHYST.get(), 9)
                .addIngredient(ModBlocks.AMETHYST_BLOCK.get())
                .addCriterion("amethyst", InventoryChangeTrigger.Instance.forItems(ModBlocks.AMETHYST_BLOCK.get()))
                .build(consumer);
    }
}