package generatorGrapheRefAleat;

public class Main {

	private final static String _path =
			"/home/quentin/Documents/Mes_graphes/grapheAleat-db";
	
	public static void main(String[] args) throws Exception {

		GeneratorGraph generator = new GeneratorGraph();
		
		System.out.println("Graphe: en cours de construction ...");
		
		generator.generateGrapheAleat(_path, 10, 5);
		
		System.out.println("Graphe: cree (" + _path + ")");
	}

}
