package io.github.chaosawakens.api.animation;

import io.github.chaosawakens.api.animation.BaseActionPoint.BiAction;
import io.github.chaosawakens.common.util.ObjectUtil;

public class BiActionPoint<T, K> extends BaseActionPoint<BiAction<T, K>> {
	private BiAction<T, K> curAction;
	private T objArgType;
	private K objArgType1;

	public BiActionPoint(double actionTick) {
		super(actionTick);
	}
	
	public BiActionPoint<T, K> setArgs(T objArg, K objArg1) {
		this.objArgType = objArg;
		this.objArgType1 = objArg1;
		return this;
	}
	
	public BiActionPoint<T, K> setCurAction(BiAction<T, K> targetAction) {
		ObjectUtil.performNullityChecks(true, objArgType, objArgType1);
		this.curAction = targetAction;
		return this;
	}

	@Override
	public void executeActionPoint() {
		if (ObjectUtil.performNullityChecks(false, objArgType, objArgType1, curAction)) {
			curAction.executeAction(objArgType, objArgType1);
		}
	}

	@Override
	public BiAction<T, K> getAction() {
		return curAction;
	}
}
