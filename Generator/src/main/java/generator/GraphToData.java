package generator;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.neo4j.graphdb.Direction;
import org.neo4j.graphdb.GraphDatabaseService;
import org.neo4j.graphdb.Node;
import org.neo4j.graphdb.Relationship;
import org.neo4j.graphdb.Transaction;

public class GraphToData {
	
	public final static String _nameIdAttribut = "name";
	public final static String _nameStartNode = "Racine";

	private State _initState;
	private List<State> _states;
	private GraphDatabaseService _graphDB;
	
	public GraphToData(GraphDatabaseService graphDB) {
		_graphDB = graphDB;
		_states = new ArrayList<State>();
		
		dataRegister();
	}
	
	private void dataRegister() {
		
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
				
			    tx.success();
			}
		}
	}
	
	/**
	 * Generator de traces.
	 * @param nbTraces nombre de traces a generer
	 * @param maxActions nombre d'actions maxi par trace
	 * @return la liste des traces
	 */
	public List<Trace> traceGenerate(int nbTraces, int maxActions) {
				
		List<Trace> traces = new ArrayList<Trace>();
		
		for (int i = 0; i < nbTraces; i++) {
			
			State state = _initState;
			Trace trace = new Trace();
			
			for (int j = 0; j < maxActions && state != null; j++) {	
				Action actionAleat = state.getActionAleat();
				state = state.executeAction(actionAleat);
				trace.addAction(actionAleat);
			}
			
			if (state == null || state.isFinal()) {
				traces.add(trace);
			}
		}
		
		return traces;
	}
	
}
