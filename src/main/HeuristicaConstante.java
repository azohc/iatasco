package main;

import aima.core.search.framework.evalfunc.HeuristicFunction;

public class HeuristicaConstante implements HeuristicFunction {

	@Override
	public double h(Object o) {
		EstadoAtasco estado = (EstadoAtasco) o;
		return 1;
	}
	
	//TODO crear segunda heuristica. esta es admisible tal y como esta
}
