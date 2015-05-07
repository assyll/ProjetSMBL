package generator;
import static org.junit.Assert.*;
import generator.Action;
import generator.Trace;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;


public class TraceTest {

	Trace trace;
	Action action1, action2;
	
	@Before
	public void start() {
		trace = new Trace();
		action1 = new Action("action 1");
		action2 = new Action("action 2");
	}
	
	@After
	public void finish() {
		trace = null;
		action1 = null;
		action2 = null;
	}
	
	@Test
	public void testAddAction() {
		assertEquals(trace.getActions().size(), 0);
		trace.addAction(action1);
		assertEquals(trace.getActions().get(0), action1);
	}
	
	@Test
	public void testGetActions() {
		assertEquals(trace.getActions().size(), 0);
		trace.addAction(action1);
		trace.addAction(action2);
		assertEquals(trace.getActions().size(), 2);
	}
	
	@Test
	public void testToString1() {
		trace.addAction(action1);
		trace.addAction(action2);
		trace.addAction(action1);
		assertEquals(trace.toString(),
				"action 1 -> action 2 -> action 1 -> END");
	}
	
	@Test
	public void testToString2() {
		trace.addAction(action1);
		trace.addAction(null);
		assertEquals(trace.toString(),
				"action 1 -> END");
	}
	
	@Test
	public void testEquals1() {
		assertNotEquals(trace, "END");
	}
	
	@Test
	public void testEquals2() {
		Trace trace2 = new Trace();
		trace.addAction(action1);
		trace2.addAction(action1);
		assertEquals(trace, trace2);
	}
	
}
