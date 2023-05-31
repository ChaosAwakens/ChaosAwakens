package io.github.chaosawakens.api.animation;

import io.github.chaosawakens.api.animation.BaseActionPoint.Action;
import io.github.chaosawakens.common.util.ObjectUtil;

public class ActionPoint<T> extends BaseActionPoint<Action<T>> {
	private Action<T> curAction;
	private T objArgType;

	public ActionPoint(double actionTick) {
		super(actionTick);
	}
	
	public ActionPoint<T> setArgs(T objArg) {
		this.objArgType = objArg;
		return this;
	}
	
	public ActionPoint<T> setCurAction(Action<T> targetAction) {
		if (objArgType == null) throw new NullPointerException("Cannot set action for action point because object arg type is null!");
		this.curAction = targetAction;
		return this;
	}

	@Override
	public void executeActionPoint() {
		if (ObjectUtil.performNullityChecks(false, objArgType, curAction)) {
			curAction.executeAction(objArgType);
		}
	}

	@Override
	public Action<T> getAction() {
		return curAction;
	}
}