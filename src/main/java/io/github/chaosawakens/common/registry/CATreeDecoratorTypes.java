package io.github.chaosawakens.common.registry;

import io.github.chaosawakens.ChaosAwakens;
import io.github.chaosawakens.common.worldgen.treedecorator.LeafCarpetTreeDecorator;
import io.github.chaosawakens.common.worldgen.treedecorator.VinesBelowLeavesTreeDecorator;
import net.minecraft.world.gen.treedecorator.TreeDecoratorType;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;

public class CATreeDecoratorTypes {
	public static final DeferredRegister<TreeDecoratorType<?>> TREE_DECORATOR_TYPES = DeferredRegister.create(ForgeRegistries.TREE_DECORATOR_TYPES, ChaosAwakens.MODID);
	
//	public static final RegistryObject<TreeDecoratorType<CrystalBranchDecorator>> CRYSTAL_BRANCH_DECORATOR = TREE_DECORATOR_TYPES.register("crystal_branch_decorator", () -> new TreeDecoratorType<CrystalBranchDecorator>(null));
	public static final RegistryObject<TreeDecoratorType<VinesBelowLeavesTreeDecorator>> VINES_BELOW_LEAVES_TREE_DECORATOR = TREE_DECORATOR_TYPES.register("vines_below_leaves_tree_decorator", () -> new TreeDecoratorType<>(VinesBelowLeavesTreeDecorator.CODEC));
	public static final RegistryObject<TreeDecoratorType<LeafCarpetTreeDecorator>> LEAF_CARPET_TREE_DECORATOR = TREE_DECORATOR_TYPES.register("leaf_carpet_tree_decorator", () -> new TreeDecoratorType<>(LeafCarpetTreeDecorator.CODEC));

}
