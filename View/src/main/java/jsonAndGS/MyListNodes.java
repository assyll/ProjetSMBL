package jsonAndGS;

import java.util.LinkedList;
import java.util.List;

/**
 * <b>MyListeNodes est la classe repr�sentant une liste de nodes</b>
 * <p>
 * MyListNodes poss�de un attribut liste pouvant contenir des nodes. Il sera
 * possible d'ajouter des nodes � cette liste
 * 
 * @see MyJsonNode
 * 
 * @author hugo
 * @version 1.0
 */
public class MyListNodes {

	/**
	 * La liste contenant les nodes
	 * <p>
	 * Il est possible d'ajouter des nodes dans cette liste
	 * </p>
	 * 
	 * @see MyListNodes#MyListNodes()
	 * @see MyListNodes#addNode(MyJsonNode...)
	 * @see MyListNodes#get_listNodes()
	 */
	private List<MyJsonNode> _listNodes;

	/**
	 * Constructeur de MyListNodes
	 * <p>
	 * A la construction d'un objet MyListNodes, la liste des nodes est cr��e
	 * vide
	 * </p>
	 * 
	 * @see MyListNodes#_listNodes
	 */
	public MyListNodes() {
		_listNodes = new LinkedList<MyJsonNode>();
	}

	/**
	 * Ajouter des nodes � la liste des nodes
	 * 
	 * @param nodes
	 *            Des nodes � ajouter � la liste
	 * 
	 * @see MyJsonNode
	 * 
	 * @since 1.0
	 */
	public void addNode(MyJsonNode... nodes) {
		for (MyJsonNode node : nodes) {
			_listNodes.add(node);
		}
	}

	/**
	 * Retourne la liste des nodes
	 * 
	 * @return La liste des nodes
	 * 
	 * @see MyJsonNode
	 * 
	 * @since 1.0
	 */
	public List<MyJsonNode> get_listNodes() {
		return _listNodes;
	}

}
