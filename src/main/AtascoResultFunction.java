package main;

import aima.core.agent.Action;
import aima.core.search.framework.problem.ResultFunction;

public class AtascoResultFunction implements ResultFunction {
	
	@Override
	public Object result(Object s, Action a) {
		EstadoAtasco estado = (EstadoAtasco) s;
		if (EstadoAtasco.palante.equals(a)) {
			EstadoAtasco nuevoEstado = new EstadoAtasco(estado);
			//nuevoEstado.moverPalante(c);
			return nuevoEstado;
		}
		else if (EstadoAtasco.patras.equals(a))
		{
			EstadoAtasco nuevoEstado = new EstadoAtasco(estado);
			//nuevoEstado.moverPalante(c);
			return nuevoEstado;
		}
		
		// The Action is not understood or is a NoOp
		// the result will be the current state.
		return s;
	}
}
		
		/*	Necesitamos saber que coches hay ( cuantos y con que char )
		 *  para poder tener 1 operador que mueva cada uno o moverlos sabiendo
		 *  los que hay en el tablero.
		 * 
		 * */
