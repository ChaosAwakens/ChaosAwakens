package io.github.chaosawakens.api.animation;

import io.github.chaosawakens.api.animation.BaseActionPoint.QuadAction;
import io.github.chaosawakens.common.util.ObjectUtil;

public class QuadActionPoint<T, K, V, S> extends BaseActionPoint<QuadAction<T, K, V, S>> {
	private QuadAction<T, K, V, S> curAction;
	private T objArgType;
	private K objArgType1;
	private V objArgType2;
	private S objArgType3;

	public QuadActionPoint(double actionTick) {
		super(actionTick);
	}
	
	public QuadActionPoint<T, K, V, S> setArgs(T objArg, K objArg1, V objArg2, S objArg3) {
		this.objArgType = objArg;
		this.objArgType1 = objArg1;
		this.objArgType2 = objArg2;
		this.objArgType3 = objArg3;
		return this;
	}
	
	public QuadActionPoint<T, K, V, S> setCurAction(QuadAction<T, K, V, S> targetAction) {
		ObjectUtil.performNullityChecks(true, objArgType, objArgType1, objArgType2, objArgType3);
		this.curAction = targetAction;
		return this;
	}

	@Override
	public void executeActionPoint() {
		if (ObjectUtil.performNullityChecks(false, objArgType, objArgType1, objArgType2, objArgType3, curAction)) {
			curAction.executeAction(objArgType, objArgType1, objArgType2, objArgType3);
		}
	}

	@Override
	public QuadAction<T, K, V, S> getAction() {
		return curAction;
	}
}
