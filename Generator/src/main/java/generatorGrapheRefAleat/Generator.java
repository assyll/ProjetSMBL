package generatorGrapheRefAleat;

import java.util.ArrayList;
import java.util.List;
import java.util.Map.Entry;
import java.util.Random;

import org.neo4j.graphdb.DynamicLabel;
import org.neo4j.graphdb.DynamicRelationshipType;
import org.neo4j.graphdb.GraphDatabaseService;
import org.neo4j.graphdb.Node;
import org.neo4j.graphdb.Transaction;
import org.neo4j.graphdb.factory.GraphDatabaseFactory;

public class Generator {

	/**
	 * Une chance sur NB_CHANCE_CREER de creer un nouveau noeud
	 * ou une nouvelle transition.
	 */
	private final int NB_CHANCE_CREER = 2;
	
	/**
	 * Genere un graphe de reference aleatoire.
	 * Seul les noeuds sans transitions sortantes sont finaux.
	 * La Racine est representee differement des autres noeuds.
	 * @param path : chemin de creation de la base de donnees.
	 * @param nbNodes : nombre de noeuds a creer (environ).
	 * @param maxTrans : nombre de transitions sortantes maximum par noeud.
	 * @throws Exception 
	 */
	public void generateGrapheAleat(
			final String path, final int nbNodes, final int maxTrans)
					throws Exception {
		
		// noeuds et transitions crees
		List<NodeToCreate> nodes = new ArrayList<NodeToCreate>();
		List<Transition> transitions = new ArrayList<Transition>();
		
		// La racine
		NodeToCreate node = new NodeToCreate("Racine");
		nodes.add(node);
		
		// Creation du graphe (de maniere recursive)
		Quadruplet quadruplet = new Quadruplet();
		quadruplet.numNoeud = 1;
		quadruplet.numTransition = 0;
		quadruplet.nodesCreated = nodes;
		quadruplet.transitionsCreated = transitions;
		
		quadruplet = generateGraphAleat(
				quadruplet, node, nbNodes, maxTrans);
		
		// Desinner le graphe
		drawGraph(path, quadruplet.nodesCreated);
		
	}
	
