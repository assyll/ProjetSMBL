package agents.impl;

/**
 * TRY_TO_MERGE:             permet de demander de fusionner a un autre etat.
 * 
 * ADD_CHILD:                permet de mettre a jour l'ajout d'un fils
 *                           d'un fils d'un noeud.
 * 
 * ADD_FATHER_WITH_USERNAME: permet de mettre a jour le pere d'un noeud
 *                           tout en devenue l'etat courant grace a l'username.
 *                           
 * WAIT_FOR_NEXT_ACTION:     permet de savoir si on attend une action.
 * 
 * UPDATE_CHILD:             permet de demander aux transitions entrantes lors
 *                           de la fusion de mettre a jour leur fils.
 *               
 * SUICIDE_HIERARCHY:        permet de demander a un agent de se suicider apres
 *                           l'avoir demander a ses propres transitions
 *                           et etats fils.
 *                    
 * ASK_THE_FATHER_TO_MERGE:  permet de demander a ses peres s'il veut et peut
 *                           fusionner apres que moi je viens de faire.
 *                           
 * SEND_INFOS:               permet a un agent d'envoyer ses informations a
 *                           un autre agent.
 */
public enum RequestType implements Type {
	TRY_TO_MERGE,
	ADD_CHILD,
	ADD_FATHER_WITH_USERNAME,
	WAIT_FOR_NEXT_ACTION,
	UPDATE_CHILD,
	SUICIDE_HIERARCHY,
	ASK_THE_FATHER_TO_MERGE,
	SEND_INFOS;
}
