package generatorTracesTestAleat;
import static org.junit.Assert.*;

import org.junit.Test;

import generatorTracesTestAleat.Action;


public class ActionTest {

	@Test
	public void testToString() {
		Action action = new Action("action 1");
		assertEquals(action.toString(), "action 1");
	}
	
	@Test
	public void testEquals1() {
		Action action = new Action("action 1");
		assertEquals(action, new Action("action 1"));
	}
	
	@Test
	public void testEquals2() {
		Action action = new Action("action 1");
		assertNotEquals(action, "action 1");
	}
	
}
