package io.github.chaosawakens.content.entity.boss;

import com.google.common.base.Suppliers;
import io.github.chaosawakens.CAConstants;
import io.github.chaosawakens.api.animation_resolver_system.pipeline.parsing.base.animation.AnimationInfo;
import io.github.chaosawakens.api.animation_resolver_system.pipeline.parsing.base.model.ModelInfo;
import io.github.chaosawakens.content.entity.base.animatable.mappable.MappableBoss;
import io.github.chaosawakens.content.registry.CAResourceReloadListeners;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.monster.Monster;
import net.minecraft.world.level.Level;

import java.util.Map;
import java.util.Objects;
import java.util.function.Supplier;

public class HerculesBeetle extends MappableBoss {
    private static final Supplier<ModelInfo> MODEL_INFO = Suppliers.memoize(() -> CAResourceReloadListeners.GECKOLIB_MODEL_INFO.getMappedObjectData().entrySet().stream()
            .filter(curEntry -> Objects.equals(curEntry.getKey().getNamespace(), CAConstants.MOD_ID) && curEntry.getKey().getPath().endsWith("hercules_beetle.geo"))
            .map(Map.Entry::getValue)
            .findFirst()
            .orElseThrow());

    public HerculesBeetle(EntityType<? extends Monster> entityType, Level level) {
        super(entityType, level);
    }

    public static AttributeSupplier.Builder createAttributes() {
        return Monster.createMonsterAttributes()
                .add(Attributes.MAX_HEALTH, 250)
                .add(Attributes.ARMOR, 20)
                .add(Attributes.MOVEMENT_SPEED, 0.35D)
                .add(Attributes.FLYING_SPEED, 0.42D)
                .add(Attributes.KNOCKBACK_RESISTANCE, 0.8D)
                .add(Attributes.ATTACK_SPEED, 10)
                .add(Attributes.ATTACK_DAMAGE, 25)
                .add(Attributes.ATTACK_KNOCKBACK, 2D)
                .add(Attributes.FOLLOW_RANGE, 40);
    }

    @Override
    public boolean isAttackingStatically() {
        return false;
    }

    @Override
    public void tick() {
        super.tick();

        skeleton.setRotation(0, getYHeadRot(), 0);
        skeleton.refreshStructure();
    }

    @Override
    public AnimationInfo getAnimationInfo() {
        return null;
    }

    @Override
    public ModelInfo getModelInfo() {
        return MODEL_INFO.get();
    }
}
