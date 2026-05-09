package io.github.chaosawakens.content.item.misc;

import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.shapes.CollisionContext;

import java.util.Arrays;
import java.util.function.Supplier;

public class BlackListItemNameBlockItem extends DeferredItemNameBlockItem {
    protected final Supplier<Supplier<? extends Block>>[] blacklist;

    public BlackListItemNameBlockItem(Supplier<Supplier<? extends Block>> blockSup, Properties properties, Supplier<Supplier<? extends Block>>[] blacklist) {
        super(blockSup, properties);

        this.blacklist = blacklist;
    }

    @Override
    protected boolean canPlace(BlockPlaceContext context, BlockState state) {
        Player player = context.getPlayer();
        CollisionContext collisionContext = player == null ? CollisionContext.empty() : CollisionContext.of(player);
        BlockState targetState = context.getLevel().getBlockState(context.getClickedPos().below());

        return (!mustSurvive() || state.canSurvive(context.getLevel(), context.getClickedPos()))
                && context.getLevel().isUnobstructed(state, context.getClickedPos(), collisionContext)
                && Arrays.stream(blacklist).noneMatch(supplier -> targetState.is(supplier.get().get()));
    }

    public Supplier<Supplier<? extends Block>>[] getBlacklist() {
        return blacklist;
    }
}
