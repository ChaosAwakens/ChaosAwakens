package io.github.chaosawakens.api.block.standard;

import net.minecraft.data.models.blockstates.BlockStateGenerator;
import net.minecraft.data.models.blockstates.MultiPartGenerator;
import net.minecraft.data.models.blockstates.MultiVariantGenerator;
import net.minecraft.world.level.block.Block;
import org.jetbrains.annotations.Nullable;

import java.util.function.Supplier;

/**
 * A wrapper builder class primarily used to hold and group blockstate data for more convenient blockstate definitions.
 */
public class BlockStateDefinition {
    private final Supplier<Block> parentBlock;
    @Nullable
    private BlockStateGenerator blockStateSupplier;

    private BlockStateDefinition(Supplier<Block> parentBlock) {
        this.parentBlock = parentBlock;
    }

    /**
     * Creates a new {@link BlockStateDefinition}.
     *
     * @param parentBlock The parent {@link Supplier<Block>} targeted for blockstate datagen,
     *                   stored in the newly-initialized {@link BlockStateDefinition} instance.
     *
     * @return A new {@link BlockStateDefinition}.
     */
    public static BlockStateDefinition of(Supplier<Block> parentBlock) {
        return new BlockStateDefinition(parentBlock);
    }

    /**
     * Sets this BSD's {@link BlockStateGenerator}. Generally speaking, there are 2 known implementations of this interface
     * ({@link MultiPartGenerator} and {@link MultiVariantGenerator}), and they're likely the only implementations you'll ever
     * need. However, since you only need to pass their parent interface in, you're not conformed to the 2 aforementioned types when working with
     * BSDs.
     *
     * @param blockStateSupplier The {@link BlockStateGenerator} parsed and used for blockstate datagen.
     *
     * @return {@code this} (builder method).
     *
     * @see MultiPartGenerator
     * @see MultiVariantGenerator
     */
    public BlockStateDefinition withBlockStateSupplier(BlockStateGenerator blockStateSupplier) {
        this.blockStateSupplier = blockStateSupplier;
        return this;
    }

    /**
     * Gets the parent {@link Supplier<Block>} targeted and used for blockstate datagen.
     *
     * @return The parent {@link Supplier<Block>}.
     */
    public Supplier<Block> getParentBlock() {
        return parentBlock;
    }

    /**
     * Gets the {@link BlockStateGenerator} targeted and parsed/used for blockstate datagen.
     *
     * @return The {@link BlockStateGenerator}.
     *
     * @see #withBlockStateSupplier(BlockStateGenerator)
     */
    public BlockStateGenerator getBlockStateSupplier() {
        return blockStateSupplier;
    }
}