	/**
	 * Genere un graphe de maniere aleatoire.
	 * Avec 1 chance sur 2 pour chaque noeud de continuer recursivement.
	 * Et 1 chance sur NB_CHANCE_CREER pour creer un nouveau noeud
	 * ou une nouvelle transition a chaque fois.
	 * 
	 * @param quadruplet Ensemble de quatres informations:
	 *                   numero du noeud courant,
	 *                   numero de la transition courante,
	 *                   liste des noeuds crees pour l'instant,
	 *                   liste des transitions creees pour l'instant.
	 * @param nodeFather Noeud de depart.
	 * @param maxNodes Nombre de noeuds maximum dans le graphe.
	 * @param maxTrans Nombre de transitions maximum dans le graphes.
	 * @return un quadruplet de meme information que le parametre d'entree.
	 */
	private Quadruplet generateGraphAleat(
			Quadruplet quadruplet, NodeToCreate nodeFather,
			final int maxNodes, final int maxTrans) {
		
		// Recuperation des informations
		int numNoeud = quadruplet.numNoeud;
		int numTransition = quadruplet.numTransition;
		List<NodeToCreate> nodesCreated = quadruplet.nodesCreated;
		List<Transition> transitionsCreated = quadruplet.transitionsCreated;
				
		// Determine le nombre de transitions sortants aleatoirement.
		int nbTrans;
		if (nodesCreated.size() == 1 || numNoeud < maxNodes) {
			nbTrans = new Random().nextInt(maxTrans) + 1;
		} else {
			nbTrans = new Random().nextInt(maxTrans + 1);
		}
		
		Quadruplet newQuadruplet = quadruplet;
		
		// Creation des transitions.
		for (int i = 0; i < nbTrans; i++) {
			
			NodeToCreate nodeOut = null;
			Transition transition = null;
			
			// Chargement des nouvelles valeurs du quadruplet
			numNoeud = newQuadruplet.numNoeud;
			numTransition = newQuadruplet.numTransition;
			nodesCreated = newQuadruplet.nodesCreated;
			transitionsCreated = newQuadruplet.transitionsCreated;
			
			// 1 chance sur NB_CHANCE_CREER de creer un nouveau noeud.
			boolean createNewNode =
					(new Random().nextInt(NB_CHANCE_CREER) == 0);
			
			// Creer un nouveau noeud si pas le choix.
			if (!createNewNode && nodesCreated.size() == 1) {
				createNewNode = true;
			}
			
			// Verifier les conditions
			if (numNoeud >= maxNodes) {
				createNewNode = false;
			}
			
			// Determiner le noeud de fin de transition
			if (!createNewNode) {
				nodeOut = nodeAleat(nodesCreated);
			} else {
				nodeOut = new NodeToCreate("Noeud " + (numNoeud++));
				nodesCreated.add(nodeOut);
			}
			
			// 1 chance sur NB_CHANCE_CREER de creer une nouvelle transition.
			boolean createNewTransition =
					(new Random().nextInt(NB_CHANCE_CREER) == 0);
			
			// Creer une nouvelle transition si pas le choix.
			if (!createNewTransition && transitionsCreated.size() == 0) {
				createNewTransition = true;
			}
			
			// Determiner la transition
			if (!createNewTransition) {
				transition = transitionAleat(transitionsCreated);
			} else {
				transition = new Transition("T " + (numTransition++));
				transitionsCreated.add(transition);
			}
			
			// Ajouter la transition au noeud pere
			nodeFather.addTransition(transition, nodeOut);
			
			// 4 chance sur 5 de faire un appel recursif
			boolean appelRecursif =
					(new Random().nextInt(5) >= 1);
			
			
			// Generation de mon nouveau quadruplet
			newQuadruplet = new Quadruplet();
			newQuadruplet.numNoeud = numNoeud;
			newQuadruplet.numTransition = numTransition;
			newQuadruplet.nodesCreated = nodesCreated;
			newQuadruplet.transitionsCreated = transitionsCreated;
			
			// Appel recursif si les conditions nous le permettent
			if (appelRecursif && numNoeud < maxNodes && createNewNode) {
				newQuadruplet = generateGraphAleat(newQuadruplet, nodeOut,
						maxNodes, maxTrans);
			}
		}
		
		return newQuadruplet;
	}
	
	/**
	 * Permet de recuperer un noeud aleatoire d'une liste.
	 * @param nodes Liste des noeuds a tirer au sort.
	 * @return Un noeud parmi la liste.
	 */
	private NodeToCreate nodeAleat(List<NodeToCreate> nodes) {
		int nombreAleat = new Random().nextInt(nodes.size());
		return nodes.get(nombreAleat);
	}
	
	/**
	 * permet de recuperer une transition aleatoire d'une liste.
	 * @param transitions Liste des transitions a tirer au sort.
	 * @return Une transition parmi la liste.
	 */
	private Transition transitionAleat(List<Transition> transitions) {
		int nombreAleat = new Random().nextInt(transitions.size());
		return transitions.get(nombreAleat);
	}
	
	/**
	 * Ensemble de quatre informations:
	 * Numero du noeud courant,
	 * Numero de la transition courante,
	 * Liste des noeuds crees pour le moment,
	 * Liste des transitions creees pour le moment.
	 */
	private class Quadruplet {
		public int numNoeud;
		public int numTransition;
		public List<NodeToCreate> nodesCreated;
		public List<Transition> transitionsCreated;
	}
	
