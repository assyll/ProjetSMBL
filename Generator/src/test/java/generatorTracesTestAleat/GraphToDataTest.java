package generatorTracesTestAleat;
import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.List;

import generatorTracesTestAleat.Action;
import generatorTracesTestAleat.GraphToData;
import generatorTracesTestAleat.State;
import generatorTracesTestAleat.Trace;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;



public class GraphToDataTest {

	private Trace _trace1;
	private Trace _trace2;
	private Trace _trace3;
	private Trace _trace4;
	private GraphToData _graph;
	
	private static final String _DB_PATH = "target/graphe1-db";
	
	@Before
	public void start() throws Exception {
		_graph = new GraphToData(_DB_PATH);
		
		_trace1 = new Trace();
		_trace2 = new Trace();
		_trace3 = new Trace();
		_trace4 = new Trace();
		
		_trace1.addAction(new Action("A1"));
		_trace1.addAction(new Action("A3"));
		
		_trace2.addAction(new Action("A1"));
		_trace2.addAction(new Action("A2"));
		_trace2.addAction(new Action("A2"));
		
		_trace3.addAction(new Action("A1"));
		_trace3.addAction(new Action("A2"));
		_trace3.addAction(new Action("A3"));
		_trace3.addAction(new Action("A3"));
		
		_trace4.addAction(new Action("A1"));
		_trace4.addAction(new Action("A2"));
		_trace4.addAction(new Action("A3"));
		_trace4.addAction(new Action("A2"));
		_trace4.addAction(new Action("A2"));
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
				new State(GraphToData._nameStartNode, false));
		
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
	public void testTraceGenerate1() {
		List<Trace> traces = _graph.traceGenerate(0, 10, true, false);
		assertTrue(traces.isEmpty());
	}
	
	@Test
	public void testTraceGenerate2() {
		List<Trace> traces = _graph.traceGenerate(10, 0, true, false);
		assertTrue(traces.isEmpty());
	}
	
	@Test
	public void testTraceGenerate3() {
		List<Trace> traces = _graph.traceGenerate(10, 1, true, false);
		assertTrue(traces.isEmpty());
	}
	
	@Test
	public void testTraceGenerate4() {
		List<Trace> traces = _graph.traceGenerate(50, 2, true, false);
		assertEquals(traces.size(), 1);
		assertEquals(traces.get(0), _trace1);
	}
	
	@Test
	public void testTraceGenerate5() {
		List<Trace> traces = _graph.traceGenerate(50, 3, true, false);
		assertEquals(traces.size(), 2);
		assertTrue(traces.contains(_trace1));
		assertTrue(traces.contains(_trace2));
	}
	
	@Test
	public void testTraceGenerate6() {
		List<Trace> traces = _graph.traceGenerate(100, 5, true, false);
		assertEquals(traces.size(), 4);
		assertTrue(traces.contains(_trace1));
		assertTrue(traces.contains(_trace2));
		assertTrue(traces.contains(_trace3));
		assertTrue(traces.contains(_trace4));
	}
	
	@Test
	public void testTraceGenerate7() {
		List<Trace> traces = _graph.traceGenerate(100, 100, true, false);
		assertTrue(!traces.isEmpty());
		assertTrue(traces.size() <= 100);
	}
	
}
