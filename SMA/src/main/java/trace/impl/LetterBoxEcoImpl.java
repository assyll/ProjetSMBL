package trace.impl;

import general.LetterBoxEco;
import trace.ActionTrace;
import trace.interfaces.IAddAction;
import trace.interfaces.IUpdateCurrentAgent;

public class LetterBoxEcoImpl extends LetterBoxEco implements IAddAction, IUpdateCurrentAgent{

	@Override
	protected IAddAction make_addAction() {
		// TODO Auto-generated method stub
		return this;
	}

	@Override
	protected IUpdateCurrentAgent make_updateCurrentAgent() {
		// TODO Auto-generated method stub
		return this;
	}

	@Override
	protected LetterBoxSpecies make_LetterBoxSpecies() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void addAction(ActionTrace actionTrace) {
		// TODO Auto-generated method stub
		
	}

}
