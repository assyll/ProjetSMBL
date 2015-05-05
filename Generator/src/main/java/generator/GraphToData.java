package generator;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.neo4j.graphdb.GraphDatabaseService;
import org.neo4j.graphdb.Node;
import org.neo4j.graphdb.Relationship;

public class GraphToData {
	
	public final static String nameIdAttribut = "name";
	public final static String nameStartNode = "Racine";

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
		Iterator<Node> nodeIterator = _graphDB.getAllNodes().iterator();
		
		while (nodeIterator.hasNext()) {
			
			node = nodeIterator.next();
			state = new State ((String) node.getProperty(nameIdAttribut));
			relationShipIterator = node.getRelationships().iterator();
			
			while (relationShipIterator.hasNext()) {
				state.addAction(new Action((String) relationShipIterator.
						next().getProperty(nameIdAttribut)));
			}
			
			if (state.getName() == nameStartNode) {
				_initState = state;
			}
			
			_states.add(state);
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
			for (int j = 0; j < maxActions ; j++) {
				
				Action actionAleat = state.getActionAleat();
				// TO BE CONTINUED !!
			}
		}
		
		return traces;
	}
	
}
