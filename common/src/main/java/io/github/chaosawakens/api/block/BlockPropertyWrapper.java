package io.github.chaosawakens.api.block;

import it.unimi.dsi.fastutil.objects.ObjectArrayList;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.storage.loot.LootTable;

import java.util.List;

/**
 * A wrapper class used to store information used in datagen to simplify creating data entries for blocks.
 */
public class BlockPropertyWrapper {
    private final BPWBuilder builder;

    private BlockPropertyWrapper(BPWBuilder builder) {
        this.builder = builder;
    }

    /**
     * Constructs a builder chain in which certain datagen properties can be assigned and re-built with in this BlockPropertyWrapper instance.
     *
     * @return A new {@link BPWBuilder} instance (builder pattern).
     */
    public static BPWBuilder builder() {
        return new BPWBuilder();
    }

    /**
     * A builder class used to construct certain data for datagen.
     */
    public static class BPWBuilder {
        private String manuallyUnlocalizedBlockName = "";
        private List<String> definedSeparatorWords = ObjectArrayList.of();
        private LootTable.Builder blockLootTableBuilder;

        private BPWBuilder() {
        }

        /**
         * Assigns a custom translation key for datagen. By default, a basic regex algorithm is used to automatically de-localize
         * the block name into something more legible (I.E. The names you see in-game). This property is simply an override
         * mechanic which aims to give the end-developer more control over the resulting name instead of being forced to rely on
         * the aforementioned algorithm.
         * <p></p>
         * The algorithm in question, in a nutshell, works as follows (the code block below is purely demonstrative of the
         * de-localization process and has nothing to do with how the algorithm is actually written):
         * <pre>
         *     {@code
         *      public class AlgorithmExampleDescriptor {
         *
         *          public static void main(String[] args) {
         *              String unlocalizedName = "block.chaosawakens.block_of_ruby"; // The registry name/initial unlocalized name
         *
         *              // Steps
         *              AlgorithmLanguageProvider.validateNullity(unlocalizedName); // Checks whether the provided 'unlocalizedName' is empty/all whitespaces/you get the point
         *              AlgorithmLanguageProvider.validateRegex(unlocalizedName); // Checks whether the provided 'unlocalizedName' has the signature registry name separator character "."
         *              AlgorithmLanguageProvider.formatCaps(unlocalizedName); // Output: "Block.Chaosawakens.Block_Of_Ruby"
         *              AlgorithmLanguageProvider.formatSeparators(unlocalizedName); // Output: "Block.Chaosawakens.Block_of_Ruby" <-- Any defined "separator" Strings are lowercased, see #withCustomSeparatorWords(List)
         *              AlgorithmLanguageProvider.formatSpecialSeparators(unlocalizedName); // Output: "Block of Ruby" <-- All characters preceding the last "." are substringed/removed, and then any "_" characters are replaced with whitespaces
         *
         *              // End result
         *              System.out.println(unlocalizedName); // Output: "Block of Ruby"
         *          }
         *      }
         *     }
         * </pre>
         * <b>NOTE:</b> Block registry names ending with "_block" (e.g. "block.chaosawakens.royal_guardian_scale_block") have the "_block" part removed automatically during the translation process (the registry name stays the same,
         * of course). You may use this method to bypass that step if needed.
         *
         * @param manuallyUnlocalizedBlockName The name override used to de-localize the parent {@linkplain Block Block's} registry name.
         *
         * @return {@code this} (builder method).
         *
         * @see #withCustomSeparatorWords(List)
         */
        public BPWBuilder withCustomName(String manuallyUnlocalizedBlockName) {
            this.manuallyUnlocalizedBlockName = manuallyUnlocalizedBlockName;
            return this;
        }

        /**
         * Assigns a {@link List} of custom separator words which are lowercased during the algorithm's de-localization process. This is ignored if {@link #manuallyUnlocalizedBlockName} is defined.
         * The default entries for this are {"Of", "And"}. This {@link List} is appended to the default separator definitions rather than replacing them.
         *
         * @param definedSeparatorWords The {@link List} of custom separator words to lowercase while the algorithm is running.
         *
         * @return {@code this} (builder method).
         *
         * @see #withCustomName(String)
         */
        public BPWBuilder withCustomSeparatorWords(List<String> definedSeparatorWords) {
            this.definedSeparatorWords = definedSeparatorWords;
            return this;
        }

        /**
         * Assigns a given {@link LootTable.Builder} to this builder. Can be {@code null}.
         *
         * @param blockLootTableBuilder The {@link LootTable.Builder} used to build this BPWBuilder's parent block's loot table in datagen.
         *
         * @return {@code this} (builder method).
         */
        public BPWBuilder withLootTable(LootTable.Builder blockLootTableBuilder) {
            this.blockLootTableBuilder = blockLootTableBuilder;
            return this;
        }

        /**
         * Builds a new {@link BlockPropertyWrapper} using this builder's data.
         *
         * @return The newly data-populated {@link BlockPropertyWrapper}.
         */
        public BlockPropertyWrapper build() {
            return new BlockPropertyWrapper(this);
        }
    }
}
