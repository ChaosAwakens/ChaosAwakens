package io.github.chaosawakens.common.registry;

import com.google.common.base.Suppliers;
import com.google.common.collect.ImmutableList;
import io.github.chaosawakens.CAConstants;
import io.github.chaosawakens.api.entity.ClientDataEntry;
import io.github.chaosawakens.client.model.hostile.EntModel;
import io.github.chaosawakens.client.model.hostile.robo.RoboPounderModel;
import io.github.chaosawakens.client.model.passive.animal.land.AppleCowModel;
import io.github.chaosawakens.client.model.passive.animal.land.CarrotPigModel;
import io.github.chaosawakens.client.model.passive.animal.land.LettuceChickenModel;
import io.github.chaosawakens.client.model.passive.animal.land.StinkBugModel;
import io.github.chaosawakens.client.renderer.hostile.EntRenderer;
import io.github.chaosawakens.client.renderer.hostile.robo.RoboPounderRenderer;
import io.github.chaosawakens.client.renderer.passive.animal.land.AppleCowRenderer;
import io.github.chaosawakens.client.renderer.passive.animal.land.CarrotPigRenderer;
import io.github.chaosawakens.client.renderer.passive.animal.land.LettuceChickenRenderer;
import io.github.chaosawakens.client.renderer.passive.animal.land.StinkBugRenderer;
import it.unimi.dsi.fastutil.objects.ObjectArrayList;
import it.unimi.dsi.fastutil.objects.ObjectObjectImmutablePair;

import java.util.function.Supplier;

public class CAClientDataEntries {
    private static final ObjectArrayList<Supplier<ClientDataEntry>> CLIENT_DATA_ENTRIES = new ObjectArrayList<>();

    // Food Animal
    public static final Supplier<ClientDataEntry> APPLE_COW = registerClientDataEntry(Suppliers.ofInstance(new ClientDataEntry(CAConstants.prefix("apple_cow"), (ctx) -> () -> new AppleCowRenderer<>(ctx.get()), ObjectObjectImmutablePair.of(() -> AppleCowModel.BASE_LAYER, AppleCowModel::createBodyLayer))));
    public static final Supplier<ClientDataEntry> CARROT_PIG = registerClientDataEntry(Suppliers.ofInstance(new ClientDataEntry(CAConstants.prefix("carrot_pig"), (ctx) -> () -> new CarrotPigRenderer<>(ctx.get()), ObjectObjectImmutablePair.of(() -> CarrotPigModel.BASE_LAYER, CarrotPigModel::createBodyLayer))));

    public static final Supplier<ClientDataEntry> LETTUCE_CHICKEN = registerClientDataEntry(Suppliers.ofInstance(new ClientDataEntry(CAConstants.prefix("lettuce_chicken"), (ctx) -> () -> new LettuceChickenRenderer<>(ctx.get()), ObjectObjectImmutablePair.of(() -> LettuceChickenModel.BASE_LAYER, LettuceChickenModel::createBodyLayer))));

    // Bug
    public static final Supplier<ClientDataEntry> STINK_BUG = registerClientDataEntry(Suppliers.ofInstance(new ClientDataEntry(CAConstants.prefix("stink_bug"), (ctx) -> () -> new StinkBugRenderer<>(ctx.get()), ObjectObjectImmutablePair.of(() -> StinkBugModel.BASE_LAYER, StinkBugModel::createBodyLayer))));

    // Robo
    public static final Supplier<ClientDataEntry> ROBO_POUNDER = registerClientDataEntry(Suppliers.ofInstance(new ClientDataEntry(CAConstants.prefix("robo_pounder"), (ctx) -> () -> new RoboPounderRenderer<>(ctx.get()), ObjectObjectImmutablePair.of(() -> RoboPounderModel.BASE_LAYER, RoboPounderModel::createBodyLayer))));

    // Nature (Hostile)
    public static final Supplier<ClientDataEntry> ENT = registerClientDataEntry(Suppliers.ofInstance(new ClientDataEntry(CAConstants.prefix("ent"), (ctx) -> () -> new EntRenderer<>(ctx.get()), ObjectObjectImmutablePair.of(() -> EntModel.BASE_LAYER, EntModel::createBodyLayer))));

    private static Supplier<ClientDataEntry> registerClientDataEntry(Supplier<ClientDataEntry> clientDataEntrySup) {
        if (!CLIENT_DATA_ENTRIES.contains(clientDataEntrySup)) CLIENT_DATA_ENTRIES.add(clientDataEntrySup);
        return clientDataEntrySup;
    }

    public static ImmutableList<Supplier<ClientDataEntry>> getClientDataEntries() {
        return ImmutableList.copyOf(CLIENT_DATA_ENTRIES);
    }
}
