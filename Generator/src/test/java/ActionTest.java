import static org.junit.Assert.*;
import org.junit.Test;

import generator.Action;


public class ActionTest {

	@Test
	public void testToString() {
		Action action = new Action("action 1");
		assertEquals(action.toString(), "action 1");
	}
	
	@Test
	public void testEquals() {
		Action action = new Action("action 1");
		assertEquals(action, new Action("action 1"));
	}
	
	
}
