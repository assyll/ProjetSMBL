package generator;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

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
		Iterator<Relationship> relationShipIterator;
		
		Iterator<Node> nodeIterator =  null;
		try (Transaction tx = _graphDB.beginTx()) {
			nodeIterator = _graphDB.getAllNodes().iterator();	
		    tx.success();
		}
		
		System.out.println("hasNext = " + nodeIterator.hasNext());
		
		while (nodeIterator.hasNext()) {
			
			node = nodeIterator.next();
			state = new State ((String) node.getProperty(_nameIdAttribut));
			relationShipIterator = node.getRelationships().iterator();
			
			_states.add(state);
			
			while (relationShipIterator.hasNext()) {
				
				Relationship relationShip = relationShipIterator.next();
				
				Action action = new Action((String) relationShip.
						getProperty(_nameIdAttribut));
				
				State stateAfterAction = new State ((String) (relationShip.
						getEndNode().getProperty(_nameIdAttribut)));
				
				if (_states.contains(stateAfterAction)) {
					stateAfterAction = _states.
							get(_states.indexOf(stateAfterAction));
				}
				
				state.addAction(action, stateAfterAction);
			}
						
			if (state.getName() == _nameStartNode) {
				_initState = state;
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
			
			boolean finished = false;
			State state = _initState;
			Trace trace = new Trace();
			
			for (int j = 0; j < maxActions && !finished; j++) {
				Action actionAleat = state.getActionAleat();
				state = state.executeAction(actionAleat);
				finished = (actionAleat == null);
				trace.addAction(actionAleat);
			}
			
			traces.add(trace);
		}
		
		return traces;
	}
	
}
