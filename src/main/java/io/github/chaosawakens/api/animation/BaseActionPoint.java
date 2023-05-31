package io.github.chaosawakens.api.animation;

import io.github.chaosawakens.api.animation.BaseActionPoint.BaseAction;

public abstract class BaseActionPoint<A extends BaseAction> {
	private final double actionTick;
	/*public static final BiAction<LivingEntity, Float> HURT_TARGET = (target, damage) -> {
		if (target.getLastHurtByMob() != null) target.hurt(DamageSource.mobAttack(target.getLastHurtByMob()), damage);
	};*/

	public BaseActionPoint(double actionTick) {
		this.actionTick = actionTick;
	}

	public double getActionTick() {
		return actionTick;
	}

	public abstract void executeActionPoint();

	public abstract A getAction();

	protected interface BaseAction {
	}

	@FunctionalInterface
	public interface Action<T> extends BaseAction {
		void executeAction(T targetObj);
	}

	@FunctionalInterface
	public interface BiAction<T, K> extends BaseAction {
		void executeAction(T targetObj, K targetObj2);
	}

	@FunctionalInterface
	public interface TriAction<T, K, V> extends BaseAction {
		void executeAction(T targetObj, K targetObj2, V targetObj3);
	}

	@FunctionalInterface
	public interface QuadAction<T, K, V, S> extends BaseAction {
		void executeAction(T targetObj, K targetObj2, V targetObj3, S targetObj4);
	}
}
