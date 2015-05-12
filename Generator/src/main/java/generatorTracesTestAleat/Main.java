package generatorTracesTestAleat;

import java.util.List;

public class Main {

	public static void main(String[] args) throws Exception {

		GeneratorTraces generator = new GeneratorTraces(
				"/home/quentin/Documents/Mes_graphes/graphe1-db");
		
		List<Trace> traces = generator.traceGenerateCoverage(false, 0.5f);
		
		for (Trace t: traces) {
			System.out.println(t);
		}
		
	}

}
