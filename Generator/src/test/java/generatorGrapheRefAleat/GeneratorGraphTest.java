package generatorGrapheRefAleat;

import org.junit.Test;

public class GeneratorGraphTest {

	private final String _path =
			"/home/quentin/Documents/Mes_graphes/grapheAleat-db";
	
	private final int _nbNodes = 10;
	private final int _maxTrans = 3;
	
	@Test
	public void testGenerateGrapheAleat() throws Exception {

		GeneratorGraph generator = new GeneratorGraph();
		
		System.out.println(""
				+ "Graphe: en cours de construction"
				+ "(" + _nbNodes + " noeuds, " + _maxTrans
				+ " transitions max / noeud) ...");
		
		generator.generateGrapheAleat(_path, _nbNodes, _maxTrans);
		
		System.out.println("Graphe: cree (" + _path + ")");
	}
	
}
