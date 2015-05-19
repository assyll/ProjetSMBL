package generatorGrapheRefAleat;

import static org.junit.Assert.*;

import org.junit.Test;

public class TransitionTest {

	@Test
	public void testGetName() {
		assertEquals(new Transition("T1").getName(), "T1");
	}
	
}
