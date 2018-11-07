package main;

import java.util.LinkedHashSet;
import java.util.Set;

import aima.core.agent.Action;
import aima.core.probability.mdp.ActionsFunction;

public class AtascoActionsFunction implements ActionsFunction
{
	@Override
	public Set<Action> actions(Object state)
	{
		EstadoAtasco estado = (EstadoAtasco) state;
		// lista de acciones posibles
		Set<Action> actions = new LinkedHashSet<Action>();
	
		return actions;
	}
}

