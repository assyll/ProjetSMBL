package generalStructure.impl;

import org.neo4j.graphdb.Direction;
import org.neo4j.graphdb.DynamicLabel;
import org.neo4j.graphdb.DynamicRelationshipType;
import org.neo4j.graphdb.GraphDatabaseService;
import org.neo4j.graphdb.Label;
import org.neo4j.graphdb.Node;
import org.neo4j.graphdb.Relationship;
import org.neo4j.graphdb.Transaction;
import org.neo4j.graphdb.factory.GraphDatabaseFactory;

import convertGraph.Fichier;
import agents.interfaces.StateMemory;
import agents.interfaces.TransMemory;
import general.GraphComp;
import generalStructure.interfaces.IGraph;

public class GraphCompImpl extends GraphComp implements IGraph {

	private final GraphDatabaseService _graphNeo4J;
	
	public GraphCompImpl(String path) {
		Fichier.deleteFileOrDirectory(path);
		_graphNeo4J =
				new GraphDatabaseFactory().newEmbeddedDatabase(path);
	}
	
	@Override
	protected IGraph make_graph() {
		return this;
	}

	@Override
	public void majStateAgent(String id, StateMemory memory) {
		
		try (Transaction tx = _graphNeo4J.beginTx()) {
			
			// ------------------- maj de l'etat ------------------
			
			// informations neo4j utiles
			Node node;
			Label label = DynamicLabel.label(
					memory.isRoot() ? "Racine" : "Noeud");
			
			if ((node = _graphNeo4J.findNode(label, "id", id)) != null) {
				// Si le noeud existe alors le mettre a jour
				
				node.setProperty("Source", memory.isRoot());
				node.setProperty("Final", memory.isFinal());				
			} else {
				// Sinon il faut creer correctement le nouveau noeud
				node = _graphNeo4J.createNode();
				
				node.addLabel(label);
				node.setProperty("name", id);
				node.setProperty("ui.label", id);
				node.setProperty("ui.class",
						memory.isRoot() ? "Source" : "Node");
				
				node.setProperty("id", id);
				node.setProperty("Source", memory.isRoot());
				node.setProperty("Final", memory.isFinal());
			}
			
			// ----------------------------------------------------
			tx.success();
		}
	}
	
	@Override
	public void majTransitionAgent(String id, TransMemory memory) {
				
		try (Transaction tx = _graphNeo4J.beginTx()) {
			
			// --------------- maj de la transition ---------------
			
			Relationship relationship;
			
			if ((relationship = findRelationshipById(id)) != null) {
				
				// si la transition existe alors la mettre a jour (2 cas)
				Node fatherNode = relationship.getStartNode();
				Node childNode = relationship.getEndNode();
				
				// son pere a change OU son fils a change
				if (!fatherNode.getProperty("id").
						equals(memory.getStateSourceId())
						|| !childNode.getProperty("id").
						equals(memory.getStateCibleId())) {
					
					relationship.delete(); // efface la transition actuelle
					createRelationship(id, memory); // redessine la transition
				}
				
			} else {
				// Sinon la creer correctement SI LE NOEUD FILS A ETE CREE !
				createRelationship(id, memory);
			}
			
			// ----------------------------------------------------
			tx.success();
		}
	}
	
	private Relationship createRelationship(String id, TransMemory memory) {
		
		Node fatherNode, childNode;
		Relationship relationship = null;
		
		childNode = findNodeById(memory.getStateCibleId());
		
		if (childNode != null) {
						
			// recupere les noeuds aux deux extremites de la transition
			fatherNode = findNodeById(memory.getStateSourceId());
			
			// creer physiquement la transition
			relationship = fatherNode.createRelationshipTo(childNode,
					DynamicRelationshipType.withName("Transition"));
			
			// y ajoute les attributs
			relationship.setProperty("id", id);
			relationship.setProperty("name", id);
			relationship.setProperty("ui.label", id);
			relationship.setProperty("action", memory.getAction().
					getAction().getActionMap().get("action"));
		}
		
		return relationship;
		
	}
	
	private Relationship findRelationshipById(String id) {
		for (Node node: _graphNeo4J.getAllNodes()) {
			for (Relationship relationship:
				node.getRelationships(Direction.OUTGOING)) {
				if (relationship.getProperty("id").equals(id)) {
					return relationship;
				}
			}
		}
		return null;
	}
	
	private Node findNodeById(String id) {
		for (Node node: _graphNeo4J.getAllNodes()) {
			if (node.getProperty("id").equals(id)) {
				return node;
			}
		}
		return null;
	}
	
	public void close() {
		_graphNeo4J.shutdown();
	}

}
