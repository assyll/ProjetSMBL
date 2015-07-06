package generalStructure.interfaces;

public interface IControl {

	public boolean getPause();
	public boolean getStop();
	
	/**
	 * Modifie la valeur de lecture/pause.
	 * Si cest a vrai, le mettre a faux et vice-versa.
	 * Sans parametre.
	 */
	public void setPause();
	
}
