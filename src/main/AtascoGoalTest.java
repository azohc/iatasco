package main;

import aima.core.search.framework.problem.GoalTest;

public class AtascoGoalTest implements GoalTest{

	
	@Override
	public boolean isGoalState(Object arg0) {
		// TODO Auto-generated method stub
		return false;
	}

	
	public boolean GoalTest(EstadoAtasco e) {
		for(int i = 0; i < e.getTam(); i++) 
			if(e.getTablero()[i][e.getTam() - 1] == 'z')
				return true;
		
		return false;
	}
}
