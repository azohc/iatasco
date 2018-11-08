package main;

import aima.core.agent.Action;
import aima.core.search.framework.problem.StepCostFunction;

public class AtascoStepCostFunction implements StepCostFunction{

	@Override
	public double c(Object arg0, Action arg1, Object arg2) {
		return 1;
	}

}
