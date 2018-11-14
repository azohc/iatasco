package main;

import aima.core.search.framework.problem.GoalTest;

public class AtascoGoalTest implements GoalTest{
		
	@Override
	public boolean isGoalState(Object state) {
		EstadoAtasco e = (EstadoAtasco) state;
		for(int i = 0; i < e.getTam(); i++) 
			if(e.getTablero()[i][e.getTam() - 1] == EstadoAtasco.COCHE_ROJO)
				return true;
		
		return false;
	}


}
