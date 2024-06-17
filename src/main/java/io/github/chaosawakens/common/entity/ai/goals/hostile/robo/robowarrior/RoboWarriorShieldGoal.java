package io.github.chaosawakens.common.entity.ai.goals.hostile.robo.robowarrior;

import io.github.chaosawakens.api.animation.IAnimationBuilder;
import io.github.chaosawakens.common.entity.hostile.robo.RoboWarriorEntity;
import net.minecraft.entity.ai.goal.Goal;

import java.util.function.Supplier;

public class RoboWarriorShieldGoal extends Goal {
    protected final RoboWarriorEntity owner;
    protected final Supplier<IAnimationBuilder> shieldUpAnim;
    protected final Supplier<IAnimationBuilder> shieldAnim;
    protected final Supplier<IAnimationBuilder> shieldDownAnim;
    protected final Supplier<IAnimationBuilder> shieldDestroyedAnim;

    public RoboWarriorShieldGoal(RoboWarriorEntity owner, Supplier<IAnimationBuilder> shieldUpAnim, Supplier<IAnimationBuilder> shieldAnim, Supplier<IAnimationBuilder> shieldDownAnim, Supplier<IAnimationBuilder> shieldDestroyedAnim) {
        this.owner = owner;
        this.shieldUpAnim = shieldUpAnim;
        this.shieldAnim = shieldAnim;
        this.shieldDownAnim = shieldDownAnim;
        this.shieldDestroyedAnim = shieldDestroyedAnim;
    }

    @Override
    public boolean canUse() {
        return false;
    }
}
