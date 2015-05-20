package convertGraph;

import java.io.File;
import java.util.HashMap;
import java.util.Map;

import org.graphstream.graph.Edge;
import org.graphstream.graph.Graph;
import org.neo4j.graphdb.DynamicLabel;
import org.neo4j.graphdb.DynamicRelationshipType;
import org.neo4j.graphdb.GraphDatabaseService;
import org.neo4j.graphdb.Node;
import org.neo4j.graphdb.Relationship;
import org.neo4j.graphdb.Transaction;
import org.neo4j.graphdb.factory.GraphDatabaseFactory;

public class ConvertGStoNeo4j {
	
	public final static String _pathDestinationNeo4j =
			"/home/quentin/Documents/Mes_graphes/graphToGS";
	
	private final Graph _graphGS;
	
	public ConvertGStoNeo4j(Graph graphGS) {
		_graphGS = graphGS;
	}
	
	public void convertToNeo4j(String pathDestination) {
		
		// Correspondance des noeuds graphStream aux noeuds Neo4J
		Map<org.graphstream.graph.Node, Node> mapNodes = 
				new HashMap<org.graphstream.graph.Node, Node>();
		
		// Supprimer le graphe s'il existe deja
		File dir = new File(pathDestination);
		if (dir.isDirectory()) {
			Fichier.deleteFileOrDirectory(dir);
		}
		
		// Creer le graphe grace au path.
		GraphDatabaseService graphNeo4j = new GraphDatabaseFactory().
				newEmbeddedDatabase(pathDestination);
		registerShutdownHook(graphNeo4j);
		
		try (Transaction tx = graphNeo4j.beginTx()) {
			
			// Parcours des noeuds
			for (org.graphstream.graph.Node nodeGS : _graphGS.getEachNode()) {
				
				// Creation du noeud
				Node nodeNeo4j = graphNeo4j.createNode();
				
				// Ajout du label
				Object labelNodeGS = nodeGS.getAttribute("Source");
				boolean isRoot = (boolean) (labelNodeGS instanceof Boolean
						? nodeGS.getAttribute("Source")
								: Boolean.parseBoolean((String) labelNodeGS));
				
				nodeNeo4j.addLabel(DynamicLabel.label(
						isRoot ? "Racine" : "Noeud"));
				
				// Ajout des proprietes
				nodeNeo4j.setProperty("name", nodeGS.getId());
				for (String attributeKey : nodeGS.getAttributeKeySet()) {
					nodeNeo4j.setProperty(
							attributeKey, nodeGS.getAttribute(attributeKey));
				}
				
				// Ajout de la correspondance dans la map
				mapNodes.put(nodeGS, nodeNeo4j);
			}

			// Parcours des transitions
			for (Edge edgeGS : _graphGS.getEachEdge()) {
				
				// Recuperation du noeud start
				Node nodeStart = mapNodes.get(edgeGS.getNode0());
				
				// Recuperation du noeud end
				Node nodeEnd = mapNodes.get(edgeGS.getNode1());
				
				// Creation de la transition
				Relationship relationshipNeo4j = nodeStart.
						createRelationshipTo(nodeEnd,
								DynamicRelationshipType.
								withName("Transition"));
				
				// Ajout des proprietes
				relationshipNeo4j.setProperty("name", edgeGS.getId());
				for (String attributeKey : edgeGS.getAttributeKeySet()) {
					relationshipNeo4j.setProperty(
							attributeKey, edgeGS.getAttribute(attributeKey));
				}
			}
			
			tx.success();
		}
		
		graphNeo4j.shutdown();
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
