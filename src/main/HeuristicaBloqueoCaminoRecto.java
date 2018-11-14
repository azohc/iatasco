package main;

import aima.core.search.framework.evalfunc.HeuristicFunction;
import aima.core.util.datastructure.XYLocation;

public class HeuristicaBloqueoCaminoRecto implements HeuristicFunction{

	@Override
	public double h(Object o) {
		EstadoAtasco estado = (EstadoAtasco) o;
		return getCochesEnElCaminoRecto(estado);
	}
	
	private int getCochesEnElCaminoRecto(EstadoAtasco e) {
		
		XYLocation posCocheASacar = e.getCarMap().get('z');
		
		int i = posCocheASacar.getYCoOrdinate(), j = posCocheASacar.getXCoOrdinate();
		char[][] tablero = e.getTablero();
		
		int carIter = j + 1;
		while(tablero[i][carIter] == 'z')
			carIter++;
		
		
		int numCoches = 0;
		
		//asumimos que no habra ningun estado en el que se
		//encuentre otro coche en frente del coche a sacar del 
		//atasco, es decir, no habra estado con mas de un
		//coche en horizontal situads en la fila de la meta
		for(; carIter < e.getTam(); carIter++) 
			if(tablero[i][carIter] != '.' && tablero[i][carIter] != '@')
				numCoches++;
		
		return numCoches;
	}

}
