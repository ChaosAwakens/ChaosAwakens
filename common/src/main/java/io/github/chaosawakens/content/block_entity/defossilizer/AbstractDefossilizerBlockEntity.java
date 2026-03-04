package io.github.chaosawakens.content.block_entity.defossilizer;

import io.github.chaosawakens.content.block.mining.fossil.FossilBlockInstance;
import io.github.chaosawakens.content.data.recipe.defossilizing.AbstractDefossilizingRecipe;
import io.github.chaosawakens.content.item.utility.PowerChipItem;
import it.unimi.dsi.fastutil.objects.Object2IntOpenHashMap;
import it.unimi.dsi.fastutil.objects.ObjectArrayList;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.NonNullList;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.util.Mth;
import net.minecraft.world.Container;
import net.minecraft.world.ContainerHelper;
import net.minecraft.world.WorldlyContainer;
import net.minecraft.world.entity.ExperienceOrb;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.player.StackedContents;
import net.minecraft.world.inventory.ContainerData;
import net.minecraft.world.inventory.RecipeHolder;
import net.minecraft.world.inventory.StackedContentsCompatible;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.BucketItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.crafting.Recipe;
import net.minecraft.world.item.crafting.RecipeManager;
import net.minecraft.world.item.crafting.RecipeType;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.entity.BaseContainerBlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.Vec3;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public abstract class AbstractDefossilizerBlockEntity extends BaseContainerBlockEntity implements WorldlyContainer, RecipeHolder, StackedContentsCompatible {
    public static final int FOSSIL_INPUT_SLOT_INDEX = 0;
    public static final int BUCKET_INPUT_SLOT_INDEX = 1;
    public static final int POWER_CHIP_INPUT_SLOT_INDEX = 2;
    public static final int DEFOSSILIZED_OUTPUT_SLOT_INDEX = 3;
    public static final int TOTAL_DEFOSSILIZER_SLOTS = 4;
    public static final int[] OUTPUT_SLOTS = new int[]{BUCKET_INPUT_SLOT_INDEX, POWER_CHIP_INPUT_SLOT_INDEX, DEFOSSILIZED_OUTPUT_SLOT_INDEX}; // Down
    public static final int[] SIDE_SLOTS = new int[]{BUCKET_INPUT_SLOT_INDEX, POWER_CHIP_INPUT_SLOT_INDEX}; // North, South, East, West
    public static final int[] UPWARD_INPUT_SLOTS = new int[]{FOSSIL_INPUT_SLOT_INDEX}; // Up
    public static final int[][] SLOT_GROUPS_BY_IDX = new int[][]{UPWARD_INPUT_SLOTS, SIDE_SLOTS};
    public static final Direction[][] DIRECTIONS_BY_SLOT_IDX = new Direction[][]{
            new Direction[]{Direction.UP},
            new Direction[]{Direction.NORTH, Direction.SOUTH, Direction.EAST, Direction.WEST}
    };
    public static final int INV_SLOT_START = 4;
    public static final int INV_SLOT_END = 31;
    public static final int USE_ROW_SLOT_START = 31;
    public static final int USE_ROW_SLOT_END = 40;
    public static final int DEFOSSILIZATION_PROGRESS_DATA_INDEX = 0;
    public static final int DEFOSSILIZATION_TIME_DATA_INDEX = 1;
    public static final int DEFAULT_DEFOSSILIZATION_TIME = 200;
    protected final RecipeType<? extends AbstractDefossilizingRecipe> recipeType;
    protected final RecipeManager.CachedCheck<Container, ? extends AbstractDefossilizingRecipe> quickCheck;
    protected final Object2IntOpenHashMap<ResourceLocation> usedRecipeCache = new Object2IntOpenHashMap<>();
    protected final ContainerData dataAccess;
    protected NonNullList<ItemStack> items = NonNullList.withSize(4, ItemStack.EMPTY);
    protected int defossilizationProgress;
    protected int totalDefossilizationTime;

    public AbstractDefossilizerBlockEntity(BlockEntityType<?> type, BlockPos pos, BlockState blockState, RecipeType<? extends AbstractDefossilizingRecipe> recipeType) {
        super(type, pos, blockState);

        this.quickCheck = RecipeManager.createCheck(recipeType);
        this.recipeType = recipeType;

        this.dataAccess = new ContainerData() {

            @Override
            public int get(int dataIdx) {
                switch (dataIdx) {
                    case DEFOSSILIZATION_PROGRESS_DATA_INDEX -> {
                        return defossilizationProgress;
                    }
                    case DEFOSSILIZATION_TIME_DATA_INDEX -> {
                        return totalDefossilizationTime;
                    }
                    default -> {
                        return 0;
                    }
                }
            }

            @Override
            public void set(int dataIdx, int value) {
                switch (dataIdx) {
                    case DEFOSSILIZATION_PROGRESS_DATA_INDEX -> defossilizationProgress = value;
                    case DEFOSSILIZATION_TIME_DATA_INDEX -> totalDefossilizationTime = value;
                }
            }

            @Override
            public int getCount() {
                return 2;
            }
        };
    }

    @NotNull
    public abstract AbstractDefossilizingRecipe.DefossilizationCategory getDefossilizationCategory();

    @Override
    public int getContainerSize() {
        return TOTAL_DEFOSSILIZER_SLOTS;
    }

    @Override
    public boolean isEmpty() {
        return items.isEmpty() || items.stream().allMatch(ItemStack::isEmpty);
    }

    @Override
    public void load(CompoundTag tag) {
        super.load(tag);

        this.defossilizationProgress = tag.getInt("DefossilizationProgress");
        this.totalDefossilizationTime = tag.getInt("TotalDefossilizationTime");

        ContainerHelper.loadAllItems(tag, items);

        CompoundTag cachedRecipeTag = tag.getCompound("UsedRecipeCache");

        cachedRecipeTag.getAllKeys().forEach(recipeId -> usedRecipeCache.put(new ResourceLocation(recipeId), cachedRecipeTag.getInt(recipeId)));
    }

    @Override
    protected void saveAdditional(CompoundTag tag) {
        super.saveAdditional(tag);

        tag.putInt("DefossilizationProgress", defossilizationProgress);
        tag.putInt("TotalDefossilizationTime", totalDefossilizationTime);

        ContainerHelper.saveAllItems(tag, items);

        CompoundTag cachedRecipeTag = new CompoundTag();

        usedRecipeCache.forEach((recipeId, idx) -> cachedRecipeTag.putInt(recipeId.toString(), idx));

        tag.put("UsedRecipeCache", cachedRecipeTag);
    }

    @Override
    public @NotNull ItemStack getItem(int idx) {
        return items.get(idx);
    }

    @Override
    public @NotNull ItemStack removeItem(int idx, int count) {
        return ContainerHelper.removeItem(items, idx, count);
    }

    @Override
    public @NotNull ItemStack removeItemNoUpdate(int idx) {
        return ContainerHelper.takeItem(items, idx);
    }

    @Override
    public void setItem(int idx, @NotNull ItemStack setStack) {
        ItemStack existingStack = this.items.get(idx);
        boolean wasAlreadyInSlot = !setStack.isEmpty() && ItemStack.isSameItemSameTags(existingStack, setStack);

        this.items.set(idx, setStack);

        if (setStack.getCount() > getMaxStackSize()) setStack.setCount(getMaxStackSize());

        if (idx == FOSSIL_INPUT_SLOT_INDEX && !wasAlreadyInSlot) {
            this.defossilizationProgress = 0;
            this.totalDefossilizationTime = getTotalDefossilizationTime(level, this);

            setChanged();
        }
    }

    @Override
    public boolean stillValid(@NotNull Player player) {
        return Container.stillValidBlockEntity(this, player);
    }

    @Override
    public void clearContent() {
        items.clear();
    }

    @Override
    public int @NotNull [] getSlotsForFace(Direction inputDir) {
        if (inputDir == Direction.DOWN) return OUTPUT_SLOTS;
        else return inputDir == Direction.UP ? UPWARD_INPUT_SLOTS : SIDE_SLOTS;
    }

    @Override
    public boolean canPlaceItem(int slotIdx, ItemStack inputStack) {
        if (slotIdx == DEFOSSILIZED_OUTPUT_SLOT_INDEX) return false;
        else if (slotIdx == FOSSIL_INPUT_SLOT_INDEX) return isFossil(inputStack);
        else if (slotIdx == BUCKET_INPUT_SLOT_INDEX) return isBucketComponent(inputStack) && items.get(BUCKET_INPUT_SLOT_INDEX).isEmpty();
        else if (slotIdx == POWER_CHIP_INPUT_SLOT_INDEX) return isPowerChipComponent(inputStack);
        else return false;
    }

    @Override
    public boolean canPlaceItemThroughFace(int slotIdx, ItemStack inputStack, @Nullable Direction inputDir) {
        if (inputDir != null) {
            for (int i = 0; i < SLOT_GROUPS_BY_IDX.length; i++) {
                int[] slotGroup = SLOT_GROUPS_BY_IDX[i];
                Direction[] directions = DIRECTIONS_BY_SLOT_IDX[i];
                boolean anyMatchDir = false;

                for (Direction direction : directions) {
                    if (direction == inputDir) {
                        anyMatchDir = true;
                        break;
                    }
                }

                for (int slot : slotGroup) {
                    if (slot == slotIdx && anyMatchDir && canPlaceItem(slot, inputStack)) return true;
                }
            }
        }

        return false;
    }

    @Override
    public boolean canTakeItemThroughFace(int slotIdx, ItemStack targetStack, Direction interactionDir) {
        for (int i = 0; i < SLOT_GROUPS_BY_IDX.length; i++) {
            int[] slotGroup = SLOT_GROUPS_BY_IDX[i];
            Direction[] directions = DIRECTIONS_BY_SLOT_IDX[i];
            boolean anyMatchDir = false;

            for (Direction direction : directions) {
                if (direction == interactionDir) {
                    anyMatchDir = true;
                    break;
                }
            }

            for (int slot : slotGroup) {
                if (slot == slotIdx && anyMatchDir) return true;
            }
        }

        return interactionDir == Direction.DOWN;
    }

    @Override
    public void setRecipeUsed(@Nullable Recipe<?> recipe) {
        if (recipe != null) usedRecipeCache.addTo(recipe.getId(), 1);
    }

    @Override
    public @Nullable Recipe<?> getRecipeUsed() {
        return null;
    }

    @Override
    public void fillStackedContents(StackedContents stackedContents) {
        for (ItemStack existingStack : items) {
            stackedContents.accountStack(existingStack);
        }
    }

    public NonNullList<ItemStack> getItems() {
        return items;
    }

    public int getDefossilizationProgress() {
        return defossilizationProgress;
    }

    public int getTotalDefossilizationTime() {
        return totalDefossilizationTime;
    }

    public boolean isDefossilizing() {
        return totalDefossilizationTime > 0;
    }

    public void awardUsedRecipesAndPopExperience(ServerPlayer player) {
        List<Recipe<?>> recipesToAward = getRecipesToAwardAndPopExperience(player.serverLevel(), player.position());

        player.awardRecipes(recipesToAward);

        for (Recipe<?> recipe : recipesToAward) {
            if (recipe != null) {
                player.triggerRecipeCrafted(recipe, items);
            }
        }

        usedRecipeCache.clear();
    }

    public List<Recipe<?>> getRecipesToAwardAndPopExperience(ServerLevel level, Vec3 popVec) {
        ObjectArrayList<Recipe<?>> recipesToAward = new ObjectArrayList<>();

        usedRecipeCache.forEach((recipeId, recipeIdx) -> {
            level.getRecipeManager().byKey(recipeId).ifPresent((recipe) -> {
                recipesToAward.add(recipe);
                createExperience(level, popVec, recipeIdx, ((AbstractDefossilizingRecipe) recipe).getExperience());
            });
        });

        return recipesToAward;
    }

    public static void serverTick(Level curLevel, BlockPos targetPos, BlockState targetState, AbstractDefossilizerBlockEntity targetDefossilizerBlockEntity) {
        boolean markChanged = false;
        boolean wasDefossilizing = targetDefossilizerBlockEntity.isDefossilizing();

        ItemStack fossilStack = targetDefossilizerBlockEntity.getItem(FOSSIL_INPUT_SLOT_INDEX);
        ItemStack bucketStack = targetDefossilizerBlockEntity.getItem(BUCKET_INPUT_SLOT_INDEX);
        ItemStack powerChipStack = targetDefossilizerBlockEntity.getItem(POWER_CHIP_INPUT_SLOT_INDEX);

        boolean hasIngredients = !fossilStack.isEmpty();
        boolean hasBucket = !bucketStack.isEmpty();
        boolean hasPowerChip = !powerChipStack.isEmpty();
        boolean hasFuel = hasBucket && hasPowerChip;
        boolean shouldProgressDefossilization = wasDefossilizing || hasFuel;

        if (shouldProgressDefossilization || hasIngredients) {
            Recipe<?> curRecipe = null;

            if (hasIngredients) curRecipe = targetDefossilizerBlockEntity.quickCheck.getRecipeFor(targetDefossilizerBlockEntity, curLevel).orElse(null);

            int maxStackSize = targetDefossilizerBlockEntity.getMaxStackSize();

            if (!targetDefossilizerBlockEntity.isDefossilizing() && canDefossilize(curRecipe, targetDefossilizerBlockEntity, targetDefossilizerBlockEntity.getItem(DEFOSSILIZED_OUTPUT_SLOT_INDEX), maxStackSize)) {
                targetDefossilizerBlockEntity.totalDefossilizationTime = getTotalDefossilizationTime(curLevel, targetDefossilizerBlockEntity);
                targetDefossilizerBlockEntity.defossilizationProgress = 0;

                if (targetDefossilizerBlockEntity.isDefossilizing()) markChanged = true;
            }

            if (targetDefossilizerBlockEntity.isDefossilizing() && canDefossilize(curRecipe, targetDefossilizerBlockEntity, targetDefossilizerBlockEntity.getItem(DEFOSSILIZED_OUTPUT_SLOT_INDEX), maxStackSize)) {
                targetDefossilizerBlockEntity.defossilizationProgress++;

                if (targetDefossilizerBlockEntity.defossilizationProgress >= targetDefossilizerBlockEntity.totalDefossilizationTime) {
                    targetDefossilizerBlockEntity.defossilizationProgress = 0;
                    targetDefossilizerBlockEntity.totalDefossilizationTime = getTotalDefossilizationTime(curLevel, targetDefossilizerBlockEntity);

                    if (defossilize(curRecipe, targetDefossilizerBlockEntity, targetDefossilizerBlockEntity.getItem(DEFOSSILIZED_OUTPUT_SLOT_INDEX), maxStackSize)) {
                        targetDefossilizerBlockEntity.setRecipeUsed(curRecipe);
                    }

                    markChanged = true;
                }
            } else {
                targetDefossilizerBlockEntity.defossilizationProgress = 0;
                targetDefossilizerBlockEntity.totalDefossilizationTime = 0;
            }
        } else {
            if (!targetDefossilizerBlockEntity.isDefossilizing() && targetDefossilizerBlockEntity.defossilizationProgress > 0) {
                targetDefossilizerBlockEntity.defossilizationProgress = Mth.clamp(targetDefossilizerBlockEntity.defossilizationProgress - 2, 0, targetDefossilizerBlockEntity.totalDefossilizationTime);
            }

            targetDefossilizerBlockEntity.totalDefossilizationTime = 0;
        }

        if (wasDefossilizing != targetDefossilizerBlockEntity.isDefossilizing()) {
            markChanged = true;
            curLevel.setBlock(targetPos, targetState, Block.UPDATE_ALL);
        }

        if (markChanged) setChanged(curLevel, targetPos, targetState);
    }

    public static void createExperience(ServerLevel level, Vec3 popVec, int recipeIdx, float xpAmount) {
        int roundedXpAmount = Mth.floor(recipeIdx * xpAmount);
        double incrProbability = Mth.frac(recipeIdx * xpAmount);

        if (incrProbability != 0.0F && Math.random() < incrProbability) roundedXpAmount++;

        ExperienceOrb.award(level, popVec, roundedXpAmount);
    }

    public static int getTotalDefossilizationTime(Level level, AbstractDefossilizerBlockEntity blockEntity) {
        return blockEntity.quickCheck.getRecipeFor(blockEntity, level).map(AbstractDefossilizingRecipe::getDefossilizationTime).orElse(0);
    }

    public static boolean isFossil(ItemStack targetStack) {
        return targetStack.getItem() instanceof BlockItem blockItem && blockItem.getBlock() instanceof FossilBlockInstance;
    }

    public static boolean isPowerChipComponent(ItemStack targetStack) {
        return targetStack.getItem() instanceof PowerChipItem;
    }

    public static boolean isBucketComponent(ItemStack targetStack) {
        return targetStack.getItem() instanceof BucketItem;
    }

    public static boolean canDefossilize(Recipe<?> targetRecipe, AbstractDefossilizerBlockEntity targetDefossilizerBlockEntity, ItemStack defossilizedOutputStack, int maxStackSize) {
        ItemStack outputStack = targetRecipe == null ? ItemStack.EMPTY : targetRecipe.getResultItem(targetDefossilizerBlockEntity.level.registryAccess());
        return targetRecipe instanceof AbstractDefossilizingRecipe defossilizingRecipe
                && defossilizingRecipe.getDefossilizationCategory() == targetDefossilizerBlockEntity.getDefossilizationCategory()
                && (!targetDefossilizerBlockEntity.getItem(FOSSIL_INPUT_SLOT_INDEX).isEmpty())
                && (defossilizingRecipe.getBucketIngredient().test(targetDefossilizerBlockEntity.getItem(BUCKET_INPUT_SLOT_INDEX)))
                && (defossilizingRecipe.getPowerChipIngredient().test(targetDefossilizerBlockEntity.getItem(POWER_CHIP_INPUT_SLOT_INDEX)))
                && (defossilizedOutputStack.isEmpty() ||
                (ItemStack.isSameItemSameTags(outputStack, defossilizedOutputStack)
                        && outputStack.getCount() + defossilizedOutputStack.getCount() <= maxStackSize));
    }

    public static boolean defossilize(Recipe<?> targetRecipe, AbstractDefossilizerBlockEntity targetDefossilizerBlockEntity, ItemStack defossilizedOutputStack, int maxStackSize) {
        if (targetRecipe != null && canDefossilize(targetRecipe, targetDefossilizerBlockEntity, defossilizedOutputStack, maxStackSize)) {
            ItemStack outputStack = targetRecipe.getResultItem(targetDefossilizerBlockEntity.level.registryAccess());
            ItemStack existingOutputStack = targetDefossilizerBlockEntity.getItem(AbstractDefossilizerBlockEntity.DEFOSSILIZED_OUTPUT_SLOT_INDEX);

            if (existingOutputStack.isEmpty()) {
                targetDefossilizerBlockEntity.setItem(AbstractDefossilizerBlockEntity.DEFOSSILIZED_OUTPUT_SLOT_INDEX, outputStack.copy());
            } else if (ItemStack.isSameItemSameTags(existingOutputStack, outputStack)) {
                existingOutputStack.grow(outputStack.getCount());
            }

            ItemStack fossilStack = targetDefossilizerBlockEntity.getItem(AbstractDefossilizerBlockEntity.FOSSIL_INPUT_SLOT_INDEX);
            ItemStack bucketStack = targetDefossilizerBlockEntity.getItem(AbstractDefossilizerBlockEntity.BUCKET_INPUT_SLOT_INDEX);
            ItemStack powerChipStack = targetDefossilizerBlockEntity.getItem(AbstractDefossilizerBlockEntity.POWER_CHIP_INPUT_SLOT_INDEX);

            fossilStack.shrink(1);
            powerChipStack.shrink(1);

            if (bucketStack.getItem() instanceof BucketItem bucketItem && !bucketItem.content.defaultFluidState().isEmpty()) {
                targetDefossilizerBlockEntity.setItem(AbstractDefossilizerBlockEntity.BUCKET_INPUT_SLOT_INDEX, Items.BUCKET.getDefaultInstance());
            }

            return true;
        } else return false;
    }
}
