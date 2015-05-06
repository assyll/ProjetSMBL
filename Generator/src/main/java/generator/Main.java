package generator;

import java.io.IOException;
import java.text.ParseException;
import java.util.List;

import org.neo4j.graphdb.GraphDatabaseService;
import org.neo4j.graphdb.factory.GraphDatabaseFactory;

import com.fasterxml.jackson.core.JsonParseException;

public class Main {

	private static final int _nbTraces = 10;
	private static final int _maxActions = 1;
	
	private static final String _DB_PATH = "target/graphe1-db"; //Chemin BDD
	private static GraphDatabaseService _graphDb;

	public static void main(String[] args) throws
		JsonParseException, IOException, ParseException {
		
		createDb();

		GraphToData graphToData = new GraphToData(_graphDb);
		List<Trace> traces = graphToData.traceGenerate(_nbTraces, _maxActions);
		
		if (traces.size() == 0) {
			System.out.println("Aucune possibilite");
		}
		
		for (Trace t: traces) {
			System.out.println(t);
		}
		
		shutDown();

	}

	static void createDb() throws
		JsonParseException, IOException, ParseException {
		
		_graphDb = new GraphDatabaseFactory().newEmbeddedDatabase(_DB_PATH);
		registerShutdownHook(_graphDb);
	}

	static void shutDown() {
		_graphDb.shutdown();
	}

	private static void registerShutdownHook(
			final GraphDatabaseService graphDb) {
		
		Runtime.getRuntime().addShutdownHook(new Thread() {
			@Override
			public void run() {
				graphDb.shutdown();
			}
		});
	}

}
