package main;

import aima.core.search.framework.problem.GoalTest;

public class AtascoGoalTest implements GoalTest{
		
	@Override
	public boolean isGoalState(Object state) {
		for(int i = 0; i < ((EstadoAtasco) state).getTam(); i++) 
			if(((EstadoAtasco) state).getTablero()[i][((EstadoAtasco) state).getTam() - 1] == 'z')
				return true;
		
		return false;
	}


}
