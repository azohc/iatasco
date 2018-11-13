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
	
	
	
	private static class AtascoResultFunction implements ResultFunction {
		public Object result(Object o, Action a) {
			EstadoAtasco estado = (EstadoAtasco) o;
			
			char car =  a.toString().charAt(13);	// a.toString <= "Action[name==zPAL]", por ejemplo
			Action auxAction = new DynamicAction(a.toString().substring(14, a.toString().length() - 1));
									
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
			return o;
		}
			
	}
	

	private static class AtascoActionsFunction implements ActionsFunction {
		@Override
		public Set<Action> actions(Object o) {
			EstadoAtasco estado = (EstadoAtasco) o;
			
			Set<Action> actions = new LinkedHashSet<Action>();
			
			List<Action> l = estado.getActionList();
			
			for(Action a : l) {
				char car =  a.toString().charAt(13); // a.toString <= "Action[name==zPAL]", por ejemplo
				if(estado.canMoveCar(a, car))
					actions.add(a);				
			}
			
			return actions;
		}
	
		
	}
}