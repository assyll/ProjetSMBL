package convertGraph;

import static org.junit.Assert.*;

import org.graphstream.graph.Graph;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.neo4j.graphdb.GraphDatabaseService;
import org.neo4j.graphdb.factory.GraphDatabaseFactory;

public class ConvertNeo4jToGSTest {

	private ConvertNeo4jToGS converter;
	private final String _pathNeo4j =
			"/home/quentin/Documents/Mes_graphes/grapheGStoNeo4j";
	
	@Before
	public void start() {
		converter = new ConvertNeo4jToGS(_pathNeo4j);
	}
	
	@After
	public void stop() {
		converter = null;
	}
	
	@Test
	public void testConvertToGS() {
		Graph grapheGS = converter.convertToGS();
		GraphDatabaseService grapheNeo4j =
				new GraphDatabaseFactory().newEmbeddedDatabase(_pathNeo4j);
		
		// Verification
		assertTrue(VerifyGraphNeo4jAndGS.verify(grapheNeo4j, grapheGS));
	}
	
}
