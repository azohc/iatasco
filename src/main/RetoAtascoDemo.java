package main;

import java.util.Iterator;
import java.util.List;
import java.util.Properties;

import aima.core.agent.Action;
import aima.core.search.framework.SearchAgent;
import aima.core.search.framework.problem.Problem;
import aima.core.search.uninformed.BreadthFirstSearch;

public class RetoAtascoDemo {
	
	private static EstadoAtasco _estadoInicial;
	
	public static void main(String[] args) {

		Demo();
	}
	
	private static void Demo()
	{
		System.out.println("\nRETOATASAZODEMO-->");
		try{
			_estadoInicial = new EstadoAtasco();
			Problem p = new Problem(_estadoInicial, AtascoFunctionFactory.getActionsFunction(), AtascoFunctionFactory.getResultFunction()
					, new AtascoGoalTest(), new AtascoStepCostFunction());
			
			BreadthFirstSearch busqueda = new BreadthFirstSearch();
			
			SearchAgent agente = new SearchAgent(p, busqueda);
			
			printActions(agente.getActions());
			printInstrumentation(agente.getInstrumentation());
		}
		catch(Exception e){
			e.printStackTrace();
		}		
	}

	private static void printInstrumentation(Properties properties) {
		Iterator<Object> keys = properties.keySet().iterator();
		while (keys.hasNext()) {
			String key = (String) keys.next();
			String property = properties.getProperty(key);
			System.out.println(key + " : " + property);
		}

	}

	private static void printActions(List<Action> actions) {
		for (int i = 0; i < actions.size(); i++) {
			String action = actions.get(i).toString();
			System.out.println(action);
		}
	}
}
