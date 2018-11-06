package main;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;

import aima.core.agent.Action;
import aima.core.agent.impl.DynamicAction;
import aima.core.util.datastructure.XYLocation;

public class EstadoAtasco {

	private char[][] _tablero;
	private int _tam;
	
	//FICHERO PREDETERMINADO
	private static String FICHERO_PREDETERMINADO = "misc/niveles.txt";		//TODO CHECK PATH

	//CODIFICACION FICHERO
	private static final int FICH_COD_NIVEL = 1;
	private static final int FICH_COD_TAM = 3;
	
	private static final char PARED = '#';
	private static final char META = '@';
	private static final char HUECO = '.';

	public static Action palante = new DynamicAction("PAL");
	public static Action patras = new DynamicAction("PAT");
	
	
	public EstadoAtasco() {
		//inicializar _tablero con tamanio predeterminado
		cargarTablero(null, 0);
	}
	
	public EstadoAtasco(EstadoAtasco e) {
		//_tablero = e._tablero; //TODO CHECK
		
		_tablero = new char[e._tam][e._tam];
		
		for(int i = 0; i < e._tam; i++)
			System.arraycopy(e._tablero[i], 0, _tablero[i], 0, e._tam);
		
		_tam = e._tam;
	}
	
	
	public void cargarTablero(String fichero, int nivel) {
		if (fichero == null)
			fichero = FICHERO_PREDETERMINADO;
	
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
				for(int j = 0; j < _tam; j++) 
					_tablero[i][j] = linea.charAt(j);
			}				
			bf.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public void moverPalante(char c) {
		if(c == PARED || c == META || c == HUECO)
			return;
		int x = 0, y = 0;
		boolean esHorizontal = Character.isLowerCase(c);
		
		while(x < _tam && _tablero[x][y] != c) {
			while(y < _tam && _tablero[x][y] != c) {
				y++;
			}
			x++;
		}
		int i;
		if(esHorizontal) {
			i = y + 1;
			while(_tablero[x][i++] == c)	//TODO check
						
			if(i < _tam - 1 && _tablero[x][i] == HUECO) {
				_tablero[x][i] = c;
				_tablero[x][y] = HUECO;
			}
		}
		else {
			i = x + 1;
			while(_tablero[i++][y] == c)	//TODO check
			
			if(i < _tam - 1 && _tablero[i][y] == HUECO) {
				_tablero[i][y] = c;
				_tablero[x][y] = HUECO;
			}
		}
	}
	
	public void moverPatras(char c) {
		if(c == PARED || c == META || c == HUECO)
			return;
		int x = 0, y = 0;
		boolean esHorizontal = Character.isLowerCase(c);
		
		while(x < _tam && _tablero[x][y] != c) {
			while(y < _tam && _tablero[x][y] != c) {
				y++;
			}
			x++;
		}
		int i;
		if(esHorizontal) {
			i = y + 1;
			while(_tablero[x][i++] == c)	//TODO check ? oob
						
			if(y > 1 && _tablero[x][y-1] == HUECO) {
				_tablero[x][y - 1] = c;
				_tablero[x][i - 1] = HUECO;
			}
		}
		else {
			i = x + 1;
			while(_tablero[i++][y] == c)	//TODO check ? oob
			
			if(x > 1 && _tablero[x-1][y] == HUECO) {
				_tablero[x - 1][y] = c;
				_tablero[i - 1][y] = HUECO;
			}
		}
	}
	
	public boolean canMoveCar(Action where, char car) {
		if(car == PARED || car == META || car == HUECO)
			return false;
		XYLocation carLoc = getPositionOf(car);
		boolean esHorizontal = Character.isLowerCase(car);
		int i = 0, x = carLoc.getXCoOrdinate(), y = carLoc.getYCoOrdinate();
		
		if(where.equals(palante)) {
			if(esHorizontal) {
				i = y + 1;
				while(_tablero[x][i++] == car)	//TODO check
							
				if(i < _tam - 1 && _tablero[x][i] == HUECO) 
					return true;
			}
			else {
				i = x + 1;
				while(_tablero[i++][y] == car)	//TODO check
				
				if(i < _tam - 1 && _tablero[i][y] == HUECO) 
					return true;
			}
		}
		else {
			if(esHorizontal) 
				if(y > 1 && _tablero[x][y-1] == HUECO) 
					return true;
			else 
				if(x > 1 && _tablero[x-1][y] == HUECO) 
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
		return new XYLocation(x, y);		
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

	public void setTam(int _tam) {
		this._tam = _tam;
	}
	
	
	
}
