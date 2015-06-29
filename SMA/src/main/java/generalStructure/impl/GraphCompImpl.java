package generalStructure.impl;

import jsonAndGS.MyJsonGenerator;

import org.graphstream.graph.Edge;
import org.graphstream.graph.Graph;
import org.graphstream.graph.implementations.MultiGraph;
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
import generalStructure.interfaces.IStop;
import generalStructure.interfaces.UpdateGraph;

public class GraphCompImpl extends GraphComp implements IGraph {

	private Graph _graphGS;
	private final GraphDatabaseService _graphNeo4J;

	public GraphCompImpl(String path) {
		Fichier.deleteFileOrDirectory(path);
		_graphNeo4J =
				new GraphDatabaseFactory().newEmbeddedDatabase(path);
		registerShutdownHook(_graphNeo4J);
		_graphGS = new MultiGraph("graph gs");
	}

	@Override
	protected IGraph make_graph() {
		return this;
	}

	@Override
	public void majStateAgent(String id, StateMemory memory) {
		//majStateAgentNeo4j(id, memory);
		majStateAgentGS(id, memory);
	}

	@Override
	public void majTransitionAgent(String id, TransMemory memory) {
		//majTransitionAgentNeo4j(id, memory);
		majTransitionAgentGS(id, memory);
	}
	
	
	
	// ------------------------ NEO4J --------------------------
	
	private void majStateAgentNeo4j(String id, StateMemory memory) {
		try (Transaction tx = _graphNeo4J.beginTx()) {

			Node node;
			
			if (memory == null) {
				
				// suicide
				node = findNodeById(id);
				node.delete();
				
			} else {
			
				// ------------------- maj de l'etat -----------------
	
				if ((node = findNodeById(id)) != null) {
					// Si le noeud existe alors le mettre a jour
	
					node.setProperty("source", memory.isRoot());
					node.setProperty("final", memory.isFinal());				
				} else {
					// Sinon il faut creer correctement le nouveau noeud
					node = _graphNeo4J.createNode();
	
					Label label = DynamicLabel.label(
							memory.isRoot() ? "Racine" : "Noeud");
					
					node.addLabel(label);
					node.setProperty("name", id);
					node.setProperty("ui.label", id);
					node.setProperty("ui.class",
							memory.isRoot() ? "Source" : "Node");
	
					node.setProperty("id", id);
					node.setProperty("Source", memory.isRoot());
					node.setProperty("Final", memory.isFinal());
				}
			
			}

			// ----------------------------------------------------
			tx.success();
		}
	}
	
	private void majTransitionAgentNeo4j(String id, TransMemory memory) {
		try (Transaction tx = _graphNeo4J.beginTx()) {

			// --------------- maj de la transition ---------------

			Relationship relationship;
			
			if (memory == null) {
				
				// suicide
				relationship = findRelationshipById(id);
				relationship.delete();
				
			} else {

				if ((relationship = findRelationshipById(id)) != null) {
	
					// si la transition existe alors la mettre a jour (2 cas)
					Node fatherNode = relationship.getStartNode();
					Node childNode = relationship.getEndNode();
	
					// son pere a change OU son fils a change
					if (!fatherNode.getProperty("id").
							equals(memory.getStateSourceId())
							|| !childNode.getProperty("id").
							equals(memory.getStateCibleId())) {
		
						try {
							relationship.delete(); // efface la transition actuelle
							createRelationship(id, memory); // redessine la transition
						} catch (Exception e) {
						}
	
					}
	
				} else {
					// Sinon la creer correctement SI LE NOEUD FILS A ETE CREE !
					createRelationship(id, memory);
				}
			
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
		System.out.println("Shutting down database");
	}

	@Override
	protected UpdateGraph make_updateGraph() {
		return new UpdateGraph() {
			@Override
			public Graph getGraph() {
				return GraphCompImpl.this._graphGS;
			}
		};
	}

	@Override
	public void closeGraph() {
		close();
	}

	private static void registerShutdownHook(final GraphDatabaseService graphDb) {
		Runtime.getRuntime().addShutdownHook(new Thread() {
			@Override
			public void run() {
				graphDb.shutdown();
			}
		});
	}
	
	
	
	// ------------------------ GraphStream -----------------------------
	
	
	private void majStateAgentGS(String id, StateMemory memory) {
		
		org.graphstream.graph.Node node;
		
		if (memory == null) {
			
			// suicide
			try {
				node = _graphGS.removeNode(id);
			} catch (Exception e) {
				
			}
			
		} else {
		
			// ------------------- maj de l'etat -----------------

			if ((node = _graphGS.getNode(id)) != null) {
				// Si le noeud existe alors le mettre a jour

				node.addAttribute(MyJsonGenerator.FORMAT_NODE_SOURCE, memory.isRoot());
				node.addAttribute(MyJsonGenerator.FORMAT_NODE_FINAL, memory.isFinal());				
			} else {
				// Sinon il faut creer correctement le nouveau noeud
				node = _graphGS.addNode(id);
				
				node.addAttribute("name", id);
				node.addAttribute("ui.label", id);
				node.addAttribute("ui.class",
						memory.isRoot() ? "Source" : "Node");

				node.addAttribute("id", id);
				node.addAttribute(MyJsonGenerator.FORMAT_NODE_SOURCE, memory.isRoot());
				node.addAttribute(MyJsonGenerator.FORMAT_NODE_FINAL, memory.isFinal());
			}
		
		}
	}
	
	private void majTransitionAgentGS(String id, TransMemory memory) {

		Edge edge;
		
		if (memory == null) {
			
			// suicide
			if (_graphGS.getEdge(id) != null) {
				_graphGS.removeEdge(id);
			}
			
		} else {

			if ((edge = _graphGS.getEdge(id)) != null) {

				// si la transition existe alors la mettre a jour (2 cas)
				org.graphstream.graph.Node fatherNode = edge.getNode0();
				org.graphstream.graph.Node childNode = edge.getNode1();

				// son pere a change OU son fils a change
				if (!fatherNode.getAttribute("id").
						equals(memory.getStateSourceId())
						|| !childNode.getAttribute("id").
						equals(memory.getStateCibleId())) {
	
					try {
						if (_graphGS.getEdge(id) != null) {
							_graphGS.removeEdge(id);
						}
						createEdge(id, memory); // redessine la transition
					} catch (Exception e) {
					}

				}

			} else {
				// Sinon la creer correctement SI LE NOEUD FILS A ETE CREE !
				createEdge(id, memory);
			}
		
		}
	}
	
	private Edge createEdge(String id, TransMemory memory) {

		org.graphstream.graph.Node fatherNode, childNode;
		Edge edge = null;

		// recupere les noeuds aux deux extremites de la transition
		fatherNode = _graphGS.getNode(memory.getStateSourceId());
		childNode = _graphGS.getNode(memory.getStateCibleId());

		if (childNode != null && fatherNode != null) {

			// creer physiquement la transition
			edge = _graphGS.addEdge(id, fatherNode.getId(), childNode.getId());

			// y ajoute les attributs
			edge.addAttribute("id", id);
			edge.addAttribute("name", id);
			edge.addAttribute("ui.label", id);
			edge.addAttribute("action", memory.getAction().
					getAction().getActionMap().get("action"));
		}

		return edge;
	}

}

