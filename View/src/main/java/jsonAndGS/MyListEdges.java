package jsonAndGS;

import java.util.LinkedList;
import java.util.List;

/**
 * <b>MyListeEdges est la classe repr�sentant une liste de edges</b>
 * <p>
 * MyListEdges poss�de un attribut liste pouvant contenir des edges. Il sera
 * possible d'ajouter des edges � cette liste
 * 
 * @see MyJsonEdge
 * 
 * @author hugo
 * @version 1.0
 */
public class MyListEdges {

	/**
	 * La liste contenant les edges
	 * <p>
	 * Il est possible d'ajouter des edges dans cette liste
	 * </p>
	 * 
	 * @see MyListEdges#MyListEdges()
	 * @see MyListEdges#addEdges(MyJsonEdge...)
	 * @see MyListEdges#get_listEdges()
	 */
	private List<MyJsonEdge> _listEdges;

	/**
	 * Constructeur de MyListEdges
	 * <p>
	 * A la construction d'un objet MyListEdges, la liste des edges est cr��e
	 * vide
	 * </p>
	 * 
	 * @see MyListEdges#_listEdges
	 */
	public MyListEdges() {
		_listEdges = new LinkedList<MyJsonEdge>();
	}

	/**
	 * Ajouter des edges � la liste des edges
	 * 
	 * @param edges
	 *            Des edges � ajouter � la liste
	 * 
	 * @see MyJsonEdge
	 * 
	 * @since 1.0
	 */
	public void addEdges(MyJsonEdge... edges) {
		for (MyJsonEdge edge : edges) {
			_listEdges.add(edge);
		}
	}

	/**
	 * Retourne la liste des edges
	 * 
	 * @return La liste des edges
	 * 
	 * @see MyJsonEdge
	 * 
	 * @since 1.0
	 */
	public List<MyJsonEdge> get_listEdges() {
		return _listEdges;
	}

}
