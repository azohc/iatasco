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
		public Object result(Object o, Action accionConCodCoche) {
			EstadoAtasco estado = (EstadoAtasco) o;
			
			char car =  accionConCodCoche.toString().charAt(13);	// a.toString <= "Action[name==zPAL]", por ejemplo
			Action accionSinCodCoche = new DynamicAction(accionConCodCoche.toString().substring(14, accionConCodCoche.toString().length() - 1));
									
			if (EstadoAtasco.palante.equals(accionSinCodCoche) && estado.canMoveCar(accionConCodCoche)) {
				EstadoAtasco nuevoEstado = new EstadoAtasco(estado);
				nuevoEstado.moverPalante(car);
				return nuevoEstado;
			}
			else if (EstadoAtasco.patras.equals(accionSinCodCoche) && estado.canMoveCar(accionConCodCoche)){
				EstadoAtasco nuevoEstado = new EstadoAtasco(estado);
				nuevoEstado.moverPatras(car);
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
			
			for(Action accionConCodCoche : estado.getActionList()) {
				if(estado.canMoveCar(accionConCodCoche))
					actions.add(accionConCodCoche);				
			}
			
			return actions;
		}
	
		
	}
}