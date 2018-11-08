package main;

import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

import aima.core.agent.Action;
import aima.core.agent.impl.DynamicAction;
import aima.core.search.framework.problem.ActionsFunction;
import aima.core.search.framework.problem.ResultFunction;

public class AtascoFunctionFactory {
	
	private static ResultFunction _resultFunction = null;
	private static ActionsFunction _actionsFunction = null;
	
	
	public static ActionsFunction getActionsFunction() {
		if (null == _actionsFunction) {
			_actionsFunction = new AtascoActionsFunction();
		}
		return _actionsFunction;
	}

	public static ResultFunction getResultFunction() {
		if (null == _resultFunction) {
			_resultFunction = new AtascoResultFunction();
		}
		return _resultFunction;
	}
	
	
	
	private static class AtascoResultFunction implements ResultFunction{
		public Object result(Object s, Action a) {
			EstadoAtasco estado = (EstadoAtasco) s;
			
			char car =  a.toString().charAt(0);
			Action auxAction = new DynamicAction(a.toString().substring(1));
						
//			for
//				if
//					action.equals(a) && estacanomovecar(action, car)
//						nuevoestado = estadoatasco estado
//						nuevoestado.segunlaaccionmover(car)
//						return nuevoestado
		
			//teniendo en 'a' la acción (codificada con el caracter correspondiente
			//al coche, parseando el char para obtener el coche y creando otra una
			//accion auxiliar sin el char, su valor será "PAL" o "PAT", equivalente
			//a una de las funciones ya definidas, sabiendo cual es el coche a mover
			
			if (EstadoAtasco.palante.equals(auxAction)
					&& estado.canMoveCar(EstadoAtasco.palante, car)) {
				EstadoAtasco nuevoEstado = new EstadoAtasco(estado);
				nuevoEstado.moverPalante(car);
				return nuevoEstado;
			}
			else if (EstadoAtasco.patras.equals(auxAction)
					&& estado.canMoveCar(EstadoAtasco.palante, car)){
				EstadoAtasco nuevoEstado = new EstadoAtasco(estado);
				nuevoEstado.moverPalante(car);
				return nuevoEstado;
			}
			
			// The Action is not understood or is a NoOp
			// the result will be the current state.
			return s;
		}
			
	}
	

	private static class AtascoActionsFunction implements ActionsFunction {
		@Override
		public Set<Action> actions(Object e) {
			EstadoAtasco estado = (EstadoAtasco) e;
			
			Set<Action> actions = new LinkedHashSet<Action>();
			
			List<Action> l = estado.getActionList();
			
			for(Action a : l) {
				char car =  a.toString().charAt(0);
				if(estado.canMoveCar(a, car))
					actions.add(a);				
			}
			
			return actions;
		}
	
		
	}
}