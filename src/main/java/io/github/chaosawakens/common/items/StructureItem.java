package io.github.chaosawakens.common.items;

import net.minecraft.item.Item;
import net.minecraft.item.ItemUseContext;
import net.minecraft.stats.Stats;
import net.minecraft.util.ActionResultType;
import net.minecraft.util.Mirror;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.Rotation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.gen.feature.template.PlacementSettings;
import net.minecraft.world.gen.feature.template.Template;
import net.minecraft.world.server.ServerWorld;

public class StructureItem extends Item {
    String structureName;

    public StructureItem(Properties builderIn, String structureName) {
        super(builderIn);
        this.structureName = structureName;
    }

    @Override
    public ActionResultType onItemUse(ItemUseContext context) {
        World world = context.getWorld();
        BlockPos pos = context.getPos();
        int x = pos.getX();
        int y = pos.getY();
        int z = pos.getZ();

        if (world instanceof ServerWorld) {
            Template template = ((ServerWorld) world).getStructureTemplateManager()
                    .getTemplateDefaulted(new ResourceLocation("chaosawakens", structureName));
            if (template != null) {
                template.func_237144_a_((ServerWorld) world, new BlockPos(x - template.getSize().getX()/2, y, z - template.getSize().getZ()/2),
                        new PlacementSettings().setRotation(Rotation.CLOCKWISE_180).setMirror(Mirror.NONE).setChunk(null).setIgnoreEntities(false),
                        (world).rand);
                context.getPlayer().addStat(Stats.ITEM_USED.get(this));
                context.getItem().shrink(1);
            }
        }

        return ActionResultType.SUCCESS;
    }
}