	/**
	 * Dessine le graphe grace aux liste de noeuds et transitions passees
	 * en parametre. Stocke le graphe dans path.
	 * @param path Chemin du graphe.
	 * @param nodes Liste des noeuds a representer.
	 * @throws Exception 
	 */
	private void drawGraph(final String path,
			final List<NodeToCreate> nodesToCreate) throws Exception {
		
		// Liste des noeuds crees dans le graphe
		List<Node> nodesCreated = new ArrayList<Node>();
		
		// Creer (ou ouvre) le graphe grace au path.
		GraphDatabaseService graphDB =
				new GraphDatabaseFactory().newEmbeddedDatabase(path);
		registerShutdownHook(graphDB);
		
		// Parcours du graphe
		try (Transaction tx = graphDB.beginTx()) {
		
			// Ajout de chaque noeud
			for (NodeToCreate nodeToCreate: nodesToCreate) {
				
				Node node;
				
				// Creation du noeud si besoin
				if (!contains(nodesCreated, nodeToCreate)) {
					node = graphDB.createNode();
					nodesCreated.add(node);
				} else {
					node = retrieveNode(nodesCreated, nodeToCreate);
				}
				
				// Ajout du label
				if (nodeToCreate.getName().equals("Racine")) {
					node.addLabel(DynamicLabel.label("Racine"));
				} else {
					node.addLabel(DynamicLabel.label("Noeud"));
				}
				
				// Ajout du nom
				node.setProperty("name", nodeToCreate.getName());
				
				// Creation de ses transitions
				for (Entry<Transition, NodeToCreate> e:
					nodeToCreate.getTransitions().entrySet()) {
					
					Node nodeEnd = null;
					
					// Verifie s'il faut creer le noeud ou s'il a deja ete cree
					if (contains(nodesCreated, e.getValue())) {
						// Recuperation du noeud existant
						nodeEnd = retrieveNode(nodesCreated, e.getValue());
					} else {
						// Creation d'un nouveau noeud
						nodeEnd = graphDB.createNode();
						nodesCreated.add(nodeEnd);
						
						// Ajout du label
						if (e.getValue().getName().equals("Racine")) {
							nodeEnd.addLabel(DynamicLabel.label("Racine"));
						} else {
							nodeEnd.addLabel(DynamicLabel.label("Noeud"));
						}
						
						// Ajout du nom
						nodeEnd.setProperty("name", e.getValue().getName());
					}
					
					// Creation de la transition
					node.createRelationshipTo(nodeEnd, DynamicRelationshipType
							.withName(e.getKey().getName()));
				}
				
			}
			tx.success();
		}
		
		// Fermer le graphe.
		graphDB.shutdown();
	}
	
	/**
	 * Verifie l'existence du noeud a creer dans la liste des noeuds crees.
	 * @param nodesCreated Liste des noeuds deja crees.
	 * @param nodeToCreate Noeud a creer.
	 * @return Vrai si le noeud a creer a deja ete creer, faux sinon.
	 */
	private boolean contains(
			List<Node> nodesCreated, NodeToCreate nodeToCreate) {
		
		return retrieveNode(nodesCreated, nodeToCreate) != null;
	}
	
	/**
	 * Renvoie si possible l'instance du noeud deja cree a partir
	 * de la liste des noeuds deja crees et du noeud qu'on veut creer.
	 * @param nodesCreated Liste des noeuds deja crees.
	 * @param nodeToCreate Noeud a creer.
	 * @return L'instance du noeud s'il est contenu dans nodesCreated,
	 *         null sinon.
	 */
	private Node retrieveNode(
			List<Node> nodesCreated, NodeToCreate nodeToCreate) {
		
		for (Node n: nodesCreated) {
			if (n.getProperty("name").equals(nodeToCreate.getName())) {
				return n;
			}
		}
		return null;
	}
	
	/**
	 * Fermeture du graphe.
	 * @param graphDB Graphe a fermer.
	 */
	private void registerShutdownHook(
			final GraphDatabaseService graphDB) {
		
		Runtime.getRuntime().addShutdownHook(new Thread() {
			@Override
			public void run() {
				graphDB.shutdown();
			}
		});
	}
	
}
