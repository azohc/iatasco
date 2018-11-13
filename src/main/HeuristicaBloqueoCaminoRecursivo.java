package main;

import aima.core.search.framework.evalfunc.HeuristicFunction;

public class HeuristicaBloqueoCaminoRecursivo implements HeuristicFunction {

	@Override
	public double h(Object o) {
		EstadoAtasco estado = (EstadoAtasco) o;
		return 0;
	}
	
	//TODO crear segunda heuristica. Si es algo distinto a lo propuesto por este nombre, refactor it

}
