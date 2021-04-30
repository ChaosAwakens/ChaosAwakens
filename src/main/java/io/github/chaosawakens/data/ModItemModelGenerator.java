package io.github.chaosawakens.data;

import io.github.chaosawakens.ChaosAwakens;
import io.github.chaosawakens.registry.ModBlocks;
import io.github.chaosawakens.registry.ModItems;
import net.minecraft.data.DataGenerator;
import net.minecraft.item.Item;
import net.minecraftforge.client.model.generators.ItemModelBuilder;
import net.minecraftforge.client.model.generators.ItemModelProvider;
import net.minecraftforge.client.model.generators.ModelBuilder;
import net.minecraftforge.common.data.ExistingFileHelper;

import javax.annotation.Nonnull;
import java.util.function.Supplier;

public class ModItemModelGenerator extends ItemModelProvider {

    public ModItemModelGenerator(DataGenerator generator, ExistingFileHelper existingFileHelper) {
        super(generator, ChaosAwakens.MODID, existingFileHelper);
    }

    @Override
    protected void registerModels() {
        generated(ModItems.CORNDOG, ModItems.COOKED_CORNDOG, ModItems.BACON, ModItems.COOKED_BACON,
                ModItems.CORN, ModItems.TOMATO, ModItems.LETTUCE, ModItems.CHEESE, ModItems.GARDEN_SALAD,
                ModItems.BLT, ModItems.AMETHYST, ModItems.RUBY, ModItems.TIGERS_EYE, ModItems.TITANIUM_INGOT,
                ModItems.TITANIUM_NUGGET, ModItems.URANIUM_INGOT, ModItems.URANIUM_NUGGET, ModItems.ALUMINIUM_INGOT,
                ModItems.AMETHYST_HELMET, ModItems.AMETHYST_CHESTPLATE, ModItems.AMETHYST_LEGGINGS, ModItems.AMETHYST_BOOTS,
                ModItems.EMERALD_HELMET, ModItems.EMERALD_CHESTPLATE, ModItems.EMERALD_LEGGINGS, ModItems.EMERALD_BOOTS,
                ModItems.RUBY_HELMET, ModItems.RUBY_CHESTPLATE, ModItems.RUBY_LEGGINGS, ModItems.RUBY_BOOTS,
                ModItems.TIGERS_EYE_HELMET, ModItems.TIGERS_EYE_CHESTPLATE, ModItems.TIGERS_EYE_LEGGINGS, ModItems.TIGERS_EYE_BOOTS,
                ModItems.ULTIMATE_HELMET, ModItems.ULTIMATE_CHESTPLATE, ModItems.ULTIMATE_LEGGINGS, ModItems.ULTIMATE_BOOTS, ModItems.EXPERIENCE_HELMET, ModItems.EXPERIENCE_CHESTPLATE, ModItems.EXPERIENCE_LEGGINGS, ModItems.EXPERIENCE_BOOTS);
        handHeld(ModItems.AMETHYST_SWORD, ModItems.AMETHYST_PICKAXE, ModItems.AMETHYST_SHOVEL, ModItems.AMETHYST_AXE, ModItems.AMETHYST_HOE,
                ModItems.EMERALD_SWORD, ModItems.EMERALD_PICKAXE, ModItems.EMERALD_SHOVEL, ModItems.EMERALD_AXE, ModItems.EMERALD_HOE,
                ModItems.RUBY_SWORD, ModItems.RUBY_PICKAXE, ModItems.RUBY_SHOVEL, ModItems.RUBY_AXE, ModItems.RUBY_HOE,
                ModItems.TIGERS_EYE_SWORD, ModItems.TIGERS_EYE_PICKAXE, ModItems.TIGERS_EYE_SHOVEL, ModItems.TIGERS_EYE_AXE, ModItems.TIGERS_EYE_HOE,
                ModItems.ULTIMATE_SWORD, ModItems.ULTIMATE_PICKAXE, ModItems.ULTIMATE_SHOVEL, ModItems.ULTIMATE_AXE, ModItems.ULTIMATE_HOE, ModItems.EXPERIENCE_SWORD,
                ModItems.THUNDER_STAFF);
        this.simpleWithExistingParent(ModBlocks.ALUMINIUM_BLOCK.getId().toString().replaceFirst("chaosawakens:", ""));
        this.simpleWithExistingParent(ModBlocks.ALUMINIUM_ORE.getId().toString().replaceFirst("chaosawakens:", ""));
        this.simpleWithExistingParent(ModBlocks.AMETHYST_BLOCK.getId().toString().replaceFirst("chaosawakens:", ""));
        this.simpleWithExistingParent(ModBlocks.AMETHYST_ORE.getId().toString().replaceFirst("chaosawakens:", ""));
        this.simpleWithExistingParent(ModBlocks.RUBY_BLOCK.getId().toString().replaceFirst("chaosawakens:", ""));
        this.simpleWithExistingParent(ModBlocks.RUBY_ORE.getId().toString().replaceFirst("chaosawakens:", ""));
        this.simpleWithExistingParent(ModBlocks.TIGERS_EYE_BLOCK.getId().toString().replaceFirst("chaosawakens:", ""));
        this.simpleWithExistingParent(ModBlocks.TIGERS_EYE_ORE.getId().toString().replaceFirst("chaosawakens:", ""));
        this.simpleWithExistingParent(ModBlocks.TITANIUM_BLOCK.getId().toString().replaceFirst("chaosawakens:", ""));
        this.simpleWithExistingParent(ModBlocks.TITANIUM_ORE.getId().toString().replaceFirst("chaosawakens:", ""));
        this.simpleWithExistingParent(ModBlocks.URANIUM_BLOCK.getId().toString().replaceFirst("chaosawakens:", ""));
        this.simpleWithExistingParent(ModBlocks.URANIUM_ORE.getId().toString().replaceFirst("chaosawakens:", ""));
    }

    private ModelBuilder<ItemModelBuilder> simpleWithExistingParent(String path)
    {
        return this.withExistingParent(path, modLoc("block/" + path));
    }

    @Nonnull
    @Override
    public String getName() {
        return ChaosAwakens.MODNAME + " Item models";
    }

    @SafeVarargs
    protected final void generated(final Supplier<? extends Item>... items) {

        for (Supplier<? extends Item> item : items) {
            generated(item);
        }
    }

    protected void generated(final Supplier<? extends Item> item) {
        ModDataGenHelper.generatedItem(this, item.get());
    }

    @SafeVarargs
    protected final void handHeld(final Supplier<? extends Item>... items) {

        for (Supplier<? extends Item> item : items) {
            handHeld(item);
        }
    }

    protected void handHeld(final Supplier<? extends Item> item) {
        ModDataGenHelper.handHeldItem(this, item.get());
    }
}
