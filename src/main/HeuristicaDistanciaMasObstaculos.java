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
		
		XYLocation posCocheASacar = e.getCarMap().get(EstadoAtasco.COCHE_ROJO);
		
		int i_rojo = posCocheASacar.getYCoOrdinate(), j_rojo = posCocheASacar.getXCoOrdinate();
		char[][] tablero = e.getTablero();
		
		int carIter = j_rojo + 1;	 //Iterador para llegar hasta el final del coche
		while(carIter < e.getTam() && tablero[i_rojo][carIter] == EstadoAtasco.COCHE_ROJO)
			carIter++;			//carIter queda en la celda posterior al coche
	
		//Todos los coches que impidan el paso del coche rojo se meten a una lista
		List<Character> obstaculos = new LinkedList<Character>();
	
		for(; carIter < e.getTam(); carIter++) 
			if(tablero[i_rojo][carIter] != EstadoAtasco.HUECO && tablero[i_rojo][carIter] != EstadoAtasco.META) 
				obstaculos.add(tablero[i_rojo][carIter]);
		
		retVal += obstaculos.size();
		
		//carIter queda en la primera posicion despues del coche rojo, con lo cual
		//tamTablero - carIter = movimientos para sacar al coche rojo del atasco (sin contar obstaculos)
		retVal += e.getTam() - carIter;
		
		for(char c : obstaculos) {	//Para cada obstaculo
			//Calculamos sus coordenadas
			XYLocation cocheBloqLocation = e.getCarMap().get(c);	
			int i_obst = cocheBloqLocation.getYCoOrdinate();
			int j_obst = cocheBloqLocation.getXCoOrdinate();
			
			carIter = i_obst;
			int tamCoche = 1;
			//Obtenemos el tamaño del coche, dejando carIter en la primera posicion posterior al obstaculo
			while(tablero[carIter][j_obst] == c) {
				carIter++;
				tamCoche++;
			}
			
			if(tamCoche == 2)		//Si el obstaculo es un coche, solo hace falta un movimiento para quitarlo
				retVal++;
			
			else {					//En el caso de ser un camion...
				//Se calcula la cantidad de movimientos necesarios restando las posiciones verticales
				//del coche rojo y del coche obstaculo. 
				int resta = i_obst - i_rojo;
				
				//Solo puede haber tres configuraciones en los que un camion bloquea a un coche

				//Si la ultima celda del camion esta en la fila de la meta, 
				//tiene que haber un hueco encima del camion para sacarlo en 1 movimiento, si no, son 3
				if(resta == -2)			
					retVal += tablero[i_obst - 1][j_obst] != EstadoAtasco.PARED ? 1 : 3;	
				
				//Si el medio del camion esta en la fila de la meta, 
				//dos movimientos en cualquiera de las direcciones lo desatasca
				else if(resta == -1)	
					retVal += 2;
				
				//Si la primera celda del camion esta en la fila de la meta, 
				//tiene que haber un hueco debajo del camion para sacarlo en 1 movimiento, si no, son 3
				else					
					retVal += tablero[carIter][j_obst] != EstadoAtasco.PARED ? 1 : 3;
			}
		}
		
		return retVal;
	}
}
