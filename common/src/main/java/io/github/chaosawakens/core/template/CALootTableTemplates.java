package io.github.chaosawakens.core.template;

import com.mememan.nexus.util.LootUtil;
import io.github.chaosawakens.content.block.vegetation.FruitableLeavesBlock;
import io.github.chaosawakens.content.block.vegetation.generic.CropInstance;
import net.minecraft.advancements.critereon.StatePropertiesPredicate;
import net.minecraft.world.item.enchantment.Enchantments;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.storage.loot.LootPool;
import net.minecraft.world.level.storage.loot.LootTable;
import net.minecraft.world.level.storage.loot.entries.LootItem;
import net.minecraft.world.level.storage.loot.functions.ApplyBonusCount;
import net.minecraft.world.level.storage.loot.functions.SetItemCountFunction;
import net.minecraft.world.level.storage.loot.predicates.ExplosionCondition;
import net.minecraft.world.level.storage.loot.predicates.LootItemBlockStatePropertyCondition;
import net.minecraft.world.level.storage.loot.providers.number.ConstantValue;
import net.minecraft.world.level.storage.loot.providers.number.UniformGenerator;

import java.util.function.Supplier;

public final class CALootTableTemplates {

    private CALootTableTemplates() {
        throw new IllegalAccessError("Attempted to construct instance of template class! (CALootTableTemplates)");
    }

    public static LootTable.Builder dropLeavesRipe(Supplier<Block> targetBlock) {
        return LootUtil.dropLeaves(targetBlock).withPool(LootPool.lootPool()
                .setRolls(ConstantValue.exactly(1.0F))
                .when(LootItemBlockStatePropertyCondition.hasBlockStateProperties(targetBlock.get())
                        .setProperties(StatePropertiesPredicate.Builder.properties()
                                .hasProperty(FruitableLeavesBlock.RIPE, true)))
                .add(LootItem.lootTableItem(((FruitableLeavesBlock) targetBlock.get()).getFruitItem().get())
                        .apply(SetItemCountFunction.setCount(UniformGenerator.between(((FruitableLeavesBlock) targetBlock.get()).getMinFruitCount(), ((FruitableLeavesBlock) targetBlock.get()).getMaxFruitCount())))));
    }

    public static LootTable.Builder dropCrop(Supplier<Block> targetBlock) {
        if (targetBlock.get() instanceof CropInstance crop) {
            return LootTable.lootTable().withPool(LootPool.lootPool()
                            .add((LootItem.lootTableItem(crop.getProduce().get())
                                    .when(LootItemBlockStatePropertyCondition.hasBlockStateProperties(targetBlock.get())
                                            .setProperties(StatePropertiesPredicate.Builder.properties().hasProperty(crop.getAgeProperty(), crop.getMaxAge()))))
                                    .otherwise(LootItem.lootTableItem(crop.getSeed().get()))))
                    .withPool(LootPool.lootPool().when(LootItemBlockStatePropertyCondition.hasBlockStateProperties(targetBlock.get())
                                    .setProperties(StatePropertiesPredicate.Builder.properties().hasProperty(crop.getAgeProperty(), crop.getMaxAge())))
                            .add(LootItem.lootTableItem(crop.getSeed().get())
                                    .apply(ApplyBonusCount.addBonusBinomialDistributionCount(Enchantments.BLOCK_FORTUNE, 0.5714286F, 3))));
        } else {
            throw new IllegalArgumentException("Target block must be instance of CropInstance");
        }
    }

    public static LootTable.Builder dropCropBodyBlock(Supplier<Block> targetBlock) {
        if (targetBlock.get() instanceof CropInstance crop) {
            return LootTable.lootTable().withPool(LootPool.lootPool()
                            .add((LootItem.lootTableItem(crop.getProduce().get())
                                    .when(ExplosionCondition.survivesExplosion()))))
                    .withPool(LootPool.lootPool().when(ExplosionCondition.survivesExplosion())
                            .add(LootItem.lootTableItem(crop.getSeed().get())
                                    .apply(ApplyBonusCount.addBonusBinomialDistributionCount(Enchantments.BLOCK_FORTUNE, 0.5714286F, 3))));
        } else {
            throw new IllegalArgumentException("Target block must be instance of CropInstance");
        }
    }
}
