package main;

import aima.core.agent.impl.DynamicAction;

public class AtascoAction extends DynamicAction {

	private char _car;
	
	public AtascoAction(String name, char coche) {
		super(name);
		_car = coche;
	}
	
	public char getCar() {
		return _car;
	}

}
