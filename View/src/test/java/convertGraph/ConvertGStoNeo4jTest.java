package convertGraph;

import static org.junit.Assert.*;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.neo4j.graphdb.GraphDatabaseService;
import org.neo4j.graphdb.factory.GraphDatabaseFactory;
import org.graphstream.graph.Edge;
import org.graphstream.graph.Graph;
import org.graphstream.graph.implementations.MultiGraph;

public class ConvertGStoNeo4jTest {

	private Graph _grapheGS;
	private final String _pathNeo4j =
			"/home/quentin/Documents/Mes_graphes/grapheGStoNeo4j";
	
	@Before
	public void start() {
		_grapheGS = new MultiGraph("grapheGS");
		
		// Ajout de quelques noeuds
		org.graphstream.graph.Node node;
		
		node = _grapheGS.addNode("Racine");
		node.addAttribute("Source", "true");
		node.addAttribute("Final", "false");
		
		node = _grapheGS.addNode("Noeud1");
		node.addAttribute("Source", "false");
		node.addAttribute("Final", "false");
		
		node = _grapheGS.addNode("Noeud2");
		node.addAttribute("Source", "false");
		node.addAttribute("Final", "false");
		
		node = _grapheGS.addNode("Noeud3");
		node.addAttribute("Source", "false");
		node.addAttribute("Final", "true");
		
		// Ajout de quelques transitions
		Edge edge;
		
		edge = _grapheGS.addEdge("T1", "Racine", "Noeud1", true);
		edge.addAttribute("action", "indexAaccueil");
		
		edge = _grapheGS.addEdge("T2", "Noeud1", "Noeud2", true);
		edge.addAttribute("action", "accueilAformulaire");
		
		edge = _grapheGS.addEdge("T3", "Noeud2", "Noeud1", true);
		edge.addAttribute("action", "forumulaireAaccueil");
		
		edge = _grapheGS.addEdge("T4", "Noeud2", "Noeud3", true);
		edge.addAttribute("action", "formulaireAfin");
	}
	
	@After
	public void stop() {
		_grapheGS = null;
	}
	
	@Test
	public void testConvertToNeo4j() {
		ConvertGStoNeo4j converter = new ConvertGStoNeo4j(_grapheGS);
		converter.convertToNeo4j(_pathNeo4j);
		
		GraphDatabaseService graphNeo4j =
				new GraphDatabaseFactory().newEmbeddedDatabase(_pathNeo4j);
		
		assertTrue(VerifyGraphNeo4jAndGS.verify(graphNeo4j, _grapheGS));
	}
	
}
