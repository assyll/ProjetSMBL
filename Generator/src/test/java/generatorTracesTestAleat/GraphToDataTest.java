package generatorTracesTestAleat;
import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.List;

import generatorTracesTestAleat.Action;
import generatorTracesTestAleat.GeneratorTraces;
import generatorTracesTestAleat.State;
import generatorTracesTestAleat.Trace;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;



public class GraphToDataTest {

	private Trace _trace1, _trace2, _trace3, _trace4;
	private GeneratorTraces _graph;
	
	private static final String _DB_PATH = "target/graphe1-db";
	
	@Before
	public void start() throws Exception {
		_graph = new GeneratorTraces(_DB_PATH);
		
		_trace1 = new Trace();
		_trace2 = new Trace();
		_trace3 = new Trace();
		_trace4 = new Trace();
		
		State state1 = new State("Racine", false);
		State state2 = new State("Etat 1", false);
		State state3 = new State("Etat 2", false);
		State state4 = new State("Etat 3", true);
		State state5 = new State("Etat 4", true);
		
		_trace1.addAction(new Action("A1", state1, state2));
		_trace1.addAction(new Action("A3", state2, state5));
		
		_trace2.addAction(new Action("A1", state1, state2));
		_trace2.addAction(new Action("A2", state2, state3));
		_trace2.addAction(new Action("A2", state3, state4));
		
		_trace3.addAction(new Action("A1", state1, state2));
		_trace3.addAction(new Action("A2", state2, state3));
		_trace3.addAction(new Action("A3", state3, state2));
		_trace3.addAction(new Action("A3", state2, state5));
		
		_trace4.addAction(new Action("A1", state1, state2));
		_trace4.addAction(new Action("A2", state2, state3));
		_trace4.addAction(new Action("A3", state3, state2));
		_trace4.addAction(new Action("A2", state2, state3));
		_trace4.addAction(new Action("A2", state3, state4));
	}
	
	@After
	public void stop() {
		_graph = null;
		_trace1 = null;
		_trace2 = null;
		_trace3 = null;
		_trace4 = null;
	}
	
	@Test
	public void testDeleteInformations() {
		_graph.deleteInformations();
		assertEquals(_graph.getInitState(), null);
		assertEquals(_graph.getStates(), new ArrayList<State>());
	}
	
	@Test
	public void testDataRegister1() throws Exception {
		
	}
	
	@Test
	public void testDataRegister2() throws Exception {
		_graph.deleteInformations();
		_graph.dataRegister();
		
		// teste l'etat initial
		assertEquals(_graph.getInitState(),
				new State(GeneratorTraces._nameStartNode, false));
		
		// teste si les etats sont bien tous enregistres
		assertTrue(_graph.getStates().contains(new State("Noeud1", false)));
		assertTrue(_graph.getStates().contains(new State("Noeud2", false)));
		assertTrue(_graph.getStates().contains(new State("Noeud3", true)));
		assertTrue(_graph.getStates().contains(new State("Noeud4", true)));
		
		// teste si les etats suivants des etats via la transition
		//font bien partis de la liste de tous les etats
		for (int i = 0; i < _graph.getStates().size(); i++) {
			assertTrue(_graph.getStates().containsAll(
					_graph.getStates().get(i).getStatesAfter()));
		}
	}
	
	@Test
	public void testTraceGenerateAleat1() {
		List<Trace> traces = _graph.traceGenerateAleat(0, 10, true, false);
		assertTrue(traces.isEmpty());
	}
	
	@Test
	public void testTraceGenerateAleat2() {
		List<Trace> traces = _graph.traceGenerateAleat(10, 0, true, false);
		assertTrue(traces.isEmpty());
	}
	
	@Test
	public void testTraceGenerateAleat3() {
		List<Trace> traces = _graph.traceGenerateAleat(10, 1, true, false);
		assertTrue(traces.isEmpty());
	}
	
	@Test
	public void testTraceGenerateAleat4() {
		List<Trace> traces = _graph.traceGenerateAleat(50, 2, true, false);
		assertEquals(traces.size(), 1);
		assertEquals(traces.get(0), _trace1);
	}
	
	@Test
	public void testTraceGenerateAleat5() {
		List<Trace> traces = _graph.traceGenerateAleat(50, 3, true, false);
		assertEquals(traces.size(), 2);
		assertTrue(traces.contains(_trace1));
		assertTrue(traces.contains(_trace2));
	}
	
	@Test
	public void testTraceGenerateAleat6() {
		List<Trace> traces = _graph.traceGenerateAleat(100, 5, true, false);
		assertEquals(traces.size(), 4);
		assertTrue(traces.contains(_trace1));
		assertTrue(traces.contains(_trace2));
		assertTrue(traces.contains(_trace3));
		assertTrue(traces.contains(_trace4));
	}
	
	@Test
	public void testTraceGenerateAleat7() {
		List<Trace> traces = _graph.traceGenerateAleat(100, 100, true, false);
		assertTrue(traces.size() > 0);
		assertTrue(traces.size() <= 100);
	}
	
	@Test
	public void testTraceGenerateAleat8() {
		List<Trace> traces = _graph.traceGenerateAleat(30, 2, false, true);
		assertTrue(traces.size() > 0);
		assertEquals(traces.size(), 30);
		for (Trace trace: traces) {
			trace.equals(_trace1);
		}
	}
	
}
