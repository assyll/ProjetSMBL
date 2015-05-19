package generatorGrapheRefAleat;

import static org.junit.Assert.*;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class NodeToCreateTest {

	private NodeToCreate nodeToCreate;
	
	@Before
	public void start() {
		nodeToCreate = new NodeToCreate("Noeud 1");
	}
	
	@After
	public void stop() {
		nodeToCreate = null;
	}
	
	@Test
	public void testGetName() {
		assertEquals(nodeToCreate.getName(), "Noeud 1");
	}
	
	@Test
	public void testAddTransitions() {
		assertTrue(nodeToCreate.getTransitions().isEmpty());
		nodeToCreate.addTransition(new Transition ("T1"), nodeToCreate);
		assertEquals(nodeToCreate.getTransitions().size(), 1);
	}
	
}
