package generatorTracesTestAleat;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.neo4j.graphdb.Direction;
import org.neo4j.graphdb.DynamicLabel;
import org.neo4j.graphdb.DynamicRelationshipType;
import org.neo4j.graphdb.GraphDatabaseService;
import org.neo4j.graphdb.Node;
import org.neo4j.graphdb.Path;
import org.neo4j.graphdb.Relationship;
import org.neo4j.graphdb.Transaction;
import org.neo4j.graphdb.factory.GraphDatabaseFactory;
import org.neo4j.graphdb.traversal.Evaluators;
import org.neo4j.graphdb.traversal.Uniqueness;
import org.neo4j.graphdb.traversal.UniquenessFactory;

public class GeneratorTraces {
	
	public final static String _nameIdAttribut = "name";
	public final static String _nameStartNode = "Racine";

	private String _DB_PATH;
	private State _initState;
	private List<State> _states;
	private List<Action> _actions;
	private GraphDatabaseService _graphDB;
	
	public GeneratorTraces(String db_Path) throws Exception {
		
		_DB_PATH = db_Path;
		_states = new ArrayList<State>();
		_actions = new ArrayList<Action>();
		
		dataRegister();
	}
	
	/**
	 * Public pour tester.
	 */
	public void deleteInformations() {
		_initState = null;
		_states = new ArrayList<State>();
	}
	
	/**
	 * Public pour tester.
	 */
	public void dataRegister() throws Exception{
		
		System.out.println("Ouverture du graphe ...");
		
		deleteInformations();
		createDb();
		System.out.println("Graphe ouvert.");
		System.out.println("Recuperation des infos du graphe ...");
		
		Node node;
		State state;
		boolean isFinal;
		Iterator<Relationship> relationShipIterator;
		
		Iterator<Node> nodeIterator =  null;
		try (Transaction tx = _graphDB.beginTx()) {
			
			nodeIterator = _graphDB.getAllNodes().iterator();	

			while (nodeIterator.hasNext()) {
				
				node = nodeIterator.next();
				
				isFinal = ! node.getRelationships(Direction.OUTGOING).
						iterator().hasNext();
				
				state = new State ((String) node.
						getProperty(_nameIdAttribut), isFinal);
				
				if (_states.contains(state)) {
					state = _states.
							get(_states.indexOf(state));
				} else {
					_states.add(state);
				}
				
				relationShipIterator = node.
						getRelationships(Direction.OUTGOING).iterator();
				
				while (relationShipIterator.hasNext()) {

					Relationship relationShip = relationShipIterator.next();
					
					isFinal = ! relationShip.getEndNode().getRelationships
							(Direction.OUTGOING).iterator().hasNext();
					State stateAfterAction = new State ((String) (relationShip.
							getEndNode().getProperty(_nameIdAttribut)),
							isFinal);
					
					if (_states.contains(stateAfterAction)) {
						stateAfterAction = _states.
								get(_states.indexOf(stateAfterAction));
					} else {
						_states.add(stateAfterAction);
					}
					
					Action action = new Action((String) relationShip.
							getProperty(_nameIdAttribut),
							state, stateAfterAction);
					
					if (!_actions.contains(action)) {
						_actions.add(action);
					}
									
					state.addAction(action, stateAfterAction);
					
				}
										
				if (state.getName().equals(_nameStartNode)) {
					_initState = state;
				}
			}
			tx.success();
		}
		
		System.out.println("Informations enregistrees");
		System.out.println("Fermeture du graphe ...");
		shutDown();
		System.out.println("Graphe ferme");
	}
	
	/**
	 * Generateur de traces de facon aleatoire.
	 * @param nbTraces nombre de traces a generer
	 * @param maxActions nombre d'actions maxi par trace
	 * @param stopToFinal booleen avertissant si un trace peut sarreter ou pas
	 *                    sur un etat final.
	 * @param withRepetition booleen avertissant si la liste des traces
	 *                       retournee peut contenir des doublons ou pas.
	 * @return la liste des traces
	 */
	public List<Trace> traceGenerateAleat(int nbTraces, int maxActions,
			boolean stopToFinal, boolean withRepetition) {
		
		List<Trace> traces = new ArrayList<Trace>();

		for (int i = 0; i < nbTraces; i++) {
			
			State state = _initState;
			Trace trace = new Trace();
						
			for (int j = 0; j < maxActions && state != null; j++) {
				Action actionAleat = state.getActionAleat();
				state = state.executeAction(actionAleat);
				trace.addAction(actionAleat);
			}
			
			if ((state == null || !stopToFinal || state.isFinal())
					&& (withRepetition || !traces.contains(trace))) {
				traces.add(trace);
			}
		}
		
		return traces;
	}
	
