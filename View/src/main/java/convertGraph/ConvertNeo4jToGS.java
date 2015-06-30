package convertGraph;

import generatorTracesTest.GeneratorTraces;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.graphstream.graph.Edge;
import org.graphstream.graph.Graph;
import org.graphstream.graph.Node;
import org.graphstream.graph.implementations.MultiGraph;
import org.neo4j.graphdb.Direction;
import org.neo4j.graphdb.GraphDatabaseService;
import org.neo4j.graphdb.Relationship;
import org.neo4j.graphdb.Transaction;
import org.neo4j.graphdb.factory.GraphDatabaseFactory;

public class ConvertNeo4jToGS {
	
	private GraphDatabaseService _graphNeo4j;
	
	public ConvertNeo4jToGS(String pathGraphNeo4j) {
		try {
			_graphNeo4j = new GraphDatabaseFactory().
					newEmbeddedDatabase(pathGraphNeo4j);
		} catch (Exception e) {
			_graphNeo4j = null;
		}
	}
	
	public Graph convertToGS() {
		
		// Liste des relationship
		List<Relationship> relationships = new ArrayList<Relationship>();
		
		// Graphe "graphStream"
		Graph graphGS = new MultiGraph("graph2");
		
		// Parcours du graphe Neo4j
		try (Transaction tx = _graphNeo4j.beginTx()) {
			
			@SuppressWarnings({ "deprecation", "unused" })
			Iterator<org.neo4j.graphdb.Node> iteratorNodesNeo4j =
					_graphNeo4j.getAllNodes().iterator();
			
			// Parcours des noeuds
			while (iteratorNodesNeo4j.hasNext()) {
				
				org.neo4j.graphdb.Node nodeNeo4j = iteratorNodesNeo4j.next();
				
				// Creation du noeud
				Node nodeGS = graphGS.addNode(
						(String) nodeNeo4j.getProperty("name"));
				
				// Ajout des attributs
				Iterator<String> iteratorProperties =
						nodeNeo4j.getPropertyKeys().iterator();
				
				while (iteratorProperties.hasNext()) {
					String propertyKey = iteratorProperties.next();
					nodeGS.addAttribute(
							propertyKey, nodeNeo4j.getProperty(propertyKey));
				}
				
				// Ajout de l'attribut source
				boolean isSource = nodeNeo4j.
						getLabels().iterator().next().name().equals("Racine");
				nodeGS.addAttribute(GeneratorTraces._sourceAttribut, isSource ? "true" : "false");
				
				// Enregistrement des relationship du noeud
				Iterator<Relationship> iteratorRelationship =
						nodeNeo4j.getRelationships(Direction.OUTGOING)
						.iterator();
				while (iteratorRelationship.hasNext()) {
					relationships.add(iteratorRelationship.next());
				}
			}
			
			// Parcours des transitions
			for (Relationship relationship: relationships) {
				
				// Creation de la edge
				String idEdge = (String) relationship.getProperty("name");
				String idNodeStart = (String) relationship.
						getStartNode().getProperty("name");
				String idNodeEnd = (String) relationship.
						getEndNode().getProperty("name");
				Edge edgeGS = graphGS.
						addEdge(idEdge, idNodeStart, idNodeEnd, true);
				
				// Ajout des attributs
				Iterator<String> iteratorPropertiesKey =
						relationship.getPropertyKeys().iterator();
				
				while (iteratorPropertiesKey.hasNext()) {
					String propertyKey = iteratorPropertiesKey.next();
					edgeGS.addAttribute(propertyKey,
							relationship.getProperty(propertyKey));
				}
				
			}
			
			tx.success();
			
		} catch (Exception e) {
			return null;
		}
		
		_graphNeo4j.shutdown();
		
		return graphGS;	
	}
	
}
