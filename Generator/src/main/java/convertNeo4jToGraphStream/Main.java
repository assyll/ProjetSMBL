package convertNeo4jToGraphStream;

import org.graphstream.graph.Graph;
import org.graphstream.graph.implementations.AdjacencyListGraph;

import convertNeo4jToGraphStream.DatabaseProxy.Mode;

public class Main {

	public final static String _pathNeo4j =
			"/home/quentin/Documents/Mes_graphes/graphe1-db";
	
	public static void main(String[] args) throws Exception {
		Neo4JProxy src = new Neo4JProxy();
		Graph g = new AdjacencyListGraph("g");

		g.addAttribute("ui.quality");
		g.addAttribute("ui.antialias");
		g.addAttribute("ui.stylesheet", "node { fill-color: rgba(38, 38, 38, 100); } edge { fill-color: rgba(38, 38, 38, 50); }");

		src.addSink(g);

		g.display(true);

		src.connect(_pathNeo4j, Mode.READ_ONLY);

		src.disconnect();
	}
	
}
