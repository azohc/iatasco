package main;

import java.util.LinkedList;
import java.util.List;

import aima.core.search.framework.evalfunc.HeuristicFunction;
import aima.core.util.datastructure.XYLocation;

public class HeuristicaDistanciaMasObstaculos implements HeuristicFunction {

	@Override
	public double h(Object o) {
		EstadoAtasco estado = (EstadoAtasco) o;
		return getDistanciaMasObstaculos(estado);
	}
	
	// Cuenta la cantidad de movimientos (sin contemplar obstaculos) hasta la meta +
	//		  					  el numero de obstaculos entre el coche y la meta +
	//		 el minimo numero de movimientos para que cada obstaculo deje de serlo +
	
	private double getDistanciaMasObstaculos(EstadoAtasco e){
		double retVal = 0;
		
		XYLocation posCocheASacar = e.getCarMap().get('z');
		
		int i = posCocheASacar.getYCoOrdinate(), j = posCocheASacar.getXCoOrdinate();
		char[][] tablero = e.getTablero();
		
		int carIter = j + 1;	 //Iterador para llegar hasta el final del coche
		while(carIter < e.getTam() && tablero[i][carIter] == 'z')
			carIter++;			//carIter queda en la celda posterior al coche
			
		List<Character> obstaculos = new LinkedList<Character>();
		
		for(; carIter < e.getTam(); carIter++) 
			if(tablero[i][carIter] != EstadoAtasco.HUECO && tablero[i][carIter] != EstadoAtasco.META) 
				obstaculos.add(tablero[i][carIter]);
	
		retVal += obstaculos.size();
		
		//carIter queda en la primera posicion despues del coche a sacar, con lo cual
		//tamTablero - carIter = movimientos para salir del atasco (sin contar obstaculos)
		retVal += e.getTam() - carIter;
		
		
		for(char c : obstaculos) {	//Para cada obstaculo
			XYLocation cocheBloqLocation = e.getCarMap().get(c);
			int iObst = cocheBloqLocation.getYCoOrdinate();
			int jObst = cocheBloqLocation.getXCoOrdinate();
			
			carIter = i;
			int tamCoche = 1;
			
			while(tablero[carIter][jObst] == c) {
				carIter++;
				tamCoche++;
			}
			
			if(tamCoche == 2)		//Si el obstaculo es un coche, solo hace falta un movimiento para quitarlo
				retVal++;
			else {					//En el caso de ser un camion...
				int resta = iObst - i;
				
				//Si la ultima celda del camion esta en la fila de la meta, 
				//tiene que haber un hueco encima del camion para sacarlo en 1 movimiento, si no, son 3
				if(resta == -2)			
					retVal += tablero[iObst - 1][jObst] != EstadoAtasco.PARED ? 1 : 3;	
				
				//Si el medio del camion esta en la fila de la meta, 
				//dos movimientos en cualquiera de las direcciones lo desatasca
				else if(resta == -1)	
					retVal += 2;
				
				//Si la primera celda del camion esta en la fila de la meta, 
				//tiene que haber un hueco debajo del camion para sacarlo en 1 movimiento, si no, son 3
				else					
					retVal += tablero[carIter][jObst] != EstadoAtasco.PARED ? 1 : 3;
			}
		}
		
		return retVal;
	}
}
