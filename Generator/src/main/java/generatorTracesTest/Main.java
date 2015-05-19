package generatorTracesTest;

import java.util.List;

public class Main {

	public static void main(String[] args) throws Exception {

		GeneratorTraces generator = new GeneratorTraces(
				"/home/quentin/Documents/Mes_graphes/graphe1-db");
		
		List<Trace> traces;
		
		//traces = generator.traceGenerateAleat(10, 10, true, false);
		//traces = generator.traceGenerateCoverageAleat(0.2f);
		traces = generator.traceGenerateCoverageIntelligent(10);
		
		for (Trace t: traces) {
			System.out.println(t);
		}
		
	}

}