	/**
	 * Generateur de traces de facon a remplir un certain pourcentage
	 * de recouvrement du graphe de maniere aleatoire.
	 * @param percentToCover pourcentage du graphe a couvrir
	 * @return la liste des traces
	 */
	public List<Trace> traceGenerateCoverageAleat(float percentToCover) {
		
		System.out.println("Debut generation tests trace");
		
		float percent = 0.0f;
		List<Trace> traces = new ArrayList<Trace>();
		
		while (percent < percentToCover) {
			
			State state = _initState;
			Trace trace = new Trace();
						
			while (state != null) {
					
					Action actionAleat =
							state.getActionAleat();
					state = state.executeAction(actionAleat);
					
					if (actionAleat != null) {
						trace.addAction(actionAleat);
						actionAleat.setVisited(true);
					}
			}
			
			if (!traces.contains(trace)) {
				traces.add(trace);
			}

			percent = (float) nbActionsVisited() / (float) _actions.size();
		}
		
		System.out.println("pourcentage couverture = "
				+ (percent * 100) + "%");
		
		return traces;
	}
	
	public List<Trace> traceGenerateCoverageIntelligent(int maxActions)
			throws Exception {
		
		List<Trace> traces = new ArrayList<Trace>();
		createDb();
		
		try (Transaction tx = _graphDB.beginTx()) {
			
			Node racine = _graphDB.findNode(
					DynamicLabel.label("Racine"), "name", "Racine");
			
			for ( Path position : _graphDB.traversalDescription()
			        .depthFirst()
			        .relationships(
			        		DynamicRelationshipType.withName("Transition")
			        		, Direction.OUTGOING)
			        .evaluator( Evaluators.toDepth( maxActions ) )
			        .uniqueness(Uniqueness.NONE)
			        .traverse(racine))  {
				
				Node endNode = position.endNode();
				
				// Recuperation de la trace
				Trace trace;
				if (!endNode.hasRelationship(Direction.OUTGOING)) {
					if (!traces.contains(trace = convertToTrace(position)))
					traces.add(trace);
				}
			}
			
			tx.success();
		}
		
		return traces;
	}
	
	private Trace convertToTrace(Path chemin) {
		Trace trace = new Trace();
		Iterator<Relationship> it = chemin.relationships().iterator();
		
		while (it.hasNext()) {
			Relationship relationship = it.next();
			State stateStart = new State((String) relationship.
					getStartNode().getProperty(_nameIdAttribut), false);
			State stateEnd = new State((String) relationship.
					getEndNode().getProperty(_nameIdAttribut), !relationship.
					getEndNode().hasRelationship(Direction.OUTGOING));
			trace.addAction(new Action((String) relationship.
					getProperty(_nameIdAttribut), stateStart, stateEnd));
		}
		
		return trace;
	}
	
	private int nbActionsVisited() {
		int nb = 0;
		for (Action action: _actions) {
			if (action.isVisited()) {
				nb++;
			}
		}
		return nb;
	}
	
	/**
	 * Uniquement pour tester.
	 * @return la liste des etats
	 */
	public List<State> getStates() {
		return _states;
	}
	
	/**
	 * Uniquement pour tester.
	 * @return l'etat initial
	 */
	public State getInitState() {
		return _initState;
	}
	
	private void createDb() throws Exception {
	
		_graphDB = new GraphDatabaseFactory().newEmbeddedDatabase(_DB_PATH);
		registerShutdownHook(_graphDB);
	}

	private void shutDown() {
		_graphDB.shutdown();
	}

	private void registerShutdownHook(
			final GraphDatabaseService graphDb) {
		
		Runtime.getRuntime().addShutdownHook(new Thread() {
			@Override
			public void run() {
				graphDb.shutdown();
			}
		});
	}
	
}
