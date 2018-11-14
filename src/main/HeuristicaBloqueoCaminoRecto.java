package main;

import aima.core.search.framework.evalfunc.HeuristicFunction;
import aima.core.util.datastructure.XYLocation;

public class HeuristicaBloqueoCaminoRecto implements HeuristicFunction{

	@Override
	public double h(Object o) {
		EstadoAtasco estado = (EstadoAtasco) o;
		return getCochesEnElCaminoRecto(estado);
	}
	
	// Unicamente cuenta la cantidad de coches en el tramo entre el coche rojo y la meta
	
	private double getCochesEnElCaminoRecto(EstadoAtasco e) {
		
		XYLocation posCocheASacar = e.getCarMap().get(EstadoAtasco.COCHE_ROJO);
		
		int i = posCocheASacar.getYCoOrdinate(), j = posCocheASacar.getXCoOrdinate();
		char[][] tablero = e.getTablero();
		
		int carIter = j + 1; 	//Iterador para llegar hasta el final del coche
		while(carIter < e.getTam() && tablero[i][carIter] == EstadoAtasco.COCHE_ROJO)
			carIter++;			 //carIter queda en la celda posterior al coche
		
		
		double numCoches = 0;
		
		//asumimos que no habra ningun estado en el que se
		//encuentre otro coche en la misma direccion que el
		//coche rojo, es decir, no habra estado con mas de un
		//coche en horizontal situado en la fila de la meta
		for(; carIter < e.getTam(); carIter++) 
			if(tablero[i][carIter] != EstadoAtasco.HUECO && tablero[i][carIter] != EstadoAtasco.META)
				numCoches++;
		
		return numCoches;
	}

}
