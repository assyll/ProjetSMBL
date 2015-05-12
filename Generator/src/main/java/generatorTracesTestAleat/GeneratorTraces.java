package generatorTracesTestAleat;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.neo4j.graphdb.Direction;
import org.neo4j.graphdb.GraphDatabaseService;
import org.neo4j.graphdb.Node;
import org.neo4j.graphdb.Relationship;
import org.neo4j.graphdb.Transaction;
import org.neo4j.graphdb.factory.GraphDatabaseFactory;

public class GeneratorTraces {
	
	public final static String _nameIdAttribut = "name";
	public final static String _nameStartNode = "Racine";

	private String _DB_PATH;
	private State _initState;
	private List<State> _states;
	private GraphDatabaseService _graphDB;
	
	public GeneratorTraces(String db_Path) throws Exception {
		
		_DB_PATH = db_Path;
		_states = new ArrayList<State>();
		
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
		
		deleteInformations();
		createDb();
		
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
										
					Action action = new Action((String) relationShip.
							getProperty(_nameIdAttribut));
					
					isFinal = ! relationShip.getEndNode().getRelationships
							(Direction.OUTGOING).iterator().hasNext();
					State stateAfterAction = new State ((String) (relationShip.
							getEndNode().getProperty(_nameIdAttribut)), isFinal);
					
					if (_states.contains(stateAfterAction)) {
						stateAfterAction = _states.
								get(_states.indexOf(stateAfterAction));
					} else {
						_states.add(stateAfterAction);
					}
									
					state.addAction(action, stateAfterAction);
					
				}
										
				if (state.getName().equals(_nameStartNode)) {
					_initState = state;
				}
			}
			tx.success();
		}
		shutDown();
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
			
			if ((state == null || !stopToFinal ||
					(state.isFinal() /*&& stopToFinal*/))
					&& (withRepetition ||
							(!traces.contains(trace) /*&& !withRepetition*/))) {
				traces.add(trace);
			}
		}
		
		return traces;
	}
	
	/**
	 * Generateur de traces de facon a remplir un certain pourcentage
	 * de recouvrement du graphe.
	 * @param stopToFinal booleen avertissant si la liste des traces
	 *                       retournee peut contenir des doublons ou pas.
	 * @param percentToCover pourcentage du graphe a couvrir
	 * @return la liste des traces
	 */
	public List<Trace> traceGenerateCoverage(
			boolean stopToFinal, float percentToCover) {
		
		float percent = 0.0f;
		List<Trace> traces = new ArrayList<Trace>();
		List<State> statesVisited = new ArrayList<State>();

		for (; percent < percentToCover; ) {
			
			State state = _initState;
			Trace trace = new Trace();
			
			if (!statesVisited.contains(state)) {
				statesVisited.add(state);
			}
						
			while (state != null) {
				Action actionIntelligente =
						state.getActionIntelligent(statesVisited);
				state = state.executeAction(actionIntelligente);
				trace.addAction(actionIntelligente);
				
				if (!statesVisited.contains(state)) {
					statesVisited.add(state);
				}
			}
			
			if ((state == null || !stopToFinal || state.isFinal())
					&& !traces.contains(trace)) {
				traces.add(trace);
			}
			
			percent = (float) statesVisited.size() / (float) _states.size();
			System.out.println(statesVisited.size()+"/"+_states.size()+"="+percent);
		}
		
		System.out.println("pourcentage couverture = "
				+ (percent * 100) + "%");
		
		return traces;
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
