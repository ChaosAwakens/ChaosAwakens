package io.github.chaosawakens.api.animation;

import io.github.chaosawakens.api.animation.BaseActionPoint.TriAction;
import io.github.chaosawakens.common.util.ObjectUtil;

public class TriActionPoint<T, K, V> extends BaseActionPoint<TriAction<T, K, V>> {
	private TriAction<T, K, V> curAction;
	private T objArgType;
	private K objArgType1;
	private V objArgType2;

	public TriActionPoint(double actionTick) {
		super(actionTick);
	}
	
	public TriActionPoint<T, K, V> setArgs(T objArg, K objArg1, V objArg2) {
		this.objArgType = objArg;
		this.objArgType1 = objArg1;
		this.objArgType2 = objArg2;
		return this;
	}
	
	public TriActionPoint<T, K, V> setCurAction(TriAction<T, K, V> targetAction) {
		ObjectUtil.performNullityChecks(true, objArgType, objArgType1, objArgType2);
		this.curAction = targetAction;
		return this;
	}

	@Override
	public void executeActionPoint() {
		if (ObjectUtil.performNullityChecks(false, objArgType, objArgType1, objArgType2, curAction)) {
			curAction.executeAction(objArgType, objArgType1, objArgType2);
		}
	}

	@Override
	public TriAction<T, K, V> getAction() {
		return curAction;
	}
}
