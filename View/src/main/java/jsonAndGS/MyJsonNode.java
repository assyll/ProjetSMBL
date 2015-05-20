package jsonAndGS;

import java.util.List;

/**
 * <b>MyJsonNode est la classe représentant une node</b>
 * <p>
 * Une node est caractérisée par les informations suivantes :
 * </p>
 * <ul>
 * <li>Un nom unique</li>
 * <li>Un booléen définissant si la node est source ou non</li>
 * <li>Un booléen définissant si la node est finale ou non</li>
 * </ul>
 * <p>
 * De plus une node possède une liste d'attributs il sera possible d'ajouter des
 * attributs à cette liste
 * </p>
 * 
 * @author hugo
 * @version 1.0
 */
public class MyJsonNode {

	/**
	 * Le nom de la node
	 * 
	 * @see MyJsonNode#MyJsonNode(String, boolean, boolean, List)
	 * @see MyJsonNode#get_name()
	 */
	private String _name;

	/**
	 * Le booléen définissant si la node est source [finale] ou non
	 * 
	 * @see MyJsonNode#MyJsonNode(String, boolean, boolean, List)
	 * @see MyJsonNode#is_source()
	 * @see MyJsonNode#is_final()
	 */
	private boolean _source, _final;

	/**
	 * La liste contenant les attributs de la node
	 * <p>
	 * Il est possible d'ajouter des attributs à cette liste
	 * </p>
	 * 
	 * @see MyJsonNode#MyJsonNode(String, boolean, boolean, List)
	 * @see MyJsonNode#get_attributs()
	 */
	private List<String> _attributs;

	/**
	 * Constructeur de MyJsonNode
	 * 
	 * @param name
	 *            Le nom de la node
	 * @param source
	 *            Le booléen définissant si la node est source ou non
	 * @param fin
	 *            Le booléen définissant si la node est finale ou non
	 * @param attributs
	 *            La liste des attributs de la node
	 * 
	 * @see MyJsonNode#_name
	 * @see MyJsonNode#_source
	 * @see MyJsonNode#_final
	 * @see MyJsonNode#_attributs
	 */
	public MyJsonNode(String name, boolean source, boolean fin,
			List<String> attributs) {
		_name = name;
		_source = source;
		_final = fin;
		_attributs = attributs;
	}

	/**
	 * Retourne le nom de la node
	 * 
	 * @return Le nom de la node
	 * 
	 * @since 1.0
	 */
	public String get_name() {
		return _name;
	}

	/**
	 * Retourne le booléen définissant si la node est source
	 * 
	 * @return Le booléen définissant si la node est source
	 * 
	 * @since 1.0
	 */
	public boolean is_source() {
		return _source;
	}

	/**
	 * Retourne le booléen définissant si la node est finale
	 * 
	 * @return Le booléen définissant si la node est finale
	 * 
	 * @since 1.0
	 */
	public boolean is_final() {
		return _final;
	}

	/**
	 * Ajoute un attribut à la liste des attributs
	 * 
	 * @param attribut
	 *            Le nouvel attribut de la node
	 * 
	 * @see MyJsonNode#_attributs
	 * 
	 * @since 1.0
	 */
	public void addAttribut(String attribut) {
		_attributs.add(attribut);
	}

	/**
	 * Retourne la liste des attributs de la node
	 * 
	 * @return La liste des attributs de la node
	 * 
	 * @since 1.0
	 */
	public List<String> get_attributs() {
		return _attributs;
	}

}
