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
	
	private HashMap<String, XYLocation> _cars;
	private List<Action> _actions; 	//TODO mirar si esto es tan reconocible como lo siguiente

	public static Action palante = new DynamicAction("PAL");
	public static Action patras = new DynamicAction("PAT");


	
	//FICHERO PREDETERMINADO
	private static String FICHERO_PREDETERMINADO = "misc/niveles.txt";		//TODO CHECK PATH

	//CODIFICACION FICHERO
	private static final int FICH_COD_NIVEL = 1;
	private static final int FICH_COD_TAM = 3;
	
	private static final char PARED = '#';
	private static final char META = '@';
	private static final char HUECO = '.';


	
	public EstadoAtasco() {
		cargarTablero(null, 0);
	}
	
	public EstadoAtasco(EstadoAtasco e) {
		
		_tablero = new char[e._tam][e._tam];
		
		for(int i = 0; i < e._tam; i++)
			System.arraycopy(e._tablero[i], 0, _tablero[i], 0, e._tam);
		
		_tam = e._tam;
		_cars = new HashMap<String,XYLocation>(e._cars);
	}
	
	
	public void cargarTablero(String fichero, int nivel) {
		if (fichero == null)
			fichero = FICHERO_PREDETERMINADO;
		
		_cars = new HashMap<String, XYLocation>();
		
	
		File f = new File(fichero);
		try {
			BufferedReader bf =  new BufferedReader(new FileReader(f));
			
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
					if(c != PARED && c != META && c != HUECO && !_cars.containsKey(c+""))
					{
						_cars.put(c+"", new XYLocation(j, i));	//TODO revar cambiado i por j en constr
						//como aqui guardas en la x <= j y en la y <=i,
						//en la linea 175 se "deshace" el cruce de variables, para usar j e i en vez de x e y
						
						//TODO:fixazohc
						_actions.add(new DynamicAction(c+"PAL"));
						_actions.add(new DynamicAction(c+ "PAT"));

						//_actions.add(palante);//TODO:MIRAR
						//_actions.add(patras);						
					}
				}
			}				
			bf.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public void moverPalante(char c) {
		if(c == PARED || c == META || c == HUECO)
			return;
	
		int i = 0, j = 0;
		boolean esHorizontal = Character.isLowerCase(c);
		
		while(i < _tam && _tablero[i][j] != c) {
			while(j < _tam && _tablero[i][j] != c) 
				j++;
			i++;
		}
		
		int carIter;
		if(esHorizontal) {
			carIter = j + 1;
			while(_tablero[i][carIter++] == c)
						
			if(carIter < _tam - 1 && _tablero[i][carIter] == HUECO) {
				_tablero[i][carIter] = c;
				_tablero[i][j] = HUECO;
			}
		}
		else {
			carIter = i + 1;
			while(_tablero[carIter++][j] == c)
			
			if(carIter < _tam - 1 && _tablero[carIter][j] == HUECO) {
				_tablero[carIter][j] = c;
				_tablero[i][j] = HUECO;
			}
		}
	}
	
	public void moverPatras(char c) {
		if(c == PARED || c == META || c == HUECO)
			return;
		
		int i = 0, j = 0;
		boolean esHorizontal = Character.isLowerCase(c);
		
		while(i < _tam && _tablero[i][j] != c) {
			while(j < _tam && _tablero[i][j] != c) 
				j++;
			i++;
		}
		
		int carIter;
		if(esHorizontal) {
			carIter = j + 1;
			while(_tablero[i][carIter++] == c)	
						
			if(j > 1 && _tablero[i][j-1] == HUECO) {
				_tablero[i][j - 1] = c;
				_tablero[i][carIter - 1] = HUECO;
			}
		}
		else {
			carIter = i + 1;
			while(_tablero[carIter++][j] == c)	
				
			if(i > 1 && _tablero[i-1][j] == HUECO) {
				_tablero[i - 1][j] = c;
				_tablero[carIter - 1][j] = HUECO;
			}
		}
	}
	
	public boolean canMoveCar(Action where, char car) {
		if(car == PARED || car == META || car == HUECO)
			return false;
		
		XYLocation carLoc = getPositionOf(car);
		boolean esHorizontal = Character.isLowerCase(car);
		
		//cambiado i -> iter, x -> j,  y -> i			
		int carIter = 0, j = carLoc.getXCoOrdinate(), i = carLoc.getYCoOrdinate();	//TODO revar
		
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
		}
		
		if(x==_tam && y == _tam)
			x = y = 0;
		
		return new XYLocation(y, x);	//TODO revar , cambiado y por x en el constr
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
		
		for(String clave : _cars.keySet()) {
			XYLocation pos = getPositionOf(clave.charAt(0));
			hash += 11 * pos.getXCoOrdinate() + 19 * pos.getYCoOrdinate();
		}
		
		return hash;
	}
	
}
