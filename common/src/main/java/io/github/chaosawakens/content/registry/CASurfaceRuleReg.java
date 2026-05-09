package io.github.chaosawakens.content.registry;

import com.google.common.collect.ImmutableList;
import com.mememan.nexus.asm.annotations.RegistrarEntry;
import com.mememan.nexus.platform.NexusServices;
import com.mojang.serialization.Codec;
import io.github.chaosawakens.CAConstants;
import io.github.chaosawakens.content.worldgen.surface_rule.CASurfaceRules;
import it.unimi.dsi.fastutil.objects.ObjectArrayList;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.levelgen.SurfaceRules;
import net.minecraft.world.level.levelgen.material.MaterialRuleList;

import java.util.function.Supplier;

@RegistrarEntry
public class CASurfaceRuleReg {
    private static final ObjectArrayList<Supplier<MaterialRuleList>> BLOCKS = new ObjectArrayList<>();

    public static final Supplier<Codec<? extends SurfaceRules.ConditionSource>> DENSE_DIRT_RULE = registerConditionRule("dense_dirt_rule", CASurfaceRules.DenseDirtUnderSurface.CODEC::codec);

    private static Supplier<Codec<? extends SurfaceRules.ConditionSource>> registerConditionRule(ResourceLocation id, Supplier<Codec<? extends SurfaceRules.ConditionSource>> actualCodecSup) {
        Supplier<Codec<? extends SurfaceRules.ConditionSource>> conditionRuleSup = NexusServices.REGISTRAR.registerObject(id, actualCodecSup, BuiltInRegistries.MATERIAL_CONDITION);

        return conditionRuleSup;
    }

    private static Supplier<Codec<? extends SurfaceRules.ConditionSource>> registerConditionRule(String id, Supplier<Codec<? extends SurfaceRules.ConditionSource>> actualCodec) {
        return registerConditionRule(CAConstants.prefix(id), actualCodec);
    }

    public static ImmutableList<Supplier<MaterialRuleList>> getBlocks() {
        return ImmutableList.copyOf(BLOCKS);
    }
}