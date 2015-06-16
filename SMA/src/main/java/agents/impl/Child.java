package agents.impl;

public class Child {

	private String transId;
	private String endStateId;
	
	/**
	 * Couple de Id d'une transition et de Id de son etat cible.
	 * @param transId Id de la transition
	 * @param endStateId Id de l'etat cible
	 */
	public Child(String transId, String endStateId) {
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
	
}
