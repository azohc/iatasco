package main;

import java.util.Iterator;
import java.util.List;
import java.util.Properties;

import aima.core.agent.Action;
import aima.core.search.framework.SearchAgent;
import aima.core.search.framework.SearchForActions;
import aima.core.search.framework.problem.Problem;
import aima.core.search.framework.qsearch.GraphSearch;
import aima.core.search.informed.AStarSearch;
import aima.core.search.informed.GreedyBestFirstSearch;
import aima.core.search.uninformed.BreadthFirstSearch;

public class RetoAtascoDemo {
	
	private static EstadoAtasco _estadoInicial;
	
	public static void main(String[] args) {
		System.out.println("Busquedas informadas: Heuristica bloqueo en camino recto" + System.lineSeparator());
//		retoAtascoAStarCaminoRectoDemo(); 
//		retoAtascoGBFCaminoRectoDemo();		
//		
//		System.out.println(System.lineSeparator());
//		System.out.println("Busquedas informadas: Heuristica bloqueo en camino recursivo" + System.lineSeparator());
//		retoAtascoAStarCaminoRecursivoDemo();
//		retoAtascoGBFCaminoRecursivoDemo();
//		
//		System.out.println(System.lineSeparator());
//		System.out.println("Busquedas no informadas:" + System.lineSeparator());
		retoAtascoDemo();
		//TODO meter busquedas no informadas
	}
	

	private static void retoAtascoDemo() 
	{
		_estadoInicial = new EstadoAtasco();
		System.out.println("\nRetoAtascoDemo-->");
		try{
			Problem problema = new Problem(_estadoInicial, 
					AtascoFunctionFactory.getActionsFunction(), 
					AtascoFunctionFactory.getResultFunction(), 
					new AtascoGoalTest(), new AtascoStepCostFunction());
			
			BreadthFirstSearch busqueda = new BreadthFirstSearch();
			SearchAgent agent = new SearchAgent(problema, busqueda);
			
			printActions(agent.getActions());
			printInstrumentation(agent.getInstrumentation());
		}
		catch(Exception e){
			e.printStackTrace();
		}		
	}
	
	
	
	private static void retoAtascoAStarCaminoRectoDemo()
	{
		System.out.println("\nRetoAtascoDemo AStar HeuristicaBloqueoCaminoRecto-->");
		try{
			
			Problem problema = new Problem(_estadoInicial, 
					AtascoFunctionFactory.getActionsFunction(), 
					AtascoFunctionFactory.getResultFunction(), 
					new AtascoGoalTest(), new AtascoStepCostFunction());
			
			SearchForActions busqueda = new AStarSearch(new GraphSearch(), new HeuristicaBloqueoCaminoRecto());
			SearchAgent agent = new SearchAgent(problema, busqueda);
			
			printActions(agent.getActions());
			printInstrumentation(agent.getInstrumentation());
		}
		catch(Exception e){
			e.printStackTrace();
		}		
	}
	
	private static void retoAtascoAStarCaminoRecursivoDemo()
	{
		System.out.println("\nRetoAtascoDemo AStar HeuristicaBloqueoCaminoRecursivo-->");
		try{
			Problem problema = new Problem(_estadoInicial, 
					AtascoFunctionFactory.getActionsFunction(), 
					AtascoFunctionFactory.getResultFunction(), 
					new AtascoGoalTest(), new AtascoStepCostFunction());
			
			SearchForActions busqueda = new AStarSearch(new GraphSearch(), new HeuristicaBloqueoCaminoRecursivo());
			SearchAgent agent = new SearchAgent(problema, busqueda);
			
			printActions(agent.getActions());
			printInstrumentation(agent.getInstrumentation());
		}
		catch(Exception e){
			e.printStackTrace();
		}		
	}
	
	private static void retoAtascoGBFCaminoRectoDemo() 
	{
		System.out.println("\nRetoAtascoDemo Greedy Best First Search HeuristicaBloqueoCaminoRecto-->");
		try{
			Problem problema = new Problem(_estadoInicial, 
					AtascoFunctionFactory.getActionsFunction(), 
					AtascoFunctionFactory.getResultFunction(), 
					new AtascoGoalTest(), new AtascoStepCostFunction());
			
			SearchForActions busqueda = new GreedyBestFirstSearch(new GraphSearch(), new HeuristicaBloqueoCaminoRecto());
			SearchAgent agent = new SearchAgent(problema, busqueda);
			
			printActions(agent.getActions());
			printInstrumentation(agent.getInstrumentation());
		}
		catch(Exception e){
			e.printStackTrace();
		}		
	}
	
	private static void retoAtascoGBFCaminoRecursivoDemo() 
	{
		System.out.println("\nRetoAtascoDemo Greedy Best First Search HeuristicaBloqueoCaminoRecto-->");
		try{
			Problem problema = new Problem(_estadoInicial, 
					AtascoFunctionFactory.getActionsFunction(), 
					AtascoFunctionFactory.getResultFunction(), 
					new AtascoGoalTest(), new AtascoStepCostFunction());
			
			SearchForActions busqueda = new GreedyBestFirstSearch(new GraphSearch(), new HeuristicaBloqueoCaminoRecursivo());
			SearchAgent agent = new SearchAgent(problema, busqueda);
			
			printActions(agent.getActions());
			printInstrumentation(agent.getInstrumentation());
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
