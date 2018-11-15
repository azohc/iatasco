package main;

import java.util.Iterator;
import java.util.List;
import java.util.Properties;

import aima.core.agent.Action;
import aima.core.search.framework.SearchAgent;
import aima.core.search.framework.SearchForActions;
import aima.core.search.framework.problem.Problem;
import aima.core.search.framework.qsearch.GraphSearch;
import aima.core.search.framework.qsearch.TreeSearch;
import aima.core.search.informed.AStarSearch;
import aima.core.search.informed.GreedyBestFirstSearch;
import aima.core.search.uninformed.BreadthFirstSearch;
import aima.core.search.uninformed.DepthFirstSearch;
import aima.core.search.uninformed.UniformCostSearch;

public class RetoAtascoDemo {
	
	private static EstadoAtasco _estadoInicial = new EstadoAtasco();
	
	public static void main(String[] args) {
		if(args.length > 0)	// Si se quiere usar un fichero con niveles distinto, 
			_estadoInicial = new EstadoAtasco(args[0]); // Se ha de pasar su ruta como unico parametro
		
		//INFORMADAS
		System.out.println("Busquedas informadas: Heuristica bloqueo en camino recto");
		retoAtascoAStarCaminoRectoDemo(); 
		retoAtascoGBFCaminoRectoDemo();		
		
		System.out.println();
		System.out.println("Busquedas informadas: Heuristica distancia mas obstaculos");
		retoAtascoAStarDistanciaMasObstaculosDemo();
		retoAtascoGBFDistanciaMasObstaculosDemo();
		
		//NO INFORMADAS
		System.out.println(System.lineSeparator());
		System.out.println("Busquedas no informadas: Breadth First Search" );
		retoAtascoBreadthFirstSearchDemo();
		
		System.out.println();
		System.out.println("Busquedas no informadas: Uniform Cost Search" );
		retoAtascoUniformCostSearchDemo();
		
		System.out.println();
		System.out.println("Busquedas no informadas: Depth First Search" );
		//DFS con GraphSearch
		retoAtascoDFSGraphDemo();
		System.out.println();
		//DFS con TreeSearch
		//retoAtascoDFSTreeDemo(); //Se pilla en un bucle infinito, acaba lanzando
		//java.lang.OutOfMemoryError: GC overhead limit exceeded
	}
	
	private static void retoAtascoDFSTreeDemo() 
	{
		System.out.println("\nRetoAtascoDemo Depth First Search usando TreeSearch-->");
		try{
			Problem problema = new Problem(_estadoInicial, 
					AtascoFunctionFactory.getActionsFunction(), 
					AtascoFunctionFactory.getResultFunction(), 
					new AtascoGoalTest(), new AtascoStepCostFunction());
			
			DepthFirstSearch busqueda = new DepthFirstSearch(new TreeSearch());
			SearchAgent agent = new SearchAgent(problema, busqueda);
			
			printActions(agent.getActions());
			printInstrumentation(agent.getInstrumentation());
		}
		catch(Exception e){
			e.printStackTrace();
		}		
	}
	
	private static void retoAtascoDFSGraphDemo() 
	{
		System.out.println("\nRetoAtascoDemo Depth First Search usando GraphSearch-->");
		try{
			Problem problema = new Problem(_estadoInicial, 
					AtascoFunctionFactory.getActionsFunction(), 
					AtascoFunctionFactory.getResultFunction(), 
					new AtascoGoalTest(), new AtascoStepCostFunction());
			
			DepthFirstSearch busqueda = new DepthFirstSearch(new GraphSearch());
			SearchAgent agent = new SearchAgent(problema, busqueda);
			
			printActions(agent.getActions());
			printInstrumentation(agent.getInstrumentation());
		}
		catch(Exception e){
			e.printStackTrace();
		}		
	}

	private static void retoAtascoBreadthFirstSearchDemo() 
	{
		System.out.println("\nRetoAtascoDemo Breadth First Search-->");
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
	
	private static void retoAtascoUniformCostSearchDemo() 
	{
		System.out.println("\nRetoAtascoDemo Uniform Cost Search-->");
		try{
			Problem problema = new Problem(_estadoInicial, 
					AtascoFunctionFactory.getActionsFunction(), 
					AtascoFunctionFactory.getResultFunction(), 
					new AtascoGoalTest(), new AtascoStepCostFunction());
			
			SearchForActions search = new UniformCostSearch();
			SearchAgent agent = new SearchAgent(problema, search);
			
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
	
	private static void retoAtascoAStarDistanciaMasObstaculosDemo()
	{
		System.out.println("\nRetoAtascoDemo AStar HeuristicaDistanciaMasObstaculos-->");
		try{
			Problem problema = new Problem(_estadoInicial, 
					AtascoFunctionFactory.getActionsFunction(), 
					AtascoFunctionFactory.getResultFunction(), 
					new AtascoGoalTest(), new AtascoStepCostFunction());
			
			SearchForActions busqueda = new AStarSearch(new GraphSearch(), new HeuristicaDistanciaMasObstaculos());
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
		System.out.println("\nRetoAtascoDemo Metodo Voraz HeuristicaBloqueoCaminoRecto-->");
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
	
	private static void retoAtascoGBFDistanciaMasObstaculosDemo() 
	{
		System.out.println("\nRetoAtascoDemo Metodo Voraz HeuristicaDistanciaMasObstaculos-->");
		try{
			Problem problema = new Problem(_estadoInicial, 
					AtascoFunctionFactory.getActionsFunction(), 
					AtascoFunctionFactory.getResultFunction(), 
					new AtascoGoalTest(), new AtascoStepCostFunction());
			
			SearchForActions busqueda = new GreedyBestFirstSearch(new GraphSearch(), new HeuristicaDistanciaMasObstaculos());
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
