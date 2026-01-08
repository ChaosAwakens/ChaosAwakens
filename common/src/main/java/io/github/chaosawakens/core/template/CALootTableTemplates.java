package io.github.chaosawakens.core.template;

import com.mememan.nexus.util.LootUtil;
import io.github.chaosawakens.content.block.vegetation.FruitableLeavesBlock;
import net.minecraft.advancements.critereon.StatePropertiesPredicate;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.storage.loot.LootPool;
import net.minecraft.world.level.storage.loot.LootTable;
import net.minecraft.world.level.storage.loot.entries.LootItem;
import net.minecraft.world.level.storage.loot.functions.SetItemCountFunction;
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
}
