package io.github.chaosawakens.content.registry;

import com.google.common.collect.ImmutableList;
import com.mememan.nexus.asm.annotations.RegistrarEntry;
import com.mememan.nexus.template.property_wrapper.BlockEntityTypePropertyWrapperTemplates;
import io.github.chaosawakens.CAConstants;
import io.github.chaosawakens.content.block_entity.defossilizer.CrystalDefossilizerBlockEntity;
import io.github.chaosawakens.content.block_entity.defossilizer.IronDefossilizerBlockEntity;
import io.github.chaosawakens.content.block_entity.robo.RoboCrateBlockEntity;
import it.unimi.dsi.fastutil.objects.ObjectArrayList;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityType;

import java.util.function.Supplier;

@RegistrarEntry
public final class CABlockEntityTypes {
    protected static final ObjectArrayList<Supplier<BlockEntityType<BlockEntity>>> BLOCK_ENTITY_TYPES = new ObjectArrayList<>();

    public static ImmutableList<Supplier<BlockEntityType<BlockEntity>>> getBlockEntityTypes() {
        return ImmutableList.copyOf(BLOCK_ENTITY_TYPES);
    }    public static final Supplier<BlockEntityType<IronDefossilizerBlockEntity>> IRON_DEFOSSILIZER = BlockEntityTypePropertyWrapperTemplates.registerBlockEntityType(CAConstants.prefix("iron_defossilizer"), () -> BlockEntityType.Builder
            .of(IronDefossilizerBlockEntity::new, CABlocks.IRON_DEFOSSILIZER.get())
            .build(null), BLOCK_ENTITY_TYPES);
    public static final Supplier<BlockEntityType<CrystalDefossilizerBlockEntity>> CRYSTAL_DEFOSSILIZER = BlockEntityTypePropertyWrapperTemplates.registerBlockEntityType(CAConstants.prefix("crystal_defossilizer"), () -> BlockEntityType.Builder
            .of(CrystalDefossilizerBlockEntity::new, CABlocks.CRYSTAL_DEFOSSILIZER.get())
            .build(null), BLOCK_ENTITY_TYPES);

    public static final Supplier<BlockEntityType<RoboCrateBlockEntity>> ROBO_CRATE = BlockEntityTypePropertyWrapperTemplates.registerBlockEntityType(CAConstants.prefix("robo_crate"), () -> BlockEntityType.Builder
            .of(RoboCrateBlockEntity::new, CABlocks.ROBO_CRATE.get())
            .build(null), BLOCK_ENTITY_TYPES);


}
