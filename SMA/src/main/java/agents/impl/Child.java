package agents.impl;

import trace.Action;

public class Child {

	private String transId;
	private String endStateId;
	private Action action;
	
	/**
	 * Triplet de Id d'une transition, de Id de son etat cible
	 * et de Id de l'action.
	 * @param transId Id de la transition
	 * @param endStateId Id de l'etat cible
	 * @param action action de la transition
	 */
	public Child(String transId, String endStateId, Action action) {
		this.action = action;
		this.transId = transId;
		this.endStateId = endStateId;
	}

	public String getTransId() {
		return transId;
	}

	public void setTransId(String transId) {
		this.transId = transId;
	}

	public String getEndStateId() {
		return endStateId;
	}

	public void setEndStateId(String endStateId) {
		this.endStateId = endStateId;
	}
	
	public Action getAction() {
		return action;
	}
	
	public void setAction(Action action) {
		this.action = action;
	}
	
}
