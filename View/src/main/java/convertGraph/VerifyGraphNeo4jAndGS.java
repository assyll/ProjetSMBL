package convertGraph;

import java.util.Iterator;

import org.graphstream.graph.Graph;
import org.neo4j.graphdb.GraphDatabaseService;
import org.neo4j.graphdb.Relationship;
import org.neo4j.graphdb.Transaction;

public class VerifyGraphNeo4jAndGS {

	public static boolean verify(
			GraphDatabaseService graphNeo4j, Graph graphGS) {
		
		try (Transaction tx = graphNeo4j.beginTx()) {
			
			Iterator<org.neo4j.graphdb.Node> itNodesNeo4j =
					graphNeo4j.getAllNodes().iterator();
			
			while (itNodesNeo4j.hasNext()) {
				if (!containsNode(itNodesNeo4j.next(), graphGS)) {
					return false;
				}
			}
			
			tx.success();
		}
		
		graphNeo4j.shutdown();
		
		return true;
	}
	
	private static boolean containsNode(
			org.neo4j.graphdb.Node nodeNeo4j, Graph grapheGS) {
		
		org.graphstream.graph.Node nodeGSCorrespondant = null;
		
		for (org.graphstream.graph.Node nodeGS: grapheGS.getEachNode()) {
			
			// Repere le noeud correspondant
			if (nodeGS.getId().equals(nodeNeo4j.getProperty("name"))) {
				
				nodeGSCorrespondant = nodeGS; // noeud repere !
				// Verifie legalite de tous ses attributs
				for (String attributeKey: nodeGS.getAttributeKeySet()) {
					
					if (!nodeNeo4j.getProperty(attributeKey).
							equals(nodeGS.getAttribute(attributeKey))) {
						return false;
					}
				}
				
				// Verifie legalie de toutes ses transitions sortantes
				for (Relationship relationship: nodeNeo4j.getRelationships()) {
					
					if (grapheGS.getEdge((String)
							relationship.getProperty("name")) == null) {
						return false;
					}
				}
			}
		}
		
		return nodeGSCorrespondant != null;
	}
	
}
