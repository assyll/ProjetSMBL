package generatorTracesTest;
import static org.junit.Assert.*;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import generatorTracesTest.Action;
import generatorTracesTest.State;


public class ActionTest {

	private State state1;
	private State state2;
	private Action action;
	
	@Before
	public void start() {
		state1 = new State("State1", false);
		state2 = new State("State2", true);
		action = new Action("action 1", state1, state2);
	}
	
	@After
	public void stop() {
		state1 = state2 = null;
		action = null;
	}
	
	@Test
	public void testToString() {
		assertEquals(action.toString(), "action 1");
	}
	
	@Test
	public void testGetStateStart() {
		assertEquals(action.getStateStart(), state1);
	}
	
	@Test
	public void testGetStateEnd() {
		assertEquals(action.getStateEnd(), state2);
	}
	
	@Test
	public void testSetVisited() {
		assertFalse(action.isVisited());
		action.setVisited(true);
		assertTrue(action.isVisited());
		action.setVisited(false);
		assertFalse(action.isVisited());
	}
	
	@Test
	public void testEquals1() {
		assertEquals(action, new Action("action 1", state1, state2));
	}
	
	@Test
	public void testEquals2() {
		assertNotEquals(action, new Action("action 1", state1, state1));
	}
	
	@Test
	public void testEquals3() {
		assertNotEquals(action, new Action("action 1", state2, state2));
	}
	
	@Test
	public void testEquals4() {
		assertNotEquals(action, new Action("action 2", state1, state2));
	}
	
	@Test
	public void testEquals5() {
		assertNotEquals(action, "action 1");
	}
	
}
