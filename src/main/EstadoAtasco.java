package main;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;

import aima.core.agent.Action;
import aima.core.agent.impl.DynamicAction;
import aima.core.util.datastructure.XYLocation;

public class EstadoAtasco {
	
	private char[][] _tablero;
	private int _tam;
	
	//para saber en que posicion se encuentra cada coche
	private HashMap<Character, XYLocation> _carCoords;
	
	//para guardar acciones que relacionan a un coche con su identificador
	private List<Action> _actions; 	

	public static Action palante = new DynamicAction("PAL");
	public static Action patras = new DynamicAction("PAT");


	
	//FICHERO PREDETERMINADO
	private static String FICHERO_PREDETERMINADO = "misc/niveles.txt";	

	//CODIFICACION FICHERO
	private static final int FICH_COD_NIVEL = 1;
	private static final int FICH_COD_TAM = 3;
	
	private static final char PARED = '#';
	private static final char META = '@';
	private static final char HUECO = '.';


	
	public EstadoAtasco() {
		cargarTablero(null, 0);
	}
	
	//TODO aniadir constructor para usar otro fichero / nivel
	
	public EstadoAtasco(EstadoAtasco e) {
		
		_tablero = new char[e._tam][e._tam];
		
		for(int i = 0; i < e._tam; i++)
			System.arraycopy(e._tablero[i], 0, _tablero[i], 0, e._tam);
		
		_tam = e._tam;
		_carCoords = new HashMap<Character,XYLocation>(e._carCoords);
		for(Action a : e.getActionList())
			_actions.add(a);
	}
	
	//Se asume que no seran introducidos tableros con una distribucion de coches erronea
	public void cargarTablero(String fichero, int nivel) {
		if (fichero == null)
			fichero = FICHERO_PREDETERMINADO;
		
		_carCoords = new HashMap<Character, XYLocation>();
		_actions = new LinkedList<Action>();
			
		try {
			BufferedReader bf =  new BufferedReader(new FileReader(new File(fichero)));
			String linea = bf.readLine();
			String[] plinea = linea.split("\\s+");	
			
			while(Integer.parseInt(plinea[FICH_COD_NIVEL]) != nivel)	{			
				linea = bf.readLine();
				plinea = linea.split("\\s+");	
			}
			
			_tam = Integer.parseInt(plinea[FICH_COD_TAM]);
			_tablero = new char[_tam][_tam];
			
			for(int i = 0; i < _tam; i++) {
				linea = bf.readLine();
				
				for(int j = 0; j < _tam; j++) {
					char c = linea.charAt(j);
					_tablero[i][j] = c;
					
					if(c != PARED && c != META && c != HUECO && !_carCoords.containsKey(c)){
						_carCoords.put(c, new XYLocation(j, i));
						_actions.add(new DynamicAction(c + "PAL"));
						_actions.add(new DynamicAction(c + "PAT"));
					}
				}
			}				
			bf.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public void moverPalante(char coche) {
		if(coche == PARED || coche == META || coche == HUECO)
			return;
		
		boolean esHorizontal = Character.isLowerCase(coche);
		
		XYLocation coords = _carCoords.get(coche);
		int i = coords.getYCoOrdinate(), j = coords.getXCoOrdinate();
		
		int carIter;
		if(esHorizontal) {
			carIter = j + 1;
			while(_tablero[i][carIter++] == coche)
						
			if(carIter < _tam - 1 && _tablero[i][carIter] == HUECO) {
				_tablero[i][carIter] = coche;
				_tablero[i][j] = HUECO;
			}
			
			XYLocation newLocation = new XYLocation(j+1, i);
			_carCoords.put(coche, newLocation);
		}
		else {
			carIter = i + 1;
			while(_tablero[carIter++][j] == coche)
			
			if(carIter < _tam - 1 && _tablero[carIter][j] == HUECO) {
				_tablero[carIter][j] = coche;
				_tablero[i][j] = HUECO;
			}
			
			XYLocation newLocation = new XYLocation(j, i+1);
			_carCoords.put(coche, newLocation);		
		}
	}
	
	public void moverPatras(char coche) {
		if(coche == PARED || coche == META || coche == HUECO)
			return;

		boolean esHorizontal = Character.isLowerCase(coche);
		
		XYLocation coords = _carCoords.get(coche);
		int i = coords.getYCoOrdinate(), j = coords.getXCoOrdinate();
		
		int carIter;
		if(esHorizontal) {
			carIter = j + 1;
			while(_tablero[i][carIter++] == coche)	
						
			if(j > 1 && _tablero[i][j-1] == HUECO) {
				_tablero[i][j - 1] = coche;
				_tablero[i][carIter - 1] = HUECO;
			}
			
			XYLocation newLocation = new XYLocation(j-1, i);
			_carCoords.put(coche, newLocation);	
		}
		else {
			carIter = i + 1;
			while(_tablero[carIter++][j] == coche)	
				
			if(i > 1 && _tablero[i-1][j] == HUECO) {
				_tablero[i - 1][j] = coche;
				_tablero[carIter - 1][j] = HUECO;
			}
			
			XYLocation newLocation = new XYLocation(j, i-1);
			_carCoords.put(coche, newLocation);	
		}
	}
	
	public boolean canMoveCar(Action where, char car) {
		if(car == PARED || car == META || car == HUECO)
			return false;
		
		XYLocation carLoc = _carCoords.get(car);
		boolean esHorizontal = Character.isLowerCase(car);
		
		int carIter = 0, j = carLoc.getXCoOrdinate(), i = carLoc.getYCoOrdinate();	
		
		if(where.equals(palante)) {
			if(esHorizontal) {
				carIter = i + 1;
				while(_tablero[j][carIter++] == car)	
							
				if(carIter < _tam - 1 && _tablero[j][carIter] == HUECO) 
					return true;
			}
			else {
				carIter = j + 1;
				while(_tablero[carIter++][i] == car)
				
				if(carIter < _tam - 1 && _tablero[carIter][i] == HUECO) 
					return true;
			}
		}
		else if(where.equals(patras)){
			if(esHorizontal) 
				if(i > 1 && _tablero[j][i-1] == HUECO) 
					return true;
			else 
				if(j > 1 && _tablero[j-1][i] == HUECO) 
					return true;
		}
		return false;
	}
	
	public XYLocation getPositionOf(char c) {
		int x = 0, y = 0;
		
		while(x < _tam && _tablero[x][y] != c) {
			while(y < _tam && _tablero[x][y] != c) {
				y++;
			}
			x++;
			y = 0;
		}
		
		if(x==_tam && y == _tam)
			x = y = 0;
		
		return new XYLocation(y, x);	
	}
	
	
	@Override
	public boolean equals(Object o) {

		if (this == o) 
			return true;
		
		if ((o == null) || (this.getClass() != o.getClass()) || _tam != ((EstadoAtasco) o).getTam()) 
			return false;
		
		for (int i = 0; i < _tam; i++) 
			for(int j = 0; j < _tam; j++)
				if(_tablero[i][j] != ((EstadoAtasco) o).getTablero()[i][j])
					return false;
		
		return true;
	}
	
	public char[][] getTablero(){
		return _tablero;
	}

	public int getTam() {
		return _tam;
	}
	
	public List<Action> getActionList()
	{
		return _actions;
	}

	public void setTam(int _tam) {
		this._tam = _tam;
	}
	
	@Override
	public int hashCode()
	{
		int hash = 0;
		
		for(Character c : _carCoords.keySet()) {
			XYLocation pos = _carCoords.get(c);
			hash += 11 * pos.getXCoOrdinate() + 19 * pos.getYCoOrdinate();
		}
		
		return hash;
	}

	public HashMap<Character, XYLocation> getCarMap() {
		return _carCoords;
	}

	public void setCarCoords(HashMap<Character, XYLocation> _carCoords) {
		this._carCoords = _carCoords;
	}
	
}